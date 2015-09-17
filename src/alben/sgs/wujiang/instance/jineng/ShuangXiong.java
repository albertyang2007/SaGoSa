package alben.sgs.wujiang.instance.jineng;

import alben.sgs.android.R;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.instance.JueDou;
import alben.sgs.type.Type;
import alben.sgs.type.Type.JinNangApplyTo;
import alben.sgs.wujiang.WuJiang;

public class ShuangXiong extends JueDou {
	public CardPai sp1 = null;

	public ShuangXiong(Type.CardPai na, Type.CardPaiClass c, int n,
			JinNangApplyTo at) {
		super(na, c, n, R.drawable.card_back, at);
		this.ID = generateID++;
		this.dispName = "ЫЋал";
		this.name = Type.CardPai.WJJiNeng;
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
