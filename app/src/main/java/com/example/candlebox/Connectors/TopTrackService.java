package com.example.candlebox.Connectors;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.candlebox.SpotifyStuff.TopTracks;
import com.example.candlebox.SpotifyStuff.VolleyCallBack;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TopTrackService {
    private ArrayList<TopTracks> songs = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private RequestQueue queue;
    String artists, songTitle;

    public TopTrackService(Context context) {
        sharedPreferences = context.getSharedPreferences("SPOTIFY", 0);
        queue = Volley.newRequestQueue(context);
    }

    public ArrayList<TopTracks> getTopSongs() {
        return songs;
    }

    public ArrayList<TopTracks> getTopTracks(final VolleyCallBack callBack) {
        String endpoint = "https://api.spotify.com/v1/me/top/tracks?limit=5";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, endpoint, null, response -> {
                    Gson gson = new Gson();
                    JSONArray jsonArray = response.optJSONArray("items");
                    for (int n = 0; n < jsonArray.length(); n++) {
                        try {
                            JSONObject object = jsonArray.getJSONObject(n);
                            TopTracks song = gson.fromJson(object.toString(), TopTracks.class);
                            songs.add(song);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                    for (int n = 0; n < jsonArray.length(); n++) {
                        try {
                            JSONObject object = jsonArray.getJSONObject(n);
                            object = object.optJSONObject("album");
                            TopTracks song = gson.fromJson(object.toString(), TopTracks.class);
                            songs.add(song);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    callBack.onSuccess();
                }, error -> {
                    // TODO: Handle error

                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String token = sharedPreferences.getString("token", "");
                String auth = "Bearer " + token;
                headers.put("Authorization", auth);
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
        return songs;
    }
}


