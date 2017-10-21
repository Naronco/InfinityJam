package com.naronco.infinityjam.scenes;

import com.deviotion.ld.eggine.graphics.Screen;
import com.deviotion.ld.eggine.graphics.Sprite;
import com.deviotion.ld.eggine.math.Polygon2d;
import com.deviotion.ld.eggine.math.Vector2d;
import com.deviotion.ld.eggine.sound.Sound;
import com.naronco.infinityjam.*;
import com.naronco.infinityjam.Character;
import com.naronco.infinityjam.dialog.Dialog;
import com.naronco.infinityjam.dialog.StaticAnswer;
import com.naronco.infinityjam.interactables.DialogTrigger;
import com.naronco.infinityjam.interactables.Walkway;
import com.naronco.infinityjam.quests.DrugDealerQuest;
import com.naronco.infinityjam.quests.DrugDealerVisitQuest;

import java.io.File;

public class Elevator extends PointAndClickScene {
	Character randomDude;
	boolean showRandomDude = false;
	boolean randomDudeTalked = false;

	@Override
	public void load() {
		background = new Sprite(new File("res/elevator.png"));

		randomDude = new Character(16, 200, 1);

		addMovementArea(new Polygon2d(new Vector2d(46, 56), new Vector2d(156, 56), new Vector2d(184, 96), new Vector2d(10, 96)));

		interactables.add(new Walkway("Aufzug", new Vector2d(100, 64), new Polygon2d(
				new Vector2d(120, 6),
				new Vector2d(124, 71),
				new Vector2d(69, 71),
				new Vector2d(76, 6)
		)));

		interactables.add(new Walkway("Zimmer", new Vector2d(100, 92), new Polygon2d(
				new Vector2d(24, 80),
				new Vector2d(174, 80),
				new Vector2d(184, 96),
				new Vector2d(10, 96)
		)));

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
	public void renderForeground(Screen screen) {
		super.renderForeground(screen);
		if (showRandomDude) {
			randomDude.draw(screen);
		}
	}

	@Override
	public void update() {
		super.update();
		if (showRandomDude && !randomDudeTalked && randomDude.walkingEnded()) {
			randomDudeTalked = true;
			Game.instance.showMessage("I hob gehört da Drogn Deala woass wia ma do duach kimmd. Ea wohnt in Raum 153013");
			Game.instance.animationPlaying = false;
			interactables.add(new DialogTrigger("Random Dude", new Polygon2d(
					randomDude.getSpritePosition(),
					randomDude.getSpritePosition().add(new Vector2d(20, 0)),
					randomDude.getSpritePosition().add(new Vector2d(20, 33)),
					randomDude.getSpritePosition().add(new Vector2d(0, 33))
			), new Dialog(
					"Servus, wos wuist du?",
					new StaticAnswer("Wo finde ich den Drogen Dealer?", new Dialog("Ea wohnt in Raum 153013, hob i doch schonmoi gsogt")),
					new StaticAnswer("Servus", new Dialog("Was soll der schlechte Akzent?"))
			)));
		}
	}

	public void triggerDude() {
		showRandomDude = true;
		randomDude.walkTo(64, 64);
		Game.instance.animationPlaying = true;
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
	public boolean stepOn(int x, int y) {
		if (Game.instance.isQuestFinished(DrugDealerQuest.class))
			return super.stepOn(x, y);
		else if (area.intersects(x, y)) {
			Game.instance.showMessage("Scheint als wäre der Aufzug kaputt. Ich muss einen Weg finden ihn trotzdem benutzen zu können");
			if (!questGiven) {
				Game.instance.addQuest(new DrugDealerVisitQuest());
				Game.instance.elevator.triggerDude();
				questGiven = true;
			}
			return true;
		}
		return false;
	}
}