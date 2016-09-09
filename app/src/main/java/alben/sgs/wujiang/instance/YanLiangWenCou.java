package alben.sgs.wujiang.instance;

import alben.sgs.android.R;
import alben.sgs.android.dialog.SelectCardPaiFromWJDialog;
import alben.sgs.android.dialog.YesNoDialog;
import alben.sgs.cardpai.CardPai;
import alben.sgs.type.Type;
import alben.sgs.type.UpdateWJViewData;
import alben.sgs.type.WuJiangCardPaiData;
import alben.sgs.wujiang.WuJiang;
import alben.sgs.wujiang.instance.jineng.ShuangXiong;
import android.view.View;

public class YanLiangWenCou extends WuJiang {
	public CardPai sxPDCP = null;

	public YanLiangWenCou(Type.WuJiang n, Type.Country c, Type.Sex s, int b) {
		super(n, c, s, b);
		this.imageNumber = alben.sgs.android.R.drawable.wj_yanliangwenchou;
		this.jiNengDesc = "(火扩展包武将)\n"
				+ "双雄：摸牌阶段，你可以选择放弃摸牌并进行一次判定：你获得此判定牌并且此回合出牌阶段可以将任意一张与该判定牌不同颜色的手牌当【决斗】使用。";
		this.dispName = "颜良文丑";
		this.jiNengN1 = "双雄";
	}

	public void listenEnterHuiHeEvent() {
		super.listenEnterHuiHeEvent();
		this.sxPDCP = null;
		this.enableWuJiangJiNengBtn();
	}

	public void enableWuJiangJiNengBtn() {
		if (!this.tuoGuan) {
			this.gameApp.libGameViewData.mJiNengBtn1
					.setVisibility(View.VISIBLE);
			this.gameApp.libGameViewData.mJiNengBtn1.setEnabled(true);
			this.gameApp.libGameViewData.mJiNengBtn1Txt.setText(this.jiNengN1);

			// for ZhangJiao's HuangTian
			if (this.gameApp.gameLogicData.zhuGongWuJiang instanceof ZhangJiao) {
				this.gameApp.libGameViewData.mJiNengBtn2
						.setVisibility(View.VISIBLE);
				this.gameApp.libGameViewData.mJiNengBtn2.setEnabled(true);
				this.gameApp.libGameViewData.mJiNengBtn2Txt
						.setText(this.gameApp.gameLogicData.zhuGongWuJiang.jiNengN3);
			}
		}
		// invoke super to set the correct JiNengBtnImage
		super.enableWuJiangJiNengBtn();
	}

	public void moPai() {
		boolean sx = false;
		if (this.tuoGuan) {
			if (!this.pdResult.leBuShiShuOK) {
				CardPai shaCP = this.selectCardPaiFromShouPai(Type.CardPai.Sha);
				if (this.shouPai.size() >= 2 && shaCP != null) {
					sx = true;
				}
			}
		} else {
			this.gameApp.ynData.reset();
			this.gameApp.ynData.okTxt = "确认";
			this.gameApp.ynData.cancelTxt = "取消";
			this.gameApp.ynData.genInfo = "是否发动" + this.jiNengN1 + "?";

			YesNoDialog dlg = new YesNoDialog(this.gameApp.gameActivityContext,
					this.gameApp);
			dlg.showDialog();
			sx = this.gameApp.ynData.result;
		}

		if (sx) {

			this.gameApp.libGameViewData.logInfo(this.dispName + "["
					+ this.jiNengN1 + "]", Type.logDelay.Delay);

			this.oneTimeJiNengTrigger = true;

			ShuangXiong sxCP = new ShuangXiong(Type.CardPai.nil,
					Type.CardPaiClass.nil, 0, Type.JinNangApplyTo.all);
			sxCP.gameApp = this.gameApp;
			sxCP.belongToWuJiang = this;

			sxPDCP = this.gameApp.gameLogicData.cpHelper.popCardPaiForPanDing(
					this, sxCP);

			sxPDCP.reset();
			sxPDCP.belongToWuJiang = this;
			sxPDCP.cpState = Type.CPState.ShouPai;
			this.shouPai.add(sxPDCP);

			if (this.tuoGuan) {
				this.gameApp.gameLogicData.wjHelper
						.updateWJ8ShouPaiToLibGameView();
			} else {
				UpdateWJViewData item = new UpdateWJViewData();
				item.updateShouPaiNumber = true;
				this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(
						this, item);
			}

			return;
		} else {
			super.moPai();
			return;
		}
	}

