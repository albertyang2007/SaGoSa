package alben.sgs.cardpai.instance;

import alben.sgs.android.R;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.FangJuCardPai;
import alben.sgs.type.Type;
import alben.sgs.wujiang.WuJiang;
import alben.sgs.wujiang.instance.jineng.HuoJi;

public class TengJia extends FangJuCardPai {
	public TengJia(Type.CardPai na, Type.CardPaiClass c, int n, int imgNumber) {
		super(na, c, n, imgNumber);
		this.dispName = "ÌÙ¼×";
		this.zbImgNumber = R.drawable.zb_tengjia;
	}

	public boolean defenceWork(WuJiang srcWJ, WuJiang tarWJ, CardPai srcCP) {

		boolean zqysTrigger = false;
		if (srcWJ.zhuangBei.wuQi != null
				&& srcWJ.zhuangBei.wuQi instanceof ZhuQueYuShan) {
			ZhuQueYuShan zqys = (ZhuQueYuShan) srcWJ.zhuangBei.wuQi;
			zqysTrigger = zqys.trigger;
		}

		if (srcCP.name == Type.CardPai.Sha && !zqysTrigger) {
			this.gameApp.libGameViewData.logInfo(tarWJ.dispName + "´©ÌÙ¼×,²»ÓÃ³öÉÁ",
					Type.logDelay.NoDelay);
			return true;
		}

		if (srcCP instanceof NanManRuQin) {
			this.gameApp.libGameViewData.logInfo(tarWJ.dispName + "´©ÌÙ¼×,²»ÓÃ³öÉ±",
					Type.logDelay.NoDelay);
			return true;
		}

		if (srcCP instanceof WanJianQiFa) {
			this.gameApp.libGameViewData.logInfo(tarWJ.dispName + "´©ÌÙ¼×,²»ÓÃ³öÉÁ",
					Type.logDelay.NoDelay);
			return true;
		}

		return false;
	}

	public void listenIncreaseBloodEvent(CardPai srcCP) {
		if (srcCP.shangHaiN < 0) {
			if (((srcCP instanceof HuoSha)) || ((srcCP instanceof HuoGong))
					|| ((srcCP instanceof HuoJi))) {
				this.gameApp.libGameViewData.logInfo(
						this.belongToWuJiang.dispName + "´©ÌÙ¼×»ðÉËº¦+1",
						Type.logDelay.Delay);
				srcCP.shangHaiN -= 1;
			}
			if ((srcCP.belongToWuJiang != null)
					&& (srcCP.belongToWuJiang.zhuangBei.wuQi != null)
					&& ((srcCP.belongToWuJiang.zhuangBei.wuQi instanceof ZhuQueYuShan))
					&& (((ZhuQueYuShan) srcCP.belongToWuJiang.zhuangBei.wuQi).trigger)) {
				this.gameApp.libGameViewData.logInfo(
						this.belongToWuJiang.dispName + "´©ÌÙ¼×»ðÉËº¦+1",
						Type.logDelay.Delay);
				srcCP.shangHaiN -= 1;
			}
		}
	}
}
