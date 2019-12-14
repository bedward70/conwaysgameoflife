package ru.bedward70.conwaysgameoflife.v3.toolbar;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.function.Consumer;

public class SpeedSlider extends JSlider {

    public SpeedSlider(int min, int max, int value, Consumer<Integer> consumer) {
        super(min, max, value);
        setMaximumSize(new Dimension(300, 50));
        addChangeListener(e -> consumer.accept(getValue()));
    }
}
