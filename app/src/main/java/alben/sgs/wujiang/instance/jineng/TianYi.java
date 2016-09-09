package alben.sgs.wujiang.instance.jineng;

import alben.sgs.cardpai.CardPai;
import alben.sgs.type.Type;
import alben.sgs.wujiang.WuJiang;
import alben.sgs.wujiang.instance.TaiShiChi;

public class TianYi extends CardPai {
	public CardPai sp1 = null;

	public TianYi(Type.CardPai na, Type.CardPaiClass c, int n) {
		super(na, c, n);
		this.ID = generateID++;
		this.dispName = "天义";
		this.cpState = Type.CPState.nil;
		this.name = Type.CardPai.WJJiNeng;
	}

	public String toString() {
		return this.dispName;
	}

	public boolean work(WuJiang srcWJ, WuJiang tarWJ, CardPai tarCP1) {

		srcWJ.oneTimeJiNengTrigger = true;

		this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "["
				+ this.dispName + "]" + tarWJ.dispName, Type.logDelay.Delay);

		CardPai tarCP = tarWJ.askOneCPForCompare(srcWJ);

		if (tarCP == null)
			return false;

		String info = "失败";
		if (this.sp1.number > tarCP.number) {
			// Ok, I can kill 1 more Oppt
			((TaiShiChi) srcWJ).tianYiSuccess = true;
			info = "成功";
		}

		srcWJ.detatchCardPaiFromShouPai(this.sp1);
		tarWJ.detatchCardPaiFromShouPai(tarCP);

		if (!srcWJ.tuoGuan || !tarWJ.tuoGuan) {
			this.gameApp.gameLogicData.wjHelper.updateWJ8ShouPaiToLibGameView();
		}

		this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "的牌是" + this.sp1,
				Type.logDelay.HalfDelay);

		this.gameApp.libGameViewData.logInfo(tarWJ.dispName + "的牌是" + tarCP
				+ "," + this.dispName + info, Type.logDelay.HalfDelay);

		return true;
	}

	public void selectTarWJForAI() {
		this.tarWJForAI = null;
		if (this.belongToWuJiang != null) {

			// select Oppt first, select max shou pai one
			if (this.belongToWuJiang.opponentList.size() > 0) {
				this.tarWJForAI = this.belongToWuJiang.opponentList.get(0);
				for (int i = 1; i < this.belongToWuJiang.opponentList.size(); i++) {
					WuJiang tmpWJ = this.belongToWuJiang.opponentList.get(i);
					if (this.tarWJForAI.shouPai.size() < tmpWJ.shouPai.size()
							&& tmpWJ.shouPai.size() > 0) {
						this.tarWJForAI = tmpWJ;
					}
				}
			}

			// select Friend seconds, get the max shou pai one
			if (this.tarWJForAI == null
					&& this.belongToWuJiang.friendList.size() > 0) {
				this.tarWJForAI = this.belongToWuJiang.friendList.get(0);
				for (int i = 1; i < this.belongToWuJiang.friendList.size(); i++) {
					WuJiang tmpWJ = this.belongToWuJiang.friendList.get(i);
					if (this.tarWJForAI.shouPai.size() < tmpWJ.shouPai.size()
							&& tmpWJ.shouPai.size() > 0) {
						this.tarWJForAI = tmpWJ;
					}
				}
			}

			// reset to null, since no shou pai can not compare
			if (this.tarWJForAI != null && this.tarWJForAI.shouPai.size() == 0) {
				this.tarWJForAI = null;
			}
		}
	}
}
