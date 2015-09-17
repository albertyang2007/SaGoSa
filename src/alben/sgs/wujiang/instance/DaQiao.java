package alben.sgs.wujiang.instance;

import alben.sgs.android.R;
import alben.sgs.android.dialog.SelectCardPaiFromWJDialog;
import alben.sgs.android.dialog.YesNoDialog;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.ZhuangBeiCardPai;
import alben.sgs.type.Type;
import alben.sgs.type.WuJiangCardPaiData;
import alben.sgs.wujiang.WuJiang;
import alben.sgs.wujiang.instance.jineng.GuoSe;
import android.view.View;

public class DaQiao extends WuJiang {
	public DaQiao(Type.WuJiang n, Type.Country c, Type.Sex s, int b) {
		super(n, c, s, b);
		this.imageNumber = alben.sgs.android.R.drawable.wj_daqiao;
		this.jiNengDesc = "1、国色：出牌阶段，你可以将你的任意方块花色的牌当【乐不思蜀】使用。\n"
				+ "2、流离：当你成为【杀】的目标时，你可以弃一张牌，并将此【杀】转移给你攻击范围内的另一名角色（该角色不得是【杀】的使用者）。";
		this.dispName = "大乔";
		this.jiNengN1 = "国色";
		this.jiNengN2 = "流离";
	}

	public void listenEnterHuiHeEvent() {
		super.listenEnterHuiHeEvent();
		this.enableWuJiangJiNengBtn();
	}

	public void enableWuJiangJiNengBtn() {
		if (!this.tuoGuan) {
			this.gameApp.libGameViewData.mJiNengBtn1
					.setVisibility(View.VISIBLE);
			this.gameApp.libGameViewData.mJiNengBtn1.setEnabled(true);
			this.gameApp.libGameViewData.mJiNengBtn1Txt.setText(this.jiNengN1);

			this.gameApp.libGameViewData.mJiNengBtn2
					.setVisibility(View.VISIBLE);
			this.gameApp.libGameViewData.mJiNengBtn2.setEnabled(false);
			this.gameApp.libGameViewData.mJiNengBtn2Txt.setText(this.jiNengN2);
		}
		// invoke super to set the correct JiNengBtnImage
		super.enableWuJiangJiNengBtn();
	}

