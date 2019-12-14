package ru.bedward70.conwaysgameoflife.v3;

import ru.bedward70.conwaysgameoflife.v3.game.Running;

import javax.swing.*;
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
