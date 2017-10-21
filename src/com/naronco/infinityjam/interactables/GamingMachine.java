package com.naronco.infinityjam.interactables;

import com.deviotion.ld.eggine.math.Polygon2d;
import com.naronco.infinityjam.Game;
import com.naronco.infinityjam.Interactable;
import com.naronco.infinityjam.Item;
import com.naronco.infinityjam.scenes.SlotMachine;

public class GamingMachine implements Interactable {
	private Polygon2d outline;

	public GamingMachine(Polygon2d outline) {
		this.outline = outline;
	}

	@Override
	public String getName() {
		return "Spielautomat";
	}

	@Override
	public String getNameWithArticle() {
		return "ein Spielautomat";
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
		Game.instance.showMessage("TEAM:TEXT EINFÜGEN");
	}

	@Override
	public void use(int x, int y) {
		Game.instance.setScene(new SlotMachine());
	}

	@Override
	public void take(int x, int y) {
		Game.instance.showMessage("TEAM:TEXT EINFÜGEN");
	}

	@Override
	public void punch(int x, int y) {
		Game.instance.showMessage("TEAM:TEXT EINFÜGEN");
	}

	@Override
	public void interact(int x, int y, Item item) {
	}
}
