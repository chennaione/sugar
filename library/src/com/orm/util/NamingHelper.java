package com.orm.util;

import com.orm.dsl.Column;
import com.orm.dsl.Table;

import java.lang.reflect.Field;

public class NamingHelper {

    public static String toSQLNameDefault(String javaNotation) {
        if (javaNotation.equalsIgnoreCase("_id")) {
            return "_id";
        }

        StringBuilder sb = new StringBuilder();
        char[] buf = javaNotation.toCharArray();

        for (int i = 0; i < buf.length; i++) {
            char prevChar = (i > 0) ? buf[i - 1] : 0;
            char c = buf[i];
            char nextChar = (i < buf.length - 1) ? buf[i + 1] : 0;
            boolean isFirstChar = (i == 0);

            if (isFirstChar || Character.isLowerCase(c) || Character.isDigit(c)) {
                sb.append(Character.toUpperCase(c));
            } else if (Character.isUpperCase(c)) {
                if (Character.isLetterOrDigit(prevChar)) {
                    if (Character.isLowerCase(prevChar)) {
                        sb.append('_').append(Character.toUpperCase(c));
                    } else if (nextChar > 0 && Character.isLowerCase(nextChar)) {
                        sb.append('_').append(Character.toUpperCase(c));
                    } else {
                        sb.append(c);
                    }
                } else {
                    sb.append(c);
                }
            }
        }

        return sb.toString();
    }

    public static String toSQLName(Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            Column annotation = field.getAnnotation(Column.class);
            return annotation.name();
        }

        return toSQLNameDefault(field.getName());
    }

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
