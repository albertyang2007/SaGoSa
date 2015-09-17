package alben.sgs.type;

import alben.sgs.cardpai.CardPai;
import alben.sgs.wujiang.WuJiang;

public class CardPaiActionForTarWuJiang {
	public WuJiang tarWJ = null;
	public CardPai srcCP = null;

	public void reset() {
		this.tarWJ = null;
		this.srcCP = null;
	}
}
