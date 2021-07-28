package com.example.candlebox.SpotifyStuff;

public class RecentlyPlayedSong {

    private String id;
    private String name;

    public RecentlyPlayedSong(String id, String name) {
        this.name = name;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
