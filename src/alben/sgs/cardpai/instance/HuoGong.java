package alben.sgs.cardpai.instance;

import alben.sgs.android.R;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.JinNangCardPai;
import alben.sgs.type.Type;
import alben.sgs.type.Type.JinNangApplyTo;
import alben.sgs.wujiang.WuJiang;

public class HuoGong extends JinNangCardPai {
	public HuoGong(Type.CardPai na, Type.CardPaiClass c, int n, int imgNumber,
			JinNangApplyTo at) {
		super(na, c, n, imgNumber, at);
		this.dispName = "火攻";
		this.selectTarWJNumber = 1;
		this.linkImpact = true;
	}

	public boolean work(WuJiang srcWJ, WuJiang tarWJ, CardPai tarCP) {
		this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "对"
				+ tarWJ.dispName + "使用" + this, Type.logDelay.Delay);

		this.listenPreWorkEvent(srcWJ, tarWJ, tarCP);

		if (srcWJ.askForWuXieKeJi(srcWJ, this, tarWJ, this)) {
			this.gameApp.libGameViewData.logInfo(this.dispName + "被无懈了",
					Type.logDelay.NoDelay);
		} else {

			// request tarWJ show shou pai
			CardPai showCP = tarWJ.showOneCPForHuoGong();

			if (showCP == null) {
				this.gameApp.libGameViewData.logInfo(tarWJ.dispName + "已经没有手牌",
						Type.logDelay.NoDelay);
				return false;
			} else {
				this.gameApp.libGameViewData.logInfo(tarWJ.dispName + "出示卡牌"
						+ showCP, Type.logDelay.Delay);
			}

			// request srcWJ to discard some huashi
			CardPai disCP = srcWJ.discardOneCPForHuoGong(showCP);
			if (disCP == null) {
				this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "放弃火攻",
						Type.logDelay.NoDelay);
			} else if (disCP.clas == showCP.clas) {
				this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "丢弃卡牌"
						+ disCP, Type.logDelay.Delay);

				this.countTotalShangHaiN(tarWJ);
				this.shangHaiSrcWJ = srcWJ;
				tarWJ.increaseBlood(srcWJ, this);
			}
		}

		return true;
	}

	public void onClickUpdateView() {

		if (this.gameApp.gameLogicData.myWuJiang.state != Type.State.ChuPai
				|| this.gameApp.gameLogicData.askForPai != Type.CardPai.notNil) {
			return;
		}

		// set the select wj number
		this.gameApp.selectWJViewData.reset();
		this.gameApp.selectWJViewData.selectNumber = this.selectTarWJNumber;

		// 当被点击时候，显示可以到的武将
		if (this.belongToWuJiang != null) {
			String canSelectStr = "";
			WuJiang tarWJ = this.belongToWuJiang.nextOne;
			while (!tarWJ.equals(this.belongToWuJiang)) {
				tarWJ.clicked = false;
				tarWJ.canSelect = false;
				if (tarWJ.shouPai.size() > 0) {
					// set the tarWJ to gray
					this.gameApp.libGameViewData.imageWJs[tarWJ.imageViewIndex]
							.setBackgroundDrawable(this.gameApp.getResources()
									.getDrawable(R.drawable.bg_green));
					// set can reach flag
					tarWJ.canSelect = true;
					canSelectStr += tarWJ.dispName + " ";
				}
				tarWJ = tarWJ.nextOne;
			}
			if (canSelectStr.trim().length() <= 0)
				this.gameApp.libGameViewData.logInfo("没有可火攻对象",
						Type.logDelay.NoDelay);
		} else {
			this.gameApp.libGameViewData.logInfo("Error:此卡牌不属于任何武将",
					Type.logDelay.NoDelay);
		}
	}

	public void onUnClickUpdateView() {

		if (this.gameApp.gameLogicData.myWuJiang.state != Type.State.ChuPai
				|| this.gameApp.gameLogicData.askForPai != Type.CardPai.notNil) {
			return;
		}

		if (this.belongToWuJiang != null) {
			WuJiang tarWJ = this.belongToWuJiang.nextOne;
			while (!tarWJ.equals(this.belongToWuJiang)) {
				// set the tarWJ to original background
				this.gameApp.libGameViewData.imageWJs[tarWJ.imageViewIndex]
						.setBackgroundDrawable(this.gameApp.getResources()
								.getDrawable(R.drawable.bg_black));
				tarWJ.canSelect = false;
				tarWJ.clicked = false;
				tarWJ = tarWJ.nextOne;
			}
		}
	}

	// click wj and set data
	public void onClickWJUpdateView(WuJiang curClickWJ) {
		gameApp.selectWJViewData.selectedWJ1 = curClickWJ;
	}

	// un-click wj and re-set data
	public void onUnClickWJUpdateView(WuJiang curClickWJ) {
		gameApp.selectWJViewData.selectedWJ1 = null;
	}

	public boolean canChuPai() {

		if (this.gameApp.gameLogicData.myWuJiang.state == Type.State.ChuPai
				&& this.gameApp.gameLogicData.askForPai == Type.CardPai.notNil) {

			if (this.gameApp.selectWJViewData.selectNumber >= 1
					&& this.gameApp.selectWJViewData.selectedWJ1 != null) {
				return true;
			} else {
				this.gameApp.libGameViewData.logInfo("Error:不满足出牌条件",
						Type.logDelay.NoDelay);
				return false;
			}
		} else {
			return true;
		}
	}

	public int countTotalShangHaiN(WuJiang tarWJ) {
		return this.shangHaiN;
	}

	public void selectTarWJForAI() {
		this.tarWJForAI = null;
		if (this.belongToWuJiang != null) {

			// select one Oppt tarWJ who has shouPai and tengJia
			for (int i = 0; i < this.belongToWuJiang.opponentList.size(); i++) {
				WuJiang tmpWJ = this.belongToWuJiang.opponentList.get(i);
				if (tmpWJ.shouPai.size() > 0 && tmpWJ.zhuangBei.fangJu != null
						&& tmpWJ.zhuangBei.fangJu instanceof TengJia) {
					this.tarWJForAI = tmpWJ;
					return;
				}
			}

			// select one Oppt tarWJ who has shouPai
			if (this.tarWJForAI == null) {
				for (int i = 0; i < this.belongToWuJiang.opponentList.size(); i++) {
					WuJiang tmpWJ = this.belongToWuJiang.opponentList.get(i);
					if (tmpWJ.shouPai.size() > 0) {
						this.tarWJForAI = tmpWJ;
						return;
					}
				}
			}
		}
	}

}
