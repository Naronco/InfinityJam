package com.naronco.infinityjam.interactables;

import com.deviotion.ld.eggine.math.Polygon2d;
import com.naronco.infinityjam.Game;
import com.naronco.infinityjam.Interactable;
import com.naronco.infinityjam.Item;

public class Hole implements Interactable {
	private Polygon2d outline;

	public Hole(Polygon2d outline) {
		this.outline = outline;
	}

	@Override
	public String getName() {
		return "Loch";
	}

	@Override
	public String getNameWithArticle() {
		return "ein Loch";
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
	public void look(int x, int y) {
		Game.instance.showMessage("Quasi meine Spühlung.");

	}

	@Override
	public void use(int x, int y) {
		Game.instance.showMessage("Wie jetzt, erwartest du von mir dass ich da rein springe?");
	}

	@Override
	public void take(int x, int y) {
		Game.instance.showMessage("Das Loch sitzt fest an der Wand.");

	}

	@Override
	public void punch(int x, int y) {
		Game.instance.showMessage("Seit meiner letzten Freundin tue ich meine Faust nicht mehr in Löcher...");
	}

	@Override
	public void interact(int x, int y, Item item) {
		switch (item) {
			case KEY:
				Game.instance.showMessage("Ich brauche diesen Schlüssel noch, nur wofür...");
				break;
		}
	}
}