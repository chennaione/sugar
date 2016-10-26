package com.orm;

import android.app.Application;

import net.sqlcipher.database.SQLiteDatabase;

public class SugarApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SQLiteDatabase.loadLibs(this);
        SugarContext.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }

}
