package com.naronco.infinityjam;

import com.deviotion.ld.eggine.graphics.Screen;
import com.deviotion.ld.eggine.math.Polygon2d;

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
			Game.instance.setScene(transitionTo, new ISceneTransition() {
				@Override
				public void render(Screen screen, double time) {
					screen.renderRectangle(0, (int)(-86 + 86 * 2 * time), 200, 86, 0x121212);
				}
			});
			return true;
		}
		return false;
	}
}
