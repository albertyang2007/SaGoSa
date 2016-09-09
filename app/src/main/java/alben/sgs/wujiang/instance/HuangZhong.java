package alben.sgs.wujiang.instance;

import alben.sgs.android.dialog.YesNoDialog;
import alben.sgs.type.Type;
import alben.sgs.wujiang.WuJiang;
import android.view.View;

public class HuangZhong extends WuJiang {
	public HuangZhong(Type.WuJiang n, Type.Country c, Type.Sex s, int b) {
		super(n, c, s, b);
		this.imageNumber = alben.sgs.android.R.drawable.wj_huangzhong;
		this.jiNengDesc = "(风扩展包武将)\n"
				+ "烈弓：出牌阶段，以下两种情况，你可以令你使用的『杀』不可被闪避：1、目标角色的手牌数大于或等于你的体力值。2、目标角色的手牌数小于或等于你的攻击范围。\n"
				+ "★攻击范围计算只和武器有关，与+1马-1马无关。";
		this.dispName = "黄忠";
		this.jiNengN1 = "烈弓";
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
		}
		// invoke super to set the correct JiNengBtnImage
		super.enableWuJiangJiNengBtn();
	}

	// LieGong
	public boolean checkShaMingZhong(WuJiang tarWJ) {
		if (tarWJ == null)
			return false;

		boolean lg = false;

		if (tarWJ.shouPai.size() >= this.blood)
			lg = true;

		int distance = 1;
		if (this.zhuangBei.wuQi != null)
			distance = this.zhuangBei.wuQi.distance;

		if (tarWJ.shouPai.size() <= distance)
			lg = true;

		if (lg && !this.tuoGuan) {
			this.gameApp.ynData.reset();
			this.gameApp.ynData.okTxt = "确认";
			this.gameApp.ynData.cancelTxt = "取消";
			this.gameApp.ynData.genInfo = "是否对" + tarWJ.dispName + "使用"
					+ this.jiNengN1 + "?";

			YesNoDialog dlg = new YesNoDialog(this.gameApp.gameActivityContext,
					this.gameApp);
			dlg.showDialog();
			lg = this.gameApp.ynData.result;
		}

		if (lg) {
			this.gameApp.libGameViewData.logInfo(this.dispName + "["
					+ this.jiNengN1 + "]" + tarWJ.dispName + ",杀不可闪避!",
					Type.logDelay.Delay);
		}

		return lg;
	}
}
