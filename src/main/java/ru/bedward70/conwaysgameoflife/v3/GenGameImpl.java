package ru.bedward70.conwaysgameoflife.v3;

import ru.bedward70.conwaysgameoflife.v3.game.Field;
import ru.bedward70.conwaysgameoflife.v3.game.GenGame;
import ru.bedward70.conwaysgameoflife.v3.paint.PaintModel;

import java.util.Random;

public class GenGameImpl implements GenGame, Field {

    private final int width;
    private final int height;
    private final PaintModel[][] models;

    private Random random = new Random();

    private boolean running;

    public GenGameImpl(final int width, final int height) {
        this.width = width;
        this.height = height;
        this.models = new PaintModel[width][height];

        for (int x = 0; x < width; x ++) {
            for (int y = 0; y < height; y ++) {
                this.models[x][y] = new PaintModel() {
                    @Override
                    public byte getFood() {
                        return (byte) (0x00ff & random.nextInt());
                    }

                    @Override
                    public byte getMineral() {
                        return (byte) (0x00ff & random.nextInt());
                    }
                };
            }
        }
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public PaintModel[][] getModels() {
        return models;
    }

    @Override
    public void start() {
        System.out.println("start");
        running = true;
    }

    @Override
    public void stop() {
        System.out.println("stop");
        running = false;
    }

    @Override
    public boolean isRunning() {
        System.out.println("isRunning");
        return running;
    }

    @Override
    public void clean() {
        System.out.println("clean");
    }
}
