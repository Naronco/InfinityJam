package com.naronco.infinityjam.scenes;

import com.deviotion.ld.eggine.graphics.Screen;
import com.naronco.infinityjam.IScene;

public class Bedroom implements IScene {
    @Override
    public void load() {
    }

    @Override
    public void unload() {
    }

    @Override
    public String detailAt(int x, int y) {
        return null;
    }

    @Override
    public void click(int x, int y, int mode) {

    }

    @Override
    public void render(Screen screen) {
        screen.renderRectangle(0, 0, 16, 16, 0);
    }

    @Override
    public void update() {

    }
}
