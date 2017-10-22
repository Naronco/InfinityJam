package com.naronco.infinityjam.interactables;

import com.deviotion.ld.eggine.math.Polygon2d;
import com.deviotion.ld.eggine.math.Vector2d;
import com.naronco.infinityjam.Game;
import com.naronco.infinityjam.Interactable;
import com.naronco.infinityjam.Item;
import com.naronco.infinityjam.dialog.Dialog;

public class DialogTrigger implements Interactable {
	public String name;
	public Polygon2d outline;
	public Dialog trigger;

	public DialogTrigger(String name, Polygon2d outline, Dialog trigger) {
		this.name = name;
		this.outline = outline;
		this.trigger = trigger;
	}

	@Override
	public boolean hasPunch() {
		return true;
	}

	@Override
	public boolean hasImplicitClick() {
		return true;
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
	public void look(int x, int y) {
		Game.instance.showMessage("Sieht aus wie jeder andere");
	}

	@Override
	public void use(int x, int y) {
		Game.instance.pushDialog(trigger);
	}

	@Override
	public void take(int x, int y) {
		Game.instance.showMessage("Ich glaub das würde er nicht wollen.");
	}

	@Override
	public void punch(int x, int y) {
		Game.instance.showMessage("Er hat mir doch nichts getan.");
	}

	@Override
	public void interact(int x, int y, Item item) {
		if (item == Item.KNIFE || item == Item.SAW)
			Game.instance.showMessage("Ich möchte keinen weh tun.");
		else
			Game.instance.showMessage("Das behalt ich lieber.");
	}

	@Override
	public void implicit(int x, int y) {
		use(x, y);
	}
}
