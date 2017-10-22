package com.naronco.infinityjam.interactables;

import com.deviotion.ld.eggine.math.Polygon2d;
import com.naronco.infinityjam.Game;
import com.naronco.infinityjam.Interactable;
import com.naronco.infinityjam.Item;
import com.naronco.infinityjam.quests.DrugDealerQuest;

public class Keypad implements Interactable {
	private Polygon2d outline;

	public Keypad(Polygon2d outline) {
		this.outline = outline;
	}

	@Override
	public String getName() {
		return "Keypad";
	}

	@Override
	public String getNameWithArticle() {
		return "ein Keypad";
	}

	@Override
	public boolean intersects(int x, int y) {
		return outline.intersects(x, y);
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
		if (Game.instance.isQuestFinished(DrugDealerQuest.class)) {
			Game.instance.showMessage("Wow ich kann ja wirklich klar den Code erkennen");
		} else {
			Game.instance.showMessage("Eine Tastenkombination scheint gefragt");
		}
	}

	@Override
	public void use(int x, int y) {
		if (Game.instance.isQuestFinished(DrugDealerQuest.class)) {
			Game.instance.showMessage("Kinderspiel");
			Game.instance.elevator.unlocked = true;
		} else {
			Game.instance.showMessage("Wie jetzt? Ich weiß nichtmal ob ich unendlich Versuche hab");
		}
	}

	@Override
	public void take(int x, int y) {
		Game.instance.showMessage("Das ist Festgeschraubt.");
	}

	@Override
	public void punch(int x, int y) {
		Game.instance.showMessage("Stabiles Gehäuse.");
	}

	@Override
	public void interact(int x, int y, Item item) {
		switch (item) {
			case KEY:
				break;
			case LEAF:
				break;
			case COINS:
				break;
			case DRUG:
				break;
			case SAW:
				break;
			case KNIFE:
				break;
		}
	}

	@Override
	public void implicit(int x, int y) {

	}
}
