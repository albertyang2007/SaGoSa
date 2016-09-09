package alben.sgs.android.debug;

import alben.sgs.android.GameApp;
import alben.sgs.android.R;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.instance.BaGuaZhen;
import alben.sgs.cardpai.instance.BaiYinShiZi;
import alben.sgs.cardpai.instance.BingHanJian;
import alben.sgs.cardpai.instance.BingLiangCunDuan;
import alben.sgs.cardpai.instance.ChiTu;
import alben.sgs.cardpai.instance.ChiXiongShuanPeiJian;
import alben.sgs.cardpai.instance.DiXu;
import alben.sgs.cardpai.instance.FangTianHuaJi;
import alben.sgs.cardpai.instance.GuDingDao;
import alben.sgs.cardpai.instance.GuanShiFu;
import alben.sgs.cardpai.instance.GuoHeChaiQiao;
import alben.sgs.cardpai.instance.HuoGong;
import alben.sgs.cardpai.instance.HuoSha;
import alben.sgs.cardpai.instance.JieDaoShaRen;
import alben.sgs.cardpai.instance.Jiu;
import alben.sgs.cardpai.instance.JueDou;
import alben.sgs.cardpai.instance.LeBuShiShu;
import alben.sgs.cardpai.instance.LeiSha;
import alben.sgs.cardpai.instance.NanManRuQin;
import alben.sgs.cardpai.instance.QiLingGong;
import alben.sgs.cardpai.instance.QiXingDao;
import alben.sgs.cardpai.instance.QingHongJian;
import alben.sgs.cardpai.instance.QingLongYuanYueDao;
import alben.sgs.cardpai.instance.RenWangDun;
import alben.sgs.cardpai.instance.Sha;
import alben.sgs.cardpai.instance.Shan;
import alben.sgs.cardpai.instance.ShanDian;
import alben.sgs.cardpai.instance.ShunShouQuanYang;
import alben.sgs.cardpai.instance.Tao;
import alben.sgs.cardpai.instance.TaoYuanJieYi;
import alben.sgs.cardpai.instance.TengJia;
import alben.sgs.cardpai.instance.TieSuoLianHuan;
import alben.sgs.cardpai.instance.WanJianQiFa;
import alben.sgs.cardpai.instance.WuGuFengDeng;
import alben.sgs.cardpai.instance.WuXieKeJi;
import alben.sgs.cardpai.instance.WuZhongShengYou;
import alben.sgs.cardpai.instance.ZhangBaSheMao;
import alben.sgs.cardpai.instance.ZhuGeLianNu;
import alben.sgs.cardpai.instance.ZhuQueYuShan;
import alben.sgs.type.Type;
import alben.sgs.wujiang.WuJiang;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class DebugAddShouPaiDialog {
	public GameApp gameApp = null;

	public DebugAddShouPaiDialog(Context context, GameApp gp) {

		this.gameApp = gp;

		LayoutInflater factory = LayoutInflater.from(context);
		final View textEntryView = factory.inflate(R.layout.debug_add_sp, null);
		AlertDialog dlg = new AlertDialog.Builder(context).setView(
				textEntryView).create();

		dlg.show();

		Button tao = (Button) dlg.findViewById(R.id.debug_add_tao);
		Button jiu = (Button) dlg.findViewById(R.id.debug_add_jiu);
		Button heisha = (Button) dlg.findViewById(R.id.debug_add_heisha);
		Button hongsha = (Button) dlg.findViewById(R.id.debug_add_hongsha);
		Button leisha = (Button) dlg.findViewById(R.id.debug_add_leisha);
		Button huosha = (Button) dlg.findViewById(R.id.debug_add_huosha);
		Button huogong = (Button) dlg.findViewById(R.id.debug_add_huogong);
		Button shan = (Button) dlg.findViewById(R.id.debug_add_shan);
		Button ghcq = (Button) dlg.findViewById(R.id.debug_add_ghcq);
		Button blcd = (Button) dlg.findViewById(R.id.debug_add_blcd);
		Button jdsr = (Button) dlg.findViewById(R.id.debug_add_jdsr);
		Button lbss = (Button) dlg.findViewById(R.id.debug_add_lbss);
		Button nnrq = (Button) dlg.findViewById(R.id.debug_add_nnrq);
		Button ssxy = (Button) dlg.findViewById(R.id.debug_add_ssxy);
		Button juedou = (Button) dlg.findViewById(R.id.debug_add_juedou);
		Button shandian = (Button) dlg.findViewById(R.id.debug_add_shandian);
		Button chitu = (Button) dlg.findViewById(R.id.debug_add_chitu);
		Button dilu = (Button) dlg.findViewById(R.id.debug_add_dilu);
		Button tyjy = (Button) dlg.findViewById(R.id.debug_add_tyjy);
		Button tslh = (Button) dlg.findViewById(R.id.debug_add_tslh);
		Button wjqf = (Button) dlg.findViewById(R.id.debug_add_wjqf);
		Button wxkj = (Button) dlg.findViewById(R.id.debug_add_wxkj);
		Button wzsy = (Button) dlg.findViewById(R.id.debug_add_wzsy);
		Button wgfd = (Button) dlg.findViewById(R.id.debug_add_wgfd);
		Button bgz = (Button) dlg.findViewById(R.id.debug_add_bgz);
		Button bysz = (Button) dlg.findViewById(R.id.debug_add_bysz);
		Button rwd = (Button) dlg.findViewById(R.id.debug_add_rwd);
		Button tengjia = (Button) dlg.findViewById(R.id.debug_add_tengjia);
		Button cxspj = (Button) dlg.findViewById(R.id.debug_add_cxspj);
		Button fthj = (Button) dlg.findViewById(R.id.debug_add_fthj);
		Button gdd = (Button) dlg.findViewById(R.id.debug_add_gdd);
		Button gsf = (Button) dlg.findViewById(R.id.debug_add_gsf);
		Button bhj = (Button) dlg.findViewById(R.id.debug_add_bhj);
		Button qlg = (Button) dlg.findViewById(R.id.debug_add_qlg);
		Button qhj = (Button) dlg.findViewById(R.id.debug_add_qhj);
		Button qlyyd = (Button) dlg.findViewById(R.id.debug_add_qlyyd);
		Button zbsm = (Button) dlg.findViewById(R.id.debug_add_zbsm);
		Button zqsy = (Button) dlg.findViewById(R.id.debug_add_zqsy);
		Button zgln = (Button) dlg.findViewById(R.id.debug_add_zgln);
		Button qxd = (Button) dlg.findViewById(R.id.debug_add_qxd);

		tao.setOnClickListener(new CardPaiImageListener());
		jiu.setOnClickListener(new CardPaiImageListener());
		heisha.setOnClickListener(new CardPaiImageListener());
		hongsha.setOnClickListener(new CardPaiImageListener());
		leisha.setOnClickListener(new CardPaiImageListener());
		huosha.setOnClickListener(new CardPaiImageListener());
		huogong.setOnClickListener(new CardPaiImageListener());
		shan.setOnClickListener(new CardPaiImageListener());
		ghcq.setOnClickListener(new CardPaiImageListener());
		blcd.setOnClickListener(new CardPaiImageListener());
		jdsr.setOnClickListener(new CardPaiImageListener());
		lbss.setOnClickListener(new CardPaiImageListener());
		nnrq.setOnClickListener(new CardPaiImageListener());
		ssxy.setOnClickListener(new CardPaiImageListener());
		juedou.setOnClickListener(new CardPaiImageListener());
		shandian.setOnClickListener(new CardPaiImageListener());
		chitu.setOnClickListener(new CardPaiImageListener());
		dilu.setOnClickListener(new CardPaiImageListener());
		tyjy.setOnClickListener(new CardPaiImageListener());
		tslh.setOnClickListener(new CardPaiImageListener());
		wjqf.setOnClickListener(new CardPaiImageListener());
		wxkj.setOnClickListener(new CardPaiImageListener());
		wzsy.setOnClickListener(new CardPaiImageListener());
		wgfd.setOnClickListener(new CardPaiImageListener());
		bgz.setOnClickListener(new CardPaiImageListener());
		bysz.setOnClickListener(new CardPaiImageListener());
		rwd.setOnClickListener(new CardPaiImageListener());
		tengjia.setOnClickListener(new CardPaiImageListener());
		cxspj.setOnClickListener(new CardPaiImageListener());
		fthj.setOnClickListener(new CardPaiImageListener());
		gdd.setOnClickListener(new CardPaiImageListener());
		gsf.setOnClickListener(new CardPaiImageListener());
		bhj.setOnClickListener(new CardPaiImageListener());
		qlg.setOnClickListener(new CardPaiImageListener());
		qhj.setOnClickListener(new CardPaiImageListener());
		qlyyd.setOnClickListener(new CardPaiImageListener());
		zbsm.setOnClickListener(new CardPaiImageListener());
		zqsy.setOnClickListener(new CardPaiImageListener());
		zgln.setOnClickListener(new CardPaiImageListener());
		qxd.setOnClickListener(new CardPaiImageListener());
	}

	private class CardPaiImageListener implements
			android.view.View.OnClickListener {
		public void onClick(View v) {
			CardPai sp = null;
			WuJiang myWJ = gameApp.gameLogicData.myWuJiang;
			switch (v.getId()) {

			case R.id.debug_add_tao: {
				sp = new Tao(Type.CardPai.Tao, Type.CardPaiClass.FangPian, 2,
						R.drawable.cp_fp_2_tao);
				break;
			}
			case R.id.debug_add_jiu: {
				sp = new Jiu(Type.CardPai.Jiu, Type.CardPaiClass.FangPian, 9,
						R.drawable.cp_fp_9_jiu);
				break;
			}
			case R.id.debug_add_heisha: {
				sp = new Sha(Type.CardPai.LeiSha, Type.CardPaiClass.HeiTao, 4,
						R.drawable.cp_ht_4_leisha);
				break;
			}
			case R.id.debug_add_hongsha: {
				sp = new Sha(Type.CardPai.HuoSha, Type.CardPaiClass.FangPian,
						4, R.drawable.cp_fp_4_huosha);
				break;
			}
			case R.id.debug_add_leisha: {
				sp = new LeiSha(Type.CardPai.LeiSha, Type.CardPaiClass.HeiTao,
						4, R.drawable.cp_ht_4_leisha);
				break;
			}
			case R.id.debug_add_huosha: {
				sp = new HuoSha(Type.CardPai.HuoSha,
						Type.CardPaiClass.FangPian, 4,
						R.drawable.cp_fp_4_huosha);
				break;
			}
			case R.id.debug_add_huogong: {
				sp = new HuoGong(Type.CardPai.HuoGong,
						Type.CardPaiClass.FangPian, 12,
						R.drawable.cp_fp_q_huogong, Type.JinNangApplyTo.anyone);
				break;
			}
			case R.id.debug_add_shan: {
				sp = new Shan(Type.CardPai.Shan, Type.CardPaiClass.FangPian,
						10, R.drawable.cp_fp_10_shan);
				break;
			}
			case R.id.debug_add_ghcq: {
				sp = new GuoHeChaiQiao(Type.CardPai.GuoHeChaiQiao,
						Type.CardPaiClass.HeiTao, 3,
						R.drawable.cp_ht_3_guohechaiqiao,
						Type.JinNangApplyTo.anyone);
				break;
			}
			case R.id.debug_add_blcd: {
				sp = new BingLiangCunDuan(Type.CardPai.BingLiangCunDuan,
						Type.CardPaiClass.HeiTao, 10,
						R.drawable.cp_ht_10_bingliangcunduan,
						Type.JinNangApplyTo.distance_1);
				break;
			}
			case R.id.debug_add_jdsr: {
				sp = new JieDaoShaRen(Type.CardPai.JieDaoShaRen,
						Type.CardPaiClass.MeiHua, 12,
						R.drawable.cp_mh_q_jiedaosharen,
						Type.JinNangApplyTo.anyone);
				break;
			}
			case R.id.debug_add_lbss: {
				sp = new LeBuShiShu(Type.CardPai.LeBuShiShu,
						Type.CardPaiClass.HeiTao, 6,
						R.drawable.cp_ht_6_lebushishu,
						Type.JinNangApplyTo.anyone);
				break;
			}
			case R.id.debug_add_nnrq: {
				sp = new NanManRuQin(Type.CardPai.NanManRuQin,
						Type.CardPaiClass.HeiTao, 7,
						R.drawable.cp_ht_7_nannamruqin, Type.JinNangApplyTo.all);
				break;
			}
			case R.id.debug_add_ssxy: {
				sp = new ShunShouQuanYang(Type.CardPai.ShunShouQuanYang,
						Type.CardPaiClass.FangPian, 3,
						R.drawable.cp_fp_3_shunshouquanyang,
						Type.JinNangApplyTo.distance_1);
				break;
			}
			case R.id.debug_add_juedou: {
				sp = new JueDou(Type.CardPai.JueDou,
						Type.CardPaiClass.FangPian, 1,
						R.drawable.cp_fp_1_juedou, Type.JinNangApplyTo.anyone);
				break;
			}
			case R.id.debug_add_shandian: {
				sp = new ShanDian(Type.CardPai.ShanDian,
						Type.CardPaiClass.HeiTao, 1,
						R.drawable.cp_ht_1_shandian, Type.JinNangApplyTo.self);
				break;
			}
			case R.id.debug_add_chitu: {
				sp = new ChiTu(Type.CardPai.ChiTu, Type.CardPaiClass.HongTao,
						5, R.drawable.cp_hot_5_chitu, -1);
				break;
			}
			case R.id.debug_add_dilu: {
				sp = new DiXu(Type.CardPai.DiXu, Type.CardPaiClass.MeiHua, 5,
						R.drawable.cp_mh_5_dilu, +1);
				break;
			}
			case R.id.debug_add_tyjy: {
				sp = new TaoYuanJieYi(Type.CardPai.TaoYuanJieYi,
						Type.CardPaiClass.HongTao, 1,
						R.drawable.cp_hot_1_taoyuanjieyi,
						Type.JinNangApplyTo.all);
				break;
			}
			case R.id.debug_add_tslh: {
				sp = new TieSuoLianHuan(Type.CardPai.TieSuoLianHuan,
						Type.CardPaiClass.HeiTao, 11,
						R.drawable.cp_ht_j_tiesuolianhuan,
						Type.JinNangApplyTo.anyone);
				break;
			}
			case R.id.debug_add_wjqf: {
				sp = new WanJianQiFa(Type.CardPai.WanJianQiFa,
						Type.CardPaiClass.HongTao, 1,
						R.drawable.cp_hot_1_wanjianqifa,
						Type.JinNangApplyTo.all);
				break;
			}
			case R.id.debug_add_wxkj: {
				sp = new WuXieKeJi(Type.CardPai.WuXieKeJi,
						Type.CardPaiClass.HeiTao, 11,
						R.drawable.cp_ht_j_wuxiekeji,
						Type.JinNangApplyTo.anyone);
				break;
			}
			case R.id.debug_add_wzsy: {
				sp = new WuZhongShengYou(Type.CardPai.WuZhongShengYou,
						Type.CardPaiClass.HongTao, 7,
						R.drawable.cp_hot_7_wuzhongshengyou,
						Type.JinNangApplyTo.self);
				break;
			}
			case R.id.debug_add_wgfd: {
				sp = new WuGuFengDeng(Type.CardPai.WuGuFengDeng,
						Type.CardPaiClass.HongTao, 3,
						R.drawable.cp_hot_3_wugufengdeng,
						Type.JinNangApplyTo.all);
				break;
			}
			case R.id.debug_add_bgz: {
				sp = new BaGuaZhen(Type.CardPai.BaGuaZhen,
						Type.CardPaiClass.HeiTao, 2,
						R.drawable.cp_ht_2_baguazhen);
				break;
			}
			case R.id.debug_add_bysz: {
				sp = new BaiYinShiZi(Type.CardPai.BaiYinShiZi,
						Type.CardPaiClass.MeiHua, 1,
						R.drawable.cp_mh_1_baiyinshizi);
				break;
			}
			case R.id.debug_add_rwd: {
				sp = new RenWangDun(Type.CardPai.RenWangDun,
						Type.CardPaiClass.MeiHua, 2,
						R.drawable.cp_mh_2_renwangdun);
				break;
			}
			case R.id.debug_add_tengjia: {
				sp = new TengJia(Type.CardPai.TengJia,
						Type.CardPaiClass.MeiHua, 2, R.drawable.cp_mh_2_tengjia);
				break;
			}
			case R.id.debug_add_cxspj: {
				sp = new ChiXiongShuanPeiJian(
						Type.CardPai.ChiXiongShuanPeiJian,
						Type.CardPaiClass.HeiTao, 2,
						R.drawable.cp_ht_2_chixiongshuanggujian, 2);
				break;
			}
			case R.id.debug_add_fthj: {
				sp = new FangTianHuaJi(Type.CardPai.FangTianHuaJi,
						Type.CardPaiClass.FangPian, 12,
						R.drawable.cp_fp_q_fangtianhuaji, 4);
				break;
			}
			case R.id.debug_add_gdd: {
				sp = new GuDingDao(Type.CardPai.GuDingDao,
						Type.CardPaiClass.HeiTao, 1,
						R.drawable.cp_ht_1_gudingdao, 2);
				break;
			}
			case R.id.debug_add_gsf: {
				sp = new GuanShiFu(Type.CardPai.GuanShiFu,
						Type.CardPaiClass.FangPian, 5,
						R.drawable.cp_fp_5_guanshifu, 3);
				break;
			}
			case R.id.debug_add_bhj: {
				sp = new BingHanJian(Type.CardPai.BingHanJian,
						Type.CardPaiClass.HeiTao, 2,
						R.drawable.cp_ht_2_hanbingjian, 2);
				break;
			}
			case R.id.debug_add_qlg: {
				sp = new QiLingGong(Type.CardPai.QiLingGong,
						Type.CardPaiClass.HongTao, 5,
						R.drawable.cp_hot_5_qilinggong, 5);
				break;
			}
			case R.id.debug_add_qhj: {
				sp = new QingHongJian(Type.CardPai.QingHongJian,
						Type.CardPaiClass.HeiTao, 6,
						R.drawable.cp_ht_6_qinghongjian, 2);
				break;
			}
			case R.id.debug_add_qlyyd: {
				sp = new QingLongYuanYueDao(Type.CardPai.QingLongYuanYueDao,
						Type.CardPaiClass.HeiTao, 5,
						R.drawable.cp_ht_5_qinglongyuanyuedao, 3);
				break;
			}
			case R.id.debug_add_zbsm: {
				sp = new ZhangBaSheMao(Type.CardPai.ZhangBaSheMao,
						Type.CardPaiClass.HeiTao, 12,
						R.drawable.cp_ht_q_zhangbashemao, 3);
				break;
			}
			case R.id.debug_add_zqsy: {
				sp = new ZhuQueYuShan(Type.CardPai.ZhuQueYuShan,
						Type.CardPaiClass.FangPian, 1,
						R.drawable.cp_fp_1_zhuqueshanyu, 4);
				break;
			}
			case R.id.debug_add_zgln: {
				sp = new ZhuGeLianNu(Type.CardPai.ZhuGeLianNu,
						Type.CardPaiClass.FangPian, 1,
						R.drawable.cp_fp_1_zhugeliannv, 1);
				break;
			}
			case R.id.debug_add_qxd: {
				sp = new QiXingDao(Type.CardPai.QiXingDao,
						Type.CardPaiClass.FangPian, 12,
						R.drawable.cp_fp_q_qixingdao, 1);
				break;
			}

			}

			if (myWJ != null && sp != null) {
				sp.reset();
				sp.belongToWuJiang = myWJ;
				sp.cpState = Type.CPState.ShouPai;
				sp.gameApp = gameApp;
				myWJ.shouPai.add(sp);
				gameApp.gameLogicData.wjHelper.updateWJ8ShouPaiToLibGameView();
			}
		}
	}
}
