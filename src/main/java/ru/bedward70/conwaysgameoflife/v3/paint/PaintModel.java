package ru.bedward70.conwaysgameoflife.v3.paint;

import ru.bedward70.conwaysgameoflife.v3.game.Model;

public interface PaintModel {

    byte getFood();
    byte getMineral();
    Model getModel();
}
