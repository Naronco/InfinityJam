package com.naronco.infinityjam;

import com.deviotion.ld.eggine.graphics.Screen;
import com.deviotion.ld.eggine.graphics.Sprite;
import com.deviotion.ld.eggine.graphics.SpriteAnimation;
import com.deviotion.ld.eggine.graphics.SpriteSheet;
import com.deviotion.ld.eggine.math.Dimension2d;
import com.deviotion.ld.eggine.math.Vector2d;
import com.naronco.infinityjam.scenes.PointAndClickScene;

import java.io.File;

public class Character extends SpriteAnimation {
	public int idleStartTile, idleEndTile, walkStartTile, walkEndTile, baseFps;

	public Character(int x, int y, int idleStartTile, int idleEndTile, int walkStartTile, int walkEndTile, int baseFps) {
		super(Game.instance.characters, idleStartTile, idleEndTile, baseFps);
		position = target = new Vector2d(x, y);
		this.idleStartTile = idleStartTile;
		this.idleEndTile = idleEndTile;
		this.walkStartTile = walkStartTile;
		this.walkEndTile = walkEndTile;
		this.baseFps = baseFps;
	}

	public Character(int x, int y, int row) {
		this(x, y, 0 + row * 8, 0 + row * 8, 1 + row * 8, 4 + row * 8, 10);
	}

	public Character(int x, int y) {
		this(x, y, 0);
	}

	public void walkTo(Vector2d v) {
		if (target.subtract(v).getLengthSquared() < 16) {
			running = true;
			animationFps = baseFps * 2;
		} else {
			target = v;
			running = false;
			animationFps = baseFps;
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
		super.nextFrame();
		Vector2d diff = target.subtract(position);
		if (diff.getLengthSquared() > 1) {
			walking = true;
			wasWalking = true;
			diff = diff.normalized().multiply(running ? 1.2 : 0.6);
			position = position.add(diff);
			flipX = diff.getX() > 0;
		} else walking = false;
		if (walking) {
			setStartTile(walkStartTile);
			setEndTile(walkEndTile);
		} else {
			setStartTile(idleStartTile);
			setEndTile(idleEndTile);
		}
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

	public void draw(Screen screen) {
		nextFrame();
		if (flipX)
			screen.renderAnimatedSpriteFlipped(getSpritePosition(), this);
		else
			screen.renderAnimatedSprite(getSpritePosition(), this);
	}
}
