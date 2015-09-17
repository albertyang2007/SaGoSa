package alben.sgs.wujiang.instance.jineng;

import alben.sgs.android.R;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.ZhuangBeiCardPai;
import alben.sgs.cardpai.instance.FangTianHuaJi;
import alben.sgs.cardpai.instance.Sha;
import alben.sgs.type.Type;
import alben.sgs.type.Type.GameType;
import alben.sgs.wujiang.WuJiang;

public class WuSheng extends Sha {
	public CardPai sp1 = null;

	public WuSheng(Type.CardPai na, Type.CardPaiClass c, int n) {
		super(na, c, n, R.drawable.card_back);
		this.dispName = "武圣";
	}

	public String toString() {
		return this.sp1 + " " + this.dispName;
	}

	public boolean work(WuJiang srcWJ, WuJiang tarWJ, CardPai tarCP) {

		// set color in case renwanddun
		this.clas = this.sp1.clas;

		if (this.sp1.cpState == Type.CPState.ShouPai) {
			this.sp1.belongToWuJiang = null;
			srcWJ.detatchCardPaiFromShouPai(this.sp1);
			this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "["
					+ this.dispName + "]丢弃了" + this.sp1,
					Type.logDelay.HalfDelay);
		} else if (this.sp1.cpState == Type.CPState.wuQiPai
				|| this.sp1.cpState == Type.CPState.fangJuPai
				|| this.sp1.cpState == Type.CPState.jiaYiMaPai
				|| this.sp1.cpState == Type.CPState.jianYiMaPai) {
			srcWJ.unstallZhuangBei((ZhuangBeiCardPai) this.sp1);
		}

		return super.work(srcWJ, tarWJ, tarCP);
	}

	public void selectTarWJForAI() {
		// run and select one target wujiang for this cardpai
		// by default will select one oppt
		if (this.belongToWuJiang != null) {

			int distance = 0;
			this.tarWJForAI = null;
			boolean applyWuQi = true;
			if (this.sp1.cpState == Type.CPState.wuQiPai) {
				applyWuQi = false;
			}

			if (this.gameApp.settingsViewData.gameType == GameType.g_1v1) {

				WuJiang tarWJ = this.belongToWuJiang.nextOne;
				distance = this.gameApp.gameLogicData.wjHelper.countDistance(
						this.belongToWuJiang, tarWJ, applyWuQi);

				// if discard jianYiMa, then do not count this ma
				if (this.sp1.cpState == Type.CPState.jianYiMaPai)
					distance += 1;

				if (distance <= 1) {
					this.tarWJForAI = tarWJ;
					return;
				}
			}

			// for FangTianHuaJi
			if (this.sp1.cpState != Type.CPState.wuQiPai
					&& this.belongToWuJiang.shouPai.size() == 1
					&& this.belongToWuJiang.zhuangBei.wuQi != null
					&& this.belongToWuJiang.zhuangBei.wuQi instanceof FangTianHuaJi) {

				this.gameApp.selectWJViewData.reset();
				this.gameApp.selectWJViewData.selectNumber = 3;
				this.gameApp.selectWJViewData.selectedWJAtLeast1 = true;

				for (int i = 0; i < this.belongToWuJiang.opponentList.size(); i++) {
					WuJiang tarWJ = this.belongToWuJiang.opponentList.get(i);
					distance = this.gameApp.gameLogicData.wjHelper
							.countDistance(this.belongToWuJiang, tarWJ,
									applyWuQi);

					// if discard jianYiMa, then do not count this ma
					if (this.sp1.cpState == Type.CPState.jianYiMaPai)
						distance += 1;

					if (distance <= 1) {
						if (this.gameApp.selectWJViewData.selectedWJAtLeast1
								&& this.gameApp.selectWJViewData.selectedWJs
										.size() < this.gameApp.selectWJViewData.selectNumber) {
							if (!this.gameApp.selectWJViewData.selectedWJs
									.contains(tarWJ))
								this.gameApp.selectWJViewData.selectedWJs
										.add(tarWJ);
						}
					}
				}
				return;
			}

			// for normal case, select one tarWJ
			this.gameApp.selectWJViewData.reset();
			this.gameApp.selectWJViewData.selectNumber = 1;
			for (int i = 0; i < this.belongToWuJiang.opponentList.size(); i++) {
				WuJiang tarWJ = this.belongToWuJiang.opponentList.get(i);
				distance = this.gameApp.gameLogicData.wjHelper.countDistance(
						this.belongToWuJiang, tarWJ, applyWuQi);

				// if discard jianYiMa, then do not count this ma
				if (this.sp1.cpState == Type.CPState.jianYiMaPai)
					distance += 1;

				if (distance <= 1) {
					this.tarWJForAI = tarWJ;
					return;
				}
			}

		} else {
			this.gameApp.libGameViewData.logInfo("Error:卡牌不属于任何武将" + this,
					Type.logDelay.NoDelay);
		}
	}

	public void onClickUpdateView() {

		if (this.gameApp.gameLogicData.myWuJiang.state != Type.State.ChuPai
				|| this.gameApp.gameLogicData.askForPai != Type.CardPai.notNil) {
			return;
		}

		// 当被点击时候，显示可以到的WJ
		if (this.belongToWuJiang != null) {

			if (!this.belongToWuJiang.canIChuSha()) {
				this.gameApp.libGameViewData.logInfo("你不能出2次以上的杀",
						Type.logDelay.NoDelay);
				return;
			}

			// set the select wj number
			this.gameApp.selectWJViewData.reset();
			this.gameApp.selectWJViewData.selectNumber = this.selectTarWJNumber;

			if (this.sp1.cpState != Type.CPState.wuQiPai
					// one is original redCP another is WuSheng
					&& this.belongToWuJiang.shouPai.size() == 2
					&& (this.belongToWuJiang.zhuangBei.wuQi != null && this.belongToWuJiang.zhuangBei.wuQi instanceof FangTianHuaJi)) {
				this.gameApp.selectWJViewData.selectNumber = 3;
				this.gameApp.selectWJViewData.selectedWJAtLeast1 = true;
				this.gameApp.libGameViewData.logInfo("你有"
						+ this.belongToWuJiang.zhuangBei.wuQi.dispName
						+ ",最多可杀3人", Type.logDelay.NoDelay);
			}

			boolean applyWuQi = true;
			if (this.sp1.cpState == Type.CPState.wuQiPai) {
				applyWuQi = false;
			}

			String canSelectStr = "";
			WuJiang tarWJ = this.belongToWuJiang.nextOne;
			while (!tarWJ.equals(this.belongToWuJiang)) {
				tarWJ.clicked = false;
				tarWJ.canSelect = false;
				int distance = this.gameApp.gameLogicData.wjHelper
						.countDistance(this.belongToWuJiang, tarWJ, applyWuQi);

				// if discard jianYiMa, then do not count this ma
				if (this.sp1.cpState == Type.CPState.jianYiMaPai)
					distance += 1;

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
		} else {
			this.gameApp.libGameViewData.logInfo("Error:此卡牌不属于任何武将",
					Type.logDelay.NoDelay);
		}
	}
}
