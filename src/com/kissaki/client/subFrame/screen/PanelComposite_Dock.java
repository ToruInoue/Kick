package com.kissaki.client.subFrame.screen;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.Image;
import com.kissaki.client.subFrame.debug.Debug;

/**
 * ドックパネルの特性：どこか一カ所にしか出せない
 * サイズはどこかで固定されている
 * @author sassembla
 *
 */
public class PanelComposite_Dock extends Composite {
	DockPanel dockPanel = null;
	Image image = null;
	Debug debug = null;
	
	/**
	 * コンストラクタ
	 */
	public PanelComposite_Dock() {
		debug = new Debug(this);
		
		dockPanel = new DockPanel();
		dockPanel.setBorderWidth(1);
		initWidget(dockPanel);
//		dockPanel.setSize("900", "800");
		
		image = new Image("resource/spacer.gif");//ロックしちゃうのか、、
		update();
//		image.setUrl("resource/spacer.gif");
		
//		image.addLoadHandler(new LoadHandler() {
//			
//			public void onLoad(LoadEvent event) {
//				debug.trace("event_"+event);
//				update();
//			}
//		});
		
		
		
		
		
	}
	
	/**
	 * メモ、Resourceろんな事が出来そう
	 */
	public void test () {

		ImageResource resource = null;
		resource = new ImageResource() {
			
			public String getName() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public boolean isAnimated() {
				// TODO Auto-generated method stub
				return false;
			}
			
			public int getWidth() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public String getURL() {
				
				// TODO Auto-generated method stub
				return "resource/spacer.gif";
			}
			
			public int getTop() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public int getLeft() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public int getHeight() {
				// TODO Auto-generated method stub
				return 0;
			}
		};
		image.setResource(resource);
	}
	
	public void update () {
		dockPanel.add(image, DockPanel.NORTH);
	}
	

	/**
	 * メインパネルにWidgetをセットする。
	 * 上書き。
	 * @param w
	 */
	public void setToMainPanel (Widget w) {
//		debug.trace("setToMainPanel_");
//		dockPanel.add(w, DockPanel.CENTER);
//		dockPanel.add(w, DockPanel.NORTH);
//		dockPanel.add(w, DockPanel.WEST);
//		dockPanel.add(w, DockPanel.EAST);
		dockPanel.add(w, DockPanel.SOUTH);
//		mainPanel.clear(); 
//		mainPanel.add(w);
	}
	

	/**
	 * サブパネルにWidgetをセットする。
	 * 上書き。
	 * @param w
	 */
	public void setToSubPanel (Widget w) {
//		debug.trace("setToSubPanel_");
//		subPanel.clear(); 
//		dockPanel.add(w, DockPanel.CENTER);
//		dockPanel.add(w, DockPanel.WEST);
//		dockPanel.add(w, DockPanel.EAST);
//		dockPanel.add(w, DockPanel.SOUTH);
		
	}
}
