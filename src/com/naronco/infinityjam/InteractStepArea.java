package com.naronco.infinityjam;

import com.deviotion.ld.eggine.math.Polygon2d;

public class InteractStepArea implements StepArea {
	public Polygon2d area;
	public Interactable trigger;

	public InteractStepArea(Polygon2d area, Interactable trigger) {
		this.area = area;
		this.trigger = trigger;
	}

	@Override
	public boolean stepOn(int x, int y) {
		if (area.intersects(x, y)) {
			trigger.use(-1, -1);
			return true;
		}
		return false;
	}
}
