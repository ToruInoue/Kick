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
	
	private JSONObject m_itemdata;//この中に、名前とかキーとかアレイとかわんさか入ってる
	
	public ClientSideCurrentItemDataModel (JSONObject m_itemdata) {
		debug = new Debug(this);
		this.m_itemdata = m_itemdata;
		
	}
	
	/**
	 * オーナーのキーが入ったアレイを抽出する
	 * @return
	 */
	public JSONArray getOwnerArray () {
		
		JSONArray array = null;
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
	 * @return
	 */
	public JSONArray getTagArray() {
		JSONArray array = null;
		try {
			array = m_itemdata.get("m_tagList").isArray();
		} catch (Exception e) {
			debug.trace("getTagArray_"+e);
		}
		
		if (array != null) {
			return array;
		}
		return null;
	}

	
	/**
	 * アイテムキーを取得する
	 * @return
	 */
	
	public JSONObject getItemKey() {
		JSONObject itemKey = m_itemdata.get("key").isObject();
		return itemKey;
	}
	
	
	
	
	
	
	
	
}
