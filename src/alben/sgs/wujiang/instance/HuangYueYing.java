package alben.sgs.wujiang.instance;

import alben.sgs.type.Type;
import alben.sgs.wujiang.WuJiang;
import android.view.View;

public class HuangYueYing extends WuJiang {
	public HuangYueYing(Type.WuJiang n, Type.Country c, Type.Sex s, int b) {
		super(n, c, s, b);
		this.imageNumber = alben.sgs.android.R.drawable.wj_huangyueying;
		this.jiNengDesc = "1�����ǣ�ÿ����ʹ��һ�ŷ���ʱ�����ʱ������������֮ǰ�������������һ���ơ�\n"
				+ "2����ţ���ʹ���κν����޾������ơ�";
		this.dispName = "����Ӣ";
		this.jiNengN1 = "����";
		this.jiNengN2 = "���";
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

			this.gameApp.libGameViewData.mJiNengBtn2
					.setVisibility(View.VISIBLE);
			this.gameApp.libGameViewData.mJiNengBtn2.setEnabled(false);
			this.gameApp.libGameViewData.mJiNengBtn2Txt.setText(this.jiNengN2);
		}
		// invoke super to set the correct JiNengBtnImage
		super.enableWuJiangJiNengBtn();
	}
}
