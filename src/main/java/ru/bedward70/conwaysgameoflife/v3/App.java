package ru.bedward70.conwaysgameoflife.v3;

import ru.bedward70.conwaysgameoflife.v3.game.Field;
import ru.bedward70.conwaysgameoflife.v3.paint.PaintModel;
import ru.bedward70.conwaysgameoflife.v3.paint.PaintStrategy;
import ru.bedward70.conwaysgameoflife.v3.panel.GenPanel;
import ru.bedward70.conwaysgameoflife.v3.toolbar.GenToolBar;

import javax.swing.*;
import java.awt.*;

public class App {

    public static void main(final String... args)
        throws ClassNotFoundException,
        UnsupportedLookAndFeelException,
        InstantiationException,
        IllegalAccessException {

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(
            () -> new GenFrame(
                "GenFrame",
                new GenToolBar(new GenGameImpl()),
                new GenPanel(
                    getField(),
                    getPaintStrategy()
                )
            )
        );
    }

    private static Field getField() {
        return new Field() {

            @Override
            public int getWidth() {
                return 100;
            }

            @Override
            public int getHeight() {
                return 100;
            }

            @Override
            public PaintModel getModel(int x, int y) {
                return null;
            }
        };
    }

    private static PaintStrategy getPaintStrategy() {
        return new PaintStrategy() {

            @Override
            public void paint(Graphics g, int x, int y, int w, int h, PaintModel model) {
                g.setColor(new Color(0x505050));
                g.fillRect(x, y, w, h);
            }
        };
    }
}
