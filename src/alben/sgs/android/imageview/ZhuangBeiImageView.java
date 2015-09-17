package alben.sgs.android.imageview;

import android.content.Context;
import android.util.AttributeSet;

public class ZhuangBeiImageView extends MyImageView {
	public ZhuangBeiImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// 30X15
		this.widthPer = (float) (30.0f / this.designWith);
		this.heightPer = (float) (15.0f / this.designHight);
	}
}
