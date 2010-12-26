package com.kissaki.client.ItemOwnersViewController;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.kissaki.client.KickController;
import com.kissaki.client.KickStatusInterface;
import com.kissaki.client.subFrame.debug.Debug;
import com.kissaki.client.userStatusController.userDataModel.ClientSideRequestQueueModel;

public class ItemOwnersViewController implements KickStatusInterface {

	Debug debug;
	KickController kCont;
	/*
	 * コンストラクタ
	 */
	public ItemOwnersViewController (KickController kCont) {
		debug = new Debug(this);
		this.kCont = kCont;
	}
	
	
	/**
	 * このステートでの実行メソッド
	 * @param state
	 * @param exec
	 */
	public void executeSTATUS_KICK_OWNERS_INITIALIZE_2 (String exec) {
		/*
		 * 起動時に直接この画面に来る場合
		 */
		if (exec.startsWith("initializeOwning")) {
			kCont.setKickStatus(STATUS_KICK_OWNERS_PROC_2);
			
			//フォーカスをログイン時のアイテムに設定する
			kCont.getUStCont().setM_nowFocusingItemAddress(kCont.getUStCont().getM_loginItemPath());
			
			
			//自分の最新データを取得
			kCont.getUStCont().addRequestToRequestQueue(kCont.getUStCont().getUserKey().toString(), ClientSideRequestQueueModel.REQUEST_TYPE_UPDATE_MYDATA);
			kCont.procQueExecute(kCont.getUStCont().getUserKey());
			
			
			/*
			 * アイテムの結果が帰ってくる筈なので、
			 * 帰ってきたら、そのキーを元に
			 * コメント取得を行う。
			 */
		}
	}
	
	public void executeSTATUS_KICK_OWNERS_PROC_2 (String exec) {
	
		if (exec.startsWith("JoinedAsNewOwner+")) {
			//どのリクエストが叶ったか、判断してから動きたいが。
			debug.trace("アイテムが保存されたそうなので、自分の情報を更新します");
			kCont.getUStCont().addRequestToRequestQueue(kCont.getUStCont().getUserKey().toString(), ClientSideRequestQueueModel.REQUEST_TYPE_UPDATE_MYDATA);
			kCont.procQueExecute(kCont.getUStCont().getUserKey());
		}
		
		/*
		 * ユーザーデータのアップデートが通達された
		 * フォーカスしているアイテムの情報を確認しにいく
		 */
		if (exec.startsWith("ItemUpdated+")) {
			
			JSONObject userKeyWithAddress = new JSONObject();
			userKeyWithAddress.put("userKey", kCont.getUStCont().getUserKey());
			userKeyWithAddress.put("itemAddressAsIdentifier", new JSONString(kCont.getUStCont().getM_nowFocusingItemAddress()));
			
			kCont.getUStCont().addRequestToRequestQueue(userKeyWithAddress.toString(), ClientSideRequestQueueModel.REQUEST_TYPE_GET_ITEM_FROM_ADDRESS);
			kCont.procQueExecute(kCont.getUStCont().getUserKey());
		}
		/*
		 * キーからアイテムを取得する
		 */
		if (exec.startsWith("GetAllCommentOfItem+")) {
			String itemKey = exec.substring("GetAllCommentOfItem+".length(), exec.length());
			
			kCont.getUStCont().addRequestToRequestQueue(itemKey, ClientSideRequestQueueModel.REQUEST_TYPE_GETALLCOMMENT);
			kCont.procQueExecute(kCont.getUStCont().getUserKey());//サーバにリクエストを送りこむ
		}
		
		/*
		 * アイテムが見つかったので、コメントを取得する
		 */
		if (exec.startsWith("ItemFound+")) {
			JSONObject itemKeyObject = null;
			try {
				String itemData = exec.substring("ItemFound+".length(), exec.length());
				JSONObject itemDataObject = JSONParser.parseStrict(itemData).isObject();
				debug.trace("オブジェクトはあった_"+itemDataObject);
				itemKeyObject = itemDataObject.get("requested").isObject().get("key").isObject();
			} catch (Exception e) {
				debug.trace("アイテムが見つかったよ__e_"+e);
			}
			
			JSONObject itemKeyWithUserKey = new JSONObject();
			
			itemKeyWithUserKey.put("itemKey", itemKeyObject);
			itemKeyWithUserKey.put("userKey", kCont.getUStCont().getUserKey());
//			LoadingOwnersOfItemでやっちまったほうが一元化できる。
			
			
			kCont.setKickStatus(STATUS_KICK_OWNERS_INIT);
			kCont.procedure("LoadingOwnersOfItem+"+itemKeyWithUserKey);
			//ユーザー画像が出ない、あと、ウインドウが出ない(分派が遅すぎるポイントが複数ある、ということ。)
			/*
			 * ユーザー画像IDと、あと何よりも画面要素。
			 * ココからの初期化で
			 */
		}
		
	}

	
	
}
