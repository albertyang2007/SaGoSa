package alben.sgs.wujiang.instance.jineng;

import alben.sgs.cardpai.CardPai;
import alben.sgs.type.Type;

public class LuoShen extends CardPai {
	public LuoShen(Type.CardPai na, Type.CardPaiClass c, int n) {
		super(na, c, n);
		this.ID = generateID++;
		this.dispName = "ÂåÉñ";
		this.cpState = Type.CPState.nil;
		this.name = Type.CardPai.WJJiNeng;
	}
	
	public String toString() {
		return this.dispName;
	}
}
