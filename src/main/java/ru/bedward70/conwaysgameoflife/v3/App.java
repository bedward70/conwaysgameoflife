/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 Eduard Balovnev (bedward70)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package ru.bedward70.conwaysgameoflife.v3;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.FileDialog;
import java.awt.Label;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.JFrame;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import ru.bedward70.conwaysgameoflife.v3.game.GenGame;
import ru.bedward70.conwaysgameoflife.v3.game.GenGameImpl;
import ru.bedward70.conwaysgameoflife.v3.game.GenModelGame;
import ru.bedward70.conwaysgameoflife.v3.model.ModelImpl;
import ru.bedward70.conwaysgameoflife.v3.paint.PaintStrategySimple;
import ru.bedward70.conwaysgameoflife.v3.panel.GenPanel;
import ru.bedward70.conwaysgameoflife.v3.toolbar.Button;
import ru.bedward70.conwaysgameoflife.v3.toolbar.RunningButton;
import ru.bedward70.conwaysgameoflife.v3.toolbar.SpeedSlider;
import ru.bedward70.conwaysgameoflife.v3.toolbar.TurnButton;

/**
 * The class contains a main entry point.
 *
 * @since 0.01
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class App {

    /**
     * Default delay, ms.
     */
    private static final int DEFAULT_DELAY_MS = 1000;

    /**
     * Min delay, ms.
     */
    private static final int MIN_DELAY_MS = 1;

    /**
     * Max delay, ms.
     */
    private static final int MAX_DELAY_MS = 5000;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * Private constructor.
     */
    private App() {
    }

    /**
     * The main entry point.
     * @param args Args
     * @throws Exception Exception
     */
    public static void main(final String... args) throws Exception {
        final AppAction action = new AppAction(App.DEFAULT_DELAY_MS);
        final GenGameImpl game = new GenGameImpl(30, 20);
        action.setTurnGame(() -> game.turn());
        action.setTurnGame(() -> game.clean());

        final GenPanel panel = new GenPanel(
            32,
            game,
            new PaintStrategySimple()
        );
        action.setRepaintPanel(() -> panel.repaint());
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(
            () -> {
                final GenFrame frame = new GenFrame(
                    "GenFrame",
                    createToolBar(action),
                    panel,
                    createStatusBar(action)
                );
                action.setLoad(() -> load(frame, game));
                action.setSave(() -> save(frame, game));
            }
        );
    }

    private static void load(final JFrame frame, final GenModelGame game) {
        FileDialog fd = new FileDialog(frame, "Choose a file", FileDialog.LOAD);
//        fd.setDirectory("C:\\");
        fd.setFile("*.json");
        fd.setVisible(true);
        String path = fd.getDirectory();
        String filename = fd.getFile();
        if (filename == null) {
            System.out.println("You cancelled the choice");
        } else {
            System.out.println("You chose " + filename);
            try {
                String str = new String ( Files.readAllBytes( Paths.get(path, filename) ) );
                ModelImpl[] myObjects = OBJECT_MAPPER.readValue(str, ModelImpl[].class);
                for (ModelImpl model : myObjects) {
                    if(game.movelMove(model, model.getX(), model.getY())) {
                        game.addModel(model);
                    };
                }
            } catch (Exception e) {

            }
        }
    }

    private static void save(final JFrame frame, final GenGame game) {
        FileDialog fd = new FileDialog(frame, "Choose a file", FileDialog.SAVE);
//        fd.setDirectory("C:\\");
        fd.setFile("*.json");
        fd.setVisible(true);
        String path = fd.getDirectory();
        String filename = fd.getFile();
        if (filename == null) {
            System.out.println("You cancelled the choice");
        } else {
            System.out.println("You chose " + filename);
            String str = "Hello";
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(new File(path, filename)));
                writer.write(OBJECT_MAPPER.writeValueAsString(game.getModels()));
                writer.flush();
                writer.close();
            } catch (Exception e) {

            }
        }
    }

    /**
     * Creates tool bar.
     * @param action Application action
     * @return JToolBar instance as the tool bar.
     */
    private static JToolBar createToolBar(final AppAction action) {
        final JToolBar result = new JToolBar();
        result.setFloatable(false);
        result.add(new RunningButton(action));
        final Label speedlabel = new Label(Integer.toString(App.DEFAULT_DELAY_MS));
        result.add(
            new SpeedSlider(
                App.MIN_DELAY_MS,
                App.MAX_DELAY_MS,
                App.DEFAULT_DELAY_MS,
                value -> {
                    action.setUpdateDelay(value);
                    speedlabel.setText(Integer.toString(value));
                }
            )
        );
        result.add(speedlabel);
        result.add(new TurnButton(() -> action.executeTurn()));
        result.add(new Button("Очистить", () -> action.executeClean()));
        result.add(new Button("Load", () -> action.executeLoad()));
        result.add(new Button("Save", () -> action.executeSave()));
        return result;
    }

    /**
     * Creates status bar.
     * @param action Application action
     * @return JToolBar instance as the status bar.
     */
    private static JToolBar createStatusBar(final AppAction action) {
        final JToolBar result = new JToolBar();
        final String template = "Cycles: ";
        final Label cyclelabel = new Label(String.format(template, 0));
        result.add(cyclelabel);
        action.setCycleConsumer(value -> cyclelabel.setText(String.format(template, value)));
        return result;
    }
}
