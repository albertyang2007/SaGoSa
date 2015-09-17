package alben.sgs.wujiang;

import java.util.ArrayList;

import alben.sgs.ai.ChuPaiAIHelper;
import alben.sgs.android.GameApp;
import alben.sgs.android.R;
import alben.sgs.android.data.LibGameViewData;
import alben.sgs.android.dialog.SelectCardPaiFromWJDialog;
import alben.sgs.android.dialog.SelectHuaShiDialog;
import alben.sgs.android.dialog.YesNoDialog;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.FangJuCardPai;
import alben.sgs.cardpai.JinNangCardPai;
import alben.sgs.cardpai.MaCardPai;
import alben.sgs.cardpai.WuQiCardPai;
import alben.sgs.cardpai.ZhuangBeiCardPai;
import alben.sgs.cardpai.instance.BaGuaZhen;
import alben.sgs.cardpai.instance.BaiYinShiZi;
import alben.sgs.cardpai.instance.BingLiangCunDuan;
import alben.sgs.cardpai.instance.LeBuShiShu;
import alben.sgs.cardpai.instance.NanManRuQin;
import alben.sgs.cardpai.instance.QingHongJian;
import alben.sgs.cardpai.instance.RenWangDun;
import alben.sgs.cardpai.instance.Sha;
import alben.sgs.cardpai.instance.ShanDian;
import alben.sgs.cardpai.instance.TengJia;
import alben.sgs.cardpai.instance.TieSuoLianHuan;
import alben.sgs.cardpai.instance.WanJianQiFa;
import alben.sgs.cardpai.instance.WuXieKeJi;
import alben.sgs.cardpai.instance.ZBSMSha;
import alben.sgs.cardpai.instance.ZhangBaSheMao;
import alben.sgs.cardpai.instance.ZhuGeLianNu;
import alben.sgs.cardpai.instance.ZhuQueYuShan;
import alben.sgs.common.Helper;
import alben.sgs.common.LoopWuJiangHelper;
import alben.sgs.event.ChuPaiEvent;
import alben.sgs.event.TaoEvent;
import alben.sgs.event.WuXieKeJiEvent;
import alben.sgs.listerner.ChuPaiListerner;
import alben.sgs.type.AuditResult;
import alben.sgs.type.CardPaiActionForTarWuJiang;
import alben.sgs.type.PanDingResult;
import alben.sgs.type.Type;
import alben.sgs.type.UpdateWJViewData;
import alben.sgs.type.WuJiangCardPaiData;
import alben.sgs.type.WuJiangZhuangBei;
import alben.sgs.wujiang.instance.HuangYueYing;
import alben.sgs.wujiang.instance.LvMeng;
import alben.sgs.wujiang.instance.SunQuan;
import alben.sgs.wujiang.instance.TaiShiChi;
import alben.sgs.wujiang.instance.ZhangJiao;
import alben.sgs.wujiang.instance.ZhuGeLiang;
import alben.sgs.wujiang.instance.jineng.HuangTian;
import android.view.View;

public class WuJiang implements ChuPaiListerner {

	public Helper commonHelper = new Helper();

	public Type.WuJiang name = Type.WuJiang.nil;
	public Type.Role role = Type.Role.Nil;
	public Type.Country country = Type.Country.Nil;
	public Type.Sex sex = Type.Sex.nil;
	public Type.State state = Type.State.nil;
	public String dispName = "";
	private int maxBlood = 0;
	public int blood = 0;
	public int imageNumber = 0;// link to src of image
	public int imageViewIndex = 0;// for myWJ, index is 7
	public String jiNengDesc = new String("");
	public WuJiangZhuangBei zhuangBei = new WuJiangZhuangBei();
	public WuJiang nextOne = null;
	public WuJiang preOne = null;
	public GameApp gameApp = null;
	public LibGameViewData libGameData = null;
	public boolean tuoGuan = true;
	public boolean allocated = false;
	public boolean canSelect = false;// can reach this wu jiang
	public boolean clicked = false;
	public PanDingResult pdResult = new PanDingResult();
	public ArrayList<WuJiang> friendList = new ArrayList<WuJiang>();
	public ArrayList<WuJiang> opponentList = new ArrayList<WuJiang>();
	public ArrayList<CardPai> shouPai = new ArrayList<CardPai>();
	public ArrayList<CardPai> panDingPai = new ArrayList<CardPai>();
	public boolean lianHuan = false;
	public boolean fanMian = false;
	public int huiHeChuShaN = 0;
	public String specialChuPaiReason = "";
	public boolean heJiu = false;

	public boolean oneTimeJiNengTrigger = false;
	public boolean huangTianJiNengTrigger = false;

	public String jiNengN1 = "";
	public String jiNengN2 = "";
	public String jiNengN3 = "";
	public String jiNengN4 = "";

	public int wj8ShouPaiCurPage = 0;// use for show wj8 shou pai by page

	public WuJiang(Type.WuJiang n, Type.Country c, Type.Sex s, int b) {
		this.name = n;
		this.country = c;
		this.sex = s;
		this.maxBlood = b;
		this.blood = this.maxBlood;
	}

	public void reset() {
		this.zhuangBei.reset();
		while (this.shouPai.size() > 0) {
			this.shouPai.get(0).reset();
			this.shouPai.remove(0);
		}
		while (this.panDingPai.size() > 0) {
			this.panDingPai.get(0).reset();
			this.panDingPai.remove(0);
		}
		this.tuoGuan = true;
		this.allocated = false;
		this.clicked = false;
		this.imageViewIndex = 0;
		this.blood = this.maxBlood;
		this.pdResult.reset();
		this.canSelect = false;
		this.lianHuan = false;
		this.fanMian = false;
		this.nextOne = null;
		this.preOne = null;
		this.huiHeChuShaN = 0;
		this.specialChuPaiReason = "";
		this.heJiu = false;
		this.oneTimeJiNengTrigger = false;
		this.huangTianJiNengTrigger = false;
		this.wj8ShouPaiCurPage = 0;

		while (this.friendList.size() > 0) {
			this.friendList.remove(0);
		}
		while (this.opponentList.size() > 0) {
			this.opponentList.remove(0);
		}
	}

	public void setRole(Type.Role r) {
		this.role = r;
	}

	public void setGame(GameApp g) {
		this.gameApp = g;
		this.libGameData = this.gameApp.libGameViewData;
	}

	public int getMaxBlood() {
		if (this.role == Type.Role.ZhuGong)
			return this.gameApp.gameLogicData.zhuGongMaxBlood;
		else
			return this.maxBlood;
	}

	public int getOrigMaxBlood() {
		return this.maxBlood;
	}

	// srcWJ use srcCP shangHai this WJ
	// remember to set shangHaiSrcWJ when calling this method!
	public void increaseBlood(WuJiang srcWJ, CardPai srcCP) {

		boolean origLianHuan = this.lianHuan;

		// handle srcWJ jiNeng right away
		// Just like: WeiYuan
		if (srcWJ != null && srcWJ.state != Type.State.Dead) {
			srcWJ.listenIncreaseBloodEvent(this, srcCP);
		}

		if (this.zhuangBei.fangJu != null) {
			this.zhuangBei.fangJu.listenIncreaseBloodEvent(srcCP);
		}

		if (srcCP.shangHaiN >= 0) {
			this.gameApp.libGameViewData.logInfo(this.dispName + "恢复"
					+ Math.abs(srcCP.shangHaiN) + "滴血", Type.logDelay.Delay);
		} else {
			this.gameApp.libGameViewData.logInfo(this.dispName + "失去"
					+ Math.abs(srcCP.shangHaiN) + "滴血", Type.logDelay.Delay);
		}

		this.blood = this.blood + srcCP.shangHaiN;

		if (this.blood >= this.getMaxBlood())
			this.blood = this.getMaxBlood();

		UpdateWJViewData item = new UpdateWJViewData();
		item.updateBlood = true;
		item.updateLianHuan = true;
		this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(this,
				item);

		if (this.blood <= 0) {
			libGameData.logInfo(this.dispName + "临死,求桃", Type.logDelay.Delay);
			int rtnTao = this.askForTao(srcCP, (1 - this.blood));

			if ((rtnTao + this.blood) >= 1) {
				// alive
				this.blood += rtnTao;
				libGameData.logInfo(this.dispName + "被救活", Type.logDelay.Delay);
			} else {
				// wj dead
				this.dead(srcCP);
			}
			this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(
					this, item);
		}

		// handle this WJ jiNeng right away
		// Just like: CaoCao, XiaHou
		if (srcCP.shangHaiN < 0 && this.state != Type.State.Dead) {
			this.listenIncreaseBloodEvent(srcCP);
		}

		// check if this wj is link to other, make sure below method is
		// invoked only one time
		if (origLianHuan && srcCP.linkImpact && !srcCP.countLinkImpactCompleted
				&& (!this.gameApp.gameLogicData.wjHelper.checkMatchOver())) {
			if (this.state != Type.State.Dead) {
				// this wj is still alive, had already count the shangHai
				this.lianHuan = false;
				this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(
						this, item);
			}
			srcCP.countLinkImpactCompleted = true;
			TieSuoLianHuan.increaseBloodForOtherLinkWJ(this.gameApp,
					this.gameApp.gameLogicData.wjHelper
							.findNextOne(this.imageViewIndex), srcCP);
		}
		//
		if ((srcCP.countLinkImpactCompleted) && (this.lianHuan))
			this.lianHuan = false;
	}

