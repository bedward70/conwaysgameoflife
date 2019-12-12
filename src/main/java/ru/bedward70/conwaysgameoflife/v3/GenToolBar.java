package ru.bedward70.conwaysgameoflife.v3;

import javax.swing.*;
import java.awt.*;

public class GenToolBar extends JToolBar {

    public GenToolBar(GenGame game) {
        setFloatable(false);

        add(buttonStartStop(game));
        add(buttonClean(game));
    }

    private JButton buttonStartStop(final GenGame game) {
        final JButton result = new JButton("Запустить");
        result.setMaximumSize(new Dimension(100, 50));
        // запуск/остановка симуляции; попутно меняется надпись на кнопке
        result.addActionListener(e -> {
            if (game.isRun()) {
                game.stop();
                result.setText("Запустить");
            } else {
                game.start();
                result.setText("Остановить");
            }
        });
        return result;
    }

    private JButton buttonClean(final GenGame game) {
        final JButton result = new JButton("Очистить");
        result.setMaximumSize(new Dimension(100, 50));
        // очистка поля
        result.addActionListener(e -> game.clean());
        return result;
    }
}
