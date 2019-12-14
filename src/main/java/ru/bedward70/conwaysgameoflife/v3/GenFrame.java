package ru.bedward70.conwaysgameoflife.v3;

import ru.bedward70.conwaysgameoflife.v3.panel.GenPanel;

import javax.swing.*;
import java.awt.*;

public class GenFrame extends JFrame {

    public GenFrame(String title, JToolBar toolBar, GenPanel genPanel, JToolBar statusBar) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        add(toolBar, BorderLayout.NORTH);
        add(genPanel);
        add(statusBar, BorderLayout.SOUTH);

        pack();
        setVisible(true);
    }


}
