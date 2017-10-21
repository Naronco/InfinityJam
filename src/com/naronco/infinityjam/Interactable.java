package com.naronco.infinityjam;

import com.deviotion.ld.eggine.math.Rectangle2d;

public interface Interactable {
    String getName();
    Rectangle2d getRectangle();
    boolean hasLook();
    boolean hasUse();
    boolean hasTake();
    boolean hasPunch();
    void look();
    void use();
    void take();
    void punch();
}
