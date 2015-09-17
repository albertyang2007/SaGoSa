package alben.sgs.android.mycontroller;

import alben.sgs.android.GameActivity;
import alben.sgs.android.GameApp;
import alben.sgs.android.QGameListActivity;
import alben.sgs.android.R;
import alben.sgs.android.debug.DebugDialog;
import alben.sgs.android.io.ExceptionTraceHelper;
import alben.sgs.qgame.QGameXMLHelper;
import alben.sgs.type.Type;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;

public class QGameBtnController implements View.OnTouchListener {
	public GameApp gameApp;

	public QGameBtnController(GameApp paramGameApp) {
		this.gameApp = paramGameApp;
	}

	public void handleOpenQGameBtnEvent(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			v.setBackgroundDrawable(this.gameApp.getResources().getDrawable(
					R.drawable.btn_bg1_down));
			break;
		case MotionEvent.ACTION_UP:
			v.setBackgroundDrawable(this.gameApp.getResources().getDrawable(
					R.drawable.btn_bg1));
			Intent localIntent = new Intent(this.gameApp,
					QGameListActivity.class);
			localIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			this.gameApp.startActivity(localIntent);

			break;
		}
	}

	public void handleRunQGameBtnEvent(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			v.setBackgroundDrawable(this.gameApp.getResources().getDrawable(
					R.drawable.btn_bg1_down));
			break;
		case MotionEvent.ACTION_UP:
			v.setBackgroundDrawable(this.gameApp.getResources().getDrawable(
					R.drawable.btn_bg1));
			// first check debug exception
			if (this.gameApp.gameLogicData.exceptionTrack.trim().length() > 0) {
				new DebugDialog(this.gameApp.gameActivityContext, this.gameApp);
				this.gameApp.gameLogicData.exceptionTrack = "";
				return;
			}

			// santiy check
			if (this.gameApp.gameLogicData.wjHelper.qgameSanityCheck()) {
				this.gameApp.runFromQGame = true;
				Intent localIntent = new Intent(this.gameApp,
						GameActivity.class);
				this.gameApp.startActivity(localIntent);
			}

			break;
		}
	}

	public void handleSaveQGameBtnEvent(View v, MotionEvent event) {
		switch (event.getAction()) {
		default:
		case MotionEvent.ACTION_DOWN:
			v.setBackgroundDrawable(this.gameApp.getResources().getDrawable(
					R.drawable.btn_bg1_down));
			break;
		case MotionEvent.ACTION_UP:
			v.setBackgroundDrawable(this.gameApp.getResources().getDrawable(
					R.drawable.btn_bg1));
			try {
				QGameXMLHelper localQGameXMLHelper = new QGameXMLHelper(
						this.gameApp);
				localQGameXMLHelper.writeFileToSD("test_qgame.xml",
						localQGameXMLHelper.generateGameWJToXMLCtx());
			} catch (Exception e) {
				this.gameApp.libGameViewData.logInfo("Òì³£ÍË³ö:" + e,
						Type.logDelay.Delay);
				this.gameApp.libGameViewData.fileUtil
						.addContent(ExceptionTraceHelper.getTrace(e));
				this.gameApp.gameLogicData.exceptionTrack = ExceptionTraceHelper
						.getTrace(e);
			}
			break;
		}
	}

	public boolean onTouch(View v, MotionEvent event) {
		switch (v.getId()) {
		case R.id.ChuPai:
			handleSaveQGameBtnEvent(v, event);
			break;
		case R.id.FangQi:
			handleOpenQGameBtnEvent(v, event);
			break;
		case R.id.QiPai:
			handleRunQGameBtnEvent(v, event);
		}
		return false;
	}
}