package com.naronco.infinityjam.scenes;

import com.deviotion.ld.eggine.graphics.Font;
import com.deviotion.ld.eggine.graphics.Screen;
import com.deviotion.ld.eggine.graphics.Sprite;
import com.deviotion.ld.eggine.math.Polygon2d;
import com.deviotion.ld.eggine.math.Vector2d;
import com.deviotion.ld.eggine.sound.Sound;
import com.naronco.infinityjam.*;
import com.naronco.infinityjam.quests.DrugDealerQuest;
import com.naronco.infinityjam.quests.DrugDealerVisitQuest;

import java.io.File;

public class Elevator extends PointAndClickScene {
	@Override
	public void load() {
		background = new Sprite(new File("res/elevator.png"));

		addMovementArea(new Polygon2d(new Vector2d(46, 56), new Vector2d(156, 56), new Vector2d(184, 96), new Vector2d(10, 96)));

		stepAreas.add(new ExitStepArea(new Polygon2d(new Vector2d(24, 80), new Vector2d(174, 80), new Vector2d(184, 96), new Vector2d(10, 96)), Game.instance.hallway));
		stepAreas.add(new ElevatorQuest(new Polygon2d(new Vector2d(73, 57), new Vector2d(120, 56), new Vector2d(124, 70), new Vector2d(70, 71)), Game.instance.street));
	}

	@Override
	public void enter(IScene prev) {
		if (prev == Game.instance.street)
			Game.instance.player.teleport(new Vector2d(100, 60));
		else
			Game.instance.player.teleport(new Vector2d(90, 105));
	}

	@Override
	public Sound getBackgroundMusic() {
		return Sounds.home;
	}
}

class ElevatorQuest extends ExitStepArea {
	boolean questGiven;

	public ElevatorQuest(Polygon2d area, IScene transitionTo) {
		super(area, transitionTo);
		questGiven = false;
	}

	@Override
	public void stepOn(int x, int y) {
		if (Game.instance.isQuestFinished(DrugDealerQuest.class))
			super.stepOn(x, y);
		else {
			Game.instance.showMessage("Scheint als wäre der Aufzug kaputt. Ich muss einen Weg finden ihn trotzdem benutzen zu können");
			if (!questGiven) {
				Game.instance.addQuest(new DrugDealerVisitQuest());
				questGiven = true;
			}
		}
	}
}