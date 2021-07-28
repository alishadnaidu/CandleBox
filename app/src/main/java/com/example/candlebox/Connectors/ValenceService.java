package com.example.candlebox.Connectors;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.candlebox.SpotifyStuff.RecentlyPlayedSong;
import com.example.candlebox.SpotifyStuff.SpotifyMainActivity;
import com.example.candlebox.SpotifyStuff.Valence;
import com.example.candlebox.SpotifyStuff.VolleyCallBack;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ValenceService {

    public static final String TAG = "ValenceService";
    public static ArrayList<String> finalValences = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private RequestQueue queue;
    public static HashMap<String, String> valenceMap = new HashMap<String, String>();
    public static HashMap<String, String> negativeMap = new HashMap<String, String>();
    public static HashMap<String, String> neutralMap = new HashMap<String, String>();
    public static HashMap<String, String> positiveMap = new HashMap<String, String>();

    public ValenceService(Context context) {
        sharedPreferences = context.getSharedPreferences("SPOTIFY", 0);
        queue = Volley.newRequestQueue(context);
    }

    public ArrayList<String> getValences() {
        return finalValences;
    }


    public ArrayList<String> getValenceValues(final VolleyCallBack callBack) {

        //String realPoint = "https://api.spotify.com/v1/audio-features?ids=2Eeur20xVqfUoM3Q7EFPFt%2C3hUxzQpSfdDqwM3ZTFQY0K%2C1BxfuPKGuaTgP7aM0Bbdwr%2C4svZDCRz4cJoneBpjpx8DJ%2C5kI4eCXXzyuIUXjQra0Cxi";
        String endpoint = SpotifyMainActivity.valenceEndpoint;
        //Log.i("Valences endpoint!!!:", endpoint);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, endpoint, null, response -> {
                    JSONArray audioFeatures = response.optJSONArray("audio_features");
                    Log.i("ValenceService", String.valueOf(audioFeatures.length()));
                    for (int n = 0; n < audioFeatures.length(); n++) {
                        try {
                            double v = audioFeatures.getJSONObject(n).optDouble("valence");
                            finalValences.add(String.valueOf(v));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    Log.i("All the valences: ", String.valueOf(finalValences));
                    makeMap();

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
        return finalValences;
    }

    private void makeMap() {
        for (int i = 0; i < SpotifyMainActivity.songIds.size(); i++) {
            valenceMap.put(SpotifyMainActivity.songIds.get(i), finalValences.get(i));
        }
        for (Map.Entry m : valenceMap.entrySet()) {
            Log.i(TAG, m.getKey() + ": " + m.getValue());
        }
        splitMap();
    }

    private void splitMap() {
        for (Map.Entry m : valenceMap.entrySet()) {
            if (Double.parseDouble(m.getValue().toString()) < 0.333) {
                negativeMap.put(m.getKey().toString(), m.getValue().toString());
            }
            else if (Double.parseDouble(m.getValue().toString()) < 0.666) {
                neutralMap.put((m.getKey().toString()), m.getValue().toString());
            }
            else {
                positiveMap.put((m.getKey().toString()), m.getValue().toString());
            }
        }
    }
}