package alben.sgs.common;

import java.util.ArrayList;

import alben.sgs.type.Type;
import alben.sgs.wujiang.WuJiang;

public class LoopWuJiangHelper {
	public ArrayList<WuJiang> loopWJs = new ArrayList<WuJiang>();

	public LoopWuJiangHelper(ArrayList<WuJiang> paramArrayList,
			WuJiang fromWuJiang) {
		WuJiang localWuJiang = fromWuJiang;
		if (localWuJiang.state != Type.State.Dead)
			this.loopWJs.add(localWuJiang);
		while (!localWuJiang.nextOne.equals(fromWuJiang)) {
			localWuJiang = localWuJiang.nextOne;
			if (localWuJiang.state == Type.State.Dead) {
				localWuJiang = localWuJiang.nextOne;
				continue;
			}
			this.loopWJs.add(localWuJiang);
		}
	}

	public WuJiang nextLoopWJ() {
		WuJiang localObject = null;
		while ((localObject == null) && (this.loopWJs.size() > 0)) {
			WuJiang localWuJiang = (WuJiang) this.loopWJs.get(0);
			if (localWuJiang.state == Type.State.Dead) {
				this.loopWJs.remove(0);
				continue;
			} else {
				localObject = localWuJiang;
				this.loopWJs.remove(0);
			}
		}
		return localObject;
	}
}

/*
 * Location: G:\Work\dex2jar\classes.jar Qualified Name:
 * alben.sgs.common.LoopWuJiangHelper JD-Core Version: 0.6.0
 */