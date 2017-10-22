package com.naronco.infinityjam.scenes;

import com.deviotion.ld.eggine.graphics.Screen;
import com.deviotion.ld.eggine.graphics.Sprite;
import com.deviotion.ld.eggine.math.Dimension2d;
import com.deviotion.ld.eggine.math.Rectangle2d;
import com.deviotion.ld.eggine.math.Vector2d;
import com.deviotion.ld.eggine.sound.Sound;
import com.naronco.infinityjam.*;
import com.naronco.infinityjam.Character;
import com.naronco.infinityjam.scenes.inventory.Inventory;
import com.naronco.infinityjam.transitions.BlackOverlayTransition;

import java.io.File;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Random;

public class AlleyChallenge implements IScene {
	private static final String[] MESSAGES = {
			"Du Dummkopf!",
			"Du Schwachkopf!",
			"Du hast uns Gerippengedömmelt!",
			"Das ist zwar kein Wort, aber du hast vollkommen recht!",
			"Du hörst bestimmt Nickelback!",
			"Du über Vierziger!",
			"Teetrinker!",
			"Du Jura-Student!",
			"Minispielspieler!",
			"Du Bambusbauer!",
			"Du Disney Fan!",
			"Du elektrisiertes Bockwürstchen!"
	};

	private Dimension2d size;

	private List<Vector2d> bulletPositions = new ArrayList<Vector2d>();

	private double velocity = 0;

	private Random random = new Random();
	private int ticks = 0;
	private int currentMessageIndex = 0;

	private Alley prevAlley;

	public AlleyChallenge(Dimension2d size) {
		this.size = size;
	}

	@Override
	public void load() {
	}

	@Override
	public void enter(IScene prev) {
		prevAlley = (Alley)prev;
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
			screen.renderSprite(pos, Sprites.BULLET);
		}
	}

	@Override
	public void update() {
		++ticks;
		if (ticks % 30 == 0) {
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
		}

		if (ticks % 90 == 0) {
			Game.instance.showMessage(MESSAGES[currentMessageIndex++]);
			currentMessageIndex %= MESSAGES.length;
		}

		if (ticks > 30 * 30 && prevAlley != null) {
			prevAlley.setMode(Alley.MODE_BAD_GUYS_RUNAWAY);
			Game.instance.setScene(prevAlley, new BlackOverlayTransition());
			prevAlley = null;
		}

		Character player = Game.instance.getPlayer();

		for (int i = 0; i < bulletPositions.size(); ++i) {
			Vector2d pos = bulletPositions.get(i);
			if (pos.getX() < -50) {
				bulletPositions.remove(i--);
			} else {
				pos.setX(pos.getX() - 3.0);

				Rectangle2d bulletRect = new Rectangle2d(pos, Sprites.BULLET.getDimension());
				Rectangle2d playerRect = player.getRectangle().copy();

				playerRect.getPosition().setX(playerRect.getPosition().getX() + playerRect.getSize().getWidth() * 0.25);
				playerRect.getSize().setWidth(playerRect.getSize().getWidth() * 0.5);

				if (bulletRect.intersects(playerRect)) {
					Game.instance.die();
				}
			}
		}

		player.flipX = true;

		player.getPosition().setX(20);

		velocity += 0.5;
		player.getPosition().setY(Math.max((int)player.getSpriteSheet().getSpriteSize().getHeight() / 2, Math.min(player.getPosition().getY() + velocity, (int)size.getHeight())));
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
