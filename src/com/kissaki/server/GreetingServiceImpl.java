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
import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.memcache.MemcacheServicePb.MemcacheDeleteRequest.Item;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import net.arnx.jsonic.JSON;


/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {
	int count = 0;
	Debug debug;
	
	Channel channel = new Channel();
	
	
	static String MASTERKEY = "master";
	String channelId = MASTERKEY;
	
	public GreetingServiceImpl () {
		debug = new Debug(this);
		
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
		
		try {
			/*
			 * アイテムは、ユーザーキーを入れて登録する、その時、すでにアイテムがDBにあれば、キーのオーナーの値を追加する。
			 */
			//仮で、一件セーブしよう。
			String itemName = "仮";
			String address = "http://";
			
			ArrayList<Key> ownerList = new ArrayList<Key>();
			ownerList.add(key);
			debug.trace("オーナー作成完了");
			Key itemkey = Datastore.createKey(ItemDataModel.class, address);//アドレスで一意にする。
			
			debug.trace("オーナー作成完了1");
			ItemDataModel itemDataModel = new ItemDataModel();
			itemDataModel.setKey(itemkey);
			debug.trace("オーナー作成完了2");
			itemDataModel.setM_itemName(itemName);
			
			itemDataModel.setM_ownerList(ownerList);
			
			ArrayList<Key> commentList = new ArrayList<Key>();
			itemDataModel.setM_commentList(commentList);
			
			ArrayList<Key> tagList = new ArrayList<Key>();
			itemDataModel.setM_tagList(tagList);
			
			debug.trace("オーナー作成完了4");
			Datastore.put(itemDataModel);
			debug.trace("オーナー作成完了5");
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
		//JOSNからKeyを取得する。
		String keySource = input.substring("getItemData+".length(), input.length());
		
		Key key = JSON.decode(keySource, Key.class);
		
//		アイテムデータをPushで返す
		
		ItemDataModelMeta meta = ItemDataModelMeta.get();
		List<ItemDataModel> itemList = Datastore.query(meta)
		.filter(meta.key.equal(key))
		.asList(); 
		
		for (Iterator<ItemDataModel> uIteletor = itemList.iterator(); uIteletor.hasNext();) {
			ItemDataModel item = uIteletor.next();
			Map<String,ItemDataModel> map = new HashMap<String,ItemDataModel>();
			
			map.put("itemDataModel",item);
			String itemJSONString = JSON.encode(map);
			channel.sendMessage(channelId, itemJSONString);
		}
		return "ok";
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
					
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("userData",currentUserDataModel);
					map.put("channelID",channel.getChannel(channelId));
					String s = JSON.encode(map);
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
		map.put("channelID",channel.getChannel(channelId));
		String s = JSON.encode(map);
		ret = s;
		return ret;
	}


	
}
