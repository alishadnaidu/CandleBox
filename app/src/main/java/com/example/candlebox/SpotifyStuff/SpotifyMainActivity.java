package com.example.candlebox.SpotifyStuff;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.candlebox.Connectors.SongService;
import com.example.candlebox.Connectors.TopTrackService;
import com.example.candlebox.Connectors.ValenceService;
import com.example.candlebox.R;

import java.io.IOException;
import java.util.ArrayList;


public class SpotifyMainActivity extends AppCompatActivity {

    public static final String TAG = "SpotifyMainActivity";
    private TextView userView;
    private TextView songView;
    private Button btnGetMeSong;
    private EditText etGetMeSong;
    public static String valenceEndpoint = "https://api.spotify.com/v1/audio-features?ids=";

    private SongService songService;
    private TopTrackService topTrackService;
    private ValenceService valenceService;
    private ArrayList<TopTracks> favTracks;
    private ArrayList<String> valences;
    public static ArrayList<String> songIds;
    public static String candleEntry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spotify_main);

        //initialize service to get songs, top tracks, and valences
        songService = new SongService(getApplicationContext());
        topTrackService = new TopTrackService(getApplicationContext());
        valenceService = new ValenceService(getApplicationContext());

        userView = findViewById(R.id.mainSongID);
        songView = findViewById(R.id.songName);
        etGetMeSong = findViewById(R.id.etGetMeSong);
        btnGetMeSong = findViewById(R.id.btnGetMeSong);

        SharedPreferences sharedPreferences = this.getSharedPreferences("SPOTIFY", 0);
        userView.setText(sharedPreferences.getString("userid", "No User"));

        getTopTracks();

        //work in progress: goal is to recommend a song when button is clicked
        btnGetMeSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                candleEntry = etGetMeSong.getText().toString();
                if (candleEntry == "" || candleEntry == null) {
                    Toast.makeText(SpotifyMainActivity.this, "Must enter name of candle", Toast.LENGTH_SHORT).show();
                    return;
                }
                RetrieveSentiment.candleEntryUrl = "http://localhost:8000/" + candleEntry;
                Log.i(TAG, RetrieveSentiment.candleEntryUrl);

//                try {
//                    RetrieveSentiment.getSentiment(RetrieveSentiment.candleEntryUrl);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

            }
        });

    }

    //main helper method, gets top tracks using the top track service
    private void getTopTracks() {
        topTrackService.getTopTracks(() -> {
            favTracks = topTrackService.getTopSongs();
            updateTopTrack();
            processList();
        });
    }

    //updating the display to show the first item in top tracks list
    private void updateTopTrack() {
        Log.i(TAG, String.valueOf(favTracks.size()));
        if (favTracks.size() > 0) {
            songView.setText(favTracks.get(0).getName());
        }
    }

    //makes list of song ids from the user's top tracks
    private void processList() {
        songIds = new ArrayList<>();
        for (int i = 0; i < favTracks.size(); i++) {
            songIds.add(favTracks.get(i).getId());
        }
        makeEndpoint();
    }

    //creates the url endpoint to get valence of each song in top tracks
    private void makeEndpoint() {
        String toAdd;
        for (int b = 0; b < songIds.size(); b++) {
            toAdd = songIds.get(b);
            if (b != songIds.size()-1) {
                toAdd += "%2C";
            }
            valenceEndpoint += toAdd;
        }
        getValences();
    }

    private void getValences() {
        valences = valenceService.getValenceValues(new VolleyCallBack() {
            @Override
            public void onSuccess() {
                Log.i(TAG, "Finished with getting valences!");
            }
        });
        Log.i("Valence Values:", String.valueOf(ValenceService.finalValences));
    }

}
