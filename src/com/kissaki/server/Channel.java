package com.kissaki.server;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;

public class Channel {
    
    private static ChannelService cs = ChannelServiceFactory.getChannelService();

    public String getChannel(String channelId){
        return  cs.createChannel(channelId);
    }
     
    public void sendMessage(String channelID, String content) {
        
        // URIEncode
         try {
             content = URLEncoder.encode(content, "UTF-8");
         } catch (UnsupportedEncodingException e) {
             throw new RuntimeException(e);
         }
        
        ChannelMessage cm = 
            new ChannelMessage(channelID,
                "{ \"content\":\"" + content + "\"}");
        cs.sendMessage(cm);
    }
 }
