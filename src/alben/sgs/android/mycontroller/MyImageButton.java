package alben.sgs.android.mycontroller;

import alben.sgs.type.Type;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.ImageButton;

public class MyImageButton extends ImageButton {
	private String text = "Unknown";
	private int color = Color.BLACK;
	private String familyName = "ºÚÌå";
	private Typeface font = Typeface.create(familyName, Typeface.NORMAL);
	private int x = 15;
	private int y = 12;

	public MyImageButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setXYMode(Type.DisplayMode.Small);
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setColor(int color) {
		this.color = color;
	}

	// if the Background is btn_bg1
	// x=18;y=15
	// if the Background is btn_bg_long
	// x=32;y=15
	public void setXYMode(Type.DisplayMode dis) {
		if (dis == Type.DisplayMode.Small) {
			this.x = 15;
			this.y = 12;
		} else if (dis == Type.DisplayMode.Middle) {
			this.x = 20;
			this.y = 12;
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint paint = new Paint();
		paint.setTypeface(this.font);
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setColor(this.color);
		canvas.drawText(this.text, this.x, this.y, paint);
	}
}
