package ru.bedward70.conwaysgameoflife.v3.game;

import ru.bedward70.conwaysgameoflife.v3.model.Model;

public interface GenModelGame {


    boolean movelMove(Model model, int toX, int toY);

    int eating(int x, int y);
}
