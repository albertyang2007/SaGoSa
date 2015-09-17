package alben.sgs.wujiang.instance;

import alben.sgs.android.dialog.YesNoDialog;
import alben.sgs.type.Type;
import alben.sgs.type.UpdateWJViewData;
import alben.sgs.wujiang.WuJiang;
import android.view.View;

public class CaoRen extends WuJiang {

	public CaoRen(Type.WuJiang n, Type.Country c, Type.Sex s, int b) {
		super(n, c, s, b);
		this.imageNumber = alben.sgs.android.R.drawable.wj_caoren;
		this.jiNengDesc = "(����չ���佫)\n" + "���أ��غϽ����׶Σ�������������ƣ�����������������¸��غ�";
		this.dispName = "����";
		this.jiNengN1 = "����";
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
		}
		// invoke super to set the correct JiNengBtnImage
		super.enableWuJiangJiNengBtn();
	}

	public void listenExitHuiHeEvent() {
		if (!this.fanMian) {
			this.juShou();
		}
	}

	public void juShou() {
		if (this.tuoGuan) {
			// for AI
			if (this.blood < this.getOrigMaxBlood()
					&& this.shouPai.size() < this.blood) {
				this.fanMian = true;

				this.gameApp.gameLogicData.cpHelper
						.addCardPaiToWuJiang(this, 3);

				UpdateWJViewData item = new UpdateWJViewData();
				item.updateShouPaiNumber = true;
				item.updateFanMian = true;
				this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(
						this, item);

				this.gameApp.libGameViewData.logInfo(this.dispName + "["
						+ this.jiNengN1 + "],��3����", Type.logDelay.NoDelay);
			}
		} else {
			this.gameApp.ynData.reset();
			this.gameApp.ynData.okTxt = "ȷ��";
			this.gameApp.ynData.cancelTxt = "ȡ��";
			this.gameApp.ynData.genInfo = "�Ƿ񷢶�" + this.jiNengN1 + "?";

			YesNoDialog dlg = new YesNoDialog(this.gameApp.gameActivityContext,
					this.gameApp);
			dlg.showDialog();

			if (this.gameApp.ynData.result) {
				this.fanMian = true;

				this.gameApp.gameLogicData.cpHelper
						.addCardPaiToWuJiang(this, 3);

				this.gameApp.gameLogicData.wjHelper
						.updateWJ8ShouPaiToLibGameView();

				UpdateWJViewData item = new UpdateWJViewData();
				item.updateShouPaiNumber = true;
				item.updateFanMian = true;
				this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(
						this, item);

				this.gameApp.libGameViewData.logInfo(this.dispName + "["
						+ this.jiNengN1 + "],��3����", Type.logDelay.NoDelay);
			}
		}
	}
}
