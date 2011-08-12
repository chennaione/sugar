package com.orm;

import java.util.HashSet;
import java.util.Set;
import static com.orm.dsl.Collection.set;

public class SugarApp extends android.app.Application{

    SugarDb sugarDb;
    Set<SugarDb> entities;

    public void onCreate(){
        super.onCreate();
        sugarDb = new SugarDb(this);
        entities = set();
    }

    public void onTerminate(){
        super.onTerminate();
    }
}
