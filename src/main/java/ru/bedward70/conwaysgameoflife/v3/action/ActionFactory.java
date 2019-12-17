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
import ru.bedward70.conwaysgameoflife.v3.model.ModelColor;
import ru.bedward70.conwaysgameoflife.v3.model.ModelDirection;
import ru.bedward70.conwaysgameoflife.v3.model.ModelImpl;
import ru.bedward70.conwaysgameoflife.v3.model.ModelSet;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class ActionFactory {

    private static final int OPERATION_BITE_SHIFT = 3;
    private static final int EATING_DELIMITER = 2;
    private static Random RANDOM = new Random();

    private final Map<Integer, ActionTemplate> operationTemplateMap = new HashMap<>();

    public ActionFactory() {
        operationTemplateMap.put(0, new ActionTemplate("nop", 0, 1, (gn, b, m, g) -> true));
        operationTemplateMap.put(1, new ActionTemplate("step", 2, 8,  (gn, b, m, g) -> step(gn, b, m, g)));
        operationTemplateMap.put(2, new ActionTemplate("eating", 4, 8, (gn, b, m, g) -> eating(gn, b, m, g)));
        operationTemplateMap.put(3, new ActionTemplate("new_direction", 0, 4, (gn, b, m, g) -> changeDirection(gn, b, m, g)));
        operationTemplateMap.put(4, new ActionTemplate("reproduction", 0, 8, (gn, b, m, g) -> reproduction(gn, b, m, g)));
        operationTemplateMap.put(5, new ActionTemplate("food_level", 0, 1, (gn, b, m, g) -> foodLevel(gn, b, m, g)));
    }

    public ModelAction nextOperation(ActionGeneSet geneSet) {

        ModelAction result;
        final byte actionGene = geneSet.getCellAndIncreaseCounter();
        final ActionTemplate template = Optional
            .ofNullable(operationTemplateMap.get((0x003F & actionGene) >>> OPERATION_BITE_SHIFT))
            .orElseGet(() -> operationTemplateMap.get(0));

        return new ModelAction(
            geneSet,
            actionGene,
            template
        );
    }

    public static boolean step(ActionGeneSet g, Byte b, ModelSet model, GenModelGame game) {
        int toX = model.getX() + model.getDirection().getX();
        int toY = model.getY() + model.getDirection().getY();
        final boolean result = game.movelMove(model, toX, toY);
        if (result) {
            model.setX(toX);
            model.setY(toY);
        }
        return result;
    }

    public static boolean eating(ActionGeneSet g, Byte b, ModelSet model, GenModelGame game) {
        int energy = game.eating(model.getX(), model.getY()) / EATING_DELIMITER;
        model.addEnergy(energy);
        return true;
    }

    public static boolean changeDirection(ActionGeneSet g, Byte b, ModelSet model, GenModelGame game) {
        ModelDirection newDirection = ModelDirection.valueOf(0x03 & b);
        boolean relative = (0x04 & b) > 0;
        if (relative) {
            model.setDirection(ModelDirection.valueOf(newDirection.getIndex() + model.getDirection().getIndex()));
        } else {
            model.setDirection(newDirection);
        }
        return true;
    }
    public static boolean reproduction(ActionGeneSet g, Byte b, ModelSet model, GenModelGame game) {
        final int energy = model.getEnergyForReproduction();

        while (energy > 0) {
            int x = RANDOM.nextInt(game.getWidth());
            int y = RANDOM.nextInt(game.getHeight());
            final boolean result = game.movelMove(null, x, y);
            if (result) {
                ModelImpl modelImpl;
                if (ModelColor.BLACK.equals(model.getColor())) {
                    modelImpl = new ModelImpl(
                        model.getActionGeneSet().copy(1),
                        model.getColor(),
                        ModelDirection.valueOf(RANDOM.nextInt(4)),
                        x,
                        y,
                        energy
                    );
                } else {
                    modelImpl = new ModelImpl(
                        model.getColor(),
                        ModelDirection.valueOf(RANDOM.nextInt(4)),
                        x,
                        y,
                        energy
                    );
                }
                game.addModel(modelImpl);
                break;
            }
        }

        return energy > 0;
    }


    private Boolean foodLevel(ActionGeneSet gn, Byte b, ModelSet m, GenModelGame g) {
        byte food = g.getFood(m.getX(), m.getY());
        byte level = gn.getCellAndIncreaseCounter();
        if ((0x00ff & food) >= (0x00ff & level)) {
            gn.readCellAndIncreaseCounterByValue(0);
        } else {
            gn.readCellAndIncreaseCounterByValue(1);
        }
        return true;
    }
}
