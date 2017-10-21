package com.naronco.infinityjam.scenes;

import com.deviotion.ld.eggine.graphics.Screen;
import com.deviotion.ld.eggine.graphics.SpriteSheet;
import com.deviotion.ld.eggine.math.Vector2d;

public class SlotMachineRoll {
	private static final double ACCELERATION = 0.3;

	private SlotMachineSymbol[] slots;
	private SpriteSheet slotsSheet;
	private double duration = 3.0;
	private double offset = 0;
	private double startOffset = 0, targetOffset = 0;
	private double time = 0;
	private boolean rolling = false;

	public SlotMachineRoll(SpriteSheet slotsSheet, SlotMachineSymbol[] slots) {
		this.slotsSheet = slotsSheet;
		this.slots = slots.clone();
	}

	public void roll(SlotMachineSymbol expectedResult) {
		if (rolling)
			return;
		int fullHeight = slots.length * 22;
		while (offset >= fullHeight)
			offset -= fullHeight;

		int index = 0;
		for (int i = 0; i < slots.length; ++i) {
			if (expectedResult == slots[i]) {
				index = i;
				break;
			}
		}

		targetOffset = (slots.length * 5 - index) * 22;
		startOffset = offset;
		time = 0;
		rolling = true;
	}

	public void update() {
		if (rolling) {
			time += 1 / 30.0;
			if (time >= duration) {
				time = duration;
				rolling = false;
			}

			double p = time / duration;
			p = 0.5 + 0.5 * Math.sin(Math.PI * (p - 0.5));

			offset = startOffset + (targetOffset - startOffset) * p;
		}
	}

	public void render(Screen screen, int x, int y) {
		int clipHeight = 59;
		int fullHeight = slots.length * 22;

		for (int s = 0; s < slots.length; ++s) {
			int yy = y + s * 22 + (int)offset + 22;
			while (yy >= clipHeight) {
				yy -= fullHeight;
			}

			Vector2d tileVector = slotsSheet.getTileVector(slots[s].ordinal());
			screen.renderClippedSprite(x, yy, (int)tileVector.getX(), (int)tileVector.getY(), 20, 20, 0, 0, 200, clipHeight, slotsSheet.getSprite());
		}
	}

	public SlotMachineSymbol[] getSlots() {
		return slots;
	}

	public boolean isRolling() {
		return rolling;
	}
}
