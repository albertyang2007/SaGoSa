package alben.sgs.android.mycontroller;

import alben.sgs.android.GameApp;
import alben.sgs.android.R;
import alben.sgs.cardpai.CardPai;
import alben.sgs.type.Type;
import alben.sgs.wujiang.WuJiang;
import android.view.View;
import android.view.View.OnClickListener;

public class MyWuJiangImageListener implements OnClickListener {
	public GameApp gameApp;

	public MyWuJiangImageListener(GameApp app) {
		this.gameApp = app;
	}

	public void onClick(View v) {
		int index = 0;
		switch (v.getId()) {
		case R.id.WuJiang1: {
			index = 0;
			break;
		}
		case R.id.WuJiang2: {
			index = 1;
			break;
		}
		case R.id.WuJiang3: {
			index = 2;
			break;
		}
		case R.id.WuJiang4: {
			index = 3;
			break;
		}
		case R.id.WuJiang5: {
			index = 4;
			break;
		}
		case R.id.WuJiang6: {
			index = 5;
			break;
		}
		case R.id.WuJiang7: {
			index = 6;
			break;
		}
		case R.id.WuJiang8: {
			index = 7;
			break;
		}
		}

		if (gameApp.selectWJViewData.selectNumber == 0) {
			// the clicked caid pai does not need one wj
			return;
		}

		// if click this wu jiang, check if it is for select for chu pai
		WuJiang curClickWJ = gameApp.gameLogicData.wjHelper
				.getWuJiangByImageViewIndex(index);

		if (curClickWJ == null)
			return;

		if (!gameApp.gameLogicData.selectWJAfterChuPai) {
			// click one shou pai and show what this cp can do

			int selectedCPNumber = gameApp.gameLogicData.myWuJiang
					.countSelectedShouPai();
			if (selectedCPNumber != 1) {
				// zero or two more card pai is clicked
				return;
			}

			CardPai curClickCP = gameApp.gameLogicData.myWuJiang
					.getSelectedShouPai();

			if (curClickWJ.canSelect) {
				// update wj view
				if (!curClickWJ.clicked) {
					curClickWJ.clicked = true;
					gameApp.libGameViewData.imageWJs[index]
							.setBackgroundDrawable(this.gameApp.getResources()
									.getDrawable(R.drawable.bg_blue));
					gameApp.libGameViewData.logInfo("你选择了"
							+ curClickWJ.dispName, Type.logDelay.NoDelay);

					if (curClickCP != null) {
						curClickCP.onClickWJUpdateView(curClickWJ);
					}
				} else {
					// un-click it
					curClickWJ.clicked = false;
					gameApp.libGameViewData.imageWJs[index]
							.setBackgroundDrawable(this.gameApp.getResources()
									.getDrawable(R.drawable.bg_green));
					gameApp.libGameViewData.logInfo("你反选了"
							+ curClickWJ.dispName, Type.logDelay.NoDelay);

					if (curClickCP != null) {
						curClickCP.onUnClickWJUpdateView(curClickWJ);
					}
				}
			}
		} else {
			// one shou pai had been chu, post select wj
			// like: tiesuolianhuan
			if (!curClickWJ.clicked) {

				gameApp.libGameViewData.logInfo("你选择了" + curClickWJ.dispName,
						Type.logDelay.NoDelay);

				// set selected to green
				gameApp.libGameViewData.imageWJs[index]
						.setBackgroundDrawable(gameApp.getResources()
								.getDrawable(R.drawable.bg_blue));
				curClickWJ.clicked = true;
				// set value
				if (gameApp.selectWJViewData.selectNumber == 1) {
					gameApp.selectWJViewData.selectedWJ1 = curClickWJ;
				}
				if (gameApp.selectWJViewData.selectNumber == 2) {
					if (gameApp.selectWJViewData.selectedWJ1 == null)
						gameApp.selectWJViewData.selectedWJ1 = curClickWJ;
					else if (gameApp.selectWJViewData.selectedWJ2 == null)
						gameApp.selectWJViewData.selectedWJ2 = curClickWJ;
				}
				if (gameApp.selectWJViewData.selectedWJAtLeast1) {
					if (!gameApp.selectWJViewData.selectedWJs
							.contains(curClickWJ))
						gameApp.selectWJViewData.selectedWJs.add(curClickWJ);
				}
			} else {
				// unclick it
				gameApp.libGameViewData.logInfo("你反选了" + curClickWJ.dispName,
						Type.logDelay.NoDelay);

				curClickWJ.clicked = false;
				gameApp.libGameViewData.imageWJs[index]
						.setBackgroundDrawable(gameApp.getResources()
								.getDrawable(R.drawable.bg_green));
				// de-select if had been selected
				if (gameApp.selectWJViewData.selectedWJ1 != null
						&& gameApp.selectWJViewData.selectedWJ1
								.equals(curClickWJ)) {
					gameApp.selectWJViewData.selectedWJ1 = null;
				}
				if (gameApp.selectWJViewData.selectedWJ2 != null
						&& gameApp.selectWJViewData.selectedWJ2
								.equals(curClickWJ)) {
					gameApp.selectWJViewData.selectedWJ2 = null;
				}
				if (gameApp.selectWJViewData.selectedWJAtLeast1) {
					if (gameApp.selectWJViewData.selectedWJs
							.contains(curClickWJ))
						gameApp.selectWJViewData.selectedWJs.remove(curClickWJ);
				}
			}
		}
	}
}
