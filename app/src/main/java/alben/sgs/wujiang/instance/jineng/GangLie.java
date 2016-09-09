package alben.sgs.wujiang.instance.jineng;

import alben.sgs.cardpai.CardPai;
import alben.sgs.type.Type;

public class GangLie extends CardPai {
	public CardPai srcCP = null;

	public GangLie(Type.CardPai na, Type.CardPaiClass c, int n) {
		super(na, c, n);
		this.ID = generateID++;
		this.dispName = "ธีมา";
		this.shangHaiN = -1;
		this.cpState = Type.CPState.nil;
		this.name = Type.CardPai.WJJiNeng;
	}

	public String toString() {
		return this.dispName;
	}
}
