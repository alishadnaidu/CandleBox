package com.example.candlebox;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SongRecActivity extends AppCompatActivity {

    private RecyclerView rvSongRecs;
    private ArrayList<Song> songsList;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_recs);
        rvSongRecs = findViewById(R.id.rvSongRecs);

        songsList = new ArrayList<>();
        songsList.add(new Song("Cornelia Street", "Taylor Swift", R.drawable.cat_cafe));
        songsList.add(new Song("Mirrorball", "Taylor Swift", R.drawable.final_candle_logo));

        SongsAdapter songsAdapter = new SongsAdapter(this, songsList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        rvSongRecs.setLayoutManager(linearLayoutManager);
        rvSongRecs.setAdapter(songsAdapter);
    }
}
