package com.kissaki.client.PushController;

import java.util.List;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.kissaki.client.KickController;
import com.kissaki.client.subFrame.debug.Debug;
import com.kissaki.client.userStatusController.userDataModel.ClientSideCurrentItemDataModel;
import com.kissaki.client.userStatusController.userDataModel.ClientSideRequestQueueModel;

public class PushController {
	Debug debug;
	KickController kCont;
	
	
	public PushController (KickController kCont) {
		debug = new Debug(this);
		this.kCont = kCont;
	}
	
	
	/**
	 * プッシュを受けての挙動を行う
	 * @param exec
	 */
	public void pushedExecute(String exec) {
		String data = exec.substring("push+".length(), exec.length());
		
//		debug.trace("解析スタート");
		
		JSONObject root = null;
		JSONString command = null;
		JSONString value = null;
		
		String commandString = null;
		
		if (JSONParser.parseStrict(data).isObject() != null) {
//			debug.trace("root作成");
			root = JSONParser.parseStrict(data).isObject();
		}
		
		try {
			if (root != null) {
//				debug.trace("rootがある_"+root);
				command = root.get("command").isString();
			}
		} catch (Exception e) {
			debug.trace("error?_"+e);
		}
		
		if (command != null) {
			debug.trace("Pushで到達したcommand_"+command);
			commandString = command.toString();
		}
		
		debug.assertTrue(commandString != null, "commandStringがnullです");
		
		
		if (commandString.contains(ClientSideRequestQueueModel.GET_ALL_USER_WHO_HAVE_THIS_ITEM)) {
			if (kCont.isMyself(root, kCont.getUStCont().getUserKey())) {
				debug.trace("要求していた全所持者リストが到着");
				kCont.procedure(ClientSideRequestQueueModel.GET_ALL_USER_WHO_HAVE_THIS_ITEM+root);
			}
		}
		if (commandString.contains("CURRENT_USER_DATA")) {
			if (kCont.isMyself(root, kCont.getUStCont().getUserKey())) {
				debug.trace("最新のUserDataを取得_"+root);
				kCont.procedure("UserDataUpdated");
			}
		}
		
		/*
		 * 探したアイテムが取得出来た
		 */
		if (commandString.contains("ITEM_FOUND")) {
			if (kCont.isMyself(root, kCont.getUStCont().getUserKey())) {
				debug.trace("アイテムが見つかったよ_"+root);
				kCont.procedure("ItemFound+"+root);
			}
		}
		
		if (commandString.contains("NO_COMMENT")) {
			if (kCont.isMyself(root, kCont.getUStCont().getUserKey())) {
				debug.trace("コメントデータが一件も無い_"+commandString);
				debug.assertTrue(kCont.isMyself(root, kCont.getUStCont().getUserKey()), "自分しかいないので自分宛");
				
				//一件も存在しないので、自分のpopを出す
				kCont.procedure("NoComment+"+commandString);
			} else {
				debug.trace("他人で、コメントデータが無いやつが来た_"+kCont.getUserNameFromUserKey(root));
			}
		}
		
		if (commandString.contains("THERE_IS_MY_COMMENT")) {
			if (kCont.isMyself(root, kCont.getUStCont().getUserKey())) {
				debug.trace("自分のコメント、あります。");
			} else {
				debug.trace("他人のコメント、あります。_"+kCont.getUserNameFromUserKey(root));
			}
		}
		
		if (commandString.contains("NO_MY_DATA")) {
			if (kCont.isMyself(root, kCont.getUStCont().getUserKey())) {
				debug.trace("NO_MY_DATA_このアイテムに関しての自分のコメントデータが無い_"+commandString);
				kCont.procedure("NoComment+"+commandString);
			} else {
				debug.trace("NO_MY_DATA_このアイテムに関してのとある人自身のコメントデータが無いというイベントが他人のところで発生した_"+commandString);
//				kCont.procedure("SomeoneNoComment+"+commandString);
			}
		}

		if (commandString.contains("COMMENT_SAVED")) {
			if (kCont.isMyself(root, kCont.getUStCont().getUserKey())) {
				debug.trace("自分のコメントデータが保存出来た_"+commandString);
				JSONObject itemKey = root.get("requested").isObject();
				kCont.procedure("CommentSaved+"+itemKey);
			} else {
				debug.trace("他人のコメントデータが保存出来た");
			}
		}
		
		/**
		 * 
		 */
		if (commandString.contains(ClientSideRequestQueueModel.REQUEST_TYPE_GET_USER_INDIVIDUAL_TAG)) {
			if (kCont.isMyself(root, kCont.getUStCont().getUserKey())) {
				debug.trace("リクエストしたアイテムについて、タグのシャワーが降り注ぐ_"+root);
				kCont.procedure(ClientSideRequestQueueModel.REQUEST_TYPE_GET_USER_INDIVIDUAL_TAG+root);
			}
		}
		
		/*
		 * 最新コメントの受け取り
		 */
		if (commandString.contains("LATEST_COMMENT_DATA")) {
			if (kCont.isMyself(root, kCont.getUStCont().getUserKey())) {
				debug.trace("コメントデータをゲット_"+commandString);
				JSONObject blockWithNumber = new JSONObject();
				JSONNumber gotUserImageNumber = root.get("userImageNumber").isNumber();
				JSONObject gotCommentData = root.get("wholeCommentData").isObject();
				
				blockWithNumber.put("userImageNumber", gotUserImageNumber);
				blockWithNumber.put("wholeCommentData", gotCommentData);
				
				debug.trace("blockWithNumber_"+blockWithNumber);
				
				
				kCont.procedure("MyCommentGet+"+blockWithNumber);
			} else {
				JSONObject blockWithNumber = new JSONObject();
				JSONNumber gotUserImageNumber = root.get("userImageNumber").isNumber();
				JSONObject gotCommentData = root.get("wholeCommentData").isObject();
				
				blockWithNumber.put("userImageNumber", gotUserImageNumber);
				blockWithNumber.put("wholeCommentData", gotCommentData);
				
				
				kCont.procedure("SomeCommentGet+"+blockWithNumber);
			}
		}
		
		
		/*
		 * 全体コメントの受け取り
		 */
		if (commandString.contains("ALL_COMMENT_DATA")) {
			if (kCont.isMyself(root, kCont.getUStCont().getUserKey())) {
				debug.trace("from自分なので、自分がリクエストした奴");
				
				debug.trace("コメントデータをゲット_"+commandString);
				JSONObject blockWithNumber = new JSONObject();
				JSONNumber gotUserImageNumber = root.get("userImageNumber").isNumber();
				JSONObject gotCommentData = root.get("wholeCommentData").isObject();
				
				blockWithNumber.put("userImageNumber", gotUserImageNumber);
				blockWithNumber.put("wholeCommentData", gotCommentData);
				
				kCont.procedure("MyCommentGet+"+blockWithNumber);
			}
		}
		
		if (commandString.contains(ClientSideRequestQueueModel.EVENT_TAG_CREATED)) {
			if (kCont.isMyself(root, kCont.getUStCont().getUserKey())) {
				debug.trace("TAG_CREATEDが発生_"+commandString);
				//自分の持ってるアイテムのどれかにタグが足された筈ですが、pushで帰ってきます。口を開けて待ってなさい。
				kCont.procedure(ClientSideRequestQueueModel.EVENT_TAG_CREATED+root);
			} else {
				debug.trace("他人のタグがアップデートされた_"+kCont.getUserNameFromUserKey(root));
			}
		}
		
		if (commandString.contains("ITEM_ADDED_TO_USER")) {
			
			if (kCont.isMyself(root, kCont.getUStCont().getUserKey())) {
				debug.trace("root_"+root);
				kCont.procedure("ITEM_ADDED_TO_USER_ItemUpdated+"+root);
			} else {
				debug.trace("別のユーザーのアイテムが別のユーザーに加算された_"+kCont.getUserNameFromUserKey(root));
			}	
		}
		
		/*
		 * 特定のアイテムに対して、ユーザーの名前がついているタグをサーバから受け取った
		 */
		if (commandString.contains(ClientSideRequestQueueModel.EVENT_USER_TAG_RECEIVED)) {
			if (kCont.isMyself(root, kCont.getUStCont().getUserKey())) {
				kCont.procedure(ClientSideRequestQueueModel.EVENT_USER_TAG_RECEIVED+root);
			} else {
				debug.trace("別のユーザーが特定のアイテムに対して、そのユーザーの名前がついているタグをサーバから受け取った_"+kCont.getUserNameFromUserKey(root));
			}	
		}
		
		if (commandString.contains("ITEM_CREATED")) {
			if (kCont.isMyself(root, kCont.getUStCont().getUserKey())) {
//				debug.trace("このユーザーによって、このユーザーのアイテムがつくられたようです");
			} else {
//				debug.trace("だれかによって、このユーザーのアイテムがつくられたようです_"+getUserNameFromUserKey(root));
			}
			
			//アイテムのリクエストが終了したので、ステータスを変える
		}
		
		/*
		 * アイテムの受け取りが発生
		 */
		if (commandString.contains("PUSH_ITEM")) {
			if (kCont.isMyself(root, kCont.getUStCont().getUserKey())) {
				debug.trace("For myself From myself");
				kCont.procedure("ItemReceived+"+root);
			} else {
//				debug.trace("For myself from someone");
			}
		}
		
		/*
		 * コメント取得の受付完了サインの受け取りが発生
		 */
		if (commandString.contains("GETCOMMENT_ACCEPTED")) {
			if (kCont.isMyself(root, kCont.getUStCont().getUserKey())) {
				kCont.procedure("GETCOMMENT_ACCEPTED"+root);
				
			}
		}
		
		
		if (commandString.contains("TAG_ALREADY_OWN")) {
			if (kCont.isMyself(root, kCont.getUStCont().getUserKey())) {
				debug.trace("TAG_ALREADY_OWN_ココに来てる");
			}
		}
		
		if (commandString.contains("TAG_TO_ITEM_OWNER_ADDED")) {
			if (kCont.isMyself(root, kCont.getUStCont().getUserKey())) {
				debug.trace("TAG_TO_ITEM_OWNER_ADDED_ココに来てる_"+root);//アイテム側から見たイベント
//				kCont.procedure("JoinedAsNewOwner+"+root);
			}
		}
		
		
		if (commandString.contains("ITEM_ALREADY_OWN")) {
			if (kCont.isMyself(root, kCont.getUStCont().getUserKey())) {
//				debug.trace("ITEM_ALREADY_OWN_このアイテムはすでにあなたによって所持されています_"+root);
				kCont.procedure("ItemAlreadyOwn+"+root);
			} else {
				//誰かから、このアイテムを持っています、という通信が来た
			}
		}
		
		if (commandString.contains("ALREADY_ADDED_TO_USER")) {
			if (kCont.isMyself(root, kCont.getUStCont().getUserKey())) {
				value = root.get("itemAddress").isString();
				
//				setKickStatus(STATUS_KICK_OWNERS_INIT);
//				kCont.procedure("LoadingOwnersOfItem+"+kCont.getUStCont().getUserKey());
			}
		}
		
		
		if (commandString.contains("CURRENT_ITEM_DATA")) {
			if (kCont.isMyself(root, kCont.getUStCont().getUserKey())) {
//				debug.trace("CURRENT_ITEM_DATA_root_"+root);
				
				JSONArray array = root.get("userOwnItems").isArray();
				
				kCont.procedure("UserItemCurrent+"+array.toString());
			}	
		}
		
		/*
		 * ユーザーデータ
		 */
		if (commandString.contains(ClientSideRequestQueueModel.REQUEST_TYPE_UPDATE_MYDATA)) {
			if (kCont.isMyself(root, kCont.getUStCont().getUserKey())) {
				debug.trace("CURRENT_ITEM_DATA_root_"+root);
				
				
				kCont.procedure(ClientSideRequestQueueModel.REQUEST_TYPE_UPDATE_MYDATA+root);
			}	
		}
		
	}
}
