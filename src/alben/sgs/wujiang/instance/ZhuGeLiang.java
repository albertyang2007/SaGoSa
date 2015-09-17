package alben.sgs.wujiang.instance;

import java.util.ArrayList;

import alben.sgs.android.R;
import alben.sgs.android.dialog.GuanXingDialig;
import alben.sgs.android.dialog.YesNoDialog;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.FangJuCardPai;
import alben.sgs.cardpai.WuQiCardPai;
import alben.sgs.cardpai.instance.BingLiangCunDuan;
import alben.sgs.cardpai.instance.LeBuShiShu;
import alben.sgs.cardpai.instance.ShanDian;
import alben.sgs.cardpai.instance.Tao;
import alben.sgs.type.Type;
import alben.sgs.wujiang.WuJiang;
import android.view.View;

public class ZhuGeLiang extends WuJiang {
	public ZhuGeLiang(Type.WuJiang n, Type.Country c, Type.Sex s, int b) {
		super(n, c, s, b);
		this.imageNumber = alben.sgs.android.R.drawable.wj_zhugeliang;
		this.jiNengDesc = "1、观星：回合开始阶段，你可以观看牌堆顶的X张牌（×为存活角色的数量且最多为5），将其中任意数量的牌以任意顺序置于牌堆顶，其余则以任意顺序置于牌堆底。\n"
				+ "2、空城：锁定技，当你没有手牌时，你不能成为【杀】或【决斗】的目标。";
		this.dispName = "诸葛亮";
		this.jiNengN1 = "观星";
		this.jiNengN2 = "空城";
	}

