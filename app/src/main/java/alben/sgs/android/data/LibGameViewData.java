package alben.sgs.android.data;

import alben.sgs.android.GameApp;
import alben.sgs.android.dialog.MainLoopHelper;
import alben.sgs.android.imageview.MyImageView;
import alben.sgs.android.io.FileUtil;
import alben.sgs.android.mycontroller.MyImageButton;
import alben.sgs.android.thread.LogTask;
import alben.sgs.type.Type;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LibGameViewData {
	public GameApp gameApp;

	public FileUtil fileUtil = null;

	public DisplayMetrics dm = new DisplayMetrics();
	public DisplayData display = new DisplayData();

	public LibGameViewData(GameApp app) {
		this.gameApp = app;
		this.fileUtil = new FileUtil(this.gameApp);
	}

	public MyImageView[] imageWJs = { null, null, null, null, null, null, null,
			null };

	public ImageViewBloods[] imageWJBloods = { new ImageViewBloods(),
			new ImageViewBloods(), new ImageViewBloods(),
			new ImageViewBloods(), new ImageViewBloods(),
			new ImageViewBloods(), new ImageViewBloods(), new ImageViewBloods() };

	// public TextView[] txtWJLostBloods = { null, null, null, null, null, null,
	// null, null };

	public ImageViewPangDing[] imageWJDPs = { new ImageViewPangDing(),
			new ImageViewPangDing(), new ImageViewPangDing(),
			new ImageViewPangDing(), new ImageViewPangDing(),
			new ImageViewPangDing(), new ImageViewPangDing(),
			new ImageViewPangDing() };

	public MyImageView[] imgWJShouPaiNumber = { null, null, null, null, null,
			null, null, null };

	public MyImageView[] imgLianHuans = { null, null, null, null, null, null,
			null, null };

	public MyImageView[] imgFanMians = { null, null, null, null, null, null,
			null, null };

	public ImageViewZhuangBei[] imgZhuangBei = { new ImageViewZhuangBei(),
			new ImageViewZhuangBei(), new ImageViewZhuangBei(),
			new ImageViewZhuangBei(), new ImageViewZhuangBei(),
			new ImageViewZhuangBei(), new ImageViewZhuangBei(),
			new ImageViewZhuangBei() };

	public class ImageViewBloods {
		public MyImageView[] img9Bloods = { null, null, null, null, null, null,
				null, null, null };
	}

	public MyImageView[] imageWJRoles = { null, null, null, null, null, null,
			null, null };

	public class ImageViewPangDing {
		public MyImageView[] imgPds = { null, null, null };
	}

	public class ImageViewZhuangBei {
		public MyImageView imgWuqi;
		public MyImageView imgFangju;
		public MyImageView imgJaYiMa;
		public MyImageView imgJianYiMa;
	}

	public LinearLayout[] linearWJs = { null, null, null, null, null, null,
			null, null, null };
	public LinearLayout wj8LinearArea = null;

	public LinearLayout linearFunctionBtn = null;
	public LinearLayout linearJinengBtn = null;

	public ImageView[] WJ8ShouPaiArrow = { null, null, null, null, null, null,
			null, null };

	public MyImageView[] WJ8ShouPai = { null, null, null, null, null, null,
			null, null };

	// display number of shou pais for wj8
	public int maxShouPaiDisplayForWJ8 = this.WJ8ShouPai.length;

	public ImageView arrowLeft_xx = null;
	public ImageView arrowRight_xx = null;

	public int selectWJIndex = 0;

	public View firstView;

	public MyImageView mInfoImg;
	public TextView mInfoView;
	public String tarInfo = null;

	public MyImageView mChuPai;
	public MyImageView mFangQi;
	public MyImageView mQiPai;
	public MyImageButton mTuoGuan;
	public MyImageButton mStartMatch;

	public MyImageView mJiNengBtn1;
	public MyImageView mJiNengBtn2;
	public MyImageView mJiNengBtn3;
	public MyImageView mJiNengBtn4;

	public TextView mJiNengBtn1Txt;
	public TextView mJiNengBtn2Txt;
	public TextView mJiNengBtn3Txt;
	public TextView mJiNengBtn4Txt;
	
	//image for hole the space
	public MyImageView roleSpace1;//before JiNeng1

	// below for debug
	public MyImageButton mReturn;// for DebugAddShouPaiDialog
	public MyImageButton mRenDe;// for debug
	// debug end

	// 6 line logs
	int curLogLine = 0;
	public String curLog = new String();
	public String[] logs = { new String(), new String(), new String(),
			new String(), new String(), new String() };

	public void appendLog(String s) {

		int ind = this.curLogLine - 1;
		if (ind == -1) {
			ind = this.logs.length - 1;
		}
		if (s.equals(this.logs[ind]))
			return;

		if (this.curLogLine < this.logs.length) {
			this.logs[this.curLogLine++] = s;
			if (this.curLogLine == this.logs.length) {
				this.curLogLine = 0;
			}
		}
	}

	public void reset() {
		// reset log
		for (int i = 0; i < this.logs.length; i++) {
			this.logs[i] = "";
		}
		this.curLogLine = 0;
	}

	public String getLatestLogs() {

		String s = "";

		for (int i = this.curLogLine; i < this.logs.length; i++) {
			s += this.logs[i];
		}

		for (int i = 0; i < this.curLogLine; i++) {
			s += this.logs[i];
		}
		return s;
	}

	//
	public boolean logLoop = false;
	public int delayMillSeconds = 800;

	public void logInfo(String s, Type.logDelay delay) {

		if (s.trim().length() == 0 || s.trim().equals("\n"))
			return;

		// log this to file
		this.fileUtil.addContent(s.trim() + "\n");

		this.appendLog(s.trim() + "\n");
		this.mInfoView.setText(this.getLatestLogs());
		this.mInfoView.setTextSize(24);

		if (delay == Type.logDelay.NoDelay)
			return;

		// by default, delay==Type.logDelay.Delay
		int delayMS = this.gameApp.settingsViewData.delayMillionSeconds;

		if (delay == Type.logDelay.DoubleDelay)
			delayMS = delayMS * 2;

		if (delay == Type.logDelay.HalfDelay)
			delayMS = delayMS / 2;

		if (this.gameApp.gameLogicData.myWuJiang != null
				&& this.gameApp.gameLogicData.myWuJiang.tuoGuan) {
			delayMS = 200;
		}

		try {
			LogTask lt = new LogTask(delayMS);
			lt.execute("");
			MainLoopHelper.looperRun();
		} catch (RuntimeException e2) {
		}
	}
}
