package com.orm;

import android.content.Context;
import net.sqlcipher.database.SQLiteDatabase;


public class Database {
    private final String databasePassword;
    private SugarDb sugarDb;
    private SQLiteDatabase sqLiteDatabase;

    public Database(Context context){
        SQLiteDatabase.loadLibs(context);
        databasePassword = SugarConfig.getDatabasePassword(context);
        this.sugarDb  = new SugarDb(context);
    }


    public synchronized SQLiteDatabase getDB() {
        if (this.sqLiteDatabase == null) {
            this.sqLiteDatabase = this.sugarDb.getWritableDatabase(databasePassword);
        }

        return this.sqLiteDatabase;
    }

}
