package alben.sgs.android.data;

import java.util.ArrayList;

import alben.sgs.wujiang.WuJiang;

public class SelectWuJiangData {
	// to be select
	public WuJiang[] wuJiangs = { null, null, null, null, null, null, null,
			null };
	// selected one
	public WuJiang selectedWJ1 = null;
	public WuJiang selectedWJ2 = null;
	public ArrayList<WuJiang> selectedWJs = new ArrayList<WuJiang>();

	public boolean selectedWJAtLeast1 = false;
	public boolean allowSelectedZeroWJ = false;
	public int selectNumber = 0;

	public void reset() {
		for (int i = 0; i < this.wuJiangs.length; i++) {
			this.wuJiangs[i] = null;
		}

		while (this.selectedWJs.size() > 0) {
			this.selectedWJs.remove(0);
		}
		this.selectedWJ1 = null;
		this.selectedWJ2 = null;
		this.selectNumber = 0;
		this.selectedWJAtLeast1 = false;
		this.allowSelectedZeroWJ = false;
	}
}
