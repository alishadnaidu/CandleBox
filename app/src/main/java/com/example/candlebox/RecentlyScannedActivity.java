package com.example.candlebox;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class RecentlyScannedActivity extends AppCompatActivity {

    public static final String TAG = "RecentlyScannedActivity";

    protected CandlesAdapter adapter;
    protected List<RecentlyScannedCandles> allCandles;

    private RecyclerView rvRecentlyScanned;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recently_scanned);

        rvRecentlyScanned = findViewById(R.id.rvRecentlyScanned);

        // initialize the array that will hold posts and create an adapter for RecentlyScannedCandles
        allCandles = new ArrayList<>();
        adapter = new CandlesAdapter(this, allCandles);

        rvRecentlyScanned.setAdapter(adapter);
        rvRecentlyScanned.setLayoutManager(new LinearLayoutManager(this));


        queryRecentlyScanned();
    }


    // load in the entries in the recently scanned candles class
    private void queryRecentlyScanned() {
        ParseQuery<RecentlyScannedCandles> query = ParseQuery.getQuery(RecentlyScannedCandles.class);
        query.include(RecentlyScannedCandles.KEY_RECENTRAWBARCODEVALUE);
        query.addDescendingOrder("createdAt");
        query.findInBackground(new FindCallback<RecentlyScannedCandles>() {
            @Override
            public void done(List<RecentlyScannedCandles> candles, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting candles", e);
                    return;
                }
                for (RecentlyScannedCandles candle: candles) {
                    Log.i(TAG, "Candle name: " + candle.getRecentCandleName());
                }
                allCandles.addAll(candles);
                adapter.notifyDataSetChanged();
            }
        });
    }

    //inflate actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // comes into play when an item in the actionbar is clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // if the logout icon is tapped, log out + navigate to the login screen
        if (item.getItemId() == R.id.logout) {
            ParseUser.logOut();
            ParseUser currentUser = ParseUser.getCurrentUser();
            Intent i = new Intent(RecentlyScannedActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
            return true;
        }

        // if the home icon is tapped, navigate to home screen
        if (item.getItemId() == R.id.home) {
            Intent i = new Intent(RecentlyScannedActivity.this, MainActivity.class);
            startActivity(i);
            return true;
        }

        // if the scan icon is tapped, navigate to barcode scanning screen
        if (item.getItemId() == R.id.scan) {
            Intent i = new Intent(RecentlyScannedActivity.this, BarcodeScannerActivity.class);
            startActivity(i);
            return true;
        }

        // if the add icon is tapped, navigate to logging screen
        if (item.getItemId() == R.id.add) {
            Intent i = new Intent(RecentlyScannedActivity.this, AddActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
