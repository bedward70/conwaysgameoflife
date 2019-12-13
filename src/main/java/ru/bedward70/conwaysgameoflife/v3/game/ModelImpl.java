package ru.bedward70.conwaysgameoflife.v3.game;

public class ModelImpl implements Model {

    private ModelDirection direction;

    private int x;
    private int y;

    public ModelImpl(ModelDirection direction, int x, int y) {
        this.direction = direction;
        this.x = x;
        this.y = y;
    }

    public ModelDirection getDirection() {
        return direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ModelImpl setDirection(ModelDirection direction) {
        this.direction = direction;
        return this;
    }

    public ModelImpl setX(int x) {
        this.x = x;
        return this;
    }

    public ModelImpl setY(int y) {
        this.y = y;
        return this;
    }
}
