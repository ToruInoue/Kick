package com.kissaki.client.subFrame.screen;

import com.google.gwt.user.client.ui.Widget;


/**
 * スクリーンの仕掛けで使用しているインターフェース
 * @author sassembla
 *
 */
public interface ScreenInterface {

	public void setToMainPanel (Widget w);
	public void setToSubPanel (Widget w);
	
	public void setToPanel(int id, Widget w);
	
	
}
