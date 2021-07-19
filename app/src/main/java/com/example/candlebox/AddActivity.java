package com.example.candlebox;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
                Toast.makeText(AddActivity.this, "Successfully logged hours!", Toast.LENGTH_SHORT).show();
                stepperTouch.setCount(0);
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
            Intent i = new Intent(AddActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
            return true;
        }

        // if the home icon is tapped, navigate to home screen
        if (item.getItemId() == R.id.home) {
            Intent i = new Intent(AddActivity.this, MainActivity.class);
            startActivity(i);
            return true;
        }

        // if the scan icon is tapped, navigate to barcode scanning screen
        if (item.getItemId() == R.id.scan) {
            Intent i = new Intent(AddActivity.this, BarcodeScannerActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
