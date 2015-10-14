package com.podbox.challenge.domain.spotify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Item from Spotify
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {

    @JsonProperty("uri")
    private String uri;

    @JsonProperty("uri")
    public String getUri() {
        return uri;
    }

    @JsonProperty("uri")
    public void setUri(String uri) {
        this.uri = uri;
    }


}