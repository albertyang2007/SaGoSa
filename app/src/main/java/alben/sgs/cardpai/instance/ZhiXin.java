package alben.sgs.cardpai.instance;

import alben.sgs.android.R;
import alben.sgs.cardpai.MaCardPai;
import alben.sgs.type.Type;
import alben.sgs.android.R;

public class ZhiXin extends MaCardPai {
	public ZhiXin(Type.CardPai na, Type.CardPaiClass c, int n, int imgNumber,
			int d) {
		super(na, c, n, imgNumber, d);
		this.dispName = "во?";
		this.zbImgNumber = R.drawable.zb_zhixin;
	}
}
