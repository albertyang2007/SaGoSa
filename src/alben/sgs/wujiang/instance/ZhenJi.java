package alben.sgs.wujiang.instance;

import alben.sgs.android.R;
import alben.sgs.android.dialog.SelectCardPaiFromWJDialog;
import alben.sgs.android.dialog.YesNoDialog;
import alben.sgs.cardpai.CardPai;
import alben.sgs.type.Type;
import alben.sgs.type.UpdateWJViewData;
import alben.sgs.type.WuJiangCardPaiData;
import alben.sgs.wujiang.WuJiang;
import alben.sgs.wujiang.instance.jineng.LuoShen;
import alben.sgs.wujiang.instance.jineng.QingGuo;
import android.view.View;

public class ZhenJi extends WuJiang {
	public ZhenJi(Type.WuJiang n, Type.Country c, Type.Sex s, int b) {
		super(n, c, s, b);
		this.imageNumber = alben.sgs.android.R.drawable.wj_zhenji;
		this.jiNengDesc = "1�����������Խ���ĺ�ɫ���Ƶ�������ʹ�ã�������\n"
				+ "2�����񣺻غϿ�ʼ�׶Σ�����Խ����ж�����Ϊ��ɫ��������ô���Ч����ж��ƣ��������ٴ�ʹ������,��˷�����ֱ�����ֺ�ɫ���㲻Ը���ж���Ϊֹ��\n"
				+ "��ʹ�����ʱ�����ı��Ƶ�������ƣ������ã����ƵĻ�ɫ�͵������䡣";
		this.dispName = "�缧";
		this.jiNengN1 = "���";
		this.jiNengN2 = "����";
	}

	public void listenEnterHuiHeEvent() {
		super.listenEnterHuiHeEvent();
		this.enableWuJiangJiNengBtn();

		this.luoShen();
	}

	public void enableWuJiangJiNengBtn() {
		if (!this.tuoGuan) {
			this.gameApp.libGameViewData.mJiNengBtn1
					.setVisibility(View.VISIBLE);
			this.gameApp.libGameViewData.mJiNengBtn1.setEnabled(true);
			this.gameApp.libGameViewData.mJiNengBtn1Txt.setText(this.jiNengN1);

			this.gameApp.libGameViewData.mJiNengBtn2
					.setVisibility(View.VISIBLE);
			this.gameApp.libGameViewData.mJiNengBtn2.setEnabled(false);
			this.gameApp.libGameViewData.mJiNengBtn2Txt.setText(this.jiNengN2);
		}
		// invoke super to set the correct JiNengBtnImage
		super.enableWuJiangJiNengBtn();
	}

