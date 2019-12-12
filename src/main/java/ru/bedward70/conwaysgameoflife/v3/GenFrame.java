package ru.bedward70.conwaysgameoflife.v3;

import javax.swing.*;
import java.awt.*;

public class GenFrame extends JFrame {


    public GenFrame(String title, JToolBar toolBar) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        add(toolBar, BorderLayout.NORTH);

        pack();
        setVisible(true);
    }
}
