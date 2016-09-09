package alben.sgs.qgame;

import java.util.ArrayList;

import alben.sgs.android.R;

public class QGameDataBase {
	public ArrayList<QGame> qgameList = new ArrayList<QGame>();

	public QGameDataBase() {
		this.qgameList.add(new QGame(R.xml.qgame1, "许褚1到17血", "人物:许褚,小乔,大乔,A"));
		this.qgameList.add(new QGame(R.xml.qgame2, "周瑜1桃3血", "人物:周瑜,孙权"));
		this.qgameList.add(new QGame(R.xml.qgame3, "周泰狂骨加血", "人物:周泰,小乔,司马懿"));
	}
}

/*
 * Location: G:\Work\dex2jar\classes.jar Qualified Name:
 * alben.sgs.qgame.QGameDataBase JD-Core Version: 0.6.0
 */