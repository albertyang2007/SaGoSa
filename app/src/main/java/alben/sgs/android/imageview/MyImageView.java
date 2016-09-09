package alben.sgs.android.imageview;

import alben.sgs.android.GameApp;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class MyImageView extends ImageView {
	public GameApp gameApp = null;
	public float widthPer = 1.0f;// 图像宽占整个屏幕的百分比
	public float heightPer = 1.0f;// 图像高占整个屏幕的百分比

	public float designWith = 320.0f;//
	public float designHight = 250.0f;//

	public MyImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.gameApp = (GameApp) context.getApplicationContext();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}

	public void setImageDrawable(Drawable drawable) {
		super.setImageDrawable(drawable);
	}

	public void setImageDrawable(int id) {
		Bitmap origBM = BitmapFactory.decodeStream(getResources()
				.openRawResource(id));

		int newWidth = (int) (this.gameApp.libGameViewData.display.screenWidth * widthPer);
		int newHeight = (int) (this.gameApp.libGameViewData.display.screenHeight * heightPer);
		float wf = (float) ((newWidth * 1.0f) / (origBM.getWidth() * 1.0f));
		float hf = (float) ((newHeight * 1.0f) / (origBM.getHeight() * 1.0f));

		Matrix matrix = new Matrix();
		matrix.postScale(wf, hf);

		Bitmap newBM = Bitmap.createBitmap(origBM, 0, 0, origBM.getWidth(),
				origBM.getHeight(), matrix, true);
		this.setImageBitmap(newBM);
	}
}
