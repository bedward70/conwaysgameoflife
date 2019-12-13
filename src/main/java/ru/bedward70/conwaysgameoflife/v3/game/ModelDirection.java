package ru.bedward70.conwaysgameoflife.v3.game;

public enum ModelDirection {

    FRONT(0),
    RIGHT(1),
    BACK(2),
    LEFT(3);

    private final int index;

    ModelDirection(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
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
