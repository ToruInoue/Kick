package com.kissaki.client.procedure;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

import com.kissaki.client.KickController;
import com.kissaki.client.imageResource.Resources;
import com.kissaki.client.subFrame.debug.Debug;
import com.kissaki.client.userStatusController.userDataModel.ClientSideCurrentItemDataModel;
import com.kissaki.client.userStatusController.userDataModel.ClientSideCurrentTagDataModel;



/**
 * アイテム自体を表示する
 * @author ToruInoue
 *
 */
public class ItemDialogBox extends PopupPanel {
	Debug debug;
	
	private static final List<String> Tags = Arrays.asList(
	          "Like", "Want", "Have", "Had",
	          "Good","とか"
	     );
	
	private KickController kCont;
	
	Image image;
	TextBox newTagBox;
	
	private ClientSideCurrentItemDataModel m_myModel;
	private JSONObject m_userKey;
	VerticalPanel userTagPanel;//自分がつけたタグのパネル
	
	
	public ItemDialogBox (KickController kickCont, JSONObject userKey, ClientSideCurrentItemDataModel currentModel, JSONArray myTagArray, int x, int y) {
		debug = new Debug(this);
		
		this.kCont = kickCont;
		this.m_userKey = userKey;
		
		if (myTagArray != null) {
			debug.trace("myTagArrayが空ではなく、いろいろ有ります_"+myTagArray);
		}
		
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
		
		

		
		HorizontalPanel basePanel = new HorizontalPanel();//最大外部
		VerticalPanel vPanel = new VerticalPanel();
		basePanel.add(vPanel);
		
		userTagPanel = new VerticalPanel();
		basePanel.add(userTagPanel);
		userTagPanel.setVisible(true);
//		VerticalPanel otherUserTagPanel = new VerticalPanel();
//		basePanel.add(otherUserTagPanel);
		
		userTagPanel.add(new HTML("MyTag"));
		if (myTagArray != null) {
			for (int i = 0; i < myTagArray.size(); i++) {
				debug.trace("自分が付けたタグObject_"+myTagArray.get(i).isObject());
				
				JSONObject tagObject = myTagArray.get(i).isObject();
				debug.trace("マイタグのほう_"+tagObject);
				final TagButton b = new TagButton(tagObject);
				b.setText(URL.decode(tagObject.get("m_tagName").isString().toString()));
				b.addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						JSONObject buttonKey = new JSONObject();
						buttonKey.put("tagObject", b.getTagObject());
						buttonKey.put("itemKey", m_myModel.getItemKey());
						buttonKey.put("userKey", m_userKey);
						
						kCont.procedure("TagTapped+"+buttonKey);
					}
				});
				userTagPanel.add(b);
			}
		}
		
//		otherUserTagPanel.add(new HTML("全タグ"));
//		JSONArray tagArray = m_myModel.getTagArray();
//		/*
//		 * このアイテムについて、全員が付けたタグ
//		 */
//		for (int i = 0; i < tagArray.size(); i++) {
//			JSONObject tagObject = tagArray.get(i).isObject();
//			
//			debug.trace("他人タグの方_"+tagObject);//キーしかはいってない
//			final TagButton b = new TagButton(tagObject);
//			b.setText(URL.decode((tagObject.get("name").isString().toString())));
//			b.addClickHandler(new ClickHandler() {
//				
//				@Override
//				public void onClick(ClickEvent event) {
//					JSONObject buttonKey = new JSONObject();
//					buttonKey.put("tagObject", b.getTagObject());
//					buttonKey.put("itemKey", m_myModel.getItemKey());
//					buttonKey.put("userKey", m_userKey);
//					
//					kCont.procedure("TagTapped+"+buttonKey);
//				}
//			});
//			otherUserTagPanel.add(b);
//		}
		
		
		
		newTagBox = new TextBox();//新規タグを入力する欄
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
					}
				}
			}
		});
		
		TextCell textCell = new TextCell();
		CellList<String> cellList = new CellList<String>(textCell);
		final SingleSelectionModel<String> selectionModel = 
			new SingleSelectionModel<String>();
		cellList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {//描画してから出したいですね。
				
				Window.alert("Clicked! " + selectionModel.getSelectedObject());
			}
		});
		
		cellList.setRowData(1, Tags);//使ってないね。
		
		
		//アイテムのイメージをセット
		vPanel.add(image);
		
		//新規タグ用の内容をココに入れる
		vPanel.add(newTagBox);
		
		
		
		
		setWidget(basePanel);
		setPopupPosition(300, 280);
//		center();
		show();
		
	}

	/**
	 * 暫定のtagキーを作り出すところ
	 * @param tagName
	 * @return
	 */
	private String TagKeyGenerator(String tagName) {
		tagName = URL.encode(tagName);
		return tagName;//+"@"+m_myModel.getItemKeyName();
	}
	
	public void selfKill() {
		hide();
	}

	/**
	 * 自分のタグの表示/非表示をセットする
	 * @param show
	 */
	public void changeViewMode (boolean show) {
		if (show) {
			userTagPanel.setVisible(true);
		} else {
			userTagPanel.setVisible(false);
		}
	}
	
}
