package com.example.candlebox.CandleStuff.EmissionStats;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.candlebox.CandleStuff.BarcodeScanner.BarcodeScannerActivity;
import com.example.candlebox.CandleStuff.Login.LoginActivity;
import com.example.candlebox.CandleStuff.Models.Stats;
import com.example.candlebox.R;
import com.example.candlebox.SpotifyStuff.SpotifyWebActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import nl.dionsegijn.steppertouch.OnStepCallback;
import nl.dionsegijn.steppertouch.StepperTouch;

public class AddActivity extends AppCompatActivity {

    public static final String TAG = "AddActivity";
    private Button btnLog;
    private StepperTouch stepperTouch;
    private Integer hoursToAdd;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        btnLog = findViewById(R.id.btnLog);

        stepperTouch = findViewById(R.id.stepperTouch);
        stepperTouch.setMinValue(0);
        stepperTouch.setSideTapEnabled(true);
        stepperTouch.addStepCallback(new OnStepCallback() {
            @Override
            public void onStep(int value, boolean positive) {
                hoursToAdd = value;
            }
        });

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //error-handling for if user inputs 0
                if (hoursToAdd == 0) {
                    Toast.makeText(AddActivity.this, "Hours must be greater than 0!", Toast.LENGTH_SHORT).show();
                    return;
                }
                ParseUser currentUser = ParseUser.getCurrentUser();
                saveStats(hoursToAdd, currentUser);
                Intent i = new Intent(AddActivity.this, MainActivity.class);
                startActivity(i);
                }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        Intent homeIntent = new Intent(AddActivity.this, MainActivity.class);
                        startActivity(homeIntent);
                        return true;
                    case R.id.action_scan:
                        Intent addIntent = new Intent(AddActivity.this, BarcodeScannerActivity.class);
                        startActivity(addIntent);
                        return true;
                    case R.id.action_spotify:
                        Intent spotifyIntent = new Intent(AddActivity.this, SpotifyWebActivity.class);
                        startActivity(spotifyIntent);
                        return true;
                    case R.id.action_logout:
                        ParseUser.logOut();
                        // this will be null bc there is no current user
                        ParseUser currentUser = ParseUser.getCurrentUser();
                        Intent logoutIntent = new Intent(AddActivity.this, LoginActivity.class);
                        startActivity(logoutIntent);
                        finish();
                        return true;
                    default: return true;
                }
            }
        });
    }

    //saves stats (hours and user), uploads to back4app database
    private void saveStats(Integer hours, ParseUser currentUser) {
        Stats stats = new Stats();
        stats.setHours(hours);
        stats.setUser(currentUser);
        stats.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "error while saving stats");
                    Toast.makeText(AddActivity.this, "Error while saving stats", Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG, "Stats save was successful!");

                if (hours == 1) {
                    Toast.makeText(AddActivity.this, "Successfully logged " + hours + " hour!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(AddActivity.this, "Successfully logged " + hours + " hours!", Toast.LENGTH_SHORT).show();
                }
                stepperTouch.setCount(0);
            }
        });
    }

}
