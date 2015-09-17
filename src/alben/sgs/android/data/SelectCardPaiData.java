package alben.sgs.android.data;

import alben.sgs.cardpai.CardPai;

public class SelectCardPaiData {
	public int selectCPNumber = 0;
	public CardPai selectedCP1 = null;
	public CardPai selectedCP2 = null;
	public CardPai selectedCP3 = null;
	public CardPai[] CPForSelect = { null, null, null, null, null, null, null,
			null };

	public void reset() {
		this.selectedCP1 = null;
		this.selectedCP2 = null;
		this.selectedCP3 = null;
		this.selectCPNumber = 0;
		for (int i = 0; i < this.CPForSelect.length; i++)
			this.CPForSelect[i] = null;
	}
}
