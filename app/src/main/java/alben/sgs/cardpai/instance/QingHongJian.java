package alben.sgs.cardpai.instance;

import alben.sgs.android.R;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.WuQiCardPai;
import alben.sgs.type.Type;
import alben.sgs.wujiang.WuJiang;

public class QingHongJian extends WuQiCardPai {
	public QingHongJian(Type.CardPai na, Type.CardPaiClass c, int n,
			int imgNumber, int d) {
		super(na, c, n, imgNumber, d);
		this.dispName = "Çà¸Ö½£";
		this.zbImgNumber = R.drawable.zb_qinggangjian;
	}

	public void listenShaEvent(WuJiang srcWJ, WuJiang tarWJ, CardPai srcCP) {
		if (srcWJ.state != Type.State.ChuPai)
			return;

		if (!(srcCP instanceof Sha))
			return;

		if (tarWJ.zhuangBei.fangJu != null)
			srcWJ.specialChuPaiReason = "[" + this.dispName + "]";
	}
}
