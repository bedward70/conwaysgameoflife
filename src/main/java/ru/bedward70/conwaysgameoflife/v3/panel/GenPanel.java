package ru.bedward70.conwaysgameoflife.v3.panel;

import ru.bedward70.conwaysgameoflife.v3.game.Field;
import ru.bedward70.conwaysgameoflife.v3.paint.PaintModel;
import ru.bedward70.conwaysgameoflife.v3.paint.PaintStrategy;

import javax.swing.*;
import java.awt.*;

public class GenPanel extends JPanel {

    /**
     * Размер клетки на экране.
     */
    private int cellSize = 8;
    /**
     * Промежуток между клетками.
     */
    private int cellGap = 1;

    private final Field field;
    private PaintStrategy paintStrategy;

    public GenPanel(final Field field, final PaintStrategy paintStrategy) {
        this.field = field;
        this.paintStrategy = paintStrategy;
        setBackground(Color.BLACK);
    }

    /*
     * Возвращает размер панели с учетом размера поля и клеток.
     */
    @Override
    public Dimension getPreferredSize() {

        final Insets b = getInsets();
        return new Dimension((cellSize + cellGap) * field.getWidth() + cellGap + b.left + b.right,
                (cellSize + cellGap) * field.getHeight() + cellGap + b.top + b.bottom);
    }

    /*
     * Прорисовка содержимого панели.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Insets b = getInsets();
        for (int y = 0; y < field.getHeight(); y++) {
            for (int x = 0; x < field.getWidth(); x++) {
                final PaintModel model = field.getModel(x, y);
                paintStrategy.paint(
                    g,
                    b.left + cellGap + x * (cellSize + cellGap),
                    b.top + cellGap + y * (cellSize + cellGap),
                    cellSize,
                    cellSize,
                    model
                );
            }
        }
    }
}
