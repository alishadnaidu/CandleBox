package com.example.candlebox;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Candles")
public class Candles extends ParseObject {
    public static final String KEY_RAWBARCODEVALUE = "rawBarcodeValue";
    public static final String KEY_CANDLENAME = "candleName";

    public String getRawBarcodeValue() {
        return getString(KEY_RAWBARCODEVALUE);
    }
    public void setRawBarcodeValue(String rawBarcodeValue) { put(KEY_RAWBARCODEVALUE, rawBarcodeValue);}

    public String getCandleName() {
        return getString(KEY_CANDLENAME);
    }
    public void setCandleName(String candleName) { put(KEY_CANDLENAME, candleName);}
}
