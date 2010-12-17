package com.kissaki.client.procedure;


import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.kissaki.client.KickController;
import com.kissaki.client.subFrame.debug.Debug;

/**
 * アイテムについての自分と、自分へのコメントを表示するダイアログ
 * 
 * 
 * @author ToruInoue
 */
public class NoCommentDialogBox extends PopupPanel {
	Debug debug;
	
	private KickController kCont;
	
	String m_userKey;
	final Image m_userImage;
	final String m_comment;
	
	/**
	 * コンストラクタ
	 * @param kickCont
	 * @param currentUserKey 
	 * @param userImage
	 * @param commentSpace
	 */
	public NoCommentDialogBox (KickController kickCont, String currentUserKey, Image userImage, String comment) {
		debug = new Debug(this);
		
		this.kCont = kickCont;
		this.m_userImage = userImage;
		
		setM_userKey(currentUserKey);
		this.m_comment = comment;
		
		VerticalPanel panel = new VerticalPanel();
		panel.add(userImage);
		
		
		TextBox commentWindow = new TextBox();
		commentWindow.setWidth("600");
		
		
		
		
		panel.add(commentWindow);
		
		setWidget(panel);
		
	}

	public Object getM_userKey() {
		return this.m_userKey;
	}

	public void setM_userKey(String userKey) {
		this.m_userKey = userKey;
	}

	/**
	 * コメントの本文、だれによって書かれたのかをアップデートする。
	 * @param currentCommentBody
	 * @param currentCommentDate
	 * @param currentCommentedBy
	 */
	public void updateComment(String currentCommentBody,
			String currentCommentDate, String currentCommentedBy) {
		debug.trace("currentCommentBody_"+currentCommentBody+"_currentCommentDate_"+currentCommentDate+"/currentCommentedBy_"+currentCommentedBy);
	}

}
