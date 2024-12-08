package com.possiblemission.game;

import com.possiblemission.datastructures.abstractdatatypes.queues.LinkedQueue;
import com.possiblemission.entities.Enemy;
import com.possiblemission.entities.Player;
import com.possiblemission.entities.abstractEntities.Human;
import pt.ipp.estg.ed.QueueADT;

import java.util.Iterator;

public class GameManager {

    private Game game;

    private QueueADT<Human> turn;

    private boolean isManual;

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


    public boolean StartGame(){
        if(isManual){
             return ManualGame();
        }else {
            return AutomaticGame();
        }
    }

    private boolean ManualGame(){
        return true;
    }

    private boolean AutomaticGame(){
        Human human = turn.dequeue();
        if(human.getClass() == Player.class){
            Iterator<Division> it = game.getMap().iteratorShortestPath(human.getCurrentDivision(),game.getClosestMedKit(human.getCurrentDivision()));
            if(it.hasNext()){
                game.moveHuman(it.next(),human);
            }

        }else{
            Iterator<Division> it = game.getMap().iteratorBFS(human.getCurrentDivision()); //tem de andar num raio de 2 nodes
        }
        return false;
    }


}
