package com.kissaki.client.canvasController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

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
	
	public CanvasController (KickController kickCont) {
		debug = new Debug(this);
		kCont = kickCont;
		itemDialogBoxList = new ArrayList<ItemDialogBox>();
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
	 * @param currentItemList
	 */
	public void updateItemcInfo(List<ClientSideCurrentItemDataModel> currentItemList) {
//		存在するアイテムの数だけ、アイテム情報を描画する
		int i = 0;
		for (Iterator<ClientSideCurrentItemDataModel> itemItel = currentItemList.iterator(); itemItel.hasNext(); i++) {
			ClientSideCurrentItemDataModel currentModel = itemItel.next();
			
			updateCanvas(10,10);
			
			debug.trace("アップデートがかかっている");
			//p.image("http://a0.twimg.com/images/dev/bookmark.png", ""+100, ""+100, ""+100, ""+100);
			
			//アイテムの情報を、ダイアログにして出す。
			//タグはボタンとしてくっつける。
			//
			ItemDialogBox item = new ItemDialogBox(kCont, currentModel, 100,100);
			itemDialogBoxList.add(item);
		}
	}

}
