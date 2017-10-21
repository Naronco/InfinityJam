package com.deviotion.ld.eggine.math;

public class Rectangle2d {
    private Vector2d position;
    private Dimension2d size;

    public Rectangle2d (Vector2d position, Dimension2d size) {
        this.position = position;
        this.size = size;
    }

    public Vector2d getPosition() {
        return this.position;
    }
    public Dimension2d getSize() {
        return this.size;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }
    public void setSize(Dimension2d size) {
        this.size = size;
    }
}
