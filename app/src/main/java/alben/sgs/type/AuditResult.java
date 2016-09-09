package alben.sgs.type;

public class AuditResult {
	public int shouPai_belongToWuJiang_mismatch = 0;
	public int shouPai_cpState_mismatch = 0;
	public int zhuangBei_cpState_mismatch = 0;
	public int panDing_cpState_mismatch = 0;

	public boolean hasMismatch() {
		if (shouPai_belongToWuJiang_mismatch != 0)
			return true;
		if (shouPai_cpState_mismatch != 0)
			return true;
		if (zhuangBei_cpState_mismatch != 0)
			return true;
		if (panDing_cpState_mismatch != 0)
			return true;
		return false;
	}

	public String report() {
		String reportStr = "";
		if (shouPai_belongToWuJiang_mismatch != 0) {
			reportStr += "SP_BWJ_MisN=" + shouPai_belongToWuJiang_mismatch
					+ "\n";
		}
		if (shouPai_cpState_mismatch != 0) {
			reportStr += "SP_CPS_MisN=" + shouPai_cpState_mismatch + "\n";
		}
		if (zhuangBei_cpState_mismatch != 0) {
			reportStr += "ZB_CPS_MisN=" + zhuangBei_cpState_mismatch + "\n";
		}
		if (panDing_cpState_mismatch != 0) {
			reportStr += "PD_CPS_MisN=" + panDing_cpState_mismatch + "\n";
		}
		return reportStr;
	}
}
