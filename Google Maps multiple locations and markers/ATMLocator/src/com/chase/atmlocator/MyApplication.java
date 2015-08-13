package com.chase.atmlocator;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
	public static Context mContext;
	private static MyApplication instance = null;
	@Override
	public void onCreate() {
		super.onCreate();
		mContext = this;
	}
	
	public static MyApplication getInstance() {
		if (instance == null) {
			instance=new MyApplication();
		} 
		return instance;
	}

}
