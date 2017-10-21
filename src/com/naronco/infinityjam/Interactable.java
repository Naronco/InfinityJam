package com.naronco.infinityjam;

import com.deviotion.ld.eggine.math.Rectangle2d;

public interface Interactable {
    String getName();
    String getNameWithArticle();
    boolean intersects(int x, int y);
    boolean hasLook();
    boolean hasUse();
    boolean hasTake();
    boolean hasPunch();
    boolean hasImplicitClick();
    void look(int x, int y);
    void use(int x, int y);
    void take(int x, int y);
    void punch(int x, int y);
    void interact(int x, int y, Item item);
	void implicit(int x, int y);
}
