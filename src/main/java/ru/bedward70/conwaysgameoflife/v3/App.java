package ru.bedward70.conwaysgameoflife.v3;

import ru.bedward70.conwaysgameoflife.v2.LifeSim;

import javax.swing.*;

public class App {

    public static void main(final String... args)
        throws ClassNotFoundException,
        UnsupportedLookAndFeelException,
        InstantiationException,
        IllegalAccessException {

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(() -> new GenFrame("GenFrame", new GenToolBar(new GenGameImpl())));
    }
}
