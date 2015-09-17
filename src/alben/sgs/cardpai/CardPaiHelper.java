package alben.sgs.cardpai;

import java.util.ArrayList;

import alben.sgs.android.GameApp;
import alben.sgs.android.R;
import alben.sgs.cardpai.instance.BaGuaZhen;
import alben.sgs.cardpai.instance.BaiYinShiZi;
import alben.sgs.cardpai.instance.BingHanJian;
import alben.sgs.cardpai.instance.BingLiangCunDuan;
import alben.sgs.cardpai.instance.ChiTu;
import alben.sgs.cardpai.instance.ChiXiongShuanPeiJian;
import alben.sgs.cardpai.instance.DaWan;
import alben.sgs.cardpai.instance.DiXu;
import alben.sgs.cardpai.instance.FangTianHuaJi;
import alben.sgs.cardpai.instance.GuDingDao;
import alben.sgs.cardpai.instance.GuanShiFu;
import alben.sgs.cardpai.instance.GuoHeChaiQiao;
import alben.sgs.cardpai.instance.HuaJu;
import alben.sgs.cardpai.instance.HuoGong;
import alben.sgs.cardpai.instance.HuoSha;
import alben.sgs.cardpai.instance.JieDaoShaRen;
import alben.sgs.cardpai.instance.Jiu;
import alben.sgs.cardpai.instance.JueDou;
import alben.sgs.cardpai.instance.JueYing;
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
import alben.sgs.cardpai.instance.ZhiXin;
import alben.sgs.cardpai.instance.ZhuGeLianNu;
import alben.sgs.cardpai.instance.ZhuQueYuShan;
import alben.sgs.cardpai.instance.ZhuaHuangFeiDian;
import alben.sgs.common.Helper;
import alben.sgs.type.Type;
import alben.sgs.type.UpdateWJViewData;
import alben.sgs.wujiang.WuJiang;
import alben.sgs.wujiang.instance.GuoJia;
import android.view.View;

public class CardPaiHelper {

	public Helper commonHelper = new Helper();

	public ArrayList<CardPai> cardPaiPool = new ArrayList<CardPai>();

	public ArrayList<CardPai> cardPais = new ArrayList<CardPai>();
	public int currentCP = 0;
	public GameApp gameApp = null;

	public CardPaiHelper(GameApp g) {
		this.gameApp = g;
		this.addCardPai();
	}

