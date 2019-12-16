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
package ru.bedward70.conwaysgameoflife.v3.action;

import ru.bedward70.conwaysgameoflife.v3.game.GenModelGame;
import ru.bedward70.conwaysgameoflife.v3.model.ModelSet;

import java.util.function.BiFunction;

public class ModelAction {

    private final ActionGeneSet geneSet;
    private final byte actionGene;
    private final ActionTemplate template;

    public ModelAction(ActionGeneSet geneSet, byte actionGene, ActionTemplate template) {
        this.geneSet = geneSet;
        this.actionGene = actionGene;
        this.template = template;
    }

    public String getName() {
        return this.template.getName();
    }

    public int getEnergy() {
        return this.template.getEnergy();
    }

    public int getCycle() {
        return this.template.getCycle();
    }

    public BiFunction<ModelSet, GenModelGame, Boolean> getCallable() {
        return (m, g) -> this.template.getCallable().apply(this.geneSet, this.actionGene, m, g);
    }
}
