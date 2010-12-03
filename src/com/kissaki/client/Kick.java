package com.kissaki.client;

import com.kissaki.client.channel.Channel;
import com.kissaki.client.channel.ChannelFactory;
import com.kissaki.client.channel.SocketListener;
import com.kissaki.client.subFrame.debug.Debug;
import com.kissaki.client.subFrame.screen.ScreenEventRegister;
import com.kissaki.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.widgetideas.graphics.client.Color;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Kick implements EntryPoint {
	Debug debug;
	ProcessingImplements p2;

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
		GWTCanvas canvas = new GWTCanvas(0,0,600,280);
		canvas.setBackgroundColor(new Color(0, 0, 0, 0.5f));


		new ScreenEventRegister(canvas);

		debug = new Debug(this);

		ProcessingImplements p = new ProcessingImplements(canvas.getElement(), "");
		p.size("600","280","");

		p.background(100);
		p.ellipse("10", "100", "40", "40");


		//上書きできるw
		p2 = new ProcessingImplements(canvas.getElement(), "");
		p2.size("100","100", "");
		p2.ellipse("50", "40", "50", "50");

		//ログイン
		iAm = "p_"+System.currentTimeMillis();
		p2.init("");//あれ、引数がある、、、

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
		return;
	}


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
					p2.ellipse("100", "100", "10", "10");//リロードがかかればいいのだけれど、そうで無い場合は、どうすればいいかな。
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
