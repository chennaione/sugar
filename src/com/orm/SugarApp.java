package com.orm;

import android.util.Log;

import java.util.*;

import static com.orm.dsl.Collection.set;

public class SugarApp extends android.app.Application{

    Database database;

    public void onCreate(){
        super.onCreate();
        this.database = new Database(this);
    }

    public void onTerminate(){

    if (this.database != null) {
      this.database.closeDB();
    }
        super.onTerminate();
    }


}
