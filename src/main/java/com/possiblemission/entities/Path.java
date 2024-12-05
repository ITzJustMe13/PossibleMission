package com.possiblemission.entities;

import com.possiblemission.game.Division;

public class Path implements Comparable<Path> {

    private Division division;

    private int cost;

    @Override
    public int compareTo(Path o) {
        return Integer.compare(this.cost, o.getCost());
    }

    public int getCost(){
        return cost;
    }
}
