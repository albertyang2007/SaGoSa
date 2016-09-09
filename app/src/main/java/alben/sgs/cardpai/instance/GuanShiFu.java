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

public class GuanShiFu extends WuQiCardPai {
	public GuanShiFu(Type.CardPai na, Type.CardPaiClass c, int n,
			int imgNumber, int d) {
		super(na, c, n, imgNumber, d);
		this.dispName = "贯石斧";
		this.zbImgNumber = R.drawable.zb_guanshifu;
	}

	public void listenShanEvent(WuJiang srcWJ, WuJiang tarWJ, CardPai srcCP) {

		if (srcWJ.state != Type.State.ChuPai)
			return;

		if (!(srcCP instanceof Sha))
			return;

		if (srcWJ.shouPai.size() < 2)
			return;

		if (srcWJ.tuoGuan) {
			// for AI
			CardPai cp1 = srcWJ.shouPai.get(0);
			CardPai cp2 = srcWJ.shouPai.get(1);

			srcWJ.detatchCardPaiFromShouPai(cp1);
			srcWJ.detatchCardPaiFromShouPai(cp2);

			cp1.belongToWuJiang = null;
			cp2.belongToWuJiang = null;

			srcCP.countTotalShangHaiN(tarWJ);
			this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "弃2张手牌" + cp1
					+ " " + cp2, Type.logDelay.NoDelay);
			srcCP.shangHaiSrcWJ = srcWJ;
			tarWJ.increaseBlood(srcWJ, srcCP);
		} else {
			this.gameApp.ynData.reset();
			this.gameApp.ynData.okTxt = "确认";
			this.gameApp.ynData.cancelTxt = "取消";
			this.gameApp.ynData.genInfo = tarWJ.dispName + "出闪" + ",是否使用"
					+ this.dispName + "弃2张手牌强制令对方受伤?";

			YesNoDialog dlg1 = new YesNoDialog(
					this.gameApp.gameActivityContext, this.gameApp);
			dlg1.showDialog();

			if (!this.gameApp.ynData.result)
				return;

			this.gameApp.wjDetailsViewData.reset();
			this.gameApp.wjDetailsViewData.selectedCardNumber = 2;
			// this.gameApp.wjDetailsViewData.viewItem.viewWuQi = true;
			this.gameApp.wjDetailsViewData.canViewShouPai = true;

			this.gameApp.wjDetailsViewData.selectedWJ = srcWJ;

			WuJiangCardPaiData wjCPData = new WuJiangCardPaiData();
			wjCPData.zhuangBei.fangJu = srcWJ.zhuangBei.fangJu;
			wjCPData.zhuangBei.jianYiMa = srcWJ.zhuangBei.jianYiMa;
			wjCPData.zhuangBei.jiaYiMa = srcWJ.zhuangBei.jiaYiMa;
			wjCPData.shouPai = srcWJ.shouPai;

			SelectCardPaiFromWJDialog dlg2 = new SelectCardPaiFromWJDialog(
					this.gameApp.gameActivityContext, this.gameApp, wjCPData);

			dlg2.showDialog();

			CardPai cp1 = this.gameApp.wjDetailsViewData.selectedCardPai1;
			CardPai cp2 = this.gameApp.wjDetailsViewData.selectedCardPai2;

			if (cp1 != null && cp2 != null) {
				UpdateWJViewData item = new UpdateWJViewData();
				item.updateAll = true;
				if (cp1.cpState == Type.CPState.pandDingPai) {
					srcWJ.panDingPai.remove(cp1);
				} else if (cp1.cpState == Type.CPState.ShouPai) {
					srcWJ.detatchCardPaiFromShouPai(cp1);
				} else if (cp1.cpState == Type.CPState.wuQiPai
						|| cp1.cpState == Type.CPState.fangJuPai
						|| cp1.cpState == Type.CPState.jiaYiMaPai
						|| cp1.cpState == Type.CPState.jianYiMaPai) {
					srcWJ.unstallZhuangBei((ZhuangBeiCardPai) cp1);
				}

				if (cp2.cpState == Type.CPState.pandDingPai) {
					srcWJ.panDingPai.remove(cp2);
				} else if (cp2.cpState == Type.CPState.ShouPai) {
					srcWJ.detatchCardPaiFromShouPai(cp2);
				} else if (cp2.cpState == Type.CPState.wuQiPai
						|| cp2.cpState == Type.CPState.fangJuPai
						|| cp2.cpState == Type.CPState.jiaYiMaPai
						|| cp2.cpState == Type.CPState.jianYiMaPai) {
					srcWJ.unstallZhuangBei((ZhuangBeiCardPai) cp2);
				}

				cp1.belongToWuJiang = null;
				cp2.belongToWuJiang = null;

				srcCP.countTotalShangHaiN(tarWJ);
				this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "弃2张手牌"
						+ cp1 + " " + cp2, Type.logDelay.NoDelay);
				tarWJ.increaseBlood(srcWJ, srcCP);
			}
		}
	}
}
