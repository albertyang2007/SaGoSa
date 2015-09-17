package alben.sgs.android.dialog;

import alben.sgs.android.GameApp;
import alben.sgs.android.R;
import alben.sgs.cardpai.CardPai;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class WuGuFengDengDialog extends Dialog {
	public GameApp gameApp;
	public Object returnValue;

	public TextView genInfo = null;
	public TextView wjInfo[] = { null, null, null, null, null, null, null, null };
	public ImageView cpInfo[] = { null, null, null, null, null, null, null,
			null };
	public CardPai selectedCP = null;

	public WuGuFengDengDialog(Context context, GameApp app) {

		super(context);
		this.gameApp = app;
		setOwnerActivity((Activity) app.gameActivityContext);
		onCreate();
	}

	private void endDialog(int result) {
		dismiss();
		if (result == 1)
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

	public Object showNonBlockDialog() {
		findViewById(R.id.wgfd_okBtn).setVisibility(View.INVISIBLE);
		findViewById(R.id.wgfd_okBtn).setEnabled(false);
		super.show();
		return returnValue;
	}

	public void endNonBlockDialog() {
		endDialog(0);
	}

	public void updateLatestView() {
		int index = 0;
		for (; index < this.cpInfo.length; index++) {
			CardPai cp = this.gameApp.selectCPData.CPForSelect[index];
			if (cp != null) {
				this.cpInfo[index].setImageDrawable(this.gameApp.getResources()
						.getDrawable(cp.imageNumber));
				this.cpInfo[index].setVisibility(View.VISIBLE);
				if (cp.belongToWuJiang != null) {
					this.wjInfo[index].setText(cp.belongToWuJiang.dispName);
					this.wjInfo[index].setVisibility(View.VISIBLE);
				}
			} else {
				break;
			}
		}
		// empty others cp
		for (; index < this.cpInfo.length; index++) {
			this.cpInfo[index].setVisibility(View.INVISIBLE);
			this.wjInfo[index].setVisibility(View.INVISIBLE);
		}
	}

	public void onCreate() {
		setContentView(R.layout.wugufengdeng);

		this.genInfo = (TextView) findViewById(R.id.wgfd_info);

		this.wjInfo[0] = (TextView) findViewById(R.id.wgfd_wj1);
		this.wjInfo[1] = (TextView) findViewById(R.id.wgfd_wj2);
		this.wjInfo[2] = (TextView) findViewById(R.id.wgfd_wj3);
		this.wjInfo[3] = (TextView) findViewById(R.id.wgfd_wj4);
		this.wjInfo[4] = (TextView) findViewById(R.id.wgfd_wj5);
		this.wjInfo[5] = (TextView) findViewById(R.id.wgfd_wj6);
		this.wjInfo[6] = (TextView) findViewById(R.id.wgfd_wj7);
		this.wjInfo[7] = (TextView) findViewById(R.id.wgfd_wj8);

		this.cpInfo[0] = (ImageView) findViewById(R.id.wgfd_cp1);
		this.cpInfo[1] = (ImageView) findViewById(R.id.wgfd_cp2);
		this.cpInfo[2] = (ImageView) findViewById(R.id.wgfd_cp3);
		this.cpInfo[3] = (ImageView) findViewById(R.id.wgfd_cp4);
		this.cpInfo[4] = (ImageView) findViewById(R.id.wgfd_cp5);
		this.cpInfo[5] = (ImageView) findViewById(R.id.wgfd_cp6);
		this.cpInfo[6] = (ImageView) findViewById(R.id.wgfd_cp7);
		this.cpInfo[7] = (ImageView) findViewById(R.id.wgfd_cp8);

		this.updateLatestView();

		for (int i = 0; i < this.cpInfo.length; i++) {
			this.cpInfo[i].setOnClickListener(new CardPaiImageListener());
		}

		findViewById(R.id.wgfd_okBtn).setOnClickListener(
				new android.view.View.OnClickListener() {
					@Override
					public void onClick(View paramView) {
						if (selectedCP == null) {
							genInfo.setText("请你选择一张卡牌");
						} else {
							gameApp.selectCPData.selectedCP1 = selectedCP;
							endDialog(1);
						}
					}
				});
	}

	private class CardPaiImageListener implements
			android.view.View.OnClickListener {
		public void onClick(View v) {
			int index = 0;
			switch (v.getId()) {
			case R.id.wgfd_cp1: {
				index = 0;
				break;
			}
			case R.id.wgfd_cp2: {
				index = 1;
				break;
			}
			case R.id.wgfd_cp3: {
				index = 2;
				break;
			}
			case R.id.wgfd_cp4: {
				index = 3;
				break;
			}
			case R.id.wgfd_cp5: {
				index = 4;
				break;
			}
			case R.id.wgfd_cp6: {
				index = 5;
				break;
			}
			case R.id.wgfd_cp7: {
				index = 6;
				break;
			}
			case R.id.wgfd_cp8: {
				index = 7;
				break;
			}
			}
			if (gameApp.selectCPData.CPForSelect[index] != null) {
				if (gameApp.selectCPData.CPForSelect[index].belongToWuJiang == null) {
					selectedCP = gameApp.selectCPData.CPForSelect[index];

					for (int i = 0; i < cpInfo.length; i++) {
						if (i == index)
							cpInfo[i].setBackgroundDrawable(gameApp
									.getResources().getDrawable(
											R.drawable.bg_green));
						else
							cpInfo[i].setBackgroundDrawable(gameApp
									.getResources().getDrawable(
											R.drawable.bg_black));
					}
				} else {
					genInfo.setText("该卡牌已经被选择");
				}
			}
		}
	}
}
