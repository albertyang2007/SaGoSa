package alben.sgs.cardpai.instance;

import alben.sgs.android.R;
import alben.sgs.cardpai.BasicCardPai;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.FangJuCardPai;
import alben.sgs.type.Type;
import alben.sgs.wujiang.WuJiang;
import alben.sgs.wujiang.instance.DaQiao;
import alben.sgs.wujiang.instance.HuangZhong;
import alben.sgs.wujiang.instance.LvBu;
import alben.sgs.wujiang.instance.MaChao;
import alben.sgs.wujiang.instance.TaiShiChi;
import alben.sgs.wujiang.instance.XuZhu;
import alben.sgs.wujiang.instance.ZhuGeLiang;

public class Sha extends BasicCardPai {

	public Sha(Type.CardPai na, Type.CardPaiClass c, int n, int imgNumber) {
		super(na, c, n, imgNumber);
		this.dispName = "杀";
		this.selectTarWJNumber = 1;
	}

	public boolean work(WuJiang srcWJ, WuJiang tarWJ, CardPai tarCP) {

		if (tarWJ == null
				&& this.gameApp.selectWJViewData.selectedWJs.size() > 0) {
			tarWJ = this.gameApp.selectWJViewData.selectedWJs.get(0);
		}

		if (srcWJ instanceof HuangZhong || srcWJ instanceof MaChao) {
			return this.workWithMingZhongChecking(srcWJ, tarWJ, tarCP);
		}

		if (srcWJ instanceof TaiShiChi) {
			return ((TaiShiChi) srcWJ).takeOverShaWork(this, tarWJ, tarCP);
		}

		if (srcWJ.zhuangBei.wuQi != null
				&& srcWJ.zhuangBei.wuQi instanceof FangTianHuaJi
				&& this.gameApp.selectWJViewData.selectNumber == 3) {
			return ((FangTianHuaJi) srcWJ.zhuangBei.wuQi).takeOverShaWork(
					srcWJ, this, tarWJ, tarCP);
		}

		// invoke huiHeChuShaN++ here and do not put it to commonShaWork
		srcWJ.huiHeChuShaN++;

		return this.commonShaWork(srcWJ, tarWJ, tarCP);

	}

