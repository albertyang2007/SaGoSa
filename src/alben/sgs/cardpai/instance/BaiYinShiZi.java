package alben.sgs.cardpai.instance;

import alben.sgs.android.R;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.FangJuCardPai;
import alben.sgs.type.Type;

public class BaiYinShiZi extends FangJuCardPai {
	public BaiYinShiZi(Type.CardPai na, Type.CardPaiClass c, int n,
			int imgNumber) {
		super(na, c, n, imgNumber);
		this.dispName = "白银狮子";
		this.shangHaiN = 1;
		this.zbImgNumber = R.drawable.zb_baiyinshizi;
	}

	public void reset() {
		super.reset();
		this.shangHaiN = 1;
	}

	public void listenIncreaseBloodEvent(CardPai srcCP) {
		if (srcCP.shangHaiN <= -2) {
			this.gameApp.libGameViewData.logInfo(this.belongToWuJiang.dispName
					+ "有白银狮子护身", Type.logDelay.Delay);
			srcCP.shangHaiN = -1;
		}
	}
}
