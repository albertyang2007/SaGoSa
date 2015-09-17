package alben.sgs.type;

import alben.sgs.cardpai.FangJuCardPai;
import alben.sgs.cardpai.MaCardPai;
import alben.sgs.cardpai.WuQiCardPai;

public class WuJiangZhuangBei {
	public WuQiCardPai wuQi = null;
	public FangJuCardPai fangJu = null;
	public MaCardPai jiaYiMa = null;
	public MaCardPai jianYiMa = null;

	public void reset() {
		if (this.wuQi != null) {
			this.wuQi.reset();
			this.wuQi = null;
		}
		if (this.fangJu != null) {
			this.fangJu.reset();
			this.fangJu = null;
		}

		if (this.jiaYiMa != null) {
			this.jiaYiMa.reset();
			this.jiaYiMa = null;
		}

		if (this.jianYiMa != null) {
			this.jianYiMa.reset();
			this.jianYiMa = null;
		}
	}

	public boolean containsZB() {
		if (this.wuQi != null)
			return true;
		if (this.fangJu != null)
			return true;
		if (this.jiaYiMa != null)
			return true;
		if (this.jianYiMa != null)
			return true;
		return false;
	}
}
