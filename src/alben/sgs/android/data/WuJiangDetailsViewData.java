package alben.sgs.android.data;

import java.util.ArrayList;

import alben.sgs.cardpai.CardPai;
import alben.sgs.wujiang.WuJiang;

public class WuJiangDetailsViewData {
	public WuJiang selectedWJ = null;
	public CardPai selectedCardPai1 = null;
	public CardPai selectedCardPai2 = null;
	public int selectedCardNumber = 0;
	public boolean selectedCardN1Or2 = false;
	public boolean selectedCardAtLeast1 = false;
	public boolean canViewShouPai = false;
	public boolean requestTwoCPSameColor = true;// just for LuanJi
	public ArrayList<CardPai> selectedCardPaiList = new ArrayList<CardPai>();

	public void reset() {
		this.selectedWJ = null;
		this.selectedCardPai1 = null;
		this.selectedCardPai2 = null;
		this.selectedCardNumber = 0;
		this.canViewShouPai = false;
		this.selectedCardN1Or2 = false;
		this.selectedCardAtLeast1 = false;
		this.requestTwoCPSameColor = false;
		while (this.selectedCardPaiList.size() > 0) {
			this.selectedCardPaiList.remove(0);
		}
	}
}
