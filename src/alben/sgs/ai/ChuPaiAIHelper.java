package alben.sgs.ai;

import java.util.ArrayList;

import alben.sgs.android.GameApp;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.instance.Sha;
import alben.sgs.cardpai.instance.ZBSMSha;
import alben.sgs.cardpai.instance.ZhangBaSheMao;
import alben.sgs.type.CardPaiActionForTarWuJiang;
import alben.sgs.type.Type;
import alben.sgs.wujiang.WuJiang;

public class ChuPaiAIHelper {
	// private GameApp gameApp = null;
	private WuJiang srcWJ = null;
	private ArrayList<CardPai> cpList = new ArrayList<CardPai>();

	public ChuPaiAIHelper(GameApp app, WuJiang wj) {
		// this.gameApp = app;
		this.srcWJ = wj;
	}

	public CardPai popOneShouPai() {
		CardPai cp = null;
		if (this.cpList.size() > 0) {
			cp = this.cpList.get(0);
			this.cpList.remove(0);
		}
		return cp;
	}

	public void ConstructChuPaiList() {
		// first empty cpList
		while (this.cpList.size() > 0) {
			this.cpList.remove(0);
		}
		this.cpList = null;
		this.cpList = new ArrayList<CardPai>();

		// tie suo lian huan
		CardPai tslhCP = this.srcWJ
				.selectCardPaiFromShouPai(Type.CardPai.TieSuoLianHuan);
		if (tslhCP != null) {
			this.cpList.add(tslhCP);
		}

		// wugufengdeng
		CardPai wgfdCP = this.srcWJ
				.selectCardPaiFromShouPai(Type.CardPai.WuGuFengDeng);
		if (wgfdCP != null) {
			this.cpList.add(wgfdCP);
		}

		// wu zhong sheng you
		CardPai wzsyCP = this.srcWJ
				.selectCardPaiFromShouPai(Type.CardPai.WuZhongShengYou);
		if (wzsyCP != null) {
			this.cpList.add(wzsyCP);
		}

		// tao yuan jie yi
		CardPai tyjyCP = this.srcWJ
				.selectCardPaiFromShouPai(Type.CardPai.TaoYuanJieYi);
		if (tyjyCP != null) {
			this.cpList.add(tyjyCP);
		}

		// jie dao sha ren
		CardPai jdsr = this.srcWJ
				.selectCardPaiFromShouPai(Type.CardPai.JieDaoShaRen);
		if (jdsr != null) {
			jdsr.selectTarWJForAI();
			WuJiang tarWJ = jdsr.getTarWJForAI();
			if (tarWJ != null) {
				this.cpList.add(jdsr);
			}
		}

		// huogong
		CardPai hgCP = this.srcWJ
				.selectCardPaiFromShouPai(Type.CardPai.HuoGong);
		if (hgCP != null) {
			hgCP.selectTarWJForAI();
			WuJiang tarWJ = hgCP.getTarWJForAI();
			if (tarWJ != null) {
				this.cpList.add(hgCP);
			}
		}

		// guohechaiqiao
		CardPai ghcqCP = this.srcWJ
				.selectCardPaiFromShouPai(Type.CardPai.GuoHeChaiQiao);
		if (ghcqCP != null) {
			ghcqCP.selectTarWJForAI();
			WuJiang tarWJ = ghcqCP.getTarWJForAI();
			if (tarWJ != null) {
				this.cpList.add(ghcqCP);
			}
		}

		// shunshouquanyang
		CardPai ssqyCP = this.srcWJ
				.selectCardPaiFromShouPai(Type.CardPai.ShunShouQuanYang);
		if (ssqyCP != null) {
			ssqyCP.selectTarWJForAI();
			WuJiang tarWJ = ssqyCP.getTarWJForAI();
			if (tarWJ != null) {
				this.cpList.add(ssqyCP);
			}
		}

		// wuqi
		CardPai wuQiCP = this.srcWJ.selectWuQiFromShouPai();
		if (wuQiCP != null && this.srcWJ.needInstallNewZB(wuQiCP)) {
			this.cpList.add(wuQiCP);
		}

		// fangju
		CardPai fangJuCP = this.srcWJ.selectFangJuFromShouPai();
		if (fangJuCP != null && this.srcWJ.needInstallNewZB(fangJuCP)) {
			this.cpList.add(fangJuCP);
		}

		// jia yi ma
		CardPai jiaYiMaCP = this.srcWJ.selectMaCardPaiFromShouPai(1);
		if (jiaYiMaCP != null && this.srcWJ.needInstallNewZB(jiaYiMaCP)) {
			this.cpList.add(jiaYiMaCP);
		}

		// jian yi ma
		CardPai jianYiMaCP = this.srcWJ.selectMaCardPaiFromShouPai(-1);
		if (jianYiMaCP != null && this.srcWJ.needInstallNewZB(jianYiMaCP)) {
			this.cpList.add(jianYiMaCP);
		}

		// chi tao

		CardPai taoCP = this.srcWJ.selectCardPaiFromShouPai(Type.CardPai.Tao);
		if (taoCP != null) {
			if (this.srcWJ.blood < this.srcWJ.getMaxBlood()) {
				this.cpList.add(taoCP);
			}
		}

		// jue dou
		CardPai jdCP = this.srcWJ.selectCardPaiFromShouPai(Type.CardPai.JueDou);
		if (jdCP != null) {
			jdCP.selectTarWJForAI();
			WuJiang tarWJ = jdCP.getTarWJForAI();
			if (tarWJ != null) {
				this.cpList.add(jdCP);
			}
		}

		// wan jian qi fa
		CardPai wjqiCP = this.srcWJ
				.selectCardPaiFromShouPai(Type.CardPai.WanJianQiFa);
		if (wjqiCP != null) {
			this.cpList.add(wjqiCP);
		}

		// nan man ru qin
		CardPai nmrqCP = this.srcWJ
				.selectCardPaiFromShouPai(Type.CardPai.NanManRuQin);
		if (nmrqCP != null) {
			this.cpList.add(nmrqCP);
		}

		// chu sha
		{
			if (this.srcWJ.canIChuSha()) {
				CardPaiActionForTarWuJiang useCPShaWJ = this.srcWJ
						.whichWJICanShaWithWuQi(this.srcWJ.zhuangBei.wuQi,
								this.srcWJ.opponentList);
				if (useCPShaWJ.tarWJ != null) {
					// I can use shaCP sha tarWJ
					if (useCPShaWJ.srcCP != null) {

						useCPShaWJ.srcCP.tarWJForAI = useCPShaWJ.tarWJ;
						// maybe add more tarWJ if wuQi is FangTianHuaJi
						// or WJ is TaiShiChi
						((Sha) useCPShaWJ.srcCP).selectMoreTarWJForAI();

						this.cpList.add(useCPShaWJ.srcCP);
					}
				}
			}

			// do not use the old one
			boolean runOldSha = false;
			if (runOldSha) {
				CardPai shaCP = this.srcWJ
						.selectCardPaiFromShouPai(Type.CardPai.Sha);
				if (shaCP == null) {
					// no sha, check if wuqi is zhangbashemao
					if (srcWJ.zhuangBei.wuQi != null
							&& srcWJ.zhuangBei.wuQi instanceof ZhangBaSheMao
							&& srcWJ.shouPai.size() >= 2) {
						shaCP = new ZBSMSha(srcWJ.shouPai.get(0), srcWJ.shouPai
								.get(1));
					}
				}
				if (shaCP != null) {
					if (this.srcWJ.canIChuSha()) {
						shaCP.selectTarWJForAI();
						WuJiang tarWJ = shaCP.getTarWJForAI();
						if (tarWJ != null) {
							this.cpList.add(shaCP);
						}
					}
				}
			}
		}

		// lebushishu
		CardPai lbssCP = this.srcWJ
				.selectCardPaiFromShouPai(Type.CardPai.LeBuShiShu);
		if (lbssCP != null) {
			lbssCP.selectTarWJForAI();
			WuJiang tarWJ = lbssCP.getTarWJForAI();
			if (tarWJ != null) {
				this.cpList.add(lbssCP);
			}
		}

		// BingLiangCunDuan
		CardPai blcdCP = this.srcWJ
				.selectCardPaiFromShouPai(Type.CardPai.BingLiangCunDuan);
		if (blcdCP != null) {
			blcdCP.selectTarWJForAI();
			WuJiang tarWJ = blcdCP.getTarWJForAI();
			if (tarWJ != null) {
				this.cpList.add(blcdCP);
			}
		}

		// ShanDian
		CardPai sdCP = this.srcWJ
				.selectCardPaiFromShouPai(Type.CardPai.ShanDian);
		if (sdCP != null) {
			if (!this.srcWJ.hasSDInPanDindArea()) {
				this.cpList.add(sdCP);
			}
		}

		// JiNeng CardPai, such as LiJian
		// By default put JiNeng at the button of chuPai
		CardPai wjjnCP2 = this.srcWJ.generateJiNengCardPai();
		if (wjjnCP2 != null) {
			this.cpList.add(wjjnCP2);
		}

		// For global WuJiang JiNeng, such as ZhangJiao's HuangTian
		CardPai huangTianCP = this.srcWJ.generateHuangTianJiNengCardPai();
		if (huangTianCP != null) {
			this.cpList.add(huangTianCP);
		}
	}
}
