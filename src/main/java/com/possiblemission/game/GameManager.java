package com.possiblemission.game;

import com.possiblemission.Enums.Difficulty;
import com.possiblemission.datastructures.abstractdatatypes.lists.unordered.UnorderedArrayList;
import com.possiblemission.datastructures.abstractdatatypes.queues.LinkedQueue;
import com.possiblemission.entities.Armour;
import com.possiblemission.entities.Enemy;
import com.possiblemission.entities.HealthKit;
import com.possiblemission.entities.Player;
import com.possiblemission.entities.abstractEntities.Human;
import com.possiblemission.entities.abstractEntities.Items;
import pt.ipp.estg.ed.QueueADT;

import java.util.Iterator;
import java.util.Scanner;

/**
 * Manages the game flow, including player and enemy turns, movement, battles, and game state.
 */
public class GameManager {
    /** The game instance being managed. */
    private Game game;
    /** Queue for managing the turn order of humans in the game. */
    private QueueADT<Human> turn;
    /** Indicates whether the game is played manually or automatically. */
    private boolean isManual;
    /** The human whose turn is currently being processed. */
    private Human currentTurn;
    /** List of divisions where the player has moved. */
    private UnorderedArrayList<Division> moves;
    /** Flag to determine if the player is escaping the game map. */
    private boolean playerIsEscaping;
    /** List of current enemies division. */
    UnorderedArrayList<Enemy> enemiesDivision;

    /**
     * Constructs a GameManager instance.
     *
     * @param game       The game instance.
     * @param isManual   Whether the game is manual or automatic.
     * @param playerName The name of the player.
     */
    public GameManager(Game game, boolean isManual, String playerName){
        this.game = game;
        this.turn = new LinkedQueue<>();
        this.isManual = isManual;
        this.playerIsEscaping = false;
        this.moves = new UnorderedArrayList<>();
        this.enemiesDivision = new UnorderedArrayList<>();
        game.addPlayer(initializePlayer(playerName));
        turn.enqueue(game.getPlayer());
        for(Enemy enemy : game.getEnemies()){
            turn.enqueue(enemy);
        }
    }

    /**
     * Checks if the player is winning by reaching the target division.
     *
     * @return true if the player wins, false otherwise.
     */
    private boolean PlayerWinning(){
        return game.getPlayer().getCurrentDivision() == game.getTarget().getDivision();
    }

    /**
     * Starts the game based on the selected difficulty and mode.
     *
     * @param difficulty The difficulty level of the game.
     * @return true if the player wins, false otherwise.
     */
    public boolean startGame(Difficulty difficulty){
        handleDifficulty(difficulty);
        if(isManual){
            setStartDiv();
            return ManualGame();
        }else {
            return AutomaticGame();
        }
    }

    /**
     * Prompts the player to select a starting division.
     */
    private void setStartDiv(){
        Scanner in = new Scanner(System.in);
        int start = 0;
        UnorderedArrayList<Division> entries = game.getEntriesAndExits();

        while(start == 0 && start < entries.size() ) {
            System.out.println("Choose where to start\n");
            for(int i = 0; i < entries.size(); i++) {
                Division division = entries.get(i);
                System.out.println(i+1+"-"+division.getName());
            }
            start = in.nextInt();
            game.getPlayer().setCurrentDivision(entries.get(start-1));
        }
    }

    /**
     * Adjusts game settings based on the selected difficulty.
     *
     * @param difficulty The difficulty level of the game.
     */
    private void handleDifficulty(Difficulty difficulty){
        UnorderedArrayList<Enemy> enemies = (UnorderedArrayList<Enemy>) game.getEnemies();
        if(difficulty == Difficulty.EASY){
            int[] HealthPool = new int[] {10,15,20};
            for(Enemy enemy : enemies){
                enemy.setHealth(HealthPool[(int)(Math.random() * HealthPool.length)]);
            }
            game.getPlayer().setBackpackSize(4);
        } else if (difficulty == Difficulty.MEDIUM) {
            int[] HealthPool = new int[] {25,30,35};
            for(Enemy enemy : enemies){
                enemy.setHealth(HealthPool[(int)(Math.random() * HealthPool.length)]);
            }
            game.getPlayer().setBackpackSize(2);
        } else if (difficulty == Difficulty.HARD) {
            int[] HealthPool = new int[] {40,45,60};
            for(Enemy enemy : enemies){
                enemy.setHealth(HealthPool[(int)(Math.random() * HealthPool.length)]);
            }
            game.getPlayer().setBackpackSize(1);
        }
    }

