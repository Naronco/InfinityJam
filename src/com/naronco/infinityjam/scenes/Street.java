package com.naronco.infinityjam.scenes;

import com.deviotion.ld.eggine.graphics.Sprite;
import com.deviotion.ld.eggine.math.Polygon2d;
import com.deviotion.ld.eggine.math.Vector2d;
import com.deviotion.ld.eggine.sound.Sound;
import com.naronco.infinityjam.ExitStepArea;
import com.naronco.infinityjam.Game;
import com.naronco.infinityjam.IScene;
import com.naronco.infinityjam.Sounds;
import com.naronco.infinityjam.interactables.Walkway;

import java.io.File;

public class Street extends PointAndClickScene {
	@Override
	public void load() {
		background = new Sprite(new File("res/street.png"));

		addMovementArea(new Polygon2d(new Vector2d(-5, 57), new Vector2d(205, 57), new Vector2d(205, 66), new Vector2d(-5, 66)));

		interactables.add(new Walkway("Aufzug", new Vector2d(35, 60), new Polygon2d(
				new Vector2d(20, 30),
				new Vector2d(47, 30),
				new Vector2d(47, 66),
				new Vector2d(20, 66)
		)));

		interactables.add(new Walkway("Dunkle Gasse", new Vector2d(94, 56), new Polygon2d(
				new Vector2d(89, 0),
				new Vector2d(103, 0),
				new Vector2d(99, 55),
				new Vector2d(88, 55)
		)));

		interactables.add(new Walkway("Casino 29134239436854268469158942354684324891236445641256423156...", new Vector2d(138, 60), new Polygon2d(
				new Vector2d(134, 28),
				new Vector2d(159, 28),
				new Vector2d(159, 56),
				new Vector2d(134, 56)
		)));

		stepAreas.add(new ExitStepArea(new Polygon2d(new Vector2d(20, 57), new Vector2d(50, 57), new Vector2d(50, 66), new Vector2d(20, 66)), Game.instance.elevator));
		stepAreas.add(new ExitStepArea(new Polygon2d(new Vector2d(88, 55), new Vector2d(99, 55), new Vector2d(99, 63), new Vector2d(88, 63)), Game.instance.alley));
		stepAreas.add(new ExitStepArea(new Polygon2d(new Vector2d(125, 57), new Vector2d(160, 57), new Vector2d(160, 66), new Vector2d(125, 66)), Game.instance.casino));
	}

	@Override
	public void enter(IScene prev) {
		if (prev == Game.instance.casino)
			Game.instance.player.teleport(new Vector2d(142, 60));
		else if (prev == Game.instance.hallway)
			Game.instance.player.teleport(new Vector2d(100, 80));
		else
			Game.instance.player.teleport(new Vector2d(34, 60));
	}

	@Override
	public Sound getBackgroundMusic() {
		return Sounds.cityTheme;
	}
}
