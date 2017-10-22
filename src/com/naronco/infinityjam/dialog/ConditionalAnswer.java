package com.naronco.infinityjam.dialog;

import com.naronco.infinityjam.Game;

public class ConditionalAnswer implements IAnswer {
	private String title;
	private IAnswer onTrue, onFalse;
	private ICondition condition;

	public ConditionalAnswer(String title, ICondition condition, IAnswer onTrue, IAnswer onFalse) {
		this.title = title;
		this.condition = condition;
		this.onTrue = onTrue;
		this.onFalse = onFalse;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public boolean run() {
		if (condition.decide()) {
			return onTrue.run();
		} else {
			return onFalse.run();
		}
	}
}
