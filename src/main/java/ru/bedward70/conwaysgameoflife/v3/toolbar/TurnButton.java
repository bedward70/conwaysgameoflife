package ru.bedward70.conwaysgameoflife.v3.toolbar;

import javax.swing.*;
import java.awt.*;

public class TurnButton extends JButton {

    public TurnButton(final Runnable runnable) {
        super("Turn");
        setMaximumSize(new Dimension(100, 50));
        // очистка поля
        addActionListener(e -> runnable.run());
    }
}
