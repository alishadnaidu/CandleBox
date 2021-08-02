//package com.example.candlebox.SpotifyStuff;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.TextView;
//
//import org.apache.http.HttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
//
//import java.io.BufferedReader;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//
//public abstract class AsyncTask<S, V, S1> extends Activity {
//    private TextView textView;
//
//    /** Called when the activity is first created. */
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //setContentView(R.layout.main);
//        //textView = (TextView) findViewById(R.id.TextView01);
//    }
//
//    protected abstract Void onPreExecute();
//
//    protected abstract String doInBackground(String... urls);
//
//    protected abstract void onPostExecute(String result);
//
//    private class AsyncClass extends AsyncTask<String, Void, String> {
//        @Override
//        protected String doInBackground(String... urls) {
//            String response = "";
//            for (String url : urls) {
//                DefaultHttpClient client = new DefaultHttpClient();
//                HttpGet httpGet = new HttpGet(url);
//                try {
//                    HttpResponse execute = client.execute(httpGet);
//                    InputStream content = execute.getEntity().getContent();
//
//                    BufferedReader buffer = new BufferedReader(
//                            new InputStreamReader(content));
//                    String s = "";
//                    while ((s = buffer.readLine()) != null) {
//                        response += s;
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            return response;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            //textView.setText(Html.fromHtml(result));
//        }
//    }
//
//    public void readWebpage(View view) {
//        DownloadWebPageTask task = new DownloadWebPageTask();
//        task.execute(new String[] { "http://www.google.com" });
//
//    }
//}
