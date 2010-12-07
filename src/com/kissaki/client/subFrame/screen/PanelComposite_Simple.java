package com.kissaki.client.subFrame.screen;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.kissaki.client.subFrame.debug.Debug;

/**
 * シンプルなパネルのコンポジット
 * 
 * ScreenInterfaceに関連しており、
 * 
 * 
 * @author sassembla
 *
 */
public class PanelComposite_Simple extends Composite implements ScreenInterface {
	Debug debug = null;
	SimplePanel simplePanel = null;
	
	public PanelComposite_Simple() {
		debug = new Debug(this);
		debug.removeTraceSetting(Debug.DEBUG_EVENT_ON);//このクラスでは、デバッグをイベント表示しない。
		
		simplePanel = new SimplePanel();
		initWidget(simplePanel);
		simplePanel.setSize("1000", "1000");
	}
	
	public void setToMainPanel (Widget w) {
		debug.trace("simplePanel_"+simplePanel);//末端から加えるのは無理、という事か。
		simplePanel.add(w);
	}
	
	public void setToSubPanel (Widget w) {
		
	}

	public void setToPanel(int id, Widget w) {
		
	}

}
