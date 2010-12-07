package com.kissaki.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface TestBundle extends ClientBundle {

  public static final TestBundle INSTANCE = GWT.create(TestBundle.class);
  
  ImageResource arrow();//なるほど、ファイル名で記述出来る訳か、、; うーん、使いやすい訳ではないな。。動的に指定出来ると嬉しい。が、コレは静的なものか。
}