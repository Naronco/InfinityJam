package com.naronco.infinityjam.scenes;

import com.deviotion.ld.eggine.graphics.Sprite;
import com.deviotion.ld.eggine.math.Polygon2d;
import com.deviotion.ld.eggine.math.Vector2d;
import com.deviotion.ld.eggine.sound.Sound;
import com.naronco.infinityjam.ExitStepArea;
import com.naronco.infinityjam.Game;
import com.naronco.infinityjam.IScene;
import com.naronco.infinityjam.Sounds;
import com.naronco.infinityjam.interactables.Bed;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Hallway extends PointAndClickScene {
	@Override
	public void load() {
		background = new Sprite(new File("res/hallway.png"));

		addMovementArea(new Polygon2d(new Vector2d(-5, 55), new Vector2d(205, 55), new Vector2d(205, 86), new Vector2d(-5, 86)));

		stepAreas.add(new ExitStepArea(new Polygon2d(new Vector2d(10, 52), new Vector2d(40, 52), new Vector2d(40, 62), new Vector2d(10, 62)), Game.instance.bedroom));
	}

	@Override
	public void enter(IScene prev) {
		if (prev == Game.instance.bedroom)
			Game.instance.player.teleport(new Vector2d(26, 64));
	}

	@Override
	public Sound getBackgroundMusic() {
		return Sounds.casino;
	}
}
