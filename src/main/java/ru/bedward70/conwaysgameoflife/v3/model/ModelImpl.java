package ru.bedward70.conwaysgameoflife.v3.model;

import ru.bedward70.conwaysgameoflife.v3.game.GenModelGame;

import java.util.Random;

import static java.util.Objects.isNull;

public class ModelImpl implements Model, ModelSet {

    private static final int MAX_CYCLES = 8;
    private static final int MAX_ENERGY = 256;
    private ModelDirection direction;

    private int x;
    private int y;
    private boolean alife;
    private int energy;

    private Random random = new Random();

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

    public void setDirection(ModelDirection direction) {
        this.direction = direction;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void addEnergy(int additional) {
        this.energy = Math.min(this.energy + additional, MAX_ENERGY - 1);
    }

    @Override
    public void turn(final GenModelGame game) {
        if (alife) {
            int cycle = 0;
            eat(2);
            while (alife && cycle < MAX_CYCLES) {
                final byte i = (byte) (0x001F & random.nextInt());
                ModelOperation operation = nextOperation(i);
                if (isNull(operation)) {
                    cycle++;
                } else {
                    eat(operation.getEnergy());
                    cycle += operation.getCycle();
                    if (alife) {
                        System.out.println("OP = " + operation.getName() + " : " + operation.getCallable().apply(i, this, game));
                        System.out.println(this);
                    }
                }
            }
        } else {
            System.out.println("dead");
        }
    }

    private ModelOperation nextOperation(byte i) {
        ModelOperation result;
        if ((i & 0x0018) == 0x0008) {
            result = new ModelOperation("step", 2, 8, (b, m, g) -> ModelOperation.step(b, m, g));
        } else if ((i & 0x0018) == 0x0010) {
            result = new ModelOperation("eating", 4, 8, (b, m, g) -> ModelOperation.eating(b, m, g));
        } else if ((i & 0x0018) == 0x0018) {
            result = new ModelOperation("new_direction", 0, 4, (b, m, g) -> ModelOperation.changeDirection(b, m, g));
        } else {
            result = null;
        }
        return result;
    }

    private void eat(int i) {
        energy -= i;
        alife = alife && energy > 0;
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
