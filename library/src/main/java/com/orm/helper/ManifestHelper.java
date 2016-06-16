package com.orm.helper;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import static com.orm.util.ContextUtil.*;

/**
 * Helper class for accessing properties in the AndroidManifest
 */
public final class ManifestHelper {
    private static final String LOG_TAG = "Sugar";
    private static Boolean debugEnabled = null;

    /**
     * Key for the database name meta data.
     */
    public final static String METADATA_DATABASE = "DATABASE";

    /**
     * Key for the database version meta data.
     */
    public final static String METADATA_VERSION = "VERSION";
    public final static String METADATA_DOMAIN_PACKAGE_NAME = "DOMAIN_PACKAGE_NAME";
    public final static String METADATA_QUERY_LOG = "QUERY_LOG";

    /**
     * The default name for the database unless specified in the AndroidManifest.
     */
    public final static String DATABASE_DEFAULT_NAME = "Sugar.db";

    //Prevent instantiation
    private ManifestHelper() { }

    /**
     * Grabs the database version from the manifest.
     *
     * @return the database version as specified by the {@link #METADATA_VERSION} version or 1 of
     *         not present
     */
    public static int getDatabaseVersion() {
        Integer databaseVersion = getMetaDataInteger(METADATA_VERSION);

        if ((databaseVersion == null) || (databaseVersion == 0)) {
            databaseVersion = 1;
        }

        return databaseVersion;
    }

    /**
     * Grabs the domain name of the model classes from the manifest.
     *
     * @return the package String that Sugar uses to search for model classes
     */
    public static String getDomainPackageName() {
        String domainPackageName = getMetaDataString(METADATA_DOMAIN_PACKAGE_NAME);

        if (domainPackageName == null) {
            domainPackageName = "";
        }

        return domainPackageName;
    }

    /**
     * Grabs the name of the database file specified in the manifest.
     *
     * @return the value for the {@value #METADATA_DATABASE} meta data in the AndroidManifest or
     *         {@link #DATABASE_DEFAULT_NAME} if not present
     */
    public static String getDatabaseName() {
        String databaseName = getMetaDataString(METADATA_DATABASE);

        if (databaseName == null) {
            databaseName = DATABASE_DEFAULT_NAME;
        }

        return databaseName;
    }

    public static String getDbName() {
        return getDatabaseName();
    }

    /**
     * Grabs the debug flag from the manifest.
     *
     * @return true if the debug flag is enabled
     */
    public static boolean isDebugEnabled() {
        return (null == debugEnabled) ? debugEnabled = getMetaDataBoolean(METADATA_QUERY_LOG) : debugEnabled;
    }

    private static String getMetaDataString(String name) {
        PackageManager pm = getPackageManager();
        String value = null;

        try {
            ApplicationInfo ai = pm.getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            value = ai.metaData.getString(name);
        } catch (Exception e) {
            if (ManifestHelper.isDebugEnabled()) {
                Log.d(LOG_TAG, "Couldn't find config value: " + name);
            }
        }

        return value;
    }

    private static Integer getMetaDataInteger(String name) {
        PackageManager pm = getPackageManager();
        Integer value = null;

        try {
            ApplicationInfo ai = pm.getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            value = ai.metaData.getInt(name);
        } catch (Exception e) {
            if (ManifestHelper.isDebugEnabled()) {
                Log.d(LOG_TAG, "Couldn't find config value: " + name);
            }
        }

        return value;
    }

    private static Boolean getMetaDataBoolean(String name) {
        PackageManager pm = getPackageManager();
        Boolean value = false;

        try {
            ApplicationInfo ai = pm.getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            value = ai.metaData.getBoolean(name);
        } catch (Exception e) {
            Log.d(LOG_TAG, "Couldn't find config value: " + name);
        }

        return value;
    }
}
