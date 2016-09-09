package alben.sgs.wujiang.instance;

import alben.sgs.android.R;
import alben.sgs.android.dialog.SelectCardPaiFromWJDialog;
import alben.sgs.android.dialog.YesNoDialog;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.ZhuangBeiCardPai;
import alben.sgs.type.Type;
import alben.sgs.type.UpdateWJViewData;
import alben.sgs.type.WuJiangCardPaiData;
import alben.sgs.wujiang.WuJiang;
import alben.sgs.wujiang.instance.jineng.JieYuan;
import android.view.View;

public class SunShangXiang extends WuJiang {
	public SunShangXiang(Type.WuJiang n, Type.Country c, Type.Sex s, int b) {
		super(n, c, s, b);
		this.imageNumber = alben.sgs.android.R.drawable.wj_sunshangxiang;
		this.jiNengDesc = "1、结姻：出牌阶段，你可以弃两张手牌并选择一名受伤的男性角色：你和目标角色各回复1点体力。每回合限用一次。\n"
				+ "2、枭姬：当你失去一张装备区里的牌时，你可以立即摸两张牌。\n"
				+ "★使用结姻的条件是“有受伤的男性角色”，与你是否受伤无关。";
		this.dispName = "孙尚香";
		this.jiNengN1 = "结姻";
		this.jiNengN2 = "枭姬";
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

	// JiNeng2
	public void unstallZhuangBei(ZhuangBeiCardPai zbCP) {
		super.unstallZhuangBei(zbCP);
		this.xiaoJi();
	}

	// I am much liking the ZB, so install every ZB I have!
	public boolean needInstallNewZB(CardPai zbCP) {
		return true;
	}

	public void xiaoJi() {
		this.gameApp.gameLogicData.cpHelper.addCardPaiToWuJiang(this, 2);
		this.gameApp.libGameViewData.logInfo(this.dispName + "["
				+ this.jiNengN2 + "],立即摸2张牌", Type.logDelay.Delay);

		if (!this.tuoGuan) {
			this.gameApp.gameLogicData.wjHelper.updateWJ8ShouPaiToLibGameView();
		} else {
			UpdateWJViewData item = new UpdateWJViewData();
			item.updateShouPaiNumber = true;
			this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(
					this, item);
		}
	}

	// For AI: Add JieYuan cardpai into shoupai
	public CardPai generateJiNengCardPai() {
		if (this.oneTimeJiNengTrigger) {
			return null;
		}

		if (!this.tuoGuan)
			return null;

		if (this.shouPai.size() <= 1)
			return null;

		JieYuan jieYuan = new JieYuan(Type.CardPai.nil, Type.CardPaiClass.nil,
				0);
		jieYuan.gameApp = this.gameApp;
		jieYuan.belongToWuJiang = this;
		jieYuan.shangHaiSrcWJ = this;
		jieYuan.sp1 = this.shouPai.get(0);
		jieYuan.sp2 = this.shouPai.get(1);

		// first check if whose blood is less then max
		jieYuan.selectTarWJForAI();

		if (jieYuan.getTarWJForAI() != null)
			return jieYuan;
		else
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

			if (this.shouPai.size() <= 1) {
				this.gameApp.libGameViewData.logInfo("你不够手牌不能发动"
						+ this.jiNengN1, Type.logDelay.NoDelay);
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

			// first select two shoupai
			this.gameApp.wjDetailsViewData.reset();
			this.gameApp.wjDetailsViewData.selectedCardNumber = 2;
			this.gameApp.wjDetailsViewData.canViewShouPai = true;
			this.gameApp.wjDetailsViewData.selectedWJ = this;

			WuJiangCardPaiData wjCPData = new WuJiangCardPaiData();
			wjCPData.shouPai = this.shouPai;

			SelectCardPaiFromWJDialog dlg2 = new SelectCardPaiFromWJDialog(
					this.gameApp.gameActivityContext, this.gameApp, wjCPData);
			dlg2.showDialog();

			CardPai sp1 = this.gameApp.wjDetailsViewData.selectedCardPai1;
			CardPai sp2 = this.gameApp.wjDetailsViewData.selectedCardPai2;

			if (sp1 == null || sp2 == null)
				return;

			// then select one wj
			gameApp.selectWJViewData.reset();
			gameApp.selectWJViewData.selectNumber = 1;
			// show wujiang can be selected
			WuJiang tarWJ = this.nextOne;
			while (!tarWJ.equals(this)) {
				if (tarWJ.sex == Type.Sex.man
						&& tarWJ.blood < tarWJ.getMaxBlood()) {
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
							+ gameApp.selectWJViewData.selectNumber + "个武将");

			WuJiang tmpWJ = gameApp.selectWJViewData.selectedWJ1;

			if (tmpWJ == null)
				return;

			JieYuan jieYuan = new JieYuan(Type.CardPai.nil,
					Type.CardPaiClass.nil, 0);
			jieYuan.gameApp = this.gameApp;
			jieYuan.belongToWuJiang = this;
			jieYuan.shangHaiSrcWJ = this;
			jieYuan.sp1 = sp1;
			jieYuan.sp2 = sp2;

			jieYuan.selectedByClick = true;

			// reset myWuJiang shoupai to unselect card pai
			this.resetShouPaiSelectedBoolean();
			// then add liJian into shoupai list
			this.shouPai.add(jieYuan);

			if (gameApp.gameLogicData.askForPai == Type.CardPai.notNil) {
				this.gameApp.gameLogicData.userInterface.loop = true;
				this.gameApp.gameLogicData.userInterface
						.sendMessageToUIForWakeUp();
			}

			break;
		}
		}
	}
}
