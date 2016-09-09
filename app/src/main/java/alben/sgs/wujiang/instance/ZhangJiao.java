package alben.sgs.wujiang.instance;

import alben.sgs.android.R;
import alben.sgs.android.dialog.SelectCardPaiFromWJDialog;
import alben.sgs.android.dialog.YesNoDialog;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.instance.BaGuaZhen;
import alben.sgs.cardpai.instance.BingLiangCunDuan;
import alben.sgs.cardpai.instance.LeBuShiShu;
import alben.sgs.cardpai.instance.ShanDian;
import alben.sgs.type.Type;
import alben.sgs.type.UpdateWJViewData;
import alben.sgs.type.WuJiangCardPaiData;
import alben.sgs.wujiang.WuJiang;
import alben.sgs.wujiang.instance.jineng.GangLie;
import alben.sgs.wujiang.instance.jineng.LeiJi;
import alben.sgs.wujiang.instance.jineng.LuoShen;
import alben.sgs.wujiang.instance.jineng.ShuangXiong;
import alben.sgs.wujiang.instance.jineng.TieJi;
import android.view.View;

public class ZhangJiao extends WuJiang {
	public ZhangJiao(Type.WuJiang n, Type.Country c, Type.Sex s, int b) {
		super(n, c, s, b);
		this.imageNumber = alben.sgs.android.R.drawable.wj_zhangjiao;
		this.jiNengDesc = "(风扩展包武将)\n"
				+ "1、雷击：每当你使用或打出一张【闪】时（在结算前），可令任意一名角色判定，若为【黑桃】，你对该角色造成2点雷电伤害。\n"
				+ "2、鬼道：在任意一名角色的判定牌生效前，你可以用自己的一张【黑桃】或【梅花】牌替换之。\n"
				+ "3、黄天：主公技，群雄角色可在他们各自的回合里给你一张【闪】或【闪电】。";
		this.dispName = "张角";
		this.jiNengN1 = "雷击";
		this.jiNengN2 = "鬼道";
		this.jiNengN3 = "黄天";
	}

	public void listenEnterHuiHeEvent() {
		super.listenEnterHuiHeEvent();
		this.enableWuJiangJiNengBtn();
	}

	public void enableWuJiangJiNengBtn() {
		if (!this.tuoGuan) {
			this.gameApp.libGameViewData.mJiNengBtn1
					.setVisibility(View.VISIBLE);
			this.gameApp.libGameViewData.mJiNengBtn1.setEnabled(false);
			this.gameApp.libGameViewData.mJiNengBtn1Txt.setText(this.jiNengN1);

			this.gameApp.libGameViewData.mJiNengBtn2
					.setVisibility(View.VISIBLE);
			this.gameApp.libGameViewData.mJiNengBtn2.setEnabled(false);
			this.gameApp.libGameViewData.mJiNengBtn2Txt.setText(this.jiNengN2);

			this.gameApp.libGameViewData.mJiNengBtn3
					.setVisibility(View.VISIBLE);
			this.gameApp.libGameViewData.mJiNengBtn3.setEnabled(false);
			this.gameApp.libGameViewData.mJiNengBtn3Txt.setText(this.jiNengN3);
		}
		// invoke super to set the correct JiNengBtnImage
		super.enableWuJiangJiNengBtn();
	}

	// JiNeng1: LeiJi
	public CardPai chuShan(WuJiang srcWJ, CardPai srcCP) {
		CardPai shanCP = super.chuShan(srcWJ, srcCP);
		if (shanCP != null) {
			this.LeiJi(srcWJ, srcCP);
		}
		return shanCP;
	}

