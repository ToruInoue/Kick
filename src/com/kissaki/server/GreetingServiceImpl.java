package com.kissaki.server;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slim3.datastore.Datastore;

import com.kissaki.client.GreetingService;
import com.kissaki.client.subFrame.debug.Debug;
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
		
		return "default";//HTTP_OKキーを返せばいい
	}


	/**
	 * 最近のユーザーのデータを受け渡す
	 * @param input
	 * @return
	 */
	private String getCurrentUserDataQualification(String input) {
		input = input.substring("getMyData+".length(), input.length());
		debug.trace("input_"+input);
		
		
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
		
		try {
			jsonDatas = new JSONObject(input);
			newTaggedItemKeyObject = jsonDatas.getJSONObject("itemKey");
			newTagNameString = jsonDatas.getString("newTag");
			taggingUserKeyObject = jsonDatas.getJSONObject("userKey");
		} catch (JSONException e) {
			debug.trace("addTagToItemQualification_"+e);
		}
		
		Key taggingUserKey = gson.fromJson(taggingUserKeyObject.toString(), Key.class);
		
		
		newTagKeyString = newTagNameString;//タグ名でキーを作る。適当。　+"@"+newTaggedItemKeyObject.toString()
		debug.trace("newTagKeyString_"+newTagKeyString);
		/*
		 * {"itemKey":{"kind":"item", "id":0, "name":"http://a"}, "newTag":"aaaa", "userKey":{"kind":"user", "id":0, "name":"aaaa@bbbb"}}
		 */
		
		currentTagkey = Datastore.createKey(TagDataModel.class, newTagKeyString);

		/*
		 * アイテムについてるタグ、ではなく、タグが世界中をまたぐ場合を考えたら、このアイテムとの連携が強いこの名前のタグ、という風になるのかな。
		 * それとも、この名称のタグという集団になるのかな。
		 * 
		 * 今回は、このアイテムについているタグにだけ注目すればいいので、そうしてタグ名だけのキーにした。
		 * 
		 * 名前からは検索できるが、ヒットするのは他のアイテムについている同名のタグ、なんてこともあり得る。
		 * 
		 */
		TagDataModelMeta meta = TagDataModelMeta.get();
		List<TagDataModel> tags = Datastore.query(meta)
		.filter(meta.key.equal(currentTagkey))//ドンピシャがあるか否か
		.asList(); 
		
		
		//おなじ語句を入れたら引っかかるゆとり仕様(で、けっきょく同じアイテムのキーをもっているのであれば、という形になる。)
		if (0 < tags.size()) {//同じキーを持った物は、存在しない筈。
			debug.trace("currentTagkey_存在している_"+currentTagkey);
			//複数個に対応していない
			
			TagDataModel currentTagDataModel = Datastore.get(TagDataModel.class, tags.get(0).getKey());//存在していたタグを引き出す
			
			List<Key> itemOwnerList = currentTagDataModel.getM_itemOwnerList();//このタグがついたアイテムがだれのもちものなのか、引き出す
			
			
			debug.trace("taggingUserKey_"+taggingUserKey);
			
			if (itemOwnerList.contains(taggingUserKey)) {//あんたの手元にこのタグもうあるじゃん
				debug.trace("すでにこのアイテムについてのタグは所持されているby_"+taggingUserKey);
				
				JSONObject obj = new JSONObject();
				try {
					obj.put("command", "TAG_FOR_THIS_ITEM_ALREADY_OWN");
					obj.put("value", taggingUserKey);
				} catch (Exception e) {
					debug.trace("itemOwnerList.contains(taggingUserKey)_error_"+e);
				}
				channel.sendMessage(channelId, obj.toString());
			} else {//まだあなたはこのタグを所持していない
				currentTagDataModel.getM_itemOwnerList().add(taggingUserKey);//タグ所有者としての情報をタグに追加
				Datastore.put(currentTagDataModel);
				
				JSONObject obj = new JSONObject();
				try {
					obj.put("command", "TAG_TO_ITEM_OWNER_ADDED");
					obj.put("value", taggingUserKey);
				} catch (Exception e) {
					debug.trace("まだあなたはこのタグを所持していない_error_"+e);
				}
				
				channel.sendMessage(channelId, obj.toString());
			}
			
		} else {
			debug.trace("同名のタグが無い");
			
			TagDataModel newTagData = new TagDataModel();
			
			newTagData.setKey(currentTagkey);
			debug.trace("キーをセット_"+newTagData.getKey());
			
			debug.trace("リストを作成");
			List<Key> taggedItemOwnList = new ArrayList<Key>();
			taggedItemOwnList.add(taggingUserKey);//自分を加えておく
			debug.trace("taggedItemOwnList_"+taggedItemOwnList.size());
			newTagData.setM_itemOwnerList(taggedItemOwnList);
			
			debug.trace("所持アイテム情報を設定");
			Key newTaggedItemKey = gson.fromJson(newTaggedItemKeyObject.toString(), Key.class);
			debug.trace("newTaggedItemKey_"+newTaggedItemKey);
			List<Key> taggedItemKeyList = new ArrayList<Key>();
			taggedItemKeyList.add(newTaggedItemKey);
			newTagData.setM_TagOwnerItemList(taggedItemKeyList);
			
			
			newTagData.setM_tagName(newTagNameString);
			try {
			debug.trace("到達、タグがここで保存出来ない");
			Datastore.put(newTagData);
			debug.trace("どうなってるのかな_");
			} catch (Exception e) {
				debug.trace("どうなってるのかな_error_"+e);
			}
			
			
			JSONObject obj = new JSONObject();
			try {
				obj.put("command", "TAG_CREATED");
				obj.put("value", taggingUserKey);
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
				debug.trace("新規作成完了_"+itemAddressKey);

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
		//新規登録
		Key key = Datastore.createKey(UserDataModel.class, createUserIdentity(userName,userPass));
		UserDataModel uDataModel = new UserDataModel();
		uDataModel.setKey(key);
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
