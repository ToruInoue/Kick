package com.kissaki.client;


import java.util.Arrays;//array
import java.util.List;//list


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.UmbrellaException;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.kissaki.client.channel.Channel;
import com.kissaki.client.channel.ChannelFactory;
import com.kissaki.client.channel.SocketListener;
import com.kissaki.client.itemDataModel.ItemDataModel;
import com.kissaki.client.login.MyDialogBox;
import com.kissaki.client.subFrame.debug.Debug;
import com.kissaki.client.subFrame.screen.ScreenEvent;
import com.kissaki.client.subFrame.screen.ScreenEventRegister;

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
						setKickStatus(STATUS_KICK_LOGIN_SUCCEEDED);
						procedure("Channel_Open:"+result);
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
				debug.trace("status_"+uStCont.getUserStatus());
				
				debug.trace("name_"+uStCont.getUserName());
				debug.trace("password_"+uStCont.getUserPass());
				
				uStCont.setUserStatus(UserStatusController.STATUS_USER_LOGIN);
				
				setKickStatus(STATUS_KICK_EXEC_INIT);
				
				String channelPass = exec.subSequence("Channel_Open:".length(), exec.length()).toString();
				
				//チャンネルがいつ開くかわからないので、Channelの接続をここから行う
				setChannelID(channelPass);
				
				//画面の片付けとかも行う。
			}
			break;
			
			
		case STATUS_KICK_EXEC_INIT:
			
			setKickStatus(STATUS_KICK_EXEC_PROC);
			
			//アイテムデータ、それに紐づくデータの取得を行う
			String nameWithPass = uStCont.getUserName()+":"+uStCont.getUserPass();
			
			greetingService.greetServer("getItemData+"+nameWithPass+"",
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
			
			//ローディング画面表示する
			
			
			
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
		Channel channel = ChannelFactory.createChannel(result);
		
		/**
		 * 接続ハンドラ
		 */
		channel.open(new SocketListener() {

			public void onOpen() {
				debug.trace(uStCont.getUserName()+"_channel 開きました");
				procedure("");
			}


			public void onMessage(String encodedData) {
				debug.trace(uStCont.getUserName()+"_メッセージを受け取りました_" + encodedData);//改行コードが入ってる。
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
		inputUserPass(name);
		
		procedure("login実行");
	}
	
	
	/**
	 * pushで、サーバからアイテム情報が届いたときに処理する
	 */
	private void getItem (ItemDataModel itemDataModel) {
		
	}
	
}


