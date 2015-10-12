package com.podbox.challenge.domain;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by LaBete on 12/10/2015.
 */
@XmlRootElement(name = "rss")
public class BillBoardResult {

    private Channel channel;

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
