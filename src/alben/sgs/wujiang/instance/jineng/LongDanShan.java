package alben.sgs.wujiang.instance.jineng;

import alben.sgs.android.R;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.instance.Shan;
import alben.sgs.type.Type;

public class LongDanShan extends Shan {
	public CardPai sp1 = null;

	public LongDanShan(Type.CardPai na, Type.CardPaiClass c, int n) {
		super(na, c, n, R.drawable.card_back);
		this.ID = generateID++;
		this.dispName = "Áúµ¨";
	}
}
