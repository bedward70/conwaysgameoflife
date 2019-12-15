package ru.bedward70.conwaysgameoflife.v3.model;

import ru.bedward70.conwaysgameoflife.v3.action.ActionFactory;
import ru.bedward70.conwaysgameoflife.v3.action.ActionGeneSet;
import ru.bedward70.conwaysgameoflife.v3.action.ModelAction;
import ru.bedward70.conwaysgameoflife.v3.game.GenModelGame;

import java.util.Random;

import static java.util.Objects.isNull;

public class ModelImpl implements Model, ModelSet {

    private static final int MAX_CYCLES = 8;
    private static final int MAX_ENERGY = 256;
    private static Random RANDOM = new Random();

    private static final ActionFactory ACTION_FACTORY = new ActionFactory();
    private static ActionGeneSet actionGeneSet = new ActionGeneSet() {

        @Override
        public byte getCellAndIncreaseCounter() {
            return (byte) (RANDOM.nextInt());
        }

        @Override
        public void readCellAndIncreaseCounterByValue(int i) {

        }
    };
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
    public int getEnergyForReproduction() {
        boolean reproduction = energy >= (MAX_ENERGY * 3 /4);
        int energyToShare;
        if (reproduction) {
            energyToShare = energy - MAX_ENERGY * 2 / 4;
            energy =  MAX_ENERGY * 2 / 4;
        } else {
            energyToShare = 0;
        }
        return energyToShare;
    }

    @Override
    public void turn(final GenModelGame game) {
        if (alife) {
            int cycle = 0;
            eat(2);
            while (alife && cycle < MAX_CYCLES) {
                ModelAction operation = ACTION_FACTORY.nextOperation(this.actionGeneSet);
                eat(operation.getEnergy());
                cycle += operation.getCycle();
                if (alife) {
                    System.out.println("OP = " + operation.getName() + " : " + operation.getCallable().apply(this, game));
                    System.out.println(this);
                }
            }
        } else {
            System.out.println("dead");
        }
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
