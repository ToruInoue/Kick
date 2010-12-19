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
	
	public int getUserImageNumber () {
		return 1;
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
		try {
			debug.trace("request_"+request);
			m_rQueueModelMap.add(new ClientSideRequestQueueModel(request, requestTypeGet));
			debug.trace("request_inserted_"+request);
		} catch (Exception e) {
			debug.trace("addRequestToRequestQueue_error"+e);
			while (true) {
				if (false) break;
			}
		}
	}
	
	/**
	 * リクエストで未実行のものを、Mapとして返す
	 */
	public Map<String,String> getExecutableQueuedRequest () {
		debug.trace("execute_retMap_start");
		int i = 0;
		//for (i = 0; i < m_uQueueModelMap.size(); i++) {
		for (Iterator<ClientSideRequestQueueModel> qIteletor = m_rQueueModelMap.iterator(); qIteletor.hasNext(); i++) {
			ClientSideRequestQueueModel current = qIteletor.next();//m_uQueueModelMap.get(i);
			if (current.isNotYet()) {
				debug.trace("current_"+current);
				//通信として実行中の状態に設定する
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

	/**
	 * 最新の所持アイテムキーのアレイを受け取ったので、
	 * 手持ちのものと比較して処理をセットする。
	 * 
	 * レイヤー状の処理が楽しい。
	 * @param owningItemKeyArray
	 */
	public void compareToCurrentRequest(JSONObject userKey, JSONArray owningItemKeyArray) {
		//名前と合致するか確認し、合致するものが無ければ新規取得が必要なものとしてセットする。
		//{"kind":"item", "id":0, "name":"http://a"}
		for (int i = 0; i < owningItemKeyArray.size(); i++) {
			
			JSONObject currentObject = owningItemKeyArray.get(i).isObject();
			//アイテムキーと、自分のキーのペアを送る。
			
			JSONObject userKeyWithItemKey = new JSONObject();
			
			debug.trace("currentAddress_"+currentObject.toString());
			
			userKeyWithItemKey.put("itemKey", currentObject);
			userKeyWithItemKey.put("userKey", userKey);
			checkContainsDataURL(userKeyWithItemKey.toString());//比較して存在しなければ、追加する
		}
	}
	
	/**
	 * アイテムの内部を探索、一致したら戻る
	 * @param currentAddress
	 */
	private void checkContainsDataURL (String currentAddress) {
		for (Iterator<ClientSideRequestQueueModel> requestModelItel = m_rQueueModelMap.iterator(); requestModelItel.hasNext();) {	
			try {
				
				ClientSideRequestQueueModel currentModel = requestModelItel.next();
				debug.trace("currentModel_"+currentModel.getM_dataURL());
				if (currentModel.getM_dataURL().equals(currentAddress)) {//アドレス一致するものがある
					//アドレス一致する = 含まれている
					debug.trace("アドレスがすでにQueueに含まれている_"+currentAddress);
					return;//第2層なので、これ以上探す必要が無い
				}
				
				if (!requestModelItel.hasNext()) {
					//address = address.substring(1,currentAddress_.length()-1);
					debug.trace("含まれていないので追加する_アドレスしか追加していないので、注意。"+currentAddress);
					
					addRequestToRequestQueue(currentAddress, ClientSideRequestQueueModel.REQUEST_TYPE_GET_ITEM);
					return;
				}
			} catch (Exception e) {
				debug.trace("ConcurrentModifi_error_"+e);
				e.printStackTrace();
				while (true) {
					if (false) break;
				}
			}
		}	
	}




	
}
