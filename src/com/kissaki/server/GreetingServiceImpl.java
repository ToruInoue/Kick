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
		String userKeyString = null;
		String userKeyName = null;
		try {
			rootObject = new JSONObject(itemKeyString);
			JSONObject itemKeyObject = rootObject.getJSONObject("itemKey");
			itemKeyName = itemKeyObject.get("name").toString();
			
			
			JSONObject userKeyObject = rootObject.getJSONObject("userKey");
			userKeyName = userKeyObject.getString("name");
			userKeyString = userKeyObject.toString();
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
			
			if (comments == null) {//このアイテムに関するコメントは一件も無い
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("requested", rootObject);
				map.put("command", "NO_COMMENT");
				
				String currentCommentData = gson.toJson(map);
				debug.trace("一件も無い_"+currentCommentData);
				channel.sendMessage(channelId, currentCommentData);
			}
			
			boolean myself = false;
			
			if (comments != null) {
				//ゲットし終わったら、コメントを取得
				for (Iterator<CommentDataModel> commentItel = comments.iterator(); commentItel.hasNext();) {
					CommentDataModel currentComment = commentItel.next();
	//				private String m_commentBody;
	//				private Date m_commentDate;
					
					Map<String, Object> map = new HashMap<String, Object>();
	
					map.put("requested", rootObject);
					map.put("wholeCommentData", currentComment);
//					map.put("commentMaster", currentComment.getM_commentMasterID());
//					map.put("commentBody", currentComment.getM_commentBody());
//					map.put("commentDate", currentComment.getM_commentDate());
//					map.put("commentedBy", currentComment.getM_commentedBy());
					map.put("command", "COMMENT_DATA");
					
					String s = currentComment.getM_commentMasterID().getName();//gson.toJson(currentComment.getM_commentMasterID());
					debug.trace("userKeyName_"+userKeyName);
					debug.trace("s_"+s);
					String currentCommentData = gson.toJson(map);
					debug.trace("コメントをユーザーに送信する");
					channel.sendMessage(channelId, currentCommentData);
					
					
					if (userKeyName.equals(s)) {//ユーザー名で判断する
						debug.trace("コメント書きの中に、自分が居た");
						myself = true;
						
						Map<String, Object> innerMap = new HashMap<String, Object>();
						innerMap.put("command", "THERE_IS_MY_COMMENT");//自分の新規ウインドウがあったら、それを消す
						String currentMyselfData = gson.toJson(innerMap);
						channel.sendMessage(channelId, currentMyselfData);
					}
				}
			}
			
			//自分がマスターであるアイテムが、無い、ということは、他にどんな要素があるんだろう？
			
			if (!myself) {
				debug.trace("自分がマスターになっているコメントが無い");
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("command", "NO_MY_DATA");
				map.put("userKey", "NO_MY_DATA");
				
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
		
		try {
//			JSONObject userKeyObject = object.getJSONObject("userKey");
			
			userKey = gson.fromJson(input, Key.class);
			//userKey = userKeyObject
		} catch (Exception e) {
			debug.trace("getCurrentUserDataQualification_gson_parse_"+e);
			
		}
		debug.trace("userKey_"+userKey);
		try {
			UserDataModelMeta meta = UserDataModelMeta.get();
			List<UserDataModel> users = Datastore.query(meta)
			//.filter(meta.key.equal(userKey))//まあ、よく考えたらキーをー致させて物を探す、って結構乱暴な気も。多分文字コードの問題。
			.asList();
			
			if (0 < users.size()) {
				//debug.assertTrue(users.size() == 1, "ちょ、おなじキーのプレイヤーが何人もいるんですか");
				
				
				for (Iterator<UserDataModel> userItel = users.iterator(); userItel.hasNext();) {
					UserDataModel myCurrentData = userItel.next();
					if (myCurrentData.getKey().getName().equals(userKey.getName())) {
						
						Map<String, Object> map = new HashMap<String, Object>();
						
						map.put("userOwnItems", myCurrentData.getItemKeys());
						map.put("command", "CURRENT_ITEM_DATA");
						
						myCurrentJsonData = gson.toJson(map);
						
						channel.sendMessage(channelId, myCurrentJsonData);
						break;
					}
					
				}
				
			}
		} catch (Exception e) {
			debug.trace("List<UserDataModel>_users_error_"+e);
		}
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
		String currentItemName = null;
		String taggingUserNameString = null;
		
		try {
			jsonDatas = new JSONObject(input);
			newTaggedItemKeyObject = jsonDatas.getJSONObject("itemKey");
			newTagNameString = jsonDatas.getString("newTag");
			taggingUserKeyObject = jsonDatas.getJSONObject("userKey");
			
			currentItemName = newTaggedItemKeyObject.get("name").toString();
			taggingUserNameString = taggingUserKeyObject.get("name").toString();
		} catch (JSONException e) {
			debug.trace("addTagToItemQualification_"+e);
		}
		
//		Key taggingUserKey = gson.fromJson(taggingUserKeyObject.toString(), Key.class);//gsonで作っちゃ駄目ということ。
		
		newTagKeyString = newTagNameString;//アイテム名との組み合わせがいいな。
		/*
		 * {"itemKey":{"kind":"item", "id":0, "name":"http://a"}, "newTag":"aaaa", "userKey":{"kind":"user", "id":0, "name":"aaaa@bbbb"}}
		 */
		
		
		/*
		 * 事前準備、ユーザーとアイテムの検索をしておく
		 */
		//タグ付けされたアイテムのキー
		Key newTaggedItemKey = Datastore.createKey(ItemDataModel.class, currentItemName);

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
		
		
		Key newTagUserKey = Datastore.createKey(UserDataModel.class, taggingUserNameString);

		List<UserDataModel> users = null;
		try {//該当するアイテムを探す
			UserDataModelMeta userMeta = UserDataModelMeta.get();
			users = Datastore.query(userMeta)
			.filter(userMeta.key.equal(newTagUserKey))
			.asList(); 
		} catch (Exception e) {
			debug.trace("該当するユーザーを探す_"+e);
		}
		
		UserDataModel currentUserDataModel = null;
		if (0 < users.size()) {
			debug.assertTrue(items.size() == 1, "サイズが1ではない");
			currentUserDataModel = Datastore.get(UserDataModel.class, users.get(0).getKey());
		}
		
		
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
				
				JSONObject obj = new JSONObject();
				try {
					obj.put("command", "TAG_FOR_THIS_ITEM_ALREADY_OWN");
//					obj.put("tagIdentity", arg1);
//					obj.put("value", taggingUserKey);
				} catch (Exception e) {
					debug.trace("itemOwnerList.contains(taggingUserKey)_error_"+e);
				}
				channel.sendMessage(channelId, obj.toString());
			} else {//まだあなたはこのタグを所持していない
				currentTagDataModel.getM_itemOwnerList().add(currentUserDataModel.getKey());//タグ所有者としての情報をタグに追加
				Datastore.put(currentTagDataModel);
				
				JSONObject obj = new JSONObject();
				try {
					obj.put("command", "TAG_TO_ITEM_OWNER_ADDED");
//					obj.put("value", taggingUserKey);
				} catch (Exception e) {
					debug.trace("まだあなたはこのタグを所持していない_error_"+e);
				}
				
				channel.sendMessage(channelId, obj.toString());
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
			
			debug.trace("モデルをセット");
			Datastore.put(newTagData);
			debug.trace("セット完了");
			
			
			try {
				debug.trace("アイテムの情報にタグを設置");
				currentItemDataModel.getM_tagList().add(newTagData.getKey());
				Datastore.put(currentItemDataModel);
			} catch (Exception e) {
				debug.trace("error_"+e);
			}
			
			//TODO　ユーザーの持ちタグ情報をユーザーに追加、するか、どうか。アイテムに紐づいてるから、アイテムを捨てるときに捨てればいいのだが。
			
			
			JSONObject obj = new JSONObject();
			try {
				obj.put("command", "TAG_CREATED");
				obj.put("value", newTagNameString);
				obj.put("tagId", currentTagkey);
				
			} catch (Exception e) {
				debug.trace("TAG_CREATED_error_"+e);
			}
			
			channel.sendMessage(channelId, obj.toString());
		}
		
		return "ok";
	}




	/**
	 * **オーナー情報が入ってない
	 * アイテムをデータベースに登録する。
	 * すでにある場合、オーナーリストに情報を追加する。
	 * @param input
	 * @return
	 */
	private String setItemQualification(String input) {
		Key key = null;
		JSONObject jsonDatas = null;
		JSONObject userKey = null;
		String userName = null;

		try {
			/*
			 * このユーザーキーで、アイテムを登録する。
			 */
			String itemAddressWithUserKey = input.substring("setItemData+".length(), input.length());
			debug.trace("itemAddressWithUserKey_"+itemAddressWithUserKey);

			jsonDatas = new JSONObject(itemAddressWithUserKey);
			userKey = jsonDatas.getJSONObject("userKey");
			userName = userKey.getString("name");
			debug.trace("userName_"+userName);

			key = Datastore.createKey(UserDataModel.class, userName);//本当はユーザ名とアドレスで一意にすべき。

		} catch (Exception e) {
			debug.trace("parse_error_"+e);
		}


		try {//新アイテムの登録と、ユーザーにその所持を設定する。　トランザクションの一致がほしい
			/*
			 * アイテムは、ユーザーキーを入れて登録する、その時、すでにアイテムがDBにあれば、キーのオーナーの値を追加する。
			 */
			String itemName = "pre";
			String itemAddressKey = jsonDatas.getString("itemAddressKey");


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

				ItemDataModel currentItemDataMode = Datastore.get(ItemDataModel.class, items.get(0).getKey());
				
				List<Key> ownerList = currentItemDataMode.getM_ownerList();
				
				if (ownerList.contains(key)) {
					debug.trace("すでにこのアイテムは所持されているby_"+key);
					
					JSONObject obj = new JSONObject();
					obj.put("command", "ITEM_ALREADY_OWN");
					obj.put("value", itemAddressKey);

					channel.sendMessage(channelId, obj.toString());
				} else {
					currentItemDataMode.getM_ownerList().add(key);//アイテムの情報を追加(おそらくすでに存在する場合とか、ありえそうだ。新規作成時しかここにこないから平気だと思うが。)
					Datastore.put(currentItemDataMode);
					
					JSONObject obj = new JSONObject();
					obj.put("command", "ITEM_OWNER_ADDED");
					obj.put("value", itemAddressKey);

					channel.sendMessage(channelId, obj.toString());
				}
				
				

			} else {//無いので新規作成
				ArrayList<Key> ownerList = new ArrayList<Key>();
				ownerList.add(key);

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

				JSONObject obj = new JSONObject();
				obj.put("command", "ITEM_CREATED");
				obj.put("value", itemAddressKey);

				channel.sendMessage(channelId, obj.toString());
			}



			/*
			 * で、ユーザー情報も更新する。
			 */
			UserDataModelMeta userMeta = UserDataModelMeta.get();
			List<Key> myKey = Datastore.query(userMeta)
			.filter(userMeta.key.equal(key))
			.asKeyList();
			debug.assertTrue(myKey.size() == 1, "ちょ、何人も居るの");

			if(myKey.size() > 0) {
				UserDataModel myself = Datastore.get(UserDataModel.class, myKey.get(0));
				List<Key> itemKeyList = myself.getItemKeys();
				
				if (itemKeyList.contains(currentItemkey)) {//もってた
					JSONObject obj = new JSONObject();
					obj.put("command", "ALREADY_ADDED_TO_USER");
					obj.put("value", itemAddressKey);

					channel.sendMessage(channelId, obj.toString());
				} else {
					myself.getItemKeys().add(currentItemkey);//アイテムの情報を追加(おそらくすでに存在する場合とか、ありえそうだ。新規作成時しかここにこないから平気だと思うが。)
					Datastore.put(myself);
					
					JSONObject obj = new JSONObject();
					obj.put("command", "ITEM_ADDED_TO_USER");
					obj.put("currentItemkey", currentItemkey);
	
					channel.sendMessage(channelId, obj.toString());
				}
			}

		}catch (Exception e) {
			debug.trace("save?_"+e);
		}
		return "ok";
	}

	/**
	 * アイテムをデータベースから取得する
	 * @param input
	 * @return
	 */
	private String getItemQualification(String input) {
		Key key = null;
		//JSONからKeyを取得する。
		String keySource = input.substring("getItemData+".length(), input.length());
		
		debug.trace("とりにきました_"+keySource);//きっとまたエンコードの問題
		String itemKeyUTF8String = keySource;//myEncode(keySource);
		
		Key getterKey = null;
		try {
			key = gson.fromJson(itemKeyUTF8String, Key.class);
			debug.trace("keyFromGson_"+key.getName());
			
			/*
			 * 名称からキーを作り直した方がいいようだ。
			 * おそらく、文字コードの問題。
			 */
			getterKey = Datastore.createKey(ItemDataModel.class, key.getName());
			debug.trace("getterKey_"+getterKey);
			
		} catch (Exception e) {
			debug.trace("とりにきました_error_"+e);
		}

		debug.assertTrue(key != null, "nullでアイテム召還に到達");
		//		アイテムデータをPushで返す
		
		List<ItemDataModel> itemList = null;
		
		try {
			ItemDataModelMeta meta = ItemDataModelMeta.get();
			itemList = Datastore.query(meta)
			.filter(meta.key.equal(getterKey))
			.asList(); 
		} catch (Exception e) {
			debug.trace("ItemDataModelMeta_get_error_"+e);
		}

		if (0 <itemList.size()) {
			debug.assertTrue(itemList.size() == 1, "同じキーをもったアイテムが複数取得出来てしまった");
			
			for (Iterator<ItemDataModel> uIteletor = itemList.iterator(); uIteletor.hasNext();) {
				ItemDataModel item = uIteletor.next();

				
				try {
//					Key currentItemKey = item.getKey();//こいつをJSONObjectに型情報付きで変換すればいい。
//					
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("item", item);
					map.put("command", "PUSH_ITEM");//mapで書いて、最後にgsonでjsonに変換する。JSONObjectは使わない。
					
					String s = gson.toJson(map);
					
					
					channel.sendMessage(channelId, s.toString());
					
				} catch (Exception e) {
					debug.trace("getItemQualification_error_"+e);
				}

				
			}
		}

		return "ok";
	}



	//	文字列のUTF8エンコードを行う
	private String myEncode(String keySource) {
		try {
			byte b[] = keySource.getBytes("UTF-8");
			String s = new String(b);
			debug.trace("s_"+s);

			if (keySource == s) {
				debug.trace("一緒");
			}



			return s;
		} catch (Exception e) {
			debug.trace("encode_error_"+e);
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
