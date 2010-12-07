package com.kissaki.client.subFrame.screen;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.user.client.ui.Widget;
import com.kissaki.client.subFrame.debug.Debug;

public class ScreenHandler implements EventHandler {
	Debug debug = null;
	
	/**
	 * コンストラクタ
	 */
	public ScreenHandler() {
		debug = new Debug(this);
		debug.trace("コンストラクタ");
	}

	
	/**
	 * メイン用Widget加算
	 * @param widget
	 */
	public void addToMain (Widget widget) {
		ScreenController screenController = ScreenController.getScreenController();//
		screenController.setMainScreen(widget);
	}
	
	/**
	 * サブ用のWidget加算
	 * @param widget
	 */
	public void addToSub (Widget widget) {
		ScreenController screenController = ScreenController.getScreenController();
		screenController.setSubScreen(widget);
	}
	
}