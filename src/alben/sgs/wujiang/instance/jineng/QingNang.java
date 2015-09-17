package alben.sgs.wujiang.instance.jineng;

import alben.sgs.android.R;
import alben.sgs.cardpai.CardPai;
import alben.sgs.type.Type;
import alben.sgs.wujiang.WuJiang;

public class QingNang extends CardPai {
	public CardPai sp1 = null;

	public QingNang(Type.CardPai na, Type.CardPaiClass c, int n) {
		super(na, c, n);
		this.dispName = "青囊";
		this.ID = generateID++;
		this.cpState = Type.CPState.nil;
		this.name = Type.CardPai.WJJiNeng;
		this.shangHaiN = 1;
		this.selectTarWJNumber = 1;
	}

	public boolean work(WuJiang srcWJ, WuJiang tarWJ, CardPai tarCP) {

		srcWJ.oneTimeJiNengTrigger = true;

		this.sp1.belongToWuJiang = null;
		this.sp1.cpState = Type.CPState.FeiPaiDui;
		srcWJ.detatchCardPaiFromShouPai(this.sp1);

		this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "["
				+ this.dispName + "]" + this.sp1 + tarWJ.dispName,
				Type.logDelay.Delay);

		this.shangHaiSrcWJ = srcWJ;
		tarWJ.increaseBlood(srcWJ, this);
		return true;
	}

	// overwrite
	public void selectTarWJForAI() {
		if (this.belongToWuJiang != null) {

			this.tarWJForAI = null;

			if (this.belongToWuJiang.blood < this.belongToWuJiang.getMaxBlood()) {
				this.tarWJForAI = this.belongToWuJiang;
				return;
			}

			if (this.belongToWuJiang
					.isFriend(this.gameApp.gameLogicData.zhuGongWuJiang)) {
				if (this.gameApp.gameLogicData.zhuGongWuJiang.blood < this.gameApp.gameLogicData.zhuGongWuJiang
						.getMaxBlood()) {
					this.tarWJForAI = this.gameApp.gameLogicData.zhuGongWuJiang;
					return;
				}
			}

			for (int i = 0; i < this.belongToWuJiang.friendList.size(); i++) {
				WuJiang tarWJ = this.belongToWuJiang.friendList.get(i);
				if (tarWJ.blood < tarWJ.getMaxBlood()) {
					this.tarWJForAI = tarWJ;
					return;
				}
			}
		}
	}

	public void onClickUpdateView() {

		if (this.gameApp.gameLogicData.myWuJiang.state != Type.State.ChuPai
				|| this.gameApp.gameLogicData.askForPai != Type.CardPai.notNil) {
			return;
		}

		// set the select wj number
		this.gameApp.selectWJViewData.selectNumber = this.selectTarWJNumber;

		String canSelectStr = this.belongToWuJiang.dispName + " ";

		// my self can also be selected
		this.belongToWuJiang.canSelect = true;
		this.gameApp.libGameViewData.imageWJs[this.belongToWuJiang.imageViewIndex]
				.setBackgroundDrawable(this.gameApp.getResources().getDrawable(
						R.drawable.bg_green));

		WuJiang tarWJ = this.belongToWuJiang.nextOne;
		while (!tarWJ.equals(this.belongToWuJiang)) {
			tarWJ.clicked = false;
			tarWJ.canSelect = false;
			if (tarWJ.blood < tarWJ.getMaxBlood()) {
				// set the tarWJ to gray
				this.gameApp.libGameViewData.imageWJs[tarWJ.imageViewIndex]
						.setBackgroundDrawable(this.gameApp.getResources()
								.getDrawable(R.drawable.bg_green));
				// set can reach flag
				tarWJ.canSelect = true;
				canSelectStr += tarWJ.dispName + " ";
			}
			tarWJ = tarWJ.nextOne;
		}

		if (canSelectStr.trim().length() <= 0)
			this.gameApp.libGameViewData.logInfo("没有可" + this.dispName + "的对象",
					Type.logDelay.NoDelay);

	}

	// click wj and set data
	public void onClickWJUpdateView(WuJiang curClickWJ) {
		if (gameApp.selectWJViewData.selectNumber == 1) {
			gameApp.selectWJViewData.selectedWJ1 = curClickWJ;
		}
	}
}
