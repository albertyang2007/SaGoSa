package alben.sgs.android.imageview;

import android.content.Context;
import android.util.AttributeSet;

public class JiNengImageView extends MyImageView {
	public JiNengImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// 25X15
		this.widthPer = (float) (25.0f / this.designWith);
		this.heightPer = (float) (15.0f / this.designHight);
	}
}
