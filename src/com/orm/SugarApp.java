package com.orm;

import java.util.*;

import static com.orm.dsl.Collection.set;

public class SugarApp extends android.app.Application{

    Database database;
    Set<SugarDb> entities;

    public void onCreate(){
        super.onCreate();
        database = new Database(this);
        entities = set();
    }

    public void onTerminate(){

    if (this.database != null) {
      this.database.closeDB();
    }
        super.onTerminate();
    }


}
