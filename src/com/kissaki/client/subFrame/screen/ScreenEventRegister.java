package com.kissaki.client.subFrame.screen;

import com.google.gwt.event.shared.SimpleEventBus;//	HandlerManager;
import com.google.gwt.user.client.ui.Widget;

/**
 * スクリーン関連のイベントバス
 * 
 * @author sassembla
 */
public class ScreenEventRegister extends SimpleEventBus {

	/**
	 * コンストラクタ
	 * @param w
	 */
	public ScreenEventRegister(Widget w) {
		this.addHandler(ScreenEvent.TYPE, new ScreenHandler());
		this.fireEvent(new ScreenEvent(1, w));
//		this.fireEvent(new ScreenEvent(2, w));
	}

	
}
