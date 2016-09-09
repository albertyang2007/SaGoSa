package alben.sgs.wujiang.instance.jineng;

import alben.sgs.android.R;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.instance.HuoGong;
import alben.sgs.type.Type;
import alben.sgs.type.Type.JinNangApplyTo;
import alben.sgs.wujiang.WuJiang;

public class HuoJi extends HuoGong {
	public CardPai sp1 = null;

	public HuoJi(Type.CardPai na, Type.CardPaiClass c, int n, JinNangApplyTo at) {
		super(na, c, n, R.drawable.card_back, at);
		this.dispName = "»ð¼Æ";
		this.cpState = Type.CPState.nil;
		this.name = Type.CardPai.WJJiNeng;
	}

	public String toString() {
		return this.dispName;
	}

	public boolean work(WuJiang srcWJ, WuJiang tarWJ, CardPai tarCP) {
		srcWJ.detatchCardPaiFromShouPai(this.sp1);
		this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "["
				+ this.dispName + "]" + this.sp1 + tarWJ.dispName,
				Type.logDelay.Delay);
		return super.work(srcWJ, tarWJ, tarCP);
	}
}
