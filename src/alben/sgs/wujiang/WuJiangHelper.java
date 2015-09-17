package alben.sgs.wujiang;

import java.util.ArrayList;

import alben.sgs.android.GameApp;
import alben.sgs.android.R;
import alben.sgs.android.dialog.SelectWuJiangDialog;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.WuQiCardPai;
import alben.sgs.cardpai.instance.BingLiangCunDuan;
import alben.sgs.cardpai.instance.LeBuShiShu;
import alben.sgs.cardpai.instance.Shan;
import alben.sgs.cardpai.instance.ShanDian;
import alben.sgs.common.Helper;
import alben.sgs.type.AuditResult;
import alben.sgs.type.Type;
import alben.sgs.type.UpdateWJViewData;
import alben.sgs.type.WuJiangNextOneData;
import alben.sgs.wujiang.instance.CaoCao;
import alben.sgs.wujiang.instance.CaoRen;
import alben.sgs.wujiang.instance.DaQiao;
import alben.sgs.wujiang.instance.DianWei;
import alben.sgs.wujiang.instance.DiaoChan;
import alben.sgs.wujiang.instance.GanNing;
import alben.sgs.wujiang.instance.GuanYu;
import alben.sgs.wujiang.instance.GuoJia;
import alben.sgs.wujiang.instance.HuaTuo;
import alben.sgs.wujiang.instance.HuangGai;
import alben.sgs.wujiang.instance.HuangYueYing;
import alben.sgs.wujiang.instance.HuangZhong;
import alben.sgs.wujiang.instance.LiuBei;
import alben.sgs.wujiang.instance.LuSun;
import alben.sgs.wujiang.instance.LvBu;
import alben.sgs.wujiang.instance.LvMeng;
import alben.sgs.wujiang.instance.MaChao;
import alben.sgs.wujiang.instance.PengDe;
import alben.sgs.wujiang.instance.PengTong;
import alben.sgs.wujiang.instance.ShiMaYi;
import alben.sgs.wujiang.instance.SunQuan;
import alben.sgs.wujiang.instance.SunShangXiang;
import alben.sgs.wujiang.instance.TaiShiChi;
import alben.sgs.wujiang.instance.WeiYuan;
import alben.sgs.wujiang.instance.WoLong;
import alben.sgs.wujiang.instance.XiaHouTing;
import alben.sgs.wujiang.instance.XiaoHouYan;
import alben.sgs.wujiang.instance.XiaoQiao;
import alben.sgs.wujiang.instance.XuZhu;
import alben.sgs.wujiang.instance.XunYu;
import alben.sgs.wujiang.instance.YanLiangWenCou;
import alben.sgs.wujiang.instance.YuanShao;
import alben.sgs.wujiang.instance.ZhangFei;
import alben.sgs.wujiang.instance.ZhangJiao;
import alben.sgs.wujiang.instance.ZhangLiao;
import alben.sgs.wujiang.instance.ZhaoYun;
import alben.sgs.wujiang.instance.ZhenJi;
import alben.sgs.wujiang.instance.ZhouYu;
import alben.sgs.wujiang.instance.ZhuGeLiang;
import android.view.View;

public class WuJiangHelper {

	public ArrayList<WuJiang> wuJiangPool = new ArrayList<WuJiang>();
	public int currentWJ = 0;
	public WuJiang[] zhuGongs = { null, null, null };

	public GameApp gameApp = null;

	public WuJiangHelper(GameApp g) {
		this.gameApp = g;
		this.addWuJiang();
		this.zhuGongs[0] = this.getWuJiangByName(Type.WuJiang.CaoCao);
		this.zhuGongs[1] = this.getWuJiangByName(Type.WuJiang.LiuBei);
		this.zhuGongs[2] = this.getWuJiangByName(Type.WuJiang.SunQuan);

		// add the UAT WJ here
		// this.zhuGongs[0] = this.getWuJiangByName(Type.WuJiang.HuaTuo);
		// this.zhuGongs[1] = this.getWuJiangByName(Type.WuJiang.DianWei);
		// this.zhuGongs[2] = this.getWuJiangByName(Type.WuJiang.DiaoChan);
	}

	public void reset() {
		this.randomlyWuJiangPool();
		this.randomlyWuJiangPool();
		this.currentWJ = 0;
	}

	public void addWuJiang() {
		// first three is zhu gong
		this.wuJiangPool.add(new CaoCao(Type.WuJiang.CaoCao, Type.Country.Wei,
				Type.Sex.man, 4));
		this.wuJiangPool.add(new SunQuan(Type.WuJiang.SunQuan, Type.Country.Wu,
				Type.Sex.man, 4));
		this.wuJiangPool.add(new LiuBei(Type.WuJiang.LiuBei, Type.Country.Shu,
				Type.Sex.man, 4));
		//
		this.wuJiangPool.add(new DiaoChan(Type.WuJiang.DiaoChan,
				Type.Country.Qun, Type.Sex.woman, 3));
		this.wuJiangPool.add(new HuaTuo(Type.WuJiang.HuaTuo, Type.Country.Qun,
				Type.Sex.man, 3));
		this.wuJiangPool.add(new LvBu(Type.WuJiang.LvBu, Type.Country.Qun,
				Type.Sex.man, 4));
		this.wuJiangPool.add(new GuanYu(Type.WuJiang.GuanYu, Type.Country.Shu,
				Type.Sex.man, 4));
		this.wuJiangPool.add(new HuangYueYing(Type.WuJiang.HuangYueYing,
				Type.Country.Shu, Type.Sex.woman, 3));
		this.wuJiangPool.add(new MaChao(Type.WuJiang.MaChao, Type.Country.Shu,
				Type.Sex.man, 4));
		this.wuJiangPool.add(new ZhangFei(Type.WuJiang.ZhangFei,
				Type.Country.Shu, Type.Sex.man, 4));
		this.wuJiangPool.add(new ZhaoYun(Type.WuJiang.ZhaoYun,
				Type.Country.Shu, Type.Sex.man, 4));
		this.wuJiangPool.add(new ZhuGeLiang(Type.WuJiang.ZhuGeLiang,
				Type.Country.Shu, Type.Sex.man, 3));
		this.wuJiangPool.add(new GuoJia(Type.WuJiang.GuoJia, Type.Country.Wei,
				Type.Sex.man, 3));
		this.wuJiangPool.add(new ShiMaYi(Type.WuJiang.ShiMaYi,
				Type.Country.Wei, Type.Sex.man, 3));
		this.wuJiangPool.add(new XiaHouTing(Type.WuJiang.XiaHouTing,
				Type.Country.Wei, Type.Sex.man, 4));
		this.wuJiangPool.add(new XuZhu(Type.WuJiang.XuZhu, Type.Country.Wei,
				Type.Sex.man, 4));
		this.wuJiangPool.add(new ZhangLiao(Type.WuJiang.ZhangLiao,
				Type.Country.Wei, Type.Sex.man, 4));
		this.wuJiangPool.add(new ZhenJi(Type.WuJiang.ZhenJi, Type.Country.Wei,
				Type.Sex.woman, 3));
		this.wuJiangPool.add(new DaQiao(Type.WuJiang.DaQiao, Type.Country.Wu,
				Type.Sex.woman, 3));
		this.wuJiangPool.add(new GanNing(Type.WuJiang.GanNing, Type.Country.Wu,
				Type.Sex.man, 4));
		this.wuJiangPool.add(new HuangGai(Type.WuJiang.HuangGai,
				Type.Country.Wu, Type.Sex.man, 4));
		this.wuJiangPool.add(new LuSun(Type.WuJiang.LuSun, Type.Country.Wu,
				Type.Sex.man, 3));
		this.wuJiangPool.add(new LvMeng(Type.WuJiang.LvMeng, Type.Country.Wu,
				Type.Sex.man, 4));
		this.wuJiangPool.add(new SunShangXiang(Type.WuJiang.SunShangXiang,
				Type.Country.Wu, Type.Sex.woman, 3));
		this.wuJiangPool.add(new ZhouYu(Type.WuJiang.ZhouYu, Type.Country.Wu,
				Type.Sex.man, 3));
		this.wuJiangPool.add(new ZhangJiao(Type.WuJiang.ZhangJiao,
				Type.Country.Qun, Type.Sex.man, 3));
		this.wuJiangPool.add(new HuangZhong(Type.WuJiang.HuangZhong,
				Type.Country.Shu, Type.Sex.man, 4));
		this.wuJiangPool.add(new WeiYuan(Type.WuJiang.WeiYuan,
				Type.Country.Shu, Type.Sex.man, 4));
		this.wuJiangPool.add(new CaoRen(Type.WuJiang.CaoRen, Type.Country.Wei,
				Type.Sex.man, 4));
		this.wuJiangPool.add(new XiaoHouYan(Type.WuJiang.XiaHouYan,
				Type.Country.Wei, Type.Sex.man, 4));
		this.wuJiangPool.add(new XiaoQiao(Type.WuJiang.XiaoQiao,
				Type.Country.Wu, Type.Sex.woman, 3));
		this.wuJiangPool.add(new PengDe(Type.WuJiang.PengDe, Type.Country.Qun,
				Type.Sex.man, 4));
		this.wuJiangPool.add(new YanLiangWenCou(Type.WuJiang.YanLiangWenCou,
				Type.Country.Qun, Type.Sex.man, 4));
		this.wuJiangPool.add(new YuanShao(Type.WuJiang.YuanShao,
				Type.Country.Qun, Type.Sex.man, 4));
		this.wuJiangPool.add(new PengTong(Type.WuJiang.PengTong,
				Type.Country.Shu, Type.Sex.man, 3));
		this.wuJiangPool.add(new WoLong(Type.WuJiang.WoLong, Type.Country.Shu,
				Type.Sex.man, 3));
		this.wuJiangPool.add(new DianWei(Type.WuJiang.DianWei,
				Type.Country.Wei, Type.Sex.man, 4));
		this.wuJiangPool.add(new XunYu(Type.WuJiang.XunYu, Type.Country.Wei,
				Type.Sex.man, 3));
		this.wuJiangPool.add(new TaiShiChi(Type.WuJiang.TaiShiChi,
				Type.Country.Wu, Type.Sex.man, 4));
	}

