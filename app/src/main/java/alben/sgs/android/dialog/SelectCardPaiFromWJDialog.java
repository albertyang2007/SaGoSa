package alben.sgs.android.dialog;

import alben.sgs.android.GameApp;
import alben.sgs.android.R;
import alben.sgs.android.imageview.MyImageView;
import alben.sgs.cardpai.CardPai;
import alben.sgs.type.WuJiangCardPaiData;
import alben.sgs.wujiang.WuJiang;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SelectCardPaiFromWJDialog extends Dialog {

	public GameApp gameApp;
	public Object returnValue;

	public WuJiang tarWJ = null;
	public TextView tx = null;
	public MyImageView[] cpiv = { null, null, null, null, null, null, null,
			null, null, null, null };
	public CardPai[] cp = { null, null, null, null, null, null, null, null,
			null, null, null };

	public WuJiangCardPaiData wjCPData = null;

	public SelectCardPaiFromWJDialog(Context context, GameApp app,
			WuJiangCardPaiData wjCPD) {
		super(context);
		this.gameApp = app;
		this.wjCPData = wjCPD;
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

	public void onCreate() {

		setContentView(R.layout.wj_cp_details);

		this.tarWJ = this.gameApp.wjDetailsViewData.selectedWJ;

		tx = (TextView) findViewById(R.id.wjd_info);
		if (gameApp.wjDetailsViewData.selectedCardN1Or2)
			tx.setText("请你选择1到2个卡牌");
		else if (gameApp.wjDetailsViewData.selectedCardAtLeast1)
			tx.setText("请选择至少1个卡牌");
		else
			tx.setText("请你选择" + gameApp.wjDetailsViewData.selectedCardNumber
					+ "个卡牌");

		findViewById(R.id.wjd_okBtn).setOnClickListener(
				new android.view.View.OnClickListener() {
					@Override
					public void onClick(View paramView) {

						boolean match = false;

						// if case for some error
						if (cp[0] == null) {
							tx.setText("Error:没有卡牌可选");
							match = true;
						}

						if (gameApp.wjDetailsViewData.selectedCardAtLeast1
								&& gameApp.wjDetailsViewData.selectedCardPaiList
										.size() > 0) {
							match = true;
						}

						if (gameApp.wjDetailsViewData.selectedCardN1Or2) {
							if (gameApp.wjDetailsViewData.selectedCardPai1 != null
									|| gameApp.wjDetailsViewData.selectedCardPai2 != null) {
								match = true;
							}
						}

						if (gameApp.wjDetailsViewData.selectedCardNumber == 1) {
							if (gameApp.wjDetailsViewData.selectedCardPai1 != null) {
								match = true;
							}
						} else if (gameApp.wjDetailsViewData.selectedCardNumber == 2) {
							if (gameApp.wjDetailsViewData.selectedCardPai1 != null
									&& gameApp.wjDetailsViewData.selectedCardPai2 != null) {

								if (gameApp.wjDetailsViewData.requestTwoCPSameColor) {
									if (gameApp.wjDetailsViewData.selectedCardPai1.clas == gameApp.wjDetailsViewData.selectedCardPai2.clas) {
										match = true;
									} else {
										tx.setText("请选择2个相同花色的牌");
										return;
									}
								} else {
									match = true;
								}
							}
						}

						if (match) {
							endDialog(1);
						} else {
							if (gameApp.wjDetailsViewData.selectedCardN1Or2)
								tx.setText("请选择1到2个卡牌");
							else if (gameApp.wjDetailsViewData.selectedCardAtLeast1)
								tx.setText("请选择至少1个卡牌");
							else
								tx
										.setText("请选择"
												+ gameApp.wjDetailsViewData.selectedCardNumber
												+ "个卡牌");
						}
					}
				});

		MyImageView wj = (MyImageView) findViewById(R.id.wjd_selected_WuJiang);
		cpiv[0] = (MyImageView) findViewById(R.id.wjd_cp1);
		cpiv[1] = (MyImageView) findViewById(R.id.wjd_cp2);
		cpiv[2] = (MyImageView) findViewById(R.id.wjd_cp3);
		cpiv[3] = (MyImageView) findViewById(R.id.wjd_cp4);
		cpiv[4] = (MyImageView) findViewById(R.id.wjd_cp5);
		cpiv[5] = (MyImageView) findViewById(R.id.wjd_cp6);
		cpiv[6] = (MyImageView) findViewById(R.id.wjd_cp7);
		cpiv[7] = (MyImageView) findViewById(R.id.wjd_cp8);
		cpiv[8] = (MyImageView) findViewById(R.id.wjd_cp9);
		cpiv[9] = (MyImageView) findViewById(R.id.wjd_cp10);
		cpiv[10] = (MyImageView) findViewById(R.id.wjd_cp11);

		for (int i = 0; i < this.cpiv.length; i++)
			cpiv[i].setOnClickListener(new CardPaiImageListener());

		wj.setImageDrawable(this.tarWJ.imageNumber);

		int index = 0;
		// first add panding pai
		for (; index < this.wjCPData.panDingPai.size(); index++) {
			this.cp[index] = this.wjCPData.panDingPai.get(index);
			this.cp[index].selectedByClick = false;
			this.cpiv[index].setImageDrawable(this.cp[index].imageNumber);
		}
		// second add zhuangbei pai
		if (this.wjCPData.zhuangBei.wuQi != null) {
			this.cp[index] = this.wjCPData.zhuangBei.wuQi;
			this.cp[index].selectedByClick = false;
			this.cpiv[index].setImageDrawable(this.cp[index].imageNumber);
			index++;
		}

		if (this.wjCPData.zhuangBei.fangJu != null) {
			this.cp[index] = this.wjCPData.zhuangBei.fangJu;
			this.cp[index].selectedByClick = false;
			this.cpiv[index].setImageDrawable(this.cp[index].imageNumber);
			index++;
		}

		if (this.wjCPData.zhuangBei.jiaYiMa != null) {
			this.cp[index] = this.wjCPData.zhuangBei.jiaYiMa;
			this.cp[index].selectedByClick = false;
			this.cpiv[index].setImageDrawable(this.cp[index].imageNumber);
			index++;
		}

		if (this.wjCPData.zhuangBei.jianYiMa != null) {
			this.cp[index] = this.wjCPData.zhuangBei.jianYiMa;
			this.cp[index].selectedByClick = false;
			this.cpiv[index].setImageDrawable(this.cp[index].imageNumber);
			index++;
		}
		// then add shou pai
		{
			int spIndex = 0;
			for (; ((index < this.cp.length) && (spIndex < this.wjCPData.shouPai
					.size()));) {
				this.cp[index] = this.wjCPData.shouPai.get(spIndex++);
				this.cp[index].selectedByClick = false;
				if (this.gameApp.wjDetailsViewData.canViewShouPai) {
					this.cpiv[index]
							.setImageDrawable(this.cp[index].imageNumber);
				} else {
					this.cpiv[index].setImageDrawable(R.drawable.card_back);
				}
				// next spImage
				index++;
			}
		}

		// other set card pai back and set invisible
		for (int i = index; i < this.cpiv.length; i++) {
			this.cpiv[index].setImageDrawable(R.drawable.card_back);
			this.cpiv[index].setVisibility(View.INVISIBLE);
			index++;
		}

	}

	public class CardPaiImageListener implements
			android.view.View.OnClickListener {
		public void onClick(View v) {
			int index = 0;
			switch (v.getId()) {
			case R.id.wjd_cp1: {
				index = 0;
				break;
			}
			case R.id.wjd_cp2: {
				index = 1;
				break;
			}
			case R.id.wjd_cp3: {
				index = 2;
				break;
			}
			case R.id.wjd_cp4: {
				index = 3;
				break;
			}
			case R.id.wjd_cp5: {
				index = 4;
				break;
			}
			case R.id.wjd_cp6: {
				index = 5;
				break;
			}
			case R.id.wjd_cp7: {
				index = 6;
				break;
			}
			case R.id.wjd_cp8: {
				index = 7;
				break;
			}
			case R.id.wjd_cp9: {
				index = 8;
				break;
			}
			case R.id.wjd_cp10: {
				index = 9;
				break;
			}
			case R.id.wjd_cp11: {
				index = 10;
				break;
			}
			}

			CardPai clickedCp = cp[index];

			if (clickedCp == null) {
				tx.setText("Error: 所选卡牌为空");
				return;
			}

			if (!clickedCp.selectedByClick) {
				clickedCp.selectedByClick = true;
				cpiv[index].setBackgroundDrawable(gameApp.getResources()
						.getDrawable(R.drawable.bg_green));
			} else {
				clickedCp.selectedByClick = false;
				cpiv[index].setBackgroundDrawable(gameApp.getResources()
						.getDrawable(R.drawable.bg_black));
			}

			if (clickedCp.selectedByClick) {
				// set value
				if (gameApp.wjDetailsViewData.selectedCardNumber == 1) {
					gameApp.wjDetailsViewData.selectedCardPai1 = clickedCp;
				} else if (gameApp.wjDetailsViewData.selectedCardNumber == 2) {
					if (gameApp.wjDetailsViewData.selectedCardPai1 == null) {
						gameApp.wjDetailsViewData.selectedCardPai1 = clickedCp;
					} else if (gameApp.wjDetailsViewData.selectedCardPai2 == null) {
						gameApp.wjDetailsViewData.selectedCardPai2 = clickedCp;
					}
				} else if (gameApp.wjDetailsViewData.selectedCardAtLeast1) {
					if (!gameApp.wjDetailsViewData.selectedCardPaiList
							.contains(clickedCp))
						gameApp.wjDetailsViewData.selectedCardPaiList
								.add(clickedCp);
				}
			} else {
				// unclick it
				// de-select if had been selected
				if (gameApp.wjDetailsViewData.selectedCardPai1 != null
						&& gameApp.wjDetailsViewData.selectedCardPai1
								.equals(clickedCp)) {
					gameApp.wjDetailsViewData.selectedCardPai1 = null;
				}
				if (gameApp.wjDetailsViewData.selectedCardPai2 != null
						&& gameApp.wjDetailsViewData.selectedCardPai2
								.equals(clickedCp)) {
					gameApp.wjDetailsViewData.selectedCardPai2 = null;
				}
				if (gameApp.wjDetailsViewData.selectedCardAtLeast1) {
					if (gameApp.wjDetailsViewData.selectedCardPaiList
							.contains(clickedCp))
						gameApp.wjDetailsViewData.selectedCardPaiList
								.remove(clickedCp);
				}
			}
		}
	}
}
