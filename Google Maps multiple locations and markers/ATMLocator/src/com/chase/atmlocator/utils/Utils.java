package com.chase.atmlocator.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.chase.atmlocator.MyApplication;

public class Utils {
	private static Utils instance = null;
	private static final String APP_SHARED_PREFS = "com.location.chase";
	private SharedPreferences appSharedPrefs;
	private Editor prefsEditor;

	public static Utils getInstance() {
		if (instance != null) {
			return instance;
		} else {
			return instance = new Utils(MyApplication.mContext);
		}
	}

	/**
	 * Saving data in shared preferences which will store life time of
	 * Application
	 */
	public Utils(Context context) {
		this.appSharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS,
				Context.MODE_PRIVATE);
		this.prefsEditor = appSharedPrefs.edit();
	}

	/** Saving Latitude */
	public void saveLatitude(String lat) {
		Log.e("lat", "lat====>"+lat);
		prefsEditor.putString("latitude", lat);
		prefsEditor.commit();
	}

	/** Getting latitude */
	public String getLatitude() {
		return appSharedPrefs.getString("latitude", "0.0");
	}

	/** Saving longitude */
	public void saveLongitude(String longitude) {
		Log.e("longitude", "longitude====>"+longitude);
		prefsEditor.putString("longitude", longitude);
		prefsEditor.commit();
	}

	/** Getting longitude */
	public String getLongitude() {
		return appSharedPrefs.getString("longitude", "0.0");
	}

	public boolean isNetworkAvailable(Context context) {
		try {
			ConnectivityManager connectivity = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				NetworkInfo[] info = connectivity.getAllNetworkInfo();
				if (info != null) {
					for (int i = 0; i < info.length; i++) {
						if (info[i].getState() == NetworkInfo.State.CONNECTED) {
							return true;
						}
					}
				}
			}
		} catch (Exception e) {
		}
		return false;
	}

	public void showToast(String message) {
		Toast.makeText(MyApplication.mContext, message, Toast.LENGTH_SHORT)
				.show();
	}

}
