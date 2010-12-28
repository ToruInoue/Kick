package com.kissaki.client.userStatusController.userDataModel;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.kissaki.client.subFrame.debug.Debug;

/**
 * このユーザーが現在クライアントサイドに持ってるアイテム情報のモデル
 * @author ToruInoue
 *
 */
public class ClientSideCurrentItemDataModel {
	Debug debug;
	
	private JSONObject m_itemdata;
	
	public ClientSideCurrentItemDataModel (JSONObject itemdata) {
		debug = new Debug(this);
		this.m_itemdata = itemdata;
	}
	
	/**
	 * オーナーのキーが入ったアレイを抽出する
	 * @return
	 */
	public JSONArray getOwnerArray () {
		
		JSONArray array = new JSONArray();
		try {
			array = m_itemdata.get("m_ownerList").isArray();
		} catch (Exception e) {
			debug.trace("getOwnerArray_"+e);
		}
		
		if (array != null) {
			return array;
		}
		return null;
	}
	
	/**
	 * タグのアレイを抽出する
	 * このアイテムに着いているタグ全てになるので、
	 * ユーザーのタグではない。
	 * うーーん、、
	 * 
	 * ユーザー所持のタグを抽出する作業を、サーバに任せよう、で、帰ってきた物をここで表示する。
	 * @return
	 */
	public JSONArray getTagArray() {
		JSONArray array = null;// = new JSONArray();
		try {
			if (m_itemdata.get("m_tagList").isArray() != null) {
				array = m_itemdata.get("m_tagList").isArray();
			} else {
				debug.trace("そもそもそんなもの無いっぽい_"+m_itemdata);
				array = new JSONArray();
			}
		} catch (Exception e) {
			debug.trace("getTagArray_"+e);
			array = new JSONArray();
			debug.trace("初期化で対応_"+array);
		}
		debug.assertTrue(array != null, "getTagArrayの返答がnullになりそう");
		return array;
	}
	
	/**
	 * アイテムキーを取得する
	 * @return
	 */
	public JSONObject getItemKey() {
		JSONObject itemKey = m_itemdata.get("key").isObject();
		return itemKey;
	}
	
	public String getItemKeyName () {
		String name = null;
		name = getItemKey().get("name").isString().toString();
		return name;
	}
	
	public JSONObject itemItself () {
		return m_itemdata;
	}
	
}
