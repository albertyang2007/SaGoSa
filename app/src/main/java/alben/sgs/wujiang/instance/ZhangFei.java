package alben.sgs.wujiang.instance;

import alben.sgs.type.Type;
import alben.sgs.wujiang.WuJiang;
import android.view.View;

public class ZhangFei extends WuJiang {
	public ZhangFei(Type.WuJiang n, Type.Country c, Type.Sex s, int b) {
		super(n, c, s, b);
		this.imageNumber = alben.sgs.android.R.drawable.wj_zhangfei;
		this.jiNengDesc = "���������ƽ׶Σ������ʹ�����������ġ�ɱ����";
		this.dispName = "�ŷ�";
		this.jiNengN1 = "����";
	}

	public void listenEnterHuiHeEvent() {
		super.listenEnterHuiHeEvent();
		this.enableWuJiangJiNengBtn();
	}
	
	public void enableWuJiangJiNengBtn() {
		if (!this.tuoGuan) {
			this.gameApp.libGameViewData.mJiNengBtn1.setVisibility(View.VISIBLE);
			this.gameApp.libGameViewData.mJiNengBtn1.setEnabled(false);
			this.gameApp.libGameViewData.mJiNengBtn1Txt.setText(this.jiNengN1);
		}
		// invoke super to set the correct JiNengBtnImage
		super.enableWuJiangJiNengBtn();
	}

	// ����:
	public boolean canIChuSha() {
		if (this.state == Type.State.ChuPai && this.huiHeChuShaN > 1) {
			this.specialChuPaiReason = "[����]";
		}
		return true;
	}
}
