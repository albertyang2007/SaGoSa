package alben.sgs.wujiang.instance;

import alben.sgs.android.R;
import alben.sgs.android.dialog.SelectCardPaiFromWJDialog;
import alben.sgs.android.dialog.YesNoDialog;
import alben.sgs.cardpai.CardPai;
import alben.sgs.type.Type;
import alben.sgs.type.WuJiangCardPaiData;
import alben.sgs.wujiang.WuJiang;
import android.view.View;

public class ZhangLiao extends WuJiang {
	public ZhangLiao(Type.WuJiang n, Type.Country c, Type.Sex s, int b) {
		super(n, c, s, b);
		this.imageNumber = alben.sgs.android.R.drawable.wj_zhangliao;
		this.jiNengDesc = "突袭：摸牌阶段，你可以放弃摸牌，然后从至多两名（至少一名）角色的手牌里各抽取一张牌。\n"
				+ "★摸牌阶段，你一旦发动突袭，就不能从牌堆获得牌。\n" + "★只剩一名其他角色时，你就只能选择这一名角色。\n"
				+ "★若此时其他任何人都没有手牌，你就不能发动突袭。";
		this.dispName = "张辽";
		this.jiNengN1 = "突袭";
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

	public void moPai() {
		boolean tx = false;
		WuJiang tarWJ1 = null;
		WuJiang tarWJ2 = null;

		boolean hasSP = false;
		for (int i = 0; i < this.opponentList.size(); i++) {
			if (this.opponentList.get(i).shouPai.size() > 0) {
				hasSP = true;
				break;
			}
		}

		for (int i = 0; i < this.friendList.size(); i++) {
			if (this.friendList.get(i).shouPai.size() > 0) {
				hasSP = true;
				break;
			}
		}

		if (hasSP) {
			if (this.tuoGuan) {
				if (this.opponentList.size() >= 2) {
					for (int i = 0; i < this.opponentList.size(); i++) {
						WuJiang tmpWJ = this.opponentList.get(i);
						if (tmpWJ.shouPai.size() >= 1) {
							if (tarWJ1 == null) {
								tarWJ1 = tmpWJ;
							} else if (tarWJ2 == null) {
								tarWJ2 = tmpWJ;
								break;
							}
						}
					}

					if (tarWJ1 != null && tarWJ1.shouPai.size() >= 1) {
						tx = true;

						CardPai cp1 = tarWJ1.shouPai.get(0);
						tarWJ1.detatchCardPaiFromShouPai(cp1);
						cp1.belongToWuJiang = this;
						cp1.cpState = Type.CPState.ShouPai;
						this.shouPai.add(cp1);
					}

					if (tarWJ2 != null && tarWJ2.shouPai.size() >= 1) {
						tx = true;

						CardPai cp2 = tarWJ2.shouPai.get(0);
						tarWJ2.detatchCardPaiFromShouPai(cp2);
						cp2.belongToWuJiang = this;
						cp2.cpState = Type.CPState.ShouPai;
						this.shouPai.add(cp2);
					}
				}
			} else {
				this.gameApp.ynData.reset();
				this.gameApp.ynData.okTxt = "确认";
				this.gameApp.ynData.cancelTxt = "取消";
				this.gameApp.ynData.genInfo = "是否发动" + this.jiNengN1 + "?";

				YesNoDialog dlg = new YesNoDialog(
						this.gameApp.gameActivityContext, this.gameApp);
				dlg.showDialog();

				if (this.gameApp.ynData.result) {

					tx = true;

					// then select two wj
					gameApp.selectWJViewData.reset();
					gameApp.selectWJViewData.selectNumber = 2;
					gameApp.selectWJViewData.selectedWJAtLeast1 = true;

					// show wujiang can be selected
					WuJiang tarWJ = this.nextOne;
					while (!tarWJ.equals(this)) {
						if (tarWJ.shouPai.size() > 0) {
							gameApp.libGameViewData.imageWJs[tarWJ.imageViewIndex]
									.setBackgroundDrawable(gameApp
											.getResources().getDrawable(
													R.drawable.bg_green));
							tarWJ.canSelect = true;
							tarWJ.clicked = false;
						}
						tarWJ = tarWJ.nextOne;
					}
					// use UI for interaction
					gameApp.gameLogicData.userInterface.askUserSelectWuJiang(
							gameApp.gameLogicData.myWuJiang, "请选择"
									+ gameApp.selectWJViewData.selectNumber
									+ "个武将");

					tarWJ1 = gameApp.selectWJViewData.selectedWJ1;
					tarWJ2 = gameApp.selectWJViewData.selectedWJ2;

					if (tarWJ1 != null) {

						// first select one shoupai
						this.gameApp.wjDetailsViewData.reset();
						this.gameApp.wjDetailsViewData.selectedCardNumber = 1;
						this.gameApp.wjDetailsViewData.canViewShouPai = false;
						this.gameApp.wjDetailsViewData.selectedWJ = tarWJ1;

						WuJiangCardPaiData wjCPData = new WuJiangCardPaiData();
						wjCPData.shouPai = tarWJ1.shouPai;

						SelectCardPaiFromWJDialog dlg2 = new SelectCardPaiFromWJDialog(
								this.gameApp.gameActivityContext, this.gameApp,
								wjCPData);
						dlg2.showDialog();

						CardPai cp1 = this.gameApp.wjDetailsViewData.selectedCardPai1;

						tarWJ1.detatchCardPaiFromShouPai(cp1);
						cp1.belongToWuJiang = this;
						cp1.cpState = Type.CPState.ShouPai;
						this.shouPai.add(cp1);
					}

					if (tarWJ2 != null) {
						// first select one shoupai
						this.gameApp.wjDetailsViewData.reset();
						this.gameApp.wjDetailsViewData.selectedCardNumber = 1;
						this.gameApp.wjDetailsViewData.canViewShouPai = false;
						this.gameApp.wjDetailsViewData.selectedWJ = tarWJ2;

						WuJiangCardPaiData wjCPData = new WuJiangCardPaiData();
						wjCPData.shouPai = tarWJ2.shouPai;

						SelectCardPaiFromWJDialog dlg2 = new SelectCardPaiFromWJDialog(
								this.gameApp.gameActivityContext, this.gameApp,
								wjCPData);
						dlg2.showDialog();

						CardPai cp2 = this.gameApp.wjDetailsViewData.selectedCardPai1;

						tarWJ2.detatchCardPaiFromShouPai(cp2);
						cp2.belongToWuJiang = this;
						cp2.cpState = Type.CPState.ShouPai;
						this.shouPai.add(cp2);
					}
				}
			}
		}

		if (tx) {
			String info = "";
			int cpN = 0;
			if (tarWJ1 != null) {
				info = tarWJ1.dispName;
				cpN++;
			}
			if (tarWJ2 != null) {
				info += " " + tarWJ2.dispName;
				cpN++;
			}

			this.gameApp.libGameViewData.logInfo(this.dispName + "摸牌" + "["
					+ this.jiNengN1 + "]" + info, Type.logDelay.Delay);

			return;
		} else {
			super.moPai();
			return;
		}
	}
}
