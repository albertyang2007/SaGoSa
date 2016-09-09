package alben.sgs.wujiang.instance;

import alben.sgs.android.R;
import alben.sgs.android.dialog.SelectCardPaiFromWJDialog;
import alben.sgs.android.dialog.YesNoDialog;
import alben.sgs.cardpai.CardPai;
import alben.sgs.type.Type;
import alben.sgs.type.WuJiangCardPaiData;
import alben.sgs.wujiang.WuJiang;
import alben.sgs.wujiang.instance.jineng.LongDan;
import alben.sgs.wujiang.instance.jineng.LongDanShan;
import android.view.View;

public class ZhaoYun extends WuJiang {
	public ZhaoYun(Type.WuJiang n, Type.Country c, Type.Sex s, int b) {
		super(n, c, s, b);
		this.imageNumber = alben.sgs.android.R.drawable.wj_zhaoyun;
		this.jiNengDesc = "龙胆：你可以将你手牌的【杀】当【闪】、【闪】当【杀】使用或打出。\n"
				+ "★使用龙胆时，仅改变牌的类别(名称)和作用，而牌的花色和点数不变";
		this.dispName = "赵云";
		this.jiNengN1 = "龙胆";
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
		}
		// invoke super to set the correct JiNengBtnImage
		super.enableWuJiangJiNengBtn();
	}

	public CardPai selectCardPaiFromShouPai(Type.CardPai cpT) {
		CardPai cp = super.selectCardPaiFromShouPai(cpT);
		// Just for AI, for Shan
		if (this.tuoGuan && cp == null && cpT == Type.CardPai.Shan) {
			cp = super.selectCardPaiFromShouPai(Type.CardPai.Sha);
			if (cp != null) {
				this.gameApp.libGameViewData.logInfo(this.dispName + "["
						+ this.jiNengN1 + "]", Type.logDelay.NoDelay);
			}
		}

		// Just for AI, for Sha
		if (this.tuoGuan && cp == null && cpT == Type.CardPai.Sha) {
			CardPai shanCP = super.selectCardPaiFromShouPai(Type.CardPai.Shan);
			if (shanCP != null) {
				LongDan ld = new LongDan(shanCP.name, shanCP.clas,
						shanCP.number);
				ld.gameApp = this.gameApp;
				ld.imageNumber = shanCP.imageNumber;
				ld.belongToWuJiang = this;
				ld.sp1 = shanCP;

				cp = ld;
			}
		}
		return cp;
	}

	// Just for JueDou, NanMan
	public CardPai chuSha(WuJiang srcWJ, CardPai srcCP) {
		CardPai shaCP = super.chuSha(srcWJ, srcCP);
		if (this.tuoGuan && shaCP == null) {
			shaCP = super.selectCardPaiFromShouPai(Type.CardPai.Shan);
			if (shaCP != null) {
				shaCP.belongToWuJiang = null;
				this.detatchCardPaiFromShouPai(shaCP);
				this.gameApp.libGameViewData.logInfo(this.dispName + "["
						+ this.jiNengN1 + "]" + this.dispName + "出" + shaCP,
						Type.logDelay.Delay);
			}
		}
		return shaCP;
	}

	// Shan as Sha, Sha as Shan
	public void handleJiNengBtnEvent(int eventID) {

		switch (eventID) {
		case R.id.JiNeng1: {
			if (this.state != Type.State.ChuPai
					&& this.state != Type.State.Response)
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
				if (gameApp.gameLogicData.askForPai == Type.CardPai.Sha
						|| gameApp.gameLogicData.askForPai == Type.CardPai.notNil) {
					if (this.shouPai.get(i).name == Type.CardPai.Shan)
						wjCPData.shouPai.add(this.shouPai.get(i));
				} else if (gameApp.gameLogicData.askForPai == Type.CardPai.Shan) {
					if (this.shouPai.get(i).name == Type.CardPai.Sha)
						wjCPData.shouPai.add(this.shouPai.get(i));
				}
			}

			SelectCardPaiFromWJDialog dlg2 = new SelectCardPaiFromWJDialog(
					this.gameApp.gameActivityContext, this.gameApp, wjCPData);
			dlg2.showDialog();

			CardPai selectCP = this.gameApp.wjDetailsViewData.selectedCardPai1;
			if (selectCP == null)
				return;

			// This is in ChuPai, chu Sha
			if (gameApp.gameLogicData.askForPai == Type.CardPai.notNil) {
				LongDan ld = new LongDan(selectCP.name, selectCP.clas,
						selectCP.number);
				ld.gameApp = this.gameApp;
				ld.imageNumber = selectCP.imageNumber;
				ld.belongToWuJiang = this;
				ld.sp1 = selectCP;

				ld.selectedByClick = true;

				// reset myWuJiang shoupai to unselect card pai
				this.resetShouPaiSelectedBoolean();
				// then add sxCP into shoupai list
				this.shouPai.add(ld);

				ld.onClickUpdateView();

			} else if (gameApp.gameLogicData.askForPai == Type.CardPai.Sha) {
				LongDan ld = new LongDan(selectCP.name, selectCP.clas,
						selectCP.number);
				ld.gameApp = this.gameApp;
				ld.imageNumber = selectCP.imageNumber;
				ld.belongToWuJiang = this;
				ld.sp1 = selectCP;

				// remember
				this.detatchCardPaiFromShouPai(selectCP);

				ld.selectedByClick = true;

				// reset myWuJiang shoupai to unselect card pai
				this.resetShouPaiSelectedBoolean();
				// then add sxCP into shoupai list
				this.shouPai.add(ld);

				this.gameApp.gameLogicData.userInterface
						.sendMessageToUIForWakeUp();

			} else if (gameApp.gameLogicData.askForPai == Type.CardPai.Shan) {
				LongDanShan lds = new LongDanShan(selectCP.name, selectCP.clas,
						selectCP.number);
				lds.gameApp = this.gameApp;
				lds.imageNumber = selectCP.imageNumber;
				lds.belongToWuJiang = this;
				lds.sp1 = selectCP;

				// remember
				this.detatchCardPaiFromShouPai(selectCP);

				lds.selectedByClick = true;

				// reset myWuJiang shoupai to unselect card pai
				this.resetShouPaiSelectedBoolean();
				// then add sxCP into shoupai list
				this.shouPai.add(lds);

				this.gameApp.gameLogicData.userInterface
						.sendMessageToUIForWakeUp();
			}

			break;
		}
		}
	}
}
