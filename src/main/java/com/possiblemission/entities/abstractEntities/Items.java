package com.possiblemission.entities.abstractEntities;

import com.possiblemission.game.Division;

/**
 * Abstract class representing an item.
 * The item has a value and is associated with a division.
 */
public abstract class Items {

    /** The division the item is associated with */
    protected Division division;

    /** The value of the item */
    protected int value;

    /**
     * Constructs a new item with a specified value and division.
     *
     * @param value The value of the item
     * @param division The division the item is associated with
     */
    public Items(int value, Division division) {
        this.value = value;
        this.division = division;
    }

    /**
     * Returns the value of the item.
     *
     * @return The value of the item
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets the division the item is associated with.
     *
     * @param division The division to be set for the item
     */
    public void setDivision(Division division) {
        this.division = division;
    }

    /**
     * Returns the division the item is associated with.
     *
     * @return The division of the item
     */
    public Division getDivision() {
        return division;
    }


}
