package alben.sgs.cardpai.instance;

import alben.sgs.android.R;
import alben.sgs.android.dialog.SelectCardPaiFromWJDialog;
import alben.sgs.android.dialog.YesNoDialog;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.WuQiCardPai;
import alben.sgs.type.Type;
import alben.sgs.type.UpdateWJViewData;
import alben.sgs.type.WuJiangCardPaiData;
import alben.sgs.wujiang.WuJiang;

public class ChiXiongShuanPeiJian extends WuQiCardPai {
	public ChiXiongShuanPeiJian(Type.CardPai na, Type.CardPaiClass c, int n,
			int imgNumber, int d) {
		super(na, c, n, imgNumber, d);
		this.dispName = "����˫�ɽ�";
		this.zbImgNumber = R.drawable.zb_cixiongshuanggujian;
	}

	public void listenShaEvent(WuJiang srcWJ, WuJiang tarWJ, CardPai srcCP) {
		if (srcWJ.state != Type.State.ChuPai)
			return;

		if (!(srcCP instanceof Sha))
			return;

		if (srcWJ.sex == tarWJ.sex)
			return;

		srcWJ.specialChuPaiReason = "[" + this.dispName + "]";

		UpdateWJViewData item = new UpdateWJViewData();
		item.updateShouPaiNumber = true;

		boolean srcWJMoPai = true;

		if (srcWJ.tuoGuan) {
			if (!tarWJ.tuoGuan) {
				if (tarWJ.shouPai.size() > 0) {
					this.gameApp.ynData.reset();
					this.gameApp.ynData.okTxt = "��һ������";
					this.gameApp.ynData.cancelTxt = "�öԷ�����";
					this.gameApp.ynData.genInfo = srcWJ.dispName + "ʹ��"
							+ this.dispName + "ɱ��,ѡ����һ�����ƻ����öԷ���һ����?";

					YesNoDialog dlg1 = new YesNoDialog(
							this.gameApp.gameActivityContext, this.gameApp);
					dlg1.showDialog();

					if (this.gameApp.ynData.result) {
						// tarWJ qi pai
						this.gameApp.wjDetailsViewData.reset();
						this.gameApp.wjDetailsViewData.selectedCardNumber = 1;
						this.gameApp.wjDetailsViewData.canViewShouPai = true;

						this.gameApp.wjDetailsViewData.selectedWJ = tarWJ;

						WuJiangCardPaiData wjCPData = new WuJiangCardPaiData();
						wjCPData.shouPai = tarWJ.shouPai;

						SelectCardPaiFromWJDialog dlg2 = new SelectCardPaiFromWJDialog(
								this.gameApp.gameActivityContext, this.gameApp,
								wjCPData);

						dlg2.showDialog();

						CardPai cp1 = this.gameApp.wjDetailsViewData.selectedCardPai1;
						if (cp1 != null) {
							tarWJ.detatchCardPaiFromShouPai(cp1);
							cp1.belongToWuJiang = null;

							this.gameApp.libGameViewData.logInfo(tarWJ.dispName
									+ "����1������" + cp1, Type.logDelay.Delay);
							srcWJMoPai = false;
						}
					} else {
						// srcWJ mo pai
					}
				} else {
					// srcWJ mo pai
				}
			} else {
				// tarWJ is tuo guan, for AI
				if (tarWJ.shouPai.size() >= tarWJ.blood) {
					// tarWJ qi pai
					srcWJMoPai = false;
					// tarWJ qi pai
					CardPai cp = tarWJ.shouPai.get(0);
					tarWJ.detatchCardPaiFromShouPai(cp);
					cp.belongToWuJiang = null;

					this.gameApp.libGameViewData.logInfo(tarWJ.dispName
							+ "����1������" + cp, Type.logDelay.Delay);

				} else {
					// srcWJ mo pai
				}
			}
		} else {
			// for myWuJiang is srcWJ
			this.gameApp.ynData.reset();
			this.gameApp.ynData.okTxt = "ȷ��";
			this.gameApp.ynData.cancelTxt = "ȡ��";
			this.gameApp.ynData.genInfo = "�Ƿ񷢶�" + this.dispName + "��Ч?";

			YesNoDialog dlg1 = new YesNoDialog(
					this.gameApp.gameActivityContext, this.gameApp);
			dlg1.showDialog();

			if (this.gameApp.ynData.result) {
				// ask tarWJ qipai or not
				// qi pai or let dui fang mo pai
				if (tarWJ.shouPai.size() >= tarWJ.blood) {
					srcWJMoPai = false;
					// tarWJ qi pai
					CardPai cp = tarWJ.shouPai.get(0);
					tarWJ.detatchCardPaiFromShouPai(cp);
					cp.belongToWuJiang = null;

					this.gameApp.libGameViewData.logInfo(tarWJ.dispName
							+ "����1������" + cp, Type.logDelay.Delay);
				}
			} else {
				srcWJMoPai = false;
			}
		}

		if (srcWJMoPai) {
			CardPai cp = this.gameApp.gameLogicData.cpHelper.popCardPai();
			cp.belongToWuJiang = srcWJ;
			cp.cpState = Type.CPState.ShouPai;
			srcWJ.shouPai.add(cp);
			if (srcWJ.tuoGuan) {
				this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(
						srcWJ, item);
			} else {
				this.gameApp.gameLogicData.wjHelper
						.updateWJ8ShouPaiToLibGameView();
			}
			this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "����1����",
					Type.logDelay.Delay);
		}
	}
}
