package com.kissaki.client;

import java.io.Serializable;
import java.util.Date;
//import java.util.HashMap;

import com.google.gwt.dev.util.collect.HashMap;

/**
 * クライアントサイドのモデルクラス、HashMapでいけるかも。
 * @author ToruInoue
 *
 */
public class Memo implements Serializable {
	
	
	/**
	 * @gwt.typeArgs <java.lang.String, java.lang.String>
	 */
//	private HashMap<String, String> kvsMap;
//	
//	public Memo () {
//		kvsMap = new HashMap<String, String>();
//		kvsMap.put("name","");//こんな感じに値の名称と内容をセット出来る。
//		
//	}
	
	private static final long serialVersionUID = 1L;

	
	private String author;
	
	
	private Date date;
	
	
	
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
