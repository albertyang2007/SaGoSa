package alben.sgs.wujiang.instance;

import java.util.ArrayList;

import alben.sgs.android.R;
import alben.sgs.android.dialog.SelectCardPaiFromWJDialog;
import alben.sgs.android.dialog.YesNoDialog;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.FangJuCardPai;
import alben.sgs.cardpai.WuQiCardPai;
import alben.sgs.cardpai.instance.BaGuaZhen;
import alben.sgs.cardpai.instance.QingHongJian;
import alben.sgs.cardpai.instance.ZhuGeLianNu;
import alben.sgs.type.Type;
import alben.sgs.type.WuJiangCardPaiData;
import alben.sgs.wujiang.WuJiang;
import alben.sgs.wujiang.instance.jineng.TianYi;
import android.view.View;

public class TaiShiChi extends WuJiang {
	public boolean tianYiSuccess = false;

	public TaiShiChi(Type.WuJiang n, Type.Country c, Type.Sex s, int b) {
		super(n, c, s, b);
		this.imageNumber = alben.sgs.android.R.drawable.wj_taishici;
		this.jiNengDesc = "(火扩展包武将)\n"
				+ "天义：出牌阶段，你可以和一名角色拼点。若你赢，你获得以下技能直到回合结束：攻击范围无限；可额外使用一张【杀】；使用【杀】时可额外指定一个目标。若你没赢，你不能使用【杀】直到回合结束。每回合限一次。";
		this.dispName = "太史慈";
		this.jiNengN1 = "天义";
	}

	public void reset() {
		super.reset();
		this.tianYiSuccess = false;
	}

	public void listenEnterHuiHeEvent() {
		super.listenEnterHuiHeEvent();
		this.tianYiSuccess = false;
		this.enableWuJiangJiNengBtn();
	}

	public void enableWuJiangJiNengBtn() {
		if (!this.tuoGuan) {
			this.gameApp.libGameViewData.mJiNengBtn1
					.setVisibility(View.VISIBLE);
			this.gameApp.libGameViewData.mJiNengBtn1.setEnabled(true);
			this.gameApp.libGameViewData.mJiNengBtn1Txt.setText(this.jiNengN1);
		}
		// invoke super to set the correct JiNengBtnImage
		super.enableWuJiangJiNengBtn();
	}

