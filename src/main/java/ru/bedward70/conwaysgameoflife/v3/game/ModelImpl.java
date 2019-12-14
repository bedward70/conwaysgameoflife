package ru.bedward70.conwaysgameoflife.v3.game;

public class ModelImpl implements Model {

    private ModelDirection direction;

    private int x;
    private int y;
    private boolean alife;

    public ModelImpl(ModelDirection direction, int x, int y) {
        this.direction = direction;
        this.x = x;
        this.y = y;

        this.alife = true;
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

    @Override
    public void turn(GenModelGame game) {
        if (alife) {
            System.out.println("cycle");
            System.out.println("step " + this + " = " + step(game));
        } else {
            System.out.println("dead");
        }
    }

    private boolean step(GenModelGame game) {
        int toX = this.x + direction.getX();
        int toY = this.y + direction.getY();
        final boolean result = game.movelMove(this, toX, toY);
        if (result) {
            this.x = toX;
            this.y = toY;
        }
        return result;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ModelImpl{");
        sb.append("direction=").append(direction);
        sb.append(", x=").append(x);
        sb.append(", y=").append(y);
        sb.append(", alife=").append(alife);
        sb.append('}');
        return sb.toString();
    }
}
