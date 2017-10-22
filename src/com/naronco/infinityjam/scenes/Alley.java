package com.naronco.infinityjam.scenes;

import com.deviotion.ld.eggine.graphics.Screen;
import com.deviotion.ld.eggine.graphics.Sprite;
import com.deviotion.ld.eggine.graphics.TextArea;
import com.deviotion.ld.eggine.math.Dimension2d;
import com.deviotion.ld.eggine.math.Polygon2d;
import com.deviotion.ld.eggine.math.Vector2d;
import com.deviotion.ld.eggine.sound.Sound;
import com.naronco.infinityjam.Character;
import com.naronco.infinityjam.*;
import com.naronco.infinityjam.dialog.Dialog;
import com.naronco.infinityjam.dialog.StaticAnswer;
import com.naronco.infinityjam.interactables.Walkway;
import com.naronco.infinityjam.quests.AlleyQuest;
import com.naronco.infinityjam.transitions.BlackOverlayTransition;

import java.io.File;

public class Alley extends PointAndClickScene {
	public static final int MODE_BAD_GUYS_CONFRONTATION = 0;
	public static final int MODE_BAD_GUYS_RUNAWAY = 1;
	public static final int MODE_DEFAULT = 2;

	private int mode = MODE_BAD_GUYS_CONFRONTATION;

	Character guys[];

	@Override
	public void load() {
		background = new Sprite(new File("res/alley.png"));

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
	public void click(int x, int y, int mode) {
		/*if (this.mode == MODE_BAD_GUYS_CONFRONTATION)
			Game.instance.setScene(new AlleyChallenge(new Dimension2d(200, 86)), new BlackOverlayTransition());
		else*/
			super.click(x, y, mode);
	}

	@Override
	public void enter(IScene prev) {
		switch (mode) {
			case MODE_BAD_GUYS_CONFRONTATION:
				Game.instance.pushDialog(new Dialog("Du Schuft!!",
						new StaticAnswer("Was??",
								new Dialog("Du hast eine Kreissäge aus dem 53. Stock geworfen und damit unser Zelt zerstört!",
										new StaticAnswer("Das habe ich nicht!",
												new Dialog("Naja ok... Wahrscheinlich hast du recht... Trotzdem schuldig!", (TextArea textArea) -> enterMinigame())
										),
										new StaticAnswer("Ich gebe es zu.",
												new Dialog("Wir wussten es!", (TextArea textArea) -> enterMinigame())
										)
								)
						)
				));
				break;
			case MODE_BAD_GUYS_RUNAWAY:
				Game.instance.animationPlaying = true;
				Game.instance.showMessage("Aaaaah! Er ist zu stark! Wir verschwinden");

				for (Character guy : guys)
					guy.walkTo(new Vector2d(270, 150));

				break;
			case MODE_DEFAULT:
				if (!Game.instance.isQuestFinished(AlleyQuest.class)) {
					AlleyQuest quest = Game.instance.getQuest(AlleyQuest.class);
					if (quest == null) {
						quest = new AlleyQuest();
						Game.instance.addQuest(quest);
					}
					Game.instance.finishQuest(quest);
					Game.instance.player.teleport(new Vector2d(10, 50));
				}
				break;
		}

		if (prev == Game.instance.street)
			Game.instance.player.teleport(new Vector2d(10, 50));
	}

	@Override
	public void renderForeground(Screen screen) {
		super.renderForeground(screen);

		switch (mode) {
			case MODE_BAD_GUYS_CONFRONTATION:
			case MODE_BAD_GUYS_RUNAWAY:
				for (Character guy : guys)
					guy.draw(screen);
				break;
		}
	}

	@Override
	public void update() {
		super.update();

		if (mode == MODE_BAD_GUYS_RUNAWAY) {
			for (Character guy : guys) {
				if (guy.walkingEnded()) {
					Game.instance.animationPlaying = false;

					setMode(MODE_DEFAULT);
					Game.instance.setScene(this, new BlackOverlayTransition());

					break;
				}
			}
		}
	}

	@Override
	public Sound getBackgroundMusic() {
		return Sounds.cityTheme;
	}

	private void enterMinigame() {
		Game.instance.setScene(new AlleyChallenge(new Dimension2d(200, 86)), new BlackOverlayTransition());
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;

		guys = null;
		switch (mode) {
			case MODE_BAD_GUYS_CONFRONTATION:
				guys = new Character[3];
				for (int i = 0; i < guys.length; ++i) {
					int y = 60;
					if (i == 1)
						y = 68;
					guys[i] = new Character(200 - 60 - i * 30, y, 1);
				}
				break;
			case MODE_BAD_GUYS_RUNAWAY:
				guys = new Character[3];
				for (int i = 0; i < guys.length; ++i) {
					int y = 60;
					if (i == 1)
						y = 68;
					guys[i] = new Character(200 - 60 - i * 30, y, 1);
				}
				break;
		}
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