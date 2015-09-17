/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package alben.sgs.android;

import alben.sgs.android.debug.DebugAddShouPaiDialog;
import alben.sgs.android.debug.DebugDialog;
import alben.sgs.android.debug.MyRenDeButtonListener;
import alben.sgs.android.dialog.NonBlockYesNoDialog;
import alben.sgs.android.imageview.MyImageView;
import alben.sgs.android.io.ExceptionTraceHelper;
import alben.sgs.android.io.SettingIOHelper;
import alben.sgs.android.mycontroller.MyFunctionButtonController;
import alben.sgs.android.mycontroller.MyImageButton;
import alben.sgs.android.mycontroller.MyJiNengButtonController;
import alben.sgs.android.mycontroller.MyWJ8ShouPaiImageListener;
import alben.sgs.android.mycontroller.MyWJ8WuQiTxtListener;
import alben.sgs.android.mycontroller.MyWuJiangImageListener;
import alben.sgs.cardpai.CardPaiHelper;
import alben.sgs.type.Type;
import alben.sgs.type.Type.GameType;
import alben.sgs.ui.UserInterface;
import alben.sgs.wujiang.WuJiang;
import alben.sgs.wujiang.WuJiangHelper;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GameActivity extends Activity {

	// ===below data are for game android activity
	public static final String EXTRA_START_PLAYER = "alben.sgs.android.GameActivity.EXTRA_START_PLAYER";

	public GameApp gameApp = null;

	// ===end

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);

		setContentView(R.layout.main_l);

		gameApp = ((GameApp) getApplicationContext());
		gameApp.gameActivityContext = GameActivity.this;
		this.gameApp.libGameViewData.fileUtil.ctx = GameActivity.this;
		getWindowManager().getDefaultDisplay().getMetrics(
				this.gameApp.libGameViewData.dm);
		this.gameApp.libGameViewData.display
				.setDisplay(this.gameApp.libGameViewData.dm);

		this.initViewController();

		this.gameApp.gameLogicData.cpHelper = new CardPaiHelper(gameApp);
		this.gameApp.gameLogicData.wjHelper = new WuJiangHelper(gameApp);
		this.gameApp.gameLogicData.userInterface = new UserInterface(gameApp);
		this.gameApp.gameLogicData.settingsHelper = new SettingIOHelper(
				this.gameApp);
		this.gameApp.gameLogicData.settingsHelper.loadSettings();
		//
		this.gameApp.mainHandler = new Handler() {
			public void handleMessage(Message paramMessage) {
				if (paramMessage.what == 100) {
					if (gameApp.settingsViewData.gameType == Type.GameType.g_1v1) {
						gameApp.gameLogicData.numberWJ = 2;
					} else if (gameApp.settingsViewData.gameType == Type.GameType.g_5_people) {
						gameApp.gameLogicData.numberWJ = 5;
					} else if (gameApp.settingsViewData.gameType == Type.GameType.g_81_people) {
						gameApp.gameLogicData.numberWJ = 8;
					}
					//
					// gameApp.libGameViewData.mStartMatch.setEnabled(false);
					startNewGame();
					// gameApp.libGameViewData.mStartMatch.setEnabled(true);
					//
				} else if (paramMessage.what == 200) {
					// gameApp.libGameViewData.mStartMatch.setEnabled(false);
					startNewQGame();
					// gameApp.libGameViewData.mStartMatch.setEnabled(true);
				}
			}
		};
		//
		if (!this.gameApp.runFromQGame)
			sendMessageStartNewGame();
		else
			sendMessageStartNewQGame();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		this.gameApp.libGameViewData.fileUtil.closeFile();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

		} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

		}
	}

	public void initViewController() {

		this.gameApp.libGameViewData.firstView = findViewById(R.id.first_view);
		this.gameApp.libGameViewData.mInfoView = (TextView) findViewById(R.id.center_info);
		this.gameApp.libGameViewData.mInfoImg = (MyImageView) findViewById(R.id.center_img);
		this.gameApp.libGameViewData.mInfoImg
				.setImageDrawable(R.drawable.bg_center_info);

		this.gameApp.libGameViewData.mChuPai = (MyImageView) findViewById(R.id.ChuPai);
		this.gameApp.libGameViewData.mChuPai
				.setImageDrawable(R.drawable.btn_function_ok);

		this.gameApp.libGameViewData.mFangQi = (MyImageView) findViewById(R.id.FangQi);
		this.gameApp.libGameViewData.mFangQi
				.setImageDrawable(R.drawable.btn_function_cancel);

		this.gameApp.libGameViewData.mQiPai = (MyImageView) findViewById(R.id.QiPai);
		this.gameApp.libGameViewData.mQiPai
				.setImageDrawable(R.drawable.btn_function_finish);

		this.gameApp.libGameViewData.mTuoGuan = (MyImageButton) findViewById(R.id.TuoGuan);
		this.gameApp.libGameViewData.mStartMatch = (MyImageButton) findViewById(R.id.StartMatch);
		this.gameApp.libGameViewData.mReturn = (MyImageButton) findViewById(R.id.Return);
		this.gameApp.libGameViewData.mRenDe = (MyImageButton) findViewById(R.id.RenDe);

		this.gameApp.libGameViewData.mJiNengBtn1 = (MyImageView) findViewById(R.id.JiNeng1);
		this.gameApp.libGameViewData.mJiNengBtn2 = (MyImageView) findViewById(R.id.JiNeng2);
		this.gameApp.libGameViewData.mJiNengBtn3 = (MyImageView) findViewById(R.id.JiNeng3);
		this.gameApp.libGameViewData.mJiNengBtn4 = (MyImageView) findViewById(R.id.JiNeng4);

		this.gameApp.libGameViewData.mJiNengBtn1
				.setImageDrawable(R.drawable.btn_jineng_shu);
		this.gameApp.libGameViewData.mJiNengBtn2
				.setImageDrawable(R.drawable.btn_jineng_shu);
		this.gameApp.libGameViewData.mJiNengBtn3
				.setImageDrawable(R.drawable.btn_jineng_shu);
		this.gameApp.libGameViewData.mJiNengBtn4
				.setImageDrawable(R.drawable.btn_jineng_shu);

		this.gameApp.libGameViewData.mJiNengBtn1Txt = (TextView) findViewById(R.id.JiNeng1_txt);
		this.gameApp.libGameViewData.mJiNengBtn2Txt = (TextView) findViewById(R.id.JiNeng2_txt);
		this.gameApp.libGameViewData.mJiNengBtn3Txt = (TextView) findViewById(R.id.JiNeng3_txt);
		this.gameApp.libGameViewData.mJiNengBtn4Txt = (TextView) findViewById(R.id.JiNeng4_txt);

		this.gameApp.libGameViewData.linearWJs[0] = (LinearLayout) findViewById(R.id.linear_wj1);
		this.gameApp.libGameViewData.linearWJs[1] = (LinearLayout) findViewById(R.id.linear_wj2);
		this.gameApp.libGameViewData.linearWJs[2] = (LinearLayout) findViewById(R.id.linear_wj3);
		this.gameApp.libGameViewData.linearWJs[3] = (LinearLayout) findViewById(R.id.linear_wj4);
		this.gameApp.libGameViewData.linearWJs[4] = (LinearLayout) findViewById(R.id.linear_wj5);
		this.gameApp.libGameViewData.linearWJs[5] = (LinearLayout) findViewById(R.id.linear_wj6);
		this.gameApp.libGameViewData.linearWJs[6] = (LinearLayout) findViewById(R.id.linear_wj7);
		this.gameApp.libGameViewData.linearWJs[7] = (LinearLayout) findViewById(R.id.linear_wj8);

		this.gameApp.libGameViewData.wj8LinearArea = (LinearLayout) findViewById(R.id.wj8_relative_area);

		this.gameApp.libGameViewData.imageWJBloods[0].img9Bloods[0] = (MyImageView) findViewById(R.id.wj1_blood1);
		this.gameApp.libGameViewData.imageWJBloods[0].img9Bloods[1] = (MyImageView) findViewById(R.id.wj1_blood2);
		this.gameApp.libGameViewData.imageWJBloods[0].img9Bloods[2] = (MyImageView) findViewById(R.id.wj1_blood3);
		this.gameApp.libGameViewData.imageWJBloods[0].img9Bloods[3] = (MyImageView) findViewById(R.id.wj1_blood4);
		this.gameApp.libGameViewData.imageWJBloods[0].img9Bloods[4] = (MyImageView) findViewById(R.id.wj1_blood5);
		this.gameApp.libGameViewData.imageWJBloods[0].img9Bloods[5] = (MyImageView) findViewById(R.id.wj1_blood6);
		this.gameApp.libGameViewData.imageWJBloods[0].img9Bloods[6] = (MyImageView) findViewById(R.id.wj1_blood7);
		this.gameApp.libGameViewData.imageWJBloods[0].img9Bloods[7] = (MyImageView) findViewById(R.id.wj1_blood8);
		this.gameApp.libGameViewData.imageWJBloods[0].img9Bloods[8] = (MyImageView) findViewById(R.id.wj1_blood9);

		this.gameApp.libGameViewData.imageWJBloods[1].img9Bloods[0] = (MyImageView) findViewById(R.id.wj2_blood1);
		this.gameApp.libGameViewData.imageWJBloods[1].img9Bloods[1] = (MyImageView) findViewById(R.id.wj2_blood2);
		this.gameApp.libGameViewData.imageWJBloods[1].img9Bloods[2] = (MyImageView) findViewById(R.id.wj2_blood3);
		this.gameApp.libGameViewData.imageWJBloods[1].img9Bloods[3] = (MyImageView) findViewById(R.id.wj2_blood4);
		this.gameApp.libGameViewData.imageWJBloods[1].img9Bloods[4] = (MyImageView) findViewById(R.id.wj2_blood5);
		this.gameApp.libGameViewData.imageWJBloods[1].img9Bloods[5] = (MyImageView) findViewById(R.id.wj2_blood6);
		this.gameApp.libGameViewData.imageWJBloods[1].img9Bloods[6] = (MyImageView) findViewById(R.id.wj2_blood7);
		this.gameApp.libGameViewData.imageWJBloods[1].img9Bloods[7] = (MyImageView) findViewById(R.id.wj2_blood8);
		this.gameApp.libGameViewData.imageWJBloods[1].img9Bloods[8] = (MyImageView) findViewById(R.id.wj2_blood9);

		this.gameApp.libGameViewData.imageWJBloods[2].img9Bloods[0] = (MyImageView) findViewById(R.id.wj3_blood1);
		this.gameApp.libGameViewData.imageWJBloods[2].img9Bloods[1] = (MyImageView) findViewById(R.id.wj3_blood2);
		this.gameApp.libGameViewData.imageWJBloods[2].img9Bloods[2] = (MyImageView) findViewById(R.id.wj3_blood3);
		this.gameApp.libGameViewData.imageWJBloods[2].img9Bloods[3] = (MyImageView) findViewById(R.id.wj3_blood4);
		this.gameApp.libGameViewData.imageWJBloods[2].img9Bloods[4] = (MyImageView) findViewById(R.id.wj3_blood5);
		this.gameApp.libGameViewData.imageWJBloods[2].img9Bloods[5] = (MyImageView) findViewById(R.id.wj3_blood6);
		this.gameApp.libGameViewData.imageWJBloods[2].img9Bloods[6] = (MyImageView) findViewById(R.id.wj3_blood7);
		this.gameApp.libGameViewData.imageWJBloods[2].img9Bloods[7] = (MyImageView) findViewById(R.id.wj3_blood8);
		this.gameApp.libGameViewData.imageWJBloods[2].img9Bloods[8] = (MyImageView) findViewById(R.id.wj3_blood9);

		this.gameApp.libGameViewData.imageWJBloods[3].img9Bloods[0] = (MyImageView) findViewById(R.id.wj4_blood1);
		this.gameApp.libGameViewData.imageWJBloods[3].img9Bloods[1] = (MyImageView) findViewById(R.id.wj4_blood2);
		this.gameApp.libGameViewData.imageWJBloods[3].img9Bloods[2] = (MyImageView) findViewById(R.id.wj4_blood3);
		this.gameApp.libGameViewData.imageWJBloods[3].img9Bloods[3] = (MyImageView) findViewById(R.id.wj4_blood4);
		this.gameApp.libGameViewData.imageWJBloods[3].img9Bloods[4] = (MyImageView) findViewById(R.id.wj4_blood5);
		this.gameApp.libGameViewData.imageWJBloods[3].img9Bloods[5] = (MyImageView) findViewById(R.id.wj4_blood6);
		this.gameApp.libGameViewData.imageWJBloods[3].img9Bloods[6] = (MyImageView) findViewById(R.id.wj4_blood7);
		this.gameApp.libGameViewData.imageWJBloods[3].img9Bloods[7] = (MyImageView) findViewById(R.id.wj4_blood8);
		this.gameApp.libGameViewData.imageWJBloods[3].img9Bloods[8] = (MyImageView) findViewById(R.id.wj4_blood9);

		this.gameApp.libGameViewData.imageWJBloods[4].img9Bloods[0] = (MyImageView) findViewById(R.id.wj5_blood1);
		this.gameApp.libGameViewData.imageWJBloods[4].img9Bloods[1] = (MyImageView) findViewById(R.id.wj5_blood2);
		this.gameApp.libGameViewData.imageWJBloods[4].img9Bloods[2] = (MyImageView) findViewById(R.id.wj5_blood3);
		this.gameApp.libGameViewData.imageWJBloods[4].img9Bloods[3] = (MyImageView) findViewById(R.id.wj5_blood4);
		this.gameApp.libGameViewData.imageWJBloods[4].img9Bloods[4] = (MyImageView) findViewById(R.id.wj5_blood5);
		this.gameApp.libGameViewData.imageWJBloods[4].img9Bloods[5] = (MyImageView) findViewById(R.id.wj5_blood6);
		this.gameApp.libGameViewData.imageWJBloods[4].img9Bloods[6] = (MyImageView) findViewById(R.id.wj5_blood7);
		this.gameApp.libGameViewData.imageWJBloods[4].img9Bloods[7] = (MyImageView) findViewById(R.id.wj5_blood8);
		this.gameApp.libGameViewData.imageWJBloods[4].img9Bloods[8] = (MyImageView) findViewById(R.id.wj5_blood9);

		this.gameApp.libGameViewData.imageWJBloods[5].img9Bloods[0] = (MyImageView) findViewById(R.id.wj6_blood1);
		this.gameApp.libGameViewData.imageWJBloods[5].img9Bloods[1] = (MyImageView) findViewById(R.id.wj6_blood2);
		this.gameApp.libGameViewData.imageWJBloods[5].img9Bloods[2] = (MyImageView) findViewById(R.id.wj6_blood3);
		this.gameApp.libGameViewData.imageWJBloods[5].img9Bloods[3] = (MyImageView) findViewById(R.id.wj6_blood4);
		this.gameApp.libGameViewData.imageWJBloods[5].img9Bloods[4] = (MyImageView) findViewById(R.id.wj6_blood5);
		this.gameApp.libGameViewData.imageWJBloods[5].img9Bloods[5] = (MyImageView) findViewById(R.id.wj6_blood6);
		this.gameApp.libGameViewData.imageWJBloods[5].img9Bloods[6] = (MyImageView) findViewById(R.id.wj6_blood7);
		this.gameApp.libGameViewData.imageWJBloods[5].img9Bloods[7] = (MyImageView) findViewById(R.id.wj6_blood8);
		this.gameApp.libGameViewData.imageWJBloods[5].img9Bloods[8] = (MyImageView) findViewById(R.id.wj6_blood9);

		this.gameApp.libGameViewData.imageWJBloods[6].img9Bloods[0] = (MyImageView) findViewById(R.id.wj7_blood1);
		this.gameApp.libGameViewData.imageWJBloods[6].img9Bloods[1] = (MyImageView) findViewById(R.id.wj7_blood2);
		this.gameApp.libGameViewData.imageWJBloods[6].img9Bloods[2] = (MyImageView) findViewById(R.id.wj7_blood3);
		this.gameApp.libGameViewData.imageWJBloods[6].img9Bloods[3] = (MyImageView) findViewById(R.id.wj7_blood4);
		this.gameApp.libGameViewData.imageWJBloods[6].img9Bloods[4] = (MyImageView) findViewById(R.id.wj7_blood5);
		this.gameApp.libGameViewData.imageWJBloods[6].img9Bloods[5] = (MyImageView) findViewById(R.id.wj7_blood6);
		this.gameApp.libGameViewData.imageWJBloods[6].img9Bloods[6] = (MyImageView) findViewById(R.id.wj7_blood7);
		this.gameApp.libGameViewData.imageWJBloods[6].img9Bloods[7] = (MyImageView) findViewById(R.id.wj7_blood8);
		this.gameApp.libGameViewData.imageWJBloods[6].img9Bloods[8] = (MyImageView) findViewById(R.id.wj7_blood9);

		this.gameApp.libGameViewData.imageWJBloods[7].img9Bloods[0] = (MyImageView) findViewById(R.id.wj8_blood1);
		this.gameApp.libGameViewData.imageWJBloods[7].img9Bloods[1] = (MyImageView) findViewById(R.id.wj8_blood2);
		this.gameApp.libGameViewData.imageWJBloods[7].img9Bloods[2] = (MyImageView) findViewById(R.id.wj8_blood3);
		this.gameApp.libGameViewData.imageWJBloods[7].img9Bloods[3] = (MyImageView) findViewById(R.id.wj8_blood4);
		this.gameApp.libGameViewData.imageWJBloods[7].img9Bloods[4] = (MyImageView) findViewById(R.id.wj8_blood5);
		this.gameApp.libGameViewData.imageWJBloods[7].img9Bloods[5] = (MyImageView) findViewById(R.id.wj8_blood6);
		this.gameApp.libGameViewData.imageWJBloods[7].img9Bloods[6] = (MyImageView) findViewById(R.id.wj8_blood7);
		this.gameApp.libGameViewData.imageWJBloods[7].img9Bloods[7] = (MyImageView) findViewById(R.id.wj8_blood8);
		this.gameApp.libGameViewData.imageWJBloods[7].img9Bloods[8] = (MyImageView) findViewById(R.id.wj8_blood9);

		this.gameApp.libGameViewData.imageWJRoles[0] = (MyImageView) findViewById(R.id.wj1_role);
		this.gameApp.libGameViewData.imageWJRoles[1] = (MyImageView) findViewById(R.id.wj2_role);
		this.gameApp.libGameViewData.imageWJRoles[2] = (MyImageView) findViewById(R.id.wj3_role);
		this.gameApp.libGameViewData.imageWJRoles[3] = (MyImageView) findViewById(R.id.wj4_role);
		this.gameApp.libGameViewData.imageWJRoles[4] = (MyImageView) findViewById(R.id.wj5_role);
		this.gameApp.libGameViewData.imageWJRoles[5] = (MyImageView) findViewById(R.id.wj6_role);
		this.gameApp.libGameViewData.imageWJRoles[6] = (MyImageView) findViewById(R.id.wj7_role);
		this.gameApp.libGameViewData.imageWJRoles[7] = (MyImageView) findViewById(R.id.wj8_role);

		this.gameApp.libGameViewData.mChuPai
				.setOnClickListener(new MyFunctionButtonController(gameApp));
		this.gameApp.libGameViewData.mFangQi
				.setOnClickListener(new MyFunctionButtonController(gameApp));
		this.gameApp.libGameViewData.mQiPai
				.setOnClickListener(new MyFunctionButtonController(gameApp));
		this.gameApp.libGameViewData.mTuoGuan
				.setOnClickListener(new MyFunctionButtonController(gameApp));

		this.gameApp.libGameViewData.mJiNengBtn1
				.setOnClickListener(new MyJiNengButtonController(gameApp));
		this.gameApp.libGameViewData.mJiNengBtn2
				.setOnClickListener(new MyJiNengButtonController(gameApp));
		this.gameApp.libGameViewData.mJiNengBtn3
				.setOnClickListener(new MyJiNengButtonController(gameApp));
		this.gameApp.libGameViewData.mJiNengBtn4
				.setOnClickListener(new MyJiNengButtonController(gameApp));

		this.gameApp.libGameViewData.mTuoGuan.setText("托管");
		this.gameApp.libGameViewData.mStartMatch.setText("开局");
		this.gameApp.libGameViewData.mReturn.setText("取牌");
		this.gameApp.libGameViewData.mRenDe.setText("仁德");

		this.gameApp.libGameViewData.mStartMatch
				.setOnClickListener(new MyStartGameButtonListener());
		this.gameApp.libGameViewData.mReturn
				.setOnClickListener(new MyReturnButtonListener());
		this.gameApp.libGameViewData.mRenDe
				.setOnClickListener(new MyRenDeButtonListener(this.gameApp));

		this.gameApp.libGameViewData.imageWJs[0] = (MyImageView) findViewById(R.id.WuJiang1);
		this.gameApp.libGameViewData.imageWJs[1] = (MyImageView) findViewById(R.id.WuJiang2);
		this.gameApp.libGameViewData.imageWJs[2] = (MyImageView) findViewById(R.id.WuJiang3);
		this.gameApp.libGameViewData.imageWJs[3] = (MyImageView) findViewById(R.id.WuJiang4);
		this.gameApp.libGameViewData.imageWJs[4] = (MyImageView) findViewById(R.id.WuJiang5);
		this.gameApp.libGameViewData.imageWJs[5] = (MyImageView) findViewById(R.id.WuJiang6);
		this.gameApp.libGameViewData.imageWJs[6] = (MyImageView) findViewById(R.id.WuJiang7);
		this.gameApp.libGameViewData.imageWJs[7] = (MyImageView) findViewById(R.id.WuJiang8);

		for (int i = 0; i < this.gameApp.libGameViewData.imageWJs.length; i++) {
			this.gameApp.libGameViewData.imageWJs[i]
					.setOnClickListener(new MyWuJiangImageListener(gameApp));
			this.gameApp.libGameViewData.imageWJs[i]
					.setImageDrawable(R.drawable.wj_gray);
		}

		this.gameApp.libGameViewData.imageWJDPs[0].imgPds[0] = (MyImageView) findViewById(R.id.wj1_pd1);
		this.gameApp.libGameViewData.imageWJDPs[0].imgPds[1] = (MyImageView) findViewById(R.id.wj1_pd2);
		this.gameApp.libGameViewData.imageWJDPs[0].imgPds[2] = (MyImageView) findViewById(R.id.wj1_pd3);

		this.gameApp.libGameViewData.imageWJDPs[1].imgPds[0] = (MyImageView) findViewById(R.id.wj2_pd1);
		this.gameApp.libGameViewData.imageWJDPs[1].imgPds[1] = (MyImageView) findViewById(R.id.wj2_pd2);
		this.gameApp.libGameViewData.imageWJDPs[1].imgPds[2] = (MyImageView) findViewById(R.id.wj2_pd3);

		this.gameApp.libGameViewData.imageWJDPs[2].imgPds[0] = (MyImageView) findViewById(R.id.wj3_pd1);
		this.gameApp.libGameViewData.imageWJDPs[2].imgPds[1] = (MyImageView) findViewById(R.id.wj3_pd2);
		this.gameApp.libGameViewData.imageWJDPs[2].imgPds[2] = (MyImageView) findViewById(R.id.wj3_pd3);

		this.gameApp.libGameViewData.imageWJDPs[3].imgPds[0] = (MyImageView) findViewById(R.id.wj4_pd1);
		this.gameApp.libGameViewData.imageWJDPs[3].imgPds[1] = (MyImageView) findViewById(R.id.wj4_pd2);
		this.gameApp.libGameViewData.imageWJDPs[3].imgPds[2] = (MyImageView) findViewById(R.id.wj4_pd3);

		this.gameApp.libGameViewData.imageWJDPs[4].imgPds[0] = (MyImageView) findViewById(R.id.wj5_pd1);
		this.gameApp.libGameViewData.imageWJDPs[4].imgPds[1] = (MyImageView) findViewById(R.id.wj5_pd2);
		this.gameApp.libGameViewData.imageWJDPs[4].imgPds[2] = (MyImageView) findViewById(R.id.wj5_pd3);

		this.gameApp.libGameViewData.imageWJDPs[5].imgPds[0] = (MyImageView) findViewById(R.id.wj6_pd1);
		this.gameApp.libGameViewData.imageWJDPs[5].imgPds[1] = (MyImageView) findViewById(R.id.wj6_pd2);
		this.gameApp.libGameViewData.imageWJDPs[5].imgPds[2] = (MyImageView) findViewById(R.id.wj6_pd3);

		this.gameApp.libGameViewData.imageWJDPs[6].imgPds[0] = (MyImageView) findViewById(R.id.wj7_pd1);
		this.gameApp.libGameViewData.imageWJDPs[6].imgPds[1] = (MyImageView) findViewById(R.id.wj7_pd2);
		this.gameApp.libGameViewData.imageWJDPs[6].imgPds[2] = (MyImageView) findViewById(R.id.wj7_pd3);

		this.gameApp.libGameViewData.imageWJDPs[7].imgPds[0] = (MyImageView) findViewById(R.id.wj8_pd1);
		this.gameApp.libGameViewData.imageWJDPs[7].imgPds[1] = (MyImageView) findViewById(R.id.wj8_pd2);
		this.gameApp.libGameViewData.imageWJDPs[7].imgPds[2] = (MyImageView) findViewById(R.id.wj8_pd3);

		this.gameApp.libGameViewData.imgWJShouPaiNumber[0] = (MyImageView) findViewById(R.id.wj1_shoupai);
		this.gameApp.libGameViewData.imgWJShouPaiNumber[1] = (MyImageView) findViewById(R.id.wj2_shoupai);
		this.gameApp.libGameViewData.imgWJShouPaiNumber[2] = (MyImageView) findViewById(R.id.wj3_shoupai);
		this.gameApp.libGameViewData.imgWJShouPaiNumber[3] = (MyImageView) findViewById(R.id.wj4_shoupai);
		this.gameApp.libGameViewData.imgWJShouPaiNumber[4] = (MyImageView) findViewById(R.id.wj5_shoupai);
		this.gameApp.libGameViewData.imgWJShouPaiNumber[5] = (MyImageView) findViewById(R.id.wj6_shoupai);
		this.gameApp.libGameViewData.imgWJShouPaiNumber[6] = (MyImageView) findViewById(R.id.wj7_shoupai);
		this.gameApp.libGameViewData.imgWJShouPaiNumber[7] = (MyImageView) findViewById(R.id.wj8_shoupai);

		this.gameApp.libGameViewData.imgLianHuans[0] = (MyImageView) findViewById(R.id.wj1_lianhuan);
		this.gameApp.libGameViewData.imgLianHuans[1] = (MyImageView) findViewById(R.id.wj2_lianhuan);
		this.gameApp.libGameViewData.imgLianHuans[2] = (MyImageView) findViewById(R.id.wj3_lianhuan);
		this.gameApp.libGameViewData.imgLianHuans[3] = (MyImageView) findViewById(R.id.wj4_lianhuan);
		this.gameApp.libGameViewData.imgLianHuans[4] = (MyImageView) findViewById(R.id.wj5_lianhuan);
		this.gameApp.libGameViewData.imgLianHuans[5] = (MyImageView) findViewById(R.id.wj6_lianhuan);
		this.gameApp.libGameViewData.imgLianHuans[6] = (MyImageView) findViewById(R.id.wj7_lianhuan);
		this.gameApp.libGameViewData.imgLianHuans[7] = (MyImageView) findViewById(R.id.wj8_lianhuan);

		this.gameApp.libGameViewData.imgFanMians[0] = (MyImageView) findViewById(R.id.wj1_fanmian);
		this.gameApp.libGameViewData.imgFanMians[1] = (MyImageView) findViewById(R.id.wj2_fanmian);
		this.gameApp.libGameViewData.imgFanMians[2] = (MyImageView) findViewById(R.id.wj3_fanmian);
		this.gameApp.libGameViewData.imgFanMians[3] = (MyImageView) findViewById(R.id.wj4_fanmian);
		this.gameApp.libGameViewData.imgFanMians[4] = (MyImageView) findViewById(R.id.wj5_fanmian);
		this.gameApp.libGameViewData.imgFanMians[5] = (MyImageView) findViewById(R.id.wj6_fanmian);
		this.gameApp.libGameViewData.imgFanMians[6] = (MyImageView) findViewById(R.id.wj7_fanmian);
		this.gameApp.libGameViewData.imgFanMians[7] = (MyImageView) findViewById(R.id.wj8_fanmian);

		this.gameApp.libGameViewData.imgZhuangBei[0].imgWuqi = (MyImageView) findViewById(R.id.wj1_Wuqi);
		this.gameApp.libGameViewData.imgZhuangBei[0].imgFangju = (MyImageView) findViewById(R.id.wj1_Fangju);
		this.gameApp.libGameViewData.imgZhuangBei[0].imgJaYiMa = (MyImageView) findViewById(R.id.wj1_JaYiMa);
		this.gameApp.libGameViewData.imgZhuangBei[0].imgJianYiMa = (MyImageView) findViewById(R.id.wj1_JianYiMa);

		this.gameApp.libGameViewData.imgZhuangBei[1].imgWuqi = (MyImageView) findViewById(R.id.wj2_Wuqi);
		this.gameApp.libGameViewData.imgZhuangBei[1].imgFangju = (MyImageView) findViewById(R.id.wj2_Fangju);
		this.gameApp.libGameViewData.imgZhuangBei[1].imgJaYiMa = (MyImageView) findViewById(R.id.wj2_JaYiMa);
		this.gameApp.libGameViewData.imgZhuangBei[1].imgJianYiMa = (MyImageView) findViewById(R.id.wj2_JianYiMa);

		this.gameApp.libGameViewData.imgZhuangBei[2].imgWuqi = (MyImageView) findViewById(R.id.wj3_Wuqi);
		this.gameApp.libGameViewData.imgZhuangBei[2].imgFangju = (MyImageView) findViewById(R.id.wj3_Fangju);
		this.gameApp.libGameViewData.imgZhuangBei[2].imgJaYiMa = (MyImageView) findViewById(R.id.wj3_JaYiMa);
		this.gameApp.libGameViewData.imgZhuangBei[2].imgJianYiMa = (MyImageView) findViewById(R.id.wj3_JianYiMa);

		this.gameApp.libGameViewData.imgZhuangBei[3].imgWuqi = (MyImageView) findViewById(R.id.wj4_Wuqi);
		this.gameApp.libGameViewData.imgZhuangBei[3].imgFangju = (MyImageView) findViewById(R.id.wj4_Fangju);
		this.gameApp.libGameViewData.imgZhuangBei[3].imgJaYiMa = (MyImageView) findViewById(R.id.wj4_JaYiMa);
		this.gameApp.libGameViewData.imgZhuangBei[3].imgJianYiMa = (MyImageView) findViewById(R.id.wj4_JianYiMa);

		this.gameApp.libGameViewData.imgZhuangBei[4].imgWuqi = (MyImageView) findViewById(R.id.wj5_Wuqi);
		this.gameApp.libGameViewData.imgZhuangBei[4].imgFangju = (MyImageView) findViewById(R.id.wj5_Fangju);
		this.gameApp.libGameViewData.imgZhuangBei[4].imgJaYiMa = (MyImageView) findViewById(R.id.wj5_JaYiMa);
		this.gameApp.libGameViewData.imgZhuangBei[4].imgJianYiMa = (MyImageView) findViewById(R.id.wj5_JianYiMa);

		this.gameApp.libGameViewData.imgZhuangBei[5].imgWuqi = (MyImageView) findViewById(R.id.wj6_Wuqi);
		this.gameApp.libGameViewData.imgZhuangBei[5].imgFangju = (MyImageView) findViewById(R.id.wj6_Fangju);
		this.gameApp.libGameViewData.imgZhuangBei[5].imgJaYiMa = (MyImageView) findViewById(R.id.wj6_JaYiMa);
		this.gameApp.libGameViewData.imgZhuangBei[5].imgJianYiMa = (MyImageView) findViewById(R.id.wj6_JianYiMa);

		this.gameApp.libGameViewData.imgZhuangBei[6].imgWuqi = (MyImageView) findViewById(R.id.wj7_Wuqi);
		this.gameApp.libGameViewData.imgZhuangBei[6].imgFangju = (MyImageView) findViewById(R.id.wj7_Fangju);
		this.gameApp.libGameViewData.imgZhuangBei[6].imgJaYiMa = (MyImageView) findViewById(R.id.wj7_JaYiMa);
		this.gameApp.libGameViewData.imgZhuangBei[6].imgJianYiMa = (MyImageView) findViewById(R.id.wj7_JianYiMa);

		this.gameApp.libGameViewData.imgZhuangBei[7].imgWuqi = (MyImageView) findViewById(R.id.wj8_Wuqi);
		this.gameApp.libGameViewData.imgZhuangBei[7].imgFangju = (MyImageView) findViewById(R.id.wj8_Fangju);
		this.gameApp.libGameViewData.imgZhuangBei[7].imgJaYiMa = (MyImageView) findViewById(R.id.wj8_JaYiMa);
		this.gameApp.libGameViewData.imgZhuangBei[7].imgJianYiMa = (MyImageView) findViewById(R.id.wj8_JianYiMa);

		for (int i = 0; i < this.gameApp.libGameViewData.imageWJs.length; i++) {
			this.gameApp.libGameViewData.imgZhuangBei[i].imgWuqi
					.setImageDrawable(R.drawable.zb_wuqi);
			this.gameApp.libGameViewData.imgZhuangBei[i].imgFangju
					.setImageDrawable(R.drawable.zb_fangju);
			this.gameApp.libGameViewData.imgZhuangBei[i].imgJaYiMa
					.setImageDrawable(R.drawable.zb_jiayima);
			this.gameApp.libGameViewData.imgZhuangBei[i].imgJianYiMa
					.setImageDrawable(R.drawable.zb_jianyima);
		}

		this.gameApp.libGameViewData.WJ8ShouPaiArrow[0] = (ImageView) findViewById(R.id.wj8_shouPai1_arrow);
		this.gameApp.libGameViewData.WJ8ShouPaiArrow[1] = (ImageView) findViewById(R.id.wj8_shouPai2_arrow);
		this.gameApp.libGameViewData.WJ8ShouPaiArrow[2] = (ImageView) findViewById(R.id.wj8_shouPai3_arrow);
		this.gameApp.libGameViewData.WJ8ShouPaiArrow[3] = (ImageView) findViewById(R.id.wj8_shouPai4_arrow);
		this.gameApp.libGameViewData.WJ8ShouPaiArrow[4] = (ImageView) findViewById(R.id.wj8_shouPai5_arrow);
		this.gameApp.libGameViewData.WJ8ShouPaiArrow[5] = (ImageView) findViewById(R.id.wj8_shouPai6_arrow);
		this.gameApp.libGameViewData.WJ8ShouPaiArrow[6] = (ImageView) findViewById(R.id.wj8_shouPai7_arrow);
		this.gameApp.libGameViewData.WJ8ShouPaiArrow[7] = (ImageView) findViewById(R.id.wj8_shouPai8_arrow);
		// this.gameApp.libGameViewData.WJ8ShouPaiArrow[8] = (ImageView)
		// findViewById(R.id.wj8_shouPai9_arrow);

		this.gameApp.libGameViewData.WJ8ShouPai[0] = (MyImageView) findViewById(R.id.wj8_shouPai1);
		this.gameApp.libGameViewData.WJ8ShouPai[1] = (MyImageView) findViewById(R.id.wj8_shouPai2);
		this.gameApp.libGameViewData.WJ8ShouPai[2] = (MyImageView) findViewById(R.id.wj8_shouPai3);
		this.gameApp.libGameViewData.WJ8ShouPai[3] = (MyImageView) findViewById(R.id.wj8_shouPai4);
		this.gameApp.libGameViewData.WJ8ShouPai[4] = (MyImageView) findViewById(R.id.wj8_shouPai5);
		this.gameApp.libGameViewData.WJ8ShouPai[5] = (MyImageView) findViewById(R.id.wj8_shouPai6);
		this.gameApp.libGameViewData.WJ8ShouPai[6] = (MyImageView) findViewById(R.id.wj8_shouPai7);
		this.gameApp.libGameViewData.WJ8ShouPai[7] = (MyImageView) findViewById(R.id.wj8_shouPai8);
		// this.gameApp.libGameViewData.WJ8ShouPai[8] = (MyImageView)
		// findViewById(R.id.wj8_shouPai9);

		// this.gameApp.libGameViewData.arrowLeft = (ImageView)
		// findViewById(R.id.shoupai_left_arrow);
		// this.gameApp.libGameViewData.arrowRight = (ImageView)
		// findViewById(R.id.shoupai_right_arrow);

		this.gameApp.libGameViewData.imgZhuangBei[7].imgWuqi
				.setOnClickListener(new MyWJ8WuQiTxtListener(gameApp));

		for (int i = 0; i < this.gameApp.libGameViewData.WJ8ShouPai.length; i++) {
			// this.gameApp.libGameViewData.WJ8ShouPai[i]
			// .setOnClickListener(new MyWJ8ShouPaiImageListener(gameApp));
			this.gameApp.libGameViewData.WJ8ShouPai[i].setLongClickable(true);
			this.gameApp.libGameViewData.WJ8ShouPai[i]
					.setOnTouchListener(new MyWJ8ShouPaiImageListener(gameApp));
		}

		// image just for hole the space
		this.gameApp.libGameViewData.roleSpace1 = (MyImageView) findViewById(R.id.Role_Space1);
		this.gameApp.libGameViewData.roleSpace1
				.setImageDrawable(R.drawable.r_unknown);
		this.gameApp.libGameViewData.roleSpace1.setVisibility(View.INVISIBLE);
	}

	public void resetGameImageView() {
		for (int i = 0; i < this.gameApp.libGameViewData.imageWJs.length; i++) {
			this.gameApp.libGameViewData.imageWJs[i]
					.setImageDrawable(R.drawable.wj_gray);
			this.gameApp.libGameViewData.linearWJs[i]
					.setBackgroundDrawable(this.gameApp.getResources()
							.getDrawable(R.drawable.bg_default));
			this.gameApp.libGameViewData.imageWJs[i]
					.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.bg_black));
			this.gameApp.libGameViewData.imageWJRoles[i]
					.setImageDrawable(R.drawable.r_unknown);
			this.gameApp.libGameViewData.imageWJRoles[i]
					.setVisibility(View.INVISIBLE);
			for (int k = 0; k < this.gameApp.libGameViewData.imageWJBloods[i].img9Bloods.length; k++)
				this.gameApp.libGameViewData.imageWJBloods[i].img9Bloods[k]
						.setVisibility(View.INVISIBLE);
			this.gameApp.libGameViewData.imageWJDPs[i].imgPds[0]
					.setVisibility(View.INVISIBLE);
			this.gameApp.libGameViewData.imageWJDPs[i].imgPds[1]
					.setVisibility(View.INVISIBLE);
			this.gameApp.libGameViewData.imageWJDPs[i].imgPds[2]
					.setVisibility(View.INVISIBLE);
			this.gameApp.libGameViewData.imgWJShouPaiNumber[i]
					.setVisibility(View.INVISIBLE);
			this.gameApp.libGameViewData.imgLianHuans[i]
					.setImageDrawable(R.drawable.st_lianhuan);
			this.gameApp.libGameViewData.imgLianHuans[i]
					.setVisibility(View.INVISIBLE);
			this.gameApp.libGameViewData.imgFanMians[i]
					.setImageDrawable(R.drawable.st_fanmian);
			this.gameApp.libGameViewData.imgFanMians[i]
					.setVisibility(View.INVISIBLE);
			this.gameApp.libGameViewData.imgZhuangBei[i].imgWuqi
					.setImageDrawable(R.drawable.zb_wuqi);
			this.gameApp.libGameViewData.imgZhuangBei[i].imgFangju
					.setImageDrawable(R.drawable.zb_fangju);
			this.gameApp.libGameViewData.imgZhuangBei[i].imgJaYiMa
					.setImageDrawable(R.drawable.zb_jiayima);
			this.gameApp.libGameViewData.imgZhuangBei[i].imgJianYiMa
					.setImageDrawable(R.drawable.zb_jianyima);
		}
		//
		for (int i = 0; i < this.gameApp.libGameViewData.WJ8ShouPai.length; i++) {
			this.gameApp.libGameViewData.WJ8ShouPaiArrow[i]
					.setVisibility(View.INVISIBLE);
			this.gameApp.libGameViewData.WJ8ShouPai[i]
					.setVisibility(View.INVISIBLE);
		}
		//
		// this.gameApp.libGameViewData.arrowLeft.setVisibility(View.INVISIBLE);
		// this.gameApp.libGameViewData.arrowRight.setVisibility(View.INVISIBLE);
		this.gameApp.libGameViewData.mJiNengBtn1.setVisibility(View.INVISIBLE);
		this.gameApp.libGameViewData.mJiNengBtn2.setVisibility(View.INVISIBLE);
		this.gameApp.libGameViewData.mJiNengBtn3.setVisibility(View.INVISIBLE);
		this.gameApp.libGameViewData.mJiNengBtn4.setVisibility(View.INVISIBLE);
		this.gameApp.libGameViewData.mChuPai.setVisibility(View.INVISIBLE);
		this.gameApp.libGameViewData.mFangQi.setVisibility(View.INVISIBLE);
		this.gameApp.libGameViewData.mQiPai.setVisibility(View.INVISIBLE);
		this.gameApp.libGameViewData.mTuoGuan.setVisibility(View.INVISIBLE);
		this.gameApp.libGameViewData.mStartMatch.setVisibility(View.INVISIBLE);
	}

	private class MyReturnButtonListener implements OnClickListener {
		public void onClick(View v) {
			new DebugAddShouPaiDialog(GameActivity.this,
					(GameApp) GameActivity.this.getApplicationContext());
		}
	}

	private class MyStartGameButtonListener implements OnClickListener {
		public void onClick(View v) {
			if (gameApp.gameLogicData.exceptionTrack.trim().length() > 0) {
				new DebugDialog(GameActivity.this,
						((GameApp) getApplicationContext()));
				gameApp.gameLogicData.exceptionTrack = "";
				return;
			}

			//
			GameActivity.this.gameApp.runFromQGame = false;
			gameApp.libGameViewData.mStartMatch.setEnabled(false);
			startNewGame();
			gameApp.libGameViewData.mStartMatch.setEnabled(true);
		}
	}

	public void startNewGame() {
		try {
			this.gameApp.reset();
			this.resetGameImageView();
			this.xiPai();
			this.allocateRole();
			this.faPai();
			this.enableFunctionBtn();
			this.runGame();
		} catch (Exception e) {
			this.gameApp.libGameViewData.logInfo("异常退出:" + e,
					Type.logDelay.Delay);
			this.gameApp.libGameViewData.fileUtil.addContent("curChuPaiWJ="
					+ gameApp.gameLogicData.curChuPaiWJ.dispName
					+ ", curChuPaiCP=" + gameApp.gameLogicData.curChuPaiCP);
			this.gameApp.libGameViewData.fileUtil
					.addContent(ExceptionTraceHelper.getTrace(e));

			this.gameApp.gameLogicData.exceptionTrack = ExceptionTraceHelper
					.getTrace(e);
		}
	}

	public void startNewQGame() {
		try {
			this.resetGameImageView();
			this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView();
			this.gameApp.gameLogicData.wjHelper.auditAllRunningWuJiang();
			this.gameApp.gameLogicData.cpHelper.initCardPaisForQGame();
			this.gameApp.gameLogicData.cpHelper.xiPaiForQGame();
			this.enableFunctionBtn();
			this.runQGame();
			return;
		} catch (Exception e) {
			this.gameApp.libGameViewData.logInfo("异常退出:" + e,
					Type.logDelay.Delay);
			this.gameApp.libGameViewData.fileUtil.addContent("curChuPaiWJ="
					+ gameApp.gameLogicData.curChuPaiWJ.dispName
					+ ", curChuPaiCP=" + gameApp.gameLogicData.curChuPaiCP);
			this.gameApp.libGameViewData.fileUtil
					.addContent(ExceptionTraceHelper.getTrace(e));

			this.gameApp.gameLogicData.exceptionTrack = ExceptionTraceHelper
					.getTrace(e);
		}
	}

	public void setLinearWuJiangVisible(int[] IDs, int visible) {
		for (int i = 0; i < IDs.length; i++)
			this.gameApp.libGameViewData.linearWJs[IDs[i]]
					.setVisibility(visible);
	}

	public void allocateRole() {
		if (this.gameApp.settingsViewData.gameType == GameType.g_1v1) {
			// int[] visibleWJs = { 1, 7 };
			// int[] inVisibleWJs = { 0, 2, 3, 4, 5, 6 };
			// this.setLinearWuJiangVisible(visibleWJs, View.VISIBLE);
			// this.setLinearWuJiangVisible(inVisibleWJs, View.INVISIBLE);

			gameApp.libGameViewData.logInfo("1V1开局", Type.logDelay.Delay);
			this.gameApp.gameLogicData.wjHelper.allocateRoleFor2();

		} else if (this.gameApp.settingsViewData.gameType == GameType.g_5_people) {
			// int[] visibleWJs = { 3, 4, 5, 6, 7 };
			// int[] inVisibleWJs = { 0, 1, 2 };
			// this.setLinearWuJiangVisible(visibleWJs, View.VISIBLE);
			// this.setLinearWuJiangVisible(inVisibleWJs, View.INVISIBLE);

			gameApp.libGameViewData.logInfo("5人开局", Type.logDelay.Delay);
			this.gameApp.gameLogicData.wjHelper.allocateRoleFor5();

		} else if (this.gameApp.settingsViewData.gameType == GameType.g_81_people) {
			// int[] visibleWJs = { 0, 1, 2, 3, 4, 5, 6, 7 };
			// this.setLinearWuJiangVisible(visibleWJs, View.VISIBLE);

			gameApp.libGameViewData.logInfo("8人开局", Type.logDelay.Delay);
			this.gameApp.gameLogicData.wjHelper.allocateRoleFor81();

		} else {
			// by default is 1v1
			this.gameApp.gameLogicData.wjHelper.allocateRoleFor2();
		}
	}

	public void xiPai() {
		this.gameApp.gameLogicData.cpHelper.initCardPais();
		this.gameApp.gameLogicData.cpHelper.xiPai();
	}

	public void faPai() {
		this.gameApp.gameLogicData.cpHelper.faPai();
	}

	public void enableFunctionBtn() {
		// enable all function Btn
		this.gameApp.libGameViewData.mChuPai.setVisibility(View.VISIBLE);
		this.gameApp.libGameViewData.mFangQi.setVisibility(View.VISIBLE);
		this.gameApp.libGameViewData.mQiPai.setVisibility(View.VISIBLE);
		this.gameApp.libGameViewData.mTuoGuan.setVisibility(View.VISIBLE);
		this.gameApp.libGameViewData.mStartMatch.setVisibility(View.VISIBLE);

		// enable myWuJiang JiNeng Btn
		if (this.gameApp.gameLogicData.myWuJiang != null)
			this.gameApp.gameLogicData.myWuJiang.enableWuJiangJiNengBtn();
	}

	public void runGame() {
		WuJiang zhuGong = this.gameApp.gameLogicData.wjHelper
				.getZhuGong(this.gameApp.gameLogicData.wuJiangs);
		WuJiang curWJ = zhuGong;
		int huiHe = 1;
		while (!this.gameApp.gameLogicData.wjHelper.checkMatchOver()) {
			if (curWJ.equals(zhuGong)) {
				this.gameApp.libGameViewData.logInfo("第" + (huiHe++) + "回合:",
						Type.logDelay.Delay);
			}

			curWJ.run();
			curWJ = this.gameApp.gameLogicData.wjHelper
					.findNextOne(curWJ.imageViewIndex);
		}

		// match over
		this.LogMatchOver();
	}

	public void runQGame() {
		WuJiang curWJ = this.gameApp.gameLogicData.myWuJiang;
		int huiHe = 1;
		while (!this.gameApp.gameLogicData.wjHelper.checkMatchOver()) {
			if (curWJ.equals(this.gameApp.gameLogicData.myWuJiang)) {
				this.gameApp.libGameViewData.logInfo("第" + (huiHe++) + "回合:",
						Type.logDelay.Delay);
			}

			curWJ.run();
			curWJ = this.gameApp.gameLogicData.wjHelper
					.findNextOne(curWJ.imageViewIndex);
		}

		// match over
		this.LogMatchOver();
	}

	public void LogMatchOver() {
		if (!this.gameApp.gameLogicData.gameOver)
			return;

		if (gameApp.gameLogicData.userExit) {
			this.gameApp.libGameViewData.logInfo("用户退出游戏", Type.logDelay.Delay);
			return;
		}

		if (this.gameApp.gameLogicData.zhuGongWuJiang.state == Type.State.Dead) {
			this.gameApp.libGameViewData.logInfo("主公死亡,游戏结束.",
					Type.logDelay.Delay);
		} else {
			this.gameApp.libGameViewData.logInfo("反贼和内奸都被消灭,游戏结束",
					Type.logDelay.Delay);
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				|| keyCode == KeyEvent.KEYCODE_HOME) {

			if (this.gameApp.gameLogicData.userExit
					&& !this.gameApp.gameLogicData.gameOver) {
				// had been ask user to exit but game is till running
				// consume this msg and wait until game is over
				return false;
			}

			if (!this.gameApp.gameLogicData.userExit
					&& !this.gameApp.gameLogicData.gameOver) {
				// query user exit or not
				this.gameApp.ynData.reset();
				this.gameApp.ynData.result = false;
				this.gameApp.ynData.okTxt = "确认";
				this.gameApp.ynData.cancelTxt = "取消";
				this.gameApp.ynData.genInfo = "是否退出游戏?";

				new NonBlockYesNoDialog(GameActivity.this,
						((GameApp) getApplicationContext()));

				if (this.gameApp.gameLogicData.userExit) {
					// tell the game is over due to use exit
					// send message to UI to terminate
					if (this.gameApp.gameLogicData.userInterface.loop) {
						this.gameApp.gameLogicData.userInterface
								.sendMessageToUIForWakeUp();
					}

					return false;
				} else {
					// consume this message
					return false;
				}
			}
		}

		return super.onKeyDown(keyCode, event);
	}

	public void sendMessageStartNewGame() {
		Message localMessage = this.gameApp.mainHandler.obtainMessage();
		localMessage.what = 100;
		this.gameApp.mainHandler.sendMessage(localMessage);
	}

	public void sendMessageStartNewQGame() {
		Message localMessage = this.gameApp.mainHandler.obtainMessage();
		localMessage.what = 200;
		this.gameApp.mainHandler.sendMessage(localMessage);
	}

	public void sendMessageToUIForWakeUp() {
		gameApp.gameLogicData.userInterface.sendMessageToUIForWakeUp();
	}
}
