package alben.sgs.wujiang.instance;

import alben.sgs.android.R;
import alben.sgs.android.dialog.SelectCardPaiFromWJDialog;
import alben.sgs.android.dialog.YesNoDialog;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.WuQiCardPai;
import alben.sgs.type.CardPaiActionForTarWuJiang;
import alben.sgs.type.Type;
import alben.sgs.type.WuJiangCardPaiData;
import alben.sgs.wujiang.WuJiang;
import alben.sgs.wujiang.instance.jineng.ShenSu;
import android.view.View;

public class XiaoHouYan extends WuJiang {
	public XiaoHouYan(Type.WuJiang n, Type.Country c, Type.Sex s, int b) {
		super(n, c, s, b);
		this.imageNumber = alben.sgs.android.R.drawable.wj_xiahouyan;
		this.jiNengDesc = "(风扩展包武将)\n" + "神速：你可以分别作出下列选择：\n"
				+ "1、跳过该回合的判定阶段和摸牌阶段。\n" + "2、跳过该回合出牌阶段并弃一张装备牌。\n"
				+ "你每做出上述之一项选择，视为对任意一名角色使用了一张【杀】。";
		this.dispName = "夏侯渊";
		this.jiNengN1 = "神速";
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
		}
		// invoke super to set the correct JiNengBtnImage
		super.enableWuJiangJiNengBtn();
	}

	public void panDing() {
		boolean shenSu1 = false;
		WuJiang ssTarWJ = null;
		if (this.tuoGuan) {
			if (this.hasLBSSInPanDindArea() || this.hasBLCDInPanDindArea()) {
				shenSu1 = true;
			}
		} else {
			this.gameApp.ynData.reset();
			this.gameApp.ynData.okTxt = "确认";
			this.gameApp.ynData.cancelTxt = "取消";
			this.gameApp.ynData.genInfo = "是否使用" + this.jiNengN1 + "1?";

			YesNoDialog dlg = new YesNoDialog(this.gameApp.gameActivityContext,
					this.gameApp);
			dlg.showDialog();

			shenSu1 = this.gameApp.ynData.result;

			if (shenSu1) {
				// then select one wj
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
				gameApp.gameLogicData.userInterface
						.askUserSelectWuJiang(gameApp.gameLogicData.myWuJiang,
								"请选择" + gameApp.selectWJViewData.selectNumber
										+ "个武将");

				ssTarWJ = gameApp.selectWJViewData.selectedWJ1;
			}
		}

		if (shenSu1) {
			// ShenSu1 is trigger, then also skip moPai
			this.pdResult.bingLiangCunDunOK = true;
			// ShenSu work here
			ShenSu shenSu = new ShenSu(Type.CardPai.WJJiNeng,
					Type.CardPaiClass.nil, 0);
			shenSu.gameApp = this.gameApp;
			shenSu.belongToWuJiang = this;
			shenSu.shangHaiSrcWJ = this;

			if (this.tuoGuan) {
				shenSu.selectTarWJForAI();
				ssTarWJ = shenSu.getTarWJForAI();
			}

			if (ssTarWJ == null) {
				return;
			}

			this.gameApp.libGameViewData.logInfo(this.dispName + "跳过判定和摸牌阶段["
					+ this.jiNengN1 + "1]" + ssTarWJ.dispName,
					Type.logDelay.Delay);
			shenSu.work(this, ssTarWJ, null);
			return;
		} else {
			super.panDing();
			return;
		}
	}

	public void chuPai() {
		boolean shenSu2 = false;
		CardPai wuQiCP = this.zhuangBei.wuQi;
		WuJiang ssTarWJ = null;
		if (this.tuoGuan) {
			// Check with current wuQi
			CardPaiActionForTarWuJiang useCPShaWJ = this
					.whichWJICanShaWithWuQi(this.zhuangBei.wuQi,
							this.opponentList);

			// Check with wuQi in shouPai
			if (useCPShaWJ.tarWJ == null) {
				for (int i = 0; i < this.shouPai.size(); i++) {
					CardPai sp = this.shouPai.get(i);
					if (sp instanceof WuQiCardPai) {
						// remember this SP as wuQi
						if (wuQiCP == null)
							wuQiCP = sp;
						useCPShaWJ = this.whichWJICanShaWithWuQi(
								(WuQiCardPai) sp, this.opponentList);
						if (useCPShaWJ.tarWJ != null
								&& useCPShaWJ.srcCP != null) {
							// with wuQi in shouPai, I can Sha one Oppt
							// This wuQi will install later on
							break;
						}
					}
				}
			}

			if (useCPShaWJ.tarWJ == null || useCPShaWJ.srcCP == null) {
				// with current ZB and wuQi in shouPai, I can not Sha any Oppt,
				// then shenSu2 (at least I have one WuQi)
				if (wuQiCP != null) {
					shenSu2 = true;
				}
			}
		} else {
			// first check I have wuQi in shouPai
			if (wuQiCP == null) {
				for (int i = 0; i < this.shouPai.size(); i++) {
					CardPai sp = this.shouPai.get(i);
					if (sp instanceof WuQiCardPai) {
						wuQiCP = sp;
						break;
					}
				}
			}

			if (wuQiCP != null) {
				this.gameApp.ynData.reset();
				this.gameApp.ynData.okTxt = "确认";
				this.gameApp.ynData.cancelTxt = "取消";
				this.gameApp.ynData.genInfo = "是否使用" + this.jiNengN1 + "2?";

				YesNoDialog dlg = new YesNoDialog(
						this.gameApp.gameActivityContext, this.gameApp);
				dlg.showDialog();

				shenSu2 = this.gameApp.ynData.result;

				if (shenSu2) {
					// select wuQi card pai first
					this.gameApp.wjDetailsViewData.reset();
					this.gameApp.wjDetailsViewData.selectedCardNumber = 1;
					this.gameApp.wjDetailsViewData.canViewShouPai = true;
					this.gameApp.wjDetailsViewData.selectedWJ = this;

					WuJiangCardPaiData wjCPData = new WuJiangCardPaiData();
					wjCPData.zhuangBei.wuQi = this.zhuangBei.wuQi;
					for (int i = 0; i < this.shouPai.size(); i++) {
						CardPai sp = this.shouPai.get(i);
						if (sp instanceof WuQiCardPai) {
							wjCPData.shouPai.add(sp);
						}
					}

					SelectCardPaiFromWJDialog dlg2 = new SelectCardPaiFromWJDialog(
							this.gameApp.gameActivityContext, this.gameApp,
							wjCPData);
					dlg2.showDialog();

					wuQiCP = this.gameApp.wjDetailsViewData.selectedCardPai1;

					// then select one wj
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

					ssTarWJ = gameApp.selectWJViewData.selectedWJ1;
				}
			}
		}

		if (shenSu2) {
			// ShenSu2 is trigger, then also skip chuPai
			// ShenSu work here
			ShenSu shenSu = new ShenSu(Type.CardPai.WJJiNeng,
					Type.CardPaiClass.nil, 0);
			shenSu.gameApp = this.gameApp;
			shenSu.belongToWuJiang = this;
			shenSu.shangHaiSrcWJ = this;

			if (this.tuoGuan) {
				shenSu.selectTarWJForAI();
				ssTarWJ = shenSu.getTarWJForAI();
			}

			if (ssTarWJ == null) {
				return;
			}

			if (wuQiCP.cpState == Type.CPState.wuQiPai) {
				this.unstallZhuangBei(this.zhuangBei.wuQi);
			} else if (wuQiCP.cpState == Type.CPState.ShouPai) {
				this.detatchCardPaiFromShouPai(wuQiCP);
			}

			this.gameApp.libGameViewData.logInfo(this.dispName + "丢弃" + wuQiCP
					+ "[" + this.jiNengN1 + "2]" + ssTarWJ.dispName,
					Type.logDelay.Delay);
			shenSu.work(this, ssTarWJ, null);
			return;
		} else {
			super.chuPai();
			return;
		}
	}
}
