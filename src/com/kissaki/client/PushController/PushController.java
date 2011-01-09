package com.kissaki.client.PushController;



import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.kissaki.client.KickController;
import com.kissaki.client.KickStatusInterface;
import com.kissaki.client.MessengerGWTCore.MessengerGWTImplement;
import com.kissaki.client.MessengerGWTCore.MessengerGWTInterface;
import com.kissaki.client.subFrame.debug.Debug;
import com.kissaki.client.userStatusController.userDataModel.ClientSideRequestQueueModel;

/**
 * こいつがクラスに分かれている意味が、いまいち薄い。
 * kickContを持っているのがその理由。
 * Messengerがロック+返答取得来るようになったら、なんとかしたい。
 * っていうかデッドロックするのかな。
 * っていうかマルチスレッドにできてるのに近いな。
 * 一度OSラインを走る事で、凄い事になってる気がする。
 * ブロックの保証とかどうなってるんだろ。
 * 
 * @author ToruInoue
 *
 */
public class PushController implements KickStatusInterface, MessengerGWTInterface {
	Debug debug;
	
	KickController kCont;//ジャストこの瞬間での照会のために必要
	
	MessengerGWTImplement messenger;
	
	public PushController (KickController kCont) {
		debug = new Debug(this);
		messenger = new MessengerGWTImplement(KICK_PUSHCONTROLLER, this);
		
		this.kCont = kCont;
	}
	
	
	/**
	 * プッシュを受けての挙動を行う
	 * @param exec
	 */
	private void pushedExecute(String message) {
		
		
		debug.trace("解析スタート_"+message);
		
		JSONObject root = null;
		JSONString command = null;
		JSONString value = null;
		
		String commandString = null;

		
		
		if (JSONParser.parseStrict(message).isObject() != null) {
			
			root = JSONParser.parseStrict(message).isObject();
			debug.trace("root作成_"+root);
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
				
//				kCont.procedure(ClientSideRequestQueueModel.GET_ALL_USER_WHO_HAVE_THIS_ITEM+root);
				messenger.call(KICK_CONTROLLER, ClientSideRequestQueueModel.GET_ALL_USER_WHO_HAVE_THIS_ITEM, messenger.tagValue("root", root));
			}
		}
		if (commandString.contains("CURRENT_USER_DATA")) {
			if (kCont.isMyself(root, kCont.getUStCont().getUserKey())) {
				debug.trace("最新のUserDataを取得_"+root);
				
//				kCont.procedure("UserDataUpdated");
				messenger.call(KICK_CONTROLLER, "UserDataUpdated");
			}
		}
		
		/*
		 * 探したアイテムが取得出来た
		 */
		if (commandString.contains("ITEM_FOUND")) {
			if (kCont.isMyself(root, kCont.getUStCont().getUserKey())) {
				debug.trace("アイテムが見つかったよ_"+root);
				
//				kCont.procedure("ItemFound+"+root);
				messenger.call(KICK_CONTROLLER, "ItemFound", messenger.tagValue("root", root));
			}
		}
		
		if (commandString.contains("NO_COMMENT")) {
			if (kCont.isMyself(root, kCont.getUStCont().getUserKey())) {
				debug.trace("コメントデータが一件も無い_"+commandString);
				debug.assertTrue(kCont.isMyself(root, kCont.getUStCont().getUserKey()), "自分しかいないので自分宛");
				
				//一件も存在しないので、自分のpopを出す
//				kCont.procedure("NoComment+"+commandString);
				messenger.call(KICK_CONTROLLER, "NoComment", messenger.tagValue("commandString", commandString));
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

//				kCont.procedure("NoComment+"+commandString);
				messenger.call(KICK_CONTROLLER, "NoComment", messenger.tagValue("commandString", commandString));
			} else {
				debug.trace("NO_MY_DATA_このアイテムに関してのとある人自身のコメントデータが無いというイベントが他人のところで発生した_"+commandString);
//				kCont.procedure("SomeoneNoComment+"+commandString);
			}
		}

		if (commandString.contains("COMMENT_SAVED")) {
			if (kCont.isMyself(root, kCont.getUStCont().getUserKey())) {
				debug.trace("自分のコメントデータが保存出来た_"+commandString);
				JSONObject itemKey = root.get("requested").isObject();
				
//				kCont.procedure("CommentSaved+"+itemKey);
				messenger.call(KICK_CONTROLLER, "CommentSaved", messenger.tagValue("itemKey", itemKey));
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
				
//				kCont.procedure(ClientSideRequestQueueModel.REQUEST_TYPE_GET_USER_INDIVIDUAL_TAG+root);
				messenger.call(KICK_CONTROLLER, ClientSideRequestQueueModel.REQUEST_TYPE_GET_USER_INDIVIDUAL_TAG, messenger.tagValue("root", root));
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
				
				
//				kCont.procedure("MyCommentGet+"+blockWithNumber);
				messenger.call(KICK_CONTROLLER, "MyCommentGet", messenger.tagValue("blockWithNumber", blockWithNumber));
			} else {
				JSONObject blockWithNumber = new JSONObject();
				JSONNumber gotUserImageNumber = root.get("userImageNumber").isNumber();
				JSONObject gotCommentData = root.get("wholeCommentData").isObject();
				
				blockWithNumber.put("userImageNumber", gotUserImageNumber);
				blockWithNumber.put("wholeCommentData", gotCommentData);
				
				
//				kCont.procedure("SomeCommentGet+"+blockWithNumber);
				messenger.call(KICK_CONTROLLER, "SomeCommentGet", messenger.tagValue("blockWithNumber", blockWithNumber));
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
				
//				kCont.procedure("MyCommentGet+"+blockWithNumber);
				messenger.call(KICK_CONTROLLER, "MyCommentGet", messenger.tagValue("blockWithNumber", blockWithNumber));
			}
		}
		
		if (commandString.contains(ClientSideRequestQueueModel.EVENT_TAG_CREATED)) {
			if (kCont.isMyself(root, kCont.getUStCont().getUserKey())) {
				debug.trace("TAG_CREATEDが発生_"+commandString);
				//自分の持ってるアイテムのどれかにタグが足された筈ですが、pushで帰ってきます。口を開けて待ってなさい。
				
//				kCont.procedure(ClientSideRequestQueueModel.EVENT_TAG_CREATED+root);
				messenger.call(KICK_CONTROLLER, ClientSideRequestQueueModel.EVENT_TAG_CREATED, messenger.tagValue("root", root));
			} else {
				debug.trace("他人のタグがアップデートされた_"+kCont.getUserNameFromUserKey(root));
			}
		}
		
		if (commandString.contains("ITEM_ADDED_TO_USER")) {
			
			if (kCont.isMyself(root, kCont.getUStCont().getUserKey())) {
				debug.trace("root_"+root);
				
//				kCont.procedure("ITEM_ADDED_TO_USER_ItemUpdated+"+root);
				messenger.call(KICK_CONTROLLER, "ITEM_ADDED_TO_USER_ItemUpdated", messenger.tagValue("root", root));
			} else {
				debug.trace("別のユーザーのアイテムが別のユーザーに加算された_"+kCont.getUserNameFromUserKey(root));
			}	
		}
		
		/*
		 * 特定のアイテムに対して、ユーザーの名前がついているタグをサーバから受け取った
		 */
		if (commandString.contains(ClientSideRequestQueueModel.EVENT_USER_TAG_RECEIVED)) {
			if (kCont.isMyself(root, kCont.getUStCont().getUserKey())) {
				
//				kCont.procedure(ClientSideRequestQueueModel.EVENT_USER_TAG_RECEIVED+root);
				messenger.call(KICK_CONTROLLER, ClientSideRequestQueueModel.EVENT_USER_TAG_RECEIVED, messenger.tagValue("root", root));
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
				
//				kCont.procedure("ItemReceived+"+root);
				messenger.call(KICK_CONTROLLER, "ItemReceived", messenger.tagValue("root", root));
			} else {
//				debug.trace("For myself from someone");
			}
		}
		
		/*
		 * コメント取得の受付完了サインの受け取りが発生
		 */
		if (commandString.contains("GETCOMMENT_ACCEPTED")) {
			if (kCont.isMyself(root, kCont.getUStCont().getUserKey())) {
				
//				kCont.procedure("GETCOMMENT_ACCEPTED"+root);
				messenger.call(KICK_CONTROLLER, "GETCOMMENT_ACCEPTED", messenger.tagValue("root", root));
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
//				kCont.procedure("ItemAlreadyOwn+"+root);
				messenger.call(KICK_CONTROLLER, "ItemAlreadyOwn", messenger.tagValue("root", root));
				debug.trace("HereComes");
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
				
//				kCont.procedure("UserItemCurrent+"+array.toString());
				messenger.call(KICK_CONTROLLER, "UserItemCurrent", messenger.tagValue("array", array.toString()));
			}	
		}
		
		/*
		 * ユーザーデータ
		 */
		if (commandString.contains(ClientSideRequestQueueModel.REQUEST_TYPE_UPDATE_MYDATA)) {
			if (kCont.isMyself(root, kCont.getUStCont().getUserKey())) {
				debug.trace("CURRENT_ITEM_DATA_root_"+root);
				
				
//				kCont.procedure(ClientSideRequestQueueModel.REQUEST_TYPE_UPDATE_MYDATA+root);
				messenger.call(KICK_CONTROLLER, ClientSideRequestQueueModel.REQUEST_TYPE_UPDATE_MYDATA, messenger.tagValue("root", root));
			}	
		}
		
	}

	
	@Override
	public void receiveCenter(String message) {
//		String exec = messenger.getCommand(message);
//		if (exec.equals("push")) {
//			pushedExecute(messenger.getValueForTag(message, "encodedData").isString().toString());
//		}
		
		pushedExecute(message);
	}
}
