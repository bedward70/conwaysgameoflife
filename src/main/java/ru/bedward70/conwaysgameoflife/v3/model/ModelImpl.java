package ru.bedward70.conwaysgameoflife.v3.model;

import ru.bedward70.conwaysgameoflife.v3.game.GenModelGame;

public class ModelImpl implements Model {

    private static final int MAX_CYCLES = 8;
    private ModelDirection direction;

    private int x;
    private int y;
    private boolean alife;
    private int energy;

    public ModelImpl(ModelDirection direction, int x, int y, int energy) {
        this.direction = direction;
        this.x = x;
        this.y = y;
        this.energy = energy;
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

    public boolean isAlife() {
        return alife;
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
    public void turn(final GenModelGame game) {
        if (alife) {
            int cycle = 0;
            eat(2);
            while (alife && cycle < MAX_CYCLES) {
                ModelOperation operation = nextOperation();
                eat(operation.getEnergy());
                cycle += operation.getCycle();
                if (alife) {
                    System.out.println(this);

                    System.out.println("OP = " + operation.getName() + " : " + operation.getCallable().apply(game));
                }
//                System.out.println("cycle");
//                System.out.println("step " + this + " = " + step(game));
            }
        } else {
            System.out.println("dead");
        }
    }

    private ModelOperation nextOperation() {

        return new ModelOperation("step", 2, 8, g -> step(g));
    }

    private void eat(int i) {
        energy -= i;
        alife = alife && energy > 0;
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
        sb.append(", energy=").append(energy);
        sb.append('}');
        return sb.toString();
    }
}
