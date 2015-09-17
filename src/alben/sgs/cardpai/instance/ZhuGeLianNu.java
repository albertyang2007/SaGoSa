package alben.sgs.cardpai.instance;

import alben.sgs.android.R;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.WuQiCardPai;
import alben.sgs.type.Type;
import alben.sgs.wujiang.WuJiang;

public class ZhuGeLianNu extends WuQiCardPai {
	public ZhuGeLianNu(Type.CardPai na, Type.CardPaiClass c, int n,
			int imgNumber, int d) {
		super(na, c, n, imgNumber, d);
		this.dispName = "Öî¸ðÁ¬åó";
		if (c == Type.CardPaiClass.FangPian) {
			this.zbImgNumber = R.drawable.zb_zhugeliannv_red;
		} else if (c == Type.CardPaiClass.MeiHua) {
			this.zbImgNumber = R.drawable.zb_zhugeliannv_black;
		}
	}

	public void listenShaEvent(WuJiang srcWJ, WuJiang tarWJ, CardPai srcCP) {
		if (srcWJ.state == Type.State.ChuPai && srcWJ.huiHeChuShaN > 1) {
			srcWJ.specialChuPaiReason = "[" + this.dispName + "]";
		}
	}
}
