package com.kissaki.server.itemDataModel;

import java.util.List;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;

import com.google.appengine.api.datastore.Key;

@Model(kind = "item")
public class ItemDataModel {
	
	/**
	 * アイテムのデータモデル
	 * アイテム名、
	 * アイテムの登録者リスト(Key)、
	 * アイテムに着いているタグリスト(Key)(タグは、一つのアイテムに対して着いたものとしてIDを振る。タグに対する情報はタグのIDに紐づく。)
	 * コメントのリスト(コメントはアイテムに紐づく)
	 */
	
	private String m_itemName;
	private List<Key> m_ownerList;
	private List<Key> m_tagList;
	private List<Key> m_commentList;

	@Attribute(primaryKey = true)
    private Key key;


	public void setKey(Key key) {
		this.key = key;
	}

	public Key getKey() {
		return key;
	}
	
	
	public void setM_tagList(List<Key> m_tagList) {
		this.m_tagList = m_tagList;
	}

	public List<Key> getM_tagList() {
		return m_tagList;
	}


	public void setM_ownerList(List<Key> m_ownerList) {
		this.m_ownerList = m_ownerList;
	}

	public List<Key> getM_ownerList() {
		return m_ownerList;
	}


	public void setM_commentList(List<Key> m_commentList) {
		this.m_commentList = m_commentList;
	}

	public List<Key> getM_commentList() {
		return m_commentList;
	}
	
	public void setM_itemName(String m_itemName) {
		this.m_itemName = m_itemName;
	}

	public String getM_itemName() {
		return m_itemName;
	}

	

	
}
