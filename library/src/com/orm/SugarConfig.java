package com.orm;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SugarConfig {

    static Map<Class<?>, List<Field>> fields = new HashMap<Class<?>, List<Field>>();

    private static String databasePassword = "DEFAULT_ENCRYPTION_KEY";
    private static Integer databaseVersion = 1;
    private static String databaseName = "Sugar.db";
    private static String domainPackageName = "";

    private static boolean databasePasswordSet = false;
    private static boolean databaseVersionSet = false;
    private static boolean databaseNameSet = false;
    private static boolean domainPackageNameSet = false;

    public static void loadDefaults(Context context) {
        if(!databaseVersionSet) databaseVersion = getMetaDataInteger(context, "VERSION");
        if(!databasePasswordSet) databasePassword = getMetaDataString(context, "ENCRYPTION_KEY");
        if(!domainPackageNameSet) domainPackageName = getMetaDataString(context, "DOMAIN_PACKAGE_NAME");
        if(!databaseNameSet) databaseName = getMetaDataString(context, "DATABASE");
    }

    public static void setDatabasePassword(String encryptionKey) {
        databasePassword = encryptionKey;
        databasePasswordSet = true;
    }

    public static void setDatabaseVersion(Integer version) {
        databaseVersion = version;
        databaseVersionSet = true;
    }

    public static void setDatabaseName(String dbName) {
        databaseName = dbName;
        databaseNameSet = true;
    }

    public static void setDomainPackageName(String packageName) {
        domainPackageName = packageName;
        domainPackageNameSet = true;
    }

    public static String getDatabaseName(Context context) {
        return databaseName;
    }

    public static void setFields(Class<?> clazz, List<Field> fieldz){
         fields.put(clazz, fieldz);
    }

    public static List<Field> getFields(Class<?> clazz){

        if(fields.containsKey(clazz)){
            List<Field> list = fields.get(clazz);
            return Collections.synchronizedList(list);
        }

        return null;
    }

    public static void clearCache(){
        fields.clear();
        fields = new HashMap<Class<?>, List<Field>>();
    }

    public static int getDatabaseVersion(Context context) {
        return databaseVersion;
    }

    public static String getDatabasePassword(Context context) {
        return databasePassword;
    }

    public static String getDomainPackageName(Context context){
        return domainPackageName;
    }

    public static boolean getDebugEnabled(Context context) {
        return getMetaDataBoolean(context, "QUERY_LOG");
    }

    public static String getMetaDataString(Context context, String name) {
        String value = null;

        PackageManager pm = context.getPackageManager();
        try {
            ApplicationInfo ai = pm.getApplicationInfo(context.getPackageName(), 128);
            value = ai.metaData.getString(name);
        } catch (Exception e) {
            Log.d("sugar", "Couldn't find config value: " + name);
        }

        return value;
    }

    public static Integer getMetaDataInteger(Context context, String name) {
        Integer value = null;

        PackageManager pm = context.getPackageManager();
        try {
            ApplicationInfo ai = pm.getApplicationInfo(context.getPackageName(), 128);
            value = ai.metaData.getInt(name);
        } catch (Exception e) {
            Log.d("sugar", "Couldn't find config value: " + name);
        }

        return value;
    }

    public static Boolean getMetaDataBoolean(Context context, String name) {
        Boolean value = false;

        PackageManager pm = context.getPackageManager();
        try {
            ApplicationInfo ai = pm.getApplicationInfo(context.getPackageName(), 128);
            value = ai.metaData.getBoolean(name);
        } catch (Exception e) {
            Log.d("sugar", "Couldn't find config value: " + name);
        }

        return value;
    }
}
