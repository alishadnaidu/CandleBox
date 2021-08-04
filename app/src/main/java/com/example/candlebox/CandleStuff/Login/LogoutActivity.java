package com.example.candlebox.CandleStuff.Login;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.candlebox.CandleStuff.BarcodeScanner.BarcodeScannerActivity;
import com.example.candlebox.CandleStuff.EmissionStats.MainActivity;
import com.example.candlebox.R;
import com.example.candlebox.SpotifyStuff.SpotifyWebActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseUser;

public class LogoutActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        TextView tvProfileName = findViewById(R.id.tvProfileName);
        tvProfileName.setText(ParseUser.getCurrentUser().getUsername());

        Button logoutButton = findViewById(R.id.logoutBtn);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setSelectedItemId(R.id.invisible);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        Intent homeIntent = new Intent(LogoutActivity.this, MainActivity.class);
                        startActivity(homeIntent);
                        return true;
                    case R.id.action_scan:
                        Intent addIntent = new Intent(LogoutActivity.this, BarcodeScannerActivity.class);
                        startActivity(addIntent);
                        return true;
                    case R.id.action_spotify:
                        Intent spotifyIntent = new Intent(LogoutActivity.this, SpotifyWebActivity.class);
                        startActivity(spotifyIntent);
                        return true;
                    default: return true;
                }
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                // this will be null bc there is no current user
                ParseUser currentUser = ParseUser.getCurrentUser();
                Intent logoutIntent = new Intent(LogoutActivity.this, LoginActivity.class);
                startActivity(logoutIntent);
                finish();
            }
        });

    }


}
