package com.podbox.challenge.service.spotify;

import com.podbox.challenge.domain.Track;
import com.podbox.challenge.domain.spotify.SpotifySearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.concurrent.Callable;

@Component
@Scope("prototype")
public class SpotifyThread implements Callable {

    private static Logger LOGGER = LoggerFactory.getLogger(SpotifyThread.class);

    @Autowired
    private RestTemplate restTemplate;

    private String spotifySearchUrl = "https://api.spotify.com/v1/search";

    private Track track;


    private int idTask;

    @Override
    public Object call() throws Exception {
        // Add spotify call
        URI targetUrl = UriComponentsBuilder.fromUriString(spotifySearchUrl)
                .queryParam("q", track.getTitle())
                .queryParam("type", "track")
                .build()
                .toUri();
        LOGGER.info("URI : " + targetUrl.toString());

        int i = (int) (Math.random() * (10 - 5) + 5) * 1000;
        LOGGER.info("task " + getIdTask() + " sleep for"+ i);
        Thread.sleep(i);
        final SpotifySearchResult result = restTemplate.getForObject(targetUrl, SpotifySearchResult.class);
        LOGGER.info("task done: " + getIdTask());
        result.setIdTask(getIdTask());
        return result;
    }

    public void setTrack(Track track) {
        this.track = track;
    }


    public int getIdTask() {
        return idTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }
}
