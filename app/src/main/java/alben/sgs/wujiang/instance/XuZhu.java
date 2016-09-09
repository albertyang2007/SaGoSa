package alben.sgs.wujiang.instance;

import alben.sgs.android.dialog.YesNoDialog;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.WuQiCardPai;
import alben.sgs.type.CardPaiActionForTarWuJiang;
import alben.sgs.type.Type;
import alben.sgs.wujiang.WuJiang;
import android.view.View;

public class XuZhu extends WuJiang {
	public XuZhu(Type.WuJiang n, Type.Country c, Type.Sex s, int b) {
		super(n, c, s, b);
		this.imageNumber = alben.sgs.android.R.drawable.wj_xuchu;
		this.jiNengDesc = "裸衣：摸牌阶段，你可以少摸一张牌；若如此做，该回合的出牌阶段，你使用【杀】或【决斗】（你为伤害来源时）造成的伤害+1";
		this.dispName = "许褚";
		this.jiNengN1 = "裸衣";
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

	public void moPai() {
		boolean ly = false;
		if (this.tuoGuan) {
			if (!this.pdResult.leBuShiShuOK) {
				// Check with current wuQi
				CardPaiActionForTarWuJiang useCPShaWJ = this
						.whichWJICanShaWithWuQi(this.zhuangBei.wuQi,
								this.opponentList);

				// Check with wuQi in shouPai
				if (useCPShaWJ.tarWJ == null) {
					for (int i = 0; i < this.shouPai.size(); i++) {
						CardPai sp = this.shouPai.get(i);
						if (sp instanceof WuQiCardPai) {
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

				// I can Sha at least one with current shouPai
				if (useCPShaWJ.tarWJ != null && useCPShaWJ.srcCP != null) {
					ly = true;
				}

				// check if has jueDou
				if (this.selectCardPaiFromShouPai(Type.CardPai.JueDou) != null) {
					ly = true;
				}
			}
		} else {
			this.gameApp.ynData.reset();
			this.gameApp.ynData.okTxt = "确认";
			this.gameApp.ynData.cancelTxt = "取消";
			this.gameApp.ynData.genInfo = "是否发动" + this.jiNengN1 + "?";

			YesNoDialog dlg = new YesNoDialog(this.gameApp.gameActivityContext,
					this.gameApp);
			dlg.showDialog();

			ly = this.gameApp.ynData.result;
		}

		if (ly) {
			this.oneTimeJiNengTrigger = true;
			this.gameApp.gameLogicData.cpHelper.addCardPaiToWuJiang(this, 1);
			this.gameApp.libGameViewData.logInfo(this.dispName + "["
					+ this.jiNengN1 + "]", Type.logDelay.Delay);
			return;
		} else {
			super.moPai();
			return;
		}
	}
}
