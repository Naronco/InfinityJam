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
		for (int i = children.length - 1; i >= 0; i--)
			Game.instance.pushDialog(children[i]);
		return false;
	}
}
