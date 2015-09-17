package alben.sgs.android.imageview;

import android.content.Context;
import android.util.AttributeSet;

public class BloodImageView extends MyImageView {
	public BloodImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// 4.5X8
		this.widthPer = (float) (4.5f / this.designWith);
		this.heightPer = (float) (8.0f / this.designHight);
	}
}
