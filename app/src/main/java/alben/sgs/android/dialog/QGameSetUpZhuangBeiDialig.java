package alben.sgs.android.dialog;

import alben.sgs.android.GameApp;
import alben.sgs.android.R;
import alben.sgs.android.mycontroller.MyImageButton;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.FangJuCardPai;
import alben.sgs.cardpai.MaCardPai;
import alben.sgs.cardpai.WuQiCardPai;
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

public class QGameSetUpZhuangBeiDialig extends Dialog {
	public GameApp gameApp;
	public Object returnValue;
	public WuJiang selectedWJ = null;
	public CardPai selectedZB = null;
	public ImageView selectedZBImg = null;
	public CardPai[] zhuangBei = new CardPai[15];
	public ImageView[] zhuangBeiView = new ImageView[15];

	public QGameSetUpZhuangBeiDialig(Context paramContext, GameApp paramGameApp) {
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
		setContentView(R.layout.qgame_setup_zb);

		this.selectedWJ = this.gameApp.wjDetailsViewData.selectedWJ;

		((MyImageButton) findViewById(R.id.invisible_btn2))
				.setVisibility(View.INVISIBLE);
		((MyImageButton) findViewById(R.id.invisible_btn3))
				.setVisibility(View.INVISIBLE);
		//
		this.selectedZBImg = ((ImageView) findViewById(R.id.qgame_selected_zb));
		this.selectedZBImg.setOnClickListener(new SelectedZBImageListener());

		if (this.selectedWJ.zhuangBei.wuQi == null) {
			this.selectedZBImg.setImageDrawable(this.gameApp.getResources()
					.getDrawable(R.drawable.card_back));
			this.selectedZBImg.setEnabled(false);
		} else {
			this.selectedZBImg.setImageDrawable(this.gameApp.getResources()
					.getDrawable(this.selectedWJ.zhuangBei.wuQi.imageNumber));
			this.selectedZBImg.setEnabled(true);
		}
		//
		((ImageView) findViewById(R.id.qgame_zb_wq))
				.setOnTouchListener(new MyZhuangBeiImageController());
		((ImageView) findViewById(R.id.qgame_zb_fj))
				.setOnTouchListener(new MyZhuangBeiImageController());
		((ImageView) findViewById(R.id.qgame_zb_jiama))
				.setOnTouchListener(new MyZhuangBeiImageController());
		((ImageView) findViewById(R.id.qgame_zb_jianma))
				.setOnTouchListener(new MyZhuangBeiImageController());
		//
		this.zhuangBeiView[0] = ((ImageView) findViewById(R.id.qgame_zb1));
		this.zhuangBeiView[1] = ((ImageView) findViewById(R.id.qgame_zb2));
		this.zhuangBeiView[2] = ((ImageView) findViewById(R.id.qgame_zb3));
		this.zhuangBeiView[3] = ((ImageView) findViewById(R.id.qgame_zb4));
		this.zhuangBeiView[4] = ((ImageView) findViewById(R.id.qgame_zb5));
		this.zhuangBeiView[5] = ((ImageView) findViewById(R.id.qgame_zb6));
		this.zhuangBeiView[6] = ((ImageView) findViewById(R.id.qgame_zb7));
		this.zhuangBeiView[7] = ((ImageView) findViewById(R.id.qgame_zb8));
		this.zhuangBeiView[8] = ((ImageView) findViewById(R.id.qgame_zb9));
		this.zhuangBeiView[9] = ((ImageView) findViewById(R.id.qgame_zb10));
		this.zhuangBeiView[10] = ((ImageView) findViewById(R.id.qgame_zb11));
		this.zhuangBeiView[11] = ((ImageView) findViewById(R.id.qgame_zb12));
		this.zhuangBeiView[12] = ((ImageView) findViewById(R.id.qgame_zb13));
		this.zhuangBeiView[13] = ((ImageView) findViewById(R.id.qgame_zb14));
		this.zhuangBeiView[14] = ((ImageView) findViewById(R.id.qgame_zb15));

		for (int i = 0; i < this.zhuangBeiView.length; i++)
			this.zhuangBeiView[i]
					.setOnClickListener(new ZhuangBeiImageListener());

		this.gameApp.settingsViewData.qGameSetupSetp = Type.QGameSetupStep.WuQi;

		updateZhuangBeiImg();
		//
		Button localButton = (Button) findViewById(R.id.setup_zb_ok);
		localButton.setText("È·¶¨");
		localButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				UpdateWJViewData item = new UpdateWJViewData();
				item.updateZhuangBei = true;
				gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(
						selectedWJ, item);
				endDialog(1);
			}
		});
		//
		localButton = (Button) findViewById(R.id.setup_zb_add);
		localButton.setText("Ìí¼Ó");
		localButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (selectedZB == null) {
					return;
				}
				if (selectedZB instanceof WuQiCardPai) {
					if (selectedWJ.zhuangBei.wuQi != null) {
						selectedWJ.zhuangBei.wuQi.belongToWuJiang = null;
						selectedWJ.zhuangBei.wuQi.cpState = Type.CPState.PaiDui;
					}
					selectedWJ.zhuangBei.wuQi = ((WuQiCardPai) selectedZB);
					selectedWJ.zhuangBei.wuQi.cpState = Type.CPState.wuQiPai;
				} else if (selectedZB instanceof FangJuCardPai) {
					if (selectedWJ.zhuangBei.fangJu != null) {
						selectedWJ.zhuangBei.fangJu.belongToWuJiang = null;
						selectedWJ.zhuangBei.fangJu.cpState = Type.CPState.PaiDui;
					}
					selectedWJ.zhuangBei.fangJu = ((FangJuCardPai) selectedZB);
					selectedWJ.zhuangBei.fangJu.cpState = Type.CPState.fangJuPai;
				} else if (selectedZB instanceof MaCardPai) {
					MaCardPai localMaCardPai = (MaCardPai) selectedZB;
					if (localMaCardPai.distance == 1) {
						if (selectedWJ.zhuangBei.jiaYiMa != null) {
							selectedWJ.zhuangBei.jiaYiMa.belongToWuJiang = null;
							selectedWJ.zhuangBei.jiaYiMa.cpState = Type.CPState.PaiDui;
						}
						selectedWJ.zhuangBei.jiaYiMa = localMaCardPai;
						selectedWJ.zhuangBei.jiaYiMa.cpState = Type.CPState.jiaYiMaPai;
					} else if (localMaCardPai.distance == -1) {
						if (selectedWJ.zhuangBei.jianYiMa != null) {
							selectedWJ.zhuangBei.jianYiMa.belongToWuJiang = null;
							selectedWJ.zhuangBei.jianYiMa.cpState = Type.CPState.PaiDui;
						}
						selectedWJ.zhuangBei.jianYiMa = localMaCardPai;
						selectedWJ.zhuangBei.jianYiMa.cpState = Type.CPState.jianYiMaPai;
					}
				}
				//
				selectedZB.belongToWuJiang = selectedWJ;
				selectedZBImg.setImageDrawable(gameApp.getResources()
						.getDrawable(selectedZB.imageNumber));
				selectedZBImg.setEnabled(true);
				updateZhuangBeiImg();
			}
		});

		//
		localButton = (Button) findViewById(R.id.setup_zb_del);
		localButton.setText("É¾³ý");
		localButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (gameApp.settingsViewData.qGameSetupSetp == Type.QGameSetupStep.WuQi) {
					if (selectedWJ.zhuangBei.wuQi != null) {
						selectedWJ.zhuangBei.wuQi.belongToWuJiang = null;
						selectedWJ.zhuangBei.wuQi.cpState = Type.CPState.PaiDui;
						selectedWJ.zhuangBei.wuQi = null;
					}
				} else if (gameApp.settingsViewData.qGameSetupSetp == Type.QGameSetupStep.FangJu) {
					if (selectedWJ.zhuangBei.fangJu != null) {
						selectedWJ.zhuangBei.fangJu.belongToWuJiang = null;
						selectedWJ.zhuangBei.fangJu.cpState = Type.CPState.PaiDui;
						selectedWJ.zhuangBei.fangJu = null;
					}
				} else if (gameApp.settingsViewData.qGameSetupSetp == Type.QGameSetupStep.JiaMa) {
					if (selectedWJ.zhuangBei.jiaYiMa != null) {
						selectedWJ.zhuangBei.jiaYiMa.belongToWuJiang = null;
						selectedWJ.zhuangBei.jiaYiMa.cpState = Type.CPState.PaiDui;
						selectedWJ.zhuangBei.jiaYiMa = null;
					}
				} else if (gameApp.settingsViewData.qGameSetupSetp == Type.QGameSetupStep.JianMa) {
					if (selectedWJ.zhuangBei.jianYiMa != null) {
						selectedWJ.zhuangBei.jianYiMa.belongToWuJiang = null;
						selectedWJ.zhuangBei.jianYiMa.cpState = Type.CPState.PaiDui;
						selectedWJ.zhuangBei.jianYiMa = null;
					}
				}
				//
				selectedZBImg.setImageDrawable(gameApp.getResources()
						.getDrawable(R.drawable.card_back));
				selectedZBImg.setEnabled(false);
				updateZhuangBeiImg();
			}
		});
	}

	public boolean requestZhuangBeiType(CardPai cp) {
		if (this.gameApp.settingsViewData.qGameSetupSetp == Type.QGameSetupStep.WuQi
				&& cp instanceof WuQiCardPai)
			return true;
		if (this.gameApp.settingsViewData.qGameSetupSetp == Type.QGameSetupStep.FangJu
				&& cp instanceof FangJuCardPai)
			return true;
		if (this.gameApp.settingsViewData.qGameSetupSetp == Type.QGameSetupStep.JiaMa
				&& cp instanceof MaCardPai) {
			MaCardPai maCP = (MaCardPai) cp;
			if (maCP.distance == 1)
				return true;
		}
		if (this.gameApp.settingsViewData.qGameSetupSetp == Type.QGameSetupStep.JianMa
				&& cp instanceof MaCardPai) {
			MaCardPai maCP = (MaCardPai) cp;
			if (maCP.distance == -1)
				return true;
		}
		return false;
	}

	public Object showDialog() {
		super.show();
		try {
			MainLoopHelper.looperRun();
		} catch (RuntimeException e2) {
		}
		return returnValue;
	}

	public void updateZhuangBeiBackGround() {
		for (int i = 0; i < this.zhuangBeiView.length; i++) {
			if (this.zhuangBei[i] == null)
				continue;
			if ((this.selectedZB == null)
					|| (!this.zhuangBei[i].equals(this.selectedZB)))
				this.zhuangBeiView[i].setBackgroundDrawable(this.gameApp
						.getResources().getDrawable(R.drawable.bg_black));
			else
				this.zhuangBeiView[i].setBackgroundDrawable(this.gameApp
						.getResources().getDrawable(R.drawable.bg_green));
		}
	}

	public void updateZhuangBeiImg() {
		// first empty all
		for (int i = 0; i < this.zhuangBei.length; i++)
			this.zhuangBei[i] = null;
		//
		int viewIndex = 0;
		for (int poolIndex = 0; (poolIndex < this.gameApp.gameLogicData.cpHelper.cardPaiPool
				.size())
				&& (viewIndex < this.zhuangBei.length); poolIndex++) {
			CardPai localCardPai = (CardPai) this.gameApp.gameLogicData.cpHelper.cardPaiPool
					.get(poolIndex);
			if ((requestZhuangBeiType(localCardPai))
					&& (localCardPai.belongToWuJiang == null)) {
				this.zhuangBei[viewIndex] = localCardPai;
				this.zhuangBeiView[viewIndex].setImageDrawable(this.gameApp
						.getResources().getDrawable(
								this.zhuangBei[viewIndex].imageNumber));
				this.zhuangBeiView[viewIndex].setVisibility(View.VISIBLE);
				viewIndex++;
			}
		}
		// empty others
		while (viewIndex < this.zhuangBeiView.length) {
			this.zhuangBeiView[viewIndex].setVisibility(View.INVISIBLE);
			viewIndex++;
		}
	}

	private class MyZhuangBeiImageController implements View.OnTouchListener {

		public boolean onTouch(View v, MotionEvent event) {
			CardPai localObject = null;
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				break;
			case MotionEvent.ACTION_UP:
				switch (v.getId()) {
				default:
					break;
				case R.id.qgame_zb_wq:
					gameApp.settingsViewData.qGameSetupSetp = Type.QGameSetupStep.WuQi;
					localObject = selectedWJ.zhuangBei.wuQi;
					break;
				case R.id.qgame_zb_fj:
					gameApp.settingsViewData.qGameSetupSetp = Type.QGameSetupStep.FangJu;
					localObject = selectedWJ.zhuangBei.fangJu;
					break;
				case R.id.qgame_zb_jiama:
					gameApp.settingsViewData.qGameSetupSetp = Type.QGameSetupStep.JiaMa;
					localObject = selectedWJ.zhuangBei.jiaYiMa;
					break;
				case R.id.qgame_zb_jianma:
					gameApp.settingsViewData.qGameSetupSetp = Type.QGameSetupStep.JianMa;
					localObject = selectedWJ.zhuangBei.jianYiMa;
					break;
				}
			}
			//
			selectedZB = null;
			if (localObject == null) {
				selectedZBImg.setImageDrawable(gameApp.getResources()
						.getDrawable(R.drawable.card_back));
				selectedZBImg.setEnabled(false);
			} else {
				selectedZBImg.setImageDrawable(gameApp.getResources()
						.getDrawable((localObject).imageNumber));
				selectedZBImg.setEnabled(true);
			}
			//
			updateZhuangBeiImg();
			updateZhuangBeiBackGround();
			return true;
		}
	}

	private class SelectedZBImageListener implements View.OnClickListener {
		private SelectedZBImageListener() {
		}

		public void onClick(View paramView) {
		}
	}

	private class ZhuangBeiImageListener implements View.OnClickListener {

		public void onClick(View v) {
			int i = 0;
			switch (v.getId()) {
			case R.id.qgame_zb1:
				i = 0;
				break;
			case R.id.qgame_zb2:
				i = 1;
				break;
			case R.id.qgame_zb3:
				i = 2;
				break;
			case R.id.qgame_zb4:
				i = 3;
				break;
			case R.id.qgame_zb5:
				i = 4;
				break;
			case R.id.qgame_zb6:
				i = 5;
				break;
			case R.id.qgame_zb7:
				i = 6;
				break;
			case R.id.qgame_zb8:
				i = 7;
				break;
			case R.id.qgame_zb9:
				i = 8;
				break;
			case R.id.qgame_zb10:
				i = 9;
				break;
			case R.id.qgame_zb11:
				i = 10;
				break;
			case R.id.qgame_zb12:
				i = 11;
				break;
			case R.id.qgame_zb13:
				i = 12;
				break;
			case R.id.qgame_zb14:
				i = 13;
				break;
			case R.id.qgame_zb15:
				i = 14;
				break;
			}
			//
			selectedZB = zhuangBei[i];
			updateZhuangBeiBackGround();
		}
	}
}