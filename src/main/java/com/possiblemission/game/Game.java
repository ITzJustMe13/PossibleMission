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

/**
 * Represents the main game logic, including map management, player and enemy interactions,
 * and game state tracking.
 */
public class Game {

    /** The code name of the game. */
    private String codName;

    /** The version of the game. */
    private int version;

    /** The graph representing the game map with divisions and connections. */
    private ExtendedGraphADT<Division> map;

    /** The list of items available in the game. */
    private UnorderedListADT<Items> items;

    /** The target that the player needs to capture. */
    private Target alvo;

    /** The list of enemies in the game. */
    private UnorderedListADT<Enemy> enemies;

    /** The player instance in the game. */
    private Player player;

    /** The list of divisions marked as entries or exits. */
    private UnorderedListADT<Division> entriesAndExits;

    /**
     * Constructs a Game instance with the given code name and version.
     *
     * @param codName The code name of the game.
     * @param version The version of the game.
     */
    public Game(String codName, int version) {
        this.codName = codName;
        this.version = version;
        map = new ExtendedUndirectedMatrixGraph<>();
        items = new UnorderedArrayList<>();
        enemies = new UnorderedArrayList<>();
        entriesAndExits = new UnorderedArrayList<>();
    }

    /**
     * Adds a new division to the game map.
     *
     * @param divisionName The name of the new division.
     */
    public void addDivision(String divisionName) {
        Division newDivision = new Division(divisionName);
        map.addVertex(newDivision);
    }

    /**
     * Creates a link between two divisions in the game map.
     *
     * @param division1 The name of the first division.
     * @param division2 The name of the second division.
     */
    public void addLink(String division1, String division2) {
        Division div1 = getDivisionByName(division1);
        Division div2 = getDivisionByName(division2);
        map.addEdge(div1, div2);
    }

    /**
     * Retrieves a division by its name.
     *
     * @param divisionName The name of the division to retrieve.
     * @return The division with the specified name, or null if not found.
     */
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

    /**
     * Retrieves the game map.
     *
     * @return The graph representing the game map.
     */
    public ExtendedGraphADT<Division> getMap() {
        return map;
    }

    /**
     * Adds an item to the game.
     *
     * @param item The item to add.
     */
    public void addItem(Items item){
        items.addToRear(item);
    }

    /**
     * Retrieves the list of items in the game.
     *
     * @return The list of items.
     */
    public UnorderedListADT<Items> getItems(){
        return items;
    }

    /**
     * Adds a target to the game.
     *
     * @param target The target to add.
     */
    public void addTarget(Target target){
        this.alvo = target;
    }

