package com.example.candlebox.SpotifyStuff;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.candlebox.CandleStuff.EmissionStats.AddActivity;
import com.example.candlebox.CandleStuff.BarcodeScanner.BarcodeScannerActivity;
import com.example.candlebox.CandleStuff.Login.LoginActivity;
import com.example.candlebox.CandleStuff.EmissionStats.MainActivity;
import com.example.candlebox.SpotifyStuff.Connectors.TopTrackService;
import com.example.candlebox.SpotifyStuff.Connectors.ValenceService;
import com.example.candlebox.R;
import com.parse.ParseUser;

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
    private Button btnGetMeSong;
    private EditText etGetMeSong;
    public static String valenceEndpoint = "https://api.spotify.com/v1/audio-features?ids=";
    public String sentimentUrl;
    public String sentiment;
    public static String songRecId;

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

        //initialize service to get top tracks and valences
        topTrackService = new TopTrackService(getApplicationContext());
        valenceService = new ValenceService(getApplicationContext());

        etGetMeSong = findViewById(R.id.etGetMeSong);
        btnGetMeSong = findViewById(R.id.btnGetMeSong);

        getTopTracks();

        //when button clicked, clear the edit text and determine the sentiment/recommend a song
        btnGetMeSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                candleEntry = etGetMeSong.getText().toString();
                if (candleEntry.isEmpty()) {
                    Toast.makeText(SpotifyMainActivity.this, "Please enter the name of a candle!", Toast.LENGTH_SHORT).show();
                }
                else {
                    //determine the unique url for sentiment analysis using candleEntry
                    sentimentUrl = "https://text-sentiment-analysis2.p.rapidapi.com/?text=" + candleEntry;
                    doGetRequest(sentimentUrl);
                    etGetMeSong.setText("");
                }
            }
        });

    }

    //main helper method, gets top tracks using the top track service
    private void getTopTracks() {
        topTrackService.getTopTracks(() -> {
            favTracks = topTrackService.getTopSongs();
            processList();
        });
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
                                // For the example, you can show an error dialog or a toast on the main UI thread
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        String res = response.body().string();
                        //determine the overall sentiment (positive, neutral, or negative)
                        try {
                            JSONObject json = new JSONObject(res);
                            sentiment = json.getString("sentiment");
                            Log.i("Sentiment", sentiment);
                            decideSong();
                            Intent i = new Intent(SpotifyMainActivity.this, SongRecActivity.class);
                            startActivity(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    //gets random song from corresponding list to be played in SongRecActivity
    public void decideSong() {
        Random rand = new Random();
        int upperbound = ValenceService.negativeMap.size();
        int index = rand.nextInt(upperbound);
        if (sentiment.equals("positive")) {
            songRecId = ValenceService.positiveList.get(index);
        }
        else if (sentiment.equals("neutral")) {
            songRecId = ValenceService.neutralList.get(index);
        }
        else {
            songRecId = ValenceService.negativeList.get(index);
        }
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
            Intent i = new Intent(SpotifyMainActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
            return true;
        }

        // if the home icon is tapped, navigate to home screen
        if (item.getItemId() == R.id.home) {
            Intent i = new Intent(SpotifyMainActivity.this, MainActivity.class);
            startActivity(i);
            return true;
        }

        // if the scan icon is tapped, navigate to barcode scanning screen
        if (item.getItemId() == R.id.scan) {
            Intent i = new Intent(SpotifyMainActivity.this, BarcodeScannerActivity.class);
            startActivity(i);
            return true;
        }

        if (item.getItemId() == R.id.add) {
            Intent i = new Intent(SpotifyMainActivity.this, AddActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
