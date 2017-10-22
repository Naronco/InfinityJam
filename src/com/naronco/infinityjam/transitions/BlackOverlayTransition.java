package com.naronco.infinityjam.transitions;

import com.deviotion.ld.eggine.graphics.Screen;
import com.naronco.infinityjam.ISceneTransition;

public class BlackOverlayTransition implements ISceneTransition {
	@Override
	public void render(Screen screen, double time) {
		screen.renderRectangle(0, (int)(-86 + 86 * 2 * time), 200, 86, 0x121212);
	}
}
