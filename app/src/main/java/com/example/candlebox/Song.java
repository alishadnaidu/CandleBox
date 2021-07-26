package com.example.candlebox;
public class Song {

    private String songName;
    private String artist;
    private int songCover;

    public Song(String songName, String artist, int songCover) {
        this.songName = songName;
        this.artist = artist;
        this.songCover = songCover;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Integer getSongCover() {
        return songCover;
    }

    public void setSongCover(Integer songCover) {
        this.songCover = songCover;
    }
}