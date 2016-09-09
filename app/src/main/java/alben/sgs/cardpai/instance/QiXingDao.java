package alben.sgs.cardpai.instance;

import alben.sgs.android.R;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.WuQiCardPai;
import alben.sgs.type.Type;
import alben.sgs.wujiang.WuJiang;

public class QiXingDao extends WuQiCardPai {
	public QiXingDao(Type.CardPai na, Type.CardPaiClass c, int n,
			int imgNumber, int d) {
		super(na, c, n, imgNumber, d);
		this.dispName = "七星刀";
		this.zbImgNumber = R.drawable.zb_qixingdao;
	}

	public void listenShaEvent(WuJiang srcWJ, WuJiang tarWJ, CardPai srcCP) {

		if (srcWJ.state != Type.State.ChuPai)
			return;

		if (!(srcCP instanceof Sha))
			return;

		if (srcCP != null) {
			srcCP.shangHaiReason += tarWJ.dispName + "被" + this.dispName
					+ "所杀,伤害+1\n";
			srcCP.shangHaiN -= 1;
		}

	}

	public void listenShanEvent(WuJiang srcWJ, WuJiang tarWJ, CardPai srcCP) {

		if (srcWJ.state != Type.State.ChuPai)
			return;

		if (!(srcCP instanceof Sha))
			return;

		this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "使用"
				+ this.dispName + "失败,反受其害", Type.logDelay.Delay);
		srcCP.shangHaiN = -1;
		srcCP.shangHaiSrcWJ = tarWJ;
		srcWJ.increaseBlood(srcWJ, srcCP);
	}
}
