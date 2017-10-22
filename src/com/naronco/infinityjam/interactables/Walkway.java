package com.naronco.infinityjam.interactables;

import com.deviotion.ld.eggine.math.Polygon2d;
import com.deviotion.ld.eggine.math.Vector2d;
import com.naronco.infinityjam.Game;
import com.naronco.infinityjam.Item;

public class Walkway extends Door {
	public String name;

	public Walkway(String name, Vector2d walkTo, Polygon2d outline) {
		super(walkTo, outline);
		this.name = name;
	}

	@Override
	public boolean hasPunch() {
		return false;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getNameWithArticle() {
		return getName();
	}

	@Override
	public void look(int x, int y) {
		Game.instance.showMessage("Das sieht nach einem Durchgang aus...");
	}

	@Override
	public void interact(int x, int y, Item item) {
	}
}
