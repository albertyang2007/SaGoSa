package alben.sgs.common;

import java.util.ArrayList;

import alben.sgs.android.R;
import alben.sgs.cardpai.CardPai;
import alben.sgs.type.Type;

public class Helper {

	public void sleep(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int getRandom(int max) {
		double d = Math.random();
		int r = (int) (d * max);
		return r;
	}

	// not include the min (min, max]
	public static int getRandomValueBetween(int min, int max) {
		double d = Math.random();
		int r = (int) (d * (max - min)) + min;
		if (r == min)
			r = r + 1;
		return r;
	}

	public static void randomPosition(ArrayList<Integer> positionList) {
		// xi pai
		int times = positionList.size() - 1;
		while (times != 0) {
			double d = Math.random();
			int one = (int) (d * times);
			// swap one and times
			Integer oneCP = (Integer) positionList.get(one);
			Integer timeCP = (Integer) positionList.get(times);
			positionList.set(times, oneCP);
			positionList.set(one, timeCP);
			times--;
		}
	}

	public static void emptyArrayList(ArrayList<CardPai> list) {
		while (list.size() > 0) {
			CardPai cp = (CardPai) list.get(0);
			cp.reset();
			list.remove(0);
		}
	}

	public static Type.CardPaiClass convertToCardPaiClass(String name) {
		if (name.equals("FangPian")) {
			return Type.CardPaiClass.FangPian;
		} else if (name.equals("HongTao")) {
			return Type.CardPaiClass.HongTao;
		} else if (name.equals("HeiTao")) {
			return Type.CardPaiClass.HeiTao;
		} else if (name.equals("MeiHua")) {
			return Type.CardPaiClass.MeiHua;
		}
		return Type.CardPaiClass.nil;
	}

	public static Type.State convertToWJState(String name) {
		if (name.equals("ChuPai")) {
			return Type.State.ChuPai;
		} else if (name.equals("Dead")) {
			return Type.State.Dead;
		} else if (name.equals("MoPai")) {
			return Type.State.MoPai;
		} else if (name.equals("PanDing")) {
			return Type.State.PanDing;
		} else if (name.equals("QiPai")) {
			return Type.State.QiPai;
		} else if (name.equals("Response")) {
			return Type.State.Response;
		}
		return Type.State.nil;
	}

	public static Type.Role convertToWJRole(String name) {
		if (name.equals("FanZei")) {
			return Type.Role.FanZei;
		} else if (name.equals("NeiJian")) {
			return Type.Role.NeiJian;
		} else if (name.equals("ZhongChen")) {
			return Type.Role.ZhongChen;
		} else if (name.equals("ZhuGong")) {
			return Type.Role.ZhuGong;
		}
		return Type.Role.Nil;
	}

	public static int convertToShouPaiNumberImg(int spNumber) {
		if (spNumber == 1)
			return R.drawable.number_1;
		else if (spNumber == 2)
			return R.drawable.number_2;
		else if (spNumber == 3)
			return R.drawable.number_3;
		else if (spNumber == 4)
			return R.drawable.number_4;
		else if (spNumber == 5)
			return R.drawable.number_5;
		else if (spNumber == 6)
			return R.drawable.number_6;
		else if (spNumber == 7)
			return R.drawable.number_7;
		else if (spNumber == 8)
			return R.drawable.number_8;
		else if (spNumber == 9)
			return R.drawable.number_9;
		else if (spNumber > 9)
			return R.drawable.number_9p;

		return R.drawable.number_0;
	}

	public static int convertToWJBackGroundImg(Type.Country c) {

		if (c == Type.Country.Wei)
			return R.drawable.bg_wei;
		else if (c == Type.Country.Wu)
			return R.drawable.bg_wu;
		else if (c == Type.Country.Shu)
			return R.drawable.bg_shu;
		else if (c == Type.Country.Qun)
			return R.drawable.bg_qun;
		else if (c == Type.Country.Shen)
			return R.drawable.bg_shen;

		return R.drawable.bg_default;
	}

	public static int convertToJiNengBackGroundImg(Type.Country c) {

		if (c == Type.Country.Wei)
			return R.drawable.btn_jineng_wei;
		else if (c == Type.Country.Wu)
			return R.drawable.btn_jineng_wu;
		else if (c == Type.Country.Shu)
			return R.drawable.btn_jineng_shu;
		else if (c == Type.Country.Qun)
			return R.drawable.btn_jineng_qun;
		else if (c == Type.Country.Shen)
			return R.drawable.btn_jineng_shen;

		return R.drawable.btn_jineng_qun;
	}

	public static void main(String[] args) {

	}
}
