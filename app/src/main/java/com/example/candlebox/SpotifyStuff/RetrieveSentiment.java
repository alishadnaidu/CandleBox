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
    public static void main(String[] args) throws IOException {
        Document doc  = Jsoup.connect("http://localhost:8000/awesome%20candle").get();
        String body = String.valueOf(doc.body());
        System.out.println("Body: " + body);
    }
}