package ru.bedward70.conwaysgameoflife.v3.model;

import ru.bedward70.conwaysgameoflife.v3.game.GenModelGame;

public interface Model {

    ModelDirection getDirection();

    int getX();

    int getY();

    boolean isAlife();

    void turn(GenModelGame game);
}
