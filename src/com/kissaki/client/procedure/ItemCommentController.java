package com.kissaki.client.procedure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.ui.Image;
import com.kissaki.client.KickController;
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
public class ItemCommentController {
	Debug debug;
	private final KickController kickCont;
	private final JSONObject itemKey;
	List<CommentDialogBox> commentDialogList;//コメントリストのダイアログの管理、ソート基準によっていろいろ変化しそう。
	
	static final int SORT_MODE_USER = 0;//ユーザー単位でのコメント まあ、、、こっちですよね。
	static final int SORT_MODE_ITEM = 1;//アイテム単位での一枚のコメント
	
	/**
	 * コンストラクタ
	 * @param itemKeyObject 
	 */
	public ItemCommentController (KickController kickCont, JSONObject userKey, JSONObject itemKeyObject) {
		debug = new Debug(this);
		
		commentDialogList = new ArrayList<CommentDialogBox>();//初期化
		this.kickCont = kickCont;
		this.itemKey = itemKeyObject;
	}

	
	
	/**
	 * 自分からの通信内容。おれ書き込んだよ、と。
	 * コメントを、マスターの掲示板に対して、内容、日時、筆記者について行う。
	 * @param currentMasterUserKey 
	 * @param commentMasterUserName
	 * @param currentCommentBody
	 * @param currentCommentDate
	 * @param currentCommentedByString
	 */
	public void addCommentFromMyself(JSONObject commentBlock) {
		
		JSONObject currentMasterUserKey = commentBlock.get("m_commentMasterID").isObject();
		String commentMasterUserName = currentMasterUserKey.get("name").isString().toString();
		debug.trace("commentMasterUserName_"+commentMasterUserName);
		
		String currentCommentBody = commentBlock.get("m_commentBody").isString().toString();
		String currentCommentDate = "適当";//commentBlock.get("commentDate").isString().toString();
		JSONObject currentCommentedBy = commentBlock.get("m_commentedBy").isObject();
		String currentCommentedByString = currentCommentedBy.get("name").isString().toString();
		
		String currentMyName = kickCont.getUStCont().getUserNameWithPassAroundDoubleQuart();
		debug.trace("currentMyName_"+currentMyName+"/commentMasterUserName_"+commentMasterUserName);
		//自分だったら
		if (currentMyName.equals(commentMasterUserName)) {//自分で自分のところに書き込んだ
			//自分から自分のボードに書き込んだ。yetが有れば、処理する。
			removeMyYetPanel(currentMyName);
			addUserBoardIfNeed(currentMasterUserKey,currentMyName);
			//自分のボードにコメントを書く
			addCommentToMyBoard(currentMyName, currentCommentBody, currentCommentDate, currentCommentedByString);
		} else {//他人だったら
			addUserBoardIfNeed(currentMasterUserKey, commentMasterUserName);
			//自分から他人のボードに書き込んだ
			addCommentToOtherUserBoard(commentMasterUserName, currentCommentBody, currentCommentDate, currentCommentedByString);
		}
			
			
	}
	

	/**
	 * 他人からのコメントをさばく
	 * @param commentBlock
	 */
	public void addCommentFromSomeone(JSONObject commentBlock) {
		JSONObject currentMasterUserKey = commentBlock.get("m_commentMasterID").isObject();
		String commentMasterUserName = currentMasterUserKey.get("name").isString().toString();
		debug.trace("commentMasterUserName_"+commentMasterUserName);
		
		String currentCommentBody = commentBlock.get("m_commentBody").isString().toString();
		String currentCommentDate = "適当";//commentBlock.get("commentDate").isString().toString();
		JSONObject currentCommentedBy = commentBlock.get("m_commentedBy").isObject();
		String currentCommentedByString = currentCommentedBy.get("name").isString().toString();
		
		String currentMyName = kickCont.getUStCont().getUserNameWithPassAroundDoubleQuart();

		//他人が自分のところに書き込んだ
		if (currentMyName.equals(commentMasterUserName)) {
			//すでに自分で自分に書いていて、ボードが存在しなければ書けない筈。なので、pop消しは行わない。
			debug.trace("他人が俺のボードに何をする！");
			
			addCommentToOtherUserBoard(commentMasterUserName, currentCommentBody, currentCommentDate, currentCommentedByString);
			
		} else {//他人が他人のところに書き込んだ(他人にとって他人自身かどうかはどうでもいい)
			//該当する他人の板をさがし、有れば上書き、無ければつくる。
			debug.trace("このマスターのボードを探す_"+commentMasterUserName);
			addUserBoardIfNeed(currentMasterUserKey, commentMasterUserName);
			//他人から他人のボードに書き込んだ
			addCommentToOtherUserBoard(commentMasterUserName, currentCommentBody, currentCommentDate, currentCommentedByString);
		}
		
		
	}	
	