    /**
     * Checks if there are any health kits available in the game.
     *
     * @return true if health kits are available, false otherwise.
     */
    public boolean hasMedKits(){
        for(Items item: items){
            if(item.getClass().equals(HealthKit.class)){
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves the target in the game.
     *
     * @return The target.
     */
    public Target getTarget(){
        return alvo;
    }

    /**
     * Retrieves the list of enemies in the game.
     *
     * @return The list of enemies.
     */
    public UnorderedListADT<Enemy> getEnemies(){
        return enemies;
    }

    /**
     * Adds an enemy to the game.
     *
     * @param enemy The enemy to add.
     */
    public void addEnemy(Enemy enemy){
        enemies.addToRear(enemy);
    }

    /**
     * Removes an enemy from the game.
     *
     * @param enemy The enemy to remove.
     */
    public void removeEnemy(Enemy enemy){
        enemies.remove(enemy);
    }


    /**
     * Adds the player to the game.
     *
     * @param player The player to add.
     */
    public void addPlayer(Player player){
        this.player = player;
    }

    /**
     * Retrieves the player in the game.
     *
     * @return The player.
     */
    public Player getPlayer(){
        return player;
    }

    /**
     * Marks a division as an entry or exit.
     *
     * @param division The division to mark.
     */
    public void addEntryOrExit(Division division){
        entriesAndExits.addToRear(division);
    }

    /**
     * Retrieves the list of entries and exits.
     *
     * @return The list of entries and exits.
     */
    public UnorderedArrayList<Division> getEntriesAndExits(){
        return (UnorderedArrayList<Division>) entriesAndExits;
    }

    /**
     * Moves a human to a specified division.
     *
     * @param division The division to move to.
     * @param human    The human to move.
     */
    public void moveHuman(Division division, Human human){
        human.setCurrentDivision(division);
    }

    /**
     * Helper method to calculate the path cost.
     *
     * @param start division to start
     * @param end target division
     * @return the cost
     */
    private int calculatePathCost(Division start, Division end) {
        Iterator<Division> iterator = map.iteratorShortestPath(start, end);
        int cost = 0;

        while (iterator.hasNext()) {
            cost++;
            iterator.next();
        }

        return cost;
    }

    /**
     * Method to find the closest division from one division to another from a list of target divisions.
     *
     * @param from division to start
     * @param targets list of target divisions
     * @return the closest division from the list of targets
     */
    private Division findClosestDivision(Division from, UnorderedArrayList<Division> targets) {
        Division closest = null;
        int minCost = Integer.MAX_VALUE;

        for (Division target : targets) {
            int cost = calculatePathCost(from, target);
            if (cost < minCost) {
                minCost = cost;
                closest = target;
            }
        }

        return closest;
    }

    /**
     * Method that returns the Health Kit locations.
     *
     * @return a list with all the Health kit locations.
     */
    private UnorderedArrayList<Division> getHealthKitLocations() {
        UnorderedArrayList<Division> medkitLocations = new UnorderedArrayList<>();

        for (Items item : items) {
            if (item instanceof HealthKit) {
                medkitLocations.addToRear(item.getDivision());
            }
        }

        return medkitLocations;
    }


    /**
     * Method that returns the closest HealthKit from a division.
     *
     * @param division start division
     * @return the closest division with a HealthKit
     */
    public Division getClosestMedKit(Division division) {
        UnorderedArrayList<Division> medkitLocations = getHealthKitLocations();
        return findClosestDivision(division, medkitLocations);
    }

    /**
     * Method that returns the best possible entry for the player to start the game.
     *
     * @param target the division of the target of the game
     * @return the best entry for the player to start
     */
    public Division getBestEntry(Division target) {
        return findClosestDivision(target, (UnorderedArrayList<Division>) entriesAndExits);
    }

    /**
     * Method that returns the closest exit from a given division.
     *
     * @param division given division
     * @return closest exit
     */
    public Division getClosestExit(Division division) {
        return findClosestDivision(division, (UnorderedArrayList<Division>) entriesAndExits);
    }

    /**
     * Checks if the game map is valid and fully connected.
     *
     * @return true if the map is valid, false otherwise.
     */
    public boolean checkIfMapIsValid(){
        return map.isConnected();
    }

    /**
     * Checks if a division contains enemies.
     *
     * @param division The division to check.
     * @return A list of enemies in the division.
     */
    public UnorderedArrayList<Enemy> hasEnemies(Division division){
        UnorderedArrayList<Enemy> enemiesDiv = new UnorderedArrayList<>();
        for(Enemy enemy: enemies){
            if(enemy.getCurrentDivision().equals(division)){
                enemiesDiv.addToRear(enemy);
            }
        }
        return enemiesDiv;
    }

    /**
     * Checks if a division contains an item.
     *
     * @param division The division to check.
     * @return The item in the division, or null if none exists.
     */
    public Items hasItem(Division division){
        return division.getItem();
    }

    /**
     * Checks if a division is marked as an exit.
     *
     * @param division The division to check.
     * @return true if the division is an exit, false otherwise.
     */
    public boolean isExit(Division division) {
        return getEntriesAndExits().contains(division);
    }

    /**
     * Retrieves the code name of the game.
     *
     * @return The code name.
     */
    public String getCodName(){
        return codName;
    }

    /**
     * Retrieves the version of the game.
     *
     * @return The version.
     */
    public int getCodId(){
        return version;
    }
}
