package com.example.candlebox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.candlebox.ui.SampleFragmentPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    public static Integer totalHours;
    public static Integer amountOfCO2;
    public static Integer totalTrees;
    //public static TextView tvHoursFrag;
    //private TextView tvTotalCO2;
    //private TextView tvTotalTrees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queryStats();

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(),
                MainActivity.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);


         /*
        tvHoursFrag = findViewById(R.id.tvHoursFrag);
        tvTotalCO2 = findViewById(R.id.tvCO2Frag);
        tvTotalTrees = findViewById(R.id.tvTreesFrag);

        queryStats();

          */
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
            // this will be null bc there is no current user
            ParseUser currentUser = ParseUser.getCurrentUser();
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
            return true;
        }
        //if add icon is tapped, navigate to the add screen
        if (item.getItemId() == R.id.add) {
            Intent i = new Intent(MainActivity.this, AddActivity.class);
            startActivity(i);
            return true;
        }
        if (item.getItemId() == R.id.scan) {
            Intent i = new Intent(MainActivity.this, BarcodeScannerActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void queryStats() {
        totalHours = 0;
        amountOfCO2 = 0;
        totalTrees = 0;
        ParseQuery<Stats> query = ParseQuery.getQuery(Stats.class);
        query.include(Stats.KEY_USER);
        //only include the stats of the current user
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
                //amountOfCO2 = calcCO2(totalHours);
                Log.i(TAG, "CO2: " + String.valueOf(amountOfCO2));
                //totalTrees = calcTrees(totalHours);
                Log.i(TAG, "Trees: " + String.valueOf(totalTrees));

                Frag1.tvTesterFrag1.setText(String.valueOf(totalHours));
                Frag2.tvTesterFrag2.setText(String.valueOf(amountOfCO2));

                //Frag3.tvTesterFrag3.setText(String.valueOf(totalTrees));
                /*
                tvHoursFrag.setText("Total Hours: " + totalHours);
                tvTotalCO2.setText("From burning candles, you have emitted " + calcCO2(totalHours)
                        + " grams of CO2.");
                tvTotalTrees.setText("It would take a mature tree " + calcTrees(totalHours) + " hours to " +
                        "offset that amount of CO2! Maybe try a more eco-friendly candle next time... :) You got this!");

                 */
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
}