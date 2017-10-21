package com.naronco.infinityjam.interactables;

import com.deviotion.ld.eggine.math.Dimension2d;
import com.deviotion.ld.eggine.math.Rectangle2d;
import com.deviotion.ld.eggine.math.Vector2d;
import com.naronco.infinityjam.Game;
import com.naronco.infinityjam.Interactable;

public class Bed implements Interactable {
    @Override
    public String getName() {
        return "Bed";
    }

    @Override
    public Rectangle2d getRectangle() {
        return new Rectangle2d(new Vector2d(0, 0), new Dimension2d(50, 50));
    }

    @Override
    public boolean hasLook() {
        return true;
    }

    @Override
    public boolean hasUse() {
        return false;
    }

    @Override
    public boolean hasTake() {
        return false;
    }

    @Override
    public boolean hasPunch() {
        return true;
    }

    @Override
    public void look() {

    }

    @Override
    public void use() {
    }

    @Override
    public void take() {
    }

    @Override
    public void punch() {
        Game.instance.showMessage("The bed feels bad and doesn't know what it has done wrong.");
    }
}