	public void addCardPai() {

		this.cardPaiPool.add(new Jiu(Type.CardPai.Jiu,
				Type.CardPaiClass.FangPian, 9, R.drawable.cp_fp_9_jiu));
		this.cardPaiPool.add(new Jiu(Type.CardPai.Jiu,
				Type.CardPaiClass.HeiTao, 3, R.drawable.cp_ht_3_jiu));
		this.cardPaiPool.add(new Jiu(Type.CardPai.Jiu,
				Type.CardPaiClass.HeiTao, 9, R.drawable.cp_ht_9_jiu));
		this.cardPaiPool.add(new Jiu(Type.CardPai.Jiu,
				Type.CardPaiClass.MeiHua, 3, R.drawable.cp_mh_3_jiu));
		this.cardPaiPool.add(new Jiu(Type.CardPai.Jiu,
				Type.CardPaiClass.MeiHua, 9, R.drawable.cp_mh_9_jiu));

		this.cardPaiPool.add(new Sha(Type.CardPai.Sha,
				Type.CardPaiClass.FangPian, 6, R.drawable.cp_fp_6_sha));
		this.cardPaiPool.add(new Sha(Type.CardPai.Sha,
				Type.CardPaiClass.FangPian, 7, R.drawable.cp_fp_7_sha));
		this.cardPaiPool.add(new Sha(Type.CardPai.Sha,
				Type.CardPaiClass.FangPian, 8, R.drawable.cp_fp_8_sha));
		this.cardPaiPool.add(new Sha(Type.CardPai.Sha,
				Type.CardPaiClass.FangPian, 9, R.drawable.cp_fp_9_sha));
		this.cardPaiPool.add(new Sha(Type.CardPai.Sha,
				Type.CardPaiClass.FangPian, 10, R.drawable.cp_fp_10_sha));
		this.cardPaiPool.add(new Sha(Type.CardPai.Sha,
				Type.CardPaiClass.FangPian, 13, R.drawable.cp_fp_k_sha));

		this.cardPaiPool.add(new Sha(Type.CardPai.Sha,
				Type.CardPaiClass.HeiTao, 7, R.drawable.cp_ht_7_sha));
		this.cardPaiPool.add(new Sha(Type.CardPai.Sha,
				Type.CardPaiClass.HeiTao, 8, R.drawable.cp_ht_8_sha));
		this.cardPaiPool.add(new Sha(Type.CardPai.Sha,
				Type.CardPaiClass.HeiTao, 8, R.drawable.cp_ht_8_sha));
		this.cardPaiPool.add(new Sha(Type.CardPai.Sha,
				Type.CardPaiClass.HeiTao, 9, R.drawable.cp_ht_9_sha));
		this.cardPaiPool.add(new Sha(Type.CardPai.Sha,
				Type.CardPaiClass.HeiTao, 9, R.drawable.cp_ht_9_sha));
		this.cardPaiPool.add(new Sha(Type.CardPai.Sha,
				Type.CardPaiClass.HeiTao, 10, R.drawable.cp_ht_10_sha));
		this.cardPaiPool.add(new Sha(Type.CardPai.Sha,
				Type.CardPaiClass.HeiTao, 10, R.drawable.cp_ht_10_sha));
		this.cardPaiPool.add(new Sha(Type.CardPai.Sha,
				Type.CardPaiClass.HongTao, 10, R.drawable.cp_hot_10_sha));
		this.cardPaiPool.add(new Sha(Type.CardPai.Sha,
				Type.CardPaiClass.HongTao, 10, R.drawable.cp_hot_10_sha));
		this.cardPaiPool.add(new Sha(Type.CardPai.Sha,
				Type.CardPaiClass.HongTao, 11, R.drawable.cp_hot_j_sha));

		this.cardPaiPool.add(new Sha(Type.CardPai.Sha,
				Type.CardPaiClass.MeiHua, 2, R.drawable.cp_mh_2_sha));
		this.cardPaiPool.add(new Sha(Type.CardPai.Sha,
				Type.CardPaiClass.MeiHua, 3, R.drawable.cp_mh_3_sha));
		this.cardPaiPool.add(new Sha(Type.CardPai.Sha,
				Type.CardPaiClass.MeiHua, 4, R.drawable.cp_mh_4_sha));
		this.cardPaiPool.add(new Sha(Type.CardPai.Sha,
				Type.CardPaiClass.MeiHua, 5, R.drawable.cp_mh_5_sha));
		this.cardPaiPool.add(new Sha(Type.CardPai.Sha,
				Type.CardPaiClass.MeiHua, 6, R.drawable.cp_mh_6_sha));
		this.cardPaiPool.add(new Sha(Type.CardPai.Sha,
				Type.CardPaiClass.MeiHua, 7, R.drawable.cp_mh_7_sha));
		this.cardPaiPool.add(new Sha(Type.CardPai.Sha,
				Type.CardPaiClass.MeiHua, 8, R.drawable.cp_mh_8_sha));
		this.cardPaiPool.add(new Sha(Type.CardPai.Sha,
				Type.CardPaiClass.MeiHua, 8, R.drawable.cp_mh_8_sha));
		this.cardPaiPool.add(new Sha(Type.CardPai.Sha,
				Type.CardPaiClass.MeiHua, 9, R.drawable.cp_mh_9_sha));
		this.cardPaiPool.add(new Sha(Type.CardPai.Sha,
				Type.CardPaiClass.MeiHua, 9, R.drawable.cp_mh_9_sha));
		this.cardPaiPool.add(new Sha(Type.CardPai.Sha,
				Type.CardPaiClass.MeiHua, 10, R.drawable.cp_mh_10_sha));
		this.cardPaiPool.add(new Sha(Type.CardPai.Sha,
				Type.CardPaiClass.MeiHua, 10, R.drawable.cp_mh_10_sha));
		this.cardPaiPool.add(new Sha(Type.CardPai.Sha,
				Type.CardPaiClass.MeiHua, 11, R.drawable.cp_mh_j_sha));
		this.cardPaiPool.add(new Sha(Type.CardPai.Sha,
				Type.CardPaiClass.MeiHua, 11, R.drawable.cp_mh_j_sha));

		this.cardPaiPool.add(new HuoSha(Type.CardPai.HuoSha,
				Type.CardPaiClass.FangPian, 4, R.drawable.cp_fp_4_huosha));
		this.cardPaiPool.add(new HuoSha(Type.CardPai.HuoSha,
				Type.CardPaiClass.FangPian, 5, R.drawable.cp_fp_5_huosha));
		this.cardPaiPool.add(new HuoSha(Type.CardPai.HuoSha,
				Type.CardPaiClass.HongTao, 4, R.drawable.cp_hot_4_huosha));
		this.cardPaiPool.add(new HuoSha(Type.CardPai.HuoSha,
				Type.CardPaiClass.HongTao, 7, R.drawable.cp_hot_7_huosha));
		this.cardPaiPool.add(new HuoSha(Type.CardPai.HuoSha,
				Type.CardPaiClass.HongTao, 10, R.drawable.cp_hot_10_huosha));

		this.cardPaiPool.add(new LeiSha(Type.CardPai.LeiSha,
				Type.CardPaiClass.HeiTao, 4, R.drawable.cp_ht_4_leisha));
		this.cardPaiPool.add(new LeiSha(Type.CardPai.LeiSha,
				Type.CardPaiClass.HeiTao, 5, R.drawable.cp_ht_5_leisha));
		this.cardPaiPool.add(new LeiSha(Type.CardPai.LeiSha,
				Type.CardPaiClass.HeiTao, 6, R.drawable.cp_ht_6_leisha));
		this.cardPaiPool.add(new LeiSha(Type.CardPai.LeiSha,
				Type.CardPaiClass.HeiTao, 7, R.drawable.cp_ht_7_leisha));
		this.cardPaiPool.add(new LeiSha(Type.CardPai.LeiSha,
				Type.CardPaiClass.HeiTao, 8, R.drawable.cp_ht_8_leisha));

		this.cardPaiPool.add(new LeiSha(Type.CardPai.LeiSha,
				Type.CardPaiClass.MeiHua, 5, R.drawable.cp_mh_5_leisha));
		this.cardPaiPool.add(new LeiSha(Type.CardPai.LeiSha,
				Type.CardPaiClass.MeiHua, 6, R.drawable.cp_mh_6_leisha));
		this.cardPaiPool.add(new LeiSha(Type.CardPai.LeiSha,
				Type.CardPaiClass.MeiHua, 7, R.drawable.cp_mh_7_leisha));
		this.cardPaiPool.add(new LeiSha(Type.CardPai.LeiSha,
				Type.CardPaiClass.MeiHua, 8, R.drawable.cp_mh_8_leisha));

		this.cardPaiPool.add(new Shan(Type.CardPai.Shan,
				Type.CardPaiClass.FangPian, 6, R.drawable.cp_fp_6_shan));
		this.cardPaiPool.add(new Shan(Type.CardPai.Shan,
				Type.CardPaiClass.FangPian, 7, R.drawable.cp_fp_7_shan));
		this.cardPaiPool.add(new Shan(Type.CardPai.Shan,
				Type.CardPaiClass.FangPian, 8, R.drawable.cp_fp_8_shan));
		this.cardPaiPool.add(new Shan(Type.CardPai.Shan,
				Type.CardPaiClass.FangPian, 10, R.drawable.cp_fp_10_shan));
		this.cardPaiPool.add(new Shan(Type.CardPai.Shan,
				Type.CardPaiClass.FangPian, 11, R.drawable.cp_fp_j_shan));
		this.cardPaiPool.add(new Shan(Type.CardPai.Shan,
				Type.CardPaiClass.FangPian, 2, R.drawable.cp_fp_2_shan));
		this.cardPaiPool.add(new Shan(Type.CardPai.Shan,
				Type.CardPaiClass.FangPian, 2, R.drawable.cp_fp_2_shan));
		this.cardPaiPool.add(new Shan(Type.CardPai.Shan,
				Type.CardPaiClass.FangPian, 3, R.drawable.cp_fp_3_shan));
		this.cardPaiPool.add(new Shan(Type.CardPai.Shan,
				Type.CardPaiClass.FangPian, 4, R.drawable.cp_fp_4_shan));
		this.cardPaiPool.add(new Shan(Type.CardPai.Shan,
				Type.CardPaiClass.FangPian, 5, R.drawable.cp_fp_5_shan));
		this.cardPaiPool.add(new Shan(Type.CardPai.Shan,
				Type.CardPaiClass.FangPian, 6, R.drawable.cp_fp_6_shan));
		this.cardPaiPool.add(new Shan(Type.CardPai.Shan,
				Type.CardPaiClass.FangPian, 7, R.drawable.cp_fp_7_shan));
		this.cardPaiPool.add(new Shan(Type.CardPai.Shan,
				Type.CardPaiClass.FangPian, 8, R.drawable.cp_fp_8_shan));
		this.cardPaiPool.add(new Shan(Type.CardPai.Shan,
				Type.CardPaiClass.FangPian, 9, R.drawable.cp_fp_9_shan));
		this.cardPaiPool.add(new Shan(Type.CardPai.Shan,
				Type.CardPaiClass.FangPian, 10, R.drawable.cp_fp_10_shan));
		this.cardPaiPool.add(new Shan(Type.CardPai.Shan,
				Type.CardPaiClass.FangPian, 11, R.drawable.cp_fp_j_shan));
		this.cardPaiPool.add(new Shan(Type.CardPai.Shan,
				Type.CardPaiClass.FangPian, 11, R.drawable.cp_fp_j_shan));

		this.cardPaiPool.add(new Shan(Type.CardPai.Shan,
				Type.CardPaiClass.HongTao, 8, R.drawable.cp_hot_8_shan));
		this.cardPaiPool.add(new Shan(Type.CardPai.Shan,
				Type.CardPaiClass.HongTao, 9, R.drawable.cp_hot_9_shan));
		this.cardPaiPool.add(new Shan(Type.CardPai.Shan,
				Type.CardPaiClass.HongTao, 11, R.drawable.cp_hot_j_shan));
		this.cardPaiPool.add(new Shan(Type.CardPai.Shan,
				Type.CardPaiClass.HongTao, 12, R.drawable.cp_hot_q_shan));
		this.cardPaiPool.add(new Shan(Type.CardPai.Shan,
				Type.CardPaiClass.HongTao, 2, R.drawable.cp_hot_2_shan));
		this.cardPaiPool.add(new Shan(Type.CardPai.Shan,
				Type.CardPaiClass.HongTao, 2, R.drawable.cp_hot_2_shan));
		this.cardPaiPool.add(new Shan(Type.CardPai.Shan,
				Type.CardPaiClass.HongTao, 13, R.drawable.cp_hot_k_shan));

		this.cardPaiPool.add(new Tao(Type.CardPai.Tao,
				Type.CardPaiClass.FangPian, 2, R.drawable.cp_fp_2_tao));
		this.cardPaiPool.add(new Tao(Type.CardPai.Tao,
				Type.CardPaiClass.FangPian, 3, R.drawable.cp_fp_3_tao));
		this.cardPaiPool.add(new Tao(Type.CardPai.Tao,
				Type.CardPaiClass.FangPian, 12, R.drawable.cp_fp_q_tao));
		this.cardPaiPool.add(new Tao(Type.CardPai.Tao,
				Type.CardPaiClass.HongTao, 3, R.drawable.cp_hot_3_tao));
		this.cardPaiPool.add(new Tao(Type.CardPai.Tao,
				Type.CardPaiClass.HongTao, 4, R.drawable.cp_hot_4_tao));
		this.cardPaiPool.add(new Tao(Type.CardPai.Tao,
				Type.CardPaiClass.HongTao, 5, R.drawable.cp_hot_5_tao));
		this.cardPaiPool.add(new Tao(Type.CardPai.Tao,
				Type.CardPaiClass.HongTao, 6, R.drawable.cp_hot_6_tao));
		this.cardPaiPool.add(new Tao(Type.CardPai.Tao,
				Type.CardPaiClass.HongTao, 6, R.drawable.cp_hot_6_tao));
		this.cardPaiPool.add(new Tao(Type.CardPai.Tao,
				Type.CardPaiClass.HongTao, 7, R.drawable.cp_hot_7_tao));
		this.cardPaiPool.add(new Tao(Type.CardPai.Tao,
				Type.CardPaiClass.HongTao, 8, R.drawable.cp_hot_8_tao));
		this.cardPaiPool.add(new Tao(Type.CardPai.Tao,
				Type.CardPaiClass.HongTao, 9, R.drawable.cp_hot_9_tao));
		this.cardPaiPool.add(new Tao(Type.CardPai.Tao,
				Type.CardPaiClass.HongTao, 12, R.drawable.cp_hot_q_tao));

		this.cardPaiPool.add(new BingLiangCunDuan(
				Type.CardPai.BingLiangCunDuan, Type.CardPaiClass.HeiTao, 10,
				R.drawable.cp_ht_10_bingliangcunduan,
				Type.JinNangApplyTo.distance_1));
		this.cardPaiPool.add(new BingLiangCunDuan(
				Type.CardPai.BingLiangCunDuan, Type.CardPaiClass.MeiHua, 4,
				R.drawable.cp_mh_4_bingliangcunduan,
				Type.JinNangApplyTo.distance_1));

		this.cardPaiPool.add(new GuoHeChaiQiao(Type.CardPai.GuoHeChaiQiao,
				Type.CardPaiClass.HeiTao, 3, R.drawable.cp_ht_3_guohechaiqiao,
				Type.JinNangApplyTo.anyone));
		this.cardPaiPool.add(new GuoHeChaiQiao(Type.CardPai.GuoHeChaiQiao,
				Type.CardPaiClass.HeiTao, 4, R.drawable.cp_ht_4_guohechaiqiao,
				Type.JinNangApplyTo.anyone));
		this.cardPaiPool.add(new GuoHeChaiQiao(Type.CardPai.GuoHeChaiQiao,
				Type.CardPaiClass.HeiTao, 12, R.drawable.cp_ht_q_guohechaiqiao,
				Type.JinNangApplyTo.anyone));
		this.cardPaiPool.add(new GuoHeChaiQiao(Type.CardPai.GuoHeChaiQiao,
				Type.CardPaiClass.HongTao, 12,
				R.drawable.cp_hot_q_guohechaiqiao, Type.JinNangApplyTo.anyone));
		this.cardPaiPool.add(new GuoHeChaiQiao(Type.CardPai.GuoHeChaiQiao,
				Type.CardPaiClass.MeiHua, 3, R.drawable.cp_mh_3_guohechaiqiao,
				Type.JinNangApplyTo.anyone));
		this.cardPaiPool.add(new GuoHeChaiQiao(Type.CardPai.GuoHeChaiQiao,
				Type.CardPaiClass.MeiHua, 4, R.drawable.cp_mh_4_guohechaiqiao,
				Type.JinNangApplyTo.anyone));

		this.cardPaiPool.add(new HuoGong(Type.CardPai.HuoGong,
				Type.CardPaiClass.FangPian, 12, R.drawable.cp_fp_q_huogong,
				Type.JinNangApplyTo.anyone));
		this.cardPaiPool.add(new HuoGong(Type.CardPai.HuoGong,
				Type.CardPaiClass.HongTao, 2, R.drawable.cp_hot_2_huogong,
				Type.JinNangApplyTo.anyone));
		this.cardPaiPool.add(new HuoGong(Type.CardPai.HuoGong,
				Type.CardPaiClass.HongTao, 3, R.drawable.cp_hot_3_huogong,
				Type.JinNangApplyTo.anyone));

		this.cardPaiPool.add(new JieDaoShaRen(Type.CardPai.JieDaoShaRen,
				Type.CardPaiClass.MeiHua, 12, R.drawable.cp_mh_q_jiedaosharen,
				Type.JinNangApplyTo.anyone));
		this.cardPaiPool.add(new JieDaoShaRen(Type.CardPai.JieDaoShaRen,
				Type.CardPaiClass.MeiHua, 13, R.drawable.cp_mh_k_jiedaosharen,
				Type.JinNangApplyTo.anyone));

		this.cardPaiPool.add(new JueDou(Type.CardPai.JueDou,
				Type.CardPaiClass.FangPian, 1, R.drawable.cp_fp_1_juedou,
				Type.JinNangApplyTo.anyone));
		this.cardPaiPool.add(new JueDou(Type.CardPai.JueDou,
				Type.CardPaiClass.HeiTao, 1, R.drawable.cp_ht_1_juedou,
				Type.JinNangApplyTo.anyone));
		this.cardPaiPool.add(new JueDou(Type.CardPai.JueDou,
				Type.CardPaiClass.MeiHua, 1, R.drawable.cp_mh_1_juedou,
				Type.JinNangApplyTo.anyone));

		this.cardPaiPool.add(new LeBuShiShu(Type.CardPai.LeBuShiShu,
				Type.CardPaiClass.HeiTao, 6, R.drawable.cp_ht_6_lebushishu,
				Type.JinNangApplyTo.anyone));
		this.cardPaiPool.add(new LeBuShiShu(Type.CardPai.LeBuShiShu,
				Type.CardPaiClass.HongTao, 6, R.drawable.cp_hot_6_lebushishu,
				Type.JinNangApplyTo.anyone));
		this.cardPaiPool.add(new LeBuShiShu(Type.CardPai.LeBuShiShu,
				Type.CardPaiClass.MeiHua, 6, R.drawable.cp_mh_6_lebushishu,
				Type.JinNangApplyTo.anyone));

		this.cardPaiPool.add(new NanManRuQin(Type.CardPai.NanManRuQin,
				Type.CardPaiClass.HeiTao, 7, R.drawable.cp_ht_7_nannamruqin,
				Type.JinNangApplyTo.all));
		this.cardPaiPool.add(new NanManRuQin(Type.CardPai.NanManRuQin,
				Type.CardPaiClass.HeiTao, 13, R.drawable.cp_ht_k_nannamruqin,
				Type.JinNangApplyTo.all));
		this.cardPaiPool.add(new NanManRuQin(Type.CardPai.NanManRuQin,
				Type.CardPaiClass.MeiHua, 7, R.drawable.cp_mh_7_nannamruqin,
				Type.JinNangApplyTo.all));

		this.cardPaiPool.add(new ShanDian(Type.CardPai.ShanDian,
				Type.CardPaiClass.HeiTao, 1, R.drawable.cp_ht_1_shandian,
				Type.JinNangApplyTo.self));
		this.cardPaiPool.add(new ShanDian(Type.CardPai.ShanDian,
				Type.CardPaiClass.HongTao, 12, R.drawable.cp_hot_q_shandian,
				Type.JinNangApplyTo.self));

		this.cardPaiPool.add(new ShunShouQuanYang(
				Type.CardPai.ShunShouQuanYang, Type.CardPaiClass.FangPian, 3,
				R.drawable.cp_fp_3_shunshouquanyang,
				Type.JinNangApplyTo.distance_1));
		this.cardPaiPool.add(new ShunShouQuanYang(
				Type.CardPai.ShunShouQuanYang, Type.CardPaiClass.FangPian, 4,
				R.drawable.cp_fp_4_shunshouquanyang,
				Type.JinNangApplyTo.distance_1));
		this.cardPaiPool.add(new ShunShouQuanYang(
				Type.CardPai.ShunShouQuanYang, Type.CardPaiClass.HeiTao, 3,
				R.drawable.cp_ht_3_shunshouquanyang,
				Type.JinNangApplyTo.distance_1));
		this.cardPaiPool.add(new ShunShouQuanYang(
				Type.CardPai.ShunShouQuanYang, Type.CardPaiClass.HeiTao, 4,
				R.drawable.cp_ht_4_shunshouquanyang,
				Type.JinNangApplyTo.distance_1));
		this.cardPaiPool.add(new ShunShouQuanYang(
				Type.CardPai.ShunShouQuanYang, Type.CardPaiClass.HeiTao, 11,
				R.drawable.cp_ht_j_shunshouquanyang,
				Type.JinNangApplyTo.distance_1));

		this.cardPaiPool.add(new TaoYuanJieYi(Type.CardPai.TaoYuanJieYi,
				Type.CardPaiClass.HongTao, 1, R.drawable.cp_hot_1_taoyuanjieyi,
				Type.JinNangApplyTo.all));

		this.cardPaiPool.add(new TieSuoLianHuan(Type.CardPai.TieSuoLianHuan,
				Type.CardPaiClass.HeiTao, 11,
				R.drawable.cp_ht_j_tiesuolianhuan, Type.JinNangApplyTo.anyone));
		this.cardPaiPool.add(new TieSuoLianHuan(Type.CardPai.TieSuoLianHuan,
				Type.CardPaiClass.HeiTao, 12,
				R.drawable.cp_ht_q_tiesuolianhuan, Type.JinNangApplyTo.anyone));
		this.cardPaiPool
				.add(new TieSuoLianHuan(Type.CardPai.TieSuoLianHuan,
						Type.CardPaiClass.MeiHua, 10,
						R.drawable.cp_mh_10_tiesuolianhuan,
						Type.JinNangApplyTo.anyone));
		this.cardPaiPool.add(new TieSuoLianHuan(Type.CardPai.TieSuoLianHuan,
				Type.CardPaiClass.MeiHua, 11,
				R.drawable.cp_mh_j_tiesuolianhuan, Type.JinNangApplyTo.anyone));
		this.cardPaiPool.add(new TieSuoLianHuan(Type.CardPai.TieSuoLianHuan,
				Type.CardPaiClass.MeiHua, 12,
				R.drawable.cp_mh_q_tiesuolianhuan, Type.JinNangApplyTo.anyone));
		this.cardPaiPool.add(new TieSuoLianHuan(Type.CardPai.TieSuoLianHuan,
				Type.CardPaiClass.MeiHua, 13,
				R.drawable.cp_mh_k_tiesuolianhuan, Type.JinNangApplyTo.anyone));

		this.cardPaiPool.add(new WanJianQiFa(Type.CardPai.WanJianQiFa,
				Type.CardPaiClass.HongTao, 1, R.drawable.cp_hot_1_wanjianqifa,
				Type.JinNangApplyTo.all));

		this.cardPaiPool.add(new WuXieKeJi(Type.CardPai.WuXieKeJi,
				Type.CardPaiClass.HeiTao, 11, R.drawable.cp_ht_j_wuxiekeji,
				Type.JinNangApplyTo.anyone));
		this.cardPaiPool.add(new WuXieKeJi(Type.CardPai.WuXieKeJi,
				Type.CardPaiClass.HeiTao, 13, R.drawable.cp_ht_k_wuxiekeji,
				Type.JinNangApplyTo.anyone));
		this.cardPaiPool.add(new WuXieKeJi(Type.CardPai.WuXieKeJi,
				Type.CardPaiClass.HongTao, 1, R.drawable.cp_hot_1_wuxiekeji,
				Type.JinNangApplyTo.anyone));
		this.cardPaiPool.add(new WuXieKeJi(Type.CardPai.WuXieKeJi,
				Type.CardPaiClass.HongTao, 13, R.drawable.cp_hot_k_wuxiekeji,
				Type.JinNangApplyTo.anyone));
		this.cardPaiPool.add(new WuXieKeJi(Type.CardPai.WuXieKeJi,
				Type.CardPaiClass.MeiHua, 12, R.drawable.cp_mh_q_wuxiekeji,
				Type.JinNangApplyTo.anyone));
		this.cardPaiPool.add(new WuXieKeJi(Type.CardPai.WuXieKeJi,
				Type.CardPaiClass.MeiHua, 13, R.drawable.cp_mh_k_wuxiekeji,
				Type.JinNangApplyTo.anyone));

		this.cardPaiPool.add(new WuZhongShengYou(Type.CardPai.WuZhongShengYou,
				Type.CardPaiClass.HongTao, 7,
				R.drawable.cp_hot_7_wuzhongshengyou, Type.JinNangApplyTo.self));
		this.cardPaiPool.add(new WuZhongShengYou(Type.CardPai.WuZhongShengYou,
				Type.CardPaiClass.HongTao, 8,
				R.drawable.cp_hot_8_wuzhongshengyou, Type.JinNangApplyTo.self));
		this.cardPaiPool.add(new WuZhongShengYou(Type.CardPai.WuZhongShengYou,
				Type.CardPaiClass.HongTao, 9,
				R.drawable.cp_hot_9_wuzhongshengyou, Type.JinNangApplyTo.self));
		this.cardPaiPool.add(new WuZhongShengYou(Type.CardPai.WuZhongShengYou,
				Type.CardPaiClass.HongTao, 11,
				R.drawable.cp_hot_j_wuzhongshengyou, Type.JinNangApplyTo.self));

		this.cardPaiPool.add(new WuGuFengDeng(Type.CardPai.WuGuFengDeng,
				Type.CardPaiClass.HongTao, 3, R.drawable.cp_hot_3_wugufengdeng,
				Type.JinNangApplyTo.all));
		this.cardPaiPool.add(new WuGuFengDeng(Type.CardPai.WuGuFengDeng,
				Type.CardPaiClass.HongTao, 4, R.drawable.cp_hot_4_wugufengdeng,
				Type.JinNangApplyTo.all));

		this.cardPaiPool.add(new BaGuaZhen(Type.CardPai.BaGuaZhen,
				Type.CardPaiClass.HeiTao, 2, R.drawable.cp_ht_2_baguazhen));
		this.cardPaiPool.add(new BaGuaZhen(Type.CardPai.BaGuaZhen,
				Type.CardPaiClass.MeiHua, 2, R.drawable.cp_mh_2_baguazhen));

		this.cardPaiPool.add(new BaiYinShiZi(Type.CardPai.BaiYinShiZi,
				Type.CardPaiClass.MeiHua, 1, R.drawable.cp_mh_1_baiyinshizi));

		this.cardPaiPool.add(new RenWangDun(Type.CardPai.RenWangDun,
				Type.CardPaiClass.MeiHua, 2, R.drawable.cp_mh_2_renwangdun));

		this.cardPaiPool.add(new TengJia(Type.CardPai.TengJia,
				Type.CardPaiClass.MeiHua, 2, R.drawable.cp_mh_2_tengjia));
		this.cardPaiPool.add(new TengJia(Type.CardPai.TengJia,
				Type.CardPaiClass.HeiTao, 2, R.drawable.cp_ht_2_tengjia));

		this.cardPaiPool.add(new ChiTu(Type.CardPai.ChiTu,
				Type.CardPaiClass.HongTao, 5, R.drawable.cp_hot_5_chitu, -1));

		this.cardPaiPool.add(new DaWan(Type.CardPai.DaWan,
				Type.CardPaiClass.HeiTao, 13, R.drawable.cp_ht_k_dayuan, -1));

		this.cardPaiPool.add(new DiXu(Type.CardPai.DiXu,
				Type.CardPaiClass.MeiHua, 5, R.drawable.cp_mh_5_dilu, +1));

		this.cardPaiPool.add(new HuaJu(Type.CardPai.HuaJu,
				Type.CardPaiClass.FangPian, 13, R.drawable.cp_fp_k_hualiu, +1));

		this.cardPaiPool.add(new JueYing(Type.CardPai.JueYing,
				Type.CardPaiClass.HeiTao, 5, R.drawable.cp_ht_5_jueying, +1));

		this.cardPaiPool.add(new ZhuaHuangFeiDian(
				Type.CardPai.ZhuaHuangFeiDian, Type.CardPaiClass.HongTao, 13,
				R.drawable.cp_hot_k_zhuahuangfeidian, +1));

		this.cardPaiPool.add(new ZhiXin(Type.CardPai.ZhiXin,
				Type.CardPaiClass.FangPian, 13, R.drawable.cp_fp_k_zhixin, -1));

		this.cardPaiPool.add(new ChiXiongShuanPeiJian(
				Type.CardPai.ChiXiongShuanPeiJian, Type.CardPaiClass.HeiTao, 2,
				R.drawable.cp_ht_2_chixiongshuanggujian, 2));

		this.cardPaiPool.add(new FangTianHuaJi(Type.CardPai.FangTianHuaJi,
				Type.CardPaiClass.FangPian, 12,
				R.drawable.cp_fp_q_fangtianhuaji, 4));

		this.cardPaiPool.add(new GuDingDao(Type.CardPai.GuDingDao,
				Type.CardPaiClass.HeiTao, 1, R.drawable.cp_ht_1_gudingdao, 2));

		this.cardPaiPool
				.add(new GuanShiFu(Type.CardPai.GuanShiFu,
						Type.CardPaiClass.FangPian, 5,
						R.drawable.cp_fp_5_guanshifu, 3));

		this.cardPaiPool
				.add(new BingHanJian(Type.CardPai.BingHanJian,
						Type.CardPaiClass.HeiTao, 2,
						R.drawable.cp_ht_2_hanbingjian, 2));

		this.cardPaiPool
				.add(new QiLingGong(Type.CardPai.QiLingGong,
						Type.CardPaiClass.HongTao, 5,
						R.drawable.cp_hot_5_qilinggong, 5));

		this.cardPaiPool
				.add(new QingHongJian(Type.CardPai.QingHongJian,
						Type.CardPaiClass.HeiTao, 6,
						R.drawable.cp_ht_6_qinghongjian, 2));

		this.cardPaiPool.add(new QingLongYuanYueDao(
				Type.CardPai.QingLongYuanYueDao, Type.CardPaiClass.HeiTao, 5,
				R.drawable.cp_ht_5_qinglongyuanyuedao, 3));

		// this.cardPaiPool.add(new YinYueQiang(Type.CardPai.YinYueQiang,
		// Type.CardPaiClass.FangPian, 12, 3));

		this.cardPaiPool
				.add(new QiXingDao(Type.CardPai.QiXingDao,
						Type.CardPaiClass.FangPian, 12,
						R.drawable.cp_fp_q_qixingdao, 1));

		this.cardPaiPool.add(new ZhangBaSheMao(Type.CardPai.ZhangBaSheMao,
				Type.CardPaiClass.HeiTao, 12, R.drawable.cp_ht_q_zhangbashemao,
				3));

		this.cardPaiPool.add(new ZhuQueYuShan(Type.CardPai.ZhuQueYuShan,
				Type.CardPaiClass.FangPian, 1, R.drawable.cp_fp_1_zhuqueshanyu,
				4));

		this.cardPaiPool.add(new ZhuGeLianNu(Type.CardPai.ZhuGeLianNu,
				Type.CardPaiClass.FangPian, 1, R.drawable.cp_fp_1_zhugeliannv,
				1));
		this.cardPaiPool
				.add(new ZhuGeLianNu(Type.CardPai.ZhuGeLianNu,
						Type.CardPaiClass.MeiHua, 1,
						R.drawable.cp_mh_1_zhugeliannv, 1));
	}

