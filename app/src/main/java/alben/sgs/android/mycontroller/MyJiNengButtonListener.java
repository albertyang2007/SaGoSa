package alben.sgs.android.mycontroller;

import alben.sgs.android.GameApp;
import android.view.View;
import android.view.View.OnClickListener;

public class MyJiNengButtonListener implements OnClickListener {
	public GameApp gameApp;

	public MyJiNengButtonListener(GameApp app) {
		this.gameApp = app;
	}

	public void onClick(View v) {
		this.gameApp.gameLogicData.myWuJiang.handleJiNengBtnEvent(v.getId());
	}
}
