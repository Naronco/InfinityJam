package com.naronco.infinityjam.scenes;

import com.deviotion.ld.eggine.graphics.Screen;
import com.deviotion.ld.eggine.graphics.Sprite;
import com.deviotion.ld.eggine.math.Dimension2d;
import com.deviotion.ld.eggine.math.Rectangle2d;
import com.deviotion.ld.eggine.math.Vector2d;
import com.naronco.infinityjam.IScene;
import com.naronco.infinityjam.interactables.Bed;

import java.io.File;

public class Bedroom extends PointAndClickScene {
    public Bedroom() {
        super();
        background = new Sprite(new File("res/bedroom.png"));
        interactables.add(new Bed(new Rectangle2d(new Vector2d(40, 20), new Dimension2d(65, 80))));
    }
}
