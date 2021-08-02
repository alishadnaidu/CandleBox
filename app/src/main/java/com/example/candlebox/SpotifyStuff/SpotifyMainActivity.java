package com.example.candlebox.SpotifyStuff;

import android.content.Intent;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class SpotifyMainActivity extends AppCompatActivity {

    public static final String TAG = "SpotifyMainActivity";
    private TextView userView;
    private TextView songView;
    private Button btnGetMeSong;
    private EditText etGetMeSong;
    public static String valenceEndpoint = "https://api.spotify.com/v1/audio-features?ids=";
    public String sentimentUrl;
    public String sentiment;
    public String songRecId;

    private SongService songService;
    private TopTrackService topTrackService;
    private ValenceService valenceService;
    private ArrayList<TopTracks> favTracks;
    private ArrayList<String> valences;
    public static ArrayList<String> songIds;
    public static String candleEntry;
    public OkHttpClient client = new OkHttpClient();


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

        //when button clicked, clear the edit text and determine the sentiment/recommend a song
        btnGetMeSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                candleEntry = etGetMeSong.getText().toString();
                if (candleEntry == "" || candleEntry == null) {
                    Toast.makeText(SpotifyMainActivity.this, "Must enter name of candle", Toast.LENGTH_SHORT).show();
                    return;
                }
                sentimentUrl = "https://text-sentiment-analysis2.p.rapidapi.com/?text=" + candleEntry;
                doGetRequest(sentimentUrl);
                //TODO: recommend a song based on the sentiment data
                etGetMeSong.setText("");

                Intent i = new Intent(SpotifyMainActivity.this, SongRecActivity.class);
                startActivity(i);
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

    //gets sentiment (negative, neutral, positive) from sentiment api
    public void doGetRequest(String url) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("x-rapidapi-key", "ca54996e86msh79e36570000c179p1888d1jsn3b620387026d")
                .addHeader("x-rapidapi-host", "text-sentiment-analysis2.p.rapidapi.com")
                .build();

        client.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, IOException e) {
                        // Error

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // For the example, you can show an error dialog or a toast
                                // on the main UI thread
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        String res = response.body().string();
                        Log.i("Results", res);
                        //determine the overall sentiment (positive, neutral, or negative)
                        try {
                            JSONObject json = new JSONObject(res);
                            sentiment = json.getString("sentiment");
                            Log.i("Sentiment", sentiment);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void decideSong() {
        Random rand = new Random();
        int upperbound = ValenceService.negativeMap.size();
        int index = rand.nextInt(upperbound);
        //if sentiment is positive, choose a random song id from positiveList. in SongRecActivity, play this uri
        if (sentiment.equals("positive")) {
            //songRecId = ValenceService.positiveMap
        }
        else if (sentiment.equals("negative")) {

        }
        else {

        }
    }

}
