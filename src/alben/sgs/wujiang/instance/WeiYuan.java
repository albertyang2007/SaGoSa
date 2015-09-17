package alben.sgs.wujiang.instance;

import alben.sgs.cardpai.CardPai;
import alben.sgs.type.Type;
import alben.sgs.wujiang.WuJiang;
import alben.sgs.wujiang.instance.jineng.KuangGu;
import android.view.View;

public class WeiYuan extends WuJiang {
	public WeiYuan(Type.WuJiang n, Type.Country c, Type.Sex s, int b) {
		super(n, c, s, b);
		this.imageNumber = alben.sgs.android.R.drawable.wj_weiyan;
		this.jiNengDesc = "(����չ���佫)\n"
				+ "��ǣ����������κ�ʱ����ÿ���������1���ڵ�һ����ɫ���1���˺�����ظ�1��������";
		this.dispName = "κ��";
		this.jiNengN1 = "���";
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

	// jiNengN1
	public void listenIncreaseBloodEvent(WuJiang tarWJ, CardPai srcCP) {

		if (srcCP.shangHaiN >= 0)
			return;

		if (this.blood >= this.getMaxBlood())
			return;

		int distance = this.gameApp.gameLogicData.wjHelper.countDistance(this,
				tarWJ, false);
		if (distance <= 1) {
			KuangGu kg = new KuangGu(Type.CardPai.nil, Type.CardPaiClass.nil, 0);
			kg.gameApp = this.gameApp;
			kg.belongToWuJiang = this;
			this.gameApp.libGameViewData.logInfo(this.dispName + this.jiNengN1
					+ "����", Type.logDelay.Delay);
			this.increaseBlood(this, kg);
		}
	}
}