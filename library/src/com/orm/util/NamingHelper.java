package com.orm.util;

import android.text.TextUtils;

import com.orm.dsl.Column;
import com.orm.dsl.Table;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class NamingHelper {


    /**
     * Converts a given CamelCasedString to UPPER_CASE_UNDER_SCORE
     * @param camelCased a non empty camelCased string
     * @return the equivalent string converted to UPPER_CASE_UNDER_SCORE unless camelCased equals "_id"
     * (not case sensitive) in which case "_id" is returned.
     */
    public static String toSQLNameDefault(String camelCased) {

        if (camelCased.equalsIgnoreCase("_id"))
            return "_id";

        List<String> words = new LinkedList<String>();
        StringBuilder currentWord = new StringBuilder();
        for(int i=0; i< camelCased.length(); i++){
            char c = camelCased.charAt(i);
            if(Character.isUpperCase(c)){
                if(currentWord.length() == 0) words.add(currentWord.toString());
                currentWord = new StringBuilder();
            }
            currentWord.append(Character.toUpperCase(c));
        }
        return TextUtils.join("_", words);
    }

    /**
     * Maps a Java Field object to the data base's column name.
     * @param field the Field that will be mapped.
     * @return the name of the given Field. If the Field is annotated with {@link com.orm.dsl.Column}
     *         then the {@link com.orm.dsl.Column#name() name()} will be returned if not then the Field's
     *         {@link java.lang.reflect.Field#getName() getName()} will be converted from CamelCase to
     *         UNDER_SCORE notation.
     */
    public static String toSQLName(Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            Column annotation = field.getAnnotation(Column.class);
            return annotation.name();
        }

        return toSQLNameDefault(field.getName());
    }

    /**
     * Maps a Java Class to the name of the class.
     * @param table the table
     * @return if the given class is annotated with {@link com.orm.dsl.Table} then the value for
     *         {@link com.orm.dsl.Table#name() name()} will be returned, if not then the class' simple name
     *         will be converted from CamelCase to UNDER_SCORE.
      */
    public static String toSQLName(Class<?> table) {

        if (table.isAnnotationPresent(Table.class)) {
            Table annotation = table.getAnnotation(Table.class);
            if ("".equals(annotation.name())) {
                return NamingHelper.toSQLNameDefault(table.getSimpleName());
            }
            return annotation.name();
        }
        return NamingHelper.toSQLNameDefault(table.getSimpleName());
    }
}
