package alben.sgs.android;

import alben.sgs.android.imageview.MyImageView;
import alben.sgs.android.mycontroller.MyImageButton;
import alben.sgs.android.mycontroller.QGameBtnController;
import alben.sgs.android.mycontroller.QGameWuJiangImageListener;
import alben.sgs.cardpai.CardPaiHelper;
import alben.sgs.qgame.QGameXMLHelper;
import alben.sgs.ui.UserInterface;
import alben.sgs.wujiang.WuJiang;
import alben.sgs.wujiang.WuJiangHelper;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class QGameActivity extends Activity {
	public GameApp gameApp = null;
	public Handler mainHandler = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);

		setContentView(R.layout.main_l);

		gameApp = ((GameApp) getApplicationContext());
		gameApp.gameActivityContext = QGameActivity.this;
		this.gameApp.libGameViewData.fileUtil.ctx = QGameActivity.this;
		getWindowManager().getDefaultDisplay().getMetrics(
				this.gameApp.libGameViewData.dm);
		this.gameApp.libGameViewData.display
				.setDisplay(this.gameApp.libGameViewData.dm);

		this.initViewController();

		this.gameApp.gameLogicData.cpHelper = new CardPaiHelper(gameApp);
		this.gameApp.gameLogicData.wjHelper = new WuJiangHelper(gameApp);
		this.gameApp.gameLogicData.userInterface = new UserInterface(gameApp);

		this.resetImageView();

		this.mainHandler = new Handler() {
			public void handleMessage(Message paramMessage) {
				if (paramMessage.what == 300) {
					new QGameXMLHelper(QGameActivity.this.gameApp)
							.loadQGameFromXMLFile(QGameActivity.this.gameApp.settingsViewData.qgameXMLID);
					QGameActivity.this.gameApp.gameLogicData.wjHelper
							.updateWuJiangToLibGameView();
				}
			}
		};
		//
		if (this.gameApp.settingsViewData.qgameXMLID != 0)
			sendMessageOpenQGame();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	public void initViewController() {

		this.gameApp.libGameViewData.firstView = findViewById(R.id.first_view);
		this.gameApp.libGameViewData.mInfoView = (TextView) findViewById(R.id.center_info);
		this.gameApp.libGameViewData.mInfoImg = (MyImageView) findViewById(R.id.center_img);
		this.gameApp.libGameViewData.mInfoImg
				.setImageDrawable(R.drawable.bg_center_info);

		this.gameApp.libGameViewData.mChuPai = (MyImageView) findViewById(R.id.ChuPai);
		this.gameApp.libGameViewData.mChuPai
				.setImageDrawable(R.drawable.btn_qgame_open);

		this.gameApp.libGameViewData.mFangQi = (MyImageView) findViewById(R.id.FangQi);
		this.gameApp.libGameViewData.mFangQi
				.setImageDrawable(R.drawable.btn_qgame_save);

		this.gameApp.libGameViewData.mQiPai = (MyImageView) findViewById(R.id.QiPai);
		this.gameApp.libGameViewData.mQiPai
				.setImageDrawable(R.drawable.btn_qgame_run);

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

		this.gameApp.libGameViewData.linearJinengBtn = (LinearLayout) findViewById(R.id.linear_jineng_btn);

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
				.setOnTouchListener(new QGameBtnController(this.gameApp));
		this.gameApp.libGameViewData.mFangQi
				.setOnTouchListener(new QGameBtnController(this.gameApp));
		this.gameApp.libGameViewData.mQiPai
				.setOnClickListener(new MyStartQGameButtonListener());

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
					.setOnClickListener(new QGameWuJiangImageListener(gameApp));
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
		//this.gameApp.libGameViewData.WJ8ShouPaiArrow[8] = (ImageView) findViewById(R.id.wj8_shouPai9_arrow);

		this.gameApp.libGameViewData.WJ8ShouPai[0] = (MyImageView) findViewById(R.id.wj8_shouPai1);
		this.gameApp.libGameViewData.WJ8ShouPai[1] = (MyImageView) findViewById(R.id.wj8_shouPai2);
		this.gameApp.libGameViewData.WJ8ShouPai[2] = (MyImageView) findViewById(R.id.wj8_shouPai3);
		this.gameApp.libGameViewData.WJ8ShouPai[3] = (MyImageView) findViewById(R.id.wj8_shouPai4);
		this.gameApp.libGameViewData.WJ8ShouPai[4] = (MyImageView) findViewById(R.id.wj8_shouPai5);
		this.gameApp.libGameViewData.WJ8ShouPai[5] = (MyImageView) findViewById(R.id.wj8_shouPai6);
		this.gameApp.libGameViewData.WJ8ShouPai[6] = (MyImageView) findViewById(R.id.wj8_shouPai7);
		this.gameApp.libGameViewData.WJ8ShouPai[7] = (MyImageView) findViewById(R.id.wj8_shouPai8);
		//this.gameApp.libGameViewData.WJ8ShouPai[8] = (MyImageView) findViewById(R.id.wj8_shouPai9);

		//this.gameApp.libGameViewData.arrowLeft = (ImageView) findViewById(R.id.shoupai_left_arrow);
		//this.gameApp.libGameViewData.arrowRight = (ImageView) findViewById(R.id.shoupai_right_arrow);
		
		// image just for hole the space
		this.gameApp.libGameViewData.roleSpace1 = (MyImageView) findViewById(R.id.Role_Space1);
		this.gameApp.libGameViewData.roleSpace1
				.setImageDrawable(R.drawable.r_unknown);
		this.gameApp.libGameViewData.roleSpace1.setVisibility(View.INVISIBLE);
	}

	public void resetImageView() {
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
			// for (int k = 0; k <
			// this.gameApp.libGameViewData.txtWJLostBloods.length; k++) {
			// this.gameApp.libGameViewData.txtWJLostBloods[k]
			// .setVisibility(View.INVISIBLE);
			// }
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
		//this.gameApp.libGameViewData.arrowLeft.setVisibility(View.INVISIBLE);
		//this.gameApp.libGameViewData.arrowRight.setVisibility(View.INVISIBLE);
		//
		for (int i = 0; i < this.gameApp.gameLogicData.wuJiangs.size(); i++)
			((WuJiang) this.gameApp.gameLogicData.wuJiangs.get(i)).reset();
		//
		this.gameApp.libGameViewData.mTuoGuan.setVisibility(View.INVISIBLE);
		this.gameApp.libGameViewData.mStartMatch.setVisibility(View.INVISIBLE);
		this.gameApp.libGameViewData.mReturn.setVisibility(View.INVISIBLE);
		this.gameApp.libGameViewData.mRenDe.setVisibility(View.INVISIBLE);
		this.gameApp.libGameViewData.linearJinengBtn
				.setVisibility(View.INVISIBLE);
		//
		this.gameApp.reset();
	}

	public void sendMessageOpenQGame() {
		Message localMessage = this.mainHandler.obtainMessage();
		localMessage.what = 300;
		this.mainHandler.sendMessage(localMessage);
	}

	private void startQGame() {
		this.gameApp.runFromQGame = true;
		startActivity(new Intent(this, GameActivity.class));
	}

	private class MyStartQGameButtonListener implements View.OnClickListener {
		public void onClick(View paramView) {
			if (gameApp.gameLogicData.wjHelper.qgameSanityCheck()) {
				// gameApp.libGameViewData.mStartMatch.setEnabled(false);
				startQGame();
				// gameApp.libGameViewData.mStartMatch.setEnabled(true);
			}
		}
	}
}
