package alben.sgs.type;

public class PanDingResult {
	public boolean leBuShiShuOK = false;
	public boolean bingLiangCunDunOK = false;
	public boolean checkGameOver = false;

	public void reset() {
		this.leBuShiShuOK = false;
		this.bingLiangCunDunOK = false;
		this.checkGameOver = false;
	}
}
