package com.podbox.challenge.service.spotify;

import com.podbox.challenge.App;
import com.podbox.challenge.domain.Track;
import com.podbox.challenge.domain.spotify.SpotifySearchResult;
import com.podbox.challenge.service.ChartsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by LaBete on 15/10/2015.
 */
@Component
public class SpotifyService {

    private static Logger LOGGER = LoggerFactory.getLogger(SpotifyService.class);

    @Autowired
    ApplicationContext context;

    @Autowired
    ThreadPoolTaskExecutor taskExecutor;

    public SpotifySearchResult getSpotifySearchResult(Track track) {

        SpotifySearchResult spotifySearchResult = null;

        // Create task and launch
        SpotifyThread task = (SpotifyThread) context.getBean("spotifyThread");
        task.setTrack(track);

        Future<SpotifySearchResult> future = taskExecutor.submit(task);

        try {
            spotifySearchResult = future.get();
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.error("an error occured", e);
        }
        return spotifySearchResult;
    }
}
