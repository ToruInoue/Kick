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
import com.kissaki.server.userDataModel.UserDataModel;
import com.kissaki.server.userDataModel.UserDataModelMeta;
import com.kissaki.shared.FieldVerifier;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.memcache.MemcacheServicePb.MemcacheDeleteRequest.Item;
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
			//ユーザー名とパスワードが送られてきていれば、そのまま。
			
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
			
			return loginQualification(users,userName,userPass);
		}
		
		
		if (input.startsWith("getItemData+")) {
			return getItemQualification(input);
		}
		
		if (input.startsWith("setItemData+")) {
			debug.trace("アイテムデータのセットとして到着");
			return setItemQualification(input);
		}
		
		return "default";//HTTP_OKキーを返せばいい
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
		try {
		/*
		 * このユーザーキーで、アイテムを登録する。
		 */
		String userKey = input.substring("setItemData+".length(), input.length());
		
		//userKey_{"complete":true, "id":0, "kind":"user", "name":"test@aaaa", "namespace":"", "parent":null}
		debug.trace("userKey_"+userKey);
		Key dummyKey = Datastore.createKey(UserDataModel.class, "test@aaaa");//アドレスで一意にする。
		debug.trace("dummyKey_"+dummyKey);
		key = dummyKey;//JSON.decode(userKey, Key.class);//デコードでは作り出せない、、
		debug.trace("key_"+key);
		}catch (Exception e) {
			debug.trace("error_"+e);
		}
		
		
		try {//新アイテムの登録と、ユーザーにその所持を設定する。　トランザクションの一致がほしい
			/*
			 * アイテムは、ユーザーキーを入れて登録する、その時、すでにアイテムがDBにあれば、キーのオーナーの値を追加する。
			 */
			//仮で、一件セーブしよう。
			String itemName = "仮";
			String address = "http://";
			
			ArrayList<Key> ownerList = new ArrayList<Key>();
			ownerList.add(key);
			Key itemkey = Datastore.createKey(ItemDataModel.class, address);//アドレスで一意にする。
			
			ItemDataModel itemDataModel = new ItemDataModel();
			itemDataModel.setKey(itemkey);
			
			itemDataModel.setM_itemName(itemName);
			
			itemDataModel.setM_ownerList(ownerList);
			
			ArrayList<Key> commentList = new ArrayList<Key>();
			itemDataModel.setM_commentList(commentList);
			
			ArrayList<Key> tagList = new ArrayList<Key>();
			itemDataModel.setM_tagList(tagList);
			
			Datastore.put(itemDataModel);
			
			
			/*
			 * で、ユーザー情報も更新する。
			 */
			debug.trace("ユーザーさんに持たせます");
			UserDataModelMeta meta = UserDataModelMeta.get();
			List<Key> myKey = Datastore.query(meta)
			                    .filter(meta.key.equal(key))
			                    .asKeyList();
			debug.assertTrue(myKey.size() == 1, "ちょ、何人も居るの");
			if(myKey.size() > 0) {
				UserDataModel myself = Datastore.get(UserDataModel.class, myKey.get(0));
				myself.getItemKeys().add(itemkey);//アイテムの情報を追加(おそらくすでに存在する場合とかw)
			    Datastore.put(myself);
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
//		keySourceのエンコードをしらべると、別のものになるんじゃなかろうか。
		debug.trace("before");
		String itemKeyUTF8String = encode(keySource);
		debug.trace("itemKeyUTF8String_"+itemKeyUTF8String);
		
		try {
			key = gson.fromJson(itemKeyUTF8String, Key.class);
			debug.trace("keyFromGson_"+key);
		} catch (Exception e) {
			debug.trace("error_"+e);
		}
		
		debug.assertTrue(key != null, "nullでアイテム召還に到達");
//		アイテムデータをPushで返す
		
		ItemDataModelMeta meta = ItemDataModelMeta.get();
		List<ItemDataModel> itemList = Datastore.query(meta)
		//.filter(meta.key.equal(key))//ここが一致しない、エンコードかな。 一致出来るようになってからにしよう
		.asList(); 
		
		debug.trace("itemList_"+itemList);//これがそんざいしてないんだよね。
		
		if (itemList != null) {
			for (Iterator<ItemDataModel> uIteletor = itemList.iterator(); uIteletor.hasNext();) {
				ItemDataModel item = uIteletor.next();
				Map<String,ItemDataModel> map = new HashMap<String,ItemDataModel>();
				
				debug.trace("item.getKey()_"+item.getKey());
				
				map.put("itemDataModel",item);
				String itemJSONString = gson.toJson(map);
				channel.sendMessage(channelId, itemJSONString);
			}
		}
		
		return "ok";
	}


	private String encode(String keySource) {
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
	 * @param users
	 * @param userName
	 * @param userPass
	 * @return
	 */
	private String loginQualification(List<UserDataModel> users,
			String userName, String userPass) {
		
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
					//String s = JSON.encode(map);
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
		Key key = Datastore.createKey(UserDataModel.class, userName+"@"+userPass);
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


	
}
