package alben.sgs.wujiang.instance.jineng;

import alben.sgs.android.R;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.instance.Sha;
import alben.sgs.type.Type;
import alben.sgs.wujiang.WuJiang;

public class ShenSu extends Sha {
	public CardPai wuQi = null;

	public ShenSu(Type.CardPai na, Type.CardPaiClass c, int n) {
		super(na, c, n, R.drawable.card_back);
		this.ID = generateID++;
		this.dispName = "ÉñËÙ";
		this.shangHaiN = -1;
		this.cpState = Type.CPState.nil;
		this.name = Type.CardPai.WJJiNeng;
	}

	public String toString() {
		return "É±";
	}

	public boolean work(WuJiang srcWJ, WuJiang tarWJ, CardPai tarCP) {
		return super.commonShaWork(srcWJ, tarWJ, tarCP);
	}

	public void selectTarWJForAI() {
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
