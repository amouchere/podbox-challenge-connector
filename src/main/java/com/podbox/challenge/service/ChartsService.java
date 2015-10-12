package com.podbox.challenge.service;

import com.podbox.challenge.domain.BillBoardResult;
import com.podbox.challenge.domain.Track;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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
    RestTemplate restTemplate;

    @Autowired
    private HttpClient httpClient;

    public List<Track> getHot10() {

        LOGGER.info("getHot10");
        List<Track> list = new ArrayList<Track>();


        try {
            list = getTracks(list);
        } catch (IOException e) {
            LOGGER.error("an error occured", e);
        } catch (JAXBException e) {
            LOGGER.error("an error occured", e);
        }


        return list;
    }

    private List<Track> getTracks(List<Track> list) throws IOException, JAXBException {
        final URL url = new URL("http://www.billboard.com/rss/charts/hot-100");
        final InputStream istream = url.openStream();

        JAXBContext context = JAXBContext.newInstance(BillBoardResult.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        BillBoardResult unmarshalObject = (BillBoardResult) unmarshaller.unmarshal(istream);

        for (Track track : unmarshalObject.getChannel().item) {
            LOGGER.info(track.artist);
        }

        list = unmarshalObject.getChannel().item;
        return list;
    }
}
