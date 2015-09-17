package alben.sgs.android.imageview;

import alben.sgs.android.GameApp;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.widget.ImageView;

public class DisplayHelper {
	public GameApp gameApp = null;
	public DisplayMetrics dm = null;
	public int screenWidth = dm.widthPixels;// ÆÁÄ»¿í¶È£¨ÏñËØ£© 240
	public int screenHeight = dm.heightPixels;// ÆÁÄ»¸ß¶È£¨ÏñËØ£© 320
	public float density = dm.density; // ÆÁÄ»ÃÜ¶È£¨0.75 / 1.0 / 1.5£© 0.75
	public int densityDpi = dm.densityDpi; // ÆÁÄ»ÃÜ¶ÈDPI£¨120 / 160 / 240£©120

	public DisplayHelper(GameApp g) {
		this.gameApp = g;
		this.dm = this.gameApp.libGameViewData.dm;
		this.screenWidth = this.dm.widthPixels;
		this.screenHeight = this.dm.heightPixels;
		this.density = this.dm.density;
		this.densityDpi = this.dm.densityDpi;
	}

	public void adoptImageView(ImageView imgView, int id) {
		Bitmap bm = BitmapFactory.decodeStream(this.gameApp.getResources()
				.openRawResource(id));
		Matrix matrix = new Matrix();
		matrix.postScale(1.0F, 1.0F);
		imgView.setImageBitmap(bm);
	}
}
