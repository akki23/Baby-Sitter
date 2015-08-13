package com.chase.atmlocator;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.chase.atmlocator.utils.LocationListModel;

public class DetailsPage extends Activity {
	private TextView name_textView, state_textView, locType_textView,
			label_textView, address_textView, city_textView, zip_textView,
			lat_textView, lng_textView, bank_textView;
	private LocationListModel mLocationListModel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		initUI();
		mLocationListModel=(LocationListModel) getIntent().getSerializableExtra("LocationListModel");
		setValues();
	}

	public void initUI() {
		name_textView = (TextView) findViewById(R.id.name_textView);
		state_textView = (TextView) findViewById(R.id.state_textView);
		locType_textView = (TextView) findViewById(R.id.locType_textView);
		label_textView = (TextView) findViewById(R.id.label_textView);
		address_textView = (TextView) findViewById(R.id.address_textView);
		city_textView = (TextView) findViewById(R.id.city_textView);
		zip_textView = (TextView) findViewById(R.id.zip_textView);
		lat_textView = (TextView) findViewById(R.id.lat_textView);
		lng_textView = (TextView) findViewById(R.id.lng_textView);
		bank_textView = (TextView) findViewById(R.id.bank_textView);
	}

	public void setValues() {
		name_textView.setText(Html.fromHtml(String.format(getString(R.string.name),mLocationListModel.name)));
		state_textView.setText(Html.fromHtml(String.format(getString(R.string.state),mLocationListModel.state)));
		locType_textView.setText(Html.fromHtml(String.format(getString(R.string.loc_type),mLocationListModel.locType)));
		label_textView.setText(Html.fromHtml(String.format(getString(R.string.label),mLocationListModel.label)));
		address_textView.setText(Html.fromHtml(String.format(getString(R.string.address),mLocationListModel.address)));
		city_textView.setText(Html.fromHtml(String.format(getString(R.string.city),mLocationListModel.city)));
		zip_textView.setText(Html.fromHtml(String.format(getString(R.string.zip),mLocationListModel.zip)));
		lat_textView.setText(Html.fromHtml(String.format(getString(R.string.lat),mLocationListModel.lat)));
		lng_textView.setText(Html.fromHtml(String.format(getString(R.string.lng),mLocationListModel.lng)));
		bank_textView.setText(Html.fromHtml(String.format(getString(R.string.bank),mLocationListModel.bank)));
	}
}
