package alben.sgs.wujiang.instance;

import alben.sgs.android.R;
import alben.sgs.android.dialog.SelectCardPaiFromWJDialog;
import alben.sgs.android.dialog.YesNoDialog;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.instance.BaiYinShiZi;
import alben.sgs.cardpai.instance.Sha;
import alben.sgs.cardpai.instance.Shan;
import alben.sgs.type.Type;
import alben.sgs.type.WuJiangCardPaiData;
import alben.sgs.wujiang.WuJiang;
import alben.sgs.wujiang.instance.jineng.ZhiHeng;
import android.view.View;

public class SunQuan extends WuJiang {
	public SunQuan(Type.WuJiang n, Type.Country c, Type.Sex s, int b) {
		super(n, c, s, b);
		this.imageNumber = alben.sgs.android.R.drawable.wj_sunquan;
		this.jiNengDesc = "1、制衡：出牌阶段,你可以弃掉任意数量的牌,然后摸取等量的牌.每回合限用一次。\n"
				+ "2、救援：主公技，锁定技，其他吴势力角色在你濒死状态下对你使用【桃】时，你额外回复1点体力。";
		this.dispName = "孙权";
		this.jiNengN1 = "制衡";
		this.jiNengN2 = "救援";
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
			this.gameApp.libGameViewData.mJiNengBtn2.setEnabled(false);
			this.gameApp.libGameViewData.mJiNengBtn2Txt.setText(this.jiNengN2);
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

		if (this.shouPai.size() == 0 && !this.zhuangBei.containsZB())
			return null;

		ZhiHeng zhiHeng = new ZhiHeng(Type.CardPai.nil, Type.CardPaiClass.nil,
				0);
		zhiHeng.gameApp = this.gameApp;
		zhiHeng.belongToWuJiang = this;
		zhiHeng.cpState = Type.CPState.ShouPai;

		for (int i = 0; i < this.shouPai.size(); i++) {
			if (this.blood <= 2) {
				zhiHeng.zhiHengCPs.add(this.shouPai.get(i));
			} else {
				if (!(this.shouPai.get(i) instanceof Sha)
						&& !(this.shouPai.get(i) instanceof Shan)) {
					zhiHeng.zhiHengCPs.add(this.shouPai.get(i));
				}
			}
		}

		if (this.zhuangBei.fangJu != null
				&& this.zhuangBei.fangJu instanceof BaiYinShiZi) {
			if (this.blood < this.getMaxBlood())
				zhiHeng.zhiHengCPs.add(this.zhuangBei.fangJu);
		}

		if (this.zhuangBei.jianYiMa != null) {
			zhiHeng.zhiHengCPs.add(this.zhuangBei.jianYiMa);
		}

		if (this.zhuangBei.jiaYiMa != null) {
			zhiHeng.zhiHengCPs.add(this.zhuangBei.jiaYiMa);
		}

		if (this.zhuangBei.wuQi != null) {
			zhiHeng.zhiHengCPs.add(this.zhuangBei.wuQi);
		}

		if (zhiHeng.zhiHengCPs.size() > 0)
			return zhiHeng;
		else
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

			if (this.shouPai.size() == 0 && !this.zhuangBei.containsZB()) {
				this.gameApp.libGameViewData.logInfo(
						"你没有牌不能发动" + this.jiNengN1, Type.logDelay.NoDelay);
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

			// first select card pai
			this.gameApp.wjDetailsViewData.reset();
			this.gameApp.wjDetailsViewData.selectedCardAtLeast1 = true;
			this.gameApp.wjDetailsViewData.canViewShouPai = true;

			this.gameApp.wjDetailsViewData.selectedWJ = this;

			WuJiangCardPaiData wjCPData = new WuJiangCardPaiData();

			wjCPData.shouPai = this.shouPai;
			if (this.zhuangBei.wuQi != null)
				wjCPData.zhuangBei.wuQi = this.zhuangBei.wuQi;
			if (this.zhuangBei.jianYiMa != null)
				wjCPData.zhuangBei.jianYiMa = this.zhuangBei.jianYiMa;
			if (this.zhuangBei.jiaYiMa != null)
				wjCPData.zhuangBei.jiaYiMa = this.zhuangBei.jiaYiMa;
			if (this.zhuangBei.fangJu != null)
				wjCPData.zhuangBei.fangJu = this.zhuangBei.fangJu;

			SelectCardPaiFromWJDialog dlg2 = new SelectCardPaiFromWJDialog(
					this.gameApp.gameActivityContext, this.gameApp, wjCPData);
			dlg2.showDialog();

			ZhiHeng zhiHeng = new ZhiHeng(Type.CardPai.nil,
					Type.CardPaiClass.nil, 0);
			zhiHeng.gameApp = this.gameApp;
			zhiHeng.belongToWuJiang = this;
			zhiHeng.cpState = Type.CPState.ShouPai;
			zhiHeng.zhiHengCPs = this.gameApp.wjDetailsViewData.selectedCardPaiList;

			zhiHeng.selectedByClick = true;

			// reset myWuJiang shoupai to unselect card pai
			this.resetShouPaiSelectedBoolean();
			// then add zhiHeng into shoupai list
			this.shouPai.add(zhiHeng);

			if (gameApp.gameLogicData.askForPai == Type.CardPai.notNil) {
				this.gameApp.gameLogicData.userInterface.loop = true;
				this.gameApp.gameLogicData.userInterface
						.sendMessageToUIForWakeUp();
			}

			break;
		}
		}
	}
}