	public CardPai getCardPaiByName(Type.CardPai name) {
		for (int i = 0; i < this.cardPaiPool.size(); i++) {
			CardPai cp = this.cardPaiPool.get(i);
			if (cp.name == name)
				return cp;
		}
		return null;
	}

	public CardPai getCardPaiByNameCPSNumber(String name,
			Type.CardPaiClass cps, int number) {
		for (int i = 0; i < this.cardPaiPool.size(); i++) {
			CardPai cp = this.cardPaiPool.get(i);
			if (name.equals(cp.name.toString()) && cp.clas == cps
					&& cp.number == number)
				return cp;
		}
		return null;
	}

	public void initCardPais() {
		// empty first
		while (this.cardPais.size() > 0) {
			((CardPai) this.cardPais.get(0)).reset();
			this.cardPais.remove(0);
		}
		// init
		for (int i = 0; i < this.cardPaiPool.size(); i++) {
			CardPai cp = (CardPai) this.cardPaiPool.get(i);
			cp.reset();
			cp.gameApp = this.gameApp;
			cp.cpState = Type.CPState.PaiDui;
			this.cardPais.add(cp);
		}
	}

	public void initCardPaisForQGame() {
		// empty
		while (this.cardPais.size() > 0) {
			((CardPai) this.cardPais.get(0)).reset();
			this.cardPais.remove(0);
		}
		// add paiDuiTopCPs to cardPais
		for (int i = 0; i < this.gameApp.settingsViewData.paiDuiTopCPs.size(); i++) {
			CardPai topCP = (CardPai) this.gameApp.settingsViewData.paiDuiTopCPs
					.get(i);
			topCP.reset();
			topCP.gameApp = this.gameApp;
			topCP.cpState = Type.CPState.PaiDui;
			this.cardPais.add(topCP);
		}

		// add CP from pool
		for (int i = 0; i < this.cardPaiPool.size(); i++) {
			CardPai cp = (CardPai) this.cardPaiPool.get(i);
			// skip the paiDuiTopCPs
			if ((cp.cpState != Type.CPState.nil)
					|| (cp.belongToWuJiang != null)) {
				continue;
			}
			// add it
			cp.reset();
			cp.gameApp = this.gameApp;
			cp.cpState = Type.CPState.PaiDui;
			this.cardPais.add(cp);

		}
	}

