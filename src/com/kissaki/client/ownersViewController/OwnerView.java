package com.kissaki.client.ownersViewController;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.kissaki.client.subFrame.debug.Debug;

/**
 * ビュー自身となるクラス
 * 
 * コントローラから入力された物をただ表示する
 * @author ToruInoue
 *
 */
public class OwnerView {
	Debug debug;
	
	PopupPanel myBase;
	int indexNumber = 0;
	HTML userName;
	Image myImage;
	
	TextBox inputBox;
	
	HorizontalPanel hor;
	
	VerticalPanel baseVer;
	
	VerticalPanel commentVar;
	VerticalPanel tagVar;
	
	
	
	/**
	 * コンストラクタ
	 * ビューの驅体を作る
	 */
	public OwnerView (int indexNumber) {
		debug = new Debug(this);
		
		this.indexNumber = indexNumber;
		
		
		myBase = new PopupPanel();
//		myBase.setGlassEnabled(true);//え、透明になんの？ だったら超うれしいんだけど→バックにガラスですね、判ります。
		
		
		hor = new HorizontalPanel();
		baseVer = new VerticalPanel();
		
		
		commentVar = new VerticalPanel();
		tagVar = new VerticalPanel();
		
		hor.add(baseVer);//0
		hor.add(commentVar);//1
		hor.add(tagVar);//2
		
		myBase.setWidget(hor);
//		myBase.show();
		myBase.setVisible(false);
//		myBase.hide();
//		myBase.removeFromParent()、は使えるのか。
	}
	
	/**
	 * 与えられたデータを元に、書き出す。
	 * 出来る筈だ。
	 * @param currentOwnerModel
	 */
	public void draw(OwnerModel currentOwnerModel) {
		try {
		//ここからビューの構築を行えばいい。
		
		if (userName != null && userName.isAttached()) {
			userName.removeFromParent();
			userName = new HTML(currentOwnerModel.userName);
			baseVer.add(userName);
		} else {
			userName = new HTML(currentOwnerModel.userName);
			baseVer.add(userName);
		}
		
		
		if (myImage != null && myImage.isAttached()) {
			myImage.removeFromParent();
			myImage = currentOwnerModel.userImage;
			baseVer.add(currentOwnerModel.userImage);
		} else {
			myImage = currentOwnerModel.userImage;
			baseVer.add(myImage);
		}
		
		//コメントの追加
		int commentSize = commentVar.getWidgetCount();
		if (inputBox != null) {
			//特になにもしない
		} else {
			inputBox = currentOwnerModel.getInputBox();
		}
		for (int i = 0; i < commentSize; i++) {
			commentVar.remove(i);
		}
		for (int i = 0; i < currentOwnerModel.commentArray.size(); i++) {
			commentVar.add(new HTML(currentOwnerModel.commentArray.get(i)));
			commentVar.add(inputBox);
		}
		if (commentVar.getWidgetCount() == 0) commentVar.add(inputBox);
		
		
		//タグの追加
		int tagSize = tagVar.getWidgetCount();
		for (int i = 0; i < tagSize; i++) {
			tagVar.remove(i);
		}
		for (int i = 0; i < currentOwnerModel.tagArray.size(); i++) {
			tagVar.add(currentOwnerModel.buttonize(currentOwnerModel.tagArray.get(i)));
		}
		
		
		} catch (Exception e) {
			debug.trace("draw_error_"+e);
		}
	}

	
	public void show() {
		myBase.setPopupPosition(30+200*(indexNumber%4), 30+200*((indexNumber+3)/4));
		
		myBase.setVisible(true);//myBase.setVisible(false)と、この組み合わせなら、遅延で表示できる。
		myBase.show();
	}
	
	public  void hide() {
		myBase.setVisible(false);
	}
	

}
