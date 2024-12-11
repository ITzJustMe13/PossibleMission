package com.possiblemission.entities;

import com.possiblemission.entities.abstractEntities.Items;
import com.possiblemission.game.Division;

/**
 * Represents a health kit item in the game, which can be used to heal the player.
 */
public class HealthKit extends Items {

    /**
     * Constructs a HealthKit instance with the specified value and associated division.
     *
     * @param value    The healing value of the health kit.
     * @param division The division where the health kit is located.
     */
    public HealthKit(int value, Division division) {
        super(value,division);
    }

    /**
     * Returns a string representation of the health kit.
     *
     * @return A string describing the health kit, including its value and division.
     */
    @Override
    public String toString() {
        return "HealthKit: " + value + " Division: " + division;
    }
}
