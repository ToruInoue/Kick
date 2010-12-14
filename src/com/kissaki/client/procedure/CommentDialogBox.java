package com.kissaki.client.procedure;

import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Image;
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
public class CommentDialogBox extends DialogBox {
	Debug debug;
	
	private final KickController kCont;
	
	final Image m_userImage;
	private final TextArea m_commentSpace;
	
	
	public CommentDialogBox (KickController kickCont, Image userImage, TextArea commentSpace) {
		debug = new Debug(this);
		
		this.kCont = kickCont;
		this.m_userImage = userImage;
		
		m_commentSpace = commentSpace;
		
		VerticalPanel panel = new VerticalPanel();
		panel.add(userImage);
		panel.add(commentSpace);
		
		setWidget(panel);
		
		
	}

}
