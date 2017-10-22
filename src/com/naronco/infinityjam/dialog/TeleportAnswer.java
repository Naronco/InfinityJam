package com.naronco.infinityjam.dialog;

import com.naronco.infinityjam.Game;
import com.naronco.infinityjam.IScene;

public class TeleportAnswer implements IAnswer {
	public String text;
	public IScene scene;
	public Dialog[] children;

	public TeleportAnswer(String text, IScene scene, Dialog... children) {
		this.text = text;
		this.children = children;
		this.scene = scene;
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
		Game.instance.setScene(scene);
		return false;
	}
}