	public void faPai() {

		UpdateWJViewData item = new UpdateWJViewData();
		item.updateShouPaiNumber = true;

		for (int i = 0; i < this.gameApp.gameLogicData.wuJiangs.size(); i++) {
			WuJiang wj = (WuJiang) this.gameApp.gameLogicData.wuJiangs.get(i);
			// every wj has 4 shou pai
			for (int j = 0; j < 4; j++) {
				CardPai cp = this.popCardPai();
				cp.belongToWuJiang = wj;
				cp.cpState = Type.CPState.ShouPai;
				wj.shouPai.add(cp);
			}

			this.gameApp.libGameViewData.logInfo("武将" + wj.dispName + "摸起4张手牌",
					Type.logDelay.NoDelay);
			this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(wj,
					item);
		}
	}

	public void xiPai() {
		// xi pai
		int times = this.cardPais.size() - 1;
		while (times != 0) {
			double d = Math.random();
			int one = (int) (d * times);
			// swap one and times
			CardPai oneCP = (CardPai) this.cardPais.get(one);
			CardPai timeCP = (CardPai) this.cardPais.get(times);
			this.cardPais.set(times, oneCP);
			this.cardPais.set(one, timeCP);
			times--;
		}
	}

	public void xiPaiForQGame() {
		// skip the paiDuiTopCPs
		int i = this.gameApp.settingsViewData.paiDuiTopCPs.size();
		for (int j = -1 + this.cardPais.size() - i; j > 0; j--) {
			int k = (int) (Math.random() * j);
			CardPai localCardPai2 = (CardPai) this.cardPais.get(i + k);
			CardPai localCardPai1 = (CardPai) this.cardPais.get(i + j);
			this.cardPais.set(i + j, localCardPai2);
			this.cardPais.set(i + k, localCardPai1);
		}
	}

