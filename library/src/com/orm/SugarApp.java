package com.orm;

public class SugarApp extends android.app.Application{

    private Database database;
    private static SugarApp sugarContext;

    public void onCreate(){
        super.onCreate();
        SugarApp.sugarContext = this;
        this.database = new Database(this);
    }

    /*
     * Per issue #106 on Github, this method won't be called in
     * any real Android device. This method is used purely in
     * emulated process environments such as an emulator or
     * Robolectric Android mock.
     */
    public void onTerminate(){
        if (this.database != null) {
            this.database.getDB().close();
        }
        super.onTerminate();
    }

    public static SugarApp getSugarContext(){
        return sugarContext;
    }

    protected Database getDatabase() {
        return database;
    }
}
