package com.example.candlebox.SpotifyStuff;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.candlebox.CandleStuff.EmissionStats.AddActivity;
import com.example.candlebox.CandleStuff.BarcodeScanner.BarcodeScannerActivity;
import com.example.candlebox.CandleStuff.Login.LoginActivity;
import com.example.candlebox.CandleStuff.EmissionStats.MainActivity;
import com.example.candlebox.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseUser;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.client.CallResult;
import com.spotify.protocol.types.Track;

public class SongRecActivity extends AppCompatActivity {
    public static final String TAG = "SpotifyActivity";
    private static final String CLIENT_ID = "41b1fdcb5723453d9e7e114cb85bf7be";
    private static final String REDIRECT_URI = "https://courses.codepath.com/courses/android_university_fast_track/pages/bootcamp_structure";
    private SpotifyAppRemote mSpotifyAppRemote;
    private TextView spotifyTitle;
    private Button btnPlay, btnPause;
    private ImageView ivSongCover;
    private String song;
    private String artist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_song_rec);

        spotifyTitle = findViewById(R.id.tvSongRecName);
        btnPlay = findViewById(R.id.playButton);
        btnPause = findViewById(R.id.pauseButton);
        ivSongCover = findViewById(R.id.ivSongRecImage);

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

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        mSpotifyAppRemote.getPlayerApi().pause();
                        Intent homeIntent = new Intent(SongRecActivity.this, MainActivity.class);
                        startActivity(homeIntent);
                        return true;
                    case R.id.action_scan:
                        mSpotifyAppRemote.getPlayerApi().pause();
                        Intent addIntent = new Intent(SongRecActivity.this, BarcodeScannerActivity.class);
                        startActivity(addIntent);
                        return true;
                    case R.id.action_spotify:
                        mSpotifyAppRemote.getPlayerApi().pause();
                        Intent spotifyIntent = new Intent(SongRecActivity.this, SpotifyWebActivity.class);
                        startActivity(spotifyIntent);
                        return true;
                    case R.id.action_logout:
                        mSpotifyAppRemote.getPlayerApi().pause();
                        ParseUser.logOut();
                        // this will be null bc there is no current user
                        ParseUser currentUser = ParseUser.getCurrentUser();
                        Intent logoutIntent = new Intent(SongRecActivity.this, LoginActivity.class);
                        startActivity(logoutIntent);
                        finish();
                        return true;
                    default: return true;
                }
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

        SpotifyAppRemote.connect(this, connectionParams,
                new Connector.ConnectionListener() {

                    @Override
                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        mSpotifyAppRemote = spotifyAppRemote;
                        Log.d("SongRecActivity", "Connected! Yay!");
                        // Can start interacting with App Remote
                        connected();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.e("SongRecActivity", "Failed to play song: " + throwable.getMessage(), throwable);
                    }
                });
    }

    private void connected() {
        //start playing the recommended song
        mSpotifyAppRemote.getPlayerApi().play("spotify:track:" + SpotifyMainActivity.songRecId);
        // Subscribe to PlayerState
        mSpotifyAppRemote.getPlayerApi()
                .subscribeToPlayerState()
                .setEventCallback(playerState -> {
                    final Track track = playerState.track;
                    //when track starts playing, set the title, artist, and image
                    if (track != null) {
                        Log.d("SongRecActivity", track.name + " by " + track.artist.name);
                        song = track.name;
                        artist = track.artist.name;
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
        spotifyTitle.setText(song + " by " + artist);
    }

}
