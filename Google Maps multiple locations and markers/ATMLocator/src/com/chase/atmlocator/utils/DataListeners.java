package com.chase.atmlocator.utils;

import java.util.ArrayList;



public interface DataListeners {
	public void DataVerifyPreEexcute();
	public void DataVerifyPostExecuteMethod(ArrayList<LocationListModel> mLocationListModels);
}
