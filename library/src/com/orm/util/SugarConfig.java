package com.orm.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import android.annotation.SuppressLint;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SugarConfig {

    static Map<Class<?>, List<Field>> fields = new HashMap<Class<?>, List<Field>>();

    public static String getDatabaseName(Context context) {
        return getDatabaseName(context, isTestEnviroment(context));
    }

    public static String getDatabaseName(Context context,
        Boolean isTestEnviroment) {
        String databaseName = getMetaDataString(context, "DATABASE");

        if (databaseName == null) {
            databaseName = "Sugar.db";
        }

        if (isTestEnviroment) {
            databaseName = "test" + databaseName;
        }

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
        Integer databaseVersion = getMetaDataInteger(context, "VERSION");

        if ((databaseVersion == null) || (databaseVersion == 0)) {
            databaseVersion = 1;
        }

        return databaseVersion;
    }

    public static String getDomainPackageName(Context context){
        String domainPackageName = getMetaDataString(context, "DOMAIN_PACKAGE_NAME");

        if (domainPackageName == null) {
            domainPackageName = "";
        }

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

    public static Boolean isTestEnviroment(Context context) {
        return (isClassLoaded(getDomainPackageName(context) + ".SugarTest"));
    }

    private static Boolean isClassLoaded(String name) {
        try {
            Class.forName(name);
            Log.d("Sugar", "Found class: " + name);
            return true;
        } catch( ClassNotFoundException e ) {
            return false;
        }
    }
}
