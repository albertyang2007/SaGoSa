package alben.sgs.wujiang.instance.jineng;

import alben.sgs.android.R;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.instance.Sha;
import alben.sgs.type.Type;
import alben.sgs.wujiang.WuJiang;

public class LongDan extends Sha {
	public CardPai sp1 = null;

	public LongDan(Type.CardPai na, Type.CardPaiClass c, int n) {
		super(na, c, n, R.drawable.card_back);
		this.ID = generateID++;
		this.dispName = "Áúµ¨";
	}

	public String toString() {
		return this.sp1.toString();
	}

	public boolean work(WuJiang srcWJ, WuJiang tarWJ, CardPai tarCP) {
		srcWJ.detatchCardPaiFromShouPai(this.sp1);
		this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "["
				+ this.dispName + "]", Type.logDelay.NoDelay);
		return super.work(srcWJ, tarWJ, tarCP);
	}
}
