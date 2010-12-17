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
	 * このアイテムに対して、コメント情報を追加する。
	 * 誰 > コメント　というふうに振り分けられる。
	 * @param commentBlock
	 */
	public void addComment(JSONObject commentBlock) {
		debug.trace("commentBlock_"+commentBlock);
		try {
//			{"m_commentMasterID":{"kind":"user", "id":0, "name":"aaaa@bbbb"}, "m_commentBody":"Kick here!s", "m_commentedBy":{"kind":"user", "id":0, "name":"aaaa@bbbb"}, "key":{"kind":"comment", "id":3}}

			//ユーザーキーの一致から、足すべきコメント先ダイアログを見つける
		JSONObject currentMasterUserKey = commentBlock.get("m_commentMasterID").isObject();
		String currentCommentBody = commentBlock.get("m_commentBody").isString().toString();
		String currentCommentDate = "適当";//commentBlock.get("commentDate").isString().toString();
		JSONObject currentCommentedBy = commentBlock.get("m_commentedBy").isObject();
		String currentCommentedByString = currentCommentedBy.toString();
		
		String masterName = currentMasterUserKey.get("name").isString().toString();
		
		
		debug.trace("更新_"+commentDialogList.size());
		
		
		//来たデータが他人の物なので、足す。
		String myName = kickCont.getUStCont().getUserKey().get("name").isString().toString();
		if (!masterName.equals(myName)) {
			debug.trace("自分以外のユーザーの書き込み版が飛んできた");
			debug.trace("このユーザーのコメントはまだ無いので、作る");
			//ゆっくりしていってね！
			Image image = new Image();
			image.setUrl(Resources.INSTANCE.s1().getURL());
			
			commentDialogList.add(
					new CommentDialogBox(
							kickCont, 
							itemKey,
							currentMasterUserKey, 
							image, 
							currentCommentBody+"@"+currentCommentedBy)//+":"+currentCommentDate
					);
			showComments();
			return;
		}
//		if (commentDialogList.size() == 0) {
//			Image image = new Image();
//			image.setUrl(Resources.INSTANCE.s1().getURL());
//			
//			commentDialogList.add(
//					new CommentDialogBox(
//							kickCont, 
//							itemKey,
//							currentMasterUserKey, 
//							image, 
//							currentCommentBody+"@"+currentCommentedBy)//+":"+currentCommentDate
//					);
//			showComments();
//		}//無いから作る、のではなく、零件でかつ、そのユーザーのものがないので出す。かつ、自分の奴を消す
		
		for (Iterator<CommentDialogBox> commentDialogItel = commentDialogList.iterator(); commentDialogItel.hasNext();) {
			CommentDialogBox currentCommentDialogBox = commentDialogItel.next();
			debug.trace("currentCommentDialogBox_"+currentCommentDialogBox.getM_userKey());
			debug.trace("currentMasterUserKey_"+currentMasterUserKey);
			
			if (currentCommentDialogBox.getM_userKey().toString().equals(currentMasterUserKey.toString())) {
				debug.trace("currentUserKeyが一致するダイアログがすでにある_"+currentMasterUserKey);
				//それが自分のidで、かつ新規モードのものだったら、作り直しを行う。
				if (currentMasterUserKey.get("name").toString().equals(myName) && currentCommentDialogBox.isFirstMode()) {
					debug.trace("自分の新規のやつでした");
					
					currentCommentDialogBox.hide();//ここが効いてて、でも、、ってことは、なにか混乱が有るんだ。
					commentDialogList.remove(currentCommentDialogBox);
					
					
					Image image = new Image();
					image.setUrl(Resources.INSTANCE.s1().getURL());
					
					commentDialogList.add(
							new CommentDialogBox(
									kickCont, 
									itemKey,
									currentMasterUserKey, 
									image, 
									currentCommentBody+"@"+currentCommentedBy)//+":"+currentCommentDate
							);
					return;
				}
				
				currentCommentDialogBox.updateComment(currentCommentBody, currentCommentDate, currentCommentedByString);
				return;
			}
			
//			if (!commentDialogItel.hasNext()) {
//				debug.trace("このユーザーのコメントはまだ無いので、作る");
//				//ゆっくりしていってね！
//				Image image = new Image();
//				image.setUrl(Resources.INSTANCE.s1().getURL());
//				
//				commentDialogList.add(
//						new CommentDialogBox(
//								kickCont, 
//								itemKey,
//								currentMasterUserKey, 
//								image, 
//								currentCommentBody+"@"+currentCommentedBy)//+":"+currentCommentDate
//						);
//				showComments();
//			}
		}
		} catch (Exception e) {
			debug.trace("addComment_"+e);
		}
	}
	
	/**
	 * 存在するコメント全てを表示する
	 */
	public void showComments () {
		int i = 0;
		for (Iterator<CommentDialogBox> commentDialogItel = commentDialogList.iterator(); commentDialogItel.hasNext(); i++) {
			
			CommentDialogBox currentCommentDialog = commentDialogItel.next();
			debug.trace(i+"_こいつを表示_"+currentCommentDialog.getM_userKey());
			
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
	
	

	public void testInitialize() {
		debug.trace("testInitialize");
		JSONObject obj = new JSONObject();
		obj.put("commentMaster", new JSONString("param_commentMaster"));
		obj.put("itemKey", new JSONString("param_itemKey"));
		
		obj.put("commentBody", new JSONString("param_commentBody"));
		obj.put("commentDate", new JSONString("param_commentDate"));
		obj.put("commentedBy", new JSONString("param_commentedBy"));
		
		addComment(obj);
	}

	/**
	 * 自分用の新規ポップを出す
	 */
	public void addMyCommentPopup() {
		Image image = new Image();
		image.setUrl(Resources.INSTANCE.s2().getURL());
		commentDialogList.add(
			new CommentDialogBox(
					kickCont, 
					itemKey,
					kickCont.getUStCont().getUserKey(), 
					image, 
					null)//+":"+currentCommentDate
			);
		showComments();
	}

	
	/**
	 * 自分のpopがすでにあるので、現在表示されている新規のpopを消す
	 */
	public void removeMyNewPop() {
//		String myName = kickCont.getUStCont().getUserKey().get("name").isString().toString();
//		JSONObject currentMasterUserKey = kickCont.getUStCont().getUserKey();
//		
//		
//		for (Iterator<CommentDialogBox> commentDialogItel = commentDialogList.iterator(); commentDialogItel.hasNext();) {
//			CommentDialogBox currentCommentDialogBox = commentDialogItel.next();
//			debug.trace("removeMyNewPop_currentCommentDialogBox_"+currentCommentDialogBox.getM_userKey());
//			debug.trace("test____自分の新規のやつでした_"+ currentCommentDialogBox.isFirstMode());
//			
//			if (currentCommentDialogBox.getM_userKey().toString().equals(currentMasterUserKey.toString())) {
//				String currentMasterName = currentMasterUserKey.get("name").toString();
//				debug.trace("removeMyNewPop_currentUserKeyが一致するダイアログがすでにある_"+currentMasterName);
//				debug.trace("removeMyNewPop_currentUserKeyが一致するダイアログがすでにある2_"+myName);
//				
//				//それが自分のidで、かつ新規モードのものだったら、作り直しを行う。
//				if (currentMasterName.equals(myName)) {//TODO　なぜか自分のキーを覚えてない。あるいは、もう消す必要がないのか。
//					debug.trace("removeMyNewPop_自分の新規のやつでした_"+ currentCommentDialogBox.isFirstMode());
////					currentCommentDialogBox.center();
//					currentCommentDialogBox.hide();
//					commentDialogList.remove(currentCommentDialogBox);
//					
//					
//				}
//			}
//		}			
	}
	
	
	
}
