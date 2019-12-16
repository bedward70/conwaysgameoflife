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

public enum ModelDirection {

    FRONT(0, 0, -1),
    RIGHT(1, 1, 0),
    BACK(2, 0, 1),
    LEFT(3, -1, 0);

    private final int index;
    private final int x;
    private final int y;

    ModelDirection(int index,int x, int y) {
        this.index = index;
        this.x = x;
        this.y = y;
    }

    public int getIndex() {
        return index;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static ModelDirection valueOf(final int index) {
        int validIndex = index;
        while (validIndex < 0) {
            validIndex +=4;
        }
        validIndex %=4;
        for (ModelDirection modelDirection: values()) {
            if (modelDirection.index == validIndex) {
                return modelDirection;
            }
        }
        return ModelDirection.FRONT;
    }

    public ModelDirection plus(ModelDirection modelDirection) {
        return valueOf(index + modelDirection.index);
    }
}
