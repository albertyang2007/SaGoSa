package alben.sgs.wujiang.instance;

import alben.sgs.android.dialog.YesNoDialog;
import alben.sgs.cardpai.CardPai;
import alben.sgs.type.Type;
import alben.sgs.wujiang.WuJiang;
import alben.sgs.wujiang.instance.jineng.TieJi;
import android.view.View;

public class MaChao extends WuJiang {
	public MaChao(Type.WuJiang n, Type.Country c, Type.Sex s, int b) {
		super(n, c, s, b);
		this.imageNumber = alben.sgs.android.R.drawable.wj_machao;
		this.jiNengDesc = "1�������������������������������ɫ�ľ���ʱ��ʼ��-1\n"
				+ "2���������ʹ�á�ɱ��ָ��һ����ɫΪĿ�������Խ����ж��������Ϊ��ɫ���ˡ�ɱ�����ɱ�����\n"
				+ "��������Ч����װ��-1��ʱЧ��һ����������Ȼ����װ��һƥ-1��";
		this.dispName = "��";
		this.jiNengN1 = "����";
		this.jiNengN2 = "����";
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

			this.gameApp.libGameViewData.mJiNengBtn2
					.setVisibility(View.VISIBLE);
			this.gameApp.libGameViewData.mJiNengBtn2.setEnabled(false);
			this.gameApp.libGameViewData.mJiNengBtn2Txt.setText(this.jiNengN2);
		}
		// invoke super to set the correct JiNengBtnImage
		super.enableWuJiangJiNengBtn();
	}

	// maShu
	public int getJinGongDistance() {
		return 1;
	}

	// TieJi
	public boolean checkShaMingZhong(WuJiang tarWJ) {
		if (tarWJ == null)
			return false;

		boolean tj = false;
		String info = "ʧ��";

		if (!this.tuoGuan) {
			this.gameApp.ynData.reset();
			this.gameApp.ynData.okTxt = "ȷ��";
			this.gameApp.ynData.cancelTxt = "ȡ��";
			this.gameApp.ynData.genInfo = "�Ƿ��" + tarWJ.dispName + "ʹ��"
					+ this.jiNengN2 + "?";

			YesNoDialog dlg = new YesNoDialog(this.gameApp.gameActivityContext,
					this.gameApp);
			dlg.showDialog();
			if (!this.gameApp.ynData.result)
				return false;
		}

		TieJi tjCP = new TieJi(Type.CardPai.nil, Type.CardPaiClass.nil, 0);
		tjCP.belongToWuJiang = this;
		tjCP.gameApp = this.gameApp;

		CardPai pdCP = this.gameApp.gameLogicData.cpHelper
				.popCardPaiForPanDing(tarWJ, tjCP);

		if (pdCP.clas == Type.CardPaiClass.FangPian
				|| pdCP.clas == Type.CardPaiClass.HongTao) {
			tj = true;
			info = "�ɹ�,��ɱ���ɱ�����";
		}

		this.gameApp.libGameViewData.logInfo(this.dispName + "["
				+ this.jiNengN2 + "]" + info, Type.logDelay.Delay);

		return tj;
	}
}