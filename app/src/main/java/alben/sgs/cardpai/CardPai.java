package alben.sgs.cardpai;

import alben.sgs.android.GameApp;
import alben.sgs.android.R;
import alben.sgs.type.Type;
import alben.sgs.type.Type.GameType;
import alben.sgs.wujiang.WuJiang;

public class CardPai {
	public Type.CardPai name = Type.CardPai.nil;
	public Type.CardPaiClass clas = Type.CardPaiClass.nil;
	public int number = 0;
	public int imageNumber = 0;
	public boolean selectedByClick = false;
	public String dispName = "";
	public long ID = 0;// wei yi
	public static long generateID = 0;
	public boolean linkImpact = false;
	public Type.CPState cpState = Type.CPState.nil;

	public WuJiang belongToWuJiang = null;
	public WuJiang shangHaiSrcWJ = null;
	public WuJiang tarWJForAI = null;

	public int selectTarWJNumber = 0;
	public int shangHaiN = -1;
	public String shangHaiReason = "";

	public boolean countLinkImpactCompleted = false;

	public GameApp gameApp = null;

	public CardPai(Type.CardPai na, Type.CardPaiClass c, int n, int imgNumber) {
		this.name = na;
		this.clas = c;
		this.number = n;
		this.ID = generateID++;
		this.imageNumber = imgNumber;
		// this.imageNumber = R.drawable.card_back;
	}

	// no imageNumber for jiNeng cardpai
	public CardPai(Type.CardPai na, Type.CardPaiClass c, int n) {
		this.name = na;
		this.clas = c;
		this.number = n;
		this.ID = generateID++;
		this.imageNumber = R.drawable.card_back;
	}

	public boolean equals(CardPai tarCP) {
		return this.ID == tarCP.ID;
	}

	public CardPai copy() {
		CardPai copy = new CardPai(this.name, this.clas, this.number,
				this.imageNumber);
		copy.belongToWuJiang = this.belongToWuJiang;
		copy.cpState = this.cpState;
		copy.linkImpact = this.linkImpact;
		copy.gameApp = this.gameApp;
		copy.imageNumber = this.imageNumber;
		copy.dispName = this.dispName;
		copy.ID = this.ID;
		copy.selectTarWJNumber = this.selectTarWJNumber;
		copy.shangHaiN = this.shangHaiN;
		copy.countLinkImpactCompleted = this.countLinkImpactCompleted;
		return copy;
	}

	public void reset() {
		this.selectedByClick = false;
		this.belongToWuJiang = null;
		this.shangHaiSrcWJ = null;
		this.tarWJForAI = null;
		this.cpState = Type.CPState.nil;
		this.shangHaiN = -1;
		this.shangHaiReason = "";
		this.countLinkImpactCompleted = false;
	}

	public String toString() {
		return this.dispName + " [" + this.formatHuaShi() + " " + this.number
				+ "]";
	}

	public String formatHuaShi() {
		if (this.clas == Type.CardPaiClass.FangPian)
			return "方片";
		else if (this.clas == Type.CardPaiClass.HeiTao)
			return "黑桃";
		else if (this.clas == Type.CardPaiClass.HongTao)
			return "红桃";
		else if (this.clas == Type.CardPaiClass.MeiHua)
			return "梅花";
		return "未知";
	}

	public String formatDispName() {
		String rtn = "";
		char[] ch = this.dispName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			rtn += ch[i] + "\n";
		}
		return rtn;
	}

	public void setBelongToWuJiang(WuJiang wj) {
		this.belongToWuJiang = wj;
	}

	public boolean work(WuJiang srcWJ, WuJiang tarWJ, CardPai tarCP) {
		return false;
	}

	public boolean canChuPai() {
		return true;
	}

	// click this cp and show which wj can be select
	public void onClickUpdateView() {
	}

	public void onUnClickUpdateView() {
	}

	// click wj and set data
	public void onClickWJUpdateView(WuJiang curClickWJ) {

	}

	// un-click wj and re-set data
	public void onUnClickWJUpdateView(WuJiang curClickWJ) {

	}

	public void selectTarWJForAI() {
		// run and select one target wujiang for this cardpai
		// by default will select one oppt
		if (this.belongToWuJiang != null) {

			this.tarWJForAI = null;

			if (this.gameApp.settingsViewData.gameType == GameType.g_1v1) {
				this.tarWJForAI = this.belongToWuJiang.nextOne;
			}

			if (this.belongToWuJiang.opponentList.size() > 0) {
				this.tarWJForAI = this.belongToWuJiang.opponentList.get(0);
			}
		} else {
			this.gameApp.libGameViewData.logInfo("Error:卡牌不属于任何武将" + this,
					Type.logDelay.Delay);
		}
	}

	public WuJiang getTarWJForAI() {
		return this.tarWJForAI;
	}

	public int countTotalShangHaiN(WuJiang tarWJ) {
		return this.shangHaiN;
	}
}
