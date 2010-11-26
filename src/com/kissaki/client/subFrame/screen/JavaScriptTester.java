package com.kissaki.client.subFrame.screen;

import com.google.gwt.user.client.Element;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;
import com.kissaki.client.subFrame.debug.Debug;

/**
 * Widget canvasにいろいろセットするぜ！
 * ここからProcessionのターン。
 * @author sassembla
 *
 */
public class JavaScriptTester {

	Debug debug = null;

	/**
	 * コンストラクタ
	 */
	public JavaScriptTester () {
		debug = new Debug(this);
	}




	public void trace (String s) {
		debug.trace("trace_"+s);
	}


	/**
	 * 
	 * @param canvas
	 */
	public void setCanvas(GWTCanvas canvas) {
		testJavaScript(canvas.getElement());
	}

	/**
	 * こいつを書き換える。で、合わせる。そのまま行こう。
	 * @param canvas
	 */
	public native void testJavaScript (Element canvas) /*-{

		var numParticles = 20;

		var i;
		var width = $wnd.innerWidth;
		var height = $wnd.innerHeight;
		var impulsX = 0;//インパルス、パワー値
		var impulsY = 0;
		var impulsToX = 0;
		var impulsToY = 0;

		var startedAt;
		var now;
		

		var machineIndex = 0;//タイマーのインデックス

		
		components = [];//描画対象を放り込む箱

		var pixels = [];
		for(i = 0; i<numParticles; i++ ) {//構造体としてのpixelの初期化
			pixels[i] = {
				x          : Math.random()*width,
				y          : height/2,
				toX        : 0,
				toY        : height/2,
				color      : Math.random()*200 + 55,
				angle      : Math.random()*Math.PI*2,
				size       : 0,
				toSize     : Math.random()*4+1,
				r		   : 0,
				g		   : 0,
				b          : 0,
				toR 	   : Math.random()*255,
				toG 	   : Math.random()*255,
				toB 	   : Math.random()*255,
			};

			pixels[i].toX = pixels[i].x;//目的地と現在地の一致
			pixels[i].speedX = 0;
			pixels[i].speedY = 0; 
		}

		var transitions = [//エフェクト対象、変形する事象をまとめてある箱。
//			// random position ランダムな速度と移動点の動作をする
//			function() {
//				for(i = 0; i<pixels.length; i++ ) {
//					var p = pixels[i];
//					//ランダムな目的地設定、速度設定
//					p.toX = Math.random()*width;
//					p.toY = Math.random()*height;
//					p.speedX = Math.cos(p.angle) * Math.random() * 3;
//					p.speedY = Math.sin(p.angle) * Math.random() * 3; 
//				}
//			},
//
//			// white flash 巨大化して真っ白になる
//			function() {
//				for(i = 0; i<pixels.length; i++ ) {
//					var pix = pixels[i];
//
//					pix.r = 255;
//					pix.g = 255;
//					pix.b = 255;
//					pix.size = Math.random()*50 + 50;
//				}
//			},

//			function() {//0 ランダムに
//				for(i = 0; i<pixels.length; i++ ) {
//					var pix = pixels[i];
//
//					pix.r = 255;
//					pix.g = 255;
//					pix.b = 255;
//					pix.size = Math.random()*50 + 50;
//				}
//			},
			
			// circle shape
			function() {
				var r = Math.floor(Math.random()*25 + 180);//円の最小サイズ
				for(i = 0; i<pixels.length; i++ ) {
					var p = pixels[i];
					p.toSize = Math.random()*4+10;
					p.toX = width/2 + Math.cos(i*3.6*(100/numParticles)*Math.PI/180) * r;
					p.toY = height/2 + Math.sin(i*3.6*(100/numParticles)*Math.PI/180) * r;
					impulsX = 0;
					impulsY = 0;
					p.speedX = (Math.random() - 0.5)/2;
					p.speedY = (Math.random() - 0.5)/2; 
					p.toR = Math.random()*255;
					p.toG = Math.random()*255;
					p.toB = Math.random()*255;
				}
			},
			
		];
		
		var boxNum = 0;
		var boxCaram = [];
		
		var boxX = 900;
		var boxY  = 30;
		var boxWidth = 200;
		var boxHeight = 76;
		var boxLineNum = 7;
		var boxMove = 0;
		
		var boxSideParam = 40;
		var boxVertParam = 14;
		
		var box = function box () {//ハコのメッセージ情報を更新する
			for (i = 0; i < boxNum; i++) {
				var boxIndexX = Math.floor(i/boxLineNum);
				var tiltParam = boxIndexX*boxVertParam;
				
				boxCaram[i] = {
					x		:boxX + boxSideParam * boxIndexX,
					y		:boxY + (i % boxLineNum) * boxHeight + tiltParam,
				};
			}
			
			if (boxNum == boxLineNum * 2) {
				boxMove = 1;//移動開始
			}
			
			if (boxNum == boxLineNum * 2 + 1) {
				boxMove = 0;
				boxNum = boxLineNum;
			}
			
		}
		
		var p = $wnd.Processing(canvas, "");//canvasを渡している、返り値はProcessingのオブジェクト、ここから先はProcessingだけ。
		
		var Universe = function Pixels() {//描画対象
			return {
				framecount : 0,
				update2: function () {
					impulsX = impulsX + (impulsToX - impulsX) / 30;
					impulsY = impulsY + (impulsToY - impulsY) / 30;
					
					// move to tox 差分移動
					for(i = 0; i<pixels.length; i++ ) {
						pixels[i].x = pixels[i].x + (pixels[i].toX - pixels[i].x) / 10;
						pixels[i].y = pixels[i].y + (pixels[i].toY - pixels[i].y) / 10;
						pixels[i].size = pixels[i].size + (pixels[i].toSize - pixels[i].size) / 10;

						pixels[i].r = pixels[i].r + (pixels[i].toR - pixels[i].r) / 10;
						pixels[i].g = pixels[i].g + (pixels[i].toG - pixels[i].g) / 10;
						pixels[i].b = pixels[i].b + (pixels[i].toB - pixels[i].b) / 10;
					}

					// update speed 速度の更新
					for(i = 0; i<pixels.length; i++ ) {

						// random movement フレーム動作

						// change position 位置を更新する
						pixels[i].toX += pixels[i].speedX;
						pixels[i].toY += pixels[i].speedY;

						// check for bounds 反対側から現れる
						if(pixels[i].x < 0) {
							pixels[i].x = width;
							pixels[i].toX = width;
						}
						if(pixels[i].x > width) {
							pixels[i].x = 0;
							pixels[i].toX = 0;
						}

						if(pixels[i].y < 0) {
							pixels[i].y = height;
							pixels[i].toY = height;
						}
						if(pixels[i].y > height) {
							pixels[i].y = 0;
							pixels[i].toY = 0;
						}

						// add impuls
						pixels[i].toX += Math.floor(impulsX * pixels[i].size/30);//方向性、減衰のある直進
						pixels[i].toY += Math.floor(impulsY * pixels[i].size/30);
					}

					// set an choord ランダムな番号の玉のサイズが変更するような仕掛けをセットする
					var r1 = Math.floor(Math.random() * pixels.length);
					var r2 = Math.floor(Math.random() * pixels.length);

					if (this.framecount %25 == 0) {//通信が発生したとする。
						pixels[r1].size = Math.random()*100;
						pixels[r2].size = Math.random()*100;
						
						//ハコ、書く。
						boxNum++;
						box();
					}
					
					
					if (boxMove == 1) {
						for (i = 0; i < boxCaram.length; i++) {
							
							if (boxLineNum <= i) {
								boxCaram[i].x = boxCaram[i-boxLineNum].x + boxSideParam;
								boxCaram[i].y = boxCaram[i-boxLineNum].y + boxVertParam;
							} else {
								boxCaram[i].x = boxCaram[i].x + (boxX-boxSideParam - boxCaram[i].x)/4;
								boxCaram[i].y = boxCaram[i].y + (boxY-boxVertParam+boxHeight*i - boxCaram[i].y)/4;
							}	
							
							
						}
						
//						for (i = boxCaram.length-1; 0 <= i; i--) {
//							
//							if (boxLineNum <= i) {
//								boxCaram[i].x = boxCaram[i].x + (boxCaram[i-boxLineNum].x - boxCaram[i].x)/4;
//								boxCaram[i].y = boxCaram[i].y + (boxCaram[i-boxLineNum].y - boxCaram[i].y)/4;
//							} else {
//								boxCaram[i].x = boxCaram[i].x + (boxX - boxCaram[i].x)/4;
//								boxCaram[i].y = boxCaram[i].y + (boxY - boxCaram[i].y)/4;
//							}	
//						}
					}
					
					
					if (this.framecount %35 == 0) {
//						numParticles++;	
					}
					this.framecount++;


					//スレッド部分 一定時間ごとにインパルスを追加
					now = new Date();
					if(true && now.getTime() - startedAt.getTime() >= 1400*machineIndex) {//machine[machineIndex]) {
						machineIndex++;

						//impulseを最大化する
						impulsX = Math.random()*800-400;//数値
						impulsY = -Math.random()*400;//数値

						var transIndex = Math.floor(Math.random()*transitions.length);//エフェクトをランダムに実行
						transitions[transIndex]();
					}
				},
				
				//描画メソッド
				draw: function () {	
					p.stroke(255, 0, 0);//カラーの枠線をつける
					p.ellipse(p.mouseX+5, p.mouseY+5, 5, 5);//マウスに円をつける

					for(i = 0; i<pixels.length; i++ ) {//存在するピクセルを描画
						p.fill(Math.floor(pixels[i].r), Math.floor(pixels[i].g), Math.floor(pixels[i].b));
						p.ellipse(pixels[i].x, pixels[i].y, pixels[i].size, pixels[i].size);
//						console.log(pixels[i].x);
					}
					
					for (i = 0; i < boxNum; i++) {//存在するボックス分だけ描画
						p.rect(boxCaram[i].x,boxCaram[i].y,boxWidth,boxHeight);
//						console.log(boxNum);
					}

				}
			}
		};

		this.@com.kissaki.client.subFrame.screen.JavaScriptTester::trace(Ljava/lang/String;)("width_"+width);
//		this.@com.kissaki.client.JavaScriptTester::trace(Ljava/lang/String;)("height_"+height);
		//925,555


		p.setup = function() {//セットアップ
			p.size(width, height);//キャンバスサイズを与える
//			p.noStroke();
			p.frameRate( 20 );
			p.background( 0 );//背景の描画
//			p.fill(0, 0, 0);//真っ黒で塗りつぶし

			startedAt = new Date();//開始時間を現時刻でセット
		}

		//描画ファンクション
		p.draw = function(){
			for (var i=0; i<components.length; i++) {
				components[i].update2();
			}

			p.background( 0 );//背景の描画

			for (var i=0; i<components.length; i++) {
				components[i].draw();
			}
		}

		components.push(new Universe());
		p.init();//キャンバスの初期化を行う、Processing関連

	}-*/;
	
	
}
