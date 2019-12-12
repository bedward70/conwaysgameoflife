package ru.bedward70.conwaysgameoflife;

/**
 * enumerates relative locations for any current cell
 *
 * @author Temak
 *
 */
public enum Location {
    NORTH_WEST(-1, -1), NORTH(0, -1), NORTH_EAST(1, -1), WEST(-1, 0), SELF(0, 0), EAST(1, 0), SOUTH_WEST(-1, 1), SOUTH(
        0, 1), SOUTH_EAST(1, 1);

    /**
     * x and y shift from the (0,0)
     */
    private int x;
    private int y;

    /**
     *
     * @param x
     *            see {@link #x}
     * @param y
     *            see {@link #y}
     */
    private Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @return {@link #x}
     */
    public int getX() {
        return x;
    }

    /**
     *
     * @return {@link #y}
     */
    public int getY() {
        return y;
    }
}
