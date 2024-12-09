package com.possiblemission.game;

import com.possiblemission.datastructures.abstractdatatypes.extended.ExtendedGraphADT;
import com.possiblemission.datastructures.abstractdatatypes.extended.ExtendedUndirectedMatrixGraph;
import com.possiblemission.datastructures.abstractdatatypes.lists.unordered.UnorderedArrayList;
import com.possiblemission.entities.Enemy;
import com.possiblemission.entities.HealthKit;
import com.possiblemission.entities.Target;
import com.possiblemission.entities.abstractEntities.Human;
import com.possiblemission.entities.abstractEntities.Items;
import com.possiblemission.entities.Player;

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

    public boolean hasMedKits(){
        for(Items item: items){
            if(item.getClass().equals(HealthKit.class)){
                return true;
            }
        }
        return false;
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

    public UnorderedArrayList<Division> getEntriesAndExits(){
        return (UnorderedArrayList<Division>) EntriesAndExits;
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

        UnorderedArrayList<Integer> cost = new UnorderedArrayList<>();

        for(int i=0; i<medkitsLocations.size(); i++){
            Iterator<Division> it = map.iteratorShortestPath(division,medkitsLocations.get(i));
            int count = 0;

            while (it.hasNext()){
                count++;
                it.next();
            }

            cost.addToRear(count);
        }

        int min = (int) Double.POSITIVE_INFINITY;
        int i;
        for (i = 0; i < medkitsLocations.size(); i++){
            if (cost.get(i) < min){
                min = cost.get(i);
            }
        }

        return medkitsLocations.get(i-1);
    }

    public Division getBestEntry(){
        UnorderedArrayList<Integer> cost = new UnorderedArrayList<>();

        for(int i=0; i< getEntriesAndExits().size(); i++){
            Iterator<Division> it = map.iteratorShortestPath(getEntriesAndExits().get(i),alvo.getDivision());
            int count = 0;

            while (it.hasNext()){
                count++;
                it.next();
            }

            cost.addToRear(count);
        }

        int min = (int) Double.POSITIVE_INFINITY;
        int i;
        for (i = 0; i < getEntriesAndExits().size(); i++){
            if (cost.get(i) < min){
                min = cost.get(i);
            }
        }

        return getEntriesAndExits().get(i-1);
    }


    public Division getClosestExit(Division division){
        UnorderedArrayList<Integer> cost = new UnorderedArrayList<>();

        for(int i=0; i< getEntriesAndExits().size(); i++){
            Iterator<Division> it = map.iteratorShortestPath(division,getEntriesAndExits().get(i));
            int count = 0;

            while (it.hasNext()){
                count++;
                it.next();
            }

            cost.addToRear(count);
        }

        int min = (int) Double.POSITIVE_INFINITY;
        int i;
        for (i = 0; i < getEntriesAndExits().size(); i++){
            if (cost.get(i) < min){
                min = cost.get(i);
            }
        }

        return getEntriesAndExits().get(i-1);
    }

    public boolean checkIfMapIsValid(){
        return map.isConnected();
    }

    public UnorderedArrayList<Enemy> hasEnemies(Division division){
        UnorderedArrayList<Enemy> enemiesDiv = new UnorderedArrayList<>();
        for(Enemy enemy: enemies){
            if(enemy.getCurrentDivision().equals(division)){
                enemiesDiv.addToRear(enemy);
            }
        }
        return enemiesDiv;
    }

    public Items hasItem(Division division){
        return division.getItem();
    }

    public boolean isExit(Division division) {
        return getEntriesAndExits().contains(division);
    }

    public String getCodName(){
        return codName;
    }

    public int getCodId(){
        return version;
    }
}
