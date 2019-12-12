package ru.bedward70.conwaysgameoflife;

/**
 * class incapsulating single cell
 *
 * @author Temak
 *
 */
public class Cell {

    /**
     * x coordinate of the cell
     */
    int x;
    /**
     * y coordinate of the cell
     */
    int y;

    /**
     * default constructor
     *
     * @param x
     * @see {@link #x}
     * @param y
     * @see {@link #y}
     */
    Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @return see {@link #x}
     */
    public int getX() {
        return x;
    }

    /**
     *
     * @return see {@link #y}
     */
    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "( " + x + ", " + y + " )";
    }
}
