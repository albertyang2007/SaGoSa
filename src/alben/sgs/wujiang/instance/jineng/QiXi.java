package alben.sgs.wujiang.instance.jineng;

import alben.sgs.android.R;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.ZhuangBeiCardPai;
import alben.sgs.cardpai.instance.GuoHeChaiQiao;
import alben.sgs.type.Type;
import alben.sgs.type.Type.JinNangApplyTo;
import alben.sgs.wujiang.WuJiang;

public class QiXi extends GuoHeChaiQiao {
	public CardPai sp1 = null;

	public QiXi(Type.CardPai na, Type.CardPaiClass c, int n, JinNangApplyTo at) {
		super(na, c, n, R.drawable.card_back, at);
		this.dispName = "ÆæÏ®";
	}

	public String toString() {
		return this.sp1 + " " + this.dispName;
	}

	public boolean work(WuJiang srcWJ, WuJiang tarWJ, CardPai tarCP1) {

		if (this.sp1.cpState == Type.CPState.ShouPai) {
			this.sp1.belongToWuJiang = null;
			srcWJ.detatchCardPaiFromShouPai(this.sp1);
			this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "["
					+ this.sp1 + "]", Type.logDelay.Delay);
		} else if (this.sp1.cpState == Type.CPState.wuQiPai
				|| this.sp1.cpState == Type.CPState.fangJuPai
				|| this.sp1.cpState == Type.CPState.jiaYiMaPai
				|| this.sp1.cpState == Type.CPState.jianYiMaPai) {
			srcWJ.unstallZhuangBei((ZhuangBeiCardPai) this.sp1);
		}

		return super.work(srcWJ, tarWJ, tarCP1);
	}
}
