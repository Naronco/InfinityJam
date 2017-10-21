package com.naronco.infinityjam.scenes;

import com.deviotion.ld.eggine.graphics.Screen;
import com.deviotion.ld.eggine.graphics.Sprite;
import com.deviotion.ld.eggine.math.Vector2d;
import com.deviotion.ld.eggine.sound.Sound;
import com.naronco.infinityjam.Game;
import com.naronco.infinityjam.IScene;
import com.naronco.infinityjam.Sounds;

import java.io.File;
import java.util.Random;

public class ShellGame implements IScene {
	private static final int STATE_SHOW_MOVE_UP = 0;
	private static final int STATE_SHOW_MOVE_DOWN = 1;
	private static final int STATE_SWAP = 2;
	private static final int STATE_PICK = 3;
	private static final int STATE_REVEAL = 4;

	private Sprite backgroundSprite;
	private Sprite priceSprite;
	private Sprite hutSprite;
	private int maxSwapCount, swapCount;
	private Random random;
	private Hut[] huts;
	private Hut priceHut;
	private int state = STATE_SHOW_MOVE_UP;
	private IScene prevScene = null;

	private boolean animating = false;

	public ShellGame(int maxSwapCount) {
		this.backgroundSprite = new Sprite(new File("res/hutgamebg.png"));
		this.hutSprite = new Sprite(new File("res/hut.png"));
		this.priceSprite = new Sprite(new File("res/price.png"));

		this.maxSwapCount = maxSwapCount;
		this.swapCount = 0;

		this.random = new Random();

		this.huts = new Hut[3];
		for (int i = 0; i < huts.length; ++i) {
			huts[i] = new Hut(hutSprite, new Vector2d(Game.instance.getWindow().getScreen().getDimension().getWidth() * (i + 2) / 6.0, 70));
		}

		priceHut = huts[1];
		priceHut.moveTo(priceHut.getPosition().subtract(new Vector2d(0, 15)));
	}

	@Override
	public void load() {
	}

	@Override
	public void enter(IScene prev) {
		prevScene = prev;
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
	}

	@Override
	public void renderBackground(Screen screen) {
	}

	@Override
	public void renderForeground(Screen screen) {
		screen.renderSprite(0, 0, backgroundSprite);

		if (state == STATE_SHOW_MOVE_DOWN || state == STATE_SHOW_MOVE_UP || state == STATE_REVEAL) {
			double x = priceHut.getPosition().getX() - priceSprite.getDimension().getWidth() * 0.5;
			double y = 70 - priceSprite.getDimension().getHeight();

			screen.renderSprite((int)x, (int)y, priceSprite);
		}

		for (Hut hut : huts) {
			double x = hut.getPosition().getX() - hutSprite.getDimension().getWidth() * 0.5;
			double y = hut.getPosition().getY() - hutSprite.getDimension().getHeight();

			screen.renderSprite((int)x, (int)y, hutSprite);
		}
	}

	@Override
	public void update() {
		for (Hut hut : huts) {
			hut.update();
		}

		animating = false;
		for (Hut hut : huts)
			if (hut.isAnimating())
				animating = true;

		if (!animating) {
			switch (state) {
				case STATE_SHOW_MOVE_UP:
					priceHut.moveTo(priceHut.getPosition().add(new Vector2d(0, 15)));
					state = STATE_SHOW_MOVE_DOWN;
					break;
				case STATE_SHOW_MOVE_DOWN:
				case STATE_SWAP:
					++swapCount;
					if (swapCount < maxSwapCount) {
						state = STATE_SWAP;
						int a = random.nextInt(3);
						int b = random.nextInt(3);
						while (b == a) b = random.nextInt(3);
						swap(a, b);
					} else {
						state = STATE_PICK;
						Game.instance.showMessage("Welches welchem Hütchen ist die Kugel?");
					}
					break;
				case STATE_REVEAL:
					if (prevScene != null) {
						Game.instance.setScene(prevScene);
					}
					break;
			}
		}

		if (state == STATE_PICK && Game.instance.getMouse().isLeftClicking()) {
			int mx = (int) Game.instance.getMouse().getLocation().getX();
			int my = (int) Game.instance.getMouse().getLocation().getY();

			for (Hut hut : huts) {
				if (hut.getRectangle().intersects(mx, my)) {
					state = STATE_REVEAL;
					if (priceHut == hut) {
						Game.instance.showMessage("Du hast gewonnen!");
					} else {
						Game.instance.showMessage("Du hast leider verloren");
					}
					priceHut.moveTo(priceHut.getPosition().subtract(new Vector2d(0, 15)));
				}
			}
		}
	}

	@Override
	public Sound getBackgroundMusic() {
		return Sounds.casino;
	}

	public void swap(int a, int b) {
		animating = true;
		huts[a].swapTo(huts[b].getPosition(), true);
		huts[b].swapTo(huts[a].getPosition(), false);
	}
}
