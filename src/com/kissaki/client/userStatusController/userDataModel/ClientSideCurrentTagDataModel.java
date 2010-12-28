package com.kissaki.client.userStatusController.userDataModel;

import com.google.gwt.json.client.JSONObject;
import com.kissaki.client.subFrame.debug.Debug;

public class ClientSideCurrentTagDataModel {
	Debug debug;
	private JSONObject m_tagObject;
	/**
	 * コンストラクタ
	 * @param tagObject
	 */
	public ClientSideCurrentTagDataModel(JSONObject tagObject) {
		debug = new Debug(this);
		m_tagObject = tagObject;
		
	}
	
	public JSONObject getM_tagObject () {
		return m_tagObject;
	}
	
	//タグのキー、名前、
	public JSONObject getKey() {
		return m_tagObject.get("key").isObject();
	}
	
	
	public String getKeyName() {
		return getKey().get("name").isString().toString();
	}

}
