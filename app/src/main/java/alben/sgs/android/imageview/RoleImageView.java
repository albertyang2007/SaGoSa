package alben.sgs.android.imageview;

import android.content.Context;
import android.util.AttributeSet;

public class RoleImageView extends MyImageView {
	public RoleImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// 19.5X8
		this.widthPer = (float) (19.5f / this.designWith);
		this.heightPer = (float) (8.0f / this.designHight);
	}
}
