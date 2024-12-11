package com.possiblemission.entities;

import com.possiblemission.game.Division;

/**
 * Represents a target in the game that the player needs to capture.
 */
public class Target {
    /** The division where the target is located. */
    private Division division;
    /** The type of the target. */
    private String type;

    /**
     * Constructs a Target instance with the specified type and division.
     *
     * @param type     The type of the target.
     * @param division The division where the target is located.
     */
    public Target(String type, Division division) {
        this.type = type;
        this.division = division;
    }

    /**
     * Retrieves the division where the target is located.
     *
     * @return The division of the target.
     */
    public Division getDivision() {
        return division;
    }

    /**
     * Returns a string representation of the target.
     *
     * @return A string containing the target's division and type.
     */
    @Override
    public String toString() {
        return "TARGET:" + "\n" +"Divisao: " + division + "\n" +
                "Type: " + type;
    }
}
