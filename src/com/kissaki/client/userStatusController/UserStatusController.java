package com.kissaki.client.userStatusController;


//import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.google.gwt.json.client.JSONObject;
import com.kissaki.client.subFrame.debug.Debug;
import com.kissaki.client.userStatusController.userDataModel.UserDataModel;
import com.kissaki.client.userStatusController.userDataModel.UserQueueModel;

public class UserStatusController {
	Debug debug = null;
	
	int m_userStatus;
	String m_userName = "";
	String m_userPass = "";
	String m_userKey = "";
	
	//キューモデルのリスト
	ArrayList<UserQueueModel> m_uQueueModelMap;//Listが使えるなら、中に取得済みかどうかのフラグ持っておけば、後で見れていいよね。

	//データモデルのリスト
	List<UserDataModel> m_uDataModelMap;//Listが使えるなら、中に取得済みかどうかのフラグ持っておけば、後で見れていいよね。
	
	
	public final static int STATUS_USER_LOGOUT = -1;
	public final static int STATUS_USER_LOGGING = 0;
	public final static int STATUS_USER_LOGIN = 1;
	
	
	
	
	public UserStatusController () {
		debug = new Debug(this);
		m_uQueueModelMap = new ArrayList<UserQueueModel>();
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


	/**
	 * ユーザーが所持しているアイテムの情報を追加する
	 * @param key
	 */
	public void addItemOwnInformation(JSONObject key) {
		debug.trace("addItemOwnInformation_key_"+key);
	}
	
	/**
	 * リクエストをセットする
	 * @param request
	 */
	public void addRequestToRequestQueue (String request) {
		m_uQueueModelMap.add(new UserQueueModel(request));//既に同名の物があった場合どうなるのやら。
	}
	
	/**
	 * リクエストで未実行のものを実行する
	 */
	public String executeQueuedRequest () {
		int i = 0;
		//for (i = 0; i < m_uQueueModelMap.size(); i++) {
		for (Iterator<UserQueueModel> qIteletor = m_uQueueModelMap.iterator(); qIteletor.hasNext(); i++) {
			UserQueueModel current = qIteletor.next();//m_uQueueModelMap.get(i);
			if (current.isNotYet()) {
				//通信として実行する
				
				current.setLoading();
				
				//ロード時の文字列を送る。
				return current.getLoadingRequest();//一件ずつ実行する
			}
		}
		return null;
	}
	
	
}
