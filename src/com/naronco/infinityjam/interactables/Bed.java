package com.naronco.infinityjam.interactables;

import com.deviotion.ld.eggine.math.Dimension2d;
import com.deviotion.ld.eggine.math.Polygon2d;
import com.deviotion.ld.eggine.math.Rectangle2d;
import com.deviotion.ld.eggine.math.Vector2d;
import com.naronco.infinityjam.Game;
import com.naronco.infinityjam.Interactable;
import com.naronco.infinityjam.Item;

import java.awt.*;

import static com.naronco.infinityjam.Item.KEY;

public class Bed implements Interactable {
    private Polygon2d outline;

    public Bed(Polygon2d outline) {
        this.outline = outline;
    }

    @Override
    public String getName() {
        return "Bed";
    }

    @Override
    public boolean intersects(int x, int y) {
        return outline.intersects(x, y);
    }

    @Override
    public boolean hasLook() {
        return false;
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
        Game.instance.showMessage("Bett Weich.");
    }

    @Override
    public void interact(int x, int y, Item item) {
        switch (item) {
            case KEY:
                Game.instance.showMessage("Schlüssel mit Bett");
                break;
        }
    }
}
