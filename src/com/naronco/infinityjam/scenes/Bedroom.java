package com.naronco.infinityjam.scenes;

import com.deviotion.ld.eggine.graphics.Screen;
import com.deviotion.ld.eggine.graphics.Sprite;
import com.deviotion.ld.eggine.math.Dimension2d;
import com.deviotion.ld.eggine.math.Polygon2d;
import com.deviotion.ld.eggine.math.Rectangle2d;
import com.deviotion.ld.eggine.math.Vector2d;
import com.naronco.infinityjam.ExitStepArea;
import com.naronco.infinityjam.Game;
import com.naronco.infinityjam.IScene;
import com.naronco.infinityjam.interactables.Bed;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Bedroom extends PointAndClickScene {
	@Override
	public void load() {
		background = new Sprite(new File("res/bedroom.png"));
		foreground = new Sprite(new File("res/bedroom-objects.png"));

		List<Vector2d> points = new ArrayList<Vector2d>();
		points.add(new Vector2d(39, 27));
		points.add(new Vector2d(73, 19));
		points.add(new Vector2d(105, 71));
		points.add(new Vector2d(67, 80));

		interactables.add(new Bed(new Polygon2d(points)));

		addMovementArea(new Polygon2d(new Vector2d(86, 30), new Vector2d(130, 20), new Vector2d(149, 57), new Vector2d(103, 65)));

		stepAreas.add(new ExitStepArea(new Polygon2d(new Vector2d(132, 25), new Vector2d(142, 39), new Vector2d(127, 43), new Vector2d(120, 30)), Game.instance.hallway));
	}

	@Override
	public void enter(IScene prev) {
		if (prev == Game.instance.hallway)
			Game.instance.player.teleport(new Vector2d(135, 32));
	}
}
