package com.podbox.challenge.service;

import com.podbox.challenge.domain.BillBoardResult;
import com.podbox.challenge.domain.spotify.Item;
import com.podbox.challenge.domain.spotify.SpotifySearchResult;
import com.podbox.challenge.domain.Track;
import com.podbox.challenge.domain.spotify.Tracks;
import com.podbox.challenge.service.spotify.SpotifyService;
import org.apache.http.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
public class ChartsService {

    private static Logger LOGGER = LoggerFactory.getLogger(ChartsService.class);

    @Autowired
    private SpotifyService spotifyService;

    @Autowired
    private HttpClient httpClient;


    public List<Track> getHot10() {

        LOGGER.info("getHot10");
        List<Track> list = new ArrayList<Track>();


        try {
            list = getTracks(list);
        } catch (IOException | JAXBException e) {
            LOGGER.error("an error occured", e);
        }

        List<Track> top10List = list.subList(0, 50);

        for (Track track : top10List) {
            LOGGER.info(track.getArtist());
            LOGGER.info(track.getTitle());

            SpotifySearchResult spotifySearchResult = spotifyService.getSpotifySearchResult(track);


            if (spotifySearchResult != null){
                final Tracks tracks = spotifySearchResult.getTracks();
                final List<Item> items = tracks.getItems();

                if (items.size() > 0) {
                    // get the first one ?
                    track.setSpotifyUri(items.get(0).getUri());
                }
            }

        }


        return top10List;
    }



    private List<Track> getTracks(List<Track> list) throws IOException, JAXBException {
        final URL url = new URL("http://www.billboard.com/rss/charts/hot-100");
        final InputStream istream = url.openStream();

        JAXBContext context = JAXBContext.newInstance(BillBoardResult.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        BillBoardResult unmarshalObject = (BillBoardResult) unmarshaller.unmarshal(istream);

        return unmarshalObject.getChannel().getItem();
    }
}
