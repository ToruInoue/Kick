package com.kissaki.client.userStatusController.userDataModel;

import java.util.HashMap;
import java.util.Map;


import com.kissaki.client.subFrame.debug.Debug;
import com.kissaki.client.uuidGenerator.UUID;

/**
 * このクライアントで、現在ユーザーが持っている通信を保持するキュー
 * URLを直接キーとして使用する。
 * 
 * TODO ID管理が必要
 * 
 * @author ToruInoue
 *
 */
public class ClientSideRequestQueueModel {
	private static final int REQUEST_STATUS_YET = -1;
	private static final int REQUEST_STATUS_LOADING = REQUEST_STATUS_YET+1;
	private static final int REQUEST_STATUS_LOADED = REQUEST_STATUS_LOADING+1;

	public final static String REQUEST_TYPE_GET_TAG_FROM_ITEM = "GET_TAG_BY_ITEM";
	public final static String REQUEST_TYPE_GET_TAG_FROM_PERSON = "GET_TAG_BY_YOU";
	public final static String REQUEST_TYPE_ADDNEWTAG = "ADD_TAG";
	public final static String REQUEST_TYPE_UPDATE_TAG = "UPDATE_TAG";
	public final static String REQUEST_TYPE_ADD_ITEM = "ADD_ITEM";
	public final static String REQUEST_TYPE_GET_ITEM_FROM_KEY = "GET_ITEM_FROM_KEY";
	public final static String REQUEST_TYPE_GET_ITEM_FROM_ADDRESS = "GET_ITEN_FROM_ADDRESS";
	public final static String REQUEST_TYPE_UPDATE_MYDATA = "MY_CURRENT";
	
	public final static String REQUEST_TYPE_GETALLCOMMENT = "GET_ALLCOMMENT";
	public final static String REQUEST_TYPE_GET_LATESTCOMMENT = "GET_LATESTCOMMENT";
	public final static String REQUEST_TYPE_ADDCOMMENT = "ADD_COMMENT";
	public final static String REQUEST_TYPE_GET_USER_INDIVIDUAL_TAG = "GET_USER_INDIVIDUAL_TAG";
	
	
	
	public final static String EVENT_TAG_CREATED = "tag_created";
	public final static String EVENT_USER_TAG_RECEIVED = "user_tag_received";
	
	public final static String KEY_STRING_TRIGGER_ID = "trigger_id";
	
	

	Debug debug;
	
	final String m_dataURL;
	final String m_id;
	private int m_status;
	private final String m_requestType;
	
	public ClientSideRequestQueueModel (String m_dataURL, String requestTypeGet) {
		debug = new Debug(this);
		
		this.m_dataURL = m_dataURL;
		this.m_requestType = requestTypeGet;
		
		this.m_id = UUID.uuid(8,16);
		
		setM_status(REQUEST_STATUS_YET);
	}

	public boolean isNotYet() {
		if (getM_status() == REQUEST_STATUS_YET) return true;
		return false;
	}
	
	public void setM_status(int m_status) {
		this.m_status = m_status;
	}

	public int getM_status() {
		return m_status;
	}

	public void setLoading() {
		setM_status(REQUEST_STATUS_LOADING);
	}
	
	public void setLoaded() {
		setM_status(REQUEST_STATUS_LOADED);
	}

	public String getM_dataURL() {
//		debug.trace("m_dataURL_"+m_dataURL);
		return m_dataURL;
	}
	
	public String getLoadingRequest() {
		return getM_dataURL();
	}

	public String getM_requestType() {
		return m_requestType;
	}

	/**
	 * ユーザーキーと登録したいアイテムの値を書く
	 * @return
	 */
	public Map<String,String> getLoadingRequestObject() {
		Map<String,String> ret = new HashMap<String,String>();
		ret.put(getM_requestType(), getLoadingRequest());
		ret.put(ClientSideRequestQueueModel.KEY_STRING_TRIGGER_ID, getM_id());
		return ret;
	}

	public String getM_id() {
		return m_id;
	}

	/**
	 * 終了状態の判断をおこなう
	 * @return
	 */
	public boolean isFinished() {
		if (getM_status() == REQUEST_STATUS_LOADED) {
			return true;
		}
		return false;
	}

}