    /**
     * Executes the game loop for manual mode.
     *
     * @return true if the player wins, false otherwise.
     */
    private Boolean ManualGame(){
        while(true){
            Human human = turn.dequeue();
            while(human.getHealth() <= 0){
                human = turn.dequeue();
            }
            currentTurn = human;
            if(human.getClass() == Player.class){
                if(!human.isInBattle()){
                    System.out.println("Enemies: ");
                    for(Enemy enemy : game.getEnemies()){
                        System.out.println(enemy.getName()+ " Division: " + enemy.getCurrentDivision().getName());
                    }

                    System.out.println("Items: ");
                    for(Items item : game.getItems()){
                        System.out.println(item.toString());
                    }

                    System.out.println("Target: "+ game.getTarget().getDivision().getName());

                    System.out.println("Current Division: "+ game.getPlayer().getCurrentDivision().getName());
                    int choice = 0;
                    while(choice != 1 && choice != 2){

                        System.out.println("Want to 1-move or use 2-Healthkit? HK: ");
                        if(((Player) human).hasHealthKits()){
                            System.out.println(((Player) human).getTopHealthKit().getValue());
                        }
                        Scanner scanner = new Scanner(System.in);
                        choice = scanner.nextInt();
                        if (choice == 2 && ((Player) human).getTopHealthKit() == null) {
                            System.out.println(human.getName() + " doesn't have medkits");
                            choice = 0;
                        }
                    }


                    if(choice == 1){
                        if(handleManualMove(human)){
                            return true;
                        }
                        if(!handleEnemies(human)){
                            return false;
                        }
                        handleItems(human);
                    }else {
                        System.out.println(human.getName() + " heals himself: +" +((Player) human).getTopHealthKit().getValue());
                        ((Player) human).useHealthKit();
                        System.out.println(human.getName() + "'s health: " + human.getHealth());
                    }
                }else{
                    enemiesDivision = (UnorderedArrayList<Enemy>) human.getCurrentDivision().getEnemies();
                    boolean playerWins = Battle(human,enemiesDivision);
                    if(!playerWins){
                        return false;
                    }
                }

                if(PlayerWinning()){
                    playerIsEscaping = true;
                    System.out.println(human.getName() + " captured the target!");
                }

            }else{
                Boolean enemyTurn = EnemyTurn(human);
                if(enemyTurn != null){
                    return enemyTurn;
                }

            }
            turn.enqueue(human);
        }
    }


    /**
     * Executes the game loop for automatic mode.
     *
     * @return true if the player wins, false otherwise.
     */
    private boolean AutomaticGame(){
        while(true){
            Human human = turn.dequeue();
            while(human.getHealth() <= 0){
                human = turn.dequeue();
            }
            currentTurn = human;
            if(human.getClass() == Player.class){
                Iterator<Division> it = null;
                if(((Player) human).hasHealthKits() && (((Player) human).getMaxHealth() - human.getHealth()) >= ((Player) human).getTopHealthKit().getValue()){
                    System.out.println(human.getName() + " heals himself: +" +((Player) human).getTopHealthKit().getValue());
                    ((Player) human).useHealthKit();
                    System.out.println(human.getName() + "'s health: " + human.getHealth());
                } else if(human.isInBattle()){
                    enemiesDivision = (UnorderedArrayList<Enemy>) human.getCurrentDivision().getEnemies();
                    boolean playerWins = Battle(human,enemiesDivision);
                    if(!playerWins){
                        return false;
                    }
                }else{
                    if(handleMovement(human)){
                        return true;
                    }
                    if(!handleEnemies(human)){
                        return false;
                    }
                    handleItems(human);
                }
                if(PlayerWinning()){
                    playerIsEscaping = true;
                    System.out.println(human.getName() + " captured the target!");
                }

            }else{
                Boolean enemyTurn = EnemyTurn(human);
                if(enemyTurn != null){
                    return enemyTurn;
                }

            }
            turn.enqueue(human);
        }
    }