	public void debugShowCardPaiPool(String info) {
		this.gameApp.libGameViewData.fileUtil.addContent(info + "\n");
	}

	public void reallocateCardPai() {
		// before pop cardPai, check if the cardPais still have cp
		if (this.currentCP >= this.cardPais.size() - 10) {
			this.gameApp.libGameViewData.fileUtil.addContent("牌堆重构" + "\n");
			// this.debugShowCardPaiPool("重构牌堆之前:");
			this.emptyArrayList(this.cardPais);
			for (int i = 0; i < this.cardPaiPool.size(); i++) {
				CardPai cp = (CardPai) this.cardPaiPool.get(i);
				if ((cp.cpState == Type.CPState.FeiPaiDui || cp.cpState == Type.CPState.nil)
						&& cp.belongToWuJiang == null) {
					cp.reset();
					cp.gameApp = this.gameApp;
					cp.cpState = Type.CPState.PaiDui;
					this.cardPais.add(cp);
				}
			}
			// this.debugShowCardPaiPool("重构牌堆之后:");
			// xi pai
			this.xiPai();
			// this.debugShowCardPaiPool("洗牌之后:");
			// reset
			this.currentCP = 0;
		}
	}

	public void emptyArrayList(ArrayList<CardPai> list) {
		while (list.size() > 0) {
			CardPai cp = (CardPai) list.get(0);
			cp.belongToWuJiang = null;
			cp.cpState = Type.CPState.FeiPaiDui;
			list.remove(0);
		}
	}

