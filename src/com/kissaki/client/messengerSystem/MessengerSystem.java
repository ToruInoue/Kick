package com.kissaki.client.messengerSystem;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Window;
import com.kissaki.client.subFrame.debug.Debug;

public class MessengerSystem {
	Debug debug = null;
	
	
	public MessengerSystem () {
	}


	/**
	 * 名称を受け取って初期化する
	 * @param name
	 */
	public MessengerSystem(String name) {
		debug = new Debug(this);
		setUp(name);
	}


	private native void setUp(String name) /*-{
		
	}-*/;
	
}
