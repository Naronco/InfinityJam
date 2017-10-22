package com.naronco.infinityjam.scenes;

import com.deviotion.ld.eggine.graphics.Screen;
import com.deviotion.ld.eggine.graphics.Sprite;
import com.deviotion.ld.eggine.math.Polygon2d;
import com.deviotion.ld.eggine.math.Vector2d;
import com.deviotion.ld.eggine.sound.Sound;
import com.naronco.infinityjam.Character;
import com.naronco.infinityjam.*;
import com.naronco.infinityjam.dialog.Dialog;
import com.naronco.infinityjam.dialog.StaticAnswer;
import com.naronco.infinityjam.interactables.DialogTrigger;
import com.naronco.infinityjam.interactables.Walkway;
import com.naronco.infinityjam.quests.DrugDealerQuest;
import com.naronco.infinityjam.quests.DrugDealerVisitQuest;

import java.io.File;

public class Alley extends PointAndClickScene {
	Character guys[];

	@Override
	public void load() {
		background = new Sprite(new File("res/alley.png"));

		guys = new Character[3];
		for (int i = 0; i < guys.length; ++i) {
			guys[i] = new Character(200 - 30 - i * 30, 60, 1);
		}

		addMovementArea(new Polygon2d(
				new Vector2d(0, 38),
				new Vector2d(152, 40),
				new Vector2d(200, 63),
				new Vector2d(200, 84),
				new Vector2d(0, 84)
		));

		interactables.add(new Walkway("Strasse", new Vector2d(5, 50), new Polygon2d(
				new Vector2d(0, 38),
				new Vector2d(10, 38),
				new Vector2d(10, 84),
				new Vector2d(0, 84)
		)));

		stepAreas.add(new ExitStepArea(new Polygon2d(
				new Vector2d(0, 38),
				new Vector2d(10, 38),
				new Vector2d(10, 84),
				new Vector2d(0, 84)
		), Game.instance.street));

//		stepAreas.add(new ElevatorQuest(new Polygon2d(new Vector2d(73, 57), new Vector2d(120, 56), new Vector2d(124, 70), new Vector2d(70, 71)), Game.instance.street));
	}

	@Override
	public void enter(IScene prev) {
		Game.instance.animationPlaying = true;
		Game.instance.showMessage("Du Schuft!");
		if (prev == Game.instance.street)
			Game.instance.player.teleport(new Vector2d(10, 50));
	}

	@Override
	public void renderForeground(Screen screen) {
		super.renderForeground(screen);
		for (Character guy : guys)
			guy.draw(screen);
	}

	@Override
	public void update() {
		super.update();
		/*if (showRandomDude && !randomDudeTalked && randomDude.walkingEnded()) {
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
		}*/
	}

	@Override
	public Sound getBackgroundMusic() {
		return Sounds.cityTheme;
	}
}
/*
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
*/