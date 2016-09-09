package alben.sgs.wujiang.instance;

import alben.sgs.android.dialog.SelectCardPaiFromWJDialog;
import alben.sgs.android.dialog.YesNoDialog;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.instance.ShanDian;
import alben.sgs.type.Type;
import alben.sgs.type.UpdateWJViewData;
import alben.sgs.type.WuJiangCardPaiData;
import alben.sgs.wujiang.WuJiang;
import alben.sgs.wujiang.instance.jineng.GangLie;
import alben.sgs.wujiang.instance.jineng.TianXiang;
import android.view.View;

public class XiaHouTing extends WuJiang {
	public XiaHouTing(Type.WuJiang n, Type.Country c, Type.Sex s, int b) {
		super(n, c, s, b);
		this.imageNumber = alben.sgs.android.R.drawable.wj_xiahoudun;
		this.jiNengDesc = "���ң���ÿ�ܵ�һ���˺����ɽ���һ���ж����������Ϊ���ң���Ŀ����Դ������ж�ѡһ�����������ƻ��ܵ��������ɵ�1���˺���";
		this.dispName = "�ĺ�?";
		this.jiNengN1 = "����";
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
		}
		// invoke super to set the correct JiNengBtnImage
		super.enableWuJiangJiNengBtn();
	}

	// jiNengN1
	public void listenIncreaseBloodEvent(CardPai srcCP) {

		WuJiang tarWJ = null;

		if (srcCP instanceof ShanDian) {
			return;
		}

		if (srcCP.name == Type.CardPai.WJJiNeng) {
			if (srcCP instanceof TianXiang) {
				tarWJ = ((TianXiang) srcCP).srcCP.shangHaiSrcWJ;
			} else if (srcCP instanceof GangLie) {
				tarWJ = ((GangLie) srcCP).srcCP.shangHaiSrcWJ;
			} else {
				// ???
				tarWJ = srcCP.shangHaiSrcWJ;
			}
		} else {
			if (srcCP.shangHaiSrcWJ == null) {
				this.gameApp.libGameViewData.logInfo(
						"Error:���Ҷ���Ϊ��,CP=" + srcCP, Type.logDelay.NoDelay);
				return;
			} else {
				tarWJ = srcCP.shangHaiSrcWJ;
			}
		}

		// ���ܸ����Լ�
		if (tarWJ.equals(this)) {
			this.gameApp.libGameViewData.logInfo("���Ҷ���Ϊ�Լ�,����",
					Type.logDelay.NoDelay);
			return;
		}

		if (tarWJ != null && tarWJ.state != Type.State.Dead) {
			boolean gl = false;
			if (this.tuoGuan) {
				if (this.isOpponent(tarWJ))
					gl = true;
			} else {
				this.gameApp.ynData.reset();
				this.gameApp.ynData.okTxt = "ȷ��";
				this.gameApp.ynData.cancelTxt = "ȡ��";
				this.gameApp.ynData.genInfo = tarWJ.dispName + "��������˺�,�Ƿ񷢶�"
						+ this.jiNengN1 + "?";

				YesNoDialog dlg = new YesNoDialog(
						this.gameApp.gameActivityContext, this.gameApp);
				dlg.showDialog();
				if (this.gameApp.ynData.result)
					gl = true;
			}

			if (!gl)
				return;

			GangLie gangLie = new GangLie(Type.CardPai.WJJiNeng,
					Type.CardPaiClass.nil, 0);
			gangLie.gameApp = this.gameApp;
			gangLie.srcCP = srcCP;
			gangLie.belongToWuJiang = this;
			gangLie.shangHaiSrcWJ = this;

			this.gameApp.libGameViewData
					.logInfo(this.dispName + "[" + this.jiNengN1 + "]"
							+ tarWJ.dispName, Type.logDelay.Delay);
			CardPai pdCP = this.gameApp.gameLogicData.cpHelper
					.popCardPaiForPanDing(tarWJ, gangLie);

			if (pdCP.clas == Type.CardPaiClass.HongTao) {
				return;
			}

			UpdateWJViewData item = new UpdateWJViewData();
			item.updateShouPaiNumber = true;

			boolean disSP = false;

			if (tarWJ.tuoGuan) {
				if (tarWJ.shouPai.size() >= 2) {
					CardPai dcp1 = tarWJ.shouPai.get(0);
					CardPai dcp2 = tarWJ.shouPai.get(1);
					dcp1.belongToWuJiang = null;
					dcp2.belongToWuJiang = null;
					tarWJ.detatchCardPaiFromShouPai(dcp1);
					tarWJ.detatchCardPaiFromShouPai(dcp2);

					this.gameApp.libGameViewData
							.logInfo(tarWJ.dispName + "��2������:" + dcp1 + " "
									+ dcp2, Type.logDelay.Delay);
					disSP = true;
				}
			} else {
				// not tuo guan
				if (tarWJ.shouPai.size() >= 2) {
					this.gameApp.ynData.reset();
					this.gameApp.ynData.okTxt = "ȷ��";
					this.gameApp.ynData.cancelTxt = "ȡ��";
					this.gameApp.ynData.genInfo = this.dispName + "����"
							+ gangLie.dispName + ",�Ƿ���2������?";

					YesNoDialog dlg = new YesNoDialog(
							this.gameApp.gameActivityContext, this.gameApp);
					dlg.showDialog();
					if (this.gameApp.ynData.result) {
						// select two sp to discard
						this.gameApp.wjDetailsViewData.reset();
						this.gameApp.wjDetailsViewData.selectedCardNumber = 2;
						this.gameApp.wjDetailsViewData.canViewShouPai = true;

						this.gameApp.wjDetailsViewData.selectedWJ = tarWJ;

						WuJiangCardPaiData wjCPData = new WuJiangCardPaiData();
						wjCPData.shouPai = tarWJ.shouPai;

						SelectCardPaiFromWJDialog dlg2 = new SelectCardPaiFromWJDialog(
								this.gameApp.gameActivityContext, this.gameApp,
								wjCPData);
						dlg2.showDialog();

						CardPai cp1 = this.gameApp.wjDetailsViewData.selectedCardPai1;
						CardPai cp2 = this.gameApp.wjDetailsViewData.selectedCardPai2;

						cp1.belongToWuJiang = null;
						cp2.belongToWuJiang = null;
						tarWJ.detatchCardPaiFromShouPai(cp1);
						tarWJ.detatchCardPaiFromShouPai(cp2);

						this.gameApp.libGameViewData.logInfo(tarWJ.dispName
								+ "��2������:" + cp1 + " " + cp2,
								Type.logDelay.Delay);

						disSP = true;
					}
				}
			}

			if (!disSP) {
				gangLie.shangHaiSrcWJ = this;
				tarWJ.increaseBlood(this, gangLie);
			}
		}
	}
}
