package com.kissaki.client.channel;

import com.google.gwt.core.client.JavaScriptObject;

public class Channel extends JavaScriptObject {
    protected Channel() {
    }

    public final native Socket open(SocketListener listener) /*-{
		var socket = this.open();
	
        socket.onopen = function(event) {
          listener.@com.kissaki.client.channel.SocketListener::onOpen()();
        };
        socket.onmessage = function(event) {
          listener.@com.kissaki.client.channel.SocketListener::onMessage(Ljava/lang/String;)(event.data);
        };
        
        return socket;
        
    }-*/;
}