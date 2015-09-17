package alben.sgs.wujiang.instance;

import alben.sgs.android.R;
import alben.sgs.android.dialog.SelectCardPaiFromWJDialog;
import alben.sgs.android.dialog.YesNoDialog;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.ZhuangBeiCardPai;
import alben.sgs.type.Type;
import alben.sgs.type.WuJiangCardPaiData;
import alben.sgs.wujiang.WuJiang;
import android.view.View;

public class PengDe extends WuJiang {
	public PengDe(Type.WuJiang n, Type.Country c, Type.Sex s, int b) {
		super(n, c, s, b);
		this.imageNumber = alben.sgs.android.R.drawable.wj_pangde;
		this.jiNengDesc = "(火扩展包武将)\n" + "1、马术：锁定技，当你计算与其他角色的距离时，始终-1。\n"
				+ "2、猛进：当你使用的【杀】被【闪】抵消时，你可以弃掉对方的一张牌。";
		this.dispName = "庞德";
		this.jiNengN1 = "马术";
		this.jiNengN2 = "猛进";
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

	// maShu
	public int getJinGongDistance() {
		return 1;
	}

	// MengJin
	public void listenShanEvent(WuJiang srcWJ, WuJiang tarWJ, CardPai srcCP) {
		if (this.tuoGuan) {
			if (this.isFriend(tarWJ))
				return;
			CardPai disCP = null;
			if (tarWJ.zhuangBei.fangJu != null) {
				disCP = tarWJ.zhuangBei.fangJu;
				tarWJ.unstallZhuangBei(tarWJ.zhuangBei.fangJu);
			} else if (tarWJ.zhuangBei.wuQi != null) {
				disCP = tarWJ.zhuangBei.wuQi;
				tarWJ.unstallZhuangBei(tarWJ.zhuangBei.wuQi);
			} else if (tarWJ.zhuangBei.jiaYiMa != null) {
				disCP = tarWJ.zhuangBei.jiaYiMa;
				tarWJ.unstallZhuangBei(tarWJ.zhuangBei.jiaYiMa);
			} else if (tarWJ.zhuangBei.jianYiMa != null) {
				disCP = tarWJ.zhuangBei.jianYiMa;
				tarWJ.unstallZhuangBei(tarWJ.zhuangBei.jianYiMa);
			} else if (tarWJ.shouPai.size() > 0) {
				disCP = tarWJ.shouPai.get(0);
				tarWJ.detatchCardPaiFromShouPai(disCP);
			}

			if (disCP != null) {
				this.gameApp.libGameViewData.logInfo(
						this.dispName + "[" + this.jiNengN2 + "],弃掉"
								+ tarWJ.dispName + "的" + disCP,
						Type.logDelay.Delay);
			}
		} else {
			this.gameApp.ynData.reset();
			this.gameApp.ynData.okTxt = "确认";
			this.gameApp.ynData.cancelTxt = "取消";
			this.gameApp.ynData.genInfo = "是否发动" + this.jiNengN2 + "弃掉对方的一张牌?";

			YesNoDialog dlg = new YesNoDialog(this.gameApp.gameActivityContext,
					this.gameApp);
			dlg.showDialog();
			if (!this.gameApp.ynData.result)
				return;

			// first select CP
			this.gameApp.wjDetailsViewData.reset();
			this.gameApp.wjDetailsViewData.selectedCardNumber = 1;
			this.gameApp.wjDetailsViewData.canViewShouPai = false;

			this.gameApp.wjDetailsViewData.selectedWJ = tarWJ;

			WuJiangCardPaiData wjCPData = new WuJiangCardPaiData();
			wjCPData.zhuangBei.wuQi = tarWJ.zhuangBei.wuQi;
			wjCPData.zhuangBei.fangJu = tarWJ.zhuangBei.fangJu;
			wjCPData.zhuangBei.jianYiMa = tarWJ.zhuangBei.jianYiMa;
			wjCPData.zhuangBei.jiaYiMa = tarWJ.zhuangBei.jiaYiMa;
			wjCPData.shouPai = tarWJ.shouPai;

			SelectCardPaiFromWJDialog dlg2 = new SelectCardPaiFromWJDialog(
					this.gameApp.gameActivityContext, this.gameApp, wjCPData);

			dlg2.showDialog();

			CardPai disCP = this.gameApp.wjDetailsViewData.selectedCardPai1;

			if (disCP == null)
				return;

			if (disCP.cpState == Type.CPState.pandDingPai) {
				tarWJ.panDingPai.remove(disCP);
			} else if (disCP.cpState == Type.CPState.ShouPai) {
				tarWJ.detatchCardPaiFromShouPai(disCP);
			} else if (disCP.cpState == Type.CPState.wuQiPai
					|| disCP.cpState == Type.CPState.fangJuPai
					|| disCP.cpState == Type.CPState.jiaYiMaPai
					|| disCP.cpState == Type.CPState.jianYiMaPai) {
				tarWJ.unstallZhuangBei((ZhuangBeiCardPai) disCP);
			}

			this.gameApp.libGameViewData.logInfo(this.dispName + "["
					+ this.jiNengN2 + "],弃掉" + tarWJ.dispName + "的" + disCP,
					Type.logDelay.Delay);
		}
	}

	public void handleJiNengBtnEvent(int eventID) {

		switch (eventID) {
		case R.id.JiNeng3: {
			super.handleHuangTianJiNengBtnEvent();
			break;
		}
		}
	}
}
