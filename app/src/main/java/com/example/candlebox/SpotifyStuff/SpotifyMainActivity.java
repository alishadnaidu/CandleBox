package com.example.candlebox.SpotifyStuff;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.candlebox.Connectors.SongService;
import com.example.candlebox.Connectors.TopTrackService;
import com.example.candlebox.Connectors.ValenceService;
import com.example.candlebox.R;

import java.util.ArrayList;


public class SpotifyMainActivity extends AppCompatActivity {

    public static final String TAG = "SpotifyMainActivity";
    private TextView userView;
    private TextView songView;
    private Button addBtn;
    private RecentlyPlayedSong song;
    private TopTracks top;
    public static String valenceEndpoint = "https://api.spotify.com/v1/audio-features?ids=";

    private SongService songService;
    private TopTrackService topTrackService;
    private ValenceService valenceService;
    private ArrayList<RecentlyPlayedSong> recentlyPlayedTracks;
    private ArrayList<TopTracks> favTracks;
    private ArrayList<String> valences;
    public static ArrayList<String> songIds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spotify_main);

        songService = new SongService(getApplicationContext());
        topTrackService = new TopTrackService(getApplicationContext());
        valenceService = new ValenceService(getApplicationContext());

        userView = (TextView) findViewById(R.id.mainSongID);
        songView = (TextView) findViewById(R.id.songName);
        //addBtn = (Button) findViewById(R.id.add);

        SharedPreferences sharedPreferences = this.getSharedPreferences("SPOTIFY", 0);
        userView.setText(sharedPreferences.getString("userid", "No User"));

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
        Log.i(TAG, String.valueOf(favTracks.size()));
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
        for (int i = 0; i < favTracks.size(); i++) {
            songIds.add(favTracks.get(i).getId());
        }
        Log.i(TAG, String.valueOf(songIds));
        makeEndpoint();
    }

    private void makeEndpoint() {
        String toAdd;
        for (int b = 0; b < songIds.size(); b++) {
            toAdd = songIds.get(b);
            if (b != songIds.size()-1) {
                toAdd += "%2C";
            }
            valenceEndpoint += toAdd;
        }
        //Log.i("The Valence Endpoint: ", valenceEndpoint);
        getValences();
    }

    private void getValences() {
        valences = valenceService.getValenceValues(new VolleyCallBack() {
            @Override
            public void onSuccess() {
                Log.i(TAG, "Finished!");
            }
        });
        //Log.i("Valence Values:", String.valueOf(ValenceService.finalValences));
    }

}
