package com.kissaki.client;


import java.util.Arrays;//array
import java.util.List;//list
import java.util.Map;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
import com.google.gwt.user.client.ui.Image;
import com.kissaki.client.canvasController.CanvasController;
import com.kissaki.client.channel.Channel;
import com.kissaki.client.channel.ChannelFactory;
import com.kissaki.client.channel.SocketListener;
import com.kissaki.client.imageResource.Resources;
import com.kissaki.client.itemDataModel.ItemDataModel;
import com.kissaki.client.login.MyLoginBox;
import com.kissaki.client.procedure.CommentDialogBox;
import com.kissaki.client.procedure.ItemCommentController;
import com.kissaki.client.procedure.ItemDialogBox;
import com.kissaki.client.subFrame.debug.Debug;
import com.kissaki.client.subFrame.screen.ScreenEvent;
import com.kissaki.client.subFrame.screen.ScreenEventRegister;
import com.kissaki.client.userStatusController.UserStatusController;
import com.kissaki.client.userStatusController.userDataModel.ClientSideCurrentItemDataModel;
import com.kissaki.client.userStatusController.userDataModel.ClientSideRequestQueueModel;

/**
 * アプリケーションのコントローラ
 * @author ToruInoue
 *
 */
public class KickController {
	
	Debug debug = null;
	
	CanvasController cCont;
	ScreenEventRegister reg;
	ItemCommentController itemCommentCont;
	
	String DEFAULT_REQUEST_PASS = "http://images.paraorkut.com/img/funnypics/images/f/fail_cat-12835.jpg";
	
	
	UserStatusController uStCont = null;
	
	int m_kickStatus = STATUS_KICK_APPINITIALIZE;
	
	static final int STATUS_KICK_APPINITIALIZE = -1;
	
	static final int STATUS_KICK_TESTVIEW_INIT = 0;
	
	static final int STATUS_KICK_TESTVIEW_PROC = STATUS_KICK_TESTVIEW_INIT+1;
	
	static final int STATUS_KICK_LOGIN_INIT = STATUS_KICK_TESTVIEW_PROC+1;
	static final int STATUS_KICK_LOGIN_PROC = STATUS_KICK_LOGIN_INIT+1;
	static final int STATUS_KICK_LOGIN_SUCCEEDED = STATUS_KICK_LOGIN_PROC+1;
	
	static final int STATUS_KICK_OWN_INIT = STATUS_KICK_LOGIN_SUCCEEDED+1;
	static final int STATUS_KICK_OWN_PROC = STATUS_KICK_OWN_INIT+1;
	
	static final int STATUS_KICK_OWNERS_INIT = STATUS_KICK_OWN_PROC+1;
	static final int STATUS_KICK_OWNERS_PROC = STATUS_KICK_OWNERS_INIT+1;
	
	static final int STATUS_KICK_OUTED_INIT = STATUS_KICK_OWNERS_PROC+1;
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
		
		
		
		debug.trace("status_"+uStCont.getUserStatus());
		debug.trace("name_"+uStCont.getUserName());
		debug.trace("password_"+uStCont.getUserPass());
		
		
		
