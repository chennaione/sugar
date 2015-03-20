package com.orm;

public class SugarApp extends android.app.Application{

    private Database database;
    private static SugarApp sugarContext;

    public void onCreate(){
        super.onCreate();
        SugarConfig.loadDefaults(this);
        SugarApp.sugarContext = this;
        this.database = new Database(this);
    }

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

    protected void setDatabasePassword(String encryptionKey) {
        SugarConfig.setDatabasePassword(encryptionKey);
    }
    protected void setDBVersion(Integer version) {
        SugarConfig.setDatabaseVersion(version);
    }
    protected void setDomainPackageName(String packageName) {
        SugarConfig.setDomainPackageName(packageName);
    }
    protected void setDatabaseName(String databaseName) {
        SugarConfig.setDatabaseName(databaseName);
    }
}
