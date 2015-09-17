package alben.sgs.android;

import java.util.ArrayList;
import java.util.HashMap;

import alben.sgs.cardpai.CardPaiHelper;
import alben.sgs.qgame.QGame;
import alben.sgs.qgame.QGameDataBase;
import alben.sgs.ui.UserInterface;
import alben.sgs.wujiang.WuJiangHelper;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class QGameListActivity extends ListActivity {
	public GameApp gameApp = null;
	public ArrayList<HashMap<String, String>> list = null;
	public TextView qgameDescTX = null;

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.qgame_list);
		this.qgameDescTX = ((TextView) findViewById(R.id.qgame_description));
		this.gameApp = ((GameApp) getApplicationContext());
		this.gameApp.gameActivityContext = this;
		this.gameApp.libGameViewData.fileUtil.ctx = this;
		this.gameApp.gameLogicData.cpHelper = new CardPaiHelper(this.gameApp);
		this.gameApp.gameLogicData.wjHelper = new WuJiangHelper(this.gameApp);
		this.gameApp.gameLogicData.userInterface = new UserInterface(
				this.gameApp);
		this.gameApp.gameLogicData.qgameDB = new QGameDataBase();
		this.list = new ArrayList<HashMap<String, String>>();

		for (QGame qgame : this.gameApp.gameLogicData.qgameDB.qgameList) {
			HashMap<String, String> hm = new HashMap<String, String>();
			hm.put("qgame_title", qgame.title);
			hm.put("qgame_description", qgame.description);
			hm.put("qgame_xmlID", qgame.xmlID + "");
			this.list.add(hm);
		}
		//
		setListAdapter(new SimpleAdapter(this, this.list, R.layout.qgame_info,
				new String[] { "qgame_title", "qgame_description" }, new int[] {
						R.id.qgame_title, R.id.qgame_otherinfo }));
	}

	public void onDestroy() {
		super.onDestroy();
	}

	protected void onListItemClick(ListView paramListView, View paramView,
			int paramInt, long paramLong) {
		super.onListItemClick(paramListView, paramView, paramInt, paramLong);
		this.gameApp.settingsViewData.qgameXMLID = Integer
				.parseInt((String) ((HashMap<String, String>) this.list
						.get(paramInt)).get("qgame_xmlID"));
		Intent localIntent = new Intent(this.gameApp, QGameActivity.class);
		localIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		this.gameApp.startActivity(localIntent);
	}

	protected void onResume() {
		super.onResume();
	}
}