package com.example.candlebox.SpotifyStuff;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.candlebox.R;
import com.example.candlebox.Song;
import com.example.candlebox.SpotifyStuff.SongsAdapter;

import java.util.ArrayList;

public class SongRecActivity extends AppCompatActivity {

    private RecyclerView rvSongRecs;
    private ArrayList<Song> songsList;

    public static final String TAG = "SongRecActivity";
    private static final String CLIENT_ID = "41b1fdcb5723453d9e7e114cb85bf7be";
    private static final String REDIRECT_URI = "https://courses.codepath.com/courses/android_university_fast_track/pages/bootcamp_structure";

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_recs);
        rvSongRecs = findViewById(R.id.rvSongRecs);

        songsList = new ArrayList<>();
        songsList.add(new Song("Cornelia Street", "Taylor Swift", R.drawable.corneliastreet, "spotify:track:3fGnrtrtL1IHSX9t4DKOYf"));
        songsList.add(new Song("New Year's Day", "Taylor Swift", R.drawable.rep, "spotify:track:7F5oktn5YOsR9eR5YsFtqb"));
        songsList.add(new Song("1950", "King Princess", R.drawable.kp, "spotify:track:0CZ8lquoTX2Dkg7Ak2inwA"));
        songsList.add(new Song("Love Myself", "Olivia O'Brien", R.drawable.lovemyself, "spotify:track:36y5E5cxFbz6xU8UqT7Iv7"));
        songsList.add(new Song("Wildest Dreams", "Taylor Swift", R.drawable.wildestdreams, "spotify:track:59HjlYCeBsxdI0fcm3zglw"));
        songsList.add(new Song("You Need To Calm Down", "Taylor Swift", R.drawable.lover, "spotify:track:6RRNNciQGZEXnqk8SQ9yv5"));
        songsList.add(new Song("Mirrorball", "Taylor Swift", R.drawable.folklore, "spotify:track:0ZNU020wNYvgW84iljPkPP"));

        SongsAdapter songsAdapter = new SongsAdapter(this, songsList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        rvSongRecs.setLayoutManager(linearLayoutManager);
        rvSongRecs.setAdapter(songsAdapter);
    }


}
