package alben.sgs.cardpai.instance;

import alben.sgs.android.R;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.FangJuCardPai;
import alben.sgs.type.Type;
import alben.sgs.wujiang.WuJiang;

public class RenWangDun extends FangJuCardPai {
	public RenWangDun(Type.CardPai na, Type.CardPaiClass c, int n, int imgNumber) {
		super(na, c, n, imgNumber);
		this.dispName = "仁王盾";
		this.zbImgNumber = R.drawable.zb_renwangdun;
	}

	public boolean defenceWork(WuJiang srcWJ, WuJiang tarWJ, CardPai srcCP) {

		if (srcCP instanceof Sha) {
			if (srcCP.clas == Type.CardPaiClass.HeiTao
					|| srcCP.clas == Type.CardPaiClass.MeiHua) {
				this.gameApp.libGameViewData.logInfo(tarWJ.dispName
						+ "有仁王盾,黑杀无效", Type.logDelay.NoDelay);
				return true;
			}
		}

		return false;
	}

}