	public void luoShen() {
		LuoShen luoShen = new LuoShen(Type.CardPai.WJJiNeng,
				Type.CardPaiClass.nil, 0);
		luoShen.gameApp = this.gameApp;
		luoShen.belongToWuJiang = this;

		UpdateWJViewData item = new UpdateWJViewData();
		item.updateShouPaiNumber = true;
		if (this.tuoGuan) {
			boolean run = true;
			while (run) {
				CardPai pdCP = this.gameApp.gameLogicData.cpHelper
						.popCardPaiForPanDing(this, luoShen);
				if (pdCP == null) {
					run = false;
				}

				if (pdCP.clas == Type.CardPaiClass.HongTao
						|| pdCP.clas == Type.CardPaiClass.FangPian) {
					run = false;
					this.gameApp.libGameViewData.logInfo(this.dispName + "["
							+ this.jiNengN2 + "]ʧ��", Type.logDelay.Delay);
				} else {
					pdCP.belongToWuJiang = this;
					pdCP.cpState = Type.CPState.ShouPai;
					this.shouPai.add(pdCP);

					this.gameApp.libGameViewData.logInfo(this.dispName + "["
							+ this.jiNengN2 + "]�ɹ�,��ô���",
							Type.logDelay.HalfDelay);

					this.gameApp.gameLogicData.wjHelper
							.updateWuJiangToLibGameView(this, item);
				}
			}// while
		} else {
			this.gameApp.ynData.reset();
			this.gameApp.ynData.okTxt = "ȷ��";
			this.gameApp.ynData.cancelTxt = "ȡ��";
			this.gameApp.ynData.genInfo = "�Ƿ�ʹ��" + this.jiNengN2 + "?";

			YesNoDialog dlg = new YesNoDialog(this.gameApp.gameActivityContext,
					this.gameApp);
			dlg.showDialog();

			boolean run = this.gameApp.ynData.result;
			while (run) {
				CardPai pdCP = this.gameApp.gameLogicData.cpHelper
						.popCardPaiForPanDing(this, luoShen);
				if (pdCP == null) {
					run = false;
				}

				if (pdCP.clas == Type.CardPaiClass.HongTao
						|| pdCP.clas == Type.CardPaiClass.FangPian) {
					run = false;
					this.gameApp.libGameViewData.logInfo(this.dispName + "["
							+ this.jiNengN2 + "]ʧ��", Type.logDelay.Delay);
				} else {
					pdCP.belongToWuJiang = this;
					pdCP.cpState = Type.CPState.ShouPai;
					this.shouPai.add(pdCP);

					this.gameApp.libGameViewData.logInfo(this.dispName + "["
							+ this.jiNengN2 + "]�ɹ�,��ô���",
							Type.logDelay.HalfDelay);

					this.gameApp.gameLogicData.wjHelper
							.updateWJ8ShouPaiToLibGameView();

					// ask user again
					dlg = new YesNoDialog(this.gameApp.gameActivityContext,
							this.gameApp);
					dlg.showDialog();
					run = this.gameApp.ynData.result;
				}
			}// while
		}
	}

	// Just for AI
	public CardPai selectCardPaiFromShouPai(Type.CardPai cpT) {
		CardPai cp = super.selectCardPaiFromShouPai(cpT);
		if (this.tuoGuan && cp == null && cpT == Type.CardPai.Shan) {
			cp = this.hasBlackCardPaiInShouPai();
			if (cp != null) {
				this.gameApp.libGameViewData.logInfo(this.dispName + "["
						+ this.jiNengN1 + "]", Type.logDelay.NoDelay);
			}
		}
		return cp;
	}

	// Black CP as Shan
	public void handleJiNengBtnEvent(int eventID) {

		switch (eventID) {
		case R.id.JiNeng1: {
			if (this.state != Type.State.Response)
				return;

			if (this.shouPai.size() == 0)
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

			// select card pai first
			this.gameApp.wjDetailsViewData.reset();
			this.gameApp.wjDetailsViewData.selectedCardNumber = 1;
			this.gameApp.wjDetailsViewData.canViewShouPai = true;
			this.gameApp.wjDetailsViewData.selectedWJ = this;

			WuJiangCardPaiData wjCPData = new WuJiangCardPaiData();
			for (int i = 0; i < this.shouPai.size(); i++) {
				if (this.shouPai.get(i).clas == Type.CardPaiClass.MeiHua
						|| this.shouPai.get(i).clas == Type.CardPaiClass.HeiTao) {
					wjCPData.shouPai.add(this.shouPai.get(i));
				}
			}

			SelectCardPaiFromWJDialog dlg2 = new SelectCardPaiFromWJDialog(
					this.gameApp.gameActivityContext, this.gameApp, wjCPData);
			dlg2.showDialog();

			CardPai selectCP = this.gameApp.wjDetailsViewData.selectedCardPai1;
			if (selectCP == null)
				return;

			if (gameApp.gameLogicData.askForPai == Type.CardPai.Shan) {
				QingGuo qg = new QingGuo(selectCP.name, selectCP.clas,
						selectCP.number);
				qg.gameApp = this.gameApp;
				qg.imageNumber = selectCP.imageNumber;
				qg.belongToWuJiang = this;
				qg.sp1 = selectCP;

				// remember
				this.detatchCardPaiFromShouPai(selectCP);

				qg.selectedByClick = true;

				// reset myWuJiang shoupai to unselect card pai
				this.resetShouPaiSelectedBoolean();
				// then add sxCP into shoupai list
				this.shouPai.add(qg);

				this.gameApp.gameLogicData.userInterface
						.sendMessageToUIForWakeUp();
			}

			break;
		}
		}
	}
}
