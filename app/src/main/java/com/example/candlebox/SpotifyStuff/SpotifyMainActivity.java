package com.example.candlebox.SpotifyStuff;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.candlebox.Connectors.SongService;
import com.example.candlebox.Connectors.TopTrackService;
import com.example.candlebox.R;
import com.example.candlebox.Song;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SpotifyMainActivity extends AppCompatActivity {

    private TextView userView;
    private TextView songView;
    private Button addBtn;
    private RecentlyPlayedSong song;
    private TopTracks top;

    private SongService songService;
    private TopTrackService topTrackService;
    private ArrayList<RecentlyPlayedSong> recentlyPlayedTracks;
    private ArrayList<TopTracks> favTracks;
    private ArrayList<String> songIds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spotify_main);

        songService = new SongService(getApplicationContext());
        topTrackService = new TopTrackService(getApplicationContext());

        userView = (TextView) findViewById(R.id.mainSongID);
        songView = (TextView) findViewById(R.id.songName);
        //addBtn = (Button) findViewById(R.id.add);

        SharedPreferences sharedPreferences = this.getSharedPreferences("SPOTIFY", 0);
        userView.setText(sharedPreferences.getString("userid", "No User"));

        //getTracks();
        getTopTracks();

    }

    private void getTopTracks() {
        topTrackService.getTopTracks(() -> {
            favTracks = topTrackService.getTopSongs();
            updateTopTrack();
            processList();
        });
    }

    private void updateTopTrack() {
        Log.i("SpotifyMainActivity", String.valueOf(favTracks.size()));
        if (favTracks.size() > 0) {
            for (int n = 0; n < favTracks.size(); n++) {
                //Log.i("SpotifyMainActivity", String.valueOf(favTracks.get(n).getArtist().getName()));
                //Log.i("Here are the favorites:", String.valueOf(favTracks.get(n).getName()));
            }
            songView.setText(favTracks.get(0).getName());
            top = favTracks.get(0);
        }
    }

    private void processList() {
        songIds = new ArrayList<>();
        for (int i = 0; i < favTracks.size()/2; i++) {
            songIds.add(favTracks.get(i).getId());
        }
        Log.i("SpotifyMainActivity", String.valueOf(songIds));
    }


    private void getTracks() {
        songService.getRecentlyPlayedTracks(() -> {
            recentlyPlayedTracks = songService.getSongs();
            updateSong();
        });
    }

    private void updateSong() {
        if (recentlyPlayedTracks.size() > 0) {
            for (int n = 0; n < recentlyPlayedTracks.size(); n++) {
                //Log.i("SpotifyMainActivity", String.valueOf(recentlyPlayedTracks));
            }
            songView.setText(recentlyPlayedTracks.get(0).getName());
            song = recentlyPlayedTracks.get(0);
        }
    }

}