	// let wu jiang pool randomly for selection
	public void randomlyWuJiangPool() {
		int times = this.wuJiangPool.size() - 1;
		while (times != 0) {
			double d = Math.random();
			int one = (int) (d * times);
			// swap one and times
			WuJiang oneWJ = (WuJiang) this.wuJiangPool.get(one);
			WuJiang timeWJ = (WuJiang) this.wuJiangPool.get(times);
			this.wuJiangPool.set(times, oneWJ);
			this.wuJiangPool.set(one, timeWJ);
			times--;
		}
	}

	public WuJiang getWuJiangByName(Type.WuJiang name) {
		for (int i = 0; i < this.wuJiangPool.size(); i++) {
			WuJiang wj = this.wuJiangPool.get(i);
			if (name == wj.name) {
				return wj;
			}
		}
		return null;
	}

	public WuJiang getWuJiangByStrName(String name) {
		for (int i = 0; i < this.wuJiangPool.size(); i++) {
			WuJiang wj = this.wuJiangPool.get(i);
			if (name.equals(wj.name.toString())) {
				return wj;
			}
		}
		return null;
	}

	public WuJiang getWuJiangByIndex(int index) {
		return this.wuJiangPool.get(index);
	}

	public WuJiang getWuJiangByImageViewIndex(int index) {
		for (int i = 0; i < this.gameApp.gameLogicData.wuJiangs.size(); i++) {
			WuJiang wj = this.gameApp.gameLogicData.wuJiangs.get(i);
			if (wj.imageViewIndex == index) {
				return wj;
			}
		}
		return null;
	}

	public WuJiang getWuJiangByImageViewIndex(int index, WuJiang[] wjs) {
		for (int i = 0; i < wjs.length; i++) {
			if (wjs[i].imageViewIndex == index)
				return wjs[i];
		}
		return null;
	}

	public WuJiang getWuJiangByImageViewIndex(int index,
			ArrayList<WuJiang> wjList) {
		for (int i = 0; i < wjList.size(); i++) {
			if (wjList.get(i).imageViewIndex == index)
				return wjList.get(i);
		}
		return null;
	}

	public WuJiang popOneWuJiang() {
		if (this.currentWJ >= (this.wuJiangPool.size() - 5)) {
			this.currentWJ = 0;
		}
		return this.wuJiangPool.get(this.currentWJ++);
	}

	// return a non-allocated wj by random
	public WuJiang getWuJiangByRandom() {
		WuJiang wj = null;
		boolean find = false;
		while (!find) {
			wj = this.popOneWuJiang();
			if (!wj.allocated) {
				find = true;
			}
		}
		return wj;
	}

	public WuJiang getRunningWuJiangByRole(WuJiang startWJ, Type.Role role) {
		WuJiang tarWJ = startWJ.nextOne;
		while (!tarWJ.equals(startWJ)) {
			if (tarWJ.role == role) {
				return tarWJ;
			}
			tarWJ = tarWJ.nextOne;
		}
		return null;
	}

	public WuJiang getRunningWuJiangByName(WuJiang startWJ, Type.WuJiang name) {
		WuJiang tarWJ = startWJ.nextOne;
		while (!tarWJ.equals(startWJ)) {
			if (tarWJ.name == name) {
				return tarWJ;
			}
			tarWJ = tarWJ.nextOne;
		}
		return null;
	}

	// two people: 1v1
	public void allocateRoleFor2() {
		// reset the wujiang pool to an randomly
		this.reset();
		this.gameApp.selectWJViewData.reset();

		// first: allocate role
		// hardcode here for test
		// second: select two Wu Jiang
		WuJiang wj1 = null;
		WuJiang wj2 = null;

		UpdateWJViewData item = new UpdateWJViewData();
		item.updateAll = true;

		int wjPosition = 2;

		int ran = Helper.getRandom(200);
		if (ran % 2 == 0) {
			// I am zhuGong
			this.allocateZhuGongForMyWuJiang();
			this.gameApp.gameLogicData.zhuGongWuJiang = this.gameApp.gameLogicData.myWuJiang;
			// fan zei select wujiang by random

			wj1 = this.gameApp.gameLogicData.myWuJiang;
			wj2 = this.allocateWuJiangRandomly(wjPosition, Type.Role.FanZei);

		} else {
			// random allocate wj for zhugong first
			WuJiang zhuGongWJ = this.allocateWuJiangRandomly(wjPosition,
					Type.Role.ZhuGong);
			this.gameApp.gameLogicData.zhuGongWuJiang = zhuGongWJ;

			// I am fanzei
			this.allocateRoleForMyWuJiang(Type.Role.FanZei);

			wj1 = zhuGongWJ;
			wj2 = this.gameApp.gameLogicData.myWuJiang;
		}
		// set nextone
		wj1.nextOne = wj2;
		wj2.nextOne = wj1;
		// set preone
		wj1.preOne = wj2;
		wj2.preOne = wj1;

		// set game
		wj1.setGame(this.gameApp);
		wj2.setGame(this.gameApp);

		// set friend or opponent
		wj1.opponentList.add(wj2);
		wj2.opponentList.add(wj1);

		this.gameApp.gameLogicData.wuJiangs.add(wj1);
		this.gameApp.gameLogicData.wuJiangs.add(wj2);

		// set zhugong maxblood
		this.gameApp.gameLogicData.zhuGongMaxBlood = this.gameApp.gameLogicData.zhuGongWuJiang
				.getOrigMaxBlood();
		this.gameApp.gameLogicData.zhuGongWuJiang.blood = this.gameApp.gameLogicData.zhuGongMaxBlood;

		// update view
		this.updateAllWuJiangToLibGameView();

		// reset this
		this.gameApp.selectWJViewData.reset();
	}

