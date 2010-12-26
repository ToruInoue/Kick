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
import com.kissaki.client.ItemOwnersViewController.ItemOwnersViewController;
import com.kissaki.client.PushController.PushController;
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
public class KickController implements KickStatusInterface {
	
	Debug debug = null;
	
	CanvasController cCont;
	ScreenEventRegister reg;
	ItemCommentController itemCommentCont;
	
	String DEFAULT_REQUEST_PASS = "http://images.paraorkut.com/img/funnypics/images/f/fail_cat-12835.jpg";
	
	
	UserStatusController uStCont = null;
	
	PushController pushController = null;
	ItemOwnersViewController itemOwnersViewCont = null;
	int m_kickStatus = STATUS_KICK_APPINITIALIZE;
	
	
	
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
		
			
			
			/*
			 * ログイン処理
			 */
		case STATUS_KICK_LOGIN_INIT:
			execSTATUS_KICK_LOGIN_INIT(exec);
		case STATUS_KICK_LOGIN_PROC:
			execSTATUS_KICK_LOGIN_PROC(exec);
			break;
			
			
			/*
			 * ログインが成功した後の処理、アプリケーションの準備最終フェーズ
			 */
		case STATUS_KICK_LOGIN_SUCCEEDED:
			execSTATUS_KICK_LOGIN_SUCCEEDED(exec);
			break;
			
			
			
			/*
			 * これ以降は、
			 * channel接続が通った状態
			 */
		case STATUS_KICK_LOADING_INIT:
			execSTATUS_KICK_LOADING_INIT(exec);
		case STATUS_KICK_LOADING_PROC:
			execSTATUS_KICK_LOADING_PROC(exec);
			break;
			
		

			/*
			 * 自分が所有してるアイテムの情報を取得する
			 */
		case STATUS_KICK_OWN_INIT:
			execSTATUS_KICK_OWN_INIT(exec);
		case STATUS_KICK_OWN_PROC:
			execSTATUS_KICK_OWN_PROC(exec);
			break;
			
			
			
		case STATUS_KICK_OWNERS_INIT:
			executeSTATUS_KICK_OWNERS_INIT(exec);
		case STATUS_KICK_OWNERS_PROC:
			executeSTATUS_KICK_OWNERS_PROC(exec);
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
		
		
		
//		/*
//		 * ユーザーのデータの更新を受け付ける
//		 * いつでも、、、、
//		 * 最大深度のすげ替え
//		 * 
//		 * ユーザーが持っているアイテムのキーで、更新が必要なものが飛んでくる。
//		 * 受け取ったら、過去のリクエストと比較してがんばる。
//		 */
//		if (exec.startsWith("UserItemCurrent+")) {
//			
//			String newArrivalItemData = exec.substring("UserItemCurrent+".length(), exec.length());
//			
//			JSONArray owningItemKeyArray = JSONParser.parseStrict(newArrivalItemData).isArray();
//			
//			//比較するとしたら、現在持っている/まだもっていない/取得しようとしている、　などのリクエスト状況と照らし合わせ、
//			//既に持っていればタイムスタンプを送り込んで比較、
//			//今読み込み中であれば、返答が着たら比較するように仕向けるようセット、
//			//まだ持っていなければ取得用リクエストを書く、といった所でしょう。
//			//今回は、リクエストとの簡単な比較だけ行います。 比較して、存在しなければ取得リクエストを追加。
//			
//			/*
//			 * リクエストと比較して、合致するものが無ければ追加する
//			 * TODO うーん、通信をブロック単位に分けて、そのブロックに対して実行、ってした方がいいのかな。
//			 * いつ、どんなイベントが実行されてもOKなようにするには、別の形でもいいのかもしれないが、さて。
//			 * あ、まあいいのか。　イベントごとの固有の識別さえされれば。
//			 * そういう意味で、ロード単位の識別は欲しい。通信が管理しやすくなる。
//			 */
//			try {
//				uStCont.compareToCurrentAndAddToRequest(uStCont.getUserKey(), owningItemKeyArray);
//			} catch (Exception e) {
//				debug.trace("UserItemCurren_error_"+e);
//			}
//			
//			procQueExecute(uStCont.getUserKey());//通信起動
//		}
		
