package com.naronco.infinityjam.scenes;

import com.deviotion.ld.eggine.graphics.Font;
import com.deviotion.ld.eggine.graphics.Screen;
import com.deviotion.ld.eggine.graphics.Sprite;
import com.deviotion.ld.eggine.math.Polygon2d;
import com.deviotion.ld.eggine.math.Vector2d;
import com.deviotion.ld.eggine.sound.Sound;
import com.naronco.infinityjam.ExitStepArea;
import com.naronco.infinityjam.Game;
import com.naronco.infinityjam.IScene;
import com.naronco.infinityjam.Sounds;
import com.naronco.infinityjam.interactables.Bed;
import com.naronco.infinityjam.interactables.DrugPlant;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hallway extends PointAndClickScene {
	int rooms = 0;
	int startRoom = 11;

	Map<Integer, Integer> plants = new HashMap<>();
	DrugPlant plant;

	@Override
	public void load() {
		background = new Sprite(new File("res/hallway.png"));

		plant = new DrugPlant(new Polygon2d(
				new Vector2d(94, 33),
				new Vector2d(106, 33),
				new Vector2d(111, 50),
				new Vector2d(107, 60),
				new Vector2d(96, 60),
				new Vector2d(90, 50)
		));

		interactables.add(plant);

		addMovementArea(new Polygon2d(new Vector2d(-5, 55), new Vector2d(205, 55), new Vector2d(205, 86), new Vector2d(-5, 86)));

		stepAreas.add(new ExitStepArea(new Polygon2d(new Vector2d(10, 52), new Vector2d(40, 52), new Vector2d(40, 62), new Vector2d(10, 62)), Game.instance.bedroom));
		stepAreas.add(new ExitStepArea(new Polygon2d(new Vector2d(-5, 55), new Vector2d(10, 55), new Vector2d(10, 86), new Vector2d(-5, 86)), this));
		stepAreas.add(new ExitStepArea(new Polygon2d(new Vector2d(190, 55), new Vector2d(205, 55), new Vector2d(205, 86), new Vector2d(190, 86)), this));
	}

	@Override
	public void renderBackground(Screen screen) {
		super.renderBackground(screen);
		renderRoom(screen, 8, startRoom + rooms * 4);
		renderRoom(screen, 59, startRoom + rooms * 4 + 1);
		renderRoom(screen, 113, startRoom + rooms * 4 + 2);
		renderRoom(screen, 156, startRoom + rooms * 4 + 3);
	}

	void renderRoom(Screen screen, int x, int room) {
		String s = room + "";
		while (s.length() < 3)
			s = "0" + s;
		screen.renderText(x, 6, Font.standard, "153" + s);
	}

	@Override
	public void enter(IScene prev) {
		if (plants.containsKey(rooms))
			plant.load(plants.get(rooms));
		else
			plant.load(0);
		if (prev == Game.instance.bedroom)
			Game.instance.player.teleport(new Vector2d(26, 64));
		else if (prev == Game.instance.elevator)
			Game.instance.player.teleport(new Vector2d(5, 75));
		else if (prev == this) {
			int x = (int) Game.instance.player.getPosition().getX();
			int y = (int) Game.instance.player.getPosition().getY();
			if (x < 100) {
				Game.instance.player.teleport(new Vector2d(195, y));
				rooms--;
				if (rooms < -1) {
					Game.instance.setScene(Game.instance.elevator);
					rooms = -1;
				}
			} else {
				Game.instance.player.teleport(new Vector2d(5, y));
				rooms++;
				if (startRoom + rooms * 4 + 3 > 999) {
					Game.instance.setScene(Game.instance.bedroom);
					rooms = -1; // Went over room 999, what a nerd
				}
			}
		}
	}

	@Override
	public void leave() {
		if (plant.save() != 0)
			plants.put(rooms, plant.save());
	}

	@Override
	public Sound getBackgroundMusic() {
		return Sounds.home;
	}
}
