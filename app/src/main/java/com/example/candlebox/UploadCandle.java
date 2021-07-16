package com.example.candlebox;

import android.content.Intent;
import android.os.Bundle;
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

        // if the logout icon is tapped, log out + navigate to the login screen
        if (item.getItemId() == R.id.logout) {
            ParseUser.logOut();
            ParseUser currentUser = ParseUser.getCurrentUser();
            Intent i = new Intent(UploadCandle.this, LoginActivity.class);
            startActivity(i);
            finish();
            return true;
        }

        // if the home icon is tapped, navigate to home screen
        if (item.getItemId() == R.id.home) {
            Intent i = new Intent(UploadCandle.this, MainActivity.class);
            startActivity(i);
            return true;
        }

        // if the scan icon is tapped, navigate to barcode scanning screen
        if (item.getItemId() == R.id.scan) {
            Intent i = new Intent(UploadCandle.this, BarcodeScannerActivity.class);
            startActivity(i);
            return true;
        }

        // if the add icon is tapped, navigate to add screen
        if (item.getItemId() == R.id.add) {
            Intent i = new Intent(UploadCandle.this, AddActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
