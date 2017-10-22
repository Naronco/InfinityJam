package com.naronco.infinityjam;

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
}