		setKickStatus(STATUS_KICK_APPINITIALIZE);
		procedure("startSamplePage");
	}
	
	/**
	 * 実行処理
	 */
	public void procedure(String exec) {
		switch (getKickStatus()) {
		case STATUS_KICK_APPINITIALIZE:
			if (exec.matches("startSamplePage")) {
				/*
				 * サンプル画面を出す
				 */
				setKickStatus(STATUS_KICK_TESTVIEW_INIT);
				
				
				procedure("testViewInitialize");
			}
			
			
		case STATUS_KICK_TESTVIEW_INIT:
			if (exec.matches("testViewInitialize")) {
				/*
				 * ログイン画面を出す。
				 */
				HTML a = new HTML("<p id=\"spinner\">Please wait while we do what we do best.</p>");//アドレス埋め込み、そしてバックグラウンドに押し込む  この要素を入れるタイミングで確かに再描画とか発生するわな。
//				a = new HTML("");
				
				debug.trace("到達");
				/*
				 * Googleでひらくような事をすれば、まあOKかなと思うのですが、
				 * 画面遷移への使い道が増えるので、
				 * いいんじゃないかと。
				 */
				reg = new ScreenEventRegister(
						a
						//Webページを読み込む、ためのフレームが有ればいいのかな。
						//HTMLに画像を入れ込む分には、簡単に出来る。
//						new HTML(
//						"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"  \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\"> <html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\"><head>  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>  <title>Create cool applications! | dev.twitter.com</title></head><body>    <a href=\"http://dev.twitter.com/start\">Begin</a>    <img src=\"http://a0.twimg.com/images/dev/bookmark.png\" class=\"bookmark\" alt=\"Attention!\" /></body>"
//						//画像
//						//画像へのハンドラ、、どうするかな。
//				)
				);
				setKickStatus(STATUS_KICK_TESTVIEW_PROC);
				procedure("testViewToLogin");
			}
			
		case STATUS_KICK_TESTVIEW_PROC:
			if (exec.matches("testViewToLogin")) {

				//リンクが触られたら、ログインに行く。
				setKickStatus(STATUS_KICK_LOGIN_INIT);
				String address = DEFAULT_REQUEST_PASS;
				
				JSONObject addressObject = new JSONObject();
				addressObject.put("imageAddress", new JSONString(address));
				
				procedure("startLogin+"+addressObject);
			}
			break;
		
		case STATUS_KICK_LOGIN_INIT:
			if (exec.startsWith("startLogin+")) {
				
				//アドレスに対するチェックを行う。画像以外プレビューに対応したくない。
				
				
				/*
				 * ログイン画面を出す。
				 */
				final String imageURL = exec.substring("startLogin+".length(), exec.length());
				
				
				JSONObject urlObject = JSONParser.parseStrict(imageURL).isObject();
				String urlString = urlObject.get("imageAddress").isString().toString();
				
				debug.trace("ログインしたタイミングで、フォーカスしていた画像や物のURLを入力する");
				
				Image image = new Image();
//				image.setUrl(urlString);
				image.setUrl(Resources.INSTANCE.s1().getURL());
				reg.fireEvent(new ScreenEvent(1, image));//なるほど、全て上書きされる訳だ。この部分を制御できればいいんだな。書き換え部分とそうでない部分の制御を、よりセンシティブに行う必要がある。
				//ちょうどスクリーンマネージャーの限界だったので、ここら辺で見切るとよかろう。
				
				image.addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						showLoginWindow(imageURL);
					}
				});
				

				if (exec.matches("No Match..")) {
					debug.trace("間違えました");
				}
				
				setKickStatus(STATUS_KICK_LOGIN_PROC);
			}
			
		case STATUS_KICK_LOGIN_PROC:
			

			/*
			 * ログイン処理の実行
			 * まだ入力がされただけの段階なので、
			 * ログイン出来なければやり直し、になる。
			 * どこまでやり直しにさせるかは、サービス次第。
			 */
			if (exec.startsWith("loginWithURLPath+")) {
				String itemString = exec.substring("loginWithURLPath+".length(), exec.length());
				
				//実行！
				String nameWithPass = uStCont.getUserName()+":"+uStCont.getUserPass();
				debug.trace("nameWithPass_"+nameWithPass);
				
				/*
				 * ログイン時のアイテムのアドレスをセーブしておく
				 */
				String currentItemURLString = JSONParser.parseStrict(itemString).isObject().get("imageAddress").isString().toString();
				uStCont.setM_loginItemPath(currentItemURLString);
				
				/*
				 * アイテムをAddする必要があるのか、既に持っているのか、この時点で判らないので、キューにいれておく
				 */
				uStCont.addRequestToRequestQueue(currentItemURLString, ClientSideRequestQueueModel.REQUEST_TYPE_ADD);
					
				//サーバサイドにユーザー名とパスを送る
				greetingService.greetServer("userLogin+"+nameWithPass+"",
						new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						debug.trace("failure");
					}
					
					public void onSuccess(String result) {
						if (result.matches("ログインパスワードが違います")) {//やりなおし
							setKickStatus(STATUS_KICK_LOGIN_INIT);
							procedure("No Match..");
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
			/*
			 * チャンネルの開始、繋ぎ終わってから次のフェーズへ
			 */
			if (exec.startsWith("Channel_Open:")) {
				String jsonString = exec.subSequence("Channel_Open:".length(), exec.length()).toString();
				setUpUserKey(jsonString);//ユーザー情報が整った状態
			}
			
			if (exec.matches("SocketOpened")) {

				procQueExecute(uStCont.getUserKey());//アイテムの追加が実行される筈
			
				cCont = new CanvasController(this,uStCont.getUserKey());
				cCont.initCanvas();
				
//				setKickStatus(STATUS_KICK_OWN_INIT);//アイテム全件を取得する
				setKickStatus(STATUS_KICK_OWNERS_INIT);//アイテム一件を取得する
				procedure("initializeOwning");
			}
			
			break;
			
			/*
			 * これ以降は、
			 * channel接続が通った状態
			 */
		case STATUS_KICK_OWN_INIT:
			if(exec.startsWith("initializeOwning")) {
				setKickStatus(STATUS_KICK_OWN_PROC);
				
				//この時点での所持アイテムのキー全てを召還対象にする
				setUpUserItemRequest(uStCont.getUserKey(), uStCont.getM_userItemArray());
				
				//自分の最新データの取得
				uStCont.addRequestToRequestQueue(uStCont.getUserKey().toString(), ClientSideRequestQueueModel.REQUEST_TYPE_UPDATE_MYDATA);
				procQueExecute(uStCont.getUserKey());
				
				//画面に名前でも着けるか。
				HTML yourOwnItems = new HTML("There are your own items");
				reg.fireEvent(new ScreenEvent(1, yourOwnItems));
			}
		case STATUS_KICK_OWN_PROC:
			if (exec.startsWith("ItemUpdated+")) {//アイテムが更新/加算されたので、再描画を行う
				String itemDatas = exec.substring("ItemUpdated+".length(), exec.length());
				
				JSONArray itemArray = JSONParser.parseStrict(itemDatas).isArray();
				cCont.updateItemcInfo(itemArray);
			}
			
			
			if (exec.startsWith("TagUpload+")) {
				//アイテムのキーと加算したタグの内容と加算した主のキーの合算をサーバに渡す
				//そしたらサーバ側で、タグ情報が構築され、そのタグのキーがアイテムに追加される
				
				String key = exec.substring("TagUpload+".length(),  exec.length());
				
				JSONObject itemKeyWithNewTagWithMyKey = JSONParser.parseStrict(key).isObject();
				itemKeyWithNewTagWithMyKey.put("userKey", uStCont.getUserKey());
				
				uStCont.addRequestToRequestQueue(itemKeyWithNewTagWithMyKey.toString(), ClientSideRequestQueueModel.REQUEST_TYPE_ADDNEWTAG);
				procQueExecute(uStCont.getUserKey());//サーバにリクエストを送りこむ
			}
			
			if (exec.startsWith("tagUpdated+")) {
				String execution = exec.substring("tagUpdated+".length(),  exec.length());
				
				debug.trace("タグがアップデートされました_"+execution);
				
			}
			
			/*
			 * アイテムがタッチされたら、そのコメント一覧へ
			 * 所有者の情報で、だれがどんなタグ付けてるか欲しいので、見に行く。
			 */
			if (exec.startsWith("ItemTapped+")) {
				setKickStatus(STATUS_KICK_OWNERS_INIT);
				//ロードするアイテムのキーを受け取り、コメントの情報を表示する
				String userKey = exec.substring("ItemTapped+".length(),  exec.length());
				debug.trace("userKey_"+userKey);
				procedure("LoadingOwnersOfItem+"+userKey);
				
				//itemKeyから、アイテムのオーナーの情報を聞きに行く
				
				//画面の片付けとか行う
			}
			
			break;
			
			/*
			 * アイテムのコメント情報を収集する
			 * (、、必要がある、のか？　このアイテムについてのコメントは、このアイテムに着いている。)
			 */
		case STATUS_KICK_OWNERS_INIT:
			/*
			 * 起動時に直接この画面に来る場合
			 */
			if (exec.startsWith("initializeOwning")) {
				setKickStatus(STATUS_KICK_OWNERS_PROC);
				
				//フォーカスをログイン時のアイテムに設定する
				uStCont.setM_nowFocusingItemAddress(uStCont.getM_loginItemPath());
				
				//自分の最新データを取得
				uStCont.addRequestToRequestQueue(uStCont.getUserKey().toString(), ClientSideRequestQueueModel.REQUEST_TYPE_UPDATE_MYDATA);
				procQueExecute(uStCont.getUserKey());
				
				HTML ownersOfItems = new HTML("There are owners of this item");
				reg.fireEvent(new ScreenEvent(1, ownersOfItems));
				
				/*
				 * アイテムの結果が帰ってくる筈なので、
				 * 帰ってきたら、そのキーを元に
				 * コメント取得を行う。
				 */
			}
			
			
			if (exec.startsWith("LoadingOwnersOfItem+")) {
				setKickStatus(STATUS_KICK_OWNERS_PROC);
				
				String itemKey = exec.substring("LoadingOwnersOfItem+".length(), exec.length());
				debug.trace("itemKey_"+itemKey);
				//アイテムの情報を元に、
				//チャット情報を集める
				/*
				 * アイテムの情報とユーザーの情報があるので、
				 * ここから、アイテムを所持しているユーザーを集める。
				 * 
				 */
//				アイテムのキーを元に、コメント情報を取得する。この画面は常に一発で更新する。
				uStCont.addRequestToRequestQueue(itemKey, ClientSideRequestQueueModel.REQUEST_TYPE_GETALLCOMMENT);
				procQueExecute(uStCont.getUserKey());//サーバにリクエストを送りこむ
				
				
				JSONObject itemKeyObject = JSONParser.parseStrict(itemKey).isObject();
				JSONObject itemKey2 = itemKeyObject.get("itemKey").isObject();
				itemCommentCont = new ItemCommentController(this, uStCont.getUserKey(), itemKey2);
//				itemCommentCont.testInitialize();//テスト、適当に初期化
				
				HTML ownersOfItems = new HTML("There are owners of this item");
				reg.fireEvent(new ScreenEvent(1, ownersOfItems));
			}
			
		case STATUS_KICK_OWNERS_PROC:
//			debug.trace("STATUS_KICK_OWNERS_PROC_exec_"+exec);
			
//			if (exec.startsWith("TagUpload+")) {
////				入力された文字列を、自分がマスターの自分の言葉として足す
//				//watch touch 
//				String key = exec.substring("TagUpload+".length(),  exec.length());
//			}
			
			/*
			 * アップデート自体はログイン時にかけているのだが、わざわざ
			 * 自前でもっていたログイン時のデータを、更新後のデータから検索、取得している。
			 * アップデートがすんだんだから、専用の「この名前のアイテムしりませんか」をかけてもいいのではないか？
			 */
			if (exec.startsWith("ItemUpdated+")) {//アイテムが更新/加算されたので、再描画を行う
				String itemDatas = exec.substring("ItemUpdated+".length(), exec.length());
				
				JSONArray itemArray = JSONParser.parseStrict(itemDatas).isArray();
				debug.trace("itemArray_"+itemArray);
				
				//ログイン時にフォーカスしているアドレス
				String currentLoginKey = uStCont.getM_loginItemPath();
				debug.trace("currentLoginKey_"+currentLoginKey);
				
				//現在フォーカスしているアイテム
				String currentFocus = uStCont.getM_nowFocusingItemAddress();
				
				for (int i = 0; i < itemArray.size(); i++) {
					JSONObject currentItem = itemArray.get(i).isObject();
					JSONObject currentItemKey = currentItem.get("key").isObject();
					String currentItemName = currentItemKey.get("name").isString().toString();
					
					if (currentItemName.equals(currentLoginKey)) {//名前が一致したら、その時点でのアイテムを取得する
						JSONObject itemKeyWithUserKey = new JSONObject();
						itemKeyWithUserKey.put("itemKey", currentItemKey);
						itemKeyWithUserKey.put("userKey", uStCont.getUserKey());
						
						JSONArray newItemArray = new JSONArray();
						newItemArray.set(0, currentItem);
						cCont.updateItemcInfo(newItemArray);
						
						uStCont.addRequestToRequestQueue(itemKeyWithUserKey.toString(), ClientSideRequestQueueModel.REQUEST_TYPE_GETALLCOMMENT);
						procQueExecute(uStCont.getUserKey());//サーバにリクエストを送りこむ
						break;
					}
					debug.trace(i+"_まだ無いみたい_currentLoginKey_"+currentLoginKey+"/currentItemName_"+currentItemName);	
				}
				
			}
			
			if (exec.startsWith("InputYourText+")) {
				String textInput = exec.substring("InputYourText+".length(), exec.length());
				
				debug.trace("コメント入力が有りました_"+textInput);
				JSONObject commentWithItemKeyWithUserKey = JSONParser.parseLenient(textInput).isObject();
				debug.trace("commentWithItemKeyWithUserKey_"+commentWithItemKeyWithUserKey);
				
				commentWithItemKeyWithUserKey.put("userKey", uStCont.getUserKey());
				
				uStCont.addRequestToRequestQueue(commentWithItemKeyWithUserKey.toString(), ClientSideRequestQueueModel.REQUEST_TYPE_ADDCOMMENT);
				
				procQueExecute(uStCont.getUserKey());//サーバにリクエストを送りこむ
			}
			
			
			if (exec.startsWith("NoComment+")) {
				debug.trace("自分のコメントが無いので、ウインドウを出す。");
				
				
				itemCommentCont.addMyCommentPopup();//自分の情報、特に変わったポップを出す このアイテムの自分のもの、なので、コントローラーが持っている情報を使用する。
			}
			
			
			if (exec.startsWith("MyCommentGet+")) {//自分からのコメント
				String commentData = exec.substring("MyCommentGet+".length(), exec.length());
				
				JSONObject commentBlock = JSONParser.parseStrict(commentData).isObject();
				debug.trace("imageNumber_commentBlock_"+commentBlock);
				
				itemCommentCont.addCommentFromMyself(commentBlock);
			}
			
			if (exec.startsWith("SomeCommentGet+")) {//他人からのコメント
				debug.trace("だれかからのコメントが来た");
				String commentData = exec.substring("SomeCommentGet+".length(), exec.length());
				
				JSONObject commentBlock = JSONParser.parseStrict(commentData).isObject();
				
				
				itemCommentCont.addCommentFromSomeone(commentBlock);
			}
			
			if (exec.startsWith("CommentSaved+")) {
				String commentSavedRequest = exec.substring("CommentSaved+".length(), exec.length());
				debug.trace("コメントのセーブを受け取ったので、リロードする_"+commentSavedRequest);
				//アイテムのキーを出力して、読み出す
				JSONObject value = JSONParser.parseStrict(commentSavedRequest).isObject();
				JSONObject itemKey = new JSONObject();
				itemKey.put("itemKey", value);
				itemKey.put("userKey", uStCont.getUserKey());
				
				uStCont.addRequestToRequestQueue(itemKey.toString(), ClientSideRequestQueueModel.REQUEST_TYPE_GET_LATESTCOMMENT);
				procQueExecute(uStCont.getUserKey());//サーバにリクエストを送りこむ
			}
			
			
			/*
			 * アイテムがタッチされたら、その所有者一覧へ
			 */
			if (exec.startsWith("ItemTapped+")) {
				setKickStatus(STATUS_KICK_OWN_INIT);
				
				itemCommentCont.eraseAllComment();
				itemCommentCont = null;
				
				debug.trace("exec_"+exec);
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
		
		
		
		//////////チャンネルゾーン
		
		
		
		/*
		 * ユーザーのデータの更新を受け付ける
		 * いつでも、、、、
		 * 最大深度のすげ替え
		 * 
		 * ユーザーが持っているアイテムのキーで、更新が必要なものが飛んでくる。
		 * 受け取ったら、過去のリクエストと比較してがんばる。
		 */
		if (exec.startsWith("UserItemCurrent+")) {
			
			String newArrivalItemData = exec.substring("UserItemCurrent+".length(), exec.length());
			
			JSONArray owningItemKeyArray = JSONParser.parseStrict(newArrivalItemData).isArray();
			
			//比較するとしたら、現在持っている/まだもっていない/取得しようとしている、　などのリクエスト状況と照らし合わせ、
			//既に持っていればタイムスタンプを送り込んで比較、
			//今読み込み中であれば、返答が着たら比較するように仕向けるようセット、
			//まだ持っていなければ取得用リクエストを書く、といった所でしょう。
			//今回は、リクエストとの簡単な比較だけ行います。
			
			/*
			 * リクエストと比較して、合致するものが無ければ追加する
			 */
			try {
				uStCont.compareToCurrentRequest(uStCont.getUserKey(), owningItemKeyArray);
			} catch (Exception e) {
				debug.trace("UserItemCurren_error_"+e);
			}
			
			procQueExecute(uStCont.getUserKey());
//			List<ClientSideCurrentItemDataModel> originArray = uStCont.getCurrentItems();//ここで、差分だけ返すとか超かっけー
//			
//			JSONArray newArray = new JSONArray();
//			
//			for (int i = 0; i < originArray.size(); i++) {//jsonArray化する
//				ClientSideCurrentItemDataModel currentModel = originArray.get(i);
//				newArray.set(i, currentModel.itemItself());
//			}
//			
//			procedure("ItemUpdated+"+newArray.toString());
		}
		
		/*
		 * サーバからのPush通信を受け取る部分
		 */
		if (exec.startsWith("push+")) {
			pushedExecute(exec);
		}
	}
	
	
	protected void showLoginWindow(String loginWithURL) {
		final MyLoginBox diag = new MyLoginBox(this, loginWithURL);
		diag.show();
		diag.center();
	}

	/**
	 * プッシュを受けての挙動を行う
	 * @param exec
	 */
	private void pushedExecute(String exec) {
		String data = exec.substring("push+".length(), exec.length());
		
//		debug.trace("解析スタート");
		
		JSONObject root = null;
		JSONString command = null;
		JSONString value = null;
		
		String commandString = null;
		
		if (JSONParser.parseStrict(data).isObject() != null) {
//			debug.trace("root作成");
			root = JSONParser.parseStrict(data).isObject();
		}
		
		try {
			if (root != null) {
//				debug.trace("rootがある_"+root);
				command = root.get("command").isString();
			}
		} catch (Exception e) {
			debug.trace("error?_"+e);
		}
		
		if (command != null) {
//			debug.trace("command_"+command);
			commandString = command.toString();
		}
		
		debug.assertTrue(commandString != null, "commandStringがnullです");
		
		if (commandString.contains("NO_COMMENT")) {
			if (isMyself(root, uStCont.getUserKey())) {
				debug.trace("コメントデータが一件も無い_"+commandString);
				debug.assertTrue(isMyself(root, uStCont.getUserKey()), "自分しかいないので自分宛");
				
				//一件も存在しないので、自分のpopを出す
				procedure("NoComment+"+commandString);
				return;
			}
		}
		
		if (commandString.contains("THERE_IS_MY_COMMENT")) {
			if (isMyself(root, uStCont.getUserKey())) {
				debug.trace("自分のコメント、あります。");
			} else {
				debug.trace("他人のコメント、あります。_"+getUserNameFromUserKey(root));
			}
		}
		
		if (commandString.contains("NO_MY_DATA")) {
			if (isMyself(root, uStCont.getUserKey())) {
				debug.trace("NO_MY_DATA_このアイテムに関しての自分のコメントデータが無い_"+commandString);
				procedure("NoComment+"+commandString);
			} else {
				debug.trace("NO_MY_DATA_このアイテムに関してのとある人自身のコメントデータが無い_"+commandString);
			}
		}
		
		if (commandString.contains("COMMENT_SAVED")) {
			if (isMyself(root, uStCont.getUserKey())) {
				debug.trace("自分のコメントデータが保存出来た_"+commandString);
				JSONObject itemKey = root.get("requested").isObject();
				procedure("CommentSaved+"+itemKey);
			} else {
				debug.trace("他人のコメントデータが保存出来た");
			}
		}
		
		/*
		 * 最新コメントの受け取り
		 */
		if (commandString.contains("LATEST_COMMENT_DATA")) {
			if (isMyself(root, uStCont.getUserKey())) {
				itemCommentCont.removeMyYetPanel(uStCont.getUserName());
				
				debug.trace("コメントデータをゲット_"+commandString);
				JSONObject blockWithNumber = new JSONObject();
				JSONNumber gotUserImageNumber = root.get("userImageNumber").isNumber();
				JSONObject gotCommentData = root.get("wholeCommentData").isObject();
				
				blockWithNumber.put("userImageNumber", gotUserImageNumber);
				blockWithNumber.put("wholeCommentData", gotCommentData);
				
				debug.trace("blockWithNumber_"+blockWithNumber);
				
				
				procedure("MyCommentGet+"+blockWithNumber);
			} else {
				JSONObject blockWithNumber = new JSONObject();
				JSONNumber gotUserImageNumber = root.get("userImageNumber").isNumber();
				JSONObject gotCommentData = root.get("wholeCommentData").isObject();
				
				blockWithNumber.put("userImageNumber", gotUserImageNumber);
				blockWithNumber.put("wholeCommentData", gotCommentData);
				
				
				procedure("SomeCommentGet+"+blockWithNumber);
			}
		}
		
		
		/*
		 * 全体コメントの受け取り
		 */
		if (commandString.contains("ALL_COMMENT_DATA")) {
			if (isMyself(root, uStCont.getUserKey())) {
				debug.trace("from自分なので、自分がリクエストした奴");
				itemCommentCont.removeMyYetPanel(uStCont.getUserName());
				
				debug.trace("コメントデータをゲット_"+commandString);
				JSONObject blockWithNumber = new JSONObject();
				JSONNumber gotUserImageNumber = root.get("userImageNumber").isNumber();
				JSONObject gotCommentData = root.get("wholeCommentData").isObject();
				
				blockWithNumber.put("userImageNumber", gotUserImageNumber);
				blockWithNumber.put("wholeCommentData", gotCommentData);
				
				procedure("MyCommentGet+"+blockWithNumber);
			}
		}
		
		if (commandString.contains("TAG_CREATED")) {
			if (isMyself(root, uStCont.getUserKey())) {
				debug.trace("TAG_CREATEDが発生_"+commandString);
				//自分の持ってるアイテムのどれかにタグが足された筈ですが、pushで帰ってきます。口を開けて待ってなさい。
				
				//TODO タグリクエストの完了
				
				//uStCont.completeRequest(itemKeyNameString);//完了にする そのほか、アップデートを押し付けることが出来る!!
				procedure("tagUpdated+"+root);
			} else {
				debug.trace("他人のタグがアップデートされた_"+getUserNameFromUserKey(root));
			}
		}
		
		if (commandString.contains("ITEM_ADDED_TO_USER")) {
			if (isMyself(root, uStCont.getUserKey())) {
				value = root.get("currentItemkey").isString();
				
				//今度は取得のリクエストをするのだ、か、ログイン処理をするか。
				
				uStCont.addRequestToRequestQueue(uStCont.getUserKey().toString(), ClientSideRequestQueueModel.REQUEST_TYPE_UPDATE_MYDATA);
				procQueExecute(uStCont.getUserKey());
			} else {
				debug.trace("別のユーザーのアイテムが別のユーザーに加算された_"+getUserNameFromUserKey(root));
			}	
		}
		
		if (commandString.contains("ITEM_CREATED")) {
			if (isMyself(root, uStCont.getUserKey())) {
//				debug.trace("このユーザーによって、このユーザーのアイテムがつくられたようです");
			} else {
//				debug.trace("だれかによって、このユーザーのアイテムがつくられたようです_"+getUserNameFromUserKey(root));
			}
			
			//アイテムのリクエストが終了したので、ステータスを変える
		}
		
		/*
		 * アイテムの受け取りが発生
		 */
		if (commandString.contains("PUSH_ITEM")) {
			if (isMyself(root, uStCont.getUserKey())) {
//				debug.trace("For myself From myself");
			} else {
//				debug.trace("For myself from someone");
			}
			try {
				JSONObject item = root.get("item").isObject();

				debug.trace("PUSH_ITEM_item_"+item);
				
				if (item != null) {
					//アイテムの主キーから、設定されていたリクエストを完了に指定する
					String itemKeyNameString = null;
					
					JSONObject itemKeyObject = item.get("key").isObject();
					JSONString itemKeyName = itemKeyObject.get("name").isString();
					itemKeyNameString = itemKeyName.toString();
					
					
					uStCont.completeRequest(itemKeyNameString);//完了にする そのほか、アップデートを押し付けることが出来る!!
					
					uStCont.putItemData(item);//pushしてきてもらったデータ、保存する。但し、いつでもひっくり返る可能性がある。
					List<ClientSideCurrentItemDataModel> originArray = uStCont.getCurrentItems();
					
					JSONArray array = new JSONArray();
					
					for (int i = 0; i < originArray.size(); i++) {//jsonArray化する
						ClientSideCurrentItemDataModel currentModel = originArray.get(i);
						array.set(i, currentModel.itemItself());
					}

					
					//ここで、描画に使う用のデータとして書いてしまえばいい。
					procedure("ItemUpdated+"+array.toString());
				}
			} catch (Exception e) {
				debug.trace("PUSH_ITEM_error_"+e);
			}
		}
		
		if (commandString.contains("TAG_ALREADY_OWN")) {
			if (isMyself(root, uStCont.getUserKey())) {
				debug.trace("TAG_ALREADY_OWN_ココに来てる");
			}
		}
		
		if (commandString.contains("TAG_TO_ITEM_OWNER_ADDED")) {
			if (isMyself(root, uStCont.getUserKey())) {
				debug.trace("TAG_TO_ITEM_OWNER_ADDED_ココに来てる");
			}
		}
		
		
		if (commandString.contains("ITEM_ALREADY_OWN")) {
			if (isMyself(root, uStCont.getUserKey())) {
				value = root.get("itemAddressKey").isString();
//				debug.trace("ITEM_ALREADY_OWN_このアイテムはすでにあなたによって所持されています_"+value);
			} else {
				//誰かから、このアイテムを持っています、という通信が来た
			}
		}
		
		if (commandString.contains("ALREADY_ADDED_TO_USER")) {
			if (isMyself(root, uStCont.getUserKey())) {
				value = root.get("itemAddress").isString();
				
//				setKickStatus(STATUS_KICK_OWNERS_INIT);
//				procedure("LoadingOwnersOfItem+"+uStCont.getUserKey());
			}
		}
		
		
		if (commandString.contains("CURRENT_ITEM_DATA")) {
			if (isMyself(root, uStCont.getUserKey())) {
//				debug.trace("CURRENT_ITEM_DATA_root_"+root);
				
				JSONArray array = root.get("userOwnItems").isArray();
				
				procedure("UserItemCurrent+"+array.toString());
			}	
		}
		
	}

	
	/**
	 * 自分が送ったメッセージであればtrue, それ以外はfalseを返す
	 * @param commandString
	 * @param userKey
	 * @return
	 */
	private boolean isMyself(JSONObject root, JSONObject myUserKey) {
		String myNameString = myUserKey.get("name").isString().toString();
		
		try {
			JSONObject userKey = root.get("userInfo").isObject();
			String userName = userKey.get("name").isString().toString();
			if (userName.equals(myNameString)) {
				return true;
			}
		} catch (Exception e) {
			debug.trace("isMyself_error_"+e);
		}
		
		return false;
	}
	
	/**
	 * userInfoが含まれているrootから、ユーザー名を返す
	 * @param root
	 * @return
	 */
	private String getUserNameFromUserKey (JSONObject root) {
		JSONObject userKey = root.get("userInfo").isObject();
		String userName = userKey.get("name").isString().toString();
		return userName;
	}

	/**
	 * キューの通信を実行する。
	 * Asyncだから、チョッパやで終わる筈。
	 * @param userKey
	 */
	private void procQueExecute(JSONObject userKey) {
		Map<String,String> request = null;
		
		request = uStCont.getExecutableQueuedRequest();
		
		while (request != null) {
//			debug.trace("request_"+request);
			/*
			 * アイテムからタグを取得する
			 */
			if (request.get(ClientSideRequestQueueModel.REQUEST_TYPE_GET_TAG_FROM_ITEM) != null) {
				//アイテムについているタグを取得する
				debug.assertTrue(false, "アイテムからタグを取得、未実装");
			}
			
			
			/*
			 * 人物からタグを取得する
			 */
			if (request.get(ClientSideRequestQueueModel.REQUEST_TYPE_GET_TAG_FROM_PERSON) != null) {
				//人物が持っているタグを取得する
				debug.assertTrue(false, "人からタグを取得、未実装");
			}
			
			/*
			 * タグを加算する
			 */
			if (request.get(ClientSideRequestQueueModel.REQUEST_TYPE_ADDNEWTAG) != null) {
				//タグがリクエストに並ぶ筈
				debug.trace("REQUEST_TYPE_ADDNEWTAG_中身_"+request.get(ClientSideRequestQueueModel.REQUEST_TYPE_ADDNEWTAG));
				
				String tagRequest = request.get(ClientSideRequestQueueModel.REQUEST_TYPE_ADDNEWTAG);
				greetingService.greetServer("addTagToItemData+"+tagRequest,
						new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						debug.trace("failure");
					}
					
					public void onSuccess(String result) {
//						debug.trace("REQUEST_TYPE_ADDNEWTAG_success_"+result);
						procedure("タグ付加完了");
					}
				}
				);
			}
			
			/*
			 * アイテムを加算する
			 * アドレスのみで与えている。
			 */
			if (request.get(ClientSideRequestQueueModel.REQUEST_TYPE_ADD) != null) {
				debug.trace("REQUEST_TYPE_ADD");
				String itemAddressKey = request.get(ClientSideRequestQueueModel.REQUEST_TYPE_ADD);
				
				//ここで、JSONにしてしまう
				JSONObject itemAddressWithUserKey = new JSONObject();
				
				JSONValue itemValue = new JSONString(itemAddressKey);
				itemAddressWithUserKey.put("itemAddressKey", itemValue);
				itemAddressWithUserKey.put("userKey", userKey);
				
				
				greetingService.greetServer("setItemData+"+itemAddressWithUserKey.toString(),
						new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						debug.trace("failure");
					}

					public void onSuccess(String result) {
//						debug.trace("REQUEST_TYPE_ADD_setItemData+success!_"+result);
					}
				}
				);
			}
			
			//アイテムを取得する
			if (request.get(ClientSideRequestQueueModel.REQUEST_TYPE_GET_ITEM) != null) {
				debug.trace("REQUEST_TYPE_GET_ITEM");
				String itemKey = request.get(ClientSideRequestQueueModel.REQUEST_TYPE_GET_ITEM);
				greetingService.greetServer("getItemData+"+itemKey,
						new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						debug.trace("failure");
					}
					
					public void onSuccess(String result) {
//						debug.trace("REQUEST_TYPE_GET_ITEM_success!_"+result);
					}
				}
				);
			}
			
			/*
			 * ユーザーの所持データを取得する(自分のデータを取得し、かつ、誰々がなんか追加しましたというのをブロードキャストする用)
			 */
			if (request.get(ClientSideRequestQueueModel.REQUEST_TYPE_UPDATE_MYDATA) != null) {
				debug.trace("REQUEST_TYPE_UPDATE_MYDATA");
				String userKeyForCurrent = request.get(ClientSideRequestQueueModel.REQUEST_TYPE_UPDATE_MYDATA);
				greetingService.greetServer("getMyData+"+userKeyForCurrent,
						new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						debug.trace("failure");
					}
					
					public void onSuccess(String result) {
//						debug.trace("REQUEST_TYPE_UPDATE_MYDATA_success!_"+result);
					}
				}
				);
			}
			if (request.get(ClientSideRequestQueueModel.REQUEST_TYPE_ADDCOMMENT) != null) {
				debug.trace("REQUEST_TYPE_ADDCOMMENT");
				String addCommentObject = request.get(ClientSideRequestQueueModel.REQUEST_TYPE_ADDCOMMENT);
				greetingService.greetServer("addCommentData+"+addCommentObject,
						new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						debug.trace("failure");
					}
					
					public void onSuccess(String result) {
//						debug.trace("REQUEST_TYPE_ADDCOMMENT_success!_"+result);
					}
				}
				);
			}
			
			if (request.get(ClientSideRequestQueueModel.REQUEST_TYPE_GETALLCOMMENT) != null) {
				debug.trace("REQUEST_TYPE_GETALLCOMMENT");
				
				String itemKeyForGetComment = request.get(ClientSideRequestQueueModel.REQUEST_TYPE_GETALLCOMMENT);
				//所持ユーザーと、そのコメントを取得、更新があったら(=この返答があったら)逐一塗り替える。
				greetingService.greetServer("getAllCommentData+"+itemKeyForGetComment,
						new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						debug.trace("failure");
					}
					
					public void onSuccess(String result) {
						debug.trace("success!_"+result);
					}
				}
				);
			}
			
			if (request.get(ClientSideRequestQueueModel.REQUEST_TYPE_GET_LATESTCOMMENT) != null) {
				debug.trace("REQUEST_TYPE_GET_LATESTCOMMENT");
				
				String itemKeyForGetComment = request.get(ClientSideRequestQueueModel.REQUEST_TYPE_GET_LATESTCOMMENT);
				//所持ユーザーと、そのコメントを取得、更新があったら(=この返答があったら)逐一塗り替える。
				greetingService.greetServer("getSingleCommentData+"+itemKeyForGetComment,
						new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						debug.trace("failure");
					}
					
					public void onSuccess(String result) {
						debug.trace("success!_"+result);
					}
				}
				);
			}
			
			request = uStCont.getExecutableQueuedRequest();//一件もなければnullを返す
		}
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
		try {
			
			if (JSONParser.parseStrict(jsonString).isObject() != null) {
				root = JSONParser.parseStrict(jsonString).isObject();
			}
			
			if (root != null) {
				if (root.get("channelID").isString() != null) {
					channelID = root.get("channelID").isString();
					debug.trace("channelID_"+channelID.stringValue());
				}
				
				if (root.get("userData").isObject() != null) {
					userData = root.get("userData").isObject();
				}
			}
			
			if (userData != null) {
				if (userData.containsKey("itemKeys")) {
					if (userData.get("itemKeys").isArray() != null) {
						userItemArray = userData.get("itemKeys").isArray();
					}
				}

				
				if (userData.get("key").isObject() != null) {
					JSONObject key = userData.get("key").isObject();
//					debug.trace("key_"+key);
					uStCont.setUserKey(key);
					
					int imageNumber = (int)userData.get("imageNumber").isNumber().doubleValue();
					uStCont.setM_imangeNumber(imageNumber);
				}
			}
			
			
			/*
			 * どんな必然があるだろうか。ユーザー名の取得とか、そのへんはまあどうでもいいとして。
			 */
//			debug.trace("status_"+uStCont.getUserStatus());
			debug.trace("name_"+uStCont.getUserName());
			debug.trace("password_"+uStCont.getUserPass());
			
			uStCont.setUserStatus(UserStatusController.STATUS_USER_LOGIN);
			
			
			if (channelID != null) {
				setChannelID(channelID.toString());
			} else {
				debug.assertTrue(false, "なんらかの理由でchannlelIDが入ってないようです");
			}
			
			/*
			 * 所持アイテムキーの情報をセットする
			 */
			if (userItemArray != null) {
				uStCont.setM_userItemArray(userItemArray);
			}
			
		} catch (Exception e) {
			debug.trace("エラー隠蔽の可能性がある_"+e);
		}
	}

	/**
	 * アイテムリクエストをJsonStringから読み出し行う。
	 * @param userKey 
	 * @param jsonString
	 */
	private void setUpUserItemRequest(JSONObject userKey, JSONArray userItemArray) {
		int size = userItemArray.size();
		for (int i = 0; i < size; i++) {
			JSONObject key = new JSONObject();
			
			key.put("itemKey", userItemArray.get(i).isObject());
			key.put("userKey", userKey);
			
			uStCont.addRequestToRequestQueue(key.toString(), ClientSideRequestQueueModel.REQUEST_TYPE_GET_ITEM);
		}

	}
	


	/**
	 * channelAPIが開いたので、ユーザーにキーを返す
	 * @param result
	 */
	private void setChannelID(String result) {
		/**
		 * 取得したキーでチャンネルを開く
		 */
		Channel channel = ChannelFactory.createChannel(result.substring(1,result.length()-1));
		
		
		/**
		 * 接続ハンドラ
		 */
		channel.open(new SocketListener() {

			public void onOpen() {
				debug.trace(uStCont.getUserName()+"_channel 開きました");
				procedure("SocketOpened");
			}
			
			public void onMessage(String encodedData) {
				//debug.trace(uStCont.getUserName()+"_メッセージを受け取りました_" + encodedData);
				procedure("push+"+encodedData);
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
	public void login(String name, String pass, String loginItemURL) {
		inputUserName(name);
		inputUserPass(pass);
		
		procedure("loginWithURLPath+"+loginItemURL);
	}
	
	/**
	 * 
	 * @return
	 */
	public UserStatusController getUStCont() {
		return uStCont;
	}
	
	
	
	
	
	
	
	
		
}


