package com.kissaki.client.userStatusController.userDataModel;

import java.util.HashMap;
import java.util.Map;

import com.kissaki.client.subFrame.debug.Debug;

/**
 * このクライアントで、現在ユーザーが持っている通信を保持するキュー
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
	public final static String REQUEST_TYPE_ADD = "ADD_ITEM";
	public final static String REQUEST_TYPE_GET = "GET_ITEM";
	public final static String REQUEST_TYPE_UPDATE_MYDATA = "MY_CURRENT";

	

	Debug debug;
	
	final String m_dataURL;
	private int m_status;
	private final String m_requestType;
	
	public ClientSideRequestQueueModel (String m_dataURL, String requestTypeGet) {
		debug = new Debug(this);
		
		this.m_dataURL = m_dataURL;
		this.m_requestType = requestTypeGet;
		
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
		return ret;
	}

}