    /**
     *
     * Battle method that handles the fight.
     *
     * @param human the player
     * @param enemies list of enemies in the fight
     * @return true if player still alive , false if he dies.
     */
    private boolean Battle(Human human, UnorderedArrayList<Enemy> enemies){
        if(currentTurn.getClass().equals(Player.class)){
            PlayerStrike(human, enemies);
            if(enemies.isEmpty()){
                human.setIsInBattle(false);
                return true;
            }
        }else{
            human.setHealth(human.getHealth() - enemies.first().getPower());
            System.out.println(enemies.first().getName() + " strikes " + enemies.first().getPower() + " health from: " + human.getName());
        }
        return human.getHealth() > 0;
    }

    /**
     *
     * Method that represents the player action in combat.
     *
     * @param human the player
     * @param enemies list of enemies to strike
     */
    private void PlayerStrike(Human human, UnorderedArrayList<Enemy> enemies) {
        Iterator<Enemy> iterator = enemies.iterator();
        while (iterator.hasNext()) {
            Enemy enemy = iterator.next();
            enemy.setHealth(enemy.getHealth() - human.getPower());
            System.out.println(human.getName() + " strikes " + human.getPower() + " health from: " + enemy.getName());
            if (enemy.getHealth() <= 0) {
                System.out.println(enemy.getName() + " dies...");
                iterator.remove();
                game.removeEnemy(enemy);
                enemy.setCurrentDivision(null);
            }
        }
    }


    /**
     * Initializes the player entity with a name and default attributes.
     *
     * @param name The player's name.
     * @return The initialized player.
     */
    private Player initializePlayer(String name){
        Player player = new Player(name,20,100);
        if(!isManual){
            player.setCurrentDivision(game.getBestEntry(game.getTarget().getDivision()));
        }
        return player;
    }

    /**
     * Method that handles the Enemy turn
     *
     * @param human enemy
     * @return false if player dies in enemy turn, null if nothing happens
     */
    private Boolean EnemyTurn(Human human){
        UnorderedArrayList<Enemy> enemies = new UnorderedArrayList<>();
        if(!human.isInBattle()){
            Enemy enemy = (Enemy) human;
            Division currDiv = enemy.getCurrentDivision();
            Division lastDiv = enemy.getLastDiv();
            enemy.setLastDiv(currDiv);
            currDiv.removeEnemy(enemy);
            if(enemy.getDept() == 2){
                enemy.subtractDept();
                game.moveHuman(lastDiv,enemy);
                enemy.getCurrentDivision().addEnemy(enemy);
                System.out.println(enemy.getName()+ " moved to: " + lastDiv.getName());
            }else{
                Iterator<Division> it = game.getMap().iteratorBFS(enemy.getCurrentDivision());
                if(it.hasNext()){
                    it.next();
                    Division nextDiv = it.next();
                    if (nextDiv == enemy.getBaseDiv()) {
                        enemy.subtractDept();
                    }else{
                        enemy.addDept();
                    }
                    game.moveHuman(nextDiv, enemy);
                    nextDiv.addEnemy(enemy);
                    System.out.println(enemy.getName()+ " moved to: " + nextDiv.getName());
                }
            }
            if(enemy.getCurrentDivision().equals(game.getPlayer().getCurrentDivision())){
                enemies = (UnorderedArrayList<Enemy>) enemy.getCurrentDivision().getEnemies();

                System.out.println(enemy.getName() + " started battle with " + human.getName());
                human.setIsInBattle(true);
                game.getPlayer().setIsInBattle(true);
                boolean playerWinsBattle = Battle(game.getPlayer(),enemies);

                if(!playerWinsBattle){
                    return false;
                }
            }
        }else{
            enemies = (UnorderedArrayList<Enemy>) human.getCurrentDivision().getEnemies();
            boolean playerWinsBattle = Battle(game.getPlayer(),enemies);

            if(!playerWinsBattle){
                return false;
            }
        }

        return null;
    }

    /**
     * Method that returns the game instance.
     *
     * @return game instance
     */
    public Game getGame(){
        return game;
    }

    /**
     * Method that returns the player moves during the game.
     *
     * @return list of the player moves
     */
    public UnorderedArrayList<Division> getMoves(){
        return moves;
    }


