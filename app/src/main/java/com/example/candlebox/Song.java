package com.example.candlebox;

import org.parceler.Parcel;

@Parcel
public class Song {

    public String songName;
    public String artist;
    public int songCover;
    public String songUri;

    //empty constructor for Parcel
    public Song() {}

    public Song(String songName, String artist, int songCover, String songUri) {
        this.songName = songName;
        this.artist = artist;
        this.songCover = songCover;
        this.songUri = songUri;
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

    public String getSongUri() {
        return songUri;
    }

    public void setSongUri(String uri) {
        this.songUri = uri;
    }
}