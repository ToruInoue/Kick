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
	
	
	static String MASTERKEY = "master";
	String channelId = MASTERKEY;
	
	public String greetServer(String input) throws IllegalArgumentException {

		if (input.equals("100")) {
			
	        return channel.getChannel(channelId);//Channelキーを返却
		}
		
		if (input.equals("200")) {
			
			if (count % 2 == 0) {
				channel.sendMessage(channelId, ""+count);	
			} else {
				channel.sendMessage(channelId, "Go");	
			}
			count++;
		}
		
		
		
		
		
		
		//ログイン
		if (input.startsWith("userLogin+")) {
			//ユーザー名とパスワードが送られてきていれば、そのまま。
			System.out.println("input_"+input);
			return channel.getChannel(channelId);//Channelキーを返却
		}
		
		
		if (input.startsWith("getItemData+")) {
//			アイテムデータをPushで返す
			
			channel.sendMessage(channelId, "コレでも食らいやがれ");
			
			return "ok";
		}
		
		return "default";//HTTP_OKキーを返せばいい
	}


	
}
