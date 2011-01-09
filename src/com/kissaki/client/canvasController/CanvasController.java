package com.kissaki.client.canvasController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.google.gson.JsonArray;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;
import com.kissaki.client.KickController;
import com.kissaki.client.ProcessingImplements;
import com.kissaki.client.procedure.ItemDialogBox;
import com.kissaki.client.subFrame.debug.Debug;
import com.kissaki.client.userStatusController.userDataModel.ClientSideCurrentItemDataModel;

/**
 * Canvas描画、ダイアログ描画をコントロールするクラス
 * @author ToruInoue
 *
 */
public class CanvasController {
	KickController kCont;
	Debug debug;
	
	List<ItemDialogBox> itemDialogBoxList;
	JSONObject userKey;
	/**
	 * コンストラクタ
	 * @param kickCont
	 */
	public CanvasController (JSONObject userKey) {
		debug = new Debug(this);
		
		itemDialogBoxList = new ArrayList<ItemDialogBox>();
		this.userKey = userKey;
		
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


	ProcessingImplements p;
	GWTCanvas m_canvas;
	
	
	/**
	 * キャンバスをセットする。
	 */
	public void initCanvas () {
		m_canvas = new GWTCanvas(100,0,600,280);
		
		p = new ProcessingImplements(m_canvas.getElement(), "");
		p.size("600","280","");
	}
	
	/**
	 * ゲッター
	 * @return
	 */
	public GWTCanvas canvas () {
		return m_canvas;
	}
	
	private void updateCanvas (int x, int y) {
		Random rand = new Random();
		p.color("100"+rand.nextInt(255), "20"+rand.nextInt(255), "5"+rand.nextInt(255), "1");
		p.ellipse(""+(x*20), ""+(y*20+40), ""+10, ""+10);
	}
	
	
	/**
	 * アイテム情報の描画を行う
	 * @param item
	 */
	public void updateItemInfo(JSONArray currentItemChacheList, JSONArray currentTagList) {
		
		
		int i = 0;
		//この描画の間に、currentItemChacheListがリセットされたら、しょうがない。次回の描画を待つ。定期的な描画だったら、あんまり困らないのだけれど。そんなバカじからは無い。

		for (i = 0; i < currentItemChacheList.size(); i++) {
			JSONObject currentModel = currentItemChacheList.get(i).isObject();
			ClientSideCurrentItemDataModel currentModelReplica = new ClientSideCurrentItemDataModel(currentModel);
			//今回は書く物が一つだけだからOKだが、さて。実際は、ここでずいぶん書き換えが出来る筈。
			
			updateCanvas(10,10);
			
			//p.image("http://a0.twimg.com/images/dev/bookmark.png", ""+100, ""+100, ""+100, ""+100);
			
			
			if (!itemDialogBoxList.isEmpty()) {
				//このアイテムに関連する物を全て吹っ飛ばす
				int size = itemDialogBoxList.size();
				for (int j = 0; j < size; j++) {
					ItemDialogBox currentItemDialogBox = itemDialogBoxList.get(j);
					currentItemDialogBox.selfKill();
					itemDialogBoxList.remove(j);
				}
			}
			
			/*
			 * ここで、タグのarrayを与える
			 */
			JSONArray currentMyTagList = null;
			if (currentTagList != null) { 
				if (i < currentTagList.size()) {//範囲内であれば
					currentMyTagList = currentTagList.get(i).isArray();//２つになった時、そのどちらかにタグが無かったらnullになる。
				}
			}
			
			ItemDialogBox itemDialog = new ItemDialogBox(kCont, userKey, currentModelReplica, currentMyTagList, 90+90*(i%4),10+(90*(i/4)));
			itemDialogBoxList.add(itemDialog);
		}
	}


	public void showUserTag() {
		for (Iterator<ItemDialogBox> currentItemDialogBoxItel = itemDialogBoxList.iterator(); currentItemDialogBoxItel.hasNext();) {
			ItemDialogBox currentItemDialogBox = currentItemDialogBoxItel.next();
			currentItemDialogBox.changeViewMode(true);
		}
	}
	public void hideUserTag() {
		for (Iterator<ItemDialogBox> currentItemDialogBoxItel = itemDialogBoxList.iterator(); currentItemDialogBoxItel.hasNext();) {
			ItemDialogBox currentItemDialogBox = currentItemDialogBoxItel.next();
			currentItemDialogBox.changeViewMode(false);
		}
	}
	

}
