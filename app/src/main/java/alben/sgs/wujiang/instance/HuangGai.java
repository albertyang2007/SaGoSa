package alben.sgs.wujiang.instance;

import alben.sgs.android.R;
import alben.sgs.android.dialog.YesNoDialog;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.instance.ZhuGeLianNu;
import alben.sgs.type.Type;
import alben.sgs.wujiang.WuJiang;
import alben.sgs.wujiang.instance.jineng.KuRou;
import android.view.View;

public class HuangGai extends WuJiang {
	public HuangGai(Type.WuJiang n, Type.Country c, Type.Sex s, int b) {
		super(n, c, s, b);
		this.imageNumber = alben.sgs.android.R.drawable.wj_huanggai;
		this.jiNengDesc = "苦肉：出牌阶段，你可以失去一点体力，然后摸两张牌。每回合中，你可以多次使用苦肉。\n"
				+ "★当你失去最后一点体力时，优先结算濒死事件，当你被救活后，你才可以摸两张牌。换言之，你可以用此技能自杀。";
		this.dispName = "黄盖";
		this.jiNengN1 = "苦肉";
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
			this.gameApp.libGameViewData.mJiNengBtn1.setEnabled(true);
			this.gameApp.libGameViewData.mJiNengBtn1Txt.setText(this.jiNengN1);
		}
		// invoke super to set the correct JiNengBtnImage
		super.enableWuJiangJiNengBtn();
	}

	public void handleJiNengBtnEvent(int eventID) {

		switch (eventID) {
		case R.id.JiNeng1: {

			if (this.blood <= 0)
				return;

			if (this.state != Type.State.ChuPai)
				return;

			this.gameApp.ynData.reset();
			this.gameApp.ynData.okTxt = "确认";
			this.gameApp.ynData.cancelTxt = "取消";
			this.gameApp.ynData.genInfo = "是否使用" + this.jiNengN1 + "?";

			YesNoDialog dlg = new YesNoDialog(this.gameApp.gameActivityContext,
					this.gameApp);
			dlg.showDialog();
			if (!this.gameApp.ynData.result)
				return;

			KuRou krCP = new KuRou(Type.CardPai.WJJiNeng,
					Type.CardPaiClass.nil, 0);
			krCP.belongToWuJiang = this;
			krCP.gameApp = this.gameApp;

			krCP.selectedByClick = true;

			// reset myWuJiang shoupai to unselect card pai
			this.resetShouPaiSelectedBoolean();
			// then add liJian into shoupai list
			this.shouPai.add(krCP);

			// then select tarWJ
			if (gameApp.gameLogicData.askForPai == Type.CardPai.notNil) {
				this.gameApp.gameLogicData.userInterface.loop = true;
				this.gameApp.gameLogicData.userInterface
						.sendMessageToUIForWakeUp();
			}

			break;
		}
		}
	}

	// For AI: Add KuRou cardpai into shoupai
	public CardPai generateJiNengCardPai() {

		if (!this.tuoGuan)
			return null;

		boolean kr = false;

		if (this.role == Type.Role.ZhuGong || this.role == Type.Role.ZhongChen
				|| this.role == Type.Role.NeiJian) {
			if (this.blood == this.getMaxBlood())
				kr = true;
			if (this.blood < this.getMaxBlood() && this.blood >= 2
					&& this.shouPai.size() <= this.blood)
				kr = true;
		} else if (this.role == Type.Role.FanZei) {
			if (this.blood > 2)
				kr = true;

			if (this.blood > 1) {
				if (this.zhuangBei.wuQi != null
						&& this.zhuangBei.wuQi instanceof ZhuGeLianNu)
					kr = true;
			}
		}

		if (!kr)
			return null;

		KuRou krCP = new KuRou(Type.CardPai.WJJiNeng, Type.CardPaiClass.nil, 0);
		krCP.belongToWuJiang = this;
		krCP.gameApp = this.gameApp;

		return krCP;
	}
}
