package com.naronco.infinityjam;

import com.deviotion.ld.eggine.math.Polygon2d;
import com.naronco.infinityjam.transitions.BlackOverlayTransition;

public class QuestExitStepArea extends ExitStepArea {
	Class<?> quest;
	String unfulfillMsg;

	public QuestExitStepArea(Class<?> quest, String unfulfillMsg, Polygon2d area, IScene transitionTo) {
		super(area, transitionTo);
		this.quest = quest;
		this.unfulfillMsg = unfulfillMsg;
	}

	@Override
	public boolean stepOn(int x, int y) {
		if (area.intersects(x, y)) {
			if (Game.instance.isQuestFinished(quest))
				Game.instance.setScene(transitionTo, new BlackOverlayTransition());
			else if (unfulfillMsg != null)
				Game.instance.showMessage(unfulfillMsg);
			return true;
		}
		return false;
	}
}
