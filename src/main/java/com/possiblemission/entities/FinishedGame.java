package com.possiblemission.entities;

import com.possiblemission.datastructures.abstractdatatypes.lists.unordered.UnorderedArrayList;

/**
 * Represents a finished game, including details about the game, player, and moves made.
 */
public class FinishedGame {

    /** The code name of the game. */
    private String codName;

    /** The version of the game. */
    private int version;

    /** The name of the player. */
    private String name;

    /** The health remaining for the player at the end of the game. */
    private int health;

    /** The list of moves made during the game. */
    private UnorderedArrayList<String> moves;

    /**
     * Constructs a FinishedGame instance with the specified details.
     *
     * @param codName The code name of the game.
     * @param version The version of the game.
     * @param name    The name of the player.
     * @param health  The health remaining for the player.
     * @param moves   The list of moves made during the game.
     */
    public FinishedGame(String codName, int version, String name, int health, UnorderedArrayList<String> moves) {
        this.codName = codName;
        this.version = version;
        this.name = name;
        this.health = health;
        this.moves = moves;
    }

    /**
     * Retrieves the code name of the game.
     *
     * @return The code name of the game.
     */
    public String getCodName() {
        return codName;
    }

    /**
     * Retrieves the version of the game.
     *
     * @return The version of the game.
     */
    public int getVersion() {
        return version;
    }

    /**
     * Sets the version of the game.
     *
     * @param version The version to set.
     */
    public void setVersion(int version) {
        this.version = version;
    }

    /**
     * Retrieves the name of the player.
     *
     * @return The name of the player.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns a string representation of the finished game, including details of the game and moves.
     *
     * @return A formatted string describing the finished game.
     */
    @Override
    public String toString() {
        return "CodName: "+ codName + "\nVersion: " + version + "\nPlayer: " + name + "\nHealthLeft: " + health + "\nMoves: " + moves + "\n--------------------------------------------------";
    }
}
