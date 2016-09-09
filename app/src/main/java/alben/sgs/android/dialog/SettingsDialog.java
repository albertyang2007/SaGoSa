package alben.sgs.android.dialog;

import alben.sgs.android.GameApp;
import alben.sgs.android.R;
import alben.sgs.android.io.SettingIOHelper;
import alben.sgs.android.mycontroller.MyImageButton;
import alben.sgs.type.Type;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class SettingsDialog {
	public GameApp gameApp = null;
	public AlertDialog dlg = null;

	public SettingsDialog(Context context, GameApp gp) {

		this.gameApp = gp;
		if (this.gameApp.gameLogicData.settingsHelper == null)
			this.gameApp.gameLogicData.settingsHelper = new SettingIOHelper(
					this.gameApp);
		this.gameApp.gameLogicData.settingsHelper.loadSettings();

		LayoutInflater factory = LayoutInflater.from(context);
		final View textEntryView = factory.inflate(R.layout.settings, null);
		dlg = new AlertDialog.Builder(context).setView(textEntryView).create();

		dlg.show();

		// set invisible btn
		MyImageButton invisibleBtn1 = (MyImageButton) dlg
				.findViewById(R.id.invisible_btn1);
		invisibleBtn1.setVisibility(View.INVISIBLE);
		MyImageButton invisibleBtn2 = (MyImageButton) dlg
				.findViewById(R.id.invisible_btn2);
		invisibleBtn2.setVisibility(View.INVISIBLE);
		MyImageButton invisibleBtn3 = (MyImageButton) dlg
				.findViewById(R.id.invisible_btn3);
		invisibleBtn3.setVisibility(View.INVISIBLE);
		//

		ImageView okBtn = (ImageView) dlg.findViewById(R.id.settings_ok);

		okBtn.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN: {
					v.setBackgroundDrawable(v.getResources().getDrawable(
							R.drawable.btn_bg_long_down));
					break;
				}
				case MotionEvent.ACTION_UP: {
					v.setBackgroundDrawable(v.getResources().getDrawable(
							R.drawable.btn_bg_long));
					gameApp.gameLogicData.settingsHelper.saveSettings();
					dlg.dismiss();
					break;
				}
				default:
					break;
				}
				return true;
			}
		});

		ImageView gameType_1v1 = (ImageView) dlg
				.findViewById(R.id.gameType_1v1);
		gameType_1v1.setOnTouchListener(new MySettingBtnController());

		ImageView gameType_3_people = (ImageView) dlg
				.findViewById(R.id.gameType_3_people);
		gameType_3_people.setOnTouchListener(new MySettingBtnController());

		ImageView gameType_4_people = (ImageView) dlg
				.findViewById(R.id.gameType_4_people);
		gameType_4_people.setOnTouchListener(new MySettingBtnController());

		ImageView gameType_5_people = (ImageView) dlg
				.findViewById(R.id.gameType_5_people);
		gameType_5_people.setOnTouchListener(new MySettingBtnController());

		ImageView gameType_6_people = (ImageView) dlg
				.findViewById(R.id.gameType_6_people);
		gameType_6_people.setOnTouchListener(new MySettingBtnController());

		ImageView gameType_7_people = (ImageView) dlg
				.findViewById(R.id.gameType_7_people);
		gameType_7_people.setOnTouchListener(new MySettingBtnController());

		ImageView gameType_81_people = (ImageView) dlg
				.findViewById(R.id.gameType_81_people);
		gameType_81_people.setOnTouchListener(new MySettingBtnController());

		ImageView gameType_82_people = (ImageView) dlg
				.findViewById(R.id.gameType_82_people);
		gameType_82_people.setOnTouchListener(new MySettingBtnController());

		ImageView gameType_3v3 = (ImageView) dlg
				.findViewById(R.id.gameType_3v3);
		gameType_3v3.setOnTouchListener(new MySettingBtnController());

	}

	private class MySettingBtnController implements OnTouchListener {
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN: {
				// v.setBackgroundDrawable(v.getResources().getDrawable(
				// R.drawable.btn_bg_long_down));
				break;
			}
			case MotionEvent.ACTION_UP: {
				// v.setBackgroundDrawable(v.getResources().getDrawable(
				// R.drawable.btn_bg_long));

				switch (v.getId()) {
				case R.id.gameType_1v1: {
					gameApp.settingsViewData.gameType = Type.GameType.g_1v1;
					break;
				}
				case R.id.gameType_3_people: {
					gameApp.settingsViewData.gameType = Type.GameType.g_3_people;
					break;
				}
				case R.id.gameType_4_people: {
					gameApp.settingsViewData.gameType = Type.GameType.g_4_people;
					break;
				}
				case R.id.gameType_5_people: {
					gameApp.settingsViewData.gameType = Type.GameType.g_5_people;
					break;
				}
				case R.id.gameType_6_people: {
					gameApp.settingsViewData.gameType = Type.GameType.g_6_people;
					break;
				}
				case R.id.gameType_7_people: {
					gameApp.settingsViewData.gameType = Type.GameType.g_7_people;
					break;
				}
				case R.id.gameType_81_people: {
					gameApp.settingsViewData.gameType = Type.GameType.g_81_people;
					break;
				}
				case R.id.gameType_82_people: {
					gameApp.settingsViewData.gameType = Type.GameType.g_82_people;
					break;
				}
				case R.id.gameType_3v3: {
					gameApp.settingsViewData.gameType = Type.GameType.g_3v3;
					break;
				}
				}// switch (v.getId())
			}// ACTION_UP
				break;
			}// switch (event.getAction())
			return true;
		}
	}
}
