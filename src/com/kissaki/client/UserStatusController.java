package com.kissaki.client;

import com.kissaki.client.subFrame.debug.Debug;

public class UserStatusController {
	Debug debug = null;
	
	int m_userStatus;
	String m_userName = "";
	String m_userPass = "";
	String m_userKey = "";
	
	public final static int STATUS_USER_LOGOUT = -1;
	public final static int STATUS_USER_LOGGING = 0;
	public final static int STATUS_USER_LOGIN = 1;
	
	
	
	
	public UserStatusController () {
		debug = new Debug(this);
	}
	
	
	/**
	 * 取得する
	 * @return
	 */
	public int getUserStatus () {
		return m_userStatus;
	}
	
	public void setUserStatus (int status) {
		m_userStatus = status;
	}


	public String getUserName() {
		return m_userName;
	}
	
	public void setUserName(String name) {
		m_userName = name;
	}


	public String getUserPass() {
		return m_userPass;
	}
	public void setUserPass(String  pass) {
		m_userPass = pass;
	}

	/**
	 * ユーザーのキーを取得する。
	 * @return
	 */
	public String getUserKey() {
		return m_userKey;
	}
	public void setUserKey(String key) {
		m_userKey = key;
	}
}
