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
package ru.bedward70.conwaysgameoflife.v3.game;

import ru.bedward70.conwaysgameoflife.v3.model.Model;
import ru.bedward70.conwaysgameoflife.v3.model.ModelColor;
import ru.bedward70.conwaysgameoflife.v3.model.ModelDirection;
import ru.bedward70.conwaysgameoflife.v3.model.ModelImpl;
import ru.bedward70.conwaysgameoflife.v3.paint.PaintModel;
import ru.bedward70.conwaysgameoflife.v3.paint.PaintModelImpl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import static java.util.Objects.isNull;

public class GenGameImpl implements GenGame, GenModelGame, Field {

    private final int width;
    private final int height;
    private final byte[][] foodArray;
    private final byte[][] mineralArray;
    private final LinkedList<ModelImpl> models = new LinkedList<>();

    private Random random = new Random();

    public GenGameImpl(final int width, final int height) {
        this.width = width;
        this.height = height;
        this.foodArray = new byte[width][height];
        this.mineralArray = new byte[width][height];

        for (int x = 0; x < width; x ++) {
            for (int y = 0; y < height; y ++) {
                this.foodArray[x][y] = (byte) (0x00ff & random.nextInt());
                this.mineralArray[x][y] = (byte) (0x00ff & random.nextInt());
            }
        }
        models.add(new ModelImpl(ModelColor.WHITE, ModelDirection.FRONT, random.nextInt(width), random.nextInt(height), 128));
        models.add(new ModelImpl(ModelColor.BLACK, ModelDirection.FRONT, random.nextInt(width), random.nextInt(height), 128));
        models.add(new ModelImpl(ModelColor.PINK, ModelDirection.FRONT, random.nextInt(width), random.nextInt(height), 128));
        models.add(new ModelImpl(ModelColor.GREEN, ModelDirection.FRONT, random.nextInt(width), random.nextInt(height), 128));
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void addModel(ModelImpl modelImpl) {
        models.add(modelImpl);
    }

    @Override
    public synchronized PaintModel[][] getPaintModels() {
        final PaintModel[][] result = new PaintModel[width][height];
        final Model[][] modelArray = new Model[width][height];
        models.forEach(model -> modelArray[model.getX()][model.getY()] = model);

        for (int x = 0; x < width; x ++) {
            for (int y = 0; y < height; y++) {
                result[x][y] = new PaintModelImpl(foodArray[x][y], mineralArray[x][y], modelArray[x][y]);
            }
        }
        return result;
    }

    @Override
    public synchronized void clean() {
        System.out.println("clean");
        models.clear();
    }

    @Override
    public LinkedList<ModelImpl> getModels() {
        return models;
    }

    @Override
    public synchronized void turn() {

        // foods
        for (int x = 0; x < width; x ++) {
            for (int y = 0; y < height; y++) {
                if ((0x00ff & this.foodArray[x][y]) != 0x00ff) {
                    this.foodArray[x][y] = (byte) ((0x00ff & this.foodArray[x][y]) + 1);
                }
            }
        }
        // models
        new ArrayList<>(this.models)
            .forEach(model -> model.turn(this));
        this.models.removeIf(model -> !model.isAlife());
    }

    @Override
    public boolean movelMove(final Model model, final int toX, final int toY) {
        return !(
            (toX < 0)
            || (toX >= width)
            || (toY < 0)
            || (toY >= height)
            || this.models.stream()
                .filter(m -> isNull(model) || m != model)
                .anyMatch(m -> m.getX() == toX && m.getY() == toY)
        );
    }

    @Override
    public int eating(int x, int y) {
        int result = (0x00ff & foodArray[x][y]) / 2;
        foodArray[x][y] = (byte) result;
        return result;
    }

    @Override
    public byte getFood(int x, int y) {
        return foodArray[x][y];
    }
}
