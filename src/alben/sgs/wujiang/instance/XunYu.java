package alben.sgs.wujiang.instance;

import java.util.ArrayList;

import alben.sgs.android.R;
import alben.sgs.android.dialog.SelectCardPaiFromWJDialog;
import alben.sgs.android.dialog.YesNoDialog;
import alben.sgs.cardpai.CardPai;
import alben.sgs.type.Type;
import alben.sgs.type.WuJiangCardPaiData;
import alben.sgs.wujiang.WuJiang;
import alben.sgs.wujiang.instance.jineng.QuHu;
import android.view.View;

public class XunYu extends WuJiang {
	public XunYu(Type.WuJiang n, Type.Country c, Type.Sex s, int b) {
		super(n, c, s, b);
		this.imageNumber = alben.sgs.android.R.drawable.wj_xunyu;
		this.jiNengDesc = "(火扩展包武将)\n"
				+ "1、驱虎：出牌阶段，你可以与一名体力比你多的角色拼点，若你赢，目标角色对其攻击范围内，你指定的另一名角色（不能是被驱虎的角色本身）造成1点伤害。若你没赢，他/她对你造成1点伤害。每回合限用一次。\n"
				+ "2、节命：你每受到1点伤害，可令任意一名角色将手牌补至其体力上限的张数（不能超过5张）";
		this.dispName = "荀彧";
		this.jiNengN1 = "驱虎";
		this.jiNengN2 = "节命";
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

	// For AI: Add quHu cardpai into shoupai
	public CardPai generateJiNengCardPai() {
		if (this.oneTimeJiNengTrigger) {
			return null;
		}

		if (!this.tuoGuan)
			return null;

		if (this.shouPai.size() == 0)
			return null;

		// get the biggest number one
		CardPai maxNCP = this.shouPai.get(0);
		for (int i = 1; i < this.shouPai.size(); i++) {
			if (maxNCP.number < this.shouPai.get(i).number) {
				maxNCP = this.shouPai.get(i);
			}
		}

		// My Max Number of CP is too small
		if (maxNCP.number <= 9)
			return null;

		QuHu quHu = new QuHu(Type.CardPai.nil, Type.CardPaiClass.nil, 0);
		quHu.gameApp = this.gameApp;
		quHu.belongToWuJiang = this;
		quHu.cpState = Type.CPState.ShouPai;
		quHu.sp1 = maxNCP;

		quHu.selectTarWJForAI();
		if (quHu.getTarWJForAI() != null) {
			return quHu;
		} else
			return null;
	}

	public void handleJiNengBtnEvent(int eventID) {

		switch (eventID) {
		case R.id.JiNeng1: {

			if (this.state != Type.State.ChuPai)
				return;

			if (this.oneTimeJiNengTrigger) {
				this.gameApp.libGameViewData.logInfo(this.jiNengN1 + "只能发动一次",
						Type.logDelay.NoDelay);
				return;
			}

			this.gameApp.ynData.reset();
			this.gameApp.ynData.okTxt = "确认";
			this.gameApp.ynData.cancelTxt = "取消";
			this.gameApp.ynData.genInfo = "是否发动" + this.jiNengN1 + "?";

			YesNoDialog dlg = new YesNoDialog(this.gameApp.gameActivityContext,
					this.gameApp);
			dlg.showDialog();
			if (!this.gameApp.ynData.result)
				return;

			// first select one shoupai
			this.gameApp.wjDetailsViewData.reset();
			this.gameApp.wjDetailsViewData.selectedCardNumber = 1;
			this.gameApp.wjDetailsViewData.canViewShouPai = true;
			this.gameApp.wjDetailsViewData.selectedWJ = this;

			WuJiangCardPaiData wjCPData = new WuJiangCardPaiData();
			wjCPData.shouPai = this.shouPai;

			SelectCardPaiFromWJDialog dlg2 = new SelectCardPaiFromWJDialog(
					this.gameApp.gameActivityContext, this.gameApp, wjCPData);
			dlg2.showDialog();

			CardPai maxNCP = this.gameApp.wjDetailsViewData.selectedCardPai1;

			if (maxNCP == null)
				return;

			// then select one wj
			gameApp.selectWJViewData.reset();
			gameApp.selectWJViewData.selectNumber = 1;
			// show wujiang can be selected
			WuJiang tarWJ = this.nextOne;
			while (!tarWJ.equals(this)) {
				// reset first
				gameApp.libGameViewData.imageWJs[tarWJ.imageViewIndex]
						.setBackgroundDrawable(gameApp.getResources()
								.getDrawable(R.drawable.bg_black));
				tarWJ.canSelect = false;
				tarWJ.clicked = false;

				// who can be selected
				if (tarWJ.shouPai.size() > 0) {
					gameApp.libGameViewData.imageWJs[tarWJ.imageViewIndex]
							.setBackgroundDrawable(gameApp.getResources()
									.getDrawable(R.drawable.bg_green));
					tarWJ.canSelect = true;
					tarWJ.clicked = false;
				}

				tarWJ = tarWJ.nextOne;
			}
			// use UI for interaction
			gameApp.gameLogicData.userInterface.askUserSelectWuJiang(
					gameApp.gameLogicData.myWuJiang, "请选择"
							+ gameApp.selectWJViewData.selectNumber + "个武将拼点");

			WuJiang tmpWJ = gameApp.selectWJViewData.selectedWJ1;

			if (tmpWJ == null)
				return;

			QuHu quHu = new QuHu(Type.CardPai.nil, Type.CardPaiClass.nil, 0);
			quHu.gameApp = this.gameApp;
			quHu.belongToWuJiang = this;
			quHu.cpState = Type.CPState.ShouPai;
			quHu.sp1 = maxNCP;

			quHu.selectedByClick = true;

			// reset myWuJiang shoupai to unselect card pai
			this.resetShouPaiSelectedBoolean();
			// then add zbSha into shoupai list
			this.shouPai.add(quHu);

			if (gameApp.gameLogicData.askForPai == Type.CardPai.notNil) {
				this.gameApp.gameLogicData.userInterface.loop = true;
				this.gameApp.gameLogicData.userInterface
						.sendMessageToUIForWakeUp();
			}

			break;
		}
		}
	}

	// jiNengN2
	public void listenIncreaseBloodEvent(CardPai srcCP) {
		if (this.tuoGuan) {
			ArrayList<WuJiang> jieMingWJs = new ArrayList<WuJiang>();
			for (int i = 0; i < Math.abs(srcCP.shangHaiN); i++) {
				// first consider jieMing to me
				if (this.shouPai.size() < this.getMaxBlood()) {
					this.jieMing(this);
				} else if (this
						.isFriend(this.gameApp.gameLogicData.zhuGongWuJiang)
						&& this.gameApp.gameLogicData.zhuGongWuJiang.shouPai
								.size() < this.gameApp.gameLogicData.zhuGongWuJiang
								.getMaxBlood()
						&& !jieMingWJs
								.contains(this.gameApp.gameLogicData.zhuGongWuJiang)) {
					// then consider zhuGong
					jieMingWJs.add(this.gameApp.gameLogicData.zhuGongWuJiang);
					this.jieMing(this.gameApp.gameLogicData.zhuGongWuJiang);
				} else {
					// last consider my friend
					for (int j = 0; j < this.friendList.size(); j++) {
						WuJiang tarWJ = this.friendList.get(j);
						if (tarWJ.shouPai.size() < tarWJ.getMaxBlood()
								&& !jieMingWJs.contains(tarWJ)) {
							jieMingWJs.add(tarWJ);
							this.jieMing(tarWJ);
							break;
						}
					}
				}
			}
		} else {
			this.gameApp.ynData.reset();
			this.gameApp.ynData.okTxt = "确认";
			this.gameApp.ynData.cancelTxt = "取消";
			this.gameApp.ynData.genInfo = "是否发动" + this.jiNengN2 + "?";

			YesNoDialog dlg = new YesNoDialog(this.gameApp.gameActivityContext,
					this.gameApp);
			dlg.showDialog();
			if (!this.gameApp.ynData.result)
				return;

			// then select wj
			gameApp.selectWJViewData.reset();
			gameApp.selectWJViewData.selectNumber = Math.abs(srcCP.shangHaiN);
			// maybe no one can jieMing
			gameApp.selectWJViewData.selectedWJAtLeast1 = true;
			gameApp.selectWJViewData.allowSelectedZeroWJ = true;

			// show wujiang can be selected
			if (this.shouPai.size() < this.getMaxBlood()) {
				gameApp.libGameViewData.imageWJs[this.imageViewIndex]
						.setBackgroundDrawable(gameApp.getResources()
								.getDrawable(R.drawable.bg_green));
				this.canSelect = true;
				this.clicked = false;
			} else {
				this.canSelect = false;
			}

			WuJiang tarWJ = this.nextOne;
			while (!tarWJ.equals(this)) {
				tarWJ.canSelect = false;
				tarWJ.clicked = false;
				if (tarWJ.shouPai.size() < tarWJ.getMaxBlood()) {
					gameApp.libGameViewData.imageWJs[tarWJ.imageViewIndex]
							.setBackgroundDrawable(gameApp.getResources()
									.getDrawable(R.drawable.bg_green));
					tarWJ.canSelect = true;
					tarWJ.clicked = false;
				}
				tarWJ = tarWJ.nextOne;
			}

			// use UI for interaction
			gameApp.gameLogicData.userInterface.askUserSelectWuJiang(
					gameApp.gameLogicData.myWuJiang, "请选择0~"
							+ gameApp.selectWJViewData.selectNumber + "个武将");

			for (int i = 0; i < gameApp.selectWJViewData.selectedWJs.size(); i++) {
				this.jieMing(gameApp.selectWJViewData.selectedWJs.get(i));
			}

			// after select, restore myWJ to red
			gameApp.libGameViewData.imageWJs[this.imageViewIndex]
					.setBackgroundDrawable(gameApp.getResources().getDrawable(
							R.drawable.bg_red));
		}
	}

	public void jieMing(WuJiang tarWJ) {
		int addCPN = tarWJ.getMaxBlood() - tarWJ.shouPai.size();
		if (addCPN <= 0)
			return;
		if (addCPN > 5)
			addCPN = 5;
		if (addCPN > 0) {
			this.gameApp.gameLogicData.cpHelper.addCardPaiToWuJiang(tarWJ,
					addCPN);
			this.gameApp.libGameViewData.logInfo(this.dispName + "["
					+ this.jiNengN2 + "]让" + tarWJ.dispName + "补充" + addCPN
					+ "张手牌", Type.logDelay.Delay);
		}
	}
}
