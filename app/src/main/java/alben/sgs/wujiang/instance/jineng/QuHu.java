package alben.sgs.wujiang.instance.jineng;

import alben.sgs.android.R;
import alben.sgs.cardpai.CardPai;
import alben.sgs.type.CardPaiActionForTarWuJiang;
import alben.sgs.type.Type;
import alben.sgs.wujiang.WuJiang;

public class QuHu extends CardPai {
	public CardPai sp1 = null;
	public WuJiang tarWJ1 = null;

	public QuHu(Type.CardPai na, Type.CardPaiClass c, int n) {
		super(na, c, n);
		this.ID = generateID++;
		this.dispName = "驱虎";
		this.shangHaiN = -1;
		this.cpState = Type.CPState.nil;
		this.name = Type.CardPai.WJJiNeng;
	}

	public String toString() {
		return this.dispName;
	}

	public boolean work(WuJiang srcWJ, WuJiang tarWJ, CardPai tarCP1) {

		if (srcWJ.oneTimeJiNengTrigger) {
			return false;
		} else {
			srcWJ.oneTimeJiNengTrigger = true;
		}

		this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "["
				+ this.dispName + "]" + tarWJ.dispName, Type.logDelay.Delay);

		CardPai tarCP = tarWJ.askOneCPForCompare(srcWJ);

		if (tarCP == null)
			return false;

		String info = "输了";
		boolean qh = false;
		if (this.sp1.number > tarCP.number) {
			info = "赢了";
			qh = true;
		}

		srcWJ.detatchCardPaiFromShouPai(this.sp1);
		tarWJ.detatchCardPaiFromShouPai(tarCP);

