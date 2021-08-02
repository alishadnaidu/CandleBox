package com.example.candlebox.SpotifyStuff;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.candlebox.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;

import kong.unirest.Unirest;

import static android.os.Build.VERSION.SDK_INT;

//testing for sentiment analysis, work in progress
public class RetrieveSentiment extends AppCompatActivity {
    public static final String TAG = "RetrieveSentiment";
    public static String body;
    public static String candleEntryUrl = "http://localhost:8000/mango";
    private TextView tvSong;
    //private AsyncClass asyncClass;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_recommendation_display);
        tvSong = findViewById(R.id.tvSongRecTitle);

        //getWebsite();
        //asyncClass = new AsyncClass();

        //readWebpage();

        DownloadWebPageTask downloadWebPageTask = new DownloadWebPageTask();
        downloadWebPageTask.readWebpage();

    }


    private void getWebsite() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();

                try {
                    if (SDK_INT >= 10) {
                        StrictMode.ThreadPolicy tp = StrictMode.ThreadPolicy.LAX;
                        StrictMode.setThreadPolicy(tp);
                    }
                    Document doc = Jsoup.connect("http://localhost:8000/mango").get();
                    body = String.valueOf(doc.body());
                    tvSong.setText(body);
                    Log.i(TAG, "Successfully set text");

                } catch (Exception e) {
                    Log.i(TAG, "Error! Body is: " + body);
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvSong.setText(body);
                    }
                });
            }
        }).start();
    }

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
//        Document doc = Jsoup.connect(candleEntryUrl).get();
//        body = String.valueOf(doc.body());
//        System.out.println("Body: " + body);
        //getSentiment(candleEntryUrl);
        String result = Unirest.get(candleEntryUrl).asStringAsync().get().getBody();
        System.out.println(result);
    }


    private void determineSentiment() {
        if (body.contains("Positive")) {

        }
        else if (body.contains("Neutral")) {

        }
        else {

        }
    }

}