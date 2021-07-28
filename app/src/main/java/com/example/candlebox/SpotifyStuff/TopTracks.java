package com.example.candlebox.SpotifyStuff;

public class TopTracks {
    private String id;
    private String name;
    private TopTracks artist;

    public TopTracks(String id, String name) {
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

    public TopTracks getArtist() {
        return artist;
    }

    public void setArtist(TopTracks artist) {
        this.artist = artist;
    }
}
