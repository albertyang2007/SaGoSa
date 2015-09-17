package alben.sgs.wujiang.instance;

import alben.sgs.android.dialog.YesNoDialog;
import alben.sgs.type.Type;
import alben.sgs.wujiang.WuJiang;
import android.view.View;

public class LvMeng extends WuJiang {
	public int useShaNumber = 0;

	public LvMeng(Type.WuJiang n, Type.Country c, Type.Sex s, int b) {
		super(n, c, s, b);
		this.imageNumber = alben.sgs.android.R.drawable.wj_lvmeng;
		this.jiNengDesc = "克己：若你于出牌阶段未使用或打出过任何一张【杀】，你可以跳过此回合的弃牌阶段\n"
				+ "★OL版手牌上限为20张牌。";
		this.dispName = "吕蒙";
		this.jiNengN1 = "克己";
	}

	public void listenEnterHuiHeEvent() {
		super.listenEnterHuiHeEvent();
		this.useShaNumber = 0;
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

	public void qiPai() {

		if (this.useShaNumber != 0 || this.huiHeChuShaN != 0
				|| this.shouPai.size() <= this.blood) {
			super.qiPai();
			return;
		}

		boolean keJi = false;

		if (this.tuoGuan) {
			keJi = true;
		} else {
			this.gameApp.ynData.reset();
			this.gameApp.ynData.okTxt = "确认";
			this.gameApp.ynData.cancelTxt = "取消";
			this.gameApp.ynData.genInfo = "是否发动" + this.jiNengN1 + "?";

			YesNoDialog dlg = new YesNoDialog(this.gameApp.gameActivityContext,
					this.gameApp);
			dlg.showDialog();
			keJi = this.gameApp.ynData.result;
		}

		if (keJi) {
			this.gameApp.libGameViewData.logInfo(this.dispName + "发动"
					+ this.jiNengN1, Type.logDelay.NoDelay);
			return;
		} else {
			// still call qiPai
			super.qiPai();
			return;
		}
	}
}
