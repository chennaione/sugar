package com.orm;

import android.content.Context;

import com.google.common.collect.MapMaker;
import com.orm.entity.EntityListenerManager;

import java.util.concurrent.ConcurrentMap;

public class SugarContext {

    private static SugarContext instance = null;
    private SugarDb sugarDb;
    private Context context;
    private ConcurrentMap<Object, Long> entitiesMap;
    private EntityListenerManager entitylistenerManager;

    private SugarContext(Context context) {
        this.context = context;
        this.sugarDb = new SugarDb(context);
        this.entitiesMap = new MapMaker().weakKeys().makeMap();
        this.entitylistenerManager = new EntityListenerManager(context);
    }

    public static SugarContext getSugarContext() {
        if (instance == null) {
            throw new NullPointerException("SugarContext has not been initialized properly. Call SugarContext.init(Context) in your Application.onCreate() method and SugarContext.terminate() in your Application.onTerminate() method.");
        }
        return instance;
    }

    public static void init(Context context) {
        instance = new SugarContext(context);
    }

    public static void terminate() {
        if (instance == null) {
            return;
        }
        instance.doTerminate();
    }

    /*
     * Per issue #106 on Github, this method won't be called in
     * any real Android device. This method is used purely in
     * emulated process environments such as an emulator or
     * Robolectric Android mock.
     */
    private void doTerminate() {
        if (this.sugarDb != null) {
            this.sugarDb.getDB().close();
        }
    }

    protected SugarDb getSugarDb() {
        return sugarDb;
    }

    ConcurrentMap<Object, Long> getEntitiesMap() {
        return entitiesMap;
    }

    public EntityListenerManager getEntitylistenerManager() {
        return entitylistenerManager;
    }
}