    /**
     * Handles the items found by the current human in their division.
     *
     * @param human The human entity.
     */
    private void handleItems(Human human){
        Items item = game.hasItem(human.getCurrentDivision());
        if(item != null){
            System.out.println(human.getName() + " found an item!");
            if(item.getClass().equals(HealthKit.class)){
                System.out.println(human.getName() + " added a healthkit with: "+ item.getValue() +" health");
                ((Player) human).addHealthKit((HealthKit) item);
                game.getItems().remove(item);
                human.getCurrentDivision().removeItem();
            }else{
                System.out.println(human.getName() + " used a armour: +" + item.getValue());
                ((Player) human).useArmour((Armour) item);
            }
        }
    }

    /**
     * Method that handles if the Player is in the same division as enemies.
     *
     * @param human player
     * @return true if player alive, false if he dies.
     */
    private boolean handleEnemies(Human human){
        enemiesDivision = game.hasEnemies(human.getCurrentDivision());
        if(!enemiesDivision.isEmpty()){
            System.out.println(human.getName() + " started battle");
            human.setIsInBattle(true);
            for(Enemy enemy: human.getCurrentDivision().getEnemies()){
                enemy.setIsInBattle(true);
            }
            return Battle(human, enemiesDivision);
        }
        return true;
    }

    /**
     * Method that handles automatic player movement.
     *
     * @param human the player
     * @return true if player escapes with target, false if it is in game
     */
    private boolean handleMovement(Human human){
        Iterator<Division> it = null;
        if(playerIsEscaping){
            it = game.getMap().iteratorShortestPath(human.getCurrentDivision(),game.getClosestExit(human.getCurrentDivision()));
        } else if (!game.hasMedKits()) {
            it = game.getMap().iteratorShortestPath(human.getCurrentDivision(), game.getTarget().getDivision());
        }else{
            it = game.getMap().iteratorShortestPath(human.getCurrentDivision(),game.getClosestMedKit(human.getCurrentDivision()));
        }
        if(it.hasNext()){
            it.next();
            Division move = it.next();
            game.moveHuman(move,human);
            moves.addToRear(move);
            System.out.println(human.getName() + " moved to " + human.getCurrentDivision());
            return game.isExit(human.getCurrentDivision()) && playerIsEscaping;
        }
        return false;
    }

    /**
     * Method that handles the player Manual game moves
     *
     * @param human the player
     * @return true if the player escapes with the target, false if the game continues
     */
    private boolean handleManualMove(Human human){
        Iterator<Division> itTarget = null;
        Iterator<Division> itHealth = null;
        Iterator<Division> itExit = null;
        Division target = null;
        Division health = null;
        Division exit = null;
        if(playerIsEscaping){
            itExit = game.getMap().iteratorShortestPath(human.getCurrentDivision(),game.getClosestExit(human.getCurrentDivision()));
        } else {
            itTarget = game.getMap().iteratorShortestPath(human.getCurrentDivision(), game.getTarget().getDivision());
        }
        if (game.hasMedKits()){
            itHealth = game.getMap().iteratorShortestPath(human.getCurrentDivision(),game.getClosestMedKit(human.getCurrentDivision()));
        }

        if(itTarget!=null) {
            target = getDivFromIterator(itTarget);
        }
        if(itHealth!=null) {
            health = getDivFromIterator(itHealth);
        }
        if(itExit!=null) {
            exit = getDivFromIterator(itExit);
        }

        int choice = 0;

        if(target != null){
            System.out.println("PATHS: \n1-Target: "+ target.getName());
        }
        if(health != null){
            System.out.println("PATHS: \n2-Health: "+ health.getName());
        }
        if(exit != null){
            System.out.println("PATHS: \n3-Exit: "+ exit.getName());
        }
        Scanner scan = new Scanner(System.in);
        Division move = null;

        while(choice != 1 && choice != 2 && choice != 3){
            choice = scan.nextInt();
            if(choice == 1 && target != null){
                move = target;
            }else if(choice == 2 && health != null){
                move = health;
            }else if(choice == 3 && exit != null){
                move = exit;
            }else{
                System.out.println("Invalid choice");
            }
        }

        game.moveHuman(move,human);
        moves.addToRear(move);
        System.out.println(human.getName() + " moved to " + human.getCurrentDivision());
        return game.isExit(human.getCurrentDivision()) && playerIsEscaping;

    }

    /**
     * Helper method that returns the next division from the iterator.
     *
     * @param it iterator
     * @return the next division
     */
    private Division getDivFromIterator(Iterator<Division> it){
        if(it.hasNext()) {
            it.next();
            return it.next();
        }
        return null;
    }


}
