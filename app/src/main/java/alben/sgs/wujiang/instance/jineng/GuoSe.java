package alben.sgs.wujiang.instance.jineng;

import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.ZhuangBeiCardPai;
import alben.sgs.cardpai.instance.LeBuShiShu;
import alben.sgs.type.Type;
import alben.sgs.type.Type.JinNangApplyTo;
import alben.sgs.type.UpdateWJViewData;
import alben.sgs.wujiang.WuJiang;

public class GuoSe extends LeBuShiShu {
	public CardPai sp1 = null;

	public GuoSe(Type.CardPai na, Type.CardPaiClass c, int n, JinNangApplyTo at) {
		super(na, c, n, at);
	}

	public boolean work(WuJiang srcWJ, WuJiang tarWJ, CardPai tarCP) {

		// set the image number
		this.imageNumber = this.sp1.imageNumber;

		// remove sp1 from shou pai list
		// this.sp1.belongToWuJiang = null;
		if (this.sp1.cpState == Type.CPState.ShouPai) {
			srcWJ.shouPai.remove(this.sp1);
			if (!srcWJ.tuoGuan) {
				this.gameApp.gameLogicData.wjHelper
						.updateWJ8ShouPaiToLibGameView();
			} else {
				UpdateWJViewData item = new UpdateWJViewData();
				item.updateShouPaiNumber = true;
				this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(
						srcWJ, item);
			}
		} else if (this.sp1.cpState == Type.CPState.wuQiPai
				|| this.sp1.cpState == Type.CPState.fangJuPai
				|| this.sp1.cpState == Type.CPState.jiaYiMaPai
				|| this.sp1.cpState == Type.CPState.jianYiMaPai) {
			srcWJ.unstallZhuangBei((ZhuangBeiCardPai) this.sp1);
		}

		return super.work(srcWJ, tarWJ, tarCP);
	}
}
