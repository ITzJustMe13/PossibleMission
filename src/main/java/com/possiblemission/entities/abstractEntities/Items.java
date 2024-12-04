package com.possiblemission.entities.abstractEntities;

import com.possiblemission.game.Division;

public abstract class Items {

    protected Division division;

    protected int value;

    public Items(int value, Division division) {
        this.value = value;
        this.division = division;
    }

    public int getValue() {
        return value;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public Division getDivision() {
        return division;
    }


}
