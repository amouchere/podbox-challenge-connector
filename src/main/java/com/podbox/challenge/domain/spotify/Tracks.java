package com.podbox.challenge.domain.spotify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Tracks from Spotify
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Tracks {

    @JsonProperty("items")
    private List<Item> items = new ArrayList<>();

    @JsonProperty("items")
    public List<Item> getItems() {
        return items;
    }

    @JsonProperty("items")
    public void setItems(List<Item> items) {
        this.items = items;
    }


}