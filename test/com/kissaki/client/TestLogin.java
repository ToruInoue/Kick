package com.kissaki.client;

import com.google.gwt.junit.client.GWTTestCase;
import com.kissaki.client.subFrame.debug.Debug;


/**
 * テストを書く。
 * 
 * ログインの挙動、ClientAppDelegateの状態から判断する。
 * JSON形式のシリアライザさがす、かなあ。うーん、
 * ちゃっちゃとやろう。
 * 
 * 資産化できてるのがある筈。
 * 、、、あ、EventBusがどうなったんだっけ、、、
 * 
 * いいや、今回はベタに組む。
 * 
 * ユーザーステータスを所持するクラスを作り、そのクラスの状態で遷移する。
 * ログインテストを行う。
 * @author ToruInoue
 *
 */
public class TestLogin 
//extends GWTTestCase //ゼロベースで書いてみるしかないね。
{
	Debug debug;
//	KickController kCont;
	
	public String getModuleName() {
		return "com.kissaki.Kick";
	}
	
	public void gwtSetUp () {
		debug = new Debug(this);
		debug.trace("LoginTestStart");
		
//		Kick kick = new Kick();
//		kick.onModuleLoad();
//		hello = new Hello();
//	    hello.onModuleLoad();
		//kCont = new KickController();
	}
	
	public void gwtTearDown () {
		
	}
	
	public void testTest () {
		debug.trace("通過");
	}
	
}
