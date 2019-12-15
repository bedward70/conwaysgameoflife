package ru.bedward70.conwaysgameoflife.v3.action;

import ru.bedward70.conwaysgameoflife.v3.game.GenModelGame;
import ru.bedward70.conwaysgameoflife.v3.model.ModelDirection;
import ru.bedward70.conwaysgameoflife.v3.model.ModelSet;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ActionFactory {

    private static final int OPERATION_BITE_SHIFT = 3;
    private static final int EATING_DELIMITER = 2;

    private final Map<Integer, ActionTemplate> operationTemplateMap = new HashMap<>();

    public ActionFactory() {
        operationTemplateMap.put(0, new ActionTemplate("nop", 0, 1, (gn, b, m, g) -> true));
        operationTemplateMap.put(1, new ActionTemplate("step", 2, 8,  (gn, b, m, g) -> step(gn, b, m, g)));
        operationTemplateMap.put(2, new ActionTemplate("eating", 4, 8, (gn, b, m, g) -> eating(gn, b, m, g)));
        operationTemplateMap.put(3, new ActionTemplate("new_direction", 0, 4, (gn, b, m, g) -> changeDirection(gn, b, m, g)));
    }

    public ModelAction nextOperation(ActionGeneSet geneSet) {

        ModelAction result;
        final byte actionGene = geneSet.getCellAndIncreaseCounter();
        final ActionTemplate template = Optional
            .ofNullable(operationTemplateMap.get(actionGene >>> OPERATION_BITE_SHIFT))
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
}
