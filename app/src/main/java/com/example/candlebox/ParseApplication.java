package com.example.candlebox;

import android.app.Application;

import com.parse.Parse;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("dXXFzv80BOomj2UdHzpQyZl2mzKxWLTTnzvc4Hab")
                .clientKey("LwUVY988AbT2ADox50zl4Hc9jYaEnuReHyCsJOcf")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
