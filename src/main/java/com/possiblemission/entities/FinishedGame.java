package com.possiblemission.entities;

import com.possiblemission.datastructures.abstractdatatypes.lists.unordered.UnorderedArrayList;

public class FinishedGame {

    private String codName;
    private int version;
    private String name;
    private int health;

    private UnorderedArrayList<String> moves;

    public FinishedGame(String codName, int version, String name, int health, UnorderedArrayList<String> moves) {
        this.codName = codName;
        this.version = version;
        this.name = name;
        this.health = health;
        this.moves = moves;
    }

    public String getCodName() {
        return codName;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return "CodName: "+ codName + "\nVersion: " + version + "\nPlayer: " + name + "\nHealthLeft: " + health + "\nMoves: " + moves + "\n--------------------------------------------------";
    }
}