	// two people: 5 people
	public void allocateRoleFor5() {
		// reset the wujiang pool to an randomly
		this.reset();
		this.gameApp.selectWJViewData.reset();

		// first: allocate role
		// hardcode here for test
		// second: select two Wu Jiang
		WuJiang[] wjs = { null, null, null, null, null };

		ArrayList<Integer> imageViewPosition = new ArrayList<Integer>();
		imageViewPosition.add(new Integer(1));
		imageViewPosition.add(new Integer(3));
		imageViewPosition.add(new Integer(5));
		imageViewPosition.add(new Integer(6));
		// imageViewIndex 7 is for MyWuJiang
		Helper.randomPosition(imageViewPosition);

		UpdateWJViewData item = new UpdateWJViewData();
		item.updateImage = true;
		item.updateRole = true;

		int ran = Helper.getRandom(500);
		if (ran % 4 == 0) {
			// I am zhuGong
			this.allocateZhuGongForMyWuJiang();
			this.gameApp.gameLogicData.zhuGongWuJiang = this.gameApp.gameLogicData.myWuJiang;
			wjs[0] = this.gameApp.gameLogicData.myWuJiang;
			// update zhuGong to view
			this.updateWuJiangToLibGameView(
					this.gameApp.gameLogicData.zhuGongWuJiang, item);

			// fan zei 1 select wujiang by random
			int imageIndex = imageViewPosition.get(0).intValue();
			imageViewPosition.remove(0);
			wjs[1] = this.allocateWuJiangRandomly(imageIndex, Type.Role.FanZei);

			// fan zei 2 select wujiang by random
			imageIndex = imageViewPosition.get(0).intValue();
			imageViewPosition.remove(0);
			wjs[2] = this.allocateWuJiangRandomly(imageIndex, Type.Role.FanZei);

			// zhong chen 1 select wujiang by random
			imageIndex = imageViewPosition.get(0).intValue();
			imageViewPosition.remove(0);
			wjs[3] = this.allocateWuJiangRandomly(imageIndex,
					Type.Role.ZhongChen);

			// nei jian 1 select wujiang by random
			imageIndex = imageViewPosition.get(0).intValue();
			imageViewPosition.remove(0);
			wjs[4] = this
					.allocateWuJiangRandomly(imageIndex, Type.Role.NeiJian);

		} else if (ran % 4 == 1) {
			// random allocate wj for zhugong first
			int imageIndex = imageViewPosition.get(0).intValue();
			imageViewPosition.remove(0);

			WuJiang zhuGongWJ = this.allocateWuJiangRandomly(imageIndex,
					Type.Role.ZhuGong);
			this.gameApp.gameLogicData.zhuGongWuJiang = zhuGongWJ;
			wjs[0] = zhuGongWJ;
			// update zhuGong to view
			this.updateWuJiangToLibGameView(
					this.gameApp.gameLogicData.zhuGongWuJiang, item);

			gameApp.libGameViewData.logInfo(
					"主公选择" + zhuGongWJ.dispName + "作武将", Type.logDelay.Delay);

			// I am zhongChen
			this.allocateRoleForMyWuJiang(Type.Role.ZhongChen);
			wjs[1] = this.gameApp.gameLogicData.myWuJiang;

			// fan zei 1 select wujiang by random
			imageIndex = imageViewPosition.get(0).intValue();
			imageViewPosition.remove(0);
			wjs[2] = this.allocateWuJiangRandomly(imageIndex, Type.Role.FanZei);

			// fan zei 2 select wujiang by random
			imageIndex = imageViewPosition.get(0).intValue();
			imageViewPosition.remove(0);
			wjs[3] = this.allocateWuJiangRandomly(imageIndex, Type.Role.FanZei);

			// nei jian 1 select wujiang by random
			imageIndex = imageViewPosition.get(0).intValue();
			imageViewPosition.remove(0);
			wjs[4] = this
					.allocateWuJiangRandomly(imageIndex, Type.Role.NeiJian);

		} else if (ran % 4 == 2) {
			// random allocate wj for zhugong first
			int imageIndex = imageViewPosition.get(0).intValue();
			imageViewPosition.remove(0);

			WuJiang zhuGongWJ = this.allocateWuJiangRandomly(imageIndex,
					Type.Role.ZhuGong);
			this.gameApp.gameLogicData.zhuGongWuJiang = zhuGongWJ;
			wjs[0] = zhuGongWJ;
			// update zhuGong to view
			this.updateWuJiangToLibGameView(
					this.gameApp.gameLogicData.zhuGongWuJiang, item);
			gameApp.libGameViewData.logInfo("主公选择了" + zhuGongWJ.dispName
					+ "作武将", Type.logDelay.Delay);

			// I am fanzei
			this.allocateRoleForMyWuJiang(Type.Role.FanZei);
			wjs[1] = this.gameApp.gameLogicData.myWuJiang;

			// fan zei 2 select wujiang by random
			imageIndex = imageViewPosition.get(0).intValue();
			imageViewPosition.remove(0);
			wjs[2] = this.allocateWuJiangRandomly(imageIndex, Type.Role.FanZei);

			// zhong chen 1 select wujiang by random
			imageIndex = imageViewPosition.get(0).intValue();
			imageViewPosition.remove(0);
			wjs[3] = this.allocateWuJiangRandomly(imageIndex,
					Type.Role.ZhongChen);

			// nei jian 1 select wujiang by random
			imageIndex = imageViewPosition.get(0).intValue();
			imageViewPosition.remove(0);
			wjs[4] = this
					.allocateWuJiangRandomly(imageIndex, Type.Role.NeiJian);

		} else if (ran % 4 == 3) {
			// random allocate wj for zhugong first
			int imageIndex = imageViewPosition.get(0).intValue();
			imageViewPosition.remove(0);

			WuJiang zhuGongWJ = this.allocateWuJiangRandomly(imageIndex,
					Type.Role.ZhuGong);
			this.gameApp.gameLogicData.zhuGongWuJiang = zhuGongWJ;
			wjs[0] = zhuGongWJ;
			// update zhuGong to view
			this.updateWuJiangToLibGameView(
					this.gameApp.gameLogicData.zhuGongWuJiang, item);
			gameApp.libGameViewData.logInfo("主公选择了" + zhuGongWJ.dispName
					+ "作武将", Type.logDelay.Delay);

			// I am neijian
			this.allocateRoleForMyWuJiang(Type.Role.NeiJian);
			wjs[1] = this.gameApp.gameLogicData.myWuJiang;

			// fan zei 1 select wujiang by random
			imageIndex = imageViewPosition.get(0).intValue();
			imageViewPosition.remove(0);
			wjs[2] = this.allocateWuJiangRandomly(imageIndex, Type.Role.FanZei);

			// fan zei 2 select wujiang by random
			imageIndex = imageViewPosition.get(0).intValue();
			imageViewPosition.remove(0);
			wjs[3] = this.allocateWuJiangRandomly(imageIndex, Type.Role.FanZei);

			// zhong chen 1 select wujiang by random
			imageIndex = imageViewPosition.get(0).intValue();
			imageViewPosition.remove(0);
			wjs[4] = this.allocateWuJiangRandomly(imageIndex,
					Type.Role.ZhongChen);
		}

		// set game
		for (int i = 0; i < wjs.length; i++) {
			wjs[i].setGame(this.gameApp);
		}

		// add them to logic data
		for (int i = 0; i < wjs.length; i++) {
			this.gameApp.gameLogicData.wuJiangs.add(wjs[i]);
		}

		// set nextone wj
		for (int i = 0; i < this.gameApp.gameLogicData.wuJiangs.size(); i++) {
			WuJiang wj = this.gameApp.gameLogicData.wuJiangs.get(i);
			// set nextone wj
			wj.nextOne = this.gameApp.gameLogicData.wjHelper
					.findNextOne(wj.imageViewIndex);
			wj.preOne = this.gameApp.gameLogicData.wjHelper
					.findPreOne(wj.imageViewIndex);
		}

		// set zhugong maxblood
		this.gameApp.gameLogicData.zhuGongMaxBlood = this.gameApp.gameLogicData.zhuGongWuJiang
				.getOrigMaxBlood() + 1;
		this.gameApp.gameLogicData.zhuGongWuJiang.blood = this.gameApp.gameLogicData.zhuGongMaxBlood;

		// set friend or opponent
		this.constructFriOpptList();

		// update view
		this.updateAllWuJiangToLibGameView();

		// reset this
		this.gameApp.selectWJViewData.reset();
	}

