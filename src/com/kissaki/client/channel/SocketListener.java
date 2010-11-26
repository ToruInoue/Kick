package com.kissaki.client.channel;

public interface SocketListener {
    void onOpen();
    void onMessage(String message);
}
