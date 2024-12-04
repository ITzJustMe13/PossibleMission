package com.possiblemission.entities.abstractEntities;

import com.github.cliftonlabs.json_simple.Jsonable;
import com.possiblemission.game.Division;

/**
 *
 */
public abstract class Human implements Jsonable {

    protected String name;

    protected Division currentDivision;

    protected int health;

    protected int power;

    public Human(String name, int power, int health) {
        this.name = name;
        this.power = power;
        this.health = health;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setCurrentDivision(Division currentDivision) {
        this.currentDivision = currentDivision;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
