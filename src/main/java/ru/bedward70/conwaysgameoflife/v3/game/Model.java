package ru.bedward70.conwaysgameoflife.v3.game;

public interface Model {

    ModelDirection getDirection();

    int getX();

    int getY();

    void turn(GenModelGame game);
}
