package com.orm.util;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.util.Log;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;
import com.orm.dsl.Table;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import dalvik.system.DexFile;

public class ReflectionUtil {

    public static List<Field> getTableFields(Class table) {
        List<Field> fieldList = SugarConfig.getFields(table);
        if (fieldList != null) return fieldList;

        Log.d("Sugar", "Fetching properties");
        List<Field> typeFields = new ArrayList<Field>();

        getAllFields(typeFields, table);

        List<Field> toStore = new ArrayList<Field>();
        for (Field field : typeFields) {
            if (!field.isAnnotationPresent(Ignore.class) && !Modifier.isStatic(field.getModifiers()) && !Modifier.isTransient(field.getModifiers())) {
                toStore.add(field);
            }
        }

        SugarConfig.setFields(table, toStore);
        return toStore;
    }

    private static List<Field> getAllFields(List<Field> fields, Class<?> type) {
        Collections.addAll(fields, type.getDeclaredFields());

        if (type.getSuperclass() != null) {
            fields = getAllFields(fields, type.getSuperclass());
        }

        return fields;
    }

    public static void addFieldValueToColumn(ContentValues values, Field column, Object object,
                                             Map<Object, Long> entitiesMap) {
        column.setAccessible(true);
        Class<?> columnType = column.getType();
        try {
            String columnName = NamingHelper.toSQLName(column);
            Object columnValue = column.get(object);

            if (columnType.isAnnotationPresent(Table.class)) {
                Field field = null;
                try {
                    field = columnType.getDeclaredField("id");
                    field.setAccessible(true);
                    values.put(columnName,
                            (field != null)
                                    ? String.valueOf(field.get(columnValue)) : "0");
                } catch (NoSuchFieldException e) {
                    if (entitiesMap.containsKey(columnValue)) {
                        values.put(columnName, entitiesMap.get(columnValue));
                    }
                }
            } else if (SugarRecord.class.isAssignableFrom(columnType)) {
                values.put(columnName,
                        (columnValue != null)
                                ? String.valueOf(((SugarRecord) columnValue).getId())
                                : "0");
            } else {
                if (columnType.equals(Short.class) || columnType.equals(short.class)) {
                    values.put(columnName, (Short) columnValue);
                } else if (columnType.equals(Integer.class) || columnType.equals(int.class)) {
                    values.put(columnName, (Integer) columnValue);
                } else if (columnType.equals(Long.class) || columnType.equals(long.class)) {
                    values.put(columnName, (Long) columnValue);
                } else if (columnType.equals(Float.class) || columnType.equals(float.class)) {
                    values.put(columnName, (Float) columnValue);
                } else if (columnType.equals(Double.class) || columnType.equals(double.class)) {
                    values.put(columnName, (Double) columnValue);
                } else if (columnType.equals(Boolean.class) || columnType.equals(boolean.class)) {
                    values.put(columnName, (Boolean) columnValue);
                } else if (columnType.equals(BigDecimal.class)) {
                    try {
                        values.put(columnName, column.get(object).toString());
                    } catch (NullPointerException e) {
                        values.putNull(columnName);
                    }
                } else if (Timestamp.class.equals(columnType)) {
                    try {
                        values.put(columnName, ((Timestamp) column.get(object)).getTime());
                    } catch (NullPointerException e) {
                        values.put(columnName, (Long) null);
                    }
                } else if (Date.class.equals(columnType)) {
                    try {
                        values.put(columnName, ((Date) column.get(object)).getTime());
                    } catch (NullPointerException e) {
                        values.put(columnName, (Long) null);
                    }
                } else if (Calendar.class.equals(columnType)) {
                    try {
                        values.put(columnName, ((Calendar) column.get(object)).getTimeInMillis());
                    } catch (NullPointerException e) {
                        values.put(columnName, (Long) null);
                    }
                } else if (columnType.equals(byte[].class)) {
                    if (columnValue == null) {
                        values.put(columnName, "".getBytes());
                    } else {
                        values.put(columnName, (byte[]) columnValue);
                    }
                } else {
                    if (columnValue == null) {
                        values.putNull(columnName);
                    } else if (columnType.isEnum()) {
                        values.put(columnName, ((Enum) columnValue).name());
                    } else {
                        values.put(columnName, String.valueOf(columnValue));
                    }
                }
            }

        } catch (IllegalAccessException e) {
            Log.e("Sugar", e.getMessage());
        }
    }

