package com.kissaki.client.procedure;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TextArea;
import com.kissaki.client.KickController;
import com.kissaki.client.subFrame.debug.Debug;



/**
 * 各ユーザーのインフォメーションを表示するダイアログと、それにコメントしようとしたときのハンドラを設定する
 * @author ToruInoue
 */
public class UserInformationViewController {
	Debug debug;
	
	private final KickController kickCont;
	
	private final String m_userName;
	private final Image m_userImage;
	private String m_userComment;//この人のコメント、他人が書き加える可能性がある
	private TextArea m_commentSpace;
	
	private CommentDialogBox m_commentDialog;
	
	public UserInformationViewController (KickController kickCont, String userName, String userImageSource, String userComment, int x, int y) {
		
		debug = new Debug(this);
		
		this.kickCont = kickCont;
		m_userName = userName;
		m_userImage = new Image();
		m_userImage.setUrl(userImageSource);//web上のもいけるのかな。
		
		m_userComment = userComment;//単純な物ではない筈。もっとどでかい。アレイ。
		
		m_commentSpace = new TextArea();
		m_commentSpace.setHeight("400");
		
		
		
		//ダイアログとして組む
		m_commentDialog = new CommentDialogBox(kickCont, m_userImage, m_commentSpace);
		m_commentDialog.show();
		m_commentDialog.setHeight("400");
		m_commentDialog.setPopupPosition(x, y);
		/*
		 * ユーザーの顔が有り、
		 * コメントが出ている状態、
		 * かつ位置は適当に、
		 * 四角くなるように。
		 * コメントを受け付けて、最下部にダイアログをだせるようにイベント通知を行う。
		 * タグも着く。
		 */
		
	}
	
	
	
}
