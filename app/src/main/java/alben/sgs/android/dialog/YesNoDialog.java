package alben.sgs.android.dialog;

import alben.sgs.android.GameApp;
import alben.sgs.android.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class YesNoDialog extends Dialog {
	public GameApp gameApp;
	public Object returnValue;

	public TextView textInfo = null;

	public YesNoDialog(Context context, GameApp app) {

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
		setContentView(R.layout.yesno_dlg);

		textInfo = (TextView) findViewById(R.id.yn_info);
		textInfo.setText(this.gameApp.ynData.genInfo);

		Button okBtn = (Button) findViewById(R.id.yn_okBtn);
		okBtn.setText(this.gameApp.ynData.okTxt);
		okBtn.setOnClickListener(new ButtonListener());

		Button cancelBtn = (Button) findViewById(R.id.yn_cancelBtn);
		cancelBtn.setText(this.gameApp.ynData.cancelTxt);
		cancelBtn.setOnClickListener(new ButtonListener());
	}

	public class ButtonListener implements android.view.View.OnClickListener {
		public void onClick(View v) {
			if (v.getId() == R.id.yn_okBtn) {
				gameApp.ynData.result = true;
			} else {
				gameApp.ynData.result = false;
			}
			endDialog(1);
		}
	}

}
