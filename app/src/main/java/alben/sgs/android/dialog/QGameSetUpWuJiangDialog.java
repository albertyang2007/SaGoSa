package alben.sgs.android.dialog;

import alben.sgs.android.GameApp;
import alben.sgs.android.R;
import alben.sgs.android.imageview.MyImageView;
import alben.sgs.android.mycontroller.MyImageButton;
import alben.sgs.type.Type;
import alben.sgs.wujiang.WuJiang;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class QGameSetUpWuJiangDialog extends Dialog {
	public GameApp gameApp;
	public Object returnValue;
	public WuJiang selectedWJ = null;
	public WuJiang[] wuJiang = new WuJiang[20];
	public MyImageView[] wuJiangView = new MyImageView[20];

	public QGameSetUpWuJiangDialog(Context paramContext, GameApp paramGameApp) {
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
		setContentView(R.layout.qgame_setup_wj);

		this.selectedWJ = this.gameApp.wjDetailsViewData.selectedWJ;

		((MyImageButton) findViewById(R.id.invisible_btn2))
				.setVisibility(View.INVISIBLE);
		((MyImageButton) findViewById(R.id.invisible_btn3))
				.setVisibility(View.INVISIBLE);

		((ImageView) findViewById(R.id.wj_country_ok))
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
							if (selectedWJ == null)
								break;

							selectedWJ.setGame(gameApp);
							gameApp.selectWJViewData.selectedWJ1 = selectedWJ;
							endDialog(1);
						}
						return true;
					}
				});
		//
		((ImageView) findViewById(R.id.wj_country_shu))
				.setOnTouchListener(new MyCountryImageController());
		((ImageView) findViewById(R.id.wj_country_wu))
				.setOnTouchListener(new MyCountryImageController());
		((ImageView) findViewById(R.id.wj_country_wei))
				.setOnTouchListener(new MyCountryImageController());
		((ImageView) findViewById(R.id.wj_country_qun))
				.setOnTouchListener(new MyCountryImageController());
		((ImageView) findViewById(R.id.wj_country_shen))
				.setOnTouchListener(new MyCountryImageController());

		this.wuJiangView[0] = ((MyImageView) findViewById(R.id.QGameWuJiang1));
		this.wuJiangView[1] = ((MyImageView) findViewById(R.id.QGameWuJiang2));
		this.wuJiangView[2] = ((MyImageView) findViewById(R.id.QGameWuJiang3));
		this.wuJiangView[3] = ((MyImageView) findViewById(R.id.QGameWuJiang4));
		this.wuJiangView[4] = ((MyImageView) findViewById(R.id.QGameWuJiang5));
		this.wuJiangView[5] = ((MyImageView) findViewById(R.id.QGameWuJiang6));
		this.wuJiangView[6] = ((MyImageView) findViewById(R.id.QGameWuJiang7));
		this.wuJiangView[7] = ((MyImageView) findViewById(R.id.QGameWuJiang8));
		this.wuJiangView[8] = ((MyImageView) findViewById(R.id.QGameWuJiang9));
		this.wuJiangView[9] = ((MyImageView) findViewById(R.id.QGameWuJiang10));
		this.wuJiangView[10] = ((MyImageView) findViewById(R.id.QGameWuJiang11));
		this.wuJiangView[11] = ((MyImageView) findViewById(R.id.QGameWuJiang12));
		this.wuJiangView[12] = ((MyImageView) findViewById(R.id.QGameWuJiang13));
		this.wuJiangView[13] = ((MyImageView) findViewById(R.id.QGameWuJiang14));
		this.wuJiangView[14] = ((MyImageView) findViewById(R.id.QGameWuJiang15));
		this.wuJiangView[15] = ((MyImageView) findViewById(R.id.QGameWuJiang16));
		this.wuJiangView[16] = ((MyImageView) findViewById(R.id.QGameWuJiang17));
		this.wuJiangView[17] = ((MyImageView) findViewById(R.id.QGameWuJiang18));
		this.wuJiangView[18] = ((MyImageView) findViewById(R.id.QGameWuJiang19));
		this.wuJiangView[19] = ((MyImageView) findViewById(R.id.QGameWuJiang20));

		for (int i = 0; i < this.wuJiangView.length; i++)
			this.wuJiangView[i].setOnClickListener(new WuJiangImageListener());
		//
		this.gameApp.settingsViewData.qGameCountry = Type.Country.Shu;
		updateWuJiangImg();
	}

	public Object showDialog() {
		super.show();
		try {
			MainLoopHelper.looperRun();
		} catch (RuntimeException e2) {
		}
		return returnValue;
	}

	public void updateWuJiangBackGround() {
		for (int i = 0; i < this.wuJiangView.length; i++) {
			if (this.wuJiang[i] == null)
				continue;
			if (!this.wuJiang[i].equals(this.selectedWJ))
				this.wuJiangView[i].setBackgroundDrawable(this.gameApp
						.getResources().getDrawable(R.drawable.bg_black));
			else
				this.wuJiangView[i].setBackgroundDrawable(this.gameApp
						.getResources().getDrawable(R.drawable.bg_green));
		}
	}

	public void updateWuJiangImg() {
		// empty it
		for (int i = 0; i < this.wuJiang.length; i++)
			this.wuJiang[i] = null;
		// set it
		int viewIndex = 0;
		for (int poolIndex = 0; (poolIndex < this.gameApp.gameLogicData.wjHelper.wuJiangPool
				.size()) && (viewIndex < this.wuJiang.length); poolIndex++) {
			WuJiang localWuJiang = (WuJiang) this.gameApp.gameLogicData.wjHelper.wuJiangPool
					.get(poolIndex);
			if (localWuJiang.country == this.gameApp.settingsViewData.qGameCountry
					&& !localWuJiang.allocated) {
				this.wuJiang[viewIndex] = localWuJiang;
				this.wuJiangView[viewIndex]
						.setImageDrawable(this.wuJiang[viewIndex].imageNumber);
				this.wuJiangView[viewIndex].setVisibility(View.VISIBLE);
				viewIndex++;
			}
		}
		// set other invisible
		while (viewIndex < this.wuJiangView.length) {
			this.wuJiangView[viewIndex].setVisibility(View.INVISIBLE);
			viewIndex++;
		}
	}

	private class MyCountryImageController implements View.OnTouchListener {
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				break;
			case MotionEvent.ACTION_DOWN:
				switch (v.getId()) {
				default:
					break;
				case R.id.wj_country_shu:
					gameApp.settingsViewData.qGameCountry = Type.Country.Shu;
					break;
				case R.id.wj_country_wei:
					gameApp.settingsViewData.qGameCountry = Type.Country.Wei;
					break;
				case R.id.wj_country_wu:
					gameApp.settingsViewData.qGameCountry = Type.Country.Wu;
					break;
				case R.id.wj_country_qun:
					gameApp.settingsViewData.qGameCountry = Type.Country.Qun;
					break;
				case R.id.wj_country_shen:
					gameApp.settingsViewData.qGameCountry = Type.Country.Shen;
				}
				break;
			}
			selectedWJ = null;
			updateWuJiangImg();
			updateWuJiangBackGround();
			return true;
		}
	}

	private class WuJiangImageListener implements View.OnClickListener {

		public void onClick(View v) {
			int index = 0;
			switch (v.getId()) {
			case R.id.QGameWuJiang1:
				index = 0;
				break;
			case R.id.QGameWuJiang2:
				index = 1;
				break;
			case R.id.QGameWuJiang3:
				index = 2;
				break;
			case R.id.QGameWuJiang4:
				index = 3;
				break;
			case R.id.QGameWuJiang5:
				index = 4;
				break;
			case R.id.QGameWuJiang6:
				index = 5;
				break;
			case R.id.QGameWuJiang7:
				index = 6;
				break;
			case R.id.QGameWuJiang8:
				index = 7;
				break;
			case R.id.QGameWuJiang9:
				index = 8;
				break;
			case R.id.QGameWuJiang10:
				index = 9;
				break;
			case R.id.QGameWuJiang11:
				index = 10;
				break;
			case R.id.QGameWuJiang12:
				index = 11;
				break;
			case R.id.QGameWuJiang13:
				index = 12;
				break;
			case R.id.QGameWuJiang14:
				index = 13;
				break;
			case R.id.QGameWuJiang15:
				index = 14;
				break;
			case R.id.QGameWuJiang16:
				index = 15;
				break;
			case R.id.QGameWuJiang17:
				index = 16;
				break;
			case R.id.QGameWuJiang18:
				index = 17;
				break;
			case R.id.QGameWuJiang19:
				index = 18;
				break;
			case R.id.QGameWuJiang20:
				index = 19;
			}
			selectedWJ = wuJiang[index];
			updateWuJiangBackGround();
		}
	}
}