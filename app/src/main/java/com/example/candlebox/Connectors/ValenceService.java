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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
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
    public static HashMap<String, String> sortedMap = new LinkedHashMap<String, String>();

    public ValenceService(Context context) {
        sharedPreferences = context.getSharedPreferences("SPOTIFY", 0);
        queue = Volley.newRequestQueue(context);
    }

    public ArrayList<String> getValences() {
        return finalValences;
    }


    // gets the valence value of each of the songs in the top tracks
    public ArrayList<String> getValenceValues(final VolleyCallBack callBack) {

        //endpoint is generated in SpotifyMainActivity after top tracks are pulled
        String endpoint = SpotifyMainActivity.valenceEndpoint;
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

    // generates a large map of all top tracks: (song id: valence)
    private void makeMap() {
        for (int i = 0; i < SpotifyMainActivity.songIds.size(); i++) {
            valenceMap.put(SpotifyMainActivity.songIds.get(i), finalValences.get(i));
        }
        for (Map.Entry m : valenceMap.entrySet()) {
            Log.i(TAG, m.getKey() + ": " + m.getValue());
        }
        sortMap();
    }

    // splits map into equal sized hashmaps of positive, neutral, and negative
    // allows for personalization of "happiness" level of songs for user (ex. if user listens to
    // solely happier songs, their "sad"/negative sentiment candle will still be objectively fairly happy
    private void splitMap() {
        Integer i = 0;
        Integer size = sortedMap.size();
        for (Map.Entry m : sortedMap.entrySet()) {
            if (i < size/3) {
                negativeMap.put(m.getKey().toString(), m.getValue().toString());
            }
            else if (i < size/(3.0/2.0)) {
                Log.i("Logging: ", "One for neutral tag");
                neutralMap.put((m.getKey().toString()), m.getValue().toString());
            }
            else {
                positiveMap.put((m.getKey().toString()), m.getValue().toString());
            }
            i++;
        }
        Log.i("Count: Negative map: ", negativeMap.toString());
        Log.i("Count: Neutral map: ", neutralMap.toString());
        Log.i("Count: Positive map: ", positiveMap.toString());
    }

    // sort valenceMap by valences
    private void sortMap() {
        // Create a list from elements of HashMap
        List<Map.Entry<String, String> > list =
                new LinkedList<Map.Entry<String, String> >(valenceMap.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, String> >() {
            public int compare(Map.Entry<String, String> o1,
                               Map.Entry<String, String> o2)
            {
                Integer first = (int) (Double.parseDouble(o1.getValue().toString())*1000);
                Integer second = (int) (Double.parseDouble(o2.getValue().toString())*1000);
                return first.compareTo(second);
            }
        });

        // put data from sorted list to hashmap
        for (Map.Entry<String, String> aa : list) {
            sortedMap.put(aa.getKey(), aa.getValue());
        }
        Log.i("Final sorted hashmap: ", sortedMap.toString());
        splitMap();
    }
}