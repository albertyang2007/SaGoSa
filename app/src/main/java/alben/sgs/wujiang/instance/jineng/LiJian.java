package alben.sgs.wujiang.instance.jineng;

import alben.sgs.android.R;
import alben.sgs.cardpai.CardPai;
import alben.sgs.type.Type;
import alben.sgs.wujiang.WuJiang;
import alben.sgs.wujiang.instance.LvBu;
import alben.sgs.wujiang.instance.ZhuGeLiang;

public class LiJian extends CardPai {
	public CardPai sp1 = null;
	public WuJiang tarWJ1, tarWJ2;

	public LiJian(Type.CardPai na, Type.CardPaiClass c, int n) {
		super(na, c, n);
		this.ID = generateID++;
		this.dispName = "离间";
		this.shangHaiN = -1;
		this.cpState = Type.CPState.nil;
		this.name = Type.CardPai.WJJiNeng;
		this.selectTarWJNumber = 2;
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
				+ this.dispName + "],弃掉" + this.sp1 + "让"
				+ this.tarWJ2.dispName + "和" + this.tarWJ1.dispName + "决斗",
				Type.logDelay.Delay);

		// discard one cardpai for LiJian
		this.sp1.belongToWuJiang = null;
		srcWJ.detatchCardPaiFromShouPai(this.sp1);

		// like jueDou work

