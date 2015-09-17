package alben.sgs.wujiang.instance.jineng;

import alben.sgs.android.R;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.instance.WanJianQiFa;
import alben.sgs.type.Type;
import alben.sgs.type.Type.JinNangApplyTo;
import alben.sgs.wujiang.WuJiang;

public class LuanJi extends WanJianQiFa {
	public CardPai sp1, sp2 = null;

	public LuanJi(Type.CardPai na, Type.CardPaiClass c, int n, JinNangApplyTo at) {
		super(na, c, n, R.drawable.card_back, at);
		this.ID = generateID++;
		this.dispName = "ÂÒ»÷";
		this.name = Type.CardPai.WJJiNeng;
	}

	public String toString() {
		return "Íò¼ýÆë·¢";
	}

	public boolean work(WuJiang srcWJ, WuJiang tarWJ1, CardPai tarCP) {
		srcWJ.detatchCardPaiFromShouPai(this.sp1);
		srcWJ.detatchCardPaiFromShouPai(this.sp2);
		this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "["
				+ this.dispName + "],Æúµô" + this.sp1 + "ºÍ" + this.sp2,
				Type.logDelay.Delay);
		return super.work(srcWJ, tarWJ1, tarCP);
	}
}
