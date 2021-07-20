package com.example.candlebox;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class RecentlyScannedActivity extends AppCompatActivity {

    public static final String TAG = "RecentlyScannedActivity";

    protected CandlesAdapter adapter;
    protected List<Candles> allCandles;

    private RecyclerView rvRecentlyScanned;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recently_scanned);

        rvRecentlyScanned = findViewById(R.id.rvRecentlyScanned);

        // initialize the array that will hold posts and create a PostsAdapter
        allCandles = new ArrayList<>();
        adapter = new CandlesAdapter(this, allCandles);

        rvRecentlyScanned.setAdapter(adapter);
        rvRecentlyScanned.setLayoutManager(new LinearLayoutManager(this));
        queryCandles();
    }

    private void queryCandles() {
        ParseQuery<Candles> query = ParseQuery.getQuery(Candles.class);
        query.include(Candles.KEY_RAWBARCODEVALUE);
        query.addDescendingOrder("createdAt");
        query.findInBackground(new FindCallback<Candles>() {
            @Override
            public void done(List<Candles> candles, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting candles", e);
                    return;
                }

                for (Candles candle: candles) {
                    Log.i(TAG, "Candle name: " + candle.getCandleName());
                }

                allCandles.addAll(candles);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
