package com.possiblemission.game;

import com.possiblemission.datastructures.abstractdatatypes.binarytrees.heap.LinkedHeap;
import com.possiblemission.datastructures.abstractdatatypes.extended.ExtendedGraphADT;
import com.possiblemission.datastructures.abstractdatatypes.extended.ExtendedUndirectedMatrixGraph;
import com.possiblemission.datastructures.abstractdatatypes.lists.ArrayList;
import com.possiblemission.datastructures.abstractdatatypes.lists.ordered.OrderedArrayList;
import com.possiblemission.datastructures.abstractdatatypes.lists.unordered.UnorderedArrayList;
import com.possiblemission.entities.Enemy;
import com.possiblemission.entities.HealthKit;
import com.possiblemission.entities.Target;
import com.possiblemission.entities.abstractEntities.Human;
import com.possiblemission.entities.abstractEntities.Items;
import com.possiblemission.entities.Player;

import pt.ipp.estg.ed.HeapADT;
import pt.ipp.estg.ed.OrderedListADT;
import pt.ipp.estg.ed.UnorderedListADT;

import java.util.Iterator;

public class Game {

    private String codName;

    private int version;

    private ExtendedGraphADT<Division> map;

    private UnorderedListADT<Items> items;

    private Target alvo;

    private UnorderedListADT<Enemy> enemies;

    private Player player;

    private UnorderedListADT<Division> EntriesAndExits;

    public Game(String codName, int version) {
        this.codName = codName;
        this.version = version;
        map = new ExtendedUndirectedMatrixGraph<>();
        items = new UnorderedArrayList<>();
        enemies = new UnorderedArrayList<>();
        EntriesAndExits = new UnorderedArrayList<>();
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

    public void addEntryOrExit(Division division){
        EntriesAndExits.addToRear(division);
    }

    public UnorderedListADT<Division> getEntriesAndExits(){
        return EntriesAndExits;
    }

    public void moveHuman(Division division, Human human){
        human.setCurrentDivision(division);
    }

    public Division getClosestMedKit(Division division){
        UnorderedArrayList<Division> medkitsLocations = new UnorderedArrayList<>();

        for (Items item : items){
            if(item.getClass() == HealthKit.class){
                medkitsLocations.addToRear(item.getDivision());
            }
        }

        UnorderedListADT<Integer> cost = new UnorderedArrayList<>();

        for(Division div : medkitsLocations){
            Iterator it = map.iteratorShortestPath(division,div);
            int count = 0;

            while (it.hasNext()){
                count++;
                it.next();
            }

            cost.addToRear(count);
        }

        int min = (int) Double.POSITIVE_INFINITY;
        int divisionCounter = 0;
        for (int custo : cost){
            if (custo < min){
                min = custo;
            }
            divisionCounter++;
        }

        Division closestMedkit = medkitsLocations.get(divisionCounter);

        return closestMedkit;
    }

    public boolean checkIfMapIsValid(){
        return map.isConnected();
    }
}
