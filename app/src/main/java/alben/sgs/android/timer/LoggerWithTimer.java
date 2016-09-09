package alben.sgs.android.timer;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class LoggerWithTimer extends TimerTask {
	private Timer timer = null;

	public void setTimer(Timer t) {
		this.timer = t;
	}

	public void run() {
		try {
			Message message = new Message();
			message.what = 1;
			handler.sendMessage(message);
		} catch (Exception e2) {
		}
	}

	private Handler handler = new Handler() {
		// 定义处理信息的方法
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				// 处理代码
				timer.cancel();
				Looper.getMainLooper().quit();
				break;
			}
			super.handleMessage(msg);
		}
	};;
}
