package alben.sgs.wujiang.instance.jineng;

import alben.sgs.cardpai.CardPai;
import alben.sgs.type.Type;
import alben.sgs.type.UpdateWJViewData;
import alben.sgs.wujiang.WuJiang;

public class HuangTian extends CardPai {
	public CardPai shanCP = null;

	public HuangTian(Type.CardPai na, Type.CardPaiClass c, int n) {
		super(na, c, n);
		this.ID = generateID++;
		this.dispName = "ª∆ÃÏ";
		this.cpState = Type.CPState.nil;
		this.name = Type.CardPai.WJJiNeng;
	}

	public boolean work(WuJiang srcWJ, WuJiang tarWJ, CardPai tarCP) {

		srcWJ.huangTianJiNengTrigger = true;

		srcWJ.detatchCardPaiFromShouPai(shanCP);

		this.gameApp.gameLogicData.zhuGongWuJiang.shouPai.add(shanCP);
		shanCP.belongToWuJiang = this.gameApp.gameLogicData.zhuGongWuJiang;
		shanCP.cpState = Type.CPState.ShouPai;

		if (this.gameApp.gameLogicData.zhuGongWuJiang.tuoGuan) {
			// update shou pai number
			UpdateWJViewData item = new UpdateWJViewData();
			item.updateShouPaiNumber = true;
			this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(
					this.gameApp.gameLogicData.zhuGongWuJiang, item);
		} else {
			this.gameApp.gameLogicData.wjHelper.updateWJ8ShouPaiToLibGameView();
		}

		this.gameApp.libGameViewData.logInfo(this.dispName + "["
				+ this.dispName + "]"
				+ this.gameApp.gameLogicData.zhuGongWuJiang.dispName + "1’≈≈∆"
				+ shanCP, Type.logDelay.Delay);

		return true;
	}
}
