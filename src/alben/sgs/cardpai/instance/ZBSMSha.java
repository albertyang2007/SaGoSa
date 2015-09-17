package alben.sgs.cardpai.instance;

import alben.sgs.android.R;
import alben.sgs.cardpai.CardPai;
import alben.sgs.type.Type;
import alben.sgs.type.Type.CardPaiClass;
import alben.sgs.wujiang.WuJiang;

public class ZBSMSha extends Sha {
	public CardPai sp1 = null;
	public CardPai sp2 = null;

	public ZBSMSha(CardPai cp1, CardPai cp2) {
		super(Type.CardPai.Sha, ZBSMSha.genCPS(cp1, cp2), 0,
				R.drawable.card_back);
		this.dispName = "É±";
		this.sp1 = cp1;
		this.sp2 = cp2;
		this.belongToWuJiang = cp1.belongToWuJiang;
		this.gameApp = cp1.gameApp;
		this.cpState = Type.CPState.nil;
	}

	private static CardPaiClass genCPS(CardPai cp1, CardPai cp2) {
		Type.CardPaiClass cps1 = cp1.clas;
		Type.CardPaiClass cps2 = cp2.clas;

		CardPaiClass cps = Type.CardPaiClass.HeiTao;
		if (cps1 == Type.CardPaiClass.FangPian) {
			if (cps2 != Type.CardPaiClass.HeiTao
					&& cps2 != Type.CardPaiClass.MeiHua)
				cps = Type.CardPaiClass.FangPian;
		} else if (cps1 == Type.CardPaiClass.HongTao) {
			if (cps2 != Type.CardPaiClass.HeiTao
					&& cps2 != Type.CardPaiClass.MeiHua)
				cps = Type.CardPaiClass.HongTao;
		} else if (cps1 == Type.CardPaiClass.HeiTao) {
			cps = Type.CardPaiClass.HeiTao;
		} else if (cps1 == Type.CardPaiClass.MeiHua) {
			cps = Type.CardPaiClass.MeiHua;
		}
		return cps;
	}

	public boolean work(WuJiang srcWJ, WuJiang tarWJ, CardPai tarCP) {
		this.discardTwoShouPai();
		return super.work(srcWJ, tarWJ, tarCP);
	}

	public void discardTwoShouPai() {
		if (this.belongToWuJiang != null) {
			this.gameApp.libGameViewData.logInfo(this.belongToWuJiang.dispName
					+ "[ÕÉ°ËÉßÃ¬]¶ªÆúÅÆ" + this.sp1, Type.logDelay.Delay);
			this.gameApp.libGameViewData.logInfo(this.belongToWuJiang.dispName
					+ "[ÕÉ°ËÉßÃ¬]¶ªÆúÅÆ" + this.sp2, Type.logDelay.Delay);
			this.belongToWuJiang.detatchCardPaiFromShouPai(this.sp1);
			this.belongToWuJiang.detatchCardPaiFromShouPai(this.sp2);

			this.sp1.belongToWuJiang = null;
			this.sp1.cpState = Type.CPState.FeiPaiDui;
			this.sp2.belongToWuJiang = null;
			this.sp2.cpState = Type.CPState.FeiPaiDui;
		}
	}
}
