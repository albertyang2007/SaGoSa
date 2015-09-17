package alben.sgs.wujiang.instance;

import alben.sgs.android.R;
import alben.sgs.android.dialog.SelectCardPaiFromWJDialog;
import alben.sgs.android.dialog.YesNoDialog;
import alben.sgs.cardpai.CardPai;
import alben.sgs.type.Type;
import alben.sgs.type.UpdateWJViewData;
import alben.sgs.type.WuJiangCardPaiData;
import alben.sgs.wujiang.WuJiang;
import alben.sgs.wujiang.instance.jineng.LuoShen;
import alben.sgs.wujiang.instance.jineng.QingGuo;
import android.view.View;

public class ZhenJi extends WuJiang {
	public ZhenJi(Type.WuJiang n, Type.Country c, Type.Sex s, int b) {
		super(n, c, s, b);
		this.imageNumber = alben.sgs.android.R.drawable.wj_zhenji;
		this.jiNengDesc = "1、倾国：你可以将你的黑色手牌当【闪】使用（或打出）\n"
				+ "2、洛神：回合开始阶段，你可以进行判定：若为黑色，立即获得此生效后的判定牌，并可以再次使用洛神,如此反复，直到出现红色或你不愿意判定了为止。\n"
				+ "★使用倾国时，仅改变牌的类别（名称）和作用，而牌的花色和点数不变。";
		this.dispName = "甄姬";
		this.jiNengN1 = "倾国";
		this.jiNengN2 = "洛神";
	}

	public void listenEnterHuiHeEvent() {
		super.listenEnterHuiHeEvent();
		this.enableWuJiangJiNengBtn();

		this.luoShen();
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

	public void luoShen() {
		LuoShen luoShen = new LuoShen(Type.CardPai.WJJiNeng,
				Type.CardPaiClass.nil, 0);
		luoShen.gameApp = this.gameApp;
		luoShen.belongToWuJiang = this;

		UpdateWJViewData item = new UpdateWJViewData();
		item.updateShouPaiNumber = true;
		if (this.tuoGuan) {
			boolean run = true;
			while (run) {
				CardPai pdCP = this.gameApp.gameLogicData.cpHelper
						.popCardPaiForPanDing(this, luoShen);
				if (pdCP == null) {
					run = false;
				}

				if (pdCP.clas == Type.CardPaiClass.HongTao
						|| pdCP.clas == Type.CardPaiClass.FangPian) {
					run = false;
					this.gameApp.libGameViewData.logInfo(this.dispName + "["
							+ this.jiNengN2 + "]失败", Type.logDelay.Delay);
				} else {
					pdCP.belongToWuJiang = this;
					pdCP.cpState = Type.CPState.ShouPai;
					this.shouPai.add(pdCP);

					this.gameApp.libGameViewData.logInfo(this.dispName + "["
							+ this.jiNengN2 + "]成功,获得此牌",
							Type.logDelay.HalfDelay);

					this.gameApp.gameLogicData.wjHelper
							.updateWuJiangToLibGameView(this, item);
				}
			}// while
		} else {
			this.gameApp.ynData.reset();
			this.gameApp.ynData.okTxt = "确认";
			this.gameApp.ynData.cancelTxt = "取消";
			this.gameApp.ynData.genInfo = "是否使用" + this.jiNengN2 + "?";

			YesNoDialog dlg = new YesNoDialog(this.gameApp.gameActivityContext,
					this.gameApp);
			dlg.showDialog();

			boolean run = this.gameApp.ynData.result;
			while (run) {
				CardPai pdCP = this.gameApp.gameLogicData.cpHelper
						.popCardPaiForPanDing(this, luoShen);
				if (pdCP == null) {
					run = false;
				}

				if (pdCP.clas == Type.CardPaiClass.HongTao
						|| pdCP.clas == Type.CardPaiClass.FangPian) {
					run = false;
					this.gameApp.libGameViewData.logInfo(this.dispName + "["
							+ this.jiNengN2 + "]失败", Type.logDelay.Delay);
				} else {
					pdCP.belongToWuJiang = this;
					pdCP.cpState = Type.CPState.ShouPai;
					this.shouPai.add(pdCP);

					this.gameApp.libGameViewData.logInfo(this.dispName + "["
							+ this.jiNengN2 + "]成功,获得此牌",
							Type.logDelay.HalfDelay);

					this.gameApp.gameLogicData.wjHelper
							.updateWJ8ShouPaiToLibGameView();

					// ask user again
					dlg = new YesNoDialog(this.gameApp.gameActivityContext,
							this.gameApp);
					dlg.showDialog();
					run = this.gameApp.ynData.result;
				}
			}// while
		}
	}

	// Just for AI
	public CardPai selectCardPaiFromShouPai(Type.CardPai cpT) {
		CardPai cp = super.selectCardPaiFromShouPai(cpT);
		if (this.tuoGuan && cp == null && cpT == Type.CardPai.Shan) {
			cp = this.hasBlackCardPaiInShouPai();
			if (cp != null) {
				this.gameApp.libGameViewData.logInfo(this.dispName + "["
						+ this.jiNengN1 + "]", Type.logDelay.NoDelay);
			}
		}
		return cp;
	}

	// Black CP as Shan
	public void handleJiNengBtnEvent(int eventID) {

		switch (eventID) {
		case R.id.JiNeng1: {
			if (this.state != Type.State.Response)
				return;

			if (this.shouPai.size() == 0)
				return;

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
			for (int i = 0; i < this.shouPai.size(); i++) {
				if (this.shouPai.get(i).clas == Type.CardPaiClass.MeiHua
						|| this.shouPai.get(i).clas == Type.CardPaiClass.HeiTao) {
					wjCPData.shouPai.add(this.shouPai.get(i));
				}
			}

			SelectCardPaiFromWJDialog dlg2 = new SelectCardPaiFromWJDialog(
					this.gameApp.gameActivityContext, this.gameApp, wjCPData);
			dlg2.showDialog();

			CardPai selectCP = this.gameApp.wjDetailsViewData.selectedCardPai1;
			if (selectCP == null)
				return;

			if (gameApp.gameLogicData.askForPai == Type.CardPai.Shan) {
				QingGuo qg = new QingGuo(selectCP.name, selectCP.clas,
						selectCP.number);
				qg.gameApp = this.gameApp;
				qg.imageNumber = selectCP.imageNumber;
				qg.belongToWuJiang = this;
				qg.sp1 = selectCP;

				// remember
				this.detatchCardPaiFromShouPai(selectCP);

				qg.selectedByClick = true;

				// reset myWuJiang shoupai to unselect card pai
				this.resetShouPaiSelectedBoolean();
				// then add sxCP into shoupai list
				this.shouPai.add(qg);

				this.gameApp.gameLogicData.userInterface
						.sendMessageToUIForWakeUp();
			}

			break;
		}
		}
	}
}
