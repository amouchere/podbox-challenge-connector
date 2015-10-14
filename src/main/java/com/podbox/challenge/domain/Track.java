package com.podbox.challenge.domain;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "track")
public class Track {


    private Integer rank;

    private String title;

    private String artist;

    private String spotifyUri;

    public Integer getRank() {
        return rank;
    }
    @XmlElement (name="rank_this_week")
    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        // The title in the billboard site is like : <title>1: The Hills</title>
        final String[] split = title.split(": ");
        this.title = split[1];
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getSpotifyUri() {
        return spotifyUri;
    }

    public void setSpotifyUri(String spotifyUri) {
        this.spotifyUri = spotifyUri;
    }


   /* public Track(Integer rank, String title, String artist) {
        this(rank, title, artist, null);
    }

    public Track(Integer rank, String title, String artist, String spotifyUri) {
        this.rank = rank;
        this.title = title;
        this.artist = artist;
        this.spotifyUri = spotifyUri;
    }*/
}
