package alben.sgs.android.io;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import alben.sgs.android.GameApp;
import android.content.Context;

public class FileUtil {
	public Context ctx = null;
	private GameApp gameApp = null;
	private String fileName = "sgs.log";
	private int mode = Context.MODE_WORLD_READABLE
			+ Context.MODE_WORLD_WRITEABLE + Context.MODE_APPEND;
	private FileOutputStream fos = null;

	// 实际存储路径是:/data/data/com.android.sgs/files/sgs.log

	public FileUtil(GameApp app) {
		this.gameApp = app;
	}

	public void openFile() {
		if (this.fos == null) {
			try {
				this.fos = ctx.openFileOutput(fileName, mode);
				// this.addContent("test:"+new Date());
			} catch (Exception e) {
				this.gameApp.libGameViewData.appendLog("打开文件异常:" + e);
			}
		}
	}

	public void closeFile() {
		try {
			if (this.fos != null)
				this.fos.close();
		} catch (Exception e) {
			this.gameApp.libGameViewData.appendLog("关闭文件异常:" + e);
		}
	}

	public void addContent(String content) {
		try {
			if (this.fos == null) {
				this.openFile();
			} else {
				this.fos.write(content.getBytes());
				this.fos.flush();
			}
		} catch (Exception e) {
			//this.gameApp.libGameViewData.appendLog("写文件异常:" + e);
		}
	}

	public String readFiles() {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			FileInputStream fis = ctx.openFileInput(fileName);
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = fis.read(buffer)) != -1) {
				baos.write(buffer, 0, len);
			}
			String content = baos.toString();
			fis.close();
			baos.close();
			return content;
		} catch (Exception e) {
			this.gameApp.libGameViewData.appendLog("读文件异常:" + e);
		}
		return "";
	}

}
