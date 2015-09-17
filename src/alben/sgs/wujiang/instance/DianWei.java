package alben.sgs.wujiang.instance;

import alben.sgs.android.R;
import alben.sgs.android.dialog.SelectCardPaiFromWJDialog;
import alben.sgs.android.dialog.YesNoDialog;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.WuQiCardPai;
import alben.sgs.type.Type;
import alben.sgs.type.WuJiangCardPaiData;
import alben.sgs.wujiang.WuJiang;
import alben.sgs.wujiang.instance.jineng.QiangXi;
import android.view.View;

public class DianWei extends WuJiang {
	public DianWei(Type.WuJiang n, Type.Country c, Type.Sex s, int b) {
		super(n, c, s, b);
		this.imageNumber = alben.sgs.android.R.drawable.wj_dianwei;
		this.jiNengDesc = "(火扩展包武将)\n"
				+ "强袭：出牌阶段，你可以自减1点体力或弃一张武器牌，然后对你攻击范围内的一名角色造成1点伤害。每回合限用一次。";
		this.dispName = "典韦";
		this.jiNengN1 = "强袭";
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
		}
		// invoke super to set the correct JiNengBtnImage
		super.enableWuJiangJiNengBtn();
	}

	// For AI: Add LiJian cardpai into shoupai
	public CardPai generateJiNengCardPai() {

		if (this.oneTimeJiNengTrigger) {
			return null;
		}

		if (!this.tuoGuan)
			return null;

		CardPai wqCP = this.selectWuQiFromShouPai();

		if (wqCP == null) {
			wqCP = this.zhuangBei.wuQi;
		}

		if (wqCP == null && this.blood <= 1) {
			return null;
		}

		QiangXi qiangXi = new QiangXi(Type.CardPai.WJJiNeng,
				Type.CardPaiClass.nil, 0);
		qiangXi.belongToWuJiang = this;
		qiangXi.shangHaiSrcWJ = this;
		qiangXi.gameApp = this.gameApp;
		qiangXi.sp1 = wqCP;

		if (wqCP == null) {
			qiangXi.decMyBlood = true;
		}

		qiangXi.selectTarWJForAI();
		if (qiangXi.getTarWJForAI() != null)
			return qiangXi;

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

			boolean decMyBlood = false;
			if (this.zhuangBei.wuQi == null
					&& this.selectWuQiFromShouPai() == null) {

				this.gameApp.ynData.reset();
				this.gameApp.ynData.okTxt = "确认";
				this.gameApp.ynData.cancelTxt = "取消";
				this.gameApp.ynData.genInfo = "你没有武器牌,是否自减1点体力发动"
						+ this.jiNengN1 + "?";

				YesNoDialog dlg2 = new YesNoDialog(
						this.gameApp.gameActivityContext, this.gameApp);
				dlg2.showDialog();

				if (this.gameApp.ynData.result) {
					decMyBlood = true;
				} else {
					return;
				}
			} else {
				this.gameApp.ynData.reset();
				this.gameApp.ynData.okTxt = "自减体力";
				this.gameApp.ynData.cancelTxt = "弃武器牌";
				this.gameApp.ynData.genInfo = "请选择发动" + this.jiNengN1
						+ "方式,自减体力还是丢弃武器牌";

				YesNoDialog dlg2 = new YesNoDialog(
						this.gameApp.gameActivityContext, this.gameApp);
				dlg2.showDialog();

				if (this.gameApp.ynData.result) {
					decMyBlood = true;
				}
			}

			QiangXi qiangXi = new QiangXi(Type.CardPai.WJJiNeng,
					Type.CardPaiClass.nil, 0);
			qiangXi.belongToWuJiang = this;
			qiangXi.shangHaiSrcWJ = this;
			qiangXi.gameApp = this.gameApp;
			qiangXi.decMyBlood = decMyBlood;

			// select card pai first
			if (!decMyBlood) {
				this.gameApp.wjDetailsViewData.reset();
				this.gameApp.wjDetailsViewData.selectedCardNumber = 1;
				this.gameApp.wjDetailsViewData.canViewShouPai = true;

				this.gameApp.wjDetailsViewData.selectedWJ = this;

				WuJiangCardPaiData wjCPData = new WuJiangCardPaiData();
				wjCPData.zhuangBei.wuQi = this.zhuangBei.wuQi;
				for (int i = 0; i < this.shouPai.size(); i++) {
					if (this.shouPai.get(i) instanceof WuQiCardPai)
						wjCPData.shouPai.add(this.shouPai.get(i));
				}

				SelectCardPaiFromWJDialog dlg2 = new SelectCardPaiFromWJDialog(
						this.gameApp.gameActivityContext, this.gameApp,
						wjCPData);
				dlg2.showDialog();

				CardPai wqCP = this.gameApp.wjDetailsViewData.selectedCardPai1;

				if (wqCP == null)
					return;

				qiangXi.sp1 = wqCP;

			}

			qiangXi.selectedByClick = true;
			// reset myWuJiang shoupai to unselect card pai
			this.resetShouPaiSelectedBoolean();
			// then add zbSha into shoupai list
			this.shouPai.add(qiangXi);

			// then select tarWJ
			if (gameApp.gameLogicData.askForPai == Type.CardPai.notNil) {
				// zhu dong chu pai
				qiangXi.onClickUpdateView();
			}
		}
			break;
		}
	}
}
