package com.example.candlebox.SpotifyStuff;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class RetrieveSentiment extends Activity {
    public static String body;
    public static String url = "http://localhost:8000/";

    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect(url).get();
        body = String.valueOf(doc.body());
        System.out.println("Body: " + body);
    }

    private void getUrl() {
        //url =
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