	public boolean equals(WuJiang wj2) {
		if (wj2 != null && this.name.equals(wj2.name))
			return true;
		return false;
	}

	public void run() {
		if (this.state == Type.State.Dead) {
			return;
		}

		// check fan mian
		if (this.fanMian) {
			this.fanMian = false;
			this.gameApp.libGameViewData.logInfo(this.dispName + "翻面,跳过此回合",
					Type.logDelay.Delay);

			UpdateWJViewData item = new UpdateWJViewData();
			item.updateFanMian = true;
			this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(
					this, item);
			return;
		}

		// run audit
		this.gameApp.gameLogicData.wjHelper.auditAllRunningWuJiang();

		libGameData.logInfo(this.dispName + "回合阶段 ", Type.logDelay.Delay);

		this.gameApp.gameLogicData.curChuPaiWJ = this;

		gameApp.libGameViewData.linearWJs[this.imageViewIndex]
				.setBackgroundDrawable(this.gameApp.getResources().getDrawable(
						R.drawable.bg_red));

		this.listenEnterHuiHeEvent();

		this.panDing();

		if (this.pdResult.checkGameOver) {
			if (this.gameApp.gameLogicData.wjHelper.checkMatchOver()
					|| this.state == Type.State.Dead)
				return;
		}

		if (!this.pdResult.bingLiangCunDunOK) {
			this.moPai();
		}
		if (!this.pdResult.leBuShiShuOK) {
			this.chuPai();
			if (this.gameApp.gameLogicData.wjHelper.checkMatchOver()
					|| this.state == Type.State.Dead)
				return;
		}
		this.qiPai();

		this.state = Type.State.Response;

		gameApp.libGameViewData.linearWJs[this.imageViewIndex]
				.setBackgroundDrawable(this.gameApp.getResources().getDrawable(
						R.drawable.bg_default));

		this.listenExitHuiHeEvent();
	}

	public void panDing() {

		// before panding, set shan dian active to true
		for (int i = 0; i < this.panDingPai.size(); i++) {
			if (this.panDingPai.get(i) instanceof ShanDian) {
				((ShanDian) this.panDingPai.get(i)).active = true;
			}
		}

		// this shan dian is added after current shan dian's panding
		ShanDian newSD = null;

		while (this.panDingPai.size() > 0) {
			if (this.state == Type.State.Dead)
				break;
			CardPai cp = this.panDingPai.get(this.panDingPai.size() - 1);
			if (cp instanceof LeBuShiShu) {
				LeBuShiShu lbss = (LeBuShiShu) cp;
				this.pdResult.leBuShiShuOK = lbss.panDing(this, null, null);
			}
			if (cp instanceof BingLiangCunDuan) {
				BingLiangCunDuan blcd = (BingLiangCunDuan) cp;
				this.pdResult.bingLiangCunDunOK = blcd
						.panDing(this, null, null);
			}
			if (cp instanceof ShanDian) {
				ShanDian sd = (ShanDian) cp;
				if (sd.active) {
					this.pdResult.checkGameOver = sd.panDing(this, null, null);
				} else {
					// 当只有2个武将存在而且有2个闪电存在的时候,为了
					// 避免 死循环,暂时将这个active=false的新闪电移走
					newSD = sd;
					this.panDingPai.remove(sd);
				}
			}
		}

		// 恢复新闪电
		if (newSD != null) {
			this.panDingPai.add(newSD);

			UpdateWJViewData item = new UpdateWJViewData();
			item.updatePangDing = true;
			this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(
					this, item);
		}
	}

	public void moPai() {

		this.state = Type.State.MoPai;
		libGameData.logInfo(this.dispName + "摸牌阶段", Type.logDelay.Delay);

		CardPai cp1 = this.gameApp.gameLogicData.cpHelper.popCardPai();
		CardPai cp2 = this.gameApp.gameLogicData.cpHelper.popCardPai();

		cp1.belongToWuJiang = this;
		cp2.belongToWuJiang = this;
		cp1.cpState = Type.CPState.ShouPai;
		cp2.cpState = Type.CPState.ShouPai;

		this.shouPai.add(cp1);
		this.shouPai.add(cp2);

		if (!this.tuoGuan) {
			this.gameApp.libGameViewData.logInfo(this.dispName + "摸起2张手牌",
					Type.logDelay.Delay);
			this.gameApp.gameLogicData.wjHelper.updateWJ8ShouPaiToLibGameView();
		} else {
			UpdateWJViewData item = new UpdateWJViewData();
			item.updateShouPaiNumber = true;
			this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(
					this, item);
			this.gameApp.libGameViewData.logInfo(this.dispName + "摸起2张手牌",
					Type.logDelay.Delay);
		}
	}

	public void chuPai() {

		this.state = Type.State.ChuPai;
		libGameData.logInfo(this.dispName + "出牌阶段", Type.logDelay.Delay);

		// run audit
		this.gameApp.gameLogicData.wjHelper.auditAllRunningWuJiang();

		if (this.tuoGuan) {

			// set cur chu pai wj to red, other is black
			this.gameApp.gameLogicData.wjHelper.setCurChuPaiWJColor(this);

			ChuPaiAIHelper cpAIHelper = new ChuPaiAIHelper(this.gameApp, this);
			cpAIHelper.ConstructChuPaiList();
			CardPai cp = cpAIHelper.popOneShouPai();
			WuJiang tarWJ = null;
			while (cp != null) {

				this.gameApp.gameLogicData.curChuPaiCP = cp;
				this.gameApp.gameLogicData.someWJDeadDuringChuPai = false;
				this.specialChuPaiReason = "";

				this.detatchCardPaiFromShouPai(cp);
				tarWJ = cp.getTarWJForAI();
				cp.work(this, tarWJ, null);

				// after every chu pai, check gameover or dead
				if (this.gameApp.gameLogicData.wjHelper.checkMatchOver()
						|| this.state == Type.State.Dead) {
					break;
				}

				if ((tarWJ != null && tarWJ.state == Type.State.Dead)
						|| this.gameApp.gameLogicData.someWJDeadDuringChuPai) {
					this.gameApp.gameLogicData.wjHelper.constructFriOpptList();
				}

				this.gameApp.selectWJViewData.reset();

				cpAIHelper.ConstructChuPaiList();
				cp = cpAIHelper.popOneShouPai();

				// run audit
				this.gameApp.gameLogicData.wjHelper.auditAllRunningWuJiang();
			}
		} else {
			// not TuoGuan
			gameApp.gameLogicData.huiHeJieSu = false;
			this.resetAllWuJiangForSelect();
			while (!gameApp.gameLogicData.huiHeJieSu) {
				this.gameApp.gameLogicData.wjHelper
						.updateWJ8ShouPaiToLibGameView();
				// use ui to ask chu pai, nil means can chu other pai
				this.gameApp.gameLogicData.askForPai = Type.CardPai.notNil;
				this.gameApp.gameLogicData.askForHuaShi = Type.CardPaiClass.nil;
				this.gameApp.selectWJViewData.reset();
				this.gameApp.gameLogicData.wjHelper.setCurChuPaiWJColor(this);
				this.specialChuPaiReason = "";

				CardPai cp = this.gameApp.gameLogicData.userInterface
						.askUserChuPai(this, "请你出牌");

				if (cp != null) {
					this.gameApp.gameLogicData.curChuPaiCP = cp;
					WuJiang tarWJ = this.gameApp.selectWJViewData.selectedWJ1;

					cp.work(this, tarWJ, null);

					// after every chu pai, check gameover or dead
					if (this.gameApp.gameLogicData.wjHelper.checkMatchOver()
							|| this.state == Type.State.Dead) {
						break;
					}
				}// if (cp != null)

				// reset
				this.gameApp.selectWJViewData.reset();
				this.gameApp.wjDetailsViewData.reset();

				// update view
				this.gameApp.gameLogicData.wjHelper
						.updateWJ8ShouPaiToLibGameView();
				// reset my wujiang to red in case change during chupai
				this.gameApp.gameLogicData.wjHelper.setCurChuPaiWJColor(this);

				// run audit
				this.gameApp.gameLogicData.wjHelper.auditAllRunningWuJiang();
			}// while (!gameApp.gameLogicData.huiHeJieSu)

			this.gameApp.gameLogicData.askForPai = Type.CardPai.nil;
			this.gameApp.gameLogicData.askForHuaShi = Type.CardPaiClass.nil;
			this.gameApp.selectWJViewData.reset();
		}
	}

