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
		this.dispName = "�һ�";
		this.name = Type.CardPai.WJJiNeng;
	}

	public String toString() {
		return "����뷢";
	}

	public boolean work(WuJiang srcWJ, WuJiang tarWJ1, CardPai tarCP) {
		srcWJ.detatchCardPaiFromShouPai(this.sp1);
		srcWJ.detatchCardPaiFromShouPai(this.sp2);
		this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "["
				+ this.dispName + "],����" + this.sp1 + "��" + this.sp2,
				Type.logDelay.Delay);
		return super.work(srcWJ, tarWJ1, tarCP);
	}
}