	// For AI
	public CardPai generateJiNengCardPai() {
		if (!this.tuoGuan)
			return null;

		if (!this.oneTimeJiNengTrigger)
			return null;

		if (this.sxPDCP == null)
			return null;

		boolean sxPDCPIsRed = false;

		if (this.sxPDCP.clas == Type.CardPaiClass.FangPian
				|| this.sxPDCP.clas == Type.CardPaiClass.HongTao)
			sxPDCPIsRed = true;

		if (sxPDCPIsRed) {
			CardPai blackCP = this.hasBlackCardPaiInShouPai();
			if (blackCP != null) {
				ShuangXiong sxCP = new ShuangXiong(Type.CardPai.nil,
						Type.CardPaiClass.nil, 0, Type.JinNangApplyTo.all);
				sxCP.gameApp = this.gameApp;
				sxCP.belongToWuJiang = this;
				sxCP.sp1 = blackCP;
				return sxCP;
			}
		} else {
			CardPai redCP = this.hasRedCardPaiInShouPai();
			if (redCP != null) {
				ShuangXiong sxCP = new ShuangXiong(Type.CardPai.nil,
						Type.CardPaiClass.nil, 0, Type.JinNangApplyTo.all);
				sxCP.gameApp = this.gameApp;
				sxCP.belongToWuJiang = this;
				sxCP.sp1 = redCP;
				return sxCP;
			}
		}

		return null;
	}

	// overwrite for JueDou
	public CardPai selectCardPaiFromShouPai(Type.CardPai cpT) {
		CardPai localCardPai;
		if ((!this.tuoGuan) || (cpT != Type.CardPai.JueDou)) {
			localCardPai = super.selectCardPaiFromShouPai(cpT);
		} else {
			localCardPai = super.selectCardPaiFromShouPai(cpT);
			if (localCardPai == null)
				localCardPai = this.generateJiNengCardPai();
		}
		return localCardPai;
	}

	public void handleJiNengBtnEvent(int eventID) {

		switch (eventID) {
		case R.id.JiNeng1: {
			if (this.state != Type.State.ChuPai)
				return;

			if (!this.oneTimeJiNengTrigger)
				return;

			if (this.sxPDCP == null)
				return;

			boolean sxPDCPIsRed = false;

			if (this.sxPDCP.clas == Type.CardPaiClass.FangPian
					|| this.sxPDCP.clas == Type.CardPaiClass.HongTao)
				sxPDCPIsRed = true;

			if (sxPDCPIsRed) {
				CardPai blackCP = this.hasBlackCardPaiInShouPai();
				if (blackCP == null) {
					this.gameApp.libGameViewData.logInfo("没有不同颜色手牌,不能"
							+ this.jiNengN1, Type.logDelay.NoDelay);
					return;
				}
			} else {
				CardPai redCP = this.hasRedCardPaiInShouPai();
				if (redCP == null) {
					this.gameApp.libGameViewData.logInfo("没有不同颜色手牌,不能"
							+ this.jiNengN1, Type.logDelay.NoDelay);
					return;
				}
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
			for (int i = 0; i < this.shouPai.size(); i++) {
				if (sxPDCPIsRed) {
					if (this.shouPai.get(i).clas == Type.CardPaiClass.HeiTao
							|| this.shouPai.get(i).clas == Type.CardPaiClass.MeiHua)
						wjCPData.shouPai.add(this.shouPai.get(i));
				} else {
					if (this.shouPai.get(i).clas == Type.CardPaiClass.HongTao
							|| this.shouPai.get(i).clas == Type.CardPaiClass.FangPian)
						wjCPData.shouPai.add(this.shouPai.get(i));
				}
			}

			SelectCardPaiFromWJDialog dlg2 = new SelectCardPaiFromWJDialog(
					this.gameApp.gameActivityContext, this.gameApp, wjCPData);
			dlg2.showDialog();

			CardPai selectCP = this.gameApp.wjDetailsViewData.selectedCardPai1;
			if (selectCP == null)
				return;

			ShuangXiong sxCP = new ShuangXiong(Type.CardPai.nil,
					Type.CardPaiClass.nil, 0, Type.JinNangApplyTo.all);
			sxCP.gameApp = this.gameApp;
			sxCP.belongToWuJiang = this;
			sxCP.sp1 = selectCP;

			sxCP.selectedByClick = true;

			// reset myWuJiang shoupai to unselect card pai
			this.resetShouPaiSelectedBoolean();
			// then add sxCP into shoupai list
			this.shouPai.add(sxCP);

			if (gameApp.gameLogicData.askForPai == Type.CardPai.notNil) {
				sxCP.onClickUpdateView();
			}

			break;
		}
		case R.id.JiNeng2: {
			super.handleHuangTianJiNengBtnEvent();
			break;
		}
		}
	}
}