	public void allocateRoleFor81() {

		// reset the wujiang pool to an randomly
		this.reset();
		this.gameApp.selectWJViewData.reset();

		// first: allocate role
		// hardcode here for test
		// second: select two Wu Jiang
		WuJiang[] wjs = { null, null, null, null, null, null, null, null };

		ArrayList<Integer> imageViewPosition = new ArrayList<Integer>();
		imageViewPosition.add(new Integer(0));
		imageViewPosition.add(new Integer(1));
		imageViewPosition.add(new Integer(2));
		imageViewPosition.add(new Integer(3));
		imageViewPosition.add(new Integer(4));
		imageViewPosition.add(new Integer(5));
		imageViewPosition.add(new Integer(6));
		// imageViewIndex 7 is or MyWuJiang
		Helper.randomPosition(imageViewPosition);

		UpdateWJViewData item = new UpdateWJViewData();
		item.updateImage = true;
		item.updateRole = true;

		int ran = Helper.getRandom(800);
		if (ran % 4 == 0) {
			// I am zhuGong
			this.allocateZhuGongForMyWuJiang();
			this.gameApp.gameLogicData.zhuGongWuJiang = this.gameApp.gameLogicData.myWuJiang;
			wjs[0] = this.gameApp.gameLogicData.myWuJiang;
			// update zhuGong to view
			this.updateWuJiangToLibGameView(
					this.gameApp.gameLogicData.zhuGongWuJiang, item);

			// fan zei 1 select wujiang by random
			int imageIndex = imageViewPosition.get(0).intValue();
			imageViewPosition.remove(0);
			wjs[1] = this.allocateWuJiangRandomly(imageIndex, Type.Role.FanZei);

			// fan zei 2 select wujiang by random
			imageIndex = imageViewPosition.get(0).intValue();
			imageViewPosition.remove(0);
			wjs[2] = this.allocateWuJiangRandomly(imageIndex, Type.Role.FanZei);

			// zhong chen 1 select wujiang by random
			imageIndex = imageViewPosition.get(0).intValue();
			imageViewPosition.remove(0);
			wjs[3] = this.allocateWuJiangRandomly(imageIndex,
					Type.Role.ZhongChen);

			// nei jian 1 select wujiang by random
			imageIndex = imageViewPosition.get(0).intValue();
			imageViewPosition.remove(0);
			wjs[4] = this
					.allocateWuJiangRandomly(imageIndex, Type.Role.NeiJian);

			// fan zei 3 select wujiang by random
			imageIndex = imageViewPosition.get(0).intValue();
			imageViewPosition.remove(0);
			wjs[5] = this.allocateWuJiangRandomly(imageIndex, Type.Role.FanZei);

			// fan zei 4 select wujiang by random
			imageIndex = imageViewPosition.get(0).intValue();
			imageViewPosition.remove(0);
			wjs[6] = this.allocateWuJiangRandomly(imageIndex, Type.Role.FanZei);

			// zhong chen 2 select wujiang by random
			imageIndex = imageViewPosition.get(0).intValue();
			imageViewPosition.remove(0);
			wjs[7] = this.allocateWuJiangRandomly(imageIndex,
					Type.Role.ZhongChen);

		} else if (ran % 4 == 1) {
			// random allocate wj for zhugong first
			int imageIndex = imageViewPosition.get(0).intValue();
			imageViewPosition.remove(0);

			WuJiang zhuGongWJ = this.allocateWuJiangRandomly(imageIndex,
					Type.Role.ZhuGong);
			this.gameApp.gameLogicData.zhuGongWuJiang = zhuGongWJ;
			wjs[0] = zhuGongWJ;
			// update zhuGong to view
			this.updateWuJiangToLibGameView(
					this.gameApp.gameLogicData.zhuGongWuJiang, item);
			gameApp.libGameViewData.logInfo(
					"主公选择" + zhuGongWJ.dispName + "作武将", Type.logDelay.Delay);

			// I am zhongChen
			this.allocateRoleForMyWuJiang(Type.Role.ZhongChen);
			wjs[1] = this.gameApp.gameLogicData.myWuJiang;

			// fan zei 1 select wujiang by random
			imageIndex = imageViewPosition.get(0).intValue();
			imageViewPosition.remove(0);
			wjs[2] = this.allocateWuJiangRandomly(imageIndex, Type.Role.FanZei);

			// fan zei 2 select wujiang by random
			imageIndex = imageViewPosition.get(0).intValue();
			imageViewPosition.remove(0);
			wjs[3] = this.allocateWuJiangRandomly(imageIndex, Type.Role.FanZei);

			// nei jian 1 select wujiang by random
			imageIndex = imageViewPosition.get(0).intValue();
			imageViewPosition.remove(0);
			wjs[4] = this
					.allocateWuJiangRandomly(imageIndex, Type.Role.NeiJian);

			// fan zei 3 select wujiang by random
			imageIndex = imageViewPosition.get(0).intValue();
			imageViewPosition.remove(0);
			wjs[5] = this.allocateWuJiangRandomly(imageIndex, Type.Role.FanZei);

			// fan zei 4 select wujiang by random
			imageIndex = imageViewPosition.get(0).intValue();
			imageViewPosition.remove(0);
			wjs[6] = this.allocateWuJiangRandomly(imageIndex, Type.Role.FanZei);

			// zhong chen 2 select wujiang by random
			imageIndex = imageViewPosition.get(0).intValue();
			imageViewPosition.remove(0);
			wjs[7] = this.allocateWuJiangRandomly(imageIndex,
					Type.Role.ZhongChen);

		} else if (ran % 4 == 2) {
			// random allocate wj for zhugong first
			int imageIndex = imageViewPosition.get(0).intValue();
			imageViewPosition.remove(0);

			WuJiang zhuGongWJ = this.allocateWuJiangRandomly(imageIndex,
					Type.Role.ZhuGong);
			this.gameApp.gameLogicData.zhuGongWuJiang = zhuGongWJ;
			wjs[0] = zhuGongWJ;
			// update zhuGong to view
			this.updateWuJiangToLibGameView(
					this.gameApp.gameLogicData.zhuGongWuJiang, item);
			gameApp.libGameViewData.logInfo("主公选择了" + zhuGongWJ.dispName
					+ "作武将", Type.logDelay.Delay);

			// I am fanzei
			this.allocateRoleForMyWuJiang(Type.Role.FanZei);
			wjs[1] = this.gameApp.gameLogicData.myWuJiang;

			// fan zei 2 select wujiang by random
			imageIndex = imageViewPosition.get(0).intValue();
			imageViewPosition.remove(0);
			wjs[2] = this.allocateWuJiangRandomly(imageIndex, Type.Role.FanZei);

			// zhong chen 1 select wujiang by random
			imageIndex = imageViewPosition.get(0).intValue();
			imageViewPosition.remove(0);
			wjs[3] = this.allocateWuJiangRandomly(imageIndex,
					Type.Role.ZhongChen);

			// nei jian 1 select wujiang by random
			imageIndex = imageViewPosition.get(0).intValue();
			imageViewPosition.remove(0);
			wjs[4] = this
					.allocateWuJiangRandomly(imageIndex, Type.Role.NeiJian);

			// fan zei 3 select wujiang by random
			imageIndex = imageViewPosition.get(0).intValue();
			imageViewPosition.remove(0);
			wjs[5] = this.allocateWuJiangRandomly(imageIndex, Type.Role.FanZei);

			// fan zei 4 select wujiang by random
			imageIndex = imageViewPosition.get(0).intValue();
			imageViewPosition.remove(0);
			wjs[6] = this.allocateWuJiangRandomly(imageIndex, Type.Role.FanZei);

			// zhong chen 2 select wujiang by random
			imageIndex = imageViewPosition.get(0).intValue();
			imageViewPosition.remove(0);
			wjs[7] = this.allocateWuJiangRandomly(imageIndex,
					Type.Role.ZhongChen);

		} else if (ran % 4 == 3) {
			// random allocate wj for zhugong first
			int imageIndex = imageViewPosition.get(0).intValue();
			imageViewPosition.remove(0);

			WuJiang zhuGongWJ = this.allocateWuJiangRandomly(imageIndex,
					Type.Role.ZhuGong);
			this.gameApp.gameLogicData.zhuGongWuJiang = zhuGongWJ;
			wjs[0] = zhuGongWJ;
			// update zhuGong to view
			this.updateWuJiangToLibGameView(
					this.gameApp.gameLogicData.zhuGongWuJiang, item);
			gameApp.libGameViewData.logInfo("主公选择了" + zhuGongWJ.dispName
					+ "作武将", Type.logDelay.Delay);

			// I am neijian
			this.allocateRoleForMyWuJiang(Type.Role.NeiJian);
			wjs[1] = this.gameApp.gameLogicData.myWuJiang;

			// fan zei 1 select wujiang by random
			imageIndex = imageViewPosition.get(0).intValue();
			imageViewPosition.remove(0);
			wjs[2] = this.allocateWuJiangRandomly(imageIndex, Type.Role.FanZei);

			// fan zei 2 select wujiang by random
			imageIndex = imageViewPosition.get(0).intValue();
			imageViewPosition.remove(0);
			wjs[3] = this.allocateWuJiangRandomly(imageIndex, Type.Role.FanZei);

			// zhong chen 1 select wujiang by random
			imageIndex = imageViewPosition.get(0).intValue();
			imageViewPosition.remove(0);
			wjs[4] = this.allocateWuJiangRandomly(imageIndex,
					Type.Role.ZhongChen);

			// fan zei 3 select wujiang by random
			imageIndex = imageViewPosition.get(0).intValue();
			imageViewPosition.remove(0);
			wjs[5] = this.allocateWuJiangRandomly(imageIndex, Type.Role.FanZei);

			// fan zei 4 select wujiang by random
			imageIndex = imageViewPosition.get(0).intValue();
			imageViewPosition.remove(0);
			wjs[6] = this.allocateWuJiangRandomly(imageIndex, Type.Role.FanZei);

			// zhong chen 2 select wujiang by random
			imageIndex = imageViewPosition.get(0).intValue();
			imageViewPosition.remove(0);
			wjs[7] = this.allocateWuJiangRandomly(imageIndex,
					Type.Role.ZhongChen);
		}

		// set game
		for (int i = 0; i < wjs.length; i++) {
			wjs[i].setGame(this.gameApp);
		}

		// add them to logic data
		for (int i = 0; i < wjs.length; i++) {
			this.gameApp.gameLogicData.wuJiangs.add(wjs[i]);
		}

		// set nextone wj
		for (int i = 0; i < this.gameApp.gameLogicData.wuJiangs.size(); i++) {
			WuJiang wj = this.gameApp.gameLogicData.wuJiangs.get(i);
			// set nextone wj
			wj.nextOne = this.gameApp.gameLogicData.wjHelper
					.findNextOne(wj.imageViewIndex);
			wj.preOne = this.gameApp.gameLogicData.wjHelper
					.findPreOne(wj.imageViewIndex);
		}

		// set zhugong maxblood
		this.gameApp.gameLogicData.zhuGongMaxBlood = this.gameApp.gameLogicData.zhuGongWuJiang
				.getOrigMaxBlood() + 1;
		this.gameApp.gameLogicData.zhuGongWuJiang.blood = this.gameApp.gameLogicData.zhuGongMaxBlood;

		// set friend or opponent
		this.constructFriOpptList();

		// update view
		this.updateAllWuJiangToLibGameView();

		// reset this
		this.gameApp.selectWJViewData.reset();
	}

