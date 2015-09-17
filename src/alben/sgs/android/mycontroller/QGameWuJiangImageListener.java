package alben.sgs.android.mycontroller;

import alben.sgs.android.GameApp;
import alben.sgs.android.R;
import alben.sgs.android.dialog.QGameSetUpBloodDialog;
import alben.sgs.android.dialog.QGameSetUpDialog;
import alben.sgs.android.dialog.QGameSetUpPaiDuiDialog;
import alben.sgs.android.dialog.QGameSetUpPanDingDialog;
import alben.sgs.android.dialog.QGameSetUpRoleDialog;
import alben.sgs.android.dialog.QGameSetUpShouPaiDialog;
import alben.sgs.android.dialog.QGameSetUpWuJiangDialog;
import alben.sgs.android.dialog.QGameSetUpZhuangBeiDialig;
import alben.sgs.android.dialog.YesNoDialog;
import alben.sgs.type.Type;
import alben.sgs.type.UpdateWJViewData;
import alben.sgs.wujiang.WuJiang;
import android.view.View;

public class QGameWuJiangImageListener implements View.OnClickListener {
	public GameApp gameApp;

	public QGameWuJiangImageListener(GameApp paramGameApp) {
		this.gameApp = paramGameApp;
	}

	public void onClick(View paramView) {
		int index = 0;
		switch (paramView.getId()) {
		case R.id.WuJiang1:
			index = 0;
			break;
		case R.id.WuJiang2:
			index = 1;
			break;
		case R.id.WuJiang3:
			index = 2;
			break;
		case R.id.WuJiang4:
			index = 3;
			break;
		case R.id.WuJiang5:
			index = 4;
			break;
		case R.id.WuJiang6:
			index = 5;
			break;
		case R.id.WuJiang7:
			index = 6;
			break;
		case R.id.WuJiang8:
			index = 7;
		}
		WuJiang localWuJiang = this.gameApp.gameLogicData.wjHelper
				.getWuJiangByImageViewIndex(index);
		this.gameApp.wjDetailsViewData.selectedWJ = localWuJiang;
		new QGameSetUpDialog(this.gameApp.gameActivityContext, this.gameApp)
				.showDialog();
		UpdateWJViewData item = new UpdateWJViewData();

		if (this.gameApp.settingsViewData.qGameSetupSetp == Type.QGameSetupStep.WuJiang) {
			new QGameSetUpWuJiangDialog(this.gameApp.gameActivityContext,
					this.gameApp).showDialog();
			if (localWuJiang == null) {
				// add it
				localWuJiang = this.gameApp.selectWJViewData.selectedWJ1;
				localWuJiang.reset();
				localWuJiang.imageViewIndex = index;
				localWuJiang.allocated = true;
				this.gameApp.gameLogicData.wuJiangs.add(localWuJiang);
			} else {
				if (!localWuJiang
						.equals(this.gameApp.selectWJViewData.selectedWJ1)) {
					// remove it first
					localWuJiang.reset();
					this.gameApp.gameLogicData.wuJiangs.remove(localWuJiang);

					// add it
					localWuJiang = this.gameApp.selectWJViewData.selectedWJ1;
					localWuJiang.reset();
					localWuJiang.imageViewIndex = index;
					localWuJiang.allocated = true;
					this.gameApp.gameLogicData.wuJiangs.add(localWuJiang);
				}
			}
			//
			if (localWuJiang.imageViewIndex == 7) {
				localWuJiang.tuoGuan = false;
				localWuJiang.state = Type.State.ChuPai;
				this.gameApp.gameLogicData.myWuJiang = localWuJiang;
			} else {
				localWuJiang.tuoGuan = true;
				localWuJiang.state = Type.State.Response;
			}
			//
			item.updateAll = true;
			this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(
					localWuJiang, item);
		} else if (localWuJiang != null
				&& this.gameApp.settingsViewData.qGameSetupSetp == Type.QGameSetupStep.Role) {
			new QGameSetUpRoleDialog(this.gameApp.gameActivityContext,
					this.gameApp).showDialog();
		} else if (localWuJiang != null
				&& this.gameApp.settingsViewData.qGameSetupSetp == Type.QGameSetupStep.Blood) {

			if (localWuJiang.role == Type.Role.Nil) {
				gameApp.libGameViewData.logInfo("请先设置武将身份",
						Type.logDelay.NoDelay);
				return;
			}

			new QGameSetUpBloodDialog(this.gameApp.gameActivityContext,
					this.gameApp).showDialog();
		} else if (localWuJiang != null
				&& this.gameApp.settingsViewData.qGameSetupSetp == Type.QGameSetupStep.ZhuangBei) {
			new QGameSetUpZhuangBeiDialig(this.gameApp.gameActivityContext,
					this.gameApp).showDialog();
		} else if (localWuJiang != null
				&& this.gameApp.settingsViewData.qGameSetupSetp == Type.QGameSetupStep.PanDing) {
			new QGameSetUpPanDingDialog(this.gameApp.gameActivityContext,
					this.gameApp).showDialog();
		} else if (localWuJiang != null
				&& this.gameApp.settingsViewData.qGameSetupSetp == Type.QGameSetupStep.ShouPai) {
			new QGameSetUpShouPaiDialog(this.gameApp.gameActivityContext,
					this.gameApp).showDialog();
		} else if (localWuJiang != null
				&& this.gameApp.settingsViewData.qGameSetupSetp == Type.QGameSetupStep.PaiDui) {
			new QGameSetUpPaiDuiDialog(this.gameApp.gameActivityContext,
					this.gameApp).showDialog();
		} else if (localWuJiang != null
				&& this.gameApp.settingsViewData.qGameSetupSetp == Type.QGameSetupStep.Link) {
			this.gameApp.ynData.reset();
			this.gameApp.ynData.okTxt = "是";
			this.gameApp.ynData.cancelTxt = "否";
			this.gameApp.ynData.genInfo = "是否处于连环横置状态?";
			new YesNoDialog(this.gameApp.gameActivityContext, this.gameApp)
					.showDialog();
			if (!this.gameApp.ynData.result)
				localWuJiang.lianHuan = false;
			else
				localWuJiang.lianHuan = true;
			item.updateLianHuan = true;
			this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(
					localWuJiang, item);
		}
	}
}
