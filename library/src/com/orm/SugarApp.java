package com.orm;

public class SugarApp extends android.app.Application{

    private static SugarApp sugarContext;
    private SugarDb sugarDb;

    public static SugarApp getSugarContext() {
        return sugarContext;
    }

    public void onCreate(){
        super.onCreate();
        SugarApp.sugarContext = this;
        this.sugarDb = new SugarDb(this);
    }

    /*
     * Per issue #106 on Github, this method won't be called in
     * any real Android device. This method is used purely in
     * emulated process environments such as an emulator or
     * Robolectric Android mock.
     */
    public void onTerminate(){
        if (this.sugarDb != null) {
            this.sugarDb.getDB().close();
        }
        super.onTerminate();
    }

    protected SugarDb getSugarDb() {
        return sugarDb;
    }
}
