package ru.bedward70.conwaysgameoflife.v3.model;

import ru.bedward70.conwaysgameoflife.v3.game.GenModelGame;

import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ModelOperation {

    public static final int EATING_DELIMITER = 2;
    private final String name;
    private final int energy;
    private final int cycle;
    private final OperatorFunction<Byte, ModelSet, GenModelGame, Boolean> function;

    public ModelOperation(String name, int energy, int cycle,OperatorFunction<Byte, ModelSet, GenModelGame, Boolean> function) {
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

    public OperatorFunction<Byte, ModelSet, GenModelGame, Boolean> getCallable() {
        return function;
    }

    public static boolean step(Byte b, ModelSet model, GenModelGame game) {
        int toX = model.getX() + model.getDirection().getX();
        int toY = model.getY() + model.getDirection().getY();
        final boolean result = game.movelMove(model, toX, toY);
        if (result) {
            model.setX(toX);
            model.setY(toY);
        }
        return result;
    }

    public static boolean eating(Byte b, ModelSet model, GenModelGame game) {
        int energy = game.eating(model.getX(), model.getY()) / EATING_DELIMITER;
        model.addEnergy(energy);
        return true;
    }

    public static boolean changeDirection(Byte b, ModelSet model, GenModelGame game) {
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
