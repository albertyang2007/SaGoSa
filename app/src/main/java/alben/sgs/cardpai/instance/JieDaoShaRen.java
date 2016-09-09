package alben.sgs.cardpai.instance;

import alben.sgs.android.R;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.JinNangCardPai;
import alben.sgs.type.Type;
import alben.sgs.type.Type.JinNangApplyTo;
import alben.sgs.type.UpdateWJViewData;
import alben.sgs.wujiang.WuJiang;
import alben.sgs.wujiang.instance.ZhuGeLiang;

public class JieDaoShaRen extends JinNangCardPai {
	public WuJiang tarWJ2ForAI = null;

	public JieDaoShaRen(Type.CardPai na, Type.CardPaiClass c, int n,
			int imgNumber, JinNangApplyTo at) {
		super(na, c, n, imgNumber, at);
		this.dispName = "借刀杀人";
		this.selectTarWJNumber = 2;
	}

	public boolean work(WuJiang srcWJ, WuJiang tarWJ, CardPai tarCP) {

		if (!srcWJ.tuoGuan) {
			this.tarWJ2ForAI = gameApp.selectWJViewData.selectedWJ2;
		}
		this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "借"
				+ tarWJ.dispName + "的刀杀" + this.tarWJ2ForAI.dispName,
				Type.logDelay.Delay);

		this.listenPreWorkEvent(srcWJ, tarWJ, tarCP);

		if (srcWJ.askForWuXieKeJi(srcWJ, this, tarWJ, this)) {
			this.gameApp.libGameViewData.logInfo(this.dispName + "被无懈了",
					Type.logDelay.NoDelay);
		} else {
			CardPai shaCP = tarWJ.chuSha(srcWJ, this);

			if (shaCP != null) {
				((Sha) shaCP).commonShaWork(tarWJ, this.tarWJ2ForAI, null);
			} else {
				this.gameApp.libGameViewData.logInfo(tarWJ.dispName + "的"
						+ tarWJ.zhuangBei.wuQi.dispName + "被" + srcWJ.dispName
						+ "借走了", Type.logDelay.NoDelay);
				// mei you chu sha, wuqi bei jie zou
				tarWJ.zhuangBei.wuQi.belongToWuJiang = srcWJ;
				srcWJ.shouPai.add(tarWJ.zhuangBei.wuQi);
				tarWJ.zhuangBei.wuQi = null;

				// update view
				tarWJ.updateZhuangBeiView();
				if (!srcWJ.tuoGuan) {
					this.gameApp.gameLogicData.wjHelper
							.updateWJ8ShouPaiToLibGameView();
				} else {
					UpdateWJViewData item = new UpdateWJViewData();
					item.updateShouPaiNumber = true;
					this.gameApp.gameLogicData.wjHelper
							.updateWuJiangToLibGameView(srcWJ, item);
				}
			}
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
				if (tarWJ.zhuangBei.wuQi != null) {
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
				this.gameApp.libGameViewData.logInfo("玩家都没有武器",
						Type.logDelay.NoDelay);
				return;
			}
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
		// for the first time, select wu jiang who has wu qi
		if (gameApp.selectWJViewData.selectedWJ1 == null) {
			gameApp.selectWJViewData.selectedWJ1 = curClickWJ;

			this.gameApp.libGameViewData.logInfo("请选择杀的对象",
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

				// if ZhuGeLiang kongCheng is success
				if (tarWJ instanceof ZhuGeLiang) {
					if (tarWJ.shouPai.size() == 0) {
						distance = 256;
					}
				}

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
				this.gameApp.libGameViewData.logInfo("该玩家距离不够不能出杀",
						Type.logDelay.NoDelay);
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

	public boolean canChuPai() {

		if (this.gameApp.gameLogicData.myWuJiang.state == Type.State.ChuPai
				&& this.gameApp.gameLogicData.askForPai == Type.CardPai.notNil) {

			if (this.gameApp.selectWJViewData.selectNumber >= 1
					&& this.gameApp.selectWJViewData.selectedWJ1 != null
					&& this.gameApp.selectWJViewData.selectedWJ2 != null) {
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

	public void selectTarWJForAI() {
		if (this.belongToWuJiang != null) {
			this.tarWJForAI = null;
			// first search Oppt wuQi
			for (int i = 0; i < this.belongToWuJiang.opponentList.size(); i++) {
				WuJiang tmpWJ1 = this.belongToWuJiang.opponentList.get(i);
				if (tmpWJ1.zhuangBei.wuQi != null) {
					for (int j = 0; j < this.belongToWuJiang.opponentList
							.size(); j++) {
						if (i == j)
							continue;
						WuJiang tmpWJ2 = this.belongToWuJiang.opponentList
								.get(j);
						int distance = this.gameApp.gameLogicData.wjHelper
								.countDistance(tmpWJ1, tmpWJ2, true);

						// if ZhuGeLiang kongCheng is success
						if (tmpWJ2 instanceof ZhuGeLiang) {
							if (tmpWJ2.shouPai.size() == 0) {
								distance = 256;
							}
						}

						if (distance <= 1) {
							this.tarWJForAI = tmpWJ1;
							this.tarWJ2ForAI = tmpWJ2;
							return;
						}
					}
				}
			}

			if (this.tarWJForAI != null)
				return;

			// second search Friend wuQi
			for (int i = 0; i < this.belongToWuJiang.friendList.size(); i++) {
				WuJiang tmpWJ1 = this.belongToWuJiang.friendList.get(i);
				if (tmpWJ1.zhuangBei.wuQi != null) {
					for (int j = 0; j < this.belongToWuJiang.opponentList
							.size(); j++) {
						WuJiang tmpWJ2 = this.belongToWuJiang.opponentList
								.get(j);
						int distance = this.gameApp.gameLogicData.wjHelper
								.countDistance(tmpWJ1, tmpWJ2, true);

						// if ZhuGeLiang kongCheng is success
						if (tmpWJ2 instanceof ZhuGeLiang) {
							if (tmpWJ2.shouPai.size() == 0) {
								distance = 256;
							}
						}

						if (distance <= 1) {
							this.tarWJForAI = tmpWJ1;
							this.tarWJ2ForAI = tmpWJ2;
							return;
						}
					}
				}
			}
		}
	}

}
