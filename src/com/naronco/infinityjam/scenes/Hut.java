package com.naronco.infinityjam.scenes;

import com.deviotion.ld.eggine.graphics.Sprite;
import com.deviotion.ld.eggine.math.Rectangle2d;
import com.deviotion.ld.eggine.math.Vector2d;

public class Hut {
	private Sprite sprite;
	private Vector2d position;

	private boolean animating = false;
	private boolean swapping;
	private Vector2d startPosition;
	private Vector2d targetPosition;
	private boolean foregroundPath;
	private int ticksSinceAnimStart;
	private int durationInTicks = 7;

	public Hut(Sprite sprite, Vector2d position) {
		this.sprite = sprite;
		this.position = position;
	}

	public void moveTo(Vector2d targetPosition) {
		this.startPosition = position.copy();
		this.targetPosition = targetPosition.copy();
		this.ticksSinceAnimStart = 0;
		this.animating = true;
		this.swapping = false;
	}

	public void swapTo(Vector2d targetPosition, boolean foregroundPath) {
		this.startPosition = position.copy();
		this.targetPosition = targetPosition.copy();
		this.foregroundPath = foregroundPath;
		this.ticksSinceAnimStart = 0;
		this.animating = true;
		this.swapping = true;
	}

	public void update() {
		if (animating) {
			++ticksSinceAnimStart;

			if (ticksSinceAnimStart >= durationInTicks) {
				ticksSinceAnimStart = durationInTicks;
				animating = false;
			}

			double p = ticksSinceAnimStart / (double) durationInTicks;

			if (swapping) {
				p = 0.5 + 0.5 * Math.sin(Math.PI * (p - 0.5));

				position.setX(startPosition.getX() + (targetPosition.getX() - startPosition.getX()) * p);
				position.setY(startPosition.getY() + Math.sqrt(0.5 * 0.5 - (p - 0.5) * (p - 0.5)) * 30 * (foregroundPath ? 1 : -1));
			} else {
				position.setX(startPosition.getX() + (targetPosition.getX() - startPosition.getX()) * p);
				position.setY(startPosition.getY() + (targetPosition.getY() - startPosition.getY()) * p);
			}
		}
	}

	public Vector2d getPosition() {
		return position;
	}

	public boolean isAnimating() {
		return animating;
	}

	public Rectangle2d getRectangle() {
		return new Rectangle2d(new Vector2d(position.getX() - sprite.getDimension().getWidth() * 0.5, position.getY() - sprite.getDimension().getHeight()), sprite.getDimension());
	}

	public boolean takesForegroundPath() {
		return foregroundPath;
	}
}
