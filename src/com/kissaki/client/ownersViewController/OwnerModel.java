package com.kissaki.client.ownersViewController;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.kissaki.client.Kick;
import com.kissaki.client.KickController;
import com.kissaki.client.KickStatusInterface;
import com.kissaki.client.MessengerGWTCore.MessengerGWTImplement;
import com.kissaki.client.MessengerGWTCore.MessengerGWTInterface;
import com.kissaki.client.imageResource.Resources;
import com.kissaki.client.subFrame.debug.Debug;

/**
 * ある、ないの山になる筈。
 * 
 * ユーザー名
 * ユーザーのイメージ番号
 * ユーザーのタグ
 * 
 * 
 * ユーザーのボードへのコメント
 * 		、、ここは、だれから来ているか、分解した方がいいのかな？
 * 		自分が誰か、で、不過視にしたほうがいいのかな？
 * 		←自分へのコメントなのは間違いないので、誰からのコメントか、を整理しておくといいのかもしれないが。
 * 		見えるかどうか、コントローラから指定出来ればいいや。
 * 
 * @author ToruInoue
 *
 */
public class OwnerModel implements KickStatusInterface, MessengerGWTInterface {
	Debug debug;
	
	MessengerGWTImplement messenger;

	TextBox inputBox;
	Button tagButton;
	
	public String userName;
	public int imageNumber;
	public Image userImage;

	public List<String> commentArray;
	public List<String> tagArray;
	
	
	static final int INTERFACE_NUMBER_ENTER = 13;
	
	
	/**
	 * コンストラクタ
	 * @param userName
	 */
	public OwnerModel (String userName) {
		debug = new Debug(this);
		
		messenger = new MessengerGWTImplement(KICK_OWNERMODEL, this);
		
		this.userName = userName;

		commentArray = new ArrayList<String>();
		tagArray = new ArrayList<String>();
	}
	
	
	public void addComment(String comment, String commentMaster) {
		commentArray.add(comment+":"+commentMaster);
	}
	
	public void setUserImage (int imageNumber) {
		userImage = new Image();
		userImage.setUrl(getImage(imageNumber));
		userImage.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
//				kCont.procedure("OwnerImageTapped+"+userName);
				messenger.call(KICK_CONTROLLER, "OwnerImageTapped", messenger.tagValue("userName", userName));
			}
		});
	}
	
	public void addTag(String tagName) {
		if (tagArray.contains(tagName)) {
			return;
		}
		tagArray.add(tagName);
	}
	
	
	
	/**
	 * イメージの選択(URLではなく、バカ持ち。だって面倒)
	 * @param m_imageNumber
	 * @return
	 */
	private String getImage(int m_imageNumber) {
		switch (m_imageNumber) {
		case 0:

			return Resources.INSTANCE.s1().getURL();
		case 1:

			return Resources.INSTANCE.s2().getURL();
		case 2:

			return Resources.INSTANCE.s3().getURL();
		case 3:

			return Resources.INSTANCE.s4().getURL();
		case 4:

			return Resources.INSTANCE.s5().getURL();
		case 5:

			return Resources.INSTANCE.s6().getURL();
		case 6:

			return Resources.INSTANCE.s7().getURL();
		case 7:

			return Resources.INSTANCE.s8().getURL();
		case 8:

			return Resources.INSTANCE.s9().getURL();

		default:
			break;
		}
		return null;
	}

	//こいつをコントローラに記述する、簡単な方法を希望。　そうでないと、どんどんコードが汚くなる。
	public Button buttonize(String string) {
		tagButton = new Button();
		tagButton.setText(string);
		debug.trace("ボタンつくられては、居ます。");
		tagButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				debug.trace("ボタン押されてる");
//				kCont.procedure("TagButtonTapped+"+userName);
				messenger.call(KICK_CONTROLLER, "TagButtonTapped", messenger.tagValue("userName", userName));
			}
		});
		
		return tagButton;
	}


	public TextBox getInputBox() {
		inputBox = new TextBox();
		inputBox.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == INTERFACE_NUMBER_ENTER) {
					//フォーカスを外す
					inputBox.setFocus(false);
					
					JSONObject commentWithUserKey = new JSONObject();
					commentWithUserKey.put("comment", new JSONString(URL.encode(inputBox.getText())));
					commentWithUserKey.put("masterUserName", new JSONString(userName));
					
//					kCont.procedure("CommentWritten+"+commentWithUserKey.toString());
					messenger.call(KICK_CONTROLLER, "CommentWritten", messenger.tagValue("commentWithUserKey", commentWithUserKey));
					inputBox.setText("");//空っぽ
				}
			}
		});
		return inputBox;
	}


	@Override
	public void receiveCenter(String message) {
		// TODO Auto-generated method stub
		
	}

	
	
}
