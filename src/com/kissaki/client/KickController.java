package com.kissaki.client;


import java.util.Arrays;//array
import java.util.Iterator;
import java.util.List;//list
import java.util.Map;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.kissaki.client.MessengerGWTCore.MessengerGWTImplement;
import com.kissaki.client.MessengerGWTCore.MessengerGWTInterface;
import com.kissaki.client.PushController.PushController;
import com.kissaki.client.canvasController.CanvasController;
import com.kissaki.client.channel.Channel;
import com.kissaki.client.channel.ChannelFactory;
import com.kissaki.client.channel.SocketListener;
import com.kissaki.client.imageResource.Resources;
import com.kissaki.client.login.MyLoginBox;
import com.kissaki.client.ownersViewController.OwnersViewController;
import com.kissaki.client.procedure.ItemCommentController;
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
public class KickController implements KickStatusInterface, MessengerGWTInterface {
	
	Debug debug = null;
	
	MessengerGWTImplement messenger;
	
	CanvasController canvasCont;
	ScreenEventRegister reg;
	ItemCommentController itemCommentCont;
	
	String DEFAULT_REQUEST_PASS = "http://images.paraorkut.com/img/funnypics/images/f/fail_cat-12835.jpg";
	
	UserStatusController uStCont = null;
	
	PushController pushController = null;
	
	OwnersViewController oVCont = null;
	
	int m_kickStatus = STATUS_KICK_APPINITIALIZE;
	
	
	
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
	
	
	private static final List<String> Months = Arrays.asList(
	          "Like", "Want", "Have", "Had",
	          "Good","とか"
	     );

	
	
	public KickController () {
		debug = new Debug(this);
		debug.trace("コンストラクタに到達");
		
		messenger = new MessengerGWTImplement(KICK_CONTROLLER, this);
		
		uStCont = new UserStatusController();//Ginから取得出来るといいな。
		
		
		debug.trace("status_"+uStCont.getUserStatus());
		debug.trace("name_"+uStCont.getUserName());
		debug.trace("password_"+uStCont.getUserPass());
		
		
		
		setKickStatus(STATUS_KICK_APPINITIALIZE);
//		procedure("startSamplePage");
		messenger.call(messenger.getName(), "startSamplePage");
	}
	
//	public void procedure (String s) {
//		debug.assertTrue(false, "dead");
//		debug.trace("dummy_procedure_s"+s);
//	}
	
	
	/**
	 * 実行処理
	 */
	@Override
	public void receiveCenter(String message) {
		String exec = messenger.getCommand(message);
		
		try {
		switch (getKickStatus()) {
		case STATUS_KICK_APPINITIALIZE:
			if (exec.equals("startSamplePage")) {
				
				/*
				 * サンプル画面を出す
				 */
				setKickStatus(STATUS_KICK_TESTVIEW_INIT);
				
				
//				procedure("testViewInitialize");
				messenger.call(messenger.getName(), "testViewInitialize");
			}
			
			
		case STATUS_KICK_TESTVIEW_INIT:
			if (exec.matches("testViewInitialize")) {
				/*
				 * ログイン画面を出す。
				 */
				HTML a = new HTML("<p id=\"spinner\">Please wait while we do what we do best.</p>");//アドレス埋め込み、そしてバックグラウンドに押し込む  この要素を入れるタイミングで確かに再描画とか発生するわな。
//				a = new HTML("");
				
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
				
//				procedure("testViewToLogin");
				
				messenger.call(messenger.getName(), "testViewToLogin", messenger.tagValue("address", DEFAULT_REQUEST_PASS));
				
			}
			
		case STATUS_KICK_TESTVIEW_PROC:
			if (exec.equals("testViewToLogin")) {

				//リンクが触られたら、ログインに行く。
				setKickStatus(STATUS_KICK_LOGIN_INIT);
				String address = messenger.getValueForTag(message, "address").isString().toString();
				
//				procedure("startLogin+"+addressObject);
				messenger.call(messenger.getName(), "startLogin", messenger.tagValue("address", address));
			}
			break;
		
			
			
			/*
			 * ログイン処理
			 */
		case STATUS_KICK_LOGIN_INIT:
			execSTATUS_KICK_LOGIN_INIT(message);
		case STATUS_KICK_LOGIN_PROC:
			execSTATUS_KICK_LOGIN_PROC(message);
			break;
			
			
			/*
			 * ログインが成功した後の処理、アプリケーションの準備最終フェーズ
			 */
		case STATUS_KICK_LOGIN_SUCCEEDED:
			execSTATUS_KICK_LOGIN_SUCCEEDED(message);
			break;
			
			
			
			/*
			 * これ以降は、
			 * channel接続が通った状態
			 */
		case STATUS_KICK_LOADING_INIT:
			execSTATUS_KICK_LOADING_INIT(message);
		case STATUS_KICK_LOADING_PROC:
			execSTATUS_KICK_LOADING_PROC(message);
			break;
			
			
			
		case STATUS_KICK_LOADING_USER_INIT:
			execSTATUS_KICK_LOADING_USER_INIT(message);
		case STATUS_KICK_LOADING_USER_PROC:
			execSTATUS_KICK_LOADING_USER_PROC(message);
			break;
		

			/*
			 * 自分が所有してるアイテムの情報を取得する
			 */
		case STATUS_KICK_OWN_INIT:
			execSTATUS_KICK_OWN_INIT(message);
		case STATUS_KICK_OWN_PROC:
			execSTATUS_KICK_OWN_PROC(message);
			break;
			
			
			/*
			 * 使用しない
			 */
//		case STATUS_KICK_OWNERS_INIT:
//			executeSTATUS_KICK_OWNERS_INIT(exec);
//		case STATUS_KICK_OWNERS_PROC:
//			executeSTATUS_KICK_OWNERS_PROC(exec);
//			break;
		
			
		case STATUS_KICK_OWNERSVIEW_INIT:
			executeSTATUS_KICK_OWNERSVIEW_INIT(message);
			
		case STATUS_KICK_OWNERSVIEW_PROC:
			executeSTATUS_KICK_OWNERSVIEW_PROC(message);
			break;
			
			
			
			
		case STATUS_KICK_OUTED_INIT:
			setKickStatus(STATUS_KICK_OUTED_PROC);
			
		case STATUS_KICK_OUTED_PROC:
			
			break;
			

		default:
			debug.trace("未設定のアプリケーションステータス_" + getKickStatus());
			break;
		}
		
		
		
		} catch (Exception e) {
			debug.trace("procedure_error_"+e);
		}
	}
	
	
	
	
	
	
	
	
	

	

	
	
	
	
	

	

	

	


	
	
	
	
	
	
	

	

	

