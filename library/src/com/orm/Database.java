package com.orm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


public class Database {
    private SugarDb sugarDb;
    private SQLiteDatabase sqLiteDatabase;

    public Database(Context context){
        this.sugarDb  = new SugarDb(context);
    }


  public SQLiteDatabase openDB() {
    this.sqLiteDatabase = this.sugarDb.getWritableDatabase();

    return this.sqLiteDatabase;
  }

  public void closeDB() {
    if (this.sqLiteDatabase != null) {
      this.sqLiteDatabase.close();
      this.sqLiteDatabase = null;
    }
  }
}
