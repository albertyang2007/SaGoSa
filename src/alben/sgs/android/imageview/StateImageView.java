package alben.sgs.android.imageview;

import android.content.Context;
import android.util.AttributeSet;

public class StateImageView extends MyImageView {
	public StateImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// 20X25
		this.widthPer = (float) (20.0f / this.designWith);
		this.heightPer = (float) (25.0f / this.designHight);
	}
}
