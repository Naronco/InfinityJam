package com.naronco.infinityjam.scenes;

import com.deviotion.ld.eggine.graphics.Sprite;
import com.deviotion.ld.eggine.math.Polygon2d;
import com.deviotion.ld.eggine.math.Vector2d;
import com.naronco.infinityjam.ExitStepArea;
import com.naronco.infinityjam.Game;
import com.naronco.infinityjam.IScene;

import java.io.File;

public class Street extends PointAndClickScene {
	@Override
	public void load() {
		background = new Sprite(new File("res/street.png"));

		addMovementArea(new Polygon2d(new Vector2d(-5, 57), new Vector2d(205, 57), new Vector2d(205, 66), new Vector2d(-5, 66)));

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
}
