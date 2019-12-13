package ru.bedward70.conwaysgameoflife.v3.toolbar;

import ru.bedward70.conwaysgameoflife.v3.game.Cleaning;

import javax.swing.*;
import java.awt.*;

public class CleaningButton extends JButton {

    public CleaningButton(final Runnable runnable) {
        super("Очистить");
        setMaximumSize(new Dimension(100, 50));
        // очистка поля
        addActionListener(e -> runnable.run());
    }
}
