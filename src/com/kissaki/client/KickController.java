package com.kissaki.client;


import java.util.Arrays;//array
import java.util.List;//list
import java.util.Random;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.UmbrellaException;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;
import com.kissaki.client.channel.Channel;
import com.kissaki.client.channel.ChannelFactory;
import com.kissaki.client.channel.SocketListener;
import com.kissaki.client.itemDataModel.ItemDataModel;
import com.kissaki.client.login.MyDialogBox;
import com.kissaki.client.subFrame.debug.Debug;
import com.kissaki.client.subFrame.screen.ScreenEvent;
import com.kissaki.client.subFrame.screen.ScreenEventRegister;
import com.kissaki.client.userStatusController.UserStatusController;

/**
 * アプリケーションのコントローラ
 * @author ToruInoue
 *
 */
public class KickController {
	Debug debug = null;
	
	ScreenEventRegister reg;
	
	UserStatusController uStCont = null;
	
	int m_kickStatus = STATUS_KICK_APPINITIALIZE;
	
	static final int STATUS_KICK_APPINITIALIZE = -1;
	
	static final int STATUS_KICK_TESTVIEW_INIT = 0;
	
	static final int STATUS_KICK_TESTVIEW_PROC = STATUS_KICK_TESTVIEW_INIT+1;
	
	static final int STATUS_KICK_LOGIN_INIT = STATUS_KICK_TESTVIEW_PROC+1;
	static final int STATUS_KICK_LOGIN_PROC = STATUS_KICK_LOGIN_INIT+1;
	static final int STATUS_KICK_LOGIN_SUCCEEDED = STATUS_KICK_LOGIN_PROC+1;
	
	static final int STATUS_KICK_EXEC_INIT = STATUS_KICK_LOGIN_SUCCEEDED+1;
	static final int STATUS_KICK_EXEC_PROC = STATUS_KICK_EXEC_INIT+1;
	static final int STATUS_KICK_OUTED_INIT = STATUS_KICK_EXEC_PROC+1;
	static final int STATUS_KICK_OUTED_PROC = STATUS_KICK_OUTED_INIT+1;
	
	
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
	
	
	private static final List<String> Months = Arrays.asList(
	          "Like", "Want", "Have", "Had",
	          "Good","とか"
	     );

	
	
	public KickController () {
		debug = new Debug(this);
		debug.trace("コンストラクタに到達");
		uStCont = new UserStatusController();//Ginから取得出来るといいな。
		
		if (false) {
			/*{"channelID":"channel-m2fdj8-master","userData":{"itemKeys":[],"key":{"complete":true,"id":0,"kind":"user","name":"test@test","namespace":"","parent":null},"m_userName":"test","m_userPass":"test","m_userStatus":0}}
			 */
			String jsonString = "{\"channelID\":\"channel-m2fdj8-master\",\"userData\":{\"itemKeys\":[],\"key\":{\"complete\":true,\"id\":0,\"kind\":\"user\",\"name\":\"test@test\",\"namespace\":\"\",\"parent\":null},\"m_userName\":\"test\",\"m_userPass\":\"test\",\"m_userStatus\":0}}";
			parseJsonString(jsonString);
		}
		
		debug.trace("status_"+uStCont.getUserStatus());
		debug.trace("name_"+uStCont.getUserName());
		debug.trace("password_"+uStCont.getUserPass());
		
		
		
		setKickStatus(STATUS_KICK_APPINITIALIZE);
		
		procedure("");
	}
	
