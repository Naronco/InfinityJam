package com.naronco.infinityjam.interactables;

import com.deviotion.ld.eggine.math.Dimension2d;
import com.deviotion.ld.eggine.math.Polygon2d;
import com.deviotion.ld.eggine.math.Rectangle2d;
import com.deviotion.ld.eggine.math.Vector2d;
import com.naronco.infinityjam.Game;
import com.naronco.infinityjam.Interactable;
import com.naronco.infinityjam.Item;

import java.awt.*;

import static com.naronco.infinityjam.Item.KEY;

public class Bed implements Interactable {
	private Polygon2d outline;
	private boolean hasKnife = true;

	public Bed(Polygon2d outline) {
		this.outline = outline;
	}

	@Override
	public String getName() {
		return "Bett";
	}

	@Override
	public String getNameWithArticle() {
		return "ein Bett";
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
		Game.instance.showMessage("Enthält Krabbeltierchen falls ich Nachts hungrig werde.");
	}

	@Override
	public void use(int x, int y) {
		Game.instance.showMessage("Nein Danke, ich bin schon ausgeschlafen.");
	}

	@Override
	public void take(int x, int y) {
		if (hasKnife) {
			Game.instance.showMessage("Ein Messer!");
			hasKnife = false;
			Game.instance.giveItems(Item.KNIFE, 1, 3);
		}
		else {
			Game.instance.showMessage("Sehe ich aus wie Chuck Norris?");
		}
	}

	@Override
	public void punch(int x, int y) {
		Game.instance.showMessage("Das nützt nichts");
	}

	@Override
	public void interact(int x, int y, Item item) {
		switch (item) {
			case KEY:
				Game.instance.showMessage("Der Schlüssel zum Erfolg ist definitiv nicht schlafen.");
				break;
			case LEAF:
				Game.instance.showMessage("Das ist nutzlos.");
				break;
			case COINS:
				Game.instance.showMessage("Ich habe schon zu viel Geld für die BedRulls Matraze ausgegeben.");
				break;
			case DRUG:
				Game.instance.showMessage("Dafür wurden Drogen nicht gemacht.");
				break;
			case SAW:
				Game.instance.showMessage("Lieber nicht, ich muss noch drauf schlafen.");
				break;
			case KNIFE:
				if (Game.instance.removeItem(Item.KNIFE, 1)) {
					hasKnife = true;
				}
				break;
		}
	}

	@Override
	public void implicit(int x, int y) {

	}
}