	public void qiPai() {
		this.state = Type.State.QiPai;
		libGameData.logInfo(this.dispName + "弃牌阶段", Type.logDelay.Delay);

		this.gameApp.gameLogicData.discardShouPaiN = this.shouPai.size()
				- this.blood;

		if (this.gameApp.gameLogicData.discardShouPaiN <= 0)
			return;

		if (this.tuoGuan) {
			UpdateWJViewData item = new UpdateWJViewData();
			item.updateShouPaiNumber = true;
			while (this.shouPai.size() > this.blood) {
				// random to discard the shou pai
				CardPai sp = this.shouPai.get(0);
				sp.belongToWuJiang = null;
				sp.cpState = Type.CPState.FeiPaiDui;
				this.detatchCardPaiFromShouPai(sp);
				libGameData.logInfo(this.dispName + "丢弃手牌" + sp,
						Type.logDelay.HalfDelay);
			}
		} else {
			this.gameApp.gameLogicData.wjHelper.updateWJ8ShouPaiToLibGameView();
			// ask user to select card pai to discard
			this.gameApp.gameLogicData.userInterface.discardShouPai(this);

			int newLen = this.shouPai.size();
			for (int i = 0; i < newLen;) {
				CardPai sp = this.shouPai.get(i);
				if (sp.selectedByClick) {
					sp.belongToWuJiang = null;
					sp.cpState = Type.CPState.FeiPaiDui;
					this.shouPai.remove(sp);
					newLen = this.shouPai.size();
					libGameData.logInfo(this.dispName + "丢弃手牌" + sp,
							Type.logDelay.HalfDelay);
				} else {
					i++;
				}
			}

			// update view
			this.gameApp.gameLogicData.wjHelper.updateWJ8ShouPaiToLibGameView();
		}
	}

	// set invisibility for all shou pai
	public void resetShouPaiSelectedBoolean() {
		// reset all the selected ind
		for (int i = 0; i < this.shouPai.size(); i++) {
			CardPai cp = (CardPai) this.shouPai.get(i);
			cp.selectedByClick = false;
		}
	}

	public int countSelectedShouPai() {
		int rtn = 0;
		for (int i = 0; i < this.shouPai.size(); i++) {
			CardPai cp = (CardPai) this.shouPai.get(i);
			if (cp.selectedByClick)
				rtn++;
		}
		return rtn;
	}

	// return the frist select card pai
	public CardPai getSelectedShouPai() {
		for (int i = 0; i < this.shouPai.size(); i++) {
			CardPai cp = (CardPai) this.shouPai.get(i);
			if (cp.selectedByClick) {
				return cp;
			}
		}
		return null;
	}

	public void chuPaiEvent(ChuPaiEvent cpEvent) {
		if (this.state == Type.State.Dead) {
			return;
		}
	}

	public void askForWuXieKeJiEvent(ChuPaiEvent cpEvent) {
		if (this.state == Type.State.Dead) {
			return;
		}

		if (!(cpEvent instanceof WuXieKeJiEvent)) {
			libGameData.logInfo("Error, cpEvent is not WuXieKeJiEvent.",
					Type.logDelay.NoDelay);
			return;
		}

		WuXieKeJiEvent wxkjCPEvent = (WuXieKeJiEvent) cpEvent;

		if (!this.hasWuXieKeJi())
			return;

		if (cpEvent.tarCardPai != null
				&& cpEvent.tarCardPai instanceof JinNangCardPai) {
			CardPai cp = null;
			if (!this.tuoGuan) {
				String tarWJN = (cpEvent.tarWuJiang == null) ? "Error"
						: cpEvent.tarWuJiang.dispName;
				this.gameApp.gameLogicData.askForPai = Type.CardPai.WuXieKeJi;
				cp = this.gameApp.gameLogicData.userInterface.askUserChuPai(
						this, "是否对" + tarWJN + "无懈可击?");
			} else {
				// tuo Guan mode

				// check if AOE with TengJia defence, no need wuxiekeji if
				// have TengJia
				if (cpEvent.srcCardPai instanceof WanJianQiFa
						|| cpEvent.srcCardPai instanceof NanManRuQin) {
					if (cpEvent.tarWuJiang.zhuangBei.fangJu != null
							&& cpEvent.tarWuJiang.zhuangBei.fangJu instanceof TengJia) {
						return;
					}
				}

				// check if there is panDing in my area
				for (int i = 0; i < this.panDingPai.size(); i++) {
					if (this.panDingPai.get(i) instanceof LeBuShiShu
							&& this.shouPai.size() > this.blood) {
						// remain this wuxiekeji for myself
						return;
					} else if (this.panDingPai.get(i) instanceof BingLiangCunDuan
							&& this.shouPai.size() < this.blood) {
						// remain this wuxiekeji for myself
						return;
					}
				}

				if (this.equals(cpEvent.tarWuJiang)) {
					if (wxkjCPEvent.isNotOkForMe()) {
						cp = this.chuWuXieKeJi(cpEvent.tarWuJiang);
					}
				}
				if (this.isFriend(cpEvent.tarWuJiang)) {
					if (wxkjCPEvent.isNotOkForMe()) {
						cp = this.chuWuXieKeJi(cpEvent.tarWuJiang);
					}
				} else if (this.isOpponent(cpEvent.tarWuJiang)) {
					if (wxkjCPEvent.isOkForOppt()) {
						cp = this.chuWuXieKeJi(cpEvent.tarWuJiang);
					}
				}
			}

			// notify others
			if (cp != null) {
				this.detatchCardPaiFromShouPai(cp);
				wxkjCPEvent.yiChuWuXieKeJiNumber++;
				libGameData.logInfo(this.dispName + "出牌" + cp,
						Type.logDelay.Delay);

				// only for wj huangyueying
				if (this instanceof HuangYueYing) {
					((WuXieKeJi) cp).listenPreWorkEvent(this,
							wxkjCPEvent.tarWuJiang, wxkjCPEvent.tarCardPai);
				}

				this.notifyChuPaiEvent(this, cp, cpEvent);
			}
		}
	}

	public void askForTaoEvent(ChuPaiEvent cpEvent) {
		if (this.state == Type.State.Dead) {
			return;
		}

		if (this.tuoGuan && !this.hasTao(cpEvent.srcWuJiang))
			return;

		if (!(cpEvent instanceof TaoEvent)) {
			libGameData.logInfo("Error, cpEvent is not TaoEvent.",
					Type.logDelay.NoDelay);
			return;
		}

		TaoEvent taoEvent = (TaoEvent) cpEvent;

		while ((this.hasTao(cpEvent.srcWuJiang) || !this.tuoGuan)
				&& (taoEvent.yiChuTaoNumber < taoEvent.requestTaoNumber)) {
			CardPai cp = null;
			if (!this.tuoGuan) {
				this.gameApp.gameLogicData.askForPai = Type.CardPai.Tao;
				cp = this.gameApp.gameLogicData.userInterface.askUserChuPai(
						this, "是否对" + cpEvent.srcWuJiang.dispName + "出桃?");
			} else {
				// tuo Guan mode
				if (this.equals(cpEvent.srcWuJiang)
						|| this.isFriend(cpEvent.srcWuJiang)) {
					if (taoEvent.yiChuTaoNumber < taoEvent.requestTaoNumber) {
						cp = this.chuTao(cpEvent.srcWuJiang);
					}
				}
			}

			// notify others
			if (cp != null) {
				cp.belongToWuJiang = null;
				cp.cpState = Type.CPState.FeiPaiDui;

				libGameData.logInfo(this.dispName + "出牌" + cp + " "
						+ this.specialChuPaiReason, Type.logDelay.Delay);

				if (cpEvent.srcWuJiang instanceof SunQuan
						&& cpEvent.srcWuJiang.role == Type.Role.ZhuGong
						&& !this.equals(cpEvent.srcWuJiang)
						&& this.country == Type.Country.Wu) {
					taoEvent.yiChuTaoNumber++;
					taoEvent.yiChuTaoNumber++;
					libGameData.logInfo(cpEvent.srcWuJiang.dispName + "的"
							+ cpEvent.srcWuJiang.jiNengN2 + "触发",
							Type.logDelay.Delay);
				} else {
					taoEvent.yiChuTaoNumber++;
				}
			} else {
				// bu chu tao, exit
				break;
			}
		}
	}

	// For TianYi
	public CardPai askOneCPForCompare(WuJiang srcWJ) {
		if (this.shouPai.size() == 0)
			return null;
		if (this.tuoGuan) {
			if (this.isFriend(srcWJ)) {
				// get minNCP
				CardPai minNCP = this.shouPai.get(0);
				for (int i = 1; i < this.shouPai.size(); i++) {
					if (minNCP.number > this.shouPai.get(i).number) {
						minNCP = this.shouPai.get(i);
					}
				}
				return minNCP;
			} else {
				// get maxNCP
				CardPai maxNCP = this.shouPai.get(0);
				for (int i = 1; i < this.shouPai.size(); i++) {
					if (maxNCP.number < this.shouPai.get(i).number) {
						maxNCP = this.shouPai.get(i);
					}
				}
				return maxNCP;
			}
		} else {
			return this.gameApp.gameLogicData.userInterface
					.askUserShowOneCardPai(this, "请出示一张卡牌拼点");
		}
	}

