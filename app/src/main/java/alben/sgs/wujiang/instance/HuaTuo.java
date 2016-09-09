package alben.sgs.wujiang.instance;

import alben.sgs.android.R;
import alben.sgs.android.dialog.SelectCardPaiFromWJDialog;
import alben.sgs.android.dialog.YesNoDialog;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.ZhuangBeiCardPai;
import alben.sgs.type.Type;
import alben.sgs.type.WuJiangCardPaiData;
import alben.sgs.wujiang.WuJiang;
import alben.sgs.wujiang.instance.jineng.QingNang;
import android.view.View;

public class HuaTuo extends WuJiang {
	public HuaTuo(Type.WuJiang n, Type.Country c, Type.Sex s, int b) {
		super(n, c, s, b);
		this.imageNumber = alben.sgs.android.R.drawable.wj_huatuo;
		this.jiNengDesc = "1、急救：你的回合外，你可以将你的任意红色牌当【桃】使用。\n"
				+ "2、青囊：出牌阶段，你可以主动弃掉一张手牌，令任一目标角色回复1点体力。每回合限用一次。";
		this.dispName = "华佗";
		this.jiNengN1 = "急救";
		this.jiNengN2 = "青囊";
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
			this.gameApp.libGameViewData.mJiNengBtn2.setEnabled(true);
			this.gameApp.libGameViewData.mJiNengBtn2Txt.setText(this.jiNengN2);

			// for ZhangJiao's HuangTian
			if (this.gameApp.gameLogicData.zhuGongWuJiang instanceof ZhangJiao) {
				this.gameApp.libGameViewData.mJiNengBtn3.setEnabled(true);
				this.gameApp.libGameViewData.mJiNengBtn3Txt
						.setText(this.gameApp.gameLogicData.zhuGongWuJiang.jiNengN3);
			}
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

		if (this.shouPai.size() == 0)
			return null;

		CardPai disCP = this.selectFromShouPaiByClass(Type.CardPaiClass.MeiHua);
		if (disCP == null) {
			disCP = this.selectFromShouPaiByClass(Type.CardPaiClass.HeiTao);
		}
		if (disCP == null) {
			disCP = this.selectFromShouPaiByClass(Type.CardPaiClass.FangPian);
		}
		if (disCP == null) {
			disCP = this.selectFromShouPaiByClass(Type.CardPaiClass.HongTao);
		}
		if (disCP == null) {
			return null;
		}

		QingNang qingNang = new QingNang(disCP.name, disCP.clas, disCP.number);
		qingNang.gameApp = this.gameApp;
		qingNang.belongToWuJiang = this;
		qingNang.sp1 = disCP;

		qingNang.selectTarWJForAI();
		if (qingNang.getTarWJForAI() != null) {
			return qingNang;
		}

		return null;
	}

