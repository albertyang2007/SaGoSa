package alben.sgs.cardpai;

import alben.sgs.android.R;
import alben.sgs.type.Type;
import alben.sgs.wujiang.WuJiang;

public class MaCardPai extends ZhuangBeiCardPai {
	public int zbImgNumber = R.drawable.zb_jiayima;
	public int distance = 0;

	public MaCardPai(Type.CardPai na, Type.CardPaiClass c, int n,
			int imgNumber, int d) {
		super(na, c, n, imgNumber);
		this.distance = d;
	}

	public boolean work(WuJiang srcWJ, WuJiang tarWJ, CardPai tarCP) {

		// this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "³öÅÆ:" + this);

		if (this.distance == 1) {
			if (srcWJ.zhuangBei.jiaYiMa != null) {
				srcWJ.unstallZhuangBei(this);
			}
			srcWJ.installJiaYiMa(this);
		} else {
			if (srcWJ.zhuangBei.jianYiMa != null) {
				srcWJ.unstallZhuangBei(this);
			}
			this.belongToWuJiang = srcWJ;
			srcWJ.installJianYiMa(this);
		}

		return true;
	}
}
