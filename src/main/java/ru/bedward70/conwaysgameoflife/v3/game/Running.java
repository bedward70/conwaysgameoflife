package ru.bedward70.conwaysgameoflife.v3.game;

public interface Running {
    boolean isRunning();

    void stop();

    void start();

    void setUpdateDelay(int updateDelay);
}
