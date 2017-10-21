package com.naronco.infinityjam;

import com.deviotion.ld.eggine.math.Polygon2d;

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
		if (Game.instance.isQuestFinished(quest))
			super.stepOn(x, y);
		else if (unfulfillMsg != null)
			Game.instance.showMessage(unfulfillMsg);
		return false;
	}
}
