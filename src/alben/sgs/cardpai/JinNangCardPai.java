package alben.sgs.cardpai;

import alben.sgs.type.Type;
import alben.sgs.type.Type.JinNangApplyTo;
import alben.sgs.type.UpdateWJViewData;
import alben.sgs.wujiang.WuJiang;
import alben.sgs.wujiang.instance.HuangYueYing;

public class JinNangCardPai extends CardPai {
	public JinNangApplyTo applyTo = JinNangApplyTo.nil;

	// 0 ���Լ��к�,1���Լ�����;Ĭ�� 0
	// 0 �� ������,�����,1������԰,���
	public int eventImpactN = 0;
	// ��ʱ
	public boolean delay = false;

	public JinNangCardPai(Type.CardPai na, Type.CardPaiClass c, int n,
			int imgNumber, JinNangApplyTo at) {
		super(na, c, n, imgNumber);
		this.applyTo = at;
	}

	// before chu one cardpai, hand it
	// for huangyueying
	public void listenPreWorkEvent(WuJiang srcWJ, WuJiang tarWJ, CardPai tarCP) {
		if (!this.delay && srcWJ instanceof HuangYueYing) {
			this.gameApp.gameLogicData.cpHelper.addCardPaiToWuJiang(srcWJ, 1);

			this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "["
					+ srcWJ.jiNengN1 + "],������1����", Type.logDelay.Delay);

			if (!srcWJ.tuoGuan) {
				this.gameApp.gameLogicData.wjHelper
						.updateWJ8ShouPaiToLibGameView();
			} else {
				UpdateWJViewData item = new UpdateWJViewData();
				item.updateShouPaiNumber = true;
				this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(
						srcWJ, item);
			}
		}
	}
}
