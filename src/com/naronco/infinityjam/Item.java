package com.naronco.infinityjam;

public enum Item {
	KEY,
	LEAF,
	COINS,
	DRUG,
	SAW,
	KNIFE;

	public int valueOf() {
		switch (this) {
			case KEY:
				return 0;
			case LEAF:
				return 1;
			case COINS:
				return 2;
			case DRUG:
				return 3;
			case SAW:
				return 4;
			case KNIFE:
				return 5;
		}
		throw new Error("Retarded programmers didn't implement this Item");
	}
}