	public boolean hasCardPai(Type.CardPai cpT) {
		for (int i = 0; i < this.shouPai.size(); i++) {
			CardPai cp = (CardPai) this.shouPai.get(i);
			if (cp.name == cpT)
				return true;
		}
		return false;
	}

	public boolean hasTao(WuJiang srcWJ) {
		boolean rtn = false;
		if (this.equals(srcWJ)) {
			// if the askFor Tao WJ is myself
			rtn = this.hasCardPai(Type.CardPai.Jiu)
					|| this.hasCardPai(Type.CardPai.Tao);
		} else {
			// for other WJ askFor Tao
			rtn = this.hasCardPai(Type.CardPai.Tao);
		}
		return rtn;
	}

	public CardPai chuTao(WuJiang srcWJ) {
		CardPai taoCP = null;
		if (this.equals(srcWJ)) {
			// if the askFor Tao WJ is myself
			taoCP = selectCardPaiFromShouPai(Type.CardPai.Jiu);
			if (taoCP == null) {
				taoCP = selectCardPaiFromShouPai(Type.CardPai.Tao);
			}
		} else {
			// for other WJ askFor Tao
			taoCP = selectCardPaiFromShouPai(Type.CardPai.Tao);
		}

		if (taoCP != null) {
			this.detatchCardPaiFromShouPai(taoCP);
		}

		return taoCP;
	}

	public boolean isFriend(WuJiang wj) {
		return this.friendList.contains(wj) ? true : false;
	}

	public boolean isOpponent(WuJiang wj) {
		return this.opponentList.contains(wj) ? true : false;
	}

	public CardPai selectWuQiFromShouPai() {
		for (int i = 0; i < this.shouPai.size(); i++) {
			CardPai cp = (CardPai) this.shouPai.get(i);

			if (cp.belongToWuJiang == null) {
				cp.belongToWuJiang = this;
			}

			if (cp instanceof WuQiCardPai) {
				return cp;
			}
		}
		return null;
	}

	public CardPai selectFangJuFromShouPai() {
		for (int i = 0; i < this.shouPai.size(); i++) {
			CardPai cp = (CardPai) this.shouPai.get(i);

			if (cp.belongToWuJiang == null) {
				cp.belongToWuJiang = this;
			}

			if (cp instanceof FangJuCardPai) {
				return cp;
			}
		}
		return null;
	}

	public CardPai selectMaCardPaiFromShouPai(int distance) {
		for (int i = 0; i < this.shouPai.size(); i++) {
			CardPai cp = (CardPai) this.shouPai.get(i);

			if (cp.belongToWuJiang == null) {
				cp.belongToWuJiang = this;
			}

			if (cp instanceof MaCardPai) {
				MaCardPai ma = (MaCardPai) cp;
				if (ma.distance == distance) {
					return cp;
				}
			}
		}
		return null;
	}

	public CardPai selectCardPaiFromShouPai(Type.CardPai cpT) {
		for (int i = 0; i < this.shouPai.size(); i++) {
			CardPai cp = (CardPai) this.shouPai.get(i);

			if (cp.belongToWuJiang == null) {
				cp.belongToWuJiang = this;
			}

			if (cp.gameApp == null) {
				cp.gameApp = this.gameApp;
			}

			if (cp.name == cpT) {
				return cp;
			}

			if (cpT == Type.CardPai.Sha && cp instanceof Sha) {
				return cp;
			}
		}
		return null;
	}

	public void dead(CardPai srcCP) {
		this.state = Type.State.Dead;
		this.gameApp.gameLogicData.someWJDeadDuringChuPai = true;

		// remove all card pai, zhuangbei
		Helper.emptyArrayList(this.panDingPai);
		Helper.emptyArrayList(this.shouPai);
		this.zhuangBei.reset();
		this.lianHuan = false;

		// remove from link
		WuJiang next = this.nextOne;
		WuJiang pre = this.preOne;
		pre.nextOne = next;
		next.preOne = pre;

		// update view
		UpdateWJViewData item = new UpdateWJViewData();
		item.updateAll = true;
		this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(this,
				item);

		libGameData.logInfo(this.dispName
				+ "("
				+ this.gameApp.gameLogicData.wjHelper
						.convertRoleToName(this.role) + ")死亡",
				Type.logDelay.Delay);

		if (gameApp.gameLogicData.wjHelper.checkMatchOver())
			return;

		this.checkWhoKillThisWJ(srcCP);

		// re construct friend and oppt list
		this.gameApp.gameLogicData.wjHelper.constructFriOpptList();
	}

	public CardPai chuShan(WuJiang srcWJ, CardPai srcCP) {
		CardPai shanCP = null;

		if (srcCP instanceof Sha && srcWJ.zhuangBei.wuQi != null
				&& srcWJ.zhuangBei.wuQi instanceof QingHongJian) {
			// do nothing when apply qingHongJian
		} else {
			if (this.zhuangBei.fangJu != null
					&& this.zhuangBei.fangJu instanceof BaGuaZhen) {
				shanCP = ((BaGuaZhen) this.zhuangBei.fangJu).chuShan(srcWJ,
						this, srcCP);
				if (shanCP != null)
					return shanCP;
			}
		}

		if (!this.tuoGuan) {
			// mei you baguazhen or fails
			this.gameApp.gameLogicData.askForPai = Type.CardPai.Shan;
			shanCP = this.gameApp.gameLogicData.userInterface
					.askUserChuPai(this, "是否对" + srcWJ.dispName + "的"
							+ srcCP.dispName + "出闪?");
			if (shanCP != null) {
				this.gameApp.libGameViewData.logInfo(this.dispName + "出牌"
						+ shanCP, Type.logDelay.Delay);
			}
		} else {
			// mei you baguazhen or fails
			shanCP = this.selectCardPaiFromShouPai(Type.CardPai.Shan);
			if (shanCP != null) {
				shanCP.belongToWuJiang = null;
				this.detatchCardPaiFromShouPai(shanCP);
				this.gameApp.libGameViewData.logInfo(this.dispName + "出牌"
						+ shanCP, Type.logDelay.Delay);
			}
		}
		return shanCP;
	}

	public CardPai chuSha(WuJiang srcWJ, CardPai srcCP) {
		// should be used by juedou, nanmanruqin, jiedaosharen
		CardPai shaCP = null;
		if (!this.tuoGuan) {
			this.gameApp.gameLogicData.askForPai = Type.CardPai.Sha;
			shaCP = this.gameApp.gameLogicData.userInterface
					.askUserChuPai(this, "是否对" + srcWJ.dispName + "的"
							+ srcCP.dispName + "出杀?");
		} else {
			shaCP = this.selectCardPaiFromShouPai(Type.CardPai.Sha);

			if (shaCP != null) {
				shaCP.belongToWuJiang = null;
				this.detatchCardPaiFromShouPai(shaCP);
			}

			if (shaCP == null) {
				// no sha, check if wuqi is zhangbashemao
				if (this.zhuangBei.wuQi != null
						&& this.zhuangBei.wuQi instanceof ZhangBaSheMao
						&& this.shouPai.size() >= 2) {
					shaCP = new ZBSMSha(this.shouPai.get(0), this.shouPai
							.get(1));
				}
			}
		}

		if (shaCP != null && shaCP instanceof ZBSMSha) {
			((ZBSMSha) shaCP).discardTwoShouPai();
		}

		if (shaCP != null) {
			this.gameApp.libGameViewData.logInfo(this.dispName + "出牌" + shaCP,
					Type.logDelay.Delay);

			if (this instanceof LvMeng) {
				((LvMeng) this).useShaNumber++;
			}
		}

		return shaCP;
	}

	public void detatchCardPaiFromShouPai(CardPai cp) {

		cp.cpState = Type.CPState.FeiPaiDui;
		this.shouPai.remove(cp);

		if (this.tuoGuan) {
			// update shou pai number
			UpdateWJViewData item = new UpdateWJViewData();
			item.updateShouPaiNumber = true;
			this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(
					this, item);
		} else {
			this.gameApp.gameLogicData.wjHelper.updateWJ8ShouPaiToLibGameView();
		}
	}

	public CardPai discardOneCPForHuoGong(CardPai showCP) {
		CardPai disCP = null;
		if (this.tuoGuan) {
			for (int i = 0; i < this.shouPai.size(); i++) {
				CardPai cp = (CardPai) this.shouPai.get(i);
				if (cp.clas == showCP.clas) {
					disCP = cp;
					this.detatchCardPaiFromShouPai(cp);
					cp.belongToWuJiang = null;
					break;
				}
			}
		} else {
			this.gameApp.gameLogicData.askForHuaShi = showCP.clas;
			this.gameApp.gameLogicData.askForPai = Type.CardPai.nil;

			disCP = this.gameApp.gameLogicData.userInterface.askUserChuPai(
					this, "请出一张" + showCP.formatHuaShi() + "卡牌,火攻生效");

			this.gameApp.gameLogicData.askForHuaShi = Type.CardPaiClass.nil;
			this.gameApp.gameLogicData.askForPai = Type.CardPai.nil;
		}
		return disCP;
	}

