package alben.sgs.wujiang.instance.jineng;

import alben.sgs.android.R;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.ZhuangBeiCardPai;
import alben.sgs.type.Type;
import alben.sgs.type.UpdateWJViewData;
import alben.sgs.wujiang.WuJiang;

public class QiangXi extends CardPai {
	public CardPai sp1 = null;
	public boolean decMyBlood = false;

	public QiangXi(Type.CardPai na, Type.CardPaiClass c, int n) {
		super(na, c, n);
		this.ID = generateID++;
		this.dispName = "强袭";
		this.cpState = Type.CPState.nil;
		this.name = Type.CardPai.WJJiNeng;
		this.shangHaiN = -1;
		this.selectTarWJNumber = 1;
	}

	public String toString() {
		return this.dispName;
	}

	public boolean work(WuJiang srcWJ, WuJiang tarWJ, CardPai tarCP) {

		srcWJ.oneTimeJiNengTrigger = true;

		if (this.decMyBlood) {
			UpdateWJViewData item = new UpdateWJViewData();
			item.updateBlood = true;

			this.shangHaiSrcWJ = srcWJ;

			this.gameApp.libGameViewData
					.logInfo(srcWJ.dispName + "自减1点体力[" + this.dispName + "]"
							+ tarWJ.dispName, Type.logDelay.Delay);

			srcWJ.increaseBlood(srcWJ, this);
			this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(
					srcWJ, item);

			this.shangHaiSrcWJ = srcWJ;
			tarWJ.increaseBlood(srcWJ, this);

		} else {
			if (this.sp1.cpState == Type.CPState.ShouPai) {
				this.sp1.belongToWuJiang = null;
				srcWJ.detatchCardPaiFromShouPai(this.sp1);
			} else if (this.sp1.cpState == Type.CPState.wuQiPai) {
				srcWJ.unstallZhuangBei((ZhuangBeiCardPai) this.sp1);
			}

			this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "弃掉"
					+ this.sp1 + "[" + this.dispName + "]" + tarWJ.dispName,
					Type.logDelay.Delay);

			this.shangHaiSrcWJ = srcWJ;
			tarWJ.increaseBlood(srcWJ, this);
		}

		return true;
	}

	// overwrite
	public void selectTarWJForAI() {
		if (this.belongToWuJiang != null) {
			this.tarWJForAI = null;
			// select wj for tarWJ
			boolean applyWuQi = true;
			if (!this.decMyBlood && this.sp1.cpState == Type.CPState.wuQiPai) {
				applyWuQi = false;
			}
			for (int i = 0; i < this.belongToWuJiang.opponentList.size(); i++) {
				WuJiang tarWJ = this.belongToWuJiang.opponentList.get(i);
				int distance = this.gameApp.gameLogicData.wjHelper
						.countDistance(this.belongToWuJiang, tarWJ, applyWuQi);
				if (distance <= 1) {
					this.tarWJForAI = tarWJ;
					break;
				}
			}
		}
	}

	public void onClickUpdateView() {

		if (this.gameApp.gameLogicData.myWuJiang.state != Type.State.ChuPai
				|| this.gameApp.gameLogicData.askForPai != Type.CardPai.notNil) {
			return;
		}

		// set the select wj number
		this.gameApp.selectWJViewData.selectNumber = this.selectTarWJNumber;
		this.gameApp.selectWJViewData.selectedWJ1 = null;

		boolean applyWuQi = true;
		if (this.sp1 != null && this.sp1.cpState == Type.CPState.wuQiPai) {
			applyWuQi = false;
		}

		String canSelectStr = "";
		WuJiang tarWJ = this.belongToWuJiang.nextOne;
		while (!tarWJ.equals(this.belongToWuJiang)) {
			tarWJ.clicked = false;
			tarWJ.canSelect = false;
			int distance = this.gameApp.gameLogicData.wjHelper.countDistance(
					this.belongToWuJiang, tarWJ, applyWuQi);

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

		if (canSelectStr.trim().length() <= 0)
			this.gameApp.libGameViewData.logInfo("没有可杀对象",
					Type.logDelay.NoDelay);

	}

	// click wj and set data
	public void onClickWJUpdateView(WuJiang curClickWJ) {
		if (gameApp.selectWJViewData.selectNumber == 1) {
			gameApp.selectWJViewData.selectedWJ1 = curClickWJ;
		}
	}

	public boolean canChuPai() {
		boolean rtn = false;
		if (this.gameApp.gameLogicData.myWuJiang.state == Type.State.ChuPai
				&& this.gameApp.gameLogicData.askForPai == Type.CardPai.notNil) {

			if (this.gameApp.selectWJViewData.selectNumber == 1
					&& this.gameApp.selectWJViewData.selectedWJ1 != null) {
				rtn = true;
			}
		} else {
			rtn = true;
		}

		if (!rtn)
			this.gameApp.libGameViewData.logInfo("Error:不满足出牌条件",
					Type.logDelay.NoDelay);

		return rtn;
	}

}
