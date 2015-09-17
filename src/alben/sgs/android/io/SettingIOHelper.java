package alben.sgs.android.io;

import alben.sgs.android.GameApp;
import alben.sgs.type.Type;
import android.content.Context;
import android.content.SharedPreferences;

public class SettingIOHelper {
	public static final String settingsFile = "settings";
	public GameApp gameApp = null;

	public SettingIOHelper(GameApp paramGameApp) {
		this.gameApp = paramGameApp;
	}

	public void loadSettings() {
		SharedPreferences localSharedPreferences = this.gameApp
				.getSharedPreferences(settingsFile,
						Context.MODE_WORLD_WRITEABLE);
		String str = localSharedPreferences.getString("gameType",
				Type.GameType.g_1v1 + "");

		if (str.equals("g_1v1")) {
			this.gameApp.settingsViewData.gameType = Type.GameType.g_1v1;
		} else if (str.equals("g_3v3")) {
			this.gameApp.settingsViewData.gameType = Type.GameType.g_3v3;
		} else if (str.equals("g_3_people")) {
			this.gameApp.settingsViewData.gameType = Type.GameType.g_3_people;
		} else if (str.equals("g_4_people")) {
			this.gameApp.settingsViewData.gameType = Type.GameType.g_4_people;
		} else if (str.equals("g_5_people")) {
			this.gameApp.settingsViewData.gameType = Type.GameType.g_5_people;
		} else if (str.equals("g_6_people")) {
			this.gameApp.settingsViewData.gameType = Type.GameType.g_6_people;
		} else if (str.equals("g_7_people")) {
			this.gameApp.settingsViewData.gameType = Type.GameType.g_7_people;
		} else if (str.equals("g_81_people")) {
			this.gameApp.settingsViewData.gameType = Type.GameType.g_81_people;
		} else if (str.equals("g_82_people")) {
			this.gameApp.settingsViewData.gameType = Type.GameType.g_82_people;
		}
		//
		this.gameApp.settingsViewData.delayMillionSeconds = localSharedPreferences
				.getInt("delayMillionSeconds", 2000);
	}

	public void saveSettings() {
		SharedPreferences.Editor localEditor = this.gameApp
				.getSharedPreferences(settingsFile,
						Context.MODE_WORLD_WRITEABLE).edit();
		localEditor.putString("gameType",
				this.gameApp.settingsViewData.gameType + "");
		localEditor.putInt("delayMillionSeconds",
				this.gameApp.settingsViewData.delayMillionSeconds);
		localEditor.commit();
	}
}