	public void LeiJi(WuJiang srcWJ, CardPai srcCP) {
		WuJiang ljTarWJ = null;
		if (this.tuoGuan) {
			if (this.isOpponent(this.gameApp.gameLogicData.zhuGongWuJiang)) {
				ljTarWJ = this.gameApp.gameLogicData.zhuGongWuJiang;
			} else if (this.isOpponent(srcWJ)) {
				ljTarWJ = srcWJ;
			} else if (this.opponentList.size() > 0) {
				ljTarWJ = this.opponentList.get(0);
			}
			if (ljTarWJ == null)
				return;
		} else {
			this.gameApp.ynData.reset();
			this.gameApp.ynData.okTxt = "确认";
			this.gameApp.ynData.cancelTxt = "取消";
			this.gameApp.ynData.genInfo = "是否使用" + this.jiNengN1 + "?";

			YesNoDialog dlg = new YesNoDialog(this.gameApp.gameActivityContext,
					this.gameApp);
			dlg.showDialog();
			if (!this.gameApp.ynData.result)
				return;

			// then select two wj
			gameApp.selectWJViewData.reset();
			gameApp.selectWJViewData.selectNumber = 1;
			// show wujiang can be selected
			WuJiang tarWJ = this.nextOne;
			while (!tarWJ.equals(this)) {
				gameApp.libGameViewData.imageWJs[tarWJ.imageViewIndex]
						.setBackgroundDrawable(gameApp.getResources()
								.getDrawable(R.drawable.bg_green));
				tarWJ.canSelect = true;
				tarWJ.clicked = false;
				tarWJ = tarWJ.nextOne;
			}
			// use UI for interaction
			gameApp.gameLogicData.userInterface.askUserSelectWuJiang(
					gameApp.gameLogicData.myWuJiang, "请选择"
							+ gameApp.selectWJViewData.selectNumber + "个武将");

			ljTarWJ = gameApp.selectWJViewData.selectedWJ1;
		}

		if (ljTarWJ != null) {
			this.gameApp.libGameViewData.logInfo(this.dispName + "["
					+ this.jiNengN1 + "]" + ljTarWJ.dispName,
					Type.logDelay.Delay);

			LeiJi leiJi = new LeiJi(Type.CardPai.nil, Type.CardPaiClass.nil, 0);
			leiJi.gameApp = this.gameApp;
			leiJi.belongToWuJiang = this;
			leiJi.shangHaiSrcWJ = this;

			CardPai pdCP = this.gameApp.gameLogicData.cpHelper
					.popCardPaiForPanDing(ljTarWJ, leiJi);

			if (pdCP.clas == Type.CardPaiClass.HeiTao) {
				this.gameApp.libGameViewData.logInfo(ljTarWJ.dispName + "被雷击中",
						Type.logDelay.Delay);
				ljTarWJ.increaseBlood(this, leiJi);
			}
		}
	}

