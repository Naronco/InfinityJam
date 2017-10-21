package com.naronco.infinityjam.scenes;

import com.deviotion.ld.eggine.graphics.Screen;
import com.deviotion.ld.eggine.graphics.Sprite;
import com.deviotion.ld.eggine.math.Dimension2d;
import com.deviotion.ld.eggine.math.Polygon2d;
import com.deviotion.ld.eggine.math.Rectangle2d;
import com.deviotion.ld.eggine.math.Vector2d;
import com.naronco.infinityjam.IScene;
import com.naronco.infinityjam.interactables.Bed;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Bedroom extends PointAndClickScene {
	public Bedroom() {
		super();
		background = new Sprite(new File("res/bedroom.png"));
		foreground = new Sprite(new File("res/bedroom-objects.png"));

		List<Vector2d> points = new ArrayList<Vector2d>();
		points.add(new Vector2d(39, 27));
		points.add(new Vector2d(73, 19));
		points.add(new Vector2d(105, 71));
		points.add(new Vector2d(67, 80));

		interactables.add(new Bed(new Polygon2d(points)));

		addMovementLine(new Vector2d(82, 34), new Vector2d(124, 26), 4);
		addMovementLine(new Vector2d(90, 44), new Vector2d(131, 35), 4);
		addMovementLine(new Vector2d(98, 56), new Vector2d(139, 48), 4);
	}
}
