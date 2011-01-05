package com.kissaki.client.messengerSystem;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.Window;
import com.kissaki.client.subFrame.debug.Debug;


public class MessengerSystem {
	Debug debug = null;
	
	public String messengerName;
	
	public final String KEY_MESSENGER_NAME = "messengerName";
	public final String KEY_MESSENGER_EXEC = "exec";
	public final String KEY_MESSENGER_tagValueGroup = "tagValue"; 
	
	

	
	/**
	 * コンストラクタ
	 */
	public MessengerSystem () {
		debug = new Debug(this);
		setUp();
		messengerName = "myName";//If null, throws Exception,,, JSONValue must have some value.
		Window.alert("スタート地点");
		
//		Window.Location. //場所取得が出来る、ステキ。　この、意識しているのと実際に試した時のわくわく感の違いは、どこかに書き留めておきたいもの。
		
		Window.setTitle("メッセンジャーテスト中");
		Window.getTitle();
		
		String host = Window.Location.getHost();
		debug.trace("host_"+host);
		String pathName = Window.Location.getPath();
		debug.trace("pathName_"+pathName);
		
		
		/*
		 * 現在のアドレスを使って、メッセンジャーをセットする、
		 * アドレスが変わるたびに、定期的にリセットする必要がある、、のか？
		 * ほんとに？
		 * それってきついな。
		 * 
		 * 寿命がURLと同値である、ということは、もちろん、移動しちゃったらまあ効かないという事ですね。
		 * 
		 */
		String href = Window.Location.getHref();
		debug.trace("href_"+href);
		
		JSONObject messageRoot = new JSONObject();
		JSONObject tagValueObject = new JSONObject();
		String value = "atai";
		tagValueObject.put("value", new JSONString(value));
		debug.trace("tagValueObject_"+tagValueObject);
		
		
		String exec = "TestExecution";
		
		messageRoot.put(KEY_MESSENGER_NAME, new JSONString(messengerName));
		messageRoot.put(KEY_MESSENGER_EXEC, new JSONString(exec));
		messageRoot.put(KEY_MESSENGER_tagValueGroup, tagValueObject);
		
		post(messageRoot.toString(), href);
	}

	
	/**
	 * 特定の宛先に向けて、メッセージを送付する
	 * @param message
	 * @param uri
	 */
	private native void post (String message, String uri) /*-{
		window.postMessage(message, uri);
	}-*/;

	/**
	 * Nativeのメッセージ受信部分
	 * ここに、無条件で届く筈。
	 * 届いたあと、外部のメソッドを使って分解できるようにしたい。
	 * 暫定的に、MessengerSystemのObj-C版でつかっている要素を抜粋して使う。
	 */
	private native void setUp() /*-{
		
		window.addEventListener('message', receiver, false);//そもそもコレがきいているのだろうか
		alert("セットアップ");
		
		function receiver(e) {
			alert("受信");
			window.alert(e.data);
			
//			if (e.origin == 'http://example.com') {
//				if (e.data == 'Hello world') {
//					e.source.postMessage('Hello', e.origin);
//				} else {
//					alert(e.data);
//				}
//			}
		}
	}-*/;
	
}