    public static void setFieldValueFromCursor(Cursor cursor, Field field, Object object) {
        field.setAccessible(true);
        try {
            Class fieldType = field.getType();
            String colName = NamingHelper.toSQLName(field);

            int columnIndex = cursor.getColumnIndex(colName);

            if (cursor.isNull(columnIndex)) {
                return;
            }

            if (colName.equalsIgnoreCase("id")) {
                long cid = cursor.getLong(columnIndex);
                field.set(object, Long.valueOf(cid));
            } else if (fieldType.equals(long.class) || fieldType.equals(Long.class)) {
                field.set(object,
                        cursor.getLong(columnIndex));
            } else if (fieldType.equals(String.class)) {
                String val = cursor.getString(columnIndex);
                field.set(object, val != null && val.equals("null") ? null : val);
            } else if (fieldType.equals(double.class) || fieldType.equals(Double.class)) {
                field.set(object,
                        cursor.getDouble(columnIndex));
            } else if (fieldType.equals(boolean.class) || fieldType.equals(Boolean.class)) {
                field.set(object,
                        cursor.getString(columnIndex).equals("1"));
            } else if (fieldType.equals(int.class) || fieldType.equals(Integer.class)) {
                field.set(object,
                        cursor.getInt(columnIndex));
            } else if (fieldType.equals(float.class) || fieldType.equals(Float.class)) {
                field.set(object,
                        cursor.getFloat(columnIndex));
            } else if (fieldType.equals(short.class) || fieldType.equals(Short.class)) {
                field.set(object,
                        cursor.getShort(columnIndex));
            } else if (fieldType.equals(BigDecimal.class)) {
                String val = cursor.getString(columnIndex);
                field.set(object, val != null && val.equals("null") ? null : new BigDecimal(val));
            } else if (fieldType.equals(Timestamp.class)) {
                long l = cursor.getLong(columnIndex);
                field.set(object, new Timestamp(l));
            } else if (fieldType.equals(Date.class)) {
                long l = cursor.getLong(columnIndex);
                field.set(object, new Date(l));
            } else if (fieldType.equals(Calendar.class)) {
                long l = cursor.getLong(columnIndex);
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(l);
                field.set(object, c);
            } else if (fieldType.equals(byte[].class)) {
                byte[] bytes = cursor.getBlob(columnIndex);
                if (bytes == null) {
                    field.set(object, "".getBytes());
                } else {
                    field.set(object, cursor.getBlob(columnIndex));
                }
            } else if (Enum.class.isAssignableFrom(fieldType)) {
                try {
                    Method valueOf = field.getType().getMethod("valueOf", String.class);
                    String strVal = cursor.getString(columnIndex);
                    Object enumVal = valueOf.invoke(field.getType(), strVal);
                    field.set(object, enumVal);
                } catch (Exception e) {
                    Log.e("Sugar", "Enum cannot be read from Sqlite3 database. Please check the type of field " + field.getName());
                }
            } else
                Log.e("Sugar", "Class cannot be read from Sqlite3 database. Please check the type of field " + field.getName() + "(" + field.getType().getName() + ")");
        } catch (IllegalArgumentException e) {
            Log.e("field set error", e.getMessage());
        } catch (IllegalAccessException e) {
            Log.e("field set error", e.getMessage());
        }
    }

    private static Field getDeepField(String fieldName, Class<?> type) throws NoSuchFieldException {
        try {
            Field field = type.getDeclaredField(fieldName);
            return field;
        } catch (NoSuchFieldException e) {
            Class superclass = type.getSuperclass();
            if (superclass != null) {
                Field field = getDeepField(fieldName, superclass);
                return field;
            } else {
                throw e;
            }
        }
    }

