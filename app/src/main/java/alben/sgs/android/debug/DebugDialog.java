package alben.sgs.android.debug;

import alben.sgs.android.GameApp;
import alben.sgs.android.R;
import alben.sgs.cardpai.CardPai;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class DebugDialog {
	public GameApp gameApp = null;

	public DebugDialog(Context context, GameApp gp) {

		this.gameApp = gp;

		LayoutInflater factory = LayoutInflater.from(context);
		final View textEntryView = factory.inflate(R.layout.debug, null);
		AlertDialog dlg = new AlertDialog.Builder(context)
				.setTitle("Debug")
				.setView(textEntryView)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					}
				})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
							}
						}).create();

		dlg.show();

		TextView debugInfoTx = (TextView) dlg.findViewById(R.id.debug_info);

		StringBuffer sb = new StringBuffer();
		boolean runOld = false;
		if (runOld && gameApp.gameLogicData.myWuJiang != null) {
			sb.append("State: " + gameApp.gameLogicData.myWuJiang.state + "\n");
			sb.append("Blood: " + gameApp.gameLogicData.myWuJiang.blood
					+ ", Max blood: "
					+ gameApp.gameLogicData.myWuJiang.getMaxBlood() + "\n");
			sb.append("askForPai: " + this.gameApp.gameLogicData.askForPai
					+ ", askForHuaShi: "
					+ this.gameApp.gameLogicData.askForHuaShi + "\n");

			sb.append("ShouPai Len: "
					+ gameApp.gameLogicData.myWuJiang.shouPai.size() + "\n");
			sb.append("ShouPai are: ");
			for (int i = 0; i < gameApp.gameLogicData.myWuJiang.shouPai.size(); i++) {
				sb.append(gameApp.gameLogicData.myWuJiang.shouPai.get(i) + "; ");
			}
			sb.append("\n");

			sb.append("clicked shoupai Len: "
					+ gameApp.gameLogicData.myWuJiang.countSelectedShouPai()
					+ "\n");
			sb.append("clicked shoupai are: ");
			for (int i = 0; i < gameApp.gameLogicData.myWuJiang.shouPai.size(); i++) {
				CardPai cp = gameApp.gameLogicData.myWuJiang.shouPai.get(i);
				if (cp.selectedByClick)
					sb.append(cp + "; ");
			}
			sb.append("\n");
		} else {
			sb.append("myWuJiang is null.\n");
		}

		sb.append("Latest log:\n");
		sb.append(this.gameApp.libGameViewData.getLatestLogs() + "\n");

		sb.append("LastExceptionTrack:\n");
		sb.append(this.gameApp.gameLogicData.exceptionTrack.substring(0, 512));

		debugInfoTx.setText(sb.toString());
		debugInfoTx.setEnabled(true);
	}
}