	public void updateAllWuJiangToLibGameView() {

		UpdateWJViewData item = new UpdateWJViewData();
		item.updateAll = true;

		WuJiang zhuGong = this.gameApp.gameLogicData.wjHelper
				.getZhuGong(this.gameApp.gameLogicData.wuJiangs);
		WuJiang curWJ = zhuGong;
		this.updateWuJiangToLibGameView(curWJ, item);
		this.gameApp.libGameViewData.logInfo(curWJ.dispName + "主公就位",
				Type.logDelay.NoDelay);
		curWJ = curWJ.nextOne;
		while (!curWJ.equals(zhuGong)) {
			this.updateWuJiangToLibGameView(curWJ, item);
			this.gameApp.libGameViewData.logInfo(curWJ.dispName + "武将就位",
					Type.logDelay.NoDelay);
			curWJ = curWJ.nextOne;
		}
	}

	// allocate zhugong for me
	public void allocateZhuGongForMyWuJiang() {
		// UpdateWJViewData item = new UpdateWJViewData();
		// item.updateAll = true;

		this.gameApp.gameLogicData.myRole = Type.Role.ZhuGong;

		gameApp.libGameViewData.logInfo("你的身份是主公", Type.logDelay.Delay);

		// I am zhugong, select wu jiang
		// pos 8 wu jiang for your choice
		// first three are zhu gong
		this.gameApp.selectWJViewData.wuJiangs[0] = this.zhuGongs[0];
		this.gameApp.selectWJViewData.wuJiangs[1] = this.zhuGongs[1];
		this.gameApp.selectWJViewData.wuJiangs[2] = this.zhuGongs[2];

		// set allocated to avoid others to select it
		this.zhuGongs[0].allocated = true;
		this.zhuGongs[1].allocated = true;
		this.zhuGongs[2].allocated = true;

		// random select others
		this.gameApp.selectWJViewData.wuJiangs[3] = this.getWuJiangByRandom();
		this.gameApp.selectWJViewData.wuJiangs[4] = this.getWuJiangByRandom();
		this.gameApp.selectWJViewData.wuJiangs[5] = this.getWuJiangByRandom();
		this.gameApp.selectWJViewData.wuJiangs[6] = this.getWuJiangByRandom();
		this.gameApp.selectWJViewData.wuJiangs[7] = this.getWuJiangByRandom();

		// reset for fanzei to chose
		this.zhuGongs[0].allocated = false;
		this.zhuGongs[1].allocated = false;
		this.zhuGongs[2].allocated = false;

		SelectWuJiangDialog selDlg = new SelectWuJiangDialog(
				this.gameApp.gameActivityContext, this.gameApp);
		selDlg.showDialog();

		this.gameApp.gameLogicData.myWuJiang = this.gameApp.selectWJViewData.selectedWJ1;

		this.gameApp.gameLogicData.myWuJiang.setRole(Type.Role.ZhuGong);

		this.gameApp.gameLogicData.myWuJiang.allocated = true;
		this.gameApp.gameLogicData.myWuJiang.tuoGuan = false;
		this.gameApp.gameLogicData.myWuJiang.state = Type.State.Response;

		this.gameApp.libGameViewData.logInfo("你选择了"
				+ this.gameApp.gameLogicData.myWuJiang.dispName + "作为武将",
				Type.logDelay.Delay);

		this.gameApp.gameLogicData.myWuJiang.imageViewIndex = 7;
		// this.updateWuJiangToLibGameView(this.gameApp.gameLogicData.myWuJiang,
		// item);
	}

	// allocate fanzei for me
	public void allocateRoleForMyWuJiang(Type.Role role) {
		// UpdateWJViewData item = new UpdateWJViewData();
		// item.updateAll = true;

		String s = "Error:";
		if (role == Type.Role.ZhuGong)
			s = "主公";
		else if (role == Type.Role.FanZei)
			s = "反贼";
		else if (role == Type.Role.ZhongChen)
			s = "忠臣";
		else if (role == Type.Role.NeiJian)
			s = "内奸";

		this.gameApp.gameLogicData.myRole = role;
		gameApp.libGameViewData.logInfo("你的身份是" + s, Type.logDelay.Delay);

		// I am fanzei, select wu jiang
		// pos 8 wu jiang for your choice
		// random select
		this.gameApp.selectWJViewData.wuJiangs[0] = this.getWuJiangByRandom();
		this.gameApp.selectWJViewData.wuJiangs[1] = this.getWuJiangByRandom();
		this.gameApp.selectWJViewData.wuJiangs[2] = this.getWuJiangByRandom();
		this.gameApp.selectWJViewData.wuJiangs[3] = this.getWuJiangByRandom();
		this.gameApp.selectWJViewData.wuJiangs[4] = this.getWuJiangByRandom();
		this.gameApp.selectWJViewData.wuJiangs[5] = this.getWuJiangByRandom();
		this.gameApp.selectWJViewData.wuJiangs[6] = this.getWuJiangByRandom();
		this.gameApp.selectWJViewData.wuJiangs[7] = this.getWuJiangByRandom();

		SelectWuJiangDialog selDlg = new SelectWuJiangDialog(
				this.gameApp.gameActivityContext, this.gameApp);
		selDlg.showDialog();

		this.gameApp.gameLogicData.myWuJiang = this.gameApp.selectWJViewData.selectedWJ1;

		this.gameApp.gameLogicData.myWuJiang.setRole(role);
		this.gameApp.gameLogicData.myWuJiang.allocated = true;
		this.gameApp.gameLogicData.myWuJiang.tuoGuan = false;
		this.gameApp.gameLogicData.myWuJiang.state = Type.State.Response;

		this.gameApp.libGameViewData.logInfo("你选择了"
				+ this.gameApp.gameLogicData.myWuJiang.dispName + "作为武将",
				Type.logDelay.Delay);

		this.gameApp.gameLogicData.myWuJiang.imageViewIndex = 7;
		// this.updateWuJiangToLibGameView(this.gameApp.gameLogicData.myWuJiang,
		// item);
	}

