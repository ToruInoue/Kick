package com.kissaki.client.login;

import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.kissaki.client.KickController;
import com.kissaki.client.KickStatusInterface;
import com.kissaki.client.MessengerGWTCore.MessengerGWTImplement;
import com.kissaki.client.MessengerGWTCore.MessengerGWTInterface;
import com.kissaki.client.subFrame.debug.Debug;

/**
 * カスタマイズしたダイアログの実装
 * @author ToruInoue
 *
 */
public class MyLoginBox extends DialogBox implements KickStatusInterface, MessengerGWTInterface {
	Debug debug;
	final TextBox nameSpace = new TextBox();
	final TextBox passSpace = new TextBox();
	
	MessengerGWTImplement messenger;
	
	String loginItemURL = null;//ログイン時にアイテムが選択されている、そのアイテムのURL(設定済みかどうかは、判らない
	
	
	/**
	 * コンストラクタ
	 */
	public MyLoginBox(String loginItemURL){
		debug = new Debug(this);
		messenger = new MessengerGWTImplement(KICK_LOGINDIALOG, this);
		
		this.loginItemURL = loginItemURL;
		
		setText("☆Login_"+loginItemURL);
		
		VerticalPanel panel = new VerticalPanel();
		
		
		//テキストフィールドがあれば、その入力を受け取って云々する。
//		final TextArea tArea = new TextArea();
		
		//t.setTextFieldToInputRegistration(tArea);//便利になるようにデリゲート、みたいな構造が使えるかも。
		//t.setDelegate(this);
		//t.setProcedure(0);
		
		//reg.fireEvent(new ScreenEvent(1, tArea));
		//panel.add(tArea);
		
		

		//名前フィールドのデリゲート
		panel.add(new Label("name"));
		
		TestTextEvent_Entered t = new TestTextEvent_Entered();//イベントの属性付け、ハンドラ設定
		
		t.setTextFieldToInputRegistration2(nameSpace);
		t.setDelegate2(this);
		t.setProcedure(0);
		
		panel.add(nameSpace);
		
		
		
		//パスワードフィールドのデリゲート
		panel.add(new Label("pass"));
		
		
		TestTextEvent_Entered t2 = new TestTextEvent_Entered();//イベントの属性付け、ハンドラ設定
		
		t2.setTextFieldToInputRegistration2(passSpace);
		t2.setDelegate2(this);
		t2.setProcedure(1);
		
		panel.add(passSpace);
		
		
//		//ログインボタン(もしくはPassからのEnter)
//		Button closeButton = new Button("Login");
//		closeButton.addClickHandler(new ClickHandler(){
//			@Override
//			public void onClick(ClickEvent event) {
//				hide();
//			}
//		});
//		panel.add(closeButton);
		setWidget(panel);
	}

	/**
	 * ログインを行う
	 */
	public void login() {
		debug.trace("nameSpace_"+nameSpace);
		debug.trace("passSpace_"+passSpace);
		
		messenger.call(KICK_CONTROLLER, "loginWithURLPath", 
				messenger.tagValue("name", nameSpace.getText()), 
				messenger.tagValue("pass", passSpace.getText())
				);
		
		this.hide();
	}

	@Override
	public void receiveCenter(String message) {
		// TODO Auto-generated method stub
		
	}
	
	
}