    public static void setFieldValueForId(Object object, Long value) {
        try {
            Field field = getDeepField("id", object.getClass());
            field.setAccessible(true);
            field.set(object, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static List<Class> getDomainClasses(Context context) {
        List<Class> domainClasses = new ArrayList<Class>();
        try {
            for (String className : getAllClasses(context)) {
                Class domainClass = getDomainClass(className, context);
                if (domainClass != null) domainClasses.add(domainClass);
            }
        } catch (IOException e) {
            Log.e("Sugar", e.getMessage());
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("Sugar", e.getMessage());
        }

        return domainClasses;
    }


    private static Class getDomainClass(String className, Context context) {
        Class<?> discoveredClass = null;
        try {
            discoveredClass = Class.forName(className, true, context.getClass().getClassLoader());
        } catch (ClassNotFoundException e) {
            Log.e("Sugar", e.getMessage());
        }

        if ((discoveredClass != null) &&
                ((SugarRecord.class.isAssignableFrom(discoveredClass) &&
                        !SugarRecord.class.equals(discoveredClass)) ||
                        discoveredClass.isAnnotationPresent(Table.class)) &&
                !Modifier.isAbstract(discoveredClass.getModifiers())) {

            Log.i("Sugar", "domain class : " + discoveredClass.getSimpleName());
            return discoveredClass;

        } else {
            return null;
        }
    }

    private static final String EXTRACTED_NAME_EXT = ".classes";
    private static final String EXTRACTED_SUFFIX = ".zip";

    private static final String SECONDARY_FOLDER_NAME = "code_cache" + File.separator +
            "secondary-dexes";

    private static final String PREFS_FILE = "multidex.version";
    private static final String KEY_DEX_NUMBER = "dex.number";

    private static SharedPreferences getMultiDexPreferences(Context context) {
        return context.getSharedPreferences(PREFS_FILE,
                Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB
                        ? Context.MODE_PRIVATE
                        : Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS);
    }

    private static List<String> getAllClasses(Context context) throws PackageManager.NameNotFoundException, IOException {
        List<String> paths = getSourcePaths(context);
        List<String> classNames = new ArrayList<>();
        try {
            for (int i = 0; i <paths.size(); i++){
                String path = paths.get(i);
                DexFile dexfile = null;
                if (path.endsWith(EXTRACTED_SUFFIX)) {
                    //NOT use new DexFile(path) here, because it will throw "permission error in /data/dalvik-cache"
                    dexfile = DexFile.loadDex(path, path + ".tmp", 0);
                } else {
                    dexfile = new DexFile(path);
                }

                Enumeration<String> dexEntries = dexfile.entries();
                while (dexEntries.hasMoreElements()) {
                    classNames.add(dexEntries.nextElement());
                }
            }
        } catch (NullPointerException e) {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Enumeration<URL> urls = classLoader.getResources("");
            List<String> fileNames = new ArrayList<String>();
            while (urls.hasMoreElements()) {
                String classDirectoryName = urls.nextElement().getFile();
                if (classDirectoryName.contains("bin") || classDirectoryName.contains("classes")) {
                    File classDirectory = new File(classDirectoryName);
                    for (File filePath : classDirectory.listFiles()) {
                        populateFiles(filePath, fileNames, "");
                    }
                    classNames.addAll(fileNames);
                }
            }
        } finally {
            if (null != dexfile) dexfile.close();
        }
        return classNames;
    }

    public static List<String> getSourcePaths(Context context) throws PackageManager.NameNotFoundException, IOException {
        ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
        File sourceApk = new File(applicationInfo.sourceDir);
        File dexDir = new File(applicationInfo.dataDir, SECONDARY_FOLDER_NAME);

        List<String> sourcePaths = new ArrayList<String>();
        //default apk path
        sourcePaths.add(applicationInfo.sourceDir);

        String extractedFilePrefix = sourceApk.getName() + EXTRACTED_NAME_EXT;

        //get the dex number
        int totalDexNumber = getMultiDexPreferences(context).getInt(KEY_DEX_NUMBER, 1);

        //get other dexes
        for (int i = 2; i <= totalDexNumber; i++) {
            String fileName = extractedFilePrefix + i + EXTRACTED_SUFFIX;
            File extractedFile = new File(dexDir, fileName);
            if (extractedFile.isFile()) {
                sourcePaths.add(extractedFile.getAbsolutePath());
            } else {
                throw new IOException("Missing extracted secondary dex file '" +
                        extractedFile.getPath() + "'");
            }
        }

        return sourcePaths;
    }
    private static void populateFiles(File path, List<String> fileNames, String parent) {
        if (path.isDirectory()) {
            for (File newPath : path.listFiles()) {
                if ("".equals(parent)) {
                    populateFiles(newPath, fileNames, path.getName());
                } else {
                    populateFiles(newPath, fileNames, parent + "." + path.getName());
                }
            }
        } else {
            String pathName = path.getName();
            String classSuffix = ".class";
            pathName = pathName.endsWith(classSuffix) ?
                    pathName.substring(0, pathName.length() - classSuffix.length()) : pathName;
            if ("".equals(parent)) {
                fileNames.add(pathName);
            } else {
                fileNames.add(parent + "." + pathName);
            }
        }
    }

    private static String getSourcePath(Context context) throws PackageManager.NameNotFoundException {
        return context.getPackageManager().getApplicationInfo(context.getPackageName(), 0).sourceDir;
    }
}
