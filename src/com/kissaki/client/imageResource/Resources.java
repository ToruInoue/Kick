package com.kissaki.client.imageResource;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface Resources extends ClientBundle {
	
	public static final Resources INSTANCE = GWT.create(Resources.class);

	ImageResource arrow();//なるほど、ファイル名で記述出来る訳か、、; うーん、使いやすい訳ではないな。。動的に指定出来ると嬉しい。が、コレは静的なものか。
	ImageResource s_arrow();
	ImageResource s1();
	ImageResource s2();
	ImageResource s3();
	ImageResource s4();
	ImageResource s5();
	ImageResource s6();
	ImageResource s7();
	ImageResource s8();
	ImageResource s9();
	ImageResource s10();
	ImageResource s11();
	ImageResource s12();
	ImageResource s13();
	ImageResource s14();
	
}
