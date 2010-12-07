package com.kissaki.client.login;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.kissaki.client.KickController;
import com.kissaki.client.messengerSystem.MessengerSystem;
import com.kissaki.client.subFrame.debug.Debug;

public class TestTextEvent_Entered {
	Debug debug = null;
	KickController controller;
	MyDialogBox dialocController;
	int myIndex;
	
	public TestTextEvent_Entered () {
		debug = new Debug(this);
		MessengerSystem messenger = new MessengerSystem("テキストフィールド");
	}
		
	/**
	 * このメソッド内で、iPhoneっぽいUIを実現するための手段をこなす。
	 * ロジックはあくまでも我流。
	 * 
	 * テキストフィールドについて、Enterが押されたら、Enterキーを受け入れてフォーカスを外す。
	 * その際、イベントを投げる。
	 * リスナを実装している対象へと、イベントが届く、というようにしたい。
	 * 
	 * @param tArea
	 */
	void setTextFieldToInputRegistration (final TextArea tArea) {
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
					debug.trace("inputted_"+tArea.getText());

					//入力された文字を送り込む
					inputText(myIndex,tArea.getText());
					
					procedure();
					//messenger.sendMessage(tArea.getText());
				}
			}
		});
		
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

					//入力された文字を送り込む
					inputText2(myIndex,tArea.getText());
					
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
	
	
	public void procedure () {
		debug.assertTrue(controller != null, "procedure実行時にdelegateが設定されていない");
		controller.procedure("テストなので実際には使用しません");
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
	public void setDelegate2(MyDialogBox myDialogBox) {
		dialocController = myDialogBox;
	}
	
	private void inputText2(int myIndex, String text) {
		// TODO Auto-generated method stub
		
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
