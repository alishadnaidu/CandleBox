package com.example.candlebox.CandleStuff.UploadingNewCandle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.candlebox.CandleStuff.BarcodeScanner.BarcodeScannerActivity;
import com.example.candlebox.CandleStuff.EmissionStats.AddActivity;
import com.example.candlebox.CandleStuff.EmissionStats.MainActivity;
import com.example.candlebox.CandleStuff.Login.LoginActivity;
import com.example.candlebox.CandleStuff.Models.Candles;
import com.example.candlebox.R;
import com.example.candlebox.SpotifyStuff.SpotifyWebActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class UploadCandle extends AppCompatActivity {

    public static final String TAG = "UploadActivity";
    private EditText etCandleName, etCandleIngredients, etRawBarcode;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_candle);

        etCandleName = findViewById(R.id.etCandleName);
        etCandleIngredients = findViewById(R.id.etCandleIngredients);
        etRawBarcode = findViewById(R.id.etRawBarcode);
        Button btnUploadCandle = findViewById(R.id.btnUploadCandle);

        //set hints and auto-fill raw barcode so user doesn't have to type it in
        etCandleName.setHint("Enter name of candle here...");
        etRawBarcode.setText(BarcodeScannerActivity.rawValue);
        etCandleIngredients.setHint("List the ingredients here...");

        btnUploadCandle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String candleName = etCandleName.getText().toString();
                String candleIngredients = etCandleIngredients.getText().toString();
                String rawBarcode = etRawBarcode.getText().toString();
                // error handling for when 1+ fields are left blank
                if (candleName.isEmpty() || candleIngredients.isEmpty() || rawBarcode.isEmpty()) {
                    Toast.makeText(UploadCandle.this, "Please make sure all fields are filled out!", Toast.LENGTH_SHORT).show();
                    return;
                }
                saveCandle(candleName, candleIngredients, rawBarcode);

                //when you submit a new candle to the database, get a fun thank you message!
                Intent i = new Intent(UploadCandle.this, ThanksActivity.class);
                startActivity(i);
                finish();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setSelectedItemId(R.id.invisible);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        Intent homeIntent = new Intent(UploadCandle.this, MainActivity.class);
                        startActivity(homeIntent);
                        return true;
                    case R.id.action_scan:
                        Intent addIntent = new Intent(UploadCandle.this, BarcodeScannerActivity.class);
                        startActivity(addIntent);
                        return true;
                    case R.id.action_spotify:
                        Intent spotifyIntent = new Intent(UploadCandle.this, SpotifyWebActivity.class);
                        startActivity(spotifyIntent);
                        return true;
                    case R.id.action_logout:
                        ParseUser.logOut();
                        // this will be null bc there is no current user
                        ParseUser currentUser = ParseUser.getCurrentUser();
                        Intent logoutIntent = new Intent(UploadCandle.this, LoginActivity.class);
                        startActivity(logoutIntent);
                        finish();
                        return true;
                    default: return true;
                }
            }
        });
    }

    // save new candle entry in back4app database
    private void saveCandle(String candleName, String ingredients, String rawBarcodeValue) {
        Candles candle = new Candles();
        candle.setCandleName(candleName);
        candle.setIngredients(ingredients);
        candle.setRawBarcodeValue(rawBarcodeValue);
        candle.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "error while saving stats");
                    Toast.makeText(UploadCandle.this, "Error while saving new candle", Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG, "Candle save was successful!");
                Toast.makeText(UploadCandle.this, "Successfully added new candle to database!", Toast.LENGTH_SHORT).show();

                //clear edit text fields
                etCandleName.setText("");
                etCandleIngredients.setText("");
                etRawBarcode.setText("");
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
        if (item.getItemId() == R.id.add) {
            Intent i = new Intent(UploadCandle.this, AddActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
