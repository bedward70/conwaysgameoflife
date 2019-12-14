package ru.bedward70.conwaysgameoflife.v3;

import java.util.Optional;

public class AppAction {

    private Runnable turnGame;
    private Runnable repaint;
    private Runnable clean;

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

    public void executeTurn() {
        Optional.ofNullable(turnGame).ifPresent(e -> e.run());
        Optional.ofNullable(repaint).ifPresent(e -> e.run());
    }

    public void executeClean() {
        Optional.ofNullable(clean).ifPresent(e -> e.run());
    }
}
