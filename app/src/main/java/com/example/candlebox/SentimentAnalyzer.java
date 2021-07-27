//package com.example.candlebox;
//
//import android.util.Log;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import org.jsoup.Connection;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.select.Elements;
//
//import java.io.IOException;
//
//public class SentimentAnalyzer extends AppCompatActivity {
//    private static final String url = "https://monkeylearn.com/sentiment-analysis-online/";
//
//    public static void main(String[] args) throws IOException {
////        Document doc = Jsoup.connect(url)
////                .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
////                .data("EmbedModel-module--subElem--vUF4f", "grapefruit")
////                .post();
////        Elements element = doc.getElementsByTag("textarea");
////        //System.out.println(doc.html());
////        System.out.println("Here is the text: " + element.html());
////        String title = doc.title();
////        //System.out.println(title);
//
//        Connection.Response response = Jsoup.connect("https://text2data.com/Demo")
//                .method(Connection.Method.POST)
//                .put("input", "grapefruit")
//                .execute();
//        JsoupPost =
//        System.out.println(response.body());
//
//    }
//
//}
