package com.example.candlebox.CandleStuff.Models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Date;

@ParseClassName("Candles")
public class Candles extends ParseObject {
    // set keys; set getter and setter methods for each field in Candles class
    public static final String KEY_RAWBARCODEVALUE = "rawBarcodeValue";
    public static final String KEY_CANDLENAME = "candleName";
    public static final String KEY_INGREDIENTS = "ingredients";
    public static final String KEY_IMAGE = "image";


    public String getRawBarcodeValue() {
        return getString(KEY_RAWBARCODEVALUE);
    }
    public void setRawBarcodeValue(String rawBarcodeValue) { put(KEY_RAWBARCODEVALUE, rawBarcodeValue);}

    public String getCandleName() {
        return getString(KEY_CANDLENAME);
    }
    public void setCandleName(String candleName) { put(KEY_CANDLENAME, candleName);}

    public String getIngredients() {
        return getString(KEY_INGREDIENTS);
    }
    public void setIngredients(String ingredients) { put(KEY_INGREDIENTS, ingredients);}

    public ParseFile getImg() {
        return getParseFile(KEY_IMAGE);
    }
    public void setImg(ParseFile parseFile) {
        put(KEY_IMAGE, parseFile);
    }

}
