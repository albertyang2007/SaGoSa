package alben.sgs.android.mycontroller;

import alben.sgs.android.GameApp;
import alben.sgs.android.R;
import android.view.View;
import android.view.View.OnClickListener;

public class MyJiNengButtonController implements OnClickListener {

	public GameApp gameApp;

	public MyJiNengButtonController(GameApp app) {
		this.gameApp = app;
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.JiNeng1:
		case R.id.JiNeng2:
		case R.id.JiNeng3: {
			this.gameApp.gameLogicData.myWuJiang
					.handleJiNengBtnEvent(v.getId());
			break;
		}
		default:
			break;
		}
	}
}
