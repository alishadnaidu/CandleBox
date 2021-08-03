package com.example.candlebox.CandleStuff.EmissionStats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.candlebox.CandleStuff.BarcodeScanner.BarcodeScannerActivity;
import com.example.candlebox.CandleStuff.Login.LoginActivity;
import com.example.candlebox.CandleStuff.Login.LogoutActivity;
import com.example.candlebox.CandleStuff.Models.Stats;
import com.example.candlebox.R;
import com.example.candlebox.SpotifyStuff.SpotifyWebActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    //these are set to public static because they are used in the fragments
    public static Integer totalHours;
    public static Integer amountOfCO2;
    public static Integer totalTrees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queryStats();

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(),
                MainActivity.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setSelectedItemId(R.id.action_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        Intent homeIntent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(homeIntent);
                        return true;
                    case R.id.action_scan:
                        Intent addIntent = new Intent(MainActivity.this, BarcodeScannerActivity.class);
                        startActivity(addIntent);
                        return true;
                    case R.id.action_spotify:
                        Intent spotifyIntent = new Intent(MainActivity.this, SpotifyWebActivity.class);
                        startActivity(spotifyIntent);
                        return true;
                    case R.id.action_logout:
                        Intent logoutIntent = new Intent(MainActivity.this, LogoutActivity.class);
                        startActivity(logoutIntent);
                        return true;
                    default: return true;
                }
            }
        });
    }

    //get stats on candle-burning and load it into each stats fragment
    private void queryStats() {
        totalHours = 0;
        amountOfCO2 = 0;
        totalTrees = 0;
        ParseQuery<Stats> query = ParseQuery.getQuery(Stats.class);
        query.include(Stats.KEY_USER);
        //only include the candle-burning stats of the current user
        query.whereEqualTo(Stats.KEY_USER, ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<Stats>() {
            @Override
            public void done(List<Stats> statistics, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                //add up all the hours for that user
                for (Stats stat : statistics) {
                        //check that the hours are being logged correctly
                        Log.i(TAG, "Hours: " + stat.getHours() + ", username: " +
                                stat.getUser().getUsername());
                        totalHours += stat.getHours();
                }
                //calculate the stats for CO2 and trees
                amountOfCO2 = calcCO2(totalHours);
                totalTrees = calcTrees(totalHours);

                //check that the total hours is correct
                Log.i(TAG, "Hours: " + String.valueOf(totalHours));
                Log.i(TAG, "CO2: " + String.valueOf(amountOfCO2));
                Log.i(TAG, "Trees: " + String.valueOf(totalTrees));

                //set text for fragments
                HoursFragment.tvTesterFrag1.setText(String.valueOf(totalHours));
                CarbonEmissionFragment.tvTesterFrag2.setText(String.valueOf(amountOfCO2));
            }
        });
    }

    //simple calculation functions used in queryStats()
    private Integer calcCO2(Integer hours) {
        return hours*10;
    }

    private Integer calcTrees(Integer hours) {
        return hours*4;
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
        if (item.getItemId() == R.id.add) {
            Intent i = new Intent(MainActivity.this, AddActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}