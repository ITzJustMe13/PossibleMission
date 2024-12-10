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
        return true;
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
                    System.out.println("Player heals himself: +" +((Player) human).getTopHealthKit().getValue());
                    ((Player) human).useHealthKit();
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
                    System.out.println("Player captured the target!");
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
            System.out.println(enemies.first().getName() + " strikes " + enemies.first().getPower() + " health from: Player");
        }
        return human.getHealth() > 0;
    }

    private void PlayerStrike(Human human, UnorderedArrayList<Enemy> enemies) {
        for(Enemy enemy: enemies){
            enemy.setHealth(enemy.getHealth() - human.getPower());
            System.out.println("Player strikes " + human.getPower() + " health from: " + enemy.getName());
            if(enemy.getHealth() <= 0){
                System.out.println(enemy.getName() + " dies...");
                enemies.remove(enemy);
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

                System.out.println(enemy.getName() + " started battle with Player");
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
            System.out.println("Player found an item!");
            if(item.getClass().equals(HealthKit.class)){
                System.out.println("Player added a healthkit with: "+ item.getValue() +" health");
                ((Player) human).addHealthKit((HealthKit) item);
                game.getItems().remove(item);
                human.getCurrentDivision().removeItem();
            }else{
                System.out.println("Player used a armour: +" + item.getValue());
                ((Player) human).useArmour((Armour) item);
            }
        }
    }

    private boolean handleEnemies(Human human){
        enemiesDivision = game.hasEnemies(human.getCurrentDivision());
        if(!enemiesDivision.isEmpty()){
            System.out.println("Player started battle");
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
        } else if (game.hasMedKits()) {
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


}
