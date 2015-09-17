package alben.sgs.android.dialog;

import alben.sgs.android.GameApp;
import alben.sgs.android.R;
import alben.sgs.type.Type;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SelectHuaShiDialog extends Dialog {

	public GameApp gameApp;
	public Object returnValue;

	public TextView infov = null;
	public ImageView[] hsImgs = { null, null, null, null };
	public Type.CardPaiClass selectHS = Type.CardPaiClass.nil;

	public SelectHuaShiDialog(Context context, GameApp app) {
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

	public void onCreate() {
		setContentView(R.layout.fanjian);

		infov = (TextView) findViewById(R.id.fj_hs_info);
		infov.setText("周瑜反间你,请选择花式");
		findViewById(R.id.fj_hs_info).setEnabled(true);

		findViewById(R.id.fj_okBtn).setOnClickListener(
				new android.view.View.OnClickListener() {
					@Override
					public void onClick(View paramView) {
						if (selectHS != Type.CardPaiClass.nil) {
							endDialog(1);
						}
					}
				});

		hsImgs[0] = (ImageView) findViewById(R.id.fj_fangpian);
		hsImgs[1] = (ImageView) findViewById(R.id.fj_hongtao);
		hsImgs[2] = (ImageView) findViewById(R.id.fj_meihua);
		hsImgs[3] = (ImageView) findViewById(R.id.fj_heitao);

		for (int i = 0; i < hsImgs.length; i++) {
			hsImgs[i].setOnClickListener(new HuaShiImageListener());
		}
	}

	private class HuaShiImageListener implements
			android.view.View.OnClickListener {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.fj_fangpian: {
				selectHS = Type.CardPaiClass.FangPian;
				this.resetAllHuaShiBGExceptIndex(0);
				break;
			}
			case R.id.fj_hongtao: {
				selectHS = Type.CardPaiClass.HongTao;
				this.resetAllHuaShiBGExceptIndex(1);
				break;
			}
			case R.id.fj_meihua: {
				selectHS = Type.CardPaiClass.MeiHua;
				this.resetAllHuaShiBGExceptIndex(2);
				break;
			}
			case R.id.fj_heitao: {
				selectHS = Type.CardPaiClass.HeiTao;
				this.resetAllHuaShiBGExceptIndex(3);
				break;
			}
			}
		}

		public void resetAllHuaShiBGExceptIndex(int index) {
			for (int i = 0; i < hsImgs.length; i++) {
				hsImgs[i].setBackgroundDrawable(gameApp.getResources()
						.getDrawable(R.drawable.bg_black));
			}

			// set this to green
			hsImgs[index].setBackgroundDrawable(gameApp.getResources()
					.getDrawable(R.drawable.bg_green));
		}
	}
}