	public int askForTao(CardPai srcCP, int taoN) {
		TaoEvent taoEvent = new TaoEvent(this, this, taoN);

		WuJiang startFromWJ = null;
		WuJiang endWJ = null;

		if (srcCP instanceof ShanDian) {
			startFromWJ = this;
			endWJ = this;
		} else {
			if (srcCP.belongToWuJiang != null
					&& srcCP.belongToWuJiang.state != Type.State.Dead) {
				startFromWJ = srcCP.belongToWuJiang;
				endWJ = srcCP.belongToWuJiang;
			}
		}

		// for safety check null point
		if (startFromWJ == null) {
			startFromWJ = this;
			endWJ = this;
		}

		libGameData.logInfo(this.dispName + "向" + startFromWJ.dispName + "求"
				+ taoN + "个桃", Type.logDelay.Delay);
		startFromWJ.askForTaoEvent(taoEvent);
		startFromWJ = startFromWJ.nextOne;

		int rtn = taoEvent.yiChuTaoNumber + this.blood;

		while (!startFromWJ.equals(endWJ) && rtn <= 0) {
			libGameData.logInfo(this.dispName + "向" + startFromWJ.dispName
					+ "求" + (taoN - taoEvent.yiChuTaoNumber) + "个桃",
					Type.logDelay.Delay);
			startFromWJ.askForTaoEvent(taoEvent);
			startFromWJ = startFromWJ.nextOne;

			rtn = taoEvent.yiChuTaoNumber + this.blood;
		}
		// wait sometime for wj to process the event
		// Wait till end
		return taoEvent.yiChuTaoNumber;
	}

	public boolean askForWuXieKeJi(WuJiang srcWJ, CardPai srcCP, WuJiang tarWJ,
			CardPai tarCP) {
		WuXieKeJiEvent cpEvent = new WuXieKeJiEvent(this, srcWJ, srcCP, tarWJ,
				tarCP);

		WuJiang startWJ = null;

		if (srcWJ.state != Type.State.Dead) {
			startWJ = srcWJ;
		} else {
			startWJ = this.gameApp.gameLogicData.wjHelper
					.findNextOne(srcWJ.imageViewIndex);
		}

		if (startWJ == null)
			return false;

		LoopWuJiangHelper localLoopWuJiangHelper = new LoopWuJiangHelper(
				this.gameApp.gameLogicData.wuJiangs, startWJ);

		WuJiang curWJ = localLoopWuJiangHelper.nextLoopWJ();
		while (curWJ != null) {
			curWJ.askForWuXieKeJiEvent(cpEvent);
			curWJ = localLoopWuJiangHelper.nextLoopWJ();
		}

		// wait sometime for wj to process the event
		// Wait till end
		return (cpEvent.yiChuWuXieKeJiNumber % 2 == 1) ? true : false;
	}

	public void notifyChuPaiEvent(WuJiang srcWJ, CardPai srcCP,
			ChuPaiEvent cpEvent) {
		for (int i = 0; i < this.gameApp.gameLogicData.wuJiangs.size(); i++) {
			WuJiang wj = (WuJiang) this.gameApp.gameLogicData.wuJiangs.get(i);
			cpEvent.srcWuJiang = srcWJ;
			cpEvent.srcCardPai = srcCP;
			if (srcCP.name == Type.CardPai.WuXieKeJi) {
				wj.askForWuXieKeJiEvent(cpEvent);
			}
		}
	}

	public void updateZhuangBeiView() {
		UpdateWJViewData item = new UpdateWJViewData();
		item.updateZhuangBei = true;
		this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(this,
				item);
	}

	public int getFangShouDistance() {
		if (this.zhuangBei.jiaYiMa != null)
			return 1;
		return 0;
	}

	public int getJinGongDistance() {
		if (this.zhuangBei.jianYiMa != null)
			return 1;
		return 0;
	}

	public CardPai showOneCPForHuoGong() {
		CardPai cp = null;
		if (this.tuoGuan) {
			if (this.shouPai.size() > 0)
				cp = this.shouPai.get(0);
		} else {
			cp = this.gameApp.gameLogicData.userInterface
					.askUserShowOneCardPai(this, "请出示一张卡牌");
		}
		return cp;
	}

	public boolean canIChuSha() {

		if (this.zhuangBei.wuQi != null
				&& this.zhuangBei.wuQi instanceof ZhuGeLianNu) {
			return true;
		}

		if (this.huiHeChuShaN == 0)
			return true;

		return false;
	}

	public void checkWhoKillThisWJ(CardPai srcCP) {
		WuJiang killerWJ = srcCP.shangHaiSrcWJ;

		if (killerWJ == null) {
			return;
		}

		if (killerWJ != null && killerWJ.state != Type.State.Dead) {

			UpdateWJViewData item = new UpdateWJViewData();
			item.updateShouPaiNumber = true;
			item.updateZhuangBei = true;

			if (this.role == Type.Role.FanZei) {
				// If this wj is fanzei, then who kill this wj will have
				// 3 shou pai
				this.gameApp.gameLogicData.cpHelper.addCardPaiToWuJiang(
						killerWJ, 3);

				if (killerWJ.tuoGuan) {
					this.gameApp.gameLogicData.wjHelper
							.updateWuJiangToLibGameView(killerWJ, item);
				} else {
					this.gameApp.gameLogicData.wjHelper
							.updateWJ8ShouPaiToLibGameView();
				}
				libGameData.logInfo(killerWJ.dispName + "杀死反贼,摸3张牌",
						Type.logDelay.Delay);

			} else if (killerWJ.role == Type.Role.ZhuGong
					&& this.role == Type.Role.ZhongChen) {
				// zhugong kill zhonchen, discard all shoupai and zhuangbei
				// remove all card pai, zhuangbei

				libGameData.logInfo(killerWJ.dispName + "主公杀死忠臣,\n弃掉手牌和装备",
						Type.logDelay.Delay);

				Helper.emptyArrayList(killerWJ.shouPai);
				killerWJ.zhuangBei.reset();

				if (killerWJ.tuoGuan) {
					this.gameApp.gameLogicData.wjHelper
							.updateWuJiangToLibGameView(killerWJ, item);
				} else {
					this.gameApp.gameLogicData.wjHelper
							.updateWJ8ShouPaiToLibGameView();
				}

				killerWJ.updateZhuangBeiView();
			}
		}
	}

	// tarWJ must be CaoCao
	public CardPai huJia(WuJiang srcWJ, CardPai srcCP) {
		// CaoCao ask huJia or not
		CardPai shanCP = null;
		boolean hj = false;
		if (this.tuoGuan) {
			if (this.isFriend(srcWJ)) {
				shanCP = this.chuShan(srcWJ, srcCP);
				hj = true;
			}
		} else {
			// ask UI whether huJia
			this.gameApp.ynData.reset();
			this.gameApp.ynData.okTxt = "确认";
			this.gameApp.ynData.cancelTxt = "取消";
			this.gameApp.ynData.genInfo = "是否响应" + srcWJ.dispName + "的"
					+ srcCP.dispName + "?";

			YesNoDialog dlg = new YesNoDialog(this.gameApp.gameActivityContext,
					this.gameApp);
			dlg.showDialog();
			if (this.gameApp.ynData.result) {
				shanCP = this.chuShan(srcWJ, srcCP);
				hj = true;
			}
		}

		if (!hj) {
			this.gameApp.libGameViewData.logInfo(this.dispName + "放弃响应"
					+ srcCP.dispName, Type.logDelay.Delay);
		}
		return shanCP;
	}

	// tarWJ must be LiuBei
	public CardPai jiJiang(WuJiang srcWJ, CardPai srcCP) {
		// LiuBei ask jiJiang or not
		CardPai shaCP = null;
		boolean jj = false;
		if (this.tuoGuan) {
			if (this.isFriend(srcWJ)) {
				shaCP = this.chuSha(srcWJ, srcCP);
				if (shaCP != null) {
					jj = true;
				}
			}
		} else {
			// ask UI whether jiJiang
			this.gameApp.ynData.reset();
			this.gameApp.ynData.okTxt = "确认";
			this.gameApp.ynData.cancelTxt = "取消";
			this.gameApp.ynData.genInfo = "是否响应" + srcWJ.dispName + "的"
					+ srcCP.dispName + "?";

			YesNoDialog dlg = new YesNoDialog(this.gameApp.gameActivityContext,
					this.gameApp);
			dlg.showDialog();
			if (this.gameApp.ynData.result) {
				shaCP = this.chuSha(srcWJ, srcCP);
				if (shaCP != null) {
					jj = true;
				}
			}
		}

		if (!jj) {
			this.gameApp.libGameViewData.logInfo(this.dispName + "放弃响应"
					+ srcCP.dispName, Type.logDelay.Delay);
		}
		return shaCP;
	}

