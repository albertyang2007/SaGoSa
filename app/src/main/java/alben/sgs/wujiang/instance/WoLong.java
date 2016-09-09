package alben.sgs.wujiang.instance;

import alben.sgs.android.R;
import alben.sgs.android.dialog.SelectCardPaiFromWJDialog;
import alben.sgs.android.dialog.YesNoDialog;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.instance.BaGuaZhen;
import alben.sgs.cardpai.instance.QingHongJian;
import alben.sgs.cardpai.instance.Sha;
import alben.sgs.type.Type;
import alben.sgs.type.Type.JinNangApplyTo;
import alben.sgs.type.WuJiangCardPaiData;
import alben.sgs.wujiang.WuJiang;
import alben.sgs.wujiang.instance.jineng.BaZhen;
import alben.sgs.wujiang.instance.jineng.HuoJi;
import android.view.View;

public class WoLong extends WuJiang {
	public WoLong(Type.WuJiang n, Type.Country c, Type.Sex s, int b) {
		super(n, c, s, b);
		this.imageNumber = alben.sgs.android.R.drawable.wj_wolong;
		this.jiNengDesc = "(火扩展包武将)\n" + "1、八阵：当没有装备防具时，始终视为装备着八卦阵\n"
				+ "2、看破：黑色手牌当无懈可击\n" + "3、火计：出牌阶段，你可以将你的任意一张红色手牌当【火攻】使用";
		this.dispName = "卧龙";
		this.jiNengN1 = "八阵";
		this.jiNengN2 = "看破";
		this.jiNengN3 = "火计";
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

			this.gameApp.libGameViewData.mJiNengBtn3
					.setVisibility(View.VISIBLE);
			this.gameApp.libGameViewData.mJiNengBtn3.setEnabled(true);
			this.gameApp.libGameViewData.mJiNengBtn3Txt.setText(this.jiNengN3);
		}
		// invoke super to set the correct JiNengBtnImage
		super.enableWuJiangJiNengBtn();
	}

	// overwrite, jiNeng1
	public CardPai chuShan(WuJiang srcWJ, CardPai srcCP) {

		CardPai shanCP = null;

		if (srcCP instanceof Sha && srcWJ.zhuangBei.wuQi != null
				&& srcWJ.zhuangBei.wuQi instanceof QingHongJian) {
			// do nothing when apply qingHongJian
		} else {
			if (this.zhuangBei.fangJu != null) {
				if (this.zhuangBei.fangJu instanceof BaGuaZhen) {
					shanCP = ((BaGuaZhen) this.zhuangBei.fangJu).chuShan(srcWJ,
							this, srcCP);
					if (shanCP != null)
						return shanCP;
				}
			} else {
				// No fangJu, then baZhen is triggered
				BaZhen bz = new BaZhen(Type.CardPai.nil, Type.CardPaiClass.nil,
						0);
				bz.belongToWuJiang = this;
				bz.gameApp = this.gameApp;
				shanCP = bz.chuShan(srcWJ, this, srcCP);
				if (shanCP != null)
					return shanCP;
			}
		}

		if (!this.tuoGuan) {
			// mei you baguazhen or fails
			this.gameApp.gameLogicData.askForPai = Type.CardPai.Shan;
			shanCP = this.gameApp.gameLogicData.userInterface
					.askUserChuPai(this, "是否对" + srcWJ.dispName + "的"
							+ srcCP.dispName + "出闪?");
			if (shanCP != null) {
				this.gameApp.libGameViewData.logInfo(this.dispName + "出"
						+ shanCP, Type.logDelay.Delay);
			}
		} else {
			// mei you baguazhen or fails
			shanCP = this.selectCardPaiFromShouPai(Type.CardPai.Shan);
			if (shanCP != null) {
				shanCP.belongToWuJiang = null;
				this.detatchCardPaiFromShouPai(shanCP);
				this.gameApp.libGameViewData.logInfo(this.dispName + "出"
						+ shanCP, Type.logDelay.Delay);
			}
		}
		return shanCP;
	}

	// overwrite for : jiNeng2
	public boolean hasWuXieKeJi() {
		boolean rtn = super.hasWuXieKeJi();

		if (!rtn) {
			rtn = (this.hasBlackCardPaiInShouPai() != null) ? true : false;
		}

		return rtn;
	}

	public CardPai ChuWuXieKeJi(WuJiang tarWJ) {
		CardPai rtn = super.chuWuXieKeJi(tarWJ);

		if (rtn == null && this.equals(tarWJ)) {
			rtn = this.hasBlackCardPaiInShouPai();
			if (rtn != null) {
				this.gameApp.libGameViewData.logInfo(this.dispName + "["
						+ this.jiNengN2 + "]", Type.logDelay.Delay);
			}
		}

		return rtn;
	}

	// For AI
	public CardPai generateJiNengCardPai() {
		if (!this.tuoGuan)
			return null;

		if (this.shouPai.size() <= 1)
			return null;

		CardPai redCP = this.hasRedCardPaiInShouPai();
		if (redCP == null)
			return null;

		HuoJi huoJi = new HuoJi(redCP.name, redCP.clas, redCP.number,
				JinNangApplyTo.all);
		huoJi.belongToWuJiang = this;
		huoJi.shangHaiSrcWJ = this;
		huoJi.gameApp = this.gameApp;
		huoJi.sp1 = redCP;

		huoJi.selectTarWJForAI();
		if (huoJi.getTarWJForAI() != null)
			return huoJi;

		return null;
	}

	// overwrite for HuoJi
	public CardPai selectCardPaiFromShouPai(Type.CardPai cpT) {
		CardPai localCardPai;
		if ((!this.tuoGuan) || (cpT != Type.CardPai.HuoGong)) {
			localCardPai = super.selectCardPaiFromShouPai(cpT);
		} else {
			localCardPai = super.selectCardPaiFromShouPai(cpT);
			if (localCardPai == null)
				localCardPai = generateJiNengCardPai();
		}
		return localCardPai;
	}

	public void handleJiNengBtnEvent(int eventID) {

		switch (eventID) {
		case R.id.JiNeng2: {
			if (this.state != Type.State.ChuPai
					&& this.state != Type.State.Response)
				return;

			if (this.hasBlackCardPaiInShouPai() == null) {
				this.gameApp.libGameViewData.logInfo("没有黑色手牌不能发动"
						+ this.jiNengN2, Type.logDelay.NoDelay);
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

			// select card pai first
			this.gameApp.wjDetailsViewData.reset();
			this.gameApp.wjDetailsViewData.selectedCardNumber = 1;
			this.gameApp.wjDetailsViewData.canViewShouPai = true;
			this.gameApp.wjDetailsViewData.selectedWJ = this;

			WuJiangCardPaiData wjCPData = new WuJiangCardPaiData();
			for (int i = 0; i < this.shouPai.size(); i++) {
				CardPai cp = this.shouPai.get(i);
				if (cp.clas == Type.CardPaiClass.HeiTao
						|| cp.clas == Type.CardPaiClass.MeiHua)
					wjCPData.shouPai.add(cp);
			}

			SelectCardPaiFromWJDialog dlg2 = new SelectCardPaiFromWJDialog(
					this.gameApp.gameActivityContext, this.gameApp, wjCPData);
			dlg2.showDialog();

			CardPai wxkjCP = this.gameApp.wjDetailsViewData.selectedCardPai1;

			if (wxkjCP == null)
				return;

			// reset myWuJiang shoupai to unselect card pai
			this.resetShouPaiSelectedBoolean();

			wxkjCP.selectedByClick = true;

			if (gameApp.gameLogicData.askForPai == Type.CardPai.WuXieKeJi) {
				this.gameApp.gameLogicData.userInterface
						.sendMessageToUIForWakeUp();
			}

			break;
		}
		case R.id.JiNeng3: {
			if (this.state != Type.State.ChuPai)
				return;

			if (this.hasRedCardPaiInShouPai() == null) {
				this.gameApp.libGameViewData.logInfo("没有红色手牌不能发动"
						+ this.jiNengN3, Type.logDelay.NoDelay);
				return;
			}

			this.gameApp.ynData.reset();
			this.gameApp.ynData.okTxt = "确认";
			this.gameApp.ynData.cancelTxt = "取消";
			this.gameApp.ynData.genInfo = "是否使用" + this.jiNengN3 + "?";

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
				CardPai cp = this.shouPai.get(i);
				if (cp.clas == Type.CardPaiClass.FangPian
						|| cp.clas == Type.CardPaiClass.HongTao)
					wjCPData.shouPai.add(cp);
			}

			SelectCardPaiFromWJDialog dlg2 = new SelectCardPaiFromWJDialog(
					this.gameApp.gameActivityContext, this.gameApp, wjCPData);
			dlg2.showDialog();

			CardPai huoGongCP = this.gameApp.wjDetailsViewData.selectedCardPai1;

			if (huoGongCP == null)
				return;

			HuoJi huoJi = new HuoJi(huoGongCP.name, huoGongCP.clas,
					huoGongCP.number, JinNangApplyTo.all);
			huoJi.belongToWuJiang = this;
			huoJi.shangHaiSrcWJ = this;
			huoJi.gameApp = this.gameApp;
			huoJi.sp1 = huoGongCP;

			huoJi.selectedByClick = true;

			// reset myWuJiang shoupai to unselect card pai
			this.resetShouPaiSelectedBoolean();
			// then add zbSha into shoupai list
			this.shouPai.add(huoJi);

			// then select tarWJ
			if (gameApp.gameLogicData.askForPai == Type.CardPai.notNil) {
				// zhu dong chu pai
				huoJi.onClickUpdateView();
			}

			break;
		}
		}
	}
}
