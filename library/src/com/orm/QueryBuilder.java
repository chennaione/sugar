package com.orm;

import org.joda.time.DateTime;

import java.util.Arrays;
import java.util.List;

public class QueryBuilder {

    private static List longTypes = Arrays.asList(Boolean.class, Integer.class, Long.class, java.util.Date.class,
                                                  java.util.Calendar.class, java.sql.Date.class, DateTime.class);
    private static List longTypeStrings = Arrays.asList(Boolean.TYPE, Integer.TYPE, Long.TYPE);
    private static List floatTypes = Arrays.asList(Double.class, Float.class);
    private static List floatTypeStrings = Arrays.asList(Double.TYPE, Float.TYPE);
    private static List textTypes = Arrays.asList(String.class, Character.class);
    private static List textTypeStrings = Arrays.asList(Character.TYPE);

    public static String getColumnType(Class type) {
        if (longTypes.contains(type) || longTypeStrings.contains(type)) {
            return "INTEGER";
        }

        if (floatTypes.contains(type) || floatTypeStrings.contains(type)) {
            return "FLOAT";
        }

        if (textTypes.contains(type) || textTypeStrings.contains(type)) {
            return "TEXT";
        }

        return "";
    }
}
