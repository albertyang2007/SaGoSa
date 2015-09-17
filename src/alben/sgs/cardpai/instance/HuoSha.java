package alben.sgs.cardpai.instance;

import alben.sgs.type.Type;
import alben.sgs.wujiang.WuJiang;

public class HuoSha extends Sha {
	public HuoSha(Type.CardPai na, Type.CardPaiClass c, int n, int imgNumber) {
		super(na, c, n, imgNumber);
		this.dispName = "»ðÉ±";
		this.linkImpact = true;
	}

	public int countTotalShangHaiN(WuJiang tarWJ) {
		super.countTotalShangHaiN(tarWJ);
		return this.shangHaiN;
	}
}
