package alben.sgs.event;

import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.JinNangCardPai;
import alben.sgs.wujiang.WuJiang;

public class WuXieKeJiEvent extends ChuPaiEvent {
	public static final long serialVersionUID = 0;

	public int yiChuWuXieKeJiNumber = 0;
	public int eventImpactN = 0;

	// 是否对我有利
	public boolean isNotOkForMe() {
		return yiChuWuXieKeJiNumber % 2 == eventImpactN;
	}

	// 是否对对手有利
	public boolean isOkForOppt() {
		return yiChuWuXieKeJiNumber % 2 != eventImpactN;
	}

	public WuXieKeJiEvent(Object o) {
		super(o);
	}

	public WuXieKeJiEvent(Object o, WuJiang swj, CardPai scp, WuJiang twj,
			CardPai tcp) {
		super(o, swj, scp, twj, tcp);

		if (scp instanceof JinNangCardPai) {
			JinNangCardPai jncp = (JinNangCardPai) scp;
			this.eventImpactN = jncp.eventImpactN;
		}

	}
}
