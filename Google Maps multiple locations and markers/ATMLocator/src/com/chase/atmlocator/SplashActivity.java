package com.chase.atmlocator;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;

import com.chase.atmlocator.utils.Utils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

public class SplashActivity extends Activity implements
		GoogleApiClient.ConnectionCallbacks,
		GoogleApiClient.OnConnectionFailedListener,LocationListener {
	private static final int REQUEST_CHECK_SETTINGS = 1000;
	private int MINIMUM_DELAY = 300;
	private GoogleApiClient googleApiClient;
	private LocationRequest locationRequest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		enableLocation();
	}

	public void navigation(int delay) {
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				handler.removeCallbacks(this);
				startActivity(new Intent(SplashActivity.this,
						ATMLocatorActivity.class));
				finish();
			}

		}, delay);
	}

	public void enableLocation() {
		if (googleApiClient == null) {
			googleApiClient = new GoogleApiClient.Builder(SplashActivity.this)
					.addApi(LocationServices.API)
					.addConnectionCallbacks(SplashActivity.this)
					.addOnConnectionFailedListener(this).build();
			googleApiClient.connect();

			locationRequest = LocationRequest.create();
			locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
			locationRequest.setInterval(30 * 1000);
			locationRequest.setFastestInterval(5 * 1000);
			LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
					.addLocationRequest(locationRequest);

			// **************************
			builder.setAlwaysShow(true); // this is the key ingredient
			// **************************

			PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi
					.checkLocationSettings(googleApiClient, builder.build());
			result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
				@Override
				public void onResult(LocationSettingsResult result) {
					final Status status = result.getStatus();
					final LocationSettingsStates state = result
							.getLocationSettingsStates();
					switch (status.getStatusCode()) {
					case LocationSettingsStatusCodes.SUCCESS:
						// All location settings are satisfied. The client can
						// initialize location
						// requests here.
						
						break;
					case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
						// Location settings are not satisfied. But could be
						// fixed by showing the user
						// a dialog.
						try {
							// Show the dialog by calling
							// startResolutionForResult(),
							// and check the result in onActivityResult().
							status.startResolutionForResult(
									SplashActivity.this, REQUEST_CHECK_SETTINGS);
						} catch (IntentSender.SendIntentException e) {
							// Ignore the error.
						}
						break;
					case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
						// Location settings are not satisfied. However, we have
						// no way to fix the
						// settings so we won't show the dialog.
						break;
					}
				}
			});
		}
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnected(Bundle arg0) {
//		// TODO Auto-generated method stub
		startLocationUpdates();
		
	}
	
	 protected void startLocationUpdates() {
	        PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
	                googleApiClient, locationRequest, this);
	        Log.d("", "Location update started ..............: ");
	    }

	@Override
	public void onConnectionSuspended(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		switch (requestCode) {
		case REQUEST_CHECK_SETTINGS:
			switch (resultCode) {
			case Activity.RESULT_OK:
				// All required changes were successfully made
				break;
			case Activity.RESULT_CANCELED:
				// The user was asked to change settings, but chose not to
				finish();
				break;
			default:
				break;
			}
			break;
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		if (location!=null) {
			Utils.getInstance().saveLatitude(String.valueOf(location.getLatitude()));
			Utils.getInstance().saveLongitude(String.valueOf(location.getLongitude()));
			navigation(MINIMUM_DELAY);
		}
		
	}
	
	protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                googleApiClient, this);
        Log.d("", "Location update stopped .......................");
    }
	@Override
	protected void onDestroy() {
		stopLocationUpdates();
		super.onDestroy();
	}

}
