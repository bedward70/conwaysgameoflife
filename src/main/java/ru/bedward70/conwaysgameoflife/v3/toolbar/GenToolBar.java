package ru.bedward70.conwaysgameoflife.v3.toolbar;

import ru.bedward70.conwaysgameoflife.v3.game.GenGame;

import javax.swing.*;

public class GenToolBar extends JToolBar {

    public GenToolBar(GenGame game) {
        setFloatable(false);

        add(new RunningButton(game));
        add(new CleaningButton(game));
    }
}
