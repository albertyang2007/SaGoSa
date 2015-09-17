package alben.sgs.android.mycontroller;

import alben.sgs.android.GameApp;
import alben.sgs.android.R;
import alben.sgs.cardpai.CardPai;
import alben.sgs.cardpai.instance.Jiu;
import alben.sgs.cardpai.instance.Sha;
import alben.sgs.cardpai.instance.Shan;
import alben.sgs.cardpai.instance.ZBSMSha;
import alben.sgs.type.Type;
import alben.sgs.wujiang.instance.jineng.FanJian;
import alben.sgs.wujiang.instance.jineng.GuoSe;
import alben.sgs.wujiang.instance.jineng.HuangTian;
import alben.sgs.wujiang.instance.jineng.HuoJi;
import alben.sgs.wujiang.instance.jineng.LiJian;
import alben.sgs.wujiang.instance.jineng.LianHuan;
import alben.sgs.wujiang.instance.jineng.LongDan;
import alben.sgs.wujiang.instance.jineng.LuanJi;
import alben.sgs.wujiang.instance.jineng.QiXi;
import alben.sgs.wujiang.instance.jineng.QiangXi;
import alben.sgs.wujiang.instance.jineng.QingNang;
import alben.sgs.wujiang.instance.jineng.QuHu;
import alben.sgs.wujiang.instance.jineng.TianYi;
import alben.sgs.wujiang.instance.jineng.WuSheng;
import alben.sgs.wujiang.instance.jineng.ZhiHeng;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;

public class MyFunctionButtonController implements OnClickListener {
	public GameApp gameApp;

