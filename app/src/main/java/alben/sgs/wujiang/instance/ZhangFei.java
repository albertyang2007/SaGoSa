package alben.sgs.wujiang.instance;

import alben.sgs.type.Type;
import alben.sgs.wujiang.WuJiang;
import android.view.View;

public class ZhangFei extends WuJiang {
	public ZhangFei(Type.WuJiang n, Type.Country c, Type.Sex s, int b) {
		super(n, c, s, b);
		this.imageNumber = alben.sgs.android.R.drawable.wj_zhangfei;
		this.jiNengDesc = "咆哮：出牌阶段，你可以使用任意数量的【杀】。";
		this.dispName = "张飞";
		this.jiNengN1 = "咆哮";
	}

	public void listenEnterHuiHeEvent() {
		super.listenEnterHuiHeEvent();
		this.enableWuJiangJiNengBtn();
	}
	
	public void enableWuJiangJiNengBtn() {
		if (!this.tuoGuan) {
			this.gameApp.libGameViewData.mJiNengBtn1.setVisibility(View.VISIBLE);
			this.gameApp.libGameViewData.mJiNengBtn1.setEnabled(false);
			this.gameApp.libGameViewData.mJiNengBtn1Txt.setText(this.jiNengN1);
		}
		// invoke super to set the correct JiNengBtnImage
		super.enableWuJiangJiNengBtn();
	}

	// 技能:
	public boolean canIChuSha() {
		if (this.state == Type.State.ChuPai && this.huiHeChuShaN > 1) {
			this.specialChuPaiReason = "[咆哮]";
		}
		return true;
	}
}
