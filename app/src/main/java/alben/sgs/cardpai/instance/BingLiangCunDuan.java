package alben.sgs.cardpai.instance;

import alben.sgs.android.R;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.JinNangCardPai;
import alben.sgs.type.Type;
import alben.sgs.type.Type.JinNangApplyTo;
import alben.sgs.type.UpdateWJViewData;
import alben.sgs.wujiang.WuJiang;
import alben.sgs.wujiang.instance.HuangYueYing;

public class BingLiangCunDuan extends JinNangCardPai {
	public BingLiangCunDuan(Type.CardPai na, Type.CardPaiClass c, int n,
			int imgNumber, JinNangApplyTo at) {
		super(na, c, n, imgNumber, at);
		this.dispName = "兵粮寸断";
		this.selectTarWJNumber = 1;
		this.delay = true;
	}

	public boolean work(WuJiang srcWJ, WuJiang tarWJ, CardPai tarCP) {

		if (!tarWJ.hasBLCDInPanDindArea()) {

			this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "对"
					+ tarWJ.dispName + "使用" + this, Type.logDelay.Delay);

			this.cpState = Type.CPState.pandDingPai;
			this.belongToWuJiang = tarWJ;
			tarWJ.panDingPai.add(this);

			// update view
			UpdateWJViewData item = new UpdateWJViewData();
			item.updatePangDing = true;
			this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(
					tarWJ, item);

			return true;
		}
		// reset
		this.gameApp.selectWJViewData.reset();
		return true;
	}

	public boolean panDing(WuJiang srcWJ, WuJiang tarWJ, CardPai tarCP) {
		boolean bingLiangCunDunOK = false;

		this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "判定兵粮寸断",
				Type.logDelay.Delay);
		// ask for wu xie
		if (srcWJ.askForWuXieKeJi(srcWJ, null, srcWJ, this)) {
			// cp checking had been done
			this.belongToWuJiang = null;
			this.cpState = Type.CPState.FeiPaiDui;
			srcWJ.panDingPai.remove(this);
			this.gameApp.libGameViewData.logInfo(srcWJ.dispName
					+ "兵粮寸断被无懈,可以摸牌", Type.logDelay.Delay);
		} else {
			if (!this.panDingThruCardPai(srcWJ)) {
				// cp checking had been done
				this.belongToWuJiang = null;
				this.cpState = Type.CPState.FeiPaiDui;
				srcWJ.panDingPai.remove(this);
				this.gameApp.libGameViewData.logInfo(srcWJ.dispName
						+ "兵粮寸断失败,可以摸牌", Type.logDelay.Delay);
			} else {
				bingLiangCunDunOK = true;
				// cp checking had been done
				this.belongToWuJiang = null;
				this.cpState = Type.CPState.FeiPaiDui;
				srcWJ.panDingPai.remove(this);
				this.gameApp.libGameViewData.logInfo(srcWJ.dispName
						+ "兵粮寸断成功,不可以摸牌", Type.logDelay.Delay);
			}
		}
		// update view
		UpdateWJViewData item = new UpdateWJViewData();
		item.updatePangDing = true;
		this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(srcWJ,
				item);

		return bingLiangCunDunOK;
	}

	public boolean panDingThruCardPai(WuJiang srcWJ) {
		boolean rtn = true;
		CardPai cp = this.gameApp.gameLogicData.cpHelper.popCardPaiForPanDing(
				srcWJ, this);

		if (cp.clas == Type.CardPaiClass.MeiHua) {
			rtn = false;
		}

		return rtn;
	}

	public void onClickUpdateView() {

		if (this.gameApp.gameLogicData.myWuJiang.state != Type.State.ChuPai
				|| this.gameApp.gameLogicData.askForPai != Type.CardPai.notNil) {
			return;
		}

		// set the select wj number
		this.gameApp.selectWJViewData.reset();
		this.gameApp.selectWJViewData.selectNumber = this.selectTarWJNumber;

		// 当兵粮寸段被点击时候，显示可以断到的武将
		if (this.belongToWuJiang != null) {
			String canSelectStr = "";
			WuJiang tarWJ = this.belongToWuJiang.nextOne;
			while (!tarWJ.equals(this.belongToWuJiang)) {
				tarWJ.clicked = false;
				tarWJ.canSelect = false;
				boolean hasDuan = tarWJ.hasBLCDInPanDindArea();

				int distance = this.gameApp.gameLogicData.wjHelper
						.countDistance(this.belongToWuJiang, tarWJ, false);

				if (this.belongToWuJiang instanceof HuangYueYing) {
					distance = 0;
				}

				if (!hasDuan && distance <= 1) {
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
				this.gameApp.libGameViewData.logInfo("没有可断对象",
						Type.logDelay.NoDelay);

		} else {
			this.gameApp.libGameViewData.logInfo("Error:此卡牌不属于任何武将",
					Type.logDelay.Delay);
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

	// un-click wj and re set data
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
						Type.logDelay.Delay);
				return false;
			}
		} else {
			return true;
		}
	}

	// overwrite selectTarWJForAI to select one non-Le wuJiang
	public void selectTarWJForAI() {
		this.tarWJForAI = null;
		for (int i = 0; i < this.belongToWuJiang.opponentList.size(); i++) {
			boolean hasBLCD = this.belongToWuJiang.opponentList.get(i)
					.hasBLCDInPanDindArea();

			int distance = this.gameApp.gameLogicData.wjHelper.countDistance(
					this.belongToWuJiang, this.belongToWuJiang.opponentList
							.get(i), false);

			if (this.belongToWuJiang instanceof HuangYueYing) {
				distance = 0;
			}

			if (!hasBLCD && distance <= 1) {
				this.tarWJForAI = this.belongToWuJiang.opponentList.get(i);
				break;
			}
		}
	}
}
