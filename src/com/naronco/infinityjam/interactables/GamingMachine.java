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
	public boolean hasImplicitClick() {
		return false;
	}

	@Override
	public void look(int x, int y) {
		Game.instance.showMessage("Diese Maschine ist mein Beruf");
	}

	@Override
	public void use(int x, int y) {
		Game.instance.setScene(new SlotMachine());
	}

	@Override
	public void take(int x, int y) {
		Game.instance.showMessage("Wenn ich Pleite bin und einen Kran habe, vielleicht.");
	}

	@Override
	public void punch(int x, int y) {
		Game.instance.showMessage("Keine Chance!");
	}

	@Override
	public void interact(int x, int y, Item item) {
		switch (item) {
			case KEY:
				Game.instance.showMessage("Dieser Schlüssel funktioniert nicht bei Automaten.");
				break;
			case LEAF:
				Game.instance.showMessage("Dafür brauche ich Scheine, und kein Blatt");
				break;
			case COINS:
				SlotMachine scene = new SlotMachine();
				Game.instance.setScene(scene);
				scene.roll();
				break;
			case DRUG:
				Game.instance.showMessage("Die soll ich nehmen, und nicht der Automat!");
				break;
			case SAW:
				Game.instance.showMessage("Die Säge würde bei Metall gar Zerbrechen!");
				break;
			case KNIFE:
				Game.instance.showMessage("Lustige Vorstellung, aber Nein.");
				break;
		}
	}

	@Override
	public void implicit(int x, int y) {

	}
}
