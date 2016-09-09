package alben.sgs.qgame;

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringWriter;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import alben.sgs.android.GameApp;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.FangJuCardPai;
import alben.sgs.cardpai.MaCardPai;
import alben.sgs.cardpai.WuQiCardPai;
import alben.sgs.common.Helper;
import alben.sgs.type.Type;
import alben.sgs.wujiang.WuJiang;
import android.content.res.XmlResourceParser;
import android.os.Environment;

public class QGameXMLHelper {
	public GameApp gameApp;

	public QGameXMLHelper(GameApp paramGameApp) {
		this.gameApp = paramGameApp;
	}

	public String generateGameWJToXMLCtx() {
		StringWriter localStringWriter = new StringWriter();
		try {
			XmlSerializer xmlSer = XmlPullParserFactory.newInstance()
					.newSerializer();
			xmlSer.setOutput(localStringWriter);

			xmlSer.startDocument("utf-8", true);
			// root element start
			xmlSer.startTag(null, "QGame");

			xmlSer.startTag(null, "Title");
			xmlSer.text("A Q Game Title");
			xmlSer.endTag(null, "Title");

			xmlSer.startTag(null, "Description");
			xmlSer.text("A Q Game Description");
			xmlSer.endTag(null, "Description");

			xmlSer.startTag(null, "SystemSetting");
			xmlSer.attribute(null, "round", "1");
			xmlSer.endTag(null, "SystemSetting");

			// paiDuiTopCPs start
			xmlSer.startTag(null, "paiDuiTopCPs");
			for (int i = 0; i < this.gameApp.settingsViewData.paiDuiTopCPs
					.size(); i++) {
				CardPai topCP = this.gameApp.settingsViewData.paiDuiTopCPs
						.get(i);
				xmlSer.startTag(null, "paiDuiTopCP");
				xmlSer.attribute(null, "name", topCP.name + "");
				xmlSer.attribute(null, "huashi", topCP.clas + "");
				xmlSer.attribute(null, "number", topCP.number + "");
				xmlSer.endTag(null, "paiDuiTopCP");
			}
			xmlSer.endTag(null, "paiDuiTopCPs");
			// paiDuiTopCPs end

			// WuJiangs start
			xmlSer.startTag(null, "WuJiangs");
			for (int i = 0; i < this.gameApp.gameLogicData.wuJiangs.size(); i++) {
				WuJiang wj = this.gameApp.gameLogicData.wuJiangs.get(i);
				// one wj start
				xmlSer.startTag(null, "WuJiang");
				xmlSer.attribute(null, "name", wj.name + "");
				xmlSer.attribute(null, "imageViewIndex", wj.imageViewIndex + "");
				xmlSer.attribute(null, "state", wj.state + "");
				xmlSer.attribute(null, "role", wj.role + "");
				xmlSer.attribute(null, "blood", wj.blood + "");
				xmlSer.attribute(null, "lianHuan", wj.lianHuan + "");
				// zhuangBei start
				xmlSer.startTag(null, "zhuangBei");
				if (wj.zhuangBei.wuQi != null) {
					xmlSer.startTag(null, "wuQi");
					xmlSer.attribute(null, "name", wj.zhuangBei.wuQi.name + "");
					xmlSer.attribute(null, "huashi", wj.zhuangBei.wuQi.clas
							+ "");
					xmlSer.attribute(null, "number", wj.zhuangBei.wuQi.number
							+ "");
					xmlSer.endTag(null, "wuQi");
				}
				if (wj.zhuangBei.fangJu != null) {
					xmlSer.startTag(null, "fangJu");
					xmlSer.attribute(null, "name", wj.zhuangBei.fangJu.name
							+ "");
					xmlSer.attribute(null, "huashi", wj.zhuangBei.fangJu.clas
							+ "");
					xmlSer.attribute(null, "number", wj.zhuangBei.fangJu.number
							+ "");
					xmlSer.endTag(null, "fangJu");
				}
				if (wj.zhuangBei.jiaYiMa != null) {
					xmlSer.startTag(null, "jiaYiMa");
					xmlSer.attribute(null, "name", wj.zhuangBei.jiaYiMa.name
							+ "");
					xmlSer.attribute(null, "huashi", wj.zhuangBei.jiaYiMa.clas
							+ "");
					xmlSer.attribute(null, "number",
							wj.zhuangBei.jiaYiMa.number + "");
					xmlSer.endTag(null, "jiaYiMa");
				}
				if (wj.zhuangBei.jianYiMa != null) {
					xmlSer.startTag(null, "jianYiMa");
					xmlSer.attribute(null, "name", wj.zhuangBei.jianYiMa.name
							+ "");
					xmlSer.attribute(null, "huashi", wj.zhuangBei.jianYiMa.clas
							+ "");
					xmlSer.attribute(null, "number",
							wj.zhuangBei.jianYiMa.number + "");
					xmlSer.endTag(null, "jianYiMa");
				}
				xmlSer.endTag(null, "zhuangBei");
				// zhuangBei end

				// shouPais start
				xmlSer.startTag(null, "shouPais");
				for (int j = 0; j < wj.shouPai.size(); j++) {
					CardPai spCP = wj.shouPai.get(j);
					xmlSer.startTag(null, "shouPai");
					xmlSer.attribute(null, "name", spCP.name + "");
					xmlSer.attribute(null, "huashi", spCP.clas + "");
					xmlSer.attribute(null, "number", spCP.number + "");
					xmlSer.endTag(null, "shouPai");
				}
				xmlSer.endTag(null, "shouPais");
				// shouPais end

				// panDingPais start
				xmlSer.startTag(null, "panDingPais");
				for (int j = 0; j < wj.panDingPai.size(); j++) {
					CardPai pdCP = wj.panDingPai.get(j);
					xmlSer.startTag(null, "panDingPai");
					xmlSer.attribute(null, "name", pdCP.name + "");
					xmlSer.attribute(null, "huashi", pdCP.clas + "");
					xmlSer.attribute(null, "number", pdCP.number + "");
					xmlSer.endTag(null, "panDingPai");
				}
				xmlSer.endTag(null, "panDingPais");
				// panDingPais end

				// one wj end
				xmlSer.endTag(null, "WuJiang");
			}// for

			xmlSer.endTag(null, "WuJiangs");
			// WuJiangs end

			xmlSer.endTag(null, "QGame");
			// root element end
			xmlSer.endDocument();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//
		return localStringWriter.toString();
	}

	public void loadQGameFromXMLFile(int qgameXMLID) {
		XmlResourceParser xrp = this.gameApp.getResources().getXml(qgameXMLID);
		try {
			WuJiang wj = null;
			while (xrp.getEventType() != XmlResourceParser.END_DOCUMENT) {
				switch (xrp.getEventType()) {
				case XmlResourceParser.START_TAG: {
					String tagName = xrp.getName();
					if (tagName.equals("Title")) {
						xrp.next();
						this.gameApp.settingsViewData.qGameTitle = xrp
								.getText();
					} else if (tagName.equals("Description")) {
						xrp.next();
						this.gameApp.settingsViewData.qGameDescription = xrp
								.getText();
					} else if (tagName.equals("SystemSetting")) {
						this.gameApp.settingsViewData.round = Integer
								.parseInt(xrp.getAttributeValue(null, "round"));
					} else if (tagName.equals("paiDuiTopCP")) {
						// one top CP
						String cpName = xrp.getAttributeValue(null, "name");
						Type.CardPaiClass cpHS = Helper
								.convertToCardPaiClass(xrp.getAttributeValue(
										null, "huashi"));
						int cpNumber = Integer.parseInt(xrp.getAttributeValue(
								null, "number"));
						CardPai topCP = this.gameApp.gameLogicData.cpHelper
								.getCardPaiByNameCPSNumber(cpName, cpHS,
										cpNumber);
						this.gameApp.settingsViewData.paiDuiTopCPs.add(topCP);
					} else if (wj == null && tagName.equals("WuJiang")) {
						// get attribute
						String wjName = xrp.getAttributeValue(null, "name");
						wj = this.gameApp.gameLogicData.wjHelper
								.getWuJiangByStrName(wjName);
						if (wj != null) {
							wj.reset();
							// get other attribute
							wj.imageViewIndex = Integer.parseInt(xrp
									.getAttributeValue(null, "imageViewIndex"));
							wj.state = Helper.convertToWJState(xrp
									.getAttributeValue(null, "state"));
							wj.role = Helper.convertToWJRole(xrp
									.getAttributeValue(null, "role"));
							wj.blood = Integer.parseInt(xrp.getAttributeValue(
									null, "blood"));
							wj.lianHuan = Boolean.parseBoolean(xrp
									.getAttributeValue(null, "lianHuan"));
						}
					} else if (wj != null && tagName.equals("wuQi")) {
						String zbName = xrp.getAttributeValue(null, "name");
						Type.CardPaiClass zbHS = Helper
								.convertToCardPaiClass(xrp.getAttributeValue(
										null, "huashi"));
						int zbNumber = Integer.parseInt(xrp.getAttributeValue(
								null, "number"));
						wj.zhuangBei.wuQi = ((WuQiCardPai) this.gameApp.gameLogicData.cpHelper
								.getCardPaiByNameCPSNumber(zbName, zbHS,
										zbNumber));
					} else if (wj != null && tagName.equals("fangJu")) {
						String zbName = xrp.getAttributeValue(null, "name");
						Type.CardPaiClass zbHS = Helper
								.convertToCardPaiClass(xrp.getAttributeValue(
										null, "huashi"));
						int zbNumber = Integer.parseInt(xrp.getAttributeValue(
								null, "number"));
						wj.zhuangBei.fangJu = ((FangJuCardPai) this.gameApp.gameLogicData.cpHelper
								.getCardPaiByNameCPSNumber(zbName, zbHS,
										zbNumber));
					} else if (wj != null && tagName.equals("jiaYiMa")) {
						String zbName = xrp.getAttributeValue(null, "name");
						Type.CardPaiClass zbHS = Helper
								.convertToCardPaiClass(xrp.getAttributeValue(
										null, "huashi"));
						int zbNumber = Integer.parseInt(xrp.getAttributeValue(
								null, "number"));
						wj.zhuangBei.jiaYiMa = ((MaCardPai) this.gameApp.gameLogicData.cpHelper
								.getCardPaiByNameCPSNumber(zbName, zbHS,
										zbNumber));
					} else if (wj != null && tagName.equals("jianYiMa")) {
						String zbName = xrp.getAttributeValue(null, "name");
						Type.CardPaiClass zbHS = Helper
								.convertToCardPaiClass(xrp.getAttributeValue(
										null, "huashi"));
						int zbNumber = Integer.parseInt(xrp.getAttributeValue(
								null, "number"));
						wj.zhuangBei.jianYiMa = ((MaCardPai) this.gameApp.gameLogicData.cpHelper
								.getCardPaiByNameCPSNumber(zbName, zbHS,
										zbNumber));
					} else if (wj != null && tagName.equals("shouPai")) {
						// get shoupai CP
						String spName = xrp.getAttributeValue(null, "name");
						Type.CardPaiClass spHS = Helper
								.convertToCardPaiClass(xrp.getAttributeValue(
										null, "huashi"));
						int spNumber = Integer.parseInt(xrp.getAttributeValue(
								null, "number"));
						CardPai spCP = this.gameApp.gameLogicData.cpHelper
								.getCardPaiByNameCPSNumber(spName, spHS,
										spNumber);
						wj.shouPai.add(spCP);
					} else if (wj != null && tagName.equals("panDingPai")) {
						// get panding CP
						String pdName = xrp.getAttributeValue(null, "name");
						Type.CardPaiClass pdHS = Helper
								.convertToCardPaiClass(xrp.getAttributeValue(
										null, "huashi"));
						int pdNumber = Integer.parseInt(xrp.getAttributeValue(
								null, "number"));
						CardPai pdCP = this.gameApp.gameLogicData.cpHelper
								.getCardPaiByNameCPSNumber(pdName, pdHS,
										pdNumber);
						wj.panDingPai.add(pdCP);
					}
					break;
				}
				case XmlPullParser.END_TAG: {
					String tagName = xrp.getName();
					if (tagName.equals("WuJiang")) {
						if (wj != null) {
							// add one wj to list
							this.gameApp.gameLogicData.wuJiangs.add(wj);
							// empty wj to next one
							wj = null;
						}
					}
					break;
				}
				case XmlPullParser.TEXT: {
					break;
				}
				default:
				}
				xrp.next();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// write file to sd card
	public void writeFileToSD(String fileName, String fileContent) {
		if (!Environment.getExternalStorageState().equals("mounted"))
			return;

		try {
			File localFile = new File("/sdcard/sgs/");
			Object localObject = new File("/sdcard/sgs/" + fileName);
			if (!localFile.exists())
				localFile.mkdir();
			if (!((File) localObject).exists())
				((File) localObject).createNewFile();
			localObject = new FileOutputStream((File) localObject);
			((FileOutputStream) localObject).write(fileContent.getBytes());
			((FileOutputStream) localObject).flush();
			((FileOutputStream) localObject).close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// write file to system, which location the apk install
	public void writeFileToSys(String paramString1, String paramString2) {
		try {
			FileOutputStream localFileOutputStream = this.gameApp
					.openFileOutput(paramString1, 2);
			localFileOutputStream.write(paramString2.getBytes());
			localFileOutputStream.flush();
			localFileOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}