package alben.sgs.cardpai.instance;

import alben.sgs.android.R;
import alben.sgs.cardpai.MaCardPai;
import alben.sgs.type.Type;

public class DaWan extends MaCardPai {
	public DaWan(Type.CardPai na, Type.CardPaiClass c, int n, int imgNumber,
			int d) {
		super(na, c, n, imgNumber, d);
		this.dispName = "´óÍð";
		this.zbImgNumber = R.drawable.zb_dawan;
	}

}
