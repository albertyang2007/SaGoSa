package alben.sgs.wujiang.instance;

import alben.sgs.cardpai.CardPai;
import alben.sgs.type.Type;
import alben.sgs.type.UpdateWJViewData;
import alben.sgs.wujiang.WuJiang;
import android.view.View;

public class LuSun extends WuJiang {
	public LuSun(Type.WuJiang n, Type.Country c, Type.Sex s, int b) {
		super(n, c, s, b);
		this.imageNumber = alben.sgs.android.R.drawable.wj_luxun;
		this.jiNengDesc = "1、谦逊：锁定技，你不能成为【顺手牵羊】和【乐不思蜀】的目标。\n"
				+ "2、连营：每当你失去最后一张手牌时，可立即摸一张牌。";
		this.dispName = "陆逊";
		this.jiNengN1 = "谦逊";
		this.jiNengN2 = "连营";
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

	public void detatchCardPaiFromShouPai(CardPai cp) {
		super.detatchCardPaiFromShouPai(cp);

		if (this.shouPai.size() == 0) {
			CardPai cp1 = this.gameApp.gameLogicData.cpHelper.popCardPai();

			cp1.belongToWuJiang = this;
			cp1.cpState = Type.CPState.ShouPai;
			this.shouPai.add(cp1);

			this.gameApp.libGameViewData.logInfo(this.dispName + "["
					+ this.jiNengN2 + "],立即摸1张牌", Type.logDelay.NoDelay);

			if (!this.tuoGuan) {
				this.gameApp.gameLogicData.wjHelper
						.updateWJ8ShouPaiToLibGameView();
			} else {
				UpdateWJViewData item = new UpdateWJViewData();
				item.updateShouPaiNumber = true;
				this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(
						this, item);
			}
		}
	}
}
