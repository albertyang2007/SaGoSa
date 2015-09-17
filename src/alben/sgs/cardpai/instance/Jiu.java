package alben.sgs.cardpai.instance;

import alben.sgs.cardpai.BasicCardPai;
import alben.sgs.cardpai.CardPai;
import alben.sgs.type.Type;
import alben.sgs.wujiang.WuJiang;

public class Jiu extends BasicCardPai {
	public Jiu(Type.CardPai na, Type.CardPaiClass c, int n, int imgNumber) {
		super(na, c, n, imgNumber);
		this.dispName = "¾Æ";
	}

	public boolean work(WuJiang srcWJ, WuJiang tarWJ, CardPai tarCP) {
		this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "ºÈ¾Æ" + this,
				Type.logDelay.Delay);
		srcWJ.heJiu = true;
		return true;
	}
}
