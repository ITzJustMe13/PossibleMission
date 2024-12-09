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

    private Difficulty difficulty;

    public GameManager(Game game, boolean isManual){
        this.game = game;
        this.turn = new LinkedQueue<>();
        this.isManual = isManual;
        turn.enqueue(game.getPlayer());
        for(Enemy enemy : game.getEnemies()){
            turn.enqueue(enemy);
        }
    }

    private boolean PlayerWins(){
        return game.getPlayer().getCurrentDivision() == game.getTarget().getDivision();
    }


    public boolean StartGame(Difficulty difficulty,String PlayerName){
        game.addPlayer(initializePlayer(PlayerName));
        UnorderedArrayList<Enemy> enemies = (UnorderedArrayList<Enemy>) game.getEnemies();
        if(difficulty == Difficulty.EASY){
            int[] HealthPool = new int[] {10,15,20};
            for(Enemy enemy : enemies){
                enemy.setHealth(HealthPool[(int)(Math.random() * HealthPool.length)]);
            }
        } else if (difficulty == Difficulty.MEDIUM) {
            int[] HealthPool = new int[] {25,30,35};
            for(Enemy enemy : enemies){
                enemy.setHealth(HealthPool[(int)(Math.random() * HealthPool.length)]);
            }
        } else if (difficulty == Difficulty.HARD) {
            int[] HealthPool = new int[] {40,45,60};
            for(Enemy enemy : enemies){
                enemy.setHealth(HealthPool[(int)(Math.random() * HealthPool.length)]);
            }
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
            currentTurn = human;
            if(human.getClass() == Player.class){
                Iterator<Division> it = game.getMap().iteratorShortestPath(human.getCurrentDivision(),game.getClosestMedKit(human.getCurrentDivision()));
                if(it.hasNext()){
                    game.moveHuman(it.next(),human);
                }

                UnorderedArrayList<Enemy> enemiesDivision = game.hasEnemies(human.getCurrentDivision());

                if(!enemiesDivision.isEmpty()){
                    boolean playerWinsBattle = Battle(human, enemiesDivision);
                    if(!playerWinsBattle){
                        return false;
                    }
                }

                Items item = game.hasItem(human.getCurrentDivision());
                if(item != null){
                    if(item.getClass().equals(HealthKit.class)){
                        ((Player) human).addHealthKit((HealthKit) item);
                    }else{
                        ((Player) human).useArmour((Armour) item);
                    }
                }

                if(((Player) human).hasHealthKits()){
                    if((((Player) human).getMaxHealth() - human.getHealth()) >= ((Player) human).getTopHealthKit().getValue()){
                        ((Player) human).useHealthKit();
                    }
                }

                if(PlayerWins()){
                    return true;
                }

            }else{
                Boolean enemyTurn = EnemyTurn(human);
                if(EnemyTurn(human) != null){
                    return enemyTurn;
                }
            }
        }
    }


    private boolean Battle(Human human, UnorderedArrayList<Enemy> enemies){
        do{
            if(currentTurn.getClass().equals(Player.class)){
                PlayerStrike(human, enemies);
                human.setHealth(human.getHealth() - enemies.first().getPower());
                System.out.println(enemies.first().getName() + " strikes " + enemies.first().getPower() + " health from: Player");
            }else{
                human.setHealth(human.getHealth() - enemies.first().getPower());
                System.out.println(enemies.first().getName() + " strikes " + enemies.first().getPower() + " health from: Player");
                PlayerStrike(human, enemies);
            }
        }while (human.getHealth() != 0  || !enemies.isEmpty());
        return enemies.isEmpty();

    }

    private void PlayerStrike(Human human, UnorderedArrayList<Enemy> enemies) {
        enemies.first().setHealth(enemies.first().getHealth() - human.getPower());
        System.out.println("Player strikes " + human.getPower() + " health from: " + enemies.first().getName());
        if(enemies.first().getHealth() <= 0){
            System.out.println(enemies.first().getName() + " dies...");
            enemies.removeFirst();
        }
    }

    private Player initializePlayer(String name){
        return new Player(name,20,100);
    }

    private Boolean EnemyTurn(Human human){
        Iterator<Division> it = game.getMap().iteratorBFS(human.getCurrentDivision());
    }


}
