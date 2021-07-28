package com.example.candlebox.CandleStuff;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;

@ParseClassName("RecentlyScannedCandles")
public class RecentlyScannedCandles extends ParseObject {
    // set keys; set getter and setter methods for each field in Candles class
    public static final String KEY_RECENTRAWBARCODEVALUE = "rawBarcodeValue";
    public static final String KEY_RECENTCANDLENAME = "candleName";
    public static final String KEY_RECENTINGREDIENTS = "ingredients";
    public static final String KEY_USER = "user";

    public String getRecentRawBarcodeValue() {
        return getString(KEY_RECENTRAWBARCODEVALUE);
    }
    public void setRecentRawBarcodeValue(String rawBarcodeValue) { put(KEY_RECENTRAWBARCODEVALUE, rawBarcodeValue);}

    public String getRecentCandleName() {
        return getString(KEY_RECENTCANDLENAME);
    }
    public void setRecentCandleName(String candleName) { put(KEY_RECENTCANDLENAME, candleName);}

    public String getRecentIngredients() {
        return getString(KEY_RECENTINGREDIENTS);
    }
    public void setRecentIngredients(String ingredients) { put(KEY_RECENTINGREDIENTS, ingredients);}

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }
    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

}
