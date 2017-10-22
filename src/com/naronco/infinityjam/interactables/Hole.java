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
	public boolean hasImplicitClick() {
		return false;
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
		Game.instance.showMessage("Der Schlag ging ins leere.");
	}

	@Override
	public void interact(int x, int y, Item item) {
		switch (item) {
			case KEY:
				Game.instance.showMessage("Ich brauche diesen Schlüssel noch, nur wofür...");
				break;
			case LEAF:
				Game.instance.showMessage("Das Blatt soll noch nicht leafen.");
				break;
			case COINS:
				Game.instance.showMessage("Nicht nötig, der Service ist für mich kostenlos.");
				break;
			case DRUG:
				Game.instance.showMessage("Besser nicht.");
				break;
			case SAW:
				Game.instance.showMessage("Ich brauche die Säge noch!");
				break;
			case KNIFE:
				Game.instance.showMessage("Nicht nötig.");
				break;
		}
	}

	@Override
	public void implicit(int x, int y) {

	}
}
