package com.podbox.challenge.domain.spotify;

import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LaBete on 12/10/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotifySearchResult {

    @JsonProperty("tracks")
    private Tracks tracks;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    /**
     * @return The tracks
     */
    @JsonProperty("tracks")
    public Tracks getTracks() {
        return tracks;
    }

    /**
     * @param tracks The tracks
     */
    @JsonProperty("tracks")
    public void setTracks(Tracks tracks) {
        this.tracks = tracks;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }






}
