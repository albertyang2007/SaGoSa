package alben.sgs.wujiang.instance.jineng;

import java.util.ArrayList;

import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.ZhuangBeiCardPai;
import alben.sgs.type.Type;
import alben.sgs.type.UpdateWJViewData;
import alben.sgs.wujiang.WuJiang;

public class ZhiHeng extends CardPai {
	public ArrayList<CardPai> zhiHengCPs = new ArrayList<CardPai>();

	public ZhiHeng(Type.CardPai na, Type.CardPaiClass c, int n) {
		super(na, c, n);
		this.ID = generateID++;
		this.dispName = "ÖÆºâ";
		this.cpState = Type.CPState.nil;
		this.name = Type.CardPai.WJJiNeng;
	}

	public String toString() {
		return this.dispName;
	}

	public boolean work(WuJiang srcWJ, WuJiang tarWJ, CardPai tarCP) {

		if (srcWJ.oneTimeJiNengTrigger) {
			return false;
		} else {
			srcWJ.oneTimeJiNengTrigger = true;
		}

		for (int i = 0; i < this.zhiHengCPs.size(); i++) {
			CardPai cp = this.zhiHengCPs.get(i);

			if (cp.cpState == Type.CPState.ShouPai) {
				srcWJ.detatchCardPaiFromShouPai(cp);
			} else if (cp.cpState == Type.CPState.wuQiPai
					|| cp.cpState == Type.CPState.fangJuPai
					|| cp.cpState == Type.CPState.jiaYiMaPai
					|| cp.cpState == Type.CPState.jianYiMaPai) {
				srcWJ.unstallZhuangBei((ZhuangBeiCardPai) cp);
			}

			this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "["
					+ srcWJ.jiNengN1 + "],Æúµô" + cp, Type.logDelay.HalfDelay);
		}

		this.gameApp.gameLogicData.cpHelper.addCardPaiToWuJiang(srcWJ,
				this.zhiHengCPs.size());

		UpdateWJViewData item = new UpdateWJViewData();
		item.updateAll = true;
		this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(srcWJ,
				item);

		if (!srcWJ.tuoGuan) {
			this.gameApp.gameLogicData.wjHelper.updateWJ8ShouPaiToLibGameView();
		}

		this.gameApp.libGameViewData.logInfo(srcWJ.dispName + "ÃþÈ¡"
				+ this.zhiHengCPs.size() + "ÕÅÅÆ", Type.logDelay.HalfDelay);

		return true;
	}
}
