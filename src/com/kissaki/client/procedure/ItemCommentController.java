package com.kissaki.client.procedure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.mortbay.util.ajax.JSON;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.ui.Image;
import com.kissaki.client.KickController;
import com.kissaki.client.KickStatusInterface;
import com.kissaki.client.MessengerGWTCore.MessengerGWTImplement;
import com.kissaki.client.MessengerGWTCore.MessengerGWTInterface;
import com.kissaki.client.imageResource.Resources;
import com.kissaki.client.subFrame.debug.Debug;

/**
 * アイテムに対してのユーザーカウントの保存、コメントの更新先の交通整理を行う。
 * 
 * コメントダイアログの統括管理を行うクラス。
 * 
 * @author ToruInoue
 *
 */
public class ItemCommentController implements KickStatusInterface {
//	Debug debug;
//	
//	MessengerGWTImplement messenger;
//	String userNameWithPass;
//	
//	
//	private final JSONObject m_itemKey;
//	List<CommentDialogBox> commentDialogList;//コメントリストのダイアログの管理、ソート基準によっていろいろ変化しそう。
//	
//	static final int SORT_MODE_USER = 0;//ユーザー単位でのコメント まあ、、、こっちですよね。
//	static final int SORT_MODE_ITEM = 1;//アイテム単位での一枚のコメント
//	
//	/**
//	 * コンストラクタ
//	 * @param itemKeyObject 
//	 */
//	public ItemCommentController (JSONObject userKey, JSONObject itemKeyObject) {
//		debug = new Debug(this);
//		
//		commentDialogList = new ArrayList<CommentDialogBox>();//初期化
//		
//		debug.assertTrue(itemKeyObject != null, "itemKeyObjectがnull");
//		
//		this.m_itemKey = itemKeyObject;
//	}
//
//	/*
//	 * ユーザーのタグ情報が取得出来たので、ボードに足す
//	 * ボードが無ければ無視する
//	 * 
//	 * 教訓としては、ユーザーごとのデータ取得の斬り分けをどれだけフラットに保てるか、という勝負な訳だ。
//	 * ビューをどれだけコントローラと分けられるか。レイヤー化できるか。
//	 * あと、メッセージングすげえ。
//	 */
//	public void addTagForUser (JSONObject tagOwnerObject, JSONObject tagObject) {
//		//currentMasterUserKey
//		//userOwnTagArray
//		for (Iterator<CommentDialogBox> commentDialogItel = commentDialogList.iterator(); commentDialogItel.hasNext();) {
//			//リスト中で、マスターの名称が一致するものを探す
//			CommentDialogBox currentCommentDialogBox = commentDialogItel.next();
//			String tagOwnerObjectNameString = tagOwnerObject.get("key").isObject().get("name").isString().toString();
//			
//			if (tagOwnerObjectNameString.equals(currentCommentDialogBox.getMasterUserNameWithPass())) {
//				debug.trace("addCommentToMyBoard_このボードの内容に、追記する。");
//				debug.trace("この人のタグをこの人のボードに足す");
//				currentCommentDialogBox.updateTag(tagObject);
//				
//				return;
//			}
//		}
//	}
//	
//	/**
//	 * 自分からの通信内容。おれ書き込んだよ、と。
//	 * コメントを、マスターの掲示板に対して、内容、日時、筆記者について行う。
//	 * @param currentMasterUserKey 
//	 * @param commentMasterUserName
//	 * @param currentCommentBody
//	 * @param currentCommentDate
//	 * @param currentCommentedByString
//	 */
//	public void addCommentFromMyself(JSONObject commentBlock) {
//		debug.trace("addCommentFromMyself_commentBlock_"+commentBlock);
//		
//		JSONObject rootObject = commentBlock.get("wholeCommentData").isObject();
//		JSONObject currentMasterUserKey = rootObject.get("m_commentMasterID").isObject();
//		String commentMasterUserName = currentMasterUserKey.get("name").isString().toString();
//		
//		
//		String currentCommentBody = rootObject.get("m_commentBody").isString().toString();
//		String currentCommentDate = "適当";//commentBlock.get("commentDate").isString().toString();
//		JSONObject currentCommentedBy = rootObject.get("m_commentedBy").isObject();
//		String currentCommentedByString = currentCommentedBy.get("name").isString().toString();
//		
//		debug.assertTrue(userNameWithPass != null, "userNameWithPass = null");
//		String currentMyName = userNameWithPass;
//		debug.trace("currentMyName_"+currentMyName+"	/commentMasterUserName_"+commentMasterUserName);
//		
//		JSONNumber numberObject = commentBlock.get("userImageNumber").isNumber();
//		
//		int imageNumber = (int)numberObject.doubleValue();
//		debug.trace("addCommentFromMyself_numberObject_"+numberObject);
//		
//		debug.assertTrue(currentMyName != null, "currentMyNameがnull");
//		debug.assertTrue(currentMasterUserKey != null, "currentMasterUserKeyがnull");
//		debug.assertTrue(currentCommentBody != null, "currentCommentBodyがnull");
//		debug.assertTrue(currentCommentDate != null, "currentCommentDateがnull");
//		debug.assertTrue(currentCommentedByString != null, "currentCommentedByStringがnull");
//		
//		
//		
//		//自分だったら
//		if (currentMyName.equals(commentMasterUserName)) {//自分で自分のところに書き込んだ
//			//自分から自分のボードに書き込んだ。yetが有れば、処理する。
//			removeMyYetPanel(currentMyName);
//			addUserBoardIfNeed(currentMasterUserKey,currentMyName, imageNumber);
//			//自分のボードにコメントを書く
//			addCommentToMyBoard(currentMyName, currentCommentBody, currentCommentDate, currentCommentedByString);
//		} else {//他人だったら
//			addUserBoardIfNeed(currentMasterUserKey, commentMasterUserName, imageNumber);
//			//自分から他人のボードに書き込んだ
//			addCommentToOtherUserBoard(commentMasterUserName, currentCommentBody, currentCommentDate, currentCommentedByString);
//		}
//			
//			
//	}
//	
//
//	/**
//	 * 他人からのコメントをさばく
//	 * @param commentBlock
//	 */
//	public void addCommentFromSomeone(JSONObject commentBlock) {
//		/*
//		 * commentBlock_{"userImageNumber":2, "wholeCommentData":{"m_commentMasterID":{"kind":"user", "id":0, "name":"bbbb@bbbb"}, "m_commentBody":"Kick%20here!", "m_commentedBy":{"kind":"user", "id":0, "name":"bbbb@bbbb"}, "key":{"kind":"comment", "id":2}}}
//			class com.kissaki.client.KickController:SomeCommentGet_error_java.lang.NullPointerException
//		 */
//		debug.trace(kickCont.getUStCont().getUserName()+"_@_addCommentFromSomeone_commentBlock_"+commentBlock);
//		JSONObject rootObject = commentBlock.get("wholeCommentData").isObject();
//		
//		JSONObject currentMasterUserKey = rootObject.get("m_commentMasterID").isObject();
//		String commentMasterUserName = currentMasterUserKey.get("name").isString().toString();
//		String currentCommentBody = rootObject.get("m_commentBody").isString().toString();
//		
//		String currentCommentDate = "適当";//commentBlock.get("commentDate").isString().toString();
//		JSONObject currentCommentedBy = rootObject.get("m_commentedBy").isObject();
//		String currentCommentedByString = currentCommentedBy.get("name").isString().toString();
//		String currentMyName = kickCont.getUStCont().getUserNameWithPassAroundDoubleQuart();
//		
//		int imageNumber = -1;
//		try {
//			debug.trace(kickCont.getUStCont().getUserName()+"_@_rootObject_"+rootObject);
//			JSONNumber imageNumberObject = commentBlock.get("userImageNumber").isNumber();
//			imageNumber = (int)imageNumberObject.doubleValue();
//			
//		} catch (Exception e) {
//			debug.trace("画像の数字を得る部分でのerror_"+e);
//		}
//		
//		//他人が自分のところに書き込んだ
//		if (currentMyName.equals(commentMasterUserName)) {
//			//すでに自分で自分に書いていて、ボードが存在しなければ書けない筈。なので、pop消しは行わない。
//			
//			addCommentToOtherUserBoard(commentMasterUserName, currentCommentBody, currentCommentDate, currentCommentedByString);
//			
//		} else {//他人が他人のところに書き込んだ(他人にとって他人自身かどうかはどうでもいい)
//			//該当する他人の板をさがし、有れば上書き、無ければつくる。
//			addUserBoardIfNeed(currentMasterUserKey, commentMasterUserName, imageNumber);
//			//他人から他人のボードに書き込んだ
//			addCommentToOtherUserBoard(commentMasterUserName, currentCommentBody, currentCommentDate, currentCommentedByString);
//		}
//		
//		
//	}	
//	
//
//	/**
//	 * もし特定の名称のボードが存在していなければ、
//	 * 足す。
//	 * @param currentMasterUserKey 
//	 * @param currentMyName
//	 */
//	private void addUserBoardIfNeed(JSONObject currentMasterUserKey, String boardMasterName, int imageNumber) {
//		if (commentDialogList.size() == 0) {//サイズが0だとitelに引っかかりようが無いので、作る。
//			createNewBoard(currentMasterUserKey, boardMasterName, imageNumber);
//			return;
//		}
//		
//		//0ではないので、探索して有るかどうかチェック、無ければ追加する
//		for (Iterator<CommentDialogBox> commentDialogItel = commentDialogList.iterator(); commentDialogItel.hasNext();) {
//			CommentDialogBox currentDialogBox = commentDialogItel.next();
//			debug.trace("currentDialogBox.getMasterUserNameWithPass()_"+currentDialogBox.getMasterUserNameWithPass()+"/boardMasterName_"+boardMasterName);
//			if (currentDialogBox.getMasterUserNameWithPass().equals(boardMasterName)) {//すでにあるので、作成の必要が無い
//				return;
//			}
//			
//			if (!commentDialogItel.hasNext()) {//次が無いが、見つかっていない
//				createNewBoard(currentMasterUserKey, boardMasterName, imageNumber);
//				return;
//			}
//		}
//	}
//
//	/**
//	 * 新規にボードを作成して足す
//	 * @param currentMasterUserKey 
//	 * @param boardMasterName
//	 */
//	private void createNewBoard(JSONObject currentMasterUserKey, String boardMasterName, int imageNumber) {
//		Image image = new Image();
//		image.setUrl(getImage(imageNumber));
//		
//		commentDialogList.add(
//				new CommentDialogBox(
//						kickCont, 
//						m_itemKey,
//						currentMasterUserKey, 
//						image, 
//						CommentDialogBox.MODE_COMMENT,
//						"")
//				);
//		showComments();
//	}
//
//	/**
//	 * 他人のボードに自分がコメントを書く
//	 * @param commentMasterUserName
//	 * @param currentCommentBody
//	 * @param currentCommentDate
//	 * @param currentCommentedByString
//	 */
//	private void addCommentToOtherUserBoard(String commentMasterUserName,
//			String currentCommentBody, String currentCommentDate,
//			String currentCommentedByString) {
//		for (Iterator<CommentDialogBox> commentDialogItel = commentDialogList.iterator(); commentDialogItel.hasNext();) {
//			//リスト中で、マスターの名称が一致するものを探す
//			CommentDialogBox currentCommentDialogBox = commentDialogItel.next();
//			if (commentMasterUserName.equals(currentCommentDialogBox.getMasterUserNameWithPass())) {
//				debug.trace("addCommentToOtherUserBoard_このボードの内容に、追記する。");
//				currentCommentDialogBox.updateComment(currentCommentBody, currentCommentDate, currentCommentedByString);
//				
//				return;
//			}
//		}
//		
//	}
//
//	/**
//	 * 自分のボードに自分がコメントを書く
//	 * @param currentMyName
//	 * @param currentCommentBody
//	 * @param currentCommentDate
//	 * @param currentCommentedByString
//	 */
//	private void addCommentToMyBoard(String currentMyName,
//			String currentCommentBody, String currentCommentDate,
//			String currentCommentedByString) {
//		
//		for (Iterator<CommentDialogBox> commentDialogItel = commentDialogList.iterator(); commentDialogItel.hasNext();) {
//			//リスト中で、マスターの名称が一致するものを探す
//			CommentDialogBox currentCommentDialogBox = commentDialogItel.next();
//			
//			if (currentMyName.equals(currentCommentDialogBox.getMasterUserNameWithPass())) {
//				debug.trace("addCommentToMyBoard_このボードの内容に、追記する。");
//				
//				
//				currentCommentDialogBox.updateComment(currentCommentBody, currentCommentDate, currentCommentedByString);
//				return;
//			}
//		}
//		
//	}
//
//	/**
//	 * 存在するコメント全てを表示する
//	 */
//	public void showComments () {
//		int i = 0;
//		for (Iterator<CommentDialogBox> commentDialogItel = commentDialogList.iterator(); commentDialogItel.hasNext(); i++) {
//			
//			CommentDialogBox currentCommentDialog = commentDialogItel.next();
//			debug.trace(i+"_こいつを表示_"+currentCommentDialog.getMasterUserNameWithPass());
//			
//			currentCommentDialog.setPopupPosition(positionMap[i*2], positionMap[i*2+1]);
//			
//			currentCommentDialog.show();
//			if (i == 7) {
//				debug.trace("サイズオーバーなので逃げる");
//				break;
//			}
//		}
//	}
//
//	int positionMap [] = {
//			50,100,
//			255,80,
//			460,80,
//			665,110,
//			
//			20,310,
//			220,610,
//			425,610,
//			630,315,
//	};
//	
//
//	/**
//	 * 全コメントのリセットを行う
//	 */
//	public void eraseAllComment() {
//		closeComments();
//		removeAllPopUp();
//	}
//	
//
//	/**
//	 * ポップアップを削除する
//	 */
//	private void removeAllPopUp() {
//		int num = commentDialogList.size();
//		for (int i = 0; i < num; i++) {
//			commentDialogList.remove(0);
//		}
//	}
//
//	/**
//	 * コメントを隠す
//	 */
//	private void closeComments() {
//		for (Iterator<CommentDialogBox> commentDialogItel = commentDialogList.iterator(); commentDialogItel.hasNext();) {
//			CommentDialogBox currentCommentDialog = commentDialogItel.next();
//			currentCommentDialog.hide();
//		}
//	}
//	
//	
//
//	
//	
//	final int MODE_POP_YET = 1;
//	/**
//	 * 自分用のポップがまだ無い筈なので、新規ポップを出す
//	 */
//	public void addMyCommentPopup() {
//		
//		String myNameWithPass = kickCont.getUStCont().getUserNameWithPassAroundDoubleQuart();
//			
//		for (Iterator<CommentDialogBox> dialogBoxItel = commentDialogList.iterator(); dialogBoxItel.hasNext();) {
//			CommentDialogBox currentCommentDialogBox = dialogBoxItel.next();
//
//			if (currentCommentDialogBox.isFirstMode()) {
//				debug.trace("すでにpopが有るようなので、特に何もせず帰る");//自分のものしかpopを作らない、という前提が守られるのであれば、意味があるコード
//				return;
//			}
//			debug.trace("currentCommentDialogBox.getMasterUserNameWithPass()_"+currentCommentDialogBox.getMasterUserNameWithPass());
//			if (currentCommentDialogBox.getMasterUserNameWithPass().equals(myNameWithPass)) {//マスターが自分だったら、すでにあるので何もせず帰る
//				debug.trace("すでに本物が有るようなので、特に何もせず帰る");//自分のものしかpopを作らない、という前提が守られるのであれば、意味があるコード
//				return;
//			}
//		}
//		
//		debug.trace("addMyCommentPopup_"+kickCont.getUStCont().getUserNameWithPassAroundDoubleQuart());
//		
//		Image image = new Image();
//		image.setUrl(getImage(kickCont.getUStCont().getM_imageNumber()));
//		
//		commentDialogList.add(
//			new CommentDialogBox(
//					kickCont, 
//					m_itemKey,
//					kickCont.getUStCont().getUserKey(), //MasterKey
//					image,
//					CommentDialogBox.MODE_YET_COMMENT,
//					"Kick here!")//+":"+currentCommentDate
//			);
//		showComments();
//	}
//
//	/**
//	 * イメージの選択(URLではなく、バカ持ち。だって面倒)
//	 * @param m_imageNumber
//	 * @return
//	 */
//	private String getImage(int m_imageNumber) {
//		switch (m_imageNumber) {
//		case 0:
//
//			return Resources.INSTANCE.s1().getURL();
//		case 1:
//
//			return Resources.INSTANCE.s2().getURL();
//		case 2:
//
//			return Resources.INSTANCE.s3().getURL();
//		case 3:
//
//			return Resources.INSTANCE.s4().getURL();
//		case 4:
//
//			return Resources.INSTANCE.s5().getURL();
//		case 5:
//
//			return Resources.INSTANCE.s6().getURL();
//		case 6:
//
//			return Resources.INSTANCE.s7().getURL();
//		case 7:
//
//			return Resources.INSTANCE.s8().getURL();
//		case 8:
//
//			return Resources.INSTANCE.s9().getURL();
//
//		default:
//			break;
//		}
//		return null;
//	}
//
//
//
//	/**
//	 * 特定ユーザーのYetダイアログを発見、削除する。
//	 * @param userName
//	 */
//	public void removeMyYetPanel(String userName) {
//		int num = commentDialogList.size();
//		for (int i = 0; i < num; i++) {
//			CommentDialogBox myCommentDialogBox = commentDialogList.get(i);
//			if (myCommentDialogBox.getMasterUserNameWithPass().equals(userName)) {
//				if (myCommentDialogBox.isFirstMode()) {
//					debug.trace("removeMyYetPanel_発見したので消します_"+kickCont.getUStCont().getUserNameWithPassAroundDoubleQuart());
//					myCommentDialogBox.hide();
//					commentDialogList.remove(myCommentDialogBox);
//					myCommentDialogBox = null;
//					break;
//				}
//			}
//		}
//	}
//
//	@Override
//	public void receiveCenter(String message) {
//		String exec = messenger.getCommand(message);
//		if (exec.equals("usernameFromController")) {
//			userNameWithPass = messenger.getValueForTag(message, "");
//		}
//	}


}
