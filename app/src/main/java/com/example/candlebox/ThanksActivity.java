package com.example.candlebox;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseUser;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

public class ThanksActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanks);
        Button btnTakeMeHome = findViewById(R.id.btnTakeMeHome);
        final KonfettiView konfettiView = findViewById(R.id.viewKonfetti);
        //automatic confetti when thank you page is created
        konfettiView.build().addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA, Color.rgb(42,205,255), Color.rgb(169,100,253))
                .setDirection(0.0, 359.0)
                .setSpeed(1f, 5f)
                .setFadeOutEnabled(true)
                .setTimeToLive(2000L)
                .addShapes(Shape.Square.INSTANCE, Shape.Circle.INSTANCE)
                .addSizes(new Size(12, 5f))
                .setPosition(-50f, konfettiView.getWidth() + 50f, -50f, -50f)
                .streamFor(300, 5000L);

        //so that you can click and have confetti too!
        konfettiView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                konfettiView.build()
                        .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA, Color.rgb(42,205,255), Color.rgb(169,100,253))
                        .setDirection(0.0, 359.0)
                        .setSpeed(1f, 5f)
                        .setFadeOutEnabled(true)
                        .setTimeToLive(2000L)
                        .addShapes(Shape.Square.INSTANCE, Shape.Circle.INSTANCE)
                        .addSizes(new Size(12, 5f))
                        .setPosition(-50f, konfettiView.getWidth() + 50f, -50f, -50f)
                        .streamFor(300, 5000L);
            }
        });

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

        if (item.getItemId() == R.id.song) {
            Intent i = new Intent(ThanksActivity.this, SongRecActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
