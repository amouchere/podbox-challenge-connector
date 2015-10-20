package com.podbox.challenge.service;

import com.podbox.challenge.domain.BillBoardResult;
import com.podbox.challenge.domain.spotify.Item;
import com.podbox.challenge.domain.spotify.SpotifySearchResult;
import com.podbox.challenge.domain.Track;
import com.podbox.challenge.domain.spotify.Tracks;
import com.podbox.challenge.service.spotify.SpotifyThread;
import org.apache.http.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;

@Component
public class ChartsService {

    private static final int NB_RESULT = 10;
    private static Logger LOGGER = LoggerFactory.getLogger(ChartsService.class);

    @Autowired
    private HttpClient httpClient;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;


    private CompletionService<SpotifySearchResult> completionService;

    public List<Track> getHot10() {

        Date startDate = new Date();
        LOGGER.info("getHot10");
        List<Track> list = new LinkedList<>();


        try {
            list = getTracks(list);
        } catch (IOException | JAXBException e) {
            LOGGER.error("an error occured", e);
        }

        List<Track> top10List = list.subList(0, NB_RESULT);

        // launch jobs
        launchAsynchronousSpotifyTasks(top10List);

        // wait for all jobs.
        waitEndOfJobs();

        Date endDate = new Date();
        LOGGER.info("Finish in " + (endDate.getTime() - startDate.getTime()) / 1000 + " seconds.");
        return top10List;
    }

    private void waitEndOfJobs() {
        int i = 0;
        while (i < NB_RESULT) {
            try {
                // find the first completed task
                SpotifySearchResult x = completionService.take().get();
                i++;
            } catch (InterruptedException | ExecutionException e) {
                LOGGER.error("an error occured", e);
            }
        }
    }

    private void launchAsynchronousSpotifyTasks(List<Track> top10List) {
        int indexTask = 0;
        for (Track track : top10List) {
            // Create task and launch
            SpotifyThread task = (SpotifyThread) context.getBean("spotifyThread");
            task.setTrack(track);
            task.setIdTask(indexTask);

            completionService.submit(task);
            LOGGER.info(" Task is submitted");
            indexTask++;
        }
    }


    private List<Track> getTracks(List<Track> list) throws IOException, JAXBException {
        final URL url = new URL("http://www.billboard.com/rss/charts/hot-100");
        final InputStream istream = url.openStream();

        JAXBContext context = JAXBContext.newInstance(BillBoardResult.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        BillBoardResult unmarshalObject = (BillBoardResult) unmarshaller.unmarshal(istream);

        return unmarshalObject.getChannel().getItem();
    }

    @PostConstruct
    private void init() {
        completionService = new ExecutorCompletionService<SpotifySearchResult>(threadPoolTaskExecutor);
    }
}
