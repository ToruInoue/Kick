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






		//ログイン
		if (input.startsWith("userLogin+")) {
			return loginQualification(input);
		}

		if (input.startsWith("getMyData+")) {
			return getCurrentUserDataQualification(input);
		}

		if (input.startsWith("getItemData+")) {
			return getItemQualification(input);
		}

		if (input.startsWith("setItemData+")) {
			return setItemQualification(input);
		}

		if (input.startsWith("addTagToItemData+")) {
			return addTagToItemQualification(input);
		}
		
		if (input.startsWith("addCommentData+")) {
			return addCommentQualification(input);
		}
		
		if (input.startsWith("getCommentData+")) {
			return getCommentQualification(input);
		}
		
		return "default";//HTTP_OKキーを返せばいい
	}


	/**
	 * コメントを追加する
	 * @param input
	 * @return
	 */
	private String addCommentQualification(String input) {
		String itemKeyStringOrigin = input.substring("addCommentData+".length(), input.length());
		debug.trace("addCommentQualification_itemKeyString_"+itemKeyStringOrigin);
		
		/*
		 * コメントを生成、アイテムに追加、アイテムをリロードさせる。
		 * {"comment":"Kick here!", "userKey":{"kind":"user", "id":0, "name":"aaaa@bbbb"}, "itemKey":{"kind":"item", "id":0, "name":"http://a"}}
		 */
		JSONObject rootObject = null;
		//ここでID作成する必要があるのかな。あ、別にいいのか。このユーザーがこのときこのアイテムにコメントした内容？
		//アイテムを取り出して、そこに無条件でコメントを加える
		String commentString = null;
		
		JSONObject itemKeyObject = null;
		String itemKeyString = null;
		
		JSONObject userKeyObject = null;
		String userKeyString = null;
		
		JSONObject masterUserKeyObject = null;
		String masterUserKeyString = null;
		
		try {
			rootObject = new JSONObject(itemKeyStringOrigin);
			commentString = rootObject.getString("comment");
			
			itemKeyObject = rootObject.getJSONObject("itemKey");
			itemKeyString = itemKeyObject.getString("name").toString();
			
			userKeyObject = rootObject.getJSONObject("userKey");
			userKeyString = userKeyObject.getString("name").toString();
			
			masterUserKeyObject = rootObject.getJSONObject("masterUserKey");
			masterUserKeyString = masterUserKeyObject.getString("name").toString();
			
			debug.trace("userKeyString_"+userKeyString);
		} catch (Exception e) {
			debug.trace("addCommentQualification_error_"+e);
		}
		
		Key masterUserKey = Datastore.createKey(UserDataModel.class, masterUserKeyString);
		UserDataModelMeta masterUserMeta = UserDataModelMeta.get();
		List<UserDataModel> masterUsers = Datastore.query(masterUserMeta)
		.filter(masterUserMeta.key.equal(masterUserKey))
		.asList();
		UserDataModel currentMasterUserDataModel = null;
		if (0 < masterUsers.size()) {//さすがに存在している筈だが、存在していないケースが考えられそう。
			debug.assertTrue(masterUsers.size() == 1, "マスターに該当するのが一件以上ある");
			currentMasterUserDataModel = Datastore.get(UserDataModel.class, masterUsers.get(0).getKey());
		}
		
		
		Key userKey = Datastore.createKey(UserDataModel.class, userKeyString);
		UserDataModelMeta userMeta = UserDataModelMeta.get();
		List<UserDataModel> users = Datastore.query(userMeta)
		.filter(userMeta.key.equal(userKey))
		.asList();
		
		UserDataModel currentUserDataModel = null;
		if (0 < users.size()) {//さすがに存在している筈だが、存在していないケースが考えられそう。
			debug.assertTrue(users.size() == 1, "一件以上ある");
			currentUserDataModel = Datastore.get(UserDataModel.class, users.get(0).getKey());
		}
		if (currentUserDataModel == null) {
			debug.assertTrue(false, "該当するユーザーが居ない");
		}
		
		
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
			debug.trace("ここまでは来れる筈_"+commentString+"_"+commentKey);
		}
		
		CommentDataModel currentCommentDataModel = null;
		if (commentKey != null) {
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
	 * 
	 * @param input
	 * @return
	 */
	private String getCommentQualification(String input) {
		String itemKeyString = input.substring("getCommentData+".length(), input.length());
		debug.trace("itemKeyString_"+itemKeyString);
		
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
			debug.trace("getCommentQualification_error_"+e);
		}
		//.getJSONObject("userKey");
		
		Key itemKey = Datastore.createKey(ItemDataModel.class, itemKeyName);
		
		ItemDataModelMeta itemMeta = ItemDataModelMeta.get();
		List<ItemDataModel> items = Datastore.query(itemMeta)
		.filter(itemMeta.key.equal(itemKey))
		.asList();
		debug.trace("getCommentQualification_1");
		
		if (0 < items.size()) {
			debug.assertTrue(items.size() == 1, "コメント取得時に、一意を期待して取得した、キーに該当するアイテムが一件以上存在する");
			/*
			 * このアイテムの所有者を取得、リクエスト元に送りつける
			 */
			ItemDataModel currentItem = Datastore.get(ItemDataModel.class, items.get(0).getKey());
			//コメントを取得する
			List<Key> commentKeyList = currentItem.getM_commentList();
			
			List<CommentDataModel> comments = null;
			
			//まずコメントの一覧を取得する
			for (Iterator<Key> commentKeyItel = commentKeyList.iterator(); commentKeyItel.hasNext();) {
				//このコメントをユーザーに届ける
				Key currentCommentKey = commentKeyItel.next();
				CommentDataModelMeta commentMeta = CommentDataModelMeta.get();
				comments = Datastore.query(commentMeta)
				.filter(commentMeta.key.equal(currentCommentKey))
				.asList();
			}
			Key userKey = Datastore.createKey(UserDataModel.class, userKeyName);
			UserDataModelMeta userMeta = UserDataModelMeta.get();
			List<UserDataModel> users = Datastore.query(userMeta)
			.filter(userMeta.key.equal(userKey))
			.asList();
			debug.assertTrue(users.size() == 1, "ユーザーが引っかかり過ぎ");
			UserDataModel myself = Datastore.get(UserDataModel.class, users.get(0).getKey());
			
			if (comments == null) {//このアイテムに関するコメントは一件も無い
//				userKeyNameでユーザーを取得する
				
				
				
				
				Map<String, Object> myNodataMap = new HashMap<String, Object>();
				
				myNodataMap.put("userInfo", myself.getKey());
				myNodataMap.put("command", "NO_COMMENT");
				
				String currentCommentData = gson.toJson(myNodataMap);
				debug.trace("currentCommentData_"+currentCommentData);
				channel.sendMessage(channelId, currentCommentData);
			}
			
			boolean thereIsMyself = false;
			
			if (comments != null) {
				//ゲットし終わったら、コメントを取得
				for (Iterator<CommentDataModel> commentItel = comments.iterator(); commentItel.hasNext();) {
					CommentDataModel currentComment = commentItel.next();
	//				private String m_commentBody;
	//				private Date m_commentDate;
					
					Map<String, Object> map = new HashMap<String, Object>();
	
					map.put("requested", rootObject);
					map.put("wholeCommentData", currentComment);
					map.put("command", "COMMENT_DATA");
					map.put("userInfo", myself.getKey());
					
					String s = currentComment.getM_commentMasterID().getName();//gson.toJson(currentComment.getM_commentMasterID());
					debug.trace("userKeyName_"+userKeyName);
					debug.trace("s_"+s);
					String currentCommentData = gson.toJson(map);
					debug.trace("コメントをユーザーに送信する");
					channel.sendMessage(channelId, currentCommentData);
					
					
					if (userKeyName.equals(s)) {//ユーザー名で判断する
						debug.trace("コメント書きの中に、自分が居た");
						thereIsMyself = true;
						
						Map<String, Object> innerMap = new HashMap<String, Object>();
						innerMap.put("command", "THERE_IS_MY_COMMENT");//自分の新規ウインドウがあったら、それを消す
						innerMap.put("userInfo", myself.getKey());
						String currentMyselfData = gson.toJson(innerMap);
						channel.sendMessage(channelId, currentMyselfData);
					}
				}
			}
			
			//自分がマスターであるアイテムが、無い、ということは、他にどんな要素があるんだろう？
			
			if (!thereIsMyself) {
				debug.trace("自分がマスターになっているコメントが無い_"+myself.getKey());
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("command", "NO_MY_DATA");
				map.put("userInfo", myself.getKey());

				String currentCommentData = gson.toJson(map);
				channel.sendMessage(channelId, currentCommentData);
			}
		}
		
		
		
		return "ok";
	}


	/**
	 * 最近のユーザーのデータを受け渡す
	 * @param input
	 * @return
	 */
	private String getCurrentUserDataQualification(String input) {
		input = input.substring("getMyData+".length(), input.length());
		debug.trace("getMyData__"+input);
		
		
		Key userKey = null;
		String myCurrentJsonData = null;
		String myUserName = null; 
		try {
			userKey = gson.fromJson(input, Key.class);
			myUserName = userKey.getName();
		} catch (Exception e) {
			debug.trace("getCurrentUserDataQualification_gson_parse_"+e);	
		}
		
		UserDataModel myUserData = getUserModelFromKeyName(myUserName);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("command", "CURRENT_ITEM_DATA");
		map.put("userOwnItems", myUserData.getItemKeys());
		map.put("userInfo", myUserData.getKey());
		
		String currentUserDataString = gson.toJson(map);
		
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
		input = input.substring("addTagToItemData+".length(), input.length());
		debug.trace("input_"+input);
		
		Key currentTagkey = null;
		JSONObject jsonDatas = null;
		JSONObject newTaggedItemKeyObject = null;
		String newTagNameString = null;
		String newTagKeyString = null;
		
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
		} catch (JSONException e) {
			debug.trace("addTagToItemQualification_"+e);
		}
		

		newTagKeyString = newTagNameString;//アイテム名との組み合わせがいいな。
		/*
		 * {"itemKey":{"kind":"item", "id":0, "name":"http://a"}, "newTag":"aaaa", "userKey":{"kind":"user", "id":0, "name":"aaaa@bbbb"}}
		 */
		
		
		/*
		 * 事前準備、ユーザーとアイテムの検索をしておく
		 */
		//タグ付けされたアイテムのキー
		Key newTaggedItemKey = Datastore.createKey(ItemDataModel.class, currentItemNameString);

		debug.trace("newTaggedItemKey_"+newTaggedItemKey.getName());
		
		List<ItemDataModel> items = null;
		try {//該当するアイテムを探す
			ItemDataModelMeta itemMeta = ItemDataModelMeta.get();
			items = Datastore.query(itemMeta)
			.filter(itemMeta.key.equal(newTaggedItemKey))
			.asList(); 
		} catch (Exception e) {
			debug.trace("該当するアイテムを探す_"+e);
		}
		
		ItemDataModel currentItemDataModel = null;
		if (0 < items.size()) {
			debug.assertTrue(items.size() == 1, "サイズが1ではない");
			currentItemDataModel = Datastore.get(ItemDataModel.class, items.get(0).getKey());
		}
		
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
			debug.trace("currentTagkey_存在している_"+currentTagkey);
			//複数個に対応していない
			
			TagDataModel currentTagDataModel = Datastore.get(TagDataModel.class, tags.get(0).getKey());//存在していたタグを引き出す
			
			List<Key> itemOwnerList = currentTagDataModel.getM_itemOwnerList();//このタグがついたアイテムがだれのもちものなのか、引き出す
			
			//タグをセットしにきているので、既にあれば、セットする必要は無いが、
			//タグを付けたのが自分かどうか、という情報は欲しい。
			
			if (itemOwnerList.contains(currentUserDataModel.getKey())) {//あんたの手元にこのタグもうあるじゃん
				debug.trace("すでにこのアイテムについてのタグは所持されているby_"+currentUserDataModel.getKey());
				
				
				
				Map<String, Object> channelMap_TAG_FOR_THIS_ITEM_ALREADY_OWN = new HashMap<String, Object>();
				channelMap_TAG_FOR_THIS_ITEM_ALREADY_OWN.put("command", "TAG_ALREADY_OWN");
				channelMap_TAG_FOR_THIS_ITEM_ALREADY_OWN.put("userInfo", currentUserDataModel.getKey());
				
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
			
			//TODO　ユーザーの持ちタグ情報をユーザーに追加、するか、どうか。アイテムに紐づいてるから、アイテムを捨てるときに捨てればいいのだが。
			
			debug.trace("タグ作成完了");
			Map<String, Object> map = new HashMap<String, Object>();
			try {
				map.put("command", "TAG_CREATED");
				map.put("value", newTagNameString);
				map.put("tagId", currentTagkey);
				map.put("userInfo", currentUserDataModel.getKey());
				
				String myCurrentJsonData = gson.toJson(map);
				
				channel.sendMessage(channelId, myCurrentJsonData);
			} catch (Exception e) {
				debug.trace("TAG_CREATED_error_"+e);
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
	private String setItemQualification(String input) {
		JSONObject jsonDatas = null;
		JSONObject userKeyObject = null;
		String userName = null;
		UserDataModel myUserModel = null;
		try {
			/*
			 * このユーザーキーで、アイテムを登録する。
			 */
			String itemAddressWithUserKey = input.substring("setItemData+".length(), input.length());
			debug.trace("itemAddressWithUserKey_"+itemAddressWithUserKey);

			jsonDatas = new JSONObject(itemAddressWithUserKey);
			userKeyObject = jsonDatas.getJSONObject("userKey");
			userName = userKeyObject.getString("name");
			debug.trace("userName_"+userName);

			myUserModel = getUserModelFromKeyName(userName);
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
			debug.trace("存在している_"+itemAddressKey);

			ItemDataModel currentItemDataModel = Datastore.get(ItemDataModel.class, items.get(0).getKey());
			
			List<Key> ownerList = currentItemDataModel.getM_ownerList();
			
			if (ownerList.contains(myUserModel.getKey())) {
				debug.trace("すでにこのアイテムは所持されているby_"+myUserModel.getKey());
				
				Map<String, Object> channelMap_ITEM_ALREADY_OWN = new HashMap<String, Object>();
				
				channelMap_ITEM_ALREADY_OWN.put("command", "ITEM_ALREADY_OWN");
				channelMap_ITEM_ALREADY_OWN.put("itemAddressKey", itemAddressKey);
				channelMap_ITEM_ALREADY_OWN.put("userInfo", myUserModel.getKey());
				

				String channelString_ITEM_ALREADY_OWN = gson.toJson(channelMap_ITEM_ALREADY_OWN);
				
				channel.sendMessage(channelId, channelString_ITEM_ALREADY_OWN);
			} else {
				debug.trace("自分は持ってないけど誰かが持ってる");
				currentItemDataModel.getM_ownerList().add(myUserModel.getKey());//アイテムの情報を追加(おそらくすでに存在する場合とか、ありえそうだ。新規作成時しかここにこないから平気だと思うが。)
				Datastore.put(currentItemDataModel);
				
				Map<String, Object> channelMap_ITEM_OWNER_ADDED = new HashMap<String, Object>();
				channelMap_ITEM_OWNER_ADDED.put("command", "ITEM_OWNER_ADDED");
				channelMap_ITEM_OWNER_ADDED.put("value", itemAddressKey);
				
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
			myUserModel.getItemKeys().add(currentItemkey);//アイテムの情報を追加(おそらくすでに存在する場合とか、ありえそうだ。新規作成時しかここにこないから平気だと思うが。)
			Datastore.put(myUserModel);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("command", "ITEM_ADDED_TO_USER");
			map.put("currentItemkey", currentItemkey);
			map.put("userInfo", myUserModel.getKey());
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
	private String getItemQualification(String input) {
		//JSONからKeyを取得する。
		String keySource = input.substring("getItemData+".length(), input.length());
		debug.trace("keySource_"+keySource);
		JSONObject rootObject = null;
		
		//照会用の自分の名称
		JSONObject userObject = null;
		String myUserKeyName = null;
		UserDataModel myModel = null;
		
		//飛び込んできているアイテムのデータ
		JSONObject itemModelObject = null;
		String itemModelKeyName = null;
		ItemDataModel itemModel = null;
		try {
			rootObject = new JSONObject(keySource);
			userObject = rootObject.getJSONObject("userKey");
			myUserKeyName = userObject.getString("name");
			myModel = getUserModelFromKeyName(myUserKeyName);
			
			itemModelObject = rootObject.getJSONObject("itemKey");
			itemModelKeyName = itemModelObject.getString("name");
			itemModel = getItemDataModelFromItemName(itemModelKeyName);
			
		} catch (Exception e) {
			debug.trace("getItemQualif_parse"+e);
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("command", "PUSH_ITEM");
		map.put("item", itemModel);
		map.put("userInfo", myModel.getKey());
		String itemWithMyself = gson.toJson(map);
		
		channel.sendMessage(channelId, itemWithMyself);
		
		

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
	 * ログイン処理を行うメソッド
	 * @param userName
	 * @return
	 */
	private String loginQualification(String input) {

		String userNameWithPass = input.substring("userLogin+".length(), input.length());
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
					
					debug.trace("s_"+s);
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
		uDataModel.setImageNumber(usersPre.size()+1);
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
