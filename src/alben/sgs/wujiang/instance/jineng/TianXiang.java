package alben.sgs.wujiang.instance.jineng;

import alben.sgs.cardpai.CardPai;
import alben.sgs.type.Type;
import alben.sgs.wujiang.WuJiang;

public class TianXiang extends CardPai {
	public CardPai srcCP = null;
	public CardPai sp1 = null;

	public TianXiang(Type.CardPai na, Type.CardPaiClass c, int n) {
		super(na, c, n);
		this.ID = generateID++;
		this.dispName = "天香";
		this.cpState = Type.CPState.nil;
		this.name = Type.CardPai.WJJiNeng;
	}

	public String toString() {
		return this.dispName;
	}

	public boolean work(WuJiang srcWJ, WuJiang tarWJ, CardPai srcCP) {
		int origSHN = srcCP.shangHaiN;

		this.belongToWuJiang.detatchCardPaiFromShouPai(this.sp1);

		this.gameApp.libGameViewData.logInfo(this.belongToWuJiang.dispName
				+ "[" + this.dispName + "]" + this.sp1 + ",将"
				+ Math.abs(srcCP.shangHaiN) + "点伤害转移给" + tarWJ.dispName,
				Type.logDelay.Delay);

		tarWJ.increaseBlood(this.belongToWuJiang, srcCP);

		if (tarWJ.state == Type.State.Dead)
			return true;

		int addCPN = tarWJ.getMaxBlood() - tarWJ.blood;

		this.gameApp.gameLogicData.cpHelper.addCardPaiToWuJiang(tarWJ, addCPN);

		this.gameApp.libGameViewData.logInfo(tarWJ.dispName + "减"
				+ Math.abs(srcCP.shangHaiN) + "血,摸起" + addCPN + "张牌",
				Type.logDelay.Delay);

		srcCP.shangHaiN = origSHN;

		return true;
	}

	public void selectTarWJForAI() {
		this.tarWJForAI = null;
		if (this.belongToWuJiang != null) {
			// select zhuGong
			if (this.belongToWuJiang
					.isOpponent(this.gameApp.gameLogicData.zhuGongWuJiang)) {
				this.tarWJForAI = this.gameApp.gameLogicData.zhuGongWuJiang;
			} else {
				// select the least blood Oppt
				if (this.belongToWuJiang.opponentList.size() > 0)
					this.tarWJForAI = this.belongToWuJiang.opponentList.get(0);
				for (int i = 1; i < this.belongToWuJiang.opponentList.size(); i++) {
					WuJiang tmpWJ = this.belongToWuJiang.opponentList.get(i);
					if (this.tarWJForAI.blood > tmpWJ.blood)
						this.tarWJForAI = tmpWJ;
				}
			}
		}
	}
}
