package alben.sgs.android.imageview;

import android.content.Context;
import android.util.AttributeSet;

public class CardPaiImageView extends MyImageView {
	public CardPaiImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// 55X75->50X67.5
		this.widthPer = (float) (48.0f / this.designWith);
		this.heightPer = (float) (67.5f / this.designHight);
	}
}