	public void handleJiNengBtnEvent(int eventID) {

		switch (eventID) {
		case R.id.JiNeng1: {
			if (this.state != Type.State.ChuPai)
				return;

			boolean hasFPCP = false;
			if (this.selectFromShouPaiByClass(Type.CardPaiClass.FangPian) != null) {
				hasFPCP = true;
			}
			if (this.zhuangBei.wuQi != null
					&& this.zhuangBei.wuQi.clas == Type.CardPaiClass.FangPian) {
				hasFPCP = true;
			}
			if (this.zhuangBei.fangJu != null
					&& this.zhuangBei.fangJu.clas == Type.CardPaiClass.FangPian) {
				hasFPCP = true;
			}
			if (this.zhuangBei.jianYiMa != null
					&& this.zhuangBei.jianYiMa.clas == Type.CardPaiClass.FangPian) {
				hasFPCP = true;
			}
			if (this.zhuangBei.jiaYiMa != null
					&& this.zhuangBei.jiaYiMa.clas == Type.CardPaiClass.FangPian) {
				hasFPCP = true;
			}

			if (!hasFPCP) {
				this.gameApp.libGameViewData.logInfo("没有方片牌不能发动"
						+ this.jiNengN1, Type.logDelay.NoDelay);
				return;
			}

			boolean allInLBSS = true;
			WuJiang tarWJ = this.nextOne;
			while (!tarWJ.equals(this)) {
				if (!tarWJ.hasLBSSInPanDindArea()) {
					allInLBSS = false;
					break;
				}
				tarWJ = tarWJ.nextOne;
			}

			if (allInLBSS) {
				this.gameApp.libGameViewData.logInfo("没有可以乐的武将",
						Type.logDelay.NoDelay);
				return;
			}

			this.gameApp.ynData.reset();
			this.gameApp.ynData.okTxt = "确认";
			this.gameApp.ynData.cancelTxt = "取消";
			this.gameApp.ynData.genInfo = "是否使用" + this.jiNengN1 + "?";

			YesNoDialog dlg = new YesNoDialog(this.gameApp.gameActivityContext,
					this.gameApp);
			dlg.showDialog();
			if (!this.gameApp.ynData.result)
				return;

			// select card pai first
			this.gameApp.wjDetailsViewData.reset();
			this.gameApp.wjDetailsViewData.selectedCardNumber = 1;
			this.gameApp.wjDetailsViewData.canViewShouPai = true;

			this.gameApp.wjDetailsViewData.selectedWJ = this;

			WuJiangCardPaiData wjCPData = new WuJiangCardPaiData();
			if (this.zhuangBei.wuQi != null
					&& this.zhuangBei.wuQi.clas == Type.CardPaiClass.FangPian)
				wjCPData.zhuangBei.wuQi = this.zhuangBei.wuQi;
			if (this.zhuangBei.jianYiMa != null
					&& this.zhuangBei.jianYiMa.clas == Type.CardPaiClass.FangPian)
				wjCPData.zhuangBei.jianYiMa = this.zhuangBei.jianYiMa;
			if (this.zhuangBei.jiaYiMa != null
					&& this.zhuangBei.jiaYiMa.clas == Type.CardPaiClass.FangPian)
				wjCPData.zhuangBei.jiaYiMa = this.zhuangBei.jiaYiMa;
			if (this.zhuangBei.fangJu != null
					&& this.zhuangBei.fangJu.clas == Type.CardPaiClass.FangPian)
				wjCPData.zhuangBei.fangJu = this.zhuangBei.fangJu;

			for (int i = 0; i < this.shouPai.size(); i++) {
				if (this.shouPai.get(i).clas == Type.CardPaiClass.FangPian)
					wjCPData.shouPai.add(this.shouPai.get(i));
			}

			SelectCardPaiFromWJDialog dlg2 = new SelectCardPaiFromWJDialog(
					this.gameApp.gameActivityContext, this.gameApp, wjCPData);
			dlg2.showDialog();

			CardPai selectedCP = this.gameApp.wjDetailsViewData.selectedCardPai1;

			if (selectedCP == null)
				return;

			GuoSe fpLBSS = new GuoSe(selectedCP.name, selectedCP.clas,
					selectedCP.number, Type.JinNangApplyTo.anyone);
			fpLBSS.selectedByClick = true;
			fpLBSS.belongToWuJiang = this;
			fpLBSS.gameApp = this.gameApp;
			fpLBSS.sp1 = selectedCP;

			// reset myWuJiang shoupai to unselect card pai
			this.resetShouPaiSelectedBoolean();
			// then add zbSha into shoupai list
			this.shouPai.add(fpLBSS);

			// then select tarWJ
			if (gameApp.gameLogicData.askForPai == Type.CardPai.notNil) {
				// zhu dong chu pai
				fpLBSS.onClickUpdateView();
			}

			break;
		}
		}
	}

	// For AI: Add LiJian cardpai into shoupai
	public CardPai generateJiNengCardPai() {

		CardPai fpCP = null;

		if (fpCP == null) {
			fpCP = this.selectFromShouPaiByClass(Type.CardPaiClass.FangPian);
		}
		if (fpCP == null && this.zhuangBei.jianYiMa != null
				&& this.zhuangBei.jianYiMa.clas == Type.CardPaiClass.FangPian) {
			fpCP = this.zhuangBei.jianYiMa;
		}
		if (fpCP == null && this.zhuangBei.jiaYiMa != null
				&& this.zhuangBei.jiaYiMa.clas == Type.CardPaiClass.FangPian) {
			fpCP = this.zhuangBei.jiaYiMa;
		}
		if (fpCP == null && this.zhuangBei.wuQi != null
				&& this.zhuangBei.wuQi.clas == Type.CardPaiClass.FangPian) {
			fpCP = this.zhuangBei.wuQi;
		}

		if (fpCP == null)
			return null;

		GuoSe fpLBSS = new GuoSe(fpCP.name, fpCP.clas, fpCP.number,
				Type.JinNangApplyTo.anyone);
		fpLBSS.selectedByClick = true;
		fpLBSS.belongToWuJiang = this;
		fpLBSS.gameApp = this.gameApp;
		fpLBSS.sp1 = fpCP;

		fpLBSS.selectTarWJForAI();
		WuJiang tarWJ = fpLBSS.getTarWJForAI();
		if (tarWJ != null && tarWJ.shouPai.size() >= tarWJ.blood)
			return fpLBSS;

		return null;
	}

