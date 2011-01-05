package com.kissaki.client.login;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.TextBox;
import com.kissaki.client.KickController;
import com.kissaki.client.messengerSystem.MessengerSystem;
import com.kissaki.client.subFrame.debug.Debug;

public class TestTextEvent_Entered {
	Debug debug = null;
	KickController controller;
	MyLoginBox dialocController;
	int myIndex;
	
	public TestTextEvent_Entered () {
		debug = new Debug(this);

	}
	

	
	
	/**
	 * ダイアログの為の実装
	 * @param tArea
	 */
	public void setTextFieldToInputRegistration2(final TextBox tArea) {
		tArea.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				debug.trace("ChangeHandler_変化したみたい_"+event.toDebugString());
			}
		});
		
		tArea.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				// TODO Auto-generated method stub
				if (event.getNativeKeyCode() == 13) {
					//フォーカスを外す
					tArea.setFocus(false);
					debug.trace("inputted_2_"+tArea.getText());

					
					procedure2();
					//messenger.sendMessage(tArea.getText());
				}
			}

			

			
		});
	}

	/**
	 * 仮設のデリゲート
	 * @param kickController
	 */
	public void setDelegate(KickController kickController) {
		
		this.controller = kickController;
		
	}
	
	public void setProcedure (int myIndex) {
		this.myIndex = myIndex;
	}
	
	
	public void inputText (int kind, String s) {
		//メソッド名が一致している奴を、実行できればいい。やっぱメッセージングにしたくなるが、今はべた書き。
		switch (kind) {
		case 0:
			controller.inputUserName(s);
			break;
		case 1:
			controller.inputUserPass(s);
			break;
			
		default:
			break;
		}
		
	}

	
	
	
	
	
	/**
	 * ダイアログからのデリゲート
	 * @param myDialogBox
	 */
	public void setDelegate2(MyLoginBox myDialogBox) {
		dialocController = myDialogBox;
	}
	
	
	private void procedure2() {
		switch (myIndex) {
		case 0://nameの処理
			//なにもしない
			break;
		case 1:
			dialocController.login();
			break;
		default:
			break;
		}
	}

}
