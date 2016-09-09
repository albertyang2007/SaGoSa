package alben.sgs.cardpai.instance;

import alben.sgs.cardpai.BasicCardPai;
import alben.sgs.cardpai.CardPai;
import alben.sgs.type.Type;
import alben.sgs.wujiang.WuJiang;

public class Tao extends BasicCardPai {
	public Tao(Type.CardPai na, Type.CardPaiClass c, int n, int imgNumber) {
		super(na, c, n, imgNumber);
		this.dispName = "Ã“";
		this.shangHaiN = 1;
	}

	public void reset() {
		super.reset();
		this.shangHaiN = 1;
	}

	public boolean work(WuJiang srcWJ, WuJiang tarWJ, CardPai tarCP) {
		this.countTotalShangHaiN(srcWJ);
		this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "≥‘" + this,
				Type.logDelay.Delay);
		srcWJ.increaseBlood(srcWJ, this);
		return true;
	}
}
