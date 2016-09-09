package alben.sgs.cardpai.instance;

import alben.sgs.type.Type;

public class LeiSha extends Sha {
	public LeiSha(Type.CardPai na, Type.CardPaiClass c, int n, int imgNumber) {
		super(na, c, n, imgNumber);
		this.dispName = "À×É±";
		this.linkImpact = true;
	}
}
