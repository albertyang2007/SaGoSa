package alben.sgs.android.dialog;

import alben.sgs.android.GameApp;
import alben.sgs.android.R;
import alben.sgs.android.imageview.MyImageView;
import alben.sgs.type.Type;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

public class SelectWuJiangDialog extends BlockDialog {
	public TextView wjInfo = null;
	public TextView info = null;
	public String role = "Error";
	public String textInfo = "";

	public SelectWuJiangDialog(Context context, GameApp gp) {
		super(context, gp);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				|| keyCode == KeyEvent.KEYCODE_HOME) {
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	public void onCreate() {

		setContentView(R.layout.select_wj);

		role = this.gameApp.gameLogicData.wjHelper
				.convertRoleToName(this.gameApp.gameLogicData.myRole);

		String ss = "";
		if (this.gameApp.gameLogicData.myRole != Type.Role.ZhuGong) {
			ss = "主公是" + this.gameApp.gameLogicData.zhuGongWuJiang.dispName;
		}

		this.textInfo = ss + " 你的身份是" + role;

		wjInfo = (TextView) findViewById(R.id.wj_jineng);
		info = (TextView) findViewById(R.id.info);

		info.setText(textInfo);
		info.setEnabled(true);

		findViewById(R.id.okBtn).setOnClickListener(
				new android.view.View.OnClickListener() {
					@Override
					public void onClick(View paramView) {
						if (gameApp.selectWJViewData.selectedWJ1 != null)
							endDialog(1);
					}
				});

		MyImageView wj0 = (MyImageView) findViewById(R.id.WuJiang1);
		MyImageView wj1 = (MyImageView) findViewById(R.id.WuJiang2);
		MyImageView wj2 = (MyImageView) findViewById(R.id.WuJiang3);
		MyImageView wj3 = (MyImageView) findViewById(R.id.WuJiang4);
		MyImageView wj4 = (MyImageView) findViewById(R.id.WuJiang5);
		MyImageView wj5 = (MyImageView) findViewById(R.id.WuJiang6);
		MyImageView wj6 = (MyImageView) findViewById(R.id.WuJiang7);
		MyImageView wj7 = (MyImageView) findViewById(R.id.WuJiang8);

		wj0.setImageDrawable(this.gameApp.selectWJViewData.wuJiangs[0].imageNumber);
		wj1.setImageDrawable(this.gameApp.selectWJViewData.wuJiangs[1].imageNumber);
		wj2.setImageDrawable(this.gameApp.selectWJViewData.wuJiangs[2].imageNumber);
		wj3.setImageDrawable(this.gameApp.selectWJViewData.wuJiangs[3].imageNumber);
		wj4.setImageDrawable(this.gameApp.selectWJViewData.wuJiangs[4].imageNumber);
		wj5.setImageDrawable(this.gameApp.selectWJViewData.wuJiangs[5].imageNumber);
		wj6.setImageDrawable(this.gameApp.selectWJViewData.wuJiangs[6].imageNumber);
		wj7.setImageDrawable(this.gameApp.selectWJViewData.wuJiangs[7].imageNumber);

		wj0.setOnClickListener(new WuJiangImageListener());
		wj1.setOnClickListener(new WuJiangImageListener());
		wj2.setOnClickListener(new WuJiangImageListener());
		wj3.setOnClickListener(new WuJiangImageListener());
		wj4.setOnClickListener(new WuJiangImageListener());
		wj5.setOnClickListener(new WuJiangImageListener());
		wj6.setOnClickListener(new WuJiangImageListener());
		wj7.setOnClickListener(new WuJiangImageListener());
	}

	private class WuJiangImageListener implements
			android.view.View.OnClickListener {
		public void onClick(View v) {
			int index = 0;
			switch (v.getId()) {
			case R.id.WuJiang1: {
				index = 0;
				break;
			}
			case R.id.WuJiang2: {
				index = 1;
				break;
			}
			case R.id.WuJiang3: {
				index = 2;
				break;
			}
			case R.id.WuJiang4: {
				index = 3;
				break;
			}
			case R.id.WuJiang5: {
				index = 4;
				break;
			}
			case R.id.WuJiang6: {
				index = 5;
				break;
			}
			case R.id.WuJiang7: {
				index = 6;
				break;
			}
			case R.id.WuJiang8: {
				index = 7;
				break;
			}
			}
			gameApp.selectWJViewData.selectedWJ1 = gameApp.selectWJViewData.wuJiangs[index];

			findViewById(R.id.WuJiang1).setBackgroundDrawable(
					gameApp.getResources().getDrawable(R.drawable.bg_black));
			findViewById(R.id.WuJiang2).setBackgroundDrawable(
					gameApp.getResources().getDrawable(R.drawable.bg_black));
			findViewById(R.id.WuJiang3).setBackgroundDrawable(
					gameApp.getResources().getDrawable(R.drawable.bg_black));
			findViewById(R.id.WuJiang4).setBackgroundDrawable(
					gameApp.getResources().getDrawable(R.drawable.bg_black));
			findViewById(R.id.WuJiang5).setBackgroundDrawable(
					gameApp.getResources().getDrawable(R.drawable.bg_black));
			findViewById(R.id.WuJiang6).setBackgroundDrawable(
					gameApp.getResources().getDrawable(R.drawable.bg_black));
			findViewById(R.id.WuJiang7).setBackgroundDrawable(
					gameApp.getResources().getDrawable(R.drawable.bg_black));
			findViewById(R.id.WuJiang8).setBackgroundDrawable(
					gameApp.getResources().getDrawable(R.drawable.bg_black));

			v.setBackgroundDrawable(gameApp.getResources().getDrawable(
					R.drawable.bg_green));

			wjInfo = (TextView) findViewById(R.id.wj_jineng);
			wjInfo.setText(gameApp.selectWJViewData.selectedWJ1.dispName + "\n"
					+ gameApp.selectWJViewData.selectedWJ1.jiNengDesc);
			wjInfo.setEnabled(true);
		}
	}
}
