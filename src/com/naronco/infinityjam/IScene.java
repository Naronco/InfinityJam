package com.naronco.infinityjam;

import com.deviotion.ld.eggine.graphics.Screen;

public interface IScene {
    void enter();
    void leave();
    String detailAt(int x, int y);
    void click(int x, int y, int mode);
    void render(Screen screen);
    void update();
}
