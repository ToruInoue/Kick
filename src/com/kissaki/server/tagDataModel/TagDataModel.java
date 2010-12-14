package com.kissaki.server.tagDataModel;

import java.util.List;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;

import com.google.appengine.api.datastore.Key;


@Model(kind = "tag")
public class TagDataModel {

	@Attribute(primaryKey = true)
    private Key key;

	/**
	 * タグ名、付けた人のID(key)、タグが設定されたアイテムのID
	 */	

	private String m_tagName;
	private List<Key> m_itemOwnerList;
	private List<Key> m_TagOwnerItemList;//一件だけのアイテムの為に、リスト。
	
	
	
	public void setKey(Key key) {
		this.key = key;
	}

	public Key getKey() {
		return key;
	}
	
	
	public void setM_itemOwnerList(List<Key> m_itemOwnerList) {
		this.m_itemOwnerList = m_itemOwnerList;
	}

	public List<Key> getM_itemOwnerList() {
		return m_itemOwnerList;
	}


	public void setM_tagName(String m_tagName) {
		this.m_tagName = m_tagName;
	}

	public String getM_tagName() {
		return m_tagName;
	}

	public void setM_TagOwnerItemList(List<Key> m_ownerItemList) {
		this.m_TagOwnerItemList = m_ownerItemList;
	}

	public List<Key> getM_TagOwnerItemList() {
		return m_TagOwnerItemList;
	}

//	public String showInfo() {
//		return getM_TagOwnerItemList()+"@"+""+getM_tagName()+"□"+getKey()+"▲"+getM_itemOwnerList().size()+"●"+getM_itemOwnerList().get(0);
//	}


	
}
