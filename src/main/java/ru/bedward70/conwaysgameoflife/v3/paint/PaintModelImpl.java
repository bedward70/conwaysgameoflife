package ru.bedward70.conwaysgameoflife.v3.paint;

import ru.bedward70.conwaysgameoflife.v3.model.Model;

public class PaintModelImpl implements PaintModel {

    private final byte food;
    private final byte mineral;
    private final Model model;

    public PaintModelImpl(byte food, byte mineral, Model model) {
        this.food = food;
        this.mineral = mineral;
        this.model = model;
    }

    @Override
    public byte getFood() {
        return food;
    }

    @Override
    public byte getMineral() {
        return mineral;
    }

    @Override
    public Model getModel() {
        return model;
    }
}
