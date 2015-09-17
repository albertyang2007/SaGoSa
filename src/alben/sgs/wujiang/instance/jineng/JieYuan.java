package alben.sgs.wujiang.instance.jineng;

import alben.sgs.cardpai.CardPai;
import alben.sgs.type.Type;
import alben.sgs.wujiang.WuJiang;

public class JieYuan extends CardPai {
	public CardPai sp1, sp2 = null;

	public JieYuan(Type.CardPai na, Type.CardPaiClass c, int n) {
		super(na, c, n);
		this.ID = generateID++;
		this.dispName = "½áÒö";
		this.shangHaiN = 1;
		this.cpState = Type.CPState.nil;
		this.name = Type.CardPai.WJJiNeng;
	}

	public String toString() {
		return this.dispName;
	}

	public boolean work(WuJiang srcWJ, WuJiang tarWJ, CardPai tarCP) {

		if (srcWJ.oneTimeJiNengTrigger) {
			return false;
		} else {
			srcWJ.oneTimeJiNengTrigger = true;
		}

		this.sp1.belongToWuJiang = null;
		this.sp2.belongToWuJiang = null;
		srcWJ.detatchCardPaiFromShouPai(this.sp1);
		srcWJ.detatchCardPaiFromShouPai(this.sp2);

		if (!srcWJ.tuoGuan)
			this.gameApp.gameLogicData.wjHelper.updateWJ8ShouPaiToLibGameView();

		this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "Æúµô" + this.sp1
				+ "," + this.sp2 + " ºÍ" + tarWJ.dispName + this.dispName,
				Type.logDelay.Delay);
		if (srcWJ.blood < srcWJ.getMaxBlood()) {
			srcWJ.increaseBlood(srcWJ, this);
		}
		tarWJ.increaseBlood(srcWJ, this);

		return true;
	}

	public void selectTarWJForAI() {
		this.tarWJForAI = null;
		if (this.belongToWuJiang != null) {

			if (this.belongToWuJiang
					.isFriend(this.gameApp.gameLogicData.zhuGongWuJiang)
					&& this.gameApp.gameLogicData.zhuGongWuJiang.sex == Type.Sex.man
					&& this.gameApp.gameLogicData.zhuGongWuJiang.blood < this.gameApp.gameLogicData.zhuGongWuJiang
							.getMaxBlood()) {
				this.tarWJForAI = this.gameApp.gameLogicData.zhuGongWuJiang;
				return;
			}

			for (int i = 0; i < this.belongToWuJiang.friendList.size(); i++) {
				WuJiang tmpWJ = this.belongToWuJiang.friendList.get(i);
				if (tmpWJ.sex == Type.Sex.man
						&& tmpWJ.blood < tmpWJ.getMaxBlood()) {
					this.tarWJForAI = tmpWJ;
					return;
				}
			}
		}
	}

}
