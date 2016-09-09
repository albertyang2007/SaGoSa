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
		this.jiNengDesc = "���⣺���ƽ׶Σ������ʧȥһ��������Ȼ���������ơ�ÿ�غ��У�����Զ��ʹ�ÿ��⡣\n"
				+ "�ﵱ��ʧȥ���һ������ʱ�����Ƚ�������¼������㱻�Ȼ����ſ����������ơ�����֮��������ô˼�����ɱ��";
		this.dispName = "�Ƹ�";
		this.jiNengN1 = "����";
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
			this.gameApp.ynData.okTxt = "ȷ��";
			this.gameApp.ynData.cancelTxt = "ȡ��";
			this.gameApp.ynData.genInfo = "�Ƿ�ʹ��" + this.jiNengN1 + "?";

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
