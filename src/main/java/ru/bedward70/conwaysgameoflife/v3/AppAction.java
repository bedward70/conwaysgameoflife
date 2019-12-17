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

import ru.bedward70.conwaysgameoflife.v3.game.Running;

import java.util.Optional;
import java.util.function.Consumer;

public class AppAction implements Running, Runnable {

    private int cycle = 0;

    private Thread simThread = null;
    /**
     * Задержка в мс между шагами симуляции.
     */
    private int updateDelay = 1000;

    private Runnable turnGame;
    private Runnable repaint;
    private Runnable clean;
    private Runnable load;
    private Runnable save;
    private Consumer<Integer> cycleConsumer;

    public AppAction(int updateDelay) {
        this.updateDelay = updateDelay;
    }

    public AppAction setTurnGame(Runnable turnGame) {
        this.turnGame = turnGame;
        return this;
    }

    public AppAction setRepaintPanel(Runnable repaint) {
        this.repaint = repaint;
        return this;
    }

    public AppAction setClean(Runnable clean) {
        this.clean = clean;
        return this;
    }

    public AppAction setCycleConsumer(Consumer<Integer> cycleConsumer) {
        this.cycleConsumer = cycleConsumer;
        return this;
    }

    /**
     * Set load property.
     *
     * @param val new load value.
     * @return the {@link AppAction} for chaining.
     */
    public AppAction setLoad(final Runnable val) {
        this.load = val;
        return this;
    }

    /**
     * Set save property.
     *
     * @param val new save value.
     * @return the {@link AppAction} for chaining.
     */
    public AppAction setSave(final Runnable val) {
        this.save = val;
        return this;
    }

    public void executeTurn() {
        cycle++;
        System.out.println("Cycles: " + String.format("%6d", cycle) + " =================");
        Optional.ofNullable(cycleConsumer).ifPresent(e -> e.accept(cycle));
        Optional.ofNullable(turnGame).ifPresent(e -> e.run());
        Optional.ofNullable(repaint).ifPresent(e -> e.run());
    }

    public void executeClean() {
        Optional.ofNullable(clean).ifPresent(e -> e.run());
    }

    public void executeLoad() {
        Optional.ofNullable(load).ifPresent(e -> e.run());
    }

    public void executeSave() {
        Optional.ofNullable(save).ifPresent(e -> e.run());
    }

    @Override
    public boolean isRunning() {
        return simThread != null;
    }

    @Override
    public void stop() {
        simThread = null;
    }

    @Override
    public void start() {
        if (simThread == null) {
            simThread = new Thread(this);
            simThread.start();
        }
    }

    @Override
    public void run() {
        while (simThread != null) {
            try {
                Thread.sleep(updateDelay);
            } catch (InterruptedException e) {
            }
            executeTurn();
        }
    }

    public void setUpdateDelay(int updateDelay) {
        this.updateDelay = updateDelay;
    }
}
