package alben.sgs.cardpai.instance;

import alben.sgs.android.R;
import alben.sgs.android.dialog.YesNoDialog;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.WuQiCardPai;
import alben.sgs.type.Type;
import alben.sgs.wujiang.WuJiang;

public class ZhuQueYuShan extends WuQiCardPai {
	public boolean trigger = false;

	public ZhuQueYuShan(Type.CardPai na, Type.CardPaiClass c, int n,
			int imgNumber, int d) {
		super(na, c, n, imgNumber, d);
		this.dispName = "��ȸ����";
		this.zbImgNumber = R.drawable.zb_zhuqueyushan;
	}

	public void reset() {
		super.reset();
		this.trigger = false;
	}

	public void listenShaEvent(WuJiang srcWJ, WuJiang tarWJ, CardPai srcCP) {

		if (srcWJ.state != Type.State.ChuPai)
			return;

		// only for normal sha, not for leisha, huosha
		if (srcCP.name != Type.CardPai.Sha)
			return;

		if (srcWJ.tuoGuan) {
			this.trigger = true;
		} else {
			this.gameApp.ynData.reset();
			this.gameApp.ynData.okTxt = "ȷ��";
			this.gameApp.ynData.cancelTxt = "ȡ��";
			this.gameApp.ynData.genInfo = "�Ƿ񷢶�" + this.dispName + "���湦Ч?";

			YesNoDialog dlg = new YesNoDialog(this.gameApp.gameActivityContext,
					this.gameApp);
			dlg.showDialog();
			this.trigger = this.gameApp.ynData.result;
		}

		if (this.trigger) {
			srcCP.linkImpact = true;
		}
	}
}
