package com.naronco.infinityjam;

import com.deviotion.ld.eggine.graphics.Screen;
import com.deviotion.ld.eggine.sound.Sound;

public interface IScene {
    void load();
    void enter(IScene prev);
    void leave();
    String detailAt(int x, int y);
    void click(int x, int y, int mode);
    void renderBackground(Screen screen);
    void renderForeground(Screen screen);
    void update();
    Sound getBackgroundMusic();
    boolean showsPlayer();
}
