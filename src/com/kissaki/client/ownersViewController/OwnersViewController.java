package com.kissaki.client.ownersViewController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kissaki.client.KickController;
import com.kissaki.client.KickStatusInterface;
import com.kissaki.client.MessengerGWTCore.MessengerGWTInterface;
import com.kissaki.client.subFrame.debug.Debug;

/**
 * ユーザーごとに単一のモデル、ビューをもち、それをコントロールするビューコントローラー
 * 
 * このユーザーのコレを変更したい→モデルが変更される→ビューが作りなおされる　という感じか。
 * 位置は、作られたときにモデルが持てばいいよ。
 * @author ToruInoue
 *
 */
public class OwnersViewController implements KickStatusInterface, MessengerGWTInterface {
	Debug debug;
	
	List<OwnerModel> ownerModelList;
	List<OwnerView> ownerViewList;
	
	Map<OwnerModel, OwnerView> connectionMap;
	
	public OwnersViewController () {
		debug = new Debug(this);
		
		ownerModelList = new ArrayList<OwnerModel>();
		ownerViewList = new ArrayList<OwnerView>();
		connectionMap = new HashMap<OwnerModel, OwnerView>();
		
	}
	
	public OwnerModel inputNewOwner (String userName) {
		OwnerModel currentOwnerModel = new OwnerModel(userName);
		
		int size = ownerModelList.size();
		
		for (int i = 0; i < size; i++) {
			if (ownerModelList.get(i).userName.equals(currentOwnerModel.userName)) {
				//有るのでここで返す
				return ownerModelList.get(i);
			}
		}
		
		//新規に作る
		OwnerView currentOwnerView = new OwnerView(size);
		connectionMap.put(currentOwnerModel, currentOwnerView);
		ownerModelList.add(currentOwnerModel);
		
//		if (userName.equals(kCont.getUStCont().getUserName())) {//自分だったら、自分へのコメントを表示(自動)
//			
//		} else {//他人だったら、他人へのコメント(自動)　、書き込み枠を書く
//			
//		}
		
		return currentOwnerModel;
	}
	
	public void initializeUserView (String userName, int imageNumber) {
		OwnerModel currentOwnerModel = inputNewOwner(userName);
		currentOwnerModel.imageNumber = imageNumber;
		currentOwnerModel.setUserImage(imageNumber);
//		ビューに反映させる
		refreshView(currentOwnerModel);
	}
	
	

	public void addComment (String userName, String comment, String commentMaster) {
		OwnerModel currentOwnerModel = inputNewOwner(userName);
		currentOwnerModel.addComment(comment, commentMaster);
//		ビューに反映させる
		refreshView(currentOwnerModel);
	}
	
	/**
	 * このユーザーのタグを足す
	 * @param userName
	 * @param tagName
	 */
	public void addTag (String userName, String tagName) {
		OwnerModel currentOwnerModel = inputNewOwner(userName);
		currentOwnerModel.addTag(tagName);
//		ビューに反映させる
		refreshView(currentOwnerModel);
	}
	
	
	private void refreshView(OwnerModel currentOwnerModel) {
		connectionMap.get(currentOwnerModel).draw(currentOwnerModel);
	}

	/**
	 * ビューを表示する
	 * @param userName
	 */
	public void showView(String userName) {
		OwnerModel currentOwnerModel = inputNewOwner(userName);
		connectionMap.get(currentOwnerModel).show();
	}
	
	public void hideView(String userName){
		OwnerModel currentOwnerModel = inputNewOwner(userName);
		connectionMap.get(currentOwnerModel).hide();
	}

	@Override
	public void receiveCenter(String message) {
		// TODO Auto-generated method stub
		
	}
}
