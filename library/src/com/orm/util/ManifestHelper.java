package com.orm.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * Helper class for accessing properties in the AndroidManifest
 */
public class ManifestHelper {

    /**
     * TODO document the meaning of this 128 flag.
     */
    private final static int FLAG = 128;

    /**
     * Key for the database name meta data
     */
    public final static String METADATA_DATABASE = "DATABASE";
    /**
     * Key for the database verison meta data
     */
    public final static String METADATA_VERSION = "VERSION";
    public final static String METADATA_DOMAIN_PACKAGE_NAME = "DOMAIN_PACKAGE_NAME";
    public final static String METADATA_QUERY_LOG = "QUERY_LOG";

    /**
     * The default name for the database unless specified in the AndroidManifest
     */
    public final static String DATABASE_DEFAULT_NAME = "Sugar.db";

    /**
     * @return the database version as specified by the {@link #METADATA_DATABASE} version or 1 of
     *         not present.
     */
    public static int getDatabaseVersion(Context context) {
        Integer databaseVersion = getMetaDataInteger(context, METADATA_VERSION);

        if ((databaseVersion == null) || (databaseVersion == 0)) {
            databaseVersion = 1;
        }

        return databaseVersion;
    }

    /**
     *
     * @param context
     * @return
     */
    public static String getDomainPackageName(Context context){
        String domainPackageName = getMetaDataString(context, METADATA_DOMAIN_PACKAGE_NAME);

        if (domainPackageName == null) {
            domainPackageName = "";
        }

        return domainPackageName;
    }

    /**
     * @param context a reference to the context
     * @return the value for the {@value #METADATA_DATABASE } meta data in the AndroidManifest or
     *         {@link #DATABASE_DEFAULT_NAME} if not present.
     */
    public static String getDatabaseName(Context context) {
        String databaseName = getMetaDataString(context, METADATA_DATABASE);

        if (databaseName == null) {
            databaseName = DATABASE_DEFAULT_NAME;
        }

        return databaseName;
    }

    /**
     * @return true if the debug flag is enabled
     */
    public static boolean getDebugEnabled(Context context) {
        return getMetaDataBoolean(context, METADATA_QUERY_LOG);
    }

    public static String getMetaDataString(Context context, String name) {
        String value = null;

        PackageManager pm = context.getPackageManager();
        try {
            ApplicationInfo ai = pm.getApplicationInfo(context.getPackageName(), FLAG);
            value = ai.metaData.getString(name);
        } catch (Exception e) {
            Log.d("sugar", "Couldn't find config value: " + name);
        }

        return value;
    }

    /**
     * Returns the value associated with the given name, or 0 if
     * no mapping of the desired type exists for the given key.
     *
     * @param name a String
     * @return an int value
     */
    public static Integer getMetaDataInteger(Context context, String name) {
        Integer value = null;

        PackageManager pm = context.getPackageManager();
        try {
            ApplicationInfo ai = pm.getApplicationInfo(context.getPackageName(), FLAG);
            value = ai.metaData.getInt(name);
        } catch (Exception e) {
            Log.d("sugar", "Couldn't find config value: " + name);
        }

        return value;
    }

    /**
     * Returns the value associated with the given name, or false if
     * no mapping of the desired type exists for the given key.
     *
     * @param name the name as present in the AndroidManifest
     * @return a boolean value
     */
    public static Boolean getMetaDataBoolean(Context context, String name) {
        Boolean value = false;

        PackageManager pm = context.getPackageManager();
        try {
            ApplicationInfo ai = pm.getApplicationInfo(context.getPackageName(), FLAG);
            value = ai.metaData.getBoolean(name);
        } catch (Exception e) {
            Log.d("sugar", "Couldn't find config value: " + name);
        }

        return value;
    }
}
