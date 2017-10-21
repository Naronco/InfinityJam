package com.naronco.infinityjam;

import com.deviotion.ld.eggine.graphics.Sprite;
import com.deviotion.ld.eggine.graphics.SpriteAnimation;
import com.deviotion.ld.eggine.graphics.SpriteSheet;
import com.deviotion.ld.eggine.math.Dimension2d;
import com.deviotion.ld.eggine.math.Vector2d;

import java.io.File;

public class Character extends SpriteAnimation {
    public Character(int x, int y) {
        super(new SpriteSheet(new Sprite(new File("res/char.png")), new Dimension2d(19, 35)), 0, 0, 4);
        position = target = new Vector2d(x, y);
    }

	public void walkTo(Vector2d v) {
		target = v;
	}

	public void walkTo(int x, int y) {
		target = new Vector2d(x, y);
	}

    public Vector2d getPosition() {
        return position;
    }

    public Vector2d getSpritePosition() {
        return position.subtract(new Vector2d(10, 35));
    }

    @Override
    public void nextFrame() {
        super.nextFrame();
        Vector2d diff = target.subtract(position);
        if (diff.getLengthSquared() > 1) {
            diff = diff.normalized().multiply(0.8);
            position = position.add(diff);
        }
    }

    private Vector2d position, target;
}
