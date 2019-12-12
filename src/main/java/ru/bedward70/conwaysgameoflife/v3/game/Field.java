package ru.bedward70.conwaysgameoflife.v3.game;

import ru.bedward70.conwaysgameoflife.v3.paint.PaintModel;

public interface Field {

    int getWidth();

    int getHeight();

    PaintModel getModel(int x, int y);
}
