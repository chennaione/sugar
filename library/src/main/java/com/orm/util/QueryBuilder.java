package com.orm.util;

import com.orm.SugarRecord;

import java.lang.RuntimeException;
import java.lang.StringBuilder;
import java.math.BigDecimal;

public class QueryBuilder {

    public static String getColumnType(Class<?> type) {
        if ((type.equals(Boolean.class)) ||
                (type.equals(Boolean.TYPE)) ||
                (type.equals(Integer.class)) ||
                (type.equals(Integer.TYPE)) ||
                (type.equals(Long.class)) ||
                (type.equals(Long.TYPE)) || (
                (!type.isPrimitive()) &&
                        (SugarRecord.class.isAssignableFrom(type))))  {
            return "INTEGER";
        }

        if ((type.equals(java.util.Date.class)) ||
                (type.equals(java.sql.Date.class)) ||
                (type.equals(java.util.Calendar.class))) {
            return "INTEGER NULL";
        }

        if (type.getName().equals("[B")) {
            return "BLOB";
        }

        if ((type.equals(Double.class)) || (type.equals(Double.TYPE)) || (type.equals(Float.class)) ||
                (type.equals(Float.TYPE))) {
            return "FLOAT";
        }

        if ((type.equals(String.class)) || (type.equals(Character.TYPE)) ||
                (type.equals(BigDecimal.class))) {
            return "TEXT";
        }

        return "";
    }

    public static String generatePlaceholders(int numberOfArgs) {
        if (numberOfArgs < 1) {
            throw new RuntimeException("The number of arguments must be greater than or equal to 1.");
        }

        StringBuilder stringBuilder = new StringBuilder(numberOfArgs * 2 - 1);
        stringBuilder.append("?");
        for (int i = 1; i < numberOfArgs; i++) {
            stringBuilder.append(",?");
        }
        return stringBuilder.toString();
    }
}
