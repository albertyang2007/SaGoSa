package alben.sgs.cardpai.instance;

import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.FangJuCardPai;
import alben.sgs.cardpai.JinNangCardPai;
import alben.sgs.common.LoopWuJiangHelper;
import alben.sgs.type.Type;
import alben.sgs.type.Type.JinNangApplyTo;
import alben.sgs.wujiang.WuJiang;

public class NanManRuQin extends JinNangCardPai {
	public NanManRuQin(Type.CardPai na, Type.CardPaiClass c, int n,
			int imgNumber, JinNangApplyTo at) {
		super(na, c, n, imgNumber, at);
		this.dispName = "南蛮入侵";
	}

	public boolean work(WuJiang srcWJ, WuJiang tarWJ1, CardPai tarCP) {
		WuJiang tarWJ = srcWJ;
		this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "使用" + this,
				Type.logDelay.Delay);

		this.listenPreWorkEvent(srcWJ, tarWJ, tarCP);

		LoopWuJiangHelper localLoopWuJiangHelper = new LoopWuJiangHelper(
				this.gameApp.gameLogicData.wuJiangs, srcWJ);
		// skip me and start from next one
		localLoopWuJiangHelper.nextLoopWJ();
		tarWJ = localLoopWuJiangHelper.nextLoopWJ();
		while ((tarWJ != null)
				&& (!this.gameApp.gameLogicData.wjHelper.checkMatchOver())) {

			if (tarWJ.zhuangBei.fangJu != null) {
				FangJuCardPai fjcp = (FangJuCardPai) tarWJ.zhuangBei.fangJu;
				if (fjcp.defenceWork(srcWJ, tarWJ, this)) {
					// next one
					tarWJ = localLoopWuJiangHelper.nextLoopWJ();
					continue;
				}
			}

			if (srcWJ.askForWuXieKeJi(srcWJ, this, tarWJ, this)) {
				this.gameApp.libGameViewData.logInfo(this.dispName + "被无懈了",
						Type.logDelay.NoDelay);
			} else {
				CardPai cp = tarWJ.chuSha(srcWJ, this);
				if (cp == null) {
					this.countTotalShangHaiN(tarWJ);
					this.gameApp.libGameViewData.logInfo(tarWJ.dispName
							+ "放弃出杀", Type.logDelay.Delay);
					this.shangHaiSrcWJ = srcWJ;
					tarWJ.increaseBlood(srcWJ, this);
					System.out.println("srcWJ.state=" + srcWJ.state);
				}
			}
			// next one
			tarWJ = localLoopWuJiangHelper.nextLoopWJ();
		}
		return true;
	}
}
