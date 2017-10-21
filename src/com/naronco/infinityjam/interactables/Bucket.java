package com.naronco.infinityjam.interactables;

import com.deviotion.ld.eggine.math.Polygon2d;
import com.naronco.infinityjam.Game;
import com.naronco.infinityjam.Interactable;
import com.naronco.infinityjam.Item;

public class Bucket implements Interactable {
	private Polygon2d outline;

	public Bucket(Polygon2d outline) {
		this.outline = outline;
	}

	@Override
	public String getName() {
		return "Eimer";
	}

	@Override
	public String getNameWithArticle() {
		return "ein Eimer";
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
		Game.instance.showMessage("In diesem Eimer kommt mein Schei...uhm, Ko...uhm, Obama rein.");
	}

	@Override
	public void use(int x, int y) {
		Game.instance.showMessage("Gerade muss ich nicht.");
	}

	@Override
	public void take(int x, int y) {
		Game.instance.showMessage("Lieber nicht, den brauche ich lieber noch für Zuhause.");

	}

	@Override
	public void punch(int x, int y) {
		Game.instance.showMessage("Absolut Ekelhaft.");
	}

	@Override
	public void interact(int x, int y, Item item) {
		switch (item) {
			case KEY:
				Game.instance.showMessage("...Es wäre nicht schlecht, wenn der Schlüssel noch benutztbar wäre.");
				break;
		}
	}
}
