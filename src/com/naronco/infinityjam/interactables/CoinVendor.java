package com.naronco.infinityjam.interactables;

import com.deviotion.ld.eggine.math.Polygon2d;
import com.naronco.infinityjam.Game;
import com.naronco.infinityjam.Interactable;
import com.naronco.infinityjam.Item;
import com.naronco.infinityjam.scenes.SlotMachine;

public class CoinVendor implements Interactable {
	private Polygon2d outline;
	private long requestTime = 0;

	public CoinVendor(Polygon2d outline) {
		this.outline = outline;
	}

	@Override
	public String getName() {
		return "Geldleihe";
	}

	@Override
	public String getNameWithArticle() {
		return "die Geldleihe";
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
		return false;
	}

	@Override
	public boolean hasPunch() {
		return false;
	}

	@Override
	public boolean hasImplicitClick() {
		return false;
	}

	@Override
	public void look(int x, int y) {
		Game.instance.showMessage("Der hier gibt armen Menschen Credits um mitzuspielen");
	}

	@Override
	public void use(int x, int y) {
		if (!Game.instance.giveItems(Item.COINS, 0, 1)) {
			Game.instance.showMessage("Du hast doch noch Geld!");
		} else {
			if (requestTime == 0) {
				requestTime = System.currentTimeMillis();
				Game.instance.showMessage("Ihre Anfrage wird nun verarbeitet, kommen Sie später erneut um Ihr Geld abzuholen.");
			} else {
				long ms = System.currentTimeMillis() - requestTime;
				if (ms / 1000 > 60 * 2) {
					Game.instance.giveItems(Item.COINS, 1, 1);
					Game.instance.showMessage("Bitte gehen Sie diesmal sorgfältiger mit Ihrem Geld um.");
					requestTime = 0;
				} else {
					Game.instance.showMessage("Wir sind noch am bearbeiten Ihrer Anfrage, kommen Sie später nochmal.");
				}
			}
		}
	}

	@Override
	public void take(int x, int y) {
	}

	@Override
	public void punch(int x, int y) {
	}

	@Override
	public void interact(int x, int y, Item item) {
		switch (item) {
			case KEY:
				Game.instance.showMessage("Der gehört mir!");
				break;
			case LEAF:
				Game.instance.showMessage("Was will er damit?");
				break;
			case COINS:
				Game.instance.showMessage("Der soll mir Geld geben und nicht anders rum!");
				break;
			case DRUG:
				Game.instance.showMessage("Nein, er ist am Arbeiten");
				break;
			case SAW:
			case KNIFE:
				Game.instance.showMessage("Dafür würden die mich rausschmeissen");
				break;
		}
	}

	@Override
	public void implicit(int x, int y) {
	}
}
