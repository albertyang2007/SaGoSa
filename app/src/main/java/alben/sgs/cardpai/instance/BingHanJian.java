package alben.sgs.cardpai.instance;

import alben.sgs.android.R;
import alben.sgs.android.dialog.SelectCardPaiFromWJDialog;
import alben.sgs.android.dialog.YesNoDialog;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.WuQiCardPai;
import alben.sgs.cardpai.ZhuangBeiCardPai;
import alben.sgs.type.Type;
import alben.sgs.type.UpdateWJViewData;
import alben.sgs.type.WuJiangCardPaiData;
import alben.sgs.wujiang.WuJiang;

public class BingHanJian extends WuQiCardPai {
	public BingHanJian(Type.CardPai na, Type.CardPaiClass c, int n,
			int imgNumber, int d) {
		super(na, c, n, imgNumber, d);
		this.dispName = "寒冰剑";
		this.zbImgNumber = R.drawable.zb_hanbingjian;
	}

	public boolean listenMingZhongEvent(WuJiang srcWJ, WuJiang tarWJ,
			CardPai srcCP) {
		boolean mzRtn = true;

		if (srcWJ.state != Type.State.ChuPai)
			return mzRtn;

		if (!(srcCP instanceof Sha))
			return mzRtn;

		// 如果大乔使用了流离,tarWJ已经改变,很可能这个时候srcWJ和tarWJ是friend
		if (srcWJ.tuoGuan) {
			int count = 0;
			if (srcWJ.isFriend(tarWJ)) {

				if (count < 1 && tarWJ.zhuangBei.jianYiMa != null) {
					this.gameApp.libGameViewData.logInfo(srcWJ.dispName
							+ this.dispName + "弃掉了" + tarWJ.dispName + "的"
							+ tarWJ.zhuangBei.jianYiMa, Type.logDelay.Delay);
					tarWJ.unstallZhuangBei(tarWJ.zhuangBei.jianYiMa);
					count++;
				}

				if (count < 1 && tarWJ.shouPai.size() > 0) {
					CardPai cp = tarWJ.shouPai.get(0);
					this.gameApp.libGameViewData.logInfo(srcWJ.dispName
							+ this.dispName + "弃掉了" + tarWJ.dispName + "的手牌"
							+ cp, Type.logDelay.Delay);
					tarWJ.detatchCardPaiFromShouPai(cp);
					cp.belongToWuJiang = null;
					count++;
				}

				if (count < 1 && tarWJ.zhuangBei.jiaYiMa != null) {
					this.gameApp.libGameViewData.logInfo(srcWJ.dispName
							+ this.dispName + "弃掉了" + tarWJ.dispName + "的"
							+ tarWJ.zhuangBei.jiaYiMa, Type.logDelay.Delay);
					tarWJ.unstallZhuangBei(tarWJ.zhuangBei.jiaYiMa);
					count++;
				}

				if (count < 1 && tarWJ.zhuangBei.wuQi != null) {
					this.gameApp.libGameViewData.logInfo(srcWJ.dispName
							+ this.dispName + "弃掉了" + tarWJ.dispName + "的"
							+ tarWJ.zhuangBei.wuQi, Type.logDelay.Delay);
					tarWJ.unstallZhuangBei(tarWJ.zhuangBei.wuQi);
					count++;
				}

				if (count < 1 && tarWJ.zhuangBei.fangJu != null) {
					this.gameApp.libGameViewData.logInfo(srcWJ.dispName
							+ this.dispName + "弃掉了" + tarWJ.dispName + "的"
							+ tarWJ.zhuangBei.fangJu, Type.logDelay.Delay);
					tarWJ.unstallZhuangBei(tarWJ.zhuangBei.fangJu);
					count++;
				}
			} else {
				if (tarWJ.zhuangBei.fangJu != null) {
					this.gameApp.libGameViewData.logInfo(srcWJ.dispName
							+ this.dispName + "弃掉了" + tarWJ.dispName + "的防具:"
							+ tarWJ.zhuangBei.fangJu, Type.logDelay.Delay);
					tarWJ.unstallZhuangBei(tarWJ.zhuangBei.fangJu);
					count++;
				}

				if (count < 2 && tarWJ.zhuangBei.jiaYiMa != null) {
					this.gameApp.libGameViewData.logInfo(srcWJ.dispName
							+ this.dispName + "弃掉了" + tarWJ.dispName + "的"
							+ tarWJ.zhuangBei.jiaYiMa, Type.logDelay.Delay);
					tarWJ.unstallZhuangBei(tarWJ.zhuangBei.jiaYiMa);
					count++;
				}

				if (count < 2 && tarWJ.zhuangBei.wuQi != null) {
					this.gameApp.libGameViewData.logInfo(srcWJ.dispName
							+ this.dispName + "弃掉了" + tarWJ.dispName + "的"
							+ tarWJ.zhuangBei.wuQi, Type.logDelay.Delay);
					tarWJ.unstallZhuangBei(tarWJ.zhuangBei.wuQi);
					count++;
				}

				if (count < 2 && tarWJ.zhuangBei.jianYiMa != null) {
					this.gameApp.libGameViewData.logInfo(srcWJ.dispName
							+ this.dispName + "弃掉了" + tarWJ.dispName + "的"
							+ tarWJ.zhuangBei.jianYiMa, Type.logDelay.Delay);
					tarWJ.unstallZhuangBei(tarWJ.zhuangBei.jianYiMa);
					count++;
				}

				if (count < 2 && tarWJ.shouPai.size() > 0) {
					CardPai cp = tarWJ.shouPai.get(0);
					this.gameApp.libGameViewData.logInfo(srcWJ.dispName
							+ this.dispName + "弃掉了" + tarWJ.dispName + "的手牌"
							+ cp, Type.logDelay.Delay);

					tarWJ.detatchCardPaiFromShouPai(cp);
					cp.belongToWuJiang = null;
					count++;
				}

				if (count < 2 && tarWJ.shouPai.size() > 0) {
					CardPai cp = tarWJ.shouPai.get(0);
					this.gameApp.libGameViewData.logInfo(srcWJ.dispName
							+ this.dispName + "弃掉了" + tarWJ.dispName + "的手牌"
							+ cp, Type.logDelay.Delay);
					tarWJ.detatchCardPaiFromShouPai(cp);
					cp.belongToWuJiang = null;
					count++;
				}
			}

			if (count > 0)
				mzRtn = false;
		} else {

			this.gameApp.ynData.reset();
			this.gameApp.ynData.okTxt = "确认";
			this.gameApp.ynData.cancelTxt = "取消";
			this.gameApp.ynData.genInfo = "是否使用" + this.dispName
					+ "弃2张牌而不令对方受伤?";

			YesNoDialog dlg1 = new YesNoDialog(
					this.gameApp.gameActivityContext, this.gameApp);
			dlg1.showDialog();

			if (!this.gameApp.ynData.result)
				return mzRtn;

			// first select CP
			this.gameApp.wjDetailsViewData.reset();
			this.gameApp.wjDetailsViewData.selectedCardNumber = 2;
			this.gameApp.wjDetailsViewData.selectedCardN1Or2 = true;
			this.gameApp.wjDetailsViewData.canViewShouPai = false;

			this.gameApp.wjDetailsViewData.selectedWJ = tarWJ;

			WuJiangCardPaiData wjCPData = new WuJiangCardPaiData();
			wjCPData.zhuangBei.wuQi = tarWJ.zhuangBei.wuQi;
			wjCPData.zhuangBei.fangJu = tarWJ.zhuangBei.fangJu;
			wjCPData.zhuangBei.jianYiMa = tarWJ.zhuangBei.jianYiMa;
			wjCPData.zhuangBei.jiaYiMa = tarWJ.zhuangBei.jiaYiMa;
			wjCPData.shouPai = tarWJ.shouPai;

			SelectCardPaiFromWJDialog dlg2 = new SelectCardPaiFromWJDialog(
					this.gameApp.gameActivityContext, this.gameApp, wjCPData);

			dlg2.showDialog();

			CardPai cp1 = this.gameApp.wjDetailsViewData.selectedCardPai1;
			CardPai cp2 = this.gameApp.wjDetailsViewData.selectedCardPai2;

			UpdateWJViewData item = new UpdateWJViewData();
			item.updateAll = true;

			String info = "";

			if (cp1 != null) {
				if (cp1.cpState == Type.CPState.ShouPai) {
					tarWJ.detatchCardPaiFromShouPai(cp1);
				} else if (cp1.cpState == Type.CPState.wuQiPai
						|| cp1.cpState == Type.CPState.fangJuPai
						|| cp1.cpState == Type.CPState.jiaYiMaPai
						|| cp1.cpState == Type.CPState.jianYiMaPai) {
					tarWJ.unstallZhuangBei((ZhuangBeiCardPai) cp1);
				}
				cp1.belongToWuJiang = null;
				info = cp1.toString() + " ";
				mzRtn = false;
			}

			if (cp2 != null) {
				if (cp2.cpState == Type.CPState.ShouPai) {
					tarWJ.detatchCardPaiFromShouPai(cp2);
				} else if (cp2.cpState == Type.CPState.wuQiPai
						|| cp2.cpState == Type.CPState.fangJuPai
						|| cp2.cpState == Type.CPState.jiaYiMaPai
						|| cp2.cpState == Type.CPState.jianYiMaPai) {
					tarWJ.unstallZhuangBei((ZhuangBeiCardPai) cp2);
				}
				cp2.belongToWuJiang = null;
				info += "\n" + cp2.toString();

				mzRtn = false;
			}

			if (info.trim().length() > 0) {
				this.gameApp.libGameViewData.logInfo(srcWJ.dispName
						+ this.dispName + "弃掉对方牌" + info, Type.logDelay.Delay);
				// update view
				this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(
						tarWJ, item);
			}
		}

		return mzRtn;
	}
}
