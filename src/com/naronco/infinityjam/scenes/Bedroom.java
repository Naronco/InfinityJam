package com.naronco.infinityjam.scenes;

import com.deviotion.ld.eggine.graphics.Screen;
import com.deviotion.ld.eggine.graphics.Sprite;
import com.deviotion.ld.eggine.math.Dimension2d;
import com.deviotion.ld.eggine.math.Polygon2d;
import com.deviotion.ld.eggine.math.Rectangle2d;
import com.deviotion.ld.eggine.math.Vector2d;
import com.deviotion.ld.eggine.sound.Sound;
import com.naronco.infinityjam.ExitStepArea;
import com.naronco.infinityjam.Game;
import com.naronco.infinityjam.IScene;
import com.naronco.infinityjam.Sounds;
import com.naronco.infinityjam.interactables.Bed;
import com.naronco.infinityjam.interactables.Bottle;
import com.naronco.infinityjam.interactables.Bucket;
import com.naronco.infinityjam.interactables.Hole;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Bedroom extends PointAndClickScene {
	@Override
	public void load() {
		background = new Sprite(new File("res/bedroom.png"));
		foreground = new Sprite(new File("res/bedroom-objects.png"));

		interactables.add(new Bed(new Polygon2d(
				new Vector2d(39, 27),
				new Vector2d(73, 19),
				new Vector2d(105, 71),
				new Vector2d(67, 80)
		)));

		interactables.add(new Bottle(new Polygon2d(
				new Vector2d(114, 44),
				new Vector2d(121, 56),
				new Vector2d(120, 69),
				new Vector2d(113, 72),
				new Vector2d(106, 70),
				new Vector2d(106, 60)
		)));

		interactables.add(new Bucket(new Polygon2d(
				new Vector2d(127, 47),
				new Vector2d(140, 44),
				new Vector2d(153, 49),
				new Vector2d(152, 62),
				new Vector2d(141, 67),
				new Vector2d(129, 61)
		)));

		interactables.add(new Hole(new Polygon2d(
				new Vector2d(146, 16),
				new Vector2d(158, 28),
				new Vector2d(158, 48),
				new Vector2d(146, 32)
		)));

		addMovementArea(new Polygon2d(new Vector2d(86, 30), new Vector2d(130, 20), new Vector2d(149, 57), new Vector2d(103, 65)));

		stepAreas.add(new ExitStepArea(new Polygon2d(new Vector2d(132, 25), new Vector2d(142, 39), new Vector2d(127, 43), new Vector2d(120, 30)), Game.instance.hallway));
	}

	@Override
	public void enter(IScene prev) {
		if (prev == Game.instance.hallway)
			Game.instance.player.teleport(new Vector2d(135, 32));
	}

	@Override
	public Sound getBackgroundMusic() {
		return Sounds.home;
	}
}