		this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "的牌是" + this.sp1,
				Type.logDelay.HalfDelay);

		this.gameApp.libGameViewData.logInfo(tarWJ.dispName + "的牌是" + tarCP,
				Type.logDelay.HalfDelay);

		this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "拼点" + info,
				Type.logDelay.Delay);

		if (qh) {
			if (!srcWJ.tuoGuan) {
				// we do not select tarWJ1 yet, select which WJ for quHu
				String canSelectStr = "";

				WuJiang curWJ = tarWJ.nextOne;
				while (!curWJ.equals(tarWJ)) {
					// reset
					this.gameApp.libGameViewData.imageWJs[curWJ.imageViewIndex]
							.setBackgroundDrawable(this.gameApp.getResources()
									.getDrawable(R.drawable.bg_black));

					curWJ.clicked = false;
					curWJ.canSelect = false;
					int distance = this.gameApp.gameLogicData.wjHelper
							.countDistance(tarWJ, curWJ, true);

					if (distance <= 1) {
						// set the tarWJ to gray
						this.gameApp.libGameViewData.imageWJs[curWJ.imageViewIndex]
								.setBackgroundDrawable(this.gameApp
										.getResources().getDrawable(
												R.drawable.bg_green));
						// set can reach flag
						curWJ.canSelect = true;
						canSelectStr += curWJ.dispName + " ";
					}
					curWJ = curWJ.nextOne;
				}

				if (canSelectStr.trim().length() > 0) {
					this.gameApp.libGameViewData.logInfo("你可以" + this.dispName
							+ " " + canSelectStr + ",请点击选择",
							Type.logDelay.NoDelay);
				} else {
					this.gameApp.selectWJViewData.selectedWJ1 = null;
					this.gameApp.libGameViewData.logInfo("该武将距离不够不能"
							+ this.dispName, Type.logDelay.NoDelay);

					return false;
				}

				// use UI for interaction
				gameApp.gameLogicData.userInterface
						.askUserSelectWuJiang(gameApp.gameLogicData.myWuJiang,
								"请选择" + gameApp.selectWJViewData.selectNumber
										+ "个武将");

				this.tarWJ1 = gameApp.selectWJViewData.selectedWJ1;

				this.gameApp.gameLogicData.userInterface.loop = true;
				this.gameApp.gameLogicData.userInterface
						.sendMessageToUIForWakeUp();
			}

			this.gameApp.libGameViewData.logInfo(srcWJ.dispName + this.dispName
					+ this.tarWJ1.dispName + "成功", Type.logDelay.Delay);
			this.tarWJ1.increaseBlood(srcWJ, this);

		} else {
			this.gameApp.libGameViewData.logInfo(srcWJ.dispName + this.dispName
					+ "失败", Type.logDelay.Delay);
			srcWJ.increaseBlood(srcWJ, this);
		}

		return true;
	}

	public void onClickUpdateView() {
		// select first wj
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

				// reset
				this.gameApp.libGameViewData.imageWJs[tarWJ.imageViewIndex]
						.setBackgroundDrawable(this.gameApp.getResources()
								.getDrawable(R.drawable.bg_black));
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
				this.gameApp.libGameViewData.logInfo("没有可拼点对象",
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
		// for the first time, select wu jiang who has wu qi
		if (gameApp.selectWJViewData.selectedWJ1 == null) {
			gameApp.selectWJViewData.selectedWJ1 = curClickWJ;

			this.gameApp.libGameViewData.logInfo("请选择" + this.dispName + "的对象",
					Type.logDelay.NoDelay);

			String canSelectStr = "";

			WuJiang tarWJ = curClickWJ.nextOne;
			while (!tarWJ.equals(curClickWJ)) {
				// reset
				this.gameApp.libGameViewData.imageWJs[tarWJ.imageViewIndex]
						.setBackgroundDrawable(this.gameApp.getResources()
								.getDrawable(R.drawable.bg_black));

				tarWJ.clicked = false;
				tarWJ.canSelect = false;
				int distance = this.gameApp.gameLogicData.wjHelper
						.countDistance(curClickWJ, tarWJ, true);

				if (distance <= 1) {
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

			if (canSelectStr.trim().length() <= 0) {
				this.gameApp.selectWJViewData.selectedWJ1 = null;
				this.gameApp.libGameViewData.logInfo("该武将距离不够不能"
						+ this.dispName, Type.logDelay.NoDelay);
				return;
			}
			// remember to return
			return;
		}

		// then for the second time, select second wj
		if (gameApp.selectWJViewData.selectedWJ2 == null) {
			gameApp.selectWJViewData.selectedWJ2 = curClickWJ;
		}
	}

	// un-click wj and re-set data
	public void onUnClickWJUpdateView(WuJiang curClickWJ) {
		// gameApp.selectWJViewData.selectedWJ1 = null;
	}

	public void selectTarWJForAI() {
		this.tarWJForAI = null;
		if (this.belongToWuJiang != null) {

			// select Oppt first, select max shou pai one
			for (int i = 0; i < this.belongToWuJiang.opponentList.size(); i++) {
				WuJiang tmpWJ = this.belongToWuJiang.opponentList.get(i);
				CardPaiActionForTarWuJiang useCPShaWJ = tmpWJ
						.whichWJICanShaWithWuQi(tmpWJ.zhuangBei.wuQi,
								tmpWJ.friendList);
				if (tmpWJ.shouPai.size() > 0 && useCPShaWJ.tarWJ != null) {
					this.tarWJForAI = tmpWJ;
					this.tarWJ1 = useCPShaWJ.tarWJ;
				}
			}

			// select Friend seconds, get the max shou pai one
			if (this.tarWJForAI == null) {
				for (int i = 0; i < this.belongToWuJiang.friendList.size(); i++) {
					WuJiang tmpWJ = this.belongToWuJiang.friendList.get(i);
					CardPaiActionForTarWuJiang useCPShaWJ = tmpWJ
							.whichWJICanShaWithWuQi(tmpWJ.zhuangBei.wuQi,
									tmpWJ.opponentList);
					if (tmpWJ.shouPai.size() > 0 && useCPShaWJ.tarWJ != null) {
						this.tarWJForAI = tmpWJ;
						this.tarWJ1 = useCPShaWJ.tarWJ;
					}
				}
			}
		}
	}
}
