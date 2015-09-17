package alben.sgs.wujiang.instance;

import alben.sgs.android.R;
import alben.sgs.android.dialog.SelectCardPaiFromWJDialog;
import alben.sgs.android.dialog.YesNoDialog;
import alben.sgs.cardpai.CardPai;
import alben.sgs.common.Helper;
import alben.sgs.event.ChuPaiEvent;
import alben.sgs.event.TaoEvent;
import alben.sgs.type.Type;
import alben.sgs.type.UpdateWJViewData;
import alben.sgs.type.WuJiangCardPaiData;
import alben.sgs.wujiang.WuJiang;
import alben.sgs.wujiang.instance.jineng.LianHuan;
import android.view.View;

public class PengTong extends WuJiang {
	public PengTong(Type.WuJiang n, Type.Country c, Type.Sex s, int b) {
		super(n, c, s, b);
		this.imageNumber = alben.sgs.android.R.drawable.wj_pantong;
		this.jiNengDesc = "(火扩展包武将)\n"
				+ "1、连环：出牌阶段，你可以将你任意一张梅花手牌当【铁索连环】使用或重铸。\n"
				+ "2、涅：限定技，当你处于濒死状态时，你可以丢弃你所有的牌和你判定区里的牌，并重置你的武将牌，然后摸三张牌且体力回复至3点。";
		this.dispName = "庞统";
		this.jiNengN1 = "连环";
		this.jiNengN2 = "涅";
	}

	public void listenEnterHuiHeEvent() {
		// save original vaue and do not change
		boolean lastValue = this.oneTimeJiNengTrigger;
		super.listenEnterHuiHeEvent();
		// restore it
		this.oneTimeJiNengTrigger = lastValue;
		this.enableWuJiangJiNengBtn();
	}

	public void enableWuJiangJiNengBtn() {
		if (!this.tuoGuan) {
			this.gameApp.libGameViewData.mJiNengBtn1
					.setVisibility(View.VISIBLE);
			this.gameApp.libGameViewData.mJiNengBtn1.setEnabled(true);
			this.gameApp.libGameViewData.mJiNengBtn1Txt.setText(this.jiNengN1);

			this.gameApp.libGameViewData.mJiNengBtn2
					.setVisibility(View.VISIBLE);
			this.gameApp.libGameViewData.mJiNengBtn2.setEnabled(false);
			this.gameApp.libGameViewData.mJiNengBtn2Txt.setText(this.jiNengN2);
		}
		// invoke super to set the correct JiNengBtnImage
		super.enableWuJiangJiNengBtn();
	}

	public void listenExitHuiHeEvent() {
		// save original vaue and do not change
		boolean lastValue = this.oneTimeJiNengTrigger;
		super.listenExitHuiHeEvent();
		// restore it
		this.oneTimeJiNengTrigger = lastValue;
	}

	// For AI: Add LiJian cardpai into shoupai
	public CardPai generateJiNengCardPai() {
		if (!this.tuoGuan)
			return null;

		CardPai mhCP = this.selectFromShouPaiByClass(Type.CardPaiClass.MeiHua);
		if (mhCP == null)
			return null;

		LianHuan lianHuan = new LianHuan(mhCP.name, mhCP.clas, mhCP.number,
				Type.JinNangApplyTo.all);
		lianHuan.gameApp = this.gameApp;
		lianHuan.belongToWuJiang = this;
		lianHuan.sp1 = mhCP;

		return lianHuan;
	}

