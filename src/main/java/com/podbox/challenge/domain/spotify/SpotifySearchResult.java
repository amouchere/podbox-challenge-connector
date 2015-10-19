package com.podbox.challenge.domain.spotify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by LaBete on 12/10/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotifySearchResult {

    private int idTask;

    @JsonProperty("tracks")
    private Tracks tracks;

    @JsonProperty("tracks")
    public Tracks getTracks() {
        return tracks;
    }

    @JsonProperty("tracks")
    public void setTracks(Tracks tracks) {
        this.tracks = tracks;
    }

    public int getIdTask() {
        return idTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }
}
