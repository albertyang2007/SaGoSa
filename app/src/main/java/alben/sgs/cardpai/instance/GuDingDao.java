package alben.sgs.cardpai.instance;

import alben.sgs.android.R;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.WuQiCardPai;
import alben.sgs.type.Type;
import alben.sgs.wujiang.WuJiang;

public class GuDingDao extends WuQiCardPai {
	public GuDingDao(Type.CardPai na, Type.CardPaiClass c, int n,
			int imgNumber, int d) {
		super(na, c, n, imgNumber, d);
		this.dispName = "π≈∂ßµ∂";
		this.zbImgNumber = R.drawable.zb_gudingdao;
	}

	public void listenShaEvent(WuJiang srcWJ, WuJiang tarWJ, CardPai srcCP) {

		if (srcWJ.state != Type.State.ChuPai)
			return;

		if (!(srcCP instanceof Sha))
			return;

		if (srcCP != null && tarWJ.shouPai.size() == 0) {
			srcCP.shangHaiReason += tarWJ.dispName + "±ª" + this.dispName
					+ "À˘…±,…À∫¶+1\n";
			srcCP.shangHaiN -= 1;
		}
	}
}