	public void handleJiNengBtnEvent(int eventID) {

		switch (eventID) {
		case R.id.JiNeng1: {
			if (this.state != Type.State.Response)
				return;

			if (this.hasRedCardPai() == null) {
				this.gameApp.libGameViewData.logInfo(
						"没有红牌不能发动" + this.jiNengN1, Type.logDelay.NoDelay);
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

			// reset myWuJiang shoupai to unselect card pai
			this.resetShouPaiSelectedBoolean();

			redCP.selectedByClick = true;
			// then add zbSha into shoupai list
			if (redCP.cpState != Type.CPState.ShouPai) {
				this.shouPai.add(redCP);
			}

			if (redCP.cpState == Type.CPState.wuQiPai
					|| redCP.cpState == Type.CPState.fangJuPai
					|| redCP.cpState == Type.CPState.jiaYiMaPai
					|| redCP.cpState == Type.CPState.jianYiMaPai) {
				this.unstallZhuangBei((ZhuangBeiCardPai) redCP);
			}

			this.specialChuPaiReason = this.jiNengN1;

			if (gameApp.gameLogicData.askForPai == Type.CardPai.Tao) {
				this.gameApp.gameLogicData.userInterface
						.sendMessageToUIForWakeUp();
			}
			break;
		}
		case R.id.JiNeng2: {

			if (this.state != Type.State.ChuPai)
				return;

			if (this.oneTimeJiNengTrigger) {
				this.gameApp.libGameViewData.logInfo(this.jiNengN2 + "只能发动一次",
						Type.logDelay.NoDelay);
				return;
			}

			if (this.shouPai.size() == 0) {
				this.gameApp.libGameViewData.logInfo(
						"没有手牌不能发动" + this.jiNengN2, Type.logDelay.NoDelay);
				return;
			}

			this.gameApp.ynData.reset();
			this.gameApp.ynData.okTxt = "确认";
			this.gameApp.ynData.cancelTxt = "取消";
			this.gameApp.ynData.genInfo = "是否发动" + this.jiNengN2 + "?";

			YesNoDialog dlg = new YesNoDialog(this.gameApp.gameActivityContext,
					this.gameApp);
			dlg.showDialog();
			if (!this.gameApp.ynData.result)
				return;

			// first select shou pai
			this.gameApp.wjDetailsViewData.reset();
			this.gameApp.wjDetailsViewData.selectedCardNumber = 1;
			this.gameApp.wjDetailsViewData.canViewShouPai = true;

			this.gameApp.wjDetailsViewData.selectedWJ = this;

			WuJiangCardPaiData wjCPData = new WuJiangCardPaiData();
			wjCPData.shouPai = this.shouPai;

			SelectCardPaiFromWJDialog dlg2 = new SelectCardPaiFromWJDialog(
					this.gameApp.gameActivityContext, this.gameApp, wjCPData);
			dlg2.showDialog();

			CardPai disCP = this.gameApp.wjDetailsViewData.selectedCardPai1;

			if (disCP == null) {
				return;
			}

			QingNang qingNang = new QingNang(disCP.name, disCP.clas,
					disCP.number);
			qingNang.gameApp = this.gameApp;
			qingNang.belongToWuJiang = this;
			qingNang.sp1 = disCP;

			qingNang.selectedByClick = true;
			// reset myWuJiang shoupai to unselect card pai
			this.resetShouPaiSelectedBoolean();
			// then add zbSha into shoupai list
			this.shouPai.add(qingNang);

			// then select tarWJ
			if (gameApp.gameLogicData.askForPai == Type.CardPai.notNil) {
				// zhu dong chu pai
				qingNang.onClickUpdateView();
			}

			break;
		}
		case R.id.JiNeng3: {
			super.handleHuangTianJiNengBtnEvent();
			break;
		}
		}
	}

	public boolean hasTao(WuJiang srcWJ) {

		boolean rtn = super.hasTao(srcWJ);

		if (!rtn && !this.equals(srcWJ) && this.state == Type.State.Response) {
			rtn = (this.hasRedCardPai() != null) ? true : false;
		}

		return rtn;
	}

	public CardPai chuTao(WuJiang srcWJ) {

		this.specialChuPaiReason = "";

		CardPai taoCP = super.chuTao(srcWJ);

		if (taoCP == null && !this.equals(srcWJ)
				&& this.state == Type.State.Response) {
			taoCP = this.selectFromShouPaiByClass(Type.CardPaiClass.FangPian);

			if (taoCP == null) {
				taoCP = this
						.selectFromShouPaiByClass(Type.CardPaiClass.HongTao);
			}

			if (taoCP != null) {
				this.detatchCardPaiFromShouPai(taoCP);
			}

			if (taoCP == null && this.zhuangBei.jianYiMa != null) {
				if (this.zhuangBei.jianYiMa.clas == Type.CardPaiClass.FangPian
						|| this.zhuangBei.jianYiMa.clas == Type.CardPaiClass.HongTao)
					taoCP = this.zhuangBei.jianYiMa;
				if (taoCP != null) {
					this.unstallZhuangBei((ZhuangBeiCardPai) taoCP);
				}
			}
			if (taoCP == null && this.zhuangBei.jiaYiMa != null) {
				if (this.zhuangBei.jiaYiMa.clas == Type.CardPaiClass.FangPian
						|| this.zhuangBei.jiaYiMa.clas == Type.CardPaiClass.HongTao)
					taoCP = this.zhuangBei.jiaYiMa;
				if (taoCP != null) {
					this.unstallZhuangBei((ZhuangBeiCardPai) taoCP);
				}
			}
			if (taoCP == null && this.zhuangBei.wuQi != null) {
				if (this.zhuangBei.wuQi.clas == Type.CardPaiClass.FangPian
						|| this.zhuangBei.wuQi.clas == Type.CardPaiClass.HongTao)
					taoCP = this.zhuangBei.wuQi;
				if (taoCP != null) {
					this.unstallZhuangBei((ZhuangBeiCardPai) taoCP);
				}
			}
			if (taoCP == null && this.zhuangBei.fangJu != null) {
				if (this.zhuangBei.fangJu.clas == Type.CardPaiClass.FangPian
						|| this.zhuangBei.fangJu.clas == Type.CardPaiClass.HongTao)
					taoCP = this.zhuangBei.fangJu;
				if (taoCP != null) {
					this.unstallZhuangBei((ZhuangBeiCardPai) taoCP);
				}
			}

			if (taoCP != null) {
				this.specialChuPaiReason = this.jiNengN1;
			}
		}
		return taoCP;
	}
}
