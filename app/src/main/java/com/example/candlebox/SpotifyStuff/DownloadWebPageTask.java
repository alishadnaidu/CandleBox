package com.example.candlebox.SpotifyStuff;

import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

//testing for sentiment analysis, work in progress
public class DownloadWebPageTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            for (String url : urls) {
                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
                try {
                    HttpResponse execute = client.execute(httpGet);
                    InputStream content = execute.getEntity().getContent();

                    BufferedReader buffer = new BufferedReader(
                            new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return response;
        }


        @Override
        protected void onPostExecute(String result) {
            //tvSong.setText(Html.fromHtml(result));
            Log.i("Here's the result: ", String.valueOf(Html.fromHtml(result)));
        }

    public void readWebpage() {
        DownloadWebPageTask task = new DownloadWebPageTask();
        task.execute(new String[] { "http://www.google.com" });
        Log.i("DownloadWebPageTask", "downloaded webpage string");
    }

}
