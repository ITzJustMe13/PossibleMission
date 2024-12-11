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

public class GameManager {

    private Game game;

    private QueueADT<Human> turn;

    private boolean isManual;

    private Human currentTurn;

    private UnorderedArrayList<Division> moves;

    private boolean playerIsEscaping;

    UnorderedArrayList<Enemy> enemiesDivision;

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

    private boolean PlayerWins(){
        return game.getPlayer().getCurrentDivision() == game.getTarget().getDivision();
    }


    public boolean startGame(Difficulty difficulty){
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
        if(isManual){
            return ManualGame();
        }else {
            return AutomaticGame();
        }
    }

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
                    while(choice == 0 && choice != 1 && choice != 2){

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
                    }else if(choice == 2){
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

                if(PlayerWins()){
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
                if(PlayerWins()){
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

    private Player initializePlayer(String name){
        Player player = new Player(name,20,100);
        if(!isManual){
            player.setCurrentDivision(game.getBestEntry());
        }
        return player;
    }

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

    public Game getGame(){
        return game;
    }

    public UnorderedArrayList<Division> getMoves(){
        return moves;
    }


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

    private Division getDivFromIterator(Iterator<Division> it){
        if(it.hasNext()) {
            it.next();
            return it.next();
        }
        return null;
    }


}
