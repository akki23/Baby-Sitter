package com.chase.atmlocator.utils;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.chase.atmlocator.R;

public class LocationAsyncTask extends AsyncTask<Void, Void, Boolean> {
	private ProgressDialog mProgressDialog;
	private Activity mActivity;
	private ArrayList<LocationListModel> mLocationListModels=new ArrayList<LocationListModel>();
	private DataListeners mDataListeners;
	private Utils mUtils;
	public LocationAsyncTask(Activity mActivity,DataListeners mDataListeners){
		this.mActivity=mActivity;
		this.mDataListeners=mDataListeners;;
		mUtils=Utils.getInstance();
	}
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		showProgressDialog();
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		try {
			String URL="https://m.chase.com/PSRWeb/location/list.action?lat="+mUtils.getLatitude()+"&lng="+mUtils.getLongitude();
//			String URL="https://m.chase.com/PSRWeb/location/list.action?lat="+"40.147864"+"&lng="+"-82.990959";
//			Log.e("", "URL===>"+URL);
			String response = HTTPSConnectiopns.httpGet(URL);
			Log.e("", "response====>"+response);
			JSONObject mainJsonObject=new JSONObject(response);
			JSONArray mainJsonArray=mainJsonObject.getJSONArray("locations");
			for (int i = 0; i < mainJsonArray.length(); i++) {
				JSONObject childObject=mainJsonArray.getJSONObject(i);
				LocationListModel mLocationListModel=new LocationListModel();
				mLocationListModel.state=childObject.getString("state");
				mLocationListModel.locType=childObject.getString("locType");
				mLocationListModel.label=childObject.getString("label");
				mLocationListModel.address=childObject.getString("address");
				mLocationListModel.city=childObject.getString("city");
				mLocationListModel.zip=childObject.getString("zip");
				mLocationListModel.name=childObject.getString("name");
				mLocationListModel.lat=childObject.getString("lat");
				mLocationListModel.lng=childObject.getString("lng");
				mLocationListModel.bank=childObject.getString("bank");
				mLocationListModels.add(mLocationListModel);
			}
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		dimissProgressDialog();
		mDataListeners.DataVerifyPostExecuteMethod(mLocationListModels);
	}
	
	public void showProgressDialog() {
		mProgressDialog = new ProgressDialog(mActivity);
		mProgressDialog.setMessage(mActivity.getString(R.string.fetching_data));
		mProgressDialog.setCancelable(true);
		mProgressDialog.setCanceledOnTouchOutside(false);
		mProgressDialog.show();
	}
	
	public void dimissProgressDialog() {
		if (mProgressDialog != null) {
			if (mProgressDialog.isShowing()) {
				mProgressDialog.dismiss();
			}
			mProgressDialog = null;
		}
	}

}
