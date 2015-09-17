package alben.sgs.android;

import alben.sgs.android.data.LibGameLogicData;
import android.os.Handler;
import alben.sgs.android.data.LibGameViewData;
import alben.sgs.android.data.SelectCardPaiData;
import alben.sgs.android.data.SelectWuJiangData;
import alben.sgs.android.data.SettingsData;
import alben.sgs.android.data.WuJiangDetailsViewData;
import alben.sgs.android.data.YesNoData;
import android.app.Application;
import android.content.Context;

public class GameApp extends Application {

	public Context gameActivityContext = null;
	public Context mainActivityContext = null;
	public Handler mainHandler = null;
	public boolean runFromQGame = false;
	public LibGameLogicData gameLogicData = new LibGameLogicData();
	public LibGameViewData libGameViewData = new LibGameViewData(this);
	public SettingsData settingsViewData = new SettingsData();
	public SelectWuJiangData selectWJViewData = new SelectWuJiangData();
	public WuJiangDetailsViewData wjDetailsViewData = new WuJiangDetailsViewData();
	public SelectCardPaiData selectCPData = new SelectCardPaiData();
	public YesNoData ynData = new YesNoData();

	public void reset() {
		this.gameLogicData.reset();
		this.selectWJViewData.reset();
		this.libGameViewData.reset();
		this.selectCPData.reset();
		this.ynData.reset();
	}
}
