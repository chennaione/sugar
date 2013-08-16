package com.orm;

public class QueryBuilder {

    public static String getColumnType(Class type) {
        if ((type.equals(Boolean.class)) ||
                (type.equals(Boolean.TYPE)) ||
                (type.equals(java.util.Date.class)) ||
                (type.equals(java.util.Calendar.class)) ||
                (type.equals(java.sql.Date.class)) ||
                (type.equals(Integer.class)) ||
                (type.equals(Integer.TYPE)) ||
                (type.equals(Long.class)) ||
                (type.equals(Long.TYPE)) || (
                (!type.isPrimitive()) &&
                        (type.getSuperclass() != null) &&
                        (type.getSuperclass().equals(SugarRecord.class)))) {
            return "INTEGER";
        }

        if ((type.equals(Double.class)) || (type.equals(Double.TYPE)) || (type.equals(Float.class)) ||
                (type.equals(Float.TYPE))) {
            return "FLOAT";
        }

        if ((type.equals(String.class)) || (type.equals(Character.TYPE))) {
            return "TEXT";
        }

        return "";
    }
}
