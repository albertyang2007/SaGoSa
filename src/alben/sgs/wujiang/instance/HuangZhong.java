package alben.sgs.wujiang.instance;

import alben.sgs.android.dialog.YesNoDialog;
import alben.sgs.type.Type;
import alben.sgs.wujiang.WuJiang;
import android.view.View;

public class HuangZhong extends WuJiang {
	public HuangZhong(Type.WuJiang n, Type.Country c, Type.Sex s, int b) {
		super(n, c, s, b);
		this.imageNumber = alben.sgs.android.R.drawable.wj_huangzhong;
		this.jiNengDesc = "(����չ���佫)\n"
				+ "�ҹ������ƽ׶Σ�����������������������ʹ�õġ�ɱ�����ɱ����ܣ�1��Ŀ���ɫ�����������ڻ�����������ֵ��2��Ŀ���ɫ��������С�ڻ������Ĺ�����Χ��\n"
				+ "�﹥����Χ����ֻ�������йأ���+1��-1���޹ء�";
		this.dispName = "����";
		this.jiNengN1 = "�ҹ�";
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
			this.gameApp.ynData.okTxt = "ȷ��";
			this.gameApp.ynData.cancelTxt = "ȡ��";
			this.gameApp.ynData.genInfo = "�Ƿ��" + tarWJ.dispName + "ʹ��"
					+ this.jiNengN1 + "?";

			YesNoDialog dlg = new YesNoDialog(this.gameApp.gameActivityContext,
					this.gameApp);
			dlg.showDialog();
			lg = this.gameApp.ynData.result;
		}

		if (lg) {
			this.gameApp.libGameViewData.logInfo(this.dispName + "["
					+ this.jiNengN1 + "]" + tarWJ.dispName + ",ɱ��������!",
					Type.logDelay.Delay);
		}

		return lg;
	}
}
