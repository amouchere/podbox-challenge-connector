package com.podbox.challenge.domain;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by LaBete on 12/10/2015.
 */
@XmlRootElement(name="channel")
public class Channel {

    private List<Track> item;

    public List<Track> getItem() {
        return item;
    }

    public void setItem(List<Track> item) {
        this.item = item;
    }
}
