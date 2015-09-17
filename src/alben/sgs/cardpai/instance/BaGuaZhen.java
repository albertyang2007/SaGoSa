package alben.sgs.cardpai.instance;

import alben.sgs.android.R;
import alben.sgs.android.dialog.YesNoDialog;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.FangJuCardPai;
import alben.sgs.type.Type;
import alben.sgs.wujiang.WuJiang;

public class BaGuaZhen extends FangJuCardPai {
	public BaGuaZhen(Type.CardPai na, Type.CardPaiClass c, int n, int imgNumber) {
		super(na, c, n, imgNumber);
		this.dispName = "八卦阵";
		this.zbImgNumber = R.drawable.zb_baguazhen;
	}

	public CardPai chuShan(WuJiang srcWJ, WuJiang tarWJ, CardPai srcCP) {

		boolean yes = true;
		if (!tarWJ.tuoGuan) {
			this.gameApp.ynData.reset();
			this.gameApp.ynData.okTxt = "确认";
			this.gameApp.ynData.cancelTxt = "取消";
			this.gameApp.ynData.genInfo = "是否对" + srcWJ.dispName + "的"
					+ srcCP.dispName + "使用" + this.dispName + "?";

			YesNoDialog dlg = new YesNoDialog(this.gameApp.gameActivityContext,
					this.gameApp);
			dlg.showDialog();
			yes = this.gameApp.ynData.result;
		}
		if (yes) {
			CardPai pdCP = this.gameApp.gameLogicData.cpHelper
					.popCardPaiForPanDing(tarWJ, this);
			if (pdCP.clas == Type.CardPaiClass.FangPian
					|| pdCP.clas == Type.CardPaiClass.HongTao) {
				this.gameApp.libGameViewData.logInfo(this.dispName + "判定成功,"
						+ tarWJ.dispName + "打出无影闪", Type.logDelay.Delay);

				return pdCP;
			} else {
				this.gameApp.libGameViewData.logInfo(this.dispName + "判定失败",
						Type.logDelay.Delay);
			}
		}
		return null;
	}
}
