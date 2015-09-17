package alben.sgs.event;

import java.util.EventObject;

import alben.sgs.cardpai.CardPai;
import alben.sgs.wujiang.WuJiang;

public class ChuPaiEvent extends EventObject {
	public static final long serialVersionUID = 0;
	Object obj;
	public WuJiang srcWuJiang = null;
	public CardPai srcCardPai = null;

	public WuJiang tarWuJiang = null;
	public CardPai tarCardPai = null;

	public ChuPaiEvent(Object o) {
		super(o);
		this.obj = o;
	}

	public ChuPaiEvent(Object o, WuJiang swj, CardPai scp, WuJiang twj,
			CardPai tcp) {
		super(o);
		this.obj = o;
		this.srcWuJiang = swj;
		this.srcCardPai = scp;

		this.tarWuJiang = twj;
		this.tarCardPai = tcp;
	}

	public String toString() {

		if (this.srcWuJiang != null && this.srcCardPai != null) {
			return this.srcWuJiang.dispName + "µÄ" + this.srcCardPai.dispName;
		}

		if (this.tarWuJiang != null && this.tarCardPai != null) {
			return this.tarWuJiang.dispName + "µÄ" + this.tarCardPai.dispName;
		}

		String s1 = ((this.srcWuJiang == null) ? "" : this.srcWuJiang.dispName);
		String s2 = ((this.srcCardPai == null) ? "" : this.srcCardPai.dispName);
		String s3 = ((this.tarWuJiang == null) ? "" : this.tarWuJiang.dispName);
		String s4 = ((this.tarCardPai == null) ? "" : this.tarCardPai.dispName);
		return s1 + "," + s2 + "," + s3 + "," + s4;
	}
}