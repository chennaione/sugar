package com.orm;

public class SugarApp extends android.app.Application{

    SugarDb sugarDb;

    public void onCreate(){
        super.onCreate();
        sugarDb = new SugarDb(this);
    }

    public void onTerminate(){
        super.onTerminate();
    }
}
