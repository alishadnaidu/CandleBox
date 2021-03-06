package com.example.candlebox.CandleStuff.EmissionStats;

import android.app.Application;

import com.example.candlebox.CandleStuff.Models.Candles;
import com.example.candlebox.CandleStuff.Models.RecentlyScannedCandles;
import com.example.candlebox.CandleStuff.Models.Stats;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Registering parse models: stats class and candles class
        ParseObject.registerSubclass(Stats.class);
        ParseObject.registerSubclass(Candles.class);
        ParseObject.registerSubclass(RecentlyScannedCandles.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("dXXFzv80BOomj2UdHzpQyZl2mzKxWLTTnzvc4Hab")
                .clientKey("LwUVY988AbT2ADox50zl4Hc9jYaEnuReHyCsJOcf")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
