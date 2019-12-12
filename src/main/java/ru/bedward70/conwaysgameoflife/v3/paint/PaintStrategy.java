package ru.bedward70.conwaysgameoflife.v3.paint;

import java.awt.*;

public interface PaintStrategy {

    void paint(Graphics g, int x, int y, int w, int h, PaintModel model);
}