	public WuJiang allocateWuJiangRandomly(int imageViewIndex, Type.Role role) {
		// UpdateWJViewData item = new UpdateWJViewData();
		// item.updateAll = true;

		WuJiang fanZeiWJ = this.getWuJiangByRandom();
		fanZeiWJ.setRole(role);
		fanZeiWJ.allocated = true;
		fanZeiWJ.state = Type.State.Response;

		fanZeiWJ.imageViewIndex = imageViewIndex;
		// this.updateWuJiangToLibGameView(fanZeiWJ, item);

		return fanZeiWJ;
	}

	public WuJiang getZhuGong(ArrayList<WuJiang> wuJiangs) {
		for (int i = 0; i < wuJiangs.size(); i++) {
			WuJiang wj = wuJiangs.get(i);
			if (wj.role == Type.Role.ZhuGong)
				return wj;
		}
		return null;
	}

	public Type.Relationship checkRelationship(WuJiang wj1, WuJiang wj2) {
		if (wj1.role == Type.Role.ZhuGong) {
			if (wj2.role == Type.Role.ZhongChen) {
				return Type.Relationship.friend;
			} else if (wj2.role == Type.Role.NeiJian) {
				return Type.Relationship.friend;
			} else
				return Type.Relationship.opponent;
		} else if (wj1.role == Type.Role.ZhongChen) {
			if (wj2.role == Type.Role.ZhuGong) {
				return Type.Relationship.friend;
			} else if (wj2.role == Type.Role.NeiJian) {
				return Type.Relationship.friend;
			} else
				return Type.Relationship.opponent;
		} else if (wj1.role == Type.Role.FanZei) {
			if (wj2.role == Type.Role.ZhuGong) {
				return Type.Relationship.opponent;
			} else if (wj2.role == Type.Role.NeiJian) {
				return Type.Relationship.opponent;
			} else
				return Type.Relationship.friend;
		} else if (wj1.role == Type.Role.NeiJian) {
			if (wj2.role == Type.Role.ZhuGong) {
				return Type.Relationship.friend;
			} else if (wj2.role == Type.Role.ZhongChen) {
				return Type.Relationship.friend;
			} else
				return Type.Relationship.opponent;
		}
		return Type.Relationship.nil;
	}

	public void updateWuJiangToLibGameView(WuJiang wj, UpdateWJViewData item) {

		int index = wj.imageViewIndex;
		// update image view
		if (item.updateAll || item.updateImage) {
			if (wj.state == Type.State.Dead) {
				this.gameApp.libGameViewData.imageWJs[index]
						.setImageDrawable(R.drawable.wj_gray);
				gameApp.libGameViewData.imageWJs[index]
						.setBackgroundDrawable(this.gameApp.getResources()
								.getDrawable(R.drawable.bg_black));
			} else {
				this.gameApp.libGameViewData.imageWJs[index]
						.setImageDrawable(wj.imageNumber);
			}
			this.gameApp.libGameViewData.imageWJs[index]
					.setVisibility(View.VISIBLE);

			// only update mywujiang background, others use default bg
			if (index == 7) {
				// update lieaner wj background
				this.gameApp.libGameViewData.linearWJs[index]
						.setBackgroundDrawable(this.gameApp
								.getResources()
								.getDrawable(
										Helper
												.convertToWJBackGroundImg(wj.country)));
				this.gameApp.libGameViewData.wj8LinearArea
						.setBackgroundDrawable(this.gameApp
								.getResources()
								.getDrawable(
										Helper
												.convertToWJBackGroundImg(wj.country)));
				// update wj8 jineng btn background
				this.gameApp.libGameViewData.mJiNengBtn1
						.setImageDrawable(Helper
								.convertToJiNengBackGroundImg(wj.country));
				this.gameApp.libGameViewData.mJiNengBtn2
						.setImageDrawable(Helper
								.convertToJiNengBackGroundImg(wj.country));
				this.gameApp.libGameViewData.mJiNengBtn3
						.setImageDrawable(Helper
								.convertToJiNengBackGroundImg(wj.country));
				this.gameApp.libGameViewData.mJiNengBtn4
						.setImageDrawable(Helper
								.convertToJiNengBackGroundImg(wj.country));
			}
		}

		// update role
		if (item.updateAll || item.updateRole) {
			int roleId = R.drawable.r_unknown;
			if (wj.role == Type.Role.ZhuGong) {
				roleId = R.drawable.r_zhugong;
			} else if (wj.role == Type.Role.ZhongChen) {
				roleId = R.drawable.r_zhongchen;
			} else if (wj.role == Type.Role.NeiJian) {
				roleId = R.drawable.r_neijian;
			} else if (wj.role == Type.Role.FanZei) {
				roleId = R.drawable.r_fanzei;
			}

			this.gameApp.libGameViewData.imageWJRoles[index]
					.setImageDrawable(roleId);
			this.gameApp.libGameViewData.imageWJRoles[index]
					.setVisibility(View.VISIBLE);
		}

		// update blood
		if (item.updateAll || item.updateBlood) {
			this.updateBloodImage(wj);
		}

		// update pan ding
		if (item.updateAll || item.updatePangDing) {

			// first disable (INVISIBLE) all
			for (int i = 0; i < this.gameApp.libGameViewData.imageWJDPs[index].imgPds.length; i++) {
				this.gameApp.libGameViewData.imageWJDPs[index].imgPds[i]
						.setVisibility(View.INVISIBLE);
			}
			// then update latest

			for (int i = 0; i < wj.panDingPai.size(); i++) {
				CardPai cp = wj.panDingPai.get(i);
				int imgId = 0;
				if ((cp instanceof LeBuShiShu) || (cp instanceof Shan)) {
					imgId = R.drawable.pd_lebushishu;
				} else if (cp instanceof BingLiangCunDuan) {
					imgId = R.drawable.pd_bingliangcunduan;
				} else if (cp instanceof ShanDian) {
					imgId = R.drawable.pd_shandian;
				}
				this.gameApp.libGameViewData.imageWJDPs[index].imgPds[i]
						.setImageDrawable(imgId);
				this.gameApp.libGameViewData.imageWJDPs[index].imgPds[i]
						.setVisibility(View.VISIBLE);
			}
		}

		// update number of shou pai
		if (item.updateAll || item.updateShouPaiNumber) {
			this.gameApp.libGameViewData.imgWJShouPaiNumber[index]
					.setImageDrawable(Helper
							.convertToShouPaiNumberImg(wj.shouPai.size()));
			if (wj.state != Type.State.Dead)
				this.gameApp.libGameViewData.imgWJShouPaiNumber[index]
						.setVisibility(View.VISIBLE);
			else
				this.gameApp.libGameViewData.imgWJShouPaiNumber[index]
						.setVisibility(View.INVISIBLE);
		}

		// update my wujiang shoupai to view
		if (index == 7) {
			this.updateWJ8ShouPaiToLibGameView();
		}

		// update lian huang
		if (item.updateAll || item.updateLianHuan) {
			if (wj.lianHuan) {
				this.gameApp.libGameViewData.imgLianHuans[index]
						.setVisibility(View.VISIBLE);
			} else {
				this.gameApp.libGameViewData.imgLianHuans[index]
						.setVisibility(View.INVISIBLE);
			}
		}

		// update fan mian
		if (item.updateAll || item.updateFanMian) {
			if (wj.fanMian) {
				this.gameApp.libGameViewData.imgFanMians[index]
						.setVisibility(View.VISIBLE);
			} else {
				this.gameApp.libGameViewData.imgFanMians[index]
						.setVisibility(View.INVISIBLE);
			}
		}

		// update zhuang bei
		if (item.updateAll || item.updateZhuangBei) {
			if (wj.zhuangBei.wuQi != null) {
				this.gameApp.libGameViewData.imgZhuangBei[index].imgWuqi
						.setImageDrawable(wj.zhuangBei.wuQi.zbImgNumber);
			} else {
				this.gameApp.libGameViewData.imgZhuangBei[index].imgWuqi
						.setImageDrawable(R.drawable.zb_wuqi);
			}
			//
			if (wj.zhuangBei.fangJu != null) {
				this.gameApp.libGameViewData.imgZhuangBei[index].imgFangju
						.setImageDrawable(wj.zhuangBei.fangJu.zbImgNumber);

			} else {
				this.gameApp.libGameViewData.imgZhuangBei[index].imgFangju
						.setImageDrawable(R.drawable.zb_fangju);
			}
			//
			if (wj.zhuangBei.jiaYiMa != null) {
				this.gameApp.libGameViewData.imgZhuangBei[index].imgJaYiMa
						.setImageDrawable(wj.zhuangBei.jiaYiMa.zbImgNumber);

			} else {
				this.gameApp.libGameViewData.imgZhuangBei[index].imgJaYiMa
						.setImageDrawable(R.drawable.zb_jiayima);
			}
			//
			if (wj.zhuangBei.jianYiMa != null) {
				this.gameApp.libGameViewData.imgZhuangBei[index].imgJianYiMa
						.setImageDrawable(wj.zhuangBei.jianYiMa.zbImgNumber);

			} else {
				this.gameApp.libGameViewData.imgZhuangBei[index].imgJianYiMa
						.setImageDrawable(R.drawable.zb_jianyima);
			}
		}
	}

