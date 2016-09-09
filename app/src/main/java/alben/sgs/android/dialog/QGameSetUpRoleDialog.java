package alben.sgs.android.dialog;

import alben.sgs.android.GameApp;
import alben.sgs.android.R;
import alben.sgs.android.mycontroller.MyImageButton;
import alben.sgs.type.Type;
import alben.sgs.type.UpdateWJViewData;
import alben.sgs.wujiang.WuJiang;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;

public class QGameSetUpRoleDialog extends Dialog {
	public GameApp gameApp;
	public Object returnValue;
	public WuJiang selectedWJ = null;

	public QGameSetUpRoleDialog(Context paramContext, GameApp paramGameApp) {
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
		setContentView(R.layout.qgame_setup_role);

		this.selectedWJ = this.gameApp.wjDetailsViewData.selectedWJ;
		((MyImageButton) findViewById(R.id.invisible_btn1))
				.setVisibility(View.INVISIBLE);
		((MyImageButton) findViewById(R.id.invisible_btn2))
				.setVisibility(View.INVISIBLE);
		((MyImageButton) findViewById(R.id.invisible_btn3))
				.setVisibility(View.INVISIBLE);

		((RadioButton) findViewById(R.id.qgame_role_zhugong_r))
				.setOnCheckedChangeListener(new QGameSetUpRoleListener());
		((RadioButton) findViewById(R.id.qgame_role_zhongchen_r))
				.setOnCheckedChangeListener(new QGameSetUpRoleListener());
		((RadioButton) findViewById(R.id.qgame_role_neijian_r))
				.setOnCheckedChangeListener(new QGameSetUpRoleListener());
		((RadioButton) findViewById(R.id.qgame_role_fanzei_r))
				.setOnCheckedChangeListener(new QGameSetUpRoleListener());

		((ImageView) findViewById(R.id.settings_ok))
				.setOnTouchListener(new View.OnTouchListener() {
					public boolean onTouch(View v, MotionEvent event) {
						switch (event.getAction()) {
						case MotionEvent.ACTION_DOWN:
							v.setBackgroundDrawable(v.getResources()
									.getDrawable(R.drawable.btn_long_ok));
							break;
						case MotionEvent.ACTION_UP:
							v.setBackgroundDrawable(v.getResources()
									.getDrawable(R.drawable.btn_long_ok));
							//
							if (gameApp.settingsViewData.qGameRole == Type.Role.Nil)
								break;

							UpdateWJViewData item = new UpdateWJViewData();
							// if original role is zhuGong, new role is not
							// zhuGong, then check blood
							if (selectedWJ.role == Type.Role.ZhuGong
									&& gameApp.settingsViewData.qGameRole != Type.Role.ZhuGong) {
								gameApp.gameLogicData.zhuGongWuJiang = null;
								if (selectedWJ.blood > selectedWJ
										.getOrigMaxBlood()) {
									selectedWJ.blood = selectedWJ
											.getOrigMaxBlood();
									item.updateBlood = true;
								}
							}

							// set new role
							selectedWJ.role = gameApp.settingsViewData.qGameRole;
							if (selectedWJ.role == Type.Role.ZhuGong) {
								gameApp.gameLogicData.zhuGongMaxBlood = (1 + selectedWJ
										.getOrigMaxBlood());
								gameApp.gameLogicData.zhuGongWuJiang = selectedWJ;
							}
							//
							item.updateRole = true;
							gameApp.gameLogicData.wjHelper
									.updateWuJiangToLibGameView(selectedWJ,
											item);
							endDialog(1);
							break;
						}
						return true;
					}
				});
	}

	public Object showDialog() {
		super.show();
		try {
			MainLoopHelper.looperRun();
		} catch (RuntimeException e2) {
		}
		return returnValue;
	}

	private class QGameSetUpRoleListener implements
			CompoundButton.OnCheckedChangeListener {

		public void onCheckedChanged(CompoundButton paramCompoundButton,
				boolean paramBoolean) {
			switch (paramCompoundButton.getId()) {
			case R.id.qgame_role_zhugong_r:
				gameApp.settingsViewData.qGameRole = Type.Role.ZhuGong;
				break;
			case R.id.qgame_role_zhongchen_r:
				gameApp.settingsViewData.qGameRole = Type.Role.ZhongChen;
				break;
			case R.id.qgame_role_neijian_r:
				gameApp.settingsViewData.qGameRole = Type.Role.NeiJian;
				break;
			case R.id.qgame_role_fanzei_r:
				gameApp.settingsViewData.qGameRole = Type.Role.FanZei;
				break;
			}
		}
	}
}