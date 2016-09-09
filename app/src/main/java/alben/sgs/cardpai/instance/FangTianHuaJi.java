package alben.sgs.cardpai.instance;

import java.util.ArrayList;

import alben.sgs.android.R;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.FangJuCardPai;
import alben.sgs.cardpai.WuQiCardPai;
import alben.sgs.type.Type;
import alben.sgs.wujiang.WuJiang;
import alben.sgs.wujiang.instance.DaQiao;
import alben.sgs.wujiang.instance.HuangZhong;
import alben.sgs.wujiang.instance.LvBu;
import alben.sgs.wujiang.instance.MaChao;

public class FangTianHuaJi extends WuQiCardPai {
	public FangTianHuaJi(Type.CardPai na, Type.CardPaiClass c, int n,
			int imgNumber, int d) {
		super(na, c, n, imgNumber, d);
		this.dispName = "方天画戟";
		this.zbImgNumber = R.drawable.zb_fangtianhuaji;
	}

	public void listenShaEvent(WuJiang srcWJ, WuJiang tarWJ, CardPai srcCP) {
		if (srcWJ.state != Type.State.ChuPai)
			return;

		if (!(srcCP instanceof Sha))
			return;

		if (this.gameApp.selectWJViewData.selectNumber == 3)
			srcWJ.specialChuPaiReason = "[" + this.dispName + "]";
	}

	public boolean takeOverShaWork(WuJiang srcWJ, CardPai srcCP,
			WuJiang tarWJ1, CardPai tarCP) {

		if (srcWJ instanceof HuangZhong || srcWJ instanceof MaChao) {
			return this.FTHJShaWithMingZhongChecking(srcWJ, srcCP, tarWJ1,
					tarCP);
		}

		srcWJ.huiHeChuShaN++;

		int origSHN = srcCP.shangHaiN;
		boolean origHeJiu = srcWJ.heJiu;

		// first contrast the tempWJList
		ArrayList<WuJiang> tempWJList = new ArrayList<WuJiang>();
		WuJiang curWJ = srcWJ.nextOne;
		while (!curWJ.equals(srcWJ)) {
			tempWJList.add(curWJ);
			curWJ = curWJ.nextOne;
		}// end

		for (int i = 0; i < tempWJList.size(); i++) {
			if (this.gameApp.gameLogicData.wjHelper.checkMatchOver())
				break;
			WuJiang tarWJ = tempWJList.get(i);
			if (tarWJ.state == Type.State.Dead)
				continue;

			if (this.gameApp.selectWJViewData.selectedWJs.contains(tarWJ)) {
				this.gameApp.selectWJViewData.selectedWJs.remove(tarWJ);
				this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "["
						+ this.dispName + "]" + srcCP + tarWJ.dispName,
						Type.logDelay.Delay);

				// First DaQiao, liuLi
				if (tarWJ instanceof DaQiao) {
					WuJiang llTarWJ = ((DaQiao) tarWJ)
							.LiuLi(srcWJ, tarWJ, this);
					if (llTarWJ != null)
						tarWJ = llTarWJ;
				}

				if (tarWJ.zhuangBei.fangJu != null
						&& !(tarWJ.zhuangBei.fangJu instanceof BaGuaZhen)) {
					FangJuCardPai fjcp = (FangJuCardPai) tarWJ.zhuangBei.fangJu;
					if (fjcp.defenceWork(srcWJ, tarWJ, srcCP)) {
						// next one
						tarWJ = tarWJ.nextOne;
						continue;
					}
				}

				CardPai shanCP = null;
				// For LvBu shuangXiong
				if (srcWJ instanceof LvBu) {
					shanCP = tarWJ.chuShan(srcWJ, srcCP);
					if (shanCP != null) {
						this.gameApp.libGameViewData.logInfo(srcWJ.dispName
								+ "的" + srcWJ.jiNengN1 + "触发",
								Type.logDelay.NoDelay);
						shanCP = tarWJ.chuShan(srcWJ, srcCP);
					}
				} else {
					shanCP = tarWJ.chuShan(srcWJ, srcCP);
				}
				//
				if (shanCP != null) {
					// First WuJiang JiNeng (PangDe)
					srcWJ.listenShanEvent(srcWJ, tarWJ, srcCP);

				} else {
					srcCP.belongToWuJiang.heJiu = origHeJiu;
					srcCP.shangHaiN = origSHN;
					srcCP.countTotalShangHaiN(tarWJ);
					srcCP.shangHaiReason = "";
					srcCP.shangHaiSrcWJ = srcWJ;
					tarWJ.increaseBlood(srcWJ, srcCP);
				}
			}
		}
		return true;
	}

	public boolean FTHJShaWithMingZhongChecking(WuJiang srcWJ, CardPai srcCP,
			WuJiang tarWJ1, CardPai tarCP) {

		srcWJ.huiHeChuShaN++;

		int origSHN = srcCP.shangHaiN;
		boolean origHeJiu = srcWJ.heJiu;

		// first contrast the tempWJList
		ArrayList<WuJiang> tempWJList = new ArrayList<WuJiang>();
		WuJiang curWJ = srcWJ.nextOne;
		while (!curWJ.equals(srcWJ)) {
			tempWJList.add(curWJ);
			curWJ = curWJ.nextOne;
		}// end

		for (int i = 0; i < tempWJList.size(); i++) {
			if (this.gameApp.gameLogicData.wjHelper.checkMatchOver())
				break;
			WuJiang tarWJ = tempWJList.get(i);
			if (tarWJ.state == Type.State.Dead)
				continue;

			if (this.gameApp.selectWJViewData.selectedWJs.contains(tarWJ)) {
				this.gameApp.selectWJViewData.selectedWJs.remove(tarWJ);
				this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "["
						+ this.dispName + "]" + srcCP + tarWJ.dispName,
						Type.logDelay.Delay);

				// First DaQiao, liuLi
				if (tarWJ instanceof DaQiao) {
					WuJiang llTarWJ = ((DaQiao) tarWJ)
							.LiuLi(srcWJ, tarWJ, this);
					if (llTarWJ != null)
						tarWJ = llTarWJ;
				}

				// Then WuJiang JiNeng
				boolean shaCanNotAvoid = srcWJ.checkShaMingZhong(tarWJ);

				if (tarWJ.zhuangBei.fangJu != null
						&& !(tarWJ.zhuangBei.fangJu instanceof BaGuaZhen)) {
					FangJuCardPai fjcp = (FangJuCardPai) tarWJ.zhuangBei.fangJu;
					if (fjcp.defenceWork(srcWJ, tarWJ, this)) {
						// next one
						tarWJ = tarWJ.nextOne;
						continue;
					}
				}

				CardPai shanCP = null;
				if (!shaCanNotAvoid) {
					shanCP = tarWJ.chuShan(srcWJ, srcCP);
				}

				if (shanCP == null) {
					srcCP.belongToWuJiang.heJiu = origHeJiu;
					srcCP.shangHaiN = origSHN;
					srcCP.countTotalShangHaiN(tarWJ);
					srcCP.shangHaiReason = "";
					srcCP.shangHaiSrcWJ = srcWJ;
					tarWJ.increaseBlood(srcWJ, srcCP);
				}
			}
		}
		return true;
	}
}
