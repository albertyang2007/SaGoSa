package alben.sgs.cardpai.instance;

import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.JinNangCardPai;
import alben.sgs.type.Type;
import alben.sgs.type.Type.JinNangApplyTo;
import alben.sgs.wujiang.WuJiang;

public class TaoYuanJieYi extends JinNangCardPai {
	public TaoYuanJieYi(Type.CardPai na, Type.CardPaiClass c, int n,
			int imgNumber, JinNangApplyTo at) {
		super(na, c, n, imgNumber, at);
		this.dispName = "桃园结义";
		this.eventImpactN = 1;
		this.shangHaiN = 1;
	}

	public void reset() {
		super.reset();
		this.shangHaiN = 1;
	}

	public boolean work(WuJiang srcWJ, WuJiang tarWJ1, CardPai tarCP) {
		WuJiang tarWJ = srcWJ;
		this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "使用" + this,
				Type.logDelay.Delay);

		this.listenPreWorkEvent(srcWJ, tarWJ, tarCP);

		boolean run = true;
		while (!tarWJ.equals(srcWJ) || run) {
			if (tarWJ.equals(srcWJ)) {
				run = false;
			}
			if (tarWJ.blood < tarWJ.getMaxBlood()) {
				if (tarWJ.askForWuXieKeJi(srcWJ, this, tarWJ, this)) {
					this.gameApp.libGameViewData.logInfo(
							this.dispName + "被无懈了", Type.logDelay.NoDelay);
				} else {
					this.countTotalShangHaiN(tarWJ);
					tarWJ.increaseBlood(srcWJ, this);
				}
			}
			// next one
			tarWJ = tarWJ.nextOne;
		}
		return true;
	}
}
