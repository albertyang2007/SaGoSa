package alben.sgs.wujiang.instance.jineng;

import alben.sgs.cardpai.CardPai;
import alben.sgs.type.Type;

public class TieJi extends CardPai {
	public TieJi(Type.CardPai na, Type.CardPaiClass c, int n) {
		super(na, c, n);
		this.ID = generateID++;
		this.dispName = "ÌúÆï";
		this.cpState = Type.CPState.nil;
		this.name = Type.CardPai.WJJiNeng;
	}
	
	public String toString() {
		return this.dispName;
	}

}
