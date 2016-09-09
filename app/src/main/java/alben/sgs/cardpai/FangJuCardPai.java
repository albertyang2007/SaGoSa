package alben.sgs.cardpai;

import alben.sgs.android.R;
import alben.sgs.type.Type;
import alben.sgs.wujiang.WuJiang;

public class FangJuCardPai extends ZhuangBeiCardPai {
	public int zbImgNumber = R.drawable.zb_fangju;
	public FangJuCardPai(Type.CardPai na, Type.CardPaiClass c, int n,
			int imgNumber) {
		super(na, c, n, imgNumber);
	}

	public boolean defenceWork(WuJiang srcWJ, WuJiang tarWJ, CardPai srcCP) {
		return false;
	}

	public boolean work(WuJiang srcWJ, WuJiang tarWJ, CardPai tarCP) {
		if (srcWJ.zhuangBei.fangJu != null) {
			srcWJ.unstallZhuangBei(this);
		}
		srcWJ.installFangJu(this);
		return true;
	}

	public void listenIncreaseBloodEvent(CardPai srcCP) {
	}
}
