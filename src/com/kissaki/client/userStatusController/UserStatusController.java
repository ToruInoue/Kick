package com.kissaki.client.userStatusController;


//import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.kissaki.client.subFrame.debug.Debug;
import com.kissaki.client.userStatusController.userDataModel.ClientSideCurrentItemDataModel;
import com.kissaki.client.userStatusController.userDataModel.ClientSideCurrentTagDataModel;
import com.kissaki.client.userStatusController.userDataModel.ClientSideRequestQueueModel;

public class UserStatusController {
	Debug debug = null;
	
	int m_userStatus;
	String m_userName = "";
	String m_userPass = "";
	JSONObject m_userKey = null;
	int m_imageNumber = -1;
	
	//リクエストキューモデルのリスト
	ArrayList<ClientSideRequestQueueModel> m_rQueueModelMap;

	//データモデルのリスト
	List<ClientSideCurrentItemDataModel> m_iDataModelMap;

	//ユーザーのタグのリスト
	//アイテムのキーに紐づく形で、マップ-リスト　になる。
	Map<String, ClientSideTagMapModel> m_tagMapModel;
	
	private JSONArray m_userItemArray;//ユーザー情報取得時に所持しているアイテムのキー一覧
	
	private String m_loginItemPath;//ログイン時に所持しているアイテムのパス
	private String m_nowFocusingItemAddress;//現在このユーザーがフォーカスしているアイテムのアドレス
	private JSONObject m_nowFocusingItemKey;//現在フォーカスしているアイテムのKey
	
