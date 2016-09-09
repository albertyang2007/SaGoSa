package alben.sgs.cardpai.instance;

import alben.sgs.android.dialog.WuGuFengDengDialog;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.JinNangCardPai;
import alben.sgs.type.Type;
import alben.sgs.type.Type.JinNangApplyTo;
import alben.sgs.type.UpdateWJViewData;
import alben.sgs.wujiang.WuJiang;

public class WuGuFengDeng extends JinNangCardPai {
	public WuGuFengDeng(Type.CardPai na, Type.CardPaiClass c, int n,
			int imgNumber, JinNangApplyTo at) {
		super(na, c, n, imgNumber, at);
		this.dispName = "五谷丰登";
		this.eventImpactN = 1;
	}

	public void initCardPaiForSelect() {
		int aliveWJ = this.gameApp.gameLogicData.wjHelper.countAliveWuJiang();
		this.gameApp.selectCPData.reset();
		for (int i = 0; i < aliveWJ; i++) {
			this.gameApp.selectCPData.CPForSelect[i] = this.gameApp.gameLogicData.cpHelper
					.popCardPai();
			this.gameApp.selectCPData.CPForSelect[i].belongToWuJiang = null;
		}
	}

	public CardPai selectOneCPFromWGFD() {
		CardPai rtn = null;

		// select the first non allocated one
		for (int i = 0; i < this.gameApp.selectCPData.CPForSelect.length; i++) {
			CardPai cp = this.gameApp.selectCPData.CPForSelect[i];
			if (cp != null && cp.belongToWuJiang == null) {
				rtn = cp;
				break;
			}
		}

		if (rtn == null) {
			this.gameApp.libGameViewData.logInfo("Error: select CP is null",
					Type.logDelay.NoDelay);
		}
		return rtn;
	}

	public boolean work(WuJiang srcWJ, WuJiang tarWJ1, CardPai tarCP) {

		this.initCardPaiForSelect();

		UpdateWJViewData item = new UpdateWJViewData();

		this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "使用" + this,
				Type.logDelay.Delay);

		this.listenPreWorkEvent(srcWJ, tarWJ1, tarCP);

		WuJiang tarWJ = srcWJ;
		boolean run = true;
		while (!tarWJ.equals(srcWJ) || run) {
			if (tarWJ.equals(srcWJ)) {
				run = false;
			}
			if (tarWJ.askForWuXieKeJi(srcWJ, this, tarWJ, this)) {
				this.gameApp.libGameViewData.logInfo(this.dispName + "被无懈了",
						Type.logDelay.NoDelay);
			} else {
				// select one of card pai
				if (tarWJ.tuoGuan) {
					CardPai cp = this.selectOneCPFromWGFD();
					if (cp != null) {
						cp.belongToWuJiang = tarWJ;
						cp.cpState = Type.CPState.ShouPai;
						tarWJ.shouPai.add(cp);

						WuGuFengDengDialog dlg = new WuGuFengDengDialog(
								this.gameApp.gameActivityContext, this.gameApp);
						dlg.showNonBlockDialog();

						this.gameApp.libGameViewData.logInfo(tarWJ.dispName
								+ "选择了" + cp,Type.logDelay.Delay);

						dlg.endNonBlockDialog();
						// update view
						item.updateShouPaiNumber = true;
						this.gameApp.gameLogicData.wjHelper
								.updateWuJiangToLibGameView(tarWJ, item);
					}
				} else {
					// not tuo guan, is myWuJiang
					WuGuFengDengDialog dlg = new WuGuFengDengDialog(
							this.gameApp.gameActivityContext, this.gameApp);
					dlg.showDialog();

					CardPai cp = gameApp.selectCPData.selectedCP1;
					cp.belongToWuJiang = tarWJ;
					cp.cpState = Type.CPState.ShouPai;
					tarWJ.shouPai.add(cp);
					this.gameApp.libGameViewData.logInfo(tarWJ.dispName
							+ "(你)选择了" + cp, Type.logDelay.Delay);
					this.gameApp.gameLogicData.wjHelper
							.updateWJ8ShouPaiToLibGameView();

				}
			}
			// set next one
			tarWJ = tarWJ.nextOne;
		}
		return true;
	}

}
