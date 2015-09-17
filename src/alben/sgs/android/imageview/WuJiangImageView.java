package alben.sgs.android.imageview;

import android.content.Context;
import android.util.AttributeSet;

public class WuJiangImageView extends MyImageView {
	public WuJiangImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// 60X40
		this.widthPer = (float) (60.0f / this.designWith);
		this.heightPer = (float) (40.0f / this.designHight);
	}
}
