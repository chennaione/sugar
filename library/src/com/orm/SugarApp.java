package com.orm;

import android.content.Context;

public class SugarApp{

    private Database database;
    private static SugarApp sugarContext;

    public static SugarApp getSugarContext(){
        return sugarContext;
    }

    protected Database getDatabase() {
        return database;
    }
    
    private SugarApp(){
    	
    }

    /**
     * Initializes Sugar ORM. It should be initialized in Application class.
     * @param context applicatinContext
     */
    public static void init(Context context){
    	sugarContext = new SugarApp();
    	sugarContext.database = new Database(context);
    }
    /**
     * Closes sugar DB connection.
     */
    public static void closeDB(){
    	if(sugarContext.database != null){
    		sugarContext.database.getDB().close();
    	}
    }
}
