package com.kissaki.client;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.TextArea;
import com.kissaki.client.subFrame.debug.Debug;

public class WidgetTestPlace {
	Debug debug = null;
	public WidgetTestPlace () {
		debug = new Debug(this);
		
		
		final TextArea tArea = new TextArea();
		tArea.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				//eventで押された文字を入れる
				debug.trace("ClickHandler_触ったみたい_"+event.toDebugString());
			}
		});
		tArea.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				debug.trace("ChangeHandler_変化したみたい_"+event.toDebugString());
			}
		});
		
		tArea.addFocusHandler(new FocusHandler() {
			
			@Override
			public void onFocus(FocusEvent event) {
				// TODO Auto-generated method stub
//				注目するぜ
				debug.trace("FocusHandler_Focusしたみたい_"+event.toDebugString());
			}
		});
		
		tArea.addMouseOverHandler(new MouseOverHandler () {
			@Override
			public void onMouseOver(MouseOverEvent event) {
				debug.trace("MouseOverHandler_マウスがかぶったみたい_"+event.toDebugString());
			}
		});
		
		tArea.setVisible(false);//不可視にできる
		tArea.setVisible(true);//可視にできる
		
	}
}
