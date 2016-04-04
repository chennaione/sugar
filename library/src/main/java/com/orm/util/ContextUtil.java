package com.orm.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;

/**
 * @author jonatan.salas
 */
public final class ContextUtil {
    private static Context ctx;

    //Prevent instantiation
    private ContextUtil() { }

    public static void init(Context context) {
        if (null == context) {
            throw new IllegalArgumentException("context shouldn't be null!");
        }

        ctx = context;
    }

    public static void terminate() {
        ctx = null;
    }

    public static Context getContext() {
        return ctx;
    }

    public static AssetManager getAssets() {
        return getContext().getAssets();
    }

    public static PackageManager getPackageManager() {
        return getContext().getPackageManager();
    }

    public static String getPackageName() {
        return getContext().getPackageName();
    }

    public static SharedPreferences getSharedPreferences(String name, int mode) {
        return getContext().getSharedPreferences(name, mode);
    }
}