	// JiNeng2: GuiDao
	public CardPai listenPanDingCardPaiEvent(WuJiang tarWJ, CardPai tarCP,
			CardPai curPDCP) {

		CardPai newPDCP = null;
		// tarCP can be base CP: BaGuaZhen,BingLiangCunDuan,LeBuShiShu,ShanDian
		// tarCP can be WJ JiNeng: TieJi,GangLie,LuoShen,LeiJi
		if (this.shouPai.size() == 0 && !this.zhuangBei.containsZB())
			return newPDCP;

		if (this.tuoGuan) {
			if (tarCP instanceof BaGuaZhen) {
				newPDCP = this.handleBaGuaZhenPDCP(tarWJ, tarCP, curPDCP);
			} else if (tarCP instanceof BingLiangCunDuan) {
				newPDCP = this
						.handleBingLiangCunDuanPDCP(tarWJ, tarCP, curPDCP);
			} else if (tarCP instanceof LeBuShiShu) {
				newPDCP = this.handleLeBuShiShuPDCP(tarWJ, tarCP, curPDCP);
			} else if (tarCP instanceof ShanDian) {
				newPDCP = this.handleShanDianPDCP(tarWJ, tarCP, curPDCP);
			} else if (tarCP instanceof TieJi) {
				newPDCP = this.handleTieJiPDCP(tarWJ, tarCP, curPDCP);
			} else if (tarCP instanceof GangLie) {
				newPDCP = this.handleGangLiePDCP(tarWJ, tarCP, curPDCP);
			} else if (tarCP instanceof LuoShen) {
				newPDCP = this.handleLuoShenPDCP(tarWJ, tarCP, curPDCP);
			} else if (tarCP instanceof LeiJi) {
				newPDCP = this.handleLeiJiPDCP(tarWJ, tarCP, curPDCP);
			} else if (tarCP instanceof ShuangXiong) {
				// do nothing
			}
		} else {
			this.gameApp.ynData.reset();
			this.gameApp.ynData.okTxt = "确认";
			this.gameApp.ynData.cancelTxt = "取消";
			this.gameApp.ynData.genInfo = tarWJ.dispName + "的" + tarCP.dispName
					+ "判定牌是" + curPDCP + ",是否使用" + this.jiNengN2 + "打出1张手牌替换之?";

			YesNoDialog dlg = new YesNoDialog(this.gameApp.gameActivityContext,
					this.gameApp);
			dlg.showDialog();

			if (this.gameApp.ynData.result) {
				// first select one shoupai
				this.gameApp.wjDetailsViewData.reset();
				this.gameApp.wjDetailsViewData.selectedCardNumber = 1;
				this.gameApp.wjDetailsViewData.canViewShouPai = true;
				this.gameApp.wjDetailsViewData.selectedWJ = this;

				WuJiangCardPaiData wjCPData = new WuJiangCardPaiData();

				if (this.zhuangBei.fangJu != null
						&& (this.zhuangBei.fangJu.clas == Type.CardPaiClass.MeiHua || this.zhuangBei.fangJu.clas == Type.CardPaiClass.HeiTao)) {
					wjCPData.zhuangBei.fangJu = this.zhuangBei.fangJu;
				}
				if (this.zhuangBei.wuQi != null
						&& (this.zhuangBei.wuQi.clas == Type.CardPaiClass.MeiHua || this.zhuangBei.wuQi.clas == Type.CardPaiClass.HeiTao)) {
					wjCPData.zhuangBei.wuQi = this.zhuangBei.wuQi;
				}
				if (this.zhuangBei.jianYiMa != null
						&& (this.zhuangBei.jianYiMa.clas == Type.CardPaiClass.MeiHua || this.zhuangBei.jianYiMa.clas == Type.CardPaiClass.HeiTao)) {
					wjCPData.zhuangBei.jianYiMa = this.zhuangBei.jianYiMa;
				}
				if (this.zhuangBei.jiaYiMa != null
						&& (this.zhuangBei.jiaYiMa.clas == Type.CardPaiClass.MeiHua || this.zhuangBei.jiaYiMa.clas == Type.CardPaiClass.HeiTao)) {
					wjCPData.zhuangBei.jiaYiMa = this.zhuangBei.jiaYiMa;
				}

				for (int i = 0; i < this.shouPai.size(); i++) {
					CardPai cp = this.shouPai.get(i);
					if (cp.clas == Type.CardPaiClass.MeiHua
							|| cp.clas == Type.CardPaiClass.HeiTao) {
						wjCPData.shouPai.add(cp);
					}
				}

				SelectCardPaiFromWJDialog dlg2 = new SelectCardPaiFromWJDialog(
						this.gameApp.gameActivityContext, this.gameApp,
						wjCPData);
				dlg2.showDialog();

				newPDCP = this.gameApp.wjDetailsViewData.selectedCardPai1;
			}
		}

		if (newPDCP != null) {
			newPDCP.belongToWuJiang = null;
			this.detatchCardPaiFromShouPai(newPDCP);

			curPDCP.belongToWuJiang = this;
			curPDCP.cpState = Type.CPState.ShouPai;
			this.shouPai.add(curPDCP);

			if (this.tuoGuan) {
				// update shou pai number
				UpdateWJViewData item = new UpdateWJViewData();
				item.updateShouPaiNumber = true;
				this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(
						this, item);
			} else {
				this.gameApp.gameLogicData.wjHelper
						.updateWJ8ShouPaiToLibGameView();
			}

			this.gameApp.libGameViewData.logInfo(this.dispName + "["
					+ this.jiNengN2 + "]打出" + newPDCP, Type.logDelay.Delay);
		}
		// null means do not change
		return newPDCP;
	}

	public CardPai handleBaGuaZhenPDCP(WuJiang tarWJ, CardPai tarCP,
			CardPai curPDCP) {
		CardPai newPDCP = null;
		boolean isRedCP = (curPDCP.clas == Type.CardPaiClass.FangPian || curPDCP.clas == Type.CardPaiClass.HongTao) ? true
				: false;
		if (this.equals(tarWJ) || this.isFriend(tarWJ)) {
			if (curPDCP.clas == Type.CardPaiClass.HeiTao) {
				newPDCP = this.hasCardPaiByClass(Type.CardPaiClass.MeiHua);
			}
		} else if (this.isOpponent(tarWJ)) {
			if (isRedCP) {
				newPDCP = this.hasCardPaiByClass(Type.CardPaiClass.MeiHua);
				if (newPDCP == null)
					newPDCP = this.hasCardPaiByClass(Type.CardPaiClass.HeiTao);
			} else if (curPDCP.clas == Type.CardPaiClass.HeiTao) {
				newPDCP = this.hasCardPaiByClass(Type.CardPaiClass.MeiHua);
			}
		}

		// by default, do not change
		return newPDCP;
	}

