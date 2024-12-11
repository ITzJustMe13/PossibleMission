package com.possiblemission.entities;

import com.possiblemission.entities.abstractEntities.Items;
import com.possiblemission.game.Division;

/**
 * Class representing an armour item, inheriting from Items.
 * The armour has a value and is associated with a division.
 */
public class Armour extends Items {

    /**
     * Constructs a new armour with a specified value and division.
     *
     * @param value The value of the armour
     * @param division The division the armour is associated with
     */
    public Armour(int value, Division division) {
        super(value, division);
    }

    /**
     * Returns a string representation of the armour.
     *
     * @return A string containing the armour's value and division
     */
    @Override
    public String toString() {
        return "Colete: " + value + " Division: " + division;
    }
}
