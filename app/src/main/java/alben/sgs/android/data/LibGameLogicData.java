package alben.sgs.android.data;

import java.util.ArrayList;

import alben.sgs.android.io.SettingIOHelper;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.CardPaiHelper;
import alben.sgs.qgame.QGameDataBase;
import alben.sgs.type.Type;
import alben.sgs.ui.UserInterface;
import alben.sgs.wujiang.WuJiang;
import alben.sgs.wujiang.WuJiangHelper;

public class LibGameLogicData {
	public ArrayList<WuJiang> wuJiangs = new ArrayList<WuJiang>();
	public int numberWJ = 0;
	public boolean huiHeJieSu = false;
	public boolean yesNoAnswer = false;
	public boolean gameOver = false;
	public boolean someWJDeadDuringChuPai = false;
	public boolean userExit = false;

	// tiesuolianhuan
	public boolean selectWJAfterChuPai = false;

	public CardPaiHelper cpHelper = null;
	public WuJiangHelper wjHelper = null;
	public UserInterface userInterface = null;

	public Type.Role myRole = Type.Role.Nil;
	public WuJiang myWuJiang = null;
	public WuJiang zhuGongWuJiang = null;
	public WuJiang curChuPaiWJ = null;
	public CardPai curChuPaiCP = null;

	public int zhuGongMaxBlood = 0;
	public int discardShouPaiN = 0;

	public Type.CardPai askForPai = Type.CardPai.nil;
	public Type.CardPaiClass askForHuaShi = Type.CardPaiClass.nil;

	public QGameDataBase qgameDB = null;
	public SettingIOHelper settingsHelper = null;

	public String exceptionTrack = "";

	public void reset() {
		while (this.wuJiangs.size() > 0) {
			((WuJiang) this.wuJiangs.get(0)).reset();
			this.wuJiangs.remove(0);
		}
		this.numberWJ = 0;
		this.huiHeJieSu = false;
		this.yesNoAnswer = false;
		this.selectWJAfterChuPai = false;
		this.someWJDeadDuringChuPai = false;
		this.myWuJiang = null;
		this.zhuGongWuJiang = null;
		this.askForHuaShi = Type.CardPaiClass.nil;
		this.askForPai = Type.CardPai.nil;
		this.gameOver = false;
		this.curChuPaiWJ = null;
		this.curChuPaiCP = null;
		this.zhuGongMaxBlood = 0;
		this.exceptionTrack = "";
		this.discardShouPaiN = 0;
		this.userExit = false;
	}

}