	public final static int STATUS_USER_LOGOUT = -1;
	public final static int STATUS_USER_LOGGING = 0;
	public final static int STATUS_USER_LOGIN = 1;
	
	
	
	
	public UserStatusController () {
		debug = new Debug(this);
		m_rQueueModelMap = new ArrayList<ClientSideRequestQueueModel>();
		m_iDataModelMap = new ArrayList<ClientSideCurrentItemDataModel>();
		
		m_userItemArray = new JSONArray();
		m_tagMapModel = new HashMap<String, ClientSideTagMapModel>();
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
	
	/**
	 * 特定のアイテムについて、自分のタグを特にコレクションする。
	 * @param itemName
	 * @param tagObject
	 */
	public void setM_tagMapModel (String itemName, JSONObject tagObject) {//アイテムのキーをキーに、タグデータのアレイを作成する。
		if (m_tagMapModel.containsKey(itemName)) {
			//すでにこのアイテムにはタグマップがある。ので、それを取得して足す
			ClientSideTagMapModel currentClientSideTagMapModel = m_tagMapModel.get(itemName);
			
			currentClientSideTagMapModel.addM_tagArray(tagObject);//すでにあれば、何も起こらない
			
		} else {
			debug.trace("既に持っているタグを押した");
			ClientSideTagMapModel newClientSideTagMapModel = new ClientSideTagMapModel();
			m_tagMapModel.put(itemName, newClientSideTagMapModel);
			newClientSideTagMapModel.addM_tagArray(tagObject);
		}
	}

	//指定したアイテムについてのタグを取得する
	public JSONArray getagArrayFromM_tagMapModelByItemName(String itemName) {
		JSONArray currentArray = new JSONArray();
		
		if (m_tagMapModel.containsKey(itemName)) {
			
			int i = 0;
			List<ClientSideCurrentTagDataModel> currentList = m_tagMapModel.get(itemName).getM_tagArray();
			for (Iterator<ClientSideCurrentTagDataModel> currentClientSideCurrentTagDataModelItel = currentList.iterator(); currentClientSideCurrentTagDataModelItel.hasNext(); i++) {//arrayをlistにして返す
				ClientSideCurrentTagDataModel currentClientSideCurrentTagDataModel = currentClientSideCurrentTagDataModelItel.next();
				currentArray.set(i, currentClientSideCurrentTagDataModel.getM_tagObject());
			}
			return currentArray;
		}
		
		return null;
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
	 * このアカウントのユーザーが所持しているアイテムのキーを、リクエストとしてセットする
	 * @param request
	 */
	public void addRequestToRequestQueue (String request, String requestTypeGet) {
		
		//TODO　すでにリクエストが存在している場合、という考慮がないので、あんまり使えない。
		
		try {
			m_rQueueModelMap.add(new ClientSideRequestQueueModel(request, requestTypeGet));
		} catch (Exception e) {
			debug.trace("addRequestToRequestQueue_error"+e);
		}
	}
	
	/**
	 * リクエストで未実行のものを、Mapとして返す
	 */
	public Map<String,String> getExecutableQueuedRequest () {
		int i = 0;
		//for (i = 0; i < m_uQueueModelMap.size(); i++) {
		for (Iterator<ClientSideRequestQueueModel> qIteletor = m_rQueueModelMap.iterator(); qIteletor.hasNext(); i++) {
			ClientSideRequestQueueModel current = qIteletor.next();//m_uQueueModelMap.get(i);
			if (current.isNotYet()) {
				debug.trace("executing__"+current.getLoadingRequestObject());
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
	 * @param id
	 */
	public void completeRequest (JSONString id) {
		int i = 0;
		for (Iterator<ClientSideRequestQueueModel> qIteletor = m_rQueueModelMap.iterator(); qIteletor.hasNext(); i++) {
			ClientSideRequestQueueModel current = qIteletor.next();
			
			debug.trace("current_getM_dataURL_"+current.getM_dataURL());
			debug.trace("current.getM_id()_"+current.getM_id()+"_/id.toString()_"+id.toString());

			if (id.toString().contains(current.getM_id())) {
				//通信として実行完了したマークを付ける(直前にローディングになっている必要があるが、仕様としてmustではないためチェックしていない。)
				current.setLoaded();
				debug.trace("完全に一致");
			}

			
			
			//未取得が残っているかどうか、チェックしたい
			//最後の一個かどうかは、いけるかな。　あと何個のこってる、とかも。
			//未取得、ロード中が残っていれば、表示を待つ？　全部キューにはいっちゃってるから、どれが、って話になるか。
		}
	}

	
	/**
	 * アイテム情報を保存する
	 * @param item
	 * @return 
	 */
	public void putItemData(JSONObject item) {
		ClientSideCurrentItemDataModel newClientSideCurrentItemDataModel = new ClientSideCurrentItemDataModel(item);
		
		if (isContainsItem(newClientSideCurrentItemDataModel) != null) {
			return;
		}
		
		m_iDataModelMap.add(newClientSideCurrentItemDataModel);
	}
	

	/**
	 * 同じキーを持つItemが既にプールに含まれていたら、そのアイテムを返す
	 * @param newClientSideCurrentItemDataModel
	 * @return
	 */
	private Object isContainsItem(ClientSideCurrentItemDataModel newClientSideCurrentItemDataModel) {
		
		for (Iterator<ClientSideCurrentItemDataModel> currentm_iDataModelMapItel = m_iDataModelMap.iterator(); currentm_iDataModelMapItel.hasNext();) {
			ClientSideCurrentItemDataModel currentClientSideCurrentItemDataModel = currentm_iDataModelMapItel.next();
			
			if (currentClientSideCurrentItemDataModel.getItemKey().get("name").isString().equals(newClientSideCurrentItemDataModel.getItemKey().get("name").isString())) {
//				同じキーのオブジェクトが既にある
				return currentClientSideCurrentItemDataModel;
			} else {
				debug.trace(currentClientSideCurrentItemDataModel.getItemKey()+"_/_"+newClientSideCurrentItemDataModel.getItemKey());
			}
		}
		return null;
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
	public void compareToCurrentAndAddToRequest(JSONObject userKey, JSONArray owningItemKeyArray) {
		//名前と合致するか確認し、合致するものが無ければ新規取得が必要なものとしてセットする。
		//{"kind":"item", "id":0, "name":"http://a"}
		for (int i = 0; i < owningItemKeyArray.size(); i++) {
			
			JSONObject currentObject = owningItemKeyArray.get(i).isObject();
			//アイテムキーと、自分のキーのペアを送る。
			
			JSONObject userKeyWithItemKey = new JSONObject();
			
			userKeyWithItemKey.put("itemKey", currentObject);
			userKeyWithItemKey.put("userKey", userKey);
			checkContainsDataURL(userKeyWithItemKey.toString());//比較して存在しなければ、キューに追加する
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
//				debug.trace("currentModel_"+currentModel.getM_dataURL());
				if (currentModel.getM_dataURL().equals(currentAddress)) {//アドレス一致するものがある
					//アドレス一致する = 含まれている
//					debug.trace("アドレスがすでにQueueに含まれている_"+currentAddress);
					return;//第2層なので、これ以上探す必要が無い
				}
				
				if (!requestModelItel.hasNext()) {
					//address = address.substring(1,currentAddress_.length()-1);
					addRequestToRequestQueue(currentAddress, ClientSideRequestQueueModel.REQUEST_TYPE_GET_ITEM_FROM_KEY);
					return;
				}
			} catch (Exception e) {
				debug.trace("ConcurrentModifi_error_"+e);
				e.printStackTrace();
				
			}
		}	
	}


	/**
	 * ユーザー名とパスワードで、通信取得する先の内容と合致する物を用意する
	 * "name@pass"
	 * @return
	 */
	public String getUserNameWithPassAroundDoubleQuart() {
		return "\""+getUserName()+"@"+getUserPass()+"\"";
	}


	public int getM_imageNumber() {
		return m_imageNumber;
	}


	public void setM_imangeNumber(int m_imangeNumber) {
		this.m_imageNumber = m_imangeNumber;
	}


	/**
	 * ユーザーのアイテムキーのアレイを所持する
	 * @param userItemArray
	 */
	public void setM_userItemArray(JSONArray userItemArray) {
		this.m_userItemArray = userItemArray;
	}


	public JSONArray getM_userOwningItemArray() {
		return m_userItemArray;
	}


	
	public void setM_loginItemPath(String currentItemURLString) {
		m_loginItemPath = currentItemURLString;
	}


	public String getM_loginItemPath() {
		return m_loginItemPath;
	}


	public void setM_nowFocusingItemAddress(String nowFocusingItemAddress) {
		this.m_nowFocusingItemAddress = nowFocusingItemAddress;
	}

	public String getM_nowFocusingItemAddress() {
		return m_nowFocusingItemAddress;
	}
	
	
	/**
	 * 現在ユーザーがフォーカスしているアイテムのキー
	 * @return
	 */
	public JSONObject getM_nowFocusingItemKey () {
		return m_nowFocusingItemKey;
	}

	public void setM_nowFocusingItemKey (JSONObject key) {
		m_nowFocusingItemKey = key;
	}


	public int getAlreadyFinishedRequestNumber() {
		debug.assertTrue(m_rQueueModelMap != null, "getAlreadyFinishedRequestNumber_m_rQueueModelMapがnullです");
		int count = 0;
		int i = 0;
		for (Iterator<ClientSideRequestQueueModel> requestItel = m_rQueueModelMap.iterator(); requestItel.hasNext(); i++) {
			ClientSideRequestQueueModel currentClientSideRequestQueueModel = requestItel.next();
			if (currentClientSideRequestQueueModel.isFinished()) {
				count++;
			}
		}
		return count;
	}


	public int getAllRequestNumber() {
		debug.assertTrue(m_rQueueModelMap != null, "getAllRequestNumber_m_rQueueModelMapがnullです");
		return m_rQueueModelMap.size();
	}

	/**
	 * 特定のキーを持つアイテムを削除、更新する
	 * @param tggerItemKey
	 */
	public void updateItemData(JSONObject taggedItem) {
		ClientSideCurrentItemDataModel newTaggedItem = new ClientSideCurrentItemDataModel(taggedItem);
		if (isContainsItem(newTaggedItem) != null) {
//			取り出す
			removeItemData(newTaggedItem);
			
//			そして足す
			putItemData(taggedItem);
			
		} else {
			debug.assertTrue(false, "該当のアイテムが含まれていない");
		}
		
		//含まれていなかったら、そもそも存在しないアイテムにタグを足したことになる。
		
	}


	private void removeItemData(ClientSideCurrentItemDataModel newTaggedItem) {
		int size = m_iDataModelMap.size();
		for (int i = 0; i < size; i++) {
			ClientSideCurrentItemDataModel currentClientSideCurrentItemDataModel = m_iDataModelMap.get(i);
			if (currentClientSideCurrentItemDataModel.getItemKeyName().equals(newTaggedItem.getItemKeyName())) {
				m_iDataModelMap.remove(i);
				return;
			}
		}
	}



	
}
