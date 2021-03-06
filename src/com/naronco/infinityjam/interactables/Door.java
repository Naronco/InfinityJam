package com.naronco.infinityjam.interactables;

import com.deviotion.ld.eggine.math.Polygon2d;
import com.deviotion.ld.eggine.math.Vector2d;
import com.naronco.infinityjam.Game;
import com.naronco.infinityjam.Interactable;
import com.naronco.infinityjam.Item;

public class Door implements Interactable {
	private Polygon2d outline;
	public Vector2d walkTo;

	public Door(Vector2d walkTo, Polygon2d outline) {
		this.walkTo = walkTo;
		this.outline = outline;
	}

	@Override
	public String getName() {
		return "Tür";
	}

	@Override
	public String getNameWithArticle() {
		return "eine Tür";
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
		return true;
	}

	@Override
	public void look(int x, int y) {
		Game.instance.showMessage("Hinter dieser Tür könnte sich alles verbergen.");
	}

	@Override
	public void use(int x, int y) {
		take(x, y);
	}

	@Override
	public void take(int x, int y) {
		Game.instance.player.walking = true;
		Game.instance.player.wasWalking = true;
		Game.instance.player.walkTo(walkTo);
	}

	@Override
	public void punch(int x, int y) {
		Game.instance.showMessage("Sicherheitstest bestanden.");
	}

	@Override
	public void interact(int x, int y, Item item) {
		switch (item) {
			case KEY:
				Game.instance.showMessage("Der passt hier nicht.");
				break;
			case LEAF:
				Game.instance.showMessage("Man kann Türen nicht mit einem Blatt öffnen");
				break;
			case COINS:
				Game.instance.showMessage("Bis jetzt hat es niemand geschafft, eine Tür zu bestechen.");
				break;
			case DRUG:
				Game.instance.showMessage("Man könnte versuchen die Tür mit Drogen zu bestechen, wenn sie leben würde.");
				break;
			case SAW:
				Game.instance.showMessage("Ich säge die Tür nicht durch, weil....Gründe!");
				break;
			case KNIFE:
				Game.instance.showMessage("Ich würde da nur ein Loch in die Tür schnitzen, aber nicht die Tür öffnen...");
				break;
		}
	}

	@Override
	public void implicit(int x, int y) {
		take(x, y);
	}
}
