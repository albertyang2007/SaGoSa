package alben.sgs.android.dialog;

import alben.sgs.android.GameApp;
import alben.sgs.android.R;
import alben.sgs.android.mycontroller.MyImageButton;
import alben.sgs.type.Type;
import alben.sgs.type.UpdateWJViewData;
import alben.sgs.wujiang.WuJiang;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class QGameSetUpBloodDialog extends Dialog {
	public int blood = 0;
	public ImageView[] bloodBtn = new ImageView[9];
	public GameApp gameApp;
	public Object returnValue;
	public WuJiang selectedWJ = null;

	public QGameSetUpBloodDialog(Context paramContext, GameApp paramGameApp) {
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
		setContentView(R.layout.qgame_setup_blood);

		this.selectedWJ = this.gameApp.wjDetailsViewData.selectedWJ;

		((MyImageButton) findViewById(R.id.invisible_btn1))
				.setVisibility(View.INVISIBLE);
		((MyImageButton) findViewById(R.id.invisible_btn2))
				.setVisibility(View.INVISIBLE);
		((MyImageButton) findViewById(R.id.invisible_btn3))
				.setVisibility(View.INVISIBLE);

		this.bloodBtn[0] = ((ImageView) findViewById(R.id.qgame_blood_1));
		this.bloodBtn[0].setOnTouchListener(new QGameSetUpBloodListener());
		this.bloodBtn[1] = ((ImageView) findViewById(R.id.qgame_blood_2));
		this.bloodBtn[1].setOnTouchListener(new QGameSetUpBloodListener());
		this.bloodBtn[2] = ((ImageView) findViewById(R.id.qgame_blood_3));
		this.bloodBtn[2].setOnTouchListener(new QGameSetUpBloodListener());
		this.bloodBtn[3] = ((ImageView) findViewById(R.id.qgame_blood_4));
		this.bloodBtn[3].setOnTouchListener(new QGameSetUpBloodListener());
		this.bloodBtn[4] = ((ImageView) findViewById(R.id.qgame_blood_5));
		this.bloodBtn[4].setOnTouchListener(new QGameSetUpBloodListener());
		this.bloodBtn[5] = ((ImageView) findViewById(R.id.qgame_blood_6));
		this.bloodBtn[5].setOnTouchListener(new QGameSetUpBloodListener());
		this.bloodBtn[6] = ((ImageView) findViewById(R.id.qgame_blood_7));
		this.bloodBtn[6].setOnTouchListener(new QGameSetUpBloodListener());
		this.bloodBtn[7] = ((ImageView) findViewById(R.id.qgame_blood_8));
		this.bloodBtn[7].setOnTouchListener(new QGameSetUpBloodListener());
		this.bloodBtn[8] = ((ImageView) findViewById(R.id.qgame_blood_9));
		this.bloodBtn[8].setOnTouchListener(new QGameSetUpBloodListener());

		((ImageView) findViewById(R.id.settings_ok))
				.setOnTouchListener(new View.OnTouchListener() {
					public boolean onTouch(View v, MotionEvent event) {
						switch (event.getAction()) {
						case MotionEvent.ACTION_DOWN:
							v.setBackgroundDrawable(v.getResources()
									.getDrawable(R.drawable.btn_long_ok));
							break;
						case MotionEvent.ACTION_UP:
							v.setBackgroundDrawable(v.getResources()
									.getDrawable(R.drawable.btn_long_ok));
							if (blood == 0)
								break;
							// check
							if (selectedWJ.role == Type.Role.ZhuGong) {
								if ((blood > 1 + selectedWJ.getOrigMaxBlood()))
									break;
							} else {
								// non zhuGong
								if (blood > selectedWJ.getOrigMaxBlood()) {
									break;
								}
							}

							selectedWJ.blood = blood;
							UpdateWJViewData item = new UpdateWJViewData();
							item.updateBlood = true;
							gameApp.gameLogicData.wjHelper
									.updateWuJiangToLibGameView(selectedWJ,
											item);
							endDialog(1);
						}
						return true;
					}
				});
	}

	public Object showDialog() {
		super.show();
		try {
			MainLoopHelper.looperRun();
		} catch (RuntimeException e2) {
		}
		return returnValue;
	}

	private class QGameSetUpBloodListener implements View.OnTouchListener {
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				break;
			case MotionEvent.ACTION_UP:
				switch (v.getId()) {
				default:
					break;
				case R.id.qgame_blood_1:
					QGameSetUpBloodDialog.this.blood = 1;
					break;
				case R.id.qgame_blood_2:
					QGameSetUpBloodDialog.this.blood = 2;
					break;
				case R.id.qgame_blood_3:
					QGameSetUpBloodDialog.this.blood = 3;
					break;
				case R.id.qgame_blood_4:
					QGameSetUpBloodDialog.this.blood = 4;
					break;
				case R.id.qgame_blood_5:
					QGameSetUpBloodDialog.this.blood = 5;
					break;
				case R.id.qgame_blood_6:
					QGameSetUpBloodDialog.this.blood = 6;
					break;
				case R.id.qgame_blood_7:
					QGameSetUpBloodDialog.this.blood = 7;
					break;
				case R.id.qgame_blood_8:
					QGameSetUpBloodDialog.this.blood = 8;
					break;
				case R.id.qgame_blood_9:
					QGameSetUpBloodDialog.this.blood = 9;
					break;
				}
				break;
			}

			//
			if (blood != 0)
				for (int i = 0; i < bloodBtn.length; i++) {
					if (bloodBtn[i] == null)
						continue;
					if (i != -1 + blood)
						bloodBtn[i].setBackgroundDrawable(gameApp
								.getResources()
								.getDrawable(R.drawable.bg_black));
					else
						bloodBtn[i].setBackgroundDrawable(gameApp
								.getResources()
								.getDrawable(R.drawable.bg_green));
				}
			return true;
		}
	}
}