	public void resetAllWuJiangForSelect() {
		WuJiang tarWJ = this.nextOne;
		while (!tarWJ.equals(this)) {
			tarWJ.canSelect = false;
			tarWJ.clicked = false;
			tarWJ = tarWJ.nextOne;
		}
	}

	public CardPai selectFromShouPaiByClass(Type.CardPaiClass cps) {
		for (int i = 0; i < this.shouPai.size(); i++) {
			CardPai sp = this.shouPai.get(i);
			if (sp.clas == cps)
				return sp;
		}
		return null;
	}

	public CardPai selectFromShouPaiByName(Type.CardPai name) {
		for (int i = 0; i < this.shouPai.size(); i++) {
			CardPai sp = this.shouPai.get(i);
			if (sp.name == name)
				return sp;
		}
		return null;
	}

	public CardPai hasBlackCardPaiInShouPai() {
		CardPai blackCP = this
				.selectFromShouPaiByClass(Type.CardPaiClass.MeiHua);
		if (blackCP == null)
			blackCP = this.selectFromShouPaiByClass(Type.CardPaiClass.HeiTao);
		return blackCP;
	}

	public CardPai hasBlackCardPai() {
		CardPai blackCP = this.hasBlackCardPaiInShouPai();

		if (blackCP == null && this.zhuangBei.jianYiMa != null) {
			if (this.zhuangBei.jianYiMa.clas == Type.CardPaiClass.MeiHua
					|| this.zhuangBei.jianYiMa.clas == Type.CardPaiClass.HeiTao) {
				blackCP = this.zhuangBei.jianYiMa;
			}
		}
		if (blackCP == null && this.zhuangBei.jiaYiMa != null) {
			if (this.zhuangBei.jiaYiMa.clas == Type.CardPaiClass.MeiHua
					|| this.zhuangBei.jiaYiMa.clas == Type.CardPaiClass.HeiTao) {
				blackCP = this.zhuangBei.jiaYiMa;
			}
		}
		if (blackCP == null && this.zhuangBei.wuQi != null) {
			if (this.zhuangBei.wuQi.clas == Type.CardPaiClass.MeiHua
					|| this.zhuangBei.wuQi.clas == Type.CardPaiClass.HeiTao) {
				blackCP = this.zhuangBei.wuQi;
			}
		}
		if (blackCP == null && this.zhuangBei.fangJu != null) {
			if (this.zhuangBei.fangJu.clas == Type.CardPaiClass.MeiHua
					|| this.zhuangBei.fangJu.clas == Type.CardPaiClass.HeiTao) {
				blackCP = this.zhuangBei.fangJu;
			}
		}

		return blackCP;
	}

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
		if (cp == null && this.zhuangBei.fangJu != null) {
			if (this.zhuangBei.fangJu.clas == cpT) {
				cp = this.zhuangBei.fangJu;
			}
		}

