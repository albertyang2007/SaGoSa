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
		this.dispName = "������";
		this.zbImgNumber = R.drawable.zb_baguazhen;
	}

	public CardPai chuShan(WuJiang srcWJ, WuJiang tarWJ, CardPai srcCP) {

		boolean yes = true;
		if (!tarWJ.tuoGuan) {
			this.gameApp.ynData.reset();
			this.gameApp.ynData.okTxt = "ȷ��";
			this.gameApp.ynData.cancelTxt = "ȡ��";
			this.gameApp.ynData.genInfo = "�Ƿ��" + srcWJ.dispName + "��"
					+ srcCP.dispName + "ʹ��" + this.dispName + "?";

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
				this.gameApp.libGameViewData.logInfo(this.dispName + "�ж��ɹ�,"
						+ tarWJ.dispName + "�����Ӱ��", Type.logDelay.Delay);

				return pdCP;
			} else {
				this.gameApp.libGameViewData.logInfo(this.dispName + "�ж�ʧ��",
						Type.logDelay.Delay);
			}
		}
		return null;
	}
}
