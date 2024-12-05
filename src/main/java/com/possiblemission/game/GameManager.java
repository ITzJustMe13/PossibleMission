package com.possiblemission.game;

import com.possiblemission.datastructures.abstractdatatypes.queues.LinkedQueue;
import com.possiblemission.entities.Enemy;
import com.possiblemission.entities.abstractEntities.Human;
import pt.ipp.estg.ed.QueueADT;

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
            ManualGame();
        }else {
            AutomaticGame();
        }
    }

    private boolean ManualGame(){
        return true;
    }

    private boolean AutomaticGame(){
        return true;
    }


}