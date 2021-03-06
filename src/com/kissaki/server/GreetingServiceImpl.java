package com.kissaki.server;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.activation.DataSource;
import javax.jdo.datastore.DataStoreCache;

import org.slim3.datastore.Datastore;

import quicktime.std.movies.media.UserData;

import com.kissaki.client.GreetingService;
import com.kissaki.client.subFrame.debug.Debug;
import com.kissaki.client.userStatusController.userDataModel.ClientSideRequestQueueModel;
import com.kissaki.server.commentDataModel.CommentDataModel;
import com.kissaki.server.commentDataModel.CommentDataModelMeta;
import com.kissaki.server.itemDataModel.ItemDataModel;
import com.kissaki.server.itemDataModel.ItemDataModelMeta;
import com.kissaki.server.tagDataModel.TagDataModel;
import com.kissaki.server.tagDataModel.TagDataModelMeta;
import com.kissaki.server.userDataModel.UserDataModel;
import com.kissaki.server.userDataModel.UserDataModelMeta;
import com.kissaki.shared.FieldVerifier;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.memcache.MemcacheServicePb.MemcacheDeleteRequest.Item;
import com.google.appengine.repackaged.org.json.JSONArray;
import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;
import com.google.appengine.repackaged.org.json.JSONString;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * APIメモ
 * 未完
 * 	ユーザーのこのアイテムに対する全タグを返す
 * 	ユーザーのアイテムリストを返す
 * 	
 * 
 * 完了
 * 	特定アドレスのアイテムを返す
 * 	特定キーのアイテムを返す
 * 	ログインを行う
 * 	タグのセットを行う
 * 	最新のユーザーデータを取得する
 * 		ユーザーのアイテムリスト
 * 	このアイテムについての全コメントを取得する
 * 
 */


