package com.naronco.infinityjam.scenes;

import com.deviotion.ld.eggine.graphics.Screen;
import com.deviotion.ld.eggine.graphics.Sprite;
import com.naronco.infinityjam.Game;
import com.naronco.infinityjam.IScene;
import com.naronco.infinityjam.Interactable;

import java.util.ArrayList;
import java.util.List;

public class PointAndClickScene implements IScene {
    protected List<Interactable> interactables = new ArrayList<Interactable>();
    protected Sprite background;

    @Override
    public void enter() {
    }

    @Override
    public void leave() {
    }

    @Override
    public String detailAt(int x, int y) {
        for (Interactable i : interactables) {
            if (i.intersects(x, y))
                return i.getName();
        }
        return null;
    }

    @Override
    public void click(int x, int y, int mode) {
        for (Interactable i : interactables) {
            if (i.intersects(x, y)) {
                switch (mode)
                {
                    case Game.MODE_LOOK:
                        if (!i.hasLook())
                            Game.instance.showMessage("The Developers forgot to make this viewable.");
                        else
                            i.look(x, y);
                        break;
                    case Game.MODE_USE:
                        if (!i.hasUse())
                            Game.instance.showMessage("I can't use this.");
                        else
                            i.use(x, y);
                        break;
                    case Game.MODE_TAKE:
                        if (!i.hasTake())
                            Game.instance.showMessage("I can't take this.");
                        else
                            i.take(x, y);
                        break;
                    case Game.MODE_PUNCH:
                        if (!i.hasPunch())
                            Game.instance.showMessage("Violence won't help.");
                        else
                            i.punch(x, y);
                        break;
                }
                return;
            }
        }
    }

    @Override
    public void render(Screen screen) {
        screen.renderSprite(0,0, background);
    }

    @Override
    public void update() {
    }
}