	// such as QingLongYuanYueDao, JieDaoShaRen
	public boolean commonShaWork(WuJiang srcWJ, WuJiang tarWJ, CardPai tarCP) {

		String wqS = "";
		if (srcWJ.zhuangBei.wuQi != null)
			wqS = "[" + srcWJ.zhuangBei.wuQi.dispName + "]";

		this.gameApp.libGameViewData.logInfo(srcWJ.dispName + wqS + this
				+ tarWJ.dispName, Type.logDelay.Delay);

		// First DaQiao, liuLi
		if (tarWJ instanceof DaQiao) {
			WuJiang llTarWJ = ((DaQiao) tarWJ).LiuLi(srcWJ, tarWJ, this);
			if (llTarWJ != null)
				tarWJ = llTarWJ;
		}

		// Then WuQi
		if (srcWJ.zhuangBei.wuQi != null) {
			srcWJ.zhuangBei.wuQi.listenShaEvent(srcWJ, tarWJ, this);
		}

		if (srcWJ.zhuangBei.wuQi != null
				&& srcWJ.zhuangBei.wuQi instanceof QingHongJian) {
			// QingHongJian wu shi dui fang fangju
			// do nothing
		} else {
			if (tarWJ.zhuangBei.fangJu != null
					&& !(tarWJ.zhuangBei.fangJu instanceof BaGuaZhen)) {
				FangJuCardPai fjcp = (FangJuCardPai) tarWJ.zhuangBei.fangJu;
				if (fjcp.defenceWork(srcWJ, tarWJ, this)) {
					return true;
				}
			}
		}

		CardPai shanCP = null;
		// For LvBu shuangXiong
		if (srcWJ instanceof LvBu) {
			shanCP = tarWJ.chuShan(srcWJ, this);
			if (shanCP != null) {
				this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "的"
						+ srcWJ.jiNengN1 + "触发", Type.logDelay.NoDelay);
				shanCP = tarWJ.chuShan(srcWJ, this);
			}
		} else {
			shanCP = tarWJ.chuShan(srcWJ, this);
		}
		//
		if (shanCP != null) {
			// First WuJiang JiNeng (PangDe)
			srcWJ.listenShanEvent(srcWJ, tarWJ, this);

			// Then WuQi JiNeng
			if (srcWJ.zhuangBei.wuQi != null) {
				srcWJ.zhuangBei.wuQi.listenShanEvent(srcWJ, tarWJ, this);
			}
		} else {
			boolean mzRtn = true;
			// Then WuQi JiNeng
			if (srcWJ.zhuangBei.wuQi != null) {
				mzRtn = srcWJ.zhuangBei.wuQi.listenMingZhongEvent(srcWJ, tarWJ,
						this);
			}

			if (mzRtn) {
				this.countTotalShangHaiN(tarWJ);
				this.shangHaiSrcWJ = srcWJ;
				tarWJ.increaseBlood(srcWJ, this);
			}
		}
		return true;
	}

	public boolean workWithMingZhongChecking(WuJiang srcWJ, WuJiang tarWJ,
			CardPai tarCP) {

		if (srcWJ.zhuangBei.wuQi != null
				&& srcWJ.zhuangBei.wuQi instanceof FangTianHuaJi
				&& this.gameApp.selectWJViewData.selectNumber == 3) {
			return ((FangTianHuaJi) srcWJ.zhuangBei.wuQi).takeOverShaWork(
					srcWJ, this, tarWJ, tarCP);
		}

		srcWJ.huiHeChuShaN++;

		String wqS = "";
		if (srcWJ.zhuangBei.wuQi != null)
			wqS = "[" + srcWJ.zhuangBei.wuQi.dispName + "]";

		this.gameApp.libGameViewData.logInfo(srcWJ.dispName + wqS + this
				+ tarWJ.dispName, Type.logDelay.Delay);

		// First DaQiao, liuLi
		if (tarWJ instanceof DaQiao) {
			WuJiang llTarWJ = ((DaQiao) tarWJ).LiuLi(srcWJ, tarWJ, this);
			if (llTarWJ != null)
				tarWJ = llTarWJ;
		}

		// Then WuQi JiNeng
		if (srcWJ.zhuangBei.wuQi != null) {
			srcWJ.zhuangBei.wuQi.listenShaEvent(srcWJ, tarWJ, this);
		}

		// Last WuJiang JiNeng
		boolean shaCanNotAvoid = srcWJ.checkShaMingZhong(tarWJ);

		if (srcWJ.zhuangBei.wuQi != null
				&& srcWJ.zhuangBei.wuQi instanceof QingHongJian) {
			// QingHongJian wu shi dui fang fangju
			// do nothing
		} else {
			if (tarWJ.zhuangBei.fangJu != null
					&& !(tarWJ.zhuangBei.fangJu instanceof BaGuaZhen)) {
				FangJuCardPai fjcp = (FangJuCardPai) tarWJ.zhuangBei.fangJu;
				if (fjcp.defenceWork(srcWJ, tarWJ, this)) {
					return true;
				}
			}
		}

		CardPai shanCP = null;
		if (!shaCanNotAvoid) {
			shanCP = tarWJ.chuShan(srcWJ, this);
		}

		if (shanCP != null) {
			if (srcWJ.zhuangBei.wuQi != null) {
				srcWJ.zhuangBei.wuQi.listenShanEvent(srcWJ, tarWJ, this);
			}
		} else {
			boolean mzRtn = true;
			if (srcWJ.zhuangBei.wuQi != null) {
				mzRtn = srcWJ.zhuangBei.wuQi.listenMingZhongEvent(srcWJ, tarWJ,
						this);
			}

			if (mzRtn) {
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

		// 当被点击时候，显示可以到的WJ
		if (this.belongToWuJiang != null) {

			if (!this.belongToWuJiang.canIChuSha()) {
				this.gameApp.libGameViewData.logInfo("你不能出2次以上的杀",
						Type.logDelay.NoDelay);
				return;
			}

			// by default set the select wj number to 1
			this.gameApp.selectWJViewData.reset();
			this.gameApp.selectWJViewData.selectNumber = this.selectTarWJNumber;

			if (this.belongToWuJiang.shouPai.size() == 1
					&& (this.belongToWuJiang.zhuangBei.wuQi != null && this.belongToWuJiang.zhuangBei.wuQi instanceof FangTianHuaJi)) {
				this.gameApp.selectWJViewData.selectedWJAtLeast1 = true;
				this.gameApp.selectWJViewData.selectNumber = 3;
				this.gameApp.libGameViewData.logInfo(
						this.belongToWuJiang.dispName
								+ this.belongToWuJiang.zhuangBei.wuQi.dispName
								+ ",最多可杀3人", Type.logDelay.NoDelay);
			}

			if (this.belongToWuJiang instanceof TaiShiChi) {
				TaiShiChi tscWJ = (TaiShiChi) this.belongToWuJiang;
				if (tscWJ.tianYiSuccess) {
					this.gameApp.selectWJViewData.selectedWJAtLeast1 = true;
					this.gameApp.selectWJViewData.selectNumber += 1;
					this.gameApp.libGameViewData.logInfo(tscWJ.dispName
							+ tscWJ.jiNengN1 + "成功,最多可杀"
							+ this.gameApp.selectWJViewData.selectNumber + "人",
							Type.logDelay.NoDelay);
				}
			}

			String canSelectStr = "";
			WuJiang tarWJ = this.belongToWuJiang.nextOne;
			while (!tarWJ.equals(this.belongToWuJiang)) {
				// reset first
				this.gameApp.libGameViewData.imageWJs[tarWJ.imageViewIndex]
						.setBackgroundDrawable(this.gameApp.getResources()
								.getDrawable(R.drawable.bg_black));
				tarWJ.clicked = false;
				tarWJ.canSelect = false;

				//
				int distance = this.gameApp.gameLogicData.wjHelper
						.countDistance(this.belongToWuJiang, tarWJ, true);

				// if TaiShiChi tianYi is success, then distance is long!
				if (this.belongToWuJiang instanceof TaiShiChi) {
					TaiShiChi tscWJ = (TaiShiChi) this.belongToWuJiang;
					if (tscWJ.tianYiSuccess) {
						distance = 0;
					}
				}

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

			if (canSelectStr.trim().length() <= 0)
				this.gameApp.libGameViewData.logInfo("没有可杀对象",
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

		if (this.gameApp.selectWJViewData.selectedWJs.size() >= this.gameApp.selectWJViewData.selectNumber) {
			this.gameApp.libGameViewData.logInfo("不能再选择武将",
					Type.logDelay.NoDelay);
			return;
		}

		// for all
		if (!gameApp.selectWJViewData.selectedWJs.contains(curClickWJ)) {
			gameApp.selectWJViewData.selectedWJs.add(curClickWJ);
		}

		// for old
		boolean runOld = false;
		if (runOld) {
			if (gameApp.selectWJViewData.selectNumber == 1) {
				gameApp.selectWJViewData.selectedWJ1 = curClickWJ;
			} else if (gameApp.selectWJViewData.selectNumber == 2) {
				if (gameApp.selectWJViewData.selectedWJ1 == null)
					gameApp.selectWJViewData.selectedWJ1 = curClickWJ;
				else if (gameApp.selectWJViewData.selectedWJ2 == null)
					gameApp.selectWJViewData.selectedWJ2 = curClickWJ;
			} else if (gameApp.selectWJViewData.selectedWJAtLeast1) {
				if (this.gameApp.selectWJViewData.selectedWJs.size() <= this.gameApp.selectWJViewData.selectNumber) {
					if (!gameApp.selectWJViewData.selectedWJs
							.contains(curClickWJ))
						gameApp.selectWJViewData.selectedWJs.add(curClickWJ);
				} else {
					this.gameApp.libGameViewData.logInfo("不能再选择武将",
							Type.logDelay.NoDelay);
				}
			}
		}
	}

	// un-click wj and re-set data
	public void onUnClickWJUpdateView(WuJiang curClickWJ) {

		// for all
		if (gameApp.selectWJViewData.selectedWJs.contains(curClickWJ)) {
			gameApp.selectWJViewData.selectedWJs.remove(curClickWJ);
		}

		// for old
		boolean runOld = false;
		if (runOld) {
			if (gameApp.selectWJViewData.selectNumber == 1) {
				gameApp.selectWJViewData.selectedWJ1 = null;
			} else if (gameApp.selectWJViewData.selectNumber == 2) {
				gameApp.selectWJViewData.selectedWJ1 = null;
				gameApp.selectWJViewData.selectedWJ2 = null;
			} else if (gameApp.selectWJViewData.selectedWJAtLeast1) {
				if (gameApp.selectWJViewData.selectedWJs.contains(curClickWJ))
					gameApp.selectWJViewData.selectedWJs.remove(curClickWJ);
			}
		}
	}

	public boolean canChuPai() {
		boolean rtn = false;
		if (this.gameApp.gameLogicData.myWuJiang.state == Type.State.ChuPai
				&& this.gameApp.gameLogicData.askForPai == Type.CardPai.notNil) {

			// for all
			if (this.gameApp.selectWJViewData.selectedWJs.size() == this.gameApp.selectWJViewData.selectNumber) {
				rtn = true;
			} else if (this.gameApp.selectWJViewData.selectedWJs.size() >= 1
					&& this.gameApp.selectWJViewData.selectedWJAtLeast1) {
				rtn = true;
			}

			boolean runOld = false;
			if (runOld) {
				if (this.gameApp.selectWJViewData.selectNumber == 1
						&& this.gameApp.selectWJViewData.selectedWJ1 != null) {
					rtn = true;
				} else if (this.gameApp.selectWJViewData.selectNumber == 2
						&& this.gameApp.selectWJViewData.selectedWJ1 != null
						&& this.gameApp.selectWJViewData.selectedWJ2 != null) {
					rtn = true;
				} else if (this.gameApp.selectWJViewData.selectedWJAtLeast1
						&& this.gameApp.selectWJViewData.selectedWJs.size() > 0
						&& this.gameApp.selectWJViewData.selectedWJs.size() <= this.gameApp.selectWJViewData.selectNumber) {
					rtn = true;
				}
			}
		} else {
			rtn = true;
		}

		if (!rtn)
			this.gameApp.libGameViewData.logInfo("Error:不满足出牌条件",
					Type.logDelay.NoDelay);

		return rtn;
	}

	// overwrite for FangTianHuaJi or TaiShiChi
	public void selectMoreTarWJForAI() {
		if (this.belongToWuJiang != null) {

			// for default case, select one tarWJ
			this.gameApp.selectWJViewData.reset();
			this.gameApp.selectWJViewData.selectNumber = 1;

			boolean needMoreTarWJ = false;

			// for FangTianHuaJi
			if (this.belongToWuJiang.shouPai.size() == 1
					&& this.belongToWuJiang.zhuangBei.wuQi != null
					&& this.belongToWuJiang.zhuangBei.wuQi instanceof FangTianHuaJi) {

				this.gameApp.selectWJViewData.selectNumber = 3;
				this.gameApp.selectWJViewData.selectedWJAtLeast1 = true;
				this.belongToWuJiang.specialChuPaiReason += " "
						+ this.belongToWuJiang.zhuangBei.wuQi.dispName;
				needMoreTarWJ = true;
			}

			// if TaiShiChi tianYi is success, then distance is long!
			if (this.belongToWuJiang instanceof TaiShiChi) {
				TaiShiChi tscWJ = (TaiShiChi) this.belongToWuJiang;
				if (tscWJ.tianYiSuccess) {
					this.gameApp.selectWJViewData.selectNumber += 1;
					this.gameApp.selectWJViewData.selectedWJAtLeast1 = true;
					this.belongToWuJiang.specialChuPaiReason += " "
							+ this.belongToWuJiang.jiNengN1;
					needMoreTarWJ = true;
				}
			}

			// if no need add more WJ, return
			if (!needMoreTarWJ)
				return;

			// first add the tarWJForAI
			if (this.tarWJForAI != null
					&& !gameApp.selectWJViewData.selectedWJs
							.contains(this.tarWJForAI)) {
				gameApp.selectWJViewData.selectedWJs.add(this.tarWJForAI);
			}

			// select tarWJ
			int distance = 0;
			for (int i = 0; i < this.belongToWuJiang.opponentList.size(); i++) {
				if (gameApp.selectWJViewData.selectedWJs.size() >= gameApp.selectWJViewData.selectNumber)
					break;

				WuJiang tarWJ = this.belongToWuJiang.opponentList.get(i);
				distance = this.gameApp.gameLogicData.wjHelper.countDistance(
						this.belongToWuJiang, tarWJ, true);

				// if TaiShiChi tianYi is success, then distance is long!
				if (this.belongToWuJiang instanceof TaiShiChi) {
					TaiShiChi tscWJ = (TaiShiChi) this.belongToWuJiang;
					if (tscWJ.tianYiSuccess) {
						distance = 0;
					}
				}

				// if ZhuGeLiang kongCheng is success
				if (tarWJ instanceof ZhuGeLiang) {
					if (tarWJ.shouPai.size() == 0) {
						distance = 256;
					}
				}

				if (distance <= 1
						&& !gameApp.selectWJViewData.selectedWJs
								.contains(tarWJ)) {
					gameApp.selectWJViewData.selectedWJs.add(tarWJ);
					// this.tarWJForAI = tarWJ;
					continue;
				}
			}
		}
	}

	public int countTotalShangHaiN(WuJiang tarWJ) {

		if (this.belongToWuJiang == null) {
			this.gameApp.libGameViewData.logInfo("Error: 卡牌不属于任何武将",
					Type.logDelay.NoDelay);
			return this.shangHaiN;
		}

		// 酒伤害
		if (this.belongToWuJiang.heJiu) {
			this.shangHaiReason += tarWJ.dispName + "被酒杀,伤害+1\n";
			this.shangHaiN -= 1;
			this.belongToWuJiang.heJiu = false;
		}

		// 武将技能伤害
		if (this.belongToWuJiang instanceof XuZhu
				&& this.belongToWuJiang.oneTimeJiNengTrigger) {
			this.shangHaiReason += this.belongToWuJiang.dispName + "裸衣杀伤力+1\n";
			this.shangHaiN -= 1;
		}

		// 武器的伤害已经在listenShaEvent计算
		this.gameApp.libGameViewData.logInfo(this.shangHaiReason,
				Type.logDelay.NoDelay);

		return this.shangHaiN;
	}
}
