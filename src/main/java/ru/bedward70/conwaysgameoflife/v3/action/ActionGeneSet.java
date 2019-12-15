package ru.bedward70.conwaysgameoflife.v3.action;

public interface ActionGeneSet {

    byte getCellAndIncreaseCounter();

    void readCellAndIncreaseCounterByValue(int i);
}
