package alben.sgs.type;

public class UpdateWJViewData {
	public boolean updateAll = false;
	public boolean updateImage = false;
	public boolean updateBlood = false;
	public boolean updateShouPaiNumber = false;
	public boolean updateRole = false;
	public boolean updateZhuangBei = false;
	public boolean updatePangDing = false;
	public boolean updateLianHuan = false;
	public boolean updateFanMian = false;

	public void reset() {
		this.updateAll = false;
		this.updateBlood = false;
		this.updateImage = false;
		this.updateLianHuan = false;
		this.updatePangDing = false;
		this.updateRole = false;
		this.updateShouPaiNumber = false;
		this.updateZhuangBei = false;
		this.updateFanMian = false;
	}
}
