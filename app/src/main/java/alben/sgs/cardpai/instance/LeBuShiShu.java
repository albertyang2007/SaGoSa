package alben.sgs.cardpai.instance;

import alben.sgs.android.R;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.JinNangCardPai;
import alben.sgs.type.Type;
import alben.sgs.type.Type.JinNangApplyTo;
import alben.sgs.type.UpdateWJViewData;
import alben.sgs.wujiang.WuJiang;
import alben.sgs.wujiang.instance.LuSun;

public class LeBuShiShu extends JinNangCardPai {
	public LeBuShiShu(Type.CardPai na, Type.CardPaiClass c, int n,
			int imgNumber, JinNangApplyTo at) {
		super(na, c, n, imgNumber, at);
		this.dispName = "乐不思蜀";
		this.selectTarWJNumber = 1;
		this.delay = true;
	}

	public LeBuShiShu(Type.CardPai na, Type.CardPaiClass c, int n,
			JinNangApplyTo at) {
		super(na, c, n, R.drawable.card_back, at);
		this.dispName = "乐不思蜀";
		this.selectTarWJNumber = 1;
		this.delay = true;
	}

	public boolean work(WuJiang srcWJ, WuJiang tarWJ, CardPai tarCP) {
		if (!tarWJ.hasLBSSInPanDindArea()) {

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
		boolean leBuShiShuOK = false;

		this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "判定乐不思蜀",
				Type.logDelay.Delay);
		// ask for wu xie
		if (srcWJ.askForWuXieKeJi(srcWJ, null, srcWJ, this)) {
			// cp checking had been done
			this.belongToWuJiang = null;
			this.cpState = Type.CPState.FeiPaiDui;
			srcWJ.panDingPai.remove(this);
			this.gameApp.libGameViewData.logInfo(this.dispName + "被无懈了,可以出牌",
					Type.logDelay.NoDelay);
		} else {
			if (!this.panDingThruCardPai(srcWJ)) {
				// cp checking had been done
				this.belongToWuJiang = null;
				this.cpState = Type.CPState.FeiPaiDui;
				srcWJ.panDingPai.remove(this);
				this.gameApp.libGameViewData.logInfo(srcWJ.dispName
						+ "乐不思蜀失败,可以出牌", Type.logDelay.NoDelay);
			} else {
				leBuShiShuOK = true;
				// cp checking had been done
				this.belongToWuJiang = null;
				this.cpState = Type.CPState.FeiPaiDui;
				srcWJ.panDingPai.remove(this);
				this.gameApp.libGameViewData.logInfo(srcWJ.dispName
						+ "乐不思蜀成功,不可以出牌", Type.logDelay.NoDelay);
			}
		}
		// update view
		UpdateWJViewData item = new UpdateWJViewData();
		item.updatePangDing = true;
		this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(srcWJ,
				item);

		return leBuShiShuOK;
	}

	public boolean panDingThruCardPai(WuJiang srcWJ) {
		boolean rtn = true;
		CardPai cp = this.gameApp.gameLogicData.cpHelper.popCardPaiForPanDing(
				srcWJ, this);

		if (cp.clas == Type.CardPaiClass.HongTao) {
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

		// 当乐不思蜀被点击时候，显示可以乐到的武将
		if (this.belongToWuJiang != null) {
			String canSelectStr = "";
			WuJiang tarWJ = this.belongToWuJiang.nextOne;
			while (!tarWJ.equals(this.belongToWuJiang)) {
				tarWJ.clicked = false;
				tarWJ.canSelect = false;
				boolean hasLe = tarWJ.hasLBSSInPanDindArea();
				if (!hasLe && !(tarWJ instanceof LuSun)) {
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
				this.gameApp.libGameViewData.logInfo("没有可乐对象",
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

	// overwrite selectTarWJForAI to select one non-Le wuJiang
	public void selectTarWJForAI() {
		for (int i = 0; i < this.belongToWuJiang.opponentList.size(); i++) {
			WuJiang tarWJ = this.belongToWuJiang.opponentList.get(i);
			boolean hasLe = tarWJ.hasLBSSInPanDindArea();
			if (!hasLe && !(tarWJ instanceof LuSun)) {
				this.tarWJForAI = tarWJ;
				break;
			}
		}
	}
}
