package alben.sgs.wujiang.instance;

import alben.sgs.android.R;
import alben.sgs.android.dialog.SelectCardPaiFromWJDialog;
import alben.sgs.android.dialog.YesNoDialog;
import alben.sgs.cardpai.CardPai;
import alben.sgs.type.Type;
import alben.sgs.type.UpdateWJViewData;
import alben.sgs.type.WuJiangCardPaiData;
import alben.sgs.wujiang.WuJiang;
import alben.sgs.wujiang.instance.jineng.LiJian;
import android.view.View;

public class DiaoChan extends WuJiang {
	public DiaoChan(Type.WuJiang n, Type.Country c, Type.Sex s, int b) {
		super(n, c, s, b);
		this.imageNumber = alben.sgs.android.R.drawable.wj_diaochan;
		this.jiNengDesc = "1、离间：出牌阶段，你可以弃一张牌并选择两名男性角色。若如此作，视为其中一名男性角色对另一名男性角色使用一张【决斗】。（此【决斗】不能被【无懈可击】响应）。每回合限用一次。\n"
				+ "2、闭月：回合结束阶段，可摸一张牌。";
		this.dispName = "貂蝉";
		this.jiNengN1 = "离间";
		this.jiNengN2 = "闭月";
	}

	// enable jiNengButton
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

			// for ZhangJiao's HuangTian
			if (this.gameApp.gameLogicData.zhuGongWuJiang instanceof ZhangJiao) {
				this.gameApp.libGameViewData.mJiNengBtn3
						.setVisibility(View.VISIBLE);
				this.gameApp.libGameViewData.mJiNengBtn3.setEnabled(true);
				this.gameApp.libGameViewData.mJiNengBtn3Txt
						.setText(this.gameApp.gameLogicData.zhuGongWuJiang.jiNengN3);
			}
		}
		// invoke super to set the correct JiNengBtnImage
		super.enableWuJiangJiNengBtn();
	}

	public void listenExitHuiHeEvent() {
		CardPai cp = this.gameApp.gameLogicData.cpHelper.popCardPai();

		cp.belongToWuJiang = this;
		cp.cpState = Type.CPState.ShouPai;
		this.shouPai.add(cp);

		this.gameApp.libGameViewData.logInfo(this.dispName + "["
				+ this.jiNengN2 + "],摸1张牌", Type.logDelay.NoDelay);

		if (!this.tuoGuan) {
			this.gameApp.gameLogicData.wjHelper.updateWJ8ShouPaiToLibGameView();
		} else {
			UpdateWJViewData item = new UpdateWJViewData();
			item.updateShouPaiNumber = true;
			this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(
					this, item);
		}
	}

	// For AI: Add LiJian cardpai into shoupai
	public CardPai generateJiNengCardPai() {

		if (this.oneTimeJiNengTrigger) {
			return null;
		}

		if (!this.tuoGuan)
			return null;

		if (this.shouPai.size() == 0)
			return null;

		LiJian liJian = new LiJian(Type.CardPai.WJJiNeng,
				Type.CardPaiClass.nil, 0);
		liJian.belongToWuJiang = this;
		liJian.gameApp = this.gameApp;
		// select one shou pai
		liJian.sp1 = this.shouPai.get(0);

		liJian.selectTarWJForAI();

		if (liJian.tarWJ1 == null || liJian.tarWJ2 == null)
			return null;

		return liJian;
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

			if (this.shouPai.size() == 0) {
				this.gameApp.libGameViewData.logInfo("你没有手牌不能发动"
						+ this.jiNengN1, Type.logDelay.NoDelay);
				return;
			}

			// check if at least two man is alive
			int manN = 0;
			WuJiang tarWJ = this.nextOne;
			while (!tarWJ.equals(this)) {
				if (tarWJ.sex == Type.Sex.man) {
					manN++;
				}
				tarWJ = tarWJ.nextOne;
			}

			if (manN < 2) {
				this.gameApp.libGameViewData.logInfo("男性武将不够,不能发动"
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

			LiJian liJian = new LiJian(Type.CardPai.WJJiNeng,
					Type.CardPaiClass.nil, 0);
			liJian.belongToWuJiang = this;
			liJian.gameApp = this.gameApp;

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

			liJian.sp1 = this.gameApp.wjDetailsViewData.selectedCardPai1;
			liJian.imageNumber = liJian.sp1.imageNumber;

			liJian.selectedByClick = true;

			// reset myWuJiang shoupai to unselect card pai
			this.resetShouPaiSelectedBoolean();
			// then add liJian into shoupai list
			this.shouPai.add(liJian);

			// then select tarWJ
			if (gameApp.gameLogicData.askForPai == Type.CardPai.notNil) {
				liJian.onClickUpdateView();
			}
			break;
		}
		case R.id.JiNeng3: {
			super.handleHuangTianJiNengBtnEvent();
			break;
		}
		}
	}

	// check if ZhuGeLiang is kongCheng
	public boolean canSelectAsFirstChuSha(WuJiang tarWJ) {
		if (tarWJ instanceof ZhuGeLiang) {
			if (tarWJ.shouPai.size() == 0) {
				return false;
			}
		}
		return true;
	}
}
