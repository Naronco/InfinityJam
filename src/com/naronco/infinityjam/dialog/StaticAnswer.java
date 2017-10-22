package com.naronco.infinityjam.dialog;

import com.naronco.infinityjam.Game;

public class StaticAnswer implements IAnswer {
	public String text;
	public Dialog[] children;

	public StaticAnswer(String text, Dialog... children) {
		this.text = text;
		this.children = children;
	}

	@Override
	public String getTitle() {
		return text;
	}

	@Override
	public boolean run() {
		Game.instance.showMessage(text);
		for (Dialog d : children)
			Game.instance.pushDialog(d);
		return false;
	}
}
