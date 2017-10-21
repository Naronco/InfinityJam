package com.naronco.infinityjam;

import com.deviotion.ld.eggine.graphics.Sprite;
import com.deviotion.ld.eggine.graphics.SpriteAnimation;
import com.deviotion.ld.eggine.graphics.SpriteSheet;
import com.deviotion.ld.eggine.math.Dimension2d;
import com.deviotion.ld.eggine.math.Vector2d;
import com.naronco.infinityjam.scenes.PointAndClickScene;

import java.io.File;

public class Character extends SpriteAnimation {
	public Character(int x, int y) {
		super(new SpriteSheet(new Sprite(new File("res/char.png")), new Dimension2d(19, 35)), 0, 0, 10);
		position = target = new Vector2d(x, y);
	}

	public void walkTo(Vector2d v) {
		if (target.subtract(v).getLengthSquared() < 16) {
			running = true;
			animationFps = 20;
		}
		else {
			target = v;
			running = false;
			animationFps = 10;
		}
	}

	public void walkTo(int x, int y) {
		walkTo(new Vector2d(x, y));
	}

	public Vector2d getPosition() {
		return position;
	}

	public Vector2d getSpritePosition() {
		return position.subtract(new Vector2d(10, 33));
	}

	@Override
	public boolean nextFrame() {
		if (walking) {
			setStartTile(1);
			setEndTile(4);
		} else {
			setStartTile(0);
			setEndTile(0);
		}
		super.nextFrame();
		Vector2d diff = target.subtract(position);
		if (diff.getLengthSquared() > 1) {
			walking = true;
			wasWalking = true;
			diff = diff.normalized().multiply(running ? 1.2 : 0.6);
			position = position.add(diff);
			flipX = diff.getX() > 0;
		} else walking = false;
		return false;
	}

	public boolean walkingEnded() {
		if (!walking && wasWalking) {
			wasWalking = false;
			return true;
		}
		return false;
	}

	public void teleport(Vector2d pos) {
		position = pos;
		target = position;
	}

	public boolean running;
	public boolean flipX;
	public boolean walking, wasWalking;
	private Vector2d position, target;
}
