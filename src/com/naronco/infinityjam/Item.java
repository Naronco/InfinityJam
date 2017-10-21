package com.naronco.infinityjam;

public enum Item {
	KEY;

	public int valueOf() {
		switch (this) {
			case KEY:
				return 0;
		}
		throw new Error("Retarded programmers didn't implement this Item");
	}
}
