package com.orm;

import android.app.Application;

import java.util.List;

public class SugarApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }

    public List<Class> getModelsClassesList() {
        return null;
    }

}
