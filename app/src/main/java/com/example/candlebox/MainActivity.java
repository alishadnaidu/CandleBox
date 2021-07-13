package com.example.candlebox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private Integer totalHours;
    private TextView tvTotalHours;
    private TextView tvTotalCO2;
    private TextView tvTotalTrees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_main);
        tvTotalHours = findViewById(R.id.tvTotalHours);
        tvTotalCO2 = findViewById(R.id.tvTotalCO2);
        tvTotalTrees = findViewById(R.id.tvTotalTrees);

        /*
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(),
                MainActivity.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
         */
        queryPosts();
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
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
            return true;
        }
        if (item.getItemId() == R.id.add) {
            Intent i = new Intent(MainActivity.this, AddActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void queryPosts() {
        totalHours = 0;
        ParseQuery<Stats> query = ParseQuery.getQuery(Stats.class);
        query.include(Stats.KEY_USER);
        query.findInBackground(new FindCallback<Stats>() {
            @Override
            public void done(List<Stats> statistics, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                for (Stats stat : statistics) {
                    //check that the hours are being logged correctly
                    Log.i(TAG, "Hours: " + stat.getHours() + ", username: " + stat.getUser().getUsername());
                    totalHours += stat.getHours();
                }
                //check that the total hours is correct
                Log.i(TAG, String.valueOf(totalHours));
                tvTotalHours.setText("Total Hours: " + totalHours);
                tvTotalCO2.setText("From burning candles, you have emitted " + calcCO2(totalHours)
                        + " grams of CO2.");
                tvTotalTrees.setText("It would take a tree " + calcTrees(totalHours) + " hours to " +
                        "offset that amount of CO2! Maybe try a more eco-friendly candle next time... :) You got this!");
            }
        });
    }

    private Integer calcCO2(Integer hours) {
        return hours*10;
    }

    private Integer calcTrees(Integer hours) {
        return hours*4;
    }
}