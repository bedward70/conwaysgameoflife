package ru.bedward70.conwaysgameoflife.v3.model;

import ru.bedward70.conwaysgameoflife.v3.action.ActionGeneSet;

import java.util.Arrays;
import java.util.Random;

public class ActionGeneSetCycle implements ActionGeneSet {
    private static Random RANDOM = new Random();

    private int pointer;
    private final byte[] geneSet;


    public ActionGeneSetCycle() {
        this.pointer = 0;
        this.geneSet = new byte[0xff];
        RANDOM.nextBytes(geneSet);
    }

    public ActionGeneSetCycle(byte[] geneSet) {
        this.pointer = 0;
        this.geneSet = geneSet;
    }

    @Override
    public byte getCellAndIncreaseCounter() {
        byte result = geneSet[pointer];
        pointer++;
        pointer %= geneSet.length;
        return result;
    }

    @Override
    public void readCellAndIncreaseCounterByValue(int i) {
        byte b = this.geneSet[pointer + i];
        pointer += (0x00ff & b);
        pointer %= geneSet.length;
    }

    @Override
    public ActionGeneSet copy(int count) {
        byte[] dst = Arrays.copyOf(geneSet, geneSet.length);
        for (int i = 0; i <  count; i++) {
            dst[RANDOM.nextInt(geneSet.length)] = (byte) (0xff & RANDOM.nextInt());
        }
        return new ActionGeneSetCycle(dst);
    }
}
