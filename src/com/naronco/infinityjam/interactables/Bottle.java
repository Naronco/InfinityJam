package com.naronco.infinityjam.interactables;

import com.deviotion.ld.eggine.math.Polygon2d;
import com.naronco.infinityjam.Game;
import com.naronco.infinityjam.Interactable;
import com.naronco.infinityjam.Item;

public class Bottle implements Interactable {
	private Polygon2d outline;

	public Bottle(Polygon2d outline) {
		this.outline = outline;
	}

	@Override
	public String getName() {
		return "Flasche Alkohol";
	}

	@Override
	public String getNameWithArticle() {
		return "eine Flasche Alkohol";
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
		Game.instance.showMessage("Eine offene Flasche, die mit Alkohol gefüllt ist.");
	}

	@Override
	public void use(int x, int y) {
		Game.instance.showMessage("Ich trinke vielleicht später etwas.");
	}

	@Override
	public void take(int x, int y) {
		Game.instance.showMessage("Nein, ich möchte keine Flaschen mit mir rumschleppen.");

	}

	@Override
	public void punch(int x, int y) {
		Game.instance.showMessage("Ich will die Flasche nicht kaputt machen, ich brauch sie bestimmt noch!");
	}

	@Override
	public void interact(int x, int y, Item item) {
		switch (item) {
			case KEY:
				Game.instance.showMessage("Die Flasche ist schon offen.");
				break;
			case LEAF:
				Game.instance.showMessage("In meiner Flasche kommt keine Pflanze rein!");
				break;
			case COINS:
				Game.instance.showMessage("Die Münze passt nicht mal durch den Öffnung!");
				break;
			case DRUG:
				Game.instance.showMessage("Ist ja nicht so, dass Alkohol allgemein schon schlimm genug ist...");
				break;
			case SAW:
				Game.instance.showMessage("Ich möchte es wenigstens noch austrinken.");
				break;
			case KNIFE:
				Game.instance.showMessage("Ich möchte es wenigstens noch austrinken.");
				break;
		}
	}

	@Override
	public void implicit(int x, int y) {

	}
}
