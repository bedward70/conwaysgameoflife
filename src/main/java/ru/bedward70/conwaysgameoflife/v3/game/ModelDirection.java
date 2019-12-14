package ru.bedward70.conwaysgameoflife.v3.game;

public enum ModelDirection {

    FRONT(0, 0, -1),
    RIGHT(1, 1, 0),
    BACK(2, 0, 1),
    LEFT(3, -1, 0);

    private final int index;
    private final int x;
    private final int y;

    ModelDirection(int index,int x, int y) {
        this.index = index;
        this.x = x;
        this.y = y;
    }

    public int getIndex() {
        return index;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static ModelDirection valueOf(final int index) {
        int validIndex = index;
        while (validIndex < 0) {
            validIndex +=4;
        }
        validIndex %=4;
        for (ModelDirection modelDirection: values()) {
            if (modelDirection.index == validIndex) {
                return modelDirection;
            }
        }
        return ModelDirection.FRONT;
    }

    public ModelDirection plus(ModelDirection modelDirection) {
        return valueOf(index + modelDirection.index);
    }
}
