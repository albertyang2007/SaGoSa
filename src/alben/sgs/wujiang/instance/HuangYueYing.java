package alben.sgs.wujiang.instance;

import alben.sgs.type.Type;
import alben.sgs.wujiang.WuJiang;
import android.view.View;

public class HuangYueYing extends WuJiang {
	public HuangYueYing(Type.WuJiang n, Type.Country c, Type.Sex s, int b) {
		super(n, c, s, b);
		this.imageNumber = alben.sgs.android.R.drawable.wj_huangyueying;
		this.jiNengDesc = "1、集智：每当你使用一张非延时类锦囊时，（在它结算之前）你可以立即摸一张牌。\n"
				+ "2、奇才：你使用任何锦囊无距离限制。";
		this.dispName = "黄月英";
		this.jiNengN1 = "集智";
		this.jiNengN2 = "奇才";
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
}
