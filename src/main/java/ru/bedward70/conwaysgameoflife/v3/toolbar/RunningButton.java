package ru.bedward70.conwaysgameoflife.v3.toolbar;

import ru.bedward70.conwaysgameoflife.v3.game.Running;

import javax.swing.*;
import java.awt.*;

public class RunningButton extends JButton {

    public RunningButton(final Running game) {
        super("Запустить");
        setMaximumSize(new Dimension(100, 50));
        // запуск/остановка симуляции; попутно меняется надпись на кнопке
        addActionListener(e -> {
            if (game.isRunning()) {
                game.stop();
                setText("Запустить");
            } else {
                game.start();
                setText("Остановить");
            }
        });
    }
}
