package ru.bedward70.conwaysgameoflife.v3;

import ru.bedward70.conwaysgameoflife.v3.game.Field;
import ru.bedward70.conwaysgameoflife.v3.paint.PaintModel;
import ru.bedward70.conwaysgameoflife.v3.paint.PaintStrategy;
import ru.bedward70.conwaysgameoflife.v3.paint.PaintStrategySimple;
import ru.bedward70.conwaysgameoflife.v3.panel.GenPanel;
import ru.bedward70.conwaysgameoflife.v3.toolbar.GenToolBar;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.util.Objects.isNull;

public class App {

    public static void main(final String... args)
        throws ClassNotFoundException,
        UnsupportedLookAndFeelException,
        InstantiationException,
        IllegalAccessException,
        IOException {

        final GenGameImpl game = new GenGameImpl(10, 10);
        final PaintStrategySimple paintStrategy = new PaintStrategySimple();

        int b = 0x00ff;
        System.out.println("" + ((int) b));


        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(
            () -> {
                new GenFrame(
                    "GenFrame",
                    new GenToolBar(game),
                    new GenPanel(
                        16,
                        game,
                        paintStrategy
                    )
                );
            }
        );
    }
}
