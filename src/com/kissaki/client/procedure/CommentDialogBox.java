package com.kissaki.client.procedure;


import com.google.gwt.dom.client.Text;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.kissaki.client.KickController;
import com.kissaki.client.KickStatusInterface;
import com.kissaki.client.MessengerGWTCore.MessengerGWTImplement;
import com.kissaki.client.MessengerGWTCore.MessengerGWTInterface;
import com.kissaki.client.subFrame.debug.Debug;

/**
 * アイテムについての自分と、自分へのコメントを表示するダイアログ
 * ユーザーごとにウインドウが用意される。
 * 
 * @author ToruInoue
 */
public class CommentDialogBox extends PopupPanel implements KickStatusInterface, MessengerGWTInterface {
	Debug debug;
	
	static final int INTERFACE_NUMBER_ENTER = 13;
	
	MessengerGWTImplement messenger;
	JSONObject userKey = null;
	
	final JSONObject m_masterUserKey;
	final Image m_userImage;
	final String m_comment;
	final JSONObject m_itemKey;
	int m_mode = -1;
	static final int MODE_YET_COMMENT = 0;
	static final int MODE_COMMENT = 1;
	final TextBox commentWindow;
	
	TextArea commentSpace;
	VerticalPanel tagVerticalPanel;
	
	/**
	 * コンストラクタ
	 * @param kickCont
	 * @param currentUserKey 
	 * @param userImage
	 * @param mode 
	 * @param commentSpace
	 */
	public CommentDialogBox (JSONObject itemKey, JSONObject masterUserKey, Image userImage, int mode, String comment) {
		debug = new Debug(this);
		
		debug.assertTrue(itemKey != null, "itemKeyがnull");
		debug.assertTrue(masterUserKey != null, "masterUserKeyがnull");
		debug.assertTrue(userImage != null, "userImageがnull");
		debug.assertTrue(comment != null, "commentがnull");
		
		messenger = new MessengerGWTImplement(KICK_COMMENTDIALOG, this);
		
		this.m_itemKey = itemKey;
		this.m_masterUserKey = masterUserKey;
		this.m_userImage = userImage;
		this.m_comment = comment;
		
		commentSpace = new TextArea();
		commentSpace.setPixelSize(180, 85);
		VerticalPanel panel = new VerticalPanel();
		panel.setBorderWidth(0);
		//透明とかは、CSS使えば出来そうね。
		HorizontalPanel hPanel = new HorizontalPanel();
		String name = masterUserKey.get("name").isString().toString();
		HTML t = new HTML(name);
		
		panel.add(hPanel);
		
		
		HorizontalPanel hUserPanel = new HorizontalPanel();
		tagVerticalPanel = new VerticalPanel();
		hUserPanel.add(userImage);//画像
		hUserPanel.add(t);//名前
		
		hUserPanel.add(tagVerticalPanel);//タグ
		
		hPanel.add(hUserPanel);
		
		
		
		commentSpace.setText(comment);//この部分、改変出来るのかな。出来るな。
		
		
		commentWindow = new TextBox();
//		commentWindow.setWidth("600");
//		commentWindow.setSize("180", "20");
		commentWindow.setMaxLength(140);
		commentWindow.setPixelSize(180, 25);
		commentWindow.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				messenger.call(KICK_COMMENTDIALOG, "CommentWindowWantUserKey");
			}
			
		});
		commentWindow.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == INTERFACE_NUMBER_ENTER) {
					debug.assertTrue(userKey != null, "userKey == null");
					
					//フォーカスを外す
					commentWindow.setFocus(false);
					
					JSONObject commentWithUserKey = new JSONObject();
					commentWithUserKey.put("comment", new JSONString(URL.encode(commentWindow.getText())));
					commentWithUserKey.put("userKey", userKey);//このウインドウに書き込んだ人
					commentWithUserKey.put("masterUserKey", m_masterUserKey);//このウインドウの主(Got from comment)
					commentWithUserKey.put("itemKey", m_itemKey);
					
					debug.trace("commentWithUserKey_"+commentWithUserKey);
//					kCont.procedure("InputYourText+"+commentWithUserKey.toString());
					messenger.call(KICK_CONTROLLER, "InputYourText", messenger.tagValue("InputYourText", commentWithUserKey));
				}
			}
		});
		
		this.m_mode = mode;
		
		switch (getM_mode()) {
			case MODE_YET_COMMENT://過去のコメントいちらんが存在しないので、出さない。
				commentWindow.setText(comment);
				panel.add(commentWindow);
				break;
		
			case MODE_COMMENT://過去のコメント一覧を表示する。
				panel.add(commentSpace);
				panel.add(commentWindow);
				break;
		}
		
		
		setWidget(panel);
		
	}

	public int getM_mode() {
		return m_mode;
	}



	/**
	 * コメントの本文、だれによって書かれたのかをアップデートする。
	 * 
	 * コメントが書いてあるマスターのIDをもとに、コメントボードの内容を取得する。
	 * アイテムとして更新が罹るが、コメント領域をアクティブに更新する。
	 * コメント領域は、DBを持たないという構造になる。
	 * @param currentCommentBody
	 * @param currentCommentDate
	 * @param currentCommentedBy
	 */
	public void updateComment(String currentCommentBody,
			String currentCommentDate, String currentCommentedBy) {
		debug.trace("currentCommentBody_"+currentCommentBody+"_currentCommentDate_"+currentCommentDate+"/currentCommentedBy_"+currentCommentedBy);
		int before = URL.decode(commentSpace.getText()).length();
		int length = URL.decode(currentCommentBody+" by "+currentCommentedBy).length();
		commentSpace.setText(URL.decode(commentSpace.getText()
				+ currentCommentBody+" by "+currentCommentedBy) +"\n");
		
		commentWindow.setText("");//空にする
		
//		commentSpace.setCursorPos(100);//意図と違う
		//commentSpace.setSelectionRange(before, length);//オートではスクロールしてくれませんね。
		
	}

	/**
	 * 
	 * @return
	 */
	public boolean isFirstMode() {
		switch (getM_mode()) {
			case MODE_COMMENT:
				return false;
			case MODE_YET_COMMENT:
				return true;
		}
		return false;
	}

	
	/**
	 * このボードのマスターの名称を取得する
	 *  
	 * @return
	 */
	public String getMasterUserNameWithPass () {
		String masterUserName = m_masterUserKey.get("name").isString().toString();
		return masterUserName;
	}

	
	/**
	 * タグのオブジェクトを表示する
	 * @param tagObject
	 */
	public void updateTag(JSONObject tagObject) {
		String tagText = tagObject.get("key").isObject().get("name").isString().toString();
		Button b = new Button();
		b.setText(tagText);
		
		//TODO 既に含んでいるかどうか、チェックしないと。コントローラーでチェックしてほしいよね。
		tagVerticalPanel.add(b);
	}

	@Override
	public void receiveCenter(String message) {
		String exec = messenger.getCommand(message);
		if (exec.equals("UserKeyFromControllerToCommentWindow")) {
			userKey = messenger.getValueForTag(message, "userKey").isObject();
		}
	}
	

}
