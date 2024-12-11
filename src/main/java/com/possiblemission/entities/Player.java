package com.possiblemission.entities;

import com.possiblemission.datastructures.abstractdatatypes.stacks.LinkedStack;
import com.possiblemission.entities.abstractEntities.Human;
import com.possiblemission.game.Division;
import pt.ipp.estg.ed.StackADT;

import java.io.IOException;
import java.io.Writer;

/**
 * Represents the player character in the game, inheriting from the Human class.
 * The player can carry and use health kits and armor, and has a backpack to store items.
 */
public class Player extends Human {

    /** The player's backpack to store health kits. */
    private StackADT<HealthKit> backpack;

    /** The maximum size of the player's backpack. */
    private int backpackSize;

    /** The maximum health the player can have. */
    private int maxHealth;

    /**
     * Constructs a Player instance with the specified name, power, and health.
     *
     * @param name   The name of the player.
     * @param power  The player's power level.
     * @param health The player's initial health.
     */
    public Player(String name, int power, int health) {
        super(name, power,health);
        backpack = new LinkedStack<>();
        this.maxHealth = health;
    }

    /**
     * Sets the current division the player is in.
     *
     * @param currentDivision The division to set as the current one.
     */
    public void setCurrentDivision(Division currentDivision) {
        this.currentDivision = currentDivision;
    }

    /**
     * Adds a health kit to the player's backpack.
     *
     * @param healthKit The health kit to add.
     */
    public void addHealthKit(HealthKit healthKit) {
        if(backpack.size() < backpackSize) {
            backpack.push(healthKit);
        }else{
            System.out.println("Backpack is full");
        }
    }

    /**
     * Uses a health kit from the player's backpack to heal.
     * If the player's health is already full or the backpack is empty, no action is taken.
     */
    public void useHealthKit(){
        if (backpack.isEmpty()) return;
        HealthKit healthKit = backpack.pop();
        int heal = healthKit.getValue();
        int healthDiference = this.maxHealth - this.health;
        if(heal >= healthDiference){
            this.health += healthDiference;
        }else{
            this.health += heal;
        }
    }

    /**
     * Uses an armor item to increase the player's health.
     *
     * @param armour The armor to use.
     */
    public void useArmour(Armour armour) {
        this.health += armour.getValue();
    }

    /**
     * Retrieves the top health kit from the player's backpack without removing it.
     *
     * @return The top health kit, or null if the backpack is empty.
     */
    public HealthKit getTopHealthKit() {
        return (backpack.isEmpty()) ? null : backpack.peek();
    }

    /**
     * Sets the maximum size of the player's backpack.
     *
     * @param backpackSize The maximum backpack size.
     */
    public void setBackpackSize(int backpackSize) {
        this.backpackSize = backpackSize;
    }

    /**
     * Checks if the player has any health kits in their backpack.
     *
     * @return true if the player has health kits, false otherwise.
     */
    public boolean hasHealthKits(){
        return !backpack.isEmpty();
    }

    /**
     * Retrieves the maximum health of the player.
     *
     * @return The maximum health value.
     */
    public int getMaxHealth() {
        return maxHealth;
    }

}
