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

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    //these are set to public static because they are used in the fragments
    public static Integer totalHours;
    public static Integer amountOfCO2;
    public static Integer totalTrees;
    private String sortingValueString;
    private String month;
    private String day;
    private Map<String, Map.Entry<String, Integer>> graphPairs = new HashMap<>();
    public static ArrayList<String> keysDates = new ArrayList<>();
    public static ArrayList<Integer> valuesHours = new ArrayList<>();
    public static TreeMap<String, Integer> sortedGraphPairs = new TreeMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queryStats();
        queryGraph();

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

    //gets the stats for making a graph of the hours logged by user for current month + last month
    private void queryGraph() {
        ParseQuery<Stats> query = ParseQuery.getQuery(Stats.class);
        query.include(Stats.KEY_USER);
        //only include the candle-burning hours of the current user
        query.whereEqualTo(Stats.KEY_USER, ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<Stats>() {
            @Override
            public void done(List<Stats> statistics, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting stats", e);
                    return;
                }
                //making a hashmap containing the dates and hours of current/last month for graph
                for (Stats stat : statistics) {
                    //get month, day, and year to create key and check that stats are from the past 2 months
                    String date = String.valueOf(stat.getCreatedAt());
                    month = String.valueOf(stat.getCreatedAt().getMonth() + 1);
                    day = date.substring(8,10);
                    Integer year = stat.getCreatedAt().getYear() + 1900;

                    //getting the current/last month and current year to compare to each entry
                    Date today = new Date();
                    String lastMonth = String.valueOf(today.getMonth());
                    String thisMonth = String.valueOf(today.getMonth() + 1);
                    Integer thisYear = today.getYear() + 1900;

                    //only include the entry if it is from the past 2 months of current year
                    if (year.equals(thisYear)) {
                        sortingValueString = month + day;
                        String formattedKey = month + "/" + day;
                        if (month.equals(lastMonth) || month.equals(thisMonth)) {
                            //adds the hours to the value associated with the key if it exists
                            // if not, it makes a new entry
                            if (graphPairs.containsKey(sortingValueString)) {
                                Map.Entry<String, Integer> mapEntry = graphPairs.get(sortingValueString);
                                Integer value = mapEntry.getValue();
                                graphPairs.put(sortingValueString, new AbstractMap.SimpleEntry(formattedKey, value + stat.getHours()));
                            }
                            else {
                                graphPairs.put(sortingValueString, new AbstractMap.SimpleEntry(formattedKey, stat.getHours()));
                            }
                        }
                    }

                }
                // populate the lists containing the data that will go in the graph
                // x-axis: keys/dates; y-axis: values/hours
                sortPoints();
                for (Map.Entry m : sortedGraphPairs.entrySet()) {
                    keysDates.add((String) m.getKey());
                    valuesHours.add((Integer) m.getValue());
                }
                setGraphPoints();
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

    //
    private void sortPoints() {
        //make a list called sortedKeys containing all the keys/dates sorted: ex. {709, 711, 713}
        ArrayList<String> keysString = new ArrayList<>(graphPairs.keySet());
        ArrayList<Integer> sortedKeys = new ArrayList<Integer>();
        for (int i = 0; i < keysString.size(); i++) {
            sortedKeys.add(Integer.parseInt(keysString.get(i)));
        }
        Collections.sort(sortedKeys);

        //grab the "entry", aka the second part of the graphPairs hashmap and get the key and value
        //to add to the sortedGraphPairs hashmap
        for (int i = 0; i < sortedKeys.size(); i++) {
            Map.Entry<String, Integer> pairEntry = graphPairs.get(sortedKeys.get(i).toString());
            sortedGraphPairs.put(pairEntry.getKey(), pairEntry.getValue());
        }
        Log.i("Final sorted hashmap: ", sortedGraphPairs.toString());
    }

    //setting the points to graph in the value line chart, starting animation
    private void setGraphPoints() {
        for (int i = 0; i < keysDates.size(); i++) {
            HoursFragment.series.addPoint(new ValueLinePoint(keysDates.get(i), valuesHours.get(i)));
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