package alben.sgs.wujiang.instance.jineng;

import alben.sgs.android.R;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.instance.TieSuoLianHuan;
import alben.sgs.type.Type;
import alben.sgs.type.Type.JinNangApplyTo;
import alben.sgs.wujiang.WuJiang;

public class LianHuan extends TieSuoLianHuan {
	public CardPai sp1 = null;

	public LianHuan(Type.CardPai na, Type.CardPaiClass c, int n,
			JinNangApplyTo at) {
		super(na, c, n, R.drawable.card_back, at);
		this.dispName = "¡¨ª∑";
	}

	public boolean work(WuJiang srcWJ, WuJiang tarWJ1, CardPai tarCP) {
		srcWJ.detatchCardPaiFromShouPai(this.sp1);
		this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "["
				+ this.dispName + "],∂™µÙ ÷≈∆" + this.sp1, Type.logDelay.Delay);
		return super.work(srcWJ, tarWJ1, tarCP);
	}
}
