package com.naronco.infinityjam;

import com.deviotion.ld.eggine.graphics.Sprite;

public enum Item {
	KEY,
	LEAF,
	COINS,
	DRUG,
	SAW,
	KNIFE;

	public String toString() {
		switch (this) {
			case KEY: return "Schlüssel";
			case LEAF: return "Blatt";
			case COINS: return "Münzen";
			case DRUG: return "Droge";
			case SAW: return "Kreissäge";
			case KNIFE: return "Messer";
		}
		return null;
	}

	public Sprite getSprite() {
		switch (this) {
			case KEY: return Sprites.KEY;
			case LEAF: return Sprites.LEAF;
			case COINS: return Sprites.COIN;
			case DRUG: return Sprites.DRUG;
			case SAW: return Sprites.SAW;
			case KNIFE: return Sprites.KNIFE;
		}
		return null;
	}
}
