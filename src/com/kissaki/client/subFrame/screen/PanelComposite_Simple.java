package com.kissaki.client.subFrame.screen;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * シンプルなパネルのコンポジット
 * サイズに関係なく、Canvasは影響を受けているようだ
 * 
 * サイズに影響を及ぼしているであろうものをリストアップしよう。
 * @author sassembla
 *
 */
public class PanelComposite_Simple extends Composite implements ScreenInterface {
	
	SimplePanel simplePanel = null;
	
	public PanelComposite_Simple() {
		simplePanel = new SimplePanel();
		initWidget(simplePanel);
		simplePanel.setSize("1000", "1000");
	}
	
	public void setToMainPanel (Widget w) {
		simplePanel.add(w);
	}
	
	public void setToSubPanel (Widget w) {
		
	}

	public void setToPanel(int id, Widget w) {
		
	}

}