	public void updateWJ8ShouPaiToLibGameView() {

		WuJiang wj = this.gameApp.gameLogicData.myWuJiang;

		int spIndex = wj.wj8ShouPaiCurPage
				* this.gameApp.libGameViewData.maxShouPaiDisplayForWJ8;

		// if shou pai is more then 4, then use as normal
		// else if shou pai is <=4, then show the shou pai to
		// index of 0,2,4,6
		int slideIndex = 1;
		if (wj.shouPai.size() <= (this.gameApp.libGameViewData.maxShouPaiDisplayForWJ8 / 2)) {
			slideIndex = 2;
		}

		// reset selectedByClick to false
		wj.resetShouPaiSelectedBoolean();

		// first reset all shou pai image and top arrow to invisibility
		for (int i = 0; i < this.gameApp.libGameViewData.maxShouPaiDisplayForWJ8; i++) {
			this.gameApp.libGameViewData.WJ8ShouPaiArrow[i]
					.setVisibility(View.INVISIBLE);
			this.gameApp.libGameViewData.WJ8ShouPai[i]
					.setVisibility(View.INVISIBLE);
		}

		// update shou pai number
		this.gameApp.libGameViewData.imgWJShouPaiNumber[wj.imageViewIndex]
				.setImageDrawable(Helper.convertToShouPaiNumberImg(wj.shouPai
						.size()));
		this.gameApp.libGameViewData.imgWJShouPaiNumber[wj.imageViewIndex]
				.setVisibility(View.VISIBLE);

		// then update based on the latest shoupai
		for (int index = 0; ((index * slideIndex) < this.gameApp.libGameViewData.maxShouPaiDisplayForWJ8 && spIndex < wj.shouPai
				.size()); index++) {
			CardPai cp = (CardPai) wj.shouPai.get(spIndex++);
			this.gameApp.libGameViewData.WJ8ShouPai[index * slideIndex]
					.setImageDrawable(cp.imageNumber);
			this.gameApp.libGameViewData.WJ8ShouPai[index * slideIndex]
					.setVisibility(View.VISIBLE);

			// selected top iron
			if (cp.selectedByClick) {
				this.gameApp.libGameViewData.WJ8ShouPaiArrow[index * slideIndex]
						.setVisibility(View.VISIBLE);
			} else {
				this.gameApp.libGameViewData.WJ8ShouPaiArrow[index * slideIndex]
						.setVisibility(View.INVISIBLE);
			}
		}

		// display left/right arrow if shou pai size > 9
		if (wj.shouPai.size() > this.gameApp.libGameViewData.maxShouPaiDisplayForWJ8) {
			// this.gameApp.libGameViewData.arrowRight.setVisibility(View.VISIBLE);
		}
	}

	public void replaceWuJiangByImageViewIndex(int imageViewIndex, WuJiang tarWJ) {
		int i = 0;
		while (i < this.gameApp.gameLogicData.wuJiangs.size()) {
			WuJiang wj = this.gameApp.gameLogicData.wuJiangs.get(i);
			if (wj.imageViewIndex == imageViewIndex) {
				wj.reset();
				this.gameApp.gameLogicData.wuJiangs.set(i, tarWJ);
				return;
			}
			i++;
		}
	}

	public void updateWuJiangToLibGameView() {
		for (int i = 0; i < this.gameApp.gameLogicData.wuJiangs.size(); i++) {
			WuJiang wj = this.gameApp.gameLogicData.wuJiangs.get(i);
			wj.setGame(this.gameApp);
			wj.nextOne = findNextOne(wj.imageViewIndex);
			wj.preOne = findPreOne(wj.imageViewIndex);
			//
			if (wj.imageViewIndex == 7) {
				this.gameApp.gameLogicData.myWuJiang = wj;
				this.gameApp.gameLogicData.myWuJiang.tuoGuan = false;
				this.gameApp.gameLogicData.myWuJiang.state = Type.State.ChuPai;
			} else {
				wj.tuoGuan = true;
				wj.state = Type.State.Response;
			}

			//
			if (wj.role == Type.Role.ZhuGong) {
				this.gameApp.gameLogicData.zhuGongWuJiang = wj;
				this.gameApp.gameLogicData.zhuGongMaxBlood = wj
						.getOrigMaxBlood() + 1;
			}
		}

		//
		this.constructFriOpptList();
		this.updateAllWuJiangToLibGameView();
		if (this.gameApp.gameLogicData.myWuJiang != null)
			this.updateWJ8ShouPaiToLibGameView();
	}

	public void updateBloodImage(WuJiang wj) {

		int index = 0;
		// set to 1
		for (; (index < wj.blood && index < this.gameApp.libGameViewData.imageWJBloods[wj.imageViewIndex].img9Bloods.length); index++) {
			this.gameApp.libGameViewData.imageWJBloods[wj.imageViewIndex].img9Bloods[index]
					.setImageDrawable(R.drawable.blood_1);
			this.gameApp.libGameViewData.imageWJBloods[wj.imageViewIndex].img9Bloods[index]
					.setVisibility(View.VISIBLE);
		}
		// reset others to 0
		for (; (index < wj.getMaxBlood() && index < this.gameApp.libGameViewData.imageWJBloods[wj.imageViewIndex].img9Bloods.length); index++) {
			this.gameApp.libGameViewData.imageWJBloods[wj.imageViewIndex].img9Bloods[index]
					.setImageDrawable(R.drawable.blood_0);
			this.gameApp.libGameViewData.imageWJBloods[wj.imageViewIndex].img9Bloods[index]
					.setVisibility(View.VISIBLE);
		}
		// update text view if lost blood
		// if (wj.blood <= 0) {
		// this.gameApp.libGameViewData.txtWJLostBloods[wj.imageViewIndex]
		// .setText(wj.blood + "");
		// this.gameApp.libGameViewData.txtWJLostBloods[wj.imageViewIndex]
		// .setVisibility(View.VISIBLE);
		// } else {
		// this.gameApp.libGameViewData.txtWJLostBloods[wj.imageViewIndex]
		// .setVisibility(View.INVISIBLE);
		// }
	}

