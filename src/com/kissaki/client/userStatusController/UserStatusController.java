package com.kissaki.client.userStatusController;


//import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import net.sourceforge.htmlunit.corejs.javascript.ScriptableObject;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.kissaki.client.subFrame.debug.Debug;
import com.kissaki.client.userStatusController.userDataModel.ClientSideCurrentItemDataModel;
import com.kissaki.client.userStatusController.userDataModel.ClientSideRequestQueueModel;

public class UserStatusController {
	Debug debug = null;
	
	int m_userStatus;
	String m_userName = "";
	String m_userPass = "";
	JSONObject m_userKey = null;
	
	//リクエストキューモデルのリスト
	ArrayList<ClientSideRequestQueueModel> m_rQueueModelMap;

	//データモデルのリスト
	List<ClientSideCurrentItemDataModel> m_iDataModelMap;
	
	
	public final static int STATUS_USER_LOGOUT = -1;
	public final static int STATUS_USER_LOGGING = 0;
	public final static int STATUS_USER_LOGIN = 1;
	
	
	
	
	public UserStatusController () {
		debug = new Debug(this);
		m_rQueueModelMap = new ArrayList<ClientSideRequestQueueModel>();
		m_iDataModelMap = new ArrayList<ClientSideCurrentItemDataModel>();
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
	public JSONObject getUserKey() {
		return m_userKey;
	}
	public void setUserKey(JSONObject key) {
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
	public void addRequestToRequestQueue (String request, String requestTypeGet) {
		m_rQueueModelMap.add(new ClientSideRequestQueueModel(request, requestTypeGet));//既に同名の物があった場合どうなるのやら。
	}
	
	/**
	 * リクエストで未実行のものを実行する
	 */
	public Map<String,String> executeQueuedRequest () {
		
		int i = 0;
		//for (i = 0; i < m_uQueueModelMap.size(); i++) {
		for (Iterator<ClientSideRequestQueueModel> qIteletor = m_rQueueModelMap.iterator(); qIteletor.hasNext(); i++) {
			ClientSideRequestQueueModel current = qIteletor.next();//m_uQueueModelMap.get(i);
			if (current.isNotYet()) {
				
				//通信として実行する
				current.setLoading();
				
				//ロード時の文字列を送る。
				return current.getLoadingRequestObject();//一件ずつ実行する
			}
		}
		return null;
	}
	
	/**
	 * 指定したリクエストを取得完了にする
	 * @param request
	 */
	public void completeRequest (String request) {
		
		int i = 0;
		for (Iterator<ClientSideRequestQueueModel> qIteletor = m_rQueueModelMap.iterator(); qIteletor.hasNext(); i++) {
			ClientSideRequestQueueModel current = qIteletor.next();
			
			if (current.getM_dataURL().equals(request)) {
				//通信として実行完了したマークを付ける(直前にローディングになっている必要があるが、仕様としてmustではないためチェックしていない。)
				current.setLoaded();
			}
		}
	}

	
	/**
	 * アイテム情報を保存する
	 * @param item
	 */
	public void putItemData(JSONObject item) {
		m_iDataModelMap.add(new ClientSideCurrentItemDataModel(item));
	}

	
	/**
	 * アイテム一覧を渡す
	 * @return
	 */
	public List<ClientSideCurrentItemDataModel> getCurrentItems() {
		return m_iDataModelMap;
	}

	/**
	 * 最新のアイテムデータをうけとり、それを現在のアイテムデータと比較、
	 * 一致しなければ、一致しない部分について、
	 * 
	 * ・ユーザーのアイテムデータを更新
	 * ・リクエストを再構成
	 * ・再描画
	 * 
	 * 、、なんだけど、今は比較がめんどいから全とっかえで取得。
	 * 
	 * 
	 * @param array
	 */
	public void compareItemData(JSONArray array) {
		//JSONObject newItem = JSONParser.parseStrict(newArrivalItemData).isArray();
		debug.trace("newArrivalItemData_"+array);
		
		//差分、でどうやって最大効率を出すかは、考えどころ。JSだし。
		debug.trace("m_iDataModelMap_before_"+m_iDataModelMap.size());
		eraseAllItemData();
		
		debug.trace("m_iDataModelMap_now_"+m_iDataModelMap.size());
		putItemDataFromArray(array);
		debug.trace("m_iDataModelMap_after_"+m_iDataModelMap.size());
	}


	/**
	 * m_iDataModelMapに全データを入れる
	 * @param array
	 */
	private void putItemDataFromArray(JSONArray array) {
		for (int i = 0; i < array.size(); i++) {
			JSONObject obj = array.get(i).isObject();
			debug.trace("obj_"+obj);
			
			putItemData(obj);
		}
	}


	/**
	 * m_iDataModelMapから全データを消去する
	 */
	private void eraseAllItemData() {
		int count = m_iDataModelMap.size();
		
		for (int i = 0; i < count; i++) {
			m_iDataModelMap.remove(0);//きちんとarrayが実装されていれば、問題ないと思うのだが、、、
		}
		
		debug.assertTrue(m_iDataModelMap.size() == 0, "m_iDataModelMapのリセットに失敗");
		
//		m_iDataModelMap = new ArrayList<ClientSideCurrentItemDataModel>();//奇麗さっぱり、、出来るかな？
	}



	
}
