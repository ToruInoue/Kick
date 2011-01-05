package com.kissaki.client;

import com.kissaki.client.channel.Channel;
import com.kissaki.client.channel.ChannelFactory;
import com.kissaki.client.channel.SocketListener;
import com.kissaki.client.messengerSystem.MessengerSystem;
import com.kissaki.client.subFrame.debug.Debug;
import com.kissaki.client.subFrame.screen.ScreenEventRegister;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.widgetideas.graphics.client.Color;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Kick implements EntryPoint {
	Debug debug;
	ProcessingImplements p;

	KickController kCont;
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
		+ "attempting to contact the server. Please check your network "
		+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT
	.create(GreetingService.class);

	String iAm;
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		if (false) {
			kCont = new KickController();
			return;
		}
		if (false) {
		
		debug = new Debug(this);
		
		GWTCanvas canvas = new GWTCanvas(0,0,600,280);
		ScreenEventRegister reg = new ScreenEventRegister(canvas);
		p = new ProcessingImplements(canvas.getElement(), "");
		p.size("600","280","");
		
		p.background(""+100);
		p.ellipse("10", "100", "40", "40");
//		p.arc(""+100, ""+20, ""+30, ""+49, ""+9, ""+6);
		
		p.stroke(""+123,"20","20");//これでカラー
		p.rect(""+30, ""+20, ""+55, ""+55);
		debug.trace("createInteger_"+IntegerJavaScriptObject.getInt());
		
	
//		//ログイン前の仮キー
		iAm = "p_"+System.currentTimeMillis();

		
		
		

		//ログイン後の入力処理
		greetingService.greetServer("100",
				new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				debug.trace("failure");
			}

			public void onSuccess(String result) {
				debug.trace("success!_"+result);
				getKey(result);
			}
		}
		);
	}
		MessengerSystem messenger = new MessengerSystem();
		
		
		return;
	}
	
	public static native JavaScriptObject createInteger (int i2) /*-{
//		function a (i2) {//値になるわけでも、実行されるわけでもない
//    		var s = i2;
//    		return this;
//    	};//悟った。無理だ。
    	
    	
//    	return (i2);//匿名関数だとnullになる
//    	return valueOf(a(i2));//DOMWindow。
		//return a(i2);
//		return eval(a(i2));//エラー
//		return valueOf(a (i2));

//		var o = { prop : i2 };//書き方変えても、駄目な物は駄目。JavaScriptObjectを継承したint値があればいいわけだ。
//		var o = new Object();
//		o.prop = i2;
//		return o.prop;
	}-*/;


	void getKey (String key){
		debug.trace("到達したので開く_" + key);

		/**
		 * 取得したキーでチャンネルを開く
		 */
		Channel channel = ChannelFactory.createChannel(key);
		
		/**
		 * 接続ハンドラ
		 */
		channel.open(new SocketListener() {

			public void onOpen() {
				debug.trace("channel 開きました");

				/**
				 * サーバに対して、拡散を希望
				 */
				greetingService.greetServer("200",
						new AsyncCallback<String>() {
					
					public void onFailure(Throwable caught) {
						debug.trace("failure");
					}

					public void onSuccess(String result) {
						debug.trace("success!_"+result);
					}
				});
			}


			public void onMessage(String encodedData) {
				debug.trace(iAm+"_メッセージを受け取りました_" + encodedData + "/");//改行コードが入ってる。
				
				debug.trace("len_"+encodedData.length());
				if (encodedData.length() == 16) {//条件分けできねえ。なんでだ。オブジェクトではない、とかなのか。encodeの問題なのか。→末尾にnullが含まれてる何かだ。。。。うーーん、、、　decodeEncodeが必須なわけだ。
					debug.trace("届いてるんですよー");
					p.ellipse("100", "100", "10", "10");//リロードがかかればいいのだけれど、そうで無い場合は、どうすればいいかな。
				}
				
				greetingService.greetServer("300",
						new AsyncCallback<String>() {
					
					public void onFailure(Throwable caught) {
						debug.trace("failure");
					}

					public void onSuccess(String result) {
						debug.trace("success!_"+result);
					}
				});
			}
			
			
			public void onClose (String encodedData) {
				debug.trace("接続切断_"+encodedData);
			}
			
		});
	}



}
