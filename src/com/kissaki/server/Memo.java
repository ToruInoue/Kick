package com.kissaki.server;

import java.io.Serializable;
import java.util.Date;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;

import com.google.appengine.api.datastore.Key;

@Model(kind = "Mem")
public class Memo implements Serializable {
	@Attribute(primaryKey = true)
    private Key key;

	
	
	
	private static final long serialVersionUID = 1L;

	
	private String author;
	
	
	private Date date;
	
	
	public void setKey(Key key) {
		this.key = key;
	}

	public Key getKey() {
		return key;
	}
	
	
	public void setAuthor(String key) {
		this.author = key;
	}

	public String getAuthor() {
		return author;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}
}
