package com.possiblemission.entities.abstractEntities;

import com.possiblemission.game.Division;

/**
 * Abstract class representing a human character.
 * The human has a name, power, health, and a current division, and can be in battle.
 */
public abstract class Human {

    /** The name of the human */
    protected String name;

    /** The current division the human belongs to */
    protected Division currentDivision;

    /** The health of the human */
    protected int health;

    /** The power of the human */
    protected int power;

    /** Indicates whether the human is currently in battle */
    protected boolean isInBattle;

    /**
     * Constructs a new human with the specified name, power, and health.
     *
     * @param name The name of the human
     * @param power The power of the human
     * @param health The health of the human
     */
    public Human(String name, int power, int health) {
        this.name = name;
        this.power = power;
        this.health = health;
    }

    /**
     * Returns the name of the human.
     *
     * @return The name of the human
     */
    public String getName() {
        return name;
    }


    /**
     * Returns the power of the human.
     *
     * @return The power of the human
     */
    public int getPower() {
        return power;
    }


    /**
     * Sets the current division the human belongs to.
     *
     * @param currentDivision The division to be set as the human's current division
     */
    public void setCurrentDivision(Division currentDivision) {
        this.currentDivision = currentDivision;
    }

    /**
     * Returns the current division the human belongs to.
     *
     * @return The current division of the human
     */
    public Division getCurrentDivision(){
        return currentDivision;
    }

    /**
     * Sets the health of the human.
     *
     * @param health The health to be set for the human
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Returns the health of the human.
     *
     * @return The health of the human
     */
    public int getHealth(){return  this.health;}

    /**
     * Sets whether the human is currently in battle.
     *
     * @param isInBattle A boolean indicating if the human is in battle
     */
    public void setIsInBattle(boolean isInBattle) {
        this.isInBattle = isInBattle;
    }

    /**
     * Returns whether the human is currently in battle.
     *
     * @return A boolean indicating if the human is in battle
     */
    public boolean isInBattle(){
        return this.isInBattle;
    }

    /**
     * Returns a string representation of the human.
     *
     * @return A string containing the name, power, health, and current division of the human
     */
    @Override
    public String toString() {
        return name + "Power: " + power + " Health: "+ health + " Division: " + currentDivision.toString();
    }
}
