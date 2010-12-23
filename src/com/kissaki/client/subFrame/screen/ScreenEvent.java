package com.kissaki.client.subFrame.screen;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.ui.Widget;
import com.kissaki.client.subFrame.debug.Debug;



/**
 * スクリーンに届けられるイベント
 * 
 * ScreenControllerを直に呼ばないための処理を記述する。
 * @author sassembla
 *
 */
public class ScreenEvent extends GwtEvent<ScreenHandler> {
	public static final Type<ScreenHandler> TYPE = new Type<ScreenHandler>();//ハンドラを特定するタイプ表記

	Debug debug = null;
	Widget widget = null;//保持しているWidget
	
	int eventNum = -1;
	
	/**
	 * コンストラクタ
	 * @param s 
	 */
	public ScreenEvent (int num, Widget w) {
		debug = new Debug(this);
//		debug.trace("ScreenEvent_コンストラクタ");
		setWidget(w);
		setNum(num);
	}


	/**
	 * ゲッター
	 * @return the widget
	 */
	public Widget getWidget() {
		return widget;
	}


	/**
	 * セッター
	 * @param widget the widget to set
	 */
	public void setWidget(Widget widget) {
		this.widget = widget;
	}


	/**
	 * ナンバーのセット
	 * @param num
	 */
	public void setNum (int num) {
		eventNum = num;
	}
	
	/**
	 * ナンバーをゲット
	 * @return
	 */
	public int getNum () {
		return eventNum;
	}
	
	
	
	/**
	 * 実際の挙動
	 * 
	 * 定義した要素が、イベント適応しているオブジェクトに対して呼ばれる構造。
	 * リスナを積めば動く、という事ですね。資料にしよう。
	 */
	@Override
	protected void dispatch(ScreenHandler handler) {
		switch (getNum()) {
		case 0:
//			debug.trace("dispatch_"+0);
//			handler.doProcess(getWidget());
			break;
		
		case 1:
//			debug.trace("dispatch_"+1);//ここで駄目になってるから、handlerの処理か。ということは、受ける側のリセットが無いからか？
			handler.addToMain(getWidget());
			break;
			
		case 2:
//			debug.trace("dispatch_"+2);
			handler.addToSub(getWidget());
			break;
			
		default:
			break;
		}
	}


	/**
	 * 型を返す
	 */
	@Override
	public Type<ScreenHandler> getAssociatedType() {
		return TYPE;
	}
	
	
}
	