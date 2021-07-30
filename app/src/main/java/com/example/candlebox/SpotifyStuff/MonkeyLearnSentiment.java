package com.example.candlebox.SpotifyStuff;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.candlebox.Connectors.SongService;
import com.example.candlebox.Connectors.TopTrackService;
import com.example.candlebox.Connectors.ValenceService;
import com.example.candlebox.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;

public class MonkeyLearnSentiment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_monkey_learn_tester);

        parseSentiment();
    }

    private void parseSentiment() {
        try {
            Document doc = Jsoup.connect("https://www.javatpoint.com/jsoup-tutorial").get();
            String title = doc.title();
            Log.i("Title: ", title);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