	public CardPai popCardPai() {
		this.reallocateCardPai();
		if (this.cardPais.size() > 0) {
			while (true) {
				// search one cp which is validate
				// for GuanXing, some cp is invalidate
				CardPai cp = this.cardPais.get(this.currentCP++);
				if (cp != null && cp.name != Type.CardPai.nil
						&& cp.clas != Type.CardPaiClass.nil && cp.number != 0) {
					cp.reset();
					return cp;
				}
			}
		} else {
			this.gameApp.libGameViewData.logInfo("已经没有牌了!GameOver!",
					Type.logDelay.NoDelay);
			this.gameApp.gameLogicData.gameOver = true;
			return new CardPai(Type.CardPai.nil, Type.CardPaiClass.HeiTao, 0,
					R.drawable.card_back);
		}
	}

	// start from which WJ? this.gameApp.gameLogicData.curChuPaiWJ!
	public CardPai broadcastPanDingCardPaiEvent(WuJiang tarWJ, CardPai tarCP,
			CardPai curPDCP) {
		CardPai newPDCP = curPDCP;

		WuJiang startWJ = null;
		if (this.gameApp.gameLogicData.curChuPaiWJ != null
				&& this.gameApp.gameLogicData.curChuPaiWJ.state != Type.State.Dead) {
			startWJ = this.gameApp.gameLogicData.curChuPaiWJ;
		} else if (tarCP.belongToWuJiang != null
				&& tarCP.belongToWuJiang.state != Type.State.Dead) {
			startWJ = tarCP.belongToWuJiang;
		} else if (tarWJ != null && tarWJ.state != Type.State.Dead) {
			startWJ = tarWJ;
		} else if (this.gameApp.gameLogicData.zhuGongWuJiang != null
				&& this.gameApp.gameLogicData.zhuGongWuJiang.state != Type.State.Dead) {
			startWJ = this.gameApp.gameLogicData.zhuGongWuJiang;
		}

		if (startWJ == null)
			return curPDCP;

		// first build the pdWJ list
		ArrayList<WuJiang> pdWJs = new ArrayList<WuJiang>();
		WuJiang curWJ = startWJ;
		pdWJs.add(curWJ);
		while (!curWJ.nextOne.equals(startWJ)) {
			curWJ = curWJ.nextOne;
			pdWJs.add(curWJ);
		}

		// then broadcast event to all wj
		curWJ = startWJ;
		WuJiang prePDWJ = null;
		while (pdWJs.size() > 0 && !curWJ.equals(prePDWJ)) {
			CardPai tmpPDCP = curWJ.listenPanDingCardPaiEvent(tarWJ, tarCP,
					newPDCP);
			if (tmpPDCP != null) {
				prePDWJ = curWJ;
				newPDCP = tmpPDCP;
			} else {
				pdWJs.remove(curWJ);
			}
			curWJ = curWJ.nextOne;
		}

		return newPDCP;
	}

