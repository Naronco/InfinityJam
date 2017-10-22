package com.naronco.infinityjam;

import com.deviotion.ld.eggine.graphics.Screen;
import com.deviotion.ld.eggine.math.Polygon2d;
import com.naronco.infinityjam.transitions.BlackOverlayTransition;

public class ExitStepArea implements StepArea {
	public Polygon2d area;
	public IScene transitionTo;

	public ExitStepArea(Polygon2d area, IScene transitionTo) {
		this.area = area;
		this.transitionTo = transitionTo;
	}

	@Override
	public boolean stepOn(int x, int y) {
		if (area.intersects(x, y)) {
			Game.instance.setScene(transitionTo, new BlackOverlayTransition());
			return true;
		}
		return false;
	}
}
