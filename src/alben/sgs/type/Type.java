package alben.sgs.type;

public class Type {
	public enum Country {
		Nil, Wei, Shu, Wu, Qun, Shen
	}

	public enum Role {
		Nil, ZhuGong, ZhongChen, FanZei, NeiJian
	}

	public enum Sex {
		nil, man, woman
	}

	public enum CardPaiClass {
		nil, notNil, HongTao, HeiTao, FangPian, MeiHua
	}

	public enum CardPai {
		nil, notNil, WJJiNeng, Sha, Shan, Tao, Jiu, HuoSha, LeiSha, BingLiangCunDuan, GuoHeChaiQiao, HuoGong, JieDaoShaRen, JueDou, LeBuShiShu, NanManRuQin, ShanDian, ShunShouQuanYang, TaoYuanJieYi, TieSuoLianHuan, WanJianQiFa, WuXieKeJi, WuZhongShengYou, BaGuaZhen, BaiYinShiZi, TengJia, ChiTu, DaWan, DiXu, HuaJu, JueYing, ZhuaHuangFeiDian, ZhiXin, ChiXiongShuanPeiJian, FangTianHuaJi, GuDingDao, GuanShiFu, BingHanJian, QiLingGong, QingHongJian, QingLongYuanYueDao, YinYueQiang, ZhangBaSheMao, ZhuQueYuShan, ZhuGeLianNu, WuGuFengDeng, RenWangDun, QiXingDao
	}

	public enum JinNangApplyTo {
		nil, distance_1, anyone, all, self
	}

	public enum WuJiang {
		nil, DiaoChan, HuaTuo, LvBu, GuanYu, HuangYueYing, LiuBei, MaChao, ZhangFei, ZhaoYun, ZhuGeLiang, CaoCao, GuoJia, ShiMaYi, XiaHouTing, XuZhu, ZhangLiao, ZhenJi, DaQiao, GanNing, HuangGai, LuSun, LvMeng, SunQuan, SunShangXiang, ZhouYu, ZhangJiao, HuangZhong, WeiYuan, CaoRen, XiaHouYan, XiaoQiao, PengDe, YanLiangWenCou, YuanShao, PengTong, WoLong, DianWei, XunYu, TaiShiChi
	}

	public enum State {
		nil, PanDing, MoPai, ChuPai, QiPai, Response, Dead
	}

	public enum CPState {
		nil, pandDingPai, wuQiPai, fangJuPai, jiaYiMaPai, jianYiMaPai, ShouPai, PaiDui, FeiPaiDui
	}

	public enum Relationship {
		nil, friend, opponent, neuter
	}

	public enum GameType {
		Nil, g_1v1, g_3v3, g_3_people, g_4_people, g_5_people, g_6_people, g_7_people, g_81_people, g_82_people
	}

	public enum DisplayMode {
		Small, Middle, Large, Huge
	}

	public enum QGameSetupStep {
		Nil, WuJiang, ShouPai, ZhuangBei, WuQi, FangJu, JiaMa, JianMa, Blood, Role, PanDing, Link, PaiDui, System
	}

	public enum logDelay {
		NoDelay, HalfDelay, Delay, DoubleDelay
	}
}
