package com.orm;

import android.content.Context;

import com.orm.util.ContextUtil;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

public class SugarContext {

    private static SugarDbConfiguration dbConfiguration = null;
    private static SugarContext instance = null;
    private SugarDb sugarDb;
    private Map<Object, Long> entitiesMap;

    private SugarContext() {
        this.sugarDb = SugarDb.getInstance();
        this.entitiesMap = Collections.synchronizedMap(new WeakHashMap<Object, Long>());
    }
    
    public static SugarContext getSugarContext() {
        if (instance == null) {
            throw new NullPointerException("SugarContext has not been initialized properly. Call SugarContext.init(Context) in your Application.onCreate() method and SugarContext.terminate() in your Application.onTerminate() method.");
        }
        return instance;
    }

    public static void init(Context context) {
        ContextUtil.init(context);
        instance = new SugarContext();
        dbConfiguration = null;
    }

    public static void init(Context context, SugarDbConfiguration configuration) {
        init(context);
        dbConfiguration = configuration;
    }


    public static void terminate() {
        if (instance == null) {
            return;
        }
        instance.doTerminate();
        ContextUtil.terminate();
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

    public static SugarDbConfiguration getDbConfiguration() {
        return dbConfiguration;
    }

    public SugarDb getSugarDb() {
        return sugarDb;
    }

    public Map<Object, Long> getEntitiesMap() {
        return entitiesMap;
    }
}
