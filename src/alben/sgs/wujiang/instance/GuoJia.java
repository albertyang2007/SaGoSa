package alben.sgs.wujiang.instance;

import alben.sgs.android.R;
import alben.sgs.android.dialog.SelectCardPaiFromWJDialog;
import alben.sgs.android.dialog.YesNoDialog;
import alben.sgs.cardpai.CardPai;
import alben.sgs.type.Type;
import alben.sgs.type.UpdateWJViewData;
import alben.sgs.type.WuJiangCardPaiData;
import alben.sgs.wujiang.WuJiang;
import android.view.View;

public class GuoJia extends WuJiang {
	public GuoJia(Type.WuJiang n, Type.Country c, Type.Sex s, int b) {
		super(n, c, s, b);
		this.imageNumber = alben.sgs.android.R.drawable.wj_guojia;
		this.jiNengDesc = "1����ʣ�������ж�����Чʱ������������������\n"
				+ "2���żƣ���ÿ�ܵ�1���˺������������ƣ������е�һ�Ž�������һ����ɫ��Ȼ����һ�Ž�������һ����ɫ��\n"
				+ "���ж�����Чʱ���ж����������";
		this.dispName = "����";
		this.jiNengN1 = "���";
		this.jiNengN2 = "�ż�";
	}

	public void listenEnterHuiHeEvent() {
		super.listenEnterHuiHeEvent();
		this.enableWuJiangJiNengBtn();
	}

	public void enableWuJiangJiNengBtn() {
		if (!this.tuoGuan) {
			this.gameApp.libGameViewData.mJiNengBtn1
					.setVisibility(View.VISIBLE);
			this.gameApp.libGameViewData.mJiNengBtn1.setEnabled(false);
			this.gameApp.libGameViewData.mJiNengBtn1Txt.setText(this.jiNengN1);

			this.gameApp.libGameViewData.mJiNengBtn2
					.setVisibility(View.VISIBLE);
			this.gameApp.libGameViewData.mJiNengBtn2.setEnabled(false);
			this.gameApp.libGameViewData.mJiNengBtn2Txt.setText(this.jiNengN2);
		}
		// invoke super to set the correct JiNengBtnImage
		super.enableWuJiangJiNengBtn();
	}

	// �ż�
	public void listenIncreaseBloodEvent(CardPai srcCP) {
		boolean wj = false;

		if (this.tuoGuan) {
			wj = true;
		} else {
			this.gameApp.ynData.reset();
			this.gameApp.ynData.okTxt = "ȷ��";
			this.gameApp.ynData.cancelTxt = "ȡ��";
			this.gameApp.ynData.genInfo = "�Ƿ�ʹ��" + this.jiNengN2 + "��"
					+ Math.abs(srcCP.shangHaiN) * 2 + "����?";

			YesNoDialog dlg = new YesNoDialog(this.gameApp.gameActivityContext,
					this.gameApp);
			dlg.showDialog();
			wj = this.gameApp.ynData.result;
		}

		if (!wj)
			return;

		for (int i = 0; i < Math.abs(srcCP.shangHaiN); i++) {
			this.gameApp.gameLogicData.cpHelper.addCardPaiToWuJiang(this, 2);

			if (this.tuoGuan) {
				UpdateWJViewData item = new UpdateWJViewData();
				item.updateShouPaiNumber = true;
				this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(
						this, item);
			} else {
				this.gameApp.gameLogicData.wjHelper
						.updateWJ8ShouPaiToLibGameView();
			}

			this.gameApp.libGameViewData.logInfo(this.dispName + "["
					+ this.jiNengN2 + "]�������2����", Type.logDelay.NoDelay);

			// send cardpai to some wj
			this.sendCardPaiToWuJiang();
		}
	}

