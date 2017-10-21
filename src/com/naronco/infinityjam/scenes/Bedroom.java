package com.naronco.infinityjam.scenes;

import com.deviotion.ld.eggine.graphics.Screen;
import com.deviotion.ld.eggine.graphics.Sprite;
import com.naronco.infinityjam.IScene;
import com.naronco.infinityjam.interactables.Bed;

import java.io.File;

public class Bedroom extends PointAndClickScene {
    public Bedroom() {
        super();
        background = new Sprite(new File("res/bedroom.png"));
        interactables.add(new Bed());
    }
}
