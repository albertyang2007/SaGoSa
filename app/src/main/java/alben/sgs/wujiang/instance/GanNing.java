package alben.sgs.wujiang.instance;

import alben.sgs.android.R;
import alben.sgs.android.dialog.SelectCardPaiFromWJDialog;
import alben.sgs.android.dialog.YesNoDialog;
import alben.sgs.cardpai.CardPai;
import alben.sgs.type.Type;
import alben.sgs.type.WuJiangCardPaiData;
import alben.sgs.wujiang.WuJiang;
import alben.sgs.wujiang.instance.jineng.QiXi;
import android.view.View;

public class GanNing extends WuJiang {
	public GanNing(Type.WuJiang n, Type.Country c, Type.Sex s, int b) {
		super(n, c, s, b);
		this.imageNumber = alben.sgs.android.R.drawable.wj_ganning;
		this.jiNengDesc = "奇袭：出牌阶段，你可以将你的任意黑色牌当【过河拆桥】使用。\n" + "★这包括自己已装备的牌。\n"
				+ "★使用奇袭时，仅改变牌的类别(名称)和作用，而牌的花色和点数不变。";
		this.dispName = "甘宁";
		this.jiNengN1 = "奇袭";
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

	// For AI
	public CardPai generateJiNengCardPai() {
		if (!this.tuoGuan)
			return null;

		CardPai blackCP = this
				.selectFromShouPaiByClass(Type.CardPaiClass.MeiHua);
		if (blackCP == null)
			blackCP = this.selectFromShouPaiByClass(Type.CardPaiClass.HeiTao);
		if (blackCP == null && this.zhuangBei.jianYiMa != null) {
			if (this.zhuangBei.jianYiMa.clas == Type.CardPaiClass.MeiHua
					|| this.zhuangBei.jianYiMa.clas == Type.CardPaiClass.HeiTao) {
				blackCP = this.zhuangBei.jianYiMa;
			}
		}
		if (blackCP == null && this.zhuangBei.jiaYiMa != null) {
			if (this.zhuangBei.jiaYiMa.clas == Type.CardPaiClass.MeiHua
					|| this.zhuangBei.jiaYiMa.clas == Type.CardPaiClass.HeiTao) {
				blackCP = this.zhuangBei.jiaYiMa;
			}
		}
		if (blackCP == null && this.zhuangBei.wuQi != null) {
			if (this.zhuangBei.wuQi.clas == Type.CardPaiClass.MeiHua
					|| this.zhuangBei.wuQi.clas == Type.CardPaiClass.HeiTao) {
				blackCP = this.zhuangBei.wuQi;
			}
		}

		if (blackCP == null)
			return null;

		QiXi bGHCQ = new QiXi(blackCP.name, blackCP.clas, blackCP.number,
				Type.JinNangApplyTo.anyone);
		bGHCQ.selectedByClick = true;
		bGHCQ.belongToWuJiang = this;
		bGHCQ.gameApp = this.gameApp;
		bGHCQ.sp1 = blackCP;

		bGHCQ.selectTarWJForAI();

		if (bGHCQ.getTarWJForAI() == null) {
			return null;
		}

		return bGHCQ;
	}

	// overwrite it for GuoHeChaiQiao
	public CardPai selectCardPaiFromShouPai(Type.CardPai cpT) {
		CardPai localCardPai;
		if ((!this.tuoGuan) || (cpT != Type.CardPai.GuoHeChaiQiao)) {
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

			if (this.hasBlackCardPai() == null) {
				this.gameApp.libGameViewData.logInfo("没有黑色牌不能发动"
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
					&& (this.zhuangBei.wuQi.clas == Type.CardPaiClass.MeiHua || this.zhuangBei.wuQi.clas == Type.CardPaiClass.HeiTao))
				wjCPData.zhuangBei.wuQi = this.zhuangBei.wuQi;
			if (this.zhuangBei.jianYiMa != null
					&& (this.zhuangBei.jianYiMa.clas == Type.CardPaiClass.MeiHua || this.zhuangBei.jianYiMa.clas == Type.CardPaiClass.HeiTao))
				wjCPData.zhuangBei.jianYiMa = this.zhuangBei.jianYiMa;
			if (this.zhuangBei.jiaYiMa != null
					&& (this.zhuangBei.jiaYiMa.clas == Type.CardPaiClass.MeiHua || this.zhuangBei.jiaYiMa.clas == Type.CardPaiClass.HeiTao))
				wjCPData.zhuangBei.jiaYiMa = this.zhuangBei.jiaYiMa;
			if (this.zhuangBei.fangJu != null
					&& (this.zhuangBei.fangJu.clas == Type.CardPaiClass.MeiHua || this.zhuangBei.fangJu.clas == Type.CardPaiClass.HeiTao))
				wjCPData.zhuangBei.fangJu = this.zhuangBei.fangJu;

			for (int i = 0; i < this.shouPai.size(); i++) {
				if (this.shouPai.get(i).clas == Type.CardPaiClass.MeiHua
						|| this.shouPai.get(i).clas == Type.CardPaiClass.HeiTao)
					wjCPData.shouPai.add(this.shouPai.get(i));
			}

			SelectCardPaiFromWJDialog dlg2 = new SelectCardPaiFromWJDialog(
					this.gameApp.gameActivityContext, this.gameApp, wjCPData);
			dlg2.showDialog();

			CardPai blackCP = this.gameApp.wjDetailsViewData.selectedCardPai1;

			if (blackCP == null)
				return;

			QiXi bGHCQ = new QiXi(blackCP.name, blackCP.clas, blackCP.number,
					Type.JinNangApplyTo.anyone);
			bGHCQ.selectedByClick = true;
			bGHCQ.belongToWuJiang = this;
			bGHCQ.gameApp = this.gameApp;
			bGHCQ.sp1 = blackCP;

			bGHCQ.selectedByClick = true;
			// reset myWuJiang shoupai to unselect card pai
			this.resetShouPaiSelectedBoolean();
			// then add zbSha into shoupai list
			this.shouPai.add(bGHCQ);

			// then select tarWJ
			if (gameApp.gameLogicData.askForPai == Type.CardPai.notNil) {
				// zhu dong chu pai
				bGHCQ.onClickUpdateView();
			}
		}
		}
	}
}
