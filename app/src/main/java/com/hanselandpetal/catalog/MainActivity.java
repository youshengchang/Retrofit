package com.hanselandpetal.catalog;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hanselandpetal.catalog.model.Flower;
import com.hanselandpetal.catalog.parsers.FlowerJSONParser;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends ListActivity {

	public static final String PHOTOS_BASE_URL =
		"http://services.hanselandpetal.com/photos/";

    public static final String ENDPOINT = "http://services.hanselandpetal.com";

	TextView output;
	ProgressBar pb;

	
	List<Flower> flowerList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		pb = (ProgressBar) findViewById(R.id.progressBar1);
		pb.setVisibility(View.INVISIBLE);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_get_data) {
			if (isOnline()) {
				requestData("http://services.hanselandpetal.com/feeds/flowers.json");
			} else {
				Toast.makeText(this, "Network isn't available", Toast.LENGTH_LONG).show();
			}
		}
		return false;
	}

	private void requestData(String uri) {

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .build();
        FlowerAPI api = adapter.create(FlowerAPI.class);

        api.getFeed(new Callback<List<Flower>>() {
            @Override
            public void success(List<Flower> flowers, Response response) {

                flowerList = flowers;
                updateDisplay();
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
	}

	protected void updateDisplay() {
		//Use FlowerAdapter to display data
		FlowerAdapter adapter = new FlowerAdapter(this, R.layout.item_flower, flowerList);
		setListAdapter(adapter);
	}
	
	protected boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		} else {
			return false;
		}
	}
	


}