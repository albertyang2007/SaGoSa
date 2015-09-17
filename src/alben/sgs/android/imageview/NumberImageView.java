package alben.sgs.android.imageview;

import android.content.Context;
import android.util.AttributeSet;

public class NumberImageView extends MyImageView {
	public NumberImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// 14X14
		this.widthPer = (float) (14.0f / this.designWith);
		this.heightPer = (float) (14.0f / this.designHight);
	}
}