		return cp;
	}

	public CardPai hasRedCardPaiInShouPai() {
		CardPai redCP = this
				.selectFromShouPaiByClass(Type.CardPaiClass.FangPian);
		if (redCP == null) {
			redCP = this.selectFromShouPaiByClass(Type.CardPaiClass.HongTao);
		}
		return redCP;
	}

	public CardPai hasRedCardPai() {
		CardPai redCP = this.hasRedCardPaiInShouPai();

		if (redCP == null && this.zhuangBei.jianYiMa != null) {
			if (this.zhuangBei.jianYiMa.clas == Type.CardPaiClass.FangPian
					|| this.zhuangBei.jianYiMa.clas == Type.CardPaiClass.HongTao) {
				redCP = this.zhuangBei.jianYiMa;
			}
		}
		if (redCP == null && this.zhuangBei.jiaYiMa != null) {
			if (this.zhuangBei.jiaYiMa.clas == Type.CardPaiClass.FangPian
					|| this.zhuangBei.jiaYiMa.clas == Type.CardPaiClass.HongTao) {
				redCP = this.zhuangBei.jiaYiMa;

			}
		}
		if (redCP == null && this.zhuangBei.wuQi != null) {
			if (this.zhuangBei.wuQi.clas == Type.CardPaiClass.FangPian
					|| this.zhuangBei.wuQi.clas == Type.CardPaiClass.HongTao) {
				redCP = this.zhuangBei.wuQi;
			}
		}
		if (redCP == null && this.zhuangBei.fangJu != null) {
			if (this.zhuangBei.fangJu.clas == Type.CardPaiClass.FangPian
					|| this.zhuangBei.fangJu.clas == Type.CardPaiClass.HongTao) {
				redCP = this.zhuangBei.fangJu;
			}
		}

		return redCP;
	}

	public boolean hasLBSSInPanDindArea() {
		for (int i = 0; i < this.panDingPai.size(); i++) {
			CardPai cp = (CardPai) this.panDingPai.get(i);
			if (cp instanceof LeBuShiShu) {
				return true;
			}
		}
		return false;
	}

	public boolean hasSDInPanDindArea() {
		for (int i = 0; i < this.panDingPai.size(); i++) {
			CardPai cp = (CardPai) this.panDingPai.get(i);
			if (cp instanceof ShanDian) {
				return true;
			}
		}
		return false;
	}

	public boolean hasBLCDInPanDindArea() {
		for (int i = 0; i < this.panDingPai.size(); i++) {
			CardPai cp = (CardPai) this.panDingPai.get(i);
			if (cp instanceof BingLiangCunDuan) {
				return true;
			}
		}
		return false;
	}

	public void unstallZhuangBei(ZhuangBeiCardPai zbCP) {
		if (zbCP instanceof FangJuCardPai)
			this.uninstallFangJu();
		else if (zbCP instanceof WuQiCardPai)
			this.uninstallWuQi();
		else if (zbCP instanceof MaCardPai) {
			MaCardPai ma = (MaCardPai) zbCP;
			if (ma.distance == +1)
				this.uninstallJiaYiMa();
			else if (ma.distance == -1)
				this.uninstallJianYiMa();
		}
	}

	public void installFangJu(FangJuCardPai fangJu) {
		this.zhuangBei.fangJu = fangJu;
		this.zhuangBei.fangJu.belongToWuJiang = this;
		this.zhuangBei.fangJu.cpState = Type.CPState.fangJuPai;
		this.updateZhuangBeiView();
		this.gameApp.libGameViewData.logInfo(this.dispName + "安装了" + fangJu,
				Type.logDelay.Delay);
	}

	private void uninstallFangJu() {
		CardPai fjCP = this.zhuangBei.fangJu;
		this.zhuangBei.fangJu.cpState = Type.CPState.FeiPaiDui;
		this.zhuangBei.fangJu = null;
		this.updateZhuangBeiView();
		this.gameApp.libGameViewData.logInfo(this.dispName + "丢弃了" + fjCP,
				Type.logDelay.Delay);

		if (this.blood < this.getMaxBlood() && fjCP instanceof BaiYinShiZi) {
			fjCP.shangHaiN = 1;
			this.gameApp.libGameViewData.logInfo(this.dispName + "失去白银狮子",
					Type.logDelay.Delay);
			this.increaseBlood(this, fjCP);
		}
	}

	public void installWuQi(WuQiCardPai wuQi) {
		this.zhuangBei.wuQi = wuQi;
		this.zhuangBei.wuQi.belongToWuJiang = this;
		this.zhuangBei.wuQi.cpState = Type.CPState.wuQiPai;
		this.updateZhuangBeiView();
		this.gameApp.libGameViewData.logInfo(this.dispName + "安装了" + wuQi,
				Type.logDelay.Delay);
	}

	private void uninstallWuQi() {
		String cpInfo = this.zhuangBei.wuQi.toString();
		this.zhuangBei.wuQi.cpState = Type.CPState.FeiPaiDui;
		this.zhuangBei.wuQi = null;
		this.updateZhuangBeiView();
		this.gameApp.libGameViewData.logInfo(this.dispName + "丢弃了" + cpInfo,
				Type.logDelay.Delay);
	}

	public void installJiaYiMa(MaCardPai ma) {
		this.zhuangBei.jiaYiMa = ma;
		this.zhuangBei.jiaYiMa.belongToWuJiang = this;
		this.zhuangBei.jiaYiMa.cpState = Type.CPState.jiaYiMaPai;
		this.updateZhuangBeiView();
		this.gameApp.libGameViewData.logInfo(this.dispName + "安装了" + ma,
				Type.logDelay.Delay);
	}

	private void uninstallJiaYiMa() {
		String cpInfo = this.zhuangBei.jiaYiMa.toString();
		this.zhuangBei.jiaYiMa.cpState = Type.CPState.FeiPaiDui;
		this.zhuangBei.jiaYiMa = null;
		this.updateZhuangBeiView();
		this.gameApp.libGameViewData.logInfo(this.dispName + "丢弃了" + cpInfo,
				Type.logDelay.Delay);
	}

	public void installJianYiMa(MaCardPai ma) {
		this.zhuangBei.jianYiMa = ma;
		this.zhuangBei.jianYiMa.belongToWuJiang = this;
		this.zhuangBei.jianYiMa.cpState = Type.CPState.jianYiMaPai;
		this.updateZhuangBeiView();
		this.gameApp.libGameViewData.logInfo(this.dispName + "安装了" + ma,
				Type.logDelay.Delay);
	}

	private void uninstallJianYiMa() {
		String cpInfo = this.zhuangBei.jianYiMa.toString();
		this.zhuangBei.jianYiMa.cpState = Type.CPState.FeiPaiDui;
		this.zhuangBei.jianYiMa = null;
		this.updateZhuangBeiView();
		this.gameApp.libGameViewData.logInfo(this.dispName + "丢弃了" + cpInfo,
				Type.logDelay.Delay);
	}

	// wj ji neng is triggered here
	// caocao: jianXiong
	public void listenIncreaseBloodEvent(CardPai srcCP) {
	}

	// WeiYuan
	public void listenIncreaseBloodEvent(WuJiang tarWJ, CardPai srcCP) {
	}

	// for AI, WuJiang JiNeng CardPai, such as LiJian
	public CardPai generateJiNengCardPai() {
		return null;
	}

	// once enter run and before panDing, then run this
	// for zhenji's luoshen
	// another function is to enable jiNeng button
	public void listenEnterHuiHeEvent() {
		this.pdResult.reset();
		this.huiHeChuShaN = 0;
		this.heJiu = false;
		this.specialChuPaiReason = "";
		this.oneTimeJiNengTrigger = false;
		this.huangTianJiNengTrigger = false;

		// set all JiNeng Button to INVISIBLE
		if (!this.tuoGuan) {
			this.gameApp.libGameViewData.mJiNengBtn1
					.setVisibility(View.INVISIBLE);
			this.gameApp.libGameViewData.mJiNengBtn2
					.setVisibility(View.INVISIBLE);
			this.gameApp.libGameViewData.mJiNengBtn3
					.setVisibility(View.INVISIBLE);
			this.gameApp.libGameViewData.mJiNengBtn4
					.setVisibility(View.INVISIBLE);
		}
	}

	public void listenExitHuiHeEvent() {
		this.heJiu = false;
		this.specialChuPaiReason = "";
		this.oneTimeJiNengTrigger = false;
	}

	public void enableWuJiangJiNengBtn() {
		if (!this.tuoGuan) {
			int imgID = Helper.convertToJiNengBackGroundImg(this.country);

			// set correct JiNeng image
			this.gameApp.libGameViewData.mJiNengBtn1.setImageDrawable(imgID);
			this.gameApp.libGameViewData.mJiNengBtn2.setImageDrawable(imgID);
			this.gameApp.libGameViewData.mJiNengBtn3.setImageDrawable(imgID);
			this.gameApp.libGameViewData.mJiNengBtn4.setImageDrawable(imgID);
		}
	}

	// if kick JiNeng btn, then handle it
	public void handleJiNengBtnEvent(int eventID) {

	}

	// HuangZhong & MaChao: LieGong & TieJi
	public boolean checkShaMingZhong(WuJiang tarWJ) {
		return false;
	}

	// PengDe
	public void listenShanEvent(WuJiang srcWJ, WuJiang tarWJ, CardPai srcCP) {

	}

	// chang panDing CardPai: For ZhangJiao and ShiMaYi
	public CardPai listenPanDingCardPaiEvent(WuJiang tarWJ, CardPai tarCP,
			CardPai curPDCP) {
		return null;
	}

	// For SunShangXiang to overwrite
	public boolean needInstallNewZB(CardPai zbCP) {
		if (zbCP instanceof FangJuCardPai) {
			if (this.zhuangBei.fangJu == null) {
				return true;
			} else {
				if (this.zhuangBei.fangJu instanceof BaiYinShiZi
						&& this.blood < this.getMaxBlood()) {
					return true;
				}
			}
		} else if (zbCP instanceof WuQiCardPai) {
			if (this.zhuangBei.wuQi == null) {
				return true;
			} else {
				// I can still use my current WuQi Sha Oppt
				CardPaiActionForTarWuJiang useCPShaWJ = this
						.whichWJICanShaWithWuQi(this.zhuangBei.wuQi,
								this.opponentList);
				if (useCPShaWJ.tarWJ != null)
					return false;

				// I can not use my current WuQi Sha Oppt, but the new one can
				useCPShaWJ.reset();
				useCPShaWJ = this.whichWJICanShaWithWuQi((WuQiCardPai) zbCP,
						this.opponentList);
				if (useCPShaWJ.tarWJ != null)
					return true;
			}
		} else if (zbCP instanceof MaCardPai) {
			MaCardPai ma = (MaCardPai) zbCP;
			if (ma.distance == +1) {
				if (this.zhuangBei.jiaYiMa == null) {
					return true;
				}
			} else if (ma.distance == -1) {
				if (this.zhuangBei.jianYiMa == null) {
					return true;
				}
			}
		}

		// by default, do not need this one
		return false;
	}

	public CardPaiActionForTarWuJiang whichWJICanShaWithWuQi(WuQiCardPai wuQi,
			ArrayList<WuJiang> tarWJList) {
		CardPaiActionForTarWuJiang useCPShaWJ = new CardPaiActionForTarWuJiang();
		WuJiang tarWJ = this.nextOne;
		while (!tarWJ.equals(this)) {
			if (tarWJList.contains(tarWJ)) {

				int distance = this.countDistanceWithWuQi(this, tarWJ, wuQi);

				// if TaiShiChi tianYi is success, then distance is long!
				if (this instanceof TaiShiChi) {
					TaiShiChi tscWJ = (TaiShiChi) this;
					if (tscWJ.tianYiSuccess) {
						distance = 0;
					}
				}

				// if ZhuGeLiang kongCheng is success
				if (tarWJ instanceof ZhuGeLiang) {
					if (tarWJ.shouPai.size() == 0) {
						distance = 256;
					}
				}

				if (distance <= 1) {
					// also check tarWJ fangJu
					if (tarWJ.zhuangBei.fangJu != null) {
						if (tarWJ.zhuangBei.fangJu instanceof TengJia) {
							CardPai shaCP = this
									.selectCardPaiFromShouPai(Type.CardPai.HuoSha);
							if (shaCP == null)
								shaCP = this
										.selectCardPaiFromShouPai(Type.CardPai.LeiSha);
							if (shaCP == null) {
								if (wuQi != null
										&& wuQi instanceof ZhuQueYuShan) {
									shaCP = this
											.selectCardPaiFromShouPai(Type.CardPai.Sha);
								}
							}

							// I can sha tarWJ with this WuQi
							if (shaCP != null) {
								useCPShaWJ.tarWJ = tarWJ;
								useCPShaWJ.srcCP = shaCP;
								break;
							}
						}// tarWJ has TengJia
						else if (tarWJ.zhuangBei.fangJu instanceof RenWangDun) {
							CardPai shaCP = this
									.selectCardPaiFromShouPai(Type.CardPai.Sha);
							if (shaCP != null
									&& (shaCP.clas == Type.CardPaiClass.FangPian || shaCP.clas == Type.CardPaiClass.HongTao)) {
								useCPShaWJ.tarWJ = tarWJ;
								useCPShaWJ.srcCP = shaCP;
								break;
							}
						}// tarWJ has RenWangDun
						else {
							// For other fangJu (BaGuaZhen && BaiYinShiZi)
							useCPShaWJ.tarWJ = tarWJ;
							useCPShaWJ.srcCP = null;
							break;
						}
					} else {
						// tarWJ has not fangJu
						useCPShaWJ.tarWJ = tarWJ;
						useCPShaWJ.srcCP = null;
						break;
					}
				}// distance <= 1
			}// this.isOpponent(tarWJ)

			tarWJ = tarWJ.nextOne;
		}// while

		//
		if (useCPShaWJ.tarWJ != null) {
			// if shaCP is null, then random select one sha
			if (useCPShaWJ.srcCP == null) {
				useCPShaWJ.srcCP = this
						.selectCardPaiFromShouPai(Type.CardPai.Sha);
				if (useCPShaWJ.srcCP == null) {
					// no sha, check if wuqi is zhangbashemao
					if (this.zhuangBei.wuQi != null
							&& this.zhuangBei.wuQi instanceof ZhangBaSheMao
							&& this.shouPai.size() >= 2) {
						useCPShaWJ.srcCP = new ZBSMSha(this.shouPai.get(0),
								this.shouPai.get(1));
					}
				}
			}
		}

		return useCPShaWJ;
	}

	// this will be overwrite by TaiShiChi
	public int countDistanceWithWuQi(WuJiang fromWJ, WuJiang toWJ,
			WuQiCardPai wuQi) {

		int disN = 1;// count from nextone
		int disP = 1;// count from preone

		if (wuQi != null) {
			disN -= (wuQi.distance - 1);
			disP -= (wuQi.distance - 1);
		}

		WuJiang start = fromWJ;
		while (!start.nextOne.equals(toWJ)) {
			disN++;
			start = start.nextOne;
		}

		start = fromWJ;
		while (!start.preOne.equals(toWJ)) {
			disP++;
			start = start.preOne;
		}

		disN = disN - fromWJ.getJinGongDistance() + toWJ.getFangShouDistance();
		disP = disP - fromWJ.getJinGongDistance() + toWJ.getFangShouDistance();

		return (disN < disP) ? disN : disP;
	}

	public boolean hasWuXieKeJi() {
		for (int i = 0; i < this.shouPai.size(); i++) {
			CardPai cp = (CardPai) this.shouPai.get(i);
			if (cp.name == Type.CardPai.WuXieKeJi)
				return true;
		}
		return false;
	}

	public CardPai chuWuXieKeJi(WuJiang tarWJ) {
		for (int i = 0; i < this.shouPai.size(); i++) {
			CardPai cp = (CardPai) this.shouPai.get(i);
			if (cp.name == Type.CardPai.WuXieKeJi)
				return cp;
		}
		return null;
	}

	// For AI, ZhangJiao, JiNeng3
	public CardPai generateHuangTianJiNengCardPai() {

		if (this.huangTianJiNengTrigger)
			return null;

		if (!(this.gameApp.gameLogicData.zhuGongWuJiang instanceof ZhangJiao)) {
			return null;
		}

		if (this.country != Type.Country.Qun) {
			return null;
		}

		if (this.equals(this.gameApp.gameLogicData.zhuGongWuJiang)) {
			return null;
		}

		if (this.gameApp.gameLogicData.zhuGongWuJiang.state == Type.State.Dead)
			return null;

		if (this.isFriend(this.gameApp.gameLogicData.zhuGongWuJiang)) {
			if (!this.gameApp.gameLogicData.zhuGongWuJiang
					.hasLBSSInPanDindArea()
					&& (this.shouPai.size() > this.blood)) {
				// use directly search to avoid return JiNeng ShanCP
				CardPai shanCP = null;
				for (int i = 0; i < this.shouPai.size(); i++) {
					CardPai cp = (CardPai) this.shouPai.get(i);
					if (cp.name == Type.CardPai.Shan) {
						shanCP = cp;
						break;
					}
				}

				if (shanCP != null) {
					HuangTian ht = new HuangTian(Type.CardPai.nil,
							Type.CardPaiClass.nil, 0);
					ht.gameApp = this.gameApp;
					ht.belongToWuJiang = this;
					ht.shanCP = shanCP;

					return ht;
				}
			}
		}

		return null;
	}

	public void handleHuangTianJiNengBtnEvent() {
		if (this.state != Type.State.ChuPai)
			return;

		if (this.gameApp.gameLogicData.zhuGongWuJiang.state == Type.State.Dead)
			return;

		if (this.huangTianJiNengTrigger) {
			this.gameApp.libGameViewData
					.logInfo(
							this.gameApp.gameLogicData.zhuGongWuJiang
									+ this.gameApp.gameLogicData.zhuGongWuJiang.jiNengN3
									+ "只能发动一次", Type.logDelay.NoDelay);
			return;
		}

		this.gameApp.ynData.reset();
		this.gameApp.ynData.okTxt = "确认";
		this.gameApp.ynData.cancelTxt = "取消";
		this.gameApp.ynData.genInfo = "是否触发"
				+ this.gameApp.gameLogicData.zhuGongWuJiang + "的"
				+ this.gameApp.gameLogicData.zhuGongWuJiang.jiNengN3 + "?";

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
		for (int i = 0; i < this.shouPai.size(); i++) {
			CardPai cp = (CardPai) this.shouPai.get(i);
			if (cp.name == Type.CardPai.Shan
					|| cp.name == Type.CardPai.ShanDian) {
				wjCPData.shouPai.add(cp);
			}
		}

		if (wjCPData.shouPai.size() == 0) {
			this.gameApp.libGameViewData.logInfo("你没有闪和闪电",
					Type.logDelay.NoDelay);
			return;
		}

		SelectCardPaiFromWJDialog dlg2 = new SelectCardPaiFromWJDialog(
				this.gameApp.gameActivityContext, this.gameApp, wjCPData);
		dlg2.showDialog();

		CardPai shanCP = this.gameApp.wjDetailsViewData.selectedCardPai1;

		if (shanCP == null)
			return;

		HuangTian ht = new HuangTian(Type.CardPai.nil, Type.CardPaiClass.nil, 0);
		ht.gameApp = this.gameApp;
		ht.belongToWuJiang = this;
		ht.shanCP = shanCP;

		ht.selectedByClick = true;

		// reset myWuJiang shoupai to unselect card pai
		this.resetShouPaiSelectedBoolean();
		// then add ht into shoupai list
		this.shouPai.add(ht);

		if (gameApp.gameLogicData.askForPai == Type.CardPai.notNil) {
			this.gameApp.gameLogicData.userInterface.loop = true;
			this.gameApp.gameLogicData.userInterface.sendMessageToUIForWakeUp();
		}
	}

	public Type.CardPaiClass selectHuaShiForFanJian() {
		if (this.tuoGuan) {
			int ran = Helper.getRandom(100);
			if (ran % 5 == 0) {
				return Type.CardPaiClass.FangPian;
			} else if (ran % 5 == 1) {
				return Type.CardPaiClass.HongTao;
			} else if (ran % 5 == 2) {
				return Type.CardPaiClass.HeiTao;
			} else if (ran % 5 == 3) {
				return Type.CardPaiClass.MeiHua;
			} else if (ran % 5 == 4) {
				return Type.CardPaiClass.FangPian;
			}
		} else {
			SelectHuaShiDialog dlg2 = new SelectHuaShiDialog(
					this.gameApp.gameActivityContext, this.gameApp);
			dlg2.showDialog();

			return dlg2.selectHS;
		}
		return Type.CardPaiClass.FangPian;
	}

	public CardPai selectCardPaiForFanJian(WuJiang tarWJ) {
		if (this.tuoGuan) {
			return tarWJ.shouPai.get(0);
		} else {
			// first select CP
			this.gameApp.wjDetailsViewData.reset();
			this.gameApp.wjDetailsViewData.selectedCardNumber = 1;
			this.gameApp.wjDetailsViewData.canViewShouPai = false;
			this.gameApp.wjDetailsViewData.selectedWJ = tarWJ;

			WuJiangCardPaiData wjCPData = new WuJiangCardPaiData();
			wjCPData.shouPai = tarWJ.shouPai;

			SelectCardPaiFromWJDialog dlg2 = new SelectCardPaiFromWJDialog(
					this.gameApp.gameActivityContext, this.gameApp, wjCPData);

			dlg2.showDialog();

			if (this.gameApp.wjDetailsViewData.selectedCardPai1 != null)
				return this.gameApp.wjDetailsViewData.selectedCardPai1;
		}

		return tarWJ.shouPai.get(0);
	}

	// check and correct shou pai, zhuangBei pai, panDing pai
	public AuditResult audit() {
		AuditResult ar = new AuditResult();
		// check shou pai
		for (int i = 0; i < this.shouPai.size(); i++) {
			CardPai sp = this.shouPai.get(i);
			sp.belongToWuJiang = this;
			sp.gameApp = this.gameApp;
			sp.cpState = Type.CPState.ShouPai;
		}

		// check zhuang bei
		if (this.zhuangBei.fangJu != null) {
			this.zhuangBei.fangJu.cpState = Type.CPState.fangJuPai;
			this.zhuangBei.fangJu.belongToWuJiang = this;
			this.zhuangBei.fangJu.gameApp = this.gameApp;
		}
		if (this.zhuangBei.wuQi != null) {
			this.zhuangBei.wuQi.cpState = Type.CPState.wuQiPai;
			this.zhuangBei.wuQi.belongToWuJiang = this;
			this.zhuangBei.wuQi.gameApp = this.gameApp;
		}
		if (this.zhuangBei.jianYiMa != null) {
			this.zhuangBei.jianYiMa.cpState = Type.CPState.jianYiMaPai;
			this.zhuangBei.jianYiMa.belongToWuJiang = this;
			this.zhuangBei.jianYiMa.gameApp = this.gameApp;
		}
		if (this.zhuangBei.jiaYiMa != null) {
			this.zhuangBei.jiaYiMa.cpState = Type.CPState.jiaYiMaPai;
			this.zhuangBei.jiaYiMa.belongToWuJiang = this;
			this.zhuangBei.jiaYiMa.gameApp = this.gameApp;
		}

		// panDing pai
		for (int i = 0; i < this.panDingPai.size(); i++) {
			CardPai cp = this.panDingPai.get(i);
			cp.cpState = Type.CPState.pandDingPai;
			cp.belongToWuJiang = this;
			cp.gameApp = this.gameApp;
		}

		return ar;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
