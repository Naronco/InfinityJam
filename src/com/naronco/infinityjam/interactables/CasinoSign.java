package com.naronco.infinityjam.interactables;

import com.deviotion.ld.eggine.math.Polygon2d;
import com.naronco.infinityjam.Game;
import com.naronco.infinityjam.Interactable;
import com.naronco.infinityjam.Item;

public class CasinoSign implements Interactable {
	private Polygon2d outline;

	public CasinoSign(Polygon2d outline) {
		this.outline = outline;
	}

	@Override
	public String getName() {
		return "Casino-Schild";
	}

	@Override
	public String getNameWithArticle() {
		return "ein Casino-Schild";
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
		Game.instance.showMessage("Der Name dieses Casinos ist so lang, der volle Name passt nicht mal im Screen rein.");
	}

	@Override
	public void use(int x, int y) {
		Game.instance.showMessage("Ich komme da nicht ran.");
	}

	@Override
	public void take(int x, int y) {
		Game.instance.showMessage("Das Schild sitzt fest über der Tür.");

	}

	@Override
	public void punch(int x, int y) {
		Game.instance.showMessage("Eine Elektroschock wäre gerade eher unpassend.");
	}

	@Override
	public void interact(int x, int y, Item item) {
		switch (item) {
			case KEY:
				Game.instance.showMessage("Lieber Spieler, das Schild stellt keine Tür da. Die Tür ist direkt unten drunter!");
				break;
		}
	}

	@Override
	public void implicit(int x, int y) {

	}
}
