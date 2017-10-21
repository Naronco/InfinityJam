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

		interactables.add(new Walkway("Casino 29134239436854268469158942354684324891236445641256423156...", new Vector2d(138, 60), new Polygon2d(
				new Vector2d(125, 32),
				new Vector2d(162, 32),
				new Vector2d(162, 66),
				new Vector2d(125, 66)
		)));

		stepAreas.add(new ExitStepArea(new Polygon2d(new Vector2d(20, 57), new Vector2d(50, 57), new Vector2d(50, 66), new Vector2d(20, 66)), Game.instance.elevator));
		stepAreas.add(new ExitStepArea(new Polygon2d(new Vector2d(125, 57), new Vector2d(160, 57), new Vector2d(160, 66), new Vector2d(125, 66)), Game.instance.casino));
	}

	@Override
	public void enter(IScene prev) {
		if (prev == Game.instance.casino)
			Game.instance.player.teleport(new Vector2d(142, 60));
		else
			Game.instance.player.teleport(new Vector2d(34, 60));
	}

	@Override
	public Sound getBackgroundMusic() {
		return Sounds.cityTheme;
	}
}
