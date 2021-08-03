package com.example.candlebox.CandleStuff.RecentlyScannedDisplay;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.candlebox.CandleStuff.BarcodeScanner.BarcodeScannerActivity;
import com.example.candlebox.CandleStuff.EmissionStats.AddActivity;
import com.example.candlebox.CandleStuff.EmissionStats.MainActivity;
import com.example.candlebox.CandleStuff.Login.LoginActivity;
import com.example.candlebox.CandleStuff.Models.RecentlyScannedCandles;
import com.example.candlebox.R;
import com.example.candlebox.SpotifyStuff.SpotifyWebActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class RecentlyScannedActivity extends AppCompatActivity {

    public static final String TAG = "RecentlyScannedActivity";

    protected CandlesAdapter adapter;
    protected List<RecentlyScannedCandles> allCandles;

    public String mostRecentBarcode;

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

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        Intent homeIntent = new Intent(RecentlyScannedActivity.this, MainActivity.class);
                        startActivity(homeIntent);
                        return true;
                    case R.id.action_scan:
                        Intent addIntent = new Intent(RecentlyScannedActivity.this, BarcodeScannerActivity.class);
                        startActivity(addIntent);
                        return true;
                    case R.id.action_spotify:
                        Intent spotifyIntent = new Intent(RecentlyScannedActivity.this, SpotifyWebActivity.class);
                        startActivity(spotifyIntent);
                        return true;
                    case R.id.action_logout:
                        ParseUser.logOut();
                        // this will be null bc there is no current user
                        ParseUser currentUser = ParseUser.getCurrentUser();
                        Intent logoutIntent = new Intent(RecentlyScannedActivity.this, LoginActivity.class);
                        startActivity(logoutIntent);
                        finish();
                        return true;
                    default: return true;
                }
            }
        });
    }


    // load in the entries in the recently scanned candles class
    private void queryRecentlyScanned() {
        ParseQuery<RecentlyScannedCandles> query = ParseQuery.getQuery(RecentlyScannedCandles.class);
        query.include(RecentlyScannedCandles.KEY_RECENTRAWBARCODEVALUE);
        //only include the candle-burning stats of the current user
        query.whereEqualTo(RecentlyScannedCandles.KEY_USER, ParseUser.getCurrentUser());
        query.addDescendingOrder("createdAt");
        query.findInBackground(new FindCallback<RecentlyScannedCandles>() {
            @Override
            public void done(List<RecentlyScannedCandles> candles, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting candles", e);
                    return;
                }
                allCandles.addAll(candles);
                adapter.notifyDataSetChanged();
            }
        });
    }


//    // gets the raw barcode value of the most recent entry
//    private String getFirstCandle() {
//        ParseQuery<RecentlyScannedCandles> query = ParseQuery.getQuery(RecentlyScannedCandles.class);
//        query.include(RecentlyScannedCandles.KEY_RECENTRAWBARCODEVALUE);
//        query.addDescendingOrder("createdAt");
//        query.getFirstInBackground(new GetCallback<RecentlyScannedCandles>() {
//            @Override
//            public void done(RecentlyScannedCandles object, ParseException e) {
//                if (e != null) {
//                    Log.e(TAG, "Issue with finding first candle", e);
//                    return;
//                }
//                mostRecentBarcode = object.getRecentRawBarcodeValue();
//                Log.i(TAG, "first candle barcode: " + mostRecentBarcode);
//            }
//        });
//        return mostRecentBarcode;
//    }
}
