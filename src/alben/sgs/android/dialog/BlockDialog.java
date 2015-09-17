package alben.sgs.android.dialog;

import alben.sgs.android.GameApp;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Looper;

public class BlockDialog extends Dialog {
	// 真正阻塞式对话框,主进程只能等待对话框完成才能继续
	public GameApp gameApp;
	public Object returnValue;

	public BlockDialog(Context context, GameApp app) {
		super(context);
		this.gameApp = app;
		setOwnerActivity((Activity) app.gameActivityContext);
		onCreate();
	}

	/** Called when the activity is first created. */
	public void onCreate() {
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
}
