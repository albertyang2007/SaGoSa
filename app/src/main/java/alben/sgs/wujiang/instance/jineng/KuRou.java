package alben.sgs.wujiang.instance.jineng;

import alben.sgs.cardpai.CardPai;
import alben.sgs.type.Type;
import alben.sgs.type.UpdateWJViewData;
import alben.sgs.wujiang.WuJiang;

public class KuRou extends CardPai {

	public KuRou(Type.CardPai na, Type.CardPaiClass c, int n) {
		super(na, c, n);
		this.ID = generateID++;
		this.dispName = "ø‡»‚";
		this.shangHaiN = -1;
		this.cpState = Type.CPState.nil;
		this.name = Type.CardPai.WJJiNeng;
	}

	public String toString() {
		return this.dispName;
	}

	public boolean work(WuJiang srcWJ, WuJiang tarWJ, CardPai tarCP) {

		this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "["
				+ this.dispName + "]", Type.logDelay.HalfDelay);

		this.shangHaiSrcWJ = srcWJ;
		srcWJ.increaseBlood(srcWJ, this);

		if (srcWJ.state == Type.State.Dead) {
			return false;
		}

		this.gameApp.gameLogicData.cpHelper.addCardPaiToWuJiang(srcWJ, 2);

		if (srcWJ.tuoGuan) {
			UpdateWJViewData item = new UpdateWJViewData();
			item.updateShouPaiNumber = true;
			this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(
					srcWJ, item);
		} else {
			this.gameApp.gameLogicData.wjHelper.updateWJ8ShouPaiToLibGameView();
		}

		this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "√˛2’≈≈∆",
				Type.logDelay.HalfDelay);

		return true;
	}

}
