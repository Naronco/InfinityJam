package com.naronco.infinityjam.scenes;

import com.deviotion.ld.eggine.graphics.Sprite;
import com.deviotion.ld.eggine.math.Polygon2d;
import com.deviotion.ld.eggine.math.Vector2d;
import com.naronco.infinityjam.ExitStepArea;
import com.naronco.infinityjam.Game;
import com.naronco.infinityjam.IScene;

import java.io.File;

public class Casino extends PointAndClickScene {
	@Override
	public void load() {
		background = new Sprite(new File("res/casino.png"));

		addMovementArea(new Polygon2d(new Vector2d(16, 56), new Vector2d(200, 60), new Vector2d(200, 85), new Vector2d(0, 85)));

		stepAreas.add(new ExitStepArea(new Polygon2d(new Vector2d(17, 54), new Vector2d(38, 52), new Vector2d(30, 86), new Vector2d(0, 86)), Game.instance.street));
	}

	@Override
	public void enter(IScene prev) {
		Game.instance.player.teleport(new Vector2d(14, 70));
	}
}
