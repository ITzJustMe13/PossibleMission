package com.possiblemission.entities;

import com.possiblemission.entities.abstractEntities.Human;
import com.possiblemission.game.Division;


/**
 * Class representing an enemy, inheriting from Human.
 * An enemy has a depth level and is associated with divisions, such as the base and last divisions.
 */
public class Enemy extends Human {

    /** The enemy's depth */
    private int depth;

    /** The enemy's base division */
    private final Division baseDiv;

    /** The last division the enemy was in */
    private Division lastDiv;

    /**
     * Constructs a new enemy with the name, power, and base division.
     *
     * @param name The name of the enemy
     * @param power The power of the enemy
     * @param division The base division the enemy is in
     */
    public Enemy(String name, int power, Division division) {
        super(name, power,100);
        setCurrentDivision(division);
        this.baseDiv = division;
        this.depth = 0;
    }

    /**
     * Returns the enemy's base division.
     *
     * @return The base division
     */
    public Division getBaseDiv(){
        return this.baseDiv;
    }

    /**
     * Returns the last division the enemy was in.
     *
     * @return The last division
     */
    public Division getLastDiv(){
        return this.lastDiv;
    }

    /**
     * Increases the enemy's depth by 1.
     */
    public void addDept(){
        this.depth++;
    }


    /**
     * Decreases the enemy's debt by 1.
     */
    public void subtractDept(){
        this.depth--;
    }

    /**
     * Returns the current debt of the enemy.
     *
     * @return The enemy's debt
     */
    public int getDept(){
        return this.depth;
    }

    /**
     * Sets the last division the enemy was in.
     *
     * @param div The division to be set as the enemy's last division
     */
    public void setLastDiv(Division div){
        this.lastDiv = div;
    }

}
