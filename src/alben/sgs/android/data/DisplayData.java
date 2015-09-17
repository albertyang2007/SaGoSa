package alben.sgs.android.data;

import android.util.DisplayMetrics;

public class DisplayData {
	public int screenWidth = 240;// ��Ļ��ȣ����أ� 240
	public int screenHeight = 320;// ��Ļ�߶ȣ����أ� 320
	public float density = 0.75f; // ��Ļ�ܶȣ�0.75 / 1.0 / 1.5�� 0.75
	public int densityDpi = 120; // ��Ļ�ܶ�DPI��120 / 160 / 240��120

	public void setDisplay(int sW, int sH, float den, int Dpi) {
		this.screenWidth = sW;
		this.screenHeight = sH;
		this.density = den;
		this.densityDpi = Dpi;
	}

	public void setDisplay(DisplayMetrics dm) {
		this.screenWidth = (dm.widthPixels != 0) ? dm.widthPixels : 240;
		this.screenHeight = (dm.heightPixels != 0) ? dm.heightPixels : 320;
		this.density = (dm.density != 0.0f) ? dm.density : 0.75f;
		this.densityDpi = (dm.densityDpi != 0) ? dm.densityDpi : 120;
	}
}
