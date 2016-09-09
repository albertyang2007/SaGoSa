package alben.sgs.android.data;

import java.util.ArrayList;

import alben.sgs.cardpai.CardPai;
import alben.sgs.type.Type;

public class SettingsData {
	public int delayMillionSeconds = 2000;
	public Type.GameType gameType = Type.GameType.g_1v1;
	public ArrayList<CardPai> paiDuiTopCPs = new ArrayList<CardPai>();
	public Type.Country qGameCountry = Type.Country.Nil;
	public String qGameDescription = "";
	public Type.Role qGameRole = Type.Role.Nil;
	public Type.QGameSetupStep qGameSetupSetp = Type.QGameSetupStep.Nil;
	public String qGameTitle = "";
	public int qgameXMLID = 0;
	public int round = 1;
}
