package alben.sgs.android.dialog;

import alben.sgs.android.GameApp;
import alben.sgs.android.R;
import alben.sgs.type.Type;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class QGameSetUpDialog extends Dialog {
	public GameApp gameApp;
	public Object returnValue;

	public QGameSetUpDialog(Context paramContext, GameApp paramGameApp) {
		super(paramContext);
		this.gameApp = paramGameApp;
		setOwnerActivity((Activity) paramGameApp.gameActivityContext);
		onCreate();
	}

	public void endDialog(int paramInt) {
		dismiss();
		Looper.getMainLooper().quit();
	}

	public void onCreate() {
		setContentView(R.layout.qgame_setup_menu);

		((ImageView) findViewById(R.id.btn_qgame_setup_wj))
				.setOnTouchListener(new QGameSetUpListener());
		((ImageView) findViewById(R.id.btn_qgame_setup_role))
				.setOnTouchListener(new QGameSetUpListener());
		((ImageView) findViewById(R.id.btn_qgame_setup_zb))
				.setOnTouchListener(new QGameSetUpListener());
		((ImageView) findViewById(R.id.btn_qgame_setup_sp))
				.setOnTouchListener(new QGameSetUpListener());
		((ImageView) findViewById(R.id.btn_qgame_setup_blood))
				.setOnTouchListener(new QGameSetUpListener());
		((ImageView) findViewById(R.id.btn_qgame_setup_pd))
				.setOnTouchListener(new QGameSetUpListener());
		((ImageView) findViewById(R.id.btn_qgame_setup_link))
				.setOnTouchListener(new QGameSetUpListener());
		((ImageView) findViewById(R.id.btn_qgame_setup_paidui))
				.setOnTouchListener(new QGameSetUpListener());
		((ImageView) findViewById(R.id.btn_qgame_setup_sys))
				.setOnTouchListener(new QGameSetUpListener());
	}

	public Object showDialog() {
		super.show();
		try {
			MainLoopHelper.looperRun();
		} catch (RuntimeException e2) {
		}
		return returnValue;
	}

	private class QGameSetUpListener implements View.OnTouchListener {

		public boolean onTouch(View v, MotionEvent event) {
			gameApp.settingsViewData.qGameSetupSetp = Type.QGameSetupStep.Nil;
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				break;
			case MotionEvent.ACTION_UP:
				switch (v.getId()) {
				case R.id.btn_qgame_setup_wj:
					gameApp.settingsViewData.qGameSetupSetp = Type.QGameSetupStep.WuJiang;
					break;
				case R.id.btn_qgame_setup_role:
					gameApp.settingsViewData.qGameSetupSetp = Type.QGameSetupStep.Role;
					break;
				case R.id.btn_qgame_setup_zb:
					gameApp.settingsViewData.qGameSetupSetp = Type.QGameSetupStep.ZhuangBei;
					break;
				case R.id.btn_qgame_setup_sp:
					gameApp.settingsViewData.qGameSetupSetp = Type.QGameSetupStep.ShouPai;
					break;
				case R.id.btn_qgame_setup_blood:
					gameApp.settingsViewData.qGameSetupSetp = Type.QGameSetupStep.Blood;
					break;
				case R.id.btn_qgame_setup_pd:
					gameApp.settingsViewData.qGameSetupSetp = Type.QGameSetupStep.PanDing;
					break;
				case R.id.btn_qgame_setup_link:
					gameApp.settingsViewData.qGameSetupSetp = Type.QGameSetupStep.Link;
					break;
				case R.id.btn_qgame_setup_paidui:
					gameApp.settingsViewData.qGameSetupSetp = Type.QGameSetupStep.PaiDui;
					break;
				case R.id.btn_qgame_setup_sys:
					gameApp.settingsViewData.qGameSetupSetp = Type.QGameSetupStep.System;
				}
				endDialog(1);
			}
			return true;
		}
	}
}