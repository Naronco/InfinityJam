package com.naronco.infinityjam.scenes;

import com.deviotion.ld.eggine.graphics.Sprite;
import com.deviotion.ld.eggine.math.Polygon2d;
import com.deviotion.ld.eggine.math.Vector2d;
import com.deviotion.ld.eggine.sound.Sound;
import com.naronco.infinityjam.ExitStepArea;
import com.naronco.infinityjam.Game;
import com.naronco.infinityjam.IScene;
import com.naronco.infinityjam.Sounds;
import com.naronco.infinityjam.interactables.GamingMachine;
import com.naronco.infinityjam.interactables.Walkway;

import java.io.File;

public class Casino extends PointAndClickScene {
	@Override
	public void load() {
		background = new Sprite(new File("res/casino.png"));

		addMovementArea(new Polygon2d(new Vector2d(16, 56), new Vector2d(200, 60), new Vector2d(200, 85), new Vector2d(0, 85)));

		stepAreas.add(new ExitStepArea(new Polygon2d(new Vector2d(17, 54), new Vector2d(38, 52), new Vector2d(30, 86), new Vector2d(0, 86)), Game.instance.street));

		interactables.add(new Walkway("Strasse", new Vector2d(16, 67), new Polygon2d(
				new Vector2d(15, 21),
				new Vector2d(38, 55),
				new Vector2d(40, 85),
				new Vector2d(-30, 85)
		)));

		int distance = (83 - 47);
		int width = (75 - 47);
		int height = (56 - 7);

		for (int i = 1; i < 4; ++i) {
			int x = 47 + distance * i;
			int y = 7;
			GamingMachine machine = new GamingMachine(new Polygon2d(new Vector2d(x, y), new Vector2d(x + width, y), new Vector2d(x + width, y + height), new Vector2d(x, y + height)));
			interactables.add(machine);
		}
	}

	@Override
	public void enter(IScene prev) {
		Game.instance.player.teleport(new Vector2d(14, 70));
	}

	@Override
	public Sound getBackgroundMusic() {
		return Sounds.casino;
	}
}
