package ru.bedward70.conwaysgameoflife.v3;

import ru.bedward70.conwaysgameoflife.v3.game.GenGame;

public class GenGameImpl implements GenGame {
    private boolean runing;

    @Override
    public void start() {
        System.out.println("start");
        runing = true;
    }

    @Override
    public void stop() {
        System.out.println("stop");
        runing = false;
    }

    @Override
    public boolean isRunning() {
        System.out.println("isRunning");
        return runing;
    }

    @Override
    public void clean() {
        System.out.println("clean");
    }
}