	public void listenEnterHuiHeEvent() {
		super.listenEnterHuiHeEvent();
		this.enableWuJiangJiNengBtn();

		this.guanXing();
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

	public void guanXing() {

		// count the living wuJiang
		int alivingWJN = 1;
		WuJiang tarWJ = this.nextOne;
		while (!tarWJ.equals(this)) {
			alivingWJN++;
			tarWJ = tarWJ.nextOne;
		}

		// view the cardPai from pool
		CardPai[] viewCPs = { null, null, null, null, null };
		int viewCPN = (alivingWJN >= 5) ? 5 : alivingWJN;

		if (this.tuoGuan) {
			for (int i = 0; i < viewCPN; i++) {
				viewCPs[i] = this.gameApp.gameLogicData.cpHelper
						.viewTopCardPai(i);
			}

			// get the max value from all arrangement and update the top CardPai
			CardPai[] newTopCPS = countTheMaxValueList(viewCPs, viewCPN);
			for (int i = 0; i < viewCPN; i++) {
				this.gameApp.gameLogicData.cpHelper.setTopCardPai(i,
						newTopCPS[i]);
			}

		} else {

			this.gameApp.ynData.reset();
			this.gameApp.ynData.okTxt = "确认";
			this.gameApp.ynData.cancelTxt = "取消";
			this.gameApp.ynData.genInfo = "是否" + this.jiNengN1 + "?";

			YesNoDialog dlg = new YesNoDialog(this.gameApp.gameActivityContext,
					this.gameApp);
			dlg.showDialog();
			if (!this.gameApp.ynData.result)
				return;

			gameApp.libGameViewData.logInfo(this.dispName + "[" + this.jiNengN1
					+ "]", Type.logDelay.NoDelay);

			this.gameApp.wjDetailsViewData.reset();
			this.gameApp.wjDetailsViewData.selectedWJ = this;

			GuanXingDialig dlg2 = new GuanXingDialig(
					this.gameApp.gameActivityContext, this.gameApp);
			dlg2.showDialog();
		}

		//
		int butN = 0;
		for (int i = 0; i < viewCPN; i++) {
			CardPai topCP = gameApp.gameLogicData.cpHelper.viewTopCardPai(i);
			if (topCP.name == Type.CardPai.nil) {
				butN++;
			}
		}
		gameApp.libGameViewData.logInfo(
				gameApp.gameLogicData.myWuJiang.dispName + "将" + butN
						+ "张牌放入堆底", Type.logDelay.Delay);
	}

	public CardPai[] countTheMaxValueList(CardPai[] viewCPs, int viewCPN) {
		CardPai[] maxValueCPs = viewCPs;

		ArrayList<CardPai[]> list = this.buildCardPaiArrangement(viewCPs,
				viewCPN);

		maxValueCPs = list.get(0);
		int maxValue = this.getMaxValue(maxValueCPs);
		for (int i = 1; i < list.size(); i++) {
			int tmpValue = this.getMaxValue(list.get(i));
			if (tmpValue > maxValue) {
				maxValueCPs = list.get(i);
				maxValue = tmpValue;
			}
		}

		// if maxValue still 0 means it is better to put one or two cardpai into
		// button
		if (maxValue == 0) {
			CardPai butCP = new CardPai(Type.CardPai.nil,
					Type.CardPaiClass.nil, 0);
			butCP.imageNumber = R.drawable.card_back;
			// put all of them into button
			for (int i = 0; i < viewCPN; i++) {
				maxValueCPs[i] = butCP;
			}
		}

		return maxValueCPs;
	}

	// pai lie
	public ArrayList<CardPai[]> buildCardPaiArrangement(CardPai[] viewCPs,
			int viewCPN) {

		ArrayList<CardPai[]> list = new ArrayList<CardPai[]>();

		if (viewCPN == 2) {
			CardPai[] arr1 = { viewCPs[0], viewCPs[1] };
			CardPai[] arr2 = { viewCPs[1], viewCPs[0] };
			list.add(arr1);
			list.add(arr2);
		} else if (viewCPN == 3) {
			CardPai[] arr1 = { viewCPs[0], viewCPs[1], viewCPs[2] };
			CardPai[] arr2 = { viewCPs[0], viewCPs[2], viewCPs[1] };
			CardPai[] arr3 = { viewCPs[1], viewCPs[0], viewCPs[2] };
			CardPai[] arr4 = { viewCPs[1], viewCPs[2], viewCPs[0] };
			CardPai[] arr5 = { viewCPs[2], viewCPs[0], viewCPs[1] };
			CardPai[] arr6 = { viewCPs[2], viewCPs[1], viewCPs[0] };
			list.add(arr1);
			list.add(arr2);
			list.add(arr3);
			list.add(arr4);
			list.add(arr5);
			list.add(arr6);
		} else if (viewCPN == 4) {
			CardPai[] arr1 = { viewCPs[0], viewCPs[1], viewCPs[2], viewCPs[3] };
			CardPai[] arr2 = { viewCPs[0], viewCPs[1], viewCPs[3], viewCPs[2] };
			CardPai[] arr3 = { viewCPs[0], viewCPs[2], viewCPs[1], viewCPs[3] };
			CardPai[] arr4 = { viewCPs[0], viewCPs[2], viewCPs[3], viewCPs[1] };
			CardPai[] arr5 = { viewCPs[0], viewCPs[3], viewCPs[2], viewCPs[1] };
			CardPai[] arr6 = { viewCPs[0], viewCPs[3], viewCPs[1], viewCPs[2] };
			CardPai[] arr7 = { viewCPs[1], viewCPs[0], viewCPs[2], viewCPs[3] };
			CardPai[] arr8 = { viewCPs[1], viewCPs[0], viewCPs[3], viewCPs[2] };
			CardPai[] arr9 = { viewCPs[1], viewCPs[2], viewCPs[0], viewCPs[3] };
			CardPai[] arr10 = { viewCPs[1], viewCPs[2], viewCPs[3], viewCPs[0] };
			CardPai[] arr11 = { viewCPs[1], viewCPs[3], viewCPs[2], viewCPs[0] };
			CardPai[] arr12 = { viewCPs[1], viewCPs[3], viewCPs[0], viewCPs[2] };
			CardPai[] arr13 = { viewCPs[2], viewCPs[1], viewCPs[0], viewCPs[3] };
			CardPai[] arr14 = { viewCPs[2], viewCPs[1], viewCPs[3], viewCPs[0] };
			CardPai[] arr15 = { viewCPs[2], viewCPs[0], viewCPs[1], viewCPs[3] };
			CardPai[] arr16 = { viewCPs[2], viewCPs[0], viewCPs[3], viewCPs[1] };
			CardPai[] arr17 = { viewCPs[2], viewCPs[3], viewCPs[0], viewCPs[1] };
			CardPai[] arr18 = { viewCPs[2], viewCPs[3], viewCPs[1], viewCPs[0] };
			CardPai[] arr19 = { viewCPs[3], viewCPs[1], viewCPs[2], viewCPs[0] };
			CardPai[] arr20 = { viewCPs[3], viewCPs[1], viewCPs[0], viewCPs[2] };
			CardPai[] arr21 = { viewCPs[3], viewCPs[2], viewCPs[1], viewCPs[0] };
			CardPai[] arr22 = { viewCPs[3], viewCPs[2], viewCPs[0], viewCPs[1] };
			CardPai[] arr23 = { viewCPs[3], viewCPs[0], viewCPs[2], viewCPs[1] };
			CardPai[] arr24 = { viewCPs[3], viewCPs[0], viewCPs[1], viewCPs[2] };
			list.add(arr1);
			list.add(arr2);
			list.add(arr3);
			list.add(arr4);
			list.add(arr5);
			list.add(arr6);
			list.add(arr7);
			list.add(arr8);
			list.add(arr9);
			list.add(arr10);
			list.add(arr11);
			list.add(arr12);
			list.add(arr13);
			list.add(arr14);
			list.add(arr15);
			list.add(arr16);
			list.add(arr17);
			list.add(arr18);
			list.add(arr19);
			list.add(arr20);
			list.add(arr21);
			list.add(arr22);
			list.add(arr23);
			list.add(arr24);
		} else if (viewCPN == 5) {
			CardPai[] arr1 = { viewCPs[0], viewCPs[1], viewCPs[2], viewCPs[3],
					viewCPs[4] };
			CardPai[] arr2 = { viewCPs[0], viewCPs[1], viewCPs[2], viewCPs[4],
					viewCPs[3] };
			CardPai[] arr3 = { viewCPs[0], viewCPs[1], viewCPs[3], viewCPs[2],
					viewCPs[4] };
			CardPai[] arr4 = { viewCPs[0], viewCPs[1], viewCPs[3], viewCPs[4],
					viewCPs[2] };
			CardPai[] arr5 = { viewCPs[0], viewCPs[1], viewCPs[4], viewCPs[3],
					viewCPs[2] };
			CardPai[] arr6 = { viewCPs[0], viewCPs[1], viewCPs[4], viewCPs[2],
					viewCPs[3] };
			CardPai[] arr7 = { viewCPs[0], viewCPs[2], viewCPs[1], viewCPs[3],
					viewCPs[4] };
			CardPai[] arr8 = { viewCPs[0], viewCPs[2], viewCPs[1], viewCPs[4],
					viewCPs[3] };
			CardPai[] arr9 = { viewCPs[0], viewCPs[2], viewCPs[3], viewCPs[1],
					viewCPs[4] };
			CardPai[] arr10 = { viewCPs[0], viewCPs[2], viewCPs[3], viewCPs[4],
					viewCPs[1] };
			CardPai[] arr11 = { viewCPs[0], viewCPs[2], viewCPs[4], viewCPs[3],
					viewCPs[1] };
			CardPai[] arr12 = { viewCPs[0], viewCPs[2], viewCPs[4], viewCPs[1],
					viewCPs[3] };
			CardPai[] arr13 = { viewCPs[0], viewCPs[3], viewCPs[2], viewCPs[1],
					viewCPs[4] };
			CardPai[] arr14 = { viewCPs[0], viewCPs[3], viewCPs[2], viewCPs[4],
					viewCPs[1] };
			CardPai[] arr15 = { viewCPs[0], viewCPs[3], viewCPs[1], viewCPs[2],
					viewCPs[4] };
			CardPai[] arr16 = { viewCPs[0], viewCPs[3], viewCPs[1], viewCPs[4],
					viewCPs[2] };
			CardPai[] arr17 = { viewCPs[0], viewCPs[3], viewCPs[4], viewCPs[1],
					viewCPs[2] };
			CardPai[] arr18 = { viewCPs[0], viewCPs[3], viewCPs[4], viewCPs[2],
					viewCPs[1] };
			CardPai[] arr19 = { viewCPs[0], viewCPs[4], viewCPs[2], viewCPs[3],
					viewCPs[1] };
			CardPai[] arr20 = { viewCPs[0], viewCPs[4], viewCPs[2], viewCPs[1],
					viewCPs[3] };
			CardPai[] arr21 = { viewCPs[0], viewCPs[4], viewCPs[3], viewCPs[2],
					viewCPs[1] };
			CardPai[] arr22 = { viewCPs[0], viewCPs[4], viewCPs[3], viewCPs[1],
					viewCPs[2] };
			CardPai[] arr23 = { viewCPs[0], viewCPs[4], viewCPs[1], viewCPs[3],
					viewCPs[2] };
			CardPai[] arr24 = { viewCPs[0], viewCPs[4], viewCPs[1], viewCPs[2],
					viewCPs[3] };
			CardPai[] arr25 = { viewCPs[1], viewCPs[0], viewCPs[2], viewCPs[3],
					viewCPs[4] };
			CardPai[] arr26 = { viewCPs[1], viewCPs[0], viewCPs[2], viewCPs[4],
					viewCPs[3] };
			CardPai[] arr27 = { viewCPs[1], viewCPs[0], viewCPs[3], viewCPs[2],
					viewCPs[4] };
			CardPai[] arr28 = { viewCPs[1], viewCPs[0], viewCPs[3], viewCPs[4],
					viewCPs[2] };
			CardPai[] arr29 = { viewCPs[1], viewCPs[0], viewCPs[4], viewCPs[3],
					viewCPs[2] };
			CardPai[] arr30 = { viewCPs[1], viewCPs[0], viewCPs[4], viewCPs[2],
					viewCPs[3] };
			CardPai[] arr31 = { viewCPs[1], viewCPs[2], viewCPs[0], viewCPs[3],
					viewCPs[4] };
			CardPai[] arr32 = { viewCPs[1], viewCPs[2], viewCPs[0], viewCPs[4],
					viewCPs[3] };
			CardPai[] arr33 = { viewCPs[1], viewCPs[2], viewCPs[3], viewCPs[0],
					viewCPs[4] };
			CardPai[] arr34 = { viewCPs[1], viewCPs[2], viewCPs[3], viewCPs[4],
					viewCPs[0] };
			CardPai[] arr35 = { viewCPs[1], viewCPs[2], viewCPs[4], viewCPs[3],
					viewCPs[0] };
			CardPai[] arr36 = { viewCPs[1], viewCPs[2], viewCPs[4], viewCPs[0],
					viewCPs[3] };
			CardPai[] arr37 = { viewCPs[1], viewCPs[3], viewCPs[2], viewCPs[0],
					viewCPs[4] };
			CardPai[] arr38 = { viewCPs[1], viewCPs[3], viewCPs[2], viewCPs[4],
					viewCPs[0] };
			CardPai[] arr39 = { viewCPs[1], viewCPs[3], viewCPs[0], viewCPs[2],
					viewCPs[4] };
			CardPai[] arr40 = { viewCPs[1], viewCPs[3], viewCPs[0], viewCPs[4],
					viewCPs[2] };
			CardPai[] arr41 = { viewCPs[1], viewCPs[3], viewCPs[4], viewCPs[0],
					viewCPs[2] };
			CardPai[] arr42 = { viewCPs[1], viewCPs[3], viewCPs[4], viewCPs[2],
					viewCPs[0] };
			CardPai[] arr43 = { viewCPs[1], viewCPs[4], viewCPs[2], viewCPs[3],
					viewCPs[0] };
			CardPai[] arr44 = { viewCPs[1], viewCPs[4], viewCPs[2], viewCPs[0],
					viewCPs[3] };
			CardPai[] arr45 = { viewCPs[1], viewCPs[4], viewCPs[3], viewCPs[2],
					viewCPs[0] };
			CardPai[] arr46 = { viewCPs[1], viewCPs[4], viewCPs[3], viewCPs[0],
					viewCPs[2] };
			CardPai[] arr47 = { viewCPs[1], viewCPs[4], viewCPs[0], viewCPs[3],
					viewCPs[2] };
			CardPai[] arr48 = { viewCPs[1], viewCPs[4], viewCPs[0], viewCPs[2],
					viewCPs[3] };
			CardPai[] arr49 = { viewCPs[2], viewCPs[1], viewCPs[0], viewCPs[3],
					viewCPs[4] };
			CardPai[] arr50 = { viewCPs[2], viewCPs[1], viewCPs[0], viewCPs[4],
					viewCPs[3] };
			CardPai[] arr51 = { viewCPs[2], viewCPs[1], viewCPs[3], viewCPs[0],
					viewCPs[4] };
			CardPai[] arr52 = { viewCPs[2], viewCPs[1], viewCPs[3], viewCPs[4],
					viewCPs[0] };
			CardPai[] arr53 = { viewCPs[2], viewCPs[1], viewCPs[4], viewCPs[3],
					viewCPs[0] };
			CardPai[] arr54 = { viewCPs[2], viewCPs[1], viewCPs[4], viewCPs[0],
					viewCPs[3] };
			CardPai[] arr55 = { viewCPs[2], viewCPs[0], viewCPs[1], viewCPs[3],
					viewCPs[4] };
			CardPai[] arr56 = { viewCPs[2], viewCPs[0], viewCPs[1], viewCPs[4],
					viewCPs[3] };
			CardPai[] arr57 = { viewCPs[2], viewCPs[0], viewCPs[3], viewCPs[1],
					viewCPs[4] };
			CardPai[] arr58 = { viewCPs[2], viewCPs[0], viewCPs[3], viewCPs[4],
					viewCPs[1] };
			CardPai[] arr59 = { viewCPs[2], viewCPs[0], viewCPs[4], viewCPs[3],
					viewCPs[1] };
			CardPai[] arr60 = { viewCPs[2], viewCPs[0], viewCPs[4], viewCPs[1],
					viewCPs[3] };
			CardPai[] arr61 = { viewCPs[2], viewCPs[3], viewCPs[0], viewCPs[1],
					viewCPs[4] };
			CardPai[] arr62 = { viewCPs[2], viewCPs[3], viewCPs[0], viewCPs[4],
					viewCPs[1] };
			CardPai[] arr63 = { viewCPs[2], viewCPs[3], viewCPs[1], viewCPs[0],
					viewCPs[4] };
			CardPai[] arr64 = { viewCPs[2], viewCPs[3], viewCPs[1], viewCPs[4],
					viewCPs[0] };
			CardPai[] arr65 = { viewCPs[2], viewCPs[3], viewCPs[4], viewCPs[1],
					viewCPs[0] };
			CardPai[] arr66 = { viewCPs[2], viewCPs[3], viewCPs[4], viewCPs[0],
					viewCPs[1] };
			CardPai[] arr67 = { viewCPs[2], viewCPs[4], viewCPs[0], viewCPs[3],
					viewCPs[1] };
			CardPai[] arr68 = { viewCPs[2], viewCPs[4], viewCPs[0], viewCPs[1],
					viewCPs[3] };
			CardPai[] arr69 = { viewCPs[2], viewCPs[4], viewCPs[3], viewCPs[0],
					viewCPs[1] };
			CardPai[] arr70 = { viewCPs[2], viewCPs[4], viewCPs[3], viewCPs[1],
					viewCPs[0] };
			CardPai[] arr71 = { viewCPs[2], viewCPs[4], viewCPs[1], viewCPs[3],
					viewCPs[0] };
			CardPai[] arr72 = { viewCPs[2], viewCPs[4], viewCPs[1], viewCPs[0],
					viewCPs[3] };
			CardPai[] arr73 = { viewCPs[3], viewCPs[1], viewCPs[2], viewCPs[0],
					viewCPs[4] };
			CardPai[] arr74 = { viewCPs[3], viewCPs[1], viewCPs[2], viewCPs[4],
					viewCPs[0] };
			CardPai[] arr75 = { viewCPs[3], viewCPs[1], viewCPs[0], viewCPs[2],
					viewCPs[4] };
			CardPai[] arr76 = { viewCPs[3], viewCPs[1], viewCPs[0], viewCPs[4],
					viewCPs[2] };
			CardPai[] arr77 = { viewCPs[3], viewCPs[1], viewCPs[4], viewCPs[0],
					viewCPs[2] };
			CardPai[] arr78 = { viewCPs[3], viewCPs[1], viewCPs[4], viewCPs[2],
					viewCPs[0] };
			CardPai[] arr79 = { viewCPs[3], viewCPs[2], viewCPs[1], viewCPs[0],
					viewCPs[4] };
			CardPai[] arr80 = { viewCPs[3], viewCPs[2], viewCPs[1], viewCPs[4],
					viewCPs[0] };
			CardPai[] arr81 = { viewCPs[3], viewCPs[2], viewCPs[0], viewCPs[1],
					viewCPs[4] };
			CardPai[] arr82 = { viewCPs[3], viewCPs[2], viewCPs[0], viewCPs[4],
					viewCPs[1] };
			CardPai[] arr83 = { viewCPs[3], viewCPs[2], viewCPs[4], viewCPs[0],
					viewCPs[1] };
			CardPai[] arr84 = { viewCPs[3], viewCPs[2], viewCPs[4], viewCPs[1],
					viewCPs[0] };
			CardPai[] arr85 = { viewCPs[3], viewCPs[0], viewCPs[2], viewCPs[1],
					viewCPs[4] };
			CardPai[] arr86 = { viewCPs[3], viewCPs[0], viewCPs[2], viewCPs[4],
					viewCPs[1] };
			CardPai[] arr87 = { viewCPs[3], viewCPs[0], viewCPs[1], viewCPs[2],
					viewCPs[4] };
			CardPai[] arr88 = { viewCPs[3], viewCPs[0], viewCPs[1], viewCPs[4],
					viewCPs[2] };
			CardPai[] arr89 = { viewCPs[3], viewCPs[0], viewCPs[4], viewCPs[1],
					viewCPs[2] };
			CardPai[] arr90 = { viewCPs[3], viewCPs[0], viewCPs[4], viewCPs[2],
					viewCPs[1] };
			CardPai[] arr91 = { viewCPs[3], viewCPs[4], viewCPs[2], viewCPs[0],
					viewCPs[1] };
			CardPai[] arr92 = { viewCPs[3], viewCPs[4], viewCPs[2], viewCPs[1],
					viewCPs[0] };
			CardPai[] arr93 = { viewCPs[3], viewCPs[4], viewCPs[0], viewCPs[2],
					viewCPs[1] };
			CardPai[] arr94 = { viewCPs[3], viewCPs[4], viewCPs[0], viewCPs[1],
					viewCPs[2] };
			CardPai[] arr95 = { viewCPs[3], viewCPs[4], viewCPs[1], viewCPs[0],
					viewCPs[2] };
			CardPai[] arr96 = { viewCPs[3], viewCPs[4], viewCPs[1], viewCPs[2],
					viewCPs[0] };
			CardPai[] arr97 = { viewCPs[4], viewCPs[1], viewCPs[2], viewCPs[3],
					viewCPs[0] };
			CardPai[] arr98 = { viewCPs[4], viewCPs[1], viewCPs[2], viewCPs[0],
					viewCPs[3] };
			CardPai[] arr99 = { viewCPs[4], viewCPs[1], viewCPs[3], viewCPs[2],
					viewCPs[0] };
			CardPai[] arr100 = { viewCPs[4], viewCPs[1], viewCPs[3],
					viewCPs[0], viewCPs[2] };
			CardPai[] arr101 = { viewCPs[4], viewCPs[1], viewCPs[0],
					viewCPs[3], viewCPs[2] };
			CardPai[] arr102 = { viewCPs[4], viewCPs[1], viewCPs[0],
					viewCPs[2], viewCPs[3] };
			CardPai[] arr103 = { viewCPs[4], viewCPs[2], viewCPs[1],
					viewCPs[3], viewCPs[0] };
			CardPai[] arr104 = { viewCPs[4], viewCPs[2], viewCPs[1],
					viewCPs[0], viewCPs[3] };
			CardPai[] arr105 = { viewCPs[4], viewCPs[2], viewCPs[3],
					viewCPs[1], viewCPs[0] };
			CardPai[] arr106 = { viewCPs[4], viewCPs[2], viewCPs[3],
					viewCPs[0], viewCPs[1] };
			CardPai[] arr107 = { viewCPs[4], viewCPs[2], viewCPs[0],
					viewCPs[3], viewCPs[1] };
			CardPai[] arr108 = { viewCPs[4], viewCPs[2], viewCPs[0],
					viewCPs[1], viewCPs[3] };
			CardPai[] arr109 = { viewCPs[4], viewCPs[3], viewCPs[2],
					viewCPs[1], viewCPs[0] };
			CardPai[] arr110 = { viewCPs[4], viewCPs[3], viewCPs[2],
					viewCPs[0], viewCPs[1] };
			CardPai[] arr111 = { viewCPs[4], viewCPs[3], viewCPs[1],
					viewCPs[2], viewCPs[0] };
			CardPai[] arr112 = { viewCPs[4], viewCPs[3], viewCPs[1],
					viewCPs[0], viewCPs[2] };
			CardPai[] arr113 = { viewCPs[4], viewCPs[3], viewCPs[0],
					viewCPs[1], viewCPs[2] };
			CardPai[] arr114 = { viewCPs[4], viewCPs[3], viewCPs[0],
					viewCPs[2], viewCPs[1] };
			CardPai[] arr115 = { viewCPs[4], viewCPs[0], viewCPs[2],
					viewCPs[3], viewCPs[1] };
			CardPai[] arr116 = { viewCPs[4], viewCPs[0], viewCPs[2],
					viewCPs[1], viewCPs[3] };
			CardPai[] arr117 = { viewCPs[4], viewCPs[0], viewCPs[3],
					viewCPs[2], viewCPs[1] };
			CardPai[] arr118 = { viewCPs[4], viewCPs[0], viewCPs[3],
					viewCPs[1], viewCPs[2] };
			CardPai[] arr119 = { viewCPs[4], viewCPs[0], viewCPs[1],
					viewCPs[3], viewCPs[2] };
			CardPai[] arr120 = { viewCPs[4], viewCPs[0], viewCPs[1],
					viewCPs[2], viewCPs[3] };
			list.add(arr1);
			list.add(arr2);
			list.add(arr3);
			list.add(arr4);
			list.add(arr5);
			list.add(arr6);
			list.add(arr7);
			list.add(arr8);
			list.add(arr9);
			list.add(arr10);
			list.add(arr11);
			list.add(arr12);
			list.add(arr13);
			list.add(arr14);
			list.add(arr15);
			list.add(arr16);
			list.add(arr17);
			list.add(arr18);
			list.add(arr19);
			list.add(arr20);
			list.add(arr21);
			list.add(arr22);
			list.add(arr23);
			list.add(arr24);
			list.add(arr25);
			list.add(arr26);
			list.add(arr27);
			list.add(arr28);
			list.add(arr29);
			list.add(arr30);
			list.add(arr31);
			list.add(arr32);
			list.add(arr33);
			list.add(arr34);
			list.add(arr35);
			list.add(arr36);
			list.add(arr37);
			list.add(arr38);
			list.add(arr39);
			list.add(arr40);
			list.add(arr41);
			list.add(arr42);
			list.add(arr43);
			list.add(arr44);
			list.add(arr45);
			list.add(arr46);
			list.add(arr47);
			list.add(arr48);
			list.add(arr49);
			list.add(arr50);
			list.add(arr51);
			list.add(arr52);
			list.add(arr53);
			list.add(arr54);
			list.add(arr55);
			list.add(arr56);
			list.add(arr57);
			list.add(arr58);
			list.add(arr59);
			list.add(arr60);
			list.add(arr61);
			list.add(arr62);
			list.add(arr63);
			list.add(arr64);
			list.add(arr65);
			list.add(arr66);
			list.add(arr67);
			list.add(arr68);
			list.add(arr69);
			list.add(arr70);
			list.add(arr71);
			list.add(arr72);
			list.add(arr73);
			list.add(arr74);
			list.add(arr75);
			list.add(arr76);
			list.add(arr77);
			list.add(arr78);
			list.add(arr79);
			list.add(arr80);
			list.add(arr81);
			list.add(arr82);
			list.add(arr83);
			list.add(arr84);
			list.add(arr85);
			list.add(arr86);
			list.add(arr87);
			list.add(arr88);
			list.add(arr89);
			list.add(arr90);
			list.add(arr91);
			list.add(arr92);
			list.add(arr93);
			list.add(arr94);
			list.add(arr95);
			list.add(arr96);
			list.add(arr97);
			list.add(arr98);
			list.add(arr99);
			list.add(arr100);
			list.add(arr101);
			list.add(arr102);
			list.add(arr103);
			list.add(arr104);
			list.add(arr105);
			list.add(arr106);
			list.add(arr107);
			list.add(arr108);
			list.add(arr109);
			list.add(arr110);
			list.add(arr111);
			list.add(arr112);
			list.add(arr113);
			list.add(arr114);
			list.add(arr115);
			list.add(arr116);
			list.add(arr117);
			list.add(arr118);
			list.add(arr119);
			list.add(arr120);
		}

		return list;
	}

	// count the max value for this WJ and his nextone
	// if top CPs are arranged as this
	// arr is at least two card pai
	public int getMaxValue(CardPai[] arr) {

		int rtn = 0;
		Type.CardPaiClass[] myPDCPT = { Type.CardPaiClass.nil,
				Type.CardPaiClass.nil, Type.CardPaiClass.nil };
		Type.CardPaiClass[] nextPDCPT = { Type.CardPaiClass.nil,
				Type.CardPaiClass.nil, Type.CardPaiClass.nil };
		int pdIndex = 0;

		boolean nextWJIsFriend = this.isFriend(this.nextOne);

		// my PanDing
		for (int i = this.panDingPai.size() - 1; i >= 0; i--) {
			CardPai pdCP = this.panDingPai.get(i);
			if (pdCP instanceof LeBuShiShu) {
				myPDCPT[pdIndex++] = Type.CardPaiClass.HongTao;
			} else if (pdCP instanceof BingLiangCunDuan) {
				myPDCPT[pdIndex++] = Type.CardPaiClass.MeiHua;
			} else if (pdCP instanceof ShanDian) {
				myPDCPT[pdIndex++] = Type.CardPaiClass.HeiTao;
			}
		}

		// my next one PanDing
		pdIndex = 0;
		// shanDian maybe moved to nextOne
		if (this.hasSDInPanDindArea() && !this.nextOne.hasSDInPanDindArea()) {
			nextPDCPT[pdIndex++] = Type.CardPaiClass.HeiTao;
		}
		for (int i = this.nextOne.panDingPai.size() - 1; i >= 0; i--) {
			CardPai pdCP = this.nextOne.panDingPai.get(i);
			if (pdCP instanceof LeBuShiShu) {
				nextPDCPT[pdIndex++] = Type.CardPaiClass.HongTao;
			} else if (pdCP instanceof BingLiangCunDuan) {
				nextPDCPT[pdIndex++] = Type.CardPaiClass.MeiHua;
			} else if (pdCP instanceof ShanDian) {
				nextPDCPT[pdIndex++] = Type.CardPaiClass.HeiTao;
			}
		}

		// count my PanDing value one by one
		if (this.isPDOKForMe((myPDCPT[0]), arr[0])) {
			rtn += 10;
		}
		if (this.isPDOKForMe((myPDCPT[1]), arr[1])) {
			rtn += 10;
		}
		if (arr.length > 2 && this.isPDOKForMe((myPDCPT[2]), arr[2])) {
			rtn += 10;
		}

		int cpIndexForNextWJ = this.panDingPai.size() + 2;

		// count my next PanDing value one by one
		if (nextWJIsFriend) {
			if (arr.length > cpIndexForNextWJ
					&& this.isPDOKForMe((nextPDCPT[0]), arr[cpIndexForNextWJ])) {
				rtn += 9;
			}
			if (arr.length > (cpIndexForNextWJ + 1)
					&& this.isPDOKForMe((nextPDCPT[1]),
							arr[cpIndexForNextWJ + 1])) {
				rtn += 9;
			}
			if (arr.length > (cpIndexForNextWJ + 2)
					&& this.isPDOKForMe((nextPDCPT[2]),
							arr[cpIndexForNextWJ + 2])) {
				rtn += 9;
			}
		} else {
			if (arr.length > cpIndexForNextWJ
					&& this
							.isPDOKForOppt((nextPDCPT[0]),
									arr[cpIndexForNextWJ])) {
				rtn += 9;
			}
			if (arr.length > (cpIndexForNextWJ + 1)
					&& this.isPDOKForOppt((nextPDCPT[1]),
							arr[cpIndexForNextWJ + 1])) {
				rtn += 9;
			}
			if (arr.length > (cpIndexForNextWJ + 2)
					&& this.isPDOKForOppt((nextPDCPT[2]),
							arr[cpIndexForNextWJ + 2])) {
				rtn += 9;
			}
		}

		if (rtn == 0) {
			// this means there no panDing in this wj and nextOne wj
			// consider to count the card pai value itself
			if (this.blood < this.getMaxBlood()) {
				if (arr[0] instanceof Tao || arr[1] instanceof Tao)
					rtn += 8;
			}

			if (this.zhuangBei.fangJu == null) {
				if (arr[0] instanceof FangJuCardPai
						&& !(arr[1] instanceof FangJuCardPai))
					rtn += 7;
			}

			if (this.zhuangBei.wuQi == null) {
				if (arr[0] instanceof WuQiCardPai
						&& !(arr[1] instanceof WuQiCardPai))
					rtn += 6;
			}

			// count for next friend
			if (nextWJIsFriend && arr.length > 2) {
				if (this.nextOne.blood < this.nextOne.getMaxBlood()) {
					if (arr[2] instanceof Tao)
						rtn += 8;
				}

				if (this.nextOne.zhuangBei.fangJu == null) {
					if (arr[2] instanceof FangJuCardPai)
						rtn += 7;
				}

				if (this.nextOne.zhuangBei.wuQi == null) {
					if (arr[2] instanceof WuQiCardPai)
						rtn += 6;
				}
			}
		}

		return rtn;
	}

	public boolean isPDOKForMe(Type.CardPaiClass pdCPT, CardPai cp) {

		if (pdCPT == Type.CardPaiClass.HeiTao) {
			if (cp.clas != Type.CardPaiClass.HeiTao) {
				return true;
			} else {
				if (cp.number < 2 || cp.number > 9)
					return true;
			}
		}

		if (pdCPT == Type.CardPaiClass.HongTao
				&& cp.clas == Type.CardPaiClass.HongTao) {
			return true;
		}

		if (pdCPT == Type.CardPaiClass.MeiHua
				&& cp.clas == Type.CardPaiClass.MeiHua) {
			return true;
		}

		return false;
	}

	public boolean isPDOKForOppt(Type.CardPaiClass pdCPT, CardPai cp) {

		if (pdCPT == Type.CardPaiClass.HeiTao
				&& (cp.clas == Type.CardPaiClass.HeiTao && cp.number >= 2 && cp.number <= 9)) {
			return true;
		}

		if (pdCPT == Type.CardPaiClass.HongTao
				&& cp.clas != Type.CardPaiClass.HongTao) {
			return true;
		}

		if (pdCPT == Type.CardPaiClass.MeiHua
				&& cp.clas != Type.CardPaiClass.MeiHua) {
			return true;
		}

		return false;
	}
}
