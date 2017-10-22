package com.naronco.infinityjam.scenes;

import com.deviotion.ld.eggine.graphics.Screen;
import com.deviotion.ld.eggine.graphics.Sprite;
import com.deviotion.ld.eggine.math.Dimension2d;
import com.deviotion.ld.eggine.math.Vector2d;
import com.deviotion.ld.eggine.sound.Sound;
import com.naronco.infinityjam.Character;
import com.naronco.infinityjam.Game;
import com.naronco.infinityjam.IScene;
import com.naronco.infinityjam.Sounds;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AlleyChallenge implements IScene {
	private Dimension2d size;

	private Sprite bulletSprite;
	private List<Vector2d> bulletPositions = new ArrayList<Vector2d>();

	private double velocity = 0;

	private Random random = new Random();
	private int ticks = 0;

	public AlleyChallenge(Dimension2d size) {
		this.size = size;
		this.bulletSprite = new Sprite(new File("res/key.png"));
	}

	@Override
	public void load() {
	}

	@Override
	public void enter(IScene prev) {
	}

	@Override
	public void leave() {
	}

	@Override
	public String detailAt(int x, int y) {
		return null;
	}

	@Override
	public void click(int x, int y, int mode) {
		velocity = -5;

	/*	for (int i = 0; i < bombPositions.size(); ++i) {
			Vector2d pos = bombPositions.get(i);
			double dx = pos.getX() - x;
			double dy = pos.getY() - y;
			if ((dx * dx) + (dy * dy) < 20 * 20) {
				bombPositions.remove(i--);
			}
		}*/
	}

	@Override
	public void renderBackground(Screen screen) {
	}

	@Override
	public void renderForeground(Screen screen) {
		for (Vector2d pos : bulletPositions) {
			screen.renderSprite(pos, bulletSprite);
		}
	}

	@Override
	public void update() {
		++ticks;
		if (ticks >= 30) {
			double s = size.getHeight() / 6;
			if (Math.random() < 0.5) {
				for (int i = 0; i < random.nextInt(3) + 1; ++i) {
					bulletPositions.add(new Vector2d(size.getWidth() + 10, i * s));
				}
			} else {
				for (int i = 3 + random.nextInt(3); i < 6; ++i) {
					bulletPositions.add(new Vector2d(size.getWidth() + 10, i * s));
				}
			}
			ticks = 0;
		}

		for (int i = 0; i < bulletPositions.size(); ++i) {
			Vector2d pos = bulletPositions.get(i);
			if (pos.getX() < -50) {
				bulletPositions.remove(i--);
			} else {
				pos.setX(pos.getX() - 3.0);
			}
		}

		Character player = Game.instance.getPlayer();
		player.flipX = true;

		player.getPosition().setX(20);

		velocity += 0.5;
		player.getPosition().setY(Math.min(player.getPosition().getY() + velocity, (int)size.getHeight()));
	}

	@Override
	public Sound getBackgroundMusic() {
		return Sounds.miniGame;
	}

	@Override
	public boolean showsPlayer() {
		return true;
	}
}
