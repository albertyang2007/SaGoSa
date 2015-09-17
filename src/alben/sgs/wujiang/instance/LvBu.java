package alben.sgs.wujiang.instance;

import alben.sgs.android.R;
import alben.sgs.type.Type;
import alben.sgs.wujiang.WuJiang;
import android.view.View;

public class LvBu extends WuJiang {
	public LvBu(Type.WuJiang n, Type.Country c, Type.Sex s, int b) {
		super(n, c, s, b);
		this.imageNumber = alben.sgs.android.R.drawable.wj_lvbu;
		this.jiNengDesc = "��˫������������ʹ�á�ɱ��ʱ��Ŀ���ɫ������ʹ�����š��������ܵ�����������С��������Ľ�ɫÿ��������������š�ɱ����\n"
				+ "�����Է�ֻ��һ�š�������ɱ���򼴱�ʹ�ã��������Ҳ��Ч��";
		this.dispName = "����";
		this.jiNengN1 = "��˫";
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

			// for ZhangJiao's HuangTian
			if (this.gameApp.gameLogicData.zhuGongWuJiang instanceof ZhangJiao) {
				this.gameApp.libGameViewData.mJiNengBtn2.setEnabled(true);
				this.gameApp.libGameViewData.mJiNengBtn2Txt
						.setText(this.gameApp.gameLogicData.zhuGongWuJiang.jiNengN3);
			}
		}
		// invoke super to set the correct JiNengBtnImage
		super.enableWuJiangJiNengBtn();
	}

	public void handleJiNengBtnEvent(int eventID) {

		switch (eventID) {
		case R.id.JiNeng2: {
			super.handleHuangTianJiNengBtnEvent();
			break;
		}
		}
	}
}
