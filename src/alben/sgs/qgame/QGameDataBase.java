package alben.sgs.qgame;

import java.util.ArrayList;

import alben.sgs.android.R;

public class QGameDataBase {
	public ArrayList<QGame> qgameList = new ArrayList<QGame>();

	public QGameDataBase() {
		this.qgameList.add(new QGame(R.xml.qgame1, "����1��17Ѫ", "����:����,С��,����,A"));
		this.qgameList.add(new QGame(R.xml.qgame2, "���1��3Ѫ", "����:���,��Ȩ"));
		this.qgameList.add(new QGame(R.xml.qgame3, "��̩��Ǽ�Ѫ", "����:��̩,С��,˾��ܲ"));
	}
}

/*
 * Location: G:\Work\dex2jar\classes.jar Qualified Name:
 * alben.sgs.qgame.QGameDataBase JD-Core Version: 0.6.0
 */