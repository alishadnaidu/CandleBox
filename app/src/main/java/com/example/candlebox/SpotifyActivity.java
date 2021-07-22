package com.example.candlebox;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.parse.ParseUser;
import com.spotify.android.appremote.api.AppRemote;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;

import com.spotify.protocol.client.CallResult;
import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;

//starter code from : https://developer.spotify.com/documentation/android/quick-start/
public class SpotifyActivity extends AppCompatActivity {
    public static final String TAG = "SpotifyActivity";
    private static final String CLIENT_ID = "41b1fdcb5723453d9e7e114cb85bf7be";
    private static final String REDIRECT_URI = "https://courses.codepath.com/courses/android_university_fast_track/pages/bootcamp_structure";
    private SpotifyAppRemote mSpotifyAppRemote;
    private TextView spotifyTitle;
    private EditText etSearchBar;
    private Button btnPlay, btnPause;
    private ImageView ivSongCover;
    private String song;
    private String artist;
    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spotify);
        spotifyTitle = findViewById(R.id.tvSpotifyTitle);
        etSearchBar = findViewById(R.id.etSearchBar);
        btnPlay = findViewById(R.id.btnPlay);
        btnPause = findViewById(R.id.btnPause);
        ivSongCover = findViewById(R.id.ivSongCover);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSpotifyAppRemote.getPlayerApi().resume();
                setCurrentlyPlaying();
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSpotifyAppRemote.getPlayerApi().pause();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        ConnectionParams connectionParams =
                new ConnectionParams.Builder(CLIENT_ID)
                        .setRedirectUri(REDIRECT_URI)
                        .showAuthView(true)
                        .build();

        // We will start writing our code here.
        SpotifyAppRemote.connect(this, connectionParams,
                new Connector.ConnectionListener() {

                    @Override
                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        mSpotifyAppRemote = spotifyAppRemote;
                        Log.d("MainActivity", "Connected! Yay!");

                        // Now you can start interacting with App Remote
                        connected();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.e("MainActivity", "Failed to play song: " + throwable.getMessage(), throwable);

                        // Something went wrong when attempting to connect! Handle errors here
                    }
                });
    }

    private void connected() {
        //start playing a song
        mSpotifyAppRemote.getPlayerApi().play("spotify:track:0ZNU020wNYvgW84iljPkPP");
        // Subscribe to PlayerState
        mSpotifyAppRemote.getPlayerApi()
                .subscribeToPlayerState()
                .setEventCallback(playerState -> {
                    final Track track = playerState.track;
                    if (track != null) {
                        Log.d("MainActivity", track.name + " by " + track.artist.name);
                        song = track.name;
                        artist = track.artist.name;
                        imageUrl = track.imageUri.toString();
                        Log.i(TAG, imageUrl + " " + imageUrl.hashCode());

                        setImage(track);
                        setCurrentlyPlaying();
                    }
                });
    }

    @Override
    protected void onStop() {
        super.onStop();
        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
    }


    private void setImage(Track track) {
        mSpotifyAppRemote.getImagesApi().getImage(track.imageUri).setResultCallback(new CallResult.ResultCallback<Bitmap>() {
            @Override
            public void onResult(Bitmap data) {
                ivSongCover.setImageBitmap(data);
            }
        });
    }

    //sets a textview with the currently playing song and artist
    private void setCurrentlyPlaying() {
        spotifyTitle.setText("Currently playing: " + song + " by " + artist);
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
            Intent i = new Intent(SpotifyActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
            return true;
        }
        //if add icon is tapped, navigate to the add screen
        if (item.getItemId() == R.id.add) {
            Intent i = new Intent(SpotifyActivity.this, AddActivity.class);
            startActivity(i);
            return true;
        }
        //if scan icon is tapped, navigate to the barcode screen
        if (item.getItemId() == R.id.scan) {
            Intent i = new Intent(SpotifyActivity.this, BarcodeScannerActivity.class);
            startActivity(i);
            return true;
        }
        if (item.getItemId() == R.id.home) {
            Intent i = new Intent(SpotifyActivity.this, MainActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
