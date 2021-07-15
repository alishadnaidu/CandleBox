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

public class AddActivity extends AppCompatActivity {

    public static final String TAG = "AddActivity";
    private EditText etHours;
    private Button btnLog;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etHours = findViewById(R.id.etHours);
        btnLog = findViewById(R.id.btnLog);

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //assign the integer value with etHours to hours integer variable
                Integer hours = Integer.parseInt(etHours.getText().toString());
                //if user has not inputted anything, show a toast
                if (hours.toString().isEmpty() || hours == 0) {
                    Toast.makeText(AddActivity.this, "Hours cannot be empty or 0!", Toast.LENGTH_SHORT).show();
                    return;
                }
                ParseUser currentUser = ParseUser.getCurrentUser();
                saveStats(hours, currentUser);
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
                etHours.setText("");
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
        if (item.getItemId() == R.id.home) {
            Intent i = new Intent(AddActivity.this, MainActivity.class);
            startActivity(i);
            return true;
        }
        if (item.getItemId() == R.id.scan) {
            Intent i = new Intent(AddActivity.this, BarcodeScannerActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
