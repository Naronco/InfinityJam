package com.naronco.infinityjam;

public class Player extends Character implements Interactable {
	public Player(int x, int y) {
		super(x, y);
	}

	public boolean safeDrug = false;
	public boolean drugged = false;

	@Override
	public String getName() {
		return "Du";
	}

	@Override
	public String getNameWithArticle() {
		return "ein Du";
	}

	@Override
	public boolean intersects(int x, int y) {
		return getRectangle().intersects(x, y);
	}

	@Override
	public boolean hasLook() {
		return false;
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
	}

	@Override
	public void use(int x, int y) {
	}

	@Override
	public void take(int x, int y) {
	}

	@Override
	public void punch(int x, int y) {
	}

	@Override
	public void interact(int x, int y, Item item) {
		if (item == Item.KNIFE)
			Game.instance.die();
		else if (item == Item.DRUG) {
			if (safeDrug)
				drugged = true;
			else
				Game.instance.die();
		}
	}

	@Override
	public void implicit(int x, int y) {
	}
}
