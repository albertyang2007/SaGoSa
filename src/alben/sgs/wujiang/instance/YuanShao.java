package alben.sgs.wujiang.instance;

import java.util.ArrayList;

import alben.sgs.android.R;
import alben.sgs.android.dialog.SelectCardPaiFromWJDialog;
import alben.sgs.android.dialog.YesNoDialog;
import alben.sgs.cardpai.CardPai;
import alben.sgs.type.Type;
import alben.sgs.type.UpdateWJViewData;
import alben.sgs.type.WuJiangCardPaiData;
import alben.sgs.wujiang.WuJiang;
import alben.sgs.wujiang.instance.jineng.LuanJi;
import android.view.View;

public class YuanShao extends WuJiang {
	public YuanShao(Type.WuJiang n, Type.Country c, Type.Sex s, int b) {
		super(n, c, s, b);
		this.imageNumber = alben.sgs.android.R.drawable.wj_yuanshao;
		this.jiNengDesc = "(火扩展包武将)\n"
				+ "1、乱击：出牌阶段，你可以将任意两张相同花色的手牌当【万箭齐发】使用。\n"
				+ "2、血裔：主公技，锁定技，场上每有一名其他群雄角色存活，你的手牌上限便+2。";
		this.dispName = "袁绍";
		this.jiNengN1 = "乱击";
		this.jiNengN2 = "血裔";
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

	// For AI
	public CardPai generateJiNengCardPai() {
		if (!this.tuoGuan)
			return null;

		if (this.shouPai.size() < 2)
			return null;

		// search two card pai whose has same color
		ArrayList<CardPai> cpLFangPian = new ArrayList<CardPai>();
		ArrayList<CardPai> cpLHongTao = new ArrayList<CardPai>();
		ArrayList<CardPai> cpLHeiTao = new ArrayList<CardPai>();
		ArrayList<CardPai> cpLMeiHua = new ArrayList<CardPai>();

		for (int i = 0; i < this.shouPai.size(); i++) {
			if (this.shouPai.get(i).clas == Type.CardPaiClass.FangPian)
				cpLFangPian.add(this.shouPai.get(i));
			else if (this.shouPai.get(i).clas == Type.CardPaiClass.HongTao)
				cpLHongTao.add(this.shouPai.get(i));
			else if (this.shouPai.get(i).clas == Type.CardPaiClass.HeiTao)
				cpLHeiTao.add(this.shouPai.get(i));
			else if (this.shouPai.get(i).clas == Type.CardPaiClass.MeiHua)
				cpLMeiHua.add(this.shouPai.get(i));
		}

		LuanJi luanJi = new LuanJi(Type.CardPai.nil, Type.CardPaiClass.nil, 0,
				Type.JinNangApplyTo.all);
		luanJi.gameApp = this.gameApp;
		luanJi.belongToWuJiang = this;

		if (cpLMeiHua.size() >= 2) {
			luanJi.sp1 = cpLMeiHua.get(0);
			luanJi.sp2 = cpLMeiHua.get(1);
			return luanJi;
		} else if (cpLHeiTao.size() >= 2) {
			luanJi.sp1 = cpLHeiTao.get(0);
			luanJi.sp2 = cpLHeiTao.get(1);
			return luanJi;
		} else if (cpLFangPian.size() >= 2) {
			luanJi.sp1 = cpLFangPian.get(0);
			luanJi.sp2 = cpLFangPian.get(1);
			return luanJi;
		} else if (cpLHongTao.size() >= 2) {
			luanJi.sp1 = cpLHongTao.get(0);
			luanJi.sp2 = cpLHongTao.get(1);
			return luanJi;
		}

		return null;
	}

	// overwrite for wanJianQiFa
	public CardPai selectCardPaiFromShouPai(Type.CardPai cpT) {
		CardPai localCardPai;
		if ((!this.tuoGuan) || (cpT != Type.CardPai.WanJianQiFa)) {
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

			if (this.shouPai.size() < 2) {
				this.gameApp.libGameViewData.logInfo(
						"手牌不够不能发动" + this.jiNengN1, Type.logDelay.NoDelay);
				return;
			}

			// search two card pai whose has same color
			ArrayList<CardPai> cpLFangPian = new ArrayList<CardPai>();
			ArrayList<CardPai> cpLHongTao = new ArrayList<CardPai>();
			ArrayList<CardPai> cpLHeiTao = new ArrayList<CardPai>();
			ArrayList<CardPai> cpLMeiHua = new ArrayList<CardPai>();

			for (int i = 0; i < this.shouPai.size(); i++) {
				if (this.shouPai.get(i).clas == Type.CardPaiClass.FangPian)
					cpLFangPian.add(this.shouPai.get(i));
				else if (this.shouPai.get(i).clas == Type.CardPaiClass.HongTao)
					cpLHongTao.add(this.shouPai.get(i));
				else if (this.shouPai.get(i).clas == Type.CardPaiClass.HeiTao)
					cpLHeiTao.add(this.shouPai.get(i));
				else if (this.shouPai.get(i).clas == Type.CardPaiClass.MeiHua)
					cpLMeiHua.add(this.shouPai.get(i));
			}

			if (cpLFangPian.size() < 2 && cpLHongTao.size() < 2
					&& cpLHeiTao.size() < 2 && cpLMeiHua.size() < 2) {
				this.gameApp.libGameViewData.logInfo("相同花色的手牌不够,不能"
						+ this.jiNengN1, Type.logDelay.NoDelay);
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

			// first select two shou pai whose has some color
			this.gameApp.wjDetailsViewData.reset();
			this.gameApp.wjDetailsViewData.selectedCardNumber = 2;
			this.gameApp.wjDetailsViewData.canViewShouPai = true;
			this.gameApp.wjDetailsViewData.requestTwoCPSameColor = true;
			this.gameApp.wjDetailsViewData.selectedWJ = this;

			WuJiangCardPaiData wjCPData = new WuJiangCardPaiData();
			wjCPData.shouPai = this.shouPai;

			SelectCardPaiFromWJDialog dlg2 = new SelectCardPaiFromWJDialog(
					this.gameApp.gameActivityContext, this.gameApp, wjCPData);
			dlg2.showDialog();

			CardPai sp1 = this.gameApp.wjDetailsViewData.selectedCardPai1;
			CardPai sp2 = this.gameApp.wjDetailsViewData.selectedCardPai2;

			if (sp1 == null || sp2 == null)
				return;

			LuanJi luanJi = new LuanJi(Type.CardPai.nil, Type.CardPaiClass.nil,
					0, Type.JinNangApplyTo.all);
			luanJi.gameApp = this.gameApp;
			luanJi.belongToWuJiang = this;
			luanJi.sp1 = sp1;
			luanJi.sp2 = sp2;

			luanJi.selectedByClick = true;

			// reset myWuJiang shoupai to unselect card pai
			this.resetShouPaiSelectedBoolean();
			// then add zbSha into shoupai list
			this.shouPai.add(luanJi);

			if (gameApp.gameLogicData.askForPai == Type.CardPai.notNil) {
				this.gameApp.gameLogicData.userInterface.loop = true;
				this.gameApp.gameLogicData.userInterface
						.sendMessageToUIForWakeUp();
			}

			break;
		}
		case R.id.JiNeng3: {
			super.handleHuangTianJiNengBtnEvent();
			break;
		}
		}
	}

	// JiNeng2
	public void qiPai() {
		this.state = Type.State.QiPai;
		libGameData.logInfo(this.dispName + "弃牌阶段", Type.logDelay.NoDelay);

		int reserveCP = 0;
		if (this.role == Type.Role.ZhuGong) {
			reserveCP = this.countHowManyQunXiongWJInRunningGame() * 2;
		}

		this.gameApp.gameLogicData.discardShouPaiN = this.shouPai.size()
				- (this.blood + reserveCP);

		if (this.gameApp.gameLogicData.discardShouPaiN <= 0)
			return;

		if (reserveCP > 0) {
			libGameData.logInfo(this.dispName + "[" + this.jiNengN2 + "]",
					Type.logDelay.NoDelay);
		}

		if (this.tuoGuan) {
			UpdateWJViewData item = new UpdateWJViewData();
			item.updateShouPaiNumber = true;
			while (this.shouPai.size() > (this.blood + reserveCP)) {
				// random to discard the shou pai
				CardPai cp = this.shouPai.get(0);
				cp.belongToWuJiang = null;
				cp.cpState = Type.CPState.FeiPaiDui;
				this.detatchCardPaiFromShouPai(cp);
				libGameData.logInfo(this.dispName + "丢弃手牌" + cp,
						Type.logDelay.HalfDelay);
			}
		} else {
			this.gameApp.gameLogicData.wjHelper.updateWJ8ShouPaiToLibGameView();
			// ask user to select card pai to discard
			this.gameApp.gameLogicData.userInterface.discardShouPai(this);
			// update view
			this.gameApp.gameLogicData.wjHelper.updateWJ8ShouPaiToLibGameView();
		}
	}

	//
	public int countHowManyQunXiongWJInRunningGame() {
		int count = 0;
		WuJiang tarWJ = this.nextOne;
		while (!tarWJ.equals(this)) {
			if (tarWJ.country == Type.Country.Qun)
				count++;
			tarWJ = tarWJ.nextOne;
		}
		return count;
	}
}
