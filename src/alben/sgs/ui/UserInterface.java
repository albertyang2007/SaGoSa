package alben.sgs.ui;

import alben.sgs.android.GameApp;
import alben.sgs.android.data.LibGameViewData;
import alben.sgs.android.dialog.MainLoopHelper;
import alben.sgs.android.mycontroller.MyFunctionButtonController;
import alben.sgs.cardpai.CardPai;
import alben.sgs.type.Type;
import alben.sgs.wujiang.WuJiang;
import android.os.Handler;
import android.os.Message;

public class UserInterface {

	public Handler mHandler;
	private GameApp gameApp = null;
	private LibGameViewData libGameData = null;
	public boolean loop = false;

	public UserInterface(GameApp g) {
		this.gameApp = g;
		this.libGameData = this.gameApp.libGameViewData;
		this.mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// process incoming messages here
				// super.handleMessage(msg);
				if (msg.what == 1 && loop) {
					loop = false;
					throw new RuntimeException();
				}
			}
		};
	}

	public CardPai askUserChuPai(WuJiang curWJ, String info) {

		try {
			this.libGameData.logInfo(info, Type.logDelay.Delay);
			this.loop = true;
			MainLoopHelper.looperRun();
		} catch (RuntimeException e2) {
		}
		if (curWJ.state == Type.State.ChuPai
				|| curWJ.state == Type.State.Response) {

			CardPai cp = this.gameApp.gameLogicData.myWuJiang
					.getSelectedShouPai();

			if (cp != null && cp.canChuPai()) {
				curWJ.detatchCardPaiFromShouPai(cp);
				return cp;
			}
		}
		return null;
	}

	public void discardShouPai(WuJiang curWJ) {
		// ask the user to select card pai to discard
		// int discardShouPai = curWJ.shouPai.size() - curWJ.blood;
		if (this.gameApp.gameLogicData.discardShouPaiN <= 0)
			return;
		try {
			this.libGameData.logInfo("Çë¶ªÆú"
					+ this.gameApp.gameLogicData.discardShouPaiN + "ÕÅÊÖÅÆ",
					Type.logDelay.NoDelay);
			this.loop = true;
			MainLoopHelper.looperRun();
		} catch (RuntimeException e2) {
		}
		// discard shou pai
	}

	// for huogong, is request to show one card pai
	public CardPai askUserShowOneCardPai(WuJiang curWJ, String info) {
		CardPai cp = null;
		// before this, set notNil
		gameApp.gameLogicData.askForHuaShi = Type.CardPaiClass.notNil;

		try {
			this.libGameData.logInfo(info, Type.logDelay.Delay);
			this.loop = true;
			MainLoopHelper.looperRun();
		} catch (RuntimeException e2) {
		}

		if (curWJ.state == Type.State.Response) {
			cp = this.gameApp.gameLogicData.myWuJiang.getSelectedShouPai();
		}

		// after this, reset to nil
		gameApp.gameLogicData.askForHuaShi = Type.CardPaiClass.nil;
		return cp;
	}

	public void askUserSelectWuJiang(WuJiang curWJ, String info) {

		// change two btn's function
		new MyFunctionButtonController(gameApp).enterSelectWuJiangMode();

		try {
			this.libGameData.logInfo(info, Type.logDelay.Delay);
			this.loop = true;
			MainLoopHelper.looperRun();
		} catch (RuntimeException e2) {
		}

		// change back
		new MyFunctionButtonController(gameApp).exitSelectWuJiangMode();

		return;
	}

	public void sendMessageToUIForWakeUp() {
		Message m = this.gameApp.gameLogicData.userInterface.mHandler
				.obtainMessage();
		m.what = 1;
		this.gameApp.gameLogicData.userInterface.mHandler.sendMessage(m);
	}
}
