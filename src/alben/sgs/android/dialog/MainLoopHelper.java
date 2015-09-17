package alben.sgs.android.dialog;

import android.os.Looper;

public class MainLoopHelper {
	public static void looperRun() {
		try {
			Looper.getMainLooper().loop();
		} catch (Exception e) {

		}
	}
}
