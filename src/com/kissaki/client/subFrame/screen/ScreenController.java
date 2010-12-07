package com.kissaki.client.subFrame.screen;



import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.kissaki.client.subFrame.debug.Debug;

/**
 * Duplicate予定：Ginでの実装に切り替える。 10/12/03 19:27:17
 * 
 * シングルトンを実装した、スクリーンコントローラ。
 * シングルトンが積まれているので、アクセスに関しては常にステートマシンのように扱うこと。
 * 
 * このクラスの役割：
 * 	先ず画面単位を扱うクラスが画面レイアウトをこのクラスをひな形として作成し、
 * 	このクラス内に対して位置指定のスクリーンを発生させる。
 * 	発生したスクリーンに対して、取得コマンドと削除コマンドなどを発行しながら、
 * 	画面の内容を保持するのが役割。
 * 	更新イベントをどうするかな。
 * 
 * 
 * Why シングルトン？：
 * 	RootLayputPanelがシングルトンのため、その処理を内包するこのクラスもシングルトンとしている。
 * 
 * スクリーンとは：
 * 	ここでは、Debug表示とメインのコンテンツ表示を同時に行うためのWidgetのレイアウト用に用意したWidgetの事。
 * 	レイアウトを一定の場所に特定する事＋クラスとして保持する事で煩わしさを解消している。
 * 
 * 
 * 
 * @author sassembla
 *
 */
public class ScreenController {

	Debug debug = null;
//	RootpanelPresentator panel = null;
//	PanelComposite_Dock panel = null;
	static PanelComposite_Simple panel = null;
	
	private static ScreenController screenController = new ScreenController();//シングルトン、ステートマシンとして扱う

	/**
	 * コンストラクタ
	 */
	private ScreenController() {
		debug = new Debug(this);
		debug.removeTraceSetting(Debug.DEBUG_EVENT_ON);//このクラスでは、デバッグをイベント表示しない。
		
		panel = new PanelComposite_Simple();
		RootLayoutPanel.get().add(panel);//要素をつけるならココなのか。
		
		
	}
	
	public static void removeAllElements () {
		int num = RootLayoutPanel.get().getWidgetCount();
		
		for (int i = 0; i < num; i++) {
			RootLayoutPanel.get().remove(0);//かなあ、、、
		}
		
		panel = new PanelComposite_Simple();
		RootLayoutPanel.get().add(panel);//要素をつけるならココなのか。
	}
	
	/**
	 * このクラスのインスタンスのゲッター
	 * @return the dScreen
	 */
	public static ScreenController getScreenController() {
		return screenController;
	}
	
	
	/**
	 * メインにセットする、、この書き方は出来ない訳だな。
	 * @param w
	 */
	public void setMainScreen (Widget w) {
		removeAllElements();
		panel.setToMainPanel(w);
	}
	
	public void setSubScreen (Widget w) {
//		panel = new PanelComposite_Simple();//初期化したら、どうなる？
		panel.setToSubPanel(w);	
	}
	
}
