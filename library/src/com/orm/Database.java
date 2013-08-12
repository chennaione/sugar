package com.orm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


public class Database {
    private SugarDb sugarDb;
    private SQLiteDatabase sqLiteDatabase;

    public Database(Context context) {
        this.sugarDb  = new SugarDb(context);
    }

    public synchronized SQLiteDatabase getDB() {
        if (this.sqLiteDatabase == null) {
            this.sqLiteDatabase = this.sugarDb.getWritableDatabase();
        }

        return this.sqLiteDatabase;
    }

    public void deleteDatabase() {
        this.sugarDb.deleteTables(this.getDB());
        this.sugarDb.onCreate(this.getDB());
    }
}
