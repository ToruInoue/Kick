package com.kissaki.server.userDataModel;


import java.util.List;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;

import com.google.appengine.api.datastore.Key;

/**
 * ユーザーデータモデル
 * @author ToruInoue
 *
 */
@Model(kind = "user")
public class UserDataModel {


	private String m_userName;
	private String m_userPass;
	private int m_userStatus;
	private List<Key> itemKeys;
	//ユーザー名、パスワード、ユーザーのログイン情報、アイテムのノードKeyを持つ
	
	
	@Attribute(primaryKey = true)
    private Key key;

	public void setKey(Key key) {
		this.key = key;
	}

	public Key getKey() {
		return key;
	}

	public void setM_userName(String m_userName) {
		this.m_userName = m_userName;
	}

	public String getM_userName() {
		return m_userName;
	}

	public void setM_userPass(String m_userPass) {
		this.m_userPass = m_userPass;
	}

	public String getM_userPass() {
		return m_userPass;
	}

	public void setM_userStatus(int m_userStatus) {
		this.m_userStatus = m_userStatus;
	}

	public int getM_userStatus() {
		return m_userStatus;
	}

	public void setItemKeys(List<Key> itemKeys) {
		this.itemKeys = itemKeys;
	}

	public List<Key> getItemKeys() {
		return itemKeys;
	}
	
	public void addtemKeys(Key itemKey) {
		this.itemKeys.add(itemKey);
	}
	
	
}
