package alben.sgs.wujiang.instance;

import alben.sgs.android.dialog.YesNoDialog;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.instance.ZBSMSha;
import alben.sgs.type.Type;
import alben.sgs.type.UpdateWJViewData;
import alben.sgs.wujiang.WuJiang;
import alben.sgs.wujiang.instance.jineng.FanJian;
import alben.sgs.wujiang.instance.jineng.GangLie;
import alben.sgs.wujiang.instance.jineng.HuJia;
import alben.sgs.wujiang.instance.jineng.HuoJi;
import alben.sgs.wujiang.instance.jineng.LeiJi;
import alben.sgs.wujiang.instance.jineng.LongDan;
import alben.sgs.wujiang.instance.jineng.LuanJi;
import alben.sgs.wujiang.instance.jineng.ShenSu;
import alben.sgs.wujiang.instance.jineng.ShuangXiong;
import alben.sgs.wujiang.instance.jineng.TianXiang;
import alben.sgs.wujiang.instance.jineng.WuSheng;
import android.view.View;

public class CaoCao extends WuJiang {
	public CaoCao(Type.WuJiang n, Type.Country c, Type.Sex s, int b) {
		super(n, c, s, b);
		this.imageNumber = alben.sgs.android.R.drawable.wj_caocao;
		this.jiNengDesc = "1�����ۣ������������ö�������˺����ơ�\n"
				+ "2�����ݣ���������������Ҫʹ�ã�������һ�š�����ʱ������Է������ݡ�����κ������ɫ���ж�˳������ѡ���Ƿ���һ�š��������ṩ�����㣨��Ϊ����ʹ�û�������ֱ����һ����ɫ��û���κν�ɫ���������ʱΪֹ��";
		this.dispName = "�ܲ�";
		this.jiNengN1 = "����";
		this.jiNengN2 = "����";
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

			if (this.role == Type.Role.ZhuGong) {
				this.gameApp.libGameViewData.mJiNengBtn2
						.setVisibility(View.VISIBLE);
				this.gameApp.libGameViewData.mJiNengBtn2.setEnabled(false);
				this.gameApp.libGameViewData.mJiNengBtn2Txt
						.setText(this.jiNengN2);
			}
		}
		// invoke super to set the correct JiNengBtnImage
		super.enableWuJiangJiNengBtn();
	}

	// jiNengN1
	public void listenIncreaseBloodEvent(CardPai srcCP) {

		CardPai origSrcCP1 = null;
		CardPai origSrcCP2 = null;

		if (srcCP instanceof LeiJi || srcCP instanceof ShenSu
				|| srcCP instanceof FanJian)
			return;

		if (srcCP.name != Type.CardPai.WJJiNeng
				&& srcCP.cpState == Type.CPState.FeiPaiDui) {
			origSrcCP1 = srcCP;
			origSrcCP1.reset();
		}

		if (srcCP instanceof TianXiang) {
			origSrcCP1 = ((TianXiang) srcCP).srcCP;
			if (origSrcCP1 != null && origSrcCP1.name != Type.CardPai.WJJiNeng
					&& origSrcCP1.cpState == Type.CPState.FeiPaiDui) {
				origSrcCP1.reset();
			}
		}

		if (srcCP instanceof GangLie) {
			origSrcCP1 = ((GangLie) srcCP).srcCP;
			if (origSrcCP1 != null && origSrcCP1.name != Type.CardPai.WJJiNeng
					&& origSrcCP1.cpState == Type.CPState.FeiPaiDui) {
				origSrcCP1.reset();
			}
		}

		if (srcCP instanceof ZBSMSha) {
			origSrcCP1 = ((ZBSMSha) srcCP).sp1;
			origSrcCP2 = ((ZBSMSha) srcCP).sp2;
			origSrcCP1.reset();
			origSrcCP2.reset();
		}

		if (srcCP instanceof LuanJi) {
			origSrcCP1 = ((LuanJi) srcCP).sp1;
			origSrcCP2 = ((LuanJi) srcCP).sp2;
			origSrcCP1.reset();
			origSrcCP2.reset();
		}

		if (srcCP instanceof WuSheng) {
			origSrcCP1 = ((WuSheng) srcCP).sp1;
			origSrcCP1.reset();
		}

		if (srcCP instanceof HuoJi) {
			origSrcCP1 = ((HuoJi) srcCP).sp1;
			origSrcCP1.reset();
		}

		if (srcCP instanceof ShuangXiong) {
			origSrcCP1 = ((ShuangXiong) srcCP).sp1;
			origSrcCP1.reset();
		}

		if (srcCP instanceof LongDan) {
			origSrcCP1 = ((LongDan) srcCP).sp1;
			origSrcCP1.reset();
		}

		if (origSrcCP1 == null)
			return;

		boolean jx = false;
		if (this.tuoGuan) {
			jx = true;
		} else {
			this.gameApp.ynData.reset();
			this.gameApp.ynData.okTxt = "ȷ��";
			this.gameApp.ynData.cancelTxt = "ȡ��";
			this.gameApp.ynData.genInfo = "�Ƿ񷢶�" + this.jiNengN1 + "����˺���Դ����?";

			YesNoDialog dlg = new YesNoDialog(this.gameApp.gameActivityContext,
					this.gameApp);
			dlg.showDialog();
			if (this.gameApp.ynData.result)
				jx = true;
		}

		if (!jx)
			return;

		if (srcCP instanceof ZBSMSha) {
			origSrcCP1.belongToWuJiang = this;
			origSrcCP2.belongToWuJiang = this;
			origSrcCP1.cpState = Type.CPState.ShouPai;
			origSrcCP2.cpState = Type.CPState.ShouPai;
			this.shouPai.add(origSrcCP1);
			this.shouPai.add(origSrcCP2);
		} else {
			origSrcCP1.belongToWuJiang = this;
			origSrcCP1.cpState = Type.CPState.ShouPai;
			this.shouPai.add(origSrcCP1);
		}

		if (this.tuoGuan) {
			UpdateWJViewData item = new UpdateWJViewData();
			item.updateShouPaiNumber = true;
			this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(
					this, item);
		} else {
			this.gameApp.gameLogicData.wjHelper.updateWJ8ShouPaiToLibGameView();
		}

		this.gameApp.libGameViewData.logInfo(this.dispName + "["
				+ this.jiNengN1 + "]" + "�������" + origSrcCP1,
				Type.logDelay.NoDelay);
	}

	public CardPai chuShan(WuJiang srcWJ, CardPai srcCP) {
		// caocao is zhugong, can use huJia jiNeng
		if (this.role != Type.Role.ZhuGong)
			return super.chuShan(srcWJ, srcCP);

		boolean hj = false;

		if (this.tuoGuan) {
			// For AI: if there shan, then no need to huJia
			CardPai shanCP = this.selectCardPaiFromShouPai(Type.CardPai.Shan);
			if (shanCP != null) {
				shanCP.belongToWuJiang = null;
				this.detatchCardPaiFromShouPai(shanCP);
				return shanCP;
			}
			// if no shan, then trigger huJia
			hj = true;
		} else {
			// ask UI whether use huJia
			this.gameApp.ynData.reset();
			this.gameApp.ynData.okTxt = "ȷ��";
			this.gameApp.ynData.cancelTxt = "ȡ��";
			this.gameApp.ynData.genInfo = "�Ƿ񷢶�" + this.jiNengN2 + "?";

			YesNoDialog dlg = new YesNoDialog(this.gameApp.gameActivityContext,
					this.gameApp);
			dlg.showDialog();
			hj = this.gameApp.ynData.result;
		}

		CardPai shanCP = null;
		if (hj) {
			HuJia hujia = new HuJia(Type.CardPai.WJJiNeng,
					Type.CardPaiClass.nil, 0);
			hujia.gameApp = this.gameApp;
			hujia.belongToWuJiang = this;
			// no shan shou pai, ask huJia
			this.gameApp.libGameViewData.logInfo(this.dispName + "����["
					+ this.jiNengN2 + "]", Type.logDelay.Delay);

			WuJiang tarWJ = this.nextOne;
			while (shanCP == null && !tarWJ.equals(this)) {
				if (tarWJ.country == Type.Country.Wei) {
					shanCP = tarWJ.huJia(this, hujia);
				}
				tarWJ = tarWJ.nextOne;
			}
		}

		if (shanCP != null) {
			return shanCP;
		} else {
			// Lastly, if huJia fails, then chu Shan
			return super.chuShan(srcWJ, srcCP);
		}
	}
}
