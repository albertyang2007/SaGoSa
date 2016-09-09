package alben.sgs.android.imageview;

import android.content.Context;
import android.util.AttributeSet;

public class CenterImageView extends MyImageView {
	public CenterImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// 15 is for function btn
		this.widthPer = (float) (192.0f / this.designWith);
		this.heightPer = (float) (80.0f / this.designHight);
	}
}
