package com.orm;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

public class SugarConfig {
    public static String getDatabaseName(Context context) {
    String databaseName = getMetaDataString(context, "DATABASE");

    if (databaseName == null) {
      databaseName = "Sugar.db";
    }

    return databaseName;
  }

  public static int getDatabaseVersion(Context context) {
    Integer databaseVersion = Integer.valueOf(getMetaDataString(context, "VERSION"));

    if ((databaseVersion == null) || (databaseVersion == 0)) {
      databaseVersion = 1;
    }

    return databaseVersion;
  }

  public static boolean getDebugEnabled(Context context) {
    String queryDebugEnabled = getMetaDataString(context, "QUERY_DEBUG_ENABLED");

    if ("true".equalsIgnoreCase(queryDebugEnabled)) {
      return true;
    }

    return false;
  }

  public static String getMetaDataString(Context context, String name) {
    String value = null;

    PackageManager pm = context.getPackageManager();
    try
    {
      ApplicationInfo ai = pm.getApplicationInfo(context.getPackageName(), 128);
      value = ai.metaData.getString(name);
    }
    catch (Exception e)
    {
      Log.d("sugar", "Couldn't find config value: " + name);
    }

    return value;
  }
}