		CardPai shaCP = null;
		WuJiang[] WJs = { this.tarWJ2, this.tarWJ1 };
		boolean run = true;
		int index = 0;
		while (run) {
			// For LvBu shuangXiong
			if (WJs[(index + 1) % 2] instanceof LvBu) {
				shaCP = WJs[index % 2].chuSha(WJs[(index + 1) % 2], this);
				if (shaCP != null) {
					this.gameApp.libGameViewData.logInfo(
							WJs[(index + 1) % 2].dispName + "["
									+ WJs[(index + 1) % 2].jiNengN1 + "]",
							Type.logDelay.HalfDelay);
					shaCP = WJs[index % 2].chuSha(WJs[(index + 1) % 2], this);
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
				this.countTotalShangHaiN(WJs[index % 2]);
				this.gameApp.libGameViewData.logInfo(WJs[index % 2].dispName
						+ "放弃出杀", Type.logDelay.Delay);
				this.shangHaiSrcWJ = WJs[(index + 1) % 2];
				WJs[index % 2].increaseBlood(srcWJ, this);
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

				if (tarWJ.sex == Type.Sex.man
						&& this.canSelectAsFirstChuSha(tarWJ)) {
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

			this.gameApp.libGameViewData.logInfo("请选择第1个先出杀的武将",
					Type.logDelay.NoDelay);
		}
	}

	// click wj and set data
	public void onClickWJUpdateView(WuJiang curClickWJ) {
		// for the first time, select wu jiang who has wu qi
		if (gameApp.selectWJViewData.selectedWJ1 == null) {

			gameApp.selectWJViewData.selectedWJ1 = curClickWJ;
			this.tarWJ2 = curClickWJ;
			curClickWJ.canSelect = false;

			this.gameApp.libGameViewData.logInfo("请选择第2个出杀的武将",
					Type.logDelay.NoDelay);

			String canSelectStr = "";
			WuJiang tarWJ = this.belongToWuJiang.nextOne;
			while (!tarWJ.equals(this.belongToWuJiang)) {

				if (tarWJ.sex == Type.Sex.man && !tarWJ.equals(curClickWJ)) {
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

			// this.gameApp.libGameViewData.logInfo("你可以选择" + canSelectStr);

			// remember to return
			return;
		}

		// then for the second time, select second wj
		if (gameApp.selectWJViewData.selectedWJ2 == null) {
			gameApp.selectWJViewData.selectedWJ2 = curClickWJ;
			this.tarWJ1 = curClickWJ;
		}
	}

	// un-click wj and re-set data
	public void onUnClickWJUpdateView(WuJiang curClickWJ) {
		// gameApp.selectWJViewData.selectedWJ1 = null;
	}

	public void selectTarWJForAI() {

		if (this.belongToWuJiang == null)
			return;
		// select two WJ to JueDou, must be man
		// tarWJ1: jueDou starter, second chu sha
		// tarWJ2: first chu sha
		// search LvBu first,haha
		WuJiang tarWJ = this.belongToWuJiang.nextOne;
		while (!tarWJ.equals(this.belongToWuJiang)) {
			if (tarWJ.sex == Type.Sex.man) {
				tarWJ.canSelect = true;
			}
			if (tarWJ.name == Type.WuJiang.LvBu) {
				tarWJ.canSelect = false;
				this.tarWJ1 = tarWJ;
				// do not break, set other wj canSelect true
			}
			tarWJ = tarWJ.nextOne;
		}

		// if no lvBu
		if (this.tarWJ1 == null) {
			if (this.belongToWuJiang.role == Type.Role.FanZei) {
				// select zhuGong as jueDou starter
				if (this.gameApp.gameLogicData.zhuGongWuJiang.canSelect) {
					this.tarWJ1 = this.gameApp.gameLogicData.zhuGongWuJiang;
					this.gameApp.gameLogicData.zhuGongWuJiang.canSelect = false;
				} else {
					// if zhugong is women or lvBu, then select zhongchen as the
					// jueDou starter
					this.tarWJ1 = this.getWuJiangByRole(Type.Role.ZhongChen);
				}
				// select zhongChen as first chu sha
				if (this.canSelectAsFirstChuSha(this
						.getWuJiangByRole(Type.Role.ZhongChen))) {
					this.tarWJ2 = this.getWuJiangByRole(Type.Role.ZhongChen);
				}
			} else if (this.belongToWuJiang.role == Type.Role.ZhuGong
					|| this.belongToWuJiang.role == Type.Role.ZhongChen) {
				this.tarWJ1 = this.getWuJiangByRole(Type.Role.FanZei);
				if (this.canSelectAsFirstChuSha(this
						.getWuJiangByRole(Type.Role.NeiJian))) {
					this.tarWJ2 = this.getWuJiangByRole(Type.Role.NeiJian);
				}
			} else if (this.belongToWuJiang.role == Type.Role.NeiJian) {
				this.tarWJ1 = this.getWuJiangByRole(Type.Role.ZhongChen);
				if (this.canSelectAsFirstChuSha(this
						.getWuJiangByRole(Type.Role.FanZei))) {
					this.tarWJ2 = this.getWuJiangByRole(Type.Role.FanZei);
				}
			}
		}

		if (this.tarWJ1 == null) {
			for (int i = 0; i < this.belongToWuJiang.opponentList.size(); i++) {
				WuJiang wj = this.belongToWuJiang.opponentList.get(i);
				if (wj.canSelect) {
					wj.canSelect = false;
					this.tarWJ1 = wj;
					break;
				}
			}
		}

		if (this.tarWJ2 == null) {
			for (int i = 0; i < this.belongToWuJiang.opponentList.size(); i++) {
				WuJiang wj = this.belongToWuJiang.opponentList.get(i);
				if (wj.canSelect && this.canSelectAsFirstChuSha(wj)) {
					wj.canSelect = false;
					this.tarWJ2 = wj;
					break;
				}
			}
		}
	}

	//
	public WuJiang getWuJiangByRole(Type.Role role) {
		WuJiang tarWJ = this.belongToWuJiang.nextOne;
		while (!tarWJ.equals(this.belongToWuJiang)) {
			if (tarWJ.canSelect && tarWJ.role == role) {
				tarWJ.canSelect = false;
				return tarWJ;
			}
			tarWJ = tarWJ.nextOne;
		}
		return null;
	}

	// check if ZhuGeLiang is kongCheng
	public boolean canSelectAsFirstChuSha(WuJiang tarWJ) {
		if (tarWJ instanceof ZhuGeLiang) {
			if (tarWJ.shouPai.size() == 0) {
				return false;
			}
		}
		return true;
	}

	public boolean canChuPai() {
		boolean rtn = false;
		if (this.gameApp.gameLogicData.myWuJiang.state == Type.State.ChuPai
				&& this.gameApp.gameLogicData.askForPai == Type.CardPai.notNil) {

			if (this.gameApp.selectWJViewData.selectNumber == 1
					&& this.gameApp.selectWJViewData.selectedWJ1 != null) {
				rtn = true;
			} else if (this.gameApp.selectWJViewData.selectNumber == 2
					&& this.gameApp.selectWJViewData.selectedWJ1 != null
					&& this.gameApp.selectWJViewData.selectedWJ2 != null) {
				rtn = true;
			}
		} else {
			rtn = true;
		}

		if (!rtn) {
			this.belongToWuJiang.shouPai.remove(this);
			this.gameApp.libGameViewData.logInfo("Error:不满足出牌条件",
					Type.logDelay.NoDelay);
		}

		return rtn;
	}
}
