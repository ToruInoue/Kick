package com.kissaki.client.userStatusController.userDataModel;

import com.kissaki.client.subFrame.debug.Debug;

/**
 * このクライアントで、現在ユーザーが持っている通信を保持するキュー
 * @author ToruInoue
 *
 */
public class UserQueueModel {
	private static final int REQUEST_STATUS_YET = -1;
	private static final int REQUEST_STATUS_LOADING = REQUEST_STATUS_YET+1;
	private static final int REQUEST_STATUS_LOADED = REQUEST_STATUS_LOADING+1;
	

	Debug debug;
	
	final String m_dataURL;
	private int m_status;
	
	public UserQueueModel (String m_dataURL) {
		debug = new Debug(this);
		this.m_dataURL = m_dataURL;
		
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

}
