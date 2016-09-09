package alben.sgs.wujiang.instance;

import alben.sgs.android.R;
import alben.sgs.android.dialog.SelectCardPaiFromWJDialog;
import alben.sgs.android.dialog.YesNoDialog;
import alben.sgs.cardpai.CardPai;
import alben.sgs.type.Type;
import alben.sgs.type.WuJiangCardPaiData;
import alben.sgs.wujiang.WuJiang;
import alben.sgs.wujiang.instance.jineng.JiJiang;
import alben.sgs.wujiang.instance.jineng.RenDe;
import android.view.View;

public class LiuBei extends WuJiang {
	public int totalSendSP = 0;

	public LiuBei(Type.WuJiang n, Type.Country c, Type.Sex s, int b) {
		super(n, c, s, b);
		this.imageNumber = alben.sgs.android.R.drawable.wj_liubei;
		this.jiNengDesc = "1���ʵ£����ƽ׶Σ�����Խ�����������������������䷽ʽ����������ɫ���������������������������ʱ����ظ�1��������\n"
				+ "2����������������������Ҫʹ�ã�������һ�š�ɱ��ʱ������Է���������������������ɫ���ж�˳������ѡ���Ƿ���һ�š�ɱ�����ṩ�����㣨��Ϊ����ʹ�û�������ֱ����һ����ɫ��û���κν�ɫ���������ʱΪֹ��\n"
				+ "��ʹ���ʵ¼��ֳܷ����ƣ��Է��޷��ܾ���";
		this.dispName = "����";
		this.jiNengN1 = "�ʵ�";
		this.jiNengN2 = "����";
	}

	// enable jiNengButton
	public void listenEnterHuiHeEvent() {
		super.listenEnterHuiHeEvent();
		this.totalSendSP = 0;
		this.enableWuJiangJiNengBtn();
	}

	public void enableWuJiangJiNengBtn() {
		if (!this.tuoGuan) {
			this.gameApp.libGameViewData.mJiNengBtn1
					.setVisibility(View.VISIBLE);
			this.gameApp.libGameViewData.mJiNengBtn1.setEnabled(true);
			this.gameApp.libGameViewData.mJiNengBtn1Txt.setText(this.jiNengN1);

			this.gameApp.libGameViewData.mJiNengBtn2
					.setVisibility(View.VISIBLE);
			this.gameApp.libGameViewData.mJiNengBtn2.setEnabled(true);
			this.gameApp.libGameViewData.mJiNengBtn2Txt.setText(this.jiNengN2);
		}
		// invoke super to set the correct JiNengBtnImage
		super.enableWuJiangJiNengBtn();
	}

