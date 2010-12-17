package com.kissaki.client.procedure;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.TextBox;

import com.kissaki.client.KickController;
import com.kissaki.client.imageResource.Resources;
import com.kissaki.client.subFrame.debug.Debug;
import com.kissaki.client.userStatusController.userDataModel.ClientSideCurrentItemDataModel;

/**
 * アイテム自体を表示する
 * @author ToruInoue
 *
 */
public class ItemDialogBox extends DialogBox {
	Debug debug;
	
	
	private KickController kCont;
	
	Image image;
	TextBox newTagBox;
	
	private ClientSideCurrentItemDataModel m_myModel;
	private JSONObject m_userKey;
	
	public ItemDialogBox (KickController kickCont, JSONObject userKey, ClientSideCurrentItemDataModel currentModel, int x, int y) {
		debug = new Debug(this);
		
		this.kCont = kickCont;
		this.m_userKey = userKey;
		
		image = new Image();
		image.setUrl(Resources.INSTANCE.s_arrow().getURL());//ここは、イメージをどうするか考え終わってないから固定ね。
		
		image.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				JSONObject itemKey = new JSONObject();
				try {
					itemKey.put("itemKey", m_myModel.getItemKey());
					itemKey.put("userKey", m_userKey);
				} catch (Exception e) {
					debug.trace("newTagBox.getText().length()_error_"+e);
				}
				
				kCont.procedure("ItemTapped+"+itemKey);
			}
		});
		
		m_myModel = currentModel;
		
		

		VerticalPanel panel = new VerticalPanel();
		panel.add(image);
		JSONArray tagArray = m_myModel.getTagArray();
		
		for (int i = 0; i < tagArray.size(); i++) {
			
			JSONObject tagKey = tagArray.get(i).isObject();
			
			debug.trace(i+"_タグ_"+tagKey);
			debug.trace("tagKey_name_まだ途中_"+tagKey.get("name").isString().toString());
			
//			この名称のボタンを作ってセットする
			Button b = new Button();//ハンドラとか付けない
			String buttonName = tagKey.get("name").isString().toString();
			b.setTitle(buttonName);
			panel.add(b);
		}
		
		//新規タグ用の内容をココに入れる

		newTagBox = new TextBox();//入力してエンターがおされたら云々
		//"TagUpload+"を出力するイベントを書く
		
		
		
		newTagBox.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == 13) {
					//フォーカスを外す
					newTagBox.setFocus(false);
					
					debug.trace("inputted_2_"+newTagBox.getText());
					String text = newTagBox.getText();
					
					debug.trace("m_myModel.getItemKey()_"+m_myModel.getItemKey());
					
					if (0 < text.length()) {
						//このアイテムのキーと、タグの内容を送る
						JSONObject itemKeyWithNewTag = new JSONObject();
						try {
							itemKeyWithNewTag.put("itemKey", m_myModel.getItemKey());
							JSONString tagKey = new JSONString(TagKeyGenerator(text));
							itemKeyWithNewTag.put("newTag", tagKey);
						} catch (Exception e) {
							debug.trace("newTagBox.getText().length()_error_"+e);
						}
						
						kCont.procedure("TagUpload+"+itemKeyWithNewTag);
//						newTagBox.removeFromParent();					
					}
				}
			}
		});
		panel.add(newTagBox);
		
		setWidget(panel);
		center();
		show();
		
	}

	/**
	 * 暫定のtagキーを作り出すところ
	 * @param tagName
	 * @return
	 */
	private String TagKeyGenerator(String tagName) {
		return tagName+"@"+m_myModel.getItemKeyName();
	}
	
	public void selfKill() {
		//newTagBox.re
		hide();
	}

}
