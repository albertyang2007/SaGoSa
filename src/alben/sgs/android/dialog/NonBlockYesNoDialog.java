package alben.sgs.android.dialog;

import alben.sgs.android.GameApp;
import alben.sgs.android.R;
import alben.sgs.android.mycontroller.MyImageButton;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

public class NonBlockYesNoDialog {
	public GameApp gameApp = null;
	public AlertDialog dlg = null;
	public boolean returnValue = false;

	public NonBlockYesNoDialog(Context context, GameApp gp) {

		this.gameApp = gp;

		LayoutInflater factory = LayoutInflater.from(context);
		final View textEntryView = factory.inflate(R.layout.nonblock_yesno_dlg,
				null);
		dlg = new AlertDialog.Builder(context).setView(textEntryView).create();

		dlg.show();

		TextView txInfo = (TextView) dlg.findViewById(R.id.nonblock_info);
		txInfo.setText(this.gameApp.ynData.genInfo);

		MyImageButton okBtn = (MyImageButton) dlg
				.findViewById(R.id.nonblock_yes);
		okBtn.setText(this.gameApp.ynData.okTxt);
		okBtn.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN: {
					v.setBackgroundDrawable(v.getResources().getDrawable(
							R.drawable.btn_bg1_down));
					break;
				}
				case MotionEvent.ACTION_UP: {
					v.setBackgroundDrawable(v.getResources().getDrawable(
							R.drawable.btn_bg1));
					gameApp.gameLogicData.userExit = true;
					dlg.dismiss();
					break;
				}
				default:
					break;
				}
				return true;
			}
		});

		MyImageButton noBtn = (MyImageButton) dlg
				.findViewById(R.id.nonblock_no);
		noBtn.setText(this.gameApp.ynData.cancelTxt);
		noBtn.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN: {
					v.setBackgroundDrawable(v.getResources().getDrawable(
							R.drawable.btn_bg1_down));
					break;
				}
				case MotionEvent.ACTION_UP: {
					v.setBackgroundDrawable(v.getResources().getDrawable(
							R.drawable.btn_bg1));
					dlg.dismiss();
					break;
				}
				default:
					break;
				}
				return true;
			}
		});
	}
}