	public void handleJiNengBtnEvent(int eventID) {

		switch (eventID) {
		case R.id.JiNeng1: {
			if (this.state != Type.State.ChuPai)
				return;

			if (this.shouPai.size() == 0) {
				this.gameApp.libGameViewData.logInfo(
						"û�����Ʋ��ܷ���" + this.jiNengN1, Type.logDelay.NoDelay);
				return;
			}

			this.gameApp.ynData.reset();
			this.gameApp.ynData.okTxt = "ȷ��";
			this.gameApp.ynData.cancelTxt = "ȡ��";
			this.gameApp.ynData.genInfo = "�Ƿ�ʹ��" + this.jiNengN1 + "?";

			YesNoDialog dlg = new YesNoDialog(this.gameApp.gameActivityContext,
					this.gameApp);
			dlg.showDialog();
			if (!this.gameApp.ynData.result)
				return;

			RenDe rende = new RenDe(Type.CardPai.WJJiNeng,
					Type.CardPaiClass.nil, 0);
			rende.gameApp = this.gameApp;
			rende.belongToWuJiang = this;

			// select card pai first
			this.gameApp.wjDetailsViewData.reset();
			this.gameApp.wjDetailsViewData.selectedCardNumber = 2;
			this.gameApp.wjDetailsViewData.selectedCardN1Or2 = true;
			this.gameApp.wjDetailsViewData.canViewShouPai = true;

			this.gameApp.wjDetailsViewData.selectedWJ = this;

			WuJiangCardPaiData wjCPData = new WuJiangCardPaiData();
			wjCPData.shouPai = this.shouPai;

			SelectCardPaiFromWJDialog dlg2 = new SelectCardPaiFromWJDialog(
					this.gameApp.gameActivityContext, this.gameApp, wjCPData);
			dlg2.showDialog();

			rende.sp1 = this.gameApp.wjDetailsViewData.selectedCardPai1;
			rende.sp2 = this.gameApp.wjDetailsViewData.selectedCardPai2;

			// then select one wj
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
					gameApp.gameLogicData.myWuJiang, "��ѡ��"
							+ gameApp.selectWJViewData.selectNumber + "���佫");

			rende.tarWJ = gameApp.selectWJViewData.selectedWJ1;

			if (rende.tarWJ == null)
				return;

			rende.selectedByClick = true;

			// reset myWuJiang shoupai to unselect card pai
			this.resetShouPaiSelectedBoolean();
			// then add liJian into shoupai list
			this.shouPai.add(rende);

			// then select tarWJ
			if (gameApp.gameLogicData.askForPai == Type.CardPai.notNil) {
				this.gameApp.gameLogicData.userInterface.loop = true;
				this.gameApp.gameLogicData.userInterface
						.sendMessageToUIForWakeUp();
			}

			break;
		}
		}
	}

	// For AI: Add RenDe cardpai into shoupai
	public CardPai generateJiNengCardPai() {

		if (!this.tuoGuan)
			return null;

		if (this.shouPai.size() == 0)
			return null;

		RenDe rende = new RenDe(Type.CardPai.WJJiNeng, Type.CardPaiClass.nil, 0);
		rende.gameApp = this.gameApp;
		rende.belongToWuJiang = this;

		WuJiang huaTuo = this.gameApp.gameLogicData.wjHelper
				.getRunningWuJiangByName(this, Type.WuJiang.HuaTuo);
		WuJiang sunShangXiang = this.gameApp.gameLogicData.wjHelper
				.getRunningWuJiangByName(this, Type.WuJiang.SunShangXiang);
		WuJiang huangGai = this.gameApp.gameLogicData.wjHelper
				.getRunningWuJiangByName(this, Type.WuJiang.HuangGai);

		// huatuo
		if (huaTuo != null && this.isFriend(huaTuo)) {

			CardPai redCP = this
					.selectFromShouPaiByClass(Type.CardPaiClass.FangPian);
			if (redCP == null) {
				redCP = this
						.selectFromShouPaiByClass(Type.CardPaiClass.HongTao);
			}

			if (redCP != null) {
				rende.sp1 = redCP;
				rende.tarWJ = huaTuo;
				return rende;
			}
		}

		// xiangxiang
		if (sunShangXiang != null && this.isFriend(sunShangXiang)) {

			CardPai zbCP = this.selectMaCardPaiFromShouPai(-1);
			if (zbCP == null) {
				zbCP = this.selectMaCardPaiFromShouPai(+1);
			}
			if (zbCP == null) {
				zbCP = this.selectWuQiFromShouPai();
			}
			if (zbCP == null) {
				zbCP = this.selectFangJuFromShouPai();
			}

			if (zbCP != null) {
				rende.sp1 = zbCP;
				rende.tarWJ = sunShangXiang;
				return rende;
			}
		}

		// huangGai
		if (huangGai != null && this.isFriend(huangGai)) {

			CardPai taoCP = this.selectCardPaiFromShouPai(Type.CardPai.Tao);

			if (taoCP != null) {
				rende.sp1 = taoCP;
				rende.tarWJ = huangGai;
				return rende;
			}

			CardPai lianNu = this
					.selectCardPaiFromShouPai(Type.CardPai.ZhuGeLianNu);

			if (lianNu != null) {
				rende.sp1 = lianNu;
				rende.tarWJ = huangGai;
				return rende;
			}
		}

		// many many scenario ....
		//
		if ((this.blood < this.getMaxBlood() && this.shouPai.size() >= 2)
				|| (this.blood == this.getMaxBlood() && this.shouPai.size() >= this.blood)) {
			WuJiang tarWJ = null;
			if (this.friendList.size() > 0) {
				tarWJ = this.friendList.get(0);
			}
			if (tarWJ != null) {
				rende.tarWJ = tarWJ;
				rende.sp1 = this.shouPai.get(0);
				rende.sp2 = this.shouPai.get(1);
				return rende;
			}
		}

		return null;
	}

	public CardPai chuSha(WuJiang srcWJ, CardPai srcCP) {
		// LiuBei is zhugong, can use huJia jiNeng
		if (this.role != Type.Role.ZhuGong)
			return super.chuSha(srcWJ, srcCP);

		boolean jj = false;

		if (this.tuoGuan) {
			// For AI: if there sha, then no need to jiJiang
			CardPai shaCP = this.selectCardPaiFromShouPai(Type.CardPai.Sha);
			if (shaCP != null) {
				shaCP.belongToWuJiang = null;
				this.detatchCardPaiFromShouPai(shaCP);
				return shaCP;
			}
			// if no shan, then trigger huJia
			jj = true;
		} else {
			// ask UI whether use huJia
			this.gameApp.ynData.reset();
			this.gameApp.ynData.okTxt = "ȷ��";
			this.gameApp.ynData.cancelTxt = "ȡ��";
			this.gameApp.ynData.genInfo = "�Ƿ񷢶�" + this.jiNengN2 + "?";

			YesNoDialog dlg = new YesNoDialog(this.gameApp.gameActivityContext,
					this.gameApp);
			dlg.showDialog();
			jj = this.gameApp.ynData.result;
		}

		CardPai shaCP = null;
		if (jj) {
			JiJiang jiJiang = new JiJiang(Type.CardPai.WJJiNeng,
					Type.CardPaiClass.nil, 0);
			jiJiang.gameApp = this.gameApp;
			jiJiang.belongToWuJiang = this;
			// no shan shou pai, ask huJia
			this.gameApp.libGameViewData.logInfo(this.dispName + "����"
					+ this.jiNengN2, Type.logDelay.Delay);

			WuJiang tarWJ = this.nextOne;
			while (shaCP == null && !tarWJ.equals(this)) {
				if (tarWJ.country == Type.Country.Shu) {
					shaCP = tarWJ.jiJiang(this, jiJiang);
				}
				tarWJ = tarWJ.nextOne;
			}
		}

		if (shaCP != null) {
			return shaCP;
		} else {
			// Lastly, if JiJiang fails, then chu Sha
			return super.chuSha(srcWJ, srcCP);
		}
	}
}