	protected void showLoginWindow(String loginWithURL) {
		final MyLoginBox diag = new MyLoginBox(loginWithURL);
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
			 * タグ情報を更新する
			 */
			if (request.get(ClientSideRequestQueueModel.REQUEST_TYPE_UPDATE_TAG) != null) {
				String tagUpdateRequest = request.get(ClientSideRequestQueueModel.REQUEST_TYPE_UPDATE_TAG);
				
				tagUpdateRequest = addUUID(request, tagUpdateRequest);
				
				greetingService.greetServer(ClientSideRequestQueueModel.REQUEST_TYPE_UPDATE_TAG+tagUpdateRequest,
						new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						debug.trace("failure");
					}
					
					public void onSuccess(String result) {
//						debug.trace("REQUEST_TYPE_UPDATE_TAG_success_"+result);
//						procedure("タグ更新完了");
					}
				}
				);
			}
			
			/*
			 * タグを加算する
			 */
			if (request.get(ClientSideRequestQueueModel.REQUEST_TYPE_ADDNEWTAG) != null) {
				//タグがリクエストに並ぶ筈
				debug.trace("REQUEST_TYPE_ADDNEWTAG_中身_"+request.get(ClientSideRequestQueueModel.REQUEST_TYPE_ADDNEWTAG));
				
				String tagRequest = request.get(ClientSideRequestQueueModel.REQUEST_TYPE_ADDNEWTAG);
				
				tagRequest = addUUID(request, tagRequest);
				
				greetingService.greetServer(ClientSideRequestQueueModel.REQUEST_TYPE_ADD_TAG_TO_ITEM+tagRequest,
						new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						debug.trace("failure");
					}
					
					public void onSuccess(String result) {
//						debug.trace("REQUEST_TYPE_ADDNEWTAG_success_"+result);
//						procedure("タグ付加完了");
					}
				}
				);
			}
			
			/*
			 * アイテムを加算する
			 */
			if (request.get(ClientSideRequestQueueModel.REQUEST_TYPE_ADD_ITEM_WITH_URL) != null) {
				debug.trace("REQUEST_TYPE_ADD_ITEM");
				String itemAddressKey = request.get(ClientSideRequestQueueModel.REQUEST_TYPE_ADD_ITEM_WITH_URL);
				
				JSONObject itemAddressWithUserKey = JSONParser.parseStrict(itemAddressKey).isObject();
				
				debug.trace("REQUEST_TYPE_ADD_ITEM_itemAddressWithUserKey_"+itemAddressWithUserKey);
				
				itemAddressWithUserKey.put("userKey", userKey);
				
				itemAddressKey = addUUID(request, itemAddressWithUserKey.toString());
				
				greetingService.greetServer(ClientSideRequestQueueModel.REQUEST_TYPE_ADD_ITEM_WITH_URL+itemAddressKey,
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
				
				itemAddress = addUUID(request, itemAddress);
				
				greetingService.greetServer(ClientSideRequestQueueModel.REQUEST_TYPE_GET_ITEM_FROM_ADDRESS+itemAddress,
						new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						debug.trace("failure");
					}
					
					public void onSuccess(String result) {
//						debug.trace("REQUEST_TYPE_GET_ITEM_FROM_ADDRESS_success!_"+result);
					}
				}
				);
			}
			
			//キーからアイテムを取得する
			if (request.get(ClientSideRequestQueueModel.REQUEST_TYPE_GET_ITEM_FROM_KEY) != null) {
				debug.trace("REQUEST_TYPE_GET_ITEM_FROM_KEY");
				String itemKey = request.get(ClientSideRequestQueueModel.REQUEST_TYPE_GET_ITEM_FROM_KEY);
				
				itemKey = addUUID(request, itemKey);
				
				greetingService.greetServer(ClientSideRequestQueueModel.REQUEST_TYPE_GET_ITEM_FROM_KEY+itemKey,
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
				
				userKeyForCurrent = addUUID(request, userKeyForCurrent);
				
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
			if (request.get(ClientSideRequestQueueModel.REQUEST_TYPE_GET_SPECIFIC_USER_INFORMATION) != null) {
				String userNameWithPass = request.get(ClientSideRequestQueueModel.REQUEST_TYPE_GET_SPECIFIC_USER_INFORMATION);
				
				userNameWithPass = addUUID(request, userNameWithPass);
				greetingService.greetServer(ClientSideRequestQueueModel.REQUEST_TYPE_GET_SPECIFIC_USER_INFORMATION+userNameWithPass,
						new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						debug.trace("failure");
					}
					
					public void onSuccess(String result) {
						debug.trace("GET_SPECIFIC_USER_INFORMATION_succeed!_"+result);
					}
				});
			}
			
			if (request.get(ClientSideRequestQueueModel.REQUEST_TYPE_GET_USER_INDIVIDUAL_TAG) != null) {
				String getndividualTagRequestObject = request.get(ClientSideRequestQueueModel.REQUEST_TYPE_GET_USER_INDIVIDUAL_TAG);
				
				getndividualTagRequestObject = addUUID(request, getndividualTagRequestObject);
				greetingService.greetServer(ClientSideRequestQueueModel.REQUEST_TYPE_GET_USER_INDIVIDUAL_TAG+getndividualTagRequestObject,
						new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						debug.trace("failure");
					}
					
					public void onSuccess(String result) {
//						debug.trace("REQUEST_TYPE_GET_USER_INDIVIDUAL_TAG_success!_"+result);
					}
				});
			}
			if (request.get(ClientSideRequestQueueModel.REQUEST_TYPE_ADDCOMMENT) != null) {
				debug.trace("REQUEST_TYPE_ADDCOMMENT");
				String addCommentObject = request.get(ClientSideRequestQueueModel.REQUEST_TYPE_ADDCOMMENT);
				
				addCommentObject = addUUID(request, addCommentObject);
				
				greetingService.greetServer(ClientSideRequestQueueModel.REQUEST_TYPE_ADDCOMMENT+addCommentObject,
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
				
				itemKeyForGetComment = addUUID(request, itemKeyForGetComment);
				
				//所持ユーザーと、そのコメントを取得、更新があったら(=この返答があったら)逐一塗り替える。
				greetingService.greetServer(ClientSideRequestQueueModel.REQUEST_TYPE_GETALLCOMMENT+itemKeyForGetComment,
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
			if (request.get(ClientSideRequestQueueModel.GET_ALL_USER_WHO_HAVE_THIS_ITEM) != null) {
				String itemKeyForGetAllCommentedUser = request.get(ClientSideRequestQueueModel.GET_ALL_USER_WHO_HAVE_THIS_ITEM);
				
				itemKeyForGetAllCommentedUser = addUUID(request, itemKeyForGetAllCommentedUser);
				
				//所持ユーザーと、そのコメントを取得、更新があったら(=この返答があったら)逐一塗り替える。
				greetingService.greetServer(ClientSideRequestQueueModel.GET_ALL_USER_WHO_HAVE_THIS_ITEM+itemKeyForGetAllCommentedUser,
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
				
				itemKeyForGetComment = addUUID(request, itemKeyForGetComment);
				
				//所持ユーザーと、そのコメントを取得、更新があったら(=この返答があったら)逐一塗り替える。
				greetingService.greetServer(ClientSideRequestQueueModel.REQUEST_TYPE_GET_LATESTCOMMENT+itemKeyForGetComment,
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
	 * JSONのパッケージを全て展開し、必要なUUIDをセットする
	 * @param itemKey
	 * @return
	 */
	private String addUUID(Map<String, String>request,  String itemKey) {
		JSONObject base = JSONParser.parseStrict(itemKey).isObject();
		base.put(ClientSideRequestQueueModel.KEY_STRING_TRIGGER_ID, new JSONString(request.get(ClientSideRequestQueueModel.KEY_STRING_TRIGGER_ID)));
		return base.toString();
	}

	/**
	 * ユーザーデータ設定を行う。
	 * channelIDの文字列を返す。
	 * @param jsonString
	 */
	private String setUpUserKey(String result) {
		JSONObject root = null;
		
		JSONObject userData = null;
		
		int userImageNumber = -1;
		JSONArray userItemArray = null;
		String channelID = null;
		
		try {
			if (JSONParser.parseStrict(result).isObject() != null) {
				root = JSONParser.parseStrict(result).isObject();
				debug.trace("root_"+root);
			}
			
			debug.assertTrue(root.get("channelID").isString() != null, "root.get(\"channelID\").isString() = null");
			debug.assertTrue(root.get("userData").isObject() != null, "root.get(\"userData\").isObject() = null");
			
			
			if (root != null) {
				if (root.get("channelID").isString() != null) {
					channelID = root.get("channelID").isString().toString();
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
					JSONObject userKey = userData.get("key").isObject();
					uStCont.setUserKey(userKey);
					
					userImageNumber = (int)userData.get("imageNumber").isNumber().doubleValue();
					uStCont.setM_imangeNumber(userImageNumber);
					
					if (userItemArray != null) {
						uStCont.setM_userItemArray(userItemArray);
					}
				}
			}
			
			return channelID;
		} catch (Exception e) {
			debug.trace("fail to initialize_"+e);
		}
		return null;
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
				messenger.call(KICK_CONTROLLER, "SocketOpened");
			}
			
			public void onMessage(String encodedData) {
				//debug.trace(uStCont.getUserName()+"_メッセージを受け取りました_" + encodedData);
//				messenger.call(KICK_PUSHCONTROLLER, "push", messenger.tagValue("encodedData", encodedData));
				pushController.receiveCenter(encodedData);
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

	
	
	

	public UserStatusController getUStCont() {
		debug.assertTrue(uStCont != null, "uStContが初期化されていない");
		return uStCont;
	}
	

	/**
	 * 現在所持しているアイテムを、全部ウインドウの形式で表示する
	 */
	private void showItems() {
		List<ClientSideCurrentItemDataModel> originArray = uStCont.getCurrentItems();
		
		
		JSONArray tagArray = new JSONArray();
		JSONArray itemArray = new JSONArray();
		
		for (int i = 0; i < originArray.size(); i++) {//jsonArray化する
			ClientSideCurrentItemDataModel currentModel = originArray.get(i);
			itemArray.set(i, currentModel.itemItself());
			
			JSONArray currentItemTagArray = uStCont.getagArrayFromM_tagMapModelByItemName(currentModel.getItemKeyName());
			debug.trace("showItems_currentItemTagArray_"+currentItemTagArray);
			if (currentItemTagArray != null) {
				tagArray.set(i, currentItemTagArray);
			} else {
				debug.trace(i+"_nullセットは出来てる訳では無い、と思うのだが。");
//				tagArray.set(i, null);//これって出来るのかな
			}
		}
		
		//キーで呼び出したアイテムの情報アレイが完成してるので、表示する
		canvasCont.updateItemInfo(itemArray, tagArray);
	}
	
	private void hideUserTag() {
		canvasCont.hideUserTag();
	}
	
	private void showUserTag() {
		canvasCont.showUserTag();
	}
	
	
	
	/**
	 * ユーザーが所持しているアイテムの中から、特に特定のアイテムを表示する
	 * @param m_nowFocusingItemKey
	 */
	private void showOneItem(JSONObject m_nowFocusingItemKey) {
		List<ClientSideCurrentItemDataModel> originArray = uStCont.getCurrentItems();
		
		debug.assertTrue(0 < originArray.size(), "originArrayが０件");
		
		for (Iterator<ClientSideCurrentItemDataModel> dataModelItel = originArray.iterator(); dataModelItel.hasNext();) {
			ClientSideCurrentItemDataModel currentClientSideCurrentItemDataModel = dataModelItel.next();
			
			if (currentClientSideCurrentItemDataModel.getItemKey().toString().equals(m_nowFocusingItemKey.toString())) {
				
				JSONArray array = new JSONArray();
				array.set(0, currentClientSideCurrentItemDataModel.itemItself());
				
				canvasCont.updateItemInfo(array, null);
				canvasCont.hideUserTag();
				return;
			}
		}
		debug.trace("showItem_探せませんでした_"+m_nowFocusingItemKey);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * ログイン処理
	 * @param exec
	 */
	private void execSTATUS_KICK_LOGIN_INIT(String message) {
		String exec = messenger.getCommand(message);
		
		if (exec.equals("startLogin")) {
			/*
			 * ログイン画面を出す。
			 */
			final String address = messenger.getValueForTag(message, "address").isString().toString();
			
			
			uStCont.setM_loginItemPath(address);
			
			
			Image image = new Image();
			
			image.setUrl(Resources.INSTANCE.s1().getURL());//仮の物体の画像アドレス
			
			
			reg.fireEvent(new ScreenEvent(1, image));//なるほど、全て上書きされる訳だ。この部分を制御できればいいんだな。書き換え部分とそうでない部分の制御を、よりセンシティブに行う必要がある。
			//ちょうどスクリーンマネージャーの限界だったので、ここら辺で見切るとよかろう。
			
			image.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					showLoginWindow(address);
				}
			});
			
			setKickStatus(STATUS_KICK_LOGIN_PROC);
		}
	}
	private void execSTATUS_KICK_LOGIN_PROC(String message) {
		String exec = messenger.getCommand(message);
		
		if (exec.equals("No Match..")) {
			debug.trace("間違えました");
			
			//再表示処理
		}
		
		/*
		 * ログイン処理の実行
		 * まだ入力がされただけの段階なので、
		 * ログイン出来なければやり直し、になる。
		 * どこまでやり直しにさせるかは、サービス次第。
		 */
		if (exec.equals("loginWithURLPath")) {
			
			
			inputUserName(messenger.getValueForTag(message, "name").isString().toString());
			inputUserPass(messenger.getValueForTag(message, "pass").isString().toString());
			
			
			//実行！
			String nameWithPass = uStCont.getUserName()+":"+uStCont.getUserPass();
			
			
			/*
			 * アイテムをAddする必要があるのか、既に持っているのか、この時点で判らないので、キューにいれておく
			 */
			JSONObject itemAddressWithUserKey = new JSONObject();
			itemAddressWithUserKey.put("itemAddressKey", new JSONString(uStCont.getM_loginItemPath()));
			
			
			uStCont.addRequestToRequestQueue(itemAddressWithUserKey.toString(), ClientSideRequestQueueModel.REQUEST_TYPE_ADD_ITEM_WITH_URL);
			//サーバサイドにユーザー名とパスを送る
			greetingService.greetServer(ClientSideRequestQueueModel.REQUEST_TYPE_LOGIN+nameWithPass,
					new AsyncCallback<String>() {
				public void onFailure(Throwable caught) {
					debug.trace("failure");
				}
				
				public void onSuccess(String result) {
					if (result.matches("ログインパスワードが違います")) {//やりなおし
						setKickStatus(STATUS_KICK_LOGIN_INIT);
						
//						procedure("No Match..");
						messenger.call(KICK_CONTROLLER, "No Match..");
						
					} else {
						setKickStatus(STATUS_KICK_LOGIN_SUCCEEDED);
						
						debug.trace("result_"+result);
						
						String channelID = setUpUserKey(result);//fail or login
						
						debug.trace("channelID_"+channelID);
						
						setKickStatus(STATUS_KICK_LOGIN_SUCCEEDED);
						messenger.call(KICK_CONTROLLER, "ChannelReady", messenger.tagValue("channelID", messenger.removeDoubleQuatation(channelID)));
						
					}
				}
			}
			);
		}
	}
	private void execSTATUS_KICK_LOGIN_SUCCEEDED(String message) {
		String exec = messenger.getCommand(message);
		
		/*
		 * チャンネルの開始、繋ぎ終わってから次のフェーズへ
		 */
		if (exec.equals("ChannelReady")) {
			pushController = new PushController(this);//こいつの中でユーザーコンテキストを扱うための処理、ほんとはこのとき入れればいいんだと思うんだが。
			
			setChannelID(messenger.getValueForTag(message, "channelID").isString().toString());
		}
		
		
		if (exec.equals("SocketOpened")) {
			procQueExecute(uStCont.getUserKey());//キューを消費する(何が入っているかは知らない)
			
			
			canvasCont = new CanvasController(uStCont.getUserKey());
			canvasCont.initCanvas();
			
			
			setKickStatus(STATUS_KICK_LOADING_INIT);//ローディングを行う
			//ユーザーがログインした時に扱ったアイテムの確認をする
//			procedure("initializeLoading");
			messenger.call(KICK_CONTROLLER, "initializeLoading");
		}
	}
	
	
	
	
	
	
	/**
	 * ローディング
	 * @param exec
	 */
	private void execSTATUS_KICK_LOADING_INIT(String message) {
		String exec = messenger.getCommand(message);
		
		if (exec.equals("initializeLoading")) {
			
			debug.trace("initializeLoading_到着");
			setKickStatus(STATUS_KICK_LOADING_PROC);
			
			
			//フォーカスをログイン時のアイテムに設定する
			uStCont.setM_nowFocusingItemAddress(uStCont.getM_loginItemPath());
			

			HTML loaingMessage = new HTML(uStCont.getUserName()+"@Loading...");
			reg.fireEvent(new ScreenEvent(1, loaingMessage));
		}
	}

	private void execSTATUS_KICK_LOADING_PROC(String message) {
		String exec = messenger.getCommand(message);
		boolean m_enterWithOwn = false;
		debug.trace("exec_"+exec);
		{//すでにサーバ上にあった
//			if (exec.startsWith("JoinedAsNewOwner+")) {
//				debug.trace("自分がすでにサーバ上に存在していたあるアイテムについて、新オーナーとして加わった");
//			}
			{
				//既に自分が持ってた
				if (exec.equals("ItemAlreadyOwn")) {
					debug.trace("アイテムは既に登録されている");
					
					JSONObject root = messenger.getValueForTag(message, "root").isObject();
					
					//アイテムのキーが手に入っているので、フォーカスにセットする
					JSONObject focusingItemKey = root.get("requestedItemKey").isObject();
					uStCont.setM_nowFocusingItemKey(focusingItemKey);
					
					//手に入ったので、リクエストを完了にする
					uStCont.completeRequest(root.get(ClientSideRequestQueueModel.KEY_STRING_TRIGGER_ID).isString());
					
					
					if (m_enterWithOwn) {
						setKickStatus(STATUS_KICK_OWN_INIT);
//						procedure("initializeOwning");
						messenger.call(messenger.getName(), "initializeOwning");
					} else {
						//ここで、直接遷移してもいい筈、だが、安易にやると前提がずれて行く。これは厳しいね。フェーズごとのデータ全体の前提がずれていく。
						//どうすれば担保できる？
						
							
						JSONObject userKeyWithItemKey = new JSONObject();
						userKeyWithItemKey.put("userKey", uStCont.getUserKey());
						userKeyWithItemKey.put("itemKey", focusingItemKey);
						
						
						setKickStatus(STATUS_KICK_OWNERSVIEW_INIT);
//						procedure("LoadingOwnersOfItem+"+userKeyWithItemKey);
						messenger.call(KICK_CONTROLLER, "LoadingOwnersOfItem", messenger.tagValue("userKeyWithItemKey", userKeyWithItemKey));
					}
				}
			}
		}
		
		
		{//アイテムがまだサーバ上に無かった場合
			if (exec.equals("ITEM_ADDED_TO_USER_ItemUpdated")) {
				debug.trace("自分にアイテムが追加されたので、ユーザーデータのアップデートが必要");
				
				JSONObject root = messenger.getValueForTag(message, "root").isObject();
				
				//リクエストを完了させる
				uStCont.completeRequest(root.get(ClientSideRequestQueueModel.KEY_STRING_TRIGGER_ID).isString());
				
				//この時点で件数が1/1の筈
				debug.assertTrue(uStCont.getAlreadyFinishedRequestNumber() == uStCont.getAllRequestNumber(), "ロード完了漏れ");
				
				//ここで、フォーカスしているアイテムを取りに行く。
				JSONObject userKeyWithAddress = new JSONObject();
				userKeyWithAddress.put("userKey", uStCont.getUserKey());
				userKeyWithAddress.put("itemAddressAsIdentifier", new JSONString(uStCont.getM_nowFocusingItemAddress()));
				
				uStCont.addRequestToRequestQueue(userKeyWithAddress.toString(), ClientSideRequestQueueModel.REQUEST_TYPE_GET_ITEM_FROM_ADDRESS);
				
				procQueExecute(uStCont.getUserKey());
			}
			
			if (exec.equals("ItemFound")) {//探していたアイテムが見つかり、かつフォーカスしていたものだったら
				JSONObject rootObject = messenger.getValueForTag(message, "root").isObject();
				//Finish request
				JSONString id = rootObject.get(ClientSideRequestQueueModel.KEY_STRING_TRIGGER_ID).isString();
				uStCont.completeRequest(id);
				
				debug.assertTrue(uStCont.getAlreadyFinishedRequestNumber() == uStCont.getAllRequestNumber(), "終わっていないリクエストが存在する");
				
				JSONObject requestedItemKey = rootObject.get("requestedItem").isObject().get("key").isObject();
				
				
				/*
				 * フォーカスがそのまま結構大事になるな、、、うーん、、仮だとどうでもよかったからな。
				 */
				uStCont.setM_nowFocusingItemKey(requestedItemKey);//フォーカスされたアイテムのキーとして保存しておく
				
				
				
				//ユーザーのデータを最新にする
				uStCont.addRequestToRequestQueue(uStCont.getUserKey().toString(),ClientSideRequestQueueModel.REQUEST_TYPE_UPDATE_MYDATA);
				procQueExecute(uStCont.getUserKey());
			}
			
			//this is final point of login with add new item and that is first.
			if (exec.equals(ClientSideRequestQueueModel.REQUEST_TYPE_UPDATE_MYDATA)) {
				

				JSONObject rootObject = messenger.getValueForTag(message, ClientSideRequestQueueModel.REQUEST_TYPE_UPDATE_MYDATA).isObject();
				
				JSONString id = rootObject.get(ClientSideRequestQueueModel.KEY_STRING_TRIGGER_ID).isString();
				uStCont.completeRequest(id);
				debug.assertTrue(uStCont.getAlreadyFinishedRequestNumber() == uStCont.getAllRequestNumber(), "終わっていないリクエストが存在する");
				
				//get User owning item key array
				JSONArray currentItemArray = rootObject.get("userData").isObject().get("itemKeys").isArray();
				
				uStCont.setM_userItemArray(currentItemArray);
				debug.trace("ユーザーデータとか受け取った_"+uStCont.getM_userOwningItemArray());
				
				
				if (m_enterWithOwn) {
					setKickStatus(STATUS_KICK_OWN_INIT);
//					procedure("initializeOwning");
					messenger.call(KICK_CONTROLLER, "initializeOwning");
				} else {
					JSONObject newRootObject = new JSONObject();
					newRootObject.put("userKey", uStCont.getUserKey());
					newRootObject.put("itemKey", uStCont.getM_nowFocusingItemKey());
					
					
					setKickStatus(STATUS_KICK_OWNERSVIEW_INIT);
//					procedure("LoadingOwnersOfItem+"+newRootObject);
					messenger.call(KICK_CONTROLLER, "LoadingOwnersOfItem", messenger.tagValue("newRootObject", newRootObject));
				}
			}
		}
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 所持アイテム
	 */
	private void execSTATUS_KICK_OWN_INIT(String message) {
		String exec = messenger.getCommand(message);
		
		if(exec.equals("initializeOwning")) {
			setKickStatus(STATUS_KICK_OWN_PROC);
			
			//この時点での所持アイテムのキーから、アイテムの情報を召還。　全てを描画対象にする
			setUpUserItemRequest(uStCont.getUserKey(), uStCont.getM_userOwningItemArray());
			procQueExecute(uStCont.getUserKey());
			
			debug.trace("描画対象、データ待ち中_"+uStCont.getM_userOwningItemArray());
			
			HTML loaingMessage = new HTML(uStCont.getUserName()+"@Initializing...");
			reg.fireEvent(new ScreenEvent(1, loaingMessage));
		}
		
		
		
		
	}
	private void execSTATUS_KICK_OWN_PROC(String message) {
		String exec = messenger.getCommand(message);
		{//アイテムが送られてきた
			if (exec.equals("ItemReceived")) {
				
				
				try {
					JSONObject root = messenger.getValueForTag(message, "root").isObject();
					JSONObject item = root.get("item").isObject();
					JSONString id = root.get(ClientSideRequestQueueModel.KEY_STRING_TRIGGER_ID).isString();
	
					debug.trace("execSTATUS_KICK_OWN_PROC_PUSH_ITEM_item_"+item);
					
					if (item != null) {
						//アイテムのidキーから、設定されていたリクエストを完了に指定する
						
						uStCont.putItemData(item);//pushしてきてもらったデータ、現在のアカウントのキャッシュに保存する。
						
						uStCont.completeRequest(id);
						debug.trace("一応、全件処理完了");
						showItems();//遅延実行したい
						
					}
				} catch (Exception e) {
					debug.trace("PUSH_ITEM_error_"+e);
				}
				
				HTML loaingMessage = new HTML(uStCont.getUserName()+"@Initializing..."+"_"+uStCont.getAlreadyFinishedRequestNumber()+"/"+uStCont.getAllRequestNumber());
				reg.fireEvent(new ScreenEvent(1, loaingMessage));
			}
			
			
			
			if (false) {//遅延実行で、このブロックを一度抜けた後で表示したい内容。
				HTML loaingMessage = new HTML("All Item Loaded");
				reg.fireEvent(new ScreenEvent(1, loaingMessage));
				//全アイテムがそろった = リクエストしていたアイテム全てが届いた、、、
			}
		}
		

		
		{//タグ関連
			if (exec.equals("TagTapped")) {
				

				JSONObject root = messenger.getValueForTag(message, "buttonKey").isObject();
				
				
				uStCont.addRequestToRequestQueue(root.toString(), ClientSideRequestQueueModel.REQUEST_TYPE_UPDATE_TAG);
				procQueExecute(uStCont.getUserKey());//サーバにリクエストを送りこむ
			}
			
			
			if (exec.equals("TagUpload")) {
				//アイテムのキーと加算したタグの内容と加算した主のキーの合算をサーバに渡す
				//そしたらサーバ側で、タグ情報が構築され、そのタグのキーがアイテムに追加される
				
				
				JSONObject itemKeyWithNewTagWithMyKey = messenger.getValueForTag(message, "itemKeyWithNewTag").isObject();
				
				itemKeyWithNewTagWithMyKey.put("userKey", uStCont.getUserKey());
				
				uStCont.addRequestToRequestQueue(itemKeyWithNewTagWithMyKey.toString(), ClientSideRequestQueueModel.REQUEST_TYPE_ADDNEWTAG);
				procQueExecute(uStCont.getUserKey());//サーバにリクエストを送りこむ
			}
			
			if (exec.equals(ClientSideRequestQueueModel.EVENT_TAG_CREATED)) {
				
				
				JSONObject rootObject = messenger.getValueForTag(message, ClientSideRequestQueueModel.EVENT_TAG_CREATED).isObject();
				
				uStCont.setM_tagMapModel(
						rootObject.get("itemName").isString().toString(), 
						rootObject.get("tagObject").isObject()
						);
				
				JSONObject taggedItem = rootObject.get("taggedItem").isObject();
				
				//アイテムキャッシュのデータを書き換える、これでタグがはいった。
				uStCont.updateItemData(taggedItem);
				
				//更新が終わったので、データを描画する
				showItems();
			}
			
			if (exec.equals(ClientSideRequestQueueModel.EVENT_USER_TAG_RECEIVED)) {
				/*
				 * このアイテムについてのユーザーのタグのアレイを受け取ったので、表示に反映する
				 */
				
				/*
				 * itemName
				 * tagObject
				 */
				JSONObject rootObject = messenger.getValueForTag(message, ClientSideRequestQueueModel.EVENT_USER_TAG_RECEIVED).isObject();
				uStCont.setM_tagMapModel(
						rootObject.get("itemName").isString().toString(), 
						rootObject.get("tagObject").isObject()
						);
				showItems();
			}
		}
		
		
		{//遷移
			/*
			 * どのアイテムがタッチされたかで、タッチされたアイテムのキーから
			 * そのアイテムに寄せられたコメント一覧へとジャンプする
			 */
			if (exec.equals("ItemTapped")) {
				
				setKickStatus(STATUS_KICK_OWNERSVIEW_INIT);
				//ロードするアイテムのキーを受け取り、コメントの情報を表示する
				

				HTML loaingMessage = new HTML(uStCont.getUserName()+"@Loading Comments...");
				reg.fireEvent(new ScreenEvent(1, loaingMessage));
				
				JSONObject tappedItemKeyObject = messenger.getValueForTag(message, "itemKey").isObject();
				
				JSONObject root = new JSONObject();
				root.put("userKey", tappedItemKeyObject.get("userKey").isObject());
				root.put("itemKey", tappedItemKeyObject.get("itemKey").isObject());
				
//				procedure("LoadingOwnersOfItem+"+root);
				messenger.call(KICK_CONTROLLER, "LoadingOwnersOfItem", messenger.tagValue("root", root));
				hideUserTag();
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	/*
	 * ユーザー情報からのローディングを行う
	 * 初回とは異なる経路でのログイン(他人の情報だけを取得する) 
	 */
	/**
	 * 
	 * @param exec
	 */
	private void execSTATUS_KICK_LOADING_USER_INIT(String message) {
		String exec = messenger.getCommand(message);
		
		if (exec.equals("InitializeFromUserTapped")) {
			debug.assertTrue(false, "未調整_InitializeFromUserTapped");
			debug.trace("別人かどうか判断しない、ユーザー情報の読み出し開始_"+exec);
			//このユーザーの情報を取得、持ち物一覧と、持ち物に着いているタグ一覧を取得する
			uStCont.addRequestToRequestQueue(exec, ClientSideRequestQueueModel.REQUEST_TYPE_GET_SPECIFIC_USER_INFORMATION);
		}
		
		/*
		 * GET_SPECIFIC_USER_INFORMATIONの返答
		 * 
		 */
//		if () {
//			
//		}
		
		
		
	}
	
	private void execSTATUS_KICK_LOADING_USER_PROC(String message) {
		// TODO Auto-generated method stub
		
	}


	
	
	
	
	
	
	
	
	
	
	
	

	/**
	 * フォーカスしているアイテムキーから、コメントを集める
	 * @param exec
	 */
	private void executeSTATUS_KICK_OWNERS_INIT(String exec) {
//		/*
//		 * アイテムキーを指定してのオーナーズの初期化
//		 */
//		if (exec.startsWith("LoadingOwnersOfItem+")) {
//			
//			setKickStatus(STATUS_KICK_OWNERS_PROC);
//			
//			debug.assertTrue(uStCont.getM_nowFocusingItemKey() != null, "getM_nowFocusingItemKeyが空");
//			String itemKeyWithUserKey = exec.substring("LoadingOwnersOfItem+".length(), exec.length());
//			
//			//全コメントを召還する
//			JSONObject root = null;
//			try {
//				//{"itemKey":{"kind":"item", "id":0, "name":"\"http://images.paraorkut.com/img/funnypics/images/f/fail_cat-12835.jpg\""}, "userKey":{"kind":"user", "id":0, "name":"aaaa@aaaa"}}
//				root = JSONParser.parseStrict(itemKeyWithUserKey).isObject();
//				JSONObject userKey = root.get("userKey").isObject();
//				JSONObject itemKey = root.get("itemKey").isObject();
//				
//				debug.assertTrue(userKey.get("name").equals(uStCont.getUserKey().get("name")), "ユーザーの名称が一致しない");
//				uStCont.setM_nowFocusingItemKey(itemKey);//フォーカスをセットする
//			} catch (Exception e) {
//				debug.trace("LoadingOwnersOfI_error_"+e);
//			}
//			uStCont.addRequestToRequestQueue(root.toString(), ClientSideRequestQueueModel.REQUEST_TYPE_GETALLCOMMENT);
//			
//			
//			//アイテムデータ自体も召還する
//			JSONObject itemRequestKey = new JSONObject();
//			itemRequestKey.put("userKey", uStCont.getUserKey());
//			itemRequestKey.put("itemKey", uStCont.getM_nowFocusingItemKey());//ココは、アイテムのキー
//			uStCont.addRequestToRequestQueue(itemRequestKey.toString(), ClientSideRequestQueueModel.REQUEST_TYPE_GET_ITEM_FROM_KEY);
//			
//			
//			debug.assertTrue(uStCont.getM_nowFocusingItemKey() != null, "uStCont.getM_nowFocusingItemKeyの初期化が終わってない");
//			itemCommentCont = new ItemCommentController(this, uStCont.getUserKey(), uStCont.getM_nowFocusingItemKey());//コメントコントローラの初期化
//			
//			
//			//For All window, タグ情報を取得する
//			JSONObject request = new JSONObject();
//			request.put("userInfo", uStCont.getUserKey());
//			request.put("itemInfo", uStCont.getM_nowFocusingItemKey());
//			uStCont.addRequestToRequestQueue(request.toString(), ClientSideRequestQueueModel.REQUEST_TYPE_GET_USER_INDIVIDUAL_TAG);
//			
//			
//			procQueExecute(uStCont.getUserKey());//do
//			
//			HTML ownersOfItem = new HTML(uStCont.getUserName()+"@There are owners of this item Loading..."+uStCont.getAlreadyFinishedRequestNumber()+"/"+uStCont.getAllRequestNumber());
//			reg.fireEvent(new ScreenEvent(1, ownersOfItem));
//		}
//		
	}
	private void executeSTATUS_KICK_OWNERS_PROC(String exec) {
//		{//タグ関連
////			if (exec.startsWith("TagUpload+")) {
////				//アイテムのキーと加算したタグの内容と加算した主のキーの合算をサーバに渡す
////				//そしたらサーバ側で、タグ情報が構築され、そのタグのキーがアイテムに追加される
////				
////				String key = exec.substring("TagUpload+".length(),  exec.length());
////				
////				JSONObject itemKeyWithNewTagWithMyKey = JSONParser.parseStrict(key).isObject();
////				itemKeyWithNewTagWithMyKey.put("userKey", uStCont.getUserKey());
////				
////				uStCont.addRequestToRequestQueue(itemKeyWithNewTagWithMyKey.toString(), ClientSideRequestQueueModel.REQUEST_TYPE_ADDNEWTAG);
////				procQueExecute(uStCont.getUserKey());//サーバにリクエストを送りこむ
////			}
////			
////			if (exec.startsWith("tagUpdated+")) {
////				String execution = exec.substring("tagUpdated+".length(),  exec.length());
////				
////				debug.trace("タグがアップデートされました_"+execution);
////				String key = exec.substring("TagUpload+".length(),  exec.length());
////			}
//			if (exec.startsWith(ClientSideRequestQueueModel.REQUEST_TYPE_GET_USER_INDIVIDUAL_TAG)) {
//				String rootString = exec.substring(ClientSideRequestQueueModel.REQUEST_TYPE_GET_USER_INDIVIDUAL_TAG.length(), exec.length());
//				
//				debug.trace("tagRceive_start"+rootString);
//				//誰のタグなのか。内容はなんなのか、がはいってる筈。
//				
//				JSONObject rootObject = JSONParser.parseStrict(rootString).isObject();
//				
//				JSONObject tagOwnerObject = rootObject.get("ownerObject").isObject();
//				JSONObject tagObject = rootObject.get("tagObject").isObject();
//				itemCommentCont.addTagForUser(tagOwnerObject, tagObject);
//				
//			}
//		}
//		
//		//アイテムの受け取り(アイテム情報を出す為に必要)
//		{
//			if (exec.startsWith("ItemReceived+")) {
//				String rootString = exec.substring("ItemReceived+".length(), exec.length());
//				debug.trace("rootString_"+rootString);
//				
//				try {
//					JSONObject root = JSONParser.parseStrict(rootString).isObject();
//					JSONObject item = root.get("item").isObject();
//					JSONString id = root.get(ClientSideRequestQueueModel.KEY_STRING_TRIGGER_ID).isString();
//	
//					debug.trace("execSTATUS_KICK_OWN_PROC_PUSH_ITEM2_item_"+item);
//					
//					if (item != null) {
//						//アイテムのidキーから、設定されていたリクエストを完了に指定する
//						
//						uStCont.putItemData(item);//pushしてきてもらったデータ、現在のアカウントのキャッシュに保存する。
//						uStCont.completeRequest(id);
//						
//						showOneItem(uStCont.getM_nowFocusingItemKey());
//					}
//				} catch (Exception e) {
//					debug.trace("PUSH_ITEM2_error_"+e);
//				}
//				
//				HTML ownersOfItem = new HTML(uStCont.getUserName()+"@There are owners of this item Loading..."+uStCont.getAlreadyFinishedRequestNumber()+"/"+uStCont.getAllRequestNumber());
//				reg.fireEvent(new ScreenEvent(1, ownersOfItem));
//			}
//		}
//		
//		
//		if (exec.startsWith("GETCOMMENT_ACCEPTED")) {
//			String rootString = exec.substring("GETCOMMENT_ACCEPTED".length(), exec.length());
//			
//			JSONObject root = JSONParser.parseStrict(rootString).isObject();
//			JSONString id = root.get(ClientSideRequestQueueModel.KEY_STRING_TRIGGER_ID).isString();
//
//			uStCont.completeRequest(id);
//			
//			HTML ownersOfItem = new HTML(uStCont.getUserName()+"@There are owners of this item Loading..."+uStCont.getAlreadyFinishedRequestNumber()+"/"+uStCont.getAllRequestNumber());
//			reg.fireEvent(new ScreenEvent(1, ownersOfItem));
//		}
//		
//		
//		if (exec.startsWith("InputYourText+")) {
//			String textInput = exec.substring("InputYourText+".length(), exec.length());
//			
//			debug.trace("コメント入力が有りました_"+textInput);
//			JSONObject commentWithItemKeyWithUserKey = JSONParser.parseLenient(textInput).isObject();
//			debug.trace("commentWithItemKeyWithUserKey_"+commentWithItemKeyWithUserKey);
//			
////			commentWithItemKeyWithUserKey.put("userKey", uStCont.getUserKey());//これが原因でペアが消滅するとか、前も見た事が、、
////			commentWithItemKeyWithUserKey.put("itemKey", uStCont.getM_nowFocusingItemKey());//足したが、元来いらないくさい。
//			
//			uStCont.addRequestToRequestQueue(commentWithItemKeyWithUserKey.toString(), ClientSideRequestQueueModel.REQUEST_TYPE_ADDCOMMENT);
//			
//			procQueExecute(uStCont.getUserKey());//サーバにリクエストを送りこむ
//		}
//		
//		
//		if (exec.startsWith("NoComment+")) {
//			debug.trace("自分のコメントが無いので、ウインドウを出す。");
//			itemCommentCont.addMyCommentPopup();
//		}
//		
//		
//		if (exec.startsWith("MyCommentGet+")) {//自分からのコメント
//			itemCommentCont.removeMyYetPanel(uStCont.getUserName());
//			
//			String commentData = exec.substring("MyCommentGet+".length(), exec.length());
//			
//			JSONObject commentBlock = JSONParser.parseStrict(commentData).isObject();
//			debug.trace("imageNumber_commentBlock_"+commentBlock);
//			
//			itemCommentCont.addCommentFromMyself(commentBlock);
//		}
//		
//		if (exec.startsWith("SomeCommentGet+")) {//他人からのコメント
//			debug.trace("だれかからのコメントが来た_自分は_"+uStCont.getUserName());
//			String commentData = exec.substring("SomeCommentGet+".length(), exec.length());
//			
//			JSONObject commentBlock = JSONParser.parseStrict(commentData).isObject();
//			itemCommentCont.addCommentFromSomeone(commentBlock);
//			
//		}
//		
//		if (exec.startsWith("CommentSaved+")) {
//			String commentSavedRequest = exec.substring("CommentSaved+".length(), exec.length());
//			debug.trace("コメントのセーブを受け取ったので、リロードする_"+commentSavedRequest);
//			//アイテムのキーを出力して、読み出す
//			JSONObject value = JSONParser.parseStrict(commentSavedRequest).isObject();
//			JSONObject itemKey = new JSONObject();
//			itemKey.put("itemKey", value);
//			itemKey.put("userKey", uStCont.getUserKey());
//			
//			uStCont.addRequestToRequestQueue(itemKey.toString(), ClientSideRequestQueueModel.REQUEST_TYPE_GET_LATESTCOMMENT);
//			procQueExecute(uStCont.getUserKey());//サーバにリクエストを送りこむ
//		}
//		
//		/*
//		 * アイテムがタッチされたら、その所有者一覧へ
//		 */
//		if (exec.startsWith("ItemTapped+")) {
//			setKickStatus(STATUS_KICK_OWN_INIT);
//			
//			itemCommentCont.eraseAllComment();
////			debug.assertTrue(false, "調整中");
//			debug.trace("exec_"+exec);
//			showUserTag();
//		}
	}

	
	
	
	
	
	
	
	
	
	
	
	


	private void executeSTATUS_KICK_OWNERSVIEW_INIT(String message) {
		String exec = messenger.getCommand(message);
		
		if (exec.equals("LoadingOwnersOfItem")) {
			//このキーで、GET_ALL_USER_COMMENT_TO_ITEMリクエストを実行します。このリクエストの完了を持って、ビューの初期化を完了させます
			JSONObject rootRequest = messenger.getValueForTag(message, "userKeyWithItemKey").isObject();
			debug.trace("rootRequest_"+rootRequest);
			
			uStCont.addRequestToRequestQueue(rootRequest.toString(),  ClientSideRequestQueueModel.GET_ALL_USER_WHO_HAVE_THIS_ITEM);
			procQueExecute(uStCont.getUserKey());
		}
		if (exec.equals(ClientSideRequestQueueModel.GET_ALL_USER_WHO_HAVE_THIS_ITEM)) {
			JSONObject rootOject = messenger.getValueForTag(message, "root").isObject();
			debug.trace("なんか、入ってないんだってその後どう？");
			
			debug.trace("rootOject_"+rootOject);
			JSONArray ownerList = rootOject.get("ownerList").isArray();
			debug.trace("ownerList_"+ownerList);

			
			
			//リクエストの完了
			uStCont.completeRequest(rootOject.get(ClientSideRequestQueueModel.KEY_STRING_TRIGGER_ID).isString());
			
			//コントローラーの初期化
			oVCont = new OwnersViewController();
			
			//ユーザーのリストが手に入ったので、それをもって、画面の初期化を行う
			if (0 < ownerList.size()) {
				
				for (int i = 0; i < ownerList.size(); i++) {
					String userName = ownerList.get(i).isObject().get("key").isObject().get("name").isString().toString();
					int userImageNumber = (int)ownerList.get(i).isObject().get("imageNumber").isNumber().doubleValue();
					oVCont.initializeUserView(userName, userImageNumber);
					
					oVCont.showView(userName);
				}
			}
			
			
			//タグを召還する
			JSONObject request = new JSONObject();
			request.put("userInfo", uStCont.getUserKey());
			request.put("itemInfo", uStCont.getM_nowFocusingItemKey());
			uStCont.addRequestToRequestQueue(request.toString(), ClientSideRequestQueueModel.REQUEST_TYPE_GET_USER_INDIVIDUAL_TAG);
			
			
			//アイテムデータ自体を召還する
			JSONObject itemRequestKey = new JSONObject();
			itemRequestKey.put("userKey", uStCont.getUserKey());
			itemRequestKey.put("itemKey", uStCont.getM_nowFocusingItemKey());//ココは、アイテムのキー
			uStCont.addRequestToRequestQueue(itemRequestKey.toString(), ClientSideRequestQueueModel.REQUEST_TYPE_GET_ITEM_FROM_KEY);
			procQueExecute(uStCont.getUserKey());
			
			setKickStatus(STATUS_KICK_OWNERSVIEW_PROC);//実行フェーズへ
			
		}
		
		
	}
	private void executeSTATUS_KICK_OWNERSVIEW_PROC(String message) {
		String exec = messenger.getCommand(message);
		
		//アイテムの受け取り(アイテム情報を出す為に必要)
		{
			if (exec.equals("ItemReceived")) {
				

				try {
					JSONObject root = messenger.getValueForTag(message, "root").isObject();
					JSONObject item = root.get("item").isObject();
					JSONString id = root.get(ClientSideRequestQueueModel.KEY_STRING_TRIGGER_ID).isString();
	
					debug.trace("execSTATUS_KICK_OWN_PROC_PUSH_ITEM2_item_"+item);
					
					if (item != null) {
						//アイテムのidキーから、設定されていたリクエストを完了に指定する
						
						uStCont.putItemData(item);//pushしてきてもらったデータ、現在のアカウントのキャッシュに保存する。
						uStCont.completeRequest(id);
						
						showOneItem(uStCont.getM_nowFocusingItemKey());
					}
				} catch (Exception e) {
					debug.trace("PUSH_ITEM2_error_"+e);
				}
				
//				HTML ownersOfItem = new HTML(uStCont.getUserName()+"@There are owners of this item Loading..."+uStCont.getAlreadyFinishedRequestNumber()+"/"+uStCont.getAllRequestNumber());
//				reg.fireEvent(new ScreenEvent(1, ownersOfItem));
			}
		}
		
		if (exec.equals("OwnerImageTapped")) {
			
			debug.trace("execイメージが押された_"+exec);
			/*
			 * とあるユーザーの画像が押された
			 * -ユーザーのホーム画面に移動する
			 * →情報取得して遷移
			 */
			JSONString userName = messenger.getValueForTag(message, "userName").isString();
			
			JSONObject root = new JSONObject();
			root.put("userNameWithKey", userName);
			
			setKickStatus(STATUS_KICK_LOADING_USER_INIT);//ユーザー情報の取得と、そのオウンリストの表示に走る
//			procedure("InitializeFromUserTapped+"+root);
			messenger.call(KICK_CONTROLLER, "InitializeFromUserTapped", messenger.tagValue("root", root));
		}
		if (exec.equals("TagButtonTapped")) {
			String userName = messenger.getValueForTag(message, "userName").isString().toString();
			
			debug.trace("userNameさんのタグボタンが押された"+userName);
			/*
			 * 自分の画面で、自分/誰かのタグを押した
			 * -タグの種類の縛りが必要
			 * -同じタグを押した人の一覧を取得、表示する
			 * っていう画面に遷移する。
			 * 
			 * つまりユーザーの画面に行くんだね。
			 */
		}
		
		if (exec.equals("notyet")) {
			/*
			 * -アイテムボタンを押すと、フォーカスを切り替え、そのページへとジャンプする
			 * 
			 */
			
		}
		if (exec.equals("CommentWritten")) {
			JSONObject root = messenger.getValueForTag(message, "commentWithUserKey").isObject();
			debug.trace("コメント入力が来た_"+root);
		}
		
		if (exec.equals(ClientSideRequestQueueModel.REQUEST_TYPE_GET_USER_INDIVIDUAL_TAG)) {
			//誰のタグなのか。内容はなんなのか、がはいってる筈。
			
			JSONObject rootObject = messenger.getValueForTag(message, ClientSideRequestQueueModel.REQUEST_TYPE_GET_USER_INDIVIDUAL_TAG).isObject();
			
			JSONObject tagOwnerObject = rootObject.get("ownerObject").isObject();
			JSONObject tagObject = rootObject.get("tagObject").isObject();
			debug.trace("tagOwnerObject_"+tagOwnerObject+"/	tagObject_"+tagObject);
			oVCont.addTag(
					tagOwnerObject.get("key").isObject().get("name").isString().toString(), 
					tagObject.get("m_tagName").isString().toString()
					);
		}
		
		
		/*
		 * アイテムがタッチされたら、その所有者一覧へ
		 */
		if (exec.equals("ItemTapped")) {
			debug.assertTrue(false, "アイテムがタップされたんだけど、遷移先はこのアイテムについて所有してる人たちじゃないの?");
			setKickStatus(STATUS_KICK_OWN_INIT);
			
			debug.trace("exec_"+exec);
//			showUserTag();
		}
	}
		
}


