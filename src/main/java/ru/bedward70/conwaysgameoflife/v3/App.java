package ru.bedward70.conwaysgameoflife.v3;

import ru.bedward70.conwaysgameoflife.v3.game.GenGameImpl;
import ru.bedward70.conwaysgameoflife.v3.paint.PaintStrategySimple;
import ru.bedward70.conwaysgameoflife.v3.panel.GenPanel;
import ru.bedward70.conwaysgameoflife.v3.toolbar.CleaningButton;
import ru.bedward70.conwaysgameoflife.v3.toolbar.RunningButton;
import ru.bedward70.conwaysgameoflife.v3.toolbar.TurnButton;

import javax.swing.*;
import java.io.IOException;

public class App {

    public static void main(final String... args)
        throws ClassNotFoundException,
        UnsupportedLookAndFeelException,
        InstantiationException,
        IllegalAccessException,
        IOException {

        final AppAction appAction = new AppAction();

        final GenGameImpl game = new GenGameImpl(10, 10);
        appAction.setTurnGame(() -> game.turn());

        final GenPanel genPanel = new GenPanel(
            32,
            game,
            new PaintStrategySimple()
        );
        appAction.setRepaintPanel(() -> genPanel.repaint());

        final JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.add(new RunningButton(game));
        toolBar.add(new TurnButton(() -> appAction.executeTurn()));
        toolBar.add(new CleaningButton(() -> appAction.executeClean()));


        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(
            () -> {

                new GenFrame(
                    "GenFrame",
                    toolBar,
                    genPanel
                );
            }
        );

    }
}
