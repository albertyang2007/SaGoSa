package alben.sgs.android.dialog;

import java.util.ArrayList;

import alben.sgs.android.GameApp;
import alben.sgs.android.R;
import alben.sgs.cardpai.CardPai;
import alben.sgs.type.Type;
import alben.sgs.type.UpdateWJViewData;
import alben.sgs.wujiang.WuJiang;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class QGameSetUpShouPaiDialog extends Dialog {
	public ImageView[] cardPaiImgView = new ImageView[15];
	public CardPai[] cardPais = new CardPai[15];
	public int[] cpPage = new int[4];
	public GameApp gameApp;
	public int inWhichTab = R.id.qgame_shoupai_cp;
	public Object returnValue;
	public CardPai selectedCP = null;
	public WuJiang selectedWJ = null;
	public ArrayList<CardPai> shouPais = new ArrayList<CardPai>();

	public QGameSetUpShouPaiDialog(Context paramContext, GameApp paramGameApp) {
		super(paramContext);
		this.gameApp = paramGameApp;
		setOwnerActivity((Activity) paramGameApp.gameActivityContext);
		onCreate();
	}

	public void endDialog(int paramInt) {
		dismiss();
		Looper.getMainLooper().quit();
	}

	public void onCreate() {
		setContentView(R.layout.qgame_setup_sp);

		this.selectedWJ = this.gameApp.wjDetailsViewData.selectedWJ;

		((ImageView) findViewById(R.id.qgame_shoupai_cp))
				.setOnTouchListener(new TopTabImageController());
		((ImageView) findViewById(R.id.qgame_fangpian_cp))
				.setOnTouchListener(new TopTabImageController());
		((ImageView) findViewById(R.id.qgame_hongtao_cp))
				.setOnTouchListener(new TopTabImageController());
		((ImageView) findViewById(R.id.qgame_meihua_cp))
				.setOnTouchListener(new TopTabImageController());
		((ImageView) findViewById(R.id.qgame_heitao_cp))
				.setOnTouchListener(new TopTabImageController());

		this.cardPaiImgView[0] = ((ImageView) findViewById(R.id.qgame_cp1));
		this.cardPaiImgView[1] = ((ImageView) findViewById(R.id.qgame_cp2));
		this.cardPaiImgView[2] = ((ImageView) findViewById(R.id.qgame_cp3));
		this.cardPaiImgView[3] = ((ImageView) findViewById(R.id.qgame_cp4));
		this.cardPaiImgView[4] = ((ImageView) findViewById(R.id.qgame_cp5));
		this.cardPaiImgView[5] = ((ImageView) findViewById(R.id.qgame_cp6));
		this.cardPaiImgView[6] = ((ImageView) findViewById(R.id.qgame_cp7));
		this.cardPaiImgView[7] = ((ImageView) findViewById(R.id.qgame_cp8));
		this.cardPaiImgView[8] = ((ImageView) findViewById(R.id.qgame_cp9));
		this.cardPaiImgView[9] = ((ImageView) findViewById(R.id.qgame_cp10));
		this.cardPaiImgView[10] = ((ImageView) findViewById(R.id.qgame_cp11));
		this.cardPaiImgView[11] = ((ImageView) findViewById(R.id.qgame_cp12));
		this.cardPaiImgView[12] = ((ImageView) findViewById(R.id.qgame_cp13));
		this.cardPaiImgView[13] = ((ImageView) findViewById(R.id.qgame_cp14));
		this.cardPaiImgView[14] = ((ImageView) findViewById(R.id.qgame_cp15));
		//
		for (int i = 0; i < this.cardPaiImgView.length; i++)
			this.cardPaiImgView[i]
					.setOnClickListener(new CardPaiImageListener());
		//
		for (int i = 0; i < this.selectedWJ.shouPai.size(); i++)
			this.shouPais.add(this.selectedWJ.shouPai.get(i));

		updateShouPaiToView();
		Button localButton = (Button) findViewById(R.id.setup_cp_ok);
		localButton.setText("确定");
		localButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// first empty all
				while (selectedWJ.shouPai.size() > 0) {
					((CardPai) selectedWJ.shouPai.get(0)).reset();
					selectedWJ.shouPai.remove(0);
				}
				// then add new one
				for (int i = 0; i < shouPais.size(); i++)
					selectedWJ.shouPai.add(shouPais.get(i));

				UpdateWJViewData item = new UpdateWJViewData();
				item.updateShouPaiNumber = true;
				gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(
						selectedWJ, item);
				// update my wujiang
				if (selectedWJ.imageViewIndex == 7)
					gameApp.gameLogicData.wjHelper
							.updateWJ8ShouPaiToLibGameView();
				endDialog(1);
			}
		});

		localButton = (Button) findViewById(R.id.setup_cp_add);
		localButton.setText("添加");
		localButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if ((selectedCP != null) && (shouPais.size() < cardPais.length)) {
					selectedCP.belongToWuJiang = selectedWJ;
					selectedCP.cpState = Type.CPState.ShouPai;
					shouPais.add(selectedCP);
					switch (inWhichTab) {
					case R.id.qgame_fangpian_cp:
						updateHuaShiCardPaiToView(Type.CardPaiClass.FangPian);
						break;
					case R.id.qgame_hongtao_cp:
						updateHuaShiCardPaiToView(Type.CardPaiClass.HongTao);
						break;
					case R.id.qgame_meihua_cp:
						updateHuaShiCardPaiToView(Type.CardPaiClass.MeiHua);
						break;
					case R.id.qgame_heitao_cp:
						updateHuaShiCardPaiToView(Type.CardPaiClass.HeiTao);
						break;
					}
					resetCardPaiBackGround();
				}
			}
		});

		localButton = (Button) findViewById(R.id.setup_cp_del);
		localButton.setText("删除");
		localButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if ((inWhichTab == R.id.qgame_shoupai_cp)
						&& (selectedCP != null)) {
					selectedCP.reset();
					shouPais.remove(selectedCP);
					updateShouPaiToView();
					resetCardPaiBackGround();
				}
			}
		});

		localButton = (Button) findViewById(R.id.setup_cp_pre);
		localButton.setText("上页");
		localButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				int[] arrayOfInt;
				switch (inWhichTab) {
				case R.id.qgame_fangpian_cp:
					arrayOfInt = cpPage;
					arrayOfInt[0] = (-1 + arrayOfInt[0]);
					updateHuaShiCardPaiToView(Type.CardPaiClass.FangPian);
					break;
				case R.id.qgame_hongtao_cp:
					arrayOfInt = cpPage;
					arrayOfInt[1] = (-1 + arrayOfInt[1]);
					updateHuaShiCardPaiToView(Type.CardPaiClass.HongTao);
					break;
				case R.id.qgame_meihua_cp:
					arrayOfInt = cpPage;
					arrayOfInt[2] = (-1 + arrayOfInt[2]);
					updateHuaShiCardPaiToView(Type.CardPaiClass.MeiHua);
					break;
				case R.id.qgame_heitao_cp:
					arrayOfInt = cpPage;
					arrayOfInt[3] = (-1 + arrayOfInt[3]);
					updateHuaShiCardPaiToView(Type.CardPaiClass.HeiTao);
					break;
				}
			}
		});

		localButton = (Button) findViewById(R.id.setup_cp_next);
		localButton.setText("下页");
		localButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				int[] arrayOfInt;
				switch (inWhichTab) {
				case R.id.qgame_fangpian_cp:
					arrayOfInt = cpPage;
					arrayOfInt[0] = (1 + arrayOfInt[0]);
					updateHuaShiCardPaiToView(Type.CardPaiClass.FangPian);
					break;
				case R.id.qgame_hongtao_cp:
					arrayOfInt = cpPage;
					arrayOfInt[1] = (1 + arrayOfInt[1]);
					updateHuaShiCardPaiToView(Type.CardPaiClass.HongTao);
					break;
				case R.id.qgame_meihua_cp:
					arrayOfInt = cpPage;
					arrayOfInt[2] = (1 + arrayOfInt[2]);
					updateHuaShiCardPaiToView(Type.CardPaiClass.MeiHua);
					break;
				case R.id.qgame_heitao_cp:
					arrayOfInt = cpPage;
					arrayOfInt[3] = (1 + arrayOfInt[3]);
					updateHuaShiCardPaiToView(Type.CardPaiClass.HeiTao);
					break;
				}
			}
		});
	}

	public void resetCardPaiBackGround() {
		for (int i = 0; i < this.cardPaiImgView.length; i++)
			this.cardPaiImgView[i].setBackgroundDrawable(this.gameApp
					.getResources().getDrawable(R.drawable.bg_black));
	}

	public Object showDialog() {
		super.show();
		try {
			MainLoopHelper.looperRun();
		} catch (RuntimeException e2) {
		}
		return returnValue;
	}

	public void updateHuaShiCardPaiToView(Type.CardPaiClass paramCardPaiClass) {
		int i = 0;
		for (int j = 0; j < this.cpPage.length; j++)
			if (this.cpPage[j] >= 0) {
				if (this.cpPage[j] <= 2)
					continue;
				this.cpPage[j] = 2;
			} else {
				this.cpPage[j] = 0;
			}
		switch (this.inWhichTab) {
		case R.id.qgame_fangpian_cp:
			i = this.cpPage[0];
			break;
		case R.id.qgame_hongtao_cp:
			i = this.cpPage[1];
			break;
		case R.id.qgame_meihua_cp:
			i = this.cpPage[2];
			break;
		case R.id.qgame_heitao_cp:
			i = this.cpPage[3];
		}
		//
		i *= this.cardPaiImgView.length;
		int viewIndex;
		for (viewIndex = 0; viewIndex < this.cardPais.length; viewIndex++)
			this.cardPais[viewIndex] = null;
		viewIndex = 0;
		int k = 0;
		for (int poolIndex = 0; (poolIndex < this.gameApp.gameLogicData.cpHelper.cardPaiPool
				.size()) && (viewIndex < this.cardPaiImgView.length); poolIndex++) {
			CardPai localCardPai = (CardPai) this.gameApp.gameLogicData.cpHelper.cardPaiPool
					.get(poolIndex);
			if ((localCardPai.clas != paramCardPaiClass)
					|| (localCardPai.belongToWuJiang != null)
					|| (this.gameApp.settingsViewData.paiDuiTopCPs
							.contains(localCardPai)))
				continue;
			k++;
			if (k < i)
				continue;
			this.cardPais[viewIndex] = localCardPai;
			this.cardPaiImgView[viewIndex].setImageDrawable(this.gameApp
					.getResources().getDrawable(localCardPai.imageNumber));
			this.cardPaiImgView[viewIndex].setEnabled(true);
			viewIndex++;
		}
		// set others to back
		while (viewIndex < this.cardPaiImgView.length) {
			this.cardPaiImgView[viewIndex].setImageDrawable(this.gameApp
					.getResources().getDrawable(R.drawable.card_back));
			this.cardPaiImgView[viewIndex].setEnabled(false);
			viewIndex++;
		}
	}

	public void updateShouPaiToView() {
		int viewIndex;
		for (viewIndex = 0; (viewIndex < this.shouPais.size())
				&& (viewIndex < this.cardPaiImgView.length); viewIndex++) {
			this.cardPaiImgView[viewIndex].setImageDrawable(this.gameApp
					.getResources().getDrawable(
							(this.shouPais.get(viewIndex)).imageNumber));
			this.cardPaiImgView[viewIndex].setEnabled(true);
		}
		// set others to back
		while (viewIndex < this.cardPaiImgView.length) {
			this.cardPaiImgView[viewIndex].setImageDrawable(this.gameApp
					.getResources().getDrawable(R.drawable.card_back));
			this.cardPaiImgView[viewIndex].setEnabled(false);
			viewIndex++;
		}
	}

	private class CardPaiImageListener implements View.OnClickListener {

		public void onClick(View v) {
			int j = 0;
			switch (v.getId()) {
			case R.id.qgame_cp1:
				j = 0;
				break;
			case R.id.qgame_cp2:
				j = 1;
				break;
			case R.id.qgame_cp3:
				j = 2;
				break;
			case R.id.qgame_cp4:
				j = 3;
				break;
			case R.id.qgame_cp5:
				j = 4;
				break;
			case R.id.qgame_cp6:
				j = 5;
				break;
			case R.id.qgame_cp7:
				j = 6;
				break;
			case R.id.qgame_cp8:
				j = 7;
				break;
			case R.id.qgame_cp9:
				j = 8;
				break;
			case R.id.qgame_cp10:
				j = 9;
				break;
			case R.id.qgame_cp11:
				j = 10;
				break;
			case R.id.qgame_cp12:
				j = 11;
				break;
			case R.id.qgame_cp13:
				j = 12;
				break;
			case R.id.qgame_cp14:
				j = 13;
				break;
			case R.id.qgame_cp15:
				j = 14;
			}
			//
			selectedCP = null;
			if (inWhichTab != R.id.qgame_shoupai_cp)
				selectedCP = cardPais[j];
			else if (j < shouPais.size())
				selectedCP = ((CardPai) shouPais.get(j));
			for (int i = 0; i < cardPaiImgView.length; i++)
				if (i != j)
					cardPaiImgView[i].setBackgroundDrawable(gameApp
							.getResources().getDrawable(R.drawable.bg_black));
				else
					cardPaiImgView[i].setBackgroundDrawable(gameApp
							.getResources().getDrawable(R.drawable.bg_green));
		}
	}

	private class TopTabImageController implements View.OnTouchListener {

		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				break;
			case MotionEvent.ACTION_UP:
				inWhichTab = v.getId();
				switch (v.getId()) {
				default:
					break;
				case R.id.qgame_fangpian_cp:
					updateHuaShiCardPaiToView(Type.CardPaiClass.FangPian);
					break;
				case R.id.qgame_hongtao_cp:
					updateHuaShiCardPaiToView(Type.CardPaiClass.HongTao);
					break;
				case R.id.qgame_meihua_cp:
					updateHuaShiCardPaiToView(Type.CardPaiClass.MeiHua);
					break;
				case R.id.qgame_heitao_cp:
					updateHuaShiCardPaiToView(Type.CardPaiClass.HeiTao);
					break;
				case R.id.qgame_shoupai_cp:
					updateShouPaiToView();
					break;
				}
			}
			return true;
		}
	}
}