package com.kissaki.server;

import com.kissaki.client.GreetingService;
import com.kissaki.shared.FieldVerifier;
import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {
	int count = 0;
	Channel channel = new Channel();
	
	static String MSTERKEY = "master";
	
	public String greetServer(String input) throws IllegalArgumentException {

		if (input.equals("100")) {
			String channelId = MSTERKEY;
	        return channel.getChannel(channelId);
		}
		
		if (input.equals("200")) {
			sendMessage(""+count++);	
		}
		
		return "default";
	}

	public void sendMessage(String msg) {
		String encodedMessage = msg;
		getChannelService().sendMessage(new ChannelMessage(MSTERKEY, encodedMessage));	
	}
	

	private static ChannelService getChannelService() {
		// NB: This is really cheap, but if it became expensive, we could stuff
		// it in a thread local.
		return ChannelServiceFactory.getChannelService();
	}

}
