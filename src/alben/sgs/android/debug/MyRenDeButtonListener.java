package alben.sgs.android.debug;

import alben.sgs.android.GameApp;
import alben.sgs.android.R;
import alben.sgs.android.dialog.SelectCardPaiFromWJDialog;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.FangJuCardPai;
import alben.sgs.cardpai.MaCardPai;
import alben.sgs.cardpai.WuQiCardPai;
import alben.sgs.cardpai.ZhuangBeiCardPai;
import alben.sgs.type.Type;
import alben.sgs.type.UpdateWJViewData;
import alben.sgs.type.WuJiangCardPaiData;
import alben.sgs.wujiang.WuJiang;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;

public class MyRenDeButtonListener implements OnClickListener {
	public GameApp gameApp;

	public MyRenDeButtonListener(GameApp app) {
		this.gameApp = app;
	}

	public void onClick(View v) {

		if (gameApp.gameLogicData.myWuJiang == null)
			return;

		if (gameApp.gameLogicData.myWuJiang.shouPai.size() == 0)
			return;

		// send some card pai to somebody
		// first select shou pai
		gameApp.wjDetailsViewData.reset();
		gameApp.wjDetailsViewData.selectedCardNumber = 2;
		gameApp.wjDetailsViewData.selectedCardN1Or2 = true;
		gameApp.wjDetailsViewData.canViewShouPai = true;

		gameApp.wjDetailsViewData.selectedWJ = gameApp.gameLogicData.myWuJiang;

		WuJiangCardPaiData wjCPData = new WuJiangCardPaiData();
		wjCPData.shouPai = gameApp.gameLogicData.myWuJiang.shouPai;

		SelectCardPaiFromWJDialog dlg2 = new SelectCardPaiFromWJDialog(
				gameApp.gameActivityContext, gameApp, wjCPData);
		dlg2.showDialog();

		CardPai cp1 = gameApp.wjDetailsViewData.selectedCardPai1;
		CardPai cp2 = gameApp.wjDetailsViewData.selectedCardPai2;

		// then select wj
		gameApp.selectWJViewData.reset();
		gameApp.selectWJViewData.selectNumber = 1;
		// show wujiang can be selected
		WuJiang tarWJ = gameApp.gameLogicData.myWuJiang.nextOne;
		while (!tarWJ.equals(gameApp.gameLogicData.myWuJiang)) {
			gameApp.libGameViewData.imageWJs[tarWJ.imageViewIndex]
					.setBackgroundDrawable(gameApp.getResources().getDrawable(
							R.drawable.bg_green));
			tarWJ.canSelect = true;
			tarWJ.clicked = false;
			tarWJ = tarWJ.nextOne;
		}
		// use UI for interaction
		gameApp.gameLogicData.userInterface.askUserSelectWuJiang(
				gameApp.gameLogicData.myWuJiang, "请选择"
						+ gameApp.selectWJViewData.selectNumber + "个武将");

		WuJiang selectedWJ = gameApp.selectWJViewData.selectedWJ1;

		if (selectedWJ != null) {
			String info = "";
			if (cp1 != null) {
				info += cp1;
				gameApp.gameLogicData.myWuJiang.shouPai.remove(cp1);
				cp1.belongToWuJiang = selectedWJ;
				if (cp1 instanceof WuQiCardPai) {
					if (selectedWJ.zhuangBei.wuQi != null) {
						selectedWJ.unstallZhuangBei((ZhuangBeiCardPai) cp1);
					}
					selectedWJ.installWuQi((WuQiCardPai) cp1);
				} else if (cp1 instanceof FangJuCardPai) {
					if (selectedWJ.zhuangBei.fangJu != null) {
						selectedWJ.unstallZhuangBei((ZhuangBeiCardPai) cp1);
					}
					selectedWJ.installFangJu((FangJuCardPai) cp1);
				} else if (cp1 instanceof MaCardPai) {
					MaCardPai ma = (MaCardPai) cp1;
					if (ma.distance == +1) {
						if (selectedWJ.zhuangBei.jiaYiMa != null) {
							selectedWJ.unstallZhuangBei((ZhuangBeiCardPai) cp1);
						}
						selectedWJ.installJiaYiMa((MaCardPai) cp1);
					} else if (ma.distance == -1) {
						if (selectedWJ.zhuangBei.jianYiMa != null) {
							selectedWJ.unstallZhuangBei((ZhuangBeiCardPai) cp1);
						}
						selectedWJ.installJianYiMa((MaCardPai) cp1);
					}
				} else {
					cp1.cpState = Type.CPState.ShouPai;
					cp1.belongToWuJiang = selectedWJ;
					cp1.gameApp = this.gameApp;
					selectedWJ.shouPai.add(cp1);
				}
			}

			if (cp2 != null) {
				info += cp2;
				gameApp.gameLogicData.myWuJiang.shouPai.remove(cp2);
				cp2.belongToWuJiang = selectedWJ;
				if (cp2 instanceof WuQiCardPai) {
					if (selectedWJ.zhuangBei.wuQi != null) {
						selectedWJ.unstallZhuangBei((ZhuangBeiCardPai) cp2);
					}
					selectedWJ.installWuQi((WuQiCardPai) cp2);
				} else if (cp2 instanceof FangJuCardPai) {
					if (selectedWJ.zhuangBei.fangJu != null) {
						selectedWJ.unstallZhuangBei((ZhuangBeiCardPai) cp2);
					}
					selectedWJ.installFangJu((FangJuCardPai) cp2);
				} else if (cp2 instanceof MaCardPai) {
					MaCardPai ma = (MaCardPai) cp2;
					if (ma.distance == +1) {
						if (selectedWJ.zhuangBei.jiaYiMa != null) {
							selectedWJ.unstallZhuangBei((ZhuangBeiCardPai) cp2);
						}
						selectedWJ.installJiaYiMa((MaCardPai) cp2);
					} else if (ma.distance == -1) {
						if (selectedWJ.zhuangBei.jianYiMa != null) {
							selectedWJ.unstallZhuangBei((ZhuangBeiCardPai) cp2);
						}
						selectedWJ.installJianYiMa((MaCardPai) cp2);
					}
				} else {
					cp2.cpState = Type.CPState.ShouPai;
					cp2.belongToWuJiang = selectedWJ;
					cp2.gameApp = this.gameApp;
					selectedWJ.shouPai.add(cp2);
				}
			}

			gameApp.gameLogicData.wjHelper.updateWJ8ShouPaiToLibGameView();

			UpdateWJViewData item = new UpdateWJViewData();
			item.updateAll = true;
			gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(
					selectedWJ, item);

			gameApp.libGameViewData.logInfo("你送给" + selectedWJ.dispName
					+ "的牌为:" + info , Type.logDelay.Delay);

			// wakeup UI to continue process
			{
				gameApp.gameLogicData.userInterface.loop = true;
				Message m = gameApp.gameLogicData.userInterface.mHandler
						.obtainMessage();
				m.what = 1;
				gameApp.gameLogicData.userInterface.mHandler.sendMessage(m);
			}
		}
	}
}
