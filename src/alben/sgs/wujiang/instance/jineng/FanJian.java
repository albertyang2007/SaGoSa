package alben.sgs.wujiang.instance.jineng;

import alben.sgs.cardpai.CardPai;
import alben.sgs.type.Type;
import alben.sgs.type.UpdateWJViewData;
import alben.sgs.wujiang.WuJiang;
import alben.sgs.wujiang.instance.SunQuan;

public class FanJian extends CardPai {
	public CardPai sp1 = null;

	public FanJian(Type.CardPai na, Type.CardPaiClass c, int n) {
		super(na, c, n);
		this.ID = generateID++;
		this.dispName = "反间";
		this.shangHaiN = -1;
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

		this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "["
				+ tarWJ.dispName + "]" + this.dispName, Type.logDelay.Delay);

		Type.CardPaiClass selectHS = tarWJ.selectHuaShiForFanJian();

		String hsInfo = "";
		if (selectHS == Type.CardPaiClass.FangPian)
			hsInfo = "方片";
		else if (selectHS == Type.CardPaiClass.HeiTao)
			hsInfo = "黑桃";
		else if (selectHS == Type.CardPaiClass.HongTao)
			hsInfo = "红桃";
		else if (selectHS == Type.CardPaiClass.MeiHua)
			hsInfo = "梅花";

		this.gameApp.libGameViewData.logInfo(tarWJ.dispName + "选择了" + hsInfo
				+ "花式", Type.logDelay.Delay);

		CardPai selectCP = tarWJ.selectCardPaiForFanJian(srcWJ);

		this.gameApp.libGameViewData.logInfo(tarWJ.dispName + "抽中了卡牌"
				+ selectCP, Type.logDelay.Delay);

		if (selectCP.clas != selectHS) {
			this.gameApp.libGameViewData.logInfo(tarWJ.dispName + "受到"
					+ this.dispName + "伤害", Type.logDelay.Delay);
			tarWJ.increaseBlood(srcWJ, this);
		} else {
			this.gameApp.libGameViewData.logInfo(srcWJ.dispName + this.dispName
					+ "失败", Type.logDelay.Delay);
		}

		// check if match is over
		if (gameApp.gameLogicData.wjHelper.checkMatchOver())
			return true;

		srcWJ.detatchCardPaiFromShouPai(selectCP);

		selectCP.belongToWuJiang = tarWJ;
		tarWJ.shouPai.add(selectCP);

		if (tarWJ.tuoGuan) {
			// update shou pai number
			UpdateWJViewData item = new UpdateWJViewData();
			item.updateShouPaiNumber = true;
			this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(
					tarWJ, item);
		} else {
			this.gameApp.gameLogicData.wjHelper.updateWJ8ShouPaiToLibGameView();
		}

		return true;
	}

	public void selectTarWJForAI() {
		this.tarWJForAI = null;
		if (this.belongToWuJiang != null) {
			// 1 tao 3 blood!
			if (this.belongToWuJiang.shouPai.size() == 1
					&& this.belongToWuJiang.shouPai.get(0).name == Type.CardPai.Tao
					&& this.gameApp.gameLogicData.zhuGongWuJiang instanceof SunQuan
					&& this.gameApp.gameLogicData.zhuGongWuJiang.blood == 1
					&& this.belongToWuJiang
							.isFriend(this.gameApp.gameLogicData.zhuGongWuJiang)) {
				this.tarWJForAI = this.gameApp.gameLogicData.zhuGongWuJiang;
				return;
			}

			boolean hasTao = this.belongToWuJiang
					.hasTao(this.belongToWuJiang.nextOne);

			boolean canFanJianOppt = true;
			if (hasTao && this.belongToWuJiang.shouPai.size() <= 3) {
				canFanJianOppt = false;
			}

			// select the least blood from Oppt
			if (this.belongToWuJiang.opponentList.size() > 0 && canFanJianOppt) {
				this.tarWJForAI = this.belongToWuJiang.opponentList.get(0);
				for (int i = 1; i < this.belongToWuJiang.opponentList.size(); i++) {
					if (this.tarWJForAI.blood > this.belongToWuJiang.opponentList
							.get(i).blood) {
						this.tarWJForAI = this.belongToWuJiang.opponentList
								.get(i);
					}
				}
			}
		}
	}
}
