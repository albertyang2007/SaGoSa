package alben.sgs.cardpai.instance;

import alben.sgs.android.GameApp;
import alben.sgs.android.R;
import alben.sgs.android.dialog.YesNoDialog;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.JinNangCardPai;
import alben.sgs.common.LoopWuJiangHelper;
import alben.sgs.type.Type;
import alben.sgs.type.Type.JinNangApplyTo;
import alben.sgs.type.UpdateWJViewData;
import alben.sgs.wujiang.WuJiang;

public class TieSuoLianHuan extends JinNangCardPai {

	boolean linkOrReset = false;// false: reset; true: link wj

	public TieSuoLianHuan(Type.CardPai na, Type.CardPaiClass c, int n,
			int imgNumber, JinNangApplyTo at) {
		super(na, c, n, imgNumber, at);
		this.dispName = "铁索连环";
		this.eventImpactN = 0;
		this.selectTarWJNumber = 2;
	}

	public void reset() {
		super.reset();
		this.linkOrReset = false;
		this.eventImpactN = 0;
	}

	public boolean work(WuJiang srcWJ, WuJiang tarWJ1, CardPai tarCP) {

		if (srcWJ.tuoGuan) {
			// for AI: reset
			this.linkOrReset = false;
		} else {
			// get value from dlg
			this.gameApp.ynData.reset();
			this.gameApp.ynData.okTxt = "连环";
			this.gameApp.ynData.cancelTxt = "重铸";
			this.gameApp.ynData.genInfo = "连环还是重铸?";
			YesNoDialog dlg = new YesNoDialog(this.gameApp.gameActivityContext,
					this.gameApp);
			dlg.showDialog();
			this.linkOrReset = this.gameApp.ynData.result;
		}

		if (this.linkOrReset) {// link to wj

			// only for wj huangyueying
			this.listenPreWorkEvent(srcWJ, tarWJ1, tarCP);

			WuJiang[] tarWJ = { null, null };

			if (srcWJ.tuoGuan) {
				// for AI:
				tarWJ[0] = srcWJ;
				tarWJ[1] = srcWJ.nextOne;
			} else {
				// open dlg to select two wj for link
				this.gameApp.selectWJViewData.reset();
				this.gameApp.selectWJViewData.selectNumber = this.selectTarWJNumber;

				// set wj color to green to be select
				this.initAllWuJiangForSelect(srcWJ, R.drawable.bg_green);

				// use UI for interaction
				this.gameApp.gameLogicData.userInterface.askUserSelectWuJiang(
						srcWJ, "请选择" + this.selectTarWJNumber + "个武将");

				tarWJ[0] = this.gameApp.selectWJViewData.selectedWJ1;
				tarWJ[1] = this.gameApp.selectWJViewData.selectedWJ2;

				// reset wj color to black
				this.initAllWuJiangForSelect(srcWJ, R.drawable.bg_black);
			}

			for (int i = 0; i < tarWJ.length; i++) {
				// 如果已经有link存在，则认为这是一个有益事件(unlink)
				// 否则认为是有害事件
				if (tarWJ[i].lianHuan) {
					this.eventImpactN = 1;
				}

				if (srcWJ.askForWuXieKeJi(srcWJ, this, tarWJ[i], this)) {
					this.gameApp.libGameViewData.logInfo(this.dispName + "被无懈了"
							+ this, Type.logDelay.NoDelay);
				} else {
					// link
					this.linkWuJiang(tarWJ[i]);
				}
			}
		} else {// reset
			CardPai cp = this.gameApp.gameLogicData.cpHelper.popCardPai();
			cp.belongToWuJiang = srcWJ;
			cp.cpState = Type.CPState.ShouPai;
			srcWJ.shouPai.add(cp);
			this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "重置" + this,
					Type.logDelay.Delay);

			if (srcWJ.tuoGuan) {
				UpdateWJViewData item = new UpdateWJViewData();
				item.updateShouPaiNumber = true;
				this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(
						srcWJ, item);
			} else {
				this.gameApp.gameLogicData.wjHelper
						.updateWJ8ShouPaiToLibGameView();
			}
		}

		this.gameApp.ynData.reset();
		return true;
	}

	public void linkWuJiang(WuJiang wj) {
		String log = "";
		if (!wj.lianHuan) {
			wj.lianHuan = true;
			log = "被连环加上";
		} else {
			wj.lianHuan = false;
			log = "连环被解除";
		}
		this.gameApp.libGameViewData.logInfo(wj.dispName + log,
				Type.logDelay.Delay);
		// update view
		UpdateWJViewData item = new UpdateWJViewData();
		item.updateLianHuan = true;
		this.gameApp.gameLogicData.wjHelper
				.updateWuJiangToLibGameView(wj, item);
	}

	public void initAllWuJiangForSelect(WuJiang srcWJ, int color) {
		WuJiang curWJ = srcWJ;
		curWJ.clicked = false;
		gameApp.libGameViewData.imageWJs[curWJ.imageViewIndex]
				.setBackgroundDrawable(gameApp.getResources()
						.getDrawable(color));
		curWJ = curWJ.nextOne;
		while (!curWJ.equals(srcWJ)) {
			curWJ.clicked = false;
			gameApp.libGameViewData.imageWJs[curWJ.imageViewIndex]
					.setBackgroundDrawable(gameApp.getResources().getDrawable(
							color));
			curWJ = curWJ.nextOne;
		}
	}

	// this method only invoke once!
	public static void increaseBloodForOtherLinkWJ(GameApp gameApp,
			WuJiang startFromWJ, CardPai srcCP) {

		UpdateWJViewData item = new UpdateWJViewData();
		item.updateBlood = true;
		item.updateLianHuan = true;

		// remember this value in case some one change it
		int origSHN = srcCP.shangHaiN;

		LoopWuJiangHelper loopWJHelper = new LoopWuJiangHelper(
				gameApp.gameLogicData.wuJiangs, startFromWJ);
		for (WuJiang localWuJiang = loopWJHelper.nextLoopWJ(); (localWuJiang != null)
				&& (!gameApp.gameLogicData.wjHelper.checkMatchOver()); localWuJiang = loopWJHelper
				.nextLoopWJ()) {
			if (!localWuJiang.lianHuan)
				continue;
			srcCP.shangHaiN = origSHN;
			gameApp.libGameViewData.logInfo(localWuJiang.dispName + "受到"
					+ Math.abs(srcCP.shangHaiN) + "点连环伤害", Type.logDelay.Delay);
			localWuJiang.increaseBlood(srcCP.belongToWuJiang, srcCP);
			gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(
					localWuJiang, item);
		}

		// restore
		srcCP.shangHaiN = origSHN;
	}

	// http://ask.yokagames.com/question.php?qid=7583
	// 连环角色受到的是同属性、同程度、同来源的伤害。
	// 连环中角色受到的伤害＝第一名受到属性伤害并触发传导的角色所受到的伤害＋自己装备的加成或削减
}
