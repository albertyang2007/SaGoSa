package alben.sgs.cardpai;

import alben.sgs.android.R;
import alben.sgs.type.Type;
import alben.sgs.wujiang.WuJiang;

public class WuQiCardPai extends ZhuangBeiCardPai {
	public int zbImgNumber = R.drawable.zb_wuqi;
	public int distance = 0;

	public WuQiCardPai(Type.CardPai na, Type.CardPaiClass c, int n,
			int imgNumber, int d) {
		super(na, c, n, imgNumber);
		this.distance = d;
	}

	public boolean work(WuJiang srcWJ, WuJiang tarWJ, CardPai tarCP) {

		// this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "³öÅÆ:" + this);

		if (srcWJ.zhuangBei.wuQi != null) {
			srcWJ.unstallZhuangBei(this);
		}
		srcWJ.installWuQi(this);
		return true;
	}

	public void listenShaEvent(WuJiang srcWJ, WuJiang tarWJ, CardPai srcCP) {

	}

	public void listenShanEvent(WuJiang srcWJ, WuJiang tarWJ, CardPai srcCP) {

	}

	public boolean listenMingZhongEvent(WuJiang srcWJ, WuJiang tarWJ,
			CardPai srcCP) {
		return true;
	}
}
