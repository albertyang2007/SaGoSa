package alben.sgs.android.dialog;

import alben.sgs.android.GameApp;
import alben.sgs.android.R;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.instance.BingLiangCunDuan;
import alben.sgs.cardpai.instance.LeBuShiShu;
import alben.sgs.cardpai.instance.ShanDian;
import alben.sgs.type.Type;
import alben.sgs.type.UpdateWJViewData;
import alben.sgs.wujiang.WuJiang;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class QGameSetUpPanDingDialog extends Dialog {
	public int firstClickIndex = -1;
	public GameApp gameApp;
	public ImageView[] pdCPImg = new ImageView[8];
	public CardPai[] pdCPs = new CardPai[8];
	public ImageView[] pdUsedImg = new ImageView[3];
	public Object returnValue;
	public int seconClickIndex = -1;
	public WuJiang selectedWJ = null;
	public int tempClickIndex = 0;

	public QGameSetUpPanDingDialog(Context paramContext, GameApp paramGameApp) {
		super(paramContext);
		this.gameApp = paramGameApp;
		setOwnerActivity((Activity) paramGameApp.gameActivityContext);
		onCreate();
	}

	public void endDialog(int paramInt) {
		dismiss();
		Looper.getMainLooper().quit();
	}

	public void initPandingAreaView() {
		//
		int viewIndex;
		for (viewIndex = 0; viewIndex < selectedWJ.panDingPai.size(); viewIndex++) {
			this.pdUsedImg[viewIndex]
					.setImageDrawable(this.gameApp.getResources().getDrawable(
							(selectedWJ.panDingPai.get(viewIndex)).imageNumber));
			this.pdUsedImg[viewIndex]
					.setOnClickListener(new PanDingAreaCardPaiImageListener());
			this.pdUsedImg[viewIndex].setEnabled(true);
		}
		// set others to back
		while (viewIndex < this.pdUsedImg.length) {
			this.pdUsedImg[viewIndex].setImageDrawable(this.gameApp
					.getResources().getDrawable(R.drawable.card_back));
			this.pdUsedImg[viewIndex]
					.setOnClickListener(new PanDingAreaCardPaiImageListener());
			this.pdUsedImg[viewIndex].setEnabled(false);
			viewIndex++;
		}
	}

	public void onCreate() {
		setContentView(R.layout.qgame_setup_pd);

		this.selectedWJ = this.gameApp.wjDetailsViewData.selectedWJ;
		TextView pdInfo = (TextView) findViewById(R.id.qgame_pd_info);
		TextView pdCPInfo = (TextView) findViewById(R.id.qgame_pd_cp_info);
		pdInfo.setText("判定区");
		pdCPInfo.setText("判定牌");

		this.pdUsedImg[0] = ((ImageView) findViewById(R.id.qgame_pd1));
		this.pdUsedImg[1] = ((ImageView) findViewById(R.id.qgame_pd2));
		this.pdUsedImg[2] = ((ImageView) findViewById(R.id.qgame_pd3));

		this.pdCPImg[0] = ((ImageView) findViewById(R.id.qgame_pd_cp1));
		this.pdCPImg[1] = ((ImageView) findViewById(R.id.qgame_pd_cp2));
		this.pdCPImg[2] = ((ImageView) findViewById(R.id.qgame_pd_cp3));
		this.pdCPImg[3] = ((ImageView) findViewById(R.id.qgame_pd_cp4));
		this.pdCPImg[4] = ((ImageView) findViewById(R.id.qgame_pd_cp5));
		this.pdCPImg[5] = ((ImageView) findViewById(R.id.qgame_pd_cp6));
		this.pdCPImg[6] = ((ImageView) findViewById(R.id.qgame_pd_cp7));
		this.pdCPImg[7] = ((ImageView) findViewById(R.id.qgame_pd_cp8));

		initPandingAreaView();
		updatePandingCardPaiView();

		Button okBtn = (Button) findViewById(R.id.qgame_pd_okBtn);
		okBtn.setText("确定");
		okBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramView) {
				UpdateWJViewData item = new UpdateWJViewData();
				item.updatePangDing = true;
				gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(
						selectedWJ, item);
				endDialog(1);
			}
		});
		//
		Button addBtn = (Button) findViewById(R.id.qgame_pd_addBtn);
		addBtn.setText("添加");
		addBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if ((seconClickIndex >= 0) && (seconClickIndex < pdCPs.length)) {
					CardPai localCardPai = pdCPs[seconClickIndex];
					if (selectedWJ.panDingPai.size() < 3
							&& !alreadyHasSuchPDCP(localCardPai.name)) {
						localCardPai.belongToWuJiang = selectedWJ;
						localCardPai.cpState = Type.CPState.pandDingPai;
						selectedWJ.panDingPai.add(localCardPai);
					}
				}
				updatePandingAreaView();
				updatePandingCardPaiView();
				resetClickPoint();
				resetImgColor();
			}
		});
		//
		Button delBtn = (Button) findViewById(R.id.qgame_pd_delBtn);
		delBtn.setText("删除");
		delBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if ((firstClickIndex >= 0)
						&& (firstClickIndex < selectedWJ.panDingPai.size())) {
					CardPai localCardPai = selectedWJ.panDingPai
							.get(firstClickIndex);
					if (localCardPai != null) {
						localCardPai.reset();
						selectedWJ.panDingPai.remove(localCardPai);
						updatePandingAreaView();
						updatePandingCardPaiView();
						resetClickPoint();
						resetImgColor();
					}
				}
			}
		});
	}

	public void resetClickPoint() {
		this.firstClickIndex = -1;
		this.seconClickIndex = -1;
	}

	public boolean alreadyHasSuchPDCP(Type.CardPai name) {
		for (int i = 0; i < selectedWJ.panDingPai.size(); i++) {
			CardPai pdCP = selectedWJ.panDingPai.get(i);
			if (pdCP.name == name) {
				return true;
			}
		}
		return false;
	}

	public void resetImgColor() {
		for (int viewIndex = 0; viewIndex < this.pdUsedImg.length; viewIndex++)
			this.pdUsedImg[viewIndex].setBackgroundDrawable(this.gameApp
					.getResources().getDrawable(R.drawable.bg_black));

		for (int viewIndex = 0; viewIndex < this.pdCPImg.length; viewIndex++)
			this.pdCPImg[viewIndex].setBackgroundDrawable(this.gameApp
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

	public void updatePandingAreaView() {
		int i;
		for (i = 0; i < selectedWJ.panDingPai.size(); i++) {
			this.pdUsedImg[i].setImageDrawable(this.gameApp.getResources()
					.getDrawable((selectedWJ.panDingPai.get(i)).imageNumber));
			this.pdUsedImg[i].setEnabled(true);
		}
		// set others to back
		while (i < this.pdUsedImg.length) {
			this.pdUsedImg[i].setImageDrawable(this.gameApp.getResources()
					.getDrawable(R.drawable.card_back));
			this.pdUsedImg[i].setEnabled(false);
			i++;
		}
	}

	public void updatePandingCardPaiView() {
		int viewIndex = 0;
		for (int poolIndex = 0; (poolIndex < this.gameApp.gameLogicData.cpHelper.cardPaiPool
				.size()) && (viewIndex < this.pdCPImg.length); poolIndex++) {
			CardPai localCardPai = (CardPai) this.gameApp.gameLogicData.cpHelper.cardPaiPool
					.get(poolIndex);
			if ((localCardPai.belongToWuJiang != null)
					|| ((!(localCardPai instanceof LeBuShiShu))
							&& (!(localCardPai instanceof BingLiangCunDuan)) && (!(localCardPai instanceof ShanDian))))
				continue;
			this.pdCPs[viewIndex] = localCardPai;
			this.pdCPImg[viewIndex].setImageDrawable(this.gameApp
					.getResources().getDrawable(
							this.pdCPs[viewIndex].imageNumber));
			this.pdCPImg[viewIndex]
					.setOnClickListener(new CardPaiImageListener());
			this.pdCPImg[viewIndex].setEnabled(true);
			viewIndex++;
		}
		// set others to back
		while (viewIndex < this.pdCPImg.length) {
			this.pdCPImg[viewIndex].setImageDrawable(this.gameApp
					.getResources().getDrawable(R.drawable.card_back));
			this.pdCPImg[viewIndex].setEnabled(false);
			viewIndex++;
		}
	}

	private class CardPaiImageListener implements View.OnClickListener {

		public void onClick(View v) {
			int i = 0;
			switch (v.getId()) {
			case R.id.qgame_pd_cp1:
				i = 0;
				break;
			case R.id.qgame_pd_cp2:
				i = 1;
				break;
			case R.id.qgame_pd_cp3:
				i = 2;
				break;
			case R.id.qgame_pd_cp4:
				i = 3;
				break;
			case R.id.qgame_pd_cp5:
				i = 4;
				break;
			case R.id.qgame_pd_cp6:
				i = 5;
				break;
			case R.id.qgame_pd_cp7:
				i = 6;
				break;
			case R.id.qgame_pd_cp8:
				i = 7;
				break;
			}
			//
			pdCPImg[i].setBackgroundDrawable(gameApp.getResources()
					.getDrawable(R.drawable.bg_green));
			seconClickIndex = i;
		}
	}

	private class PanDingAreaCardPaiImageListener implements
			View.OnClickListener {

		public void onClick(View v) {
			int i = 0;
			switch (v.getId()) {
			case R.id.qgame_pd1:
				i = 0;
				break;
			case R.id.qgame_pd2:
				i = 1;
				break;
			case R.id.qgame_pd3:
				i = 2;
				break;
			}
			//
			pdUsedImg[i].setBackgroundDrawable(gameApp.getResources()
					.getDrawable(R.drawable.bg_green));
			firstClickIndex = i;
		}
	}
}