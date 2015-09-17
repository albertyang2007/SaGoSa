package alben.sgs.wujiang.instance.jineng;

import alben.sgs.cardpai.CardPai;
import alben.sgs.type.Type;

public class LeiJi extends CardPai {
	public LeiJi(Type.CardPai na, Type.CardPaiClass c, int n) {
		super(na, c, n);
		this.ID = generateID++;
		this.dispName = "À×»÷";
		this.shangHaiN = -2;
		this.cpState = Type.CPState.nil;
		this.name = Type.CardPai.WJJiNeng;
		this.linkImpact = true;
	}

	public String toString() {
		return this.dispName;
	}
}
