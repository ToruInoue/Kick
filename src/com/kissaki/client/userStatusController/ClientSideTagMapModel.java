package com.kissaki.client.userStatusController;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gwt.json.client.JSONObject;
import com.kissaki.client.subFrame.debug.Debug;
import com.kissaki.client.userStatusController.userDataModel.ClientSideCurrentTagDataModel;

/*
 * タグをアイテムに足し、引きする。
 * JSONObjectとして、タグのオブジェクトを格納する。
 * JSONObject > Key　になる筈。ということは、currentTagObjectというクラスとコントローラが必要。
 */
/**
 * アイテムのキー名:そのアイテムに着いている自分のタグリスト、という形に情報を持つ為のクラス。
 * 
 * @author ToruInoue
 *
 */
public class ClientSideTagMapModel {
	private ArrayList<ClientSideCurrentTagDataModel> m_tagArray;
	Debug debug;
	
	public ClientSideTagMapModel () {
		debug = new Debug(this);
		m_tagArray = new ArrayList<ClientSideCurrentTagDataModel>();
	}
	
	
	/**
	 * タグをアレイに追加する
	 * @param tagObject
	 */
	public void addM_tagArray (JSONObject tagObject) {
		ClientSideCurrentTagDataModel newClientSideCurrentTagDataModel = new ClientSideCurrentTagDataModel(tagObject);
		
		if (isContainTag(newClientSideCurrentTagDataModel) != null) {
			//更新、、、って、あるのか？
			return;
		}
		debug.trace("m_tagArray_before_"+m_tagArray.size());
		m_tagArray.add(newClientSideCurrentTagDataModel);
		debug.trace("m_tagArray_after_"+m_tagArray.size());
		
	}
	
	/**
	 * このノードのタグに該当するものが含まれていたら、そのタグを返す
	 * @param tagKey
	 * @return
	 */
	public ClientSideCurrentTagDataModel isContainTag(ClientSideCurrentTagDataModel newClientSideCurrentTagDataModel) {
		for (Iterator<ClientSideCurrentTagDataModel> currentClientSideCurrentTagDataModelItel = m_tagArray.iterator(); currentClientSideCurrentTagDataModelItel.hasNext();) {
			
			ClientSideCurrentTagDataModel currentClientSideCurrentTagDataModel = currentClientSideCurrentTagDataModelItel.next();
			if (currentClientSideCurrentTagDataModel.getKeyName().equals(newClientSideCurrentTagDataModel.getKeyName())) {
				return currentClientSideCurrentTagDataModel;
			}
		}
		
		return null;//含まれていない
	}


	public ArrayList<ClientSideCurrentTagDataModel> getM_tagArray() {
		return m_tagArray;
	}
	
	
	
}
