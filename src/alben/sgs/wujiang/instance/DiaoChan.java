package alben.sgs.wujiang.instance;

import alben.sgs.android.R;
import alben.sgs.android.dialog.SelectCardPaiFromWJDialog;
import alben.sgs.android.dialog.YesNoDialog;
import alben.sgs.cardpai.CardPai;
import alben.sgs.type.Type;
import alben.sgs.type.UpdateWJViewData;
import alben.sgs.type.WuJiangCardPaiData;
import alben.sgs.wujiang.WuJiang;
import alben.sgs.wujiang.instance.jineng.LiJian;
import android.view.View;

public class DiaoChan extends WuJiang {
	public DiaoChan(Type.WuJiang n, Type.Country c, Type.Sex s, int b) {
		super(n, c, s, b);
		this.imageNumber = alben.sgs.android.R.drawable.wj_diaochan;
		this.jiNengDesc = "1����䣺���ƽ׶Σ��������һ���Ʋ�ѡ���������Խ�ɫ�������������Ϊ����һ�����Խ�ɫ����һ�����Խ�ɫʹ��һ�š������������ˡ����������ܱ�����и�ɻ�����Ӧ����ÿ�غ�����һ�Ρ�\n"
				+ "2�����£��غϽ����׶Σ�����һ���ơ�";
		this.dispName = "����";
		this.jiNengN1 = "���";
		this.jiNengN2 = "����";
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

			this.gameApp.libGameViewData.mJiNengBtn2
					.setVisibility(View.VISIBLE);
			this.gameApp.libGameViewData.mJiNengBtn2.setEnabled(false);
			this.gameApp.libGameViewData.mJiNengBtn2Txt.setText(this.jiNengN2);

			// for ZhangJiao's HuangTian
			if (this.gameApp.gameLogicData.zhuGongWuJiang instanceof ZhangJiao) {
				this.gameApp.libGameViewData.mJiNengBtn3
						.setVisibility(View.VISIBLE);
				this.gameApp.libGameViewData.mJiNengBtn3.setEnabled(true);
				this.gameApp.libGameViewData.mJiNengBtn3Txt
						.setText(this.gameApp.gameLogicData.zhuGongWuJiang.jiNengN3);
			}
		}
		// invoke super to set the correct JiNengBtnImage
		super.enableWuJiangJiNengBtn();
	}

	public void listenExitHuiHeEvent() {
		CardPai cp = this.gameApp.gameLogicData.cpHelper.popCardPai();

		cp.belongToWuJiang = this;
		cp.cpState = Type.CPState.ShouPai;
		this.shouPai.add(cp);

		this.gameApp.libGameViewData.logInfo(this.dispName + "["
				+ this.jiNengN2 + "],��1����", Type.logDelay.NoDelay);

		if (!this.tuoGuan) {
			this.gameApp.gameLogicData.wjHelper.updateWJ8ShouPaiToLibGameView();
		} else {
			UpdateWJViewData item = new UpdateWJViewData();
			item.updateShouPaiNumber = true;
			this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(
					this, item);
		}
	}

	// For AI: Add LiJian cardpai into shoupai
	public CardPai generateJiNengCardPai() {

		if (this.oneTimeJiNengTrigger) {
			return null;
		}

		if (!this.tuoGuan)
			return null;

		if (this.shouPai.size() == 0)
			return null;

		LiJian liJian = new LiJian(Type.CardPai.WJJiNeng,
				Type.CardPaiClass.nil, 0);
		liJian.belongToWuJiang = this;
		liJian.gameApp = this.gameApp;
		// select one shou pai
		liJian.sp1 = this.shouPai.get(0);

		liJian.selectTarWJForAI();

		if (liJian.tarWJ1 == null || liJian.tarWJ2 == null)
			return null;

		return liJian;
	}

	public void handleJiNengBtnEvent(int eventID) {

		switch (eventID) {
		case R.id.JiNeng1: {
			if (this.state != Type.State.ChuPai)
				return;

			if (this.oneTimeJiNengTrigger) {
				this.gameApp.libGameViewData.logInfo(this.jiNengN1 + "ֻ�ܷ���һ��",
						Type.logDelay.NoDelay);
				return;
			}

			if (this.shouPai.size() == 0) {
				this.gameApp.libGameViewData.logInfo("��û�����Ʋ��ܷ���"
						+ this.jiNengN1, Type.logDelay.NoDelay);
				return;
			}

			// check if at least two man is alive
			int manN = 0;
			WuJiang tarWJ = this.nextOne;
			while (!tarWJ.equals(this)) {
				if (tarWJ.sex == Type.Sex.man) {
					manN++;
				}
				tarWJ = tarWJ.nextOne;
			}

			if (manN < 2) {
				this.gameApp.libGameViewData.logInfo("�����佫����,���ܷ���"
						+ this.jiNengN1, Type.logDelay.NoDelay);
				return;
			}

			this.gameApp.ynData.reset();
			this.gameApp.ynData.okTxt = "ȷ��";
			this.gameApp.ynData.cancelTxt = "ȡ��";
			this.gameApp.ynData.genInfo = "�Ƿ�ʹ��" + this.jiNengN1 + "?";

			YesNoDialog dlg = new YesNoDialog(this.gameApp.gameActivityContext,
					this.gameApp);
			dlg.showDialog();
			if (!this.gameApp.ynData.result)
				return;

			LiJian liJian = new LiJian(Type.CardPai.WJJiNeng,
					Type.CardPaiClass.nil, 0);
			liJian.belongToWuJiang = this;
			liJian.gameApp = this.gameApp;

			// first select one shoupai
			this.gameApp.wjDetailsViewData.reset();
			this.gameApp.wjDetailsViewData.selectedCardNumber = 1;
			this.gameApp.wjDetailsViewData.canViewShouPai = true;

			this.gameApp.wjDetailsViewData.selectedWJ = this;

			WuJiangCardPaiData wjCPData = new WuJiangCardPaiData();
			wjCPData.shouPai = this.shouPai;

			SelectCardPaiFromWJDialog dlg2 = new SelectCardPaiFromWJDialog(
					this.gameApp.gameActivityContext, this.gameApp, wjCPData);
			dlg2.showDialog();

			liJian.sp1 = this.gameApp.wjDetailsViewData.selectedCardPai1;
			liJian.imageNumber = liJian.sp1.imageNumber;

			liJian.selectedByClick = true;

			// reset myWuJiang shoupai to unselect card pai
			this.resetShouPaiSelectedBoolean();
			// then add liJian into shoupai list
			this.shouPai.add(liJian);

			// then select tarWJ
			if (gameApp.gameLogicData.askForPai == Type.CardPai.notNil) {
				liJian.onClickUpdateView();
			}
			break;
		}
		case R.id.JiNeng3: {
			super.handleHuangTianJiNengBtnEvent();
			break;
		}
		}
	}

	// check if ZhuGeLiang is kongCheng
	public boolean canSelectAsFirstChuSha(WuJiang tarWJ) {
		if (tarWJ instanceof ZhuGeLiang) {
			if (tarWJ.shouPai.size() == 0) {
				return false;
			}
		}
		return true;
	}
}
