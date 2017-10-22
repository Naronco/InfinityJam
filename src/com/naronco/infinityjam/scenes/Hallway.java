package com.naronco.infinityjam.scenes;

import com.deviotion.ld.eggine.graphics.Font;
import com.deviotion.ld.eggine.graphics.Screen;
import com.deviotion.ld.eggine.graphics.Sprite;
import com.deviotion.ld.eggine.math.Polygon2d;
import com.deviotion.ld.eggine.math.Vector2d;
import com.deviotion.ld.eggine.sound.Sound;
import com.naronco.infinityjam.*;
import com.naronco.infinityjam.dialog.Dialog;
import com.naronco.infinityjam.dialog.StaticAnswer;
import com.naronco.infinityjam.interactables.*;
import com.naronco.infinityjam.quests.DrugDealerQuest;
import com.naronco.infinityjam.quests.DrugDealerVisitQuest;

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

		interactables.add(new Door(new Vector2d(25, 58), new Polygon2d(
				new Vector2d(11, 14),
				new Vector2d(39, 16),
				new Vector2d(42, 57),
				new Vector2d(9, 56)
		)));

		interactables.add(new Door(new Vector2d(75, 58), new Polygon2d(
				new Vector2d(61, 16),
				new Vector2d(89, 18),
				new Vector2d(89, 54),
				new Vector2d(60, 55)
		)));

		interactables.add(new Door(new Vector2d(130, 58), new Polygon2d(
				new Vector2d(114, 17),
				new Vector2d(146, 19),
				new Vector2d(145, 55),
				new Vector2d(114, 54)
		)));

		interactables.add(new Door(new Vector2d(170, 58), new Polygon2d(
				new Vector2d(158, 16),
				new Vector2d(185, 18),
				new Vector2d(186, 54),
				new Vector2d(156, 54)
		)));

		interactables.add(new Walkway("Richtung Aufzug", new Vector2d(0, 70), new Polygon2d(
				new Vector2d(-5, 0),
				new Vector2d(10, 0),
				new Vector2d(10, 85),
				new Vector2d(-5, 85)
		)));

		interactables.add(new Walkway("Endloser Gang", new Vector2d(200, 70), new Polygon2d(
				new Vector2d(190, 0),
				new Vector2d(205, 0),
				new Vector2d(205, 85),
				new Vector2d(190, 85)
		)));

		addMovementArea(new Polygon2d(new Vector2d(-5, 55), new Vector2d(205, 55), new Vector2d(205, 86), new Vector2d(-5, 86)));

		stepAreas.add(new ExitStepArea(new Polygon2d(new Vector2d(10, 52), new Vector2d(40, 52), new Vector2d(40, 62), new Vector2d(10, 62)), Game.instance.bedroom));
		stepAreas.add(new InteractStepArea(new Polygon2d(new Vector2d(113, 55), new Vector2d(144, 55), new Vector2d(144, 72), new Vector2d(113, 72)), new DrugDealerDoor("Ominöse Tür")));
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
					if (!Game.instance.giveItems(Item.COINS, 1, 9))
						Game.instance.showMessage("Noch eine Münze kriegst du aber nicht");
					else
						Game.instance.showMessage("Um deine Hobbylosigkeit zu belohnen kriegst du eine Münze");
				}
			}
			if (plants.containsKey(rooms))
				plant.load(plants.get(rooms));
			else
				plant.load(0);
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

class DrugDealerDoor implements Interactable {
	String name;

	public DrugDealerDoor(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getNameWithArticle() {
		return name;
	}

	@Override
	public boolean intersects(int x, int y) {
		return false;
	}

	@Override
	public boolean hasLook() {
		return true;
	}

	@Override
	public boolean hasUse() {
		return true;
	}

	@Override
	public boolean hasTake() {
		return true;
	}

	@Override
	public boolean hasPunch() {
		return true;
	}

	@Override
	public boolean hasImplicitClick() {
		return false;
	}

	@Override
	public void look(int x, int y) {
		Game.instance.showMessage("Da steht eingeritzt 'Geheimer Drogen Dealer'");
	}

	@Override
	public void use(int x, int y) {
		if (Game.instance.hallway.startRoom + Game.instance.hallway.rooms * 4 + 2 != 13)
			return;
		IQuest visitQ = Game.instance.getQuest(DrugDealerVisitQuest.class);
		if (visitQ != null) {
			Game.instance.pushDialog(new Dialog("Was ist?",
					new StaticAnswer("Kannst du mir mit dem Aufzug helfen?",
							new Dialog("Hm... Das kann ich schon, nur brauche ich ein paar Zutaten"),
							new Dialog("Wenn du mir meine Zutaten bringen kannst helf ich dir den Aufzug wieder zu benutzen",
									new StaticAnswer("Wo finde ich besagte Zutaten?",
											new Dialog("Ich habe überall im Gang vermeindliche Dekopflanzen platziert. Eigentlich sind es Drogen."),
											new Dialog("Pflück einfach die reifen Blätter, aber sei dabei sehr Vorsichtig!"))
							))
			));
			Game.instance.finishQuest(visitQ);
			Game.instance.addQuest(new DrugDealerQuest());
		} else {
			Game.instance.showMessage("Lieber nicht.");
		}
	}

	@Override
	public void take(int x, int y) {
		Game.instance.showMessage("Ich glaub die ist extra gesichert");
	}

	@Override
	public void punch(int x, int y) {
		Game.instance.showMessage("Dann würde er mir nicht mehr helfen");
	}

	@Override
	public void interact(int x, int y, Item item) {

	}

	@Override
	public void implicit(int x, int y) {
	}
}