	public CardPai handleBingLiangCunDuanPDCP(WuJiang tarWJ, CardPai tarCP,
			CardPai curPDCP) {
		CardPai newPDCP = null;
		boolean isMeiHuaCP = (curPDCP.clas == Type.CardPaiClass.MeiHua) ? true
				: false;
		if (this.equals(tarWJ) || this.isFriend(tarWJ)) {
			if (!isMeiHuaCP) {
				newPDCP = this.hasCardPaiByClass(Type.CardPaiClass.MeiHua);
				if (newPDCP == null)
					newPDCP = this.hasCardPaiByClass(Type.CardPaiClass.HeiTao);
			}
		} else if (this.isOpponent(tarWJ) && isMeiHuaCP) {
			newPDCP = this.selectFromShouPaiByClass(Type.CardPaiClass.HeiTao);
		}
		// by default, do not change
		return newPDCP;
	}

	public CardPai handleLeBuShiShuPDCP(WuJiang tarWJ, CardPai tarCP,
			CardPai curPDCP) {
		CardPai newPDCP = null;
		boolean isHongTaoCP = (curPDCP.clas == Type.CardPaiClass.HongTao) ? true
				: false;
		if (this.equals(tarWJ) || this.isFriend(tarWJ)) {
			if (!isHongTaoCP) {
				newPDCP = this.hasCardPaiByClass(Type.CardPaiClass.MeiHua);
				if (newPDCP == null)
					newPDCP = this.hasCardPaiByClass(Type.CardPaiClass.HeiTao);
			}
		} else if (isHongTaoCP && this.isOpponent(tarWJ)) {
			newPDCP = this.hasCardPaiByClass(Type.CardPaiClass.MeiHua);
			if (newPDCP == null)
				newPDCP = this.hasCardPaiByClass(Type.CardPaiClass.HeiTao);
		}

		// by default, do not change
		return newPDCP;
	}

	public CardPai handleShanDianPDCP(WuJiang tarWJ, CardPai tarCP,
			CardPai curPDCP) {
		CardPai newPDCP = null;
		boolean isZhongSD = false;
		if (curPDCP.clas == Type.CardPaiClass.HeiTao
				&& (curPDCP.number >= 2 && curPDCP.number <= 9))
			isZhongSD = true;

		if (this.equals(tarWJ) || this.isFriend(tarWJ)) {
			newPDCP = this.hasCardPaiByClass(Type.CardPaiClass.MeiHua);
			if (newPDCP == null) {
				CardPai tmpPDCP = this
						.hasCardPaiByClass(Type.CardPaiClass.HeiTao);
				if (tmpPDCP != null
						&& (tmpPDCP.number < 2 || tmpPDCP.number > 9))
					newPDCP = tmpPDCP;
			}
		} else if (this.isOpponent(tarWJ)) {
			if (!isZhongSD) {
				CardPai tmpPDCP = this
						.hasCardPaiByClass(Type.CardPaiClass.HeiTao);
				if (tmpPDCP != null) {
					if (tmpPDCP.number >= 2 && tmpPDCP.number <= 9)
						newPDCP = tmpPDCP;
				}

				if (newPDCP == null) {
					newPDCP = this.hasCardPaiByClass(Type.CardPaiClass.MeiHua);
					if (newPDCP == null)
						newPDCP = this
								.hasCardPaiByClass(Type.CardPaiClass.HeiTao);
				}
			}
		}

		// by default, do not change
		return newPDCP;
	}

