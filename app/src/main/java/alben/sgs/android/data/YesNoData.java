package alben.sgs.android.data;

public class YesNoData {
	public String okTxt = "OK";
	public String cancelTxt = "Cancel";
	public String genInfo = "";
	public boolean result = true;// ok

	public void reset() {
		this.okTxt = "OK";
		this.cancelTxt = "Cancel";
		this.genInfo = "";
		this.result = true;
	}
}
