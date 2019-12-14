package ru.bedward70.conwaysgameoflife.v3.game;

import java.util.concurrent.Callable;
import java.util.function.Function;

public class ModelOperation {

    private final String name;
    private final int energy;
    private final int cycle;
    private final Function<GenModelGame, Boolean> callable;

    public ModelOperation(String name, int energy, int cycle, Function<GenModelGame, Boolean> callable) {
        this.name = name;
        this.energy = energy;
        this.cycle = cycle;
        this.callable = callable;
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

    public Function<GenModelGame, Boolean> getCallable() {
        return callable;
    }
}
