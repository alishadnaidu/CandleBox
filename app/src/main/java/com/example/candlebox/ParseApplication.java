package com.example.candlebox;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Register your parse models
        ParseObject.registerSubclass(Stats.class);
        ParseObject.registerSubclass(Candles.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("dXXFzv80BOomj2UdHzpQyZl2mzKxWLTTnzvc4Hab")
                .clientKey("LwUVY988AbT2ADox50zl4Hc9jYaEnuReHyCsJOcf")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
