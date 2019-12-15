package ru.bedward70.conwaysgameoflife.v3.game;

import ru.bedward70.conwaysgameoflife.v3.model.Model;
import ru.bedward70.conwaysgameoflife.v3.model.ModelImpl;

public interface GenModelGame {


    boolean movelMove(Model model, int toX, int toY);

    int eating(int x, int y);

    int getWidth();

    int getHeight();

    void addModel(ModelImpl modelImpl);
}
