package alben.sgs.android.dialog;

import alben.sgs.android.GameApp;
import alben.sgs.android.R;
import alben.sgs.cardpai.CardPai;
import alben.sgs.type.Type;
import alben.sgs.wujiang.WuJiang;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class GuanXingDialig extends Dialog {
	public GameApp gameApp;
	public Object returnValue;

	public ImageView[] topCPImg = { null, null, null, null, null };
	public ImageView[] butCPImg = { null, null, null, null, null };
	public CardPai[] viewCPs = { null, null, null, null, null };
	public CardPai[] buttonCPs = { null, null, null, null, null };
	public int viewCPN = 0;
	public int firstClickIndex = -1;
	public int seconClickIndex = -1;
	public int tempClickIndex = 0;

	public GuanXingDialig(Context context, GameApp app) {

		super(context);
		this.gameApp = app;
		setOwnerActivity((Activity) app.gameActivityContext);
		onCreate();
	}

	public void endDialog(int result) {
		dismiss();
		Looper.getMainLooper().quit();
	}

	public Object showDialog() {
		super.show();
		try {
			MainLoopHelper.looperRun();
		} catch (RuntimeException e2) {
		}
		return returnValue;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				|| keyCode == KeyEvent.KEYCODE_HOME) {
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	/** Called when the activity is first created. */
	public void onCreate() {
		setContentView(R.layout.guanxing);

		TextView txTop = (TextView) findViewById(R.id.gx_top_info);
		TextView txBut = (TextView) findViewById(R.id.gx_but_info);
		txTop.setText("ÅÆ¶Ñ¶¥");
		txBut.setText("ÅÆ¶Ñµ×");

		this.topCPImg[0] = (ImageView) findViewById(R.id.gx_top_cp1);
		this.topCPImg[1] = (ImageView) findViewById(R.id.gx_top_cp2);
		this.topCPImg[2] = (ImageView) findViewById(R.id.gx_top_cp3);
		this.topCPImg[3] = (ImageView) findViewById(R.id.gx_top_cp4);
		this.topCPImg[4] = (ImageView) findViewById(R.id.gx_top_cp5);

		this.butCPImg[0] = (ImageView) findViewById(R.id.gx_but_cp1);
		this.butCPImg[1] = (ImageView) findViewById(R.id.gx_but_cp2);
		this.butCPImg[2] = (ImageView) findViewById(R.id.gx_but_cp3);
		this.butCPImg[3] = (ImageView) findViewById(R.id.gx_but_cp4);
		this.butCPImg[4] = (ImageView) findViewById(R.id.gx_but_cp5);

		// count the living wuJiang
		WuJiang myWJ = this.gameApp.gameLogicData.myWuJiang;
		int alivingWJN = 1;
		WuJiang tarWJ = myWJ.nextOne;
		while (!tarWJ.equals(myWJ)) {
			alivingWJN++;
			tarWJ = tarWJ.nextOne;
		}

		this.viewCPN = (alivingWJN >= 5) ? 5 : alivingWJN;

		CardPai butCP = new CardPai(Type.CardPai.nil, Type.CardPaiClass.nil, 0);
		butCP.imageNumber = R.drawable.card_back;

		// add card pai to view
		for (int i = 0; (i < viewCPN && i < this.topCPImg.length); i++) {
			this.viewCPs[i] = this.gameApp.gameLogicData.cpHelper
					.viewTopCardPai(i);
			this.topCPImg[i].setImageDrawable(this.gameApp.getResources()
					.getDrawable(this.viewCPs[i].imageNumber));

			this.buttonCPs[i] = butCP.copy();

			this.topCPImg[i].setOnClickListener(new TopCardPaiImageListener());

			this.butCPImg[i].setOnClickListener(new TopCardPaiImageListener());
		}

		// disable other top and button card pai
		for (int i = viewCPN; i < this.topCPImg.length; i++) {
			this.topCPImg[i].setVisibility(View.INVISIBLE);
			this.butCPImg[i].setVisibility(View.INVISIBLE);
		}

		//

		findViewById(R.id.gx_okBtn).setOnClickListener(
				new android.view.View.OnClickListener() {
					@Override
					public void onClick(View paramView) {
						endDialog(1);
					}
				});
	}

	private class TopCardPaiImageListener implements
			android.view.View.OnClickListener {
		public void onClick(View v) {
			int index = 0;
			switch (v.getId()) {
			// top
			case R.id.gx_top_cp1: {
				index = 0;
				break;
			}
			case R.id.gx_top_cp2: {
				index = 1;
				break;
			}
			case R.id.gx_top_cp3: {
				index = 2;
				break;
			}
			case R.id.gx_top_cp4: {
				index = 3;
				break;
			}
			case R.id.gx_top_cp5: {
				index = 4;
				break;
			}
				// button
			case R.id.gx_but_cp1: {
				index = 0;
				index += 10;
				break;
			}
			case R.id.gx_but_cp2: {
				index = 1;
				index += 10;
				break;
			}
			case R.id.gx_but_cp3: {
				index = 2;
				index += 10;
				break;
			}
			case R.id.gx_but_cp4: {
				index = 3;
				index += 10;
				break;
			}
			case R.id.gx_but_cp5: {
				index = 4;
				index += 10;
				break;
			}
			}

			// set color
			if (index < 10) {
				topCPImg[index].setBackgroundDrawable(gameApp.getResources()
						.getDrawable(R.drawable.bg_green));
			} else {
				butCPImg[index - 10].setBackgroundDrawable(gameApp
						.getResources().getDrawable(R.drawable.bg_green));
			}

			// set click
			if (firstClickIndex == -1) {
				firstClickIndex = index;
			} else if (seconClickIndex == -1) {
				seconClickIndex = index;
			}

			if (firstClickIndex != -1 && seconClickIndex != -1) {
				// change the two CP
				changeTwoCardPaisPostion();
				// reset click
				resetClickPoint();
				// reset color
				resetImgColor();
			}
		}
	}

	// reset them
	public void resetClickPoint() {
		firstClickIndex = -1;
		seconClickIndex = -1;
	}

	// set color to click
	public void resetImgColor() {
		for (int i = 0; i < viewCPN; i++) {
			topCPImg[i].setBackgroundDrawable(gameApp.getResources()
					.getDrawable(R.drawable.bg_black));
			butCPImg[i].setBackgroundDrawable(gameApp.getResources()
					.getDrawable(R.drawable.bg_black));
		}
	}

	// change the two CP
	public void changeTwoCardPaisPostion() {
		if (firstClickIndex >= 10 && seconClickIndex >= 10) {
			// the selected to CPs are in but, no action
			return;
		}

		if (firstClickIndex < 10 && seconClickIndex < 10) {
			// change the top order

			// switch them
			CardPai firstCP = viewCPs[firstClickIndex];
			CardPai seconCP = viewCPs[seconClickIndex];

			viewCPs[seconClickIndex] = firstCP;
			viewCPs[firstClickIndex] = seconCP;

			this.gameApp.gameLogicData.cpHelper.setTopCardPai(seconClickIndex,
					firstCP);
			this.gameApp.gameLogicData.cpHelper.setTopCardPai(firstClickIndex,
					seconCP);

			// update the topImage view
			topCPImg[firstClickIndex].setImageDrawable(this.gameApp
					.getResources().getDrawable(
							viewCPs[firstClickIndex].imageNumber));
			topCPImg[seconClickIndex].setImageDrawable(this.gameApp
					.getResources().getDrawable(
							viewCPs[seconClickIndex].imageNumber));
		}

		if (firstClickIndex < 10 && seconClickIndex >= 10) {
			// move first one from top to button

			seconClickIndex -= 10;

			CardPai firstCP = viewCPs[firstClickIndex];
			CardPai seconCP = buttonCPs[seconClickIndex];

			// switch them
			buttonCPs[seconClickIndex] = firstCP;
			viewCPs[firstClickIndex] = seconCP;

			this.gameApp.gameLogicData.cpHelper.setTopCardPai(firstClickIndex,
					seconCP);

			// update the topImage view
			topCPImg[firstClickIndex].setImageDrawable(this.gameApp
					.getResources().getDrawable(
							viewCPs[firstClickIndex].imageNumber));
			butCPImg[seconClickIndex].setImageDrawable(this.gameApp
					.getResources().getDrawable(
							buttonCPs[seconClickIndex].imageNumber));
		}

		if (firstClickIndex >= 10 && seconClickIndex < 10) {
			// move first one from button to top

			firstClickIndex -= 10;

			CardPai firstCP = buttonCPs[firstClickIndex];
			CardPai seconCP = viewCPs[seconClickIndex];

			viewCPs[seconClickIndex] = firstCP;
			buttonCPs[firstClickIndex] = seconCP;

			this.gameApp.gameLogicData.cpHelper.setTopCardPai(seconClickIndex,
					firstCP);

			// update the topImage view
			topCPImg[seconClickIndex].setImageDrawable(this.gameApp
					.getResources().getDrawable(
							viewCPs[seconClickIndex].imageNumber));
			butCPImg[firstClickIndex].setImageDrawable(this.gameApp
					.getResources().getDrawable(
							buttonCPs[firstClickIndex].imageNumber));
		}
	}
}
