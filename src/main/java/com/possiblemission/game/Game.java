package com.possiblemission.game;

import com.possiblemission.datastructures.abstractdatatypes.extended.ExtendedGraphADT;
import com.possiblemission.datastructures.abstractdatatypes.extended.ExtendedUndirectedMatrixGraph;
import com.possiblemission.datastructures.abstractdatatypes.lists.unordered.UnorderedArrayList;
import com.possiblemission.entities.Enemy;
import com.possiblemission.entities.Target;
import com.possiblemission.entities.abstractEntities.Items;
import com.possiblemission.entities.Player;

import pt.ipp.estg.ed.UnorderedListADT;

public class Game {

    private String codName;

    private int version;

    private ExtendedGraphADT<Division> map;

    private UnorderedListADT<Items> items;

    private Target alvo;

    private UnorderedListADT<Enemy> enemies;

    private Player player;

    public Game(String codName, int version) {
        this.codName = codName;
        this.version = version;
        map = new ExtendedUndirectedMatrixGraph<>();
        items = new UnorderedArrayList<>();
        enemies = new UnorderedArrayList<>();
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

    public void addItem(Items item){
        items.addToRear(item);
    }

    public UnorderedListADT<Items> getItems(){
        return items;
    }

    public void addTarget(Target target){
        this.alvo = target;
    }

    public Target getTarget(){
        return alvo;
    }

    public UnorderedListADT<Enemy> getEnemies(){
        return enemies;
    }

    public void addEnemy(Enemy enemy){
        enemies.addToRear(enemy);
    }

    public void addPlayer(Player player){
        this.player = player;
    }

    public Player getPlayer(){
        return player;
    }

    public Division[] getAdjecentDivisions(Division division){
        Division[] divisions = (Division[]) map.getAdjentVertexes(division);
        return divisions;
    }
}
