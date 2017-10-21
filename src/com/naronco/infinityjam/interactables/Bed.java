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
    public boolean intersects(int x, int y) {
        return x < 105 && x > 40 && y > 20 && y < 100;
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
        Game.instance.showMessage("The bed feels bad and doesn't know what it has done wrong.");
    }
}
