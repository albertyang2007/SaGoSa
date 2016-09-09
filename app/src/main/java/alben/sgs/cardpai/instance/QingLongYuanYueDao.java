package alben.sgs.cardpai.instance;

import alben.sgs.android.R;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.WuQiCardPai;
import alben.sgs.type.Type;
import alben.sgs.wujiang.WuJiang;
import alben.sgs.wujiang.instance.ZhuGeLiang;

public class QingLongYuanYueDao extends WuQiCardPai {
	public QingLongYuanYueDao(Type.CardPai na, Type.CardPaiClass c, int n,
			int imgNumber, int d) {
		super(na, c, n, imgNumber, d);
		this.dispName = "青龙偃月刀";
		this.zbImgNumber = R.drawable.zb_qinglongyanyuedao;
	}

	public void listenShanEvent(WuJiang srcWJ, WuJiang tarWJ, CardPai srcCP) {
		if (srcWJ.state != Type.State.ChuPai)
			return;

		if (srcWJ.shouPai.size() == 0)
			return;

		// ZhuGeLiang is kongCheng, can not Sha
		if (tarWJ instanceof ZhuGeLiang) {
			if (tarWJ.shouPai.size() == 0) {
				return;
			}
		}

		if (srcWJ.tuoGuan) {
			CardPai shaCP = srcWJ.selectCardPaiFromShouPai(Type.CardPai.Sha);

			if (shaCP != null) {
				srcWJ.detatchCardPaiFromShouPai(shaCP);
				shaCP.belongToWuJiang = null;
				shaCP.cpState = Type.CPState.FeiPaiDui;
				srcWJ.specialChuPaiReason = "[" + this.dispName + "]";
				((Sha) shaCP).commonShaWork(srcWJ, tarWJ, null);
			}
		} else {
			this.gameApp.gameLogicData.askForPai = Type.CardPai.Sha;
			CardPai shaCP = this.gameApp.gameLogicData.userInterface
					.askUserChuPai(srcWJ, "是否使用青龙偃月刀再出杀?");

			if (shaCP != null) {
				srcWJ.specialChuPaiReason = "[" + this.dispName + "]";
				((Sha) shaCP).commonShaWork(srcWJ, tarWJ, null);
			}
		}
	}
}
