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

import org.eazegraph.lib.models.ValueLinePoint;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    //these are set to public static because they are used in the fragments
    public static Integer totalHours;
    public static Integer amountOfCO2;
    public static Integer totalTrees;

    private String month;
    private String day;
    private HashMap<String, Integer> graphPairs = new HashMap<>();
    public static ArrayList<String> keysDates = new ArrayList<>();
    public static ArrayList<Integer> valuesHours = new ArrayList<>();

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
                    Log.e(TAG, "Issue with getting stats", e);
                    return;
                }
                //add up all the hours for that user
                for (Stats stat : statistics) {
                        //check that the hours are being logged correctly
                        Log.i(TAG, "Hours: " + stat.getHours() + ", username: " +
                                stat.getUser().getUsername());
                        totalHours += stat.getHours();

                        //making a hashmap containing the dates and hours of current month for graph
                        String date = String.valueOf(stat.getCreatedAt());
                        month = String.valueOf(stat.getCreatedAt().getMonth() + 1);
                        day = date.substring(8,10);

                        //getting the current month
                        Date today = new Date();
                        String thisMonth = String.valueOf(today.getMonth() + 1);

                        //adds the hours to the value associated with the key if it exists
                        // if not, it makes a new entry
                        if (month.equals(thisMonth)) {
                            String key = month + "/" + day;
                            if (graphPairs.containsKey(key)) {
                                Log.i("DatesList", "The key is not null");
                                Integer value = graphPairs.get(key);
                                graphPairs.put(key, value + stat.getHours());
                            }
                            else {
                                Log.i("DatesList", "The key is null" + graphPairs.get(key));
                                graphPairs.put(key, stat.getHours());
                            }
                        }
                }

                // now we have a complete hashmap with the dates and hours of the current month
                // populate the lists containing the data that will go in the graph
                // x-axis: keys/dates; y-axis: values/hours
                for (Map.Entry m : graphPairs.entrySet()) {
                    keysDates.add((String) m.getKey());
                    valuesHours.add((Integer) m.getValue());
                }
                setGraphPoints();

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

    //simple calculation function used in queryStats()
    private Integer calcCO2(Integer hours) {
        return hours*10;
    }

    //simple calculation function used in queryStats()
    private Integer calcTrees(Integer hours) {
        return hours * 4;
    }

    //setting the points to graph in the value line chart, starting animation
    private void setGraphPoints() {
        for (int i = 0; i < keysDates.size(); i++) {
            HoursFragment.series.addPoint(new ValueLinePoint(keysDates.get(i), valuesHours.get(i)));
            Log.i("Pairs", keysDates.get(i) + ", " + valuesHours.get(i).toString());
        }
        //add the series and start animation here so that it runs in the correct order, otherwise graph will be blank
        HoursFragment.mCubicValueLineChart.addSeries(HoursFragment.series);
        HoursFragment.mCubicValueLineChart.startAnimation();
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