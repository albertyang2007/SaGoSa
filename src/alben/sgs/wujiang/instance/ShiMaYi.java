package alben.sgs.wujiang.instance;

import alben.sgs.android.dialog.SelectCardPaiFromWJDialog;
import alben.sgs.android.dialog.YesNoDialog;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.instance.BaGuaZhen;
import alben.sgs.cardpai.instance.BaiYinShiZi;
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
import alben.sgs.wujiang.instance.jineng.TianXiang;
import alben.sgs.wujiang.instance.jineng.TieJi;
import android.view.View;

public class ShiMaYi extends WuJiang {
	public ShiMaYi(Type.WuJiang n, Type.Country c, Type.Sex s, int b) {
		super(n, c, s, b);
		this.imageNumber = alben.sgs.android.R.drawable.wj_simayi;
		this.jiNengDesc = "1、反馈：你可以立即从对你造成伤害的来源处获得一张牌。\n"
				+ "2、鬼才：在任意角色的判定牌生效前，你可以打出一张手牌代替之。\n"
				+ "★一次无论受到多少点伤害，只能获得一张牌，若选择手牌则从对方手里随机抽取，选择面前的装备则由你任选。";
		this.dispName = "司马懿";
		this.jiNengN1 = "反馈";
		this.jiNengN2 = "鬼才";
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
		}
		// invoke super to set the correct JiNengBtnImage
		super.enableWuJiangJiNengBtn();
	}

	// jiNengN1
	public void listenIncreaseBloodEvent(CardPai srcCP) {

		WuJiang srcWJ = null;

		if (srcCP instanceof ShanDian) {
			return;
		}

		if (srcCP.name == Type.CardPai.WJJiNeng) {
			if (srcCP instanceof TianXiang) {
				srcWJ = ((TianXiang) srcCP).srcCP.shangHaiSrcWJ;
			} else if (srcCP instanceof GangLie) {
				srcWJ = ((GangLie) srcCP).srcCP.shangHaiSrcWJ;
			} else {
				// ???
				srcWJ = srcCP.shangHaiSrcWJ;
			}
		} else {
			if (srcCP.shangHaiSrcWJ == null) {
				this.gameApp.libGameViewData.logInfo(
						"Error:反馈对象为空,CP=" + srcCP, Type.logDelay.NoDelay);
				return;
			} else {
				srcWJ = srcCP.shangHaiSrcWJ;
			}
		}

		if (srcWJ == null)
			return;

		if (srcWJ.shouPai.size() == 0 && !srcWJ.zhuangBei.containsZB())
			return;

		if (this.tuoGuan) {
			CardPai cp = null;
			if (this.equals(srcWJ) || this.isFriend(srcWJ)) {
				if (srcWJ.blood < srcWJ.getMaxBlood()
						&& srcWJ.zhuangBei.fangJu != null
						&& srcWJ.zhuangBei.fangJu instanceof BaiYinShiZi) {
					cp = srcWJ.zhuangBei.fangJu;
					srcWJ.unstallZhuangBei(srcWJ.zhuangBei.fangJu);
				}
			} else {
				// srcWJ is Oppt
				if (cp == null && srcWJ.zhuangBei.wuQi != null) {
					cp = srcWJ.zhuangBei.wuQi;
					srcWJ.unstallZhuangBei(srcWJ.zhuangBei.wuQi);
				}
				if (cp == null && srcWJ.zhuangBei.fangJu != null) {
					cp = srcWJ.zhuangBei.fangJu;

					srcWJ.unstallZhuangBei(srcWJ.zhuangBei.fangJu);
				}
				if (cp == null && srcWJ.zhuangBei.jiaYiMa != null) {
					cp = srcWJ.zhuangBei.jiaYiMa;

					srcWJ.unstallZhuangBei(srcWJ.zhuangBei.jiaYiMa);
				}
				if (cp == null && srcWJ.zhuangBei.jianYiMa != null) {
					cp = srcWJ.zhuangBei.jianYiMa;

					srcWJ.unstallZhuangBei(srcWJ.zhuangBei.jianYiMa);
				}
				if (cp == null && srcWJ.shouPai.size() > 0) {
					cp = srcWJ.shouPai.get(0);

					srcWJ.detatchCardPaiFromShouPai(cp);
				}
			}

			if (cp != null) {
				cp.belongToWuJiang = this;
				cp.cpState = Type.CPState.ShouPai;
				this.shouPai.add(cp);

				UpdateWJViewData item = new UpdateWJViewData();
				item.updateShouPaiNumber = true;
				this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(
						this, item);

				this.gameApp.libGameViewData.logInfo(this.dispName + "["
						+ this.jiNengN1 + "]从" + srcWJ.dispName + "获得1张牌",
						Type.logDelay.Delay);
			}
		} else {
			this.gameApp.ynData.reset();
			this.gameApp.ynData.okTxt = "确认";
			this.gameApp.ynData.cancelTxt = "取消";
			this.gameApp.ynData.genInfo = "是否发动" + this.jiNengN1 + "从"
					+ srcWJ.dispName + "获取1张牌?";

			YesNoDialog dlg = new YesNoDialog(this.gameApp.gameActivityContext,
					this.gameApp);
			dlg.showDialog();
			if (!this.gameApp.ynData.result)
				return;

			// first select one shoupai
			this.gameApp.wjDetailsViewData.reset();
			this.gameApp.wjDetailsViewData.selectedCardNumber = 1;
			this.gameApp.wjDetailsViewData.canViewShouPai = false;
			this.gameApp.wjDetailsViewData.selectedWJ = srcWJ;

			WuJiangCardPaiData wjCPData = new WuJiangCardPaiData();
			wjCPData.zhuangBei.fangJu = srcWJ.zhuangBei.fangJu;
			wjCPData.zhuangBei.wuQi = srcWJ.zhuangBei.wuQi;
			wjCPData.zhuangBei.jianYiMa = srcWJ.zhuangBei.jianYiMa;
			wjCPData.zhuangBei.jiaYiMa = srcWJ.zhuangBei.jiaYiMa;
			wjCPData.shouPai = srcWJ.shouPai;

			SelectCardPaiFromWJDialog dlg2 = new SelectCardPaiFromWJDialog(
					this.gameApp.gameActivityContext, this.gameApp, wjCPData);
			dlg2.showDialog();

			CardPai cp = this.gameApp.wjDetailsViewData.selectedCardPai1;

			if (cp != null) {
				if (cp.cpState == Type.CPState.ShouPai) {
					srcWJ.detatchCardPaiFromShouPai(cp);
				} else if (cp.cpState == Type.CPState.fangJuPai) {
					srcWJ.unstallZhuangBei(srcWJ.zhuangBei.fangJu);
				} else if (cp.cpState == Type.CPState.wuQiPai) {
					srcWJ.unstallZhuangBei(srcWJ.zhuangBei.wuQi);
				} else if (cp.cpState == Type.CPState.jianYiMaPai) {
					srcWJ.unstallZhuangBei(srcWJ.zhuangBei.jianYiMa);
				} else if (cp.cpState == Type.CPState.jiaYiMaPai) {
					srcWJ.unstallZhuangBei(srcWJ.zhuangBei.jiaYiMa);
				}

				cp.belongToWuJiang = this;
				cp.cpState = Type.CPState.ShouPai;
				this.shouPai.add(cp);

				this.gameApp.gameLogicData.wjHelper
						.updateWJ8ShouPaiToLibGameView();

				this.gameApp.libGameViewData.logInfo(this.dispName + "["
						+ this.jiNengN1 + "]从" + srcWJ.dispName + "获得1张牌",
						Type.logDelay.Delay);
			}

		}
	}

	// JiNeng2: GuiCai
	public CardPai listenPanDingCardPaiEvent(WuJiang tarWJ, CardPai tarCP,
			CardPai curPDCP) {

		CardPai newPDCP = null;
		// tarCP can be base CP: BaGuaZhen,BingLiangCunDuan,LeBuShiShu,ShanDian
		// tarCP can be WJ JiNeng: TieJi,GangLie,LuoShen,LeiJi
		if (this.shouPai.size() == 0)
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
					+ "判定牌是" + curPDCP + ",是否使用" + this.jiNengN2 + "打出1张手牌代替之?";

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
				wjCPData.shouPai = this.shouPai;

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
			this.gameApp.libGameViewData.logInfo(this.dispName + "["
					+ this.jiNengN2 + "]打出" + newPDCP, Type.logDelay.Delay);
		}
		// null means do not change
		return newPDCP;
	}

	public CardPai handleBaGuaZhenPDCP(WuJiang tarWJ, CardPai tarCP,
			CardPai curPDCP) {
		CardPai newPDCP = null;
		boolean isBlackCP = (curPDCP.clas == Type.CardPaiClass.HeiTao || curPDCP.clas == Type.CardPaiClass.MeiHua) ? true
				: false;
		if (isBlackCP && this.equals(tarWJ)) {
			if (this.selectCardPaiFromShouPai(Type.CardPai.Shan) == null) {
				// I do not have shan
				newPDCP = this.hasRedCardPaiInShouPai();
			}
		} else if (isBlackCP && this.isFriend(tarWJ)) {
			boolean helpMyFriend = false;
			if (tarWJ.shouPai.size() < tarWJ.blood
					&& tarWJ.blood < tarWJ.getMaxBlood())
				helpMyFriend = true;
			if (tarWJ.role == Type.Role.ZhuGong && tarWJ.blood <= 2)
				helpMyFriend = true;

			if (helpMyFriend) {
				newPDCP = this.hasRedCardPaiInShouPai();
			}
		} else if (!isBlackCP && this.isOpponent(tarWJ)) {
			newPDCP = this.hasBlackCardPaiInShouPai();
		}

		// by default, do not change
		return newPDCP;
	}

	public CardPai handleBingLiangCunDuanPDCP(WuJiang tarWJ, CardPai tarCP,
			CardPai curPDCP) {
		CardPai newPDCP = null;
		boolean isMeiHuaCP = (curPDCP.clas == Type.CardPaiClass.MeiHua) ? true
				: false;
		if (!isMeiHuaCP && this.equals(tarWJ)) {
			newPDCP = this.selectFromShouPaiByClass(Type.CardPaiClass.MeiHua);
		} else if (!isMeiHuaCP && this.isFriend(tarWJ)) {
			boolean helpMyFriend = false;
			if (tarWJ.shouPai.size() < tarWJ.blood
					&& tarWJ.blood < tarWJ.getMaxBlood())
				helpMyFriend = true;
			if (tarWJ.role == Type.Role.ZhuGong && tarWJ.blood <= 2)
				helpMyFriend = true;

			if (helpMyFriend) {
				newPDCP = this
						.selectFromShouPaiByClass(Type.CardPaiClass.MeiHua);
			}
		} else if (isMeiHuaCP && this.isOpponent(tarWJ)) {
			newPDCP = this.selectFromShouPaiByClass(Type.CardPaiClass.HeiTao);
			if (newPDCP == null)
				newPDCP = this.hasRedCardPaiInShouPai();
		}
		// by default, do not change
		return newPDCP;
	}

	public CardPai handleLeBuShiShuPDCP(WuJiang tarWJ, CardPai tarCP,
			CardPai curPDCP) {
		CardPai newPDCP = null;
		boolean isHongTaoCP = (curPDCP.clas == Type.CardPaiClass.HongTao) ? true
				: false;
		if (!isHongTaoCP && this.equals(tarWJ)) {
			newPDCP = this.selectFromShouPaiByClass(Type.CardPaiClass.HongTao);
		} else if (!isHongTaoCP && this.isFriend(tarWJ)) {
			boolean helpMyFriend = false;
			if (tarWJ.shouPai.size() < tarWJ.blood
					&& tarWJ.blood < tarWJ.getMaxBlood())
				helpMyFriend = true;
			if (tarWJ.role == Type.Role.ZhuGong && tarWJ.blood <= 2)
				helpMyFriend = true;

			if (helpMyFriend) {
				newPDCP = this
						.selectFromShouPaiByClass(Type.CardPaiClass.HongTao);
			}
		} else if (isHongTaoCP && this.isOpponent(tarWJ)) {
			newPDCP = this.hasBlackCardPaiInShouPai();
			if (newPDCP == null)
				newPDCP = this
						.selectFromShouPaiByClass(Type.CardPaiClass.FangPian);
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

		if (isZhongSD && this.equals(tarWJ)) {
			newPDCP = this.selectNonZhongShanDianCP();
		} else if (isZhongSD && this.isFriend(tarWJ)) {
			boolean helpMyFriend = false;
			if (tarWJ.shouPai.size() < tarWJ.blood
					&& tarWJ.blood < tarWJ.getMaxBlood())
				helpMyFriend = true;
			if (tarWJ.role == Type.Role.ZhuGong && tarWJ.blood <= 2)
				helpMyFriend = true;

			if (helpMyFriend) {
				newPDCP = this.selectNonZhongShanDianCP();
			}
		} else if (!isZhongSD && this.isOpponent(tarWJ)) {
			CardPai tmpPDCP = this
					.selectFromShouPaiByClass(Type.CardPaiClass.HeiTao);
			if (tmpPDCP != null) {
				if (tmpPDCP.number >= 2 && tmpPDCP.number <= 9)
					newPDCP = tmpPDCP;
			}
		}

		// by default, do not change
		return newPDCP;
	}

	public CardPai handleTieJiPDCP(WuJiang tarWJ, CardPai tarCP, CardPai curPDCP) {
		CardPai newPDCP = null;
		boolean isRedCP = (curPDCP.clas == Type.CardPaiClass.FangPian || curPDCP.clas == Type.CardPaiClass.HongTao) ? true
				: false;
		if (isRedCP && this.equals(tarWJ)) {
			newPDCP = this.hasBlackCardPaiInShouPai();
		} else if (isRedCP && this.isFriend(tarWJ)) {
			boolean helpMyFriend = false;
			if (tarWJ.shouPai.size() < tarWJ.blood
					&& tarWJ.blood < tarWJ.getMaxBlood())
				helpMyFriend = true;
			if (tarWJ.role == Type.Role.ZhuGong && tarWJ.blood <= 2)
				helpMyFriend = true;

			if (helpMyFriend) {
				newPDCP = this.hasBlackCardPaiInShouPai();
			}
		} else if (!isRedCP && this.isOpponent(tarWJ)) {
			newPDCP = this.hasRedCardPaiInShouPai();
		}

		// by default, do not change
		return newPDCP;
	}

	public CardPai handleGangLiePDCP(WuJiang tarWJ, CardPai tarCP,
			CardPai curPDCP) {
		CardPai newPDCP = null;
		boolean isHongTaoCP = (curPDCP.clas == Type.CardPaiClass.HongTao) ? true
				: false;
		if (!isHongTaoCP && this.equals(tarWJ)) {
			newPDCP = this.selectFromShouPaiByClass(Type.CardPaiClass.HongTao);
		} else if (!isHongTaoCP && this.isFriend(tarWJ)) {
			boolean helpMyFriend = false;
			if (tarWJ.shouPai.size() < tarWJ.blood
					&& tarWJ.blood < tarWJ.getMaxBlood())
				helpMyFriend = true;
			if (tarWJ.role == Type.Role.ZhuGong && tarWJ.blood <= 2)
				helpMyFriend = true;

			if (helpMyFriend) {
				newPDCP = this
						.selectFromShouPaiByClass(Type.CardPaiClass.HongTao);
			}
		} else if (isHongTaoCP && this.isOpponent(tarWJ)) {
			newPDCP = this.hasBlackCardPaiInShouPai();
			if (newPDCP == null)
				this.selectFromShouPaiByClass(Type.CardPaiClass.FangPian);
		}

		// by default, do not change
		return newPDCP;
	}

	public CardPai handleLuoShenPDCP(WuJiang tarWJ, CardPai tarCP,
			CardPai curPDCP) {
		CardPai newPDCP = null;
		boolean isBlackCP = (curPDCP.clas == Type.CardPaiClass.HeiTao || curPDCP.clas == Type.CardPaiClass.MeiHua) ? true
				: false;
		if (!isBlackCP && this.isFriend(tarWJ)) {
			boolean helpMyFriend = false;
			if (tarWJ.shouPai.size() < tarWJ.blood
					&& tarWJ.blood < tarWJ.getMaxBlood())
				helpMyFriend = true;
			if (tarWJ.role == Type.Role.ZhuGong && tarWJ.blood <= 2)
				helpMyFriend = true;

			if (helpMyFriend) {
				newPDCP = this.hasBlackCardPaiInShouPai();
			}
		} else if (isBlackCP && this.isOpponent(tarWJ)) {
			newPDCP = this.hasRedCardPaiInShouPai();
		}

		// by default, do not change
		return newPDCP;
	}

	public CardPai handleLeiJiPDCP(WuJiang tarWJ, CardPai tarCP, CardPai curPDCP) {
		CardPai newPDCP = null;
		boolean isHeiTaoCP = (curPDCP.clas == Type.CardPaiClass.HeiTao) ? true
				: false;
		if (isHeiTaoCP && (this.equals(tarWJ) || this.isFriend(tarWJ))) {
			newPDCP = this.selectFromShouPaiByClass(Type.CardPaiClass.MeiHua);
			if (newPDCP == null)
				newPDCP = this.hasRedCardPaiInShouPai();
		} else if (!isHeiTaoCP && this.isOpponent(tarWJ)) {
			newPDCP = this.hasCardPaiByClass(Type.CardPaiClass.HeiTao);
		}

		// by default, do not change
		return newPDCP;
	}

	public CardPai selectNonZhongShanDianCP() {
		for (int i = 0; i < this.shouPai.size(); i++) {
			CardPai cp = this.shouPai.get(i);
			if (cp.clas == Type.CardPaiClass.HeiTao
					&& (cp.number >= 2 && cp.number <= 9)) {
				continue;
			} else {
				return cp;
			}
		}
		return null;
	}
}
