/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 Eduard Balovnev (bedward70)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package ru.bedward70.conwaysgameoflife.v3.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.bedward70.conwaysgameoflife.v3.action.ActionFactory;
import ru.bedward70.conwaysgameoflife.v3.action.ActionGeneSet;
import ru.bedward70.conwaysgameoflife.v3.action.ModelAction;
import ru.bedward70.conwaysgameoflife.v3.game.GenModelGame;

import java.util.Random;

public class ModelImpl implements Model, ModelSet {

    private static final int MAX_CYCLES = 8;
    private static final int MAX_ENERGY = 256;
    private static Random RANDOM = new Random();
    private static ActionGeneSet ACTION_GENE_SET = new ActionGeneSet() {

        @Override
        public byte getCellAndIncreaseCounter() {
            return (byte) (RANDOM.nextInt());
        }

        @Override
        public void readCellAndIncreaseCounterByValue(int i) {

        }

        @Override
        public ActionGeneSet copy(int i) {
            return new ActionGeneSetCycle();
        }
    };

    private static final ActionFactory ACTION_FACTORY = new ActionFactory();
    private final ActionGeneSet actionGeneSet;
    private ModelDirection direction;

    private final ModelColor color;
    @JsonProperty("x")
    private int x;
    @JsonProperty("y")
    private int y;
    @JsonProperty("alife")
    private boolean alife;
    @JsonProperty("energy")
    private int energy;

    public ModelImpl(final ModelColor color, ModelDirection direction, int x, int y, int energy) {
        this(ACTION_GENE_SET, color, direction, x, y, energy);
    }

    public ModelImpl(final ActionGeneSet actionGeneSet, final ModelColor color, ModelDirection direction, int x, int y, int energy) {
        this.actionGeneSet = actionGeneSet;
        this.color = color;
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

    public ModelColor getColor() {
        return color;
    }

    public ActionGeneSet getActionGeneSet() {
        return actionGeneSet;
    }

    @Override
    public void addEnergy(int additional) {
        this.energy = Math.min(this.energy + additional, MAX_ENERGY - 1);
    }

    @Override
    @JsonIgnore
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
