package alben.sgs.android.mycontroller;

import alben.sgs.android.GameApp;
import alben.sgs.android.R;
import alben.sgs.cardpai.CardPai;
import alben.sgs.type.Type;
import android.view.MotionEvent;
import android.view.View;

//http://dev.10086.cn/cmdn/wiki/index.php?edition-view-5880-1.html
public class MyWJ8ShouPaiImageListener implements View.OnTouchListener {
	public GameApp gameApp;
	public float[] srcPos = { 0.0f, 0.0f };
	public float[] desPos = { 0.0f, 0.0f };
	public static float FLING_MIN_DISTANCE = 10.0f;//

	public MyWJ8ShouPaiImageListener(GameApp app) {
		this.gameApp = app;
	}

	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		default:
		case MotionEvent.ACTION_DOWN: {
			this.srcPos[0] = event.getX();
			this.srcPos[1] = event.getY();
			break;
		}
		case MotionEvent.ACTION_UP: {
			// first screen if user is move or just kick sp
			this.desPos[0] = event.getX();
			this.desPos[1] = event.getY();

			boolean justClick = false;

			if (this.gameApp.gameLogicData.myWuJiang.shouPai.size() <= this.gameApp.libGameViewData.maxShouPaiDisplayForWJ8) {
				// no move or not care, just kick
				// select one shou pai
				justClick = true;
			}

			// if shou pai is more then 8
			if (this.srcPos[0] - this.desPos[0] > FLING_MIN_DISTANCE) {
				// move left
				this.gameApp.gameLogicData.myWuJiang.wj8ShouPaiCurPage++;
			} else if (this.desPos[0] - this.srcPos[0] > FLING_MIN_DISTANCE) {
				// move right
				this.gameApp.gameLogicData.myWuJiang.wj8ShouPaiCurPage--;
			} else {
				// no move or not care, just kick
				// select one shou pai
				justClick = true;
			}

			if (justClick) {
				// click and select on shou pai
				this.onClick(v);
			} else {
				// move shou pai to next or pre page
				int totalPage = (this.gameApp.gameLogicData.myWuJiang.shouPai
						.size() / this.gameApp.libGameViewData.maxShouPaiDisplayForWJ8) + 1;

				if ((this.gameApp.gameLogicData.myWuJiang.shouPai.size() % this.gameApp.libGameViewData.maxShouPaiDisplayForWJ8) == 0)
					totalPage--;

				if (this.gameApp.gameLogicData.myWuJiang.wj8ShouPaiCurPage >= totalPage) {
					this.gameApp.gameLogicData.myWuJiang.wj8ShouPaiCurPage = totalPage - 1;
				} else if (this.gameApp.gameLogicData.myWuJiang.wj8ShouPaiCurPage < 0) {
					this.gameApp.gameLogicData.myWuJiang.wj8ShouPaiCurPage = 0;
				}
				//
				this.gameApp.gameLogicData.wjHelper
						.updateWJ8ShouPaiToLibGameView();
			}

			this.resetPos();

			break;
		}
		case MotionEvent.ACTION_MOVE: {
			// move and slide the shou pai
			break;
		}
		}
		return false;
	}

	public void resetPos() {
		this.srcPos[0] = 0.0f;
		this.srcPos[1] = 0.0f;
		this.desPos[0] = 0.0f;
		this.desPos[1] = 0.0f;

	}

	public void onClick(View v) {
		int shouPaiIndex = 0;
		switch (v.getId()) {
		case R.id.wj8_shouPai1: {
			shouPaiIndex = 0;
			break;
		}
		case R.id.wj8_shouPai2: {
			shouPaiIndex = 1;
			break;
		}
		case R.id.wj8_shouPai3: {
			shouPaiIndex = 2;
			break;
		}
		case R.id.wj8_shouPai4: {
			shouPaiIndex = 3;
			break;
		}
		case R.id.wj8_shouPai5: {
			shouPaiIndex = 4;
			break;
		}
		case R.id.wj8_shouPai6: {
			shouPaiIndex = 5;
			break;
		}
		case R.id.wj8_shouPai7: {
			shouPaiIndex = 6;
			break;
		}
		case R.id.wj8_shouPai8: {
			shouPaiIndex = 7;
			break;
		}
			// case R.id.wj8_shouPai9: {
			// shouPaiIndex = 8;
			// break;
			// }
		}

		// if only select wj after chu pai, disable shou pai show funciton
		if (gameApp.gameLogicData.selectWJAfterChuPai)
			return;

		// get current click cp
		CardPai clickedCp = gameApp.gameLogicData.myWuJiang.shouPai
				.get(shouPaiIndex);

		if (clickedCp == null) {
			gameApp.libGameViewData.logInfo("Error: ÄãÑ¡ÔñµÄ¿¨ÅÆÎª¿Õ",
					Type.logDelay.NoDelay);
			return;
		}

		// set top arrow visibility or invisibility
		int visibility = gameApp.libGameViewData.WJ8ShouPaiArrow[shouPaiIndex]
				.getVisibility();
		if (visibility == View.VISIBLE) {
			gameApp.libGameViewData.WJ8ShouPaiArrow[shouPaiIndex]
					.setVisibility(View.INVISIBLE);
			clickedCp.selectedByClick = false;

			// if click this card pai, update the game view
			clickedCp.onUnClickUpdateView();

		} else if (visibility == View.INVISIBLE) {
			gameApp.libGameViewData.WJ8ShouPaiArrow[shouPaiIndex]
					.setVisibility(View.VISIBLE);
			clickedCp.selectedByClick = true;
			// only workaround solution if belongToWuJiang is null
			if (clickedCp.belongToWuJiang == null)
				clickedCp.belongToWuJiang = gameApp.gameLogicData.myWuJiang;

			// if click this card pai, update the game view
			clickedCp.onClickUpdateView();
		}
	}
}
