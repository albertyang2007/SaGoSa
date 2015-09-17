package alben.sgs.cardpai.instance;

import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.JinNangCardPai;
import alben.sgs.type.Type;
import alben.sgs.type.Type.JinNangApplyTo;
import alben.sgs.type.UpdateWJViewData;
import alben.sgs.wujiang.WuJiang;

public class ShanDian extends JinNangCardPai {
	public boolean active = false;

	public ShanDian(Type.CardPai na, Type.CardPaiClass c, int n, int imgNumber,
			JinNangApplyTo at) {
		super(na, c, n, imgNumber, at);
		this.dispName = "闪电";
		this.linkImpact = true;
		this.delay = true;
		this.shangHaiN = -3;
	}

	public void reset() {
		super.reset();
		this.shangHaiN = -3;
		this.active = false;
	}

	public boolean work(WuJiang srcWJ, WuJiang tarWJ, CardPai tarCP) {
		if (!srcWJ.hasSDInPanDindArea()) {

			this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "安装" + this,
					Type.logDelay.Delay);

			this.cpState = Type.CPState.pandDingPai;
			this.belongToWuJiang = srcWJ;
			this.active = false;
			srcWJ.panDingPai.add(this);

			// update view
			UpdateWJViewData item = new UpdateWJViewData();
			item.updatePangDing = true;
			this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(
					srcWJ, item);

			return true;
		}
		return true;
	}

	public boolean panDing(WuJiang srcWJ, WuJiang tarWJ, CardPai tarCP) {
		boolean hitShanDian = false;

		UpdateWJViewData item = new UpdateWJViewData();
		item.updatePangDing = true;

		this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "判定闪电",
				Type.logDelay.Delay);
		// ask for wu xie
		if (srcWJ.askForWuXieKeJi(srcWJ, null, srcWJ, this)) {
			this.gameApp.libGameViewData.logInfo(this.dispName + "被无懈了",
					Type.logDelay.NoDelay);
			this.moveShanDianToNextOne(srcWJ);
		} else if (!this.panDingThruCardPai(srcWJ)) {
			// cp checking had been done
			this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "闪电失败",
					Type.logDelay.Delay);
			this.moveShanDianToNextOne(srcWJ);
		} else {
			// cp checking had been done
			hitShanDian = true;
			this.belongToWuJiang = null;
			this.cpState = Type.CPState.FeiPaiDui;
			srcWJ.panDingPai.remove(this);

			this.countTotalShangHaiN(srcWJ);
			this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "被闪电闪中",
					Type.logDelay.Delay);

			// update view
			this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(
					srcWJ, item);
			this.shangHaiSrcWJ = null;
			srcWJ.increaseBlood(null, this);
		}

		// update view
		this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(srcWJ,
				item);

		return hitShanDian;
	}

	public void moveShanDianToNextOne(WuJiang srcWJ) {

		srcWJ.panDingPai.remove(this);

		int count = 0;// to avoid fails too many times

		boolean found = false;
		WuJiang fromWJ = srcWJ.nextOne;
		while (!found && fromWJ != null && count++ < 20) {
			if (fromWJ.state != Type.State.Dead && !fromWJ.hasSDInPanDindArea()) {
				this.belongToWuJiang = fromWJ;
				this.active = false;
				fromWJ.panDingPai.add(this);
				// update view
				UpdateWJViewData item = new UpdateWJViewData();
				item.updatePangDing = true;
				this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(
						srcWJ, item);
				this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(
						fromWJ, item);
				this.gameApp.libGameViewData.logInfo(fromWJ.dispName + "安装了闪电",
						Type.logDelay.Delay);

				found = true;
			} else {
				fromWJ = fromWJ.nextOne;
			}
		}
	}

	public boolean panDingThruCardPai(WuJiang srcWJ) {
		boolean rtn = false;
		CardPai cp = this.gameApp.gameLogicData.cpHelper.popCardPaiForPanDing(
				srcWJ, this);

		if (cp.clas == Type.CardPaiClass.HeiTao) {
			if (cp.number >= 2 && cp.number <= 9)
				rtn = true;
		}
		return rtn;
	}
}
