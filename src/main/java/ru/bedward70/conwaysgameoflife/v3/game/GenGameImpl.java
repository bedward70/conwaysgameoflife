package ru.bedward70.conwaysgameoflife.v3.game;

import ru.bedward70.conwaysgameoflife.v3.paint.PaintModel;
import ru.bedward70.conwaysgameoflife.v3.paint.PaintModelImpl;

import java.util.LinkedList;
import java.util.Random;

public class GenGameImpl implements GenGame, Field {

    private final int width;
    private final int height;
    private final byte[][] foods;
    private final byte[][] minerals;
    private final Model[][] models;

    private final LinkedList<Model> modelList = new LinkedList<>();

    private Random random = new Random();

    private boolean running;

    public GenGameImpl(final int width, final int height) {
        this.width = width;
        this.height = height;
        this.foods = new byte[width][height];
        this.minerals = new byte[width][height];
        this.models = new Model[width][height];

        for (int x = 0; x < width; x ++) {
            for (int y = 0; y < height; y ++) {
                this.foods[x][y] = (byte) (0x00ff & random.nextInt());
                this.minerals[x][y] = (byte) (0x00ff & random.nextInt());
            }
        }
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        Model model = new ModelImpl(ModelDirection.FRONT, x, y);
        models[x][y] = model;
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
    public PaintModel[][] getPaintModels() {
        final PaintModel[][] paintModels = new PaintModel[width][height];
        for (int x = 0; x < width; x ++) {
            for (int y = 0; y < height; y++) {
                paintModels[x][y] = new PaintModelImpl(foods[x][y], minerals[x][y], models[x][y]);
            }
        }
        return paintModels;
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