	public void setCurChuPaiWJColor(WuJiang srcWJ) {
		WuJiang curWJ = srcWJ;
		gameApp.libGameViewData.imageWJs[curWJ.imageViewIndex]
				.setBackgroundDrawable(gameApp.getResources().getDrawable(
						R.drawable.bg_red));
		curWJ = curWJ.nextOne;
		while (!curWJ.equals(srcWJ)) {
			gameApp.libGameViewData.imageWJs[curWJ.imageViewIndex]
					.setBackgroundDrawable(gameApp.getResources().getDrawable(
							R.drawable.bg_black));
			curWJ = curWJ.nextOne;
		}
	}

	public int countAliveWuJiang() {
		int rtn = 0;
		for (int i = 0; i < this.gameApp.gameLogicData.wuJiangs.size(); i++) {
			if (this.gameApp.gameLogicData.wuJiangs.get(i).state != Type.State.nil
					&& this.gameApp.gameLogicData.wuJiangs.get(i).state != Type.State.Dead)
				rtn++;
		}
		return rtn;
	}

	public int countDistance(WuJiang fromWJ, WuJiang toWJ, boolean applyWuQi) {

		int disN = 1;// count from nextone
		int disP = 1;// count from preone

		if (applyWuQi && fromWJ.zhuangBei.wuQi != null) {
			WuQiCardPai wq = (WuQiCardPai) fromWJ.zhuangBei.wuQi;
			disN -= (wq.distance - 1);
			disP -= (wq.distance - 1);
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

		// weiyuan kuanggu himself
		if (fromWJ.equals(toWJ))
			disN = 0;

		return (disN < disP) ? disN : disP;
	}

	public String convertRoleToName(Type.Role role) {
		String rtn = "error role";
		if (role == Type.Role.ZhuGong) {
			rtn = "主公";
		} else if (role == Type.Role.FanZei) {
			rtn = "反贼";
		} else if (role == Type.Role.ZhongChen) {
			rtn = "忠臣";
		} else if (role == Type.Role.NeiJian) {
			rtn = "内奸";
		}
		return rtn;
	}

	public boolean checkMatchOver() {

		if (gameApp.gameLogicData.userExit) {
			this.gameApp.gameLogicData.gameOver = true;
			return true;
		}

		for (int i = 0; i < this.gameApp.gameLogicData.wuJiangs.size(); i++) {
			WuJiang wj = this.gameApp.gameLogicData.wuJiangs.get(i);
			if (wj.role == Type.Role.ZhuGong && wj.state == Type.State.Dead) {
				this.gameApp.gameLogicData.gameOver = true;
				return true;
			}

			if ((wj.role == Type.Role.FanZei && wj.state != Type.State.Dead)
					|| (wj.role == Type.Role.NeiJian && wj.state != Type.State.Dead)) {
				this.gameApp.gameLogicData.gameOver = false;
				return false;
			}
		}
		this.gameApp.gameLogicData.gameOver = true;
		return true;
	}

	public void constructFriOpptList() {
		if (this.gameApp.gameLogicData.gameOver)
			return;
		for (int i = 0; i < this.gameApp.gameLogicData.wuJiangs.size(); i++) {
			WuJiang wji = this.gameApp.gameLogicData.wuJiangs.get(i);
			if (wji.state == Type.State.Dead)
				continue;
			wji.friendList = null;
			wji.friendList = new ArrayList<WuJiang>();
			wji.opponentList = null;
			wji.opponentList = new ArrayList<WuJiang>();

			for (int j = 0; j < this.gameApp.gameLogicData.wuJiangs.size(); j++) {
				WuJiang wjj = this.gameApp.gameLogicData.wuJiangs.get(j);
				if (wjj.state == Type.State.Dead)
					continue;
				if (i != j) {
					if (wji.role == Type.Role.ZhuGong) {
						if (wjj.role == Type.Role.ZhongChen) {
							wji.friendList.add(wjj);
						} else if (wjj.role == Type.Role.FanZei) {
							wji.opponentList.add(wjj);
						} else if (wjj.role == Type.Role.NeiJian) {
							wji.opponentList.add(wjj);
						}
					} else if (wji.role == Type.Role.ZhongChen) {
						if (wjj.role == Type.Role.ZhuGong) {
							wji.friendList.add(wjj);
						} else if (wjj.role == Type.Role.FanZei) {
							wji.opponentList.add(wjj);
						} else if (wjj.role == Type.Role.NeiJian) {
							wji.opponentList.add(wjj);
						} else if (wjj.role == Type.Role.ZhongChen) {
							wji.friendList.add(wjj);
						}
					} else if (wji.role == Type.Role.FanZei) {
						if (wjj.role == Type.Role.ZhuGong) {
							wji.opponentList.add(wjj);
						} else if (wjj.role == Type.Role.ZhongChen) {
							wji.opponentList.add(wjj);
						} else if (wjj.role == Type.Role.NeiJian) {
							wji.opponentList.add(wjj);
						} else if (wjj.role == Type.Role.FanZei) {
							wji.friendList.add(wjj);
						}
					} else if (wji.role == Type.Role.NeiJian) {
						if (wjj.role == Type.Role.FanZei) {
							wji.opponentList.add(wjj);
						} else if (wjj.role == Type.Role.ZhongChen) {
							wji.opponentList.add(wjj);
						} else if (wjj.role == Type.Role.NeiJian) {
							wji.friendList.add(wjj);
						} else if (wjj.role == Type.Role.ZhuGong) {
							if (this.countAliveWuJiang() == 2) {
								wji.opponentList.add(wjj);
							}
						}
					}
				}
			}
		}
	}

	public void auditAllRunningWuJiang() {
		for (int i = 0; i < this.gameApp.gameLogicData.wuJiangs.size(); i++) {
			WuJiang curWJ = this.gameApp.gameLogicData.wuJiangs.get(i);
			if (curWJ.state != Type.State.Dead) {
				AuditResult ar = curWJ.audit();
				if (ar.hasMismatch()) {
					this.gameApp.libGameViewData.fileUtil
							.addContent("Audit Mismatch Report:\n"
									+ curWJ.dispName + ":\n"
									+ ar.report().trim());
					// this.gameApp.libGameViewData.logInfo("Audit Mismatch: "
					// + curWJ.dispName);
				}
			}
		}
	}

	public WuJiang findNextOne(int viewImageIndex) {
		int[] wjNext = WuJiangNextOneData.wjNext[viewImageIndex];
		for (int j = 0; j < wjNext.length; j++) {
			WuJiang nextWJ = this.getWuJiangByImageViewIndex(wjNext[j],
					this.gameApp.gameLogicData.wuJiangs);
			if (nextWJ != null && nextWJ.state != Type.State.Dead) {
				return nextWJ;
			}
		}
		return null;
	}

	public WuJiang findPreOne(int viewImageIndex) {
		int[] wjNext = WuJiangNextOneData.wjNext[viewImageIndex];
		for (int j = 0; j < wjNext.length; j++) {
			WuJiang nextWJ = this.getWuJiangByImageViewIndex(
					wjNext[wjNext.length - 1 - j],
					this.gameApp.gameLogicData.wuJiangs);
			if (nextWJ != null && nextWJ.state != Type.State.Dead) {
				return nextWJ;
			}
		}
		return null;
	}

	public boolean qgameSanityCheck() {
		if (this.gameApp.gameLogicData.zhuGongWuJiang == null) {
			this.gameApp.libGameViewData.logInfo("设置错误: 缺少主公.",
					Type.logDelay.NoDelay);
			return false;
		}

		if (this.gameApp.gameLogicData.myWuJiang == null) {
			this.gameApp.libGameViewData.logInfo("设置错误: 缺少自己武将.",
					Type.logDelay.NoDelay);
			return false;
		}

		for (int i = 0; i < this.gameApp.gameLogicData.wuJiangs.size(); i++) {
			WuJiang wj = (WuJiang) this.gameApp.gameLogicData.wuJiangs.get(i);
			if (wj.role == Type.Role.Nil) {
				this.gameApp.libGameViewData.logInfo("设置错误: 武将" + wj.dispName
						+ "缺少身份.", Type.logDelay.NoDelay);
				return false;
			}
			if (wj.blood <= 0) {
				this.gameApp.libGameViewData.logInfo("设置错误: 武将" + wj.dispName
						+ "缺少体力.", Type.logDelay.NoDelay);
				return false;
			}
		}

		return true;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
}
