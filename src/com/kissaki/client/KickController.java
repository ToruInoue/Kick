package com.kissaki.client;


import java.util.ArrayList;
import java.util.Arrays;//array
import java.util.List;//list
import java.util.Map;
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
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;
import com.kissaki.client.canvasController.CanvasController;
import com.kissaki.client.channel.Channel;
import com.kissaki.client.channel.ChannelFactory;
import com.kissaki.client.channel.SocketListener;
import com.kissaki.client.imageResource.Resources;
import com.kissaki.client.itemDataModel.ItemDataModel;
import com.kissaki.client.login.MyDialogBox;
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
	
	String DEFAULT_REQUEST_PASS = "http://a";
	
	
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
				Image image = new Image();
				image.setUrl(Resources.INSTANCE.arrow().getURL());
				reg.fireEvent(new ScreenEvent(1, image));
				
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
			
			if (exec.matches("No Match..")) {
				debug.trace("間違えました");
			}
			
			setKickStatus(STATUS_KICK_LOGIN_PROC);
			
		case STATUS_KICK_LOGIN_PROC:
			//ログイン処理を行うフェーズに移行する
			if (exec.matches("login実行")) {
				//実行！
				String nameWithPass = uStCont.getUserName()+":"+uStCont.getUserPass();
				debug.trace("nameWithPass_"+nameWithPass);
				
				//現在うつってる物を、送信する予定としてリクエストに加えておく TODO DEFAULT_REQUEST_PASSを固定で送ってる、このサイトのコレのアドレス、という形で。
				uStCont.addRequestToRequestQueue(DEFAULT_REQUEST_PASS, ClientSideRequestQueueModel.REQUEST_TYPE_ADD);
				
					
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
		case STATUS_KICK_OWN_INIT:
			uStCont.addRequestToRequestQueue(uStCont.getUserKey().toString(), ClientSideRequestQueueModel.REQUEST_TYPE_UPDATE_MYDATA);
			setKickStatus(STATUS_KICK_OWN_PROC);
			/*
			 * ユーザーキーと所持アイテムのキーで召還する。
			 * キューにためておいた通信を行う。そんだけ。
			 */
			procQueExecute(uStCont.getUserKey());
			
			
			//ローディング画面表示する
			cCont = new CanvasController(this,uStCont.getUserKey());
			cCont.initCanvas();
			reg.fireEvent(new ScreenEvent(1, cCont.canvas()));
			
		case STATUS_KICK_OWN_PROC:
			//アイテムを表示する
			if (exec.matches("アイテム取得開始")) {
				//描画のアップデートを行う、、モデルの件数みてがんばればいいかな。
				debug.trace("アイテム取得が開始しました");
			}
			
			
			
			
			if (exec.startsWith("ItemUpdated+")) {//アイテムが更新/加算されたので、再描画を行う
				debug.trace("ItemUpdatedに来てる");
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
			
			/*
			 * アイテムがタッチされたら、そのコメント一覧へ
			 * 所有者の情報で、だれがどんなタグ付けてるか欲しいので、見に行く。
			 */
			if (exec.startsWith("ItemTapped+")) {
				setKickStatus(STATUS_KICK_OWNERS_INIT);
				//ロードするアイテムのキーを受け取り、コメントの情報を表示する
				String key = exec.substring("ItemTapped+".length(),  exec.length());
				procedure("LoadingOwnersOfItem+"+key);
				
				//itemKeyから、アイテムのオーナーの情報を聞きに行く
				
				//画面の片付けとか行う
			}
			
			break;
			
			/*
			 * アイテムのコメント情報を収集する
			 * (、、必要がある、のか？　このアイテムについてのコメントは、このアイテムに着いている。)
			 */
		case STATUS_KICK_OWNERS_INIT:
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
			}
			
		case STATUS_KICK_OWNERS_PROC:
//			if (exec.startsWith("TagUpload+")) {
////				入力された文字列を、自分がマスターの自分の言葉として足す
//				//watch touch 
//				String key = exec.substring("TagUpload+".length(),  exec.length());
//			}
			
			if (exec.matches("ItemUpdated+")) {//アイテムが加算されたので、再描画を行う
				//cCont.updateItemcInfo(uStCont.getCurrentItems());
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
				debug.trace("commentBlock_"+commentBlock);
				
				itemCommentCont.addComment(commentBlock);
			}
			
			if (exec.startsWith("SomeCommentGet+")) {//他人からのコメント
				debug.trace("だれかからのコメントが来た");
				String commentData = exec.substring("SomeCommentGet+".length(), exec.length());

				JSONObject commentBlock = JSONParser.parseStrict(commentData).isObject();
				debug.trace("commentBlock_"+commentBlock);
				
				//itemCommentCont.addComment(commentBlock);
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
			debug.trace("command_"+command);
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
		
		if (commandString.contains("COMMENT_DATA")) {
			if (isMyself(root, uStCont.getUserKey())) {
				debug.trace(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				debug.trace("from自分なので、自分がリクエストした奴");
				itemCommentCont.removeMyYetPanel(uStCont.getUserName());
				
				debug.trace("コメントデータをゲット_"+commandString);
				JSONObject gotCommentData = root.get("wholeCommentData").isObject();
				procedure("MyCommentGet+"+gotCommentData);
			} else {
				debug.trace("--------------------------------------------------------");
				debug.trace("他人からのリクエストで、コメントデータが届いた_"+getUserNameFromUserKey(root));
				
				debug.trace("コメントデータをゲット_"+commandString);
				JSONObject gotCommentData = root.get("wholeCommentData").isObject();
				procedure("SomeCommentGet+"+gotCommentData);
			}
			
			
		}
		
		if (commandString.contains("TAG_CREATED")) {
			if (isMyself(root, uStCont.getUserKey())) {
				debug.trace("TAG_CREATEDが発生_"+commandString);
				//自分の持ってるアイテムのどれかにタグが足された筈ですが、pushで帰ってきます。口を開けて待ってなさい。
				
				//TODO タグリクエストの完了
				
				//uStCont.completeRequest(itemKeyNameString);//完了にする そのほか、アップデートを押し付けることが出来る!!
				procedure("tagUpdated+"+commandString);//TODO このタグが更新されたので、要素を更新する、、、、のだが、まあ、ボタンなので、内容更新がめんどい。
			} else {
				debug.trace("他人のタグがアップデートされた_"+getUserNameFromUserKey(root));
			}
		}
		
		if (commandString.contains("ITEM_ADDED_TO_USER")) {
			if (isMyself(root, uStCont.getUserKey())) {
				debug.trace("このユーザーのアイテムがこのユーザーに加算された_"+root);
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
				debug.trace("このユーザーによって、このユーザーのアイテムがつくられたようです");
			} else {
				debug.trace("だれかによって、このユーザーのアイテムがつくられたようです_"+getUserNameFromUserKey(root));
			}
			
			debug.trace("ITEM_CREATED_このアイテムが設定されました_"+value);
			//アイテムのリクエストが終了したので、ステータスを変える
		}
		
		/*
		 * アイテムの受け取りが発生
		 */
		if (commandString.contains("PUSH_ITEM")) {
			if (isMyself(root, uStCont.getUserKey())) {
				debug.trace("For myself From myself");
			} else {
				debug.trace("For myself from someone");
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
					
					debug.trace("itemKeyNameString_"+itemKeyNameString);
					
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
				debug.trace("ITEM_ALREADY_OWN_このアイテムはすでにあなたによって所持されています_"+value);
			} else {
				//誰かから、このアイテムを持っています、という通信が来た
			}
		}
		
		if (commandString.contains("ALREADY_ADDED_TO_USER")) {
			if (isMyself(root, uStCont.getUserKey())) {
				value = root.get("itemAddress").isString();
				debug.trace("ALREADY_ADDED_TO_USER_あなたのアイテムリストにアイテムが既に入っています_"+value);
			}
		}
		
		
		if (commandString.contains("CURRENT_ITEM_DATA")) {
			if (isMyself(root, uStCont.getUserKey())) {
				debug.trace("CURRENT_ITEM_DATA_root_"+root);
				
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
			debug.trace("request_"+request);
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
						debug.trace("success!_"+result);
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
						debug.trace("setItemData+success!_"+result);
					}
				}
				);
			}
			
			//アイテムを取得する
			if (request.get(ClientSideRequestQueueModel.REQUEST_TYPE_GET_ITEM) != null) {
				String itemKey = request.get(ClientSideRequestQueueModel.REQUEST_TYPE_GET_ITEM);
				debug.trace("itemKey_"+itemKey);
				greetingService.greetServer("getItemData+"+itemKey,
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
			}
			
			/*
			 * ユーザーの所持データを取得する(自分のデータを取得し、かつ、誰々がなんか追加しましたというのをブロードキャストする用)
			 */
			if (request.get(ClientSideRequestQueueModel.REQUEST_TYPE_UPDATE_MYDATA) != null) {
				String userKeyForCurrent = request.get(ClientSideRequestQueueModel.REQUEST_TYPE_UPDATE_MYDATA);
				greetingService.greetServer("getMyData+"+userKeyForCurrent,
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
			}
			if (request.get(ClientSideRequestQueueModel.REQUEST_TYPE_ADDCOMMENT) != null) {
				String addCommentObject = request.get(ClientSideRequestQueueModel.REQUEST_TYPE_ADDCOMMENT);
				greetingService.greetServer("addCommentData+"+addCommentObject,
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
			}
			if (request.get(ClientSideRequestQueueModel.REQUEST_TYPE_GETALLCOMMENT) != null) {
				String itemKeyForGetComment = request.get(ClientSideRequestQueueModel.REQUEST_TYPE_GETALLCOMMENT);
				//所持ユーザーと、そのコメントを取得、更新があったら(=この返答があったら)逐一塗り替える。
				greetingService.greetServer("getAllCommentData+"+itemKeyForGetComment,
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
			}
			
			if (request.get(ClientSideRequestQueueModel.REQUEST_TYPE_GET_LATESTCOMMENT) != null) {
				String itemKeyForGetComment = request.get(ClientSideRequestQueueModel.REQUEST_TYPE_GET_LATESTCOMMENT);
				//所持ユーザーと、そのコメントを取得、更新があったら(=この返答があったら)逐一塗り替える。
				greetingService.greetServer("getSingleCommentData+"+itemKeyForGetComment,
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
					channelID = root.get("channelID").isString();//ここで、前後%22が着いてる。とっちゃえばいい、というものでも無いと思う。取り方がある筈。
					
					debug.trace("channelID_"+channelID.stringValue());
				}
				
				if (root.get("userData").isObject() != null) {
					userData = root.get("userData").isObject();
					debug.trace("userData_"+userData);
				}
			}
			
			if (userData != null) {
				try {
					if (userData.get("itemKeys").isArray() != null) {
						debug.trace("userData.get(\"itemKeys\").isArray()_"+userData.get("itemKeys").isArray());
						userItemArray = userData.get("itemKeys").isArray();
						debug.trace("size_"+userItemArray.size());
					}
				} catch (Exception e) {
					debug.trace("userData.get(\"itemKeys\").isArray() != nullであれば出ない筈なんだけど_"+e);
				}
				
				if (userData.get("key").isObject() != null) {
					JSONObject key = userData.get("key").isObject();
					debug.trace("key_"+key);
					uStCont.setUserKey(key);
				}
			}
			
			
			/*
			 * どんな必然があるだろうか。ユーザー名の取得とか、そのへんはまあどうでもいいとして。
			 */
			debug.trace("status_"+uStCont.getUserStatus());
			debug.trace("name_"+uStCont.getUserName());
			debug.trace("password_"+uStCont.getUserPass());
			
			uStCont.setUserStatus(UserStatusController.STATUS_USER_LOGIN);
			
			setKickStatus(STATUS_KICK_OWN_INIT);
			
			if (channelID != null) {
				setChannelID(channelID.toString());
			} else {
				debug.assertTrue(false, "なんらかの理由でchannlelIDが入ってないようです");
			}
			
			/*
			 * アイテム取得のリクエストを用意する(ユーザーデータ全体にアイテム所持一覧が含まれている)
			 */
			if (userItemArray != null) {
				setUpUserItemRequest(uStCont.getUserKey(), userItemArray);
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
		debug.trace("setUpUserItemRequestに来てる");
		int size = userItemArray.size();
		for (int i = 0; i < size; i++) {
			JSONObject key = new JSONObject();
			
			key.put("itemKey", userItemArray.get(i).isObject());
			key.put("userKey", userKey);
			
			uStCont.addRequestToRequestQueue(key.toString(), ClientSideRequestQueueModel.REQUEST_TYPE_GET_ITEM);
		}

	}
	
	
	/**
	 * JSONの構文分解。
	 * テストしましょう。
	 * @param jsonString
	 * @return 
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
		String channelIDUTF8String = URL.encode(result);//%22(")がついている。なんとかならないかな。
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
	public void login(String name, String pass) {
		inputUserName(name);
		inputUserPass(pass);
		
		procedure("login実行");
	}
	
	/**
	 * 
	 * @return
	 */
	public UserStatusController getUStCont() {
		return uStCont;
	}
	
	
	
	
	
	
	
	
		
}


