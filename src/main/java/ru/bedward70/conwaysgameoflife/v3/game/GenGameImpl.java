package ru.bedward70.conwaysgameoflife.v3.game;

import ru.bedward70.conwaysgameoflife.v3.model.Model;
import ru.bedward70.conwaysgameoflife.v3.model.ModelDirection;
import ru.bedward70.conwaysgameoflife.v3.model.ModelImpl;
import ru.bedward70.conwaysgameoflife.v3.paint.PaintModel;
import ru.bedward70.conwaysgameoflife.v3.paint.PaintModelImpl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class GenGameImpl implements GenGame, GenModelGame, Field {

    private final int width;
    private final int height;
    private final byte[][] foodArray;
    private final byte[][] mineralArray;
    private final LinkedList<Model> models = new LinkedList<>();

    private Random random = new Random();

    public GenGameImpl(final int width, final int height) {
        this.width = width;
        this.height = height;
        this.foodArray = new byte[width][height];
        this.mineralArray = new byte[width][height];

        for (int x = 0; x < width; x ++) {
            for (int y = 0; y < height; y ++) {
                this.foodArray[x][y] = (byte) (0x00ff & random.nextInt());
                this.mineralArray[x][y] = (byte) (0x00ff & random.nextInt());
            }
        }
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        Model model = new ModelImpl(ModelDirection.FRONT, x, y, 128);
        models.add(model);
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
    public synchronized PaintModel[][] getPaintModels() {
        final PaintModel[][] result = new PaintModel[width][height];
        final Model[][] modelArray = new Model[width][height];
        models.forEach(model -> modelArray[model.getX()][model.getY()] = model);

        for (int x = 0; x < width; x ++) {
            for (int y = 0; y < height; y++) {
                result[x][y] = new PaintModelImpl(foodArray[x][y], mineralArray[x][y], modelArray[x][y]);
            }
        }
        return result;
    }

    @Override
    public void clean() {
        System.out.println("clean");
    }

    @Override
    public synchronized void turn() {
        new ArrayList<>(this.models)
            .forEach(model -> model.turn(this));
        this.models.removeIf(model -> !model.isAlife());
    }

    @Override
    public boolean movelMove(final Model model, final int toX, final int toY) {
        return !(
                (toX < 0)
            || (toX >= width)
            || (toY < 0)
            || (toY >= height)
            || this.models.stream()
                .filter(m -> m != model)
                .anyMatch(m -> m.getX() == toX && m.getY() == toY)
        );
    }
}
