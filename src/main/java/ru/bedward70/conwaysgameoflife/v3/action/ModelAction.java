package ru.bedward70.conwaysgameoflife.v3.action;

import ru.bedward70.conwaysgameoflife.v3.game.GenModelGame;
import ru.bedward70.conwaysgameoflife.v3.model.ModelSet;

import java.util.function.BiFunction;

public class ModelAction {

    private final ActionGeneSet geneSet;
    private final byte actionGene;
    private final ActionTemplate template;

    public ModelAction(ActionGeneSet geneSet, byte actionGene, ActionTemplate template) {
        this.geneSet = geneSet;
        this.actionGene = actionGene;
        this.template = template;
    }

    public String getName() {
        return this.template.getName();
    }

    public int getEnergy() {
        return this.template.getEnergy();
    }

    public int getCycle() {
        return this.template.getCycle();
    }

    public BiFunction<ModelSet, GenModelGame, Boolean> getCallable() {
        return (m, g) -> this.template.getCallable().apply(this.geneSet, this.actionGene, m, g);
    }
}
