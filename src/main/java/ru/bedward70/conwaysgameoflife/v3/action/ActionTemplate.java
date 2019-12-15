package ru.bedward70.conwaysgameoflife.v3.action;

import ru.bedward70.conwaysgameoflife.v3.game.GenModelGame;
import ru.bedward70.conwaysgameoflife.v3.model.ModelSet;

public class ActionTemplate {

    public static final int EATING_DELIMITER = 2;
    private final String name;
    private final int energy;
    private final int cycle;
    private final ActionFunction<ActionGeneSet, Byte, ModelSet, GenModelGame, Boolean> function;

    public ActionTemplate(
        String name,
        int energy,
        int cycle,
        ActionFunction<ActionGeneSet, Byte, ModelSet, GenModelGame, Boolean> function
    ) {
        this.name = name;
        this.energy = energy;
        this.cycle = cycle;
        this.function = function;
    }

    public String getName() {
        return name;
    }

    public int getEnergy() {
        return energy;
    }

    public int getCycle() {
        return cycle;
    }

    public ActionFunction<ActionGeneSet, Byte, ModelSet, GenModelGame, Boolean> getCallable() {
        return function;
    }

}
