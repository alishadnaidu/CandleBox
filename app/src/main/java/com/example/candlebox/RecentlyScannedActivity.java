package com.example.candlebox;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class RecentlyScannedActivity extends AppCompatActivity {
    public static final String TAG = "RecentlyScannedActivity";
    protected CandleAdapter adapter;
    protected List<Candles> allCandles;
    private RecyclerView rvRecentlyScanned = findViewById(R.id.rvRecentlyScanned);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recently_scanned);

        rvRecentlyScanned = findViewById(R.id.rvRecentlyScanned);

        // initialize the array that will hold candles and create a CandleAdapter
        allCandles = new ArrayList<>();
        adapter = new CandleAdapter(this, allCandles);

        rvRecentlyScanned.setAdapter(adapter);
        rvRecentlyScanned.setLayoutManager(new LinearLayoutManager(this));
        getCandleData();
    }

    //helper method to find candle in database and redirect to upload candle page if it doesn't exist
    private void getCandleData() {
        ParseQuery<Candles> query = ParseQuery.getQuery(Candles.class);
        query.include(Candles.KEY_CANDLENAME);

        //uses getFirstInBackground rather than findInBackground so it doesn't have to search through whole db
        query.findInBackground(new FindCallback<Candles>() {
            @Override
            public void done(List<Candles> objects, ParseException e) {
                // check for errors
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                // for debugging purposes let's print every post description to logcat
                for (Candles candle : objects) {
                    Log.i(TAG, "Candle name: " + candle.getCandleName() + ", toxicity: " + candle.getIngredients());
                }

                // save received posts to list and notify adapter of new data
                allCandles.addAll(objects);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
