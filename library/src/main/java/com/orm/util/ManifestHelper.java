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
     * Key for the database name meta data.
     */
    public final static String METADATA_DATABASE = "DATABASE";
    /**
     * Key for the database verison meta data.
     */
    public final static String METADATA_VERSION = "VERSION";
    public final static String METADATA_DOMAIN_PACKAGE_NAME = "DOMAIN_PACKAGE_NAME";
    public final static String METADATA_QUERY_LOG = "QUERY_LOG";
    /**
     * The default name for the database unless specified in the AndroidManifest.
     */
    public final static String DATABASE_DEFAULT_NAME = "Sugar.db";

    /**
     * Grabs the database version from the manifest.
     *
     * @param context  the {@link android.content.Context} of the Android application
     * @return the database version as specified by the {@link #METADATA_VERSION} version or 1 of
     *         not present
     */
    public static int getDatabaseVersion(Context context) {
        Integer databaseVersion = getMetaDataInteger(context, METADATA_VERSION);

        if ((databaseVersion == null) || (databaseVersion == 0)) {
            databaseVersion = 1;
        }

        return databaseVersion;
    }

    /**
     * Grabs the domain name of the model classes from the manifest. 
     *
     * @param context  the {@link android.content.Context} of the Android application
     * @return the package String that Sugar uses to search for model classes
     */
    public static String getDomainPackageName(Context context){
        String domainPackageName = getMetaDataString(context, METADATA_DOMAIN_PACKAGE_NAME);

        if (domainPackageName == null) {
            domainPackageName = "";
        }

        return domainPackageName;
    }

    /**
     * Grabs the name of the database file specified in the manifest.
     *
     * @param context  the {@link android.content.Context} of the Android application
     * @return the value for the {@value #METADATA_DATABASE} meta data in the AndroidManifest or
     *         {@link #DATABASE_DEFAULT_NAME} if not present
     */
    public static String getDatabaseName(Context context) {
        String databaseName = getMetaDataString(context, METADATA_DATABASE);

        if (databaseName == null) {
            databaseName = DATABASE_DEFAULT_NAME;
        }

        return databaseName;
    }

    /**
     * Grabs the debug flag from the manifest.
     *
     * @param context  the {@link android.content.Context} of the Android application
     * @return true if the debug flag is enabled
     */
    public static boolean getDebugEnabled(Context context) {
        return getMetaDataBoolean(context, METADATA_QUERY_LOG);
    }

    private static String getMetaDataString(Context context, String name) {
        String value = null;

        PackageManager pm = context.getPackageManager();
        try {
            ApplicationInfo ai = pm.getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            value = ai.metaData.getString(name);
        } catch (Exception e) {
            Log.d("sugar", "Couldn't find config value: " + name);
        }

        return value;
    }

    private static Integer getMetaDataInteger(Context context, String name) {
        Integer value = null;

        PackageManager pm = context.getPackageManager();
        try {
            ApplicationInfo ai = pm.getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            value = ai.metaData.getInt(name);
        } catch (Exception e) {
            Log.d("sugar", "Couldn't find config value: " + name);
        }

        return value;
    }

    private static Boolean getMetaDataBoolean(Context context, String name) {
        Boolean value = false;

        PackageManager pm = context.getPackageManager();
        try {
            ApplicationInfo ai = pm.getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            value = ai.metaData.getBoolean(name);
        } catch (Exception e) {
            Log.d("sugar", "Couldn't find config value: " + name);
        }

        return value;
    }

}