	public void handleJiNengBtnEvent(int eventID) {

		switch (eventID) {
		case R.id.JiNeng1: {
			if (this.state != Type.State.ChuPai)
				return;

			CardPai mhCP = this
					.selectFromShouPaiByClass(Type.CardPaiClass.MeiHua);
			if (mhCP == null) {
				this.gameApp.libGameViewData.logInfo(
						"没有梅花不能发动" + this.jiNengN1, Type.logDelay.NoDelay);
				return;
			}

			this.gameApp.ynData.reset();
			this.gameApp.ynData.okTxt = "确认";
			this.gameApp.ynData.cancelTxt = "取消";
			this.gameApp.ynData.genInfo = "是否使用" + this.jiNengN1 + "?";

			YesNoDialog dlg = new YesNoDialog(this.gameApp.gameActivityContext,
					this.gameApp);
			dlg.showDialog();
			if (!this.gameApp.ynData.result)
				return;

			// first select one shoupai
			this.gameApp.wjDetailsViewData.reset();
			this.gameApp.wjDetailsViewData.selectedCardNumber = 1;
			this.gameApp.wjDetailsViewData.canViewShouPai = true;

			this.gameApp.wjDetailsViewData.selectedWJ = this;

			WuJiangCardPaiData wjCPData = new WuJiangCardPaiData();
			for (int i = 0; i < this.shouPai.size(); i++) {
				if (this.shouPai.get(i).clas == Type.CardPaiClass.MeiHua)
					wjCPData.shouPai.add(this.shouPai.get(i));
			}

			SelectCardPaiFromWJDialog dlg2 = new SelectCardPaiFromWJDialog(
					this.gameApp.gameActivityContext, this.gameApp, wjCPData);
			dlg2.showDialog();

			mhCP = this.gameApp.wjDetailsViewData.selectedCardPai1;

			if (mhCP == null)
				return;

			LianHuan lianHuan = new LianHuan(mhCP.name, mhCP.clas, mhCP.number,
					Type.JinNangApplyTo.all);
			lianHuan.gameApp = this.gameApp;
			lianHuan.belongToWuJiang = this;
			lianHuan.sp1 = mhCP;

			lianHuan.selectedByClick = true;

			// reset myWuJiang shoupai to unselect card pai
			this.resetShouPaiSelectedBoolean();
			// then add lianHuan into shoupai list
			this.shouPai.add(lianHuan);

			if (gameApp.gameLogicData.askForPai == Type.CardPai.notNil) {
				this.gameApp.gameLogicData.userInterface
						.sendMessageToUIForWakeUp();
			}
			break;
		}
		}
	}

	public void askForTaoEvent(ChuPaiEvent cpEvent) {
		if (this.equals(cpEvent.srcWuJiang)) {
			// I am deading, ask myself for Tao
			if (this.panNie(cpEvent)) {
				return;
			}
		}
		super.askForTaoEvent(cpEvent);
	}

	// PanNie
	public boolean panNie(ChuPaiEvent cpEvent) {

		if (this.oneTimeJiNengTrigger)
			return false;

		if (this.tuoGuan) {
			TaoEvent taoEvent = (TaoEvent) cpEvent;
			if ((taoEvent.yiChuTaoNumber + this.taoNumberInShouPai() + this.blood) >= 1) {
				return false;
			}
		} else {
			this.gameApp.ynData.reset();
			this.gameApp.ynData.okTxt = "确认";
			this.gameApp.ynData.cancelTxt = "取消";
			this.gameApp.ynData.genInfo = "是否发动" + this.jiNengN2 + "?";

			YesNoDialog dlg = new YesNoDialog(this.gameApp.gameActivityContext,
					this.gameApp);
			dlg.showDialog();
			if (!this.gameApp.ynData.result)
				return false;
		}

		// remove all card pai, zhuangbei
		Helper.emptyArrayList(this.panDingPai);
		Helper.emptyArrayList(this.shouPai);
		this.zhuangBei.reset();
		if (this.lianHuan) {
			this.lianHuan = false;
		}

		// set yiChuTaoNumber to value
		this.blood = 3;
		TaoEvent taoEvent = (TaoEvent) cpEvent;
		taoEvent.yiChuTaoNumber = 3 - this.blood;
		this.gameApp.gameLogicData.cpHelper.addCardPaiToWuJiang(this, 3);

		UpdateWJViewData item = new UpdateWJViewData();
		item.updateAll = true;
		this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(this,
				item);
		if (!this.tuoGuan) {
			this.gameApp.gameLogicData.wjHelper.updateWJ8ShouPaiToLibGameView();
		}

		this.gameApp.libGameViewData.logInfo(this.dispName + "["
				+ this.jiNengN2 + "],摸3张牌体力回复至3点", Type.logDelay.Delay);

		this.oneTimeJiNengTrigger = true;

		return true;
	}

	public int taoNumberInShouPai() {
		int taoN = 0;
		for (int i = 0; i < this.shouPai.size(); i++) {
			if (this.shouPai.get(i).name == Type.CardPai.Tao
					|| this.shouPai.get(i).name == Type.CardPai.Jiu) {
				taoN++;
			}
		}
		return taoN;
	}
}
