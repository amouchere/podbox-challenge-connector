package com.podbox.challenge.domain;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "track")
public class Track {


    public Integer rank;

    public String title;

    public String artist;

    public String spotifyUri;

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
