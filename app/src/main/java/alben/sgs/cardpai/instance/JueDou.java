package alben.sgs.cardpai.instance;

import alben.sgs.android.R;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.JinNangCardPai;
import alben.sgs.type.Type;
import alben.sgs.type.Type.JinNangApplyTo;
import alben.sgs.wujiang.WuJiang;
import alben.sgs.wujiang.instance.LvBu;
import alben.sgs.wujiang.instance.XuZhu;
import alben.sgs.wujiang.instance.ZhuGeLiang;

public class JueDou extends JinNangCardPai {
	public JueDou(Type.CardPai na, Type.CardPaiClass c, int n, int imgNumber,
			JinNangApplyTo at) {
		super(na, c, n, imgNumber, at);
		this.dispName = "决斗";
		this.selectTarWJNumber = 1;
	}

	public boolean work(WuJiang srcWJ, WuJiang tarWJ, CardPai tarCP) {
		this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "对"
				+ tarWJ.dispName + "使用" + this, Type.logDelay.Delay);

		this.listenPreWorkEvent(srcWJ, tarWJ, tarCP);

		if (srcWJ.askForWuXieKeJi(srcWJ, this, tarWJ, this)) {
			this.gameApp.libGameViewData.logInfo(this.dispName + "被无懈了",
					Type.logDelay.NoDelay);
		} else {
			CardPai shaCP = null;
			WuJiang[] WJs = { tarWJ, srcWJ };
			boolean run = true;
			int index = 0;
			while (run) {
				// For LvBu shuangXiong
				if (WJs[(index + 1) % 2] instanceof LvBu) {
					shaCP = WJs[index % 2].chuSha(WJs[(index + 1) % 2], this);
					if (shaCP != null) {
						this.gameApp.libGameViewData.logInfo(
								WJs[(index + 1) % 2].dispName + "的"
										+ WJs[(index + 1) % 2].jiNengN1 + "触发",
								Type.logDelay.NoDelay);
						shaCP = WJs[index % 2].chuSha(WJs[(index + 1) % 2],
								this);
					}
				} else {
					shaCP = WJs[index % 2].chuSha(WJs[(index + 1) % 2], this);
				}
				//
				if (shaCP != null) {
					// next WJ chu sha
					index++;
				} else {
					run = false;
					this.gameApp.libGameViewData.logInfo(
							WJs[index % 2].dispName + "放弃出杀",
							Type.logDelay.NoDelay);
					this.countTotalShangHaiN(WJs[index % 2]);
					this.shangHaiSrcWJ = WJs[(index + 1) % 2];
					WJs[index % 2].increaseBlood(srcWJ, this);
				}
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

		// 当决斗被点击时候，显示可以选择的武将
		if (this.belongToWuJiang != null) {
			String canSelectStr = "";
			WuJiang tarWJ = this.belongToWuJiang.nextOne;
			while (!tarWJ.equals(this.belongToWuJiang)) {

				// if ZhuGeLiang kongCheng is success
				if (tarWJ instanceof ZhuGeLiang) {
					if (tarWJ.shouPai.size() == 0) {
						this.gameApp.libGameViewData.imageWJs[tarWJ.imageViewIndex]
								.setBackgroundDrawable(this.gameApp
										.getResources().getDrawable(
												R.drawable.bg_black));
						tarWJ.clicked = false;
						tarWJ.canSelect = false;

						tarWJ = tarWJ.nextOne;
						continue;
					}
				}

				// set the tarWJ to green
				this.gameApp.libGameViewData.imageWJs[tarWJ.imageViewIndex]
						.setBackgroundDrawable(this.gameApp.getResources()
								.getDrawable(R.drawable.bg_green));
				// set can reach flag
				tarWJ.clicked = false;
				tarWJ.canSelect = true;
				canSelectStr += tarWJ.dispName + " ";
				tarWJ = tarWJ.nextOne;
			}

			if (canSelectStr.trim().length() <= 0)
				this.gameApp.libGameViewData.logInfo("没有可决斗对象",
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

		if (this.belongToWuJiang != null) {
			if (this.belongToWuJiang instanceof XuZhu
					&& this.belongToWuJiang.oneTimeJiNengTrigger) {
				if (!(tarWJ instanceof XuZhu)) {
					this.gameApp.libGameViewData.logInfo(
							this.belongToWuJiang.dispName + "裸衣决斗,杀伤力+1",
							Type.logDelay.NoDelay);
					this.shangHaiN -= 1;
				}
			}
		}
		return this.shangHaiN;
	}

	public void selectTarWJForAI() {
		this.tarWJForAI = null;
		if (this.belongToWuJiang != null) {

			if (this.belongToWuJiang
					.isOpponent(this.gameApp.gameLogicData.zhuGongWuJiang)) {

				this.tarWJForAI = this.gameApp.gameLogicData.zhuGongWuJiang;

				// overwrite tarWJForAI if ZhuGeLiang is kongCheng
				if (this.tarWJForAI instanceof ZhuGeLiang
						&& this.tarWJForAI.shouPai.size() == 0) {
					this.tarWJForAI = null;
				}

				if (this.tarWJForAI != null)
					return;
			}

			// search the least shou pai Oppt as tarWJ
			int numberOppt = this.belongToWuJiang.opponentList.size();
			for (int i = 0; i < numberOppt; i++) {
				WuJiang wj = this.belongToWuJiang.opponentList.get(i);
				// skip if ZhuGeLiang is kongCheng
				if (wj instanceof ZhuGeLiang && wj.shouPai.size() == 0) {
					continue;
				}

				this.tarWJForAI = wj;
				return;

			}
		}
	}
}
