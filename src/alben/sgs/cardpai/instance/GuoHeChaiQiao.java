package alben.sgs.cardpai.instance;

import alben.sgs.android.R;
import alben.sgs.android.dialog.SelectCardPaiFromWJDialog;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.JinNangCardPai;
import alben.sgs.cardpai.ZhuangBeiCardPai;
import alben.sgs.type.Type;
import alben.sgs.type.Type.JinNangApplyTo;
import alben.sgs.type.UpdateWJViewData;
import alben.sgs.type.WuJiangCardPaiData;
import alben.sgs.wujiang.WuJiang;

public class GuoHeChaiQiao extends JinNangCardPai {
	public GuoHeChaiQiao(Type.CardPai na, Type.CardPaiClass c, int n,
			int imgNumber, JinNangApplyTo at) {
		super(na, c, n, imgNumber, at);
		this.dispName = "过河拆桥";
		this.selectTarWJNumber = 1;
	}

	public boolean work(WuJiang srcWJ, WuJiang tarWJ, CardPai tarCP1) {
		this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "对"
				+ tarWJ.dispName + "使用" + this, Type.logDelay.Delay);

		this.listenPreWorkEvent(srcWJ, tarWJ, tarCP1);

		if (srcWJ.askForWuXieKeJi(srcWJ, this, tarWJ, this)) {
			this.gameApp.libGameViewData.logInfo(this.dispName + "被无懈了",
					Type.logDelay.NoDelay);
		} else {
			CardPai tarCP = null;
			if (!srcWJ.tuoGuan) {
				this.gameApp.wjDetailsViewData.reset();
				this.gameApp.wjDetailsViewData.selectedCardNumber = 1;
				this.gameApp.wjDetailsViewData.selectedWJ = tarWJ;

				WuJiangCardPaiData wjCPData = new WuJiangCardPaiData();
				wjCPData.panDingPai = tarWJ.panDingPai;
				wjCPData.zhuangBei.wuQi = tarWJ.zhuangBei.wuQi;
				wjCPData.zhuangBei.fangJu = tarWJ.zhuangBei.fangJu;
				wjCPData.zhuangBei.jianYiMa = tarWJ.zhuangBei.jianYiMa;
				wjCPData.zhuangBei.jiaYiMa = tarWJ.zhuangBei.jiaYiMa;
				wjCPData.shouPai = tarWJ.shouPai;

				SelectCardPaiFromWJDialog dlg = new SelectCardPaiFromWJDialog(
						this.gameApp.gameActivityContext, this.gameApp,
						wjCPData);

				dlg.showDialog();
				tarCP = this.gameApp.wjDetailsViewData.selectedCardPai1;
			} else {
				// for AI:
				tarCP = this.selectCPForAI(srcWJ, tarWJ);
			}

			if (tarCP == null) {
				this.gameApp.libGameViewData.logInfo("Error:没有牌可拆",
						Type.logDelay.Delay);
				return false;
			}

			if (tarCP.cpState == Type.CPState.pandDingPai) {
				tarWJ.panDingPai.remove(tarCP);
			} else if (tarCP.cpState == Type.CPState.ShouPai) {
				tarWJ.detatchCardPaiFromShouPai(tarCP);
			} else if (tarCP.cpState == Type.CPState.wuQiPai
					|| tarCP.cpState == Type.CPState.fangJuPai
					|| tarCP.cpState == Type.CPState.jiaYiMaPai
					|| tarCP.cpState == Type.CPState.jianYiMaPai) {
				tarWJ.unstallZhuangBei((ZhuangBeiCardPai) tarCP);
			}

			this.gameApp.libGameViewData.logInfo(
					tarWJ.dispName + "被拆掉" + tarCP, Type.logDelay.Delay);

			tarCP.belongToWuJiang = null;
			tarCP.cpState = Type.CPState.FeiPaiDui;
			// update view
			UpdateWJViewData item = new UpdateWJViewData();
			item.updateAll = true;
			this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(
					tarWJ, item);

			if (!tarWJ.tuoGuan) {
				this.gameApp.gameLogicData.wjHelper
						.updateWJ8ShouPaiToLibGameView();
			}
		}
		return true;
	}

	public void onClickUpdateView() {

		if (this.gameApp.gameLogicData.myWuJiang.state != Type.State.ChuPai
				|| this.gameApp.gameLogicData.askForPai != Type.CardPai.notNil) {
			return;
		}

		// set the select wj number
		this.gameApp.selectWJViewData.reset();
		this.gameApp.selectWJViewData.selectNumber = this.selectTarWJNumber;

		// 当被点击时候，显示可以应用的WJ
		if (this.belongToWuJiang != null) {
			String canSelectStr = "";
			WuJiang tarWJ = this.belongToWuJiang.nextOne;
			while (!tarWJ.equals(this.belongToWuJiang)) {
				tarWJ.clicked = false;
				tarWJ.canSelect = false;
				if (tarWJ.shouPai.size() > 0 || tarWJ.panDingPai.size() > 0
						|| tarWJ.zhuangBei.containsZB()) {
					// set the tarWJ to gray
					this.gameApp.libGameViewData.imageWJs[tarWJ.imageViewIndex]
							.setBackgroundDrawable(this.gameApp.getResources()
									.getDrawable(R.drawable.bg_green));
					// set can reach flag
					tarWJ.canSelect = true;
					canSelectStr += tarWJ.dispName + " ";
				}
				tarWJ = tarWJ.nextOne;
			}

			if (canSelectStr.trim().length() <= 0)
				this.gameApp.libGameViewData.logInfo("没有可拆对象",
						Type.logDelay.NoDelay);

		} else {
			this.gameApp.libGameViewData.logInfo("Error:此卡牌不属于任何武将",
					Type.logDelay.Delay);
		}
	}

	public void onUnClickUpdateView() {

		if (this.gameApp.gameLogicData.myWuJiang.state != Type.State.ChuPai
				|| this.gameApp.gameLogicData.askForPai != Type.CardPai.notNil) {
			return;
		}

		if (this.belongToWuJiang != null) {
			WuJiang tarWJ = this.belongToWuJiang.nextOne;
			while (!tarWJ.equals(this.belongToWuJiang)) {
				// set the tarWJ to original background
				this.gameApp.libGameViewData.imageWJs[tarWJ.imageViewIndex]
						.setBackgroundDrawable(this.gameApp.getResources()
								.getDrawable(R.drawable.bg_black));
				tarWJ.canSelect = false;
				tarWJ.clicked = false;
				tarWJ = tarWJ.nextOne;
			}
		}
	}

	// click wj and set data
	public void onClickWJUpdateView(WuJiang curClickWJ) {
		gameApp.selectWJViewData.selectedWJ1 = curClickWJ;
	}

	// un-click wj and re-set data
	public void onUnClickWJUpdateView(WuJiang curClickWJ) {
		gameApp.selectWJViewData.selectedWJ1 = null;
	}

	public boolean canChuPai() {

		if (this.gameApp.gameLogicData.myWuJiang.state == Type.State.ChuPai
				&& this.gameApp.gameLogicData.askForPai == Type.CardPai.notNil) {

			if (this.gameApp.selectWJViewData.selectNumber >= 1
					&& this.gameApp.selectWJViewData.selectedWJ1 != null) {
				return true;
			} else {
				this.gameApp.libGameViewData.logInfo("Error:不满足出牌条件",
						Type.logDelay.Delay);
				return false;
			}
		} else {
			return true;
		}
	}

	public void selectTarWJForAI() {
		// run and select one target wujiang for this cardpai
		// by default will select one oppt
		if (this.belongToWuJiang != null) {
			this.tarWJForAI = null;

			// 1 check my friend has panDing cardPai or not
			for (int i = 0; i < this.belongToWuJiang.friendList.size(); i++) {
				WuJiang tmpWJ = this.belongToWuJiang.friendList.get(i);
				CardPai tmpCP = this.selectCPForAI(this.belongToWuJiang, tmpWJ);
				if (tmpCP != null) {
					this.tarWJForAI = tmpWJ;
					return;
				}
			}

			// 2 then select from Oppt
			// first try fangJu
			for (int i = 0; i < this.belongToWuJiang.opponentList.size(); i++) {
				WuJiang tmpWJ = this.belongToWuJiang.opponentList.get(i);
				CardPai tmpCP = this.selectCPForAI(this.belongToWuJiang, tmpWJ);
				if (tmpCP != null) {
					if (tmpCP.cpState == Type.CPState.fangJuPai) {
						this.tarWJForAI = tmpWJ;
						return;
					}
				}
			}

			// then try wuQi
			for (int i = 0; i < this.belongToWuJiang.opponentList.size(); i++) {
				WuJiang tmpWJ = this.belongToWuJiang.opponentList.get(i);
				CardPai tmpCP = this.selectCPForAI(this.belongToWuJiang, tmpWJ);
				if (tmpCP != null) {
					if (tmpCP.cpState == Type.CPState.wuQiPai) {
						this.tarWJForAI = tmpWJ;
						return;
					}
				}
			}

			// then try +1 ma
			for (int i = 0; i < this.belongToWuJiang.opponentList.size(); i++) {
				WuJiang tmpWJ = this.belongToWuJiang.opponentList.get(i);
				CardPai tmpCP = this.selectCPForAI(this.belongToWuJiang, tmpWJ);
				if (tmpCP != null) {
					if (tmpCP.cpState == Type.CPState.jiaYiMaPai) {
						this.tarWJForAI = tmpWJ;
						return;
					}
				}
			}

			// then try shou pai
			for (int i = 0; i < this.belongToWuJiang.opponentList.size(); i++) {
				WuJiang tmpWJ = this.belongToWuJiang.opponentList.get(i);
				CardPai tmpCP = this.selectCPForAI(this.belongToWuJiang, tmpWJ);
				if (tmpCP != null) {
					if (tmpCP.cpState == Type.CPState.ShouPai) {
						this.tarWJForAI = tmpWJ;
						return;
					}
				}
			}

			// then try -1 ma
			for (int i = 0; i < this.belongToWuJiang.opponentList.size(); i++) {
				WuJiang tmpWJ = this.belongToWuJiang.opponentList.get(i);
				CardPai tmpCP = this.selectCPForAI(this.belongToWuJiang, tmpWJ);
				if (tmpCP != null) {
					if (tmpCP.cpState == Type.CPState.jianYiMaPai) {
						this.tarWJForAI = tmpWJ;
						return;
					}
				}
			}

			// finally no tarWJ??
			// this.tarWJForAI = null;
		}
	}

	public CardPai selectCPForAI(WuJiang srcWJ, WuJiang tarWJ) {
		CardPai tarCP = null;
		if (srcWJ.isFriend(tarWJ)) {
			for (int i = 0; i < tarWJ.panDingPai.size(); i++) {
				CardPai cp = tarWJ.panDingPai.get(i);
				if (cp instanceof LeBuShiShu || cp instanceof BingLiangCunDuan) {
					tarCP = cp;
					break;
				}
			}
		} else {
			// is Oppt
			if (tarWJ.zhuangBei.fangJu != null) {
				if (tarWJ.zhuangBei.fangJu instanceof BaiYinShiZi) {
					if (tarWJ.blood == tarWJ.getMaxBlood()) {
						return tarWJ.zhuangBei.fangJu;
					}
				} else {
					return tarWJ.zhuangBei.fangJu;
				}
			}

			if (tarWJ.zhuangBei.jiaYiMa != null) {
				return tarWJ.zhuangBei.jiaYiMa;
			}

			if (tarWJ.zhuangBei.wuQi != null) {
				return tarWJ.zhuangBei.wuQi;
			}

			if (tarWJ.shouPai.size() > 0) {
				return tarWJ.shouPai.get(0);
			}

			if (tarWJ.zhuangBei.jianYiMa != null) {
				return tarWJ.zhuangBei.jianYiMa;
			}
		}
		return tarCP;
	}
}
