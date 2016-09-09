package alben.sgs.wujiang.instance;

import alben.sgs.android.R;
import alben.sgs.android.dialog.SelectCardPaiFromWJDialog;
import alben.sgs.android.dialog.YesNoDialog;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.ZhuangBeiCardPai;
import alben.sgs.cardpai.instance.ZBSMSha;
import alben.sgs.cardpai.instance.ZhangBaSheMao;
import alben.sgs.type.Type;
import alben.sgs.type.WuJiangCardPaiData;
import alben.sgs.wujiang.WuJiang;
import alben.sgs.wujiang.instance.jineng.WuSheng;
import android.view.View;

public class GuanYu extends WuJiang {
	public GuanYu(Type.WuJiang n, Type.Country c, Type.Sex s, int b) {
		super(n, c, s, b);
		this.imageNumber = alben.sgs.android.R.drawable.wj_guanyu;
		this.jiNengDesc = "武圣：你可以将你的任意一张红色牌当【杀】使用或打出。\n"
				+ "★若同时用到当前装备的红色装备效果时，不可把这张装备牌当【杀】来使用或打出。\n"
				+ "★使用武圣时，仅改变牌的类别(名称)和作用，而牌的花色和点数不变。";
		this.dispName = "关羽";
		this.jiNengN1 = "武圣";
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

	public CardPai generateJiNengCardPai() {
		if (!this.tuoGuan)
			return null;

		if (!this.canIChuSha()) {
			return null;
		}

		CardPai redCP = this.hasRedCardPai();

		if (redCP == null)
			return null;

		WuSheng redSha = new WuSheng(redCP.name, redCP.clas, redCP.number);
		redSha.selectedByClick = true;
		redSha.belongToWuJiang = this;
		redSha.gameApp = this.gameApp;
		redSha.sp1 = redCP;

		redSha.selectTarWJForAI();

		if (redSha.getTarWJForAI() == null) {
			return null;
		}

		return redSha;
	}

	public void handleJiNengBtnEvent(int eventID) {

		switch (eventID) {
		case R.id.JiNeng1: {

			if (this.state == Type.State.ChuPai) {
				if (!this.canIChuSha()) {
					this.gameApp.libGameViewData.logInfo("你不能出2次以上的杀",
							Type.logDelay.NoDelay);
					return;
				}
			} else if (this.state == Type.State.Response) {
				if (gameApp.gameLogicData.askForPai != Type.CardPai.Sha) {
					this.gameApp.libGameViewData.logInfo("你现在不能使用"
							+ this.jiNengN1, Type.logDelay.NoDelay);
					return;
				}
			}

			if (this.hasRedCardPai() == null) {
				this.gameApp.libGameViewData.logInfo("没有红色牌不能发动"
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

			// select card pai first
			this.gameApp.wjDetailsViewData.reset();
			this.gameApp.wjDetailsViewData.selectedCardNumber = 1;
			this.gameApp.wjDetailsViewData.canViewShouPai = true;

			this.gameApp.wjDetailsViewData.selectedWJ = this;

			WuJiangCardPaiData wjCPData = new WuJiangCardPaiData();
			if (this.zhuangBei.wuQi != null
					&& (this.zhuangBei.wuQi.clas == Type.CardPaiClass.FangPian || this.zhuangBei.wuQi.clas == Type.CardPaiClass.HongTao))
				wjCPData.zhuangBei.wuQi = this.zhuangBei.wuQi;
			if (this.zhuangBei.jianYiMa != null
					&& (this.zhuangBei.jianYiMa.clas == Type.CardPaiClass.FangPian || this.zhuangBei.jianYiMa.clas == Type.CardPaiClass.HongTao))
				wjCPData.zhuangBei.jianYiMa = this.zhuangBei.jianYiMa;
			if (this.zhuangBei.jiaYiMa != null
					&& (this.zhuangBei.jiaYiMa.clas == Type.CardPaiClass.FangPian || this.zhuangBei.jiaYiMa.clas == Type.CardPaiClass.HongTao))
				wjCPData.zhuangBei.jiaYiMa = this.zhuangBei.jiaYiMa;
			if (this.zhuangBei.fangJu != null
					&& (this.zhuangBei.fangJu.clas == Type.CardPaiClass.FangPian || this.zhuangBei.fangJu.clas == Type.CardPaiClass.HongTao))
				wjCPData.zhuangBei.fangJu = this.zhuangBei.fangJu;

			for (int i = 0; i < this.shouPai.size(); i++) {
				if (this.shouPai.get(i).clas == Type.CardPaiClass.FangPian
						|| this.shouPai.get(i).clas == Type.CardPaiClass.HongTao)
					wjCPData.shouPai.add(this.shouPai.get(i));
			}

			SelectCardPaiFromWJDialog dlg2 = new SelectCardPaiFromWJDialog(
					this.gameApp.gameActivityContext, this.gameApp, wjCPData);
			dlg2.showDialog();

			CardPai redCP = this.gameApp.wjDetailsViewData.selectedCardPai1;

			if (redCP == null)
				return;

			WuSheng redSha = new WuSheng(redCP.name, redCP.clas, redCP.number);
			redSha.selectedByClick = true;
			redSha.belongToWuJiang = this;
			redSha.gameApp = this.gameApp;
			redSha.sp1 = redCP;

			redSha.selectedByClick = true;
			// reset myWuJiang shoupai to unselect card pai
			this.resetShouPaiSelectedBoolean();
			// then add zbSha into shoupai list
			this.shouPai.add(redSha);

			// then select tarWJ
			if (gameApp.gameLogicData.askForPai == Type.CardPai.notNil) {
				// zhu dong chu pai
				redSha.onClickUpdateView();
			} else if (gameApp.gameLogicData.askForPai == Type.CardPai.Sha) {
				// to response juedou, nanmanruqin,jiedaosharen
				this.gameApp.gameLogicData.userInterface
						.sendMessageToUIForWakeUp();
			}

			break;
		}
		}
	}

	public CardPai chuSha(WuJiang srcWJ, CardPai srcCP) {
		// should be used by juedou, nanmanruqin, jiedaosharen
		CardPai shaCP = null;
		if (!this.tuoGuan) {
			this.gameApp.gameLogicData.askForPai = Type.CardPai.Sha;
			shaCP = this.gameApp.gameLogicData.userInterface
					.askUserChuPai(this, "是否对" + srcWJ.dispName + "的"
							+ srcCP.dispName + "出杀?");
		} else {
			// first search sha from shoupai
			shaCP = this.selectCardPaiFromShouPai(Type.CardPai.Sha);

			if (shaCP != null) {
				shaCP.belongToWuJiang = null;
				this.detatchCardPaiFromShouPai(shaCP);
			}

			// then search if redSha
			if (shaCP == null) {
				CardPai redCP = this.hasRedCardPai();
				if (redCP != null) {
					WuSheng redSha = new WuSheng(redCP.name, redCP.clas,
							redCP.number);
					redSha.belongToWuJiang = this;
					redSha.gameApp = this.gameApp;
					redSha.sp1 = redCP;

					shaCP = redSha;
				}
			}

			// then search if zbsm sha
			if (shaCP == null) {
				// no sha, check if wuqi is zhangbashemao
				if (this.zhuangBei.wuQi != null
						&& this.zhuangBei.wuQi instanceof ZhangBaSheMao
						&& this.shouPai.size() >= 2) {
					shaCP = new ZBSMSha(this.shouPai.get(0), this.shouPai
							.get(1));
				}
			}
		}

		if (shaCP != null && shaCP instanceof ZBSMSha) {
			((ZBSMSha) shaCP).discardTwoShouPai();
		}

		if (shaCP != null && shaCP instanceof WuSheng) {
			CardPai disCP = ((WuSheng) shaCP).sp1;
			if (disCP.cpState == Type.CPState.ShouPai) {
				this.detatchCardPaiFromShouPai(disCP);
				if (!this.tuoGuan)
					this.gameApp.gameLogicData.wjHelper
							.updateWJ8ShouPaiToLibGameView();
			} else if (disCP.cpState == Type.CPState.wuQiPai
					|| disCP.cpState == Type.CPState.fangJuPai
					|| disCP.cpState == Type.CPState.jiaYiMaPai
					|| disCP.cpState == Type.CPState.jianYiMaPai) {
				this.unstallZhuangBei((ZhuangBeiCardPai) disCP);
			}
		}

		if (shaCP != null) {
			this.gameApp.libGameViewData.logInfo(this.dispName + "出" + shaCP,
					Type.logDelay.Delay);
		}

		return shaCP;
	}
}