	// Overwrite
	public boolean canIChuSha() {
		if (!this.oneTimeJiNengTrigger) {
			return super.canIChuSha();
		} else {
			if (!this.tianYiSuccess) {
				return false;
			} else {
				// tianYi is successfully
				if (this.zhuangBei.wuQi != null
						&& this.zhuangBei.wuQi instanceof ZhuGeLianNu) {
					return true;
				}

				if (this.huiHeChuShaN <= 1) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

	// For AI: Add TianYi cardpai into shoupai
	public CardPai generateJiNengCardPai() {

		if (this.oneTimeJiNengTrigger) {
			return null;
		}

		if (!this.tuoGuan)
			return null;

		if (this.shouPai.size() == 0)
			return null;

		// get the biggest number one
		CardPai maxNCP = this.shouPai.get(0);
		for (int i = 1; i < this.shouPai.size(); i++) {
			if (maxNCP.number < this.shouPai.get(i).number) {
				maxNCP = this.shouPai.get(i);
			}
		}

		// My Max Number of CP is too small
		if (maxNCP.number <= 9)
			return null;

		TianYi tianYi = new TianYi(Type.CardPai.nil, Type.CardPaiClass.nil, 0);
		tianYi.gameApp = this.gameApp;
		tianYi.belongToWuJiang = this;
		tianYi.cpState = Type.CPState.ShouPai;
		tianYi.sp1 = maxNCP;

		tianYi.selectTarWJForAI();
		if (tianYi.getTarWJForAI() != null)
			return tianYi;
		else
			return null;
	}

	public void handleJiNengBtnEvent(int eventID) {

		switch (eventID) {
		case R.id.JiNeng1: {

			if (this.state != Type.State.ChuPai)
				return;

			if (this.oneTimeJiNengTrigger) {
				this.gameApp.libGameViewData.logInfo(this.jiNengN1 + "只能发动一次",
						Type.logDelay.NoDelay);
				return;
			}

			if (this.shouPai.size() == 0) {
				this.gameApp.libGameViewData.logInfo("你没有手牌不能发动"
						+ this.jiNengN1, Type.logDelay.NoDelay);
				return;
			}

			this.gameApp.ynData.reset();
			this.gameApp.ynData.okTxt = "确认";
			this.gameApp.ynData.cancelTxt = "取消";
			this.gameApp.ynData.genInfo = "是否使用" + this.jiNengN1 + "?";

			YesNoDialog dlg = new YesNoDialog(this.gameApp.gameActivityContext,
					this.gameApp);
			dlg.showDialog();
			if (!this.gameApp.ynData.result)
				return;

			// first select one shoupai
			this.gameApp.wjDetailsViewData.reset();
			this.gameApp.wjDetailsViewData.selectedCardNumber = 1;
			this.gameApp.wjDetailsViewData.canViewShouPai = true;
			this.gameApp.wjDetailsViewData.selectedWJ = this;

			WuJiangCardPaiData wjCPData = new WuJiangCardPaiData();
			wjCPData.shouPai = this.shouPai;

			SelectCardPaiFromWJDialog dlg2 = new SelectCardPaiFromWJDialog(
					this.gameApp.gameActivityContext, this.gameApp, wjCPData);
			dlg2.showDialog();

			CardPai maxNCP = this.gameApp.wjDetailsViewData.selectedCardPai1;

			if (maxNCP == null)
				return;

			// then select one wj
			gameApp.selectWJViewData.reset();
			gameApp.selectWJViewData.selectNumber = 1;
			// show wujiang can be selected
			WuJiang tarWJ = this.nextOne;
			while (!tarWJ.equals(this)) {
				// reset first
				gameApp.libGameViewData.imageWJs[tarWJ.imageViewIndex]
						.setBackgroundDrawable(gameApp.getResources()
								.getDrawable(R.drawable.bg_black));
				tarWJ.canSelect = false;
				tarWJ.clicked = false;

				// who can be selected
				if (tarWJ.shouPai.size() > 0) {
					gameApp.libGameViewData.imageWJs[tarWJ.imageViewIndex]
							.setBackgroundDrawable(gameApp.getResources()
									.getDrawable(R.drawable.bg_green));
					tarWJ.canSelect = true;
					tarWJ.clicked = false;
				}

				tarWJ = tarWJ.nextOne;
			}
			// use UI for interaction
			gameApp.gameLogicData.userInterface.askUserSelectWuJiang(
					gameApp.gameLogicData.myWuJiang, "请选择"
							+ gameApp.selectWJViewData.selectNumber + "个武将");

			WuJiang tmpWJ = gameApp.selectWJViewData.selectedWJ1;

			if (tmpWJ == null)
				return;

			TianYi tianYi = new TianYi(Type.CardPai.nil, Type.CardPaiClass.nil,
					0);
			tianYi.gameApp = this.gameApp;
			tianYi.belongToWuJiang = this;
			tianYi.cpState = Type.CPState.ShouPai;
			tianYi.sp1 = maxNCP;

			tianYi.selectedByClick = true;

			// reset myWuJiang shoupai to unselect card pai
			this.resetShouPaiSelectedBoolean();
			// then add liJian into shoupai list
			this.shouPai.add(tianYi);

			if (gameApp.gameLogicData.askForPai == Type.CardPai.notNil) {
				this.gameApp.gameLogicData.userInterface.loop = true;
				this.gameApp.gameLogicData.userInterface
						.sendMessageToUIForWakeUp();
			}
			break;
		}
		}
	}

	// overwrite for AI,just for TianYi Sha,
	// not for ShunShouQuanYang or BingLiangCunDuan
	public int countDistanceWithWuQi(WuJiang fromWJ, WuJiang toWJ,
			WuQiCardPai wuQi) {
		if (this.tianYiSuccess)
			return 0;
		else
			return super.countDistanceWithWuQi(fromWJ, toWJ, wuQi);
	}

	//
	public boolean takeOverShaWork(CardPai srcCP, WuJiang tarWJ1, CardPai tarCP) {

		this.huiHeChuShaN++;

		int origSHN = srcCP.shangHaiN;
		boolean origHeJiu = this.heJiu;

		// first contrast the tempWJList
		ArrayList<WuJiang> tempWJList = new ArrayList<WuJiang>();
		WuJiang curWJ = this.nextOne;
		while (!curWJ.equals(this)) {
			tempWJList.add(curWJ);
			curWJ = curWJ.nextOne;
		}// end

		String wqS = "";
		if (this.zhuangBei.wuQi != null)
			wqS = "[" + this.zhuangBei.wuQi.dispName + "]";

		for (int i = 0; i < tempWJList.size(); i++) {
			if (this.gameApp.gameLogicData.wjHelper.checkMatchOver())
				break;
			WuJiang tarWJ = tempWJList.get(i);
			if (tarWJ.state == Type.State.Dead)
				continue;

			if (this.gameApp.selectWJViewData.selectedWJs.contains(tarWJ)) {
				this.gameApp.selectWJViewData.selectedWJs.remove(tarWJ);
				this.gameApp.libGameViewData.logInfo(this.dispName + wqS
						+ srcCP + tarWJ.dispName, Type.logDelay.Delay);

				// First DaQiao, liuLi
				if (tarWJ instanceof DaQiao) {
					WuJiang llTarWJ = ((DaQiao) tarWJ)
							.LiuLi(this, tarWJ, srcCP);
					if (llTarWJ != null)
						tarWJ = llTarWJ;
				}

				// Then WuQi
				if (this.zhuangBei.wuQi != null) {
					this.zhuangBei.wuQi.listenShaEvent(this, tarWJ, srcCP);
				}

				if (this.zhuangBei.wuQi != null
						&& this.zhuangBei.wuQi instanceof QingHongJian) {
					// QingHongJian wu shi dui fang fangju
					// do nothing
				} else {
					if (tarWJ.zhuangBei.fangJu != null
							&& !(tarWJ.zhuangBei.fangJu instanceof BaGuaZhen)) {
						FangJuCardPai fjcp = (FangJuCardPai) tarWJ.zhuangBei.fangJu;
						if (fjcp.defenceWork(this, tarWJ, srcCP)) {
							return true;
						}
					}
				}

				CardPai shanCP = null;
				shanCP = tarWJ.chuShan(this, srcCP);

				//
				if (shanCP != null) {
					// First WuJiang JiNeng (PangDe)
					this.listenShanEvent(this, tarWJ, srcCP);

					// Then WuQi JiNeng
					if (this.zhuangBei.wuQi != null) {
						this.zhuangBei.wuQi.listenShanEvent(this, tarWJ, srcCP);
					}
				} else {
					// Then WuQi JiNeng
					boolean mzRtn = true;
					if (this.zhuangBei.wuQi != null) {
						mzRtn = this.zhuangBei.wuQi.listenMingZhongEvent(this,
								tarWJ, srcCP);
					}

					if (mzRtn) {
						srcCP.belongToWuJiang.heJiu = origHeJiu;
						srcCP.shangHaiN = origSHN;
						srcCP.countTotalShangHaiN(tarWJ);
						srcCP.shangHaiReason = "";
						srcCP.shangHaiSrcWJ = this;
						tarWJ.increaseBlood(this, srcCP);
					}
				}
			}
		}
		return true;
	}
}