	// overwrite it for LeBuShiShu
	public CardPai selectCardPaiFromShouPai(Type.CardPai cpT) {
		CardPai localCardPai;
		if ((!this.tuoGuan) || (cpT != Type.CardPai.LeBuShiShu)) {
			localCardPai = super.selectCardPaiFromShouPai(cpT);
		} else {
			localCardPai = super.selectCardPaiFromShouPai(cpT);
			if (localCardPai == null)
				localCardPai = this.generateJiNengCardPai();
		}
		return localCardPai;
	}

	// jiNengN2:LiuLi
	// tarWJ== this and change tarWJ to other WJ
	public WuJiang LiuLi(WuJiang srcWJ, WuJiang tarWJ, CardPai srcCP) {

		if (this.shouPai.size() == 0 && !this.zhuangBei.containsZB()) {
			return tarWJ;
		}

		CardPai disCP = null;
		if (this.tuoGuan) {
			// first select disCP
			if (this.shouPai.size() > 0) {
				disCP = this.shouPai.get(0);
			} else if (this.zhuangBei.jianYiMa != null) {
				disCP = this.zhuangBei.jianYiMa;
			} else if (this.zhuangBei.jiaYiMa != null) {
				disCP = this.zhuangBei.jiaYiMa;
			} else if (this.zhuangBei.wuQi != null) {
				disCP = this.zhuangBei.wuQi;
			} else if (this.zhuangBei.fangJu != null) {
				disCP = this.zhuangBei.fangJu;
			}

			if (disCP == null)
				return null;

			// select wj for liuLi tarWJ
			boolean applyWuQi = true;
			if (disCP.cpState == Type.CPState.wuQiPai) {
				applyWuQi = false;
			}

			WuJiang llTarWJ = null;
			for (int i = 0; i < this.opponentList.size(); i++) {
				WuJiang tmpWJ = this.opponentList.get(i);
				int distance = this.gameApp.gameLogicData.wjHelper
						.countDistance(this, tmpWJ, applyWuQi);

				// if discard jianYiMa, then do not count this ma
				if (disCP.cpState == Type.CPState.jianYiMaPai)
					distance += 1;

				// if ZhuGeLiang kongCheng is success
				if (tmpWJ instanceof ZhuGeLiang) {
					if (tmpWJ.shouPai.size() == 0) {
						distance = 256;
					}
				}

				if (distance <= 1 && !tmpWJ.equals(srcWJ)) {
					llTarWJ = tmpWJ;
					break;
				}
			}

			CardPai shanCP = this.selectCardPaiFromShouPai(Type.CardPai.Shan);
			// if I am zhuGong and blood is less then 2, then select friend
			if (llTarWJ == null && this.role == Type.Role.ZhuGong
					&& this.blood <= 2 && shanCP == null) {
				for (int i = 0; i < this.friendList.size(); i++) {
					WuJiang tmpWJ = this.friendList.get(i);
					int distance = this.gameApp.gameLogicData.wjHelper
							.countDistance(this, tmpWJ, applyWuQi);

					// if discard jianYiMa, then do not count this ma
					if (disCP.cpState == Type.CPState.jianYiMaPai)
						distance += 1;

					// if ZhuGeLiang kongCheng is success
					if (tmpWJ instanceof ZhuGeLiang) {
						if (tmpWJ.shouPai.size() == 0) {
							distance = 256;
						}
					}

					if (distance <= 1 && !tmpWJ.equals(srcWJ)) {
						llTarWJ = tmpWJ;
						break;
					}
				}
			}

			if (llTarWJ != null) {
				disCP.belongToWuJiang = null;
				if (disCP.cpState == Type.CPState.ShouPai) {
					this.detatchCardPaiFromShouPai(disCP);
				} else if (disCP.cpState == Type.CPState.wuQiPai
						|| disCP.cpState == Type.CPState.fangJuPai
						|| disCP.cpState == Type.CPState.jiaYiMaPai
						|| disCP.cpState == Type.CPState.jianYiMaPai) {
					this.unstallZhuangBei((ZhuangBeiCardPai) disCP);
				}

				this.gameApp.libGameViewData.logInfo(this.dispName + "["
						+ this.jiNengN2 + "]" + disCP + ",将杀转移给"
						+ llTarWJ.dispName, Type.logDelay.Delay);
				return llTarWJ;
			}

		} else {
			this.gameApp.ynData.reset();
			this.gameApp.ynData.okTxt = "确认";
			this.gameApp.ynData.cancelTxt = "取消";
			this.gameApp.ynData.genInfo = srcWJ.dispName + "杀你,是否发动"
					+ this.jiNengN2 + "?";

			YesNoDialog dlg = new YesNoDialog(this.gameApp.gameActivityContext,
					this.gameApp);
			dlg.showDialog();
			if (!this.gameApp.ynData.result)
				return tarWJ;

			// select card pai first
			this.gameApp.wjDetailsViewData.reset();
			this.gameApp.wjDetailsViewData.selectedCardNumber = 1;
			this.gameApp.wjDetailsViewData.canViewShouPai = true;
			this.gameApp.wjDetailsViewData.selectedWJ = this;

			WuJiangCardPaiData wjCPData = new WuJiangCardPaiData();
			wjCPData.zhuangBei.wuQi = this.zhuangBei.wuQi;
			wjCPData.zhuangBei.fangJu = this.zhuangBei.fangJu;
			wjCPData.zhuangBei.jianYiMa = this.zhuangBei.jianYiMa;
			wjCPData.zhuangBei.jiaYiMa = this.zhuangBei.jiaYiMa;
			wjCPData.shouPai = this.shouPai;

			SelectCardPaiFromWJDialog dlg2 = new SelectCardPaiFromWJDialog(
					this.gameApp.gameActivityContext, this.gameApp, wjCPData);
			dlg2.showDialog();

			disCP = this.gameApp.wjDetailsViewData.selectedCardPai1;

			if (disCP == null)
				return tarWJ;

			// select wj for tarWJ
			boolean applyWuQi = true;
			if (disCP.cpState == Type.CPState.wuQiPai) {
				applyWuQi = false;
			}

			String canSelectStr = "";
			WuJiang llTarWJ = this.nextOne;
			while (!llTarWJ.equals(this)) {
				llTarWJ.canSelect = false;
				int distance = this.gameApp.gameLogicData.wjHelper
						.countDistance(this, llTarWJ, applyWuQi);

				// if discard jianYiMa, then do not count this ma
				if (disCP.cpState == Type.CPState.jianYiMaPai)
					distance += 1;

				// if ZhuGeLiang kongCheng is success
				if (llTarWJ instanceof ZhuGeLiang) {
					if (llTarWJ.shouPai.size() == 0) {
						distance = 256;
					}
				}

				if (distance <= 1 && !llTarWJ.equals(srcWJ)) {
					// set the tarWJ to gray
					this.gameApp.libGameViewData.imageWJs[llTarWJ.imageViewIndex]
							.setBackgroundDrawable(this.gameApp.getResources()
									.getDrawable(R.drawable.bg_green));
					// set can reach flag
					llTarWJ.canSelect = true;
					canSelectStr += llTarWJ.dispName + " ";
				}
				llTarWJ = llTarWJ.nextOne;
			}

			if (canSelectStr.trim().length() <= 0) {
				this.gameApp.libGameViewData.logInfo("没有可" + this.jiNengN2
						+ "对象", Type.logDelay.NoDelay);
				return tarWJ;
			}

			// use UI for interaction
			gameApp.selectWJViewData.reset();
			gameApp.selectWJViewData.selectNumber = 1;
			gameApp.gameLogicData.userInterface.askUserSelectWuJiang(
					gameApp.gameLogicData.myWuJiang, "你可以" + this.jiNengN2
							+ "到" + canSelectStr + ",请点击选择");

			if (this.gameApp.selectWJViewData.selectedWJ1 != null) {
				tarWJ = this.gameApp.selectWJViewData.selectedWJ1;

				disCP.belongToWuJiang = null;
				if (disCP.cpState == Type.CPState.ShouPai) {
					this.detatchCardPaiFromShouPai(disCP);
					this.gameApp.gameLogicData.wjHelper
							.updateWJ8ShouPaiToLibGameView();
				} else if (disCP.cpState == Type.CPState.wuQiPai
						|| disCP.cpState == Type.CPState.fangJuPai
						|| disCP.cpState == Type.CPState.jiaYiMaPai
						|| disCP.cpState == Type.CPState.jianYiMaPai) {
					this.unstallZhuangBei((ZhuangBeiCardPai) disCP);
				}

				this.gameApp.libGameViewData.logInfo(this.dispName + "["
						+ this.jiNengN2 + "]" + disCP + ",将杀转移给"
						+ tarWJ.dispName, Type.logDelay.Delay);
				return tarWJ;
			}
		}
		// finally return the original tarWJ, no liuLi
		return tarWJ;
	}
}