	/**
	 * もし特定の名称のボードが存在していなければ、
	 * 足す。
	 * @param currentMasterUserKey 
	 * @param currentMyName
	 */
	private void addUserBoardIfNeed(JSONObject currentMasterUserKey, String boardMasterName) {
		if (commentDialogList.size() == 0) {//サイズが0だとitelに引っかかりようが無いので、作る。
			createNewBoard(currentMasterUserKey, boardMasterName);
			return;
		}
		
		//0ではないので、探索して有るかどうかチェック、無ければ追加する
		for (Iterator<CommentDialogBox> commentDialogItel = commentDialogList.iterator(); commentDialogItel.hasNext();) {
			CommentDialogBox currentDialogBox = commentDialogItel.next();
			debug.trace("currentDialogBox.getMasterUserNameWithPass()_"+currentDialogBox.getMasterUserNameWithPass()+"/boardMasterName_"+boardMasterName);
			if (currentDialogBox.getMasterUserNameWithPass().equals(boardMasterName)) {//すでにあるので、作成の必要が無い
				return;
			}
			
			if (!commentDialogItel.hasNext()) {//次が無いが、見つかっていない
				createNewBoard(currentMasterUserKey, boardMasterName);
				return;
			}
		}
	}

	/**
	 * 新規にボードを作成して足す
	 * @param currentMasterUserKey 
	 * @param boardMasterName
	 */
	private void createNewBoard(JSONObject currentMasterUserKey, String boardMasterName) {
		Image image = new Image();
		image.setUrl(Resources.INSTANCE.s1().getURL());
		
		commentDialogList.add(
				new CommentDialogBox(
						kickCont, 
						itemKey,
						currentMasterUserKey, 
						image, 
						CommentDialogBox.MODE_COMMENT,
						"")
				);
		showComments();
	}

	/**
	 * 他人のボードに自分がコメントを書く
	 * @param commentMasterUserName
	 * @param currentCommentBody
	 * @param currentCommentDate
	 * @param currentCommentedByString
	 */
	private void addCommentToOtherUserBoard(String commentMasterUserName,
			String currentCommentBody, String currentCommentDate,
			String currentCommentedByString) {
		for (Iterator<CommentDialogBox> commentDialogItel = commentDialogList.iterator(); commentDialogItel.hasNext();) {
			//リスト中で、マスターの名称が一致するものを探す
			CommentDialogBox currentCommentDialogBox = commentDialogItel.next();
			if (commentMasterUserName.equals(currentCommentDialogBox.getMasterUserNameWithPass())) {
				debug.trace("addCommentToOtherUserBoard_このボードの内容に、追記する。");
				currentCommentDialogBox.updateComment(currentCommentBody, currentCommentDate, currentCommentedByString);
				
				return;
			}
		}
		
	}

	/**
	 * 自分のボードに自分がコメントを書く
	 * @param currentMyName
	 * @param currentCommentBody
	 * @param currentCommentDate
	 * @param currentCommentedByString
	 */
	private void addCommentToMyBoard(String currentMyName,
			String currentCommentBody, String currentCommentDate,
			String currentCommentedByString) {
		
		for (Iterator<CommentDialogBox> commentDialogItel = commentDialogList.iterator(); commentDialogItel.hasNext();) {
			//リスト中で、マスターの名称が一致するものを探す
			CommentDialogBox currentCommentDialogBox = commentDialogItel.next();
			
			if (currentMyName.equals(currentCommentDialogBox.getMasterUserNameWithPass())) {
				debug.trace("addCommentToMyBoard_このボードの内容に、追記する。");
				currentCommentDialogBox.updateComment(currentCommentBody, currentCommentDate, currentCommentedByString);
				return;
			}
		}
		
	}

