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
		this.dispName = "���ǵ�";
		this.zbImgNumber = R.drawable.zb_qixingdao;
	}

	public void listenShaEvent(WuJiang srcWJ, WuJiang tarWJ, CardPai srcCP) {

		if (srcWJ.state != Type.State.ChuPai)
			return;

		if (!(srcCP instanceof Sha))
			return;

		if (srcCP != null) {
			srcCP.shangHaiReason += tarWJ.dispName + "��" + this.dispName
					+ "��ɱ,�˺�+1\n";
			srcCP.shangHaiN -= 1;
		}

	}

	public void listenShanEvent(WuJiang srcWJ, WuJiang tarWJ, CardPai srcCP) {

		if (srcWJ.state != Type.State.ChuPai)
			return;

		if (!(srcCP instanceof Sha))
			return;

		this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "ʹ��"
				+ this.dispName + "ʧ��,�����亦", Type.logDelay.Delay);
		srcCP.shangHaiN = -1;
		srcCP.shangHaiSrcWJ = tarWJ;
		srcWJ.increaseBlood(srcWJ, srcCP);
	}
}
