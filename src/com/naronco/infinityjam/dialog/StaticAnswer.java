package com.naronco.infinityjam.dialog;

import com.naronco.infinityjam.Game;

public class StaticAnswer implements IAnswer {
	public String text;
	public Dialog child;

	public StaticAnswer(String text, Dialog child) {
		this.text = text;
		this.child = child;
	}

	@Override
	public String getTitle() {
		return text;
	}

	@Override
	public boolean run() {
		Game.instance.showMessage(text);
		Game.instance.pushDialog(child);
		return false;
	}
}
