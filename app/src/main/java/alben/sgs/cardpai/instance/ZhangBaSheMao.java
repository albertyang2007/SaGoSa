package alben.sgs.cardpai.instance;

import alben.sgs.android.R;
import alben.sgs.android.dialog.SelectCardPaiFromWJDialog;
import alben.sgs.android.dialog.YesNoDialog;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.WuQiCardPai;
import alben.sgs.type.Type;
import alben.sgs.type.WuJiangCardPaiData;

public class ZhangBaSheMao extends WuQiCardPai {
	public ZhangBaSheMao(Type.CardPai na, Type.CardPaiClass c, int n,
			int imgNumber, int d) {
		super(na, c, n, imgNumber, d);
		this.dispName = "丈八蛇矛";
		this.zbImgNumber = R.drawable.zb_zhangbashemao;
	}

	public void listenWJ8ClickEvent() {
		if (this.belongToWuJiang == null) {
			return;
		}

		this.gameApp.ynData.reset();
		this.gameApp.ynData.okTxt = "确认";
		this.gameApp.ynData.cancelTxt = "取消";
		this.gameApp.ynData.genInfo = "是否使用丈八蛇矛弃2张手牌出杀?";

		YesNoDialog dlg = new YesNoDialog(this.gameApp.gameActivityContext,
				this.gameApp);
		dlg.showDialog();
		if (!this.gameApp.ynData.result)
			return;

		// then chu pai
		this.gameApp.wjDetailsViewData.reset();
		this.gameApp.wjDetailsViewData.selectedCardNumber = 2;
		this.gameApp.wjDetailsViewData.canViewShouPai = true;

		this.gameApp.wjDetailsViewData.selectedWJ = this.belongToWuJiang;

		WuJiangCardPaiData wjCPData = new WuJiangCardPaiData();
		wjCPData.shouPai = this.belongToWuJiang.shouPai;

		SelectCardPaiFromWJDialog dlg2 = new SelectCardPaiFromWJDialog(
				this.gameApp.gameActivityContext, this.gameApp, wjCPData);

		dlg2.showDialog();

		CardPai cp1 = this.gameApp.wjDetailsViewData.selectedCardPai1;
		CardPai cp2 = this.gameApp.wjDetailsViewData.selectedCardPai2;

		if (cp1 == null || cp2 == null)
			return;

		ZBSMSha zbSha = new ZBSMSha(cp1, cp2);
		zbSha.selectedByClick = true;

		// reset myWuJiang shoupai to unselect card pai
		this.belongToWuJiang.resetShouPaiSelectedBoolean();
		// then add zbSha into shoupai list
		this.belongToWuJiang.shouPai.add(zbSha);

		// then select tarWJ
		if (gameApp.gameLogicData.askForPai == Type.CardPai.notNil) {
			// zhu dong chu pai
			zbSha.onClickUpdateView();
		} else if (gameApp.gameLogicData.askForPai == Type.CardPai.Sha) {
			// to response juedou, nanmanruqin,jiedaosharen
			this.gameApp.gameLogicData.userInterface.sendMessageToUIForWakeUp();
		}
	}
}
