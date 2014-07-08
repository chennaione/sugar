package com.example;

import com.orm.SugarApp;

import android.app.Application;

public class ClientApp extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		SugarApp.init(this);
	}
	
	@Override
	public void onTerminate() {
		super.onTerminate();
		SugarApp.terminate();
	}
}
