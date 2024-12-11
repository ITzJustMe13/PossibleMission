package com.possiblemission.game;


import com.possiblemission.datastructures.abstractdatatypes.lists.unordered.UnorderedArrayList;
import com.possiblemission.entities.Enemy;
import com.possiblemission.entities.Target;
import com.possiblemission.entities.abstractEntities.Items;
import pt.ipp.estg.ed.UnorderedListADT;

/**
 * Represents a division in the game, which can contain enemies, items, and targets.
 */
public class Division {

    /** The name of the division. */
    private String name;

    /** The list of enemies present in the division. */
    private UnorderedListADT<Enemy> enemies;

    /** Indicates if the division is marked as an entry or exit point. */
    private boolean isExitOrEntry;

    /** Indicates if the division contains the target. */
    private boolean hasTarget;

    /** The target present in the division, if any. */
    private Target target;

    /** The item present in the division, if any. */
    private Items item;

    /**
     * Constructs a Division instance with the specified name.
     *
     * @param name The name of the division.
     */
    public Division(String name) {
        this.name = name;
        enemies = new UnorderedArrayList<>();
    }

    /**
     * Retrieves the name of the division.
     *
     * @return The name of the division.
     */
    public String getName() {
        return name;
    }

    /**
     * Adds an enemy to the division.
     *
     * @param enemy The enemy to add.
     */
    public void addEnemy(Enemy enemy){
        enemies.addToRear(enemy);
    }

    /**
     * Removes an enemy from the division.
     *
     * @param enemy The enemy to remove.
     */
    public void removeEnemy(Enemy enemy){
        enemies.remove(enemy);
    }

    /**
     * Marks the division as an entry or exit point.
     *
     * @param exitOrEntry true if the division is an entry or exit point, false otherwise.
     */
    public void setExitOrEntry(boolean exitOrEntry) {
        isExitOrEntry = exitOrEntry;
    }

    /**
     * Sets the target in the division.
     *
     * @param target The target to set.
     */
    public void setTarget(Target target) {
        this.hasTarget = true;
        this.target = target;
    }

    /**
     * Sets the item in the division.
     *
     * @param item The item to set.
     */
    public void setItem(Items item) {
        this.item = item;
    }

    /**
     * Retrieves the item present in the division.
     *
     * @return The item in the division, or null if none exists.
     */
    public Items getItem() {
        return item;
    }

    /**
     * Retrieves the list of enemies present in the division.
     *
     * @return The list of enemies.
     */
    public UnorderedListADT<Enemy> getEnemies(){
        return this.enemies;
    }

    /**
     * Returns a string representation of the division.
     *
     * @return The name of the division.
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Removes the item from the division.
     */
    public void removeItem() {
        this.item = null;
    }
}
