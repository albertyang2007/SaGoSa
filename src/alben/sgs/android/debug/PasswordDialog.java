package alben.sgs.android.debug;

import alben.sgs.android.GameApp;
import alben.sgs.android.R;
import alben.sgs.android.dialog.BlockDialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class PasswordDialog extends BlockDialog {
	int dialogResult;

	public PasswordDialog(Context context, GameApp gameApp) {

		super(context, gameApp);
		TextView promptLbl = (TextView) findViewById(R.id.promptLbl);
		promptLbl.setText("«Î ‰»Î√‹¬Î/n");
	}

	public int getDialogResult() {
		return dialogResult;
	}

	public void setDialogResult(int dialogResult) {
		this.dialogResult = dialogResult;
	}

	/** Called when the activity is first created. */
	public void onCreate() {
		setContentView(R.layout.password_dialog);
		findViewById(R.id.cancelBtn).setOnClickListener(
				new android.view.View.OnClickListener() {

					@Override
					public void onClick(View paramView) {
						endDialog(0);
					}
				});
		findViewById(R.id.okBtn).setOnClickListener(
				new android.view.View.OnClickListener() {

					@Override
					public void onClick(View paramView) {
						endDialog(1);
					}
				});
	}
}
