package com.orm;

import android.content.Context;

public class SugarApp {
	
	private static SugarApp instance = null;
	private Context context;
	private Database database;
	
	private SugarApp(Context context) {
		this.context = context;
		this.database = new Database(context);
	}

	public static SugarApp getSugarContext(){
		if (instance == null) 
			throw new NullPointerException("SugarApp has not been initialized properly. Call Sugar.init(Context) in your Application.onCreate() method and Sugar.terminate(Context).");
			
	    return instance;
	}

	public static void init(Context context) {
		instance = new SugarApp(context);
	}
	
	public static void terminate() {
		if (instance == null)
			return;
		
		instance.doTerminate();
	}

	protected Database getDatabase() {
        return database;
    }
	
	private void doTerminate() {
		if (database == null)
			return;

        this.database.getDB().close();
	}
}