	public void sendCardPaiToWuJiang() {
		UpdateWJViewData item = new UpdateWJViewData();
		item.updateShouPaiNumber = true;

		if (this.tuoGuan) {
			if (this.friendList.size() == 0)
				return;

			if (this.shouPai.size() <= this.blood)
				return;

			WuJiang tarWJ = this.friendList.get(0);
			for (int i = 0; i < this.friendList.size(); i++) {
				WuJiang tmpWJ = this.friendList.get(i);
				if (tmpWJ.shouPai.size() < tmpWJ.blood) {
					tarWJ = tmpWJ;
					break;
				}
			}

			CardPai tarCP = this.shouPai.get(this.shouPai.size() - 2);

			this.detatchCardPaiFromShouPai(tarCP);

			tarCP.belongToWuJiang = tarWJ;
			tarCP.cpState = Type.CPState.ShouPai;
			tarWJ.shouPai.add(tarCP);

			if (tarWJ.tuoGuan) {
				this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(
						tarWJ, item);
			} else {
				this.gameApp.gameLogicData.wjHelper
						.updateWJ8ShouPaiToLibGameView();
			}

			this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(
					this, item);

			this.gameApp.libGameViewData.logInfo(this.dispName + "["
					+ this.jiNengN2 + "]��1�����ƽ���" + tarWJ.dispName,
					Type.logDelay.Delay);

		} else {
			this.gameApp.ynData.reset();
			this.gameApp.ynData.okTxt = "ȷ��";
			this.gameApp.ynData.cancelTxt = "ȡ��";
			this.gameApp.ynData.genInfo = "�Ƿ�" + this.jiNengN2 + "���ƽ���������ɫ?";

			YesNoDialog dlg = new YesNoDialog(this.gameApp.gameActivityContext,
					this.gameApp);
			dlg.showDialog();
			if (!this.gameApp.ynData.result)
				return;

			// select card pai first
			this.gameApp.wjDetailsViewData.reset();
			this.gameApp.wjDetailsViewData.selectedCardNumber = 2;
			this.gameApp.wjDetailsViewData.selectedCardN1Or2 = true;
			this.gameApp.wjDetailsViewData.canViewShouPai = true;

			this.gameApp.wjDetailsViewData.selectedWJ = this;

			WuJiangCardPaiData wjCPData = new WuJiangCardPaiData();

			wjCPData.shouPai.add(this.shouPai.get(this.shouPai.size() - 2));
			wjCPData.shouPai.add(this.shouPai.get(this.shouPai.size() - 1));

			SelectCardPaiFromWJDialog dlg2 = new SelectCardPaiFromWJDialog(
					this.gameApp.gameActivityContext, this.gameApp, wjCPData);
			dlg2.showDialog();

			CardPai tarCP1 = this.gameApp.wjDetailsViewData.selectedCardPai1;
			CardPai tarCP2 = this.gameApp.wjDetailsViewData.selectedCardPai2;

			if (tarCP1 == null && tarCP2 == null)
				return;

			// then select wj (due to limitiation, only one wj can be selected)
			gameApp.selectWJViewData.reset();
			gameApp.selectWJViewData.selectNumber = 1;
			// show wujiang can be selected
			WuJiang tarWJ = this.nextOne;
			while (!tarWJ.equals(this)) {
				gameApp.libGameViewData.imageWJs[tarWJ.imageViewIndex]
						.setBackgroundDrawable(gameApp.getResources()
								.getDrawable(R.drawable.bg_green));
				tarWJ.canSelect = true;
				tarWJ.clicked = false;
				tarWJ = tarWJ.nextOne;
			}
			// use UI for interaction
			gameApp.gameLogicData.userInterface.askUserSelectWuJiang(
					gameApp.gameLogicData.myWuJiang, "��ѡ��"
							+ gameApp.selectWJViewData.selectNumber + "���佫");

			tarWJ = gameApp.selectWJViewData.selectedWJ1;

			if (tarWJ == null)
				return;

			int sendCPN = 0;
			if (tarCP1 != null) {
				sendCPN++;
				this.detatchCardPaiFromShouPai(tarCP1);

				tarCP1.belongToWuJiang = tarWJ;
				tarCP1.cpState = Type.CPState.ShouPai;
				tarWJ.shouPai.add(tarCP1);
			}

			if (tarCP2 != null) {
				sendCPN++;
				this.detatchCardPaiFromShouPai(tarCP2);

				tarCP2.belongToWuJiang = tarWJ;
				tarCP2.cpState = Type.CPState.ShouPai;
				tarWJ.shouPai.add(tarCP2);
			}

			if (sendCPN > 0) {
				this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(
						tarWJ, item);
				this.gameApp.gameLogicData.wjHelper
						.updateWJ8ShouPaiToLibGameView();

				this.gameApp.libGameViewData.logInfo(this.dispName + "["
						+ this.jiNengN2 + "]��" + sendCPN + "�����ƽ���"
						+ tarWJ.dispName, Type.logDelay.NoDelay);
			}
		}
	}
}