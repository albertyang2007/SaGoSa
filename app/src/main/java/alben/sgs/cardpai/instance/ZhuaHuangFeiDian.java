package alben.sgs.cardpai.instance;

import alben.sgs.android.R;
import alben.sgs.cardpai.MaCardPai;
import alben.sgs.type.Type;

public class ZhuaHuangFeiDian extends MaCardPai {
	public ZhuaHuangFeiDian(Type.CardPai na, Type.CardPaiClass c, int n,
			int imgNumber, int d) {
		super(na, c, n, imgNumber, d);
		this.dispName = "×¦»Æ·Éµç";
		this.zbImgNumber = R.drawable.zb_zhuahuangfeidian;
	}
}