	/**
	 * 実行処理
	 */
	public void procedure(String exec) {
		switch (getKickStatus()) {
		case STATUS_KICK_APPINITIALIZE:
			/*
			 * サンプル画面を出す
			 */
			setKickStatus(STATUS_KICK_TESTVIEW_INIT);
		case STATUS_KICK_TESTVIEW_INIT:
			/*
			 * ログイン画面を出す。
			 */
			reg = new ScreenEventRegister(new HTML(
					"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"  \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\"> <html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\"><head>  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>  <title>Create cool applications! | dev.twitter.com</title></head><body>    <a href=\"http://dev.twitter.com/start\">Begin</a>    <img src=\"http://a0.twimg.com/images/dev/bookmark.png\" class=\"bookmark\" alt=\"Attention!\" /></body>"
					//画像
					//画像へのハンドラ、、どうするかな。
			));
			
		case STATUS_KICK_TESTVIEW_PROC:
			
			//リンクが触られたら、ログインに行く。
			setKickStatus(STATUS_KICK_LOGIN_INIT);
			procedure("");
			break;
		
		case STATUS_KICK_LOGIN_INIT:
			/*
			 * ログイン画面を出す。
			 */
			try {
				HTML loginView = new HTML("Login");
				reg.fireEvent(new ScreenEvent(1, loginView));
				
				//現在このユーザがログインしているのかどうか、知らない。知りにいかないと行けないが、さて？
				//サイトに入ったら、すぐ通知を受け付ける事が出来る。どのページからでも、自由にログイン出来る。こういう強みはある。
				//ログインする事を、サジェスト出来る筈。
				
//				final Button popupButton = new Button("Show Dialog");
//				popupButton.addClickHandler(new ClickHandler(){
//					@Override
//					public void onClick(ClickEvent event) {
//
//						Image image = new Image();
//						image.setUrl(TestBundle.INSTANCE.arrow().getURL());
//						reg.fireEvent(new ScreenEvent(1, image));
//
						final MyDialogBox diag = new MyDialogBox(this);
						diag.show();
						diag.center();
//					}
//				});
//
//				reg.fireEvent(new ScreenEvent(1, popupButton));
//
//
//				TextCell textCell = new TextCell();
//				CellList<String> cellList = new CellList<String>(textCell);
//				final SingleSelectionModel<String> selectionModel = 
//					new SingleSelectionModel<String>();
//				cellList.setSelectionModel(selectionModel);
//				selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
//					@Override
//					public void onSelectionChange(SelectionChangeEvent event) {//描画してから出したいですね。
//						
//						Window.alert("Clicked! " + selectionModel.getSelectedObject());
//					}
//				});
//				
//				cellList.setRowData(1, Months);
//				reg.fireEvent(new ScreenEvent(1, cellList));
			} catch (UmbrellaException e) {
				debug.trace("e_"+e.getCauses());
			}
			
			setKickStatus(STATUS_KICK_LOGIN_PROC);
			
		case STATUS_KICK_LOGIN_PROC:
			//ログイン処理を行うフェーズに移行する
			if (exec.matches("login実行")) {
				//実行！
				String nameWithPass = uStCont.getUserName()+":"+uStCont.getUserPass();
				debug.trace("nameWithPass_"+nameWithPass);
				
				//サーバサイドにユーザー名とパスを送る
				greetingService.greetServer("userLogin+"+nameWithPass+"",
						new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						debug.trace("failure");
					}

					public void onSuccess(String result) {
						debug.trace("onSuccess_result_"+result);
						if (result.matches("ログインパスワードが違います")) {//やりなおし
							setKickStatus(STATUS_KICK_LOGIN_INIT);
							procedure("");
						} else {
							setKickStatus(STATUS_KICK_LOGIN_SUCCEEDED);
							procedure("Channel_Open:"+result);
						}
						
						
						
					}
				}
				);
				
			}
			
			break;
			
			
			/*
			 * ログインが成功した筈。
			 */
		case STATUS_KICK_LOGIN_SUCCEEDED:
			if (exec.startsWith("Channel_Open:")) {
				String jsonString = exec.subSequence("Channel_Open:".length(), exec.length()).toString();
				
				setUpUserKey(jsonString);
				//画面の片付けとかも行う。
				
				//ここで遷移がぶっちぎれて、Channelだよりになる。
			}
			break;
			
			/*
			 * channel接続が通った状態
			 */
		case STATUS_KICK_EXEC_INIT:
			
			setKickStatus(STATUS_KICK_EXEC_PROC);
			
			//ユーザーのキーでいろいろやる
			
