package com.kissaki.client.channel;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * こいつらは、GoogleのDDR由来のクラス。
 * oncloseに該当する処理とかは積んでないので不思議。
 * まあ、スピンダウンに任せていいという発想なのかもしれない。
 * @author ToruInoue
 *
 */
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
