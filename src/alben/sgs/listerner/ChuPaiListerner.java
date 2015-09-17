package alben.sgs.listerner;

import java.util.EventListener;

import alben.sgs.event.ChuPaiEvent;

public interface ChuPaiListerner extends EventListener {
	public void chuPaiEvent(ChuPaiEvent cpEvent);

	public void askForWuXieKeJiEvent(ChuPaiEvent cpEvent);

	public void askForTaoEvent(ChuPaiEvent cpEvent);
}