			//Jsonでクエリー構築しよう
			/*
			 * ユーザーキーと所持アイテムのキーで召還する。
			 * キューにためておいた通信を行う。そんだけ。
			 */
			String userKey = uStCont.getUserKey();
			procQueExecute(userKey);
			
			
			//ローディング画面表示する
			initCanvas();
			
			
		case STATUS_KICK_EXEC_PROC:
			//アイテムを表示する
			if (exec.matches("アイテム取得開始")) {
				//描画のアップデートを行う、、モデルの件数みてがんばればいいかな。
				debug.trace("アイテム取得が開始しました");
			}
			
			break;
			
		case STATUS_KICK_OUTED_INIT:
			setKickStatus(STATUS_KICK_OUTED_PROC);
			
		case STATUS_KICK_OUTED_PROC:
			
			break;
			

		default:
			debug.trace("未設定のアプリケーションステータス_" + getKickStatus());
			break;
		}
		
		if (exec.startsWith("encodedData:")) {
			String encodedData = exec.substring("encodedData:".length(), exec.length());
			debug.trace("どうなんだろう_"+encodedData);
			
			getItem(encodedData);
		}
	}
	
	
	/**
	 * キューの通信を実行する。
	 * Asyncだから、チョッパやで終わる筈。
	 * @param userKey
	 */
	private void procQueExecute(String userKey) {
		String request = null;
		
		request = uStCont.executeQueuedRequest();
		
		while (request != null) {
			debug.trace("このブロック開始");
			greetingService.greetServer("getItemData+"+request,
					new AsyncCallback<String>() {
				public void onFailure(Throwable caught) {
					debug.trace("failure");
				}
	
				public void onSuccess(String result) {
					debug.trace("success!_"+result);
					procedure("アイテム取得開始");
				}
			}
			);
			
			request = uStCont.executeQueuedRequest();//一件もなければnullを返す
		}
		debug.trace("到着、全件ロード開始");
	}

	/**
	 * ユーザーデータのユーザーキーを設定する。
	 * @param jsonString
	 */
	private void setUpUserKey(String jsonString) {
		
		/*
		 * ユーザーのキーを取得する
		 */
		JSONObject root = null;
		JSONObject userData = null;
		JSONArray userItemArray = null;
		JSONString channelID = null;
		
		
		if (JSONParser.parseStrict(jsonString).isObject() != null) {
			root = JSONParser.parseStrict(jsonString).isObject();
		}
		
		if (root != null) {
			if (root.get("channelID").isString() != null) {
				channelID = root.get("channelID").isString();//ここで、前後%22が着いてる。とっちゃえばいい、というものでも無いと思う。取り方がある筈。
				
				debug.trace("channelID_"+channelID.stringValue());
			}
			
			if (root.get("userData").isObject() != null) {
				userData = root.get("userData").isObject();
			}
		}
		
		if (userData != null) {
			if (userData.get("itemKeys").isArray() != null) {
				userItemArray = userData.get("itemKeys").isArray();
				debug.trace("size_"+userItemArray.size());
			}
			
			if (userData.get("key").isObject() != null) {
				JSONObject key = userData.get("key").isObject();
				debug.trace("key_"+key);
				uStCont.setUserKey(key.toString());
			}
		}
		
		
		/*
		 * どんな必然があるだろうか。ユーザー名の取得とか、そのへんはまあどうでもいいとして。
		 */
		debug.trace("status_"+uStCont.getUserStatus());
		debug.trace("name_"+uStCont.getUserName());
		debug.trace("password_"+uStCont.getUserPass());
		
		uStCont.setUserStatus(UserStatusController.STATUS_USER_LOGIN);
		
		setKickStatus(STATUS_KICK_EXEC_INIT);
		
		if (channelID != null) {
			setChannelID(channelID.toString());
		} else {
			debug.assertTrue(false, "なんらかの理由でchannlelIDが入ってないようです");
		}
		
		/*
		 * アイテム取得のリクエストを用意する(ユーザーデータ全体にアイテム所持一覧が含まれている)
		 */
		if (userItemArray != null) setUpUserItemRequest(userItemArray);
		
		if (true) {
			updateItemData("http://");
		}
	}

	/**
	 * アイテムリクエストをJsonStringから読み出し行う。
	 * @param jsonString
	 */
	private void setUpUserItemRequest(JSONArray userItemArray) {

		//ここでアレイとして保存しておく。

		
		int size = userItemArray.size();
		for (int i = 0; i < size; i++) {
			JSONObject key = userItemArray.get(i).isObject();
			debug.trace("key_"+key);
			uStCont.addRequestToRequestQueue(key.toString());
			
			//キーは、このユーザーが持っている筈のもの、呼び水として使う。
			/*
			 * 寿命が非常に短いものがいいな。
			 * リクエスト投げたら、消えるようなやつ。
			 * ユーザーのリクエストキューにセットして、どんどん呼び出す、という形にしよう。
			 * currentなユーザーのアイテムリクエストキュー。
			 * 
			 * このユーザーのアカウントに深く紐づいたDataModelがほしい。
			 * どうやって実現するのがクールかな。
			 * ユーザー名が切り替わるたびに死ぬとか、そういったのがいいな。
			 * ユーザーをコンテキストとして動く、値の集団。
			 * まあ、今は考慮のみ行う。
			 * 
			 * ユーザーコントローラーの中に、モデルを持とう。
			 */
			
			
//			//このIDについて、リクエストを投げる。
//			greetingService.greetServer("getItemData+"+key,
//					new AsyncCallback<String>() {
//				public void onFailure(Throwable caught) {
//					debug.trace("failure");
//				}
//
//				public void onSuccess(String result) {
//					debug.trace("success!_"+result);
//					procedure("アイテム+_"+result+"_取得開始");
//				}
//			}
//			);
		}

	}
	
	
	/**
	 * JSONの構文分解。
	 * テストしましょう。
	 * @param jsonString
	 */
	public void parseJsonString(String jsonString) {
		
		/**
		 * "{\"channelID\":\"channel-m2fdj8-master\",
		 * \"userData\":{\"itemKeys\":[],\"key\":{\"complete\":true,\"id\":0,\"kind\":\"user\",\"name\":\"test@test\",\"namespace\":\"\",\"parent\":null},\"m_userName\":\"test\",\"m_userPass\":\"test\",\"m_userStatus\":0}}";
//コレも、オブジェクト扱いらしい。
\"userData\":{\"itemKeys\":[],\"key\":{\"complete\":true,\"id\":0,\"kind\":\"user\",\"name\":\"test@test\",\"namespace\":\"\",\"parent\":null},\"m_userName\":\"test\",\"m_userPass\":\"test\",\"m_userStatus\":0}
		 */
		
		//アイテム一覧のアレイがあるので、取得する
		if (JSONParser.parseStrict(jsonString).isArray() != null) {
			debug.trace("アレイがあるみたいです");
		}
		if (JSONParser.parseStrict(jsonString).isBoolean() != null) {
			debug.trace("ブーリアン？");
		}
		
		/**
		 * うーーん、パース部分が肥大化するよう。しょうがないのかよう。
		 */
		if (JSONParser.parseStrict(jsonString).isObject() != null) {
			debug.trace("オブジェクト？");
			
			JSONObject obj = JSONParser.parseStrict(jsonString).isObject();
			debug.trace("もらた");
			

			
			JSONValue v = obj.get("channelID");
			debug.trace("channelID_"+v);
			
			if (obj.get("userData").isArray() != null) {
				debug.trace("配列としてゲット出来るようだ");
			}
			if (obj.get("userData").isString() != null) {
				debug.trace("文字列");
			}
			if (obj.get("userData").isObject() != null) {
				//\"userData\":{\"itemKeys\":[],\"key\":{\"complete\":true,\"id\":0,\"kind\":\"user\",\"name\":\"test@test\",\"namespace\":\"\",\"parent\":null},\"m_userName\":\"test\",\"m_userPass\":\"test\",\"m_userStatus\":0}
				debug.trace("Objとして");
				JSONObject obj2 = obj.get("userData").isObject();
				if (obj2.get("itemKeys").isArray() != null) {
					debug.trace("[]表記ならば配列らしい");
					JSONArray a = obj2.get("itemKeys").isArray();
					debug.trace("size_"+a.size());
				}
				if (obj2.get("m_userStatus").isObject() != null) {//値が存在して初めて読める。こう書くしかないのか。
					debug.trace("オブジェクトだよ");
					JSONNumber status = obj2.get("m_userStatus").isNumber();
					debug.trace("status_"+status);
				}
			}
//			JSONArray array = obj.get("userData").isArray();
//			debug.trace("array_"+array);
			
			
		}
		if (JSONParser.parseStrict(jsonString).isString() != null) {
			debug.trace("ストリング？");
		}
		debug.trace("チェック完了");
		
		
	}

	/**
	 * channelAPIが開いたので、ユーザーにキーを返す
	 * @param result
	 */
	private void setChannelID(String result) {
		/**
		 * 取得したキーでチャンネルを開く
		 */
		String channelIDUTF8String = URL.encode(result);//%22がついている。なんとかならないかな。
		debug.trace("channelIDUTF8String_"+channelIDUTF8String);
		channelIDUTF8String = channelIDUTF8String.substring(3,channelIDUTF8String.length()-3);
		
		Channel channel = ChannelFactory.createChannel(channelIDUTF8String);
		
		
		/**
		 * 接続ハンドラ
		 */
		channel.open(new SocketListener() {

			public void onOpen() {
				debug.trace(uStCont.getUserName()+"_channel 開きました");
				procedure("");
			}
			
			public void onMessage(String encodedData) {
				debug.trace(uStCont.getUserName()+"_メッセージを受け取りました_" + encodedData);
				procedure("encodedData:"+encodedData);
			}
			
		});
		
	}

	
	
	/**
	 * 取得する
	 * @return
	 */
	public int getKickStatus () {
		return m_kickStatus;
	}
	
	public void setKickStatus (int status) {
		m_kickStatus = status;
	}

	/**
	 * ユーザー名を保持する
	 * @param s
	 */
	public void inputUserName(String s) {
		uStCont.setUserName(s);
	}

	/**
	 * ユーザーパスを保持する
	 * @param s
	 */
	public void inputUserPass(String s) {
		uStCont.setUserPass(s);
	}

	/**
	 * ログイン
	 * @param name
	 * @param pass
	 */
	public void login(String name, String pass) {
		inputUserName(name);
		inputUserPass(pass);
		
		procedure("login実行");
	}
	
	int ix,iy = 2;
	/**
	 * pushで、サーバからアイテム情報が届いたときに処理する
	 */
	private void getItem (String encodedData) {
//		uStContの内容を更新する
		ix++; iy--;
		updateCanvas(ix,iy);
		if (iy == 0) {
			iy = 2;
		}
	}
	
	void updateItemData (String itemNameKey) {
		/*
		 * 適当に登録する
		 * ユーザー情報のkeyと、アイテム情報のkey(仮の名称)を作って、
		 * アイテムをサーバにおく。
		 */
		greetingService.greetServer("setItemData+"+itemNameKey,
				new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				debug.trace("failure");
			}

			public void onSuccess(String result) {
				debug.trace("setItemData+success!_"+result);
			}
		}
		);
	}
	
	
	
	
	
	
	
	ProcessingImplements p;

	/**
	 * キャンバスをセットする。
	 */
	private void initCanvas () {
		
		GWTCanvas canvas = new GWTCanvas(0,0,600,280);
		ScreenEventRegister reg = new ScreenEventRegister(canvas);
		p = new ProcessingImplements(canvas.getElement(), "");
		p.size("600","280","");
	}
	
	private void updateCanvas (int x, int y) {
		Random rand = new Random();
//		p.c
		p.color(""+rand.nextInt(255), ""+rand.nextInt(255), ""+rand.nextInt(255), "1");
		p.ellipse(""+x*54, ""+y*90, ""+100, ""+100);
	}
	
}


