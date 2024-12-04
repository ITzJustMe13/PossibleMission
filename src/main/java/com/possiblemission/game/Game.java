package com.possiblemission.game;

import com.possiblemission.datastructures.abstractdatatypes.extended.ExtendedGraphADT;
import com.possiblemission.datastructures.abstractdatatypes.extended.ExtendedUndirectedMatrixGraph;

public class Game {

    private String codName;

    private int version;

    private ExtendedGraphADT<Division> map;

    public Game(String codName, int version) {
        this.codName = codName;
        this.version = version;
        map = new ExtendedUndirectedMatrixGraph<>();
    }

    public void addDivision(String divisionName) {
        Division newDivision = new Division(divisionName);
        map.addVertex(newDivision);
    }

    public void addLink(String division1, String division2) {
        Division div1 = getDivisionByName(division1);
        Division div2 = getDivisionByName(division2);
        map.addEdge(div1, div2);
    }

    public Division getDivisionByName(String divisionName) {

        Object[] divisions = map.getVertexes();

        for (int i = 0; i < divisions.length; i++) {
            Division division = (Division) divisions[i];
            if(divisionName.equals(division.getName())) {
                return division;
            }
        }

        return null;
    }

    public ExtendedGraphADT<Division> getMap() {
        return map;
    }
}
