package com.naronco.infinityjam;

public enum Item {
	KEY,
	LEAF,
	COINS;

	public int valueOf() {
		switch (this) {
			case KEY:
				return 0;
			case LEAF:
				return 1;
			case COINS:
				return 2;
		}
		throw new Error("Retarded programmers didn't implement this Item");
	}
}
