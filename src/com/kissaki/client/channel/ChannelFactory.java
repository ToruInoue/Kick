package com.kissaki.client.channel;

public class ChannelFactory {
    public static final native Channel createChannel(String channelId) /*-{
      return new $wnd.goog.appengine.Channel(channelId);	
    }-*/;
}