	public CardPai handleTieJiPDCP(WuJiang tarWJ, CardPai tarCP, CardPai curPDCP) {
		CardPai newPDCP = null;
		if (this.equals(tarWJ) || this.isFriend(tarWJ)) {
			newPDCP = this.hasCardPaiByClass(Type.CardPaiClass.MeiHua);
			if (newPDCP == null)
				newPDCP = this.hasCardPaiByClass(Type.CardPaiClass.HeiTao);
		} else if (this.isOpponent(tarWJ)
				&& curPDCP.clas == Type.CardPaiClass.HeiTao) {
			newPDCP = this.hasCardPaiByClass(Type.CardPaiClass.MeiHua);
		}

		// by default, do not change
		return newPDCP;
	}

	public CardPai handleGangLiePDCP(WuJiang tarWJ, CardPai tarCP,
			CardPai curPDCP) {
		CardPai newPDCP = null;
		boolean isHongTaoCP = (curPDCP.clas == Type.CardPaiClass.HongTao) ? true
				: false;
		if (this.equals(tarWJ) || this.isFriend(tarWJ)) {
			if (!isHongTaoCP) {
				newPDCP = this.hasCardPaiByClass(Type.CardPaiClass.MeiHua);
				if (newPDCP == null)
					newPDCP = this.hasCardPaiByClass(Type.CardPaiClass.HeiTao);
			}
		} else if (this.isOpponent(tarWJ) && isHongTaoCP) {
			newPDCP = this.hasCardPaiByClass(Type.CardPaiClass.MeiHua);
			if (newPDCP == null)
				newPDCP = this.hasCardPaiByClass(Type.CardPaiClass.HeiTao);
		}

		// by default, do not change
		return newPDCP;
	}

	public CardPai handleLuoShenPDCP(WuJiang tarWJ, CardPai tarCP,
			CardPai curPDCP) {
		CardPai newPDCP = null;
		boolean isBlackCP = (curPDCP.clas == Type.CardPaiClass.HeiTao || curPDCP.clas == Type.CardPaiClass.MeiHua) ? true
				: false;
		if (this.isFriend(tarWJ)) {
			if (!isBlackCP) {
				newPDCP = this.hasCardPaiByClass(Type.CardPaiClass.MeiHua);
				if (newPDCP == null)
					newPDCP = this.hasCardPaiByClass(Type.CardPaiClass.HeiTao);
			}
		} else if (this.isOpponent(tarWJ)) {
			if (curPDCP.clas == Type.CardPaiClass.HeiTao) {
				newPDCP = this.hasCardPaiByClass(Type.CardPaiClass.MeiHua);
			}
		}

		// by default, do not change
		return newPDCP;
	}

	public CardPai handleLeiJiPDCP(WuJiang tarWJ, CardPai tarCP, CardPai curPDCP) {
		CardPai newPDCP = null;
		boolean isHeiTaoCP = (curPDCP.clas == Type.CardPaiClass.HeiTao) ? true
				: false;
		if (this.equals(tarWJ) || this.isFriend(tarWJ)) {
			newPDCP = this.hasCardPaiByClass(Type.CardPaiClass.MeiHua);
		} else if (this.isOpponent(tarWJ)) {
			if (!isHeiTaoCP) {
				newPDCP = this.hasCardPaiByClass(Type.CardPaiClass.HeiTao);
			}
		}

		// by default, do not change
		return newPDCP;
	}

	// overwrite it, if fangJu is BaGuaZhen, then do not replace it
	public CardPai hasCardPaiByClass(Type.CardPaiClass cpT) {
		CardPai cp = this.selectFromShouPaiByClass(cpT);

		if (cp == null && this.zhuangBei.jianYiMa != null) {
			if (this.zhuangBei.jianYiMa.clas == cpT) {
				cp = this.zhuangBei.jianYiMa;
			}
		}
		if (cp == null && this.zhuangBei.jiaYiMa != null) {
			if (this.zhuangBei.jiaYiMa.clas == cpT) {
				cp = this.zhuangBei.jiaYiMa;
			}
		}
		if (cp == null && this.zhuangBei.wuQi != null) {
			if (this.zhuangBei.wuQi.clas == cpT) {
				cp = this.zhuangBei.wuQi;
			}
		}
		if (cp == null && this.zhuangBei.fangJu != null
				&& !(this.zhuangBei.fangJu instanceof BaGuaZhen)) {
			if (this.zhuangBei.fangJu.clas == cpT) {
				cp = this.zhuangBei.fangJu;
			}
		}

		return cp;
	}
}
