package alben.sgs.event;

import alben.sgs.wujiang.WuJiang;

public class TaoEvent extends ChuPaiEvent {
	public static final long serialVersionUID = 0;

	public int yiChuTaoNumber = 0;
	public int requestTaoNumber = 0;

	public TaoEvent(Object o) {
		super(o);
	}

	public TaoEvent(Object o, WuJiang swj, int taoN) {
		super(o);
		this.srcWuJiang = swj;
		this.requestTaoNumber = taoN;
	}
}
