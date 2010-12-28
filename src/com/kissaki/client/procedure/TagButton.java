package com.kissaki.client.procedure;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.ui.Button;
import com.kissaki.client.subFrame.debug.Debug;

public class TagButton extends Button {
	Debug debug;
	
	JSONObject m_tagObject;

	public TagButton (JSONObject tagObject) {
		debug = new Debug(this);
		debug.assertTrue(tagObject.containsKey("key"), "tagObjectがTagDataModelのJSONObjectではない");
		m_tagObject = tagObject;
	}
	
	public JSONObject getTagObject () {
		return m_tagObject;
	}
	
	
}