	/**
	 * 存在するコメント全てを表示する
	 */
	public void showComments () {
		int i = 0;
		for (Iterator<CommentDialogBox> commentDialogItel = commentDialogList.iterator(); commentDialogItel.hasNext(); i++) {
			
			CommentDialogBox currentCommentDialog = commentDialogItel.next();
			debug.trace(i+"_こいつを表示_"+currentCommentDialog.getMasterUserNameWithPass());
			
			currentCommentDialog.setPopupPosition(100+200*i, 100);
			
			currentCommentDialog.setHeight("400");
			currentCommentDialog.setWidth("1800");
			currentCommentDialog.show();
		}
	}


	/**
	 * 全コメントのリセットを行う
	 */
	public void eraseAllComment() {
		closeComments();
		removeAllPopUp();
	}
	

	/**
	 * ポップアップを削除する
	 */
	private void removeAllPopUp() {
		int num = commentDialogList.size();
		for (int i = 0; i < num; i++) {
			commentDialogList.remove(0);
		}
	}

	/**
	 * コメントを隠す
	 */
	private void closeComments() {
		for (Iterator<CommentDialogBox> commentDialogItel = commentDialogList.iterator(); commentDialogItel.hasNext();) {
			CommentDialogBox currentCommentDialog = commentDialogItel.next();
			currentCommentDialog.hide();
		}
	}
	
	

	
	
	final int MODE_POP_YET = 1;
	/**
	 * 自分用のポップがまだ無い筈なので、新規ポップを出す
	 */
	public void addMyCommentPopup() {
		
		String myNameWithPass = kickCont.getUStCont().getUserNameWithPassAroundDoubleQuart();
			
		for (Iterator<CommentDialogBox> dialogBoxItel = commentDialogList.iterator(); dialogBoxItel.hasNext();) {
			CommentDialogBox currentCommentDialogBox = dialogBoxItel.next();

			if (currentCommentDialogBox.isFirstMode()) {
				debug.trace("すでにpopが有るようなので、特に何もせず帰る");//自分のものしかpopを作らない、という前提が守られるのであれば、意味があるコード
				return;
			}
			debug.trace("currentCommentDialogBox.getMasterUserNameWithPass()_"+currentCommentDialogBox.getMasterUserNameWithPass());
			if (currentCommentDialogBox.getMasterUserNameWithPass().equals(myNameWithPass)) {//マスターが自分だったら、すでにあるので何もせず帰る
				debug.trace("すでに本物が有るようなので、特に何もせず帰る");//自分のものしかpopを作らない、という前提が守られるのであれば、意味があるコード
				return;
			}
		}
		
		debug.trace("addMyCommentPopup_"+kickCont.getUStCont().getUserNameWithPassAroundDoubleQuart());
		Image image = new Image();
		image.setUrl(Resources.INSTANCE.s2().getURL());
		commentDialogList.add(
			new CommentDialogBox(
					kickCont, 
					itemKey,
					kickCont.getUStCont().getUserKey(), //MasterKey
					image,
					CommentDialogBox.MODE_YET_COMMENT,
					"Kick here!")//+":"+currentCommentDate
			);
		showComments();
	}

	
	/**
	 * 特定ユーザーのYetダイアログを発見、削除する。
	 * @param userName
	 */
	public void removeMyYetPanel(String userName) {
		int num = commentDialogList.size();
		for (int i = 0; i < num; i++) {
			CommentDialogBox myCommentDialogBox = commentDialogList.get(i);
			if (myCommentDialogBox.getMasterUserNameWithPass().equals(userName)) {
				if (myCommentDialogBox.isFirstMode()) {
					debug.trace("removeMyYetPanel_発見したので消します_"+kickCont.getUStCont().getUserNameWithPassAroundDoubleQuart());
					myCommentDialogBox.hide();
					commentDialogList.remove(myCommentDialogBox);
					myCommentDialogBox = null;
					break;
				}
			}
		}
	}


}
