package alben.sgs.wujiang.instance;

import alben.sgs.android.R;
import alben.sgs.android.dialog.YesNoDialog;
import alben.sgs.cardpai.CardPai;
import alben.sgs.type.Type;
import alben.sgs.type.UpdateWJViewData;
import alben.sgs.wujiang.WuJiang;
import alben.sgs.wujiang.instance.jineng.FanJian;
import android.view.View;

public class ZhouYu extends WuJiang {
	public ZhouYu(Type.WuJiang n, Type.Country c, Type.Sex s, int b) {
		super(n, c, s, b);
		this.imageNumber = alben.sgs.android.R.drawable.wj_zhouyu;
		this.jiNengDesc = "1、英姿：摸牌阶段，你可以额外摸一张牌。\n"
				+ "2、反间：出牌阶段，你可以令另一名角色选择一种花色，抽取你的一张手牌并亮出，若此牌与所选花色不吻合，则你对该角色造成1点伤害。然后不论结果，该角色都获得此牌。每回合限用一次。";
		this.dispName = "周瑜";
		this.jiNengN1 = "英姿";
		this.jiNengN2 = "反间";
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
			this.gameApp.libGameViewData.mJiNengBtn2.setEnabled(true);
			this.gameApp.libGameViewData.mJiNengBtn2Txt.setText(this.jiNengN2);
		}
		// invoke super to set the correct JiNengBtnImage
		super.enableWuJiangJiNengBtn();
	}

	public void moPai() {
		super.moPai();

		CardPai cp = this.gameApp.gameLogicData.cpHelper.popCardPai();

		cp.belongToWuJiang = this;
		cp.cpState = Type.CPState.ShouPai;
		this.shouPai.add(cp);

		this.gameApp.libGameViewData.logInfo(this.dispName + "["
				+ this.jiNengN1 + "],额外摸1张牌", Type.logDelay.NoDelay);

		if (!this.tuoGuan) {
			this.gameApp.gameLogicData.wjHelper.updateWJ8ShouPaiToLibGameView();
		} else {
			UpdateWJViewData item = new UpdateWJViewData();
			item.updateShouPaiNumber = true;
			this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(
					this, item);
		}
	}

	// For AI
	public CardPai generateJiNengCardPai() {
		if (!this.tuoGuan)
			return null;

		if (this.oneTimeJiNengTrigger)
			return null;

		if (this.shouPai.size() == 0)
			return null;

		FanJian fj = new FanJian(Type.CardPai.nil, Type.CardPaiClass.nil, 0);
		fj.gameApp = this.gameApp;
		fj.belongToWuJiang = this;
		fj.shangHaiSrcWJ = this;

		fj.selectTarWJForAI();
		if (fj.getTarWJForAI() != null)
			return fj;

		return null;
	}

	public void handleJiNengBtnEvent(int eventID) {

		switch (eventID) {
		case R.id.JiNeng2: {

			if (this.state != Type.State.ChuPai)
				return;

			if (this.shouPai.size() == 0) {
				this.gameApp.libGameViewData.logInfo(
						"没有手牌不能发动" + this.jiNengN2, Type.logDelay.NoDelay);
				return;
			}

			if (this.oneTimeJiNengTrigger) {
				this.gameApp.libGameViewData.logInfo(this.jiNengN2 + "只能发动一次",
						Type.logDelay.NoDelay);
				return;
			}

			this.gameApp.ynData.reset();
			this.gameApp.ynData.okTxt = "确认";
			this.gameApp.ynData.cancelTxt = "取消";
			this.gameApp.ynData.genInfo = "是否使用" + this.jiNengN2 + "?";

			YesNoDialog dlg = new YesNoDialog(this.gameApp.gameActivityContext,
					this.gameApp);
			dlg.showDialog();
			if (!this.gameApp.ynData.result)
				return;

			// then select one wj
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
					gameApp.gameLogicData.myWuJiang, "请选择"
							+ gameApp.selectWJViewData.selectNumber + "个武将");

			if (gameApp.selectWJViewData.selectedWJ1 == null)
				return;

			FanJian fj = new FanJian(Type.CardPai.nil, Type.CardPaiClass.nil, 0);
			fj.gameApp = this.gameApp;
			fj.belongToWuJiang = this;
			fj.shangHaiSrcWJ = this;

			fj.selectedByClick = true;

			// reset myWuJiang shoupai to unselect card pai
			this.resetShouPaiSelectedBoolean();
			// then add FanJian into shoupai list
			this.shouPai.add(fj);

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
