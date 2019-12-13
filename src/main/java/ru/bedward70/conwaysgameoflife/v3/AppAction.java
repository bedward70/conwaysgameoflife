package ru.bedward70.conwaysgameoflife.v3;

import ru.bedward70.conwaysgameoflife.v3.game.GenGameImpl;

import java.util.Optional;

public class AppAction {

    private Runnable step;
    private Runnable repaint;
    private Runnable clean;

    public AppAction setStep(Runnable step) {
        this.step = step;
        return this;
    }

    public AppAction setRepaint(Runnable repaint) {
        this.repaint = repaint;
        return this;
    }

    public AppAction setClean(Runnable clean) {
        this.clean = clean;
        return this;
    }

    public void executeStep() {
        Optional.ofNullable(step).ifPresent(e -> e.run());
    }

    public void executeRepaint() {
        Optional.ofNullable(repaint).ifPresent(e -> e.run());
    }

    public void executeClean() {
        Optional.ofNullable(clean).ifPresent(e -> e.run());
    }
}
