package com.kissaki.server.commentDataModel;

import java.util.Date;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;

import com.google.appengine.api.datastore.Key;


@Model(kind = "comment")
public class CommentDataModel {
	
	
	/**
	 * コメント一つ一つについてのデータモデル
	 * マスターのIDと、コメント本文。
	 * 日付
	 */
	private Key m_commentMasterID;
	private String m_commentBody;
	private Date m_commentDate;
	private Key m_commentedBy;
	
	@Attribute(primaryKey = true)
    private Key key;

	public void setKey(Key key) {
		this.key = key;
	}

	public Key getKey() {
		return key;
	}

	public void setM_commentBody(String m_commentBody) {
		this.m_commentBody = m_commentBody;
	}

	public String getM_commentBody() {
		return m_commentBody;
	}

	public void setM_commentMasterID(Key m_commentMasterID) {
		this.m_commentMasterID = m_commentMasterID;
	}

	public Key getM_commentMasterID() {
		return m_commentMasterID;
	}

	public void setM_commentDate(Date m_commentDate) {
		this.m_commentDate = m_commentDate;
	}

	public Date getM_commentDate() {
		return m_commentDate;
	}

	public void setM_commentedBy(Key m_commentedBy) {
		this.m_commentedBy = m_commentedBy;
	}

	public Key getM_commentedBy() {
		return m_commentedBy;
	}
	
}
