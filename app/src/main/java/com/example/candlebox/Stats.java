package com.example.candlebox;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Stats")
public class Stats extends ParseObject {
    public static final String KEY_USER = "user";
    public static final String KEY_HOURS = "hours";

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }
    public Integer getHours() {
        return getInt(KEY_HOURS);
    }

    public void setHours(Integer hours) {
        put(KEY_HOURS, hours);
    }
}