/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
GreetingService {
	int count = 0;
	Debug debug;
	Gson gson;


	Channel channel = new Channel();


	static String MASTERKEY = "master";
	String channelId = MASTERKEY;

	public GreetingServiceImpl () {
		debug = new Debug(this);
		gson = new Gson();
	}


	public String greetServer(String input) throws IllegalArgumentException {

		if (input.equals("100")) {

			return channel.getChannel(channelId);//Channelキーを返却
		}

		if (input.equals("200")) {

			if (count % 2 == 0) {
				channel.sendMessage(channelId, ""+count);	
			} else {
				channel.sendMessage(channelId, "Go");	
			}
			count++;
		}
		
		
		if (input.startsWith(ClientSideRequestQueueModel.REQUEST_TYPE_LOGIN)) {
			String ret = loginQualification(input);
			debug.trace("ret_"+ret);
			return ret;
		}
		if (input.startsWith(ClientSideRequestQueueModel.REQUEST_TYPE_UPDATE_TAG)) {
			return updateTagQualification(input);
		}
		if (input.startsWith(ClientSideRequestQueueModel.REQUEST_TYPE_UPDATE_MYDATA)) {
			return getCurrentUserDataQualification(input);
		}
		if (input.startsWith(ClientSideRequestQueueModel.REQUEST_TYPE_GET_ITEM_FROM_ADDRESS)) {
			return getItemFromAddressQualification(input);
		}
		if (input.startsWith(ClientSideRequestQueueModel.REQUEST_TYPE_GET_ITEM_FROM_KEY)) {
			return getItemFromKeyQualification(input);
		}
		if (input.startsWith(ClientSideRequestQueueModel.REQUEST_TYPE_ADD_ITEM_WITH_URL)) {
			return addItemQualification(input);
		}
		if (input.startsWith(ClientSideRequestQueueModel.REQUEST_TYPE_ADD_TAG_TO_ITEM)) {
			return addTagToItemQualification(input);
		}
		if (input.startsWith(ClientSideRequestQueueModel.REQUEST_TYPE_GET_USER_INDIVIDUAL_TAG)) {
			return getItemTagByUserQualification(input);
		}
		if (input.startsWith(ClientSideRequestQueueModel.REQUEST_TYPE_ADDCOMMENT)) {
			return addCommentQualification(input);
		}
		if (input.startsWith(ClientSideRequestQueueModel.REQUEST_TYPE_GETALLCOMMENT)) {
			return getAllCommentQualification(input);
		}
		if (input.startsWith(ClientSideRequestQueueModel.REQUEST_TYPE_GET_LATESTCOMMENT)) {
			return getLatestCommentQualification(input);
		}
		if (input.startsWith(ClientSideRequestQueueModel.GET_ALL_USER_WHO_HAVE_THIS_ITEM)) {
			return getAllOwningUserQualification(input);
		}
		if (input.startsWith(ClientSideRequestQueueModel.REQUEST_TYPE_GET_SPECIFIC_USER_INFORMATION)) {
			return getSpecificUserInformation(input);
		}
		
		return "default";//HTTP_OKキーを返せばいい
	}

	
	/**
	 * 特定のユーザーの所持アイテムと、タグを芋づる式に取得する。
	 * まずアイテムリストを渡す。ユーザーのアイテムとタグを渡すのは別のAPIの役割
	 * 存在しないユーザーなら何もおこらないという事になる
	 * @param input
	 * @return
	 */
	private String getSpecificUserInformation(String input) {
		String root = input.substring(ClientSideRequestQueueModel.REQUEST_TYPE_GET_SPECIFIC_USER_INFORMATION.length(), input.length());
		
		
		
		
		return "ok";
	}


	/**
	 * アイテムキーから、このアイテムを所持している全ユーザーのキーをリストとして返す
	 * @param input
	 * @return
	 */
	private String getAllOwningUserQualification(String input) {
		
		String rootString = input.substring(ClientSideRequestQueueModel.GET_ALL_USER_WHO_HAVE_THIS_ITEM.length(), input.length());

		String userKeyString = null;
		String itemKeyString = null;
		String triggerID = null;
		
		try {
			JSONObject rootObject = new JSONObject(rootString);
			userKeyString = rootObject.getJSONObject("userKey").getString("name");
			itemKeyString = rootObject.getJSONObject("itemKey").getString("name");
			
			triggerID = rootObject.getString(ClientSideRequestQueueModel.KEY_STRING_TRIGGER_ID);
		} catch (Exception e) {
			debug.trace("getAllOwningUserQualification_error_"+e);
		}
		
		UserDataModel currentUserData = getUserModelFromKeyName(userKeyString);
		ItemDataModel currentItemData = getItemDataModelFromItemName(itemKeyString);
		
		debug.trace("ownerKey_"+currentItemData.getM_ownerList());
		debug.trace("userKey_"+currentUserData.getKey());
		debug.trace("triggerID_"+triggerID);
		
		Map<String,Object> ownerMap = new HashMap<String,Object>();
		ownerMap.put(ClientSideRequestQueueModel.KEY_STRING_TRIGGER_ID, triggerID);
		List<UserDataModel>userArray = Datastore.get(UserDataModel.class, currentItemData.getM_ownerList());
		
		ownerMap.put("ownerList", userArray);
		ownerMap.put("userInfo", currentUserData.getKey());
		ownerMap.put("command", ClientSideRequestQueueModel.GET_ALL_USER_WHO_HAVE_THIS_ITEM);
		
		
		String ownerString = gson.toJson(ownerMap);
		channel.sendMessage(channelId, ownerString);
		
		
		return "ok";
	}


	/**
	 * タグのアップデートを行う
	 * @param input
	 * @return
	 */
	private String updateTagQualification(String input) {
		String updateKey = input.substring(ClientSideRequestQueueModel.REQUEST_TYPE_UPDATE_TAG.length(), input.length());
		
		/*
		 * タグの情報に、ユーザー情報を追加
		 */
		debug.trace("updateKey_"+updateKey);
		
		JSONObject rootObject = null;
		String triggerID = null;
		String userName = null;
		String itemName = null;
		String tagName = null;
		try {
			rootObject = new JSONObject(updateKey);
			
			JSONObject userObject = rootObject.getJSONObject("userKey");
			userName = userObject.getString("name");

			JSONObject itemObject = rootObject.getJSONObject("itemKey");
			itemName = itemObject.getString("name");
			
			JSONObject tagObject = rootObject.getJSONObject("tagObject");
			tagName = tagObject.getJSONObject("key").getString("name");
			
			
			triggerID = rootObject.getString(ClientSideRequestQueueModel.KEY_STRING_TRIGGER_ID);
		} catch (JSONException e) {
			debug.trace("REQUEST_TYPE_UPDATE_TAG_parseError_"+e);
		}
		
		UserDataModel currentUserDataModel = getUserModelFromKeyName(userName);
		ItemDataModel currentItemDataModel = getItemDataModelFromItemName(itemName);
		
		if (tagName != null) {
			TagDataModel currentTagDataModel = getTagDataModelFromName(tagName);
			debug.trace("currentTagDataModel_"+currentTagDataModel);
			if (currentTagDataModel.getM_tagOwnerItemList().contains(currentUserDataModel.getKey())) {
				debug.trace("すでに含んでいる");
			} else {
				currentTagDataModel.getM_tagOwnerItemList().add(currentUserDataModel.getKey());
				Datastore.put(currentTagDataModel);
				debug.trace("新しく追加した");//一回ココ、通っているんだよね。うーん。
				
				Map<String,Object> tagMap = new HashMap<String,Object>();
				tagMap.put(ClientSideRequestQueueModel.KEY_STRING_TRIGGER_ID, triggerID);
				tagMap.put("itemName", currentItemDataModel.getKey().getName());
				tagMap.put("tagObject", currentTagDataModel);
				tagMap.put("userInfo", currentUserDataModel.getKey());
				tagMap.put("command", ClientSideRequestQueueModel.EVENT_USER_TAG_RECEIVED);
				
				
				String itemNameWithMyTag = gson.toJson(tagMap);
				
				channel.sendMessage(channelId, itemNameWithMyTag);
			}
		}
		return "ok";
	}


	


	/**
	 * 合致するアドレスのアイテムを探す
	 * @param input
	 * @return
	 */
	private String getItemFromAddressQualification(String input) {
		String userKeyWithAddress = input.substring(ClientSideRequestQueueModel.REQUEST_TYPE_GET_ITEM_FROM_ADDRESS.length(),input.length());
		
		
		JSONObject userKeyWithAddressObject;
		String userName = null;
		String itemAddress = null;
		String triggerID = null;
		try {
			userKeyWithAddressObject = new JSONObject(userKeyWithAddress);
		
			JSONObject userKeyObject = userKeyWithAddressObject.getJSONObject("userKey");
			userName = userKeyObject.getString("name");
			
			itemAddress = userKeyWithAddressObject.getString("itemAddressAsIdentifier");
			
			triggerID = userKeyWithAddressObject.getString(ClientSideRequestQueueModel.KEY_STRING_TRIGGER_ID);
		} catch (JSONException e) {
			debug.trace("getItemFromAddressQualifi_error_"+e);
		}
		
		debug.assertTrue(userName != null, "userNameがnull");
		
		
		//この値がユーザーの所持物に入っている場合のみ、返答する。　ここからの所持は、、やろうと思えば出来るのか。あーー、、うーん、、
		UserDataModel currentUserDataModel = getUserModelFromKeyName(userName);
		ItemDataModel currentItem = null;
		if (currentUserDataModel != null) {
			//ユーザー所持のアイテムから探す
			
			currentItem = getItemDataModelFromItemName(itemAddress);
			debug.trace("currentItem_"+currentItem);
			
		}
		
		if (currentItem  != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("requestedItem", currentItem);
			map.put("command", "ITEM_FOUND");//アイテムのデータを更新するきっかけにする。
			map.put("userInfo", currentUserDataModel.getKey());
			map.put(ClientSideRequestQueueModel.KEY_STRING_TRIGGER_ID, triggerID);
			
			
			String currentCommentData = gson.toJson(map);
			channel.sendMessage(channelId, currentCommentData);
			
			
			
		} else {
			debug.trace("持ってないっす");
			
		}
		
		
		
		return "ok";
	}


	/**
	 * コメントを追加する
	 * @param input
	 * @return
	 */
	private String addCommentQualification(String input) {
		String itemKeyStringOrigin = input.substring(ClientSideRequestQueueModel.REQUEST_TYPE_ADDCOMMENT.length(), input.length());
		debug.trace("addCommentQualification_itemKeyString_"+itemKeyStringOrigin);
		
		/*
		 * コメントを生成、アイテムに追加、アイテムをリロードさせる。
		 */
		JSONObject rootObject = null;
		//ここでID作成する必要があるのかな。あ、別にいいのか。このユーザーがこのときこのアイテムにコメントした内容？
		//アイテムを取り出して、そこに無条件でコメントを加える
		String commentString = null;
		
		JSONObject itemKeyObject = null;
		String itemKeyString = null;
		
		JSONObject userKeyObject = null;
		String userKeyString = null;
		
		JSONObject masterUserkeyObject = null;//Find masteruser from name.
		String masterUserKeyString = null;
		
		try {
			rootObject = new JSONObject(itemKeyStringOrigin);
			commentString = rootObject.getString("comment");
			
			itemKeyObject = rootObject.getJSONObject("itemKey");
			itemKeyString = itemKeyObject.getString("name").toString();
			
			userKeyObject = rootObject.getJSONObject("userKey");
			userKeyString = userKeyObject.getString("name").toString();
			
			masterUserkeyObject = rootObject.getJSONObject("masterUserKey");
			masterUserKeyString = masterUserkeyObject.getString("name").toString();
			
			debug.trace("userKeyString_"+userKeyString);
		} catch (Exception e) {
			debug.trace("addCommentQualification_error_"+e);
		}
		
		UserDataModel currentMasterUserDataModel = getUserModelFromKeyName(masterUserKeyString);
		debug.assertTrue(currentMasterUserDataModel != null, "currentMasterUserDataModelがnull");
		
		UserDataModel currentUserDataModel = getUserModelFromKeyName(userKeyString);
		debug.assertTrue(currentUserDataModel != null, "currentUserDataModelがnull");
		
		
		Key itemKey = Datastore.createKey(ItemDataModel.class, itemKeyString);
		ItemDataModelMeta itemMeta = ItemDataModelMeta.get();
		List<ItemDataModel> items = Datastore.query(itemMeta)
		.filter(itemMeta.key.equal(itemKey))
		.asList();
		ItemDataModel currentItem = null;
		if (0 < items.size()) {//さすがに存在している筈だが、存在していないケースが考えられそう。
			debug.assertTrue(items.size() == 1, "一件以上ある");
			//コメントアレイをゲットしたら、そこにコメントを追加するのだが、idはコメントの件数によってつけるようにする。
			currentItem = Datastore.get(ItemDataModel.class, items.get(0).getKey());
		}
		
		List<Key> commentKeyList = null;
		Key commentKey = null;
		if (currentItem != null) {
			commentKeyList = currentItem.getM_commentList();
			if (0 < commentKeyList.size()) {
				//何件もあるはず、で、その最後尾に付け加えられればいいや。
			} else if (commentKeyList.size() == 0) {
				
			}
			
			commentKey = Datastore.createKey(CommentDataModel.class, commentKeyList.size()+1);
			//あるかないか、確認出来るが。
		}
		
		CommentDataModel currentCommentDataModel = null;
		if (commentKey != null) {
			debug.trace("コメントキーが存在している");
			//存在するコメントについて、調べる？　いいや、調べない。
			
			currentCommentDataModel = new CommentDataModel();
			
			currentCommentDataModel.setKey(commentKey);
			currentCommentDataModel.setM_commentBody(commentString);
			currentCommentDataModel.setM_commentedBy(currentUserDataModel.getKey());
			currentCommentDataModel.setM_commentMasterID(currentMasterUserDataModel.getKey());
			
			Datastore.put(currentCommentDataModel);
			
			currentItem.getM_commentList().add(currentCommentDataModel.getKey());
			Datastore.put(currentItem);
			debug.trace("コメント保存が出来た");
			
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("requested", currentItem.getKey());
			map.put("command", "COMMENT_SAVED");//アイテムのデータを更新するきっかけにする。
			map.put("userInfo", currentUserDataModel.getKey());
			
			String currentCommentData = gson.toJson(map);
			channel.sendMessage(channelId, currentCommentData);
		}
		
		
		return "ok";
	}


	/**
	 * 全件取得
	 * @param input
	 * @return
	 */
	private String getAllCommentQualification(String input) {
		String itemKeyString = input.substring(ClientSideRequestQueueModel.REQUEST_TYPE_GETALLCOMMENT.length(), input.length());
		
		//JSON化
		JSONObject rootObject = null;
		String itemKeyName = null;
		JSONObject userKeyObject = null;
		String userKeyName = null;
		String triggerID = null;
		
		try {
			rootObject = new JSONObject(itemKeyString);
			JSONObject itemKeyObject = rootObject.getJSONObject("itemKey");
			itemKeyName = itemKeyObject.get("name").toString();
			
			
			userKeyObject = rootObject.getJSONObject("userKey");
			userKeyName = userKeyObject.getString("name");
			
			triggerID = rootObject.getString(ClientSideRequestQueueModel.KEY_STRING_TRIGGER_ID);
		} catch (JSONException e) {
			debug.trace("getCommentQualifi_error_"+e);
		}
		//.getJSONObject("userKey");
		
		Key itemKey = Datastore.createKey(ItemDataModel.class, itemKeyName);
		
		ItemDataModelMeta itemMeta = ItemDataModelMeta.get();
		List<ItemDataModel> items = Datastore.query(itemMeta)
		.filter(itemMeta.key.equal(itemKey))
		.asList();
		
		if (0 < items.size()) {
			debug.assertTrue(items.size() == 1, "コメント取得時に、一意を期待して取得した、キーに該当するアイテムが一件以上存在する");
			
			//First, return back the number of comment and users who own this item.
			
			UserDataModel myself = getUserModelFromKeyName(userKeyName);
			
			/*
			 * このアイテムの所有者を取得、リクエスト元/それ以外にブロードキャストで送りつける
			 */
			ItemDataModel currentItem = Datastore.get(ItemDataModel.class, items.get(0).getKey());
			//コメントを取得する
			List<Key> commentKeyList = currentItem.getM_commentList();
			
			List<CommentDataModel> comment = null;
			
			//まずコメントの一覧を取得する
			for (Iterator<Key> commentKeyItel = commentKeyList.iterator(); commentKeyItel.hasNext();) {
				//このコメントをユーザーに届ける
				Key currentCommentKey = commentKeyItel.next();
				CommentDataModelMeta commentMeta = CommentDataModelMeta.get();
				comment = Datastore.query(commentMeta)
				.filter(commentMeta.key.equal(currentCommentKey))
				.asList();
				
				getCommentFromKeyList(comment, myself, userKeyName, rootObject);
			}
			
			if (comment == null) {//このアイテムに関するコメントは一件も無い
//				userKeyNameでユーザーを取得する
				{
					Map<String, Object> myNodataMap = new HashMap<String, Object>();
					
					myNodataMap.put("userInfo", myself.getKey());
					myNodataMap.put("command", "NO_COMMENT");
					
					String currentCommentData = gson.toJson(myNodataMap);
					debug.trace("currentCommentData_"+currentCommentData);
					channel.sendMessage(channelId, currentCommentData);
				}
				
				
				
			}
			
			//どのみちリクエスト自体が届いているので、返答
			Map<String, Object> answerMap = new HashMap<String, Object>();
			answerMap.put("userInfo", myself.getKey());
			answerMap.put("command", "GETCOMMENT_ACCEPTED");
			answerMap.put(ClientSideRequestQueueModel.KEY_STRING_TRIGGER_ID, triggerID);
			
			String commentAnswerData = gson.toJson(answerMap);
			debug.trace("commentAnswerData_"+commentAnswerData);
			channel.sendMessage(channelId, commentAnswerData);//とりあえず受け付けた事を返答する
		}
		
		
		
		return "ok";//ここでもいいのかもしれないが、面倒なので評価したくない
	}
	
	
	
	/**
	 * 最近の１件取得
	 * @param input
	 * @return
	 */
	private String getLatestCommentQualification(String input) {
		String itemKeyString = input.substring(ClientSideRequestQueueModel.REQUEST_TYPE_GET_LATESTCOMMENT.length(), input.length());
		
		//JSON化
		JSONObject rootObject = null;
		String itemKeyName = null;
		JSONObject userKeyObject = null;
		String userKeyName = null;
		try {
			rootObject = new JSONObject(itemKeyString);
			JSONObject itemKeyObject = rootObject.getJSONObject("itemKey");
			itemKeyName = itemKeyObject.get("name").toString();
			
			
			userKeyObject = rootObject.getJSONObject("userKey");
			userKeyName = userKeyObject.getString("name");
		} catch (JSONException e) {
			debug.trace("getLatestCommentQualifi_error_"+e);
		}
		//.getJSONObject("userKey");
		
		Key itemKey = Datastore.createKey(ItemDataModel.class, itemKeyName);
		
		ItemDataModelMeta itemMeta = ItemDataModelMeta.get();
		List<ItemDataModel> items = Datastore.query(itemMeta)
		.filter(itemMeta.key.equal(itemKey))
		.asList();
		
		if (0 < items.size()) {
			debug.assertTrue(items.size() == 1, "コメント取得時に、一意を期待して取得した、キーに該当するアイテムが一件以上存在する");
			
			UserDataModel myself = getUserModelFromKeyName(userKeyName);
			
			/*
			 * このアイテムの所有者を取得、リクエスト元/それ以外にブロードキャストで送りつける
			 */
			ItemDataModel currentItem = Datastore.get(ItemDataModel.class, items.get(0).getKey());
			//コメントを取得する
			List<Key> commentKeyList = currentItem.getM_commentList();
			
			List<CommentDataModel> comment = null;
			
			//まずコメントの一覧を取得する
			for (Iterator<Key> commentKeyItel = commentKeyList.iterator(); commentKeyItel.hasNext();) {
				//このコメントをユーザーに届ける
				Key currentCommentKey = commentKeyItel.next();
				CommentDataModelMeta commentMeta = CommentDataModelMeta.get();
				comment = Datastore.query(commentMeta)
				.filter(commentMeta.key.equal(currentCommentKey))
				.asList();
			}
			
			//TODO 酷いコードだ。Latestを取得出来るが、自分が書いたタイミングでのLatestでしかないし、なにより汚い。 最新を最新としてではなく順番などで受ける手法が必要。
			
			getLatestCommentFromKeyList(comment, myself, userKeyName, rootObject);//最新の一件のみを取得
			
			if (comment == null) {//このアイテムに関するコメントは一件も無い
//				userKeyNameでユーザーを取得する
				
				Map<String, Object> myNodataMap = new HashMap<String, Object>();
				
				myNodataMap.put("userInfo", myself.getKey());
				myNodataMap.put("command", "NO_COMMENT");
				
				String currentCommentData = gson.toJson(myNodataMap);
				debug.trace("getLatestCommentQualifi_currentCommentData_"+currentCommentData);
				channel.sendMessage(channelId, currentCommentData);
			}
		}
		
		return "ok";
	}

	
	/**
	 * 最終のものだけを取得する
	 * @param comment
	 * @param myself
	 * @param userKeyName
	 * @param rootObject
	 */
	private void getLatestCommentFromKeyList(List<CommentDataModel> comment,
			UserDataModel myself, String userKeyName, JSONObject rootObject) {
		boolean thereIsMyself = false;
		
		if (comment != null) {
			//ゲットし終わったら、コメントを取得
			for (Iterator<CommentDataModel> commentItel = comment.iterator(); commentItel.hasNext();) {
				CommentDataModel currentComment = commentItel.next();
				UserDataModel commentedUserData = getUserModelFromKeyName(currentComment.getM_commentMasterID().getName());
				Map<String, Object> map = new HashMap<String, Object>();

				map.put("command", "LATEST_COMMENT_DATA");
				map.put("requested", rootObject);
				map.put("wholeCommentData", currentComment);
				map.put("userInfo", myself.getKey());
				map.put("userImageNumber", commentedUserData.getImageNumber());
				
				String s = currentComment.getM_commentMasterID().getName();//gson.toJson(currentComment.getM_commentMasterID());
				
				String currentCommentData = gson.toJson(map);
				channel.sendMessage(channelId, currentCommentData);
				
				
				if (userKeyName.equals(s)) {//ユーザー名で判断する
					debug.trace("コメント書きの中に、自分が居た");
					thereIsMyself = true;
//					
//					Map<String, Object> innerMap = new HashMap<String, Object>();
//					innerMap.put("command", "THERE_IS_MY_COMMENT");
//					innerMap.put("userInfo", myself.getKey());
//					String currentMyselfData = gson.toJson(innerMap);
//					channel.sendMessage(channelId, currentMyselfData);
				}
			}
		}
		
		if (!thereIsMyself) {
			debug.trace("自分がマスターになっているコメントが無い_"+myself.getKey());
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("command", "NO_MY_DATA");
			map.put("userInfo", myself.getKey());

			String currentCommentData = gson.toJson(map);
			channel.sendMessage(channelId, currentCommentData);
		}
		
	}


	/**
	 * @param userKeyName 
	 * @param myself 
	 */
	private void getCommentFromKeyList(List<CommentDataModel> comment, UserDataModel myself, String userKeyName, JSONObject rootObject) {
		
		boolean thereIsMyself = false;
		
		if (comment != null) {
			//ゲットし終わったら、コメントを取得
			for (Iterator<CommentDataModel> commentItel = comment.iterator(); commentItel.hasNext();) {
				CommentDataModel currentComment = commentItel.next();
				UserDataModel commentedUserData = getUserModelFromKeyName(currentComment.getM_commentMasterID().getName());
				Map<String, Object> map = new HashMap<String, Object>();

				map.put("requested", rootObject);
				map.put("wholeCommentData", currentComment);
				map.put("command", "ALL_COMMENT_DATA");
				map.put("userInfo", myself.getKey());
				map.put("userImageNumber", commentedUserData.getImageNumber());//コメント主のナンバーが必要
				
				String s = currentComment.getM_commentMasterID().getName();//gson.toJson(currentComment.getM_commentMasterID());
				
				String currentCommentData = gson.toJson(map);
				channel.sendMessage(channelId, currentCommentData);
				
				
				if (userKeyName.equals(s)) {//ユーザー名で判断する
					debug.trace("コメント書きの中に、自分が居た");
					thereIsMyself = true;
//					
//					Map<String, Object> innerMap = new HashMap<String, Object>();
//					innerMap.put("command", "THERE_IS_MY_COMMENT");
//					innerMap.put("userInfo", myself.getKey());
//					String currentMyselfData = gson.toJson(innerMap);
//					channel.sendMessage(channelId, currentMyselfData);
				}
			}
		}
		
		if (!thereIsMyself) {
			debug.trace("自分がマスターになっているコメントが無い_"+myself.getKey());
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("command", "NO_MY_DATA");
			map.put("userInfo", myself.getKey());

			String currentCommentData = gson.toJson(map);
			channel.sendMessage(channelId, currentCommentData);
		}
	}


	/**
	 * 最近のユーザーのデータを受け渡す
	 * @param input
	 * @return
	 */
	private String getCurrentUserDataQualification(String input) {
		input = input.substring(ClientSideRequestQueueModel.REQUEST_TYPE_UPDATE_MYDATA.length(), input.length());
		
		Key userKey = null;
		String myUserName = null;
		String triggerID = null;
		try {
			userKey = gson.fromJson(input, Key.class);//,,,what? does it works correct? involving other data...
			myUserName = userKey.getName();
			
			JSONObject jsonDatas = new JSONObject(input);
			triggerID = jsonDatas.getString(ClientSideRequestQueueModel.KEY_STRING_TRIGGER_ID);
		} catch (Exception e) {
			debug.trace("getCurrentUserDataQualification_gson_parse_"+e);	
		}
		
		//ユーザーのアイテムのキーだけ特に送る
		UserDataModel myUserData = getUserModelFromKeyName(myUserName);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("command", "CURRENT_ITEM_DATA");
		map.put("userOwnItems", myUserData.getItemKeys());
		map.put("userInfo", myUserData.getKey());
		
		
		String currentUserItemDatasString = gson.toJson(map);
		
		channel.sendMessage(channelId, currentUserItemDatasString);
		
		
		
		//ユーザーの情報を送る
		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap.put("command", ClientSideRequestQueueModel.REQUEST_TYPE_UPDATE_MYDATA);
		userMap.put("userData", myUserData);
		userMap.put("userInfo", myUserData.getKey());
		userMap.put(ClientSideRequestQueueModel.KEY_STRING_TRIGGER_ID, triggerID);
		
		String currentUserDataString = gson.toJson(userMap);
		
		channel.sendMessage(channelId, currentUserDataString);
		
		
		return "ok";
	}


	/**
	 * タグを作成する。
	 * タグキーを指定したアイテムにセットする。
	 * 
	 * ユーザーが所有しているタグ情報を更新する。
	 * 
	 * @param input
	 * @return
	 */
	private String addTagToItemQualification(String input) {
		input = input.substring(ClientSideRequestQueueModel.REQUEST_TYPE_ADD_TAG_TO_ITEM.length(), input.length());
		debug.trace("input_"+input);
		
		Key currentTagkey = null;
		JSONObject jsonDatas = null;
		JSONObject newTaggedItemKeyObject = null;
		String newTagNameString = null;
		String newTagKeyString = null;
		String triggerID = null;
		
		JSONObject taggingUserKeyObject = null;
		String currentItemNameString = null;
		String taggingUserNameString = null;
		UserDataModel currentUserDataModel = null;
		try {
			jsonDatas = new JSONObject(input);
			newTaggedItemKeyObject = jsonDatas.getJSONObject("itemKey");
			newTagNameString = jsonDatas.getString("newTag");
			taggingUserKeyObject = jsonDatas.getJSONObject("userKey");
			
			currentItemNameString = newTaggedItemKeyObject.get("name").toString();
			taggingUserNameString = taggingUserKeyObject.get("name").toString();
			currentUserDataModel = getUserModelFromKeyName(taggingUserNameString);
			
			triggerID = jsonDatas.getString(ClientSideRequestQueueModel.KEY_STRING_TRIGGER_ID);
		} catch (JSONException e) {
			debug.trace("addTagToItemQualification_"+e);
		}
		

		newTagKeyString = newTagNameString;//アイテム名との組み合わせがいいな。
		debug.trace("タグの名称は_"+newTagKeyString);
		/*
		 * {"itemKey":{"kind":"item", "id":0, "name":"http://a"}, "newTag":"aaaa", "userKey":{"kind":"user", "id":0, "name":"aaaa@bbbb"}}
		 */
		
		
		/*
		 * 事前準備、ユーザーとアイテムの検索をしておく
		 */
		//タグ付けされたアイテムのキー
		
		ItemDataModel currentItemDataModel = getItemDataModelFromItemName(currentItemNameString);
		
		debug.trace("currentUserDataModel_getKey_"+currentUserDataModel.getKey());
		
		currentTagkey = Datastore.createKey(TagDataModel.class, newTagKeyString);
		/*
		 * アイテムについてるタグ、ではなく、タグが世界中をまたぐ場合を考えたら、このアイテムとの連携が強いこの名前のタグ、という風になるのかな。
		 * それとも、この名称のタグという集団になるのかな。
		 * 
		 * 今回は、このアイテムについているタグをキーにしている。
		 * 名前からは検索できるが、ヒットするのは他のアイテムについている同名のタグ、なんてこともあり得る。
		 */
		TagDataModelMeta tagMeta = TagDataModelMeta.get();
		List<TagDataModel> tags = Datastore.query(tagMeta)
		.filter(tagMeta.key.equal(currentTagkey))//ドンピシャ
		.asList(); 
		
		if (0 < tags.size()) {//同じキーを持った物は、存在しない筈。
			debug.trace("currentTagkeyが存在している_"+currentTagkey);
			//複数個に対応していない
			
			TagDataModel currentTagDataModel = Datastore.get(TagDataModel.class, tags.get(0).getKey());//存在していたタグを引き出す
			
			List<Key> itemOwnerList = currentTagDataModel.getM_itemOwnerList();//このタグがついたアイテムがだれのもちものなのか、引き出す
			
			//タグをセットしにきているので、既にあれば、セットする必要は無いが、
			//タグを付けたのが自分かどうか、という情報は欲しい。
			
			if (itemOwnerList.contains(currentUserDataModel.getKey())) {//あんたの手元にこのタグもうあるじゃん
				debug.trace("あんたの手元にこのタグもうあるじゃん。初期化だからって容赦しないんだからね！");
				
				Map<String, Object> channelMap_TAG_FOR_THIS_ITEM_ALREADY_OWN = new HashMap<String, Object>();
				channelMap_TAG_FOR_THIS_ITEM_ALREADY_OWN.put("command", "TAG_ALREADY_OWN");
				channelMap_TAG_FOR_THIS_ITEM_ALREADY_OWN.put("userInfo", currentUserDataModel.getKey());
				channelMap_TAG_FOR_THIS_ITEM_ALREADY_OWN.put(ClientSideRequestQueueModel.KEY_STRING_TRIGGER_ID, triggerID);
				
				String channelString_TAG_FOR_THIS_ITEM_ALREADY_OWN = gson.toJson(channelMap_TAG_FOR_THIS_ITEM_ALREADY_OWN);
				channel.sendMessage(channelId, channelString_TAG_FOR_THIS_ITEM_ALREADY_OWN);
				
			} else {//まだあなたはこのタグを所持していない
				currentTagDataModel.getM_itemOwnerList().add(currentUserDataModel.getKey());//タグ所有者としての情報をタグに追加
				Datastore.put(currentTagDataModel);
				
				Map<String, Object> map = new HashMap<String, Object>();
				try {
					map.put("command", "TAG_TO_ITEM_OWNER_ADDED");
					map.put("userKey", currentUserDataModel.getKey());
					String myCurrentJsonData = gson.toJson(map);
					
					channel.sendMessage(channelId, myCurrentJsonData);
				} catch (Exception e) {
					debug.trace("まだあなたはこのタグを所持していない_error_"+e);
				}
				
				
			}
			
		} else {
			debug.trace("同名のタグが無い");
			
			//タグの情報をセーブ　　
			TagDataModel newTagData = new TagDataModel();
			/*	private Key key;
				private String m_tagName;
				private List<Key> m_itemOwnerList;
				private List<Key> m_tagOwnerItemList;//一件だけのアイテムの為に、リスト。
			 */
			newTagData.setKey(currentTagkey);
			
			newTagData.setM_tagName(newTagNameString);
			
			List<Key> itemOwnerList = new ArrayList<Key>();//空のリストを作成してセット
			itemOwnerList.add(currentUserDataModel.getKey());//貼った人の情報をセット、
			newTagData.setM_itemOwnerList(itemOwnerList);
			
			List<Key> taggedItemOwnList = new ArrayList<Key>();//空のリストを作成してセット
			taggedItemOwnList.add(currentItemDataModel.getKey());
			newTagData.setM_tagOwnerItemList(taggedItemOwnList);
			
			Datastore.put(newTagData);
			
			
			try {
				debug.trace("アイテムの情報にタグを設置");
				currentItemDataModel.getM_tagList().add(newTagData.getKey());
				Datastore.put(currentItemDataModel);
			} catch (Exception e) {
				debug.trace("error_"+e);
			}
			
			
			debug.trace("タグ作成完了");
			Map<String, Object> map = new HashMap<String, Object>();
			try {
				/*
				 * rootObject.get("itemName").isString().toString(), 
						rootObject.get("tagObject").isObject()
				 */
				map.put("command", ClientSideRequestQueueModel.EVENT_TAG_CREATED);//ここでイベントとして、タグが発行出来た事を知らせる
				map.put("itemName", currentItemDataModel.getKey().getName());
				map.put("taggedItem", Datastore.get(ItemDataModel.class, currentItemDataModel.getKey()));
				map.put("tagObject", newTagData);
				map.put("userInfo", currentUserDataModel.getKey());
				map.put(ClientSideRequestQueueModel.KEY_STRING_TRIGGER_ID, triggerID);
				
				
				String myCurrentJsonData = gson.toJson(map);
				
				channel.sendMessage(channelId, myCurrentJsonData);
			} catch (Exception e) {
				debug.trace("TAG_CREATED_error_"+e);
			}
			
			
		}
		
		return "ok";
	}
	
	/**
	 * 各ユーザーが該当のアイテムに対してセットしてあるタグを、ユーザーごとに取得する。
	 * アイテムにセットしてあるタグ全体を取得したのち、タグx持ち主　分だけブロードキャストする。
	 * アイテムi についての　タグA を　a,b　が持っていたら、
	 * A-a
	 * A-b　
	 * という内容で送る。すげー量。
	 * 　
	 * @param input
	 * @return
	 */
	private String getItemTagByUserQualification (String input) {
		String inputKey = input.substring(ClientSideRequestQueueModel.REQUEST_TYPE_GET_USER_INDIVIDUAL_TAG.length(), input.length());
		
		String triggerID = null;
		
		String userNameString = null;
		String itemNameString = null;
		
		UserDataModel myUserModel = null;
		ItemDataModel currentItem = null;
		try {
			JSONObject rootObject = new JSONObject(inputKey);
			debug.trace("rootObject_"+rootObject);

			triggerID = rootObject.getString(ClientSideRequestQueueModel.KEY_STRING_TRIGGER_ID);
			userNameString = rootObject.getJSONObject("userInfo").getString("name");
			myUserModel = getUserModelFromKeyName(userNameString);
			
			itemNameString = rootObject.getJSONObject("itemInfo").getString("name");
			
			currentItem = getItemDataModelFromItemName(itemNameString);
		} catch (JSONException e) {
			debug.trace("getItemTagByUserQualification_error_"+e);
		}
		
		
		
		//アイテムキーにあるタグリストから、タグを一個ずつ召還、
		//その召還したタグのオーナーごとに、一個ずつメッセージを飛ばす
		
		for (Iterator<Key> currentTagKeyItel = currentItem.getM_tagList().iterator(); currentTagKeyItel.hasNext();) {
			Key currentTagDataModelKey = currentTagKeyItel.next();
			TagDataModel currentTagDataModel = Datastore.get(TagDataModel.class, currentTagDataModelKey);
			
			for (Iterator<Key> userDataModelKeyItel = currentTagDataModel.getM_itemOwnerList().iterator(); userDataModelKeyItel.hasNext();) {
				Key currentUserDataModelKey = userDataModelKeyItel.next();
				UserDataModel otherUserDataModel = Datastore.get(UserDataModel.class, currentUserDataModelKey);
				
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("command", ClientSideRequestQueueModel.REQUEST_TYPE_GET_USER_INDIVIDUAL_TAG);
				map.put(ClientSideRequestQueueModel.KEY_STRING_TRIGGER_ID, triggerID);
				map.put("userInfo", myUserModel.getKey());
				map.put("tagObject", currentTagDataModel);
				map.put("ownerObject", otherUserDataModel);
				String jsonData = gson.toJson(map);//おぞましい量のメッセージになるはず
				channel.sendMessage(channelId, jsonData);
			}
			
		}
		
		return "ok";
	}




	/**
	 * アイテムをデータベースに登録する。　httpのアドレスのみでの登録になる。
	 * すでにある場合、オーナーリストに情報を追加する。
	 * @param input
	 * @return
	 */
	private String addItemQualification(String input) {
		JSONObject jsonDatas = null;
		JSONObject userKeyObject = null;
		String userName = null;
		UserDataModel myUserModel = null;
		
		String triggerID = null;
		try {
			/*
			 * このユーザーキーで、アイテムを登録する。
			 */
			String itemAddressWithUserKey = input.substring(ClientSideRequestQueueModel.REQUEST_TYPE_ADD_ITEM_WITH_URL.length(), input.length());
			jsonDatas = new JSONObject(itemAddressWithUserKey);
			
			userKeyObject = jsonDatas.getJSONObject("userKey");
			userName = userKeyObject.getString("name");
			
			myUserModel = getUserModelFromKeyName(userName);
			triggerID = jsonDatas.getString(ClientSideRequestQueueModel.KEY_STRING_TRIGGER_ID);
		} catch (Exception e) {
			debug.trace("parse_error_"+e);
		}


		//新アイテムの登録と、ユーザーにその所持を設定する。　トランザクションの一致がほしい
		/*
		 * アイテムは、ユーザーキーを入れて登録する、その時、すでにアイテムがDBにあれば、キーのオーナーの値を追加する。
		 */
		String itemName = "pre";
		String itemAddressKey = null;
		try {
			itemAddressKey = jsonDatas.getString("itemAddressKey");
		} catch (Exception e) {
			debug.trace("parse_jsonDatas_"+e);
		}


		debug.trace("itemAddressKey_"+itemAddressKey);
		Key currentItemkey = Datastore.createKey(ItemDataModel.class, itemAddressKey);

		ItemDataModelMeta meta = ItemDataModelMeta.get();
		List<ItemDataModel> items = Datastore.query(meta)
		.filter(meta.key.equal(currentItemkey))
		.asList();




		//すでにアイテムが登録されている場合、そのデータを引き出して加算する
		if (0 < items.size()) {
			//				既に存在しているので、取り出して、持ち主情報を更新
			debug.trace("アイテムがサーバ上に存在している_"+itemAddressKey);

			ItemDataModel currentItemDataModel = Datastore.get(ItemDataModel.class, items.get(0).getKey());
			
			List<Key> ownerList = currentItemDataModel.getM_ownerList();
			
			if (ownerList.contains(myUserModel.getKey())) {
//				debug.trace("すでにこのアイテムは所持されているby_"+myUserModel.getKey());
				
				Map<String, Object> channelMap_ITEM_ALREADY_OWN = new HashMap<String, Object>();
				
				channelMap_ITEM_ALREADY_OWN.put("command", "ITEM_ALREADY_OWN");
				channelMap_ITEM_ALREADY_OWN.put("itemAddressKey", itemAddressKey);
				channelMap_ITEM_ALREADY_OWN.put("requestedItemKey", currentItemDataModel.getKey());
				channelMap_ITEM_ALREADY_OWN.put("userInfo", myUserModel.getKey());
				channelMap_ITEM_ALREADY_OWN.put(ClientSideRequestQueueModel.KEY_STRING_TRIGGER_ID, triggerID);
				
				String channelString_ITEM_ALREADY_OWN = gson.toJson(channelMap_ITEM_ALREADY_OWN);
				
				channel.sendMessage(channelId, channelString_ITEM_ALREADY_OWN);
			} else {
				debug.trace("自分は持ってないけど誰かが持ってる");
				currentItemDataModel.getM_ownerList().add(myUserModel.getKey());//アイテムの情報を追加(おそらくすでに存在する場合とか、ありえそうだ。新規作成時しかここにこないから平気だと思うが。)
				Datastore.put(currentItemDataModel);
				
				Map<String, Object> channelMap_ITEM_OWNER_ADDED = new HashMap<String, Object>();
				channelMap_ITEM_OWNER_ADDED.put("command", "ITEM_OWNER_ADDED");
				channelMap_ITEM_OWNER_ADDED.put("joinedAsNewOwner", currentItemDataModel);
				channelMap_ITEM_OWNER_ADDED.put("userInfo", myUserModel.getKey());
				
				String channelString_ITEM_OWNER_ADDED = gson.toJson(channelMap_ITEM_OWNER_ADDED);
				
				channel.sendMessage(channelId, channelString_ITEM_OWNER_ADDED);
			}
			
			

		} else {//無いので新規作成
			ArrayList<Key> ownerList = new ArrayList<Key>();
			ownerList.add(myUserModel.getKey());

			ItemDataModel itemDataModel = new ItemDataModel();
			itemDataModel.setKey(currentItemkey);

			itemDataModel.setM_itemName(itemName);

			itemDataModel.setM_ownerList(ownerList);

			ArrayList<Key> commentList = new ArrayList<Key>();//空
			itemDataModel.setM_commentList(commentList);

			ArrayList<Key> tagList = new ArrayList<Key>();//空
			itemDataModel.setM_tagList(tagList);

			Datastore.put(itemDataModel);
			debug.trace("アイテム新規作成完了_"+itemAddressKey);

			Map<String , Object> map = new HashMap<String, Object>();
			
			map.put("command", "ITEM_CREATED");
			map.put("value", itemAddressKey);
			map.put("userInfo", myUserModel.getKey());
			map.put(ClientSideRequestQueueModel.KEY_STRING_TRIGGER_ID, triggerID);
			String channelItemCreatedString = gson.toJson(map);
			
			channel.sendMessage(channelId, channelItemCreatedString);
		}




		List<Key> itemKeyList = myUserModel.getItemKeys();
		
		if (itemKeyList.contains(currentItemkey)) {//もってた
			
			Map<String, Object> channelMap_ALREADY_ADDED_TO_USER = new HashMap<String, Object>();
			channelMap_ALREADY_ADDED_TO_USER.put("command", "ALREADY_ADDED_TO_USER");
			channelMap_ALREADY_ADDED_TO_USER.put("itemAddress", itemAddressKey);
			channelMap_ALREADY_ADDED_TO_USER.put("userInfo", myUserModel.getKey());
			String channelString_ALREADY_ADDED_TO_USER = gson.toJson(channelMap_ALREADY_ADDED_TO_USER);
				
			channel.sendMessage(channelId, channelString_ALREADY_ADDED_TO_USER);
		} else {
			myUserModel.getItemKeys().add(currentItemkey);//TODO アイテムの情報を追加(おそらくすでに存在する場合とか、ありえそうだ。新規作成時しかここにこないから平気だと思うが、チェックしておくべき。
			Datastore.put(myUserModel);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("command", "ITEM_ADDED_TO_USER");
			map.put("currentItemkey", currentItemkey);
			map.put("userInfo", myUserModel.getKey());
			map.put(ClientSideRequestQueueModel.KEY_STRING_TRIGGER_ID, triggerID);
			String currentMapString = gson.toJson(map);

			channel.sendMessage(channelId, currentMapString);
		}
		return "ok";
	}
	

	/**
	 * ユーザーモデルについて、名称からデータストア取得したキーオブジェクトを渡すと、本物のセーブされているキーを返す。
	 * そうでなければnullが返る。
	 * @param userKey
	 * @return
	 */
	private UserDataModel getUserModelFromKeyName(String userKeyName) {
		UserDataModelMeta userMeta = UserDataModelMeta.get();
		
		List <UserDataModel> users = Datastore.query(userMeta)
		.filter(userMeta.key.equal(Datastore.createKey(UserDataModel.class, userKeyName)))
		.asList();  
		if (0 < users.size()) {
			debug.assertTrue(users.size() == 1, "getUserKeyFromKey_サイズが大きすぎる");
			return users.get(0);
		}
		
		return null;
	}

	
	
	
	

	/**
	 * アイテムをデータベースから取得する
	 * @param input
	 * @return
	 */
	private String getItemFromKeyQualification(String input) {
		//JSONからKeyを取得する。
		String keySource = input.substring(ClientSideRequestQueueModel.REQUEST_TYPE_GET_ITEM_FROM_KEY.length(), input.length());
		
		debug.trace("getItemFromKeyQualification_keySource_"+keySource);
		JSONObject rootObject = null;
		
		//照会用の自分の名称
		JSONObject userObject = null;
		String myUserKeyName = null;
		UserDataModel myModel = null;
		
		//飛び込んできているアイテムのデータ
		JSONObject itemModelObject = null;
		String itemModelKeyName = null;
		ItemDataModel itemModel = null;
		String triggerID = null;
		try {
			rootObject = new JSONObject(keySource);
			triggerID = rootObject.getString(ClientSideRequestQueueModel.KEY_STRING_TRIGGER_ID);
			userObject = rootObject.getJSONObject("userKey");
			myUserKeyName = userObject.getString("name");
			myModel = getUserModelFromKeyName(myUserKeyName);
			
			itemModelObject = rootObject.getJSONObject("itemKey");
			itemModelKeyName = itemModelObject.getString("name");
			itemModel = getItemDataModelFromItemName(itemModelKeyName);
			
		} catch (Exception e) {
			debug.trace("getItemQualif_parse"+e);
		}
		
		
		/*
		 * アイテム情報を届ける
		 */
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("command", "PUSH_ITEM");
		map.put(ClientSideRequestQueueModel.KEY_STRING_TRIGGER_ID, triggerID);
		map.put("item", itemModel);
		map.put("userInfo", myModel.getKey());
		String itemWithMyself = gson.toJson(map);
		
		channel.sendMessage(channelId, itemWithMyself);
		
		
		/*
		 * アイテムに付随している情報から、タグリストを作成、特にユーザーに関連する物だけ返す
		 */
		if (0 < itemModel.getM_tagList().size()) {
			
			for (Iterator<Key> currentTagDataModelKeyItel = itemModel.getM_tagList().iterator(); currentTagDataModelKeyItel.hasNext();) {
				TagDataModel currentTagDataModel = Datastore.get(TagDataModel.class, currentTagDataModelKeyItel.next());
				if (currentTagDataModel.getM_itemOwnerList().contains(myModel.getKey())) {//タグの持ち主リストに自分がはいっていたら
//					rootObject.get("itemName").isString().toString(), 
//					rootObject.get("tagObject").isObject()
				
					Map<String,Object> tagMap = new HashMap<String,Object>();
					tagMap.put("command", ClientSideRequestQueueModel.EVENT_USER_TAG_RECEIVED);
					tagMap.put("itemName", itemModel.getKey().getName());
					tagMap.put("tagObject", currentTagDataModel);
					tagMap.put("userInfo", myModel.getKey());
					tagMap.put(ClientSideRequestQueueModel.KEY_STRING_TRIGGER_ID, triggerID);
					
					
					String itemNameWithMyTag = gson.toJson(tagMap);
					
					channel.sendMessage(channelId, itemNameWithMyTag);
					
				}
			}
		}

		return "ok";
	}




	/**
	 * アイテムを名称から取得する
	 * @param itemModelKeyName
	 * @return
	 */
	private ItemDataModel getItemDataModelFromItemName(String itemModelKeyName) {
		ItemDataModelMeta itemMeta = ItemDataModelMeta.get();
		
		List <ItemDataModel> items = Datastore.query(itemMeta)
		.filter(itemMeta.key.equal(Datastore.createKey(ItemDataModel.class, itemModelKeyName)))
		.asList();
		if (0 < items.size()) {
			debug.assertTrue(items.size() == 1, "getUserKeyFromKey_サイズが大きすぎる");
			return items.get(0);
		}
		return null;
	}
	
	/**
	 * タグモデルを名称キーから取得する
	 * @param tagName
	 * @return
	 */
	private TagDataModel getTagDataModelFromName(String tagName) {
	TagDataModelMeta tagMeta = TagDataModelMeta.get();
		
		List <TagDataModel> tags = Datastore.query(tagMeta)
		.filter(tagMeta.key.equal(Datastore.createKey(TagDataModel.class, tagName)))
		.asList();  
		if (0 < tags.size()) {
			debug.assertTrue(tags.size() == 1, "getTagKeyFromKey_サイズが大きすぎる");
			return tags.get(0);
		}
		return null;
	}


	/**
	 * ログイン処理を行うメソッド
	 * @param userName
	 * @return
	 */
	private String loginQualification(String input) {

		String userNameWithPass = input.substring(ClientSideRequestQueueModel.REQUEST_TYPE_LOGIN.length(), input.length());
		String[] userNameWithPass2 = userNameWithPass.split(":");
		debug.assertTrue(userNameWithPass2.length == 2, "数が合わない、想定外の:が含まれている");

		String userName = userNameWithPass2[0];
		String userPass = userNameWithPass2[1];
		
		
		
		
		UserDataModelMeta meta = UserDataModelMeta.get();
		List<UserDataModel> users = Datastore.query(meta)
		.filter(meta.m_userName.equal(userName))
		.asList(); 

		//			新規ユーザーであればセーブ、そうでなければパスワード照合、パスワード一致しなければエラーを返す
		//簡易版なので、パスワードが違ったら、ユーザーとして登録してしまえばいいや。

		debug.trace("userName_"+userName);
		debug.trace("userPass_"+userPass);
		String ret = null;

		for (Iterator<UserDataModel> uIteletor = users.iterator(); uIteletor.hasNext();) {
			UserDataModel currentUserDataModel = uIteletor.next();

			if (currentUserDataModel.getM_userName().matches(userName)) {//ユーザーの検索、こういうのってどうやって最適化するのかな。
				if (currentUserDataModel.getM_userPass().matches(userPass)) {
					debug.trace("一致します");
					//currentUserDataModelはユーザー。なので、アイテム情報とかも持ってる筈。
					String channelKey = channel.getChannel(channelId);
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("userData",currentUserDataModel);
					map.put("channelID",channelKey);
					String s = gson.toJson(map);
					
					ret = s;
					return ret;
				} else {
					debug.trace("一致しません");
					//ユーザー名が存在したが、パスワードが一致しない
					ret = "ログインパスワードが違います";
					return ret;
				}
			} 
		}
		
		UserDataModelMeta metaPre = UserDataModelMeta.get();
		List<UserDataModel> usersPre = Datastore.query(metaPre)
		.asList();
		
		//新規登録
		Key key = Datastore.createKey(UserDataModel.class, createUserIdentity(userName,userPass));
		UserDataModel uDataModel = new UserDataModel();
		uDataModel.setKey(key);
		uDataModel.setImageNumber(usersPre.size()+1);//ここでID発行している
		uDataModel.setM_userName(userName);
		uDataModel.setM_userPass(userPass);
		Datastore.put(uDataModel);

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userData",uDataModel);
		String channelKey = channel.getChannel(channelId);
		map.put("channelID",channelKey);

		String s = gson.toJson(map);
		ret = s;
		return ret;
	}


	/**
	 * 暫定版
	 * ユーザー名とユーザーパスワードから、ユーザーのアイデンティティを作成する
	 * 
	 * こんな名前のユーザーがすでにいたら、弾く、などの処理が必要。
	 * メールアドレスなど、外部的にアクセス可能でアイデンティティーを持った値が必要
	 * 
	 * 
	 * @param userName
	 * @param userPass
	 * @return
	 */
	private String createUserIdentity(String userName, String userPass) {
		return userName+"@"+userPass;
	}



}
