package com.possiblemission.entities;

import com.possiblemission.game.Division;

public class Target {

    private Division division;

    private String type;

    public Target(String type, Division division) {
        this.type = type;
        this.division = division;
    }

    public Division getDivision() {
        return division;
    }

}
