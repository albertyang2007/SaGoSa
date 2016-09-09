package alben.sgs.cardpai.instance;

import alben.sgs.android.R;
import alben.sgs.cardpai.MaCardPai;
import alben.sgs.type.Type;

public class ChiTu extends MaCardPai {
	public ChiTu(Type.CardPai na, Type.CardPaiClass c, int n, int imgNumber,
			int d) {
		super(na, c, n, imgNumber, d);
		this.dispName = "ณเอร";
		this.zbImgNumber = R.drawable.zb_chitu;
	}
}
