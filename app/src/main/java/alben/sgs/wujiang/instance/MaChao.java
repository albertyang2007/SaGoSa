package alben.sgs.wujiang.instance;

import alben.sgs.android.dialog.YesNoDialog;
import alben.sgs.cardpai.CardPai;
import alben.sgs.type.Type;
import alben.sgs.wujiang.WuJiang;
import alben.sgs.wujiang.instance.jineng.TieJi;
import android.view.View;

public class MaChao extends WuJiang {
	public MaChao(Type.WuJiang n, Type.Country c, Type.Sex s, int b) {
		super(n, c, s, b);
		this.imageNumber = alben.sgs.android.R.drawable.wj_machao;
		this.jiNengDesc = "1、马术：锁定技，当你计算与其他角色的距离时，始终-1\n"
				+ "2、铁骑：当你使用【杀】指定一名角色为目标后，你可以进行判定，若结果为红色，此【杀】不可被闪避\n"
				+ "★马术的效果与装备-1马时效果一样，但你仍然可以装备一匹-1马";
		this.dispName = "马超";
		this.jiNengN1 = "马术";
		this.jiNengN2 = "铁骑";
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

	// maShu
	public int getJinGongDistance() {
		return 1;
	}

	// TieJi
	public boolean checkShaMingZhong(WuJiang tarWJ) {
		if (tarWJ == null)
			return false;

		boolean tj = false;
		String info = "失败";

		if (!this.tuoGuan) {
			this.gameApp.ynData.reset();
			this.gameApp.ynData.okTxt = "确认";
			this.gameApp.ynData.cancelTxt = "取消";
			this.gameApp.ynData.genInfo = "是否对" + tarWJ.dispName + "使用"
					+ this.jiNengN2 + "?";

			YesNoDialog dlg = new YesNoDialog(this.gameApp.gameActivityContext,
					this.gameApp);
			dlg.showDialog();
			if (!this.gameApp.ynData.result)
				return false;
		}

		TieJi tjCP = new TieJi(Type.CardPai.nil, Type.CardPaiClass.nil, 0);
		tjCP.belongToWuJiang = this;
		tjCP.gameApp = this.gameApp;

		CardPai pdCP = this.gameApp.gameLogicData.cpHelper
				.popCardPaiForPanDing(tarWJ, tjCP);

		if (pdCP.clas == Type.CardPaiClass.FangPian
				|| pdCP.clas == Type.CardPaiClass.HongTao) {
			tj = true;
			info = "成功,此杀不可被闪避";
		}

		this.gameApp.libGameViewData.logInfo(this.dispName + "["
				+ this.jiNengN2 + "]" + info, Type.logDelay.Delay);

		return tj;
	}
}