package alben.sgs.cardpai.instance;

import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.JinNangCardPai;
import alben.sgs.type.Type;
import alben.sgs.type.Type.JinNangApplyTo;
import alben.sgs.type.UpdateWJViewData;
import alben.sgs.wujiang.WuJiang;

public class WuZhongShengYou extends JinNangCardPai {
	public WuZhongShengYou(Type.CardPai na, Type.CardPaiClass c, int n,
			int imgNumber, JinNangApplyTo at) {
		super(na, c, n, imgNumber, at);
		this.dispName = "无中生有";
		this.eventImpactN = 1;
	}

	public boolean work(WuJiang srcWJ, WuJiang tarWJ, CardPai tarCP) {

		this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "使用" + this,
				Type.logDelay.Delay);

		this.listenPreWorkEvent(srcWJ, tarWJ, tarCP);

		if (srcWJ.askForWuXieKeJi(srcWJ, this, srcWJ, this)) {
			this.gameApp.libGameViewData.logInfo(this.dispName + "被无懈了",
					Type.logDelay.NoDelay);
		} else {
			// pop two shou pai
			CardPai cp1 = this.gameApp.gameLogicData.cpHelper.popCardPai();
			CardPai cp2 = this.gameApp.gameLogicData.cpHelper.popCardPai();

			cp1.belongToWuJiang = srcWJ;
			cp2.belongToWuJiang = srcWJ;
			cp1.cpState = Type.CPState.ShouPai;
			cp2.cpState = Type.CPState.ShouPai;
			srcWJ.shouPai.add(cp1);
			srcWJ.shouPai.add(cp2);

			if (srcWJ.equals(this.gameApp.gameLogicData.myWuJiang)) {
				this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "摸起2张手牌",
						Type.logDelay.Delay);
				this.gameApp.gameLogicData.wjHelper
						.updateWJ8ShouPaiToLibGameView();
			} else {
				UpdateWJViewData item = new UpdateWJViewData();
				item.updateShouPaiNumber = true;
				this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(
						srcWJ, item);
				this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "摸起2张手牌",
						Type.logDelay.Delay);
			}
		}
		return true;
	}
}