	public MyFunctionButtonController(GameApp app) {
		this.gameApp = app;
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ChuPai: {
			if (this.gameApp.gameLogicData.selectWJAfterChuPai)
				this.handleSelectWuJiangEvent(v);
			else
				this.handleChuPaiBtnEvent(v);
			break;
		}
		case R.id.FangQi: {
			this.handleFangQiBtnEvent(v);
			break;
		}
		case R.id.QiPai: {
			this.handleQiPaiBtnEvent(v);
			break;
		}
		case R.id.TuoGuan: {
			this.handleTuoGuanBtnEvent(v);
			break;
		}
		}
	}

	public void handleChuPaiBtnEvent(View v) {
		int selectedCPNumber = gameApp.gameLogicData.myWuJiang
				.countSelectedShouPai();
		CardPai firstSelectCP = gameApp.gameLogicData.myWuJiang
				.getSelectedShouPai();

		if (selectedCPNumber != 1) {
			return;
		}

		boolean match = false;

		if (gameApp.gameLogicData.myWuJiang.state == Type.State.ChuPai
				|| gameApp.gameLogicData.myWuJiang.state == Type.State.Response) {
			// for chuPai, askForPai is set to nil
			if ((gameApp.gameLogicData.askForPai == Type.CardPai.notNil)) {
				match = true;
			}

			// for response, askForPai is set to specific
			if (gameApp.gameLogicData.askForPai == firstSelectCP.name) {
				match = true;
			}

			// for huogong, just show one card pai
			if ((gameApp.gameLogicData.askForHuaShi == Type.CardPaiClass.notNil)) {
				match = true;
			}

			if ((gameApp.gameLogicData.askForHuaShi == firstSelectCP.clas)) {
				match = true;
			}

			// for juedou, only set askForPai to Sha, but leiSha and
			// huoSha is also ok for response to juedou
			if (gameApp.gameLogicData.askForPai == Type.CardPai.Sha) {
				if (firstSelectCP instanceof Sha) {
					match = true;
				}
			}

			// LongDan
			if (gameApp.gameLogicData.askForPai == Type.CardPai.Shan) {
				if (firstSelectCP instanceof Shan) {
					match = true;
				}
			}

			// if I am deading, Jiu can save me
			if (gameApp.gameLogicData.askForPai == Type.CardPai.Tao) {
				if (gameApp.gameLogicData.myWuJiang.blood <= 0
						&& firstSelectCP instanceof Jiu) {
					match = true;
				}
			}
		}
		if (match) {
			sendMessageToUIForWakeUp();
		}
	}

	public void handleFangQiBtnEvent(View v) {
		this.removeAnyJiNengCardPai();

		if (gameApp.gameLogicData.myWuJiang.state == Type.State.ChuPai
				|| gameApp.gameLogicData.myWuJiang.state == Type.State.Response) {
			// reset all shou pai
			gameApp.gameLogicData.wjHelper.updateWJ8ShouPaiToLibGameView();

			sendMessageToUIForWakeUp();
		}
	}

	public void handleQiPaiBtnEvent(View v) {
		this.removeAnyJiNengCardPai();

		if (gameApp.gameLogicData.myWuJiang.state == Type.State.ChuPai) {
			gameApp.gameLogicData.huiHeJieSu = true;
			gameApp.gameLogicData.myWuJiang.state = Type.State.QiPai;
			sendMessageToUIForWakeUp();
			return;
		}

		if (gameApp.gameLogicData.myWuJiang.state == Type.State.QiPai) {

			if (gameApp.gameLogicData.myWuJiang.countSelectedShouPai() == this.gameApp.gameLogicData.discardShouPaiN
					|| this.gameApp.gameLogicData.discardShouPaiN <= 0) {
				sendMessageToUIForWakeUp();
			} else {
				if (this.gameApp.gameLogicData.discardShouPaiN > 0) {
					gameApp.libGameViewData.logInfo("你需要丢弃"
							+ this.gameApp.gameLogicData.discardShouPaiN
							+ "张手牌", Type.logDelay.NoDelay);
				} else {
					gameApp.libGameViewData.logInfo(
							"Error: in MyQiPaiButtonListener",
							Type.logDelay.NoDelay);
				}
			}
		}
	}

	public void handleTuoGuanBtnEvent(View v) {
		if (this.gameApp.gameLogicData.myWuJiang.tuoGuan) {
			this.gameApp.gameLogicData.myWuJiang.tuoGuan = false;
			gameApp.libGameViewData.logInfo("你退出托管模式", Type.logDelay.NoDelay);
		} else {
			this.gameApp.gameLogicData.myWuJiang.tuoGuan = true;
			gameApp.libGameViewData.logInfo("你进入托管模式", Type.logDelay.NoDelay);
		}
	}

	public void handleSelectWuJiangEvent(View v) {
		// check if select enough wj
		boolean match = false;
		if (gameApp.selectWJViewData.selectNumber == 1) {
			if (gameApp.selectWJViewData.selectedWJ1 != null) {
				match = true;
			}
		}
		if (gameApp.selectWJViewData.selectNumber == 2) {
			if (gameApp.selectWJViewData.selectedWJ1 != null
					&& gameApp.selectWJViewData.selectedWJ2 != null) {
				match = true;
			}
		}
		if (gameApp.selectWJViewData.selectedWJAtLeast1
				&& gameApp.selectWJViewData.selectedWJs.size() > 0
				&& gameApp.selectWJViewData.selectedWJs.size() <= gameApp.selectWJViewData.selectNumber) {
			match = true;
		}

		if (gameApp.selectWJViewData.allowSelectedZeroWJ
				&& gameApp.selectWJViewData.selectedWJs.size() == 0) {
			// for XunYu: jieMing
			match = true;
		}

		if (match) {
			sendMessageToUIForWakeUp();
		} else {
			gameApp.libGameViewData.logInfo("你必须选择"
					+ gameApp.selectWJViewData.selectNumber + "个武将",
					Type.logDelay.NoDelay);
		}
	}

	public void handleSelectCPAndWJEvent(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			v.setBackgroundDrawable(this.gameApp.getResources().getDrawable(
					R.drawable.btn_bg1_down));
			break;
		}
		case MotionEvent.ACTION_UP: {
			v.setBackgroundDrawable(this.gameApp.getResources().getDrawable(
					R.drawable.btn_bg1));

			// check if select enough cp
			boolean match = false;
			if (gameApp.selectCPData.selectCPNumber == 1) {
				if (gameApp.selectCPData.selectedCP1 != null) {
					match = true;
				}
			} else if (gameApp.selectCPData.selectCPNumber == 2) {
				if (gameApp.selectCPData.selectedCP1 != null
						&& gameApp.selectCPData.selectedCP2 != null) {
					match = true;
				}
			} else if (gameApp.selectCPData.selectCPNumber == 3) {
				if (gameApp.selectCPData.selectedCP1 != null
						&& gameApp.selectCPData.selectedCP2 != null
						&& gameApp.selectCPData.selectedCP3 != null) {
					match = true;
				}
			}

			if (match) {
				sendMessageToUIForWakeUp();
			} else {
				gameApp.libGameViewData.logInfo("你必须选择"
						+ gameApp.selectCPData.selectCPNumber + "个卡牌",
						Type.logDelay.NoDelay);
			}
		}
		default:
			break;
		}
	}

	public void handleWuJiangJiNengBtnEvent(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			v.setBackgroundDrawable(this.gameApp.getResources().getDrawable(
					R.drawable.btn_bg1_down));
			break;
		}
		case MotionEvent.ACTION_UP: {
			v.setBackgroundDrawable(this.gameApp.getResources().getDrawable(
					R.drawable.btn_bg1));

			this.gameApp.gameLogicData.myWuJiang
					.handleJiNengBtnEvent(v.getId());

			break;
		}
		default:
			break;
		}
	}

	public void enterSelectWuJiangMode() {
		this.gameApp.gameLogicData.selectWJAfterChuPai = true;

		// disable all button except ok btn
		this.gameApp.libGameViewData.mFangQi.setBackgroundDrawable(this.gameApp
				.getResources().getDrawable(R.drawable.btn_bg1));
		this.gameApp.libGameViewData.mFangQi.setEnabled(false);

		this.gameApp.libGameViewData.mQiPai.setBackgroundDrawable(this.gameApp
				.getResources().getDrawable(R.drawable.btn_bg1));
		this.gameApp.libGameViewData.mQiPai.setEnabled(false);

		// also disable all shou pai
	}

	public void exitSelectWuJiangMode() {
		this.gameApp.gameLogicData.selectWJAfterChuPai = false;
		// re-enable other button
		this.gameApp.libGameViewData.mFangQi.setBackgroundDrawable(this.gameApp
				.getResources().getDrawable(R.drawable.btn_bg1));
		this.gameApp.libGameViewData.mFangQi.setEnabled(true);

		this.gameApp.libGameViewData.mQiPai.setBackgroundDrawable(this.gameApp
				.getResources().getDrawable(R.drawable.btn_bg1));
		this.gameApp.libGameViewData.mQiPai.setEnabled(true);
	}

	public void sendMessageToUIForWakeUp() {
		this.gameApp.gameLogicData.userInterface.sendMessageToUIForWakeUp();
	}

	// remove some nil cardpai, those cp are create temp, not
	// get from pool, such as ZBSMSha. This code will hit only
	// if use zhangbashemao generate one Sha, but cancel when select
	// tarWJ
	public void removeAnyJiNengCardPai() {
		int ij = 0;
		int spSize = this.gameApp.gameLogicData.myWuJiang.shouPai.size();
		while (ij < spSize) {
			if (this.gameApp.gameLogicData.myWuJiang.shouPai.get(ij).name == Type.CardPai.WJJiNeng
					|| this.gameApp.gameLogicData.myWuJiang.shouPai.get(ij) instanceof ZBSMSha
					|| this.gameApp.gameLogicData.myWuJiang.shouPai.get(ij) instanceof GuoSe
					|| this.gameApp.gameLogicData.myWuJiang.shouPai.get(ij) instanceof QiangXi
					|| this.gameApp.gameLogicData.myWuJiang.shouPai.get(ij) instanceof QiXi
					|| this.gameApp.gameLogicData.myWuJiang.shouPai.get(ij) instanceof WuSheng
					|| this.gameApp.gameLogicData.myWuJiang.shouPai.get(ij) instanceof QingNang
					|| this.gameApp.gameLogicData.myWuJiang.shouPai.get(ij) instanceof LianHuan
					|| this.gameApp.gameLogicData.myWuJiang.shouPai.get(ij) instanceof ZhiHeng
					|| this.gameApp.gameLogicData.myWuJiang.shouPai.get(ij) instanceof HuoJi
					|| this.gameApp.gameLogicData.myWuJiang.shouPai.get(ij) instanceof LiJian
					|| this.gameApp.gameLogicData.myWuJiang.shouPai.get(ij) instanceof TianYi
					|| this.gameApp.gameLogicData.myWuJiang.shouPai.get(ij) instanceof QuHu
					|| this.gameApp.gameLogicData.myWuJiang.shouPai.get(ij) instanceof LuanJi
					|| this.gameApp.gameLogicData.myWuJiang.shouPai.get(ij) instanceof HuangTian
					|| this.gameApp.gameLogicData.myWuJiang.shouPai.get(ij) instanceof LongDan
					|| this.gameApp.gameLogicData.myWuJiang.shouPai.get(ij) instanceof FanJian) {
				this.gameApp.gameLogicData.myWuJiang.shouPai.remove(ij);
				spSize = this.gameApp.gameLogicData.myWuJiang.shouPai.size();
			}
			ij++;
		}
	}
}
