package com.naronco.infinityjam;

import com.deviotion.ld.eggine.math.Rectangle2d;

public interface Interactable {
    String getName();
    boolean intersects(int x, int y);
    boolean hasLook();
    boolean hasUse();
    boolean hasTake();
    boolean hasPunch();
    void look(int x, int y);
    void use(int x, int y);
    void take(int x, int y);
    void punch(int x, int y);
}