		/*
		 * サーバからのPush通信を受け取る部分
		 */
		if (exec.startsWith("push+")) {
			pushController.pushedExecute(exec);
		}
	}
	
	
	
	
	
	
	
	
	

	

	
	
	
	
	

	

	

	


	

	protected void showLoginWindow(String loginWithURL) {
		final MyLoginBox diag = new MyLoginBox(this, loginWithURL);
		diag.show();
		diag.center();
	}

	

	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 自分が送ったメッセージであればtrue, それ以外はfalseを返す
	 * @param commandString
	 * @param userKey
	 * @return
	 */
	public boolean isMyself(JSONObject root, JSONObject myUserKey) {
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
	public String getUserNameFromUserKey (JSONObject root) {
		JSONObject userKey = root.get("userInfo").isObject();
		String userName = userKey.get("name").isString().toString();
		return userName;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * キューの通信を実行する。
	 * Asyncだから、チョッパやで終わる筈。
	 * @param userKey
	 */
	public void procQueExecute(JSONObject userKey) {
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
			 */
			if (request.get(ClientSideRequestQueueModel.REQUEST_TYPE_ADD_ITEM) != null) {
				debug.trace("REQUEST_TYPE_ADD_ITEM");
				String itemAddressKey = request.get(ClientSideRequestQueueModel.REQUEST_TYPE_ADD_ITEM);
				
				JSONObject itemAddressWithUserKey = JSONParser.parseStrict(itemAddressKey).isObject();
				
				itemAddressWithUserKey.put("userKey", userKey);
				
				greetingService.greetServer("addItemData+"+itemAddressWithUserKey,
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
			
			//アドレスからアイテムを取得する
			if (request.get(ClientSideRequestQueueModel.REQUEST_TYPE_GET_ITEM_FROM_ADDRESS) != null) {
				debug.trace("REQUEST_TYPE_GET_ITEM_FROM_ADDRESS");
				String itemAddress = request.get(ClientSideRequestQueueModel.REQUEST_TYPE_GET_ITEM_FROM_ADDRESS);
				
				greetingService.greetServer(ClientSideRequestQueueModel.REQUEST_TYPE_GET_ITEM_FROM_ADDRESS+itemAddress,
						new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						debug.trace("failure");
					}
					
					public void onSuccess(String result) {
						debug.trace("REQUEST_TYPE_GET_ITEM_FROM_ADDRESS_success!_"+result);
					}
				}
				);
			}
			
			//キーからアイテムを取得する
			if (request.get(ClientSideRequestQueueModel.REQUEST_TYPE_GET_ITEM_FROM_KEY) != null) {
				debug.trace("REQUEST_TYPE_GET_ITEM_FROM_KEY");
				String itemKey = request.get(ClientSideRequestQueueModel.REQUEST_TYPE_GET_ITEM_FROM_KEY);
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
				greetingService.greetServer(ClientSideRequestQueueModel.REQUEST_TYPE_UPDATE_MYDATA+userKeyForCurrent,
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
			
			
			
			/*
			 * 所持アイテムキーの情報をセットする
			 */
			if (userItemArray != null) {
				uStCont.setM_userItemArray(userItemArray);
			}
			
			
			/*
			 * ユーザーの用意が終わったので、channelを開く
			 */
			if (channelID != null) {
				setChannelID(channelID.toString());
			} else {
				debug.assertTrue(false, "なんらかの理由でchannlelIDが入ってないようです");
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
	public void setUpUserItemRequest(JSONObject userKey, JSONArray userItemArray) {
		int size = userItemArray.size();
		for (int i = 0; i < size; i++) {
			JSONObject key = new JSONObject();
			
			key.put("itemKey", userItemArray.get(i));//ココは、アイテムのキー
			key.put("userKey", userKey);
			debug.trace(i+"_このアイテムを取得したいのです_"+key);
			uStCont.addRequestToRequestQueue(key.toString(), ClientSideRequestQueueModel.REQUEST_TYPE_GET_ITEM_FROM_KEY);
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
	
	/**
	 * privateで有るべき。
	 * @param status
	 */
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
		procedure("loginWithURLPath");
	}
	
	

	public UserStatusController getUStCont() {
		debug.assertTrue(uStCont != null, "uStContが初期化されていない");
		return uStCont;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * ログイン処理
	 * @param exec
	 */
	private void execSTATUS_KICK_LOGIN_INIT(String exec) {
		if (exec.startsWith("startLogin+")) {
			
			//アドレスに対するチェックを行う。画像以外プレビューに対応したくない。
			
			
			/*
			 * ログイン画面を出す。
			 */
			String imageURL = exec.substring("startLogin+".length(), exec.length());
			
			
			JSONObject urlObject = JSONParser.parseStrict(imageURL).isObject();
			final String urlString = urlObject.get("imageAddress").isString().toString();
			
			debug.trace("ログインしたタイミングで、フォーカスしていた画像や物のURLを入力する");
			uStCont.setM_loginItemPath(urlString);
			
			
			Image image = new Image();
//			image.setUrl(urlString);
			image.setUrl(Resources.INSTANCE.s1().getURL());
			reg.fireEvent(new ScreenEvent(1, image));//なるほど、全て上書きされる訳だ。この部分を制御できればいいんだな。書き換え部分とそうでない部分の制御を、よりセンシティブに行う必要がある。
			//ちょうどスクリーンマネージャーの限界だったので、ここら辺で見切るとよかろう。
			
			image.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					showLoginWindow(urlString);
				}
			});
			
			setKickStatus(STATUS_KICK_LOGIN_PROC);
		}
	}
	private void execSTATUS_KICK_LOGIN_PROC(String exec) {

		if (exec.matches("No Match..")) {
			debug.trace("間違えました");
		}
		
		/*
		 * ログイン処理の実行
		 * まだ入力がされただけの段階なので、
		 * ログイン出来なければやり直し、になる。
		 * どこまでやり直しにさせるかは、サービス次第。
		 */
		if (exec.startsWith("loginWithURLPath")) {
			
			//実行！
			String nameWithPass = uStCont.getUserName()+":"+uStCont.getUserPass();
			
			
			/*
			 * アイテムをAddする必要があるのか、既に持っているのか、この時点で判らないので、キューにいれておく
			 */
			JSONObject itemAddressWithUserKey = new JSONObject();
			itemAddressWithUserKey.put("itemAddressKey", new JSONString(uStCont.getM_loginItemPath()));
			
			uStCont.addRequestToRequestQueue(itemAddressWithUserKey.toString(), ClientSideRequestQueueModel.REQUEST_TYPE_ADD_ITEM);
				
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
						debug.trace("到達");
						procedure("Channel_Open:"+result);
					}
				}
			}
			);
		}
	}
	private void execSTATUS_KICK_LOGIN_SUCCEEDED(String exec) {
		/*
		 * チャンネルの開始、繋ぎ終わってから次のフェーズへ
		 */
		if (exec.startsWith("Channel_Open:")) {
			String jsonString = exec.subSequence("Channel_Open:".length(), exec.length()).toString();
			pushController = new PushController(this);
			setUpUserKey(jsonString);//ユーザー情報が整った状態
		}
		
		if (exec.matches("SocketOpened")) {
			procQueExecute(uStCont.getUserKey());//キューを消費する(何が入っているかは知らない)
			
			cCont = new CanvasController(this,uStCont.getUserKey());
			cCont.initCanvas();
			
//			setKickStatus(STATUS_KICK_OWN_INIT);//アイテム全件を取得する
//			setKickStatus(STATUS_KICK_OWNERS_INITIALIZE_2);//アイテム一件を取得する
			setKickStatus(STATUS_KICK_LOADING_INIT);//ローディングを行う
			//ユーザーがログインした時に扱ったアイテムの確認をする
			procedure("initializeLoading");
		}
	}
	
	
	
	
	
	
	/**
	 * ローディング
	 * @param exec
	 */
	private void execSTATUS_KICK_LOADING_INIT(String exec) {
		if (exec.startsWith("initializeLoading")) {
			setKickStatus(STATUS_KICK_LOADING_PROC);
			
			/*
			 * 共通の状態としては、
			 * 
			 * ・ログイン時に指定したアイテムが、サーバ側に送られている
			 * ・保存してあるかどうかはわからない
			 * ・ユーザーの情報が最新である
			 * ・最新でない場合、最新を取得する
			 * 
			 * アイテムがユーザーに紐づいているから発生する事象。
			 * 	ユーザーがアイテムに対してなんらかの答えを持っている必要がある。
			 * が、サーバサイドまかせでいい筈。
			 * 
			 * ☆これが、クライアント-サーバではなく、クライアント-クライアントシャドウになれば、結構概念がかわるんじゃなかろうか。
			 * 
			 * 分散した処理を任せられるシャドウをもったクライアント、という思想はどうだろう。
			 * シャドウ同士が連携していれば、ユーザー間も繋げる。
			 * 
			 * シャドウはスケールする。
			 */
			//フォーカスをログイン時のアイテムに設定する
			uStCont.setM_nowFocusingItemAddress(uStCont.getM_loginItemPath());
			
//			//自分の最新データを取得
//			uStCont.addRequestToRequestQueue(uStCont.getUserKey().toString(), ClientSideRequestQueueModel.REQUEST_TYPE_UPDATE_MYDATA);
//			procQueExecute(uStCont.getUserKey());
			
			HTML loaingMessage = new HTML("Loading...");
			reg.fireEvent(new ScreenEvent(1, loaingMessage));
		}
	}

	private void execSTATUS_KICK_LOADING_PROC(String exec) {
		boolean m_enterWithOwn = false;
		
		{//すでにサーバ上にあった
			if (exec.startsWith("JoinedAsNewOwner+")) {
				debug.trace("自分がすでにサーバ上に存在していたあるアイテムについて、新オーナーとして加わった");
			}
			{
				//既に自分が持ってた
				if (exec.startsWith("ItemAlreadyOwn+")) {
					debug.trace("アイテムは既に登録されている");
					String owningItemKey = exec.substring("ItemAlreadyOwn+".length(), exec.length());
					debug.trace("owningItemKey_"+owningItemKey);
					
					//アイテムのキーが手に入っている
					
					if (m_enterWithOwn) {
						setKickStatus(STATUS_KICK_OWN_INIT);
						procedure("initializeOwning");
					} else {
						//ここで、直接遷移してもいい筈、だが、安易にやると前提がずれて行く。これは厳しいね。データ全体の前提がずれていく。
						//どうすれば担保できる？
						
						JSONObject focusingItemKey = JSONParser.parseStrict(owningItemKey).isObject().get("requestedItemKey").isObject();
							
						JSONObject root = new JSONObject();
						root.put("userKey", uStCont.getUserKey());
						root.put("itemKey", focusingItemKey);
						
						
						setKickStatus(STATUS_KICK_OWNERS_INIT);
						procedure("LoadingOwnersOfItem+"+root);
					}
				}
			}
		}
		
		
		{//アイテムがまだサーバ上に無かった場合
			if (exec.startsWith("ItemUpdated+")) {
				debug.trace("自分にアイテムが追加されたので、ユーザーデータのアップデートが必要");
				//ここで、フォーカスしているアイテムを取りに行く。
				
				JSONObject userKeyWithAddress = new JSONObject();
				userKeyWithAddress.put("userKey", uStCont.getUserKey());
				userKeyWithAddress.put("itemAddressAsIdentifier", new JSONString(uStCont.getM_nowFocusingItemAddress()));
				
				uStCont.addRequestToRequestQueue(userKeyWithAddress.toString(), ClientSideRequestQueueModel.REQUEST_TYPE_GET_ITEM_FROM_ADDRESS);
				
				procQueExecute(uStCont.getUserKey());
			}
			
			if (exec.startsWith("ItemFound+")) {//探していたアイテムが見つかり、かつフォーカスしていたものだったら
				debug.trace("アイテムが見つかったよ、つまりもう保存されてるよ");
				String owningItemKey = exec.substring("ItemFound+".length(), exec.length());

				/*
				 * 
			map.put("requested", currentItem);
			map.put("command", "ITEM_FOUND");//アイテムのデータを更新するきっかけにする。
			map.put("userInfo", currentUserDataModel.getKey());
				 */
				debug.trace("found_owningItemKey_"+owningItemKey);
				JSONObject requestedItemKey = JSONParser.parseStrict(owningItemKey).isObject().get("requestedItem").isObject().get("key").isObject();
				
				/*
				 * フォーカスがそのまま結構大事になるな、、、うーん、、仮だとどうでもよかったからな。
				 */
				uStCont.setM_nowFocusingItemKey(requestedItemKey);//フォーカスされたアイテムのキーとして保存しておく
				
				
				//ユーザーのデータを最新にする
				uStCont.addRequestToRequestQueue(uStCont.getUserKey().toString(),ClientSideRequestQueueModel.REQUEST_TYPE_UPDATE_MYDATA);
				procQueExecute(uStCont.getUserKey());
			}
			
			if (exec.startsWith(ClientSideRequestQueueModel.REQUEST_TYPE_UPDATE_MYDATA)) {
				String userOwnItemsArrayString = exec.substring(ClientSideRequestQueueModel.REQUEST_TYPE_UPDATE_MYDATA.length(),exec.length());
				JSONArray currentItemArray = JSONParser.parseStrict(userOwnItemsArrayString).isObject().get("itemKeys").isArray();
				
				uStCont.setM_userItemArray(currentItemArray);
				debug.trace("ユーザーデータとか受け取った_"+uStCont.getM_userOwningItemArray());
				
				if (m_enterWithOwn) {
					setKickStatus(STATUS_KICK_OWN_INIT);
					procedure("initializeOwning");
				} else {
					
					
					JSONObject root = new JSONObject();
					root.put("userKey", uStCont.getUserKey());
					root.put("itemKey", uStCont.getM_nowFocusingItemKey());
					
					
					setKickStatus(STATUS_KICK_OWNERS_INIT);
					procedure("LoadingOwnersOfItem+"+root);
				}
			}
		}
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 所持アイテム
	 */
	private void execSTATUS_KICK_OWN_INIT(String exec) {
		if(exec.startsWith("initializeOwning")) {
			setKickStatus(STATUS_KICK_OWN_PROC);
			
			//この時点での所持アイテムのキーから、アイテムの情報を召還。　全てを描画対象にする
			setUpUserItemRequest(uStCont.getUserKey(), uStCont.getM_userOwningItemArray());
			procQueExecute(uStCont.getUserKey());
			
			debug.trace("描画対象、データ待ち中_"+uStCont.getM_userOwningItemArray());
			
			HTML loaingMessage = new HTML("Initializing...");
			reg.fireEvent(new ScreenEvent(1, loaingMessage));
		}
		
		
	}
	private void execSTATUS_KICK_OWN_PROC(String exec) {
		{//アイテムが送られてきた
			if (exec.startsWith("ItemReceived+")) {
				String rootString = exec.substring("ItemReceived+".length(), exec.length());
				debug.trace("rootString_"+rootString);
				
				try {
					JSONObject root = JSONParser.parseStrict(rootString).isObject();
					JSONObject item = root.get("item").isObject();
	
					debug.trace("execSTATUS_KICK_OWN_PROC_PUSH_ITEM_item_"+item);
					
					if (item != null) {
						//アイテムの主キーから、設定されていたリクエストを完了に指定する
						String itemKeyNameString = null;
						
						JSONObject itemKeyObject = item.get("key").isObject();
						JSONString itemKeyName = itemKeyObject.get("name").isString();
						itemKeyNameString = itemKeyName.toString();
						
						
						uStCont.completeRequest(itemKeyNameString);//完了にする そのほか、アップデートを押し付けることが出来る!!
						
						uStCont.putItemData(item);//pushしてきてもらったデータ、保存する。
						List<ClientSideCurrentItemDataModel> originArray = uStCont.getCurrentItems();
						
						JSONArray array = new JSONArray();
						
						for (int i = 0; i < originArray.size(); i++) {//jsonArray化する
							ClientSideCurrentItemDataModel currentModel = originArray.get(i);
							array.set(i, currentModel.itemItself());
						}
						
						//キーで呼び出したアイテムの情報アレイが完成してるので、表示する
						cCont.updateItemcInfo(array);
					}
				} catch (Exception e) {
					debug.trace("PUSH_ITEM_error_"+e);
				}
				HTML loaingMessage = new HTML("Initializing2...");
				reg.fireEvent(new ScreenEvent(1, loaingMessage));
			}
			
			if (false) {
				HTML loaingMessage = new HTML("All Item Loaded");
				reg.fireEvent(new ScreenEvent(1, loaingMessage));
				//全アイテムがそろった = リクエストしていたアイテム全てが届いた、、、
			}
		}
		
		
		{//
			
		}
		
		{//タグ関連
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
		}
		
		
		{//遷移
			/*
			 * どのアイテムがタッチされたかで、タッチされたアイテムのキーから
			 * そのアイテムに寄せられたコメント一覧へとジャンプする
			 */
			if (exec.startsWith("ItemTapped+")) {
				setKickStatus(STATUS_KICK_OWNERS_INIT);
				//ロードするアイテムのキーを受け取り、コメントの情報を表示する
				String tappedItemKey = exec.substring("ItemTapped+".length(),  exec.length());
				debug.trace("tappedItemKey_"+tappedItemKey);
				
//				debug.assertTrue(false, "調整中");
				//この時点で存在している前提が、物事の複雑さを増している。
				
				HTML loaingMessage = new HTML("Loading Comments...");
				reg.fireEvent(new ScreenEvent(1, loaingMessage));
				
				JSONObject tappedItemKeyObject = JSONParser.parseStrict(tappedItemKey).isObject();
				
				JSONObject root = new JSONObject();
				root.put("userKey", tappedItemKeyObject.get("userKey").isObject());
				root.put("itemKey", tappedItemKeyObject.get("itemKey").isObject());
				
				procedure("LoadingOwnersOfItem+"+root);
			}
		}
	}
	
	
	
	


	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * フォーカスしているアイテムキーから、コメントを集める
	 * @param exec
	 */
	private void executeSTATUS_KICK_OWNERS_INIT(String exec) {
		/*
		 * アイテムキーを指定してのオーナーズの初期化
		 */
		if (exec.startsWith("LoadingOwnersOfItem+")) {
			setKickStatus(STATUS_KICK_OWNERS_PROC);
			
			
			
			HTML ownersOfItem = new HTML("There are owners of this item");
			reg.fireEvent(new ScreenEvent(1, ownersOfItem));
			
			String itemKeyWithUserKey = exec.substring("LoadingOwnersOfItem+".length(), exec.length());
			
			
			debug.trace("itemKeyWithUserKey_"+itemKeyWithUserKey);
			
			JSONObject root = null;
			 
			try {
				//{"itemKey":{"kind":"item", "id":0, "name":"\"http://images.paraorkut.com/img/funnypics/images/f/fail_cat-12835.jpg\""}, "userKey":{"kind":"user", "id":0, "name":"aaaa@aaaa"}}
				root = JSONParser.parseStrict(itemKeyWithUserKey).isObject();
				JSONObject userKey = root.get("userKey").isObject();
				JSONObject itemKey = root.get("itemKey").isObject();
			} catch (Exception e) {
				debug.assertTrue(false, "オーナー画面の初期化が不成立");
			}
			
			
			uStCont.addRequestToRequestQueue(root.toString(), ClientSideRequestQueueModel.REQUEST_TYPE_GETALLCOMMENT);
			procQueExecute(uStCont.getUserKey());//サーバにリクエストを送りこむ
			
			procedure("GetAllCommentOfItem+"+root);//現在フォーカスしている
			itemCommentCont = new ItemCommentController(this, uStCont.getUserKey(), uStCont.getM_nowFocusingItemKey());//コメントコントローラの初期化
		}
	}
	private void executeSTATUS_KICK_OWNERS_PROC(String exec) {
		
//		/*
//		 * キーからアイテムを取得する
//		 */
//		if (exec.startsWith("GetAllCommentOfItem+")) {
//			String itemKey = exec.substring("GetAllCommentOfItem+".length(), exec.length());
//			
//			uStCont.addRequestToRequestQueue(itemKey, ClientSideRequestQueueModel.REQUEST_TYPE_GETALLCOMMENT);
//			procQueExecute(uStCont.getUserKey());//サーバにリクエストを送りこむ
//		}
		
//		/*
//		 * アイテムが見つかったので、コメントを取得する
//		 */
//		if (exec.startsWith("ItemFound+")) {
//			JSONObject itemKeyObject = null;
//			try {
//				String itemData = exec.substring("ItemFound+".length(), exec.length());
//				JSONObject itemDataObject = JSONParser.parseStrict(itemData).isObject();
//				debug.trace("オブジェクトはあった_"+itemDataObject);
//				itemKeyObject = itemDataObject.get("requested").isObject().get("key").isObject();
//			} catch (Exception e) {
//				debug.trace("アイテムが見つかったよ__e_"+e);
//			}
//			
//			JSONObject itemKeyWithUserKey = new JSONObject();
//			
//			itemKeyWithUserKey.put("itemKey", itemKeyObject);
//			itemKeyWithUserKey.put("userKey", uStCont.getUserKey());
////			LoadingOwnersOfItemでやっちまったほうが一元化できる。
//			
//			setKickStatus(STATUS_KICK_OWNERS_INIT);
//			procedure("LoadingOwnersOfItem+"+itemKeyWithUserKey);
//			//ユーザー画像が出ない、あと、ウインドウが出ない(分派が遅すぎるポイントが複数ある、ということ。)
//			/*
//			 * ユーザー画像IDと、あと何よりも画面要素。
//			 * ココからの初期化で
//			 */
//		}
		
		
//		if (exec.startsWith("TagUpload+")) {
////			入力された文字列を、自分がマスターの自分の言葉として足す
//			//watch touch 
//			String key = exec.substring("TagUpload+".length(),  exec.length());
//		}
		
		
		
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
			itemCommentCont.removeMyYetPanel(uStCont.getUserName());
			
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
//			kCont.GetitemCommentCont() = null;
			
			debug.trace("exec_"+exec);
		}
	}

	
		
}


