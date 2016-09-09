package alben.sgs.cardpai.instance;

import alben.sgs.android.R;
import alben.sgs.android.dialog.SelectCardPaiFromWJDialog;
import alben.sgs.android.dialog.YesNoDialog;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.WuQiCardPai;
import alben.sgs.cardpai.ZhuangBeiCardPai;
import alben.sgs.type.Type;
import alben.sgs.type.WuJiangCardPaiData;
import alben.sgs.wujiang.WuJiang;

public class QiLingGong extends WuQiCardPai {
	public QiLingGong(Type.CardPai na, Type.CardPaiClass c, int n,
			int imgNumber, int d) {
		super(na, c, n, imgNumber, d);
		this.dispName = "���빭";
		this.zbImgNumber = R.drawable.zb_qilingong;
	}

	public boolean listenMingZhongEvent(WuJiang srcWJ, WuJiang tarWJ,
			CardPai srcCP) {
		boolean mzRtn = true;

		if (srcWJ.state != Type.State.ChuPai)
			return mzRtn;

		if (!(srcCP instanceof Sha))
			return mzRtn;

		if (tarWJ.zhuangBei.jiaYiMa == null && tarWJ.zhuangBei.jianYiMa == null)
			return mzRtn;

		if (srcWJ.tuoGuan) {

			// �������ʹ��������,tarWJ�Ѿ��ı�,�ܿ������ʱ��srcWJ��tarWJ��friend
			if (srcWJ.isFriend(tarWJ))
				return mzRtn;

			if (tarWJ.zhuangBei.jiaYiMa != null) {
				tarWJ.unstallZhuangBei(tarWJ.zhuangBei.jiaYiMa);
				this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "������"
						+ tarWJ.dispName + "��" + tarWJ.zhuangBei.jiaYiMa,
						Type.logDelay.Delay);
			} else if (tarWJ.zhuangBei.jianYiMa != null) {
				tarWJ.unstallZhuangBei(tarWJ.zhuangBei.jianYiMa);
				this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "������"
						+ tarWJ.dispName + "��" + tarWJ.zhuangBei.jianYiMa,
						Type.logDelay.Delay);
			}

			// update view
			tarWJ.updateZhuangBeiView();

		} else {
			this.gameApp.ynData.reset();
			this.gameApp.ynData.okTxt = "ȷ��";
			this.gameApp.ynData.cancelTxt = "ȡ��";
			this.gameApp.ynData.genInfo = "�Ƿ�ʹ��" + this.dispName + "�����Է�����?";

			YesNoDialog dlg1 = new YesNoDialog(
					this.gameApp.gameActivityContext, this.gameApp);
			dlg1.showDialog();

			if (!this.gameApp.ynData.result)
				return mzRtn;

			this.gameApp.wjDetailsViewData.reset();
			this.gameApp.wjDetailsViewData.selectedCardNumber = 1;

			this.gameApp.wjDetailsViewData.selectedWJ = tarWJ;

			WuJiangCardPaiData wjCPData = new WuJiangCardPaiData();
			wjCPData.zhuangBei.jianYiMa = tarWJ.zhuangBei.jianYiMa;
			wjCPData.zhuangBei.jiaYiMa = tarWJ.zhuangBei.jiaYiMa;

			SelectCardPaiFromWJDialog dlg2 = new SelectCardPaiFromWJDialog(
					this.gameApp.gameActivityContext, this.gameApp, wjCPData);

			dlg2.showDialog();

			CardPai tarCP = this.gameApp.wjDetailsViewData.selectedCardPai1;

			if (tarCP.cpState == Type.CPState.jiaYiMaPai
					&& tarWJ.zhuangBei.jiaYiMa != null) {
				tarWJ.unstallZhuangBei((ZhuangBeiCardPai) tarCP);

				this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "������"
						+ tarWJ.dispName + "��" + tarWJ.zhuangBei.jiaYiMa,
						Type.logDelay.Delay);
			} else if (tarCP.cpState == Type.CPState.jianYiMaPai
					&& tarWJ.zhuangBei.jianYiMa != null) {
				tarWJ.unstallZhuangBei((ZhuangBeiCardPai) tarCP);

				this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "������"
						+ tarWJ.dispName + "��" + tarWJ.zhuangBei.jianYiMa,
						Type.logDelay.Delay);
			}

			// update view
			tarWJ.updateZhuangBeiView();
		}

		return mzRtn;
	}
}