	// tarWJ
	public CardPai popCardPaiForPanDing(WuJiang tarWJ, CardPai tarCP) {
		// some wj will listen this event
		CardPai pdCP = this.popCardPai();
		if (pdCP != null) {
			pdCP.belongToWuJiang = null;
			pdCP.cpState = Type.CPState.FeiPaiDui;
			this.gameApp.libGameViewData.logInfo(
					tarCP.dispName + "判定牌是" + pdCP, Type.logDelay.Delay);

			// for some WJ, can change the pdcp
			pdCP = this.broadcastPanDingCardPaiEvent(tarWJ, tarCP, pdCP);

			if (tarCP.belongToWuJiang != null
					&& tarCP.belongToWuJiang instanceof GuoJia) {
				pdCP.belongToWuJiang = tarCP.belongToWuJiang;
				pdCP.cpState = Type.CPState.ShouPai;
				tarCP.belongToWuJiang.shouPai.add(pdCP);
				if (tarCP.belongToWuJiang.tuoGuan) {
					UpdateWJViewData item = new UpdateWJViewData();
					item.updateShouPaiNumber = true;
					this.gameApp.gameLogicData.wjHelper
							.updateWuJiangToLibGameView(tarCP.belongToWuJiang,
									item);
				} else {
					this.gameApp.gameLogicData.wjHelper
							.updateWJ8ShouPaiToLibGameView();
				}
				this.gameApp.libGameViewData.logInfo(
						tarCP.belongToWuJiang.dispName + "["
								+ tarCP.belongToWuJiang.jiNengN1 + "]获得判定牌",
						Type.logDelay.Delay);
			}
		}

		return pdCP;
	}

	public CardPai viewTopCardPai(int topIndex) {
		this.reallocateCardPai();
		return this.cardPais.get(this.currentCP + topIndex);
	}

	public void setTopCardPai(int topIndex, CardPai cp) {
		this.cardPais.set(this.currentCP + topIndex, cp);
	}

	public void addCardPaiToWuJiang(WuJiang srcWJ, int count) {
		for (int i = 0; i < count; i++) {
			CardPai cpx = this.popCardPai();
			cpx.belongToWuJiang = srcWJ;
			cpx.cpState = Type.CPState.ShouPai;
			srcWJ.shouPai.add(cpx);
		}

		if (srcWJ.tuoGuan) {
			// update shou pai number
			UpdateWJViewData item = new UpdateWJViewData();
			item.updateShouPaiNumber = true;
			this.gameApp.gameLogicData.wjHelper.updateWuJiangToLibGameView(
					srcWJ, item);
		} else {
			this.gameApp.gameLogicData.wjHelper.updateWJ8ShouPaiToLibGameView();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
