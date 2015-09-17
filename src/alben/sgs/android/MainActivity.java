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

import alben.sgs.android.dialog.SettingsDialog;
import alben.sgs.android.mycontroller.MyImageButton;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	public ImageButton imgBtn = null;
	public TextView txtV = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.index);

		MyImageButton invisibleBtn1 = (MyImageButton) findViewById(R.id.invisible_btn1);
		invisibleBtn1.setVisibility(View.INVISIBLE);
		MyImageButton invisibleBtn2 = (MyImageButton) findViewById(R.id.invisible_btn2);
		invisibleBtn2.setVisibility(View.INVISIBLE);
		MyImageButton invisibleBtn3 = (MyImageButton) findViewById(R.id.invisible_btn3);
		invisibleBtn3.setVisibility(View.INVISIBLE);
		MyImageButton invisibleBtn4 = (MyImageButton) findViewById(R.id.invisible_btn4);
		invisibleBtn4.setVisibility(View.INVISIBLE);

		ImageView settingsBtn = (ImageView) findViewById(R.id.settings);
		settingsBtn.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN: {
					v.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.btn_setting));
					break;
				}
				case MotionEvent.ACTION_UP: {
					v.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.btn_setting));
					settings();
					break;
				}
				default:
					break;
				}
				return true;
			}
		});

		ImageView setupQGameBtn = (ImageView) findViewById(R.id.qgame_setup);
		setupQGameBtn.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN: {
					v.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.btn_qgame_setup));
					break;
				}
				case MotionEvent.ACTION_UP: {
					v.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.btn_qgame_setup));
					setupQGame();
					break;
				}
				default:
					break;
				}
				return true;
			}
		});

		ImageView startGameBtn = (ImageView) findViewById(R.id.startGame);
		startGameBtn.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN: {
					v.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.btn_start));
					break;
				}
				case MotionEvent.ACTION_UP: {
					v.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.btn_start));
					startGame();
					break;
				}
				default:
					break;
				}
				return true;
			}
		});
	}

	private void startGame() {
		Intent i = new Intent(this, GameActivity.class);
		startActivity(i);
	}

	private void settings() {
		new SettingsDialog(MainActivity.this,
				((GameApp) getApplicationContext()));
	}

	private void setupQGame() {
		Intent i = new Intent(this, QGameActivity.class);
		startActivity(i);
	}
}