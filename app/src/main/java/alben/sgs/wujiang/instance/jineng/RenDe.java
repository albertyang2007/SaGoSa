package alben.sgs.wujiang.instance.jineng;

import alben.sgs.cardpai.CardPai;
import alben.sgs.type.Type;
import alben.sgs.type.UpdateWJViewData;
import alben.sgs.wujiang.WuJiang;
import alben.sgs.wujiang.instance.LiuBei;

public class RenDe extends CardPai {
	public CardPai sp1, sp2 = null;
	public WuJiang tarWJ = null;

	public RenDe(Type.CardPai na, Type.CardPaiClass c, int n) {
		super(na, c, n);
		this.ID = generateID++;
		this.dispName = "仁德";
		this.shangHaiN = 1;
		this.cpState = Type.CPState.nil;
		this.name = Type.CardPai.WJJiNeng;
	}

	public String toString() {
		return this.dispName;
	}

	public boolean work(WuJiang srcWJ, WuJiang tarWJ, CardPai tarCP) {

		if (srcWJ.tuoGuan) {
			tarWJ = this.tarWJ;
		}

		LiuBei lbwj = (LiuBei) srcWJ;
		UpdateWJViewData item = new UpdateWJViewData();
		item.updateShouPaiNumber = true;

		int cpN = 0;
		if (this.sp1 != null) {
			cpN++;
			srcWJ.detatchCardPaiFromShouPai(this.sp1);
			this.sp1.belongToWuJiang = tarWJ;
			this.sp1.gameApp = this.gameApp;
			this.sp1.cpState = Type.CPState.ShouPai;
			tarWJ.shouPai.add(this.sp1);
		}
		if (this.sp2 != null) {
			cpN++;
			srcWJ.detatchCardPaiFromShouPai(this.sp2);
			this.sp2.belongToWuJiang = tarWJ;
			this.sp2.gameApp = this.gameApp;
			this.sp2.cpState = Type.CPState.ShouPai;
			tarWJ.shouPai.add(this.sp2);
		}

		if (srcWJ.tuoGuan) {
			this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(
					srcWJ, item);
		} else {
			this.gameApp.gameLogicData.wjHelper.updateWJ8ShouPaiToLibGameView();
		}

		if (tarWJ.tuoGuan) {
			this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(
					tarWJ, item);
		} else {
			this.gameApp.gameLogicData.wjHelper.updateWJ8ShouPaiToLibGameView();
		}

		this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "["
				+ this.dispName + "]将" + cpN + "张手牌交给" + tarWJ.dispName,
				Type.logDelay.Delay);

		lbwj.totalSendSP += cpN;
		if (!lbwj.oneTimeJiNengTrigger && lbwj.totalSendSP >= 2
				&& srcWJ.blood < srcWJ.getMaxBlood()) {
			lbwj.oneTimeJiNengTrigger = true;
			this.shangHaiSrcWJ = srcWJ;
			srcWJ.increaseBlood(srcWJ, this);
		}

		return true;
	}
}
