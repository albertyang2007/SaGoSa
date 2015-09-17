package alben.sgs.android.mycontroller;

import alben.sgs.android.GameApp;
import alben.sgs.cardpai.instance.ZhangBaSheMao;
import alben.sgs.type.Type;
import android.view.View;
import android.view.View.OnClickListener;

public class MyWJ8WuQiTxtListener implements OnClickListener {
	public GameApp gameApp;

	public MyWJ8WuQiTxtListener(GameApp app) {
		this.gameApp = app;
	}

	public void onClick(View v) {
		// click zhangbashemao
		if (gameApp.gameLogicData.myWuJiang == null)
			return;

		if (gameApp.gameLogicData.myWuJiang.shouPai.size() < 2)
			return;

		if (gameApp.gameLogicData.myWuJiang.state != Type.State.ChuPai
				&& gameApp.gameLogicData.myWuJiang.state != Type.State.Response)
			return;

		if (gameApp.gameLogicData.myWuJiang.state == Type.State.ChuPai
				&& !gameApp.gameLogicData.myWuJiang.canIChuSha()) {
			return;
		}

		if ((gameApp.gameLogicData.askForPai != Type.CardPai.notNil)
				&& gameApp.gameLogicData.askForPai != Type.CardPai.Sha)
			return;

		if (gameApp.gameLogicData.myWuJiang.zhuangBei.wuQi == null)
			return;

		if (!(gameApp.gameLogicData.myWuJiang.zhuangBei.wuQi instanceof ZhangBaSheMao))
			return;

		ZhangBaSheMao zbsm = (ZhangBaSheMao) gameApp.gameLogicData.myWuJiang.zhuangBei.wuQi;
		zbsm.listenWJ8ClickEvent();
	}
}
