package com.example.candlebox;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseUser;

public class ThanksActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanks);
        Button btnTakeMeHome = findViewById(R.id.btnTakeMeHome);

        btnTakeMeHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ThanksActivity.this, MainActivity.class);
                startActivity(i);
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
            Intent i = new Intent(ThanksActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
            return true;
        }

        // if the home icon is tapped, navigate to home screen
        if (item.getItemId() == R.id.home) {
            Intent i = new Intent(ThanksActivity.this, MainActivity.class);
            startActivity(i);
            return true;
        }

        // if the scan icon is tapped, navigate to barcode scanning screen
        if (item.getItemId() == R.id.scan) {
            Intent i = new Intent(ThanksActivity.this, BarcodeScannerActivity.class);
            startActivity(i);
            return true;
        }

        // if the add icon is tapped, navigate to add screen
        if (item.getItemId() == R.id.add) {
            Intent i = new Intent(ThanksActivity.this, AddActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
