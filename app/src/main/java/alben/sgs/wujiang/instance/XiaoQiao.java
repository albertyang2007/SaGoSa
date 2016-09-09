package alben.sgs.wujiang.instance;

import alben.sgs.android.R;
import alben.sgs.android.dialog.SelectCardPaiFromWJDialog;
import alben.sgs.android.dialog.YesNoDialog;
import alben.sgs.cardpai.CardPai;
import alben.sgs.type.Type;
import alben.sgs.type.WuJiangCardPaiData;
import alben.sgs.wujiang.WuJiang;
import alben.sgs.wujiang.instance.jineng.TianXiang;
import android.view.View;

public class XiaoQiao extends WuJiang {
	public XiaoQiao(Type.WuJiang n, Type.Country c, Type.Sex s, int b) {
		super(n, c, s, b);
		this.imageNumber = alben.sgs.android.R.drawable.wj_xiaoqiao;
		this.jiNengDesc = "(风扩展包武将)\n"
				+ "1、天香：每当你受到伤害时，你可以弃一张『红桃』手牌来转移此伤害给任意一名角色，然后该角色摸X张牌，X为该角色当前已损失的体力值。\n"
				+ "2、红颜：锁定技，你的『黑桃』牌均视为『红桃』花色。";
		this.dispName = "小乔";
		this.jiNengN1 = "天香";
		this.jiNengN2 = "红颜";
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
		}
		// invoke super to set the correct JiNengBtnImage
		super.enableWuJiangJiNengBtn();
	}

	// JiNeng1
	public void increaseBlood(WuJiang srcWJ, CardPai srcCP) {

		if (srcCP.shangHaiN >= 0) {
			// do not tianXiang, invoke super
			super.increaseBlood(srcWJ, srcCP);
			return;
		}

		boolean tianXiang = false;
		WuJiang txTarWJ = null;

		CardPai txCP = this.selectFromShouPaiByClass(Type.CardPaiClass.HeiTao);
		if (txCP == null)
			txCP = this.selectFromShouPaiByClass(Type.CardPaiClass.HongTao);

		if (txCP == null) {
			super.increaseBlood(srcWJ, srcCP);
			return;
		}

		if (this.tuoGuan) {
			if (txCP != null && this.opponentList.size() > 0) {
				tianXiang = true;
			}
		} else {
			this.gameApp.ynData.reset();
			this.gameApp.ynData.okTxt = "确认";
			this.gameApp.ynData.cancelTxt = "取消";
			this.gameApp.ynData.genInfo = "是否发动" + this.jiNengN1 + "?";

			YesNoDialog dlg = new YesNoDialog(this.gameApp.gameActivityContext,
					this.gameApp);
			dlg.showDialog();
			if (this.gameApp.ynData.result) {
				// select card pai first
				this.gameApp.wjDetailsViewData.reset();
				this.gameApp.wjDetailsViewData.selectedCardNumber = 1;
				this.gameApp.wjDetailsViewData.canViewShouPai = true;
				this.gameApp.wjDetailsViewData.selectedWJ = this;

				WuJiangCardPaiData wjCPData = new WuJiangCardPaiData();
				for (int i = 0; i < this.shouPai.size(); i++) {
					if (this.shouPai.get(i).clas == Type.CardPaiClass.HeiTao
							|| this.shouPai.get(i).clas == Type.CardPaiClass.HongTao)
						wjCPData.shouPai.add(this.shouPai.get(i));
				}

				SelectCardPaiFromWJDialog dlg2 = new SelectCardPaiFromWJDialog(
						this.gameApp.gameActivityContext, this.gameApp,
						wjCPData);
				dlg2.showDialog();

				txCP = this.gameApp.wjDetailsViewData.selectedCardPai1;

				if (txCP != null) {
					// then select txTarWJ
					gameApp.selectWJViewData.reset();
					gameApp.selectWJViewData.selectNumber = 1;
					// show wujiang can be selected
					WuJiang tarWJ = this.nextOne;
					while (!tarWJ.equals(this)) {
						gameApp.libGameViewData.imageWJs[tarWJ.imageViewIndex]
								.setBackgroundDrawable(gameApp.getResources()
										.getDrawable(R.drawable.bg_green));
						tarWJ.canSelect = true;
						tarWJ.clicked = false;
						tarWJ = tarWJ.nextOne;
					}
					// use UI for interaction
					gameApp.gameLogicData.userInterface.askUserSelectWuJiang(
							gameApp.gameLogicData.myWuJiang, "请选择"
									+ gameApp.selectWJViewData.selectNumber
									+ "个武将");

					txTarWJ = gameApp.selectWJViewData.selectedWJ1;

					if (txTarWJ != null)
						tianXiang = true;
				}
			}
		}

		if (tianXiang) {
			TianXiang tx = new TianXiang(Type.CardPai.WJJiNeng,
					Type.CardPaiClass.nil, 0);
			tx.gameApp = this.gameApp;
			tx.belongToWuJiang = this;
			tx.srcCP = srcCP;
			tx.shangHaiSrcWJ = srcCP.shangHaiSrcWJ;
			// or tx.shangHaiSrcWJ = srcWJ;
			tx.sp1 = txCP;

			if (this.tuoGuan) {
				tx.selectTarWJForAI();
				txTarWJ = tx.getTarWJForAI();
			}

			tx.work(srcWJ, txTarWJ, srcCP);

			return;
		} else {
			// do not tianXiang, invoke super
			super.increaseBlood(srcWJ, srcCP);
			return;
		}
	}
}
