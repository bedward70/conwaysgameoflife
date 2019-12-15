package ru.bedward70.conwaysgameoflife.v3.model;

public interface ModelSet extends Model {

    void setDirection(ModelDirection direction);

    void setX(int x);

    void setY(int y);

    void addEnergy(int energy);

    int getEnergyForReproduction();
}
