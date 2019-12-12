package ru.bedward70.conwaysgameoflife;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Stack;



/**
 *
 * @author Temak
 *
 */
public class Game {

    /**
     * max width and height of the life area
     */
    public static final int MAX_WIDTH = 110;
    public static final int MAX_HEIGHT = 110;
    /**
     * array containing count of alive neighbours for every cell
     */
    private byte[] neighboursCount;
    /**
     * array containing count of alive neighbours for every cell in the next
     * generation
     */
    private byte[] newNeighboursCount;
    /**
     * list of alive cells
     */
    private ArrayList<Cell> alive;
    /**
     * array of all cells
     */
    private final Cell[] cells;
    /**
     * current width and height of the life area
     */
    private int width;
    private int height;
    private MessageDigest mda;
    /**
     * list of hash codes of the generations
     */
    private ArrayList<byte[]> hashes;
    /**
     * period of the life cycle
     */
    private int period = 0;

    /**
     * Creates a game area with the specified <code>width</code> and
     * <code>height</code>.
     *
     * @param width
     *            width of area, must be greater than <code>0</code>
     * @param height
     *            height of area, must be greater than <code>0</code>
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     */
    public Game(int width, int height) throws NoSuchAlgorithmException, NoSuchProviderException {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException();
        }
        this.width = width;
        this.height = height;
        mda = MessageDigest.getInstance("SHA-512");
        alive = new ArrayList<Cell>();
        neighboursCount = new byte[width * height];
        newNeighboursCount = new byte[width * height];
        cells = new Cell[MAX_HEIGHT * MAX_WIDTH];
        for (int i = 0; i < MAX_WIDTH; i++) {
            for (int j = 0; j < MAX_HEIGHT; j++) {
                cells[i * MAX_HEIGHT + j] = new Cell(i, j);
            }
        }
        hashes = new ArrayList<byte[]>();
    }

    /**
     * Get the width of life area.
     *
     * @return width of area
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get the height of life area.
     *
     * @return height of area
     */
    public int getHeight() {
        return height;
    }

    /**
     * Simulate of the transition to the next generation.
     *
     * <pre>
     * Rules:
     * <b>Birth</b>. Any dead cell with 3 live neighbors become to alive.
     * <b>Death</b>. Any live cell with less than 2 and more than 3 live neighbors dies.
     * <b>Survival</b>. Otherwise, the boolean of the cell is not changed.
     * </pre>
     */
    public void nextGeneration() {
        ArrayList<Cell> newAlive = new ArrayList<Cell>();
        for (int i = 0; i < width * height; i++) {
            newNeighboursCount[i] = 0;
        }
        Iterator<Cell> it = alive.iterator();
        while (it.hasNext()) {
            Cell c = it.next();
            if (neighboursCount[c.x * height + c.y] >= 2 && neighboursCount[c.x * height + c.y] <= 3)
                setAliveInNextGen(newAlive, c.x, c.y);
            neighboursCount[c.x * height + c.y] = 0;
        }
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (neighboursCount[i * height + j] == 3) {
                    setAliveInNextGen(newAlive, i, j);
                }
            }
        }
        if (period == 0) {
            byte[] digest = mda.digest(neighboursCount);
            Iterator<byte[]> itt = hashes.iterator();
            int k = 0;
            while (itt.hasNext()) {
                if (equals(itt.next(), (digest))) {
                    break;
                }
                k++;
            }
            if (k < hashes.size()) {
                period = hashes.size() - k;
                System.out.println(k + " hashes.size() " + hashes.size() + " Period: " + period);
            } else {
                hashes.add(digest);
            }
        }
        if (period == 1) {
            // System.out.println(k++ + " Period: " + period);
            // period = -1;
        }
        alive = newAlive;
        byte[] temp = neighboursCount;
        neighboursCount = newNeighboursCount;
        newNeighboursCount = temp;
    }

    /**
     * comparing to byte arrays
     *
     * @param a
     * @param b
     * @return true if a equals b, else false
     */
    private boolean equals(byte[] a, byte[] b) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i])
                return false;
        }
        return true;
    }

    /**
     *
     * @return {@link #period}
     */
    public int getPeriod() {
        return period;
    }

    /**
     * sets the cell to be alive or dead
     *
     * @param x
     *            coordinate of the cell
     * @param y
     *            coordinate of the cell
     * @param value
     *            to set ( true for alive, false for dead)
     */
    public void setState(int x, int y, boolean value) {
        if (value) {
            alive.add(cells[x * MAX_HEIGHT + y]);
            for (Location l : Location.values()) {
                neighboursCount[round(x + l.getX(), width) * height + round(y + l.getY(), height)]++;
            }
            neighboursCount[x * height + y]--;
        } else {
            alive.remove(cells[x * MAX_HEIGHT + y]);
            for (Location l : Location.values()) {
                neighboursCount[round(x + l.getX(), width) * height + round(y + l.getY(), height)]--;
            }
            neighboursCount[x * height + y]++;
        }
    }

    /**
     * makes alive cell dead, dead cell alive
     *
     * @param x
     * @param y
     */
    public void invertState(int x, int y) {
        Iterator<Cell> it = alive.iterator();
        Cell c = cells[x * MAX_HEIGHT + y];
        boolean isToAdd = true;
        while (it.hasNext()) {
            if (it.next().equals(c)) {
                it.remove();
                alive.remove(cells[x * MAX_HEIGHT + y]);
                for (Location l : Location.values()) {
                    neighboursCount[round(x + l.getX(), width) * height + round(y + l.getY(), height)]--;
                }
                neighboursCount[x * height + y]++;
                isToAdd = false;
            }
        }
        if (isToAdd)
            setState(x, y, true);
    }

    /**
     * set cell alive in the next generation
     *
     * @param newAlive
     *            list of alive cells in next generation
     * @param x
     * @param y
     */
    public void setAliveInNextGen(ArrayList<Cell> newAlive, int x, int y) {
        newAlive.add(cells[x * MAX_HEIGHT + y]);
        for (Location l : Location.values()) {
            newNeighboursCount[round(x + l.getX(), width) * height + round(y + l.getY(), height)]++;
        }
        newNeighboursCount[x * height + y]--;
    }

    /**
     * sets the size of the life area
     *
     * @param width
     * @param height
     */
    public void setPopulationSize(int width, int height) {
        this.width = width;
        this.height = height;
        alive.clear();
        neighboursCount = new byte[width * height];
        newNeighboursCount = new byte[width * height];
        startNewPeriod();
    }

    /**
     * Calculates the real index of element over the virtual index. Allows to
     * represent the array as a closed surface.
     *
     * @param index
     *            virtual index of element
     * @param length
     *            length of array
     * @return real index of element
     */
    private int round(int index, int length) {
        return (length + (index % length)) % length;
    }

    /**
     * start detecting period from the current generation
     */
    public void startNewPeriod() {
        hashes.clear();
        period = 0;
    }

    /**
     * @see #alive
     * @return array of alive cells
     */
    public ArrayList<Cell> getAlive() {
        return alive;
    }

    /**
     * @see #cells
     * @return Cell[] cells
     */
    public Cell[] getCells() {
        return cells;
    }

    public static void main(String[] args) throws NoSuchProviderException, NoSuchAlgorithmException {
        System.out.println("Strting...");
        long t1 = System.currentTimeMillis();
        Game game = new Game(101, 100);
        game.setState(50, 50, true);
        game.setState(51, 50, true);
        game.setState(52, 50, true);
        game.setState(52, 51, true);
        game.setState(51, 52, true);
        int k = 1;
        while (game.period == 0) {
            System.out.println(k);
            k++;
            game.nextGeneration();
        }
        long t2 = System.currentTimeMillis();
        System.out.println(game.getPeriod());
        System.out.println("Time: " + (double) (t2 - t1) / 1000);
        Iterator<Cell> it